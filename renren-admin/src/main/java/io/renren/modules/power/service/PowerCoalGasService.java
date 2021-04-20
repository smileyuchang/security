package io.renren.modules.power.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.power.entity.PowerCoalGasEntity;

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
}

