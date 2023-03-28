package com.ulas.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Follow extends BaseEntity {
    @Id
    private String id;
    private String userId;
    private String followId;
}
