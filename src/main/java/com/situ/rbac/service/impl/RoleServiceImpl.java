package com.situ.rbac.service.impl;

import com.github.pagehelper.PageInfo;
import com.situ.rbac.entity.Role;
import com.situ.rbac.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    @Override
    public int add(Role role) throws Exception {
        return 0;
    }

    @Override
    public int remove(Integer id) throws Exception {
        return 0;
    }

    @Override
    public int edit(Role role) throws Exception {
        return 0;
    }

    @Override
    public List<Role> search(Role role) {
        return null;
    }

    @Override
    public PageInfo search(Integer page, Integer limit, Role role) {
        return null;
    }

    @Override
    public Role searchByid(Integer id) {
        return null;
    }
}
