<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>登录云盘</title>
<!-- basic styles -->

<link
	href="${pageContext.request.contextPath}/static/assets/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

<!-- page specific plugin styles -->

<!-- ace styles -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/assets/css/ace-rtl.min.css" />

<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/static/assets/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath}/static/assets/js/respond.min.js"></script>
		<![endif]-->
</head>

<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i> 登陆页面
										</h4>
										<div class="space-6"></div>
										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" name="userName" id="username" class="form-control"
														placeholder="用户名" /> <i class="icon-user"></i>
												</span>
												</label> 
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" name="pwd" id="password" class="form-control"
														placeholder="密码" /> <i class="icon-lock"></i>
												</span>
												</label>
												
												<p id="loginResult"></p>
												
												<div class="space-4"></div>
												
												<div class="clearfix">
													<label class="inline"> <input type="checkbox" id="remberMe"
														class="ace" /> <span class="lbl"> 记住我</span>
													</label>

													<button type="button" id="loginUser"
														class="width-35 pull-right btn btn-sm btn-primary">
														<i class="icon-key"></i> 登陆
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
									<!-- /widget-main -->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i class="icon-arrow-left"></i>
												忘记密码
											</a>
										</div>

										<div>
											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> 注册 <i class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /login-box -->

							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> 找回密码
										</h4>

										<div class="space-6"></div>
										<p>输入你的注册邮箱</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="邮箱" /> <i
														class="icon-envelope"></i>
												</span>
												</label>

												<div class="clearfix">
													<button type="button"
														class="width-35 pull-right btn btn-sm btn-danger">
														<i class="icon-lightbulb"></i> 发送
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!-- /widget-main -->

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> 回到登陆 <i
											class="icon-arrow-right"></i>
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /forgot-box -->

							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i> 注册用户
										</h4>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" name="email" id="email" class="form-control"
														placeholder="邮箱" /> <i class="icon-envelope"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" name="name" id="name" class="form-control"
														placeholder="用户名" /> <i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" name="pwd" id="pwd" class="form-control"
														placeholder="密码" /> <i class="icon-lock"></i>
												</span>
												</label>
												<p id="regResult"></p>
												<div class="space-4"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="icon-refresh"></i> 重置
													</button>

													<button type="button"
														class="width-65 pull-right btn btn-sm btn-success" id="regUser">
														注册 <i class="icon-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											回到登陆
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /signup-box -->
						</div>
						<!-- /position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->

	<script src="${pageContext.request.contextPath}/static/assets/js/jquery-2.0.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/assets/js/jquery.cookie.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="${pageContext.request.contextPath}/static/assets/js/jquery-1.10.2.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->

	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='${pageContext.request.contextPath}/static/assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${pageContext.request.contextPath}/static/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='${pageContext.request.contextPath}/static/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		function show_box(id) {
			jQuery('.widget-box.visible').removeClass('visible');
			jQuery('#' + id).addClass('visible');
		}
	</script>
	<script>
	$(function(){
		var checkFlg = false;
		$("#remberMe").click(function(){
		    if (!checkFlg) {
		        $("#remberMe").attr("checked", true);
		    } else {
		        $("#remberMe").attr("checked", false);
		    }
		    checkFlg = !checkFlg;
		});
		if ($.cookie("remberMe") == "true") {
	        $("#remberMe").attr("checked", true);
	        $("#username").val($.cookie("username"));
	        $("#password").val($.cookie("password"));
	        checkFlg = true;
	    }
		$("#regUser").click(function(){
			$("#regResult").html("正在注册中...");
			$("#regResult").css("color","deepskyblue");
    		var name = $("#name").val();
    		var email = $("#email").val();
    		var pwd = $("#pwd").val();
    		var EmailFont = /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
			if(name==null||name==""){
				$("#regResult").html("请输入用户名！");
				$("#regResult").css("color","red");
				return false;
    		}else if(email==null||email==""){
    			$("#regResult").html("请输入邮箱！");
				$("#regResult").css("color","red");
				return false;
    		}else if(!EmailFont.test(email)){
    			$("#regResult").html("邮箱格式不正确！");
				$("#regResult").css("color","red");
				return false;
    		}else if(pwd==null||pwd==""){
    			$("#regResult").html("请输入密码！");
				$("#regResult").css("color","red");
				return false;
    		}else{
    			$.ajax({
					url:"${pageContext.request.contextPath}/regUserAction.do",
					type:"post",
					dataType:"text",
					data:{"name":name,"email":email,"pwd":pwd},
					success:function(data){
						var result=eval('('+data+')');
						if(result.errres){
							$("#regResult").html(result.errmsg);
							$("#regResult").css("color","green");
			    			window.location.href="${pageContext.request.contextPath}/login.do";
						}else{
							$("#regResult").html(result.errmsg);
							$("#regResult").css("color","red");
						}
					}
    			});
		    }
    	});
		$("#loginUser").click(function(){
			$("#loginResult").html("正在登录中...");
			$("#loginResult").css("color","deepskyblue");
    		var username = $("#username").val();
    		var password = $("#password").val();
    		
			if(username==null||username==""){
    			$("#loginResult").html("请输入用户名！");
				$("#loginResult").css("color","red");
				return false;
    		}else if(password==null||password==""){
    			$("#loginResult").html("请输入密码！");
				$("#loginResult").css("color","red");
				return false;
    		}else{
    			$.ajax({
					url:"${pageContext.request.contextPath}/loginUserAction.do",
					type:"post",
					dataType:"text",
					data:{"name":username,"pwd":password},
					success:function(data){
						var result=eval('('+data+')');
						if(result.errres){
							$("#loginResult").html(result.errmsg);
							$("#loginResult").css("color","green");
							if (checkFlg) {
							    $.cookie("remberMe", "true", { expires: 7 }); // 记住我勾选
							    $.cookie("username", username, { expires: 7 }); // 存储一个带7天期限的 cookie
							    $.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
							} else {
							    $.cookie("remberMe", "false", { expires: -1 }); // 删除 cookie
							    $.cookie("username", '', { expires: -1 });
							    $.cookie("password", '', { expires: -1 });
							}
			    			window.location.href="${pageContext.request.contextPath}/index.do";
						}else{
							$("#loginResult").html(result.errmsg);
							$("#loginResult").css("color","red");
						}
					}
    			});
		    }
    	});
	});
</script>
</body>
</html>
