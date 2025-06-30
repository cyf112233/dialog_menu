package alepando.dev.dialogPlugin.utils

import org.bukkit.plugin.Plugin
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private var plugin: Plugin? = null
    private var configManager: ConfigManager? = null
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    
    fun init(pluginInstance: Plugin) {
        plugin = pluginInstance
        if (pluginInstance is alepando.dev.dialogPlugin.DialogPlugin) {
            configManager = pluginInstance.configManager
        }
    }
    
    fun info(message: String) {
        if (configManager?.isConsoleLoggingEnabled() == true) {
            log("INFO", message)
        }
    }
    
    fun warning(message: String) {
        if (configManager?.isConsoleLoggingEnabled() == true) {
            log("WARNING", message)
        }
    }
    
    fun error(message: String) {
        if (configManager?.isErrorLoggingEnabled() == true) {
            log("ERROR", message)
        }
    }
    
    fun debug(message: String) {
        if (configManager?.isConsoleLoggingEnabled() == true) {
            log("DEBUG", message)
        }
    }
    
    fun severe(message: String) {
        if (configManager?.isErrorLoggingEnabled() == true) {
            log("SEVERE", message)
        }
    }
    
    private fun log(level: String, message: String) {
        val timestamp = dateFormat.format(Date())
        val threadName = Thread.currentThread().name
        val formattedMessage = "[$timestamp] [$threadName] [$level] $message"
        
        plugin?.logger?.info(formattedMessage)
        
        // 同时输出到控制台
        println(formattedMessage)
    }
    
    // 专门用于菜单系统的日志
    object Menu {
        fun openMenu(playerName: String, menuName: String) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 玩家 $playerName 打开菜单 $menuName")
            }
        }
        
        fun closeMenu(playerName: String, menuName: String) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 玩家 $playerName 关闭菜单 $menuName")
            }
        }
        
        fun buttonClick(playerName: String, buttonLabel: String, actionType: String) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 玩家 $playerName 点击按钮 '$buttonLabel' (动作类型: $actionType)")
            }
        }
        
        fun loaded(menuName: String, filePath: String) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 加载菜单 $menuName 从 $filePath")
            }
        }
        
        fun loadError(fileName: String, error: String) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("菜单系统: 加载菜单文件 $fileName 失败: $error")
            }
        }
        
        fun loadComplete(menuCount: Int) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 菜单加载完成，共 $menuCount 个菜单")
            }
        }
        
        fun copiedDefaultMenu(fileName: String) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 复制默认菜单文件 $fileName")
            }
        }
        
        fun actionsRegistered(actionCount: Int) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                info("菜单系统: 注册了 $actionCount 个菜单动作")
            }
        }
        
        fun storeData(key: String, data: Map<String, Any>) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("菜单系统: 存储数据 $key -> $data")
            }
        }
        
        fun retrieveData(key: String, data: Map<String, Any>?) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                if (data != null) {
                    debug("菜单系统: 获取数据 $key -> $data")
                } else {
                    warning("菜单系统: 未找到数据 $key")
                }
            }
        }
        
        fun actionExecute(playerName: String, actionType: String, success: Boolean, message: String? = null) {
            if (configManager?.isMenuLoggingEnabled() == true) {
                val status = if (success) "成功" else "失败"
                val msg = message?.let { " ($it)" } ?: ""
                info("菜单系统: 玩家 $playerName 执行动作 $actionType $status$msg")
            }
        }
        
        fun error(playerName: String, operation: String, error: String) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("菜单系统: 玩家 $playerName 在 $operation 时出错: $error")
            }
        }
        
        fun exception(playerName: String, operation: String, exception: Exception) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("菜单系统: 玩家 $playerName 在 $operation 时发生异常: ${exception.message}")
                exception.printStackTrace()
            }
        }
        
        fun exceptionContext(context: String, operation: String, exception: Exception) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("菜单系统: $context 在 $operation 时发生异常: ${exception.message}")
                exception.printStackTrace()
            }
        }
    }
    
    // 专门用于数据存储的日志
    object DataStore {
        fun store(key: String, data: Map<String, Any>) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("数据存储: 存储 $key -> $data")
            }
        }
        
        fun retrieve(key: String, data: Map<String, Any>?) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                if (data != null) {
                    debug("数据存储: 获取 $key -> $data")
                } else {
                    warning("数据存储: 未找到 $key")
                }
            }
        }
        
        fun remove(key: String) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("数据存储: 删除 $key")
            }
        }
        
        fun clear() {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                info("数据存储: 清理所有数据")
            }
        }
        
        fun getAllData(entries: Set<Map.Entry<String, Map<String, Any>>>) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("数据存储: 当前所有数据 (${entries.size} 条):")
                entries.forEach { (key, value) ->
                    debug("  $key -> $value")
                }
            }
        }
    }
    
    // 专门用于动作系统的日志
    object Actions {
        fun registerAction(actionType: String, key: String) {
            if (configManager?.isActionLoggingEnabled() == true) {
                info("动作系统: 注册动作 $actionType -> $key")
            }
        }
        
        fun executeAction(playerName: String, actionType: String, data: Map<String, Any>?) {
            if (configManager?.isActionLoggingEnabled() == true) {
                if (data != null) {
                    info("动作系统: 玩家 $playerName 执行动作 $actionType 数据: $data")
                } else {
                    warning("动作系统: 玩家 $playerName 执行动作 $actionType 但数据为空")
                }
            }
        }
        
        fun actionSuccess(playerName: String, actionType: String, result: String) {
            if (configManager?.isActionLoggingEnabled() == true) {
                info("动作系统: 玩家 $playerName 动作 $actionType 执行成功: $result")
            }
        }
        
        fun actionFailed(playerName: String, actionType: String, error: String) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("动作系统: 玩家 $playerName 动作 $actionType 执行失败: $error")
            }
        }
    }
    
    // 专门用于构建器的日志
    object Builder {
        fun startBuild(playerName: String, menuName: String) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                info("构建器: 开始为玩家 $playerName 构建菜单 $menuName")
            }
        }
        
        fun buildComplete(playerName: String, menuName: String) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                info("构建器: 玩家 $playerName 的菜单 $menuName 构建完成")
            }
        }
        
        fun buildError(playerName: String, menuName: String, error: String) {
            if (configManager?.isErrorLoggingEnabled() == true) {
                error("构建器: 玩家 $playerName 的菜单 $menuName 构建失败: $error")
            }
        }
        
        fun buttonCreated(buttonLabel: String, actionType: String) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("构建器: 创建按钮 '$buttonLabel' (动作类型: $actionType)")
            }
        }
        
        fun layoutCalculated(columns: Int, buttonCount: Int) {
            if (configManager?.isConsoleLoggingEnabled() == true) {
                debug("构建器: 布局计算完成 - $columns 列, $buttonCount 个按钮")
            }
        }
    }
} 