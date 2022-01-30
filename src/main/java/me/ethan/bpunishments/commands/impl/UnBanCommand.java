package me.ethan.bpunishments.commands.impl;

import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
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

public class UnBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission("punishments.commands.ban")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /unban <player> <reason> [-s]");
                }

                UUID uuid = UUIDUtils.getUUID(args[0]);
                if (uuid == null) {
                    player.sendMessage(ChatColor.RED + "That player does not exist!");
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                    Profile targetP = new Profile(target.getUniqueId());
                    boolean silent = false;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }
                    String reason = sb.toString().trim();

                    if (reason.contains("-s")) {
                        reason = reason.replace("-s", "");
                        silent = true;
                        player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_REVOKED)
                                .replace("{offender}", args[0])
                                .replace("{punishment}", "unbanned")
                                .replace("{staff}", player.getName()));
                    } else {
                        player.sendMessage(ChatUtils.format(Feedback.GLOBAL_PUNISHMENT_REVOKED)
                                .replace("{offender}", args[0])
                                .replace("{punishment}", "unbanned")
                                .replace("{staff}", player.getName()));
                    }

                    for(Punishment punishment : targetP.getBans()) {
                        if(punishment.isActive()) {
                            punishment.setActive(false);
                        }
                    }
                    Punishment punishment = new Punishment(Punishment.getNewID(), targetP.getUuid(),
                            player.getUniqueId(), PunishmentType.UNBAN, reason, 10L, silent, false);
                    punishment.createPunishment();
                    targetP.setBanned(false);;
                    targetP.save();
                }
                return true;
            }
        }
        return true;
    }
}
