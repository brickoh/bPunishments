package me.ethan.bpunishments.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import lombok.Getter;
import me.ethan.bpunishments.bPunishments;
import org.bson.Document;

import java.util.Arrays;


@Getter
public class MongoManager {

    private final bPunishments plugin;
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> profiles;
    private final MongoCollection<Document> punishments;


    public MongoManager(bPunishments plugin) {
        this.plugin = plugin;
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(700);
        if (plugin.getConfigYML().getBoolean("mongo.auth.enabled")) {
            mongoClient = new MongoClient(new ServerAddress(plugin.getConfigYML().getString("mongo.host"), plugin.getConfigYML().getInt("mongo.port")),
                    Arrays.asList(MongoCredential.createCredential(
                            plugin.getConfigYML().getString("mongo.auth.username"),
                            plugin.getConfigYML().getString("mongo.database"),
                            plugin.getConfigYML().getString("mongo.auth.password").toCharArray())), builder.build());
        } else {
            mongoClient = new MongoClient(new ServerAddress(plugin.getConfigYML().getString("mongo.host"), plugin.getConfigYML().getInt("mongo.port")), builder.build());
        }

        mongoDatabase = mongoClient.getDatabase(plugin.getConfigYML().getString("mongo.database"));
        profiles = mongoDatabase.getCollection("profiles");
        profiles.createIndex(Indexes.ascending("uuid"));
        punishments = mongoDatabase.getCollection("punishments");

    }
}
