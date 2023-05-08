package me.lucthesloth.ssc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Configuration {
    public static String worldName(){
        return StructureSeedCompatibility.instance.getConfig().getString("worldName", "world");
    }
    public static boolean DisableVanillaSlimeSpawningInRegions(){
        return StructureSeedCompatibility.instance.getConfig().getBoolean("DisableVanillaSlimeSpawningInRegions", true);
    }
    public static Region findRegion(int x, int z) {
        @SuppressWarnings("unchecked") List<Region> regions = (List<Region>) StructureSeedCompatibility.instance.getConfig().getList("slimeRegions", Collections.emptyList());
        for (Region region : regions) {
            if (region.isInside(x, z) != null) {
                return region;
            }
        }
        return null;
    }
    public static Region findRegion(org.bukkit.Location location) {
        return findRegion(location.getBlockX(), location.getBlockZ());
    }
    public static class Region {
        public int minX;
        public int minZ;
        public int maxX;
        public int maxZ;

        public Long seed;

        public Region(int minX, int maxX, int minZ, int maxZ, Long seed) {
            this.minX = minX;
            this.maxX = maxX;
            this.minZ = minZ;
            this.maxZ = maxZ;
            this.seed = seed;
        }
        public Region isInside(int x, int z) {
            if (minX <= x && x <= maxX && minZ <= z && z <= maxZ) {
                return this;
            }
            return null;
        }
    }
}

