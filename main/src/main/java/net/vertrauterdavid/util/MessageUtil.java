package net.vertrauterdavid.util;

import net.md_5.bungee.api.ChatColor;
import net.vertrauterdavid.EventCore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    public static String get(String key) {
        return translateColorCodes(EventCore.getInstance().getConfig().getString(key, ""));
    }

    public static String getPrefix() {
        return translateColorCodes(EventCore.getInstance().getConfig().getString("Messages.Prefix", ""));
    }

    public static String translateColorCodes(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }

}
