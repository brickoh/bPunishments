package me.ethan.bpunishments.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @author brickoh
 * @since 11/10/2021
 * project Yuki-Utils
 * GitHub https://github.com/brickoh
 */
@Setter @Getter
public class Config extends YamlConfiguration {

    private final File file;
    private String name, directory;

    public Config(final JavaPlugin plugin, final String name, final String directory) {
        this.setName(name);
        this.setDirectory(directory);
        this.file = new File(directory, name + ".yml");
        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }
        this.load();
        this.save();
    }


    public void load() {
        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException ex2) {
            final Exception e = new Exception();
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

