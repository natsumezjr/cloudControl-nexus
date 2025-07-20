# 终端组排程API文档

## 概述

该接口用于将排程应用到指定的终端组，支持节目排程和指令排程的批量应用。

## 应用终端组排程

### 基本信息
- **URL**: `/wp-json/wp/v2/terminalgroup/{terminalGroupId}`
- **Method**: `PUT`
- **授权角色**: `administrator`, `editor`, `operator`, `manager`, `assembler`
- **支持格式**: `json`
- **描述**: 应用终端组排程

### 请求参数

#### Path Parameters
| 字段名 | 必选 | 类型及范围 | 说明 | 示例 |
|--------|------|------------|------|------|
| terminalGroupId | 是 | Integer | 终端组id | 11924 |

#### Request Body
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| to_children | 否 | Boolean | 排程是否应用到子组 | false |
| program_ids | 是 | List | 节目排程时的节目id | [24585] |
| thumbnails | 否 | List | 节目排程对应的节目信息 | [{"id": 24585, "src": "..."}] |
| schedules | 是 | Object | 排程 | {} |

#### schedules.contentsSchedule (节目排程)
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| if_limit_date | 是 | Boolean | 是否指定日期 | true/false |
| limit_date | 是 | Object | 日期的起始和结束 | {} |
| limit_date.start | 是 | String | 开始日期 | "2024-07-04" |
| limit_date.start_time | 是 | String | 开始时间 | "00:00:00" |
| limit_date.end | 是 | String | 结束日期 | "2024-07-04" |
| limit_date.end_time | 是 | String | 结束时间 | "23:59:59" |
| if_limit_time | 是 | Boolean | 是否指定当天时间 | true/false |
| limit_time | 是 | Object | 当天的起始时间和结束时间 | {} |
| limit_time.end_time | 是 | String | 结束时间 | "23:59:59" |
| limit_time.start_time | 是 | String | 开始时间 | "00:00:00" |
| if_limit_weekday | 是 | Boolean | 是否指定星期 | true/false |
| limit_weekday | 是 | List | 对应周一到周天 | [true, true, true, false, false, true, true] |
| name | 是 | String | 默认值，代表节目排程 | "Play_Program" |
| operation | 是 | Object | 节目信息 | {} |
| operation.id | 是 | Integer | 节目id | 24585 |
| operation.name | 是 | String | 节目名 | "Playlist123" |
| operation.vsn | 是 | String | 节目vsn文件名 | "Playlist123_e41b5d853d35329072b75dd77ac54810_2033.vsn" |
| operation.source | 是 | String | 节目类型 | "internet" |
| priority | 是 | String | 节目优先级顺序 | "0" |
| type | 是 | String | "rotation"：轮播，"spot"：插播 | "rotation" |
| type_priority | 是 | Integer | 100：插播，200：轮播 | 200 |

#### schedules.commandSchedule (指令排程)
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| content | 是 | Object | 指令排程中指令的值 | {"name": "Value", "value": 72} |
| if_limit_date | 是 | Boolean | 指令排程是否指定日期 | true/false |
| limit_date | 是 | Object | 日期的起始和结束 | {} |
| limit_date.start | 是 | String | 指令排程指定日期开始时间 | "2024-07-04" |
| limit_date.end | 是 | String | 指令排程指定日期结束时间 | "2024-07-04" |
| if_limit_weekday | 是 | Boolean | 指令排程是否指定星期 | true/false |
| limit_weekday | 是 | List | 对应从周一到周日 | [true, true, true, true, true, true, true] |
| name | 是 | String | 指令排程类型 | "Brightness_Control" |
| op_time | 是 | List | 当天执行时间 | ["15:11:05"] |
| type | 是 | String | 类型：指令，默认值 | "command" |
| operation | 是 | Object | 需要执行的指令 | {} |

### 请求示例

```json
{
  "to_children": false,
  "program_ids": [
    24585
  ],
  "schedules": {
    "contentsSchedule": [
      {
        "if_limit_date": true,
        "if_limit_time": true,
        "if_limit_weekday": true,
        "limit_date": {
          "start": "2024-07-04",
          "start_time": "00:00:00",
          "end": "2024-07-04",
          "end_time": "23:59:59"
        },
        "limit_time": {
          "end_time": "23:59:59",
          "start_time": "00:00:00"
        },
        "limit_weekday": [
          true,
          true,
          true,
          false,
          false,
          true,
          true
        ],
        "name": "Play_Program",
        "operation": {
          "id": 24585,
          "name": "Playlist123",
          "vsn": "Playlist123_e41b5d853d35329072b75dd77ac54810_2033.vsn",
          "source": "internet"
        },
        "priority": "0",
        "type": "rotation",
        "type_priority": 200
      }
    ],
    "commandSchedule": [
      {
        "content": {
          "name": "Value",
          "value": 72
        },
        "if_limit_date": true,
        "if_limit_weekday": false,
        "limit_date": {
          "start": "2024-07-04",
          "end": "2024-07-04"
        },
        "limit_weekday": [
          true,
          true,
          true,
          true,
          true,
          true,
          true
        ],
        "name": "Brightness_Control",
        "op_time": [
          "15:11:05"
        ],
        "operation": {
          "author_url": "api/brightness",
          "karma": 2,
          "content": "{\"brightness\":\"184\"}"
        },
        "type": "command"
      }
    ]
  },
  "thumbnails": [
    {
      "id": 24585,
      "src": "http://192.168.1.30:80/wp-content/playList/thumbnails/1719993542936_9fc6299b9f6fc213063cf638d833a806.png"
    }
  ]
}
```

### 成功响应

#### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| parent | Integer | 父级ID |
| schedule_time | Long | 排程时间戳 |
| meta | String | 元数据 |
| members | Integer | 成员数量 |
| count | Integer | 计数 |
| link | String | 链接 |
| name | String | 终端组名称 |
| description | String | 描述 |
| leds | Array | 该终端组下的终端信息 |
| id | Integer | 发布到的终端组ID |
| taxonomy | String | 分类 |
| slug | String | 别名 |

#### leds字段说明
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| led_id | Integer | 终端id |
| led_name | String | 终端名 |
| _led_latest_report_time | String | 最新报告时间 |
| led_description | String | 终端描述 |

#### 响应示例
```json
{
  "parent": 1,
  "schedule_time": 1720077073,
  "meta": "",
  "members": 0,
  "count": 0,
  "link": "",
  "name": "测试终端47",
  "description": "",
  "leds": [
    {
      "led_id": 24607,
      "led_name": "zora",
      "_led_latest_report_time": "0",
      "led_description": ""
    }
  ],
  "id": 11924,
  "taxonomy": "terminalgroup",
  "slug": "测试终端47"
}
```

### 异常响应

#### 错误码说明
- **403**: 权限不足 - "No Permission"
- **400**: 参数错误 - "Bad Request." 或具体错误信息
- **500**: 服务器内部错误 - "Internal server error"

#### 错误响应示例

##### 权限不足
```json
{
    "code": 403,
    "message": "No Permission",
    "data": null
}
```

##### 参数错误
```json
{
    "code": 400,
    "message": "Invalid terminal group ID",
    "data": null
}
```

##### 服务器错误
```json
{
    "code": 500,
    "message": "Internal server error",
    "data": null
}
```

## 指令排程类型详解

### 1. schedules.commandSchedule.name (指令排程类型)

该参数用于云平台前端展示，如不需要可为空。

- `"Brightness_Control"`：亮度控制
- `"Volume_Control"`：音量控制
- `"Relay"`：继电器控制
- `"Sleep"`：休眠
- `"Colortemp_Control"`：色温控制
- `"Wakeup"`：唤醒
- `"Reboot"`：重启
- `"Clear_Cache"`：清理缓存
- `"Switch_Signal_Source"`：切换信号源
- `"Board_Relay"`：板载继电器控制

### 2. schedules.commandSchedule.content (指令排程中指令的值)

该参数用于云平台前端展示，如不需要可为空。

#### 色温控制、亮度控制、音量控制
```json
{
    "name": "Value", 
    "value": 2000
}
```

#### 休眠、唤醒、重启、清理缓存
无此参数

#### 继电器控制、板载继电器控制
```json
{
    "name": "Relay", 
    "value": "[0,1,0]"
}
```

#### 切换信号源
```json
{
    "name": "Signal_Source", 
    "value": "HDMI1"
}
```

## 使用示例

### 使用 curl 应用终端组排程
```bash
curl -X PUT "https://api.example.com/wp-json/wp/v2/terminalgroup/11924" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "to_children": false,
    "program_ids": [24585],
    "schedules": {
      "contentsSchedule": [
        {
          "if_limit_date": true,
          "if_limit_time": true,
          "if_limit_weekday": true,
          "limit_date": {
            "start": "2024-07-04",
            "start_time": "00:00:00",
            "end": "2024-07-04",
            "end_time": "23:59:59"
          },
          "limit_time": {
            "end_time": "23:59:59",
            "start_time": "00:00:00"
          },
          "limit_weekday": [true, true, true, false, false, true, true],
          "name": "Play_Program",
          "operation": {
            "id": 24585,
            "name": "Playlist123",
            "vsn": "Playlist123_e41b5d853d35329072b75dd77ac54810_2033.vsn",
            "source": "internet"
          },
          "priority": "0",
          "type": "rotation",
          "type_priority": 200
        }
      ],
      "commandSchedule": [
        {
          "content": {
            "name": "Value",
            "value": 72
          },
          "if_limit_date": true,
          "if_limit_weekday": false,
          "limit_date": {
            "start": "2024-07-04",
            "end": "2024-07-04"
          },
          "limit_weekday": [true, true, true, true, true, true, true],
          "name": "Brightness_Control",
          "op_time": ["15:11:05"],
          "operation": {
            "author_url": "api/brightness",
            "karma": 2,
            "content": "{\"brightness\":\"184\"}"
          },
          "type": "command"
        }
      ]
    },
    "thumbnails": [
      {
        "id": 24585,
        "src": "http://192.168.1.30:80/wp-content/playList/thumbnails/1719993542936_9fc6299b9f6fc213063cf638d833a806.png"
      }
    ]
  }'
```

### 使用 JavaScript 应用终端组排程
```javascript
const applyTerminalGroupSchedule = async (terminalGroupId, scheduleData) => {
    try {
        const response = await fetch(`/wp-json/wp/v2/terminalgroup/${terminalGroupId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(scheduleData)
        });
        
        if (response.ok) {
            const result = await response.json();
            console.log('应用排程成功:', result);
            return result;
        } else {
            const error = await response.json();
            console.error('应用排程失败:', error);
            throw new Error(error.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
        throw error;
    }
};

// 使用示例
const scheduleData = {
    to_children: false,
    program_ids: [24585],
    schedules: {
        contentsSchedule: [
            {
                if_limit_date: true,
                if_limit_time: true,
                if_limit_weekday: true,
                limit_date: {
                    start: "2024-07-04",
                    start_time: "00:00:00",
                    end: "2024-07-04",
                    end_time: "23:59:59"
                },
                limit_time: {
                    end_time: "23:59:59",
                    start_time: "00:00:00"
                },
                limit_weekday: [true, true, true, false, false, true, true],
                name: "Play_Program",
                operation: {
                    id: 24585,
                    name: "Playlist123",
                    vsn: "Playlist123_e41b5d853d35329072b75dd77ac54810_2033.vsn",
                    source: "internet"
                },
                priority: "0",
                type: "rotation",
                type_priority: 200
            }
        ],
        commandSchedule: [
            {
                content: {
                    name: "Value",
                    value: 72
                },
                if_limit_date: true,
                if_limit_weekday: false,
                limit_date: {
                    start: "2024-07-04",
                    end: "2024-07-04"
                },
                limit_weekday: [true, true, true, true, true, true, true],
                name: "Brightness_Control",
                op_time: ["15:11:05"],
                operation: {
                    author_url: "api/brightness",
                    karma: 2,
                    content: "{\"brightness\":\"184\"}"
                },
                type: "command"
            }
        ]
    },
    thumbnails: [
        {
            id: 24585,
            src: "http://192.168.1.30:80/wp-content/playList/thumbnails/1719993542936_9fc6299b9f6fc213063cf638d833a806.png"
        }
    ]
};

applyTerminalGroupSchedule(11924, scheduleData)
    .then(result => {
        console.log('排程应用成功:', result);
    })
    .catch(error => {
        console.error('排程应用失败:', error);
    });
```

## 注意事项

1. **权限要求**: 需要相应的角色权限才能应用排程到终端组
2. **参数验证**: 所有必需的参数都必须提供，且格式正确
3. **时间格式**: 日期格式为 "YYYY-MM-DD"，时间格式为 "HH:mm:ss"
4. **星期限制**: limit_weekday 数组必须包含7个布尔值，对应周一到周日
5. **指令类型**: 不同的指令类型需要不同的参数配置
6. **事务安全**: 排程应用操作是事务性的，确保数据一致性
7. **日志记录**: 所有排程应用操作都会被记录到日志中 