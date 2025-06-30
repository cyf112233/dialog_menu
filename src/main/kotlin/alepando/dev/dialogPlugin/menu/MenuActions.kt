package alepando.dev.dialogPlugin.menu

import alepando.dev.dialogPlugin.DialogPlugin
import alepando.dev.dialogPlugin.utils.Logger
import alepando.dev.dialogapi.executor.CustomKeyRegistry
import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.packets.reader.InputReader
import alepando.dev.dialogapi.packets.reader.types.PlayerReturnValueReader
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ConcurrentHashMap

// 保留旧的动作实现用于兼容性（但不再使用）
object MessageAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        player.sendMessage("§c此动作已弃用，请使用新的菜单系统")
    }
}

object CommandAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        player.sendMessage("§c此动作已弃用，请使用新的菜单系统")
    }
}

object TeleportAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        player.sendMessage("§c此动作已弃用，请使用新的菜单系统")
    }
}

object GiveItemAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        player.sendMessage("§c此动作已弃用，请使用新的菜单系统")
    }
}

object OpenMenuAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        player.sendMessage("§c此动作已弃用，请使用新的菜单系统")
    }
} 