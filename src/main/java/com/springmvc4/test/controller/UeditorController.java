package com.springmvc4.test.controller;

import com.springmvc4.test.util.ActionEnterExtension;
import com.springmvc4.test.util.CommonPlaceholderConfigurer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/28.
 */
@Controller
@RequestMapping("ueditor")
public class UeditorController {

    @RequestMapping("controller")
    public String index(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding( "utf-8" );
//        response.setHeader("Content-Type" , "text/html");
//
////        String rootPath = application.getRealPath( "/" );
//        PrintWriter out = response.getWriter();
//        out.write( new ActionEnter( request, "" ).exec() );
        return "index";
    }

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession()
                .getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnterExtension(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/upload/images")
    @ResponseBody
    public Map<String, Object> images (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        try{
            String basePath = "uploadFles/images/";
            String visitUrl = request.getContextPath()+"/ueditor/listimages/";

            String ext = upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf("."));
            String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat((  new Random().nextInt(900000) + 100000 ) + "").concat(ext);//concat(".").
            StringBuilder sb = new StringBuilder();
            //拼接保存路径
            sb.append(getFilePrefix(request)).append(File.separator)
                    .append(basePath).append("/").append(fileName);
            visitUrl = visitUrl.concat(fileName);
            File f = new File(sb.toString());
            if(!f.exists()){
                f.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(f);
            FileCopyUtils.copy(upfile.getInputStream(), out);
            params.put("state", "SUCCESS");
            params.put("url", visitUrl);
            params.put("size", upfile.getSize());
            params.put("original", fileName);
            params.put("type", upfile.getContentType());
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }

    @ResponseBody
    @RequestMapping("listimages/{imgname:.+}")
    public void getImage(@PathVariable("imgname")String imgname,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
//        String fileParth = CommonPlaceholderConfigurer.getContextProperty("uploadpath");
        String fileParth = getFilePrefix(request) +File.separator + "uploadFles/images/";
        if(StringUtils.isBlank(imgname) || StringUtils.isBlank(fileParth)){
            System.out.println("程序异常返回,下载图片路径："+fileParth+"，图片名称"+imgname);
            return;
        }
        File f = new File(fileParth + File.separator + imgname);
        if( !f.exists() ){
            return;
        }
        FileInputStream inputStream = new FileInputStream(f);
        response.setHeader("Content-Length", String.valueOf(f.length()));
        byte[] bos = new byte[(int) f.length()];
        IOUtils.read(inputStream,bos);
        OutputStream out = null;
        out = response.getOutputStream();
        out.write(bos);
        out.flush();
        out.close();
        inputStream.close();
    }

    /**
     * 这个路径涉及到上传图片后的回显问题,在处理回显时要注意统一
     * @param req
     * @return
     */
    public String getFilePrefix(HttpServletRequest req){
        String rootPath = req.getSession().getServletContext().getRealPath(""); // 项目根目录
        File f = new File(rootPath);
        return f.getParent(); // 我就是不想把上传的文件放在项目内,跳出项目根目录
    }
}
