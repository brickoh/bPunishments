package me.ethan.bpunishments;

import lombok.Getter;
import me.ethan.bpunishments.commands.CommandManager;
import me.ethan.bpunishments.database.MongoManager;
import me.ethan.bpunishments.listeners.ListenerManager;
import me.ethan.bpunishments.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class bPunishments extends JavaPlugin {

    @Getter private static bPunishments instance;
    @Getter private MongoManager mongoManager;
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
        new CommandManager(this);
        new ListenerManager(this);

    }

}
