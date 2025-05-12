package dev.lone64.LoneLib.common.nbt.item;

import dev.lone64.LoneLib.common.nbt.PersistentDataContainer;
import dev.lone64.LoneLib.common.util.item.ItemUtil;
import dev.lone64.LoneLib.common.util.location.LocationUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class ItemPersistentDataContainer implements PersistentDataContainer {
    private final ItemStack itemStack;

    @Override
    public <P, C> void set(String key, PersistentDataType<P, C> type, C value) {
        var namespacedKey = NamespacedKey.fromString("LoneLib:%s".formatted(key));
        Objects.requireNonNull(namespacedKey, "NamespacedKey를 불러올 수 없습니다.");
        this.getItemMeta().getPersistentDataContainer().set(namespacedKey, type, value);
        this.getResult().setItemMeta(this.getItemMeta());
    }

    @Override
    public void remove(String key) {
        var namespacedKey = NamespacedKey.fromString("LoneLib:%s".formatted(key));
        Objects.requireNonNull(namespacedKey, "NamespacedKey를 불러올 수 없습니다.");
        this.getItemMeta().getPersistentDataContainer().remove(namespacedKey);
        this.getResult().setItemMeta(this.getItemMeta());
    }

    @Override
    public <P, C> C get(String key, PersistentDataType<P, C> type) {
        var namespacedKey = NamespacedKey.fromString("LoneLib:%s".formatted(key));
        Objects.requireNonNull(namespacedKey, "NamespacedKey를 불러올 수 없습니다.");
        return this.getItemMeta().getPersistentDataContainer().get(namespacedKey, type);
    }

    @Override
    public boolean has(String key) {
        var namespacedKey = NamespacedKey.fromString("LoneLib:%s".formatted(key));
        Objects.requireNonNull(namespacedKey, "NamespacedKey를 불러올 수 없습니다.");
        return this.getItemMeta().getPersistentDataContainer().has(namespacedKey);
    }

    @Override
    public <P, C> boolean has(String key, PersistentDataType<P, C> type) {
        var namespacedKey = NamespacedKey.fromString("LoneLib:%s".formatted(key));
        Objects.requireNonNull(namespacedKey, "NamespacedKey를 불러올 수 없습니다.");
        return this.getItemMeta().getPersistentDataContainer().has(namespacedKey, type);
    }

    @Override
    public void setString(String key, String value) {
        Objects.requireNonNull(value, "String 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.STRING, value);
    }

    @Override
    public void setInteger(String key, int value) {
        this.set(key, PersistentDataType.INTEGER, value);
    }

    @Override
    public void setDouble(String key, double value) {
        this.set(key, PersistentDataType.DOUBLE, value);
    }

    @Override
    public void setFloat(String key, float value) {
        this.set(key, PersistentDataType.FLOAT, value);
    }

    @Override
    public void setLong(String key, long value) {
        this.set(key, PersistentDataType.LONG, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        this.set(key, PersistentDataType.BOOLEAN, value);
    }

    @Override
    public void setUUID(String key, UUID value) {
        Objects.requireNonNull(value, "UUID 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.STRING, value.toString());
    }

    @Override
    public void setWorld(String key, World value) {
        Objects.requireNonNull(value, "World 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.STRING, value.getName());
    }

    @Override
    public void setLocation(String key, Location value) {
        Objects.requireNonNull(value, "Location 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.STRING, LocationUtil.serialize(value));
    }

    @Override
    public void setItemStack(String key, ItemStack value) {
        Objects.requireNonNull(value, "ItemStack 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.STRING, ItemUtil.serialize(value));
    }

    @Override
    public void setStringList(String key, List<String> values) {
        Objects.requireNonNull(values, "StringList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.strings(), values);
    }

    @Override
    public void setIntegerList(String key, List<Integer> values) {
        Objects.requireNonNull(values, "IntegerList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.integers(), values);
    }

    @Override
    public void setDoubleList(String key, List<Double> values) {
        Objects.requireNonNull(values, "DoubleList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.doubles(), values);
    }

    @Override
    public void setFloatList(String key, List<Float> values) {
        Objects.requireNonNull(values, "FloatList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.floats(), values);
    }

    @Override
    public void setLongList(String key, List<Long> values) {
        Objects.requireNonNull(values, "LongList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.longs(), values);
    }

    @Override
    public void setBooleanList(String key, List<Boolean> values) {
        Objects.requireNonNull(values, "BooleanList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.booleans(), values);
    }

    @Override
    public void setUUIDList(String key, List<UUID> values) {
        Objects.requireNonNull(values, "UUIDList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.strings(), values.stream().map(UUID::toString).toList());
    }

    @Override
    public void setWorldList(String key, List<World> values) {
        Objects.requireNonNull(values, "WorldList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.strings(), values.stream().map(World::getName).toList());
    }

    @Override
    public void setLocationList(String key, List<Location> values) {
        Objects.requireNonNull(values, "LocationList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.strings(), values.stream().map(LocationUtil::serialize).toList());
    }

    @Override
    public void setItemStackList(String key, List<ItemStack> values) {
        Objects.requireNonNull(values, "ItemStackList 값이 존재하지 않습니다.");
        this.set(key, PersistentDataType.LIST.strings(), values.stream().map(ItemUtil::serialize).toList());
    }

    @Override
    public String getString(String key) {
        if (!this.has(key)) return null;
        return this.get(key, PersistentDataType.STRING);
    }

    @Override
    public int getInteger(String key) {
        if (!this.has(key)) return 0;
        return this.get(key, PersistentDataType.INTEGER);
    }

    @Override
    public double getDouble(String key) {
        if (!this.has(key)) return 0.0;
        return this.get(key, PersistentDataType.DOUBLE);
    }

    @Override
    public float getFloat(String key) {
        if (!this.has(key)) return 0.0F;
        return this.get(key, PersistentDataType.FLOAT);
    }

    @Override
    public long getLong(String key) {
        if (!this.has(key)) return 0L;
        return this.get(key, PersistentDataType.LONG);
    }

    @Override
    public boolean getBoolean(String key) {
        if (!this.has(key)) return false;
        return this.get(key, PersistentDataType.BOOLEAN);
    }

    @Override
    public UUID getUUID(String key) {
        if (!this.has(key)) return null;
        return UUID.fromString(this.getString(key));
    }

    @Override
    public World getWorld(String key) {
        if (!this.has(key)) return null;
        return Bukkit.getWorld(this.getString(key));
    }

    @Override
    public Location getLocation(String key) {
        if (!this.has(key)) return null;
        return LocationUtil.deserialize(this.getString(key));
    }

    @Override
    public ItemStack getItemStack(String key) {
        if (!this.has(key)) return null;
        return ItemUtil.deserialize(this.getString(key));
    }

    @Override
    public List<String> getStringList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.strings());
    }

    @Override
    public List<Integer> getIntegerList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.integers());
    }

    @Override
    public List<Double> getDoubleList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.doubles());
    }

    @Override
    public List<Float> getFloatList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.floats());
    }

    @Override
    public List<Long> getLongList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.longs());
    }

    @Override
    public List<Boolean> getBooleanList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.booleans());
    }

    @Override
    public List<UUID> getUUIDList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.strings()).stream().map(UUID::fromString).toList();
    }

    @Override
    public List<World> getWorldList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.strings()).stream().map(Bukkit::getWorld).toList();
    }

    @Override
    public List<Location> getLocationList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.strings()).stream().map(LocationUtil::deserialize).toList();
    }

    @Override
    public List<ItemStack> getItemStackList(String key) {
        if (!this.has(key)) return new ArrayList<>();
        return this.get(key, PersistentDataType.LIST.strings()).stream().map(ItemUtil::deserialize).toList();
    }

    @Override
    public ItemMeta getItemMeta() {
        return this.getResult().getItemMeta();
    }

    @Override
    public ItemStack getResult() {
        return this.itemStack;
    }
}