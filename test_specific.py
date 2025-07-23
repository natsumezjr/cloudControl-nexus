import requests
import json

# 测试特定的命令端点
base_url = "http://localhost:8080/wp-json/wp/v2/comments"

# 测试所有可能的命令类型
command_types = [
    "brightness", "upgrade", "reboot", "screenshot", "sleep", "wakeup",
    "boardRelay", "relay", "clearCache", "colortemp", "switchSignalSource",
    "volume", "localeSetting", "timezoneSetting", "gpsReportInterval",
    "sensorReportInterval", "contentReportSwitch", "logReportSwitch",
    "networkStatusReport", "rotateProgramReportSwitch", "updateProgram",
    "switchProgram", "clearPrograms", "deleteProgram", "currentLogReport"
]

headers = {
    "Content-Type": "application/json"
}
data = {
    "terminalIds": [1],
    "value": {}
}

print("测试所有命令类型:")
for cmd_type in command_types:
    url = f"{base_url}/{cmd_type}Command"
    try:
        response = requests.post(url, headers=headers, json=data)
        print(f"{cmd_type}Command: {response.status_code}")
        if response.status_code != 404:
            print(f"  响应: {response.text[:100]}")
    except Exception as e:
        print(f"{cmd_type}Command: 错误 - {e}")

print("\n测试基础路径:")
try:
    response = requests.get(f"{base_url}")
    print(f"GET {base_url}: {response.status_code}")
except Exception as e:
    print(f"GET {base_url}: 错误 - {e}")

print("\n测试POST基础路径:")
try:
    response = requests.post(f"{base_url}", headers=headers, json=data)
    print(f"POST {base_url}: {response.status_code}")
except Exception as e:
    print(f"POST {base_url}: 错误 - {e}") 