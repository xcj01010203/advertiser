//手机号中间几位加密
$(document).ready(function () {
    $("#tel").val($("#hidden_tel").val().substring(0, 3) + "****" + $("#hidden_tel").val().substring(7, 11));
});

//重置密码
function passWordUser(){
	var newpass=$("#newpass").val();
	var oldpass=$("#oldpass").val();
		//验证新密码是否匹配
		if($("#newpass").val() =="" || $("#newpass").val() ==null){
			$(".newpass-point").css("display", "block");
			return;
		}else if(($("#tnewpass").val() =="") || ($("#tnewpass").val() ==null) || ($("#newpass").val() !=$("#tnewpass").val())){
			$(".tnewpass-point").css("display", "block");
			return;
		}
		//调用base.js  ajax
		doPost(basePath+"/user/updatePassword", {newpass,oldpass}, function(data){
			if(data.data == "原始密码不正确,请重新输入!"){
				$(".pass-point").css("display", "block");
			}else{
				$("#passWordPop").attr("style", "display:none;");
				$("#passWordShade").attr("style", "display:none;");  
				modelWindow("重置成功!",0);
			}
		})
}
//隐藏密码错误提示
function pass_none(){
	$(".newpass-point").css("display", "none");
	$(".tnewpass-point").css("display", "none");
	$(".pass-point").css("display", "none");
}
 