package io.smcode.commands.arguments;

import io.smcode.FriendManager;
import io.smcode.commands.ArgumentExecutor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FriendRemoveArgument implements ArgumentExecutor {
    private final FriendManager manager;

    public FriendRemoveArgument(FriendManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendRichMessage("<red>Usage: /friend remove <player>");
            return;
        }

        final OfflinePlayer friend = Bukkit.getOfflinePlayer(args[1]);
        manager.removeFriends(player, friend);
    }

    @Override
    public String getUsage() {
        return "remove <player>";
    }

    @Override
    public String getDescription() {
        return "Remove player from your friends list.";
    }
}
