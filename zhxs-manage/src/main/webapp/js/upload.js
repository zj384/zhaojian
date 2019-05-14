var $ = layui.jquery, upload = layui.upload, form = layui.form;
	!function() {
		doFindClass();// 获取当前用户的班级
		doUpload();// 图片上传
		var imgId = $(".indexBody").data("imgId");
		if (imgId > 0) {
			doinitUpdateImage(imgId);
		}
		form.on('submit(addimage)', function(data) {
			console.log("hehe");
			doSaveOrUpdateImage(data);
			return true;
		});
	}();
	function doinitUpdateImage(imgId){
		var url = "/pic/doFindImgById";
		var params = {
				"id" :imgId
		};
		$.getJSON(url, params, function(result) {
			if (result.state == 1) {
				console.log(result);
				$("textarea[name='ddr']").text(result.data.ddr);
				$("#demo1").prop("src", result.data.url);
				$("input[name='id']").val(result.data.id);
			} else {
				layer.msg(result.message);
			}
		});
	}
	function doSaveOrUpdateImage(data) {
		var url
		if (data.field.id) {
			url = "/pic/doUpdateImage";
		} else {
			url = "/pic/doSaveImage";
		}
		$.post(url, data.field, function(result) {
			if (result.state == 1) {
				layer.msg(result.message);
				location.href = '/doIndexUI';
			} else {
				layer.msg(result.message);
			}
		});
	}
	function doFindClass() {
		var url = "/class/doFindclass";
		$.getJSON(url, null, function(result) {
			if (result.state == 1) {
				for (var i = 0; i < result.data.length; i++) {
					initRadio("#rax", result, 101, i);
					initRadio("#rac", result, 102, i);
					initRadio("#rag", result, 103, i);
				}
				form.render('radio');
			} else {
				layer.msg(result.message);
			}
		});
	}
	function initRadio(id, result, level, i) {
		if (result.data[i].level == level) {
			$(id).prop(
					"title",
					result.data[i].year + "界" + result.data[i].schoolname
							+ result.data[i].clazz + "班");
			$(id).prop("value", result.data[i].id);
			$(id).prop("disabled", false);
		}
	}
	function doUpload() {
		upload.render({
			elem : '#uploadFile',
			url : '/pic/doUploadPic',
			before : function(obj) {
				obj.preview(function(index, file, result) {
					$('#imgwh').attr('style',
							'width: 500px; height: 400px;');
					$('#demo1').attr('src', result); //图片链接
				});
				layer.load(); //上传loading
			},
			done : function(res) {
				if (res.code > 0) {
					layer.closeAll('loading'); //关闭loading
					return layer.msg('上传失败');
				}
				$("input[name='file']").prop("type", "hidden");
				$("input[name='file']").prop("value", res.src);
				$("input[name='file']").prop("name", "url");
				layer.closeAll('loading'); //关闭loading
				//上传成功
			},
			error : function() {
				layer.closeAll('loading'); //关闭loading
				//演示失败状态，并实现重传
				var demoText = $('#demoText');
				demoText
						.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
				demoText.find('.demo-reload').on('click', function() {
					uploadInst.upload();
				});
			}
		});
	}
	