

package io.renren.api.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.api.user.entity.UserEntity;
import io.renren.api.user.form.LoginForm;

import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginForm form);
}
