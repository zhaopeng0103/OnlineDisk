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

<title>云盘首页</title>
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

					<ul class="breadcrumb">
						<input type="button" class="button purple" value="上传文件" onclick="upload()" />
						<input type="button" class="button blue" value="创建文件夹" onclick="mkdir()" />
						<input type="button" class="button darkblue" value="删除" onclick="deldir()" />
						<input type="button" class="button teal" value="分享" onclick="share()" />
						<input type="button" class="button teal" value="复制" onclick="copyOrMove(false)" />
						<input type="button" class="button teal" value="移动" onclick="copyOrMove(true)" />
					</ul>
					<!-- .breadcrumb -->

				</div>

				<div class="page-content">
					<script type="text/javascript">
						   function mkdir(){
							   var str = '';
							   str +="<div><input type='text' id='mkdir' name='mkdir' placeholder='文件名'/>";
							   str +="<input class='button purple' type='button' onclick='addDir()' value='提交'/>&nbsp;&nbsp;&nbsp;";
							   str +="<input class='button black' type='button' onclick='removeDir()' value='取消'/></div>";
							   $("#mkdirForm").append(str);
						   }
						   function addDir(){
							   $("#mkdir").focus();
							   var mkdir = $.trim($("#mkdir").val());
							   if(mkdir==null || mkdir==''){
								   layer.msg('请输入文件夹名称', 2, -1);
								   return false;
							   }
							   layer.load(2);
							   var data = $("#mkdirForm").formToArray();
								$.ajax({
									type:"POST",
									url:"${pageContext.request.contextPath}/makeFolder.do",
									data:data,
									dataType:"json",
									async: false,
									success:function(res){
										layer.closeAll('loading');
										if(res.errres){
											$("#mkdirForm").empty();
											layer.msg('操作成功！', 2, -1);
											location.reload();
										}else{
											layer.msg(res.errmsg, 2, -1);
										}
									}
								});
						   }
						   function removeDir(){
							   $("#mkdirForm").empty();
						   }
						   function selectBox(){
							   $("#listdir input[type=checkbox]").each(function(){
								   if(this.checked==true){
										this.checked=false;
									}else{
										this.checked=true;
									}
							   });
						   }
						   function deldir(){
							   var ids = [];
							   var parentid =$("#parentid").val();
							   $("#listdir input[type=checkbox]").each(function(){
								   if(this.checked==true){
									   ids.push(this.value);
									}
							   });
							   if(ids.length>0){
								   layer.confirm('删除提醒',function(index){
									   layer.load(2);
									   $.ajax({
											type:"POST",
											url:"${pageContext.request.contextPath}/deleteFileOrFolder.do",
											data:{ ids:ids.join(','), parentid:parentid },
											dataType:"json",
											async: false,
											success:function(res){
												layer.closeAll('loading');
											    if (res.errres) {
													layer.msg('操作成功！', 2, -1);
													location.reload();
												}else{
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
						   function upload(){
							   var dir = $("#dir").val();
							   var originalDir = $("#originalDir").val();
							   var parentid =$("#parentid").val();
							   $.layer({
								    type: 2,
								    border: [0],
								    title: false,
								    closeBtn: [0, true],
								    iframe: {src : '${pageContext.request.contextPath}/showUpload.do?dir=' + dir + "&originalDir=" + originalDir + "&parentid=" + parentid},
								    area: ['510px', '450px']
								});
						   }
						   function editName(index, name){
							   $("#edit01"+index).hide();
							   $("#edit02"+index).show();
							   $("#rename"+index).focus();
							   $("#rename"+index).val(name.split(".")[0]);
						   }
						   function removeBut(index){
							   $("#edit02"+index).hide();
							   $("#edit01"+index).show();
						   }
						   function renameBut(id,name,type){
							   var parentid =$("#parentid").val();
							   var newname = $.trim($("#rename"+id).val());
							   if(newname==null || newname==''){
								   layer.msg('请输入名称', 2, -1);
								   return false;
							   }
							   layer.load(2);
							   $.ajax({
									type:"POST",
									url:"${pageContext.request.contextPath}/renameFileOrFolder.do",
									data:{ id:id, name:name, newname:newname, type:type, parentid:parentid },
									dataType:"json",
									async: false,
									success:function(res){
										layer.closeAll('loading');
										if (res.errres) {
									    	layer.msg('操作成功！', 2, -1);
											location.reload();
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
						   function share(){
							   var ids = [];
							   $("#listdir input[type=checkbox]").each(function(){
								   if(this.checked==true){
									   ids.push(this.value);
									}
							   });
							   //alert(types);
							   if(ids.length>0){
								   $.layer({
									    type: 2,
									    border: [0],
									    title: '选择要分享的用户',
									    closeBtn: [0, true],
									    iframe: {src : '${pageContext.request.contextPath}/getFollowUser.do?ids=' + ids.join(',')},
									    area: ['860px', '400px']
									});
							   }else{
								   layer.msg('你没有选择', 2, -1);
							   }
						   }
						   function copyOrMove(flag){
							   var ids = [];
							   var parentid =$("#parentid").val();
							   $("#listdir input[type=checkbox]").each(function(){
								   if(this.checked==true){
									   ids.push(this.value);
									}
							   });
							   if(ids.length>0){
								   $.layer({
									    type: 2,
									    border: [0],
									    title: '复制到',
									    closeBtn: [0, true],
									    iframe: {src : '${pageContext.request.contextPath}/showTree.do?ids='+ids.join(',')+'&flag='+flag+'&parentid='+parentid},
									    area: ['200px', '300px']
									});
							   }else{
								   layer.msg('你没有选择', 2, -1);
							   }
						   }
						</script>
					<div class="page-header">
						<ul class="breadcrumb">
							<c:forEach items="${breadcrumblist}" var="breadcrumb" varStatus="sta">
								<c:if test="${!sta.last}">
									<li><a href="${pageContext.request.contextPath}/fileList.do?dir=${breadcrumb.path}&originalDir=${breadcrumb.originalPath }&parentid=${breadcrumb.id}">${breadcrumb.originalName}</a></li>
								</c:if>
								<c:if test="${sta.last}">
									<li class="active">${breadcrumb.originalName}</li>
								</c:if>
							</c:forEach>
						</ul>
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
									<input type="hidden" id="originalDir" name="originalDir" value="${originalDir}" />
									<input type="hidden" id="parentid" name="parentid" value="${parentid}" />
								</form>
							</div>
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label><input type="checkbox" class="ace" autocomplete="off"/> <span class="lbl" ></span> </label></th>
										<th>文件名</th>
										<th>大小</th>
										<th>修改日期</th>
										<th></th>
									</tr>
								</thead>
								<tbody id="listdir">
										<c:forEach items="${filelist}" var="entry" varStatus="sta">
										<tr>
											<td class="center"><label> <input type="checkbox" class="ace" value="${entry.id}" data-type="${entry.type}" autocomplete="off"/> <span class="lbl"></span>
											</label></td>
											<td><div id="edit01${entry.id}">
													<c:if test="${entry.type=='D'}">
														<a href="${pageContext.request.contextPath}/fileList.do?dir=${entry.path}&originalDir=${entry.originalPath }&parentid=${entry.id}">${entry.originalName}</a>
													</c:if>
													<c:if test="${entry.type=='F'}">${entry.originalName}</c:if>
												</div>

												<div id="edit02${entry.id}" class="col-xs-8 col-sm-8" style="display: none">
													<div class="input-group">
														<input type="text" id="rename${entry.id}" name="name" class="form-control"> 
														<span class="input-group-btn">
															<button class="btn btn-purple btn-sm" onclick="renameBut(${entry.id},'${entry.originalName}','${entry.type}')" type="button" title="提交">
																<i class="icon-ok bigger-110"></i>
															</button>
															<button class="btn btn-purple btn-sm" onclick="removeBut(${entry.id})" type="button" title="取消">
																<i class="icon-remove bigger-110"></i>
															</button>
														</span>
													</div>
												</div> 
											</td>
											<td><c:if test="${entry.type=='D'}">文件夹</c:if> <c:if test="${entry.type=='F'}">${entry.size}</c:if></td>
											<td>${entry.date}</td>
											<td>
												<button class="btn btn-xs btn-info" onclick="editName(${entry.id},'${entry.originalName }')" title="重命名">
													<i class="icon-edit bigger-120"></i>
												</button>
												<c:if test="${entry.viewflag=='Y'}">
												<button class="btn btn-xs btn-info" onclick="viewName('${entry.originalName}','${entry.path}', '${currentUser.name }')" title="浏览">
													<i class="icon-eye-open bigger-120"></i>
												</button>
												</c:if>
												<c:if test="${entry.type=='F'}">
												<button class="btn btn-xs btn-info" onclick="downName('${entry.originalName}','${entry.path}', '${currentUser.name }')" title="下载">
													<i class="icon-cloud-download bigger-120"></i>
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
