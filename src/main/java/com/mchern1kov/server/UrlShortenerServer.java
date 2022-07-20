package com.mchern1kov.server;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mchern1kov.controller.UrlShortenerController;
import io.javalin.Javalin;

@Singleton
public class UrlShortenerServer {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;
    public static final String REDIRECT_PATH = "/redirect/";

    private final UrlShortenerController urlShortenerController;

    @Inject
    public UrlShortenerServer(UrlShortenerController urlShortenerController) {
        this.urlShortenerController = urlShortenerController;
    }

    public void start() {
        configureApplication().start(HOST, PORT);
    }

    Javalin configureApplication() {
        Javalin app = Javalin.create();
        app.get("/health", ctx -> ctx.result("healthy"));

        app.post("/register", urlShortenerController::registerShortUrl);
        app.delete("/{shortPath}", urlShortenerController::deleteShortUrl);
        app.get(REDIRECT_PATH + "{shortPath}", urlShortenerController::redirect);
        return app;
    }
}
