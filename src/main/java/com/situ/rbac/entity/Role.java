package com.situ.rbac.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
public class Role {
    private Integer id;
    private String name;
    private String code;

    private Set<Permission> permissions;
}
