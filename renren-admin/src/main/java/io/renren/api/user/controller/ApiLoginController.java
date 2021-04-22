

package io.renren.api.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import io.renren.api.user.entity.UserEntity;
import io.renren.api.user.form.LoginForm;
import io.renren.api.user.service.TokenService;
import io.renren.api.user.service.UserService;
import io.renren.common.annotation.Login;
import io.renren.common.annotation.LoginUser;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.power.service.PowerCoalGasService;
import io.renren.modules.power.service.PowerElectricService;
import io.renren.modules.power.service.PowerWaterService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Calendar;
import java.util.List;
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


    @Autowired
    private PowerWaterService powerWaterService;

    @Autowired
    private PowerElectricService powerElectricService;
    @Autowired
    private PowerCoalGasService powerCoalGasService;


    @RequestMapping("login")
    @ApiOperation("登录")
   // public R login(@RequestBody LoginForm form){
    public R login(String username,String password){
        //表单校验
        //ValidatorUtils.validateEntity(form);
        LoginForm form = new LoginForm();
        form.setUsername(username);
        form.setPassword(password);
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


    /**
     * 用户个人信息
     * @param user
     * @return
     */
    @Login
    @RequestMapping("info")
    public R info(@LoginUser UserEntity user){
        return R.ok().put("result",userService.getBaseMapper().selectOne(new QueryWrapper<UserEntity>().eq("user_id", user.getUserId())));
    }


    /**
     * 修改个人信息
     * @param 'user'
     * @return
     */
    @Login
    @RequestMapping("updateUserInfo")
    public R updateUserInfo(Long userId,String mobile,String nikeName,String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        if(!Strings.isNullOrEmpty(mobile)){
            userEntity.setMobile(mobile);
        }
        if(!Strings.isNullOrEmpty(nikeName)){
            userEntity.setNikeName(nikeName);
        }
        if(!Strings.isNullOrEmpty(password)){
            userEntity.setPassword(DigestUtils.sha256Hex(password));
        }
        int a = userService.getBaseMapper().updateById(userEntity);
        if(a == 1){
            return R.ok();
        } else {
            return R.error("修改失败");
        }

    }


    //----------------------------------当月水电煤使用情况-------------------------------------

    /**
     * 当月用水量信息
     * @param params
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/waterMonth")
    public R waterMonth(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){
        params.put("waterNumber",user.getWaterNumber());
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String month = String.valueOf(date.get(Calendar.MONTH));
        Integer intMonth = Integer.parseInt(month) + 1;
        if(intMonth < 10){
            params.put("month",year + "-" + "0" +intMonth);
        } else {
            params.put("month",year + "-" + intMonth);
        }

        Map<String,Object> resutlMap = powerWaterService.queryWaterMonth(params);
        return R.ok().put("resutlMap", resutlMap);
    }


    /**
     * 当月用电量信息
     * @param params
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/electricMonth")
    public R electricMonth(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){
        params.put("degreeNumber",user.getDegreeNumber());
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String month = String.valueOf(date.get(Calendar.MONTH));
        Integer intMonth = Integer.parseInt(month) + 1;
        if(intMonth < 10){
            params.put("month",year + "-" + "0" + intMonth);
        } else {
            params.put("month",year + "-" + intMonth);
        }

        Map<String,Object> resutlMap = powerElectricService.queryElectricMonth(params);
        return R.ok().put("resutlMap", resutlMap);
    }


    /**
     * 当月用煤气量信息
     * @param params
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/coalgasMonth")
    public R coalgasMonth(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){
        params.put("coalGasNumber",user.getCoalGasNumber());
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String month = String.valueOf(date.get(Calendar.MONTH));
        Integer intMonth = Integer.parseInt(month) + 1;
        if(intMonth < 10){
            params.put("month",year + "-" + "0" + intMonth);
        } else {
            params.put("month",year + "-" + intMonth);
        }

        Map<String,Object> resutlMap = powerCoalGasService.queryCoalgasMonth(params);
        return R.ok().put("resutlMap", resutlMap);
    }



    //----------------------------------水电煤使用情况-------------------------------------

    /**
     * 水用量信息 --列表
     * @param params
     * @param user
     * @auther yc
     * @return
     */
    @Login
    @RequestMapping("/waterList")
    public R list(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){

        //PageUtils page = powerWaterService.queryPage(params);
        params.put("waterNumber",user.getWaterNumber());
        List<Map<String,Object>> resutlMap = powerWaterService.queryByWaterNumber(params);
        return R.ok().put("resutlMap", resutlMap);
    }

    /**
     * 电用量信息 --列表
     * @param params
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/electricList")
    public R electricList(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){

        params.put("degreeNumber",user.getDegreeNumber());
        List<Map<String,Object>> resutlMap = powerElectricService.queryByDegreeNumber(params);
        return R.ok().put("resutlMap", resutlMap);
    }


    /**
     * 煤气量信息 --列表
     * @param params
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/coalgasList")
    public R coalgasList(@RequestParam Map<String, Object> params, @LoginUser UserEntity user){

        params.put("coalGasNumber",user.getCoalGasNumber());
        List<Map<String,Object>> resutlMap = powerCoalGasService.queryByCoalGasNumber(params);
        return R.ok().put("resutlMap", resutlMap);
    }




}
