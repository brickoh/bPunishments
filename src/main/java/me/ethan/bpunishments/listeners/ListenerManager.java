package me.ethan.bpunishments.listeners;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.listeners.impl.ProfileListener;

import java.util.Arrays;

public class ListenerManager {

    private final bPunishments plugin;

    public ListenerManager(bPunishments plugin) {
        this.plugin = plugin;
        registerListeners();
    }

    private void registerListeners() {
        Arrays.asList(new ProfileListener()
        ).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }
}
