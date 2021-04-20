

package io.renren.api.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.api.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
