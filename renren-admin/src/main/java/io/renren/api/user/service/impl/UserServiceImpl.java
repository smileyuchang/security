

package io.renren.api.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.api.user.dao.UserDao;
import io.renren.api.user.entity.TokenEntity;
import io.renren.api.user.entity.UserEntity;
import io.renren.api.user.form.LoginForm;
import io.renren.api.user.service.TokenService;
import io.renren.api.user.service.UserService;
import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", mobile));
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
		UserEntity user = queryByMobile(form.getUsername());
		Assert.isNull(user, "手机号或密码错误");


		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

}
