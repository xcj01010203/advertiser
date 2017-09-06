var label; //标签名称
var menuLv;   //菜单等级
var mid;   //菜单父ID
var fidMenuName; //上一级单名称
var menuId;  //标签编号id
var labelScore; //标签分数score
var fid; //标签父ID
var ids = [];

var labelModel = {};
//页面初始化
$(function(){
	getLabelList();
	//validationNumber();//验证评分
})
//加载标签信息列表
function getLabelList(){
	 $.ajax({
	        url: basePath + "/playLabel/queryPlayLabel",
	        type: "POST",
	        dataType: "json",
	        success: function (data) {
	            var html = '';
	            
	            if (data.status == 0) {
	                var data = data.data;
	                if (data) {
	                    for (var i = 0; i < data.length; i++) {
	                        html += '<ul flag="one" menuLv="' + data[i].id + '" fid="' + data[i].fid + '" id="' + data[i].id + '" class="one userManage-menu userManage-td">';
	                      
	                        html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data[i].id + '"/>'
	                            + data[i].id + '</li>';
	                        if (!!data[i].nodes && data[i].nodes.length != 0) {
	                            html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
	                                '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data[i].label + '</span></a></li>';
	                            
	                        } else {
	                            html += '<li class="menu-name"><a href="javascript:;"><span>' + data[i].label + '</span></a></li>';
	                        }
	                        html += '</ul>';
	                        
	                        if (!!data[i].nodes && data[i].nodes.length != 0) {
	                        	
	                            for (var j = 0; j < data[i].nodes.length; j++) {
	                                html += '<ul flag="two" menuLv="' + data[i].nodes[j].menuLv + '" fid="' + data[i].nodes[j].fid + '" id="' + data[i].nodes[j].id + '" class="two userManage-menu userManage-td hide">';
	                                html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data[i].nodes[j].id + '"/>'
	                                    + data[i].nodes[j].id + '</li>';
	                                
	                                if (!!data[i].nodes[j].nodes && data[i].nodes[j].nodes.length != 0) {
	                                    html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
	                                        '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data[i].nodes[j].label + '</span></a></li>';
	                                    
	                                } else {
	                                    html += '<li class="menu-name"><a href="javascript:;"><span>' + data[i].nodes[j].label + '</span></a></li>';
	                                }
	                                html += '<li class="menu-describe">' + data[i].nodes[j].score + '</li>';
	                                html += '</ul>';
	                                
	                            }
	                        }
	                    }
	                }
	            }
	            
	            $(".trend-draw").html(html);

	            // 树形插件的事件
	            treeEvent('one', 'two')
	            treeEvent('two', 'three')
	            treeEvent('three', 'four')

	            // 打开所有，关闭所有树形结构
	            closeOrOpenAll()

	            //新增菜单 获取readio选中值
	            readioSelect()

	            // 父级到子级全选
	            // parentToChildren()
	            parentToChildren("one")
	            parentToChildren("two")
	            parentToChildren("three")
	            parentToChildren("four")

	        },
	        error: function () {

	        }
	    });
}

//是否有二级菜单转换
function numToString(num) {
    if (num == 0) {
        return '否';
    }
    return '是';
}
//是否进行功能控制转换
function numToStringIsPerm(num) {
    if (num == 0) {
        return '不控制';
    }
    return '控制';
}

// 树形插件的事件
function treeEvent(str1, str2) {
    $(".trend-draw ." + str1 + " .menu-name a").click(function () {
        var len;
        if ($(this).parents("." + str1).next("." + str2).hasClass("hide")) {
            len = $(this).parents("." + str1).nextUntil("." + str1).length;
            for (var i = 0; i < len; i++) {
                if ($($(this).parents("." + str1).nextUntil("." + str1)[i]).attr("flag") == str2) {
                    $($(this).parents("." + str1).nextUntil("." + str1)[i]).removeClass("hide")
                }
            }
            $(this).find(".you").addClass("hide")
            $(this).find(".yxia").removeClass("hide")
        } else {
            $(this).parents("." + str1).nextUntil("." + str1).addClass("hide")
            $(this).find(".you").removeClass("hide")
            $(this).find(".yxia").addClass("hide")
        }
    })
}

//显示或隐藏添加窗口
function addButton(type) {
	console.log(type);
    if ($("input[name='project-type']:checked").length > 1) {
        modelWindow("只能选择一个菜单!", 0);
    } else {
        if (type == "icon iconfont") {
	        	var label = {};
			    var id = $("#menuId").val();
			    label.id = id;
			    var url = basePath + "/playLabel/removePlayLabelById";
			    doPostAjax(url, label, function (data) {
		    	$("#addShade").attr("style", "display:none;");//关闭新增弹框
		    	//getLabelList();
                window.location.href = basePath + "/base/forward/user/labelManage";
		    })
            
        }else {
            var flag = $(".trend-draw ul .menu-num input").is(':checked');
            if (flag) {
                modelWindowParam("确定在该标签下创建子标签吗", 0, null);
            } else {
                modelWindowParam("确定要创建一级标签吗", 0, null);
            }
            $("#modal-footer .btn-primary").click(function () {
                $("#addShade").removeClass("hide")
                $("#modal-content>.modal-header>button").trigger("click");
            })
        }
    }
	//弹框里的新增标签新增标签
	addLabel()
}

//弹框里的新增标签
function addLabel() {
	$("#modal-footer .btn-primary").on('click', function() {
		var a=1;
		    a=menuId;
		//判断是否是一级标签
		if(typeof(a) == 'undefined'){
			$("#oneLevelDiv").attr("style", "display:none;");
			$("#labelScoreDiv").attr("style", "display:none;");
		}
	    var label = {};
	    var url = basePath + "/playLabel/queryMaxLabelId";
	    label.id = menuId;//当前选中的id
	    label.parentId = menuId;//fid
	    doPostAjax(url, label, function (data) {
	        $("#menuId").val(data.maxId);
	        if(data.label!=""){
	          $("#fid").val(data.label);
	        }
	    })
	})
}

//删除点击确定标签时生成的id
function cancelButton() {
	var label = {};
    var id = $("#menuId").val();
    label.id = id;
    var url = basePath + "/playLabel/removePlayLabelById";
    doPostAjax(url, label, function (data) {
    	$("#addShade").attr("style", "display:none;");//关闭新增弹框
    	//getLabelList();
        window.location.href = basePath + "/base/forward/user/labelManage";
    })
}

//显示或隐藏修改窗口
//点击修改按钮出发的方法
function updateButton(type) {
	
	console.log(type)
	alert("触发修改按钮");
    $("#menuIds").val(menuId);
    $("#menuNames").val(label);
    $("#menuExplains").val(labelScore);
    if ($("input[name='project-type']:checked").length == 0 || $("input[name='project-type']:checked").length > 1) {
        modelWindow("请选择一条修改的菜单!", 0);
    } else {
        if (type == "userManage-box-btn" && fid==0) {
            $("#updatePop").attr("style", "display:block;");
            $("#updateShade").attr("style", "display:block;");
            //判断是否为一级标签如果是分数div隐藏
            $("#checkIsOrOneLevel").attr("style", "display:none;");
        }
        if (type == "userManage-box-btn" && fid!=0) {
            $("#updatePop").attr("style", "display:block;");
            $("#updateShade").attr("style", "display:block;");
            //判断是否为一级标签如果不是分数div显示
            $("#checkIsOrOneLevel").attr("style", "display:block;");
        }
        if (type == "icon iconfont" || type == "btn-cancel") {
            $("#updatePop").attr("style", "display:none;");
            $("#updateShade").attr("style", "display:none;");
        }
    }

}

//添加新的菜单
function addMenu() {
	labelModel.id = $("#menuId").val();
	labelModel.label = $("#menuName").val();
	labelModel.score = $("#labelScore").val();
    var url = basePath + "/playLabel/savePlayLabel";
    doPostAjax(url, labelModel, function (data) {
    	//getLabelList();
        window.location.href = basePath + "/base/forward/user/labelManage";
    })

    //新增成功隐藏窗口
    $("#addPop").css("display", "none");
    $("#addShade").css("display", "none");

}

function updateMenu() {
    var label = {};
    label.id = $("#menuIds").val();
    label.label = $("#menuNames").val();
    label.score = $("#menuExplains").val();
    label.parentId = fid;
    var url = basePath + "/playLabel/updatePlayLabelById";
    //调用base.js  ajax
    doPostAjax(url, label, function (data) {
        modelWindow("修改成功", 0);
        //window.location.href = basePath + "/base/forward/user/labelManage";
        getLabelList();
    })
    //修改成功隐藏窗口
    $("#updatePop").css("display", "none");
    $("#updateShade").css("display", "none");

}

//删除菜单信息
function deleteButton() {
    //复选框未选中时提示
    if ($("input[name='project-type']:checked").length == 0) {
        modelWindow("请选择一行记录进行删除", 0);
    } else {
        modelWindowParam("该操作将删除该节点以及该节点下面的所有节点，确认删除？", 0, "removeModel");
    }
}

var labelIdstr = "";
function removeModel() {
	console.log(ids)
    labelIdstr = ids.join(",")
    //console.log(labelIdstr)
    labelModel.ids=labelIdstr;
    var url = basePath + "/playLabel/removePlayLabel";
    //调用base.js  ajax
    doPostAjax(url,labelModel, function (data) {
    	modelWindow("删除成功!", 0);
    	getLabelList();
    	//window.location.href= basePath+"/base/forward/user/labelManage";
     })
    //menuIdstr = '';
}

// 打开所有，关闭所有树形结构
function closeOrOpenAll() {
    $(".new-project .right .project-info .userManage-box .close-all").click(function () {
        $(".trend-draw .two").addClass("hide")
        $(".trend-draw .three").addClass("hide")
        $(".trend-draw .four").addClass("hide")
        $(".trend-draw a .you").removeClass("hide")
        $(".trend-draw a .yxia").addClass("hide")
    })
    $(".new-project .right .project-info .userManage-box .open-all").click(function () {
        $(".trend-draw .two").removeClass("hide")
        $(".trend-draw .three").removeClass("hide")
        $(".trend-draw .four").removeClass("hide")
        $(".trend-draw a .you").addClass("hide")
        $(".trend-draw a .yxia").removeClass("hide")
    })
}

//新增菜单 判断readio是否选中
function readioSelect() {
    //判断readio是否选中
    $(".project-info .trend-draw .menu-num input").click(function () {
        $(".project-info .trend-draw .userManage-td").each(function (index, value) {
            if ($(value).find("input[name='project-type']").is(':checked')) {
                label = $(this).find(".menu-name a span").html();
                menuLv = $(this).attr("menulv");
                menuURL = $(this).find(".menu-url").text();
                labelScore = $(this).find(".menu-describe").html();
                cssName = $(this).find(".menu-icon").html();
                mid = $(this).attr("id");
                fid = $(this).attr("fid");
                menuId = $(this).find(".menu-num").text();


                var valueOne = $(value).attr("flag"), valueTwo = $(value).attr("flag");
                var value = $(value);
                while (valueOne == valueTwo) {
                    valueTwo = value.prev().attr("flag");
                    value = value.prev();
                }
                fidMenuName = value.find(".menu-describe").html();
            }

        })
    })
}


//父级到子级全选
function parentToChildren(str) {
	//alert(str);
    $(".trend-draw ." + str + " .menu-num input").click(function () {
        ids = [];
//        ids.push($(this).parents("."+str).attr('id'))
        var len = $(this).parents("." + str).nextUntil("." + str).length;
        if ($(this).is(":checked")) {
//        	console.log(1111)
            for (var j = 0; j < len; j++) {
                var child = $($(this).parents("." + str).nextUntil("." + str)[j]).attr("flag");
                var $inputDom = $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input");
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "three") {
                    if (child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "four") {
                }
                $inputDom.prop("checked");
            }
        } else {
            for (var j = 0; j < len; j++) {
                var child = $($(this).parents("." + str).nextUntil("." + str)[j]).attr("flag");
                var $inputDom = $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input");
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "three") {
                    if (child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "four") {
                }

                $inputDom.removeAttr("checked");
            }
        }
        $(".project-info .trend-draw .menu-num input").each(function (index, value) {
            if ($(value).is(":checked")) {
                ids.push($(value).parents("ul").attr("id"));
                var cStr = $(value).parents("ul").attr("flag");
                for (var j = 0; j < len; j++) {
                    var child = $($(value).parents("."+cStr).nextUntil("." + cStr)[j]).attr("flag");
                    var $inputDom = $($(value).parents("."+cStr).nextUntil("." + cStr)[j]).find(".menu-num input");
                    if (cStr == "one") {
                        if (child == "two" || child == "three" || child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "two") {
                        if (child == "three" || child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "three") {
                        if (child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "four") {

                    }
                }
            }
        });
    })
}


function checkboxArr() {
    ids = [];
    $(".trend-draw ul").each(function (index, value) {
        if ($(value).find("li input").is(":checked")) {
            ids.push($(value).find("li input").attr("id"))
        }
    })
}
//验证评分为
//function validationNumber(){
//	
//	$("#addMenu").click(function () {
//		var regu = /^\d+(\.\d)?$/;
//		var number=$("#labelScore").val();
//		if(number == null || number == undefined || number == '' || !regu.test(number)){
//			alert("请输入正确数字");
//		}else{
//			addMenu();
//		}
//		
//	})
//	
//	
//	
//}
