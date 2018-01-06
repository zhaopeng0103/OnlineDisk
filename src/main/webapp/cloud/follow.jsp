<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/public/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>云盘用户关注</title>
<jsp:include page="/public/pub.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/public/top.jsp"></jsp:include>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span>
			</a>
			<jsp:include page="/public/left.jsp"></jsp:include>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">关注用户</li>
					</ul>
				</div>

				<div class="page-content">
					<div class="page-header">
						<form id="searchUser">
							<input type="text" id="name" name="name" placeholder="输入用户名" value="${searchName }"/> <input class="button purple" type="button" onclick="getUser()" value="搜索" />
						</form>
					</div>
					<div class="row" id="showsearch">
						<c:forEach items="${userList}" var="entry">
							<div class="col-lg-2 col-md-2">
								<div class="widget-box pricing-box">
									<div class="widget-header header-color-green" style="min-height:0"></div>
									<div class="widget-body">
										<div class="widget-main">
											<span class="profile-picture" style="width:100%;text-align:center">
												<img id="avatar" alt="..." src="${pageContext.request.contextPath}/static/assets/avatars/profile-pic.jpg" style="display: inline;width:100%;">
											</span>
											<div style="text-align:center;font-size:20px;color:grey">${entry}</div>
										</div>
										<div>
											<button type="button" onclick="followUser('${entry}')" class="btn btn-block btn-small btn-success">
												<span class="glyphicon glyphicon-ok"></span> 立刻关注
											</button>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<br/>
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>已关注用户</li>
						</ul>
					</div>
					<br/>
					<div class="row">
						<c:forEach items="${followList}" var="entry">
							<div class="col-lg-2 col-md-2">
								<div class="widget-box pricing-box">
									<div class="widget-header header-color-blue" style="min-height:0"></div>
									<div class="widget-body">
										<div class="widget-main">
											<span class="profile-picture" style="width:100%;text-align:center">
												<img id="avatar" alt="..." src="${pageContext.request.contextPath}/static/assets/avatars/profile-pic.jpg" style="display: inline;width:100%;">
											</span>
											<div style="text-align:center;font-size:20px;color:grey">${entry}</div>
										</div>
										<div>
											<button type="button" onclick="unfollowUser('${entry}')" class="btn btn-block btn-small btn-primary">
												<span class="glyphicon glyphicon-remove"></span> 取消关注
											</button>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="/public/container.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="icon-double-angle-up icon-only bigger-110"></i></a>
	</div>
	<script type="text/javascript">
		function getUser() {
			var name = $.trim($("#name").val());
			if (name == null || name == '') {
				layer.msg("请输入你要找的用户名", 2, -1);
				return false;
			}
			window.location.href = "${pageContext.request.contextPath}/followList.do?name=" + name;
		}
		
		function followUser(name) {
			layer.load(2);
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/followUser.do",
				data : { name : name },
				dataType : "json",
				async: false,
				success : function(res) {
					layer.closeAll('loading');
					if (res.errres) {
						location.reload();
					}else {
						layer.msg(res.errmsg, 2, -1);
					}
				}
			});
		}
		function unfollowUser(name) {
			layer.load(2);
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/unfollowUser.do",
				data : { name : name },
				dataType : "json",
				async: false,
				success : function(res) {
					layer.closeAll('loading');
					if (res.errres) {
						location.reload();
					}else {
						layer.msg(res.errmsg, 2, -1);
					}
				}
			});
		}
		jQuery(function($) {
			$('#tasks').sortable({
				opacity:0.8,
				revert:true,
				forceHelperSize:true,
				placeholder: 'draggable-placeholder',
				forcePlaceholderSize:true,
				tolerance:'pointer',
				stop: function( event, ui ) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
					$(ui.item).css('z-index', 'auto');
				}
				}
			);
			$('#tasks').disableSelection();
			$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
				if(this.checked) $(this).closest('li').addClass('selected');
				else $(this).closest('li').removeClass('selected');
			});
		})
	</script>
</body>
</html>
