package alepando.dev.dialogPlugin

import alepando.dev.dialogPlugin.command.UICommand
import alepando.dev.dialogPlugin.command.ReloadCommand
import alepando.dev.dialogPlugin.command.HelpCommand
import alepando.dev.dialogPlugin.dialog.test.command.TestCommand
import alepando.dev.dialogPlugin.menu.MenuManager
import alepando.dev.dialogPlugin.utils.Logger
import alepando.dev.dialogPlugin.utils.ConfigManager
import alepando.dev.dialogapi.executor.CustomKeyRegistry
import alepando.dev.dialogapi.listeners.PlayerConnectionStatus
import org.bukkit.plugin.java.JavaPlugin

class DialogPlugin : JavaPlugin() {

    lateinit var menuManager: MenuManager
    lateinit var configManager: ConfigManager

    override fun onEnable() {
        // 初始化配置管理器
        configManager = ConfigManager(this)
        configManager.init()
        
        // 初始化日志系统
        Logger.init(this)
        Logger.info("DialogPlugin 正在启动...")
        
        try {
            // 初始化菜单系统
            Logger.info("正在初始化菜单系统...")
            menuManager = MenuManager(this)
            
            // 注册事件监听器
            Logger.info("正在注册事件监听器...")
            server.pluginManager.registerEvents(PlayerConnectionStatus(this), this)
            
            // 注册命令
            Logger.info("正在注册命令...")
            getCommand("test")!!.setExecutor(TestCommand(this))
            getCommand("ui")!!.setExecutor(UICommand(this))
            getCommand("uireload")!!.setExecutor(ReloadCommand(this))
            getCommand("uihelp")!!.setExecutor(HelpCommand(this))
            
            Logger.info("DialogPlugin 已启用！")
            Logger.info("菜单系统已初始化，可用菜单: ${menuManager.getAllMenuNames().joinToString(", ")}")
        } catch (e: Exception) {
            Logger.severe("DialogPlugin 启动失败: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    override fun onDisable() {
        Logger.info("DialogPlugin 正在关闭...")
        
        try {
            // 清理所有注册的Custom Key
            Logger.info("正在清理注册的Custom Key...")
            CustomKeyRegistry.clearAll()
            Logger.info("已清理所有注册的Custom Key")
        } catch (e: Exception) {
            Logger.warning("清理资源时出错: ${e.message}")
        }
        
        Logger.info("DialogPlugin 已禁用！")
    }
}
