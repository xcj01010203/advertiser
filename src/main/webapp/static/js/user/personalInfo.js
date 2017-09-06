var userModel={};

$(function() {
		var url =basePath+"/message/queryMessageUser";
		doPost(url,null,function(data){
			console.log(data.data);
			$("#uuserName").val(data.data[0].userName);
			$("#ucompany").val(data.data[0].company);
			$("#uemail").val(data.data[0].email);
			var sex =data.data[0].sex;
			if(sex==0){
				$("#sex1").attr("checked","checked");
				$("#sex2").attr("checked",false);
			}else if(sex ==1){
				$("#sex2").attr("checked","checked");
				$("#sex1").attr("checked",false);
			}
	})
	
});

//修改用户信息
function updateUser(){
	userModel.userName=$("#uuserName").val();
	userModel.sex=$("input[type='radio']:checked").val();
	userModel.company=$("#ucompany").val();
	userModel.email=$("#uemail").val();
	var url= basePath+"/user/updateUser";
	//调用base.js  ajax
	doPost(url, userModel, function(data){
		modelWindow("修改成功!",0);
	})
}