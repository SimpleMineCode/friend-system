package io.smcode.commands.arguments;

import io.smcode.commands.ArgumentExecutor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FriendAddArgument implements ArgumentExecutor {
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

        // TODO: send friend request to `target`
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
