package com.swinginwind.czss.mapper;

import java.util.List;

import com.swinginwind.czss.dto.AuthLogPager;
import com.swinginwind.czss.entity.AuthLog;

public interface AuthLogMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteAll();

    int insert(AuthLog record);

    int insertSelective(AuthLog record);

    AuthLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthLog record);

    int updateByPrimaryKey(AuthLog record);
    
    List<AuthLog> select(AuthLogPager pager);
}