package com.ulas.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMailModel implements Serializable {
    private String email;
    private String username;
    private String activationCode;
}
