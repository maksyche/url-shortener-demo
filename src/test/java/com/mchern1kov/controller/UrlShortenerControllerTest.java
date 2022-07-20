package com.mchern1kov.controller;

import com.mchern1kov.dto.RegisterUrlRequest;
import com.mchern1kov.dto.RegisterUrlResponse;
import com.mchern1kov.service.UrlShortenerService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerControllerTest {

    @Mock
    private UrlShortenerService urlShortenerService;

    @Mock
    private Context context;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    @Test
    public void registerShortUrlTest() {
        String longUrl = "http://somedomain/somelongpath";
        String shortUrl = "http://thisdomain/someshortpath";
        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest(longUrl);

        RegisterUrlResponse registerUrlResponse = new RegisterUrlResponse(shortUrl, longUrl);

        Mockito.when(context.bodyAsClass(RegisterUrlRequest.class)).thenReturn(registerUrlRequest);
        Mockito.when(urlShortenerService.registerShortUrl(Mockito.any())).thenReturn(registerUrlResponse);
        Mockito.when(context.status(Mockito.anyInt())).thenReturn(context);

        urlShortenerController.registerShortUrl(context);

        Mockito.verify(context).status(201);
        Mockito.verify(context).json(Mockito.eq(registerUrlResponse));
    }

    @Test
    public void deleteShortUrlTest() {
        String shortPath = "someshortpath";

        Mockito.when(context.pathParam("shortPath")).thenReturn(shortPath);

        urlShortenerController.deleteShortUrl(context);

        Mockito.verify(urlShortenerService).deleteShotUrl(shortPath);
        Mockito.verify(context).status(204);
    }

    @Test
    public void redirectTest() {
        String shortPath = "someshortpath";
        String longUrl = "http://somedomain/somelongpath";

        Mockito.when(context.pathParam("shortPath")).thenReturn(shortPath);
        Mockito.when(urlShortenerService.getLongUrl(shortPath)).thenReturn(longUrl);

        urlShortenerController.redirect(context);

        Mockito.verify(context).redirect(longUrl);
    }

    @Test
    public void redirectNotFoundTest() {
        String shortPath = "someshortpath";

        Mockito.when(context.pathParam("shortPath")).thenReturn(shortPath);
        Mockito.when(urlShortenerService.getLongUrl(shortPath)).thenReturn(null);

        Assertions.assertThrows(NotFoundResponse.class, () -> urlShortenerController.redirect(context));
    }
}
