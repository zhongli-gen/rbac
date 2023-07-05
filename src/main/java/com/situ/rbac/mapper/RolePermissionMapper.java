package com.situ.rbac.mapper;

import net.bytebuddy.description.field.FieldDescription;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    /**
     * 批量添加
     */
    @Insert({
            "<script>",
            "insert into role_permission",
            "(role_id,permission_id)",
            "values",
            "<foreach collection='permissions' item='permission' separator=','>",
            "(#{roleId},#{permission.id})",
            "</foreach>"
    })
    int batchInsert(@Param("roleId") Integer roleId,
                    @Param("permissions") List<Integer> permissions);
}
