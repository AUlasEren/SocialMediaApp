package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateFollowRequestDto {
    private String followId;
    private String token;
}
