package io.renren.modules.power.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 水表用量信息表
 * 
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@Data
@TableName("power_water")
public class PowerWaterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 水表数值
	 */
	private Double waterMeter;
	/**
	 * 水号
	 */
	private String waterNumber;
	/**
	 * 收费时间
	 */
	private String payTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
