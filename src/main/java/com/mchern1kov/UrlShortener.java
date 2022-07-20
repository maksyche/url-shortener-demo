package com.mchern1kov;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mchern1kov.config.GuiceModule;
import com.mchern1kov.server.UrlShortenerServer;

public class UrlShortener {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());
        UrlShortenerServer server = injector.getInstance(UrlShortenerServer.class);
        server.start();
    }

}