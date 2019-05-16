var $ = layui.$, element = layui.element, flow = layui.flow, carousel = layui.carousel,util = layui.util;
var laypage = layui.laypage, layer = layui.layer;
!function() {
	layui.config({
		base : '/res/js/util/'
	}).use([ 'menu' ], function() {
		menu = layui.menu;
		menu.init();
	});
	getName();
	$(".indexBody").load("/sys/doIndexBodyUI");
	doLoadUI(".aboutUI", "/sys/doAboutUI");
	doLoadUI(".xxUI", "/sys/doAlbumUI");
	doLoadUI(".czUI", "/sys/doAlbumUI");
	doLoadUI(".gzUI", "/sys/doAlbumUI");
	doLoadUI(".uploadUI", "/sys/doUploadUI");
    backTop();
	$(document).on("click",".icon-love",function(e){
		changeLove(e.target);
	});
}();
function backTop(){
	util.fixbar({  //返回顶部
        top: true
        ,css: {right: 15, bottom: 35}
        ,bgcolor: '#393D49'
        ,click: function(type){
          if(type === 'top'){
            layer.msg('返回顶部')
          }
        }
      });
}
function getName() {
	var url = "/sys/doFindUser";
	$.getJSON(url, null, function(result) {
		if (result.state) {
			$(".welcome-text span").text(result.data.nickname);
		} else {
			var $html = "<span class='name'><a href='/doLoginUI'>登录</a> | <a href='/doRegUI'>注册<a/></span>"
				$(".welcome-text").empty();
			$(".welcome-text").append($html);
		}
	});
}
function doLoadUI(id, url) {
	$(id).click(function(e) {
		 $(document).unbind('scroll');
		$(".indexBody").load(url);//工作线程
		$(".indexBody").data("school",id);
		//#3f2863
		$(".nav a").prop("style","color:#3f2863");
		$(".nav "+id).prop("style","color:#ff7f21");
		$(".menu").prop("class", "menu");
		$(".header-down-nav").prop("class", "layui-nav header-down-nav");
	});
}