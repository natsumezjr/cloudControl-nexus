# 简化创建节目API文档

## 概述

该接口在 2.6.6-20240611 版本之后支持，用于创建单窗口类型和多窗口类型的节目。

## 单窗口类型节目

### 1. 新建网页节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/web`
- **Method**: `POST`
- **描述**: 创建网页节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| url | 是 | String | 网页链接 | "https://www.baidu.com/" |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "url": "https://www.baidu.com/"
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 2. 新建文件节目或同步节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/document`
- **Method**: `POST`
- **描述**: 创建文件节目或同步节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| mediaId | 是 | Integer | 素材id | 16110 |
| syncProgram | 是 | Integer | 是否同步节目。0: 普通文件节目，1：同步节目 | 1 |
| constrainedProportion | 是 | Integer | 是否保持约束比例，0：不保持宽高比，1：约束比例 | 1 |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "mediaId": 16110,
    "syncProgram": 1,
    "constrainedProportion": 1
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 3. 新建单行文本或多行文本节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/lineText`
- **Method**: `POST`
- **描述**: 创建单行文本或多行文本节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| fontSize | 是 | Integer | 字体大小 | 16 |
| lineText | 是 | String | 文本内容 | "文本" |
| lineTextType | 是 | Integer | 文本类型，0：单行文本, 1：多行文本 | 1 |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "lineText": "文本",
    "lineTextType": 1
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 4. 新建时钟节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/clock`
- **Method**: `POST`
- **描述**: 创建时钟节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| fontSize | 是 | Integer | 字体大小 | 16 |
| clockType | 是 | Integer | 时钟类型，0: 数字时钟，1：模拟时钟 | 1 |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "clockType": 1
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 5. 新建天气节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/weather`
- **Method**: `POST`
- **描述**: 创建天气节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| fontSize | 是 | Integer | 字体大小 | 16 |
| regionName | 是 | String | 天气区域名 | "北京" |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "regionName": "北京"
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 6. 新建倒计时节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/countDown`
- **Method**: `POST`
- **描述**: 创建倒计时节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长 | 8000 |
| fontSize | 是 | Integer | 字体大小 | 16 |
| endDateTime | 是 | String | 倒计时终点日期和时间，格式为：("2024-01-19 20:43:54") | "2025-01-19 20:43:54" |

**请求示例**
```json
{
    "programName": "programName",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "endDateTime": "2025-01-19 20:43:54"
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 7. 新建环境节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/environment`
- **Method**: `POST`
- **描述**: 创建环境节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 56 |
| durationMillis | 是 | Integer | 播放时长，单位 ms | 9000 |
| fontSize | 是 | Integer | 字体大小，单位像素 | 18 |
| prefix | 否 | String | 固定文本前缀。当 type 为 28（烟雾）时，为大于临界值时显示的固定文本。烟雾值 + 前缀 | "当前温度: " |
| suffix | 否 | String | 固定文本后缀。当 type 为 28（烟雾）时，为小于临界值时显示的固定文本。烟雾值 + 后缀 | "°C" |
| environmentType | 是 | Integer | 环境窗口类型。21：湿度、22：温度、23：噪声、24：空气指数、28：烟雾 | 22 |
| temperatureShowAsFahrenheit | 否 | Integer | 温度是否显示为华氏度（°F）。0：否（°C），1：是（°F），仅 type 为 22（温度）时适用 | 0 |
| temperatureOffset | 否 | Integer | 温度偏移量，仅 type 为 22（温度）时适用 | 2 |
| smokeStandard | 否 | Integer | 烟雾临界值（ppm），仅 type 为 28（烟雾）时适用 | 200 |

**新建环境温度节目示例**
```json
{
    "programName": "programEnvironment",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "prefix": "当前温度: ",
    "suffix": "°C",
    "environmentType": 22,
    "temperatureShowAsFahrenheit": 0,
    "temperatureOffset": 5
}
```

**新建环境烟雾节目示例**
```json
{
    "programName": "programEnvironment",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "fontSize": 16,
    "prefix": "smoke",
    "suffix": "smoke",
    "environmentType": 28,
    "smokeStandard": 100
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

### 8. 新建新闻节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate/rss`
- **Method**: `POST`
- **描述**: 创建新闻节目，默认窗口铺满屏幕

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 580 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| durationMillis | 是 | Integer | 播放时长，单位 ms | 9000 |
| fontSize | 是 | Integer | 字体大小，单位像素 | 18 |
| rssUrl | 是 | String | 新闻聚合路径 | "https://www.yahoo.com/news/rss" |
| refreshIntervalMs | 否 | Integer | 刷新间隔时间，单位毫秒 | 600000 |
| scrollSpeed | 否 | Integer | 滚动速度，单位(px/s) | 60 |

**请求示例**
```json
{
    "programName": "programRss",
    "screenWidth": 580,
    "screenHeight": 512,
    "durationMillis": 9000,
    "fontSize": 18,
    "rssUrl": "https://www.yahoo.com/news/rss",
    "refreshIntervalMs": 5000,
    "scrollSpeed": 600
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

## 多窗口类型节目

### 创建多窗口节目

**基本信息**
- **URL**: `/wp-json/wp/v2/programs/simplifyCreate`
- **Method**: `POST`
- **描述**: 创建多种类型窗口的节目

**请求参数**

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programName | 是 | String | 节目名 | "programName" |
| screenWidth | 是 | Integer | 屏幕宽 | 512 |
| screenHeight | 是 | Integer | 屏幕高 | 256 |
| regions | 是 | Array | 窗口数组 | [] |

**regions中的regionType类型**
- `"document"`：文件窗口
- `"lineText"`：文本窗口
- `"clock"`：时钟窗口
- `"weather"`：天气窗口
- `"web"`：网页窗口
- `"countDown"`：倒计时窗口
- `"environment"`：环境窗口
- `"rss"`：新闻窗口

**创建网页节目示例**
```json
{
    "programName": "programName",
    "screenWidth": 512,
    "screenHeight": 256,
    "regions": [
        {
            "regionType": "web",
            "url": "https://www.baidu.com/",
            "durationMillis": 8000,
            "rect": {
                "rectHeight": 256,
                "rectWidth": 512,
                "x": 0,
                "y": 0
            }
        }
    ]
}
```

**创建文件节目示例**
```json
{
    "programName": "programName",
    "screenWidth": 680,
    "screenHeight": 512,
    "regions": [
        {
            "regionType": "document",
            "syncProgram": 0,
            "medium": [
                {
                    "mediaId": 13789,
                    "durationMillis": 8000,
                    "constrainedProportion": 0
                },
                {
                    "mediaId": 16110,
                    "constrainedProportion": 1
                }
            ],
            "rect": {
                "rectHeight": 256,
                "rectWidth": 580,
                "x": 0,
                "y": 0
            }
        }
    ]
}
```

**创建多种类型窗口的节目示例**
```json
{
    "programName": "programName",
    "screenWidth": 1280,
    "screenHeight": 512,
    "regions": [
        {
            "regionType": "document",
            "syncProgram": 0,
            "medium": [
                {
                    "mediaId": 13789,
                    "durationMillis": 8000
                }
            ]
        },
        {
            "regionType": "lineText",
            "text": "文本内容"
        },
        {
            "regionType": "clock",
            "clockType": 0
        },
        {
            "regionType": "weather",
            "regionName": "北京"
        },
        {
            "regionType": "web",
            "url": "https://www.baidu.com/"
        },
        {
            "regionType": "countDown",
            "endDateTime": "2025-01-20 10:56:06"
        }
    ]
}
```

**响应示例**
```json
{
    "programId": 16989
}
```

## 错误处理

### 400 Bad Request
- 请求体为空或格式错误
- 缺少必填参数
- 参数类型错误

### 500 Internal Server Error
- 服务器内部错误
- 数据库操作失败

## 注意事项

1. **单窗口节目**: 默认窗口铺满整个屏幕
2. **多窗口节目**: regions中的顺序代表窗口层级，后面的会覆盖前面的
3. **参数验证**: 所有必填参数都必须提供
4. **节目ID**: 返回的programId用于后续的节目管理操作
5. **事务处理**: 创建操作在事务中执行，确保数据一致性
6. **日志记录**: 所有创建操作都会记录详细的日志信息

## 使用示例

### 创建网页节目
```bash
curl -X POST /wp-json/wp/v2/programs/simplifyCreate/web \
  -H "Content-Type: application/json" \
  -d '{
    "programName": "MyWebProgram",
    "screenWidth": 580,
    "screenHeight": 256,
    "durationMillis": 8000,
    "url": "https://www.baidu.com/"
  }'
```

### 创建多窗口节目
```bash
curl -X POST /wp-json/wp/v2/programs/simplifyCreate \
  -H "Content-Type: application/json" \
  -d '{
    "programName": "MyMultiWindowProgram",
    "screenWidth": 1280,
    "screenHeight": 512,
    "regions": [
      {
        "regionType": "web",
        "url": "https://www.baidu.com/"
      },
      {
        "regionType": "clock",
        "clockType": 0
      }
    ]
  }'
``` 