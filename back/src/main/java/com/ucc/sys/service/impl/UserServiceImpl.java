package com.ucc.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ucc.sys.entity.User;
import com.ucc.sys.mapper.UserMapper;
import com.ucc.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mabaishun
 * @since 2023-04-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
//        根据用户与密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,user.getName());
        wrapper.eq(User::getPassword,user.getPassword());

        User loginUser = this.baseMapper.selectOne(wrapper);
//        结果不为空，生成token，并将用户存入redis
        if (loginUser != null){
//            暂时用UUID，终极方案是jwt
            String key = "user:" + UUID.randomUUID();

//            存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

//            返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return  data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //        根据token获取用户信息，redis
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj != null) {
            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name",loginUser.getName());
            data.put("avatar",loginUser.getAvatar());
//            data.put("passwork",loginUser.getPassword());

            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);

            return  data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
