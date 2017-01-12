<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>测试Ueditor ${ctx}</title>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ueditor/ueditor.all.min.js"> </script>
    <%--<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body>
<h2>Hello World!  ${ctx}</h2>

<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>

<%--<div id="ueditor">测试</div>--%>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage'){
            return '${ctx}/ueditor/upload/images';
        }
        else if (action == 'uploadvideo') { // /ueditor/listimages
            return  '${ctx}/ueditor/upload/images';
        }
        else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
</script>
</body>
</html>
