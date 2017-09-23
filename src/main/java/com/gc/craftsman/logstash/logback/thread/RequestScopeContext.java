package com.gc.craftsman.logstash.logback.thread;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by cjw on 23/09/2017.
 */
public class RequestScopeContext<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(RequestScopeContext.class);

    private static RequestScopeContext instance = new RequestScopeContext();
    private ThreadLocal<Map<K, V>> localMap = new ThreadLocal<>();
    private ThreadLocal<Set<K>> localSet = new ThreadLocal<>();

    private RequestScopeContext() {
    }

    public static RequestScopeContext getInstance() {
        return instance;
    }

    public void init() {
        localMap.set(Maps.newConcurrentMap());
        localSet.set(Sets.newConcurrentHashSet());
        logger.debug("request scope context init");
    }

    public boolean isReady() {
        return localMap.get() != null;
    }

    public Map<K, V> getLocalMap() {
        return localMap.get();
    }

    public Set<K> getLocalSet() {
        return localSet.get();
    }

    public void setLocalMapAndSet(Map map, Set set) {
        localMap.set(map);
        localSet.set(set);
    }

    public void put(K key, V value) {
        if (localSet.get() == null) {
            localSet.set(Sets.newConcurrentHashSet());
        }

        localSet.get().add(key);

        if (localMap.get() == null) {
            localMap.set(Maps.newConcurrentMap());
        }

        if (value != null) {
            localMap.get().put(key, value);
        }
    }

    public V get(K key) {
        return localMap.get().get(key);
    }

    public boolean containsKey(K key) {
        return localSet.get().contains(key);
    }

    public boolean notContainsKey(K key) {
        return !containsKey(key);
    }

    public void clear() {
        localMap.remove();
        localSet.remove();
        logger.debug("request scope context clear");
    }
}
