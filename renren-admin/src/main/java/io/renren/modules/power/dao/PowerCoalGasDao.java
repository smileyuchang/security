package io.renren.modules.power.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.power.entity.PowerCoalGasEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 煤气用量表
 * 
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@Mapper
public interface PowerCoalGasDao extends BaseMapper<PowerCoalGasEntity> {

    IPage<Map<String,Object>> queryCoalGasList(IPage<Map<String, Object>> page, @Param("params")Map<String, Object> params);

}
