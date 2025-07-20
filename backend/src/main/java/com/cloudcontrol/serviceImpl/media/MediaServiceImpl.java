package com.cloudcontrol.serviceImpl.media;

import com.cloudcontrol.dto.media.MediaDeleteResponse;
import com.cloudcontrol.dto.media.MediaUploadResponse;
import com.cloudcontrol.dto.media.MediaInfo;
import com.cloudcontrol.entity.media.Media;
import com.cloudcontrol.repository.media.MediaRepository;
import com.cloudcontrol.service.media.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 素材服务实现类
 */
@Service
public class MediaServiceImpl implements MediaService {
    
    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
    
    private final MediaRepository mediaRepository;
    
    @Value("${app.upload.path:/uploads}")
    private String uploadPath;
    
    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;
    
    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    
    @Override
    @Transactional
    public MediaUploadResponse uploadMedia(MultipartFile file) {
        try {
            // 1. 验证文件
            validateFile(file);
            
            // 2. 生成文件信息
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String fileType = getFileType(fileExtension);
            String mediaType = getMediaType(fileExtension);
            
            // 3. 生成文件路径
            String relativePath = generateFilePath(originalFilename);
            String absolutePath = uploadPath + "/" + relativePath;
            
            // 4. 保存文件
            saveFile(file, absolutePath);
            
            // 5. 生成缩略图（如果是图片）
            String thumbnailPath = null;
            Integer width = null;
            Integer height = null;
            if ("image".equals(mediaType)) {
                thumbnailPath = generateThumbnail(absolutePath, relativePath);
                Dimension dimension = getImageDimension(absolutePath);
                width = (int) dimension.getWidth();
                height = (int) dimension.getHeight();
            }
            
            // 6. 创建媒体记录
            Media media = createMediaRecord(originalFilename, fileType, mediaType, 
                                         file.getSize(), relativePath, thumbnailPath, 
                                         width, height);
            
            // 7. 保存到数据库
            media = mediaRepository.save(media);
            
            // 8. 构建响应
            return buildUploadResponse(media, relativePath, thumbnailPath);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload media: " + e.getMessage());
        }
    }
    
    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        if (file.getSize() > 100 * 1024 * 1024) { // 100MB限制
            throw new RuntimeException("File size exceeds limit");
        }
        
        String filename = file.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            throw new RuntimeException("Invalid filename");
        }
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
    
    /**
     * 获取文件类型
     */
    private String getFileType(String extension) {
        return extension;
    }
    
    /**
     * 获取媒体类型
     */
    private String getMediaType(String extension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        String[] videoExtensions = {"mp4", "avi", "mov", "wmv", "flv", "webm"};
        String[] audioExtensions = {"mp3", "wav", "ogg", "aac", "flac"};
        
        for (String ext : imageExtensions) {
            if (ext.equals(extension)) {
                return "image";
            }
        }
        
        for (String ext : videoExtensions) {
            if (ext.equals(extension)) {
                return "video";
            }
        }
        
        for (String ext : audioExtensions) {
            if (ext.equals(extension)) {
                return "audio";
            }
        }
        
        return "file";
    }
    
    /**
     * 生成文件路径
     */
    private String generateFilePath(String originalFilename) {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        
        // 生成唯一文件名
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = getFileExtension(originalFilename);
        String filename = "F_" + uuid + "_" + System.currentTimeMillis() + "." + extension;
        
        return "upload/" + year + "/" + month + "/" + filename;
    }
    
    /**
     * 保存文件
     */
    private void saveFile(MultipartFile file, String absolutePath) throws IOException {
        Path path = Paths.get(absolutePath);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
    }
    
    /**
     * 生成缩略图
     */
    private String generateThumbnail(String originalPath, String relativePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(originalPath));
            if (originalImage == null) {
                return null;
            }
            
            // 计算缩略图尺寸
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int thumbnailWidth = 200;
            int thumbnailHeight = (int) ((double) originalHeight / originalWidth * thumbnailWidth);
            
            // 创建缩略图
            BufferedImage thumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = thumbnail.createGraphics();
            g.drawImage(originalImage, 0, 0, thumbnailWidth, thumbnailHeight, null);
            g.dispose();
            
            // 保存缩略图
            String thumbnailPath = relativePath.replace(".", "-200x200.");
            String absoluteThumbnailPath = uploadPath + "/" + thumbnailPath;
            Path path = Paths.get(absoluteThumbnailPath);
            Files.createDirectories(path.getParent());
            ImageIO.write(thumbnail, "png", new File(absoluteThumbnailPath));
            
            return thumbnailPath;
            
        } catch (Exception e) {
            System.out.println("Failed to generate thumbnail: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 获取图片尺寸
     */
    private Dimension getImageDimension(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (image != null) {
                return new Dimension(image.getWidth(), image.getHeight());
            }
        } catch (Exception e) {
            System.out.println("Failed to get image dimension: " + e.getMessage());
        }
        return new Dimension(0, 0);
    }
    
    /**
     * 创建媒体记录
     */
    private Media createMediaRecord(String originalFilename, String fileType, String mediaType,
                                  Long fileSize, String filePath, String thumbnailPath,
                                  Integer width, Integer height) {
        String titleRaw = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        
        Media media = new Media(titleRaw, fileType, getMimeType(fileType), mediaType, fileSize, filePath, 1);
        media.setThumbnailPath(thumbnailPath);
        media.setWidth(width);
        media.setHeight(height);
        media.setDuration(1760); // 默认持续时间
        media.setGuid(filePath);
        media.setSlug(generateSlug(filePath));
        media.setSourceUrl(baseUrl + "/" + filePath);
        media.setSrc(thumbnailPath != null ? baseUrl + "/" + thumbnailPath : baseUrl + "/" + filePath);
        
        return media;
    }
    
    /**
     * 获取MIME类型
     */
    private String getMimeType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "mov":
                return "video/quicktime";
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * 生成Slug
     */
    private String generateSlug(String filePath) {
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
        return filename.substring(0, filename.lastIndexOf("."));
    }
    
    /**
     * 构建上传响应
     */
    private MediaUploadResponse buildUploadResponse(Media media, String filePath, String thumbnailPath) {
        MediaUploadResponse response = new MediaUploadResponse();
        
        // 设置基本信息
        response.setId(media.getId());
        response.setTitleRaw(media.getTitleRaw());
        response.setDescription(media.getDescription());
        response.setFileType(media.getFileType());
        response.setMimeType(media.getMimeType());
        response.setMediaType(media.getMediaType());
        response.setAttachmentFilesize(media.getAttachmentFilesize());
        response.setAuthor(media.getAuthor());
        response.setPostStatus(media.getPostStatus());
        response.setCommentStatus(media.getCommentStatus());
        response.setPingStatus(media.getPingStatus());
        response.setParent(media.getParent());
        response.setGuid(media.getGuid());
        response.setSlug(media.getSlug());
        response.setSourceUrl(media.getSourceUrl());
        response.setSrc(media.getSrc());
        
        // 设置时间
        String timeFormat = "yyyy-MM-dd'T'HH:mm:ss";
        response.setDate(media.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
        response.setDateGmt(media.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
        response.setModified(media.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
        response.setModifiedGmt(media.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
        
        // 设置标题
        MediaUploadResponse.TitleInfo titleInfo = new MediaUploadResponse.TitleInfo();
        titleInfo.setRendered(null);
        response.setTitle(titleInfo);
        
        // 设置完整尺寸
        if (media.getWidth() != null && media.getHeight() != null) {
            MediaUploadResponse.FullSizeInfo fullSize = new MediaUploadResponse.FullSizeInfo();
            fullSize.setWidth(media.getWidth());
            fullSize.setHeight(media.getHeight());
            response.setFullSize(fullSize);
        }
        
        // 设置媒体详情
        MediaUploadResponse.MediaDetailsInfo mediaDetails = new MediaUploadResponse.MediaDetailsInfo();
        mediaDetails.setDuration(media.getDuration());
        mediaDetails.setFile(filePath);
        mediaDetails.setWidth(media.getWidth());
        mediaDetails.setHeight(media.getHeight());
        
        // 设置缩略图信息
        if (thumbnailPath != null) {
            MediaUploadResponse.SizesInfo sizes = new MediaUploadResponse.SizesInfo();
            MediaUploadResponse.ThumbnailInfo thumbnail = new MediaUploadResponse.ThumbnailInfo();
            thumbnail.setFile(thumbnailPath);
            thumbnail.setMimeType("image/png");
            thumbnail.setWidth(200);
            thumbnail.setHeight(media.getHeight() != null ? 
                (int) ((double) media.getHeight() / media.getWidth() * 200) : 150);
            sizes.setThumbnail(thumbnail);
            mediaDetails.setSizes(sizes);
        }
        
        // 设置图片元数据
        MediaUploadResponse.ImageMetaInfo imageMeta = new MediaUploadResponse.ImageMetaInfo();
        mediaDetails.setImageMeta(imageMeta);
        
        response.setMediaDetails(mediaDetails);
        
        return response;
    }
    
    @Override
    public MediaUploadResponse getMediaByPath(String filePath) {
        try {
            // 验证文件路径
            if (filePath == null || filePath.trim().isEmpty()) {
                throw new RuntimeException("File path cannot be empty");
            }
            
            // 查询媒体记录
            Media media = mediaRepository.findByFilePath(filePath);
            if (media == null) {
                throw new RuntimeException("Media not found with path: " + filePath);
            }
            
            // 构建响应
            return buildUploadResponse(media, media.getFilePath(), media.getThumbnailPath());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get media by path: " + e.getMessage());
        }
    }
    
    @Override
    public List<MediaUploadResponse> getMediaList(int page, int perPage, String flag) {
        try {
            // 参数验证和默认值设置
            if (page < 1) page = 1;
            if (perPage < 1) perPage = 12;
            if (perPage > 100) perPage = 100; // 限制最大每页数量
            
            // 创建分页对象
            Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "createdAt"));
            
            // 根据flag进行筛选查询
            Page<Media> mediaPage;
            if ("filter".equals(flag)) {
                // 这里可以根据具体需求添加筛选条件
                // 例如：只查询特定类型的文件、特定状态的文件等
                mediaPage = mediaRepository.findAll(pageable);
            } else {
                // 默认查询所有
                mediaPage = mediaRepository.findAll(pageable);
            }
            
            // 转换为响应对象
            return mediaPage.getContent().stream()
                    .map(media -> buildUploadResponse(media, media.getFilePath(), media.getThumbnailPath()))
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to get media list: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public MediaDeleteResponse deleteMediaById(Integer mediaId, boolean force) {
        try {
            logger.info("删除素材: mediaId={}, force={}", mediaId, force);
            
            // 查找素材
            var mediaOptional = mediaRepository.findById(mediaId);
            if (mediaOptional.isEmpty()) {
                throw new RuntimeException("Media not found with id: " + mediaId);
            }
            
            Media media = mediaOptional.get();
            
            // 构建删除前的素材信息
            MediaInfo mediaInfo = buildMediaInfo(media);
            
            // 删除文件
            deleteMediaFile(media);
            
            // 删除数据库记录
            mediaRepository.delete(media);
            
            logger.info("素材删除成功: mediaId={}", mediaId);
            
            return new MediaDeleteResponse(true, "Media deleted successfully", 1, List.of(mediaInfo));
            
        } catch (Exception e) {
            logger.error("删除素材失败: mediaId={}", mediaId, e);
            throw new RuntimeException("Failed to delete media: " + e.getMessage());
        }
    }
    
    /**
     * 构建素材信息
     */
    private MediaInfo buildMediaInfo(Media media) {
        MediaInfo mediaInfo = new MediaInfo();
        
        // 设置基本信息
        mediaInfo.setId(media.getId());
        mediaInfo.setName(media.getTitleRaw());
        mediaInfo.setUrl(media.getSourceUrl());
        mediaInfo.setSize(media.getAttachmentFilesize());
        mediaInfo.setType(media.getFileType());
        
        return mediaInfo;
    }
    
    /**
     * 删除素材文件
     */
    private void deleteMediaFile(Media media) {
        try {
            // 删除主文件
            if (media.getFilePath() != null) {
                String absolutePath = uploadPath + "/" + media.getFilePath();
                Path filePath = Paths.get(absolutePath);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    logger.info("删除主文件: {}", absolutePath);
                }
            }
            
            // 删除缩略图
            if (media.getThumbnailPath() != null) {
                String absoluteThumbnailPath = uploadPath + "/" + media.getThumbnailPath();
                Path thumbnailPath = Paths.get(absoluteThumbnailPath);
                if (Files.exists(thumbnailPath)) {
                    Files.delete(thumbnailPath);
                    logger.info("删除缩略图: {}", absoluteThumbnailPath);
                }
            }
            
        } catch (Exception e) {
            logger.warn("删除文件失败: mediaId={}", media.getId(), e);
            // 不抛出异常，允许删除数据库记录
        }
    }
} 