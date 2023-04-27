package plugin.xbanchatplugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.xbanchatplugin.Chat.ChatBan;
import plugin.xbanchatplugin.Command.XBC_Command;

import java.util.Objects;
import java.util.logging.Logger;

public final class XBanChatPlugin extends JavaPlugin {
    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // 初始化插件
        Logger logger;
        logger = this.getLogger();
        logger.info(ChatColor.GREEN + "§lXChatBAN 已经被加载！");
        logger.info(ChatColor.GOLD + "§lPOWERED BY 小叮当 | 作者QQ：2683599719");
        logger.info(ChatColor.GREEN + "§l配置文件已经加载完毕！");

        // 注册类
        this.getServer().getPluginManager().registerEvents(new ChatBan(), this);
        Objects.requireNonNull(this.getCommand("xbc")).setExecutor(new XBC_Command());
        Objects.requireNonNull(getCommand("xbc")).setTabCompleter(new XBC_Command());

        // 生成默认配置文件
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // 卸载插件
        Logger logger = this.getLogger();
        logger.warning(ChatColor.RED + "XChatBAN 已经被服务器卸载");
        logger.info(ChatColor.GREEN + "POWERED BY 小叮当");
    }
}
