package com.karros.vn.mapper;

import org.apache.ibatis.annotations.Param;

import com.karros.vn.model.token.LoginToken;

public interface LoginTokenMapper {
    Integer insert(@Param("info") LoginToken info);
    
    Integer deleteLogicallyByUuid(@Param("uuid") String uuid, @Param("userId") Integer userId);
    
    Integer countByLoginToken(@Param("loginToken") String loginToken);
}
