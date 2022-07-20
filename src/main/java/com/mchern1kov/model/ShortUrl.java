package com.mchern1kov.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "shortPath")
@ToString
public class ShortUrl {
    private final String shortPath;
    private final String longUrl;
}
