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

import io.renren.modules.power.entity.PowerCoalGasEntity;
import io.renren.modules.power.service.PowerCoalGasService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 煤气用量表
 *
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@RestController
@RequestMapping("power/powercoalgas")
public class PowerCoalGasController {
    @Autowired
    private PowerCoalGasService powerCoalGasService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("power:powercoalgas:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = powerCoalGasService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("power:powercoalgas:info")
    public R info(@PathVariable("id") Integer id){
        PowerCoalGasEntity powerCoalGas = powerCoalGasService.getById(id);

        return R.ok().put("powerCoalGas", powerCoalGas);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("power:powercoalgas:save")
    public R save(@RequestBody PowerCoalGasEntity powerCoalGas){
        powerCoalGasService.save(powerCoalGas);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("power:powercoalgas:update")
    public R update(@RequestBody PowerCoalGasEntity powerCoalGas){
        ValidatorUtils.validateEntity(powerCoalGas);
        powerCoalGasService.updateById(powerCoalGas);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("power:powercoalgas:delete")
    public R delete(@RequestBody Integer[] ids){
        powerCoalGasService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导入用煤气量信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importCoalgas")
    public R importCoalgas(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        String msg;
        if (file.isEmpty()) {
            msg = "上传文件不能为空";
            return R.error(msg);
        }

        //解析上传 excel
        List<String[]> list = ExcelUtil.getExcelData(file);
        if (list != null && list.size() > 0) {

            //保存excel表格逻辑
            powerCoalGasService.importCoalgas(list);
            return R.ok("导入成功");
        } else {
            return R.ok("请勿上传空excel");
        }
    }


}
