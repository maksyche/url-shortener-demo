package com.mchern1kov.dao;

import com.google.inject.Singleton;
import com.mchern1kov.model.ShortUrl;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ShortUrlDao {

    private final ConcurrentHashMap<String, String> storage = new ConcurrentHashMap<>();

    public String getLongUrl(String shortPath) {
        return storage.get(shortPath);
    }

    public boolean contains(String shortPath) {
        return storage.containsKey(shortPath);
    }

    public void save(ShortUrl shortUrl) {
        storage.put(shortUrl.getShortPath(), shortUrl.getLongUrl());
    }

    public void delete(String shortPath) {
        storage.remove(shortPath);
    }

}
