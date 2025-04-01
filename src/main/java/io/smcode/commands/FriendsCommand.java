package io.smcode.commands;

import io.smcode.commands.arguments.FriendAddArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FriendsCommand implements CommandExecutor {
    private static final String helpFormat = "<blue>%s</blue> <gray>- %s</gray>";
    private static final Map<String, ArgumentExecutor> arguments = new HashMap<>();

    private static final Map<String, String> commandArgs = Map.of(
            "remove <player>", "Remove a player from your friend list",
            "accept <player>", "Accept a friend request",
            "list", "Show all friends"
    );

    public FriendsCommand() {
        arguments.put("add", new FriendAddArgument());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendRichMessage("<red>You must be a player to use this command!");
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        final String argument = args[0].toLowerCase();
        final ArgumentExecutor argumentExecutor = arguments.get(argument);

        if (argumentExecutor == null) {
            sendHelp(player);
            return true;
        }

        argumentExecutor.execute(player, args);

        return true;
    }

    private static void sendHelp(Player player) {
        for (String argument : arguments.keySet()) {
            player.sendRichMessage(helpFormat.formatted(argument, arguments.get(argument).getDescription()));
        }
    }
}
