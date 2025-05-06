package dev.lone64.LoneLib.common.menu.page.impl;

import dev.lone64.LoneLib.common.menu.page.Paginate;
import org.bukkit.Material;

import java.util.List;

public class MaterialPage implements Paginate<Material> {
    private int count, page;
    private List<Material> items;

    @Override
    public Paginate<Material> setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public Paginate<Material> setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public Paginate<Material> setItems(List<Material> items) {
        this.items = items;
        return this;
    }

    @Override
    public Paginate<Material> with(int count, List<Material> items) {
        return with(count, 1, items);
    }

    @Override
    public Paginate<Material> with(int count, int page, List<Material> items) {
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
    public List<Material> getItems() {
        return items;
    }
}