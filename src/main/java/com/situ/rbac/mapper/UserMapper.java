package com.situ.rbac.mapper;

import com.situ.rbac.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * 在xml中使用insert，delete，update标签配置SQL
     * 在java中也可以使用@Insert，@Delete，@Update
     * 当时用动态SQL时 ,必须使用<script></script>
     */
    @Select({
            "select * from user",
            "where username = #{username}",
    })
    @Results({
            @Result(column = "id",property = "id",id = true),
            @Result(column = "id",property = "roles",
                    many = @Many(select = "com.situ.rbac.mapper.RoleMapper.selectRoleByUserId"))
    })
    User selectByUsername(String username);

    @Select({
            "select * from user",
            "where id = #{id}",
    })
    User selectById(Integer id);
}
