package com.situ.rbac.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.rbac.entity.Permission;
import com.situ.rbac.mapper.PermissionMapper;
import com.situ.rbac.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;
    @Override
    public int add(Permission permission) throws Exception {
        int res = permissionMapper.insert(permission);
        return res;
    }

    @Override
    public int edit(Permission permission) throws Exception {
        int res = permissionMapper.update(permission);
        return res;
    }

    @Override
    public int remove(Integer id) throws Exception {
        int res = permissionMapper.delete(id);
        return res;
    }

    @Override
    public Permission getById(Integer id) throws Exception {
        Permission permission = permissionMapper.selectById(id);
        return permission;
    }

    @Override
    public List<Permission> search(Permission permission) {
        List list =  permissionMapper.select(permission);
        return list;
    }

    @Override
    public PageInfo search(Integer page,Integer limit,Permission permission) {
        PageHelper.startPage(page, limit);
        List list = permissionMapper.select(permission);
        return new PageInfo(list);
    }
}
