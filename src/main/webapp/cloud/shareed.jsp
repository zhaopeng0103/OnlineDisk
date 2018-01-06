<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/public/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>云盘首页 -- 收到的分享</title>
<jsp:include page="/public/pub.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/public/top.jsp"></jsp:include>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span>
			</a>
			<jsp:include page="/public/left.jsp"></jsp:include>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
				</div>
				<div class="page-content">
					<script type="text/javascript">
						function topath(path){
							var form = document.forms[0];
							form.path.value=path;
							form.target="_blank";
							form.submit();
						}
						function downName(originalName, path, name){
						   layer.load(2);
						   $.ajax({
								type:"POST",
								url:"${pageContext.request.contextPath}/downloadFile.do",
								data:{ originalName:originalName, path:path, name:name },
								dataType:"json",
								async: false,
								success:function(res){
									layer.closeAll('loading');
									if (res.errres) {
										window.open('${pageContext.request.contextPath}/' + res.url);
								    }else{
										layer.msg(res.errmsg, 2, -1);
								    }
								}
						   });
					    }
						function viewName(originalName, path, name){
						    $.layer({
							    type: 2,
							    border: [0],
							    title: false,
							    closeBtn: [0, true],
							    iframe: {src : '${pageContext.request.contextPath}/viewFile.do?originalName=' + originalName + '&path=' + path + '&name=' + name},
							    area: ['950px', '600px']
							});
					    }
					</script>
					<div class="page-header">
						<h1>${url}</h1>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label><input type="checkbox" class="ace" autocomplete="off"/> <span class="lbl" ></span> </label></th>
										<th>分享文件名</th>
										<th>分享文件路径</th>
										<th>分享日期</th>
										<th>分享人</th>
										<th></th>
									</tr>
								</thead>
								<tbody id="listdir">
									<c:forEach items="${shareedList}" var="entry" varStatus="sta">
										<tr>
											<td class="center">
												<label>
													<input type="checkbox" class="ace" value="" autocomplete="off"/> <span class="lbl"></span>
												</label>
											</td>
											<td>${entry.name}</td>
											<td>
												<c:if test="${entry.type=='D'}">
													<a href="javascript:topath('${entry.path}')">${entry.originalPath}</a>
												</c:if>
												<c:if test="${entry.type=='F'}">${entry.originalPath}${entry.name}</c:if>
											</td>
											<td>${entry.sharetime}</td>
											<td>${entry.shareUserName}</td>
											<td>
												<c:if test="${entry.type=='F'}">
													<button class="btn btn-xs btn-info" onclick="downName('${entry.name}','${entry.path}','${entry.shareUserName}')" title="下载">
														<i class="icon-cloud-download bigger-120"></i>
													</button>
													<button class="btn btn-xs btn-info" onclick="viewName('${entry.name}','${entry.path}','${entry.shareUserName}')" title="浏览">
														<i class="icon-eye-open bigger-120"></i>
													</button>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="/public/container.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="icon-double-angle-up icon-only bigger-110"></i></a>
	</div>
	<!-- /.main-container -->
	<script type="text/javascript">
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
