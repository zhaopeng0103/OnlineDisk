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
function topath(path){
	var form = document.forms[0];
	form.path.value=path;
	form.submit();
}
</script>
</head>

<body>
<div class="page-header"><h1>${url}</h1></div>
	<form action="cloud/shareedList.do" method="post">
		<input type="hidden" name="path"/>
		<input type="hidden" name="dir" value="${dir}"/>
		<table id="sample-table-1" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th class="center"><label><input type="checkbox" class="ace" autocomplete="off" /> <span class="lbl"></span> </label></th>
					<th>分享文件</th>
					<th>分享日期</th>
					<th></th>
				</tr>
			</thead>
			<tbody id="listdir">
				<c:forEach items="${shares}" var="entry" varStatus="sta">
					<tr>
						<td class="center"><label> <input type="checkbox" class="ace" value="" autocomplete="off" /> <span class="lbl"></span>
						</label></td>
						<td><c:if test="${entry.type=='D'}">
								<a href="javascript:topath('${entry.name}')">${entry.name}</a>
							</c:if> <c:if test="${entry.type=='F'}">${entry.name}</c:if></td>
						<td>${entry.date}</td>
						<td>
							<button class="btn btn-xs btn-info" onclick="editName(${sta.index})" title="重命名">
								<i class="icon-edit bigger-120"></i>
							</button>
							<button class="btn btn-xs btn-info" onclick="viewName(${sta.index})" title="浏览">
								<i class="icon-eye-open bigger-120"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>