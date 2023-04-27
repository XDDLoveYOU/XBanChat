package plugin.xbanchatplugin.Chat;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class ChatBan implements Listener {

    public static void onCommandRun(String s) {
        Plugin config = plugin.xbanchatplugin.XBanChatPlugin.getProvidingPlugin(plugin.xbanchatplugin.XBanChatPlugin.class);
        List<String> list = config.getConfig().getStringList("ChatBanList");
        list.add(s);
        config.getConfig().set("ChatBanList", list);
    }

    public static void onCommandRemove(String s) {
        Plugin config = plugin.xbanchatplugin.XBanChatPlugin.getProvidingPlugin(plugin.xbanchatplugin.XBanChatPlugin.class);
        List<String> list = config.getConfig().getStringList("ChatBanList");
        list.remove(s);
        config.getConfig().set("ChatBanList", list);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String PIX = "§4§l服务器 §e>> §r";
        // 定义事件变量
        Player player = event.getPlayer();
        String PlayerName = event.getPlayer().getName();
        Location location = player.getLocation();

        // 获取配置文件
        Plugin config = plugin.xbanchatplugin.XBanChatPlugin.getProvidingPlugin(plugin.xbanchatplugin.XBanChatPlugin.class);
        boolean disabledOFF = config.getConfig().getBoolean("disabled");

        // 判断配置文件开关
        if (!disabledOFF) {
            // 遍历列表
            for (String s : config.getConfig().getStringList("ChatBanList")) {
                // 判断聊天信息
                if (event.getMessage().contains(s)) {
                    player.sendMessage(PIX + ChatColor.BLUE + PlayerName + ChatColor.RED + " 您发送的信息不符合规范，已被拦截！");
                    player.playSound(location, Sound.BLOCK_NOTE_BLOCK_HARP, 5, 5);
                    event.setCancelled(true);
                }

            }

        }
    }
}
