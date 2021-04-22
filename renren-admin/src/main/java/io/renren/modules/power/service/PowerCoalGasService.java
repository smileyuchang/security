package io.renren.modules.power.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.power.entity.PowerCoalGasEntity;

import java.util.List;
import java.util.Map;

/**
 * 煤气用量表
 *
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
public interface PowerCoalGasService extends IService<PowerCoalGasEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void importCoalgas(List<String[]> list);

    List<Map<String,Object>> queryByCoalGasNumber(Map<String, Object> params);


    Map<String,Object> queryCoalgasMonth(Map<java.lang.String, java.lang.Object> params);
}

