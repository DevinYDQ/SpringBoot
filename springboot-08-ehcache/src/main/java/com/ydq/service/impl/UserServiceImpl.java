package com.ydq.service.impl;

import com.ydq.domain.User;
import com.ydq.mapper.UserMapper;
import com.ydq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Cacheable(key = "#userId")
    @Override
    public User getUserById(String userId) {
        System.out.println("getUser.......");
        return userMapper.getUserById(userId);
    }

    @Override
    @CachePut(key = "#user.id")
    public User updateUserById(User user) {
        return user;
    }

    @Override
    @CacheEvict
    public void deleteUser(String userId) {
        //在这里执行删除操作， 删除是去数据库中删除
    }


}
