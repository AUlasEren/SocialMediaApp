package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCreateUSerRequestDto {
    private Long authId;
    private String username;
    private String email;
}
