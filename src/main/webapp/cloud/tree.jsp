<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>My JSP '22.jsp' starting page</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/js/easyui1.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/js/easyui1.4/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/easyui1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/easyui1.4/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/buttons.css">
	<script src="${pageContext.request.contextPath}/static/js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		$(function (){
			$('#tt').tree({ 
			    url:'${pageContext.request.contextPath}/tree.do',
			    lines:true,
			    onClick: function(node){
					//alert(node.id);
					//alert(node.text);
					$("#path").val(node.id);
				}
			});
		});
		function copyOrMove(){
			if($("#path").val()==null||$("#path").val()==''){
				layer.msg('请选择目录', 2, -1);
				return false;
			}
			layer.load(2);
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/copyOrMoveFile.do",
				data:{ ids:$("#ids").val(), parentid:$("#parentid").val(), destid:$("#path").val(), flag:$("#flag").val() },
				dataType:"json",
				async: false,
				success:function(res){
					layer.closeAll('loading');
					if (res.errres) {
						parent.location.reload();
						parent.layer.close(index);
					}else{
						layer.msg(res.errmsg, 2, -1);
					}
				}
			});
		}
	</script>
</head>
<body>
    <ul id="tt"></ul>
    <hr><input type="hidden" id="ids" value="${ids}"/>
    <input type="hidden" id="flag" value="${flag}"/>
    <input type="hidden" id="parentid" value="${parentid}"/>
    <input type="hidden" id="path"/>
    <div align="right"><input type="button" class="button teal" value="确定" onclick="copyOrMove()" /></div>
</body>
</html>
