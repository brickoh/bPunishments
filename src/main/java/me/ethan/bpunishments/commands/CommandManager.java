package me.ethan.bpunishments.commands;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.commands.impl.BanCommand;
import me.ethan.bpunishments.commands.impl.UnBanCommand;

public class CommandManager {

    private final bPunishments plugin;

    public CommandManager(bPunishments plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        plugin.getCommand("ban").setExecutor(new BanCommand());
        plugin.getCommand("unban").setExecutor(new UnBanCommand());
    }

    private void registerTabCompletes() {
        //plugin.getCommand("ban").setExecutor(new BanCommand());
    }

}
