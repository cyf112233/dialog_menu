package alepando.dev.dialogPlugin.menu

data class MenuConfig(
    val title: String,
    val externalTitle: String,
    val canCloseWithEscape: Boolean = true,
    val pause: Boolean = false,
    val buttons: List<ButtonConfig> = emptyList(),
    val body: List<BodyConfig> = emptyList()
)

data class ButtonConfig(
    val label: String,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val action: ButtonAction
)

data class ButtonAction(
    val type: String, // MESSAGE, COMMAND, TELEPORT, GIVE_ITEM, CLOSE, OPEN_MENU, etc.
    val data: Map<String, Any>
)

data class BodyConfig(
    val type: String, // TEXT, ITEM
    val content: String,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val itemMaterial: String? = null,
    val itemName: String? = null,
    val itemLore: List<String>? = null
) 