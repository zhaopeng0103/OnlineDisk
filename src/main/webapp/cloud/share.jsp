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

<title>云盘首页 -- 我的分享</title>
<jsp:include page="/public/pub.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/public/top.jsp"></jsp:include>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span></a>
			<jsp:include page="/public/left.jsp"></jsp:include>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
					<ul class="breadcrumb">
						<input type="button" class="button darkblue" value="删除分享" onclick="delShare()" />
					</ul>
				</div>
				<div class="page-content">
					<script type="text/javascript">
				    function delShare(){
					    var ids = [];
					    $("#listdir input[type=checkbox]").each(function(){
						    if(this.checked==true){
							    ids.push(this.value);
							}
					    });
					    if(ids.length>0){
						   layer.load(2);
						   layer.confirm('删除提醒',function(index){
							   $.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/deleteShare.do",
									data : { ids:ids.join(',') },
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
							});
					    }else{
						    layer.msg('你没有选择', 2, -1);
					    }
				    }
				    $(document).ready(function(){
					    $('table th input:checkbox').on('click' , function(){
							var that = this;
							$(this).closest('table').find('tr > td:first-child input:checkbox')
							.each(function(){
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});
								
						});
				    });
				    </script>
					<div class="page-header">
						<h1>${url}</h1>
					</div>
					<!-- /.page-header -->
					<div class="row">
						<div class="col-xs-12">
							<div id="dialog-confirm" class="hide">
								<p class="bigger-110 bolder center grey">
									<i class="icon-hand-right blue bigger-120"></i> 你确定要删除么？
								</p>
							</div>
							<!-- PAGE CONTENT BEGINS -->
							<div>
								<form id="mkdirForm">
									<input type="hidden" id="dir" name="dirName" value="${dir}" />
								</form>
							</div>
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label><input type="checkbox" class="ace" autocomplete="off"/> <span class="lbl" ></span> </label></th>
										<th>分享文件名</th>
										<th>分享文件路径</th>
										<th>分享日期</th>
										<th>被分享用户</th>
									</tr>
								</thead>
								<tbody id="listdir">
									<c:forEach items="${shareList}" var="entry" varStatus="sta">
										<tr>
											<td class="center">
												<label>
													<input type="checkbox" class="ace" value="${entry.shareid }" autocomplete="off"/> <span class="lbl"></span>
												</label>
											</td>
											<td>${entry.name}</td>
											<td>
												<div id="edit01${sta.index}">
													<c:if test="${entry.type=='D'}">${entry.originalPath}</c:if>
													<c:if test="${entry.type=='F'}">${entry.originalPath}${entry.name}</c:if>
												</div>
											</td>
											<td>${entry.sharetime}</td>
											<td>${entry.shareedUserName}</td>
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
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
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
