package me.ethan.bpunishments.menus;

import me.ethan.bpunishments.bPunishments;
import me.ethan.bpunishments.feedback.Feedback;
import me.ethan.bpunishments.menus.impl.*;
import me.ethan.bpunishments.profile.Profile;
import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.menu.Button;
import me.ethan.bpunishments.utils.menu.Menu;
import me.ethan.bpunishments.utils.name.NameFetcher;
import me.ethan.bpunishments.utils.name.NameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryMenu extends Menu {

    private final bPunishments plugin = bPunishments.getInstance();
    private final Profile profile;

    public HistoryMenu(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String getTitle(Player player) {
        String name = NameUtils.getName(profile.getUuid());
        return ChatUtils.format(Feedback.MENU_HISTORY_TITLE).replace("{profile}", name);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(0, getBans(profile));
        buttons.put(2, getWarns(profile));
        buttons.put(4, getMutes(profile));
        buttons.put(6, getKicks(profile));
        buttons.put(8, getBlacklists(profile));
        return buttons;
    }


    public Button getBans(Profile profile) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemStack bans = new ItemStack(Material.matchMaterial(plugin.getLangYML().getString("menus.history.bans.item.material")));
                ItemMeta meta = bans.getItemMeta();
                meta.setDisplayName(ChatUtils.format(plugin.getLangYML().getString("menus.history.bans.item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for(String s : plugin.getLangYML().getStringList("menus.history.bans.item.lore")) {
                    s = s.replace("{bans}", String.valueOf(profile.getBans().size()));
                    lore.add(s);
                }
                meta.setLore(ChatUtils.format(lore));
                bans.setItemMeta(meta);
                return bans;

            }
            @Override
            public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new BanMenu(profile).openMenu(player);
            }
        };

    }

    public Button getWarns(Profile profile) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemStack bans = new ItemStack(Material.matchMaterial(plugin.getLangYML().getString("menus.history.warns.item.material")));
                ItemMeta meta = bans.getItemMeta();
                meta.setDisplayName(ChatUtils.format(plugin.getLangYML().getString("menus.history.warns.item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for(String s : plugin.getLangYML().getStringList("menus.history.warns.item.lore")) {
                    s = s.replace("{warns}", String.valueOf(profile.getWarns().size()));
                    lore.add(s);
                }
                meta.setLore(ChatUtils.format(lore));
                bans.setItemMeta(meta);
                return bans;
            }
            @Override
            public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new WarnMenu(profile).openMenu(player);
            }
        };
    }

    public Button getMutes(Profile profile) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemStack bans = new ItemStack(Material.matchMaterial(plugin.getLangYML().getString("menus.history.mutes.item.material")));
                ItemMeta meta = bans.getItemMeta();
                meta.setDisplayName(ChatUtils.format(plugin.getLangYML().getString("menus.history.mutes.item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for(String s : plugin.getLangYML().getStringList("menus.history.mutes.item.lore")) {
                    s = s.replace("{mutes}", String.valueOf(profile.getWarns().size()));
                    lore.add(s);
                }
                meta.setLore(ChatUtils.format(lore));
                bans.setItemMeta(meta);
                return bans;
            }
            @Override
            public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new MuteMenu(profile).openMenu(player);
            }
        };
    }

    public Button getKicks(Profile profile) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemStack bans = new ItemStack(Material.matchMaterial(plugin.getLangYML().getString("menus.history.kicks.item.material")));
                ItemMeta meta = bans.getItemMeta();
                meta.setDisplayName(ChatUtils.format(plugin.getLangYML().getString("menus.history.kicks.item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for(String s : plugin.getLangYML().getStringList("menus.history.kicks.item.lore")) {
                    s = s.replace("{kicks}", String.valueOf(profile.getKicks().size()));
                    lore.add(s);
                }
                meta.setLore(ChatUtils.format(lore));
                bans.setItemMeta(meta);
                return bans;
            }
            @Override
            public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new KickMenu(profile).openMenu(player);
            }
        };
    }

    public Button getBlacklists(Profile profile) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                ItemStack bans = new ItemStack(Material.matchMaterial(plugin.getLangYML().getString("menus.history.blacklists.item.material")));
                ItemMeta meta = bans.getItemMeta();
                meta.setDisplayName(ChatUtils.format(plugin.getLangYML().getString("menus.history.blacklists.item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for(String s : plugin.getLangYML().getStringList("menus.history.blacklists.item.lore")) {
                    s = s.replace("{blacklists}", String.valueOf(profile.getBlacklists().size()));
                    lore.add(s);
                }
                meta.setLore(ChatUtils.format(lore));
                bans.setItemMeta(meta);
                return bans;
            }
            @Override
            public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new BlacklistMenu(profile).openMenu(player);
            }
        };
    }

}
