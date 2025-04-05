package io.smcode.commands.arguments;

import io.smcode.FriendManager;
import io.smcode.commands.ArgumentExecutor;
import io.smcode.friends.FriendRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FriendAddArgument implements ArgumentExecutor {
    private final FriendManager manager;

    public FriendAddArgument(FriendManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 1) {
            player.sendRichMessage("<red>Usage: /friend add <player>");
            return;
        }

        final Player target;

        if ((target = Bukkit.getPlayer(args[1])) == null) {
            player.sendRichMessage("<red>This player is not online!");
            return;
        }

        manager.sendRequest(new FriendRequest(player, target));
    }

    @Override
    public String getUsage() {
        return "add <player>";
    }

    @Override
    public String getDescription() {
        return "Send a friend request to a player";
    }
}
