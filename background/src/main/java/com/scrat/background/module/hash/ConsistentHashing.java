package com.scrat.background.module.hash;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {
    private static final String[] servers = {
            "192.168.0.1:8888", "192.168.0.2:8888", "192.168.0.3:8888"
    };

    private static final SortedMap<Integer, String> sortedMap = new TreeMap<>();

    static {
        for (String server : servers) {
            int hash = getHash(server);
            System.out.println("hash[" + server + "]=" + hash);
            sortedMap.put(hash, server);
        }
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    private static String getServer(String key) {
        int hash = getHash(key);
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            Integer i = sortedMap.firstKey();
            return sortedMap.get(i);
        } else {
            Integer i = subMap.firstKey();
            return subMap.get(i);
        }
    }

    public static void main(String[] args) {
        String[] keys = {"Hello", "World", "!", "Summer"};
        for (String key : keys) {
            System.out.println("hash[" + key + "]=" + getHash(key) + ", route to [" + getServer(key) + "]");
        }
    }
}
