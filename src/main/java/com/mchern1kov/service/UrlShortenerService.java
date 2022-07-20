package com.mchern1kov.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mchern1kov.dao.ShortUrlDao;
import com.mchern1kov.dto.RegisterUrlRequest;
import com.mchern1kov.dto.RegisterUrlResponse;
import com.mchern1kov.model.ShortUrl;

import java.util.Random;

@Singleton
public class UrlShortenerService {
    private static final int LEFT_LIMIT = 48; // numeral '0'
    private static final int RIGHT_LIMIT = 122; // letter 'z'
    private static final int RANDOM_STRING_LENGTH = 4;

    private final ShortUrlDao shortUrlDao;
    private final Random random;

    @Inject
    public UrlShortenerService(ShortUrlDao shortUrlDao) {
        this.shortUrlDao = shortUrlDao;
        this.random = new Random();
    }

    public String getLongUrl(String shortPath) {
        return shortUrlDao.getLongUrl(shortPath);
    }

    public synchronized RegisterUrlResponse registerShortUrl(RegisterUrlRequest registerUrlRequest) {
        String shortPath = generateRandomAlphaNumericString();
        while (shortUrlDao.contains(shortPath)) { // In case of collisions
            shortPath = generateRandomAlphaNumericString();
        }
        ShortUrl shortUrl = new ShortUrl(shortPath, registerUrlRequest.getLongUrl());
        shortUrlDao.save(shortUrl);
        return new RegisterUrlResponse(shortUrl);
    }

    public void deleteShotUrl(String shortPath) {
        shortUrlDao.delete(shortPath);
    }

    private String generateRandomAlphaNumericString() {
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // Exclude some symbols
                .limit(RANDOM_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

}
