# 简化应用排程API文档

## 概述

该接口用于简化应用排程到终端组或终端，支持指令排程和节目排程的批量应用。该接口在 2.6.6-20240611 版本之后支持。

## 简化应用排程

### 基本信息
- **URL**: `/wp-json/wp/v2/terminalgroup/simplifiedSchedule`
- **Method**: `PUT`
- **Controller**: `TerminalGroupController`
- **支持格式**: `json`
- **描述**: 简化应用排程（终端组或终端）

### 请求参数

#### Request Body
| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| terminalGroupId | 是 | Integer | 终端组id | 3 |
| toChildren | 否 | Boolean | 是否应用到终端组子组，默认：false | false |
| terminalId | 是 | Integer | 终端id，（和terminalGroupId 同时存在时，terminalGroupId 生效。） | 6377 |
| commandSchedules | 是 | List | 指令排程请求参数列表 | 参考下方示例 |
| programSchedules | 是 | List | 节目排程请求参数列表 | 参考下方示例 |

#### CommandScheduleRequest 指令排程请求参数
| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| startDate | 是 | String | 开始日期 | "2024-12-14" |
| endDate | 是 | String | 结束日期 | "2025-12-14" |
| operationTime | 是 | String | 当天执行时间 | "23:59:59" |
| weeks | 是 | List | 周一到周天执行情况， true代表当天执行 | [true, false, true, true, true, false, true] |
| commandType | 是 | 枚举类型 | 指令类型 | CommandScheduleType |
| value | 是 | String | 构建该指令所需参数 | 见下说明 |

#### ProgramScheduleRequest 节目排程请求参数
| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| startDate | 是 | String | 开始日期 | "2024-12-14" |
| endDate | 是 | String | 结束日期 | "2025-12-14" |
| startTime | 是 | String | 当天的起始时间 | "00:00:00" |
| endTime | 是 | String | 当天的结束时间 | "23:59:59" |
| weeks | 是 | List<Boolean> | 周一到周天播放情况，true代表当天播放 | [true, false, true, true, true, true, true] |
| programPlayForm | 是 | Integer | 节目播放形式，0: 轮播，1: 插播 | 0 |
| programId | 是 | Integer | 节目id | 111 |
| programPriority | 是 | Integer | 节目优先级顺序 | 1 |

### 指令类型说明

该接口可对终端组，或终端 应用排程。terminalGroupId 和 terminalIds 同时存在时，使用terminalGroupId。

其中 commandSchedules.commandType 表示指令类型，commandSchedules.value 表示构建该指令所需参数：

1. **指令类型为 "BOARD_RELAY" (板载继电器指令)** 时，value代表 板载继电器状态。如："0", 0: 关闭，1：打开
2. **指令类型为 "SWITCH_SIGNAL_SOURCE" （切换信号源指令）** 时，value 代表 信号类型。如："0", 0: 同步，1：异步
3. **指令类型为 "RELAY" （继电器指令）** 时，value代表 继电器1、2、3的状态。 如："[0, 1, 0]", 0: 关闭，1：打开
4. **指令类型为 "VOLUME" （音量调节指令）** 时，value代表 音量值。如: "66"
5. **指令类型为 "BRIGHTNESS" （亮度调节指令）** 时，value代表 亮度值。如："11"
6. **指令类型为 "COLORTEMP" （色温调节指令）** 时，value代表 色温值， 如："10000", 范围：2000-10000
7. **指令类型为 "SLEEP" （休眠指令）、"WAKEUP" （唤醒指令）、"REBOOT"（重启指令）、"CLEAR_CACHE"（清缓存）** 时， value不需设置。

### 请求示例

```json
{
    "terminalGroupId": 3,
    "toChildren": false,   
    "terminalId": 6377,

    // 指令排程列表
    "commandSchedules": [
        {
            "startDate": "2024-01-01",
            "endDate": "2024-02-23",
            "operationTime": "20:50:00",
            "weeks": [true, true, true, true, true, true, true],
            "commandType": "BRIGHTNESS",
            "value": "99"
        }
    ],
    // 节目排程列表
    "programSchedules": [
        {
            "startDate": "2024-01-01",
            "endDate": "2024-02-23",
            "startTime": "20:50:00",
            "endTime": "20:50:00",
            "weeks": [true, true, true, true, true, true, true],
            "programId": 6440,
            "programPlayForm": 1
        }
    ]
}
```

### 成功响应

#### 响应格式
| 字段名 | 类型及范围 | 说明 |
|--------|------------|------|
| status | int | 状态码 (200) |
| message | String | 消息 |
| data | Object | 排程信息 |

#### 响应示例
```json
{
    "status": 200,
    "message": "Schedule applied successfully",
    "data": "排程信息"
}
```

### 异常响应

#### 错误码说明
- **403**: 权限不足 - "No Permission"
- **400**: 参数错误 - 具体错误信息
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
    "message": "Invalid command type: INVALID_TYPE",
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

## 使用示例

### 使用 curl 应用简化排程
```bash
curl -X PUT "https://api.example.com/wp-json/wp/v2/terminalgroup/simplifiedSchedule" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "terminalGroupId": 3,
    "toChildren": false,
    "terminalId": 6377,
    "commandSchedules": [
        {
            "startDate": "2024-01-01",
            "endDate": "2024-02-23",
            "operationTime": "20:50:00",
            "weeks": [true, true, true, true, true, true, true],
            "commandType": "BRIGHTNESS",
            "value": "99"
        }
    ],
    "programSchedules": [
        {
            "startDate": "2024-01-01",
            "endDate": "2024-02-23",
            "startTime": "20:50:00",
            "endTime": "20:50:00",
            "weeks": [true, true, true, true, true, true, true],
            "programId": 6440,
            "programPlayForm": 1
        }
    ]
}'
```

### 使用 JavaScript 应用简化排程
```javascript
const applySimplifiedSchedule = async (scheduleData) => {
    try {
        const response = await fetch('/wp-json/wp/v2/terminalgroup/simplifiedSchedule', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(scheduleData)
        });
        
        if (response.ok) {
            const result = await response.json();
            console.log('应用简化排程成功:', result);
            return result;
        } else {
            const error = await response.json();
            console.error('应用简化排程失败:', error);
            throw new Error(error.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
        throw error;
    }
};

// 使用示例
const scheduleData = {
    terminalGroupId: 3,
    toChildren: false,
    terminalId: 6377,
    commandSchedules: [
        {
            startDate: "2024-01-01",
            endDate: "2024-02-23",
            operationTime: "20:50:00",
            weeks: [true, true, true, true, true, true, true],
            commandType: "BRIGHTNESS",
            value: "99"
        }
    ],
    programSchedules: [
        {
            startDate: "2024-01-01",
            endDate: "2024-02-23",
            startTime: "20:50:00",
            endTime: "20:50:00",
            weeks: [true, true, true, true, true, true, true],
            programId: 6440,
            programPlayForm: 1
        }
    ]
};

applySimplifiedSchedule(scheduleData)
    .then(result => {
        console.log('简化排程应用成功:', result);
    })
    .catch(error => {
        console.error('简化排程应用失败:', error);
    });
```

## 指令类型详解

### 支持的指令类型

| 指令类型 | 说明 | value参数要求 | 示例 |
|----------|------|---------------|------|
| BOARD_RELAY | 板载继电器指令 | "0" 或 "1" | "0" |
| SWITCH_SIGNAL_SOURCE | 切换信号源指令 | "0" 或 "1" | "0" |
| RELAY | 继电器指令 | "[0,1,0]" 格式 | "[0,1,0]" |
| VOLUME | 音量调节指令 | 0-100的数字 | "66" |
| BRIGHTNESS | 亮度调节指令 | 0-100的数字 | "11" |
| COLORTEMP | 色温调节指令 | 2000-10000的数字 | "10000" |
| SLEEP | 休眠指令 | 不需要设置 | - |
| WAKEUP | 唤醒指令 | 不需要设置 | - |
| REBOOT | 重启指令 | 不需要设置 | - |
| CLEAR_CACHE | 清缓存指令 | 不需要设置 | - |

## 注意事项

1. **优先级规则**: terminalGroupId 和 terminalId 同时存在时，terminalGroupId 生效
2. **参数验证**: 所有必需的参数都必须提供，且格式正确
3. **时间格式**: 日期格式为 "YYYY-MM-DD"，时间格式为 "HH:mm:ss"
4. **星期限制**: weeks 数组必须包含7个布尔值，对应周一到周日
5. **指令类型**: 不同的指令类型需要不同的参数配置
6. **事务安全**: 排程应用操作是事务性的，确保数据一致性
7. **日志记录**: 所有排程应用操作都会被记录到日志中
8. **版本要求**: 该接口在 2.6.6-20240611 版本之后支持 