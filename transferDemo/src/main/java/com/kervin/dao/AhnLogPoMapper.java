package com.kervin.dao;

import com.kervin.model.AhnLogPo;
import com.kervin.model.AhnLogPoKey;

/**
 * 授权日志流水表Mapper
 * @author Kervin
 * @since 2018/7/26 21:26
 */
public interface AhnLogPoMapper {
    int deleteByPrimaryKey(AhnLogPoKey key);

    int insert(AhnLogPo record);

    int insertSelective(AhnLogPo record);

    AhnLogPo selectByPrimaryKey(AhnLogPoKey key);

    int updateByPrimaryKeySelective(AhnLogPo record);

    int updateByPrimaryKey(AhnLogPo record);
}