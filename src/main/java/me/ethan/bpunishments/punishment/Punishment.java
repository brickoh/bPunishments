package me.ethan.bpunishments.punishment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import org.bson.Document;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Punishment {

    private final bPunishments plugin = bPunishments.getInstance();
    @Getter private static Map<Integer, Punishment> punishments = new ConcurrentHashMap<>();
    private final int id;
    private UUID offender, executor;
    private PunishmentType punishmentType;
    private String reason;
    private long duration;
    private long executedDate, removedAtDate;
    private boolean silent, active;

    public Punishment(int id, UUID offender, UUID executor, PunishmentType punishmentType, String reason, long duration, boolean silent, boolean active) {
        this.id = id;
        this.offender = offender;
        this.executor = executor;
        this.punishmentType = punishmentType;
        this.reason = reason;
        this.duration = duration;
        this.silent = silent;
        this.active = active;
        getPunishments().put(id, this);
    }


    public void createPunishment() {
        Document document = new Document("id", id);
        document.append("offender", this.offender.toString());
        document.append("executor", this.executor.toString());
        document.append("reason", this.reason);
        document.append("duration", this.duration);
        document.append("active", this.active);
        document.append("silent", this.active);
        document.append("type", this.punishmentType.name());

        plugin.getMongoManager().getPunishments().insertOne(document);
    }

    public static int getNewID() {
        int id = 0;
        for (Document ignored : bPunishments.getInstance().getMongoManager().getPunishments().find()) {
            id++;
        }
        return id;
    }
}
