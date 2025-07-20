# 批量删除节目API文档

## 批量删除节目

### 基本信息
- **URL**: `/wp-json/wp/v2/programs/delprograms`
- **HTTP请求方式**: `POST`
- **授权角色**: `administrator`, `manager`, `editor`, `operator`
- **支持格式**: `json`

### 请求参数

| 字段名 | 必选 | 类型 | 说明 | 示例 |
|--------|------|------|------|------|
| program_ids | 是 | array | 待删除的节目数组 | [239957, 239958] |

### 请求示例

```json
{
    "program_ids": [239957, 239958, 239959]
}
```

### 成功响应

#### 响应字段
| 字段名 | 类型 | 说明 | 示例 |
|--------|------|------|------|
| id | int | 删除的节目id | 239957 |
| author | int | 删除的节目的作者Id | 1 |
| date | String | 删除的节目的创建日期 | "2020-12-26 02:48:25" |
| date_gmt | String | 删除的节目的创建日期（GMT时间） | "2020-12-26 02:48:25" |
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
[
    {
        "id": 239957,
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
]
```

## 使用示例

### 1. 删除单个节目
```bash
POST /wp-json/wp/v2/programs/delprograms
Content-Type: application/json
Authorization: Bearer your-token

{
    "program_ids": [239957]
}
```

### 2. 删除多个节目
```bash
POST /wp-json/wp/v2/programs/delprograms
Content-Type: application/json
Authorization: Bearer your-token

{
    "program_ids": [239957, 239958, 239959]
}
```

### 3. 批量删除大量节目
```bash
POST /wp-json/wp/v2/programs/delprograms
Content-Type: application/json
Authorization: Bearer your-token

{
    "program_ids": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}
```

## 错误处理

### 400 Bad Request
- 请求体为空或格式错误
- program_ids数组为空
- 包含无效的节目ID（如0或负数）
- 包含重复的节目ID

### 403 Forbidden
- 用户没有权限删除指定的节目
- 用户没有批量删除权限

### 500 Internal Server Error
- 服务器内部错误
- 数据库操作失败

## 权限验证

### 批量删除权限
- 只有具有管理员、经理、编辑者或操作员角色的用户才能执行批量删除
- 系统会验证用户是否有权限删除所有指定的节目

### 节目权限检查
- 对于每个要删除的节目，系统会检查用户是否有权限删除
- 如果用户对任何节目没有权限，整个批量删除操作会失败

## 注意事项

1. **事务处理**: 批量删除操作在事务中执行，确保数据一致性
2. **原子性**: 要么全部删除成功，要么全部失败，不会出现部分删除的情况
3. **终端同步**: 删除服务器中的节目后，终端中的节目也会被删除
4. **数据备份**: 建议在执行批量删除前备份重要数据
5. **性能考虑**: 大量节目删除可能需要较长时间，建议分批处理
6. **日志记录**: 所有删除操作都会记录详细的日志信息

## 技术实现

### 删除流程
1. 验证请求参数的有效性
2. 检查所有节目是否存在
3. 验证用户对每个节目的删除权限
4. 构建删除响应（在删除前保存节目信息）
5. 执行批量删除操作
6. 同步删除终端中的节目
7. 返回删除的节目信息

### 数据同步
- 删除服务器中的节目记录
- 向相关终端发送删除指令
- 等待终端确认删除完成
- 记录删除结果和状态

### 错误处理机制
- 参数验证错误
- 权限检查错误
- 数据库操作错误
- 终端同步错误

### 性能优化
- 使用批量删除减少数据库操作次数
- 异步处理终端同步操作
- 合理的超时设置
- 错误重试机制

## 安全考虑

### 权限控制
- 严格的角色权限验证
- 节目级别的权限检查
- 操作日志记录

### 数据保护
- 删除前数据验证
- 事务安全处理
- 备份和恢复机制

### 输入验证
- 节目ID格式验证
- 重复ID检查
- 空值处理 