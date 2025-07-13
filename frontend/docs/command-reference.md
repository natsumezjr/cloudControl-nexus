# 设备指令参考文档

本文档详细说明了CloudControl-Nexus平台支持的所有设备指令及其数据格式。

## 接口信息

- **URL**: `/wp-json/wp/v2/comments`
- **HTTP方法**: POST
- **授权角色**: Administrator, Manager, Editor, Operator, Monitor
- **支持格式**: JSON

## 指令格式说明

### 单条指令格式
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/xxx",
    "act_method": 1
  },
  "content": "{\"command\":\"wakeup\"}"
}
```

### 批量指令格式
```json
{
  "post": 1,
  "CommentData": [
    {
      "post": 1,
      "metadata": {
        "act_url": "api/action",
        "act_method": 1
      },
      "content": "{\"command\":\"wakeup\"}"
    },
    {
      "post": 1,
      "metadata": {
        "act_url": "api/brightness",
        "act_method": 2
      },
      "content": "{\"brightness\":80}"
    }
  ]
}
```

## 字段说明

| 字段名 | 必选 | 类型及范围 | 说明 |
|--------|------|------------|------|
| post | 是 | integer | 终端id |
| metadata | 是 | object | meta json |
| ├─ act_url | 是 | string | api接口路径 |
| ├─ act_method | 是 | integer | 终端屏执行方式. 0-get, 1-post, 2-put, 3-delete |
| content | 是 | string | 指令内容（JSON字符串） |

## 基础控制指令

### 1. 唤醒指令 (wakeup)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/action",
    "act_method": 1
  },
  "content": "{\"command\":\"wakeup\"}"
}
```

### 2. 休眠指令 (sleep)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/action",
    "act_method": 1
  },
  "content": "{\"command\":\"sleep\"}"
}
```

### 3. 重启指令 (reboot)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/action",
    "act_method": 1
  },
  "content": "{\"command\":\"reboot\"}"
}
```

### 4. 屏幕截屏指令 (screenshot)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "transmission/ftp/config",
    "act_method": 0
  },
  "content": "{}"
}
```

### 5. 清理缓存指令 (clearCache)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/clrresunused",
    "act_method": 3
  },
  "content": "{}"
}
```

## 显示控制指令

### 1. 亮度指令 (brightness)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/brightness",
    "act_method": 2
  },
  "content": "{\"brightness\":80}"
}
```

**参数说明：**
- brightness: 亮度值，范围 0-255

### 2. 色温指令 (colortemp)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/colortemp",
    "act_method": 2
  },
  "content": "{\"colortemp\":10000}"
}
```

**参数说明：**
- colortemp: 色温值，范围 2000-10000

### 3. 切换信号源指令 (inputmode)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/inputmode",
    "act_method": 2
  },
  "content": "{\"inputmode\":\"dvi\"}"
}
```

**参数说明：**
- inputmode: 信号源类型
  - "hdmi": 同步信号
  - "dvi": 异步信号

## 音频控制指令

### 1. 音量指令 (volume)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/volume",
    "act_method": 2
  },
  "content": "{\"musicvolume\":10}"
}
```

**参数说明：**
- musicvolume: 音量值，范围 0-15（15表示音量为100%）

## 继电器控制指令

### 1. 板载继电器指令 (boardRelay)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/board_relay",
    "act_method": 2
  },
  "content": "[{\"relay\":1,\"delay\":0,\"status\":1}]"
}
```

### 2. 继电器指令 (relay)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/relay",
    "act_method": 2
  },
  "content": "[{\"relay\":1,\"delay\":0,\"status\":0},{\"relay\":2,\"delay\":0,\"status\":1},{\"relay\":3,\"delay\":0,\"status\":0}]"
}
```

**参数说明：**
- relay: 继电器编号
- delay: 延迟时间（秒）
- status: 状态（0-关闭，1-开启）

## 系统配置指令

### 1. 升级指令 (update)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/update",
    "act_method": 0
  },
  "content": "http://ip/wp-content/upload/2020/12/update_c1_v1.67.1.1329_b7c809cdb9bd4a78500d3bd8c27f0de9_480774370.zip"
}
```

### 2. 语言和地区设置指令 (locale)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/locale",
    "act_method": 2
  },
  "content": "{\"language\":\"zh\",\"country\":\"CN\"}"
}
```

**参数说明：**
- language: 语言代码（如 zh, en）
- country: 国家代码（如 CN, US）

### 3. 时区设置指令 (timezone)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/newrtc",
    "act_method": 2
  },
  "content": "{\"timezoneId\":\"Asia/Shanghai\",\"timezone\":8,\"isautotime\":1}"
}
```

**参数说明：**
- timezoneId: 时区ID（如 Asia/Shanghai）
- timezone: UTC偏移量（如 8 表示 UTC+8）
- isautotime: 是否自动同步时间（1-是，0-否）

## 监控配置指令

### 1. GPS上报间隔指令 (gpsReport)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/setreporttime",
    "act_method": 1
  },
  "content": "{\"gps.report.interval\":30}"
}
```

**参数说明：**
- gps.report.interval: GPS上报间隔（秒），设为0关闭GPS上报

### 2. 监控上报间隔指令 (sensorReport)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/setreporttime",
    "act_method": 1
  },
  "content": "{\"sensor.report.interval\":\"300\",\"ber.report.interval\":\"300\"}"
}
```

**参数说明：**
- sensor.report.interval: 传感器上报间隔（秒）
- ber.report.interval: 误码率上报间隔（秒）

### 3. 内容上报开关指令 (contentReport)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/contentreport",
    "act_method": 2
  },
  "content": "{\"status\":1}"
}
```

**参数说明：**
- status: 开关状态（1-开启，0-关闭）

### 4. 日志上报开关指令 (logReport)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/reportswitch",
    "act_method": 2
  },
  "content": "{\"log_report\":\"on\"}"
}
```

**参数说明：**
- log_report: 开关状态（"on"-开启，"off"-关闭）

### 5. 轮播节目名上报开关指令 (rotateProgramReport)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/reportswitch",
    "act_method": 2
  },
  "content": "{\"rotate_program_vsns_report\":\"on\"}"
}
```

**参数说明：**
- rotate_program_vsns_report: 开关状态（"on"-开启，"off"-关闭）

## 节目管理指令

### 1. 更新节目指令 (updateProgram)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "",
    "act_method": 0
  },
  "content": "{\"program\":\"dirty\"}"
}
```

### 2. 切换节目指令 (switchProgram)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/vsns/sources/internet/vsns/ProgramDocumentImage_7557cda64942545a6843bbe93d07012f_1048.vsn/activated",
    "act_method": 2
  },
  "content": "{\"command\":\"\"}"
}
```

**说明：**
- act_url 需要根据实际的节目信息动态生成
- 格式：`api/vsns/sources/{type}/vsns/{name}/activated`
- type: lan（局域网节目）或 internet（互联网节目）
- name: 节目文件名

### 3. 清空节目指令 (clearPrograms)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/clrprgms",
    "act_method": 3
  },
  "content": "{}"
}
```

### 4. 删除节目指令 (deleteProgram)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/vsns/sources/internet/vsns/Playlist9017_783596d9ee396d7a604dac56a6979546_1332.vsn",
    "act_method": 3
  },
  "content": "{\"command\":\"\"}"
}
```

**说明：**
- act_url 需要根据实际的节目信息动态生成
- 格式：`api/vsns/sources/{type}/vsns/{name}`
- type: lan（局域网节目）或 internet（互联网节目）
- name: 节目文件名

## 状态查询指令

### 1. 运行日志上报指令 (currentLog)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/current_log",
    "act_method": 0
  },
  "content": "{}"
}
```

### 2. 网络接口配置信息上报指令 (ifstatus)

**请求格式：**
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/ifstatus",
    "act_method": 0
  },
  "content": "{}"
}
```

## 批量指令示例

### 批量发送多个指令
```json
{
  "post": 1,
  "CommentData": [
    {
      "post": 1,
      "metadata": {
        "act_url": "api/action",
        "act_method": 1
      },
      "content": "{\"command\":\"wakeup\"}"
    },
    {
      "post": 1,
      "metadata": {
        "act_url": "api/brightness",
        "act_method": 2
      },
      "content": "{\"brightness\":80}"
    },
    {
      "post": 1,
      "metadata": {
        "act_url": "api/volume",
        "act_method": 2
      },
      "content": "{\"musicvolume\":10}"
    }
  ]
}
```

## 错误响应格式

当指令执行失败时，响应格式如下：

```json
{
  "success": false,
  "code": 400,
  "message": "错误描述信息",
  "data": null,
  "timestamp": 1640995200000
}
```

## 常见错误代码

- `200`: 成功
- `400`: 请求参数错误
- `401`: 未授权
- `404`: 设备不存在
- `500`: 服务器内部错误
- `503`: 设备离线
- `1001`: 设备忙
- `1002`: 内容不存在
- `1003`: 播放列表不存在
- `1004`: 网络连接失败

## 注意事项

1. **content字段**必须是JSON字符串格式，不能是对象
2. **act_method**对应HTTP方法：0-GET, 1-POST, 2-PUT, 3-DELETE
3. **post字段**必须是有效的终端ID
4. **批量指令**中的每个指令都会独立执行
5. **节目相关指令**的act_url需要根据实际节目信息动态生成
6. **时间间隔**相关参数单位为秒
7. **开关状态**使用字符串"on"/"off"或数字0/1 