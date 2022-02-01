package me.ethan.bpunishments.commands.impl.ban;

import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.TimeUtils;
import me.ethan.bpunishments.utils.uuid.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class TempBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("punishments.commands.ban")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /tban <player> <time> <unit> <reason> [-s]");
                }

                UUID uuid = UUIDUtils.getUUID(args[0]);
                if (uuid == null) {
                    player.sendMessage(ChatColor.RED + "That player does not exist!");
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                    Profile targetP = new Profile(target.getUniqueId());
                    boolean silent = false;
                    String unit = args[2];
                    int duration = Integer.parseInt(args[1]);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }
                    String reason = sb.toString().trim();


                    if (reason.contains("-s")) {
                        reason = reason.replace("-s", "");
                        silent = true;
                        player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_SENT_TEMP_BAN)
                                .replace("{offender}", args[0])
                                .replace("{staff}", player.getName()));
                    } else {

                        player.sendMessage(ChatUtils.format(Feedback.GLOBAL_PUNISHMENT_SENT_TEMP_BAN)
                                .replace("{offender}", args[0])
                                .replace("{staff}", player.getName())
                                .replace("{duration}", duration + " " + unit));
                    }
                    Punishment punishment = new Punishment(Punishment.getNewID(), targetP.getUuid(), player.getUniqueId().toString(),
                            PunishmentType.TEMP_BAN, reason, System.currentTimeMillis() + TimeUtils.getTime(duration, unit), silent, true);
                    punishment.createPunishment();
                    targetP.setBanned(true);
                    targetP.getBans().add(punishment);
                    targetP.save();

                    if (target.isOnline()) {
                        target.getPlayer().kickPlayer(ChatUtils.format(Feedback.KICK_TEMP_BAN)
                                .replace("{expire}", TimeUtils.getExpiration(punishment.getDuration())));
                    }

                    return true;
                }
            }
        }
        return false;
    }
}