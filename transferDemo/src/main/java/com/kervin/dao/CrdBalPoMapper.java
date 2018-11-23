package com.kervin.dao;

import com.kervin.model.CrdBalPo;

/**
 * 卡片余额表Mapper
 * @author Kervin
 * @since 2018/7/26 21:26
 */
public interface CrdBalPoMapper {
    int deleteByPrimaryKey(String cardNum);

    int insert(CrdBalPo record);

    int insertSelective(CrdBalPo record);

    CrdBalPo selectByPrimaryKey(String cardNum);

    int updateByPrimaryKeySelective(CrdBalPo record);

    int updateByPrimaryKey(CrdBalPo record);
}