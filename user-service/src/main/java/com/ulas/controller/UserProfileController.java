package com.ulas.controller;

import com.ulas.dto.request.ActivateStatusDto;
import com.ulas.dto.request.NewCreateUSerRequestDto;
import com.ulas.dto.request.UserProfileUpdateRequestDto;
import com.ulas.repository.entity.UserProfile;
import com.ulas.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.util.List;

import static com.ulas.constants.ApiUrls.*;
import static com.ulas.constants.ApiUrls.DELETEBYID;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUSerRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestHeader(value = "Authorization")String token) {
        return ResponseEntity.ok(userProfileService.activateStatus(token));
    }
    @GetMapping(FINDALL)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> delete(@RequestParam Long authId){
        return ResponseEntity.ok(userProfileService.delete(authId));
    }

    @GetMapping(FINDBYUSERNAME)
    public ResponseEntity<UserProfile> findByUsername(@RequestParam String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }
    @GetMapping(FINDBYROLE)
    public ResponseEntity<List<UserProfile>> findByRole(@RequestHeader(value = "Authorization") String token, @RequestParam String role){
        return ResponseEntity.ok(userProfileService.findByRole(role,token));
    }


}
