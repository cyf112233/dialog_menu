package alepando.dev.dialogPlugin.command

import alepando.dev.dialogPlugin.DialogPlugin
import alepando.dev.dialogPlugin.utils.Logger
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: DialogPlugin) : CommandExecutor {
    
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("dialogplugin.reload")) {
            sender.sendMessage("§c你没有权限使用此命令！")
            return true
        }
        
        try {
            sender.sendMessage("§a正在重载菜单配置...")
            
            // 清理已注册的Custom Key
            Logger.info("清理已注册的Custom Key...")
            alepando.dev.dialogapi.executor.CustomKeyRegistry.clearAll()
            
            // 重新加载菜单管理器
            Logger.info("重新加载菜单管理器...")
            plugin.menuManager.reload()
            
            // 重新加载配置
            Logger.info("重新加载配置文件...")
            plugin.configManager.reload()
            
            sender.sendMessage("§a菜单配置重载完成！")
            sender.sendMessage("§a可用菜单: ${plugin.menuManager.getAllMenuNames().joinToString(", ")}")
            
            Logger.info("菜单配置重载完成，执行者: ${sender.name}")
            
        } catch (e: Exception) {
            sender.sendMessage("§c重载菜单配置时出错: ${e.message}")
            Logger.severe("重载菜单配置时出错: ${e.message}")
            e.printStackTrace()
        }
        
        return true
    }
} 