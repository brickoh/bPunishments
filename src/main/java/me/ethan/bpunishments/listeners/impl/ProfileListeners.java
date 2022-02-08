package me.ethan.bpunishments.listeners.impl;

import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;
import java.util.UUID;

public class ProfileListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        String address = event.getAddress().getHostAddress();
        Profile profile = new Profile(uuid, address);
        Punishment punishment = Punishment.getPunishments().get(uuid);

        if (profile.isBanned()) {
            if (punishment.getPunishmentType() == PunishmentType.BAN) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatUtils.format(Feedback.KICK_BAN));
            } else if (punishment.getPunishmentType() == PunishmentType.TEMP_BAN) {
                long duration = punishment.getDuration() - System.currentTimeMillis();
                if (duration <= 0L) {
                    punishment.setActive(false);
                    profile.setBanned(false);
                    profile.save();
                    punishment.updatePunishment();
                } else {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatUtils.format(Feedback.KICK_TEMP_BAN)
                            .replace("{expire}", punishment.getTimeLeft()));
                }
            }
        }



    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatAttempt(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfiles().get(player.getUniqueId());

        if(profile.isMuted()) {
            event.setCancelled(true);
        }

    }


}
