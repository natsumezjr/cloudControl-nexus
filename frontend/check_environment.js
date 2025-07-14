// frontend/check_environment.js
// 前端环境检查与自动依赖安装脚本

const { execSync } = require('child_process');
const fs = require('fs');

function runCommand(cmd) {
  try {
    const output = execSync(cmd, { stdio: 'pipe' }).toString().trim();
    return [true, output];
  } catch (e) {
    return [false, e.message];
  }
}

function checkNode() {
  console.log('==============================');
  console.log('检查 Node.js 环境...');
  const [ok, output] = runCommand('node --version');
  if (ok) {
    console.log('✅ Node.js 已安装:', output);
  } else {
    console.log('❌ Node.js 未安装');
    process.exit(1);
  }
}

function checkNpm() {
  const [ok, output] = runCommand('npm --version');
  if (ok) {
    console.log('✅ npm 已安装:', output);
  } else {
    console.log('❌ npm 未安装');
    process.exit(1);
  }
}

function checkNodeModules() {
  if (fs.existsSync('node_modules')) {
    console.log('✅ 依赖已安装 (node_modules 存在)');
    return true;
  } else {
    console.log('❌ 未检测到 node_modules，自动执行 npm install...');
    const [ok] = runCommand('npm install');
    if (ok) {
      console.log('✅ 依赖安装成功');
      return true;
    } else {
      console.log('❌ 依赖安装失败，请手动执行 npm install');
      process.exit(1);
    }
  }
}

function checkPackage(pkg) {
  try {
    require.resolve(pkg);
    console.log(`✅ 依赖包 ${pkg} 已安装`);
    return true;
  } catch {
    console.log(`❌ 依赖包 ${pkg} 未安装，自动安装...`);
    const [ok] = runCommand(`npm install ${pkg}`);
    if (ok) {
      console.log(`✅ 依赖包 ${pkg} 安装成功`);
      return true;
    } else {
      console.log(`❌ 依赖包 ${pkg} 安装失败，请手动安装`);
      process.exit(1);
    }
  }
}

function main() {
  console.log('CloudControl-Nexus 前端环境检查');
  checkNode();
  checkNpm();
  checkNodeModules();
  // 检查常用依赖
  ['vue', 'element-plus', 'axios', 'typescript'].forEach(checkPackage);
  console.log('==============================');
  console.log('🎉 前端开发环境检查通过！');
}

main(); 