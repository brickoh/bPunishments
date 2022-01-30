package me.ethan.bpunishments.listeners.impl;

import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class ProfileListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        String address = event.getAddress().getHostAddress();
        System.out.println(address);
        new Profile(uuid, address);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoinAttempt(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        Profile profile = Profile.getProfiles().get(uuid);

        if(profile.isBanned()) {
            System.out.println("BANNED");
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatUtils.format(Feedback.KICK_BAN)
                    .replace("{expire}", "10L"));
        } else if (profile.isBlacklisted()) {
            event.setKickMessage("");
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
