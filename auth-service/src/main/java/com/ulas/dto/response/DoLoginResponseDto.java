package com.ulas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoLoginResponseDto {
        private Long id;
        private String activationCode;
        private String username;
    }

