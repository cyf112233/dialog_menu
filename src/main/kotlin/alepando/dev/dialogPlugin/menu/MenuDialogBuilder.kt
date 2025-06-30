package alepando.dev.dialogPlugin.menu

import alepando.dev.dialogPlugin.DialogPlugin
import alepando.dev.dialogPlugin.utils.Logger
import alepando.dev.dialogapi.body.types.builders.ItemDialogBodyBuilder
import alepando.dev.dialogapi.executor.CustomKeyRegistry
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.button.data.Action
import alepando.dev.dialogapi.factory.button.data.ButtonDataBuilder
import alepando.dev.dialogapi.factory.data.DialogDataBuilder
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.packets.reader.types.PlayerReturnValueReader
import alepando.dev.dialogapi.types.builders.MultiActionDialogBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

object MenuDialogBuilder {
    
    private val keyCounter = AtomicInteger(0)
    
    // 网格配置
    private val gridConfig = GridConfig(
        canvasWidth = 400,    // 画布宽度
        canvasHeight = 300,   // 画布高度
        buttonWidth = 100,    // 默认按钮宽度
        buttonHeight = 25,    // 默认按钮高度
        margin = 10           // 按钮间距
    )
    
    fun buildDialog(menuConfig: MenuConfig, menuName: String): alepando.dev.dialogapi.types.MultiActionDialog {
        try {
            Logger.Builder.startBuild("预构建", menuConfig.title)
            
            val dialogData = buildDialogData(menuConfig)
            val dialog = buildDialog(menuConfig, dialogData, menuName)
            
            Logger.Builder.buildComplete("预构建", menuConfig.title)
            return dialog
        } catch (e: Exception) {
            Logger.Builder.buildError("预构建", menuConfig.title, e.message ?: "未知错误")
            throw e
        }
    }
    
    private fun buildDialogData(menuConfig: MenuConfig): alepando.dev.dialogapi.factory.data.DialogData {
        val builder = DialogDataBuilder()
            .title(Component.text(menuConfig.title))
            .externalTitle(Component.text(menuConfig.externalTitle))
            .canCloseWithEscape(menuConfig.canCloseWithEscape)
        
        // 添加内容体
        menuConfig.body.forEach { bodyConfig ->
            when (bodyConfig.type.uppercase()) {
                "ITEM" -> {
                    val material = bodyConfig.itemMaterial?.let { Material.valueOf(it.uppercase()) } ?: Material.STONE
                    val item = ItemStack(material)
                    
                    val itemBody = ItemDialogBodyBuilder()
                        .item(item)
                        .height(bodyConfig.height)
                        .width(bodyConfig.width)
                        .description(bodyConfig.content, bodyConfig.width)
                        .showDecorations(true)
                        .showTooltip(false)
                        .build()
                    
                    builder.addBody(itemBody)
                }
            }
        }
        
        return builder.build()
    }
    
    private fun buildDialog(menuConfig: MenuConfig, dialogData: alepando.dev.dialogapi.factory.data.DialogData, menuName: String): alepando.dev.dialogapi.types.MultiActionDialog {
        val builder = MultiActionDialogBuilder()
            .data(dialogData)
        
        // 计算按钮布局
        val buttonLayout = calculateButtonLayout(menuConfig.buttons)
        
        // 设置列数
        builder.columns(buttonLayout.columns)
        
        // 按行列顺序添加按钮
        buttonLayout.sortedButtons.forEach { buttonConfig ->
            val button = buildButton(buttonConfig, menuName)
            builder.addButton(button)
        }
        
        return builder.build()
    }
    
    private fun calculateButtonLayout(buttons: List<ButtonConfig>): ButtonLayout {
        if (buttons.isEmpty()) {
            return ButtonLayout(1, emptyList())
        }
        
        // 计算网格布局
        val gridButtons = buttons.map { buttonConfig ->
            val gridPosition = calculateGridPosition(buttonConfig)
            GridButton(buttonConfig, gridPosition)
        }
        
        // 计算需要的列数
        val maxColumn = gridButtons.maxOfOrNull { it.gridPosition.column } ?: 0
        val columns = maxColumn + 1
        
        // 按行列排序按钮
        val sortedButtons = gridButtons
            .sortedWith(compareBy({ it.gridPosition.row }, { it.gridPosition.column }))
            .map { it.buttonConfig }
        
        Logger.Builder.layoutCalculated(columns, sortedButtons.size)
        return ButtonLayout(columns, sortedButtons)
    }
    
    private fun calculateGridPosition(buttonConfig: ButtonConfig): GridPosition {
        val x = buttonConfig.x
        val y = buttonConfig.y
        val width = buttonConfig.width
        val height = buttonConfig.height
        
        // 计算列位置（基于X坐标）
        val column = (x / (gridConfig.buttonWidth + gridConfig.margin)).toInt()
        
        // 计算行位置（基于Y坐标）
        val row = (y / (gridConfig.buttonHeight + gridConfig.margin)).toInt()
        
        return GridPosition(row, column)
    }
    
    private fun buildButton(buttonConfig: ButtonConfig, menuName: String): Button {
        val buttonData = ButtonDataBuilder()
            .label(Component.text(buttonConfig.label))
            .width(buttonConfig.width)
            .build()
        
        val action = createButtonAction(buttonConfig.action, menuName)
        return Button(buttonData, Optional.of(action))
    }
    
    private fun createButtonAction(buttonAction: ButtonAction, menuName: String): Action {
        val actionType = buttonAction.type.uppercase()
        
        // 为每个按钮创建唯一的ResourceLocation
        val counter = keyCounter.incrementAndGet()
        val namespace = "menu"
        val path = "${actionType.lowercase()}_${menuName}_${counter}"
        val resourceLocation = ResourceLocation(namespace, path)
        
        // 创建带有数据的自定义动作
        val customAction = createCustomActionWithData(buttonAction)
        
        // 注册自定义动作
        try {
            CustomKeyRegistry.register(resourceLocation, customAction, PlayerReturnValueReader)
            Logger.debug("注册按钮动作: $resourceLocation -> ${buttonAction.data}")
        } catch (e: IllegalStateException) {
            Logger.warning("注册按钮动作失败: ${e.message}")
        }
        
        return Action(resourceLocation)
    }
    
    private fun createCustomActionWithData(buttonAction: ButtonAction): CustomAction {
        return object : CustomAction() {
            override fun task(player: Player, plugin: Plugin) {
                try {
                    val actionType = buttonAction.type.uppercase()
                    val dialogPlugin = plugin as? DialogPlugin
                    val configManager = dialogPlugin?.configManager
                    
                    Logger.Actions.executeAction(player.name, actionType, buttonAction.data)
                    
                    when (actionType) {
                        "MESSAGE" -> {
                            val message = buttonAction.data["message"] as? String ?: "未知消息"
                            player.sendMessage(message)
                            Logger.Actions.actionSuccess(player.name, "MESSAGE", "发送消息: $message")
                        }
                        "COMMAND" -> {
                            val command = buttonAction.data["command"] as? String ?: return
                            val asConsole = buttonAction.data["asConsole"] as? Boolean ?: false
                            
                            try {
                                if (asConsole) {
                                    // 以控制台身份执行命令
                                    plugin.server.dispatchCommand(plugin.server.consoleSender, command)
                                } else {
                                    // 以玩家身份执行命令
                                    player.performCommand(command)
                                }
                                if (configManager?.isCommandSuccessMessageEnabled() == true) {
                                    player.sendMessage("§a命令执行成功: $command")
                                }
                                Logger.Actions.actionSuccess(player.name, "COMMAND", "执行命令: $command")
                            } catch (e: Exception) {
                                if (configManager?.isFailureMessageEnabled() == true) {
                                    player.sendMessage("执行命令时出错: ${e.message}")
                                }
                                Logger.Actions.actionFailed(player.name, "COMMAND", "执行命令 '$command' 时出错: ${e.message}")
                            }
                        }
                        "TELEPORT" -> {
                            val world = buttonAction.data["world"] as? String ?: return
                            val x = buttonAction.data["x"] as? Double ?: return
                            val y = buttonAction.data["y"] as? Double ?: return
                            val z = buttonAction.data["z"] as? Double ?: return
                            val targetWorld = plugin.server.getWorld(world) ?: return
                            
                            try {
                                player.teleport(org.bukkit.Location(targetWorld, x, y, z))
                                if (configManager?.isTeleportSuccessMessageEnabled() == true) {
                                    player.sendMessage("§a传送成功到 $world($x,$y,$z)")
                                }
                                Logger.Actions.actionSuccess(player.name, "TELEPORT", "传送到 $world($x,$y,$z)")
                            } catch (e: Exception) {
                                if (configManager?.isFailureMessageEnabled() == true) {
                                    player.sendMessage("传送时出错: ${e.message}")
                                }
                                Logger.Actions.actionFailed(player.name, "TELEPORT", "传送玩家到 $world($x,$y,$z) 时出错: ${e.message}")
                            }
                        }
                        "GIVE_ITEM" -> {
                            val material = buttonAction.data["material"] as? String ?: return
                            val amount = buttonAction.data["amount"] as? Int ?: 1
                            
                            try {
                                val item = ItemStack(Material.valueOf(material.uppercase()), amount)
                                player.inventory.addItem(item)
                                if (configManager?.isItemSuccessMessageEnabled() == true) {
                                    player.sendMessage("§a已获得 $material x$amount")
                                }
                                Logger.Actions.actionSuccess(player.name, "GIVE_ITEM", "获得 $material x$amount")
                            } catch (e: Exception) {
                                if (configManager?.isFailureMessageEnabled() == true) {
                                    player.sendMessage("给予物品时出错: ${e.message}")
                                }
                                Logger.Actions.actionFailed(player.name, "GIVE_ITEM", "给予物品 $material x$amount 时出错: ${e.message}")
                            }
                        }
                        "OPEN_MENU" -> {
                            val menuName = buttonAction.data["menu"] as? String ?: return
                            val dialogPlugin = plugin as? DialogPlugin ?: return
                            val menuManager = dialogPlugin.menuManager
                            
                            try {
                                menuManager.openMenu(player, menuName)
                                Logger.Actions.actionSuccess(player.name, "OPEN_MENU", "打开菜单: $menuName")
                            } catch (e: Exception) {
                                if (configManager?.isFailureMessageEnabled() == true) {
                                    player.sendMessage("打开菜单时出错: ${e.message}")
                                }
                                Logger.Actions.actionFailed(player.name, "OPEN_MENU", "打开菜单 $menuName 时出错: ${e.message}")
                            }
                        }
                        else -> {
                            if (configManager?.isFailureMessageEnabled() == true) {
                                player.sendMessage("§c未知的动作类型: $actionType")
                            }
                            Logger.Actions.actionFailed(player.name, actionType, "未知的动作类型")
                        }
                    }
                } catch (e: Exception) {
                    val dialogPlugin = plugin as? DialogPlugin
                    val configManager = dialogPlugin?.configManager
                    
                    if (configManager?.isFailureMessageEnabled() == true) {
                        player.sendMessage("§c动作执行时出错: ${e.message}")
                    }
                    Logger.Menu.exception(player.name, "动作执行", e)
                }
            }
        }
    }
    
    // 数据类
    private data class GridConfig(
        val canvasWidth: Int,
        val canvasHeight: Int,
        val buttonWidth: Int,
        val buttonHeight: Int,
        val margin: Int
    )
    
    private data class GridPosition(
        val row: Int,
        val column: Int
    )
    
    private data class GridButton(
        val buttonConfig: ButtonConfig,
        val gridPosition: GridPosition
    )
    
    private data class ButtonLayout(
        val columns: Int,
        val sortedButtons: List<ButtonConfig>
    )
} 