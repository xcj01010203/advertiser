var email='lishuang@trinityearth.com.cn';
var REG_EMAIL = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var REG_DATE=/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))$/;
//yyyy-MM-dd
var microBlog=function()
{
	if(!/^(http:\/\/weibo.com\/u\/)([0-9]{10})(\?profile_ftype=1&is_all=1#_0)$/.test($("#mbUrl").val()))
	{
		alert("官微地址格式错误");
		return;
	}
	if(!emailValidate($("#mbEmail").val()))
		return;
	
	var targetList = new Array();  
	targetList.push({url:$("#mbUrl").val()});
	var param={};
	param.thread=2;
	param.pageProcessor='MicroBlogTopic';
	param.pipeline='HotTV';
	param.dsKey='emptyDS';
	param.sleepTime=5;
	param.randomSleep=5;
	param.siteReqHeader=1;
	//param.startDate
	//param.endDate

	param.email=$("#mbEmail").val();
	param.subject="官微数据爬取";
	param.content="csv文件格式：时间|内容|赞|转发|评论";
	
	runSpider({targetList:targetList,paraMap:param});
};

var weiboFans=function()
{
	if(!/^(http:\/\/weibo.com\/p\/)(.*)(\/followlist)$/.test($("#wbUrl").val()))
	{
		alert("微博地址格式错误");
		return;
	}
	if(!emailValidate($("#wbEmail").val()))
		return;
	
	var targetList = new Array();  
	targetList.push({url:$("#wbUrl").val()});
	var param={};
	param.thread=2;
	param.pageProcessor='WeiboFans';
	param.pipeline='HotTV';
	param.dsKey='emptyDS';
	param.sleepTime=5;
	param.randomSleep=5;
	param.siteReqHeader=1;

	param.email=$("#mbEmail").val();
	param.subject="微博粉丝数据爬取";
	param.content="csv文件格式：粉丝id|所在地|性别|生日|简介|标签信息|教育信息|工作信息";
	
	runSpider({targetList:targetList,paraMap:param});
};

var doubanComment =function(){
	if(!/^(http:\/\/movie.douban.com\/p\/)(.*)(\/comments)$/.test($("#cmUrl").val())){
		alert("豆瓣评论地址格式错误");
		return;
	}
	if(!emailValidate($("#cmEmail").val()))
		return;
	var targetList = new Array();  
	targetList.push({url:$("#cmUrl").val()});
	var param={};
	param.thread=2;
	param.pageProcessor='DouBanComment';
	param.pipeline='HotTV';
	param.dsKey='emptyDS';
	param.sleepTime=5;
	param.randomSleep=5;
	param.siteReqHeader=1;

	param.email=$("#cmEmail").val();
	param.subject="豆瓣评论数据爬取";
	param.content="csv文件格式：评论人|评论时间|评论内容|评论权重|评论等级";
	
	runSpider({targetList:targetList,paraMap:param});
}

var emailValidate=function(eml)
{
	if(REG_EMAIL.test(eml))
		return true;
	else
	{
		alert('邮箱格式错误');
		return false;
	}
}


function runSpider(para)
{
	$.ajax({  
	    type: "POST",  
	    url: "/spiderRunner/runSpiderAndSend",  
	    data: JSON.stringify(para),//将对象序列化成JSON字符串  
	    dataType:"text",  
	    contentType : 'application/json;charset=utf-8', //设置请求头信息  
	    success: function(data)
	    {  
	        if(data=="0")
	        	alert("任务启动成功");
	        else
	        	alert("任务启动失败");
	    } 
	 });
}

$(function()
{
	$(".bl").find("input:eq(1)").val(email);
	
	$("#mbBtn").click(microBlog);
	$("#wbBtn").click(weiboFans);
	$("#cmBtn").click(doubanComment);
});