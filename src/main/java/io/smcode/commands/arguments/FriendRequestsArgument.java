package io.smcode.commands.arguments;

import io.smcode.FriendManager;
import io.smcode.commands.ArgumentExecutor;
import io.smcode.friends.FriendRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class FriendRequestsArgument implements ArgumentExecutor {
    private final FriendManager manager;

    public FriendRequestsArgument(FriendManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        player.sendRichMessage("<gold>Outgoing requests:");

        for (FriendRequest request : manager.getOutgoingRequests(player)) {
            final long millisAgo = System.currentTimeMillis() - request.getTimestamp();
            final long minutesAgo = TimeUnit.MILLISECONDS.toMinutes(millisAgo);

            player.sendMessage(
                    Component.text("- " + request.getTarget().getName(), NamedTextColor.GOLD)
                            .hoverEvent(HoverEvent.showText(
                                    Component.text(minutesAgo + " minutes ago")
                            ))
            );
        }
    }

    @Override
    public String getUsage() {
        return "requests";
    }

    @Override
    public String getDescription() {
        return "See a list of outgoing friend requests.";
    }
}
