<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>My JSP '22.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/js/flexpaper/css/flexpaper.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/flexpaper/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/flexpaper/js/flexpaper.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/flexpaper/js/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/flexpaper/js/flexpaper_handlers_debug.js"></script>
</head>
<body>
	<div style="position:absolute;left:20px;top:20px;">
		<input type="hidden" id="local" value="${local}"/>
		<a id="viewerPlaceHolder" style="width:910px;height:560px;display:block"></a>
		<script type="text/javascript">
		var local = $("#local").val();
		$(document).ready(function(){
			$('#viewerPlaceHolder').FlexPaperViewer({ 
				config : {
					SwfFile : local,
					//IMGFiles : "activiti.pdf_{page}.png",
					//JSONFile : "activiti.pdf.js",
					//PDFFile : "activiti.pdf",
					Scale : 1.5, 
					ZoomTransition : 'easeOut',
					ZoomTime : 0.5, 
					ZoomInterval : 0.2,
					FitPageOnLoad : false,
					FitWidthOnLoad : false, 
					FullScreenAsMaxWindow : false,
					ProgressiveLoading : true,
					MinZoomSize : 0.2,
					MaxZoomSize : 5,
					SearchMatchAll : false,
					InitViewMode : 'Portrait',
					RenderingOrder : 'flash,html',
					MixedMode : true,
					ViewModeToolsVisible : true,
					ZoomToolsVisible : true,
					NavToolsVisible : true,
					CursorToolsVisible : true,
					SearchToolsVisible : true,
					localeChain : "zh_CN"
				}
			});
		});
		</script>
	</div>
</body>
</html>
