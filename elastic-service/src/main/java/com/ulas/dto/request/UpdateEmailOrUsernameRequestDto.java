package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateEmailOrUsernameRequestDto {
    Long autId;
    String username;
    String email;
}
