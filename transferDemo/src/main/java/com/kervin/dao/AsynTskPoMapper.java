package com.kervin.dao;

import com.kervin.model.AsynTskPo;

import java.util.List;

/**
 * 异步任务表Mapper
 * @author Kervin
 * @since 2018/7/26 21:26
 */
public interface AsynTskPoMapper {
    int deleteByPrimaryKey(String asynTskNo);

    int insert(AsynTskPo record);

    int insertSelective(AsynTskPo record);

    AsynTskPo selectByPrimaryKey(String asynTskNo);

    int updateByPrimaryKeySelective(AsynTskPo record);

    int updateByPrimaryKey(AsynTskPo record);

    List<AsynTskPo> selectAvailTsk();
}