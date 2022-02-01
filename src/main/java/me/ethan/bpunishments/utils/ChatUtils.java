package me.ethan.bpunishments.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    public static String format(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> format(ArrayList<String> messages) {
        ArrayList<String> toReturn = new ArrayList<>();
        messages.forEach(message -> toReturn.add(format(message)));
        return toReturn;
    }

    public static BaseComponent hoverMessage(String message, String hoverMessage) {
        TextComponent mainComponent = new TextComponent(format(message));
        mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(format(hoverMessage)).create()));
        return mainComponent;
    }


}
