package io.renren.modules.power.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.ExcelUtil;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.power.entity.PowerElectricEntity;
import io.renren.modules.power.service.PowerElectricService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 电表用量信息表
 *
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@RestController
@RequestMapping("power/powerelectric")
public class PowerElectricController {
    @Autowired
    private PowerElectricService powerElectricService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("power:powerelectric:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = powerElectricService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("power:powerelectric:info")
    public R info(@PathVariable("id") Integer id){
        PowerElectricEntity powerElectric = powerElectricService.getById(id);

        return R.ok().put("powerElectric", powerElectric);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("power:powerelectric:save")
    public R save(@RequestBody PowerElectricEntity powerElectric){
        powerElectricService.save(powerElectric);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("power:powerelectric:update")
    public R update(@RequestBody PowerElectricEntity powerElectric){
        ValidatorUtils.validateEntity(powerElectric);
        powerElectricService.updateById(powerElectric);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("power:powerelectric:delete")
    public R delete(@RequestBody Integer[] ids){
        powerElectricService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导入用电量信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importElectric")
    public R importElectric(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        String msg;
        if (file.isEmpty()) {
            msg = "上传文件不能为空";
            return R.error(msg);
        }

        //解析上传 excel
        List<String[]> list = ExcelUtil.getExcelData(file);
        if (list != null && list.size() > 0) {

            //保存excel表格逻辑
            powerElectricService.importElectric(list);
            return R.ok("导入成功");
        } else {
            return R.ok("请勿上传空excel");
        }
    }


}
