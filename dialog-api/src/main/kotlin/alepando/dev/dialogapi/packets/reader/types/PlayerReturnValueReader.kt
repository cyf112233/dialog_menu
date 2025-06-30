package alepando.dev.dialogapi.packets.reader.types

import alepando.dev.dialogapi.packets.reader.InputReader
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player

/**
 * A simple implementation of [InputReader] that primarily serves as an example
 * or a basic handler for dialog interactions where the returned value is of interest.
 * This reader silently handles the returned value without sending any messages to the player.
 *
 * It can be used for custom dialog components that return simple data like strings, numbers, or booleans
 * when no more complex client-side data processing is needed by the [InputReader] itself.
 */
object PlayerReturnValueReader: InputReader {
    /**
     * Handles the received packet silently without sending any messages to the player.
     *
     * @param player The Bukkit [Player] who interacted with the dialog.
     * @param value The value extracted from the packet's payload.
     */
    override fun task(player: Player, value: Any?) {
        // 静默处理，不输出任何消息
    }
}