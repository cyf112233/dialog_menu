package alepando.dev.dialogPlugin.menu

import alepando.dev.dialogPlugin.DialogPlugin
import alepando.dev.dialogPlugin.utils.Logger
import alepando.dev.dialogapi.executor.CustomKeyRegistry
import alepando.dev.dialogapi.executor.PlayerOpener.openDialog
import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.packets.reader.InputReader
import alepando.dev.dialogapi.packets.reader.types.PlayerReturnValueReader
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.concurrent.ConcurrentHashMap

class MenuManager(private val plugin: DialogPlugin) {
    
    private val menus = ConcurrentHashMap<String, MenuConfig>()
    private val menuDialogs = ConcurrentHashMap<String, alepando.dev.dialogapi.types.MultiActionDialog>()
    private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    
    init {
        loadMenus()
        // 移除预注册菜单动作，因为现在每个按钮都有自己唯一的动作
        Logger.info("菜单管理器初始化完成，使用动态按钮动作注册")
    }
    
    /**
     * 重新加载菜单配置
     */
    fun reload() {
        try {
            Logger.info("开始重新加载菜单配置...")
            
            // 清空现有菜单
            menus.clear()
            menuDialogs.clear()
            
            // 重新加载菜单
            loadMenus()
            
            Logger.info("菜单配置重新加载完成，共加载 ${menus.size} 个菜单")
        } catch (e: Exception) {
            Logger.severe("重新加载菜单配置时出错: ${e.message}")
            throw e
        }
    }
    
    private fun loadMenus() {
        try {
            val menuDir = File(plugin.dataFolder, "CD")
            if (!menuDir.exists()) {
                // 从jar中复制默认菜单
                copyDefaultMenus(menuDir)
            }
            
            val menuFiles = menuDir.listFiles { file -> file.extension == "json" }
            menuFiles?.forEach { file ->
                try {
                    val menuConfig = objectMapper.readValue<MenuConfig>(file)
                    val menuName = file.nameWithoutExtension
                    menus[menuName] = menuConfig
                    
                    // 预构建菜单对话框
                    val dialog = buildMenuDialog(menuConfig, menuName)
                    menuDialogs[menuName] = dialog
                    
                    Logger.Menu.loaded(menuName, file.absolutePath)
                } catch (e: Exception) {
                    Logger.Menu.loadError(file.name, e.message ?: "未知错误")
                }
            }
            
            Logger.Menu.loadComplete(menus.size)
        } catch (e: Exception) {
            Logger.Menu.exceptionContext("MenuManager", "加载菜单", e)
        }
    }
    
    private fun copyDefaultMenus(menuDir: File) {
        try {
            menuDir.mkdirs()
            
            // 复制默认菜单文件
            val defaultMenus = listOf("default.json", "example.json", "submenu.json", "test_buttons.json")
            defaultMenus.forEach { fileName ->
                val resource = plugin.getResource("CD/$fileName")
                if (resource != null) {
                    val file = File(menuDir, fileName)
                    resource.use { input ->
                        file.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    Logger.Menu.copiedDefaultMenu(fileName)
                }
            }
        } catch (e: Exception) {
            Logger.Menu.exceptionContext("MenuManager", "复制默认菜单", e)
        }
    }
    
    private fun buildMenuDialog(menuConfig: MenuConfig, menuName: String): alepando.dev.dialogapi.types.MultiActionDialog {
        // 这里预构建菜单对话框，包含所有按钮和动作
        return MenuDialogBuilder.buildDialog(menuConfig, menuName)
    }
    
    fun getMenu(menuName: String): MenuConfig? {
        return menus[menuName]
    }
    
    fun getAllMenuNames(): List<String> {
        return menus.keys.toList()
    }
    
    fun openMenu(player: Player, menuName: String) {
        try {
            val menuConfig = menus[menuName] ?: run {
                player.sendMessage("§c菜单 '$menuName' 不存在")
                return
            }
            
            val dialog = menuDialogs[menuName] ?: run {
                player.sendMessage("§c菜单 '$menuName' 构建失败")
                return
            }
            
            // 直接打开对话框，不需要存储玩家数据
            player.openDialog(dialog)
            
            Logger.Menu.openMenu(player.name, menuName)
        } catch (e: Exception) {
            Logger.Menu.exception(player.name, "打开菜单 $menuName", e)
            player.sendMessage("§c打开菜单时出错: ${e.message}")
        }
    }
} 