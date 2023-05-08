package me.lucthesloth.ssc.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Slab;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Consumer;

public class Slime {

    /**
     * Checks if a slime can spawn at the given location,
     * by checking if the location is inside block in a 3x2.1x3 area
     * centered on the block
     * @param loc The location to check
     * @param callback The callback function, called with the location if it is valid, or null if it is not
     *
     */

    public static boolean checkSpawningSpace(Location loc, @Nullable Consumer<Location> callback){
            if (loc.getBlockY() >=41) return false;
            Location helper = loc.clone();
            boolean valid = true;
            BoundingBox box = new BoundingBox(loc.getX() - 1, loc.getY()-0.1, loc.getZ() - 1, loc.getX() + 1, loc.getY() + 2.2, loc.getZ() + 1);
            for (int x = -1; x <= 1; x++){
                for (int z = -1; z <= 1; z++){
                    for (int y = 0; y <= 2; y++){
                        helper.add(x, y, z);
                        if (helper.getBlock().getType() != Material.AIR &&
                        helper.getBlock().getType() != Material.CAVE_AIR &&
                        helper.getBlock().getBoundingBox().overlaps(box)) {
                            valid = false;
                            break;
                        }
                        helper.subtract(x, y, z);
                    }
                    helper.subtract(0, 1, 0);
                    if (helper.getBlock().getType() == Material.BEDROCK || helper.getBlock().getType() == Material.SNOW ||
                    helper.getBlock() instanceof Leaves || !helper.getBlock().isSolid() || helper.getBlock().isLiquid() ||
                     !helper.getBlock().getBoundingBox().overlaps(box) ||
                    helper.getBlock().getType() == Material.BARRIER) {
                        valid = false;
                        break;
                    }
                    helper.add(0, 1, 0);
                    if (!valid) break;
                }
                if (!valid) break;
            }
            if (!loc.getBlock().getRelative(BlockFace.DOWN).isSolid()) valid = false;
            if (callback != null){
                if (valid) callback.accept(loc);
                else callback.accept(null);
            }
            return valid;
    }
    public static boolean isSlimeChunk(Chunk c, long seed){
        int x = c.getX();
        int z = c.getZ();
        Random rnd = new Random(
                seed +
                        (int) (x * x * 0x4c1906) +
                        (int) (x * 0x5ac0db) +
                        (int) (z * z) * 0x4307a7L +
                        (int) (z * 0x5f24f) ^ 0x3ad8025fL
        );
        return rnd.nextInt(10) == 0;
    }
}
