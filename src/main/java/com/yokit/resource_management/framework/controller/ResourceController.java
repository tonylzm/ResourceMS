package com.yokit.resource_management.framework.controller;

import com.yokit.resource_management.framework.service.ResourceService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tonylin
 * @create 2021/6/25 3:01
 */
@RestController
public class ResourceController {

  @Resource
  private ResourceService resourceService;

}
