package com.ulas.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel implements Serializable {
    private Long authId;
    private String username;
    private String email;
}
