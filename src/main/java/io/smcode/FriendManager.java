package io.smcode;

import io.smcode.friends.FriendRequest;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FriendManager {
    // target -> requests
    private final Map<UUID, List<FriendRequest>> pendingIncomingRequests = new HashMap<>();
    // source -> requests
    private final Map<UUID, List<FriendRequest>> pendingOutgoingRequests = new HashMap<>();
    private final FileConfiguration config;
    private final File configFile;

    public FriendManager() {
        this.configFile = new File(FriendsPlugin.getInstance().getDataFolder(), "friends.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public List<FriendRequest> getOutgoingRequests(Player player) {
        return pendingOutgoingRequests.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public List<OfflinePlayer> getFriends(Player player) {
        return config.getStringList(player.getUniqueId().toString())
                .stream().map(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid)))
                .toList();
    }

    public void accept(Player player, OfflinePlayer source) {
        if (!requestExists(source, player)) {
            player.sendRichMessage("<red>You don't have a friend request by this player!");
            return;
        }

        makeFriends(player, source);
    }

    public void sendRequest(FriendRequest request) {
        final UUID targetUUID = request.getTarget().getUniqueId();
        final UUID sourceUUID = request.getSource().getUniqueId();

        if (areFriends(request.getSource(), request.getTarget())) {
            request.getSource().sendRichMessage("<red>You are already friends.");
            return;
        }

        if (requestExists(request.getSource(), request.getTarget())) {
            request.getSource().sendRichMessage("<red>You already sent a friend request to this player.");
            return;
        }

        // Add incoming request (from target perspective)
        final List<FriendRequest> pendingTargetRequests = pendingIncomingRequests.getOrDefault(targetUUID,
                new ArrayList<>());
        pendingTargetRequests.add(request);
        pendingIncomingRequests.put(targetUUID, pendingTargetRequests);

        // Add outgoing request (from source perspective)
        final List<FriendRequest> pendingSourceOutgoingRequests = pendingOutgoingRequests.getOrDefault(sourceUUID,
                new ArrayList<>());
        pendingSourceOutgoingRequests.add(request);
        pendingOutgoingRequests.put(targetUUID, pendingSourceOutgoingRequests);

        request.getTarget().sendRichMessage(
                "<green>You received a new friend request by " + request.getSource().getName());
        request.getSource().sendRichMessage("<green>You sent a friend request to " + request.getTarget().getName());
    }

    public boolean areFriends(OfflinePlayer player1, OfflinePlayer player2) {
        return config.getStringList(player1.getUniqueId().toString()).contains(player2.getUniqueId().toString());
    }

    public void removeFriends(Player player1, OfflinePlayer player2) {
        if (!areFriends(player1, player2)) {
            player1.sendRichMessage("<red>You are not befriended with this player");
            return;
        }

        final List<String> player1Friends = config.getStringList(player1.getUniqueId().toString());
        player1Friends.remove(player2.getUniqueId().toString());
        final List<String> player2Friends = config.getStringList(player2.getUniqueId().toString());
        player2Friends.remove(player1.getUniqueId().toString());

        config.set(player1.getUniqueId().toString(), player1Friends);
        config.set(player2.getUniqueId().toString(), player2Friends);

        saveFriendsConfig();

        player1.sendRichMessage("<green>Successfully removed friend " + player2.getName());
    }

    private boolean requestExists(OfflinePlayer source, Player target) {
        if (!pendingIncomingRequests.containsKey(target.getUniqueId()))
            return false;

        return pendingIncomingRequests.get(target.getUniqueId())
                .stream()
                .anyMatch(request -> request.getSource().getUniqueId().equals(source.getUniqueId()));
    }

    private void makeFriends(OfflinePlayer player1, OfflinePlayer player2) {
        final List<String> player1Friends = config.getStringList(player1.getUniqueId().toString());
        player1Friends.add(player2.getUniqueId().toString());
        final List<String> player2Friends = config.getStringList(player2.getUniqueId().toString());
        player2Friends.add(player1.getUniqueId().toString());

        config.set(player1.getUniqueId().toString(), player1Friends);
        config.set(player2.getUniqueId().toString(), player2Friends);

        saveFriendsConfig();
        removeFriendRequests(player1, player2);

        if (player1.isOnline())
            ((Player) player1).sendRichMessage("<green>You are now friends with " + player2.getName());

        if (player2.isOnline())
            ((Player) player2).sendRichMessage("<green>You are now friends with " + player1.getName());
    }

    private void removeFriendRequests(OfflinePlayer player1, OfflinePlayer player2) {
        // Delete incoming requests for both players
        removeRequestFromList(player1, player2, pendingIncomingRequests);

        // Delete outgoing requests for both players
        removeRequestFromList(player1, player2, pendingOutgoingRequests);
    }

    private void removeRequestFromList(OfflinePlayer player1, OfflinePlayer player2,
                                       Map<UUID, List<FriendRequest>> list) {
        final List<FriendRequest> player1Requests = list.get(player1.getUniqueId());
        player1Requests.removeIf(request -> request.getSource().getUniqueId().equals(player2.getUniqueId()));
        list.put(player1.getUniqueId(), player1Requests);

        final List<FriendRequest> player2Requests = list.get(player2.getUniqueId());
        player2Requests.removeIf(request -> request.getSource().getUniqueId().equals(player1.getUniqueId()));
        list.put(player2.getUniqueId(), player2Requests);
    }

    private void saveFriendsConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
