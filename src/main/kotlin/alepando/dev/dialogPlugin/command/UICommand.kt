package alepando.dev.dialogPlugin.command

import alepando.dev.dialogPlugin.DialogPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UICommand(private val plugin: DialogPlugin) : CommandExecutor {
    
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("此命令只能由玩家执行！")
            return true
        }
        
        if (args.isEmpty()) {
            // 检查是否存在default菜单
            val defaultMenu = "default"
            if (plugin.menuManager.getAllMenuNames().contains(defaultMenu)) {
                // 如果存在default菜单，直接打开
                try {
                    plugin.menuManager.openMenu(sender, defaultMenu)
                    return true
                } catch (e: Exception) {
                    sender.sendMessage("打开默认菜单时出错: ${e.message}")
                    plugin.logger.severe("打开默认菜单时出错: ${e.message}")
                }
            }
            
            // 如果不存在default菜单，显示帮助信息
            sender.sendMessage("§6=== 菜单系统帮助 ===")
            sender.sendMessage("")
            sender.sendMessage("§e用法: §7/ui <菜单名>")
            sender.sendMessage("§7或直接使用 §e/ui §7打开默认菜单")
            sender.sendMessage("")
            sender.sendMessage("§e可用菜单:")
            val menuNames = plugin.menuManager.getAllMenuNames()
            if (menuNames.isNotEmpty()) {
                menuNames.forEach { menuName ->
                    val prefix = if (menuName == "default") "§a★ " else "§7- "
                    sender.sendMessage("$prefix§f$menuName")
                }
            } else {
                sender.sendMessage("§7- §c暂无可用菜单")
            }
            sender.sendMessage("")
            sender.sendMessage("§e其他命令:")
            sender.sendMessage("§7/uihelp §f- 显示完整帮助")
            if (sender.hasPermission("dialogplugin.reload")) {
                sender.sendMessage("§7/uireload §f- 重载菜单配置")
            }
            sender.sendMessage("§6====================")
            return true
        }
        
        val menuName = args[0]
        
        try {
            plugin.menuManager.openMenu(sender, menuName)
        } catch (e: Exception) {
            sender.sendMessage("打开菜单时出错: ${e.message}")
            plugin.logger.severe("打开菜单 $menuName 时出错: ${e.message}")
        }
        
        return true
    }
} 