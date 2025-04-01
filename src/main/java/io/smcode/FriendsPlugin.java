package io.smcode;

import io.smcode.commands.FriendsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class FriendsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("friends").setExecutor(new FriendsCommand());
    }
}
