!function() {
	var count = getCount();
}();
function getCount() {
	var url = "/pic/doFindImgCount";
	$.getJSON(url, null, function(result) {
		//总页数低于页码总数
		laypage.render({
			elem : 'demo0',
			limit : 24,
			count :  result.data-1,//数据总数
			jump : function(obj, first) {
				//obj包含了当前分页的所有参数，比如：
				initImage(obj.curr, obj.limit);
				//首次不执行 
				if (!first) {
					//do something
				}
			}
		});
	})
}
function initImage(curr, limit) {
	var url = "/pic/doFindImgPage";
	var param = {
		"curr" : curr,
		"limit" : limit
	}
	$.getJSON(url,param,function(result) {
		if (result.state == 1) {
			var $html = "";
			if (result.data != null) {
				$.each(result.data,function(index, item) {
					$html += "<div class='layui-col-md2 layui-col-sm4 imageItem'>"
							+ "<div class='cmdlist-container'>"
							+ "<div class='imageA'> <img layer-src='"+item.url+"' src='"+thumbnail(item.url)+"' alt='"+item.ddr+"'>"
							+ "</div> <a href='javascript:;'>"
							+ "<div class='cmdlist-text'>"
							+ "<div class='price'>"
							+ "<b>"
							+ item.nickname
							+ "</b> <span class='flow'>"
							+ "<i class='layui-icon layui-icon-delete' name='"+item.id+"'></i>"
							+ "</span>"
							+ "</div>"
							+ "</div>"
							+ "</a>"
							+ "</div>"
							+ "</div>";
				});
				$("#image_list").empty();
				$("#image_list").append($html);
			}
			$(".layui-icon-delete").click(function(e) {
				var id = $(e.target).attr("name");
				layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
				doDeleteImageById(id, e);
				});
			});
		} else {
			layer.msg(result.message);
		}
		layer.photos({
			photos : '.imageA',
			anim : 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			,tab : function(pic, layero) {
			}
		});
	});
}
function thumbnail(url){
	return url.slice(0, 36) + "thumbnail/" + url.slice(36);// 测试33 服务器36
}
function doDeleteImageById(id, e) {
	var url = "/pic/doDeleteImgById";
	var param = {
		"id" : id
	};
	$.post(url, param, function(result) {
		if (result.state == 1) {
			layer.msg(result.message);
			$(e.target).parents(".imageItem").empty();
		} else {
			layer.msg(result.message);
		}
	})
}