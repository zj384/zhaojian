var element = layui.element, table = layui.table, $ = layui.$, laypage = layui.laypage;
		!function() {
			getCount();
			getUserCount();
			$(".contentwrap").text(createTime(new Date()));
			$('.layui-card-body .layui-btn').on('click', function(){
			      var checkStatus = table.checkStatus('logTable');
			      var data = checkStatus.data;
			      layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
			    	  doDeleteLogs(data);
			      });
			  });
			
		}();
		function doDeleteLogs(data){
			var ids = [];
			for(var i = 0; i< data.length; i++) {
				ids.push(data[i].id);
			}
			var url = "/con/doDeleteLogsByIds";
			var params = {"ids":ids.toString()};
			console.log(params);
			$.post(url,params,function(result){
				if (result.state == 1) {
					layer.msg(result.message);
					getCount();
				} else {
					layer.msg(result.message);
				}
			});
			console.log(ids.toString());
		}
		function doPageInfo(count) {
			//总页数低于页码总数
			laypage.render({
						elem : 'conPage',
						count : count, //数据总数
						layout : [ 'prev', 'page', 'next', 'limit', 'refresh',
								'skip' ],
						jump : function(obj, first) {
							//obj包含了当前分页的所有参数，比如：
							doFindAllObject(obj.curr, obj.limit);
							//首次不执行 
							if (!first) {
								//do something
							}
						}
					});
		}
		function getUserCount() {
			var url = "/user/doGetUserCount";
			$.getJSON(url, null, function(result) {
				$(".total").text(result.data);
			})
		}
		function getCount() {
			var url = "/con/doLogsCount";
			$.getJSON(url, null, function(result) {
				doPageInfo(result.data);
				$(".logsCount").text(result.data)
			})
		}
		function doFindAllObject(curr, limit) {
			console.log(limit);
			layer.load();
			var url = "/con/doFindAllLogs";
			var params = {
				"curr" : curr,
				"limit" : limit
			};
			$.post(url, params, function(result) {
				if (result.state == 1) {
					initTable(result.data,params.limit);
				}
			});
		}
		function initTable(field,limit) {
			table.render({
				elem : '#logTable',
				data : field,
				cols : [ [ {
					type : 'checkbox'
				}, {
					field : 'id',
					width : 80,
					title : 'ID',
					sort : true
				}, {
					field : 'username',
					width : 80,
					title : '用户名'
				}, {
					field : 'operation',
					width : 120,
					title : '操作'
				}, {
					field : 'method',
					minWidth : 120,
					title : '方法'
				}, {
					field : 'params',
					title : '参数',
					minWidth : 120
				}, {
					field : 'time',
					width : 80,
					title : '执行时间',
					sort : true
				}, {
					field : 'ip',
					width : 130,
					title : 'IP地址'
				}, {
					field : 'createdTime',
					width : 140,
					title : '创建时间',
					templet : function(d) {
						return createTime(d.createdTime);
					}
				} ] ],
				limit : limit,
				page : false
			});
			layer.closeAll('loading');
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