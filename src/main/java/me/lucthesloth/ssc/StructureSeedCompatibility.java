package me.lucthesloth.ssc;

import org.bukkit.Bukkit;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.plugin.java.JavaPlugin;

public final class StructureSeedCompatibility extends JavaPlugin {
    public static StructureSeedCompatibility instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.saveDefaultConfig();
        this.registerCommands();
        this.registerListeners();
        this.registerAsyncRunnables();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new me.lucthesloth.ssc.listeners.SlimeListener(), this);
    }
    private void registerAsyncRunnables(){
        getServer().getScheduler().runTaskTimerAsynchronously(this, new me.lucthesloth.ssc.runnables.SlimeSpawner(), 40, Bukkit.getTicksPerSpawns(SpawnCategory.MONSTER));
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            Bukkit.getLogger().info(String.format("Slime chunks: %d", me.lucthesloth.ssc.runnables.SlimeSpawner.slimeChunks.size()));
        }, 40, 100);
    }

    private void registerCommands(){
        this.getCommand("scap").setExecutor(new me.lucthesloth.ssc.commands.MonsterCap());
    }
}
