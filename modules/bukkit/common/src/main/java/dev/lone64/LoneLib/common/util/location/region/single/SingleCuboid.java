package dev.lone64.LoneLib.common.util.location.region.single;

import org.bukkit.Location;

public class SingleCuboid implements Region {
    private int minX, maxX;
    private int minY, maxY;
    private int minZ, maxZ;

    private boolean protect;

    public SingleCuboid(Location loc, int radius, boolean protect) {
        Location l1 = loc.clone().subtract(radius, radius, radius);
        Location l2 = loc.clone().add(radius, radius, radius);

        minX = Math.min(l1.getBlockX(), l2.getBlockX());
        maxX = Math.max(l1.getBlockX(), l2.getBlockX());

        minY = Math.min(l1.getBlockY(), l2.getBlockY());
        maxY = Math.max(l1.getBlockY(), l2.getBlockY());

        minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
        maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

        this.protect = protect;
    }

    @Override
    public void setMinX(int minX) {
        this.minX = minX;
    }

    @Override
    public void setMinY(int minY) {
        this.minY = minY;
    }

    @Override
    public void setMinZ(int minZ) {
        this.minZ = minZ;
    }

    @Override
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    @Override
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    @Override
    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }

    @Override
    public void setProtected(boolean protect) {
        this.protect = protect;
    }

    @Override
    public int getMinX() {
        return minX;
    }

    @Override
    public int getMinY() {
        return minY;
    }

    @Override
    public int getMinZ() {
        return minZ;
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }

    @Override
    public int getMaxZ() {
        return maxZ;
    }

    @Override
    public boolean isProtected() {
        return protect;
    }

    @Override
    public boolean isInRegion(Location l) {
        return (l.getBlockX() <= maxX && l.getBlockX() >= minX) && (l.getY() <= maxY && l.getY() >= minY) && (l.getBlockZ() <= maxZ && l.getBlockZ() >= minZ);
    }
}