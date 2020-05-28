package com.ydq.mapper;

import com.ydq.domain.User;
import org.apache.ibatis.annotations.Param;

//@Mapper
public interface UserMapper {
    User getUserById(@Param(value = "id") String id);
}
