		!function() {
			// 查询按钮事件
			form.on('submit(query)', function(data) {
				doQuery(data.field);
				return false;
			});
			// 删除学校信息
			//监听行工具事件
			table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
					doDeleteUserById(obj.data.id, obj);
				});
			});
		}();
		function doDeleteUserById(id, obj) {
			var params = {
				"id" : id
			};
			//3.定义url
			var url = "/user/doDeleteUserById"
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

		// 查询用户
		function doQuery(param) {
			var url = "/user/doFindAllUser";
			$.getJSON(url, param, function(result) {
				console.log(result);
				doGetObjects(result.data);
			});
		}

		// 显示查询到的用户列表
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
					width : 160,
					sort : true
				}, {
					field : 'username',
					title : '用户名',
					width : 110
				}, {
					field : 'password',
					title : '密码',
					minWidth : 270
				}, {
					field : 'name',
					title : '姓名',
					width : 110
				}, {
					field : 'nickname',
					title : '昵称',
					width : 110
				}, {
					field : 'createtime',
					title : '创建时间',
					width : 150,
					templet : function(d) {
						return createTime(d.createtime);
					}
				}, {
					fixed : 'right',
					width : 80,
					align : 'center',
					toolbar : '#barDemo'
				} ] ],
				data : data,
				skin : 'line' //表格风格
				,
				even : true,
				page : true //是否显示分页
				,
				limits : [ 10, 20, 30, 40, 50 ],
				limit : 10
			//每页默认显示的数量
			});
		}
		function createTime(v) {
			var date = new Date(v);
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			m = m < 10 ? '0' + m : m;
			var d = date.getDate();
			d = d < 10 ? ("0" + d) : d;
			var str = y + "年" + m + "月" + d + "日";
			return str;
		}