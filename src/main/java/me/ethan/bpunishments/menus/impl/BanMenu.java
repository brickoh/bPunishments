package me.ethan.bpunishments.menus.impl;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.punishment.Punishment;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.TimeUtils;
import me.ethan.bpunishments.utils.menu.Button;
import me.ethan.bpunishments.utils.menu.page.PageMenu;
import me.ethan.bpunishments.utils.name.NameUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BanMenu extends PageMenu {

    private final bPunishments plugin = bPunishments.getInstance();
    private final Profile profile;

    public BanMenu(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int count = 0;
        for (Button button : getPunishments(profile)) {
            buttons.put(count, button);
            count++;
        }

        return buttons;
    }

    @Override
    public String getRawTitle(Player player) {
        return ChatUtils.format("&f" + NameUtils.getName(profile.getUuid()) + "'s &7bans");
    }

    private List<Button> getPunishments(Profile profile) {
        List<Button> toButton = new ArrayList<>();
        List<Punishment> bans = profile.getBans();
        bans.forEach(ban -> {
            if(ban.isActive()) {
                toButton.add(new Button() {
                    @Override
                    public ItemStack getItem(Player player) {
                        ItemStack active = new ItemStack(Material.RED_WOOL);
                        ItemMeta meta = active.getItemMeta();
                        meta.setDisplayName(ChatUtils.format("&e&l" + TimeUtils.getExpiration(ban.getExecutedDate())));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("&7&m------------------------------");
                        lore.add("&cBy: &e" + (ban.getExecutor().equals("CONSOLE") ? "CONSOLE" : NameUtils.getName(UUID.fromString(ban.getExecutor()))) );
                        lore.add("&cReason: &e" + ban.getReason().replace("-s", ""));
                        lore.add("&cDuration: &e1 hours, 0 minutes, 0 seconds");
                        lore.add("&cSilent: &e" + ban.isSilent());
                        lore.add("&7&m------------------------------");
                        meta.setLore(ChatUtils.format(lore));
                        active.setItemMeta(meta);
                        return active;
                    }
                });
            } else {
                toButton.add(new Button() {
                    @Override
                    public ItemStack getItem(Player player) {
                        ItemStack inactive = new ItemStack(Material.GREEN_WOOL);
                        ItemMeta meta = inactive.getItemMeta();
                        meta.setDisplayName(ChatUtils.format("&e&l" + TimeUtils.getExpiration(ban.getExecutedDate())));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("&7&m------------------------------");
                        lore.add("&cBy: &e" + (ban.getExecutor().equals("CONSOLE") ? "CONSOLE" : NameUtils.getName(UUID.fromString(ban.getExecutor()))) );
                        lore.add("&cReason: &e" + ban.getReason().replace("-s", ""));
                        lore.add("&cDuration: &e1 hours, 0 minutes, 0 seconds");
                        lore.add("&cSilent: &e" + ban.isSilent());
                        lore.add("&7&m------------------------------");
                        lore.add("&cRemoved By: &e" + (ban.getExecutor().equals("CONSOLE") ? "CONSOLE" : NameUtils.getName(UUID.fromString(ban.getExecutor()))) );
                        lore.add("&cRemoval Reason: &e" + ban.getReason().replace("-s", ""));
                        lore.add("&cRemoval Date: &e" + TimeUtils.getExpiration(ban.getRemovedAtDate()));
                        lore.add("&7&m------------------------------");
                        meta.setLore(ChatUtils.format(lore));
                        inactive.setItemMeta(meta);
                        return inactive;
                    }
                });
            }
        });
        return toButton;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public boolean usePlaceholder() {
        return true;
    }
}
