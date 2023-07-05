package com.situ.rbac.controller.api;

import com.situ.rbac.entity.Result;
import com.situ.rbac.entity.User;
import com.situ.rbac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    /*
        自己实现登录操作
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        try {
            System.out.println("=====user=======:"+user);
            String str = userService.login(user);
            return Result.success(str);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

}
