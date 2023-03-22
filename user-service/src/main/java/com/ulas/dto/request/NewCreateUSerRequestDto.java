package com.ulas.dto.request;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCreateUSerRequestDto {
    Long authId;
    private String username;
    private String email;
}
