var $ = layui.$, setter = layui.setter, admin = layui.admin, form = layui.form, router = layui
				.router(), search = router.search, layer = layui.layer;
		!function() {
			doInitUser();
			doInitClass();
			$("#xiaoxue").click(function() {
				if ($("#xiaoxue").attr("class") != "layui-btn layui-btn-disabled")
				doAdd("101");
			});
			$("#chuzhong").click(function() {
				if ($("#chuzhong").attr("class") != "layui-btn layui-btn-disabled")
				doAdd("102");
			});
			$("#gaozhong").click(function() {
				if ($("#gaozhong").attr("class") != "layui-btn layui-btn-disabled")
				doAdd("103");
			});
			form.on('submit(setmyinfo)', function(data) {
				console.log(data);
				doInsertClazzUser(data.field);
				return true;
			});
			$(".back").click(doToAbout);
		}();
		function doToAbout(){
			$(".content").empty();
			$(".content").load("/sys/doAboutUI");//工作线程
		}
		function doInsertClazzUser(params){
			var url = "/user/doUpdateUserInfo";
			$.post(url, params, function(result){
				if (result.state == 1) {
					layer.msg(result.message);
					doToAbout();
				} else {
					layer.msg(result.message);
				}
			});
		}
		function doInitUser() {
			var url = "/sys/doFindUser";
			$.getJSON(url, null, function(result) {
				if (result.state == 1) {
					$("input[name='username']").val(result.data.username);
					$("input[name='name']").val(result.data.name);
					$("input[name='nickname']").val(result.data.nickname);
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
							$("#xiaoxue").text(
									result.data[i].year + "界"
											+ result.data[i].schoolname
											+ result.data[i].clazz + "班");
							$("#xiaoxue").prop("class",
									"layui-btn layui-btn-disabled");
						}
						if (result.data[i].level == 102) {
							$("#chuzhong").text(
									result.data[i].year + "界"
											+ result.data[i].schoolname
											+ result.data[i].clazz + "班");
							$("#chuzhong").prop("class",
									"layui-btn layui-btn-disabled");
						}
						if (result.data[i].level == 103) {
							$("#gaozhong").text(
									result.data[i].year + "界"
											+ result.data[i].schoolname
											+ result.data[i].clazz + "班");
							$("#gaozhong").prop("class",
									"layui-btn layui-btn-disabled");
						}
					}
				} else {
					layer.msg(result.message);
				}

			});
		}

		function doAdd(level) {
			var btname = "保存";
			var title = "新增信息";
			layer.open({
						type : 2,
						title : title, //不显示标题栏
						closeBtn : 2,
						area : [ "400px", "500px" ],
						shade : 0.8,
						id : (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
						btn : [ btname, '取消' ],
						btnAlign : 'r',
						moveType : 1, //拖拽模式，0或者1
						content : "/about/doAddSchoolUI",
						success : function(layero, index) {
							var body = layer.getChildFrame('body', index);
							var iframeWin = window[layero.find('iframe')[0]['name']];
							body.find('input[name=schoollevel]').val(level);
						},
						yes : function(index, layero) {
							var body = layer.getChildFrame('body', index);
							var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
							var schoolid = body.find('select[name=schoolname]').val();
							var schoolname = body.find("option[value='"+schoolid+"']").text();
							var clazz = body.find('input[name=class]').val();
							var year =  body.find('input[name=year]').val();
							var params = {
								"schoolid" : schoolid,
								"clazz" : clazz,
								"level" : level,
								"year" : year,
								"name" : year + "界" + schoolname + clazz + "班"
							}
							var url = "/class/doAddClass"
							$.post(url, params, function(result) {
								if (result.state == 1) {
									initVal(params, schoolname,level,result);
								} else {
									layer.msg(result.message);
								}
							});
							layer.close(layer.index);
						},
						btn2 : function(index, layero) {
							console.log("fuck");
						}
					});
		}
		function initVal(params, schoolname,level,result){
			if (level == 101) {
				$("input[name='xiaoxue']").val(result.message);
				$("#xiaoxue").text(params.year + "界" + schoolname + params.clazz + "班");
			}
			if (level == 102) {
				$("input[name='chuzhong']").val(result.message);
				$("#chuzhong").text(params.year + "界" + schoolname + params.clazz + "班");
			}
			if (level == 103) {
				$("input[name='gaozhong']").val(result.message);
				$("#gaozhong").text(params.year + "界" + schoolname + params.clazz + "班");
			}
		}