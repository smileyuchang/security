package io.renren.modules.power.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.power.dao.PowerWaterDao;
import io.renren.modules.power.entity.PowerWaterEntity;
import io.renren.modules.power.service.PowerWaterService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


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

    @Override
    public void importWorter(List<String[]> list) {

        String msg = "";
        try {
            for (String[] s : list) {
                if (s != null && s[0] != null && s[0] != "" && s[0].indexOf(" ") == -1) {
                    //判断用户是否存在，如果已存在则跳过
                    if(null != baseMapper.selectOne(new QueryWrapper<PowerWaterEntity>().eq("water_number", s[0]))){
                        continue;
                    }

                    PowerWaterEntity powerWaterEntity = new PowerWaterEntity();
                    powerWaterEntity.setCreateTime(new Date());
                    powerWaterEntity.setWaterNumber(s[0]);
                    powerWaterEntity.setWaterMeter(Double.valueOf(s[1]));
                    powerWaterEntity.setPayTime(s[2]);
                    this.save(powerWaterEntity);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RRException(msg + "数据有误");
        }
    }

    @Override
    public List<Map<String, Object>> queryByWaterNumber(Map<String, Object> params) {
        List<Map<String, Object>>  resulstMap = this.baseMapper.queryByWaterNumber(params);
        return resulstMap;
    }

    @Override
    public Map<String, Object> queryWaterMonth(Map<String, Object> params) {
      return this.baseMapper.queryWaterMonth(params);
    }

}
