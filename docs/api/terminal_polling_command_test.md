# 终端轮询取指令测试设计文档

## 1. 场景说明
终端通过定时HTTP轮询接口（GET /wp-json/wp/v2/comments）获取待执行指令，收到后通过POST接口确认并执行。

## 2. 接口说明
- GET /wp-json/wp/v2/comments：终端拉取待执行指令
- POST /wp-json/wp/v2/comments：终端确认指令

## 3. 数据结构
- 指令对象字段：id, post, author_url, content(raw), karma

## 4. 伪代码流程
```python
while True:
    resp = requests.get('/wp-json/wp/v2/comments', params={'clt_type': 'terminal', 'device_num': device_id})
    commands = resp.json()
    for cmd in commands:
        # 处理指令
        ...
        # 指令确认
        requests.post('/wp-json/wp/v2/comments', json={'parent': cmd['id'], 'content': 'Executable comment', 'post': device_id})
    sleep(interval)
```

## 5. 注意事项
- 指令id需唯一且连续
- 指令内容需为标准JSON
- 终端需定时拉取，避免漏指令
- 指令确认需及时，防止重复执行

---

> 详细流程与接口字段请参考后端API文档和业务需求说明。 