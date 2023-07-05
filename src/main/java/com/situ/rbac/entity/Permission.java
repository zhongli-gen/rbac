package com.situ.rbac.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Permission {
    private Integer id;
    private String name;
    private String code;
}
