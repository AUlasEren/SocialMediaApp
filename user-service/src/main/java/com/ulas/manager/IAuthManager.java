package com.ulas.manager;

import com.ulas.dto.request.UpdateEmailOrUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ulas.constants.ApiUrls.FINDBYROLE;

@FeignClient(name= "userprofile-auth", url ="http://localhost:7071/api/v1/auth",decode404 = true)
public interface IAuthManager {

    @PutMapping("/updateemailorusername")
    public ResponseEntity<Boolean> updateEmailOrUsername(@RequestHeader(value = "Authorization") String token,@RequestBody UpdateEmailOrUsernameRequestDto dto);
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<Long>> findbyRole(@RequestHeader(value = "Authorization") String token, @RequestParam String role);
}
