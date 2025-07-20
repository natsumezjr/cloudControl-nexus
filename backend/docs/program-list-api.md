# 节目列表API文档

## 获取节目列表

### 基本信息
- **URL**: `/wp-json/wp/v2/programs`
- **HTTP请求方式**: `GET`
- **授权角色**: `administrator`, `editor`, `operator`, `contributor`, `auditor`, `subscriber`, `supervisor`, `invitee`, `monitor`, `manager`
- **支持格式**: `json`

### 请求参数

| 字段名 | 必选 | 类型及范围 | 说明 | 示例 |
|--------|------|------------|------|------|
| page | 否 | int | 列表的页码，默认是1 | 1 |
| per_page | 否 | int | 每页显示数量，默认12，最大100 | 12 |
| status | 否 | string | 节目状态筛选 | `publish`, `draft`, `private` |
| mime_type | 否 | string | 用于区分是桶节目还是其它节目，当是桶节目时，该值是bucket；不传则是非桶节目 | `bucket` |
| author | 否 | int | 节目作者id | 1 |
| search | 否 | string | 节目名模糊匹配的关键词 | `test` |
| orderby | 否 | string | 排序方式 | `modified`, `programName` |
| before | 否 | string | 节目修改时间在该时间之前（包含） | `2024-12-31T23:59:59` |
| after | 否 | string | 节目修改时间在该时间之后（包含） | `2024-01-01T00:00:00` |
| terminalGroupId | 否 | int | 终端组id | 1 |

### 排序方式说明
- `modified`: 根据节目修改时间排序（降序）
- `programName`: 根据节目名升序排序
- 默认: 根据创建时间排序（降序）

### 成功响应格式

```json
[
    {
        "date": "2024-07-11T02:26:12",
        "vsn_name": "Playlist63224545_a0ebbeef68bdcf0901e358d5d64ff063_4602.vsn",
        "modified_gmt": "2024-07-11T02:44:43Z",
        "author": 1,
        "published_by_schedule": false,
        "program_source_thumbnail": "https://sa2.colorlightcloud.com:443/wp-content/playList/thumbnails/1720664772610_a2ed445ee521c5ca258fadeb3ce44678.png",
        "title": {
            "rendered": "Playlist63224545"
        },
        "type": "program",
        "_embedded": {
            "author": [
                {
                    "name": "superroot"
                }
            ]
        },
        "program_info": {},
        "modified": "2024-07-11T02:44:43",
        "title_raw": "Playlist63224545",
        "id": 900,
        "terminals_groups": [
            {
                "all": true,
                "parent": 1,
                "name": "test video crop",
                "id": 92,
                "terminals": []
            }
        ],
        "date_gmt": "2024-07-11T02:26:12Z",
        "slug": "Playlist63224545_a0ebbeef68bdcf0901e358d5d64ff063_4602",
        "status": "publish"
    }
]
```

### 响应字段说明

| 字段名 | 类型 | 说明 | 示例 |
|--------|------|------|------|
| date | string | 节目创建日期 | `2024-07-11T02:26:12` |
| vsn_name | string | 节目vsn名字 | `Playlist68_e03893c6ad80438e635610687461bb65_1373.vsn` |
| modified_gmt | string | 节目修改时间（GMT） | `2024-07-11T02:24:43Z` |
| author | int | 节目作者ID | 1 |
| published_by_schedule | boolean | 是否按计划发布 | false |
| program_source_thumbnail | string | 节目缩略图路径 | `https://...` |
| title | object | 节目名 | `{"rendered": "Playlist68"}` |
| type | string | 类型 | `program` |
| _embedded | object | 嵌入信息 | 包含作者信息 |
| program_info | object | 创建节目的参数 | 节目配置信息 |
| modified | string | 节目修改时间 | `2024-07-11T02:44:43` |
| title_raw | string | 原始标题 | `Playlist63224545` |
| id | int | 节目ID | 900 |
| terminals_groups | array | 节目发布到的终端组/终端 | 终端组信息 |
| date_gmt | string | 节目创建时间（GMT） | `2024-07-11T02:26:12Z` |
| slug | string | 节目标识符 | `Playlist63224545_a0ebbeef68bdcf0901e358d5d64ff063_4602` |
| status | string | 节目状态 | `publish` |

## 使用示例

### 1. 基本查询
```bash
GET /wp-json/wp/v2/programs
```

### 2. 分页查询
```bash
GET /wp-json/wp/v2/programs?page=1&per_page=20
```

### 3. 状态筛选
```bash
GET /wp-json/wp/v2/programs?status=publish
```

### 4. 搜索节目
```bash
GET /wp-json/wp/v2/programs?search=test
```

### 5. 作者筛选
```bash
GET /wp-json/wp/v2/programs?author=1
```

### 6. 时间范围查询
```bash
GET /wp-json/wp/v2/programs?after=2024-01-01T00:00:00&before=2024-12-31T23:59:59
```

### 7. 排序查询
```bash
GET /wp-json/wp/v2/programs?orderby=programName
```

### 8. 组合查询
```bash
GET /wp-json/wp/v2/programs?page=1&per_page=10&status=publish&search=test&orderby=modified&author=1
```

## 错误处理

### 400 Bad Request
当请求参数无效时返回：
```json
{
    "error": "Invalid parameters"
}
```

### 500 Internal Server Error
当服务器内部错误时返回：
```json
{
    "error": "Internal server error"
}
```

## 注意事项

1. **分页限制**: `per_page` 参数最大值为100，超过会自动调整为100
2. **时间格式**: 时间参数支持ISO 8601格式，如 `2024-01-01T00:00:00` 或 `2024-01-01T00:00:00Z`
3. **搜索功能**: `search` 参数支持节目标题的模糊匹配
4. **排序功能**: 支持按修改时间和节目名排序
5. **权限控制**: 不同角色可以访问的节目可能不同
6. **性能优化**: 建议合理使用分页和筛选条件，避免查询大量数据

## 技术实现

### 数据库查询优化
- 使用JPA的复杂查询方法支持多条件组合查询
- 支持分页和排序
- 时间范围查询使用索引优化

### 响应格式
- 严格按照WordPress REST API格式返回数据
- 包含完整的节目元数据信息
- 支持嵌套对象和数组结构

### 缓存策略
- 建议在应用层实现适当的缓存机制
- 对于频繁查询的数据可以考虑Redis缓存 