<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/public/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>follow</title>
<jsp:include page="/public/pub.jsp"></jsp:include>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
$(function(){
	$('#sharebut').on('click', function(){
		layer.load(2);
		var names = [];
		$("#shareForm input[type=checkbox]").each(function(){
			if(this.checked==true){
				names.push(this.value);
			}
		});
		if(names.length>0){
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/shareFile.do",
				data:{ names:names.join(','), ids:$("#fileids").val() },
				dataType:"json",
				async: false,
				success:function(res){
					layer.closeAll('loading');
					if(res.errres){
						parent.layer.close(index); //执行关闭
						layer.msg("分享成功", 2, -1);
					}else{
						layer.msg(res.errmsg, 2, -1);
					}
				}
			});
	    	//parent.layer.close(index); //执行关闭
		}else{
			layer.msg('你没有选择', 2, -1);
		}
	});
});

</script>
</head>

<body>
	<form id="shareForm" method="post">
		<input type="hidden" id="fileids" name="ids" value="${ids}" />
		<c:forEach items="${followList}" var="entry">
			<div style="width:20%;float:left;margin:5px 8px;">
				<div class="widget-box pricing-box">
					<div class="widget-header header-color-green" style="min-height:0"></div>
					<div class="widget-body">
						<div class="widget-main">
							<span class="profile-picture" style="text-align:center">
								<img id="avatar" alt="..." src="${pageContext.request.contextPath}/static/assets/avatars/profile-pic.jpg" style="display: inline;width:100%;">
							</span>
						</div>
					</div>
					<div class="widget-header header-color-green">
						<label style="cursor:pointer">
							<input name="name" type="checkbox" class="ace" value="${entry}"/><span class="lbl"> ${entry}</span>
						</label>
					</div>
				</div>
			</div>
		</c:forEach>
	</form>
	<div style="clear:both"></div>
	<br/>
	<button id="sharebut" class="btn btn-info" type="button" style="margin:5px 8px;">
		<i class="icon-ok bigger-110"></i>分享
	</button>
	
</body>
</html>