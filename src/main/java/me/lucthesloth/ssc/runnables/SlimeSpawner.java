package me.lucthesloth.ssc.runnables;

import me.lucthesloth.ssc.Configuration;
import me.lucthesloth.ssc.StructureSeedCompatibility;
import me.lucthesloth.ssc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import java.util.*;

public class SlimeSpawner implements Runnable{
    private final Random random = new Random();
    public static Set<Utils.KeyPair> slimeChunks = new HashSet<>();
    public static boolean addSlimeChunk(int x, int z) {
        if (slimeChunks.stream().filter(l -> l.x == x && l.z == z).count() == 0) {
            slimeChunks.add(new Utils.KeyPair(x, z));
            return true;
        }
        return false;
    }
    public static boolean removeSlimeChunk(int x, int z) {
        return slimeChunks.removeIf(l -> l.x == x && l.z == z);
    }
    @Override
    public void run() {
        Queue<Utils.KeyPair> queue = new LinkedList<>(slimeChunks);
        Chunk chunk;
        int x,z,y;
        Location temp;
        while (!queue.isEmpty()) {
            Utils.KeyPair keyPair = queue.poll();
            chunk = StructureSeedCompatibility.instance.getServer().getWorld(Configuration.worldName()).getChunkAt(keyPair.x, keyPair.z);
            if (chunk.isLoaded()) {
                if (random.nextInt(10) == 0) {
                    for (int i = 0; i < 10; i++) {
                        x = random.nextInt(16);
                        z = random.nextInt(16);
                        y = chunk.getWorld().getHighestBlockYAt(x,z) + 5;
                        if (y <= -60) continue;
                        temp = chunk.getBlock(x, random.nextInt(-61, y), z).getLocation();
                        if (me.lucthesloth.ssc.utils.Slime.checkSpawningSpace(temp, this::spawnSlime)) {
                            break;
                        }
                    }
                }
            }
        }
    }
    public void spawnSlime(Location loc) {
        if (loc == null) return;
        if (loc.getWorld() == null) return;
        Bukkit.getScheduler().runTask(StructureSeedCompatibility.instance, () -> {
            Optional<Player> p = loc.getNearbyPlayers(96).stream().findFirst();
            if (p.isEmpty()) return;
            if (p.get().getLocation().getNearbyEntitiesByType(Slime.class, 96).stream().count() >= 20) return;
            if (p.get().getLocation().distance(loc) < 24) return;
            if (!loc.getNearbyLivingEntities(3).isEmpty()) return;
            Slime e = ((Slime) loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.SLIME));
            e.setSize(me.lucthesloth.ssc.utils.Slime.getSlimeSize(loc.getWorld().getDifficulty()));
            Bukkit.getLogger().info(String.format("Slime spawned at %d %d %d", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        });


    }
}

