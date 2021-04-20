package io.renren.modules.power.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.power.dao.PowerElectricDao;
import io.renren.modules.power.entity.PowerElectricEntity;
import io.renren.modules.power.service.PowerElectricService;


@Service("powerElectricService")
public class PowerElectricServiceImpl extends ServiceImpl<PowerElectricDao, PowerElectricEntity> implements PowerElectricService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
      /*  IPage<PowerElectricEntity> page = this.page(
                new Query<PowerElectricEntity>().getPage(params),
                new QueryWrapper<PowerElectricEntity>()
        );*/

        IPage<Map<String,Object>> list = this.baseMapper.queryElectricList(new Query<Map<String, Object>>().getPage(params), params);

        //return new PageUtils(page);
        return new PageUtils(list);
    }

}
