package dev.lone64.LoneLib.common.menu.page;

import dev.lone64.LoneLib.common.menu.page.impl.ItemStackPage;
import dev.lone64.LoneLib.common.menu.page.impl.MaterialPage;
import dev.lone64.LoneLib.common.util.java.ListUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Paginate<T> {
    Paginate<T> setPage(int page);
    Paginate<T> setCount(int count);
    Paginate<T> setItems(List<T> items);

    Paginate<T> with(int count, List<T> items);
    Paginate<T> with(int count, int page, List<T> items);

    int getPage();
    int getCount();
    List<T> getItems();

    default int getLastPage() {
        var items = new ListUtil<T>().getChunkedList(getItems(), getCount());
        return items.isEmpty() ? 1 : items.size();
    }

    default List<T> getFilteredItems() {
        var items = new HashMap<Integer, List<T>>();
        var paginates = new ListUtil<T>().getChunkedList(getItems(), getCount());
        for (int i = 0; i < paginates.size(); i++) {
            items.put(i + 1, paginates.get(i));
        }
        return items.get(getPage()) != null ? items.get(getPage()) : new ArrayList<>();
    }

    static MaterialPage makeMaterial() {
        return new MaterialPage();
    }

    static ItemStackPage makeItem() {
        return new ItemStackPage();
    }

    static <T> Paginate<T> makeCustom(Class<Paginate<T>> paginateClass) {
        try {
            return paginateClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}