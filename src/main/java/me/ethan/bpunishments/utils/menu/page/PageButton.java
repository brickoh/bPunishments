package me.ethan.bpunishments.utils.menu.page;

import me.ethan.bpunishments.utils.ChatUtils;
import me.ethan.bpunishments.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PageButton extends Button {

    private final int mod;
    private final PageMenu menu;

    public PageButton(int mod, PageMenu menu) {
        this.mod = mod;
        this.menu = menu;
    }

    @Override
    public ItemStack getItem(Player player) {
        if (this.hasNext(player)) {
            ItemStack itemStack = new ItemStack(Material.ARROW);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(mod > 0 ? ChatUtils.format("&aNext Page") : ChatUtils.format("&cPrevious Page"));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        } else {
            ItemStack itemStack = new ItemStack(Material.ARROW);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(mod > 0 ? ChatUtils.format("&cLast Page") : ChatUtils.format("&aFirst Page"));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (hasNext(player)) {
            this.menu.modPage(player, mod);
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }

}