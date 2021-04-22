package io.renren.modules.power.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.power.entity.PowerWaterEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Override
    public void importElectric(List<String[]> list) {

        String msg = "";
        try {
            for (String[] s : list) {
                if (s != null && s[0] != null && s[0] != "" && s[0].indexOf(" ") == -1) {
                    //判断用户是否存在，如果已存在则跳过
                    if(null != baseMapper.selectOne(new QueryWrapper<PowerElectricEntity>().eq("electric_number", s[0]))){
                        continue;
                    }

                    PowerElectricEntity powerElectricEntity = new PowerElectricEntity();
                    powerElectricEntity.setCreateTime(new Date());
                    powerElectricEntity.setElectricNumber(s[0]);
                    powerElectricEntity.setDegree(Double.valueOf(s[1]));
                    powerElectricEntity.setPayTime(s[2]);
                    this.save(powerElectricEntity);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RRException(msg + "数据有问题");
        }
    }

    @Override
    public List<Map<String, Object>> queryByDegreeNumber(Map<String, Object> params) {
        List<Map<String, Object>>  resulstMap = this.baseMapper.queryByDegreeNumber(params);
        return resulstMap;
    }

    @Override
    public Map<String, Object> queryElectricMonth(Map<String, Object> params) {

        return  this.baseMapper.queryElectricMonth(params);
    }

}
