package io.smcode.commands.arguments;

import io.smcode.FriendManager;
import io.smcode.commands.ArgumentExecutor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FriendAcceptArgument implements ArgumentExecutor {
    private final FriendManager manager;

    public FriendAcceptArgument(FriendManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendRichMessage("<red>Usage: /friend accept <player>");
            return;
        }

        final OfflinePlayer source = Bukkit.getOfflinePlayer(args[1]);
        manager.accept(player, source);
    }

    @Override
    public String getUsage() {
        return "accept <player>";
    }

    @Override
    public String getDescription() {
        return "Accept a pending friend request";
    }
}
