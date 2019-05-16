!function() {
	// 加载图片信息
	var id = $(".indexBody").data("school");
	var level = getLevel(id);
	initImage(level);
	flow.lazyimg();//图片流式加载
	initBestLoveImage(level);
	bestLoveImage();
}();
function bestLoveImage(){
	//建造实例
	carousel.render({
		elem: '#new-image'
			,width: '100%' //设置容器宽度
			,height: '350px'
			,arrow: 'hover' //始终显示箭头
			,anim: 'fade' //切换动画方式
	});
}
function initBestLoveImage(level){
	var url = "/pic/doFindImage";
	var param = {
			"level" : level,
			"isUser" : 0,
			"page" : 1,
			"limit" : 4,
			"islove" : 1
	};
	$.getJSON(url, param, function(result){
		if(result.state){
			$.each(result.data, function(index, item){
				$("#image"+index+" img").attr("src",item.url);
			});
		}
	});
}
function changeLove(e){
	var loveNum = $(e).prev().text();
	var imageId = $(e).next().val();
	if ($(e).attr("value")==0){
		saveLove(imageId, 1);
		$(e).html("&#xe67a;");
		$(e).attr("value", "1");
		loveNum++;
		$(e).prev().html(loveNum);
	} else {
		saveLove(imageId, 0);
		$(e).html("&#xe67b;");
		$(e).attr("value", "0");
		loveNum--;
		$(e).prev().html(loveNum);
	}
}
function saveLove(imageId,isAdd){
	var url = "/pic/changeLove";
	var param = {
			"imageId" : imageId,
			"isAdd" : isAdd
	}
	$.post(url, param,function(result){
		 if (!result.state) {
			 layer.msg("服务器出错!");
		 }
	});
}
function initImage(level) {
	var url = "/pic/doFindImage";
	flow.load({
		elem: '#imageList' //指定列表容器
			,end: ' '
			,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
				var lis = [];
				var $html = "";
				var param = {
						"level" : level,
						"isUser" : 0,
						"page" : page,
						"limit" : 6,
						"islove" : 0
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
	var islove = (item.islove == null) ? ("'0'>&#xe67b;") : ("'1'>&#xe67a;");
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
		+ "<div class='love'><span>" + item.love 
		+ "</span><i class='layui-icon icon-love' value="+ islove +"</i><input type='hidden' value='" 
		+ item.id + "'></input></div>"
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