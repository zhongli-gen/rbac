package com.situ.rbac.mapper;

import com.situ.rbac.entity.Permission;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface PermissionMapper {
    @Select({
            "select permission.* from permission",
            "INNER JOIN role_permission on permission.id = role_permission.permission_id",
            "where role_permission.role_id = #{roleId}",
    })
    Set<Permission> selectByRoleId(Integer roleId);
    @Insert({
            "insert into permission(name,code)",
            "values",
            "(#{name},#{code})",
    })
    int insert(Permission permission);
    @Update({
            "update permission set",
            "name = #{name}",
            "code = #{code}",
            "where id = #{id}"
    })
    int update(Permission permission);
    @Delete({
            "delete form permission",
            "where id = #{id}",
    })
    int delete(Integer id);
    @Select({
            "select * from permission",
            "where id = #{id}"
    })
    Permission selectById(Integer id);
    /*
    多条件组合查询，使用动态SQL，必须使用script标签的SQL包括起来
     */
    @Select({
            "<script>",
            "select * from permission",
            "<where>",
            "<if test='name!=null and name.length>0'>name like concat('%',#{name},'%')</if>",
            "<if test='code!=null and code.length>0'>name like concat('%',#{code},'%')</if>",
            "</where>",
            "</script>",
    })
    List<Permission> select(Permission permission);

}



