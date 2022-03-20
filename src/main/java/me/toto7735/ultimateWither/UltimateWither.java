package me.toto7735.ultimateWither;

import me.toto7735.ultimateWither.listener.Listener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UltimateWither extends JavaPlugin {

    private static UltimateWither instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static UltimateWither getInstance() {
        return instance;
    }
}
