package dev.lone64.LoneLib.common.util.location;

import dev.lone64.LoneLib.common.util.math.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    public static String serialize(Location loc) {
        if (loc.getWorld() == null) return null;
        return "%s:%s:%s:%s:%s:%s".formatted(
                loc.getWorld().getName(),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch()
        );
    }

    public static Location deserialize(String loc) {
        String[] data = loc.split(":");
        World world = Bukkit.getWorld(data[0]);
        double x = MathUtil.getDoubleOrElse(data[1], 0);
        double y = MathUtil.getDoubleOrElse(data[2], 0);
        double z = MathUtil.getDoubleOrElse(data[3], 0);
        float yaw = MathUtil.getFloatOrElse(data[4], 0);
        float pitch = MathUtil.getFloatOrElse(data[5], 0);
        return new Location(world, x, y, z, yaw, pitch);
    }
}