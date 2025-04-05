package io.smcode;

import io.smcode.commands.FriendsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class FriendsPlugin extends JavaPlugin {
    private static FriendsPlugin instance;

    private FriendManager manager;

    @Override
    public void onEnable() {
        instance = this;
        MenuApi.setUp(this);

        manager = new FriendManager();
        getCommand("friends").setExecutor(new FriendsCommand(manager));
    }

    public FriendManager getManager() {
        return manager;
    }

    public static FriendsPlugin getInstance() {
        return instance;
    }
}
