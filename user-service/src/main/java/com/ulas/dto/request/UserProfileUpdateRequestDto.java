package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequestDto {
    private String token;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;
}
