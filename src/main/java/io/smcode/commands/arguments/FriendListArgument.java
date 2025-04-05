package io.smcode.commands.arguments;

import io.smcode.FriendManager;
import io.smcode.commands.ArgumentExecutor;
import io.smcode.menu.FriendListMenu;
import org.bukkit.entity.Player;

public class FriendListArgument implements ArgumentExecutor {
    @Override
    public void execute(Player player, String[] args) {
        new FriendListMenu().open(player);
    }

    @Override
    public String getUsage() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "See a list of your friends";
    }
}
