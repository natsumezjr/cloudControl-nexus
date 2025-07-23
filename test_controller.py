import requests
import json

# 测试基础路径
base_url = "http://localhost:8080"

# 测试健康检查
try:
    response = requests.get(f"{base_url}/actuator/health")
    print(f"健康检查: {response.status_code}")
except Exception as e:
    print(f"健康检查失败: {e}")

# 测试基础路径
try:
    response = requests.get(f"{base_url}/wp-json/wp/v2/comments")
    print(f"基础路径: {response.status_code}")
    if response.status_code != 404:
        print(f"响应: {response.text[:200]}")
except Exception as e:
    print(f"基础路径测试失败: {e}")

# 测试一个已知工作的端点
try:
    response = requests.get(f"{base_url}/wp-json/wp/v2/comments?clt_type=terminal&device_num=CLCA40252131")
    print(f"终端轮询: {response.status_code}")
    if response.status_code != 404:
        print(f"响应: {response.text[:200]}")
except Exception as e:
    print(f"终端轮询测试失败: {e}")

# 测试POST端点
try:
    response = requests.post(f"{base_url}/wp-json/wp/v2/comments", 
                           headers={"Content-Type": "application/json"},
                           json={"parent": 1, "content": "test"})
    print(f"POST基础路径: {response.status_code}")
    if response.status_code != 404:
        print(f"响应: {response.text[:200]}")
except Exception as e:
    print(f"POST基础路径测试失败: {e}") 