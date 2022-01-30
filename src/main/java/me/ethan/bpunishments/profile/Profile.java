package me.ethan.bpunishments.profile;

import com.mongodb.client.model.Filters;
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

@Getter @Setter
public class Profile {

    private final bPunishments plugin = bPunishments.getInstance();
    @Getter private static Map<UUID, Profile> profiles = new ConcurrentHashMap<>();
    private final UUID uuid;
    private String ip = "Unknown";
    private List<Punishment> bans;
    private List<Punishment> mutes;
    private List<Punishment> warns;
    private List<Punishment> kicks;
    private List<Punishment> blacklists;
    private List<UUID> alts;
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
        document.append("blacklists", new ArrayList<>());
        document.append("bans", new ArrayList<>());
        document.append("kicks", new ArrayList<>());
        document.append("mutes", new ArrayList<>());
        document.append("warns", new ArrayList<>());
        document.append("alts", new ArrayList<>());
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
            this.blacklists = document.get("blacklists", ArrayList.class);
            this.bans = document.get("bans", ArrayList.class);
            this.mutes = document.get("mutes", ArrayList.class);
            this.warns = document.get("warns", ArrayList.class);
            this.kicks = document.get("kicks", ArrayList.class);
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
            UUID executor = UUID.fromString(document.getString("executor"));
            Punishment punishment = new Punishment(
                    document.getInteger("id"),
                    offender,
                    executor,
                    PunishmentType.valueOf(document.getString("type")),
                    document.getString("reason"),
                    document.getLong("duration"),
                    document.getBoolean("silent"),
                    document.getBoolean("active"));
            Punishment.getPunishments().put(punishment.getId(), punishment);
            System.out.println("[PUNISHMENT] " + punishment.getId() + " has been loaded");
        }
    }

    public void save() {
        Document document = new Document("uuid", uuid.toString());
        document.append("ip", ip);
        document.append("blacklists", new ArrayList<>());
        document.append("bans", new ArrayList<>());
        document.append("kicks", new ArrayList<>());
        document.append("mutes", new ArrayList<>());
        document.append("warns", new ArrayList<>());
        document.append("alts", new ArrayList<>());
        document.append("banned", banned);
        document.append("blacklisted", blacklisted);
        document.append("muted", muted);

        Document document1 = plugin.getMongoManager().getProfiles().find(Filters.eq("uuid", uuid.toString())).first();
        if (document1 == null) return;
        plugin.getMongoManager().getProfiles().replaceOne(document1, document);
    }

}
