!function() {
			var $ = layui.$, setter = layui.setter, admin = layui.admin, form = layui.form, router = layui
					.router();

			form.render();

			//提交
			form.on('submit(LAY-user-reg-submit)', function(obj) {
				var field = obj.field;
				//密码格式/^[\S]{6,12}$/
				var userReg=/^[A-Za-z0-9]{6,12}$/;
				var reg = /^[\S]{6,12}$/;
				if (!userReg.test(field.username)) {
					return layer.msg('用户名由6到12位的数字和英文字母组成');
				}
				if (!reg.test(field.password)) {
					return layer.msg('密码必须6到12位，且不能出现空格');
				}
				//确认密码
				if (field.password !== field.repass) {
					return layer.msg('两次密码输入不一致');
				}

				//是否同意用户协议
				if (!field.agreement) {
					return layer.msg('你必须同意用户协议才能注册');
				}

				//请求接口
				var insertUrl = "/user/doSaveUser";
				//2.获取表单数据
				//3.发起异步请求
				$.post(insertUrl, field, function(result) {
					if (result.state == 1) {
						layer.msg(result.message, {
							offset : '15px',
							icon : 1,
							time : 1000
						}, function() {
							location.href = '/doLoginUI'; //跳转到登入页
						});
					} else {
						layer.msg(result.message)
					}
				});
				return false;
			});
		}();