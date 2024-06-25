package com.yokit.resource_management.framework.service;

import com.yokit.resource_management.framework.dao.ResourceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fengzeng
 * @create 2021/6/25 3:01
 */
@Service
public class ResourceService {

  @Resource
  private ResourceDao resourceDao;


}
