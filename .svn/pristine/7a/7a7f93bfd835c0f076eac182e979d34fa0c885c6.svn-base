var userId="";

$(function(){
	ajaxQueryUser();
	
})

//加载用户列表
function ajaxQueryUser(){
$("#tcdPageCode").createPage({
		url: "/user/queryUser",
		data: {},
		pageSize: 10,
		successFn: function(data) {
			var dataHtml = "";
        	for	(var i = 0; i < data.data.userList.length; i++) {
        		var sex =data.data.userList[i].sex=='0'?"男":"女";
				dataHtml += '<li class="userManage-data">'
	        	dataHtml += '<ul class="userManage-data-in" userId="' + data.data.userList[i].id + '">'
	        	dataHtml += '<li class="msg-check"><input name="project-type1" value="type1" type="checkbox"/></li>'
				dataHtml += '<li class="msg-name">'+data.data.userList[i].userName+'</li>'
				dataHtml += '<li class="msg-realName">'+data.data.userList[i].realName+'</li>'
				dataHtml += '<li class="msg-sex">'+sex+'</li>'
				dataHtml += '<li class="msg-company">'+data.data.userList[i].company+'</li>'
				dataHtml += '<li class="msg-mail">'+data.data.userList[i].email+'</li>'
				dataHtml += '<li class="msg-phone">'+data.data.userList[i].tel+'</li>'
				dataHtml += '</ul>'
				dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	
        	selectChbox();
		}
	});
}

//根据用户名搜索用户信息
function search(){
	var userName = $("#feed-search").val();
	$("#tcdPageCode").createPage({
		url: "/user/queryUser",
		data: {userName:userName},
		pageSize: 10,
		successFn: function(data) {
			var dataHtml = "";
        	for	(var i = 0; i < data.data.userList.length; i++) {
        		var sex =data.data.userList[i].sex=='0'?"男":"女";
				dataHtml += '<li class="userManage-data">'
	        	dataHtml += '<ul class="userManage-data-in" userId="' + data.data.userList[i].id + '">'
	        	dataHtml += '<li class="msg-check"><input name="project-type1" value="type1" type="checkbox"/></li>'
				dataHtml += '<li class="msg-name">'+data.data.userList[i].userName+'</li>'
				dataHtml += '<li class="msg-realName">'+data.data.userList[i].realName+'</li>'
				dataHtml += '<li class="msg-sex">'+sex+'</li>'
				dataHtml += '<li class="msg-company">'+data.data.userList[i].company+'</li>'
				dataHtml += '<li class="msg-mail">'+data.data.userList[i].email+'</li>'
				dataHtml += '<li class="msg-phone">'+data.data.userList[i].tel+'</li>'
				dataHtml += '</ul>'
				dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	
        	selectChbox();
		}
	});
	
}

//选择消息类型
function addMessage(){
	if($("#messagetype").val() == '1'){
		$("#selectMessage").attr("style", "display:none;");
		$("#tcdPageCode").css("display", "none");
	}else{
		$("#selectMessage").attr("style", "display:block;");
		$("#tcdPageCode").css("display", "block");
	}
}

//添加系统消息或个人消息
function addUserMessage(){
	var messageModel={};
	messageModel.messagetype=$("#messagetype").val();
	messageModel.messageinfo=$("#msg-add-text").val();
	messageModel.userid=userId;
	userId = ''
		var url = basePath + "/message/saveMessage";
		if($("input[name='project-type1']:checked").length == 0 && $("#messagetype").val()==2){
			modelWindow("请选择用户",0);
		}else{
			//调用base.js  ajax
			 doPost(url, messageModel, function (data) {
				 modelWindow("添加消息成功",0);
				 ajaxQueryUser();
				 $("#msg-add-text").val("");
	    })
	}
}


function selectChbox(){
	//根据选中的CheckBox获取用户ID
    $(".usrManage-table-td .userManage-data .msg-check input").click(function () {
    	$(".usrManage-table-td .userManage-data").each(function (index, value) {
            if ($(value).find("input:checkbox").is(':checked')) {
                var isExist = $(this).find("ul").attr("userid") + ",";
                if (userId.indexOf(isExist) < 0) {
                	userId += $(this).find("ul").attr("userid");
                	userId += ",";
                }
            } else {
            	userId = userId.replace($(this).find("ul").attr("userid") + ",", "");
            }
            
        });

    });
}
