package dev.lone64.LoneLib.common.menu.page.impl;

import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemStackPage implements Paginate<ItemStack> {
    private int count, page;
    private List<ItemStack> items;

    @Override
    public ItemStackPage setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public ItemStackPage setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public ItemStackPage setItems(List<ItemStack> items) {
        this.items = items;
        return this;
    }

    @Override
    public ItemStackPage with(int count, List<ItemStack> items) {
        return with(count, 1, items);
    }

    @Override
    public ItemStackPage with(int count, int page, List<ItemStack> items) {
        this.count = count;
        this.page = page;
        this.items = items;
        return this;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public List<ItemStack> getItems() {
        return items;
    }
}