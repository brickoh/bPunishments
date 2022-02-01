package me.ethan.bpunishments.listeners;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.listeners.impl.MenuListener;
import me.ethan.bpunishments.listeners.impl.ProfileListener;

import java.util.Arrays;

//Converted ListenerManager to a Record completely forgot about the Java 16 Addition :O
public record ListenerManager(bPunishments plugin) {

    public ListenerManager(bPunishments plugin) {
        this.plugin = plugin;
        registerListeners();
    }

    private void registerListeners() {
        Arrays.asList(new ProfileListener(), new MenuListener()
        ).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }
}
