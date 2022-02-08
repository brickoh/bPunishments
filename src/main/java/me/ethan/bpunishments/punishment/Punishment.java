package me.ethan.bpunishments.punishment;

import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import me.ethan.bpunishments.utils.TimeUtils;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Punishment {

    private final bPunishments plugin = bPunishments.getInstance();
    @Getter
    private static Map<UUID, Punishment> punishments = new ConcurrentHashMap<>();
    private final int id;
    private UUID offender;
    private PunishmentType punishmentType;
    private String executor, reason, removeReason;
    private long duration;
    private long executedAt, removedAt;
    private boolean silent, active;

    public Punishment(int id, UUID offender, String executor, PunishmentType punishmentType, String reason, long duration, boolean silent, boolean active) {
        this.id = id;
        this.offender = offender;
        this.executor = executor;
        this.punishmentType = punishmentType;
        this.reason = reason;
        this.duration = duration;
        this.silent = silent;
        this.active = active;
        this.executedAt = System.currentTimeMillis();
        getPunishments().put(offender, this);
    }

    public Punishment(int id, UUID offender, String executor, PunishmentType punishmentType, String reason, boolean silent) {
        this.id = id;
        this.offender = offender;
        this.executor = executor;
        this.punishmentType = punishmentType;
        this.reason = reason;
        this.silent = silent;
        getPunishments().put(offender, this);
    }


    public void createPunishment() {
        Document document = new Document("id", id);
        document.append("offender", this.offender.toString());
        document.append("executor", this.executor);
        document.append("reason", this.reason);
        document.append("duration", this.duration);
        document.append("active", this.active);
        document.append("silent", this.silent);
        document.append("executed_at", this.executedAt);
        document.append("removed_at", null);
        document.append("removed_reason", null);
        document.append("type", this.punishmentType.name());
        plugin.getMongoManager().getPunishments().insertOne(document);
    }

    public void updatePunishment() {
        Document document = new Document("id", id);
        document.append("offender", this.offender.toString());
        document.append("executor", this.executor);
        document.append("reason", this.reason);
        document.append("duration", this.duration);
        document.append("active", this.active);
        document.append("silent", this.silent);
        document.append("executed_at", this.executedAt);
        document.append("removed_at", this.removedAt);
        document.append("removed_reason", this.removeReason);
        document.append("type", this.punishmentType.name());

        Document document1 = plugin.getMongoManager().getPunishments().find(Filters.eq("id", id)).first();
        if (document1 == null) return;
        plugin.getMongoManager().getPunishments().replaceOne(document1, document);
    }


    public String getAddedAtFormatted() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(this.executedAt));

        return calendar.getTime().toString();
    }

    public String getRemovedAtFormatted() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(this.removedAt));
        return calendar.getTime().toString();
    }

    public String getTimeLeft() {
        if (!(this.isActive())) {
            return "Expired";
        }

        Calendar from = Calendar.getInstance();
        from.setTime(new Date(System.currentTimeMillis()));

        Calendar to = Calendar.getInstance();
        to.setTime(new Date(this.executedAt + this.duration));

        return TimeUtils.formatDateDiff(from, to);
    }

    public static int getNewID() {
        int id = 0;
        for (Document ignored : bPunishments.getInstance().getMongoManager().getPunishments().find()) {
            id++;
        }
        return id;
    }


}
