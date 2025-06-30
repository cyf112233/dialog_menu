package alepando.dev.dialogPlugin.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

/**
 * 配置管理器，负责管理插件的配置选项
 */
class ConfigManager(private val plugin: Plugin) {
    
    private lateinit var config: FileConfiguration
    
    /**
     * 初始化配置
     */
    fun init() {
        plugin.saveDefaultConfig()
        config = plugin.config
    }
    
    /**
     * 重新加载配置
     */
    fun reload() {
        plugin.reloadConfig()
        config = plugin.config
    }
    
    /**
     * 检查是否启用控制台日志
     */
    fun isConsoleLoggingEnabled(): Boolean {
        return config.getBoolean("logging.console", false)
    }
    
    /**
     * 检查是否启用动作日志
     */
    fun isActionLoggingEnabled(): Boolean {
        return config.getBoolean("logging.actions", false)
    }
    
    /**
     * 检查是否启用菜单日志
     */
    fun isMenuLoggingEnabled(): Boolean {
        return config.getBoolean("logging.menu", false)
    }
    
    /**
     * 检查是否启用错误日志
     */
    fun isErrorLoggingEnabled(): Boolean {
        return config.getBoolean("logging.errors", false)
    }
    
    /**
     * 检查是否启用成功消息
     */
    fun isSuccessMessageEnabled(): Boolean {
        return config.getBoolean("messages.success", false)
    }
    
    /**
     * 检查是否启用失败消息
     */
    fun isFailureMessageEnabled(): Boolean {
        return config.getBoolean("messages.failure", false)
    }
    
    /**
     * 检查是否启用命令成功消息
     */
    fun isCommandSuccessMessageEnabled(): Boolean {
        return config.getBoolean("messages.command_success", false)
    }
    
    /**
     * 检查是否启用传送成功消息
     */
    fun isTeleportSuccessMessageEnabled(): Boolean {
        return config.getBoolean("messages.teleport_success", false)
    }
    
    /**
     * 检查是否启用物品获得消息
     */
    fun isItemSuccessMessageEnabled(): Boolean {
        return config.getBoolean("messages.item_success", false)
    }
    
    /**
     * 获取默认菜单名称
     */
    fun getDefaultMenu(): String {
        return config.getString("menu.default", "main") ?: "main"
    }
} 