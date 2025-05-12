package dev.lone64.LoneLib.common.nbt;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public interface PersistentDataContainer {
    <P, C> void set(String key, PersistentDataType<P, C> type, C value);
    void remove(String key);
    <P, C> C get(String key, PersistentDataType<P, C> type);
    boolean has(String key);
    <P, C> boolean has(String key, PersistentDataType<P, C> type);

    void setString(String key, String value);
    void setInteger(String key, int value);
    void setDouble(String key, double value);
    void setFloat(String key, float value);
    void setLong(String key, long value);
    void setBoolean(String key, boolean value);
    void setUUID(String key, UUID value);
    void setWorld(String key, World value);
    void setLocation(String key, Location value);
    void setItemStack(String key, ItemStack value);

    void setStringList(String key, List<String> values);
    void setIntegerList(String key, List<Integer> values);
    void setDoubleList(String key, List<Double> values);
    void setFloatList(String key, List<Float> values);
    void setLongList(String key, List<Long> values);
    void setBooleanList(String key, List<Boolean> values);
    void setUUIDList(String key, List<UUID> values);
    void setWorldList(String key, List<World> values);
    void setLocationList(String key, List<Location> values);
    void setItemStackList(String key, List<ItemStack> values);

    String getString(String key);
    int getInteger(String key);
    double getDouble(String key);
    float getFloat(String key);
    long getLong(String key);
    boolean getBoolean(String key);
    UUID getUUID(String key);
    World getWorld(String key);
    Location getLocation(String key);
    ItemStack getItemStack(String key);

    List<String> getStringList(String key);
    List<Integer> getIntegerList(String key);
    List<Double> getDoubleList(String key);
    List<Float> getFloatList(String key);
    List<Long> getLongList(String key);
    List<Boolean> getBooleanList(String key);
    List<UUID> getUUIDList(String key);
    List<World> getWorldList(String key);
    List<Location> getLocationList(String key);
    List<ItemStack> getItemStackList(String key);

    default ItemMeta getItemMeta() {return null;}
}