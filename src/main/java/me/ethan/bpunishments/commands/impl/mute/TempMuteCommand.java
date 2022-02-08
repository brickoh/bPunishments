package me.ethan.bpunishments.commands.impl.mute;

import com.google.gson.JsonObject;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.database.redis.impl.Payload;
import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.TimeUtils;
import me.ethan.bpunishments.utils.command.CommandArgs;
import me.ethan.bpunishments.utils.command.annotation.Command;
import me.ethan.bpunishments.utils.uuid.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class TempMuteCommand {

    @Command(name = "tmute", aliases = {"tm", "tempmute"}, permission = "bpunishments.commands.tempmute")
    public void execute(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length < 3) {
            sender.sendMessage(ChatColor.RED + "/" + args.getLabel() + " <player> <time> <time-unit> <reason> [-s]");
            return;
        }

        UUID uuid = UUIDUtils.getUUID(args.getArgs(0));
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "UUID came back null, is the IGN correct? (" + args.getArgs(0) + ")");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
        Profile profile = new Profile(target.getUniqueId());
        int duration = Integer.parseInt(args.getArgs(1));
        String unit = args.getArgs(2);
        boolean silent = false;
        String tf = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < args.getArgs().length; i++) {
            sb.append(args.getArgs()[i]).append(" ");
        }
        String reason = sb.toString().trim();

        if (reason.contains("-s")) {
            reason = reason.replace("-s", "");
            silent = true;
        }
        Punishment punishment;
        if (sender instanceof ConsoleCommandSender) {
            punishment = new Punishment(Punishment.getNewID(), profile.getUuid(), "CONSOLE", PunishmentType.TEMP_MUTE, reason, TimeUtils.getTime(duration, unit), silent, true);
            punishment.createPunishment();
            profile.getMutes().add(punishment);
        } else if (sender instanceof Player player) {
            punishment = new Punishment(Punishment.getNewID(), profile.getUuid(), player.getUniqueId().toString(), PunishmentType.TEMP_MUTE, reason, TimeUtils.getTime(duration, unit), silent, true);
            punishment.createPunishment();
            tf = punishment.getTimeLeft();
            profile.getMutes().add(punishment);
        }
        profile.setMuted(true);
        profile.save();
        if (silent) {
            JsonObject data = new JsonObject();
            data.addProperty("offender", target.getName());
            data.addProperty("staff", sender.getName());
            bPunishments.getInstance().getRedisManager().write(Payload.STAFF_TEMP_MUTE_SENT, data);
        } else {
            Bukkit.broadcastMessage(ChatUtils.format(Feedback.GLOBAL_PUNISHMENT_SENT_TEMP_MUTE)
                    .replace("{duration}", tf)
                    .replace("{offender}", target.getName())
                    .replace("{staff}", sender.getName()));
        }
    }

}
