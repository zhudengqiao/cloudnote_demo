//login.js 封装登录和注册处理
//主处理
$(function() {// 页面载入完毕
	// 给登录按钮绑定单击处理
	$("#login").click(checkLogin);
	// 给注册按钮绑定单击处理
	$("#regist_button").click(registUser);
});
// 注册处理
function registUser() {
	// 获取请求参数
	var name = $("#regist_username").val().trim();
	var nick = $("#nickname").val().trim();
	var password = $("#regist_password").val().trim();
	var f_password = $("#final_password").val().trim();
	// 格式检查
	// 清空消息
	// $("#warning_1 span").html("");
	// $("#warning_2 span").html("");
	// $("#warning_3 span").html("");
	var ok = true;// 是否通过验证
	if (name == "") {
		ok = false;
		$("#warning_1").show();
	}
	if (password == "") {
		ok = false;
		$("#warning_4").show();
	} else if (password.length < 6) {
		ok = false;
		$("#warning_2").show();
	}
	if (f_password != password) {
		ok = false;
		$("#warning_3").show();
	}
	// 发送Ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/user/add.do",
			type : "post",
			data : {
				"name" : name,
				"password" : password,
				"nick" : nick
			},
			dataType : "json",
			success : function(result) {
				if (result.status == 0) {// 成功
					alert(result.msg);// 提示成功
					$("#back").click();// 转向登录界面
				} else if (result.status == 1) {// 用户名被占
					$("#warning_1").show();
					$("#warning_1 span").html(result.msg);
				}
			},
			error : function() {
				alert("注册异常");
			},
		});
	}
}
// 登录处理
function checkLogin() {
	// 获取请求参数
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	// 检测参数格式
	// 清空以前提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	var ok = true;// 是否通过检测
	if (name == "") {
		ok = false;
		$("#count_span").html("用户名为空");
	}
	if (password == "") {
		ok = false;
		$("#password_span").html("密码为空");
	}
	// 发送Ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/user/login.do",
			type : "post",
			data : {
				"name" : name,
				"password" : password
			},
			dataType : "json",
			success : function(result) {
				// result是服务器返回的JSON结果
				if (result.status == 0) {// 成功
					var user = result.data;// 获取返回的user信息
					// 写入Cookie
					addCookie("uid", user.cn_user_id, 2);
					addCookie("uname", user.cn_user_name, 2);
					window.location.href = "edit.html";
				} else if (result.status == 1) {// 用户名不存在
					$("#count_span").html(result.msg);
				} else if (result.status == 2) {// 密码错误
					$("#password_span").html(result.msg);
				}
			},
			error : function() {
				alert("登录异常");
			}
		});
	}
}