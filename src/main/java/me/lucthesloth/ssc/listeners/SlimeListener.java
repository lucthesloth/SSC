package me.lucthesloth.ssc.listeners;

import me.lucthesloth.ssc.Configuration;
import me.lucthesloth.ssc.StructureSeedCompatibility;
import me.lucthesloth.ssc.runnables.SlimeSpawner;
import me.lucthesloth.ssc.utils.Slime;
import me.lucthesloth.ssc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class SlimeListener implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if (!event.getWorld().getName().equalsIgnoreCase(StructureSeedCompatibility.instance.config.worldName)) return;
        Location x = event.getChunk().getBlock(0, 0, 0).getLocation();
        Configuration.Region region = StructureSeedCompatibility.instance.config.findRegion(x);
        if (region != null && Slime.isSlimeChunk(event.getChunk(), region.seed)) {
            SlimeSpawner.addSlimeChunk(event.getChunk().getX(), event.getChunk().getZ());
        }
    }
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        SlimeSpawner.removeSlimeChunk(event.getChunk().getX(), event.getChunk().getZ());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent event){
        if (StructureSeedCompatibility.instance.config.DisableVanillaSlimeSpawningInRegions && event.getEntity().getType() == org.bukkit.entity.EntityType.SLIME && event.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL &&
        StructureSeedCompatibility.instance.config.findRegion(event.getEntity().getLocation()) != null) {
            event.setCancelled(true);
        }
    }
}
