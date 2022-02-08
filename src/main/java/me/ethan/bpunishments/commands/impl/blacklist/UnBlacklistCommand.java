package me.ethan.bpunishments.commands.impl.blacklist;

import com.google.gson.JsonObject;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.database.redis.impl.Payload;
import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.command.CommandArgs;
import me.ethan.bpunishments.utils.command.annotation.Command;
import me.ethan.bpunishments.utils.uuid.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;

public class UnBlacklistCommand {

    @Command(name = "unban", permission = "bpunishments.commands.unban")
    public void execute(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length == 0) {
            sender.sendMessage(ChatColor.RED + "/" + args.getLabel() + " <player> <reason> [-s]");
            return;
        }
        UUID uuid = UUIDUtils.getUUID(args.getArgs(0));
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "UUID came back null, is the IGN correct? (Player: " + args.getArgs(0) + ")");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
        Profile profile = new Profile(target.getUniqueId());
        boolean silent = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.getArgs().length; i++) {
            sb.append(args.getArgs()[i]).append(" ");
        }
        String reason = sb.toString().trim();
        if (reason.contains("-s")) {
            reason = reason.replace("-s", "");
            silent = true;
        }

        for(Punishment blacklist : profile.getBlacklists()) {
            if(blacklist.isActive()) {
                blacklist.setActive(false);
                blacklist.setRemovedAt(System.currentTimeMillis());
                blacklist.setRemoveReason(reason);
                blacklist.updatePunishment();
            }
        }

        profile.setBlacklisted(false);
        profile.save();

        if (silent) {
            JsonObject data = new JsonObject();
            data.addProperty("offender", target.getName());
            data.addProperty("staff", sender.getName());
            bPunishments.getInstance().getRedisManager().write(Payload.STAFF_REVOKED_BLACKLIST, data);
        } else {
            Bukkit.broadcastMessage(ChatUtils.format(Feedback.GLOBAL_PUNISHMENT_REVOKED_BLACKLIST)
                    .replace("{offender}", target.getName())
                    .replace("{staff}", sender.getName()));
        }
    }

}
