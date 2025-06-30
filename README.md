# 🎮 DialogPlugin - 对话框菜单插件

> 一个基于 [DialogPlugin](https://github.com/AlepandoCR/DialogPlugin) 框架开发的强大对话框菜单插件，支持可视化编辑器配置

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0.0-green.svg)](https://github.com/cxkcxkckx/DialogPlugin/releases)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.20+-orange.svg)](https://www.minecraft.net/)

## 📖 项目介绍

DialogPlugin 是一个专为 Minecraft 服务器设计的现代化对话框菜单插件，基于 [AlepandoCR/DialogPlugin](https://github.com/AlepandoCR/DialogPlugin) 框架开发。该插件提供了丰富的菜单配置功能，支持文本显示、按钮交互、物品展示等多种元素，让服务器管理员能够轻松创建美观实用的游戏菜单。

### 🌟 核心特性

- **🎨 可视化编辑器**：使用 [dialog.1mc.dpdns.org](https://dialog.1mc.dpdns.org) 在线编辑器，无需编程知识即可创建精美菜单
- **📱 响应式设计**：支持多种屏幕尺寸，适配不同设备
- **⚡ 高性能**：基于现代 Kotlin 开发，性能优异
- **🔧 高度可配置**：支持 JSON 配置文件，灵活定制菜单内容
- **🎯 多种交互**：支持命令执行、消息发送、传送、物品给予等多种动作
- **📊 实时日志**：完整的操作日志记录，便于调试和管理

## 🚀 功能特性

### 📋 菜单元素
- **文本显示**：支持彩色文本、格式化内容
- **按钮交互**：可点击按钮，支持多种动作类型
- **物品展示**：显示物品图标和描述信息
- **网格布局**：智能网格排列，自动计算最佳布局

### 🎮 交互动作
- **命令执行**：支持玩家命令和控制台命令
- **消息发送**：向玩家发送自定义消息
- **传送功能**：支持跨世界传送
- **物品给予**：自动给予玩家指定物品
- **菜单跳转**：支持菜单间的跳转和嵌套

### ⚙️ 管理功能
- **热重载**：支持 `/ui reload` 命令实时重载配置
- **帮助系统**：内置 `/ui help` 帮助命令
- **日志记录**：详细的操作日志和错误追踪
- **权限控制**：可配置的命令权限

## 📦 安装说明

### 系统要求
- **Minecraft 版本**：1.20+
- **Java 版本**：Java 17+
- **服务器类型**：Paper/Spigot

### 安装步骤

1. **下载插件**
   ```bash
   # 从 Releases 页面下载最新版本
   wget https://github.com/cxkcxkckx/DialogPlugin/releases/latest/download/DialogPlugin.jar
   ```

2. **安装到服务器**
   ```bash
   # 将插件文件放入 plugins 文件夹
   cp DialogPlugin.jar /path/to/your/server/plugins/
   ```

3. **启动服务器**
   ```bash
   # 重启服务器或重载插件
   /reload
   ```

4. **验证安装**
   ```bash
   # 在游戏中测试命令
   /ui help
   ```

## 🎯 使用方法

### 基础命令

| 命令 | 权限 | 描述 |
|------|------|------|
| `/ui` | `dialogplugin.use` | 打开默认菜单 |
| `/ui <菜单名>` | `dialogplugin.use` | 打开指定菜单 |
| `/ui reload` | `dialogplugin.reload` | 重载所有配置 |
| `/ui help` | `dialogplugin.help` | 显示帮助信息 |

### 权限节点

| 权限 | 描述 | 默认 |
|------|------|------|
| `dialogplugin.use` | 使用菜单命令 | `true` |
| `dialogplugin.reload` | 重载配置 | `op` |
| `dialogplugin.help` | 查看帮助 | `true` |

## ⚙️ 配置说明

### 配置文件结构

插件使用 JSON 格式的菜单配置文件，存储在 `plugins/DialogPlugin/menus/` 目录下。

#### 基础菜单配置

```json
{
  "title": "主菜单",
  "externalTitle": "服务器主菜单",
  "canCloseWithEscape": true,
  "buttons": [
    {
      "label": "随机传送",
      "x": 30,
      "y": 96,
      "width": 100,
      "height": 25,
      "action": {
        "type": "COMMAND",
        "data": {
          "command": "rtp",
          "asConsole": false
        }
      }
    }
  ],
  "body": [
    {
      "type": "TEXT",
      "content": "§5§l欢迎游玩服务器",
      "x": 108,
      "y": 42,
      "width": 200,
      "height": 20
    }
  ]
}
```

#### 配置参数说明

| 参数 | 类型 | 描述 |
|------|------|------|
| `title` | String | 菜单标题 |
| `externalTitle` | String | 外部标题 |
| `canCloseWithEscape` | Boolean | 是否可用ESC关闭 |
| `buttons` | Array | 按钮列表 |
| `body` | Array | 内容体列表 |

#### 按钮配置

```json
{
  "label": "按钮文本",
  "x": 30,
  "y": 96,
  "width": 100,
  "height": 25,
  "action": {
    "type": "COMMAND",
    "data": {
      "command": "命令内容",
      "asConsole": false
    }
  }
}
```

#### 动作类型

| 类型 | 描述 | 数据参数 |
|------|------|----------|
| `COMMAND` | 执行命令 | `command`, `asConsole` |
| `MESSAGE` | 发送消息 | `message` |
| `TELEPORT` | 传送玩家 | `world`, `x`, `y`, `z` |
| `GIVE_ITEM` | 给予物品 | `material`, `amount` |
| `OPEN_MENU` | 打开菜单 | `menu` |

#### 内容体类型

| 类型 | 描述 | 参数 |
|------|------|------|
| `TEXT` | 文本显示 | `content`, `x`, `y`, `width`, `height` |
| `ITEM` | 物品展示 | `content`, `x`, `y`, `width`, `height`, `itemMaterial` |

## 🎨 可视化编辑器

### 在线编辑器

使用我们的在线可视化编辑器，无需编程知识即可创建精美菜单：

🌐 **编辑器地址**：[dialog.1mc.dpdns.org](https://dialog.1mc.dpdns.org)

### 编辑器功能

- **拖拽式设计**：直观的拖拽界面，轻松布局元素
- **实时预览**：所见即所得的预览效果
- **模板库**：丰富的预设模板，快速开始
- **导出功能**：一键导出 JSON 配置文件
- **版本管理**：支持配置版本控制和回滚

### 使用步骤

1. 访问 [dialog.1mc.dpdns.org](https://dialog.1mc.dpdns.org)
2. 选择模板或创建新菜单
3. 拖拽添加元素，设置属性
4. 实时预览效果
5. 导出 JSON 配置文件
6. 上传到服务器使用

## 📁 文件结构

```
plugins/DialogPlugin/
├── config.yml              # 主配置文件
├── menus/                  # 菜单配置目录
│   ├── default.json        # 默认菜单
│   └── ...                 # 其他菜单文件
└── logs/                   # 日志目录
    ├── actions.log         # 操作日志
    ├── errors.log          # 错误日志
    └── menu.log            # 菜单日志
```

## 🔧 高级配置

### 主配置文件 (config.yml)

```yaml
# DialogPlugin - 对话框菜单插件配置文件
# 作者: cxkcxkckx
# 注意: 默认菜单名称为 "default"，使用 /ui 命令可直接打开 default.json 菜单

# 日志配置
logging:
  enabled: true
  level: INFO
  file: logs/menu.log

# 消息配置
messages:
  command_success: "§a命令执行成功: {command}"
  command_failure: "§c执行命令时出错: {error}"
  teleport_success: "§a传送成功到 {world}({x},{y},{z})"
  teleport_failure: "§c传送时出错: {error}"
  item_success: "§a已获得 {material} x{amount}"
  item_failure: "§c给予物品时出错: {error}"
  menu_not_found: "§c菜单 {menu} 不存在"
  reload_success: "§a配置重载成功"
  reload_failure: "§c配置重载失败: {error}"
```

### 配置选项说明

| 选项 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| `logging.enabled` | Boolean | `true` | 是否启用日志记录 |
| `logging.level` | String | `INFO` | 日志级别 |
| `logging.file` | String | `logs/menu.log` | 日志文件路径 |

## 🐛 故障排除

### 常见问题

#### 1. 菜单无法打开
- 检查菜单文件是否存在
- 验证 JSON 格式是否正确
- 确认权限设置

#### 2. 按钮无响应
- 检查动作配置是否正确
- 验证命令权限
- 查看错误日志

#### 3. 文本不显示
- 确认坐标值为整数
- 检查文本内容格式
- 验证宽度设置

### 日志查看

```bash
# 查看菜单日志
tail -f plugins/DialogPlugin/logs/menu.log

# 查看错误日志
tail -f plugins/DialogPlugin/logs/errors.log

# 查看操作日志
tail -f plugins/DialogPlugin/logs/actions.log
```

## 🤝 贡献指南

我们欢迎社区贡献！如果你想为项目做出贡献，请：

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 开发环境

```bash
# 克隆仓库
git clone https://github.com/cxkcxkckx/DialogPlugin.git

# 进入项目目录
cd DialogPlugin

# 构建项目
./gradlew build
```

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- **AlepandoCR** - 提供 [DialogPlugin 框架](https://github.com/AlepandoCR/DialogPlugin)
- **Minecraft 社区** - 提供灵感和支持
- **所有贡献者** - 帮助改进项目

## 📞 联系方式

- **GitHub**: [cxkcxkckx/DialogPlugin](https://github.com/cxkcxkckx/DialogPlugin)
- **问题反馈**: [Issues](https://github.com/cxkcxkckx/DialogPlugin/issues)
- **在线编辑器**: [dialog.1mc.dpdns.org](https://dialog.1mc.dpdns.org)

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给我们一个星标！**

Made with ❤️ by [cxkcxkckx](https://github.com/cxkcxkckx)

</div> 