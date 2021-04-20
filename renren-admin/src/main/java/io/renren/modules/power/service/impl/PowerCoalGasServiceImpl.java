package io.renren.modules.power.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.power.dao.PowerCoalGasDao;
import io.renren.modules.power.entity.PowerCoalGasEntity;
import io.renren.modules.power.service.PowerCoalGasService;


@Service("powerCoalGasService")
public class PowerCoalGasServiceImpl extends ServiceImpl<PowerCoalGasDao, PowerCoalGasEntity> implements PowerCoalGasService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
       /* IPage<PowerCoalGasEntity> page = this.page(
                new Query<PowerCoalGasEntity>().getPage(params),
                new QueryWrapper<PowerCoalGasEntity>()
        );*/


        IPage<Map<String,Object>> list = this.baseMapper.queryCoalGasList(new Query<Map<String, Object>>().getPage(params), params);

        //return new PageUtils(page);
        return new PageUtils(list);
    }

}
