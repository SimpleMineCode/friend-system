package io.smcode.friends;

import org.bukkit.entity.Player;

public class FriendRequest {
    private final Player source;
    private final Player target;
    private final long timestamp;

    public FriendRequest(Player source, Player target) {
        this.source = source;
        this.target = target;
        this.timestamp = System.currentTimeMillis();
    }

    public Player getSource() {
        return source;
    }

    public Player getTarget() {
        return target;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
