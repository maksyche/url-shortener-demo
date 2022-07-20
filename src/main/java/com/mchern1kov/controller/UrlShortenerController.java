package com.mchern1kov.controller;

import com.google.inject.Inject;
import com.mchern1kov.dto.RegisterUrlRequest;
import com.mchern1kov.service.UrlShortenerService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Inject
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    public void registerShortUrl(Context ctx) {
        RegisterUrlRequest registerUrlRequest = ctx.bodyAsClass(RegisterUrlRequest.class);
        ctx.status(201).json(urlShortenerService.registerShortUrl(registerUrlRequest));
    }

    public void deleteShortUrl(Context ctx) {
        String shortPath = ctx.pathParam("shortPath");
        urlShortenerService.deleteShotUrl(shortPath);
        ctx.status(204);
    }

    public void redirect(Context ctx) {
        String shortPath = ctx.pathParam("shortPath");
        String longUrl = urlShortenerService.getLongUrl(shortPath);
        if (longUrl == null) {
            throw new NotFoundResponse();
        }
        ctx.redirect(longUrl);
    }
}
