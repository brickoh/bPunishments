package me.ethan.bpunishments.database.redis.impl;

import com.google.gson.JsonObject;
import me.ethan.bpunishments.database.redis.other.JedisHandle;
import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.utils.ChatUtils;

import static me.ethan.bpunishments.bPunishments.getOnlinePlayers;

public class RedisListener implements JedisHandle {

    @Override
    public void handleMessage(JsonObject object) {
        Payload payload;
        try {
            payload = Payload.valueOf(object.get("payload").getAsString());
        } catch (IllegalArgumentException ignored) {
            return;
        }
        JsonObject data = object.get("data").getAsJsonObject();
        String offender = data.get("offender").getAsString();
        String staff = data.get("staff").getAsString();
        switch (payload) {
            case STAFF_BAN_SENT:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_SENT_BAN)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_MUTE_SENT:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_SENT_MUTE)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_BLACKLIST_SENT:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_SENT_BLACKLIST)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_WARN_SENT:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_SENT_WARN)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_KICK_SENT:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage("kick message needs to be added");
                });
                break;

            case STAFF_REVOKED_BAN:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_REVOKED_BAN)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_REVOKED_MUTE:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_REVOKED_MUTE)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_REVOKED_WARN:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_REVOKED_WARN)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;

            case STAFF_REVOKED_BLACKLIST:
                getOnlinePlayers().stream().filter(player -> player.hasPermission("bpunishments.staff")).forEach(player -> {
                    player.sendMessage(ChatUtils.format(Feedback.STAFF_PUNISHMENT_REVOKED_BLACKLIST)
                            .replace("{offender}", (offender))
                            .replace("{staff}", staff));
                });
                break;
        }
    }
}