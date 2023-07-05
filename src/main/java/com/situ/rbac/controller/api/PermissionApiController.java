package com.situ.rbac.controller.api;

import com.situ.rbac.entity.Permission;
import com.situ.rbac.entity.Result;
import com.situ.rbac.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.origin.Origin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permission")
@Api(description = "对权限的管理")
//@CrossOrigin(origins={"*"}) //允许跨域的注解 origins:请求的源头   *代表任意的
public class PermissionApiController {
    private final PermissionService permissionService;
    //添加
    @PostMapping
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
    //修改
    @PutMapping
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") Integer id){
        try{
            permissionService.remove(id);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id){
        try {
            Permission permission = permissionService.getById(id);
            return Result.success(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
    @GetMapping
    public Result search(Integer page,Integer limit,Permission permission){
        if(page == null){
            return Result.success(permissionService.search(permission));
        }else {
            return Result.success(permissionService.search(page,limit,permission));
        }
    }

}
