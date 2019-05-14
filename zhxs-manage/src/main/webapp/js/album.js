!function() {
	// 加载图片信息
	var id = $(".indexBody").data("school");
	initImage(id);
	flow.lazyimg();//图片流式加载
}();
function initImage(id) {
	var level = getLevel(id);
	var url = "/pic/doFindImage";
	var param = {
			"level" : level,
			"isUser" : 0,
			"page" : 1,
			"limit" : 7
	}
	$.getJSON(url,param,function(result) {
		if (result.state == 1) {
			var $html = "";
			var $new = "";
			if (result.data != null) {
				$.each(result.data,function(index, item) {
					if (index == 0) {
						$new += "<div class='new-img bigImage'><img src='"+item.url+"' alt=''></div>"
						+ "<div class='title'>"
						+ "<p class='data'>"
						+ "最新上传<span>"
						+ createTime(item.createtime)
						+ "</span>"
						+ "</p>"
						+ "<p class='address'>"
						+ "<i class='layui-icon layui-icon-username'></i><span>"
						+ item.nickname
						+ "</span>"
						+ "</p>"
						+ "<p class='text'>"
						+ item.ddr
						+ "</p>"
						+ "</div>";
					} else {
						$html += getHtml(item);
					}
				});
				$("#new-image").empty();
				$("#new-image").append($new);
				$("#imageList").empty();
				$("#imageList").append($html);
			}
		} else {
			layer.msg(result.message);
		}
		getBigPhoto();
		getLoadImage(level,url);
	});
}
function getLoadImage(level,url){
	flow.load({
		elem: '#imageList' //指定列表容器
			,end: ''
			,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
				var lis = [];
				var $html = "";
				var param = {
						"level" : level,
						"isUser" : 0,
						"page" : page+1,
						"limit" : 6
				}
				//以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
				$.getJSON(url,param,function(res){
					//假设你的列表返回在data集合中
					if (res.state == 1) {
						if (res.data.length != 0){
							layui.each(res.data, function(index, item){
								$html = getHtml(item);
								lis.push($html);
							}); 
						} else {
							layer.msg("没有更多的图片了!");
							next(null, false);
							return false;
						}
					}
					next(lis.join(''), true);
					getBigPhoto();
				});
			}
	});
}
function getHtml(item){
	var $html = "";
	$html = "<div class='layui-col-xs12 layui-col-sm4 layui-col-md4 imgList'>"
		+ "<div class='item'>"
		+ "<div class='image bigImage'><img layer-src='"+item.url+"' lay-src='"+thumbnail(item.url)+"' alt='"+item.ddr+"'></div>"
		+ "<div class='cont-text'>"
		+ "<div class='data'>"
		+ createTime(item.createtime)
		+ "</div>"
		+ "<p class='address'>"
		+ "<i class='layui-icon layui-icon-username'></i><span>"
		+ item.nickname
		+ "</span>"
		+ "</p>"
		+ "<p class='briefly'>"
		+ item.ddr
		+ "</p>"
		+ "</div>"
		+ "</div>"
		+ "</div>";
	return $html;
}
function thumbnail(url){
	return url.slice(0, 36) + "thumbnail/" + url.slice(36);
}
function getBigPhoto(){
	layer.photos({
		photos : '.bigImage',
		anim : 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		,tab : function(pic, layero) {
		}});
}
function getLevel(level){
	if (level == ".xxUI") {
		return 101;
	}
	if (level == ".czUI") {
		return 102;
	}
	if (level == ".gzUI") {
		return 103;
	}
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