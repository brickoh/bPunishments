package me.ethan.bpunishments.commands.impl;

import me.ethan.bpunishments.menus.HistoryMenu;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.uuid.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HistoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player) {
            if(args.length == 0) {
                Profile profile = Profile.getProfiles().get(player.getUniqueId());
                new HistoryMenu(profile).openMenu(player);
            }

            if(args.length == 1) {
                UUID uuid = UUIDUtils.getUUID(args[0]);
                if(uuid == null) {
                    player.sendMessage(ChatColor.RED + "That player does not exist!");
                }
                Profile profile = new Profile(uuid);
                new HistoryMenu(profile).openMenu(player);
            }

        }

        return false;
    }
}
