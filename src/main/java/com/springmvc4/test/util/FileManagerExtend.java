package com.springmvc4.test.util;

import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 *
 * 百度原生的后台Java代码自行扩展  目前仅限于图片回显变更
 * 调整文件遍历类 变更回显时图片遍历路径以及返回的图片访问路径 以此达到项目外存储上传的图片
 * 增加contextPath接收参数拼装到访问图片的路径上以便于前台展示相关图片
 *
 */
public class FileManagerExtend{
    private String dir = null;
    private String rootPath = null;
    private String[] allowFiles = null;
    private int count = 0;
    private String contextPath;
    public FileManagerExtend(Map<String, Object> conf) {
        this.rootPath = (String)conf.get("rootPath");
        String uploadPath = CommonPlaceholderConfigurer.getContextProperty("uploadpath");
        // 拼接文件路径
        this.dir = this.rootPath + File.separator + uploadPath;
        this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
        this.count = ((Integer)conf.get("count")).intValue();
        contextPath = conf.get("contextPath").toString();
    }

    public State listFile(int index) {
        File dir = new File(this.dir);
        Object state = null;
        if(!dir.exists()) {
            return new BaseState(false, 302);
        } else if(!dir.isDirectory()) {
            return new BaseState(false, 301);
        } else {
            Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);
            if(index >= 0 && index <= list.size()) {

                Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
                state = this.getState(fileList);
            } else {
                state = new MultiState(true);
            }

            ((State)state).putInfo("start", (long)index);
            ((State)state).putInfo("total", (long)list.size());
            return (State)state;
        }
    }

    private State getState(Object[] files) {
        MultiState state = new MultiState(true);
        BaseState fileState = null;
        File file = null;
        Object[] var8 = files;
        int var7 = files.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            Object obj = var8[var6];
            if(obj == null) {
                break;
            }

            file = (File)obj;
            fileState = new BaseState(true);
            //  文件读取路径设置为 配置的
            fileState.putInfo("url", contextPath+"/ueditor/listimages/" + file.getName() ); //  PathFormat.format(this.getPath(file))
            state.addState(fileState);
        }
        return state;
    }

    private String getPath(File file) {
        String path = file.getAbsolutePath();
        return path.replace(this.rootPath, "/");
    }


    private String[] getAllowFiles(Object fileExt) {
        String[] exts = null;
        String ext = null;
        if(fileExt == null) {
            return new String[0];
        } else {
            exts = (String[])fileExt;
            int i = 0;

            for(int len = exts.length; i < len; ++i) {
                ext = exts[i];
                exts[i] = ext.replace(".", "");
            }

            return exts;
        }
    }


}
