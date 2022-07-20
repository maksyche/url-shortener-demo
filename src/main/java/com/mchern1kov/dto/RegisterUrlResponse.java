package com.mchern1kov.dto;

import com.mchern1kov.model.ShortUrl;
import lombok.*;

import static com.mchern1kov.server.UrlShortenerServer.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterUrlResponse {

    private static final String PROTOCOL_PREFIX = "http://";

    private String shortUrl;
    private String longUrl;

    public RegisterUrlResponse(ShortUrl shortUrl) {
        this.shortUrl = PROTOCOL_PREFIX + HOST + ":" + PORT + REDIRECT_PATH + shortUrl.getShortPath();
        this.longUrl = shortUrl.getLongUrl();
    }
}
