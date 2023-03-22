package com.ulas.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank
    @Size(min=3,max=20,message="Kullanici adi en az 3 karakter en fazla 20 karakter olmalıdır.")
    String username;
    @Email
    String email;
    @NotBlank
    @Size(min=5,max=32,message="Sifre en az 8 karakter en fazla 32 karakter olmalıdır.")
    String password;
}
