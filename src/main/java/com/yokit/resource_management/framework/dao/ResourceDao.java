package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.entity.Resource;

public interface ResourceDao {
    int deleteByPrimaryKey(Integer resourceId);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer resourceId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

}