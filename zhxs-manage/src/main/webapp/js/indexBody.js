!function() {
	flow.lazyimg();
	initMyImages();
	carouselImg();
}();
function carouselImg(){
	//建造实例
	carousel.render({
		elem: '.banner'
			,width: '100%' //设置容器宽度
			,height: 'none'
			,arrow: 'always' //始终显示箭头
			//,anim: 'updown' //切换动画方式
	});
}
function initMyImages(){
	var url = "/pic/doFindImage";
	$("#UserImage").empty();
	flow.load({
		elem: '#UserImage', //指定列表容器
		done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
			var lis = [];
			var $html = "";
			var param = {
					"isUser" : 1,
					"page" : page,
					"limit" : 6,
					"islove" : 0
			}
			//以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
			$.getJSON(url,param,function(res){
				//假设你的列表返回在data集合中
				if (res.state == 1) {
					layui.each(res.data, function(index, item){
						$html = "<div class='item'"
							+ "<div class='layui-fluid'>"
							+ "<div class='layui-row'>"
							+ "<div class='layui-col-xs12 layui-col-sm8 layui-col-md7'>"
							+ "<div class='img'>"
							+ "<img  layer-src='"+item.url+"' lay-src='" + item.url + "' alt=''>"
							+ "</div>"
							+ "</div>"
							+ "<div class='layui-col-xs12 layui-col-sm4 layui-col-md5'>"
							+ "	<div class='item-cont'>"
							+ "<h3>"
							+ item.clazzname
							+ ((createTime(item.createtime) == getNowTime())?"<button class='layui-btn layui-btn-danger new-icon'>new</button>":"")
							+ "</h3>"
							+ "<h5>" + createTime(item.createtime) + "</h5>"
							+ "<p>" + item.ddr + "</p>"
							+ "<button class='layui-btn go-icon updateImg' name='"+item.id+"'></button>"
							+ "</div>"
							+ "</div>"
							+ "</div>"
							+ "</div>"
							+ "</div>";
						lis.push($html);
					}); 
				}
				if (res.data.length == 0){
					layer.msg("没有更多的图片了!");
					next(null, false);
					return false;
				}
				//执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
				//pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
				next(lis.join(''), true);
				$(".updateImg").click(function(e){
					var id = $(e.target).attr("name"); 
					$(".banner").remove();
					$(".indexBody").load("/sys/doUploadUI");//工作线程
					$(".indexBody").data("imgId", id);
				});
				getBigImg();
			});
		}
	});
}
function getBigImg(){
	layer.photos({
		photos : '.img',
		anim : 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		,tab : function(pic, layero) {
		}});
}
function getNowTime(){
	var myDate = new Date();
	//获取当前年
	var year=myDate.getFullYear();
	//获取当前月
	var month=myDate.getMonth()+1;
	month = month < 10 ? '0' + month : month;
	//获取当前日
	var date=myDate.getDate();
	date = date < 10 ? ("0" + date) : date;
	return  year + "年" + month + "月" + date + "日";
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