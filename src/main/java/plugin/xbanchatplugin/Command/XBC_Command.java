package plugin.xbanchatplugin.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.xbanchatplugin.XBanChatPlugin;
import plugin.xbanchatplugin.Chat.ChatBan;

import java.util.ArrayList;
import java.util.List;

public class XBC_Command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arg) {
        // 指令前缀定义
        String PIX = "§a§l[§4§lX§b§lBanChat§a§l]§r: ";

        // 获取配置文件
        Plugin config = plugin.xbanchatplugin.XBanChatPlugin.getProvidingPlugin(plugin.xbanchatplugin.XBanChatPlugin.class);

        // 定义帮助列表
        String HelpList =
                "\n§e=======§a帮助§e========\n" +
                        "§b/xbc reload 重载配置文件\n" +
                        "§b/xbc help 显示此帮助页面\n" +
                        "§b/xbc list 查看屏蔽词列表\n" +
                        "§b/xbc add [词条] 添加屏蔽关键词\n" +
                        "§b/xbc remove [词条] 删除一个屏蔽关键词\n" +
                        "§b/xbc on 开启本插件\n" +
                        "§b/xbc off 关闭本插件\n" +
                        "§a§lPluginVersion V1.1.0\n" +
                        "§e==================";

        // 判断主命令
        if (command.getName().equals("xbc")) {
                // 判断指令
                if (arg.length == 0) {
                    commandSender.sendMessage(PIX + ChatColor.GOLD + "如果需要帮助，请输入/xbc help 获取帮助！");
                } else if (arg.length == 1) {
                    // 判断二级指令
                    String arg_to_string = arg[0];
                    switch (arg_to_string) {
                        case "help":
                            // 调用帮助列表 并输出
                            commandSender.sendMessage(HelpList);

                            break;

                        case "reload":
                            // 重载配置文件方法
                            XBanChatPlugin.instance.reloadConfig();
                            commandSender.sendMessage(PIX + ChatColor.GREEN + "配置文件重载完毕！");

                            break;

                        case "list":
                            commandSender.sendMessage(PIX + ChatColor.GREEN + "当前屏蔽词列表: ");
                            List<String> list = config.getConfig().getStringList("ChatBanList");
                            for (String s1 : list) {
                                if (s1 != null) {
                                    commandSender.sendMessage("- " + ChatColor.GOLD + s1);
                                }

                            }
                            break;

                        case "on":
                            boolean disabledON = config.getConfig().getBoolean("disabled");
                            // 判断是否为false
                            if (disabledON){
                                config.getConfig().set("disabled" , false);
                                commandSender.sendMessage(PIX + ChatColor.GREEN + "已成功开启本插件！");

                            }else {
                                commandSender.sendMessage(PIX + ChatColor.RED + "插件已经启用了，无需开启！");
                            }
                            break;

                        case "off":
                            boolean disabledOFF = config.getConfig().getBoolean("disabled");
                            // 判断是否为true
                            if (!disabledOFF){
                                config.getConfig().set("disabled" , true);
                                commandSender.sendMessage(PIX + ChatColor.GOLD + "已成功关闭本插件！");
                            }else {
                                commandSender.sendMessage(PIX + ChatColor.RED + "插件已经关闭，无需再次关闭！");
                            }
                            break;

                    }
                }
                // 判断二级指令三级信息
                if (arg.length == 2) {
                    String arg_to_string = arg[0];
                    // 判断add子指令
                    if (arg_to_string.equals("add")) {
                        String arg_add_string = arg[1];
                        List<String> list = config.getConfig().getStringList("ChatBanList");
                        boolean equals = false;
                        // 遍历列表
                        for (String s1 : list) {
                            if (s1.equals(arg[1])) {
                                equals = true;
                                commandSender.sendMessage(PIX + ChatColor.RED + "屏蔽列表中已经有关键字 " + ChatColor.GOLD +
                                        arg_add_string);
                            }
                        }
                        if (!equals) {
                            // 添加关键词到配置文件
                            ChatBan.onCommandRun(arg[1]);
                            config.saveConfig();

                            // 添加成功输出
                            commandSender.sendMessage(PIX + ChatColor.GREEN + "成功添加关键词 " + ChatColor.GOLD +
                                    arg_add_string);

                        }
                        // 判断remove子指令
                    } else if (arg_to_string.equals("remove")) {
                        String arg_add_string = arg[1];
                        List<String> list = config.getConfig().getStringList("ChatBanList");

                        boolean REMOVEsuccess = false;
                        for (String s1 : list) {
                            if (s1.equals(arg[1])) {
                                ChatBan.onCommandRemove(arg[1]);
                                config.saveConfig();

                                // 发送删除通知
                                commandSender.sendMessage(PIX + ChatColor.RED + "已经成功在列表中删除 " + ChatColor.GOLD +
                                        arg_add_string);

                                REMOVEsuccess = true;
                            }
                        }
                        if (!REMOVEsuccess) {
                            commandSender.sendMessage(PIX + ChatColor.RED + "屏蔽列表里没有关键词 " + ChatColor.GREEN +
                                    arg_add_string);
                        }
                    }

                    return true;
                }
            } else {
                commandSender.sendMessage(PIX + ChatColor.RED + "错误的命令！请输入/xbc help 获取帮助！");
            }
            return true;
        }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arg) {

        // 添加TAB补全列表
        if (arg.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("reload");
            list.add("list");
            list.add("add");
            list.add("remove");
            list.add("on");
            list.add("off");
            return list;
        } else if (arg.length == 2) {
            List<String> list =new ArrayList<>();
            list.add("[词条]");
            return list;
        }
        return null;
    }
}
