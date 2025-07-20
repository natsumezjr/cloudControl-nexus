# 节目发布API文档

## 发布节目

### 基本信息
- **URL**: `/wp-json/wp/v2/programs/{programId}?flag=terminalgroup`
- **HTTP请求方式**: `PUT`
- **授权角色**: `administrator`, `editor`, `operator`, `contributor`, `auditor`, `subscriber`, `supervisor`, `invitee`, `monitor`, `manager`
- **支持格式**: `json`

### 请求参数

#### URL参数
| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programId | 是 | int | 节目ID | 1 |
| flag | 是 | string | 必须为"terminalgroup" | terminalgroup |

#### 请求体参数
| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| what | 是 | string | 必须为"assign_program_to_terminal_group" | "assign_program_to_terminal_group" |
| to | 是 | object | 发布到的终端组/终端对象 | 见下方示例 |
| to.terminals_groups | 是 | array | 发布到的终端组和终端数组 | 见下方示例 |
| to.terminals_groups.all | 是 | Boolean | 是否发布到整组终端 | false |
| to.terminals_groups.id | 是 | Integer | 终端组ID | 1 |
| to.terminals_groups.terminals | 是 | Array | 终端ID集合 | [325056] |

### 请求示例

```json
{
    "what": "assign_program_to_terminal_group",
    "to": {
        "terminals_groups": [
            {
                "all": false,
                "id": 1,
                "terminals": [
                    325056
                ]
            }
        ]
    }
}
```

### 成功响应

#### 响应字段
| 字段名 | 类型 | 说明 | 示例 |
|--------|------|------|------|
| id | int | 发布的节目ID | 1 |
| author | int | 节目作者 | 1 |
| date | String | 节目创建日期 | "2024-01-15T10:30:00" |
| date_gmt | String | 节目创建日期（GMT时间） | "2024-01-15T10:30:00Z" |
| status | String | 节目状态 | "publish" |
| modified | String | 节目修改日期 | "2024-01-15T11:45:00" |
| modified_gmt | String | 节目修改日期（GMT时间） | "2024-01-15T11:45:00Z" |

#### 响应示例
```json
{
    "id": 1,
    "author": 1,
    "date": "2024-01-15T10:30:00",
    "date_gmt": "2024-01-15T10:30:00Z",
    "status": "publish",
    "modified": "2024-01-15T11:45:00",
    "modified_gmt": "2024-01-15T11:45:00Z"
}
```

### 异常响应

#### 1. 终端组权限错误
```json
{
    "msg": "some group out of control",
    "errorCode": 20000,
    "httpCode": 500
}
```

#### 2. 节目权限错误
```json
{
    "msg": "No permission to control program",
    "errorCode": 20001,
    "httpCode": 403
}
```

#### 3. 参数验证错误
```json
{
    "msg": "Invalid program ID",
    "errorCode": 40001,
    "httpCode": 400
}
```

## 使用示例

### 1. 发布到单个终端
```bash
PUT /wp-json/wp/v2/programs/1?flag=terminalgroup
Content-Type: application/json
Authorization: Bearer your-token

{
    "what": "assign_program_to_terminal_group",
    "to": {
        "terminals_groups": [
            {
                "all": false,
                "id": 1,
                "terminals": [325056]
            }
        ]
    }
}
```

### 2. 发布到整个终端组
```bash
PUT /wp-json/wp/v2/programs/1?flag=terminalgroup
Content-Type: application/json
Authorization: Bearer your-token

{
    "what": "assign_program_to_terminal_group",
    "to": {
        "terminals_groups": [
            {
                "all": true,
                "id": 1,
                "terminals": []
            }
        ]
    }
}
```

### 3. 发布到多个终端组
```bash
PUT /wp-json/wp/v2/programs/1?flag=terminalgroup
Content-Type: application/json
Authorization: Bearer your-token

{
    "what": "assign_program_to_terminal_group",
    "to": {
        "terminals_groups": [
            {
                "all": false,
                "id": 1,
                "terminals": [325056, 325057]
            },
            {
                "all": true,
                "id": 2,
                "terminals": []
            }
        ]
    }
}
```

## 错误处理

### 400 Bad Request
- 缺少或无效的flag参数
- 无效的节目ID
- 请求体格式错误
- 缺少必需的字段

### 403 Forbidden
- 用户没有权限操作该节目
- 用户没有权限发布节目

### 500 Internal Server Error
- 用户对指定的终端组没有权限
- 服务器内部错误

## 权限验证

### 节目权限
- 只有节目的作者或具有管理员权限的用户才能发布节目
- 系统会验证当前用户是否有权限操作指定的节目

### 终端组权限
- 系统会验证用户是否有权限操作指定的终端组
- 如果用户对任何终端组没有权限，会返回相应的错误信息

## 注意事项

1. **flag参数**: 必须为"terminalgroup"，否则请求会被视为更新节目请求
2. **what参数**: 必须为"assign_program_to_terminal_group"
3. **终端组验证**: 系统会验证所有指定的终端组是否存在且用户有权限操作
4. **权限检查**: 发布前会进行双重权限验证（节目权限和终端组权限）
5. **事务处理**: 发布操作在事务中执行，确保数据一致性
6. **日志记录**: 所有发布操作都会记录详细的日志信息

## 技术实现

### 权限验证流程
1. 验证请求参数的有效性
2. 检查节目是否存在
3. 验证用户对节目的操作权限
4. 验证用户对终端组的操作权限
5. 执行发布逻辑
6. 更新节目的修改时间

### 错误处理机制
- 使用自定义异常来处理不同类型的错误
- 根据错误类型返回相应的HTTP状态码和错误信息
- 提供详细的错误日志用于调试

### 安全考虑
- 严格的参数验证
- 权限检查机制
- 事务安全处理
- 输入数据清理和验证 