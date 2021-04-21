package io.renren.modules.power.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.power.entity.PowerElectricEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Override
    public void importCoalgas(List<String[]> list) {
        String msg = "";
        try {
            for (String[] s : list) {
                if (s != null && s[0] != null && s[0] != "" && s[0].indexOf(" ") == -1) {
                    //判断用户是否存在，如果已存在则跳过
                    if(null != baseMapper.selectOne(new QueryWrapper<PowerCoalGasEntity>().eq("coal_gas_number", s[0]))){
                        continue;
                    }

                    PowerCoalGasEntity powerCoalGasEntity = new PowerCoalGasEntity();
                    powerCoalGasEntity.setCreateTime(new Date());
                    powerCoalGasEntity.setCoalGasNumber(s[0]);
                    powerCoalGasEntity.setNumber(Double.valueOf(s[1]));
                    powerCoalGasEntity.setPayTime(s[2]);
                    this.save(powerCoalGasEntity);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RRException(msg + "数据有问题");
        }

    }

}
