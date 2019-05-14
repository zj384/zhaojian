var $ = layui.$
    ,form = layui.form
    ,upload = layui.upload
    ,laydate = layui.laydate;
	!function(){
		laydate.render({
			  elem: '#year'
			  ,type: 'year'
			});
		// 加载所有省的选项
		// 初始化下拉框
		doClear();
		doInitSelect();
		doFindSelect(null, "select[name='sctypeid']");
		form.on("select(county)", function(data) {
			var url = "../school/doFindSchoolByCountyId";
			var level = $("input[name='schoollevel']").val();
			var param = {
					"countyid" : data.value,
					"sctypeid" : level
			};
			$.getJSON(url, param, function(result) {
				doShowSelect(result, "select[name='schoolname']")
			});
		});
	}();
	