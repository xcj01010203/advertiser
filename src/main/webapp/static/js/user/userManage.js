var userId="";
var userStatus;
var roleId="";
var userName="";

$(document).ready(function(){
	ajaxQueryUser();
	
})

//加载用户信息列表
function ajaxQueryUser(){
	$("#tcdPageCode").createPage({
		url: "/user/queryUser",
		data: {},
		pageSize: 10,
		successFn: function(data) { 
			var dataHtml = "";
        	for	(var i = 0; i < data.data.userList.length; i++) {
        	
        	var sex =data.data.userList[i].sex=='0'?"男":"女";
        	var status=data.data.userList[i].status =='0'?"启用用户":"冻结用户";
        	dataHtml += '<li class="userManage-data">'
        	dataHtml += '<ul userId="'+data.data.userList[i].id+'" class="userManage-data-in">'
        	dataHtml += '<li><input id="project-type" name="project-type" value="type1" type="checkbox"/></li>'
			dataHtml += '<li>'+data.data.userList[i].userName+' </li>'
			dataHtml += '<li>'+data.data.userList[i].realName+' </li>'
			dataHtml += '<li>'+sex+' </li>'
			dataHtml += '<li>'+data.data.userList[i].company+' </li>'
			dataHtml += '<li>'+data.data.userList[i].email+' </li>'
			dataHtml += '<li>'+data.data.userList[i].tel+' </li>'
			dataHtml += '<li>'+status+' </li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	
        	//选中获取userid
        	selectUserId();
	    } 
	 });
	
}

function selectUserId(){
	//选中CheckBox改变用户状态值
	$(".usrManage-table-td>li .userManage-data-in input").click(function () {
		$(".new-project .project-message .usrManage-table-td .userManage-data").each(function (index, value) {
			if($(value).find("input:checkbox").is(':checked')) {
				userName =$(this).find("li").eq(1).html().trim();
				userStatus =$(this).find("li").eq(7).html().trim();
				if(userStatus =="启用用户"){
					$("#dongjie").text("冻结用户");
				}
				if(userStatus =="冻结用户"){
					$("#dongjie").text("启用用户");
				}
				//选中获取userid
				var isExist=$(this).find("ul").attr("userid")+",";
				if(userId.indexOf(isExist)<0){
					userId +=$(this).find("ul").attr("userid");
					userId+=",";
				}
			
			}else{
				userId= userId.replace($(this).find("ul").attr("userid")+",","");
			}
		})
		
	})
}

//显示或隐藏添加窗口
function addButton(type){
	if(type == "userManage-box-btn"){
		$("#addPop").attr("style", "display:block;");  
		$("#addShade").attr("style", "display:block;");  
	}
	if(type == "icon iconfont" || type == "btn-cancel"){
		$("#addPop").attr("style", "display:none;");
		$("#addShade").attr("style", "display:none;");  
	}
	
}

//显示或隐藏修改窗口
function updateButton(type){
	//复选框未选中时提示	
	if($("input[name='project-type']:checked").length==0){
		modelWindow("请选择一条要修改的数据！",0);
	}
	//复选框选中多条提示
	if($("input[name='project-type']:checked").length>1){
		modelWindow("请选择一条要修改的数据！",0);
	}
	
	if(type == "userManage-box-btn" && $("input[name='project-type']:checked").length==1){
		$("#updatePop").attr("style", "display:block;");  
		$("#updateShade").attr("style", "display:block;");  
	}
	if(type == "icon iconfont" || type == "btn-cancel"){
		$("#updatePop").attr("style", "display:none;");
		$("#updateShade").attr("style", "display:none;");  
	}

	$(".new-project .project-message .usrManage-table-td .userManage-data").each(function (index, value) {
		if($(value).find("input:checkbox").is(':checked')) {
			userId=$(this).find("ul").attr("userid");
			$("#uuserName").val($(this).find("li").eq(1).html().trim());
			$("#urealName").val($(this).find("li").eq(2).html().trim());
			if(($(this).find("li").eq(3).html().trim()) =="男"){
				$("#sex1").attr("checked","checked");
				$("#sex2").attr("checked",false);
			}
			if(($(this).find("li").eq(3).html().trim()) =="女"){
				$("#sex2").attr("checked","checked");
				$("#sex1").attr("checked",false);
			}
			$("#ucompany").val($(this).find("li").eq(4).html().trim());
			$("#utel").val($(this).find("li").eq(6).html().trim());
			$("#uemail").val($(this).find("li").eq(5).html().trim());
		}
	})
	
}

//重置密码隐藏显示窗口
function passWordButton(type){
	if($("input[name='project-type']:checked").length==0 || $("input[name='project-type']:checked").length >1){
		modelWindow("请选择一条要重置的用户!",0);
	}else{
		if(type == "userManage-box-btn"){
			$("#passWordPop").attr("style", "display:block;");  
			$("#passWordShade").attr("style", "display:block;");  
		}
	}
	if(type == "icon iconfont" || type == "btn-cancel"){
		$("#passWordPop").attr("style", "display:none;");
		$("#passWordShade").attr("style", "display:none;");  
	}
}

//分配角色隐藏显示窗口
function roleUserButton(type){
	userId = userId.replace(",","").trim();
	//查询该用户可用的角色
	$.ajax({  
	    type: "GET",  
	    url: basePath+"/role/queryUserDoRole",
	    dataType:"json", 
	    async: false,
	    data: {userId:userId},
	    contentType : 'application/json;charset=utf-8', //设置请求头信息  
	    success: function(data){  
	    	var dataHtml = "";
	    	for	(var i = 0; i < data.data.length; i++) {
		    	dataHtml += '<li class="userManage-data">'
		    	dataHtml += '<ul roleId="'+data.data[i].id+'" class="userManage-data-in">'
		    	dataHtml += '<li><input id="project-type2" name="project-type2" value="type1" type="checkbox"/></li>'
				dataHtml += '<li>'+data.data[i].roleName+' </li>'
				dataHtml += '<li>'+data.data[i].roleExplain+' </li>'
				dataHtml += '<li>'+data.data[i].creator+' </li>'
				dataHtml += '<li>'+data.data[i].lastEditor+' </li>'
				dataHtml += '</ul>'
				dataHtml += '</li>'
	    	}
    		$(".usrManage-table-user-role").html(dataHtml)

    		//根据选中获取角色ID
    		$(".usrManage-table-user-role .userManage-data .userManage-data-in input").click(function () {
    			$(".pop .usrManage-table-user-role .userManage-data").each(function (index, value) {
    				if($(value).find("input:checkbox").is(':checked')) {
    					var isExist=$(this).find("ul").attr("roleid")+",";
    					if(roleId.indexOf(isExist)<0){
    					roleId +=$(this).find("ul").attr("roleid");
    					roleId+=",";
    					}
    				}else{
    					roleId= roleId.replace($(this).find("ul").attr("roleid")+",","");
    				}
    			})
    		})
    		
	    }
				
})

		//查询用户已有的角色
		doPost(basePath+"/role/queryUserRole", {userId}, function(data){
			var dataHtml = "";
			for	(var i = 0; i < data.data.length; i++) {
				dataHtml += '<li class="userManage-data">'
					dataHtml += '<ul roleId="'+data.data[i].id+'" class="userManage-data-in">'
					dataHtml += '<li><input id="project-type3" name="project-type3" value="type1" type="checkbox"/></li>'
						dataHtml += '<li>'+data.data[i].roleName+' </li>'
						dataHtml += '<li>'+data.data[i].roleExplain+' </li>'
						dataHtml += '<li>'+data.data[i].creator+' </li>'
						dataHtml += '<li>'+data.data[i].lastEditor+' </li>'
						dataHtml += '</ul>'
							dataHtml += '</li>'
			}
			$(".usrManage-table-user2-role2").html(dataHtml)
			
			//根据选中获取角色ID
    		$(".usrManage-table-user2-role2 .userManage-data .userManage-data-in input").click(function () {
    			$(".pop .usrManage-table-user2-role2 .userManage-data").each(function (index, value) {
    				if($(value).find("input:checkbox").is(':checked')) {
    					var isExist=$(this).find("ul").attr("roleid")+",";
    					if(roleId.indexOf(isExist)<0){
    					roleId +=$(this).find("ul").attr("roleid");
    					roleId+=",";
    					}
    				}else{
    					roleId= roleId.replace($(this).find("ul").attr("roleid")+",","");
    				}
    			})
    		})
    		
	})
	//复选框未选中时提示	
	if($("input[name='project-type']:checked").length==0){
		modelWindow("请选择要分配的用户！",0);
	}
	//复选框选中多条提示
	if($("input[name='project-type']:checked").length>1){
		modelWindow("只能选择一条用户！",0);
	}
	if(type == "userManage-box-btn" && $("input[name='project-type']:checked").length==1){
		var userNamestr1 ="给用户"+userName+"分配角色";
		var userNamestr2 ="用户"+userName+"已有角色";
		$("#pop-head-title1").html(userNamestr1)
		$("#pop-head-title2").html(userNamestr2)
		$("#roleUserpop").attr("style", "display:block;");  
		$("#roleUserShade").attr("style", "display:block;");  
	}
	if(type == "icon iconfont" || type == "btn-cancel"){
		$("#roleUserpop").attr("style", "display:none;");
		$("#roleUserShade").attr("style", "display:none;");
		window.location.href=basePath+"/base/forward/user/userManage";
	}
}

var userModel={};
//新增用户信息
function addUser(){
	userModel.userName=$("#userName").val();
	userModel.passWord=$("#opassword").val();
	userModel.realName=$("#realName").val();
	userModel.sex=$("input[type='radio']:checked").val();
	userModel.company=$("#company").val();
	userModel.tel=$("#tel").val();
	userModel.email=$("#email").val();
	var url= basePath+"/user/saveUser";
	if($("#userName").val() =="" || $("#userName").val() ==null){
		$(".userName-point").css("display", "block");
		return;
	}
	if($("#tel").val() =="" || $("#tel").val() ==null){
		$(".tel-point").css("display", "block");
		return;
	}
	if($("#email").val() =="" || $("#email").val() ==null){
		$(".email-point").css("display", "block");
		return;
	}
	//调用base.js  ajax
	doPostAjax(url, userModel, function(data){
		modelWindow("添加用户成功!",0);
		window.location.href=basePath+"/base/forward/user/userManage";
	})
	//新增成功隐藏窗口
	$("#addPop").css("display", "none");
	$("#addShade").css("display", "none"); 
}


//修改用户信息
function updateUser(){
	userModel.id=userId;
	userModel.userName=$("#uuserName").val();
	userModel.realName=$("#urealName").val();
	userModel.sex=$("input[type='radio']:checked").val();
	userModel.company=$("#ucompany").val();
	userModel.tel=$("#utel").val();
	userModel.email=$("#uemail").val();
	var url= basePath+"/user/updateUser";
	//调用base.js  ajax
	doPost(url, userModel, function(data){
		modelWindow("修改用户成功!",0);
		window.location.href=basePath+"/base/forward/user/userManage";
	})
	//修改用户信息成功隐藏窗口
	$("#updatePop").attr("style", "display:none;");
	$("#updateShade").attr("style", "display:none;"); 
}

//冻结或启用用户信息提示框
function removeButton(){
	if($("input[name='project-type']:checked").length==0){
		modelWindow("请选择要启用或冻结的用户!",0);
	}else{
		modelWindowParam("你确定要执行此操作吗!",0,"removeModel");
	}
}
//冻结或启用用户信息操作功能
function removeModel(){
	var ids=userId;
	if(userStatus =="启用用户"){
		userStatus ="2";
	}
	if(userStatus =="冻结用户"){
		userStatus ="0";
	}
	var type=userStatus;
	//调用base.js  ajax
	doPost(basePath+"/user/removeUser", {ids,type}, function(data){
		modelWindow("操作成功!",0);
		window.location.href=basePath+"/base/forward/user/userManage";
	})
}

//删除用户提示框
function deleteButton(){
	if($("input[name='project-type']:checked").length==0){
		modelWindow("请选择要删除的用户!",0);
	}else{
		modelWindowParam("你确定要执行此操作吗!",0,"deleteModel");
	}
}
//删除用户操作
function deleteModel(){
	doPost(basePath+"/user/deleteUser", {ids:userId}, function(data){
		if(data.status==1){
			modelWindow("删除失败,此用户已关联角色或创建项目,请解除后重试!",0);
		}else{
			modelWindow("删除成功!",0);
			window.location.href=basePath+"/base/forward/user/userManage";
		}
	})
}


//重置密码
function passWordUser(){
	var userModel={};
	userModel.id=userId.replace(",","");
	userModel.passWord=$("#newpass").val();
		//验证新密码是否匹配
		if($("#newpass").val() =="" || $("#newpass").val() ==null){
			$(".newpass-point").css("display", "block");
			return;
		}else if(($("#tnewpass").val() =="") || ($("#tnewpass").val() ==null) || ($("#newpass").val() !=$("#tnewpass").val())){
			$(".tnewpass-point").css("display", "block");
			return;
		}
		//调用base.js  ajax
		doPostAjax(basePath+"/user/updatePasswordByUserId", userModel, function(data){
			modelWindow("重置密码成功!",0);
			ajaxQueryUser();
			$("#passWordPop").attr("style", "display:none;");
			$("#passWordShade").attr("style", "display:none;");
		})
}

//分配用户角色
function addRole(){
	var ids =roleId;
	doPost(basePath+"/role/queryUserRole", {userId:userId}, function(data){
		if(data.data.length >0){
			modelWindow("该用户已有角色，请解除后再试!",0);
		}else if($("input[name='project-type2']:checked").length == 0 || $("input[name='project-type2']:checked").length >1){
			modelWindow("请选择一条角色分配!",0);
		}else{
			//调用base.js  ajax
			doPost(basePath+"/role/saveUserRole", {ids,userId}, function(data){
				flushRoleUserButton('userManage-box-btn');
			})
		}
	})
	
}

//解除用户角色
function removeRole(){
	var ids =roleId;
	if($("input[name='project-type3']:checked").length == 0 || $("input[name='project-type3']:checked").length >1){
		modelWindow("请选择一条角色解除!",0);
	}else{
		//调用base.js  ajax
		doPost(basePath+"/role/removeUserRole", {ids,userId}, function(data){
			flushRoleUserButton('userManage-box-btn');
		})
	}
}


//根据用户名搜索用户信息
function search(){
	var userName = $("#search").val();
	$("#tcdPageCode").createPage({
		url: "/user/queryUser",
		data: {userName:userName},
		pageSize: 10,
		successFn: function(data) {
		var dataHtml = "";
    	for	(var i = 0; i < data.data.userList.length; i++) {
    	var sex =data.data.userList[i].sex=='0'?"男":"女";
    	var status=data.data.userList[i].status =='0'?"可用":"冻结";
    	dataHtml += '<li class="userManage-data">'
    	dataHtml += '<ul userId="'+data.data.userList[i].id+'" class="userManage-data-in">'
    	dataHtml += '<li><input id="project-type" name="project-type" value="type1" type="checkbox"/></li>'
		dataHtml += '<li>'+data.data.userList[i].userName+' </li>'
		dataHtml += '<li>'+data.data.userList[i].realName+' </li>'
		dataHtml += '<li>'+sex+' </li>'
		dataHtml += '<li>'+data.data.userList[i].company+' </li>'
		dataHtml += '<li>'+data.data.userList[i].email+' </li>'
		dataHtml += '<li>'+data.data.userList[i].tel+' </li>'
		dataHtml += '<li>'+status+' </li>'
		dataHtml += '</ul>'
		dataHtml += '</li>'
    	}
    		$(".usrManage-table-td").html(dataHtml)
    		//选中获取userid
        	selectUserId();
		}
	})
	
}


//全选用户列表
function selectAll(){
	var isChecked = $("#tot").prop("checked");
	$("input[name='project-type']").prop("checked", isChecked);
	subParam(isChecked);
}
//全选用户未分配的角色
function selectAll2(){
	var isChecked = $("#tot2").prop("checked");
	$("input[name='project-type2']").prop("checked", isChecked);
	subParamRole2(isChecked);
}
//全选用户已有的角色
function selectAll3(){
	var isChecked = $("#tot3").prop("checked");
	$("input[name='project-type3']").prop("checked", isChecked);
	subParamRole3(isChecked);
}

//隐藏旧密码或新密码错误提示
function pass_none(){
	$(".newpass-point").css("display", "none");
	$(".tnewpass-point").css("display", "none");
	$(".tel-point").css("display", "none");
	$(".email-point").css("display", "none");
	$(".userName-point").css("display", "none");
}


//分配角色隐藏显示窗口
function flushRoleUserButton(type){
	userId = userId.replace(",","").trim();
	//查询该用户可用的角色
	$.ajax({  
	    type: "GET",  
	    url: basePath+"/role/queryUserDoRole",
	    dataType:"json", 
	    async: false,
	    data: {userId:userId},
	    contentType : 'application/json;charset=utf-8', //设置请求头信息  
	    success: function(data){  
	    	var dataHtml = "";
	    	for	(var i = 0; i < data.data.length; i++) {
		    	dataHtml += '<li class="userManage-data">'
		    	dataHtml += '<ul roleId="'+data.data[i].id+'" class="userManage-data-in">'
		    	dataHtml += '<li><input id="project-type2" name="project-type2" value="type1" type="checkbox"/></li>'
				dataHtml += '<li>'+data.data[i].roleName+' </li>'
				dataHtml += '<li>'+data.data[i].roleExplain+' </li>'
				dataHtml += '<li>'+data.data[i].creator+' </li>'
				dataHtml += '<li>'+data.data[i].lastEditor+' </li>'
				dataHtml += '</ul>'
				dataHtml += '</li>'
	    	}
    		$(".usrManage-table-user-role").html(dataHtml)

    		//根据选中获取角色ID
    		$(".usrManage-table-user-role .userManage-data .userManage-data-in input").click(function () {
    			$(".pop .usrManage-table-user-role .userManage-data").each(function (index, value) {
    				if($(value).find("input:checkbox").is(':checked')) {
    					var isExist=$(this).find("ul").attr("roleid")+",";
    					if(roleId.indexOf(isExist)<0){
    					roleId +=$(this).find("ul").attr("roleid");
    					roleId+=",";
    					}
    				}else{
    					roleId= roleId.replace($(this).find("ul").attr("roleid")+",","");
    				}
    			})
    		})
    		
	    }
				
})

		//查询用户已有的角色
		doPost(basePath+"/role/queryUserRole", {userId}, function(data){
			var dataHtml = "";
			for	(var i = 0; i < data.data.length; i++) {
				dataHtml += '<li class="userManage-data">'
					dataHtml += '<ul roleId="'+data.data[i].id+'" class="userManage-data-in">'
					dataHtml += '<li><input id="project-type3" name="project-type3" value="type1" type="checkbox"/></li>'
						dataHtml += '<li>'+data.data[i].roleName+' </li>'
						dataHtml += '<li>'+data.data[i].roleExplain+' </li>'
						dataHtml += '<li>'+data.data[i].creator+' </li>'
						dataHtml += '<li>'+data.data[i].lastEditor+' </li>'
						dataHtml += '</ul>'
							dataHtml += '</li>'
			}
			$(".usrManage-table-user2-role2").html(dataHtml)
			
			//根据选中获取角色ID
    		$(".usrManage-table-user2-role2 .userManage-data .userManage-data-in input").click(function () {
    			$(".pop .usrManage-table-user2-role2 .userManage-data").each(function (index, value) {
    				if($(value).find("input:checkbox").is(':checked')) {
    					var isExist=$(this).find("ul").attr("roleid")+",";
    					if(roleId.indexOf(isExist)<0){
    					roleId +=$(this).find("ul").attr("roleid");
    					roleId+=",";
    					}
    				}else{
    					roleId= roleId.replace($(this).find("ul").attr("roleid")+",","");
    				}
    			})
    		})
    		
	})
	
	if(type == "userManage-box-btn" && $("input[name='project-type']:checked").length==1){
		var userNamestr1 ="给用户"+userName+"分配角色";
		var userNamestr2 ="用户"+userName+"已有角色";
		$("#pop-head-title1").html(userNamestr1)
		$("#pop-head-title2").html(userNamestr2)
		$("#roleUserpop").attr("style", "display:block;");  
		$("#roleUserShade").attr("style", "display:block;");  
	}
	if(type == "icon iconfont" || type == "btn-cancel"){
		$("#roleUserpop").attr("style", "display:none;");
		$("#roleUserShade").attr("style", "display:none;");
		window.location.href=basePath+"/base/forward/user/userManage";
	}
}