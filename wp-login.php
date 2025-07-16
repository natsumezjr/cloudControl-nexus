<?php
// 项目路径：frontend/static/wp-login.php
// 1. 获取终端POST过来的数据
$postData = file_get_contents("php://input");

// 2. 转发到Java后端
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/api/login"); // 请替换为实际后端地址
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_HEADER, 1); // 获取header
curl_setopt($ch, CURLOPT_HTTPHEADER, array(
    "Content-Type: application/json"
));
$response = curl_exec($ch);

// 3. 解析Set-Cookie头
$header_size = curl_getinfo($ch, CURLINFO_HEADER_SIZE);
$header = substr($response, 0, $header_size);
$body = substr($response, $header_size);

preg_match('/Set-Cookie: JSESSIONID=([^;]+);/', $header, $matches);
$jsessionid = $matches[1] ?? null;

// 4. 返回给终端
header('Content-Type: application/json');
if ($jsessionid) {
    header("Set-Cookie: JSESSIONID=$jsessionid; Path=/; HttpOnly");
}
echo json_encode([
    "jsessionid" => $jsessionid,
    "data" => json_decode($body, true)
]);
curl_close($ch); 