
$(function () {
	//加载题材信息
	loadSubject();
	
	//查询项目信息
	loadProjectDetail();
});

//加载题材信息
function loadSubject() {
	var url = "/subject/querySubjectList";
	var successCall = function(response){
		if(response.status == 1){
			modelWindow(response.message)
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

//加载项目详细信息
function loadProjectDetail() {
	var url = "/project/queryProjectDetail";
	var successCall = function(response) {
		if (response.status == 1) {
			modelWindow(response.message)
			return;
		}
		
		var project = response.data;
		
		var typeJson = {};
		typeJson["1"] = "电视剧";
		typeJson["2"] = "电影";
		typeJson["3"] = "网剧";
		typeJson["6"] = "网大";
		
		//为显示面板的项目信息赋值
		$("#name").text(project.name);
		$("#type").text(typeJson[project.type]);
		$("#subjectName").text(filterNull(project.subject ? project.subject.name : ""));
		$("#company").text(filterNull(project.company));
		$("#majorActors").text(filterNull(project.majorActors));
		$("#director").text(filterNull(project.director));
		$("#playWriter").text(filterNull(project.playWriter));
		$("#producer").text(filterNull(project.producer));
		$("#introduction").text(filterNull(project.introduction));
		
		//为编辑面板的项目信息赋值
		$("input[name=id]").val(project.id);
		$("input[name=name]").val(project.name);
		$("input[name=type][value="+ project.type +"]").attr("checked", true);
		if (project.subject) {
			$("input[name='subject.id'][value="+ project.subject.id +"]").attr("checked", true);
		}
		$("input[name=company]").val(project.company);
		$("input[name=majorActors]").val(project.majorActors);
		$("input[name=director]").val(project.director);
		$("input[name=playWriter]").val(project.playWriter);
		$("input[name=producer]").val(project.producer);
		$("textarea[name=introduction]").val(project.introduction);
		
	};
	doPost(url, {}, successCall);
}


//过滤Null的数据
function filterNull(value) {
	return value ? value : "";
}

//切换到修改信息页面
function switchToMoidfyProject(own) {
	$(own).addClass("hidden");
	$(own).next("button").removeClass("hidden");
	$(own).next("button").next("button").removeClass("hidden");
	
	//切换到信息修改面板
	$("#projectContentDiv").addClass("hidden");
	$("#editProjectDiv").removeClass("hidden");
	
//	var url = "/playRound/queryRoundList";
//	var successCall = function(response) {
//		if (response.status == 1) {
//			alert(response.message);
//			return;
//		}
//		console.log(response.data.roundList);
//	};
//	
//	var data = {};
//	data.startSeriesNo = 5;
//	data.endSeriesNo = 5;
//	data.endRoundNo = 5;
//	data.atmosphereList = ["傍晚"];
//	data.siteList = ["外"];
//	data.firstLocationList = ["电话局门口"];
//	data.guestRoleIdList = ["X15E2D0A7514275A3FD30867"];
//	data.massRoleIdList = ["X15E2D0A7514275C3FECA539"];
//	data.majorRoleIdList = ["X15E27CC7112350F3FE72605", "X15E27CC711235113FE75744"];
//	data.majorRoleSearchMode = 4;
//	doPost(url, JSON.stringify(data), successCall, null, null, null, null, null, 'application/json;charset=utf-8');
}

//切换到显示信息页面
function switchToShowProject(own) {
	$(own).addClass("hidden");
	$(own).next("button").addClass("hidden");
	$(own).prev("button").removeClass("hidden");
	
	//切换到信息修改面板
	$("#projectContentDiv").removeClass("hidden");
	$("#editProjectDiv").addClass("hidden");
}


//保存项目
function saveProject(own) {
	var url = "/project/saveProject";
	var successCall = function(response) {
		if (response.status == 1) {
			modelWindow(response.message)
			return;
		}
        modelWindow("操作成功")
		
		window.location.reload();
	};
	
	var data = $("#editProjectForm").serialize();
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
