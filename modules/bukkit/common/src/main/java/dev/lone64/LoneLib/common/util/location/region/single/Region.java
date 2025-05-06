package dev.lone64.LoneLib.common.util.location.region.single;

import org.bukkit.Location;

public interface Region {
    void setMinX(int minX);
    void setMinY(int minY);
    void setMinZ(int minZ);

    void setMaxX(int maxX);
    void setMaxY(int maxY);
    void setMaxZ(int maxZ);

    void setProtected(boolean protect);

    int getMinX();
    int getMinY();
    int getMinZ();

    int getMaxX();
    int getMaxY();
    int getMaxZ();

    boolean isProtected();
    boolean isInRegion(Location location);
}