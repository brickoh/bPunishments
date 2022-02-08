package me.ethan.bpunishments.commands.impl;

import me.ethan.bpunishments.menus.HistoryMenu;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.utils.command.CommandArgs;
import me.ethan.bpunishments.utils.command.annotation.Command;
import me.ethan.bpunishments.utils.uuid.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HistoryCommand {

    @Command(name = "history", aliases = {"check", "c", "his"}, permission = "bpunishments.commands.history", inGameOnly = true)
    public void execute(CommandArgs args) {
        Player player = args.getPlayer();
        Profile profile;
        if(args.getArgs().length == 0) {
            profile = Profile.getProfiles().get(player.getUniqueId());
            new HistoryMenu(profile).openMenu(player);
        }

        if(args.getArgs().length == 1) {
            UUID uuid = UUIDUtils.getUUID(args.getArgs(0));
            if (uuid == null) {
                player.sendMessage(ChatColor.RED + "UUID came back null, is the IGN correct? (" + args.getArgs(0) + ")");
                return;
            }
            profile = new Profile(uuid);
            new HistoryMenu(profile).openMenu(player);
        }

    }

}
