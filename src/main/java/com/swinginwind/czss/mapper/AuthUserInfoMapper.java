package com.swinginwind.czss.mapper;

import java.util.List;

import com.swinginwind.czss.dto.AuthUserInfoPager;
import com.swinginwind.czss.entity.AuthUserInfo;

public interface AuthUserInfoMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteAll();

    int insert(AuthUserInfo record);

    int insertSelective(AuthUserInfo record);

    AuthUserInfo selectByPrimaryKey(String id);
    
    AuthUserInfo selectByRowNum(int rowNum);

    int updateByPrimaryKeySelective(AuthUserInfo record);

    int updateByPrimaryKey(AuthUserInfo record);
    
    int selectMaxRowNum();
    
    List<AuthUserInfo> select(AuthUserInfoPager pager);
    
    int insertBatch(List<AuthUserInfo> list);
}