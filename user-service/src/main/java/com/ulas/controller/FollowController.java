package com.ulas.controller;

import com.ulas.dto.request.CreateFollowRequestDto;
import com.ulas.repository.entity.Follow;
import com.ulas.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ulas.constants.ApiUrls.*;

@RestController
@RequestMapping(FOLLOW)
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping(CREATE)
    public ResponseEntity<?> createFollow(@RequestBody CreateFollowRequestDto dto){
        return ResponseEntity.ok(followService.createFollow(dto));
    }
    @GetMapping(FINDALL)
    public ResponseEntity<List<Follow>> findAll(){
        return ResponseEntity.ok(followService.findAll());
    }
}
