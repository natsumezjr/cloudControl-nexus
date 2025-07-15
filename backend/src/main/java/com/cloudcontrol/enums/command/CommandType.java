package com.cloudcontrol.enums.command;

import java.util.Optional;

public enum CommandType {
    BRIGHTNESS(0, "brightness", 2, "api/brightness", "亮度指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            int v = value.map(CommandType::parseInt).orElseThrow(() -> new IllegalArgumentException("亮度值不能为空"));
            if (v < 0 || v > 255) throw new IllegalArgumentException("亮度值必须在0-255之间");
            return "{\"brightness\":" + v + "}";
        }
    },
    UPGRADE(1, "upgrade", 0, "api/update", "升级指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            String url = value.map(Object::toString).orElseThrow(() -> new IllegalArgumentException("升级包地址不能为空"));
            // 直接返回字符串，不加引号
            return url;
        }
    },
    REBOOT(2, "reboot", 1, "api/action", "重启指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            return "{\"command\":\"reboot\"}";
        }
    },
    SCREENSHOT(3, "screenshot", 0, "transmission/ftp/config", "截屏指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            return "{}";
        }
    },
    SLEEP(4, "sleep", 1, "api/action", "睡眠指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            return "{\"command\":\"sleep\"}";
        }
    },
    WAKEUP(5, "wakeup", 1, "api/action", "唤醒指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            return "{\"command\":\"wakeup\"}";
        }
    },
    BOARD_RELAY(6, "boardRelay", 2, "api/board_relay", "板载继电器控制指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            // value 应为 JSON 数组字符串，如: [{"relay":1,"delay":0,"status":1}]
            return value.map(Object::toString).orElse("[]");
        }
    },
    RELAY(7, "relay", 2, "api/relay", "继电器控制指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            // value 应为 JSON 数组字符串，如: [{"relay":1,"delay":0,"status":1},...]
            return value.map(Object::toString).orElse("[]");
        }
    },
    CLEAR_CACHE(8, "clearCache", 3, "api/clrresunused", "清理缓存指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            return "{}";
        }
    },
    COLOR_TEMP(9, "colortemp", 2, "api/colortemp", "色温调节指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            int v = value.map(CommandType::parseInt).orElseThrow(() -> new IllegalArgumentException("色温值不能为空"));
            if (v < 2000 || v > 10000) throw new IllegalArgumentException("色温值必须在2000-10000之间");
            return "{\"colortemp\":" + v + "}";
        }
    },
    SWITCH_SIGNAL_SOURCE(10, "switchSignalSource", 2, "api/inputmode", "切换信号源指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            String mode = value.map(Object::toString).orElseThrow(() -> new IllegalArgumentException("信号源类型不能为空"));
            // 只允许 hdmi/dvi
            if (!"hdmi".equals(mode) && !"dvi".equals(mode)) throw new IllegalArgumentException("信号源类型必须为hdmi或dvi");
            return "{\"inputmode\":\"" + mode + "\"}";
        }
    },
    VOLUME(11, "volume", 2, "api/volume", "音量控制指令") {
        @Override
        public String buildRaw(Optional<Object> value) {
            int v = value.map(CommandType::parseInt).orElseThrow(() -> new IllegalArgumentException("音量值不能为空"));
            if (v < 0 || v > 15) throw new IllegalArgumentException("音量值必须在0-15之间");
            return "{\"musicvolume\":" + v + "}";
        }
    };

    private final int cmdNum;
    private final String cmdType;
    private final int karma;
    private final String authorUrl;
    private final String desc;

    CommandType(int cmdNum, String cmdType, int karma, String authorUrl, String desc) {
        this.cmdNum = cmdNum;
        this.cmdType = cmdType;
        this.karma = karma;
        this.authorUrl = authorUrl;
        this.desc = desc;
    }

    public int getCmdNum() { return cmdNum; }
    public String getCmdType() { return cmdType; }
    public int getKarma() { return karma; }
    public String getAuthorUrl() { return authorUrl; }
    public String getDesc() { return desc; }

    // 生成 raw 字段内容，必须由子类实现
    public abstract String buildRaw(Optional<Object> value);

    public static CommandType fromCmdNum(int cmdNum) {
        for (CommandType t : values()) {
            if (t.cmdNum == cmdNum) return t;
        }
        throw new IllegalArgumentException("Unknown cmdNum: " + cmdNum);
    }

    public static CommandType fromCmdType(String cmdType) {
        for (CommandType t : values()) {
            if (t.cmdType.equals(cmdType)) return t;
        }
        throw new IllegalArgumentException("Unknown cmdType: " + cmdType);
    }

    // 工具方法：安全转int
    private static int parseInt(Object obj) {
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof String) return Integer.parseInt((String) obj);
        throw new IllegalArgumentException("参数类型错误，必须为整数或数字字符串");
    }
}