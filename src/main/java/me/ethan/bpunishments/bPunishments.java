package me.ethan.bpunishments;

import lombok.Getter;
import me.ethan.bpunishments.commands.CommandManager;
import me.ethan.bpunishments.database.mongo.MongoManager;
import me.ethan.bpunishments.database.redis.RedisManager;
import me.ethan.bpunishments.listeners.ListenerManager;
import me.ethan.bpunishments.utils.Config;
import me.ethan.bpunishments.utils.command.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class bPunishments extends JavaPlugin {

    @Getter private static bPunishments instance;
    @Getter private MongoManager mongoManager;
    @Getter private RedisManager redisManager;
    @Getter private Config configYML, langYML;


    @Override
    public void onEnable() {
        instance = this;
        initialise();

    }

    @Override
    public void onDisable() {
        instance = null;
    }


    private void initialise() {
        configYML = new Config(this, "config", this.getDataFolder().getAbsolutePath());
        langYML = new Config(this, "lang", this.getDataFolder().getAbsolutePath());
        mongoManager = new MongoManager(this);
        redisManager = new RedisManager();
        new CommandManager(this, new CommandFramework(this));
        new ListenerManager(this);
    }


    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }

}
