<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../public/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head><base href="<%=basePath%>">
<title>下载文件</title>
<meta name="content-type" content="text/html; charset=UTF-8">
</head>

<body>
	<a href="test/${name}">${name}</a>
</body>
</html>