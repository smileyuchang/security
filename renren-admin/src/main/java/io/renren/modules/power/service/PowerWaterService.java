package io.renren.modules.power.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.power.entity.PowerWaterEntity;

import java.util.Map;

/**
 * 水表用量信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-04-19 21:43:16
 */
public interface PowerWaterService extends IService<PowerWaterEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

