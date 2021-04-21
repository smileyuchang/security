package io.renren.modules.power.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 煤气用量表
 * 
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@Data
@TableName("power_coal_gas")
public class PowerCoalGasEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 煤气用量
	 */
	private Double number;
	/**
	 * 煤气号
	 */
	private String coalGasNumber;
	/**
	 * 收费时间
	 */
	private String payTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
