var mesId=""; //反馈内容ID
var replyContents=""; //反馈回复内容
var contents=""; //反馈意见内容

$(function(){
	queryFeedBackMsg();
	
})

//加载反馈意见列表
function queryFeedBackMsg(){
$("#tcdPageCode").createPage({
		url: "/userBackReply/queryUserBackReply",
		data: {},
		pageSize: 10,
		successFn: function(data) {
			var dataHtml = "";
        	for	(var i = 0; i < data.data.backReplyList.length; i++) {
        		
        		if(data.data.backReplyList[i].replycontent !="" && data.data.backReplyList[i].replycontent !=null){
        			var repContentStr =data.data.backReplyList[i].replycontent.substr(0,8);
        		}
        		var contentStr =data.data.backReplyList[i].content.substr(0,8);
				dataHtml += '<li class="userManage-data">'
	        	dataHtml += '<ul class="userManage-data-in">'
	        	dataHtml += '<li class="feed-user">'+data.data.backReplyList[i].userName+'</li>'
				dataHtml += '<li class="feed-tell">'+data.data.backReplyList[i].tel+'</li>'
				dataHtml += '<li class="feed-back"><a content='+data.data.backReplyList[i].content+' class="contentClick" href="javascript:conClick();">'+contentStr+'.....</a></li>'
				dataHtml += '<li class="feed-time">'+formatDate(data.data.backReplyList[i].createtime)+'</li>'
				if(data.data.backReplyList[i].replystatus =="0"){
					dataHtml += '<li class="feed-style feed-style-none"><a mesId='+data.data.backReplyList[i].id+' class="messageClick" href="javascript:addReplyContent();">回复</a></li>'
				}
				if(data.data.backReplyList[i].replystatus =="1"){
					dataHtml += '<li class="feed-style feed-style-none">已回复</li>'
				}
				if(data.data.backReplyList[i].replycontent =="" || data.data.backReplyList[i].replycontent ==null){
					dataHtml += '<li class="feed-content">未回复</li>'
				}else{
					dataHtml += '<li class="feed-content"><a mesContent='+data.data.backReplyList[i].replycontent+' class="replyConClick" href="javascript:over();">'+repContentStr+'.....</a></li>'
				}
				dataHtml += '</ul>'
				dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	
        	//点击获取反馈记录ID
        	$(".messageClick").click(function () {
				mesId=$(this).attr("mesid");
        	})
        	//点击获取获取反馈回复内容
        	$(".replyConClick").click(function () {
        		replyContents=$(this).attr("mescontent");
        	})
        	
        	$(".contentClick").click(function () {
        		contents=$(this).attr("content");
        	})
		}
	});
}

//显示或隐藏回复反馈窗口
function addReplyContent(type){
	if(type == "iconfont" || type == "btn-cancel"){
		$("#addReplyShade").attr("style", "display:none;");
		$("#addReplyPop").attr("style", "display:none;");
	}else{
		$("#addReplyShade").attr("style", "display:block;");
		$("#addReplyPop").attr("style", "display:block;");
	}
	
}

//回复反馈内容
function addBeedRepltyContent(){
	var replyContent =$("#text-area").val();
	var url = basePath + "/userBackReply/updateUserBackReply";
    //调用base.js  ajax
    doPost(url, {id:mesId,replyContent:replyContent}, function (data) {
    	if(data.status !=null && data.status =="0"){
    		modelWindow("回复反馈成功",0);
    		queryFeedBackMsg();
    		$("#text-area").val("");
    		$("#addReplyShade").attr("style", "display:none;");
    		$("#addReplyPop").attr("style", "display:none;");
    	}
    })
}

//点击渲染反馈回复内容信息
function over(type){
	if(type == "iconfont"){
		$("#replyContentShade").attr("style", "display:none;");
		$("#replyContentPop").attr("style", "display:none;");
	}else{
		$("#replyContentShade").attr("style", "display:block;");
		$("#replyContentPop").attr("style", "display:block;");
		$(".feed-text").html(replyContents);
	}
}

//点击渲染反馈内容信息
function conClick(type){
	if(type == "iconfont"){
		$("#replyContentShade").attr("style", "display:none;");
		$("#replyContentPop").attr("style", "display:none;");
	}else{
		$("#replyContentShade").attr("style", "display:block;");
		$("#replyContentPop").attr("style", "display:block;");
		$(".feed-text").html(contents);
	}
}
