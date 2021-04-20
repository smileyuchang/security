

package io.renren.api.user.controller;


import io.renren.api.user.form.LoginForm;
import io.renren.api.user.service.TokenService;
import io.renren.api.user.service.UserService;
import io.renren.common.annotation.Login;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.login(form);

        return R.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        return R.ok();
    }

}
