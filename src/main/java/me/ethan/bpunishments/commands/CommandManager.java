package me.ethan.bpunishments.commands;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.commands.impl.HistoryCommand;
import me.ethan.bpunishments.commands.impl.ban.BanCommand;
import me.ethan.bpunishments.commands.impl.ban.TempBanCommand;
import me.ethan.bpunishments.commands.impl.ban.UnBanCommand;

//Converted CommandManager to a Record completely forgot about the Java 16 Addition :O
public record CommandManager(bPunishments plugin) {

    public CommandManager(bPunishments plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        plugin.getCommand("ban").setExecutor(new BanCommand());
        plugin.getCommand("tban").setExecutor(new TempBanCommand());
        plugin.getCommand("unban").setExecutor(new UnBanCommand());
        plugin.getCommand("history").setExecutor(new HistoryCommand());
    }

}
