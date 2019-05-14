		var $ = layui.jquery, upload = layui.upload, form = layui.form;
		$(function() {
			// 加载所有省的选项
			doClear();
			// 初始化下拉框
			doInitSelect();
			doFindSelect(null, "select[name='sctypeid']");
		});