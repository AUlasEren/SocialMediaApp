package com.ulas.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateNewPostRequestDto {
    private String userId;
    private String username;
    private String title;
    private String content;
    private String mediaUrl;
}
