package io.renren.modules.power.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.power.entity.PowerElectricEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 电表用量信息表
 * 
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@Mapper
public interface PowerElectricDao extends BaseMapper<PowerElectricEntity> {

    IPage<Map<String,Object>> queryElectricList(IPage<Map<String, Object>> page, @Param("params")Map<String, Object> params);
}
