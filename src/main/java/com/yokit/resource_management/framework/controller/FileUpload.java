package com.yokit.resource_management.framework.controller;

import com.yokit.resource_management.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author tonylin
 * @create 2024/6/30 15:17
 */
@RestController
public class FileUpload {

  @Value("${user.file.path}")
  private String filePath;


  /**
   * 上传文件
   * @param picture
   * @return
   */
  @RequestMapping(value = "/upload",method = RequestMethod.POST)
  public ResponseDto goodsImgUpload(@RequestParam("picture") MultipartFile picture) {
    //获取文件在服务器的储存位置
    String path = filePath;

    File filePath = new File(path);
    //文件保存路径为path
    if (!filePath.exists() && !filePath.isDirectory()) {
      filePath.mkdirs();
    }
    //获取原始文件名称(包含格式)
    String originalFileName = picture.getOriginalFilename();

    //获取文件类型，以最后一个`.`为标识
    String fileType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

    //获取文件名称（不包含格式）
    String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

    //防止文件名重复，用uuid新生成一个文件名
    String fileName = UUID.randomUUID() + name + "." + fileType;

    //在指定路径下创建一个文件
    File targetFile = new File(path, fileName);

    //将文件保存到服务器指定位置
    try {
      picture.transferTo(targetFile);

      //将文件在服务器的存储路径返回
      return new ResponseDto(200,"图片上传成功",fileName);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto(500, "图片上传失败");
    }
  }

  /**
   * 删除文件
   * @param fileName
   * @return
   */
  @RequestMapping(value = "/img/{fileName}",method = RequestMethod.DELETE)
  public ResponseDto deleteGoodsImg(@PathVariable String fileName) {
    String targetFile = filePath + "/" + fileName;
    File file = new File(targetFile);
    //判断文件是否存在
    if (file.exists()) {
      file.delete();
      return new ResponseDto(200, "文件删除成功");
    } else {
      return new ResponseDto(200, "文件不存在");
    }
  }
}
