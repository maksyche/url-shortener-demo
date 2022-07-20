package com.mchern1kov.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterUrlRequest {
    private String longUrl;
}
