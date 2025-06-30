package alepando.dev.dialogPlugin.command

import alepando.dev.dialogPlugin.DialogPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class HelpCommand(private val plugin: DialogPlugin) : CommandExecutor {
    
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("§6=== DialogPlugin 帮助 ===")
        sender.sendMessage("")
        sender.sendMessage("§e可用命令:")
        sender.sendMessage("§7/ui <菜单名> §f- 打开指定的菜单")
        sender.sendMessage("§7/ui §f- 打开默认菜单 (如果存在default.json)")
        sender.sendMessage("§7/uitest §f- 测试对话框功能")
        
        if (sender.hasPermission("dialogplugin.reload")) {
            sender.sendMessage("§7/uireload §f- 重载菜单配置")
        }
        
        sender.sendMessage("")
        sender.sendMessage("§e可用菜单:")
        val menuNames = plugin.menuManager.getAllMenuNames()
        if (menuNames.isNotEmpty()) {
            menuNames.forEach { menuName ->
                val prefix = if (menuName == "default") "§a★ " else "§7- "
                val suffix = if (menuName == "default") " §7(默认菜单)" else ""
                sender.sendMessage("$prefix§f$menuName$suffix")
            }
        } else {
            sender.sendMessage("§7- §c暂无可用菜单")
        }
        
        sender.sendMessage("")
        sender.sendMessage("§e权限:")
        sender.sendMessage("§7dialogplugin.reload §f- 重载菜单配置权限")
        sender.sendMessage("")
        sender.sendMessage("§6========================")
        
        return true
    }
} 