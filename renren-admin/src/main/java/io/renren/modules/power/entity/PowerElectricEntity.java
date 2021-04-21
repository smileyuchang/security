package io.renren.modules.power.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 电表用量信息表
 * 
 * @author Mark
 * @email 
 * @date 2021-04-20 14:34:58
 */
@Data
@TableName("power_electric")
public class PowerElectricEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 电的度数
	 */
	private Double degree;
	/**
	 * 承租方id
	 */
	private Integer userId;
	/**
	 * 电号
	 */
	private String electricNumber;
	/**
	 * 收费时间
	 */
	private String payTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
