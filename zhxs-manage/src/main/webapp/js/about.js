var $ = layui.$;
	!function() {
		doLoadUI("info", "/about/doInfoUI");
		initInfo();
		doInitClass();
		$("#alt_password").click(altPwd);
	}();
	function altPwd() {
		var btname = "修改";
		var title = "密码修改";
		layer.open({
			type : 2,
			title : title, //不显示标题栏
			closeBtn : 2,
			area : [ "400px", "380px" ],
			shade : 0.8,
			id : (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
			btn : [ btname, '取消' ],
			btnAlign : 'r',
			moveType : 1, //拖拽模式，0或者1
			content : "/about/doAltPwdUI",
			yes : function(index, layero) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var oldPassword = body.find('input[name=oldPassword]').val();
				var password = body.find('input[name=password]').val();
				var repassword = body.find('input[name=repassword]').val();
				if (password != repassword) {
					layer.msg("两次密码不一样");
					return;
				}
				var params = {
					"oldPassword" : oldPassword,
					"password" : password
				}
				var url = "/user/doupdataPassword"
				$.ajax({
					type : "post",
					url : url,
					data : params,
					async : false,
					success : function(result) {
						if (result.state == 1) {
							layer.msg(result.message);
							layer.close(index);
						} else {
							layer.msg(result.message);
						}
					}
				});
			},
			btn2 : function(index, layero) {
			}
		});
	}
	function initInfo() {
		var url = "/sys/doFindUser";
		$.getJSON(url, null,
				function(result) {
					if (result.state == 1) {
						$("span[name='name']").text("姓名: " + result.data.name);
						$("span[name='nickname']").text(
								"昵称: " + result.data.nickname);
						$("span[name='username']").text(
								"用户名: " + result.data.username);
					} else {
						layer.msg(result.message);
					}
				});
	}
	function doInitClass() {
		var url = "/class/doFindclass";
		$.getJSON(url, null, function(result) {
			if (result.state == 1) {
				for (var i = 0; i < result.data.length; i++) {
					if (result.data[i].level == 101) {
						$("span[name='xschool']").text(
								"小学: " + result.data[i].year + "界"
										+ result.data[i].schoolname
										+ result.data[i].clazz + "班");
					}
					if (result.data[i].level == 102) {
						$("span[name='cschool']").text(
								"初中: " + result.data[i].year + "界"
										+ result.data[i].schoolname
										+ result.data[i].clazz + "班");
					}
					if (result.data[i].level == 103) {
						$("span[name='gschool']").text(
								"高中: " + result.data[i].year + "界"
										+ result.data[i].schoolname
										+ result.data[i].clazz + "班");
					}
				}
			} else {
				layer.msg(result.message);
			}

		});
	}
	function doLoadUI(id, url) {
		$("#" + id).click(function() {
			$(".content").load(url);//工作线程
		});
	}