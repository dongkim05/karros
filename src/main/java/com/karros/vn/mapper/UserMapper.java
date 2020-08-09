package com.karros.vn.mapper;

import org.apache.ibatis.annotations.Param;

import com.karros.vn.model.User;

public interface UserMapper {
  User getUserByUserName(@Param("userName") String userName);
  
  Integer countByUserName(@Param("userName") String userName);
  
  Integer insert(@Param("user") User user);
}
