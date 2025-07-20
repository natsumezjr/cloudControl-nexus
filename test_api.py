import requests
import json

# 测试API端点
url = "http://localhost:8080/wp-json/wp/v2/comments/sleepCommand"
headers = {
    "Content-Type": "application/json"
}
data = {
    "terminalIds": [1],
    "value": {}
}

try:
    response = requests.post(url, headers=headers, json=data)
    print(f"状态码: {response.status_code}")
    print(f"响应头: {dict(response.headers)}")
    print(f"响应体: {response.text}")
except Exception as e:
    print(f"请求失败: {e}") 