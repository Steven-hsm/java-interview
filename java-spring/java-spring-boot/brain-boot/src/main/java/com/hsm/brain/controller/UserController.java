package com.hsm.brain.controller;


import com.hsm.brain.model.common.Result;
import com.hsm.brain.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangsm
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * 用户登录
     * @param openId
     * @return
     */
    @GetMapping("/login")
    public Result<String> login(@RequestParam("openId") String openId){
        return Result.success(userService.login(openId));
    }

}
