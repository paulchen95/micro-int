package com.chen.mint;

import java.util.HashMap;
import java.util.Map;

public class VarStore {
    private static final VarStore instance = new VarStore();
    private Map<String, Integer> store = new HashMap<String, Integer>();

    private VarStore(){}

    public static VarStore getInstance() {
        return instance;
    }

    public void reset() {
        store.clear();
    }

    public void put(String var, int num) {
        store.put(var, num);
    }

    public int get(String var) {
        return store.get(var);
    }
}
