package io.renren.modules.power.service.impl;

import io.renren.common.utils.R;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.power.dao.PowerWaterDao;
import io.renren.modules.power.entity.PowerWaterEntity;
import io.renren.modules.power.service.PowerWaterService;


@Service("powerWaterService")
public class PowerWaterServiceImpl extends ServiceImpl<PowerWaterDao, PowerWaterEntity> implements PowerWaterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
      /*  IPage<PowerWaterEntity> page = this.page(
                new Query<PowerWaterEntity>().getPage(params),
                new QueryWrapper<PowerWaterEntity>()
        );*/
        IPage<Map<String,Object>> list = this.baseMapper.queryList(new Query<Map<String, Object>>().getPage(params), params);

        //return new PageUtils(page);
        return new PageUtils(list);
    }

}
