var updatedDate;
$(function () {

	// 获取全部题材数据
    getAllSubject();

    // 给人群范围添加事件
	peopleClick()

    // 条件设置的收起展示
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"));
    // 收视地区
    clickShowOrHide($(".project-list-five .contracting-area .select"), $(".project-list-five .contracting-area .area"));
    // 播出频道
    clickShowOrHide($(".project-list-five .broadcast-channel .select"), $(".project-list-five .broadcast-channel .channel"));
    // 播出日期
    clickShowOrHide($(".project-list-five .broadcast-date .select"), $(".project-list-five .broadcast-date .date"));
    // 播出时间
    clickShowOrHide($(".project-list-five .broadcast-time .select"), $(".project-list-five .broadcast-time .time"));
    // 题材选择
    clickShowOrHide($(".project-list-five .subject-select .select"), $(".project-list-five .subject-select .subject"));
    // 人群分布
    clickShowOrHide($(".project-list-five .people-select .select"), $(".project-list-five .people-select .people"));

    //获取有收视数据的最新一天
    queryUpdatedDate();
    
    //加载收视地区
    loadArea();

    //加载频道
    loadChannel();

    //初始化时间插件
    createTime();
    
    // 触发时间插件的时候把左侧的菜单栏全隐藏掉
    $('#start-date-one, #end-date-two').click(function () {
    	hideAllDropDown();
    });
    
    //如果模块类型是投放环境，则把频道做成单选，移除全选按钮
    var modelType = $("#modelType").val();
    if (modelType == 1) {
    	$(".select-all-li").parent("ul").remove();
    	$(".subject-select").remove();
    }
    
    initQueryCondition();
    
    //点击文档时关闭所有下拉
    $(document).click(function (e) {
    	if($(e.target).parents(".five-siblings-con").length==0){
    		hideAllDropDown();
    	}
    });
    
});

// 获取全部题材数据
function getAllSubject() {
    // subjecJson subjectTypeJson 在subject.js中
	var html = '';
	for (var i = 0; i < subjectTypeJson.length; i++) {
		var item = subjectTypeJson[i]
        html += '<ul class="slite clearfix '+ item[0] +'">'
        html += '<h4>'+ item[1] +'</h4>'
		for (var j = 2; j < item.length; j++) {
			html += '<li flagId='+[item[j]]+'><a href="javascript:;">'+ subjecJson[item[j]] +'</a><i class="icon iconfont">&#xe600;</i></li>';
		}
		html += '</ul>'
	}
	$('.project-list-five .subject-select > .select-subject > .subject .subject-body').append(html)

	// 给全部题材注册事件
	subjectClick()
}

// 给全部题材注册事件
function subjectClick() {
	var subjectBody = $('.project-list-five .subject-select > .select-subject > .subject .subject-body');
	var allSubLi = subjectBody.find(".all-sub li"), sliteLi = subjectBody.find(".slite li"), subjectIdListArr = [];
	allSubLi.click(function () {
		$(this).addClass("active")
        sliteLi.removeClass("active")
    })
    sliteLi.click(function () {
        allSubLi.removeClass("active")
		if ($(this).hasClass("active")) {
            $(this).removeClass("active")
		}else {
            $(this).addClass("active")
		}
        subjectIdListArr = []
        sliteLi.each(function (index, value) {
            if ($(value).hasClass('active')) {
                subjectIdListArr.push($(value).find("a").html())
            }
        })
		$("#subjectNames").html(subjectIdListArr.join(','))
    })
}

// 给人群范围添加事件
function peopleClick() {
	var peopleBodyLi = $('.project-list-five .people-select > .select-people > .people .people-body ul li');
    var peopleBodySpan = $('.project-list-five .people-select > .select-people > .select > span');
    peopleBodyLi.on('click', function () {
    	peopleBodyLi.removeClass('active')
		$(this).addClass('active')
		peopleBodySpan.html($(this).find("a").html())
        $(document).click()
    })
}

//初始化查询条件
function initQueryCondition() {
	//默认选中第一个时间选择器
    $(".time-li").eq(0).click();
    
    //默认日期范围为”最近一年“
    $(".date-li").eq(5).click();
    
    //默认选中”34城市组“
	$("#province").find("li").eq(2).click();
    
    var modelType = $("#modelType").val();	//如果模块类型是投放环境，则把频道做成单选，移除全选按钮
    if (modelType == 1) {
		//默认选中第一个央视
		$("#cctvChannels").find("li").eq(0).click();
	} else {
		$("#satelliteTvDiv").find("ul").eq(0).find("li").click();
		confirmChannel();
	}
    
    queryData();
}

//关闭所有的下拉选项
function hideAllDropDown() {
	$(".project-list-five .five-siblings .select .down").removeClass("hide");
    $(".project-list-five .five-siblings .select .top").addClass("hide");
    $(".project-list-five .five-siblings .five-siblings-con").addClass("hide");
}

//获取有收视数据的最新一天
function queryUpdatedDate() {
	var url = "/channelDataAnalyse/queryUpdatedDate";
	var successFn = function(response) {
		updatedDate = response.data;
	};
	doPost(url, {}, successFn, null, false);
}

//切换频道tab
function switchChannelTab(own, type) {
	$(own).siblings("li").removeClass("active");
	$(own).addClass("active");
	
	$(".channel-con").addClass("hidden");
	
	if (type == 1) {	//央视
		$("#cctvDiv").removeClass("hidden");
	}
	if (type == 2) {	//卫视
		$("#satelliteTvDiv").removeClass("hidden");
	}
	if (type == 3) {	//省级地面
		$("#provinceChannelDiv").removeClass("hidden");
	}
	if (type == 4) {	//市级地面
		$("#cityChannelDiv").removeClass("hidden");
	}
}

//加载频道信息
function loadChannel() {
	$.ajax({
		url: "/static/data/channel.json",
		type: "get",
		dataType: "json",
		async: false,
		success: function(response) {
			
			var cctvLiArray = [];	//央视
			var satelliteLiArray = [];	//卫视
			var provinceLiArray = [];	//省级地面
			var cityLiArray = [];	//市级地面
			$.each(response, function(index, item) {
				var label = item.label;	//频道名
				var lid = item.lid;	//频道级别，1-央视，2-卫视，3-省级地面，4-市级地面
				var chid = item.chid;	//频道ID
				var pid = item.pid;	//频道所属省ID
				var cid = item.cid;	//频道所属城市ID
				
				var channelLiArray = [];
				if (lid == 3 || lid == 4) {
					channelLiArray.push("<li pid="+ pid +" cid="+ cid +" chid="+ chid +" label="+ label +" class='hidden' title='"+ label +"' onclick='selectChannel(this)'>");
				} else {
					channelLiArray.push("<li chid="+ chid +" title='"+ label +"' label="+ label +" onclick='selectChannel(this)'>");
				}
				
				channelLiArray.push("	<a class='over-hide' href='javascript:;'>"+ label +"</a>");
				channelLiArray.push("	<i class='icon iconfont'>&#xe600;</i>");
				channelLiArray.push("</li>");
				if (lid == 1) {
					cctvLiArray.push(channelLiArray.join(""));
				}
				if (lid == 2) {
					satelliteLiArray.push(channelLiArray.join(""));
				}
				if (lid == 3) {
					provinceLiArray.push(channelLiArray.join(""));
				}
				if (lid == 4) {
					cityLiArray.push(channelLiArray.join(""));
				}
			});
			
			$("#cctvChannels").html(cctvLiArray.join(""));
			$("#satelliteTvChannels").html(satelliteLiArray.join(""));
			$("#provinceChannels").html(provinceLiArray.join(""));
			$("#cityChannels").html(cityLiArray.join(""));
			
		}
	});
}

//选择频道
function selectChannel(own) {
	var modelType = $("#modelType").val();	//如果模块类型是投放环境，则把频道做成单选，移除全选按钮
	
	if (modelType == 1) {	//单选
		$(".channel-con").find("li").removeClass("active");
		$(own).toggleClass("active");
		
		confirmChannel();
	} else {	//多选
		if ($(own).hasClass("active")) {
			//取消全选状态
			$(own).parent("ul").prev("ul").find("li").removeClass("active");
		}
		$(own).toggleClass("active");
		
		//添加全选状态
		if ($(own).parent("ul").find("li").length == $(own).parent("ul").find("li.active").length) {
			$(own).parent("ul").prev("ul").find("li").addClass("active");
		}
	}
}

//频道全选
function selectAllChannel(own) {
	if ($(own).hasClass("active")) {
		$(own).parent("ul").next("ul").find("li").removeClass("active");
	} else {
		//只把显示出的频道选中
		$(own).parent("ul").next("ul").find("li:not(.hidden)").addClass("active");
	}
	$(own).toggleClass("active");
}

//频道确认
function confirmChannel() {
	var channelLevelList = [];
	var channelIdList = [];
	var channelNameList = [];
	
	//央视
	if ($("#cctvAll").hasClass("active")) {
		channelLevelList.push(1);
		channelNameList.push("全部央视");
	} else {
		$.each($("#cctvChannels").find("li.active"), function(index, item) {
			channelIdList.push($(this).attr("chid"));
			channelNameList.push($(this).attr("label"));
		});
	}
	
	//卫视
	if ($("#satelliteTvAll").hasClass("active")) {
		channelLevelList.push(2);
		channelNameList.push("全部卫视");
	} else {
		$.each($("#satelliteTvChannels").find("li.active"), function(index, item) {
			channelIdList.push($(this).attr("chid"));
			channelNameList.push($(this).attr("label"));
		});
	}
	
	//省级地面
	$.each($("#provinceChannels").find("li.active"), function(index, item) {
		channelIdList.push($(this).attr("chid"));
		channelNameList.push($(this).attr("label"));
	});
	
	//市级地面
	$.each($("#cityChannels").find("li.active"), function(index, item) {
		channelIdList.push($(this).attr("chid"));
		channelNameList.push($(this).attr("label"));
	});
	
	$("#channelLevelList").val(JSON.stringify(channelLevelList));
	$("#channelIdList").val(JSON.stringify(channelIdList));
	$("#channelNames").text(channelNameList.join(" | "));
	$("#channelNames").attr("title", channelNameList.join(" | "));
	
	hideAllDropDown();
}

//加载收视地区
function loadArea() {
	$.ajax({
		url: "/static/data/area_tree.json",
		type: "get",
		dataType: "json",
		async: false,
		success: function(response) {
			
			var provinceLiArray = [];
			var cityUlArray = [];
			$.each(response, function(index, item) {
				var text = item.text;
				var children = item.children;
				
				
				if (children) {
					var firstCityId = 0;
					//城市信息
					cityUlArray.push("<ul provinceId="+ item.id +" class='hidden'>");
					$.each(children, function(index, item) {
						if (index == 0) {
							firstCityId = item.id;
						}
						
						cityUlArray.push("<li onclick='selectCity(this, "+ item.id +", \""+ item.text +"\")'>");
						cityUlArray.push("	<a href='javascript:void(0);'>"+ item.text +"</a>");
						cityUlArray.push("</li>");
					});
					cityUlArray.push("</ul>");
					
					
					//省份信息
					provinceLiArray.push("<li onclick='selectCityGroup(this, "+ item.id +", \""+ item.text +"\", "+ firstCityId +")'>");
					provinceLiArray.push("	<a href='javascript:void(0);'>"+ text +"</a>");
					provinceLiArray.push("	<i class='icon iconfont' onclick='showCity(this, event, "+ item.id +")'>&#xe623;</i>");
					provinceLiArray.push("</li>");
				} else {
					//城市组信息
					provinceLiArray.push("<li onclick='selectCityGroup(this, "+ item.id +", \""+ item.text +"\")'><a href='javascript:void(0);'>"+ text +"</a></li>");
				}
			});
			
			$("#province").html(provinceLiArray.join(""));
			$("#city").html(cityUlArray.join(""));
			
		}
	});
}

//显示省份下的城市
function showCity(own, event, provinceId) {
	$(own).parent("li").siblings("li").find("i.icon").removeClass("active");
	$(own).addClass("active");
	
	$("#city").find("ul").addClass("hidden");
	$("#city").find("ul[provinceId="+ provinceId +"]").removeClass("hidden");
	
	event.stopPropagation();
}

//选择城市
function selectCity(own, cityId, cityName) {
	$(own).parents().find("#city").find("ul").find("li").removeClass("active");
	$(own).parents().find("#province").find("li").removeClass("active");
	$(own).addClass("active");
	
	//过滤出对应的频道
	$("#provinceChannels").find("li").addClass("hidden");
	$("#cityChannels").find("li").addClass("hidden");
	
	$("#provinceChannels").find("li[cid="+ cityId +"]").removeClass("hidden");
	$("#cityChannels").find("li[cid="+ cityId +"]").removeClass("hidden");
	
	$("#selectedAreaName").text(cityName);
	$("#selectedAreaId").val(cityId);
	hideAllDropDown();
}

//选择城市组
function selectCityGroup(own, groupId, groupName, firstCityId) {
	$(own).siblings("li").removeClass("active");
	$(own).parent("ul").find("li").find("i.icon").removeClass("active");
	$(own).addClass("active");
	$("#city").find("ul").addClass("hidden");
	
	//过滤出对应的频道
	$("#provinceChannels").find("li").addClass("hidden");
	$("#cityChannels").find("li").addClass("hidden");
	
	$("#provinceChannels").find("li[pid="+ groupId +"]").removeClass("hidden");
	$("#cityChannels").find("li[cid="+ firstCityId +"]").removeClass("hidden");
	
	//把所有频道取消选中
	$("#provinceChannelDiv").find("li").removeClass("active");
	$("#cityChannelDiv").find("li").removeClass("active");
	
	$("#selectedAreaName").text(groupName);
	$("#selectedAreaId").val(groupId);
	hideAllDropDown();
}

//选择播出日期
function selectTime(own, startTime, endTime) {
	$(own).siblings("li").removeClass("active");
	$(own).addClass("active");
	
	$(own).parents().find(".select-time .select span").text($(own).text());
	
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);
	hideAllDropDown();
}

//快捷设置日期
function quickSetDate(own, type) {
	$(own).siblings("li").removeClass("active");
	$(own).addClass("active");
	
	$(own).parents().find(".select-date .select span").text($(own).text());
	
	if (type != -1) {
        var startDate = getStartDate(type, updatedDate);
        var endDate = updatedDate;
        $("#startDate").datepicker('setDate', startDate);
        $("#endDate").datepicker('setDate', endDate);
    }
	
	hideAllDropDown();
}

//初始化时间插件
function createTime() {
	$("#startDate").datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
	    orientation: "bottom auto",
	    autoclose: true,
	    todayHighlight: true
	}).on("changeDate", function (ev) {
		if ($("#endDate").datepicker("getDate") && ev.date.valueOf() > $("#endDate").datepicker("getDate").valueOf()) {
		    var newDate = new Date(ev.date);
		    newDate.setDate(newDate.getDate() + 1);
		    $("#endDate").datepicker('setDate', newDate);
		    $("#endDate").datepicker("show");
		}
		
		checkDateType();
	});
	
	$("#endDate").datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
	    orientation: "bottom auto",
	    autoclose: true,
	    todayHighlight: true
	}).on("changeDate", function(ev) {
		if ($("#startDate").datepicker("getDate") && ev.date.valueOf() < $("#startDate").datepicker("getDate").valueOf()) {
		    var newDate = new Date(ev.date);
		    newDate.setDate(newDate.getDate() - 1);
		    $("#startDate").datepicker('setDate', newDate);
		    $("#startDate").datepicker("show");
		}
		checkDateType();
	});
}

//在用户选择播出日期之后判断日期范围的类型（最近一天、最近一周、最近一年.....）
function checkDateType() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	if (!startDate || !endDate) {
		return;
	}
	
	if (endDate == updatedDate && startDate == getStartDate(0, updatedDate)) {	//表示最近一天
		return;
	}
	if (endDate == updatedDate && startDate == getStartDate(1, updatedDate)) {	//表示最近一周
		return;
	}
	if (endDate == updatedDate && startDate == getStartDate(2, updatedDate)) {	//表示最近一月
		return;
	}
	if (endDate == updatedDate && startDate == getStartDate(3, updatedDate)) {	//表示最近一季
		return;
	}
	if (endDate == updatedDate && startDate == getStartDate(4, updatedDate)) {	//表示最近半年
		return;
	}
	if (endDate == updatedDate && startDate == getStartDate(5, updatedDate)) {	//表示最近一年
		return;
	}
	$(".date-li").eq(6).click();	//自定义
}

//查询分析数据
function queryData() {
	var areaId = $("#selectedAreaId").val();
	var channelLevelList = $("#channelLevelList").val();
	var channelIdList = $("#channelIdList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var channelNames = $("#channelNames").text();
    var subjectBody = $('.project-list-five .subject-select > .select-subject > .subject .subject-body');
    var allSubLi = subjectBody.find(".all-sub li");
	var sliteLi = subjectBody.find(".slite li");
    var peopleBodyLi = $('.project-list-five .people-select > .select-people > .people .people-body ul li');
    var subjectIdList, flagType, flagId;
	
	if (!channelLevelList && !channelIdList) {
		alert("请选择频道");
		return;
	}
	
	var channelLevelJson = [];
	if (channelLevelList) {
		channelLevelJson = JSON.parse(channelLevelList);
	}
	var channelIdJson = [];
	if (channelIdList) {
		channelIdJson = JSON.parse(channelIdList);
	}

	// 题材
	if (allSubLi.hasClass('active')) {
        subjectIdList = []
	} else {
        subjectIdList = []
        sliteLi.each(function (index, value) {
			if ($(value).hasClass('active')) {
                subjectIdList.push(parseInt($(value).attr('flagId')))
			}
        })
	}
	// 人群
    peopleBodyLi.each(function (index, value) {
		if ($(value).hasClass("active")) {
			flagId = $(value).attr('flagId');
			flagType = $(value).parents("ul").attr('flagType')
			return false;
		}
    })
	// console.log(typeof flagId)
	// console.log(flagType)
	if (flagType == 'ageType') {
        var params = {
            areaId: areaId,
            channelLevelList: channelLevelJson,
            channelIdList: channelIdJson,
            startDate: startDate,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            channelNames: channelNames,
            subjectIdList: subjectIdList,
            ageType: parseInt(flagId)
        };
	} else if (flagType == 'sexType') {
        var params = {
            areaId: areaId,
            channelLevelList: channelLevelJson,
            channelIdList: channelIdJson,
            startDate: startDate,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            channelNames: channelNames,
            subjectIdList: subjectIdList,
            sexType: parseInt(flagId)
        };
	} else if (flagType == 'eduType') {
        var params = {
            areaId: areaId,
            channelLevelList: channelLevelJson,
            channelIdList: channelIdJson,
            startDate: startDate,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            channelNames: channelNames,
            subjectIdList: subjectIdList,
            eduType: parseInt(flagId)
        };
    } else if (flagType == 'earnType') {
        var params = {
            areaId: areaId,
            channelLevelList: channelLevelJson,
            channelIdList: channelIdJson,
            startDate: startDate,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            channelNames: channelNames,
            subjectIdList: subjectIdList,
            earnType: parseInt(flagId)
        };
    } else {}
    console.log(params)
    // return;
	parentQueryData(params);
}

//获取以endDate为截止日期的开始时间
function getStartDate(type, endDate) {
    var fmt = "yyyy-MM-dd";
    var startDate = new Date(endDate); // 获取截止日期对象

    type = type.toString();
    switch (type) {
        case '0'://最近一天
            break;
        case '1'://最近一周
        	var weekDay = startDate.getDay();
        	startDate.addDays(- weekDay + 1);
            break;
        case '2'://最近一个月
        	startDate.setDate(1);
            break;
        case '3'://最近一季
            var currMonth = startDate.getMonth();
            if (currMonth >= 0 && currMonth <= 2) {
            	startDate.setMonth(0, 1);
            }
			if (currMonth >= 3 && currMonth <= 5) {
				startDate.setMonth(3, 1);
			}
			if (currMonth >= 6 && currMonth <= 8) {
				startDate.setMonth(6, 1);
			}
			if (currMonth >= 9 && currMonth <= 11) {
				startDate.setMonth(9, 1);
			}
            break;
        case '4'://最近半年
        	var currMonth = startDate.getMonth();
            if (currMonth >= 0 && currMonth <= 5) {
            	startDate.setMonth(0, 1);
			}
            if (currMonth >= 6 && currMonth <= 11) {
            	startDate.setMonth(6, 1);
			}
            break;
        case '5'://最近一年
        	startDate.setMonth(0, 1);
            break;
        default:
            break;
    }
    
    return startDate.format(fmt);
}