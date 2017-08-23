$(function(){
	ajaxHistoryMessage(); //历史消息列表
	
})

//加载历史消息列表
function ajaxHistoryMessage(){
$("#tcdPageCode").createPage({
		url: "/message/queryAdminMessage",
		data: {},
		pageSize: 5,
		successFn: function(data) {
			var dataHtml = "";
			var messagetype ="";
        	for	(var i = 0; i < data.data.messageList.length; i++) {
			if(data.data.messageList[i].messagetype=='1'){
				messagetype="系统消息";
			}else if(data.data.messageList[i].messagetype=='2'){
				messagetype="个人消息";
			}else if(data.data.messageList[i].messagetype=='3'){
				messagetype="反馈消息";
			}
			dataHtml += '<ul class="message-every">'
        	dataHtml += '<li class="message-title">'
        	dataHtml += '<ul class="message-title-in">'
			dataHtml += '<li class="message-head">'+messagetype+' </li>'
			dataHtml += '<li class="message-name">用户名：'+data.data.messageList[i].userName+' </li>'
			dataHtml += '<li class="message-time">'+formatDate(data.data.messageList[i].createtime)+' </li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
			dataHtml += '<li class="message-detail">'
			dataHtml += '<p>'+data.data.messageList[i].messageinfo+' </p>'
			dataHtml += '</li>'
			dataHtml += '</ul>'
        	}
        	$(".his-text").html(dataHtml)
		}
	});
}

var messagetype;
//根据消息类型搜索消息
function searchMessage(){
	messagetype = $("#searchMessage").val();
	if(messagetype =="系统消息"){
		messagetype ='1';
	}else if(messagetype =="个人消息"){
		messagetype ='2';
	}else if(messagetype =="反馈消息"){
		messagetype ='3';
	}else{
		messagetype=null;
	}
	$("#tcdPageCode").createPage({
		url: "/message/queryAdminMessage",
		data: {messagetype:messagetype},
		pageSize: 5,
		successFn: function(data) {
			var dataHtml = "";
			var messagetype ="";
        	for	(var i = 0; i < data.data.messageList.length; i++) {
			if(data.data.messageList[i].messagetype=='1'){
				messagetype="系统消息";
			}else if(data.data.messageList[i].messagetype=='2'){
				messagetype="个人消息";
			}else if(data.data.messageList[i].messagetype=='3'){
				messagetype="反馈消息";
			}
			dataHtml += '<ul class="message-every">'
        	dataHtml += '<li class="message-title">'
        	dataHtml += '<ul class="message-title-in">'
			dataHtml += '<li class="message-head">'+messagetype+' </li>'
			dataHtml += '<li class="message-name">用户名：'+data.data.messageList[i].userName+'</li>'
			dataHtml += '<li class="message-time">'+formatDate(data.data.messageList[i].createtime)+' </li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
			dataHtml += '<li class="message-detail">'
			dataHtml += '<p>'+data.data.messageList[i].messageinfo+' </p>'
			dataHtml += '</li>'
			dataHtml += '</ul>'
        	}
        	$(".his-text").html(dataHtml)
		}
	});
	
}
