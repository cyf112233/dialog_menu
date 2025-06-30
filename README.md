# 🎮 DialogPlugin - 对话框菜单插件

> 一个基于 [DialogPlugin](https://github.com/AlepandoCR/DialogPlugin) 框架开发的强大对话框菜单插件，支持可视化编辑器配置

[![License](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0.0-green.svg)](https://github.com/cxkcxkckx/DialogPlugin/releases)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.6+-orange.svg)](https://www.minecraft.net/)

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
- **Minecraft 版本**：1.21.6+
- **Java 版本**：Java 21+
- **服务器类型**：Paper 及其分支

### 安装步骤

1. **获取插件文件**
   - 从项目 Releases 页面获取最新版本

2. **安装到服务器**
   - 将插件文件放入服务器的 `plugins` 文件夹

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

插件使用 JSON 格式的菜单配置文件，存储在 `plugins/DialogPlugin/CD/` 文件夹中。
主配置文件 `config.yml` 也位于 `plugins/DialogPlugin/` 目录下。

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
    },
    {
      "type": "ITEM",
      "content": "服务器特色物品",
      "x": 150,
      "y": 80,
      "width": 50,
      "height": 50,
      "itemMaterial": "DIAMOND_SWORD"
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

#### 物品展示配置示例

```json
{
  "type": "ITEM",
  "content": "这是一个钻石剑",
  "x": 150,
  "y": 80,
  "width": 50,
  "height": 50,
  "itemMaterial": "DIAMOND_SWORD"
}
```

**注意**：物品展示功能目前仅支持基础物品材质显示，暂不支持自定义物品名称和描述。

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
plugins/
├── DialogPlugin/                  # 配置目录
│   ├── CD/                        # 菜单目录
│   │  ├── default.json            # 默认菜单
│   │  ├── example.json            # 示例菜单
│   │  ├── test_buttons.json       # 测试菜单
│   │  └── submenu.json            # 子菜单
│   └── config.yml                 # 配置文件
└── DialogPlugin.jar               # 插件主文件
```

### 配置文件结构

插件使用 JSON 格式的菜单配置文件，存储在 `plugins/DialogPlugin/CD/` 文件夹中。
主配置文件 `config.yml` 也位于 `plugins/DialogPlugin/` 目录下。

## 🔧 高级配置

### 主配置文件 (config.yml)

```yaml
# DialogPlugin - 对话框菜单插件配置文件
# 作者: cxkcxkckx
# 注意: 默认菜单名称为 "default"，使用 /ui 命令可直接打开 default.json 菜单

# 日志设置
logging:
  # 是否在控制台输出详细日志
  console: false
  # 是否记录动作执行日志
  actions: false
  # 是否记录菜单操作日志
  menu: false
  # 是否记录错误日志
  errors: false

# 消息设置
messages:
  # 是否向玩家发送动作执行成功消息
  success: false
  # 是否向玩家发送动作执行失败消息
  failure: false
  # 是否向玩家发送命令执行成功消息
  command_success: false
  # 是否向玩家发送传送成功消息
  teleport_success: false
  # 是否向玩家发送物品获得消息
  item_success: false
```

### 配置选项说明

| 选项 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| `logging.console` | Boolean | `false` | 是否在控制台输出详细日志 |
| `logging.actions` | Boolean | `false` | 是否记录动作执行日志 |
| `logging.menu` | Boolean | `false` | 是否记录菜单操作日志 |
| `logging.errors` | Boolean | `false` | 是否记录错误日志 |
| `messages.success` | Boolean | `false` | 是否发送动作执行成功消息 |
| `messages.failure` | Boolean | `false` | 是否发送动作执行失败消息 |
| `messages.command_success` | Boolean | `false` | 是否发送命令执行成功消息 |
| `messages.teleport_success` | Boolean | `false` | 是否发送传送成功消息 |
| `messages.item_success` | Boolean | `false` | 是否发送物品获得消息 |

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

### 调试建议

- 启用 `config.yml` 中的日志选项来查看详细调试信息
- 检查控制台输出的错误信息
- 验证 JSON 配置文件的语法正确性

## 📄 许可证

本项目采用 GPLv3 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

GPLv3 许可证要求：
- 任何基于本项目的衍生作品必须同样采用 GPLv3 许可证
- 必须公开源代码
- 必须保留原始版权声明

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给我们一个星标！**

Made with ❤️ by [cxkcxkckx](https://github.com/cxkcxkckx)

</div> 
