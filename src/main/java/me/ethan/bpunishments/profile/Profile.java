package me.ethan.bpunishments.profile;

import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.punishment.impl.PunishmentType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Profile {

    private final bPunishments plugin = bPunishments.getInstance();
    @Getter private static Map<UUID, Profile> profiles = new ConcurrentHashMap<>();
    private final UUID uuid;
    private String ip = "Unknown";
    private ArrayList<UUID> alts;
    private ArrayList<Punishment> bans = new ArrayList<>(),  kicks = new ArrayList<>(), mutes = new ArrayList<>(), warns = new ArrayList<>(), blacklists = new ArrayList<>();
    private boolean banned, muted, blacklisted;


    public Profile(UUID uuid, String ip) {
        this.uuid = uuid;
        this.ip = ip;
        this.load();
        this.loadPunishments();
    }

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.load();
        this.loadPunishments();
    }

    private void create() {
        Document document = new Document("uuid", uuid.toString());
        document.append("ip", ip);
        document.append("banned", banned);
        document.append("blacklisted", blacklisted);
        document.append("muted", muted);

        plugin.getMongoManager().getProfiles().insertOne(document);
        profiles.put(uuid, this);
    }

    private void load() {
        Document document = plugin.getMongoManager().getProfiles().find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            this.ip = document.getString("ip");
            this.alts = document.get("alts", ArrayList.class);
            this.muted = document.getBoolean("muted");
            this.banned = document.getBoolean("banned");
            this.blacklisted = document.getBoolean("blacklisted");
            profiles.put(uuid, this);
        } else {
            this.create();
        }
    }

    private void loadPunishments() {
        for (Document document : plugin.getMongoManager().getPunishments().find(Filters.eq("offender", uuid.toString()))) {
            UUID offender = UUID.fromString(document.getString("offender"));
            String executor = document.getString("executor");
            Punishment punishment = new Punishment(
                    document.getInteger("id"),
                    offender,
                    executor,
                    PunishmentType.valueOf(document.getString("type")),
                    document.getString("reason"),
                    document.getLong("duration"),
                    document.getBoolean("silent"),
                    document.getBoolean("active"));

            switch (punishment.getPunishmentType()) {
                case TEMP_BAN, BAN -> bans.add(punishment);
                case TEMP_MUTE, MUTE -> mutes.add(punishment);
                case BLACKLIST -> blacklists.add(punishment);
                case WARN -> warns.add(punishment);
                case KICK -> kicks.add(punishment);
            }
            Punishment.getPunishments().put(uuid, punishment);
        }
    }

    public void save() {
        Document document = new Document("uuid", uuid.toString());
        document.append("ip", ip);
        document.append("banned", banned);
        document.append("blacklisted", blacklisted);
        document.append("muted", muted);
        Document document1 = plugin.getMongoManager().getProfiles().find(Filters.eq("uuid", uuid.toString())).first();
        if (document1 == null) return;
        plugin.getMongoManager().getProfiles().replaceOne(document1, document);
    }

}
