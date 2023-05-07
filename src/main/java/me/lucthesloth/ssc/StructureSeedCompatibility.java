package me.lucthesloth.ssc;

import org.bukkit.plugin.java.JavaPlugin;

public final class StructureSeedCompatibility extends JavaPlugin {
    public static StructureSeedCompatibility instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
