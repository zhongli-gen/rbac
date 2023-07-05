package com.situ.rbac.service;

import com.github.pagehelper.PageInfo;
import com.situ.rbac.entity.Role;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

import java.util.List;

public interface RoleService {
    /**
     * 添加
     */
    int add(Role role) throws Exception;
    /**
     * 删除
     */
    int remove(Integer id)throws Exception;
    /**
     * 编辑
     */
    int edit(Role role)throws Exception;
    /**
     * 查
     */
    List<Role> search(Role role);
    /**
     * 分页查
     */
    PageInfo search(Integer page,Integer limit,Role role);
    /**
     * 根据id查
     */
    Role searchByid(Integer id);
}
