package io.smcode.menu;

import io.smcode.FriendManager;
import io.smcode.FriendsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class FriendListMenu extends PagedMenu {
    private final FriendManager manager = FriendsPlugin.getInstance().getManager();

    public FriendListMenu() {
        super(Rows.FOUR, Component.text("Friends"));
    }

    @Override
    public void onSetItems() {
        final List<ItemStack> friendItems = manager.getFriends(getPlayer())
                .stream().map(friend -> {
                    final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                    final SkullMeta meta = (SkullMeta) item.getItemMeta();

                    meta.setOwningPlayer(friend);
                    meta.setDisplayName("§a" + friend.getName());
                    item.setItemMeta(meta);

                    return item;
                }).toList();

        addAll(friendItems.toArray(new ItemStack[]{}));
    }
}
