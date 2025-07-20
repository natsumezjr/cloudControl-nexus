# 终端排程API文档

## 概述

该接口用于管理终端的排程信息，包括获取和更新终端排程，以及获取终端组排程信息。

## 获取终端排程信息

### 基本信息
- **URL**: `/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules`
- **Method**: `GET`
- **授权角色**: `administrator`, `manager`, `editor`, `operator`
- **支持格式**: `json`
- **描述**: 根据终端ID获取终端排程信息

### 请求参数

#### Path Parameters
| 字段名 | 必选 | 类型及范围 | 说明 | 示例 |
|--------|------|------------|------|------|
| terminalId | 是 | int | 终端id | 123 |

### 成功响应

#### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| status | int | 状态码 (200) |
| msg | String | 消息 ("Success.") |
| data | TerminalScheduleVO | 排程信息 |

#### 响应示例
```json
{
    "status": 200,
    "msg": "Success.",
    "data": {
        "updateTerminalScheduleTime": 1695453795,
        "scheduleJsonBean": {
            "to_children": false,
            "program_ids": [],
            "schedules": {
                "contentsSchedule": [],
                "commandSchedule": [
                    {
                        "operation": {
                            "author_url": "api/action",
                            "karma": 1,
                            "content": "{\"command\":\"sleep\"}"
                        },
                        "op_time": [
                            "05:00:00"
                        ],
                        "if_limit_date": false,
                        "limit_date": {
                            "start": "2023-09-23",
                            "end": "2023-09-23"
                        },
                        "if_limit_weekday": false,
                        "limit_weekday": [
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true
                        ],
                        "type": "command",
                        "name": "Sleep"
                    }
                ]
            },
            "thumbnails": []
        }
    }
}
```

### 异常响应

#### 错误码说明
- **403**: 权限不足 - "No Permission"
- **400**: 终端ID格式错误 - "Bad Request."

#### 错误响应示例

##### 权限不足
```json
{
    "code": 403,
    "message": "No Permission",
    "data": null
}
```

##### 终端ID格式错误
```json
{
    "code": 400,
    "message": "Bad Request.",
    "data": null
}
```

## 获取终端组排程信息

### 基本信息
- **URL**: `/wp-json/wp/v3/schedules/terminalGroupSchedules`
- **Method**: `GET`
- **授权角色**: `administrator`, `manager`, `editor`, `operator`
- **支持格式**: `json`
- **描述**: 根据终端组ID获取终端组排程信息，支持获取多个终端组排程信息

### 请求参数

#### Query Parameters
| 字段名 | 必选 | 类型及范围 | 说明 | 示例 |
|--------|------|------------|------|------|
| terminalGroupIds | 是 | Set<Integer> | 终端组ids | 21 |

### 成功响应

#### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| status | int | 状态码 (200) |
| msg | String | 消息 ("Success.") |
| data | Map<Long, ScheduleJsonBean> | 终端组id和对应的终端组排程信息 |

#### 响应示例
```json
{
  "status": 200,
  "msg": "Success.",
  "data": {
    "21": {
      "to_children": false,
      "program_ids": [],
      "schedules": {
        "contentsSchedule": [],
        "commandSchedule": [
          {
            "operation": {
              "author_url": "api/action",
              "karma": 1,
              "content": "{\"command\":\"reboot\"}"
            },
            "op_time": [
              "11:00:00"
            ],
            "if_limit_date": false,
            "limit_date": {
              "start": "2023-09-23",
              "end": "2023-09-23"
            },
            "if_limit_weekday": false,
            "limit_weekday": [
              true,
              true,
              true,
              true,
              true,
              true,
              true
            ],
            "type": "command",
            "name": "Reboot"
          }
        ]
      },
      "thumbnails": []
    }
  }
}
```

### 异常响应

#### 错误码说明
- **403**: 权限不足 - "No Permission"
- **500**: 查询失败 - "List group schedules error."

#### 错误响应示例

##### 权限不足
```json
{
    "code": 403,
    "message": "No Permission",
    "data": null
}
```

##### 查询失败
```json
{
    "code": 500,
    "message": "List group schedules error.",
    "data": null
}
```

## 更新终端排程

### 基本信息
- **URL**: `/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules`
- **Method**: `PUT`
- **授权角色**: `administrator`, `manager`, `editor`, `operator`
- **支持格式**: `json`
- **描述**: 修改指定终端排程

### 请求参数

#### Path Parameters
| 字段名 | 必选 | 类型及范围 | 说明 | 示例 |
|--------|------|------------|------|------|
| terminalId | 是 | int | 终端id | 211 |

#### Request Body
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| program_ids | 是 | List | 节目排程时的节目id | [3139, 3136] |
| thumbnails | 否 | List | 节目排程对应的节目信息 | [{"id": 3132, "src": ""}] |
| to_children | 否 | Boolean | 排程是否应用到子组 | false |
| schedules | 是 | Object | 排程 | {} |

#### schedules.commandSchedule (指令排程)
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| content | 是 | Object | 指令排程中指令的值 | {"name": "Value", "value": 50} |
| if_limit_date | 是 | Boolean | 指令排程是否指定日期 | true/false |
| limit_date | 是 | Object | 日期的起始和结束 | {} |
| limit_date.start | 是 | String | 指令排程指定日期开始时间 | "2023-12-25" |
| limit_date.end | 是 | String | 指令排程指定日期结束时间 | "2023-12-25" |
| if_limit_weekday | 是 | Boolean | 指令排程是否指定星期 | true/false |
| limit_weekday | 是 | List | 对应从周一到周日 | [true, true, false, false, true, true, true] |
| name | 是 | String | 指令排程类型 | "Volume_Control" |
| op_time | 是 | List | 当天执行时间 | ["00:00:00"] |
| type | 是 | String | 类型：指令，默认值 | "command" |
| operation | 是 | Object | 需要执行的指令 | {} |

#### schedules.contentsSchedule (节目排程)
| 参数名 | 是否必需 | 参数类型 | 参数描述 | 示例值 |
|--------|----------|----------|----------|--------|
| if_limit_date | 是 | Boolean | 日期的起始和结束 | true/false |
| limit_date | 是 | Object | 日期的起始和结束 | {} |
| limit_date.start | 是 | String | 开始日期 | "2023-12-25" |
| limit_date.start_time | 是 | String | 开始时间 | "00:00:00" |
| limit_date.end | 是 | String | 结束日期 | "2023-12-25" |
| limit_date.end_time | 是 | String | 结束时间 | "23:59:59" |
| if_limit_time | 是 | Boolean | 当天的起始时间和结束时间 | true/false |
| limit_time | 是 | Object | 当天的起始时间和结束时间 | {} |
| limit_time.end_time | 是 | String | 结束时间 | "14:30:00" |
| limit_time.start_time | 是 | String | 开始时间 | "14:00:00" |
| if_limit_weekday | 是 | Boolean | 周一到周天 | true/false |
| limit_weekday | 是 | List | 对应周一到周天 | [false, false, false, true, true, true, true] |
| name | 是 | String | 默认值，代表节目排程 | "Play_Program" |
| priority | 是 | String | 节目优先级顺序 | "0" |
| type | 是 | String | "rotation"：轮播，"spot"：插播 | "spot" |
| type_priority | 是 | Integer | 100：插播，200：轮播 | 100 |
| operation | 是 | Object | 节目信息 | {} |
| operation.id | 是 | Integer | 节目id | 3132 |
| operation.name | 是 | String | 节目名 | "Playlist1625" |
| operation.vsn | 是 | String | 节目vsn文件名 | "Playlist1625_d04bdcf85fa82735e47687ea9265126c_1380.vsn" |
| operation.source | 是 | String | 节目类型 | "internet" |

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

#### 切换信号源
```json
{
    "name": "Switch",
    "value": "async"
}
```
- `"async"`：异步
- `"sync"`：同步

#### 继电器控制
```json
{
    "name": "Relay",
    "value": "[0,1,0]"
}
```
Value: 为列表，对应继电器1、2、3的状态

#### 板载继电器
```json
{
    "name": "Board Relay",
    "value": "[1]"
}
```
value: 代表板载继电器的状态

### 3. schedules.commandSchedule.operation (指令排程中需要执行的指令)

#### 休眠
```json
{
    "author_url": "api/action",
    "karma": 1,
    "content": "{\"command\":\"sleep\"}"
}
```

#### 唤醒
```json
{
    "author_url": "api/action",
    "karma": 1,
    "content": "{\"command\":\"wakeup\"}"
}
```

#### 重启
```json
{
    "author_url": "api/action",
    "karma": 1,
    "content": "{\"command\":\"reboot\"}"
}
```

#### 亮度调节
```json
{
    "author_url": "api/brightness",
    "karma": 2,
    "content": "{\"brightness\":0}" 
}
```

#### 色温调节
```json
{
    "author_url": "api/colortemp",
    "karma": 2,
    "content": "{\"colortemp\":2000}" 
}
```

#### 音量控制
```json
{
    "author_url": "api/volume",
    "karma": 2,
    "content": "{\"musicvolume\":0}" 
}
```

#### 清理缓存
```json
{
    "author_url": "api/clrresunused",
    "karma": 3,
    "content": "{}"
}
```

#### 切换信号源
同步信号：hdmi
```json
{
    "author_url": "api/inputmode",
    "karma": 2,
    "content": "{\"inputmode\":\"hdmi\"}"
}
```

异步信号：dvi
```json
{
    "author_url": "api/inputmode",
    "karma": 2,
    "content": "{\"inputmode\":\"dvi\"}"
}
```

#### 继电器控制
```json
{
    "author_url": "api/relay",
    "karma": 2,
    "content": "[{\"relay\":1,\"delay\":0,\"status\":0},{\"relay\":2,\"delay\":0,\"status\":0},{\"relay\":3,\"delay\":0,\"status\":0}]"
}
```

#### 板载继电器控制
```json
{
    "author_url": "api/board_relay",
    "karma": 2,
    "content": "[{\"relay\":1,\"delay\":0,\"status\":1}]"
}
```

## 请求示例

### 完整示例
```json
{
    "to_children": false,
    "program_ids": [
        7085240
    ],
    "schedules": {
        "contentsSchedule": [
            {
                "if_limit_date": true,
                "if_limit_time": true,
                "if_limit_weekday": true,
                "limit_date": {
                    "start": "2024-07-08",
                    "start_time": "00:00:00",
                    "end": "2024-07-08",
                    "end_time": "23:59:59"
                },
                "limit_time": {
                    "end_time": "23:59:59",
                    "start_time": "00:00:00"
                },
                "limit_weekday": [
                    true,
                    true,
                    false,
                    false,
                    true,
                    true,
                    true
                ],
                "name": "Play_Program",
                "operation": {
                    "id": 7085240,
                    "name": "Playlist8093",
                    "vsn": "Playlist8093_2d1a007943e7de0c83b002795aef2d5b_37095.vsn",
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
                    "value": 50
                },
                "if_limit_date": true,
                "if_limit_weekday": true,
                "limit_date": {
                    "start": "2024-07-08",
                    "end": "2024-07-08"
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
                    "17:29:06"
                ],
                "operation": {
                    "author_url": "api/brightness",
                    "karma": 2,
                    "content": "{\"brightness\":\"127\"}"
                },
                "type": "command"
            }
        ]
    },
    "thumbnails": [
        {
            "id": 7085240,
            "src": "https://cdn.colorlightcloud.com/wp-content/playList/thumbnails/1717486311197_168d5d9d61e4039903ce5898bb75aa98.png"
        }
    ]
}
```

### 节目排程示例
```json
{
    "to_children": false,
    "program_ids": [3139, 3136],
    "schedules": {
        "contentsSchedule": [
            {
                "if_limit_date": true,
                "limit_date": {
                    "start": "2024-01-01",
                    "start_time": "08:00:00",
                    "end": "2024-12-31",
                    "end_time": "18:00:00"
                },
                "if_limit_time": true,
                "limit_time": {
                    "start_time": "08:00:00",
                    "end_time": "18:00:00"
                },
                "if_limit_weekday": true,
                "limit_weekday": [true, true, true, true, true, false, false],
                "name": "Play_Program",
                "priority": "0",
                "type": "rotation",
                "type_priority": 200,
                "operation": {
                    "id": 3139,
                    "name": "Morning_Program",
                    "vsn": "Morning_Program_abc123.vsn",
                    "source": "internet"
                }
            }
        ],
        "commandSchedule": []
    }
}
```

### 指令排程示例
```json
{
    "to_children": false,
    "program_ids": [],
    "schedules": {
        "contentsSchedule": [],
        "commandSchedule": [
            {
                "content": {
                    "name": "Value",
                    "value": 80
                },
                "if_limit_date": false,
                "limit_date": {
                    "start": "2024-01-01",
                    "end": "2024-12-31"
                },
                "if_limit_weekday": false,
                "limit_weekday": [true, true, true, true, true, true, true],
                "name": "Volume_Control",
                "op_time": ["09:00:00", "18:00:00"],
                "operation": {
                    "author_url": "api/volume",
                    "karma": 2,
                    "content": "{\"musicvolume\":\"80\"}"
                },
                "type": "command"
            }
        ]
    }
}
```

## 成功响应

### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| status | int | 状态码 (200) |
| msg | String | 消息 ("Success.") |
| data | TerminalScheduleVO | 更新后的排程信息 |

### 响应示例
```json
{
    "status": 200,
    "msg": "Success.",
    "data": {
        "updateTerminalScheduleTime": 1720669491,
        "scheduleJsonBean": {
            "to_children": false,
            "program_ids": [],
            "schedules": {
                "contentsSchedule": [
                    {
                        "type_priority": 200,
                        "priority": 0,
                        "if_limit_time": false,
                        "limit_time": {
                            "start_time": "00:00:00",
                            "end_time": "23:59:59"
                        },
                        "operation": {
                            "id": 917,
                            "name": "Playlist2954",
                            "vsn": "Playlist2954_0687b8367a5154236ca32572c458df63_2782.vsn",
                            "source": "internet"
                        },
                        "if_limit_date": true,
                        "limit_date": {
                            "start": "2024-07-11",
                            "end": "2024-07-11",
                            "start_time": "00:00:00",
                            "end_time": "23:59:59"
                        },
                        "if_limit_weekday": false,
                        "limit_weekday": [
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true
                        ],
                        "type": "rotation",
                        "name": "Play_Program"
                    }
                ],
                "commandSchedule": [
                    {
                        "operation": {
                            "author_url": "api/brightness",
                            "karma": 2,
                            "content": "{\"brightness\":\"74\"}"
                        },
                        "op_time": [
                            "11:44:30"
                        ],
                        "if_limit_date": false,
                        "limit_date": {
                            "start": "2024-07-11",
                            "end": "2024-07-11"
                        },
                        "if_limit_weekday": false,
                        "limit_weekday": [
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true
                        ],
                        "content": {
                            "name": "Value",
                            "value": "29"
                        },
                        "type": "command",
                        "name": "Brightness_Control"
                    }
                ]
            },
            "thumbnails": []
        }
    }
}
```

## 异常响应

### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| code | int | 错误码 |
| message | String | 错误信息 |
| data | Object | 错误详情 |

### 错误码说明
- **403**: 权限不足 - "No Permission"
- **400**: 排程信息为空 - "Schedules can not be empty."
- **403**: 无效操作 - "Invalid operation, the program has been modified!"

### 错误响应示例

#### 权限不足
```json
{
    "code": 403,
    "message": "No Permission",
    "data": null
}
```

#### 排程信息为空
```json
{
    "code": 400,
    "message": "Schedules can not be empty.",
    "data": null
}
```

#### 节目已被修改
```json
{
    "code": 403,
    "message": "Invalid operation, the program has been modified!",
    "data": null
}
```

## 注意事项

1. **权限验证**: 只有具有相应权限的用户才能更新终端排程
2. **参数验证**: 所有必填参数都必须提供
3. **优先级唯一性**: 同一终端下的节目优先级必须唯一
4. **时间格式**: 日期格式为 "YYYY-MM-DD"，时间格式为 "HH:mm:ss"
5. **星期数组**: limit_weekday 数组对应周一到周日，true 表示启用，false 表示禁用
6. **指令类型**: 不同的指令类型需要不同的参数格式
7. **事务处理**: 更新操作在事务中执行，确保数据一致性
8. **日志记录**: 所有更新操作都会记录详细的日志信息

## 使用示例

### 获取终端排程信息

#### 使用 curl 获取终端排程
```bash
curl -X GET "https://api.example.com/wp-json/wp/v3/schedules/123/terminalSchedules" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

#### 使用 JavaScript 获取终端排程
```javascript
const getTerminalSchedule = async (terminalId) => {
    try {
        const response = await fetch(`/wp-json/wp/v3/schedules/${terminalId}/terminalSchedules`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });
        
        if (response.ok) {
            const result = await response.json();
            console.log('获取排程成功:', result);
            return result;
        } else {
            const error = await response.json();
            console.error('获取排程失败:', error);
            throw new Error(error.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
        throw error;
    }
};
```

### 获取终端组排程信息

#### 使用 curl 获取终端组排程
```bash
curl -X GET "https://api.example.com/wp-json/wp/v3/schedules/terminalGroupSchedules?terminalGroupIds=21" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

#### 使用 JavaScript 获取终端组排程
```javascript
const getTerminalGroupSchedules = async (terminalGroupIds) => {
    try {
        const params = new URLSearchParams();
        terminalGroupIds.forEach(id => params.append('terminalGroupIds', id));
        
        const response = await fetch(`/wp-json/wp/v3/schedules/terminalGroupSchedules?${params}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });
        
        if (response.ok) {
            const result = await response.json();
            console.log('获取终端组排程成功:', result);
            return result;
        } else {
            const error = await response.json();
            console.error('获取终端组排程失败:', error);
            throw new Error(error.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
        throw error;
    }
};

// 使用示例
getTerminalGroupSchedules([21, 22, 23])
    .then(result => {
        console.log('终端组排程信息:', result);
    })
    .catch(error => {
        console.error('获取失败:', error);
    });
```

### 使用 curl 更新终端排程
```