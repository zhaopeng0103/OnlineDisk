<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath ="";
	if(request.getServerPort()==80){
		basePath=request.getScheme()+"://"+request.getServerName()+path+"/";
	}else{
		basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	}
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>500</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link href="${basePath}static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}static/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}static/css/styleerror.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}static/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}static/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${basePath}static/css/error.css" rel="stylesheet" type="text/css"/>
</head>
<body class="page-500-full-page">
	<div class="row-fluid">
		<div class="span12 page-500">
			<div class="number">
				500
			</div>
			<div class="details">
				<h3>哎呀，出事了！</h3>
				<p>我们正在努力解决。<br/>请待会儿再来！<br/></p>
				<p><a href="javascript:history.back(-1);">返 回</a><br></p>
			</div>
		</div>
	</div>
</html>