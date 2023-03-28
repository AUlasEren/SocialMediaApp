package com.ulas.controller;

import com.ulas.dto.request.*;
import com.ulas.dto.response.RegisterResponseDto;
import com.ulas.repository.entity.Auth;
import com.ulas.repository.enums.ERoles;
import com.ulas.service.AuthService;
import com.ulas.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.ulas.constants.ApiUrls.*;

/**
 * Login methodunu düzeltelim bize bir token üretip token dönsün ayrıca active kullanıcılar login olabilsin
 *
 */
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;

//    @PostMapping(REGISTER)
//    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
//        return ResponseEntity.ok(authService.register(dto));
//    }
    @PostMapping(REGISTER+"rabbitmq")
    public ResponseEntity<RegisterResponseDto> registerWithRabbitMq(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.registerwithRabbitMq(dto));
    }

    @PostMapping(DOLOGIN)
    public ResponseEntity<String> login(@RequestBody DoLoginRequestDto dto) {
        return ResponseEntity.ok(authService.doLogin(dto));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/createtoken")
    public ResponseEntity<String> createToken(Long id, ERoles roles){
        return ResponseEntity.ok(jwtTokenManager.createToken(id,roles).get());
    }
    @GetMapping("/createtoken2")
    public ResponseEntity<String> createToken2(Long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
    }
    @GetMapping("/getidfromtoken")
    public ResponseEntity<Long> createIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getrolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
    }


    @PutMapping("/updateemailorusername")
    public ResponseEntity<Boolean> updateEmailOrUsername(@RequestHeader(value = "Authorization") String token,@RequestBody UpdateEmailOrUsernameRequestDto dto){
        return ResponseEntity.ok(authService.updateEmailOrUsername(dto));
    }
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> delete(long id){
        return ResponseEntity.ok(authService.delete(id));
    }
    @GetMapping("/redis")
    @Cacheable(value= "redisexample")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/redisDelete")
    @CacheEvict(cacheNames = "redisexample",allEntries = true)
    public void redisDelete(){

    }
    @GetMapping("/redisDelete2")
    public Boolean redisDelete2(){
        try{
        //cacheManager.getCache("redisexample").clear(); // aynı isimle cachelenmiş bütün verileri siler
            cacheManager.getCache("redisexample").evict("nwusr1");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<Long>> findbyRole(@RequestHeader(value = "Authorization") String token, @RequestParam String role){
        return ResponseEntity.ok(authService.findByRole(role));
    }


}
