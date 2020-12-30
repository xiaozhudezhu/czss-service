package com.swinginwind.czss.mapper;

import java.util.List;

import com.swinginwind.czss.entity.EncryptUserInfo;

public interface EncryptUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EncryptUserInfo record);

    int insertSelective(EncryptUserInfo record);

    EncryptUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EncryptUserInfo record);

    int updateByPrimaryKey(EncryptUserInfo record);
    
    List<EncryptUserInfo> selectAll();
}