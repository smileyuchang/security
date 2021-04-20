package io.renren.modules.power.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.power.entity.PowerWaterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 水表用量信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-04-19 21:43:16
 */
@Mapper
public interface PowerWaterDao extends BaseMapper<PowerWaterEntity> {

    IPage<Map<String,Object>> queryList(IPage<Map<String, Object>> page, @Param("params")Map<String, Object> params);
	
}
