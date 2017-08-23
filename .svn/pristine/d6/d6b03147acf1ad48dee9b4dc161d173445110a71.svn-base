var userBackReplyModel={};
//添加反馈内容
function addContent(){
	userBackReplyModel.content=$("#replycontent").val();
	var url= basePath+"/userBackReply/saveUserBackReply";
	//调用base.js  ajax
	doPost(url, userBackReplyModel, function(data){
		
		if(data.status !=null && data.status =="0"){
    		modelWindow("添加反馈成功",0);
    		$("#replycontent").val("");
    	}
	})
}
