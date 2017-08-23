//全选获取roleId
function subParam(isChecked){
	$(".new-project .project-message .usrManage-table-td .userManage-data").each(function (index, value) {
		if(isChecked){
			roleId +=$(this).find("ul").attr("roleid");
			roleId+=",";
		}else{
				roleId= "";
		}
		
	});
}

//全选用户未分配的角色
function subParamRole2(isChecked){
	$(".usrManage-table-user-role .userManage-data").each(function (index, value) {
		if(isChecked){
			roleId +=$(this).find("ul").attr("roleid");
			roleId+=",";
		}else{
				roleId= "";
		}
		
	});
}

//全选用户已分配的角色
function subParamRole3(isChecked){
	$(".usrManage-table-user2-role2 .userManage-data").each(function (index, value) {
		if(isChecked){
			roleId +=$(this).find("ul").attr("roleid");
			roleId+=",";
		}else{
				roleId= "";
		}
		
	});
}

//全选功能 获取功能或菜单对应的ID
function subPermMenu(isChecked){
	$(".shade .pop .trend-draw .userManage-td").each(function (index, value) {
		if(isChecked){
			permId +=$(this).attr("permid");
			permId+=",";
		}else{
			permId= "";
		}
	});
}


//时间戳日期格式转换
function add0(m){return m<10?'0'+m:m }
function formatDate(shijianchuo) {
	//shijianchuo是整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}
