var userModel={};

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