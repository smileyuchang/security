package io.renren.modules.power.controller;

import java.util.*;

import io.renren.common.utils.ExcelUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.power.entity.PowerWaterEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.power.service.PowerWaterService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 水表用量信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-04-19 21:43:16
 */
@RestController
@RequestMapping("sys/powerwater")
public class PowerWaterController {
    @Autowired
    private PowerWaterService powerWaterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:powerwater:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = powerWaterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:powerwater:info")
    public R info(@PathVariable("id") Integer id){
        PowerWaterEntity powerWater = powerWaterService.getById(id);

        return R.ok().put("powerWater", powerWater);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:powerwater:save")
    public R save(@RequestBody PowerWaterEntity powerWater){
        powerWaterService.save(powerWater);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:powerwater:update")
    public R update(@RequestBody PowerWaterEntity powerWater){
        ValidatorUtils.validateEntity(powerWater);
        powerWaterService.updateById(powerWater);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:powerwater:delete")
    public R delete(@RequestBody Integer[] ids){
        powerWaterService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }




    /**
     * 导入水用量信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importWorter")
    public R importWorter(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        String msg;
        if (file.isEmpty()) {
            msg = "上传文件不能为空";
            return R.error(msg);
        }

        //解析上传 excel
        List<String[]> list = ExcelUtil.getExcelData(file);
        if (list != null && list.size() > 0) {

            //保存excel表格逻辑
            powerWaterService.importWorter(list);
            return R.ok("导入成功");
        } else {
            return R.ok("请勿上传空excel");
        }
    }


    /**
     * 下载导入水表信息模板
     * @param response
     * @return
     */
    @RequestMapping("/exportTemplate")
    public R exportTemplate(HttpServletResponse response) {

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("waterNumber", "水号");
        fieldMap.put("waterMeter", "水表数值");
        fieldMap.put("payTime", "收费时间");
        List<PowerWaterEntity> waterList = new ArrayList<>();
        waterList.add(new PowerWaterEntity());
        ExcelUtil.listToExcel(waterList, fieldMap, "water", response);
        return R.ok();
    }



}
