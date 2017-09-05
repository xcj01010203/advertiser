$(function () {
	
	//加载题材信息
	loadSubject();
});

//加载题材信息
function loadSubject() {
	var url = "/subject/querySubjectList";
	var successCall = function(response){
		if(response.status == 1){
            modelWindow(response.msssage)
			return;
		}
		var subjectList = response.data.subjectList;
		
		var tvSubjectArray = [];
		var movieSubjectArray = [];
		$.each(subjectList, function(index, item) {
			var ownerType = item.ownerType;
			var singleSubject = "";
			if (index == 0) {
				singleSubject = "<label><input name='subject.id' value='"+ item.id +"' type='radio' checked/><span>"+ item.name +"</span></label>";
			} else {
				singleSubject = "<label><input name='subject.id' value='"+ item.id +"' type='radio'/><span>"+ item.name +"</span></label>";
			}
			
			if (ownerType == 1) {
				tvSubjectArray.push(singleSubject);
			} else {
				movieSubjectArray.push(singleSubject);
			}
		});
		
		$("#tvSubjectDiv").empty();
		$("#tvSubjectDiv").append(tvSubjectArray.join(''));
		
		$("#movieSubjectDiv").empty();
		$("#movieSubjectDiv").append(movieSubjectArray.join(''));
	};
	
	doPost(url, {}, successCall);
}

//保存项目
function saveProject() {
	var url = "/project/saveProject";
	var successCall = function(response) {
		if (response.status == 1) {
			$('#myModal').modal({backdrop: 'static', keyboard: false, toggle: 'toggle'});
			$("#modal-body").text(response.message);
			return;
		}
        modelWindow("操作成功")

		var url = "/project/enterProject";
		var enterProjectCall = function(response) {
			if (response.status == 1) {
                modelWindow(response.msssage)
				return;
			}
			window.location.href = "/base/forward/playManage/basicInfo";
		};
		doPost(url, {id: response.data.id}, enterProjectCall);
	};
	
	var data = $("#projectForm").serialize();
	doPost(url, data, successCall);
}

//选择项目类型
function selectSubjectType(type) {
	if (type == 1 || type == 3) {
		$("#tvSubjectDiv").show();
		$("#movieSubjectDiv").hide();
		
		$("#tvSubjectDiv").find("label").eq(0).click();
	} else {
		$("#tvSubjectDiv").hide();
		$("#movieSubjectDiv").show();
		
		$("#movieSubjectDiv").find("label").eq(0).click();
	}
}