package com.situ.rbac.service;

import com.github.pagehelper.PageInfo;
import com.situ.rbac.entity.Permission;
import io.swagger.models.auth.In;

import java.util.List;

public interface PermissionService {
    int add(Permission permission)throws Exception;
    int edit(Permission permission)throws Exception;
    int remove(Integer id)throws Exception;
    Permission getById(Integer id)throws Exception;
    List<Permission> search(Permission permission);
    //分页
    PageInfo search(Integer page,Integer limit,Permission permission);
}
