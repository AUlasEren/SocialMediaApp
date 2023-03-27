package com.ulas.manager;

import com.ulas.dto.request.ActivateStatusDto;
import com.ulas.dto.request.NewCreateUSerRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ulas.constants.ApiUrls.*;

@FeignClient(url="http://localhost:7072/api/v1/user",decode404 = true,name = "auth-userprofile")
public interface IUserManager {
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUSerRequestDto dto);
    @PostMapping (ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateStatusDto dto);
    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> delete(@RequestParam Long authId);
}
