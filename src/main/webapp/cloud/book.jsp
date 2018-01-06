<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/public/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>云盘记事本</title>
<jsp:include page="/public/pub.jsp"></jsp:include>

<link rel="stylesheet" href="js/kindeditor-4.1.10/themes/default/default.css" />
<script charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'link']
	});
});
</script>
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
						<li class="active">欢迎页面</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<form id="searchUser">
							<input type="text" id="username" name="username" placeholder="输入用户名" /> <input class="button purple" type="button" onclick="getUser()" value="搜索" />

							<textarea id="editor_id" name="content" style="width:700px;height:200px;visibility:hidden;"></textarea>
						</form>

					</div>
					<!-- /.page-header -->
					<div class="row" id="showsearch"></div>

					<c:forEach items="${books}" var="entry">
						<div class="row">
							<div class="col-sm-12 widget-container-span">
								<div class="widget-box">
									<div class="widget-header widget-hea1der-small header-color-dark">
										<h6>记事本</h6>

										<div class="widget-toolbar">
											<a href="#" data-action="settings"> <i class="icon-cog"></i>
											</a> <a href="#" data-action="reload"> <i class="icon-refresh"></i>
											</a> <a href="#" data-action="collapse"> <i class="icon-chevron-up"></i>
											</a> <a href="#"> <i class="icon-remove"></i>
											</a>
										</div>
									</div>

									<div class="widget-body">
										<div class="widget-main padding-4">
											<div class="slim-scroll" data-height="125">
												<div class="content">${entry.content}</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>


				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="/public/container.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<script type="text/javascript">
		function getUser() {
			var html = editor.html();
			// 同步数据后可以直接取得textarea的value
			editor.sync();
			//html = document.getElementById('editor_id').value; // 原生API
			//html = K('#editor_id').val(); // KindEditor Node API
			html = $('#editor_id').val(); // jQuery
			// 设置HTML内容
			editor.html("");
			$.post('${pageContext.request.contextPath}/cloud/bookadd.do', {
				content : html
			}, function(j) {
				if (j.success) {
					location.reload();
				} else {
					alert(json.msg);
				}
			}, 'json');
		}
		function followUser(name) {
			$.post('${pageContext.request.contextPath}/user/follow.do', {
				username : name
			}, function(j) {
				if (j.success) {
					location.reload();
				} else {
					alert(json.msg);
				}
			}, 'json');
		}
		function unfollowUser(name) {
			$.post('${pageContext.request.contextPath}/user/unfollow.do', {
				username : name
			}, function(j) {
				if (j.success) {
					location.reload();
				} else {
					alert(json.msg);
				}
			}, 'json');
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
			
			$('.widget-container-span').sortable({
		        connectWith: '.widget-container-span',
				items:'> .widget-box',
				opacity:0.8,
				revert:true,
				forceHelperSize:true,
				placeholder: 'widget-placeholder',
				forcePlaceholderSize:true,
				tolerance:'pointer'
		    });
		});
	</script>
</body>
</html>
