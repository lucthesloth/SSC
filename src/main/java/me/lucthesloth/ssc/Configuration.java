package me.lucthesloth.ssc;

import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Configuration {
    public boolean slimeChunkSpawning = true;
    public List<Region> regions = Collections.emptyList();
    public boolean DisableVanillaSlimeSpawningInRegions = true;
    public String worldName = "world";
    public Configuration() {
        regions = new LinkedList<>();
        regions.add(new Region(-10000, 10000, -10000, 10000, 1938308378592162354L));
    }
    public Configuration(boolean slimeChunkSpawning, List<Region> regions, boolean DisableVanillaSlimeSpawningInRegions, String worldName) {
        this.slimeChunkSpawning = slimeChunkSpawning;
        this.regions = regions;
        this.DisableVanillaSlimeSpawningInRegions = DisableVanillaSlimeSpawningInRegions;
        this.worldName = worldName;
    }

    public Region findRegion(int x, int z) {
        for (Region region : regions) {
            if (region.isInside(x, z) != null) {
                return region;
            }
        }
        return null;
    }
    public Region findRegion(org.bukkit.Location location) {
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

