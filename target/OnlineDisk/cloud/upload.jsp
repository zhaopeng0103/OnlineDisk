<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head><base href="<%=basePath%>">
    <title>文件上传</title>
<link rel="stylesheet" href="js/plupload-2.1.2/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css" media="screen" />
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/i18n/zh_CN.js"></script>
  <body style="padding: 0;margin: 0;">
    <div id="uploader">&nbsp;</div>
<script type="text/javascript">
var files = [];
var errors = [];
//var type = 'file';
//var chunk = eval('${param.chunk}');
//var dir = eval('${param.dir}');
//var max_file_size = '5mb';
//var filters = {title : "文档", extensions : "zip,doc,docx,xls,xlsx,ppt,pptx"};
$("#uploader").pluploadQueue($.extend({
	runtimes : 'flash,html4,html5',
	url : '${pageContext.request.contextPath}/cloud/upload.do',
	//max_file_size : max_file_size,
	//file_data_name:'file',
	unique_names:true,
	//filters : [],
	flash_swf_url : 'js/Moxie.swf',
	init:{
		BeforeUpload: function(uploader, file) {
        	uploader.setOption('url','${pageContext.request.contextPath}/cloud/upload.do?dir=${param.dir}'); 
        },
		FileUploaded:function(uploader,file,response){
			if(response.response){
				var rs = $.parseJSON(response.response);
				if(rs.status){
					files.push(file.name);
				}else{
					errors.push(file.name);
				}
			}
		},
		UploadComplete:function(uploader,fs){
			var e= errors.length ? ",失败"+errors.length+"个("+errors.join("、")+")。" : "。";
			alert("上传完成！共"+fs.length+"个。成功"+files.length+e);
			target.window("close");
		}
	}
}));

</script>
  </body>
</html>