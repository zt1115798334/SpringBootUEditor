package com.zt.controller;

import com.baidu.ueditor.ActionEnter;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author zhangtong
 * Created by on 2017/11/27
 */
@Controller
@RequestMapping("ueditor")
public class UeditorController {

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/imgUpload3")
    @ResponseBody
    public Object imgUpload3(MultipartFile upfile, HttpServletRequest request) {
        String path = this.imgUpload(upfile, request);
        Map<String, String> result = Maps.newHashMap();
        File file = new File(path);
        result.put("url", path);
        result.put("size", String.valueOf(file.length()));
        result.put("type",
                file.getName().substring(file.getName().lastIndexOf(".")));
        result.put("state", "SUCCESS");
        return result;
    }

    @RequestMapping(value = "/imgUpload", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String imgUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
//        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = System.getProperty("user.dir") + File.separator + "image" + File.separator;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return filePath;
        } catch(IllegalStateException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "文件上传失败";

    }

}
