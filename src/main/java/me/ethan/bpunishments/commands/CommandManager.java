package me.ethan.bpunishments.commands;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.commands.impl.HistoryCommand;
import me.ethan.bpunishments.commands.impl.ban.BanCommand;
import me.ethan.bpunishments.commands.impl.ban.TempBanCommand;
import me.ethan.bpunishments.commands.impl.ban.UnBanCommand;
import me.ethan.bpunishments.commands.impl.blacklist.BlacklistCommand;
import me.ethan.bpunishments.commands.impl.blacklist.UnBlacklistCommand;
import me.ethan.bpunishments.commands.impl.kick.KickCommand;
import me.ethan.bpunishments.commands.impl.mute.MuteCommand;
import me.ethan.bpunishments.commands.impl.mute.TempMuteCommand;
import me.ethan.bpunishments.commands.impl.mute.UnMuteCommand;
import me.ethan.bpunishments.commands.impl.warn.UnWarnCommand;
import me.ethan.bpunishments.commands.impl.warn.WarnCommand;
import me.ethan.bpunishments.utils.command.CommandFramework;

import java.util.Arrays;

//Converted CommandManager to a Record completely forgot about the Java 16 Addition :O
public record CommandManager(bPunishments plugin, CommandFramework framework) {


    public CommandManager(bPunishments plugin, CommandFramework framework) {
        this.plugin = plugin;
        this.framework = framework;
        registerCommands();
    }

    private void registerCommands() {
        Arrays.asList(new BanCommand(), new TempBanCommand(), new UnBanCommand(),
                      new MuteCommand(), new TempMuteCommand(), new UnMuteCommand(),
                      new BlacklistCommand(), new UnBlacklistCommand(), new KickCommand(),
                      new WarnCommand(), new UnWarnCommand(), new HistoryCommand()
        ).forEach(framework::registerCommands);
    }

}
