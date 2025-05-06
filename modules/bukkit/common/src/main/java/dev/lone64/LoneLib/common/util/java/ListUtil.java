package dev.lone64.LoneLib.common.util.java;

import java.util.ArrayList;
import java.util.List;

public class ListUtil<T> {
    public List<List<T>> getChunkedList(List<T> list, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return chunks;
    }
}