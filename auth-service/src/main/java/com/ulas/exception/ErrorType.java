package com.ulas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5100,"Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre Hatası",HttpStatus.BAD_REQUEST) ,
    LOGIN_ERROR(4110,"Kullancı adı veya sifre hatali!!!",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4111,"Böyle bir kullanıcı adı mevcut",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4112,"Böyle bir kullanıcı bulunamadi" ,HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4113,"Aktivasyon kod hatası" ,HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4114,"Geçersiz Token" ,HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4115,"Token Oluşturalamadı" ,HttpStatus.BAD_REQUEST),
    NOT_ACTIVATED_ACCOUNT(4116,"Aktif olmayan hesap" ,HttpStatus.FORBIDDEN),
    USER_NOT_CREATED(4117,"Kullanıcı olusturulamadı" ,HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(4118,"Role bulunamadı" ,HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
     HttpStatus httpStatus;
}
