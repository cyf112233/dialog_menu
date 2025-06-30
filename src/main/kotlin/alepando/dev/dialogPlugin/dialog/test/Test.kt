package alepando.dev.dialogPlugin.dialog.test

import alepando.dev.dialogPlugin.DialogPlugin
import alepando.dev.dialogapi.body.types.PlainMessageDialogBody
import alepando.dev.dialogapi.body.types.builders.ItemDialogBodyBuilder
import alepando.dev.dialogapi.executor.CustomKeyRegistry
import alepando.dev.dialogapi.executor.PlayerOpener.openDialog
import alepando.dev.dialogapi.factory.actions.KillPlayerAction
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.button.data.Action
import alepando.dev.dialogapi.factory.button.data.ButtonDataBuilder
import alepando.dev.dialogapi.factory.data.DialogDataBuilder
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.input.options.MultilineOptions
import alepando.dev.dialogapi.factory.input.options.RangeInfo
import alepando.dev.dialogapi.factory.input.types.builders.NumberRangeInputBuilder
import alepando.dev.dialogapi.factory.input.types.builders.TextInputBuilder
import alepando.dev.dialogapi.packets.reader.types.PlayerReturnValueReader
import alepando.dev.dialogapi.types.builders.MultiActionDialogBuilder
import net.kyori.adventure.text.Component
import net.minecraft.network.chat.ClickEvent.Custom
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Test(
    private val player: Player,
    private val plugin: DialogPlugin
) {

    fun testDialog(){

        val killPlayerNamespace = "dialog"
        val killPlayerPath = "damage_player"
        val killPlayerKey = ResourceLocation(killPlayerNamespace, killPlayerPath)
        try {
            CustomKeyRegistry.register(
                killPlayerKey,
                KillPlayerAction,
                PlayerReturnValueReader
            )
        } catch (e: IllegalStateException) {
            player.sendMessage("Note: Kill player key was already registered: ${e.message}")
        }


        val buttonData = ButtonDataBuilder()
            .label(Component.text("test"))
            .width(100)
            .build()

        val exitButtonData = ButtonDataBuilder()
            .label(Component.text("exit"))
            .width(80)
            .build()

        val action = Action(killPlayerKey)

        val testButton = Button(buttonData, Optional.of(action))

        val exitButton = Button(exitButtonData)

        val booleanInput = NumberRangeInputBuilder()
            .label(Component.text("Input"))
            .width(150)
            .rangeInfo(RangeInfo(1.0f,10.0f))
            .labelFormat("")
            .build()

        val stringInput = TextInputBuilder()
            .label(Component.text("Input"))
            .width(256)
            .initial("")
            .labelVisible(true)
            .maxLength(300)
            .multiline(MultilineOptions(10,20))
            .build()

        val itemBody =  ItemDialogBodyBuilder()
            .item(ItemStack(Material.LAPIS_LAZULI))
            .height(20)
            .width(20)
            .description("item_test",100)
            .showDecorations(true)
            .showTooltip(false)
            .build()

        val dialogBody = PlainMessageDialogBody(100, Component.text("body"))

        val dialogData = DialogDataBuilder()
            .title(Component.text("Test Menu"))
            .externalTitle(Component.text("Menu Test"))
            .canCloseWithEscape(true)
            .addBody(dialogBody)
            .addBody(itemBody)
            .addBody(dialogBody)
            //.addInput(booleanInput)
            .addInput(stringInput)
            .build()

        val confirmationDialog = MultiActionDialogBuilder()
            .data(dialogData)
            .columns(1)
            .exitButton(exitButton)
            .addButton(testButton)
            .addButton(testButton)
            .build()

        player.openDialog(confirmationDialog)
    }
}