function doInitSelect() {
	// 选择省触发事件
	form.on("select(province)", function(data) {
		doFindSelect(data.value, "select[name='cityid']");
		$(".layui-content").data("provinceid", data.value);
	});
	// 选择市触发事件
	form.on("select(city)", function(data) {
		doFindSelect(data.value, "select[name='countyid']");
		$(".layui-content").data("cityid", data.value);
	});
	// 选择县区触发事件
	form.on("select(county)", function(data) {
		$(".layui-content").data("countyid", data.value);

	});
	form.on("select(schoolType)", function(data) {
		$(".layui-content").data("schoolTypeId", data.value);
	});
}


//重置按钮
function doClear() {
	$("select").empty();
	$("input[name='schoolname']").val("");
	doFindSelect(null, "select[name='provinceid']");
}
//查询省市县区选项
function doFindSelect(parentid, selectname) {
	if (selectname == "select[name='cityid']") {
		$(selectname).empty();
		$("select[name='county']").empty();
	}
	if (selectname == "select[name='countyid']") {
		$(selectname).empty();
	}
	var url = "/school/doFindCity";
	if (selectname == "select[name='sctypeid']") {
		url = "/school/doFindSchoolType";
	}
	var param = {
			"parentid" : parentid
	};

	$.getJSON(url, param, function(data) {
		doShowSelect(data,selectname);
	})

}
function doShowSelect(data,selectname){
	layui.use([ 'form', 'upload' ], function() {
		var $html = "";
		if (data.data != null) {
			$.each(data.data, function(index, item) {
				$html += "<option value='" + item.id + "'>"
				+ (item.name ? item.name : item.schoolname) + "</option>";
			});
			$("#school").data("selectname", selectname);
			$(selectname).empty();
			$(selectname).append($html);
			// append后必须从新渲染
			$(selectname).val("");
			form.render('select');
		}
	})
}