var table = layui.table, form = layui.form, $ = layui.jquery, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
		!function() {
			// 加载所有省的选项
			doClear();
			// 查询按钮事件
			form.on('submit(query)', function(data) {
				if (data.field.schoolname != "" || data.field.provinceid != ""){
					doQuery(data.field);
				}else{
					layer.msg("请筛选查询!");
				}
				return false;
				
			});
			$(".form-box").on("click", "#clear", doClear);
			// 新增学校
			$(".form-box").on("click", "#addSchool", doAdd);
			// 初始化下拉框
			doInitSelect();
			// 删除学校信息
			//监听行工具事件
			table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'edit') {
					doAdd(data);
				} else if (layEvent === 'del') {
					layer.confirm('真的删除行么', function(index) {
						doDeleteSchoolById(obj.data.id, obj);
					});
				} else if (layEvent === 'edit') {
					layer.msg('编辑操作');
				}
			});
		}();

		function doAdd(data) {
			console.log(data.id);
			var btname = "保存";
			var title = "新增信息";
			var url = "/school/doSaveSchool";
			if (data.id) {
				btname = "修改";
				title = "修改信息";
				url = "/school/doUpdateSchool"
			}
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
				content : "/school/doEditSchoolUI",
				success : function(layero, index) {
					var body = layer.getChildFrame('body', index);
					var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					body.find('input[name=schoolname]').val(data.schoolname);

				},
				yes : function(index, layero) {
					var body = layer.getChildFrame('body', index);
					var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					var params = {
						"id" : data.id,
						"schoolname" : body.find('input').val(),
						"provinceid" : body.find('select[name=provinceid]')
								.val(),
						"cityid" : body.find('select[name=cityid]').val(),
						"countyid" : body.find('select[name=countyid]').val(),
						"sctypeid" : body.find('select[name=sctypeid]').val()
					}
					if (body.find('input').val()) {
						$.post(url, params, function(result) {
							if (result.state == 1) {
								layer.msg(result.message);
								doQuery(params);
							} else {
								layer.msg(result.message);
							}
						});
						layer.close(layer.index);
					} else {
						layer.msg("请输入学校名称");
					}
				},
				btn2 : function(index, layero) {
					console.log("fuck");
				}

			});
		}
		function doDeleteSchoolById(id, obj) {
			var params = {
				"id" : id
			};
			//3.定义url
			var url = "/school/doDeleteSchoolById"
			var data;
			//4.发送异步请求执行删除操作
			$.post(url, params, function(result) {
				if (result.state) {
					obj.del(); //删除对应行（tr）的DOM结构
					//向服务端发送删除指令
					layer.msg("删除成功");
				} else {
					layer.msg(result.message);
				}
			});
			return data;
		}

		// 查询学校
		function doQuery(param) {
			var url = "/school/doFindAllObject";
			layer.load();
			$.getJSON(url, param, function(result) {
				doGetObjects(result.data);
			});
		}

		// 显示查询到的学校列表
		function doGetObjects(list) {
			var element = layui.element;
			var data = list;
			//展示已知数据
			table.render({
				elem : '#demo',
				cols : [ [ //标题栏
				{
					field : 'id',
					title : 'ID',
					width : 80,
					sort : true
				}, {
					field : 'schoolname',
					title : '学校名称',
					minWidth : 250
				}, {
					field : 'city',
					title : '城市',
					minWidth : 100
				}, {
					field : 'county',
					title : '县/区',
					minWidth : 100
				}, {
					field : 'province',
					title : '省',
					minWidth : 100
				}, {
					field : 'sctype',
					title : '学校类型',
					minWidth : 150
				}, {
					fixed : 'right',
					minWidth : 120,
					align : 'center',
					toolbar : '#barDemo'
				} ] ],
				data : data,
				skin : 'line' //表格风格
				,
				even : true,
				page : true //是否显示分页
				,
				limits : [ 5, 7, 10 ],
				limit : 5
			//每页默认显示的数量
			});
			layer.closeAll('loading');
		}