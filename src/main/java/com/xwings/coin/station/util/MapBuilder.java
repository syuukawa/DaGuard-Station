package com.xwings.coin.station.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private transient Map<K, V> map;

    /**
     * The caller references the MapBuilder using <tt>MapBuilder.standard()</tt>, and
     * so on. Thus, the caller should be prevented from constructing objects of
     * this class, by declaring this private constructor.
     */
    private MapBuilder() {
        this.map = new HashMap<>();
    }

    public MapBuilder<K, V> with(K key, V value) {
        this.map.put(key, value);

        return this;
    }

    public Map<K, V> create() {
        return this.map;
    }

    public static <K, V> MapBuilder<K, V> standard() {
        return new MapBuilder<>();
    }

}
