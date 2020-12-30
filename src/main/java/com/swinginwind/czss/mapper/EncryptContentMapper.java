package com.swinginwind.czss.mapper;

import java.util.List;

import com.swinginwind.czss.dto.EncryptContentPager;
import com.swinginwind.czss.entity.EncryptContent;
import com.swinginwind.czss.entity.EncryptContentWithBLOBs;

public interface EncryptContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EncryptContentWithBLOBs record);

    int insertSelective(EncryptContentWithBLOBs record);

    EncryptContentWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EncryptContentWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EncryptContentWithBLOBs record);

    int updateByPrimaryKey(EncryptContent record);
    
    List<EncryptContentWithBLOBs> select(EncryptContentPager pager);
}