$(document).ready(function(){
	ajaxQueryMessage();
		
})

//加载用户消息列表
function ajaxQueryMessage(){
$("#tcdPageCode").createPage({
		url: "/message/queryUserMessage",
		data: {},
		pageSize: 5,
		successFn: function(data) {
			var dataHtml = "";
			var messagetype ="";
        	for	(var i = 0; i < data.data.userMessageList.length; i++) {
			if(data.data.userMessageList[i].messagetype=='1'){
				messagetype="系统消息";
			}else if(data.data.userMessageList[i].messagetype=='2'){
				messagetype="个人消息";
			}else if(data.data.userMessageList[i].messagetype=='3'){
				messagetype="反馈消息";
			}
			dataHtml += '<ul class="message-every">'
        	dataHtml += '<li class="message-title">'
        	dataHtml += '<ul class="message-title-in">'
			dataHtml += '<li class="message-head">'+messagetype+' </li>'
			dataHtml += '<li class="message-time">'+formatDate(data.data.userMessageList[i].createtime)+' </li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
			dataHtml += '<li class="message-detail">'
			dataHtml += '<p>'+data.data.userMessageList[i].messageinfo+' </p>'
			dataHtml += '</li>'
			dataHtml += '</ul>'
        	}
        	$(".message-content").html(dataHtml)
		}
	});
}