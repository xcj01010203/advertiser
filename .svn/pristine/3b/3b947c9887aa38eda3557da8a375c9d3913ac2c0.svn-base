var roleId = ""; //角色ID
var permId =""; //功能菜单ID
var ids=[];  //角色选中的菜单功能ID

$(document).ready(function () {

    ajaxQueryRole();

    //根据选中的CheckBox获取角色ID
    $(".usrManage-table-td>li .userManage-data-in input").click(function () {
        $(".new-project .project-message .usrManage-table-td .userManage-data").each(function (index, value) {
            if ($(value).find("input:checkbox").is(':checked')) {
                var isExist = $(this).find("ul").attr("roleid") + ",";
                if (roleId.indexOf(isExist) < 0) {
                    roleId += $(this).find("ul").attr("roleid");
                    roleId += ",";
                }
            } else {
                roleId = roleId.replace($(this).find("ul").attr("roleid") + ",", "");
            }
        });
    });

    // 分配功能 查看该角色的功能列表 分配菜单 查看该角色的菜单列表 打开
    sAllMenuManShow()

})

//加载角色信息列表
function ajaxQueryRole() {
    $.ajax({
        type: "POST",
        url: basePath + "/role/queryRole",
        dataType: "json",
        async: false,
        contentType: 'application/json;charset=utf-8', //设置请求头信息
        success: function (data) {
            var dataHtml = "";
            for (var i = 0; i < data.data.length; i++) {
                dataHtml += '<li class="userManage-data">'
                dataHtml += '<ul name="rowul" roleId="' + data.data[i].id + '" class="userManage-data-in">'
                dataHtml += '<li><input name="project-type" value="type1" type="checkbox"/></li>'
                dataHtml += '<li>' + data.data[i].roleName + ' </li>'
                dataHtml += '<li>' + data.data[i].roleExplain + ' </li>'
                dataHtml += '<li>' + data.data[i].creator + ' </li>'
                dataHtml += '<li>' + formatDate(data.data[i].createTime) + ' </li>'
                dataHtml += '<li>' + data.data[i].lastEditor + ' </li>'
                dataHtml += '<li>' + formatDate(data.data[i].lastEditTime) + ' </li>'
                dataHtml += '</ul>'
                dataHtml += '</li>'
            }
            $(".usrManage-table-td").html(dataHtml)

        }
    });
}

//新增角色信息窗口显示或隐藏
function addRoleButton(type) {
    if (type == "userManage-box-btn") {
        $("#addPop").attr("style", "display:block;");
        $("#addShade").attr("style", "display:block;");
    }
    if (type == "icon iconfont" || type == "btn-cancel") {
        $("#addPop").attr("style", "display:none;");
        $("#addShade").attr("style", "display:none;");
    }
}

var roleModel = {};
//新增角色信息
function addRole() {
    roleModel.roleName = $("#roleName").val();
    roleModel.roleExplain = $("#roleExplain").val();
    var url = basePath + "/role/saveRole";
    //调用base.js  ajax
    doPost(url, roleModel, function (data) {
    	if(data.status=="0"){
    		modelWindow("添加成功!",0);
    		window.location.href=basePath+"/base/forward/user/roleManage";
    	}else{
    		modelWindow("添加失败!",0);
    		window.location.href=basePath+"/base/forward/user/roleManage";
    	}
    	//新增成功隐藏窗口
    	$("#addPop").css("display", "none");
    	$("#addShade").css("display", "none");
    })
}

//修改角色窗口的显示或隐藏
function updateRoleButton(type) {
    //复选框未选中时提示
    if ($("input[name='project-type']:checked").length == 0) {
    	modelWindow("请选择要修改的数据",0);
    }
    //复选框选中多条提示
    if ($("input[name='project-type']:checked").length > 1) {
    	modelWindow("请选择一条要修改的数据",0);
    }

    if (type == "userManage-box-btn" && $("input[name='project-type']:checked").length == 1) {
        $("#updatePop").css("display", "block");
        $("#updateShade").css("display", "block");
    }
    if (type == "icon iconfont" || type == "btn-cancel") {
        $("#updatePop").css("display", "none");
        $("#updateShade").css("display", "none");
    }

    $(".new-project .project-message .usrManage-table-td .userManage-data").each(function (index, value) {
        if ($(value).find("input:checkbox").is(':checked')) {
            roleId = $(this).find("ul").attr("roleid");
            $("#uroleName").val($(this).find("li").eq(1).html().trim());
            $("#uroleExplain").val($(this).find("li").eq(2).html().trim());
        }
    })

}

//修改角色信息
function updateRole() {
    roleModel.id = roleId.replace(",","");
    roleModel.roleName = $("#uroleName").val();
    roleModel.roleExplain = $("#uroleExplain").val();
    var url = basePath + "/role/updateRole";
    //调用base.js  ajax
    doPost(url, roleModel, function (data) {
    	//修改用户信息成功隐藏窗口
    	if(data.status=="0"){
			modelWindow("修改成功!",0);
			window.location.href=basePath+"/base/forward/user/roleManage";
		}else{
			modelWindow("修改失败!",0);
			window.location.href=basePath+"/base/forward/user/roleManage";
		}
    	$("#updatePop").css("display", "none");
    	$("#updateShade").css("display", "none");
    })
}

//删除角色信息
function removeRoleButton() {
    //复选框未选中时提示
    if ($("input[name='project-type']:checked").length == 0) {
    	modelWindow("请选择要删除的数据",0);
    } else {
    	modelWindowParam("你确定删除这条记录!",0,"removeModel");
    }

}

//删除角色
function removeModel(){
	var ids = roleId;
	doPost(basePath + "/role/removeRole", {ids:ids}, function (data) {
		if(data.status=="0"){
			modelWindow("删除成功!",0);
			ajaxQueryRole();
		}else{
			modelWindow("删除失败,该角色已绑定用户或菜单，请接触后重试!",0);
			ajaxQueryRole();
		}
    })
}

//分配功能 查看该角色的功能列表 分配菜单 查看该角色的菜单列表 关闭
function dtbRoleButton(that) {
    $(that).parents(".shade").addClass("hide")
}

// 分配功能 查看该角色的功能列表 分配菜单 查看该角色的菜单列表 打开
function sAllMenuManShow() {
    $(".new-project .right .project-info .userManage-box .u-btn").click(function () {
    	if($("input[name='project-type']:checked").length ==0 || $("input[name='project-type']:checked").length >1){
    		modelWindow("请选择一个角色进行分配", 0);
    	}else{
    		//获取已有角色选中
    		getAlreadyRole();
    		$(".shade").removeClass("hide")
    		var type = $(this).attr("type")
    		
    		$("#popModel").attr("style", "display:block;");
    		$("#shadeModel").attr("style", "display:block;");
    		// 分配功能 查看该角色的功能列表 分配菜单 查看该角色的菜单列表 ajax
    		seeAllotMenuManageFunc(type)
    		
    	}
    	
    })
}

// 分配功能 查看该角色的功能列表 分配菜单 查看该角色的菜单列表 ajax
function seeAllotMenuManageFunc(type) {
    function errorFn() {}
    if (type == "allot-func") {
    	// 分配菜单
        $(".shade .pop .project-info-top h4").html("分配菜单功能");
        $(".shade .pop .userManage-box").html('<button id="addMenus" class="userManage-box-btn" onclick="addMenu()">赋予菜单</button><button class="close-all userManage-box-btn">关闭所有</button><button class="open-all userManage-box-btn">打开所有</button>')
        $(".shade .pop .usrManage-th").html('<li class="func-name"><input name="project-type2" id="totPerm" value="type1" type="checkbox" onclick="selectAllPermMenu();"/>菜单名称</li><li class="func-describe">菜单描述</li><li class="func-url">菜单URL</li>');
        doPost(basePath + "/menu/queryMenu", {}, successFnMenuManage, errorFn, false);
        function successFnMenuManage(data) {
            if (data.status == 0) {
                var data = data.data;
                var html = '';
                if (!!data.menu) {
                    for (var i = 0; i < data.menu.length; i++) {
                        html += ' <ul flag="one" class="one user-table userManage-td" permId='+data.menu[i].id+' fid='+data.menu[i].fid+'>';
                        if (!!data.menu[i].nodes && data.menu[i].nodes.length != 0) {
                            html += '<li class="func-name"><input name="project-type-perm" type="checkbox" id='+data.menu[i].id+' fid='+data.menu[i].fid+'><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].menuName + '</span></a></li>';
                        } else {
                            html += '<li class="func-name"><input name="project-type-perm" type="checkbox" id='+data.menu[i].id+' fid='+data.menu[i].fid+'><span>' + data.menu[i].menuName + '</span></li>'
                        }
                        html += '<li class="func-describe">' + data.menu[i].menuExplain + '</li>'
                        html += '<li class="func-url">' + data.menu[i].menuURL + '</li>'
                        html += '</ul>';
                        if (!!data.menu[i].nodes && data.menu[i].nodes != 0) {
                            for (var j = 0; j < data.menu[i].nodes.length; j++) {
                                html += ' <ul flag="two" class="two user-table userManage-td hide" permId='+data.menu[i].nodes[j].id+' fid='+data.menu[i].nodes[j].fid+'>';
                                if (!!data.menu[i].nodes[j].nodes && data.menu[i].nodes[j].nodes.length != 0) {
                                    html += '<li class="func-name"><input name="project-type-perm" type="checkbox" id='+data.menu[i].nodes[j].id+' fid='+data.menu[i].nodes[j].fid+'><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                        '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].nodes[j].menuName + '</span></a></li>';
                                } else {
                                    html += '<li class="func-name"><input name="project-type-perm" type="checkbox" id='+data.menu[i].nodes[j].id+' fid='+data.menu[i].nodes[j].fid+'><span>' + data.menu[i].nodes[j].menuName + '</span></li>'
                                }
                                html += '<li class="func-describe">' + data.menu[i].nodes[j].menuExplain + '</li>'
                                html += '<li class="func-url">' + data.menu[i].nodes[j].menuURL + '</li>'
                                html += '</ul>';
                                if (!!data.menu[i].nodes[j].nodes && data.menu[i].nodes != 0) {
                                    for (var k = 0; k < data.menu[i].nodes[j].nodes.length; k++) {
                                        html += ' <ul flag="three" class="three user-table userManage-td hide" permId='+data.menu[i].nodes[j].nodes[k].id+'  fid='+data.menu[i].nodes[j].nodes[k].fid+'>';
                                        if (!!data.menu[i].nodes[j].nodes[k].nodes && data.menu[i].nodes[j].nodes[k].nodes.length != 0) {
                                            html += '<li class="func-name"><input name="project-type-perm" type="checkbox" id='+data.menu[i].nodes[j].nodes[k].id+' fid='+data.menu[i].nodes[j].nodes[k].fid+'><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                                '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].nodes[j].nodes[k].menuName + '</span></a></li>';
                                        } else {
                                            html += '<li class="func-name"><input name="project-type-perm" type="checkbox"  id='+data.menu[i].nodes[j].nodes[k].id+'  fid='+data.menu[i].nodes[j].nodes[k].fid+'><span>' + data.menu[i].nodes[j].nodes[k].menuName + '</span></li>'
                                        }
                                        html += '<li class="func-describe">' + data.menu[i].nodes[j].nodes[k].menuExplain + '</li>'
                                        html += '<li class="func-url">' + data.menu[i].nodes[j].nodes[k].menuURL + '</li>'
                                        html += '</ul>';
                                        if (!!data.menu[i].nodes[j].nodes[k].nodes) {
                                            for (var g = 0; g < data.menu[i].nodes[j].nodes[k].nodes.length; g++) {
                                                html += ' <ul flag="four" class="four user-table userManage-td hide" permId='+data.menu[i].nodes[j].nodes[k].nodes[g].id+'  fid='+data.menu[i].nodes[j].nodes[k].nodes[g].fid+'>';
                                                html += '<li class="func-name"><input name="project-type-perm" type="checkbox"  id='+data.menu[i].nodes[j].nodes[k].nodes[g].id+' fid='+data.menu[i].nodes[j].nodes[k].nodes[g].fid+'><span>' + data.menu[i].nodes[j].nodes[k].nodes[g].menuName + '</span></li>'
                                                html += '<li class="func-describe">' + data.menu[i].nodes[j].nodes[k].nodes[g].menuExplain + '</li>'
                                                html += '<li class="func-url">' + data.menu[i].nodes[j].nodes[k].nodes[g].menuURL + '</li>'
                                                html += '</ul>';
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                $(".shade .pop .trend-draw").html(html);
            }
        }
    } else {
}

    // 树形插件的事件
    treeEvent('one', 'two')
    treeEvent('two', 'three')
    treeEvent('three', 'four')

    // 父级到子级全选
    // parentToChildren()
    parentToChildren("one")
    parentToChildren("two")
    parentToChildren("three")

    // 打开所有，关闭所有树形结构
    closeOrOpenAll()
    //角色赋予分配菜单 判断是checkbox否选中
    readioSelectFid();
}

//树形插件的事件
function treeEvent(str1, str2) {
    $(".trend-draw ."+str1+" .func-name a").click(function () {
        var len;
        if ($(this).parents("."+str1).next("."+str2).hasClass("hide")) {
            len = $(this).parents("."+str1).nextUntil("."+str1).length;
            for (var  i = 0; i < len; i++) {
                if($($(this).parents("."+str1).nextUntil("."+str1)[i]).attr("flag") == str2) {
                    $($(this).parents("."+str1).nextUntil("."+str1)[i]).removeClass("hide")
                }
            }
            $(this).find(".you").addClass("hide")
            $(this).find(".yxia").removeClass("hide")
        } else {
            $(this).parents("."+str1).nextUntil("."+str1).addClass("hide")
            $(this).find(".you").removeClass("hide")
            $(this).find(".yxia").addClass("hide")
        }
    })
}

// 父级到子级全选
function parentToChildren(str) {
    $(".trend-draw ."+str+" .func-name input").click(function () {
    	$(".pop .trend-draw .func-name input").each(function (index, value) {
        var len = $(this).parents("."+str).nextUntil("."+str).length;
        if ($(this).is(":checked")) {
        	checkboxArr();
        	var id =$(this).attr("id");
 			ids.push(id);
            for (var i = 0; i < len; i++) {
                var child = $($(this).parents("."+str).nextUntil("."+str)[i]).attr("flag")
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[i]).find(".func-name input").prop("checked", "true")
                    }
                }else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[i]).find(".func-name input").prop("checked", "true")
                    }
                }else if (str == "three") {
                    if (child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[i]).find(".func-name input").prop("checked", "true")
                    }
                }else {}
            }
        }else {
        	checkboxArr();
        	
            for (var j = 0; j < len; j++) {
                var child = $($(this).parents("."+str).nextUntil("."+str)[j]).attr("flag")
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[j]).find(".func-name input").removeAttr("checked")
                    }
                }else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[j]).find(".func-name input").removeAttr("checked")
                    }
                }else if (str == "three") {
                    if (child == "four") {
                        $($(this).parents("."+str).nextUntil("."+str)[j]).find(".func-name input").removeAttr("checked")
                    }
                }else {}
            }
            
        }
   });    
})

}

// 打开所有，关闭所有树形结构
function closeOrOpenAll() {
    $(".shade .pop .userManage-box .close-all").click(function () {
        $(".trend-draw .two").addClass("hide")
        $(".trend-draw .three").addClass("hide")
        $(".trend-draw .four").addClass("hide")
        $(".trend-draw a .you").removeClass("hide")
        $(".trend-draw a .yxia").addClass("hide")
    })
    $(".shade .pop .userManage-box .open-all").click(function () {
        $(".trend-draw .two").removeClass("hide")
        $(".trend-draw .three").removeClass("hide")
        $(".trend-draw .four").removeClass("hide")
        $(".trend-draw a .you").addClass("hide")
        $(".trend-draw a .yxia").removeClass("hide")
    })
}

//全选
function selectAll() {
    var isChecked = $("#tot").prop("checked");
    $("input[name='project-type']").prop("checked", isChecked);
    subParam(isChecked);
}

//全选角色菜单
function selectAllPermMenu() {
    var isChecked = $("#totPerm").prop("checked");
    $("input[name='project-type-perm']").prop("checked", isChecked);
    subPermMenu(isChecked);
}

//给角色赋予菜单
var menuIdstr ="";
function addMenu(){
	var url= basePath+"/menu/saveRoleMenu";
	for(var i=0;i<ids.length;i++){
		menuIdstr =menuIdstr+","+ids[i];
	}
	if(menuIdstr !="" && menuIdstr !=null){
		if (menuIdstr.substr(0,1)==','){
			menuIdstr=menuIdstr.substr(1);
		}
		//调用base.js  ajax
		doPost(url, {roleId:roleId.replace(",",""),ids:menuIdstr}, function(data){
			modelWindow("分配菜单成功",0);
			window.location.href=basePath+"/base/forward/user/roleManage";
			$("#popModel").attr("style", "display:none;");
			$("#shadeModel").attr("style", "display:none;");
		})
	}else{
		modelWindow("请至少选择一个菜单",0);
	}
}

//角色赋予分配菜单 判断是checkbox否选中
function readioSelectFid() {
    //判断是否全选获取所有ID
	 $(".pop .usrManage-th .func-name input").click(function () {
		 var isChecked = $("#totPerm").prop("checked");
		 $("input[name='project-type-perm']").prop("checked", isChecked)
		 $(".shade .pop .trend-draw .userManage-td").each(function (index, value) {
     		if(isChecked){
     			var id =$(this).attr("permid");
     			ids.push(id);
     		}else{
     			ids =[];
     		}
     	});
	 });
	 
	//判断单选是否选中  并且找到父类选中
    $(".pop .trend-draw .userManage-td .func-name input").click(function () {
            if ($(this).is(':checked')) {
            		checkboxArr();
            		//获取到选中的当前ID
            		var fidOne = $(this).attr("id");
            		//查找ID并选中
	            	find(fidOne,ids);
	            	//数组与字符串类型转换
	            	toString(ids);
            }else{
            	var fidOne = $(this).attr("id");
            	//删除已选中的元素
            	removeByValue(fidOne,ids);
            	//取消选中删除id
            	removeNodes(fidOne,ids);
            	toString(ids);
            }
    })
}

//checkbox选中，并且把状态设置为true
function find(id,ids){
	var c = $("#"+id);
	var fids = c.attr("fid");
	c.prop("checked",true);
	if(searchIndex(id,ids)==-1){
		ids.push(id);
	}
	if(fids !=null && fids !=""){
		find(fids,ids);
	}
}

//删除取消checkbox,并且把状态设置为false
function removeNodes(id,ids){
	var c = $("#"+id);
	var cfid=c.attr("fid");
	var returns = isDelete(cfid,id);
	if(returns){
		$("#"+cfid).attr("checked",false);
		removeByValue(cfid,ids);
		removeNodes(cfid,ids);
	}
}

//处理取消选中是否删除
function isDelete(fid,id){
	var value = $("input[fid="+fid+"]");
	if(value ==null || value.length<=0){
		return false;
	}
	var count=0;
	for(var i=0;i<value.length;i++){
		var d =value.eq(i).is(':checked');
		var idstr =value.eq(i).attr("id");
		if(d){
			count++; 
		}
	}
		var res = count<=0;
		return res;
}

//删除元素
function removeByValue(val) {
	var index =searchIndex(val,ids);
	if(index>=0){
      ids.splice(index, 1);
	}
}

//查找已选中ids下标索引
function searchIndex(val,arr) {
	  for(var i=0; i<arr.length; i++) {
	    if(arr[i] == val) {
			return i;
	    }
	  }
		return -1;	
	}

//ids数组转换字符串
function toString(array){
	var str ="";
	for(var i=0;i<array.length;i++){
		str =str+","+array[i];
	}
	if(array.length>0){
		return str.substring(1,str.length-1);
	}
}

//获取已有角色选中
function getAlreadyRole(){
	if(roleId !=null&& roleId!=""){
		var roleIds =roleId.substring(0,roleId.length-1)
		doPost(basePath + "/menu/queryRoleMenu", {roleId:roleIds}, function (data) {
		var id;
		var ids=[];
		for(var i=0;i<data.data.menu.length;i++){
			id =data.data.menu[i].id;
			find(id,ids);
			if (!!data.data.menu[i].nodes) {
			for (var j = 0; j < data.data.menu[i].nodes.length; j++) {
				id =data.data.menu[i].nodes[j].id;
				find(id,ids);
				if (!!data.data.menu[i].nodes[j].nodes) {
				 for (var k = 0; k < data.data.menu[i].nodes[j].nodes.length; k++) {
					 id =data.data.menu[i].nodes[j].nodes[k].id;
						find(id,ids);
						if (!!data.data.menu[i].nodes[j].nodes[k].nodes) {
							 for (var y = 0; y < data.data.menu[i].nodes[j].nodes[k].nodes.length; y++) {
								 id =data.data.menu[i].nodes[j].nodes[k].nodes[y].id;
									find(id,ids);
									
									 		}
										}
						 		}
							}
						}
					}
				}
		})
	}
}


function checkboxArr() {
	//alert(JSON.stringify(ids));
	ids = [];
	$(".trend-draw ul").each(function (index, value) {
		if ($(value).find("li input").is(":checked")) {
			ids.push($(value).find("li input").attr("id"))
		}
	})
}
