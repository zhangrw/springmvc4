package com.springmvc4.test.util;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * 百度原生的后台Java代码扩展 目前仅限于图片回显变更
 * 调整此入口类 变更回显时图片遍历路径 增加contextPath参数以便于前台展示相关图片
 */
public class ActionEnterExtension{

    private HttpServletRequest request = null;
    private String rootPath = null;
    private String contextPath = null;
    private String actionType = null;
    private ConfigManagerExtension ConfigManagerExtension = null;


    public ActionEnterExtension(HttpServletRequest request, String rootPath) {
        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.contextPath = request.getContextPath();
        this.ConfigManagerExtension = ConfigManagerExtension.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
    }

    public String exec() {
        String callbackName = this.request.getParameter("callback");
        return callbackName != null ?
                (  !this.validCallbackName(callbackName) ? (new BaseState(false, 401)).toJSONString() : callbackName + "(" + this.invoke() + ");" )
                :this.invoke();
    }

    public String invoke() {
        if(this.actionType != null && ActionMap.mapping.containsKey(this.actionType)) {
            if(this.ConfigManagerExtension != null && this.ConfigManagerExtension.valid()) {
                State state = null;
                int actionCode = ActionMap.getType(this.actionType);
                Map conf = null;
                switch(actionCode) {
                    case 0:
                        return this.ConfigManagerExtension.getAllConfig().toString();
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        conf = this.ConfigManagerExtension.getConfig(actionCode);
                        state = (new Uploader(this.request, conf)).doExec();
                        break;
                    case 5:
                        conf = this.ConfigManagerExtension.getConfig(actionCode);
                        String[] list = this.request.getParameterValues((String)conf.get("fieldName"));
                        state = (new ImageHunter(conf)).capture(list);
                        break;
                    case 6:
                    case 7:
                        conf = this.ConfigManagerExtension.getConfig(actionCode);
                        int start = this.getStartIndex();
                        conf.put("contextPath",this.contextPath);
                        conf.put("rootPath",new File(conf.get("rootPath").toString()).getParent());
                        state = (new FileManagerExtend(conf)).listFile(start);
                }

                return state.toJSONString();
            } else {
                return (new BaseState(false, 102)).toJSONString();
            }
        } else {
            return (new BaseState(false, 101)).toJSONString();
        }
    }

    public int getStartIndex() {
        String start = this.request.getParameter("start");

        try {
            return Integer.parseInt(start);
        } catch (Exception var3) {
            return 0;
        }
    }

    public boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }

}
