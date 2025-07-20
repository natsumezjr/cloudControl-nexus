# 删除播放盒节目API文档

## 删除播放盒节目（取消发布）

### 基本信息
- **URL**: `/wp-json/wp/v2/programPublishRecords`
- **HTTP请求方式**: `DELETE`
- **授权角色**: `administrator`, `manager`, `editor`, `operator`, `contributor`
- **支持格式**: `json`

### 请求参数

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| programId | 否 | int | 取消发布的节目id | 917 |
| vsnMd5 | 否 | string | vsn文件的MD5（对于非网页端，由于没有programId，所以只能通过该参数来删除播放盒节目） | "b4fb664df98ec39d8ddf30d72ca85ad3" |
| terminalIds | 是 | array | 取消发布的终端 | [91] |

**注意**: `programId`和`vsnMd5`必须提供其中一个，不能同时为空。

### 请求示例

#### 1. 使用programId取消发布
```json
{
    "programId": 917,
    "terminalIds": [91]
}
```

#### 2. 使用vsnMd5取消发布
```json
{
    "vsnMd5": "b4fb664df98ec39d8ddf30d72ca85ad3",
    "terminalIds": [91, 92, 93]
}
```

#### 3. 取消发布到多个终端
```json
{
    "programId": 917,
    "terminalIds": [91, 92, 93, 94, 95]
}
```

### 成功响应

#### 响应字段
| 字段名 | 类型 | 说明 | 示例 |
|--------|------|------|------|
| id | int | 取消发布的节目id | 917 |
| author | int | 取消发布的节目的作者Id | 1 |
| date | String | 取消发布的节目的创建日期 | "2020-12-26 02:48:25" |
| date_gmt | String | 取消发布的节目的创建日期（GMT时间） | "2020-12-26 02:48:25" |
| content | String | 节目内容 | "" |
| title_raw | String | 节目名 | "PickSystem23333-sgy-test" |
| excerpt | String | 节目摘要 | "" |
| status | String | 节目状态 | "publish" |
| password | String | 节目密码 | "" |
| name | String | 节目名称 | "PickSystem23333-sgy-test" |
| to_ping | String | 待ping的URL | "" |
| pinged | String | 已ping的URL | "" |
| modified | String | 节目修改时间 | "2022-08-15 02:34:39" |
| modified_gmt | String | 节目修改时间（GMT） | "2022-08-15 02:34:39" |
| content_filtered | String | 过滤后的内容 | "" |
| type | String | 节目类型 | "program" |
| vsn_name | String | VSN文件名 | "PickSystem23333-sgy-test_e11cd93be3d8454db846b5fe1e0b8c5a_1975" |
| program_source_thumbnail | String | 节目缩略图 | null |
| parent | int | 父级ID | 0 |
| guid | String | 全局唯一标识符 | "archives/program/picksystem23333-sgy-test" |
| mime_type | String | MIME类型 | "" |
| program_info | Object | 节目信息 | null |
| terminals_groups | Array | 终端组 | [] |
| _embedded | Object | 嵌入信息 | {"author": []} |
| title | Object | 标题信息 | {"rendered": null} |
| _links | Object | 链接信息 | null |

#### 响应示例
```json
{
    "id": 917,
    "author": 1,
    "date": "2020-12-26 02:48:25",
    "date_gmt": "2020-12-26 02:48:25",
    "content": "",
    "title_raw": "PickSystem23333-sgy-test",
    "excerpt": "",
    "status": "publish",
    "password": "",
    "name": "PickSystem23333-sgy-test",
    "to_ping": "",
    "pinged": "",
    "modified": "2022-08-15 02:34:39",
    "modified_gmt": "2022-08-15 02:34:39",
    "content_filtered": "",
    "type": "program",
    "vsn_name": "PickSystem23333-sgy-test_e11cd93be3d8454db846b5fe1e0b8c5a_1975",
    "program_source_thumbnail": null,
    "parent": 0,
    "guid": "archives/program/picksystem23333-sgy-test",
    "mime_type": "",
    "program_info": null,
    "terminals_groups": [],
    "_embedded": {
        "author": []
    },
    "title": {
        "rendered": null
    },
    "_links": null
}
```

### 异常响应

#### 1. 未授权错误
```json
{
    "code": 401,
    "message": "Unauthorized",
    "data": null
}
```

#### 2. 权限不足错误
```json
{
    "code": 50000,
    "message": "No permission.",
    "data": null
}
```

#### 3. 参数验证错误
```json
{
    "code": 400,
    "message": "Request body cannot be null",
    "data": null
}
```

#### 4. 服务器内部错误
```json
{
    "code": 500,
    "message": "Internal server error",
    "data": null
}
```

## 使用示例

### 1. 取消发布单个终端
```bash
DELETE /wp-json/wp/v2/programPublishRecords
Content-Type: application/json
Authorization: Bearer your-token

{
    "programId": 917,
    "terminalIds": [91]
}
```

### 2. 取消发布多个终端
```bash
DELETE /wp-json/wp/v2/programPublishRecords
Content-Type: application/json
Authorization: Bearer your-token

{
    "programId": 917,
    "terminalIds": [91, 92, 93]
}
```

### 3. 使用vsnMd5取消发布
```bash
DELETE /wp-json/wp/v2/programPublishRecords
Content-Type: application/json
Authorization: Bearer your-token

{
    "vsnMd5": "b4fb664df98ec39d8ddf30d72ca85ad3",
    "terminalIds": [91]
}
```

## 错误处理

### 400 Bad Request
- 请求体为空或格式错误
- 既没有提供programId也没有提供vsnMd5
- terminalIds数组为空
- 包含无效的终端ID（如0或负数）

### 401 Unauthorized
- 用户未认证或认证失败
- 缺少认证信息

### 403 Forbidden
- 用户没有权限取消发布指定的节目
- 用户没有取消发布权限

### 500 Internal Server Error
- 服务器内部错误
- 数据库操作失败
- 终端通信失败

## 权限验证

### 取消发布权限
- 只有具有管理员、经理、编辑者、操作员或贡献者角色的用户才能执行取消发布
- 系统会验证用户是否有权限取消发布指定的节目

### 节目权限检查
- 对于要取消发布的节目，系统会检查用户是否有权限操作
- 如果用户对节目没有权限，整个取消发布操作会失败

## 注意事项

1. **只删除播放盒节目**: 此操作只会删除播放盒中的节目，不会删除服务器上的节目
2. **参数要求**: programId和vsnMd5必须提供其中一个，不能同时为空
3. **终端同步**: 取消发布后，指定的终端将不再播放该节目
4. **事务处理**: 取消发布操作在事务中执行，确保数据一致性
5. **日志记录**: 所有取消发布操作都会记录详细的日志信息
6. **权限控制**: 严格的权限验证确保操作安全

## 技术实现

### 取消发布流程
1. 验证请求参数的有效性
2. 检查节目是否存在（通过programId或vsnMd5）
3. 验证用户对节目的取消发布权限
4. 构建取消发布响应（在取消发布前保存节目信息）
5. 执行取消发布逻辑（向指定终端发送取消发布指令）
6. 返回取消发布的节目信息

### 终端通信
- 向指定的终端发送取消发布指令
- 等待终端确认取消发布完成
- 更新发布记录状态
- 记录操作日志

### 错误处理机制
- 参数验证错误
- 权限检查错误
- 节目查找错误
- 终端通信错误

### 性能优化
- 异步处理终端通信
- 合理的超时设置
- 错误重试机制
- 批量处理多个终端

## 安全考虑

### 权限控制
- 严格的角色权限验证
- 节目级别的权限检查
- 操作日志记录

### 数据保护
- 取消发布前数据验证
- 事务安全处理
- 终端状态同步

### 输入验证
- 节目ID和vsnMd5格式验证
- 终端ID有效性检查
- 空值处理 