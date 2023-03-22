package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ActivateRequestDto {
    Long id;
    private String activationCode;
}
