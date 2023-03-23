package com.ulas.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    Long createDate;
    Long updateDate;
}
