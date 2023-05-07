package me.lucthesloth.ssc.utils;

import me.lucthesloth.ssc.StructureSeedCompatibility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import javax.annotation.Nullable;
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
    public static void checkSpawningSpace(Location loc, Consumer<Location> callback){
        Bukkit.getScheduler().runTaskAsynchronously(StructureSeedCompatibility.instance, () -> {
            Location helper = loc.clone();
            boolean valid = true;
            for (int x = -1; x <= 1; x++){
                for (int z = -1; z <= 1; z++){
                    for (int y = 0; y <= 1; y++){
                        helper.add(x, y, z);
                        if (!helper.getBlock().isPassable() || helper.getBlock().isLiquid() || helper.getBlock().isSolid()) {
                            valid = false;
                            break;
                        }
                        helper.subtract(x, y, z);
                    }
                    helper.add(x, 2.1, z);
                    if (!helper.getBlock().isPassable() ||
                            helper.getBlock().isLiquid() ||
                            (helper.getBlock().isSolid() && !(helper.getBlock() instanceof org.bukkit.block.data.type.Slab)) ||
                            (helper.getBlock() instanceof org.bukkit.block.data.type.Slab && ((org.bukkit.block.data.type.Slab) helper.getBlock().getBlockData()).getType() != org.bukkit.block.data.type.Slab.Type.TOP)) {
                        valid = false;
                    }
                    helper.subtract(x, 2.1, z);
                    if (!valid) break;
                }
                if (!valid) break;
            }
            if (!loc.getBlock().getRelative(BlockFace.DOWN).isSolid()) valid = false;
            if (valid) callback.accept(loc);
            else callback.accept(null);
        });
    }
}
