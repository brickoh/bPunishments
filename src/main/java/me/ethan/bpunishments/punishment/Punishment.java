package me.ethan.bpunishments.punishment;

import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Punishment {

    private final bPunishments plugin = bPunishments.getInstance();
    @Getter
    private static Map<UUID, Punishment> punishments = new ConcurrentHashMap<>();
    private final int id;
    private UUID offender;
    private PunishmentType punishmentType;
    private String executor, reason;
    private long duration;
    private long executedDate, removedAtDate;
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
        getPunishments().put(offender, this);
    }

    public Punishment(int id, UUID offender, String executor, PunishmentType punishmentType, String reason) {
        this.id = id;
        this.offender = offender;
        this.executor = executor;
        this.punishmentType = punishmentType;
        this.reason = reason;
        getPunishments().put(offender, this);
    }


    public void createPunishment() {
        Document document = new Document("id", id);
        document.append("offender", this.offender.toString());
        document.append("executor", this.executor);
        document.append("reason", this.reason);
        document.append("duration", this.duration);
        document.append("active", this.active);
        document.append("silent", this.active);
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
        document.append("silent", this.active);
        document.append("type", this.punishmentType.name());

        Document document1 = plugin.getMongoManager().getPunishments().find(Filters.eq("id", id)).first();
        if (document1 == null) return;
        plugin.getMongoManager().getPunishments().replaceOne(document1, document);
    }




    public static int getNewID() {
        int id = 0;
        for (Document ignored : bPunishments.getInstance().getMongoManager().getPunishments().find()) {
            id++;
        }
        return id;
    }


}
