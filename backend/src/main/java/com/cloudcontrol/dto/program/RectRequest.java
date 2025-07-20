package com.cloudcontrol.dto.program;

/**
 * 矩形区域请求DTO
 */
public class RectRequest {
    
    private Integer rectHeight;
    private Integer rectWidth;
    private Integer x;
    private Integer y;
    private Integer centeralAlign;
    
    public RectRequest() {}
    
    public RectRequest(Integer rectHeight, Integer rectWidth, Integer x, Integer y, Integer centeralAlign) {
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
        this.x = x;
        this.y = y;
        this.centeralAlign = centeralAlign;
    }
    
    // getter/setter
    public Integer getRectHeight() { return rectHeight; }
    public void setRectHeight(Integer rectHeight) { this.rectHeight = rectHeight; }
    
    public Integer getRectWidth() { return rectWidth; }
    public void setRectWidth(Integer rectWidth) { this.rectWidth = rectWidth; }
    
    public Integer getX() { return x; }
    public void setX(Integer x) { this.x = x; }
    
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }
    
    public Integer getCenteralAlign() { return centeralAlign; }
    public void setCenteralAlign(Integer centeralAlign) { this.centeralAlign = centeralAlign; }
} 