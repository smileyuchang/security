package io.renren.modules.power.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.power.entity.PowerElectricEntity;

import java.util.List;
import java.util.Map;

/**
 * 电表用量信息表
 *
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
public interface PowerElectricService extends IService<PowerElectricEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void importElectric(List<String[]> list);

    List<Map<String,Object>> queryByDegreeNumber(Map<String, Object> params);

    Map<String,Object> queryElectricMonth(Map<String, Object> params);
}

