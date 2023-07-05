package com.situ.rbac.mapper;

import com.situ.rbac.entity.Role;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.Set;
@Mapper
public interface RoleMapper {

    @Select({
            "select role.* from role ",
            "INNER JOIN user_role on role.id = user_role.role_id",
            "WHERE user_role.user_id = #{userId}",
    })
    @Results({
            @Result(column = "id",property = "id",id = true),
            @Result(column = "id",property = "permissions",
                   many = @Many(select = "com.situ.rbac.mapper.PermissionMapper.selectByRoleId"))
    })
    Set<Role> selectRoleByUserId(Integer userId);
    //添加
    @Insert({
            "isert into role",
            "{name,code}",
            "value",
            "(#{name},#{code})"
    })
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(Role role);
    //修改
    @Update({
            "update role",
            "set",
            "name = #{name}",
            "code=#{code}",
            "where id = #{id}",
    })
    int update(Role role);
    //删除
    @Delete({
            "delete from role",
            "where id = #{id}",
    })
    int delete(Integer id);
    //根据id查询
    @Select({
            "select * from role",
            "where id = #{id}",
    })
    Role selectById(Integer id);

}

