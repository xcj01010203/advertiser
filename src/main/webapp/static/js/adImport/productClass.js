$(function () {
    // 全部集数的收起展示
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"));
    
    //加载品类列表
    loadGoodsList();
    
    //加载品类的广告信息
    loadGoodsImplant();
    
});

//在指定元素中绘制饼图
function rederPie(id, data) {
	option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b}<br/>场数：{c}<br/>占比：{d}%"
	    },
	    series : [
	        {
	            type: 'pie',
	            data: data,
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(id), "wonderland");
	myChart.setOption(option);
	myChart.on("click", function(params) {
		showRoleViewList(params.data.doms);
	});
}

//渲染环图
function renderRing(dom, rate) {
	var mainValue = multiply(rate, 100);
	var leftValue = multiply((1 - rate), 100);
	
	option = {
	    series: [
	        {
	            type: "pie",
	            radius: ["65%", "90%"],
	            avoidLabelOverlap: false,
	            hoverAnimation: false,
	            silent: true,
	            data:[
	                {
	                	value: leftValue, 
	                	label: {
	                		normal: {
	                			show: false,
	                			position: "center"
	                		}
	                	},
	                	itemStyle: {
	                		normal: {
	                			color: "#ddd"
	                		}
	                	}
	                },
	                {
	                	value: mainValue, 
	                	label: {
	                		normal: {
	                			show: true,
	                			position: "center",
	                			formatter: mainValue + "%",
		                		textStyle: {
		                			color: "#29d9c2",
		                			fontFamily: "宋体",
		                			fontSize: 13
		                		}
	                		}
	                	},
	                	itemStyle: {
	                		normal: {
	                			color: "#29d9c2"
	                		}
	                	}
	                }
	            ]
	        }
	    ]
	};
	
	var myChart = echarts.init(dom, "wonderland");
	myChart.setOption(option);
}

//加载品类列表
function loadGoodsList() {
	var url = "/goods/queryGoodsList";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		
		var goodsList = response.data.goodsList;
		var goodsLiArray = [];
		$.each(goodsList, function(index, item) {
			goodsLiArray.push("<li id='"+ item.id +"' onclick='clickGoods(this)'><span>"+ item.goods +"</span></li>");
		});
		
		$("#goodsListUl").html(goodsLiArray.join(""));
		
	};
	doPost(url, {}, successFn);
}

//品类点击事件
function clickGoods(own) {
	if ($(own).hasClass("active")) {
		$(own).find("i").remove();
	} else {
		$(own).append("<i class='icon iconfont'>&#xe600;</i>");
	}
	$(own).toggleClass("active");
}

//加载品类的广告信息
function loadGoodsImplant() {
	var url = "/implantAnalyse/queryGoodsImplant";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		
		var goodsList = response.data.goodsList;
		
		var pieData = [];
		var goodsLiArray = [];
		$.each(goodsList, function(index, item) {
			var singleData = {};
			singleData.name = item.goods;
			singleData.value = item.roundCount;
			singleData.doms ="<button class='story-card-con-btn' idList=\'"+ JSON.stringify(item.idList) +"\' onclick='showRoleViewList(this)'>查看详情</button>";
			pieData.push(singleData);
			if ((index + 1) % 4 == 0) {
				goodsLiArray.push("<li class='story-li story-li-mar'>");
			} else {
				goodsLiArray.push("<li class='story-li'>");
			}
			goodsLiArray.push("    <ul class='story-card-con'>");
			goodsLiArray.push("        <li>占比："+ multiply(item.rate, 100) +"%</li>");
			goodsLiArray.push("        <li>"+ item.goods +"</li>");
			goodsLiArray.push("        <li><div></div></li>");
			goodsLiArray.push("        <li>场数："+ item.roundCount +"</li>");
			goodsLiArray.push("        <li><button class='story-card-con-btn' idList=\'"+ JSON.stringify(item.idList) +"\' onclick='showRoleViewList(this)'>查看详情</button></li>");
			goodsLiArray.push("    </ul>");
			goodsLiArray.push("</li>");
		});
		//饼图
		rederPie("panelPie", pieData);
		
		//品类列表
		$("#goodsCardListUl").html(goodsLiArray.join(""));
	};
	
	var selectedGoods = $("#goodsListUl").find("li.active");
	var goodsIdList = [];
	if (selectedGoods) {
		$.each(selectedGoods, function(index, item) {
			goodsIdList.push($(item).attr("id"));
		});
	}
	
	doPost(url, {goodsIdList: goodsIdList}, successFn);
}

//显示角色场景表
function showRoleViewList(own) {
	var idList = $(own).attr("idList");
	$("#goodsRoundListPage").createPage({
		url: "/implantAnalyse/queryRoundGoodsImplant",
		pageSize: 5,
		data: {goodsIdList: JSON.parse(idList)},
		successFn: function(response) {
			if (response.status == 1) {
				alert(response.message);
				return;
			}
			
			var positionJson = {};
			positionJson["0"] = "台词";
			positionJson["1"] = "地点";
			positionJson["2"] = "台词+地点";
			
			var roundList = response.data.roundList;
			var roundTrArray = [];
			for (var key in roundList) {
				var viewList = roundList[key];
				
				$.each(viewList, function(index, item) {
					roundTrArray.push("			<tr>");
					roundTrArray.push("				<td>"+ item.seriesNo + "-" + item.roundNo +"</td>");
					roundTrArray.push("				<td class='over-hide'>"+ filterNull(item.atmosphere) +"</td>");
					roundTrArray.push("				<td class='over-hide'>"+ filterNull(item.site) +"</td>");
					roundTrArray.push("				<td class='over-hide' title='"+ filterNull(item.firstLocation) +"'>"+ filterNull(item.firstLocation) +"</td>");
					roundTrArray.push("				<td class='over-hide' title='"+ filterNull(item.majorRoleNameList.join("|")) +"'>"+ filterNull(item.majorRoleNameList.join("|")) +"</td>");
					roundTrArray.push("				<td class='over-hide' title='"+ filterNull(item.goods) +"'>"+ filterNull(item.goods) +"</td>");
					roundTrArray.push("				<td class='over-hide'>"+ positionJson[item.position] +"</td>");
					roundTrArray.push("				<td class='over-hide'><div class='pie-div' rate='"+ item.weight +"'></div></td>");
					roundTrArray.push("			</tr>");
				});
			}
			
			$("#roundListTbody").html(roundTrArray.join(""));
			
			$('#roundListDiv').modal('show');
			
			//绘制环形图
			// $.each($(".pie-div"), function(index, item) {
			// 	renderRing($(item)[0], $(item).attr("rate"));
			// });

            //绘制百分比
            $.each($(".pie-div"), function (index, value) {
                percentageNum(value, $(value).attr("rate"))
            });
		}
	});
}

// 百分比
function percentageNum(dom, num) {
    numColor = parseInt(130 * num);
    num = parseInt(num * 100);
    var html = '';
    html += '<div class="percentage-box-con"><div class="percentage-box"><div class="percentage-box-color"></div></div>';
    html += '<span class="percentage-box-num"></span></div>';
    $(dom).html(html);
    $(dom).find(".percentage-box-con .percentage-box .percentage-box-color").css("width", numColor);
    $(dom).find(".percentage-box-con .percentage-box-num").html(num + "%");
}