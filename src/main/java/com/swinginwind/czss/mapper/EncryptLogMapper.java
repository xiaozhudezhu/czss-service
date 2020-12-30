package com.swinginwind.czss.mapper;

import java.util.List;

import com.swinginwind.czss.dto.EncryptLogPager;
import com.swinginwind.czss.entity.EncryptLog;

public interface EncryptLogMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteAll();

    int insert(EncryptLog record);

    int insertSelective(EncryptLog record);

    EncryptLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EncryptLog record);

    int updateByPrimaryKey(EncryptLog record);
    
    List<EncryptLog> select(EncryptLogPager pager);
}