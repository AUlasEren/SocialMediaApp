package com.ulas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5200,"Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200,"Parametre Hatası",HttpStatus.BAD_REQUEST) ,
    USERNAME_DUPLICATE(4210,"Böyle bir kullanıcı adı mevcut",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4211,"Böyle bir kullanıcı bulunamadi" ,HttpStatus.NOT_FOUND),
    USER_NOT_CREATED(4212,"Kullanıcı olusturalamadı" ,HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4213,"Geçersiz Token" ,HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
     HttpStatus httpStatus;
}
