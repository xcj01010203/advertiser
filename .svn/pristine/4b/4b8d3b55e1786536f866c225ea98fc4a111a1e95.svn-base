$(function() {
	// 全部集数的收起展示
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"));
    
    //加载品类列表
    loadGoodsList();
    
    //加载按角色分类的广告统计信息
    loadRoleImplant();
});

//在指定元素中绘制饼图
function rederPie(dom, data) {
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
	
	var myChart = echarts.init(dom, "wonderland");
	myChart.setOption(option);
	myChart.on("click", function(params) {
		showRoundList(params.data.roleId, params.data.roleName, params.data.id, params.data.name);
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

//显示场次列表弹窗
function showRoundList(roleId, roleName, goodsId, goodsName) {
	$("#roundListPage").createPage({
		url: "/implantAnalyse/queryRoundGoodsImplant",
		pageSize: 5,
		data: {goodsIdList: [goodsId], roleId: roleId},
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
					roundTrArray.push("				<td width='5%'>"+ item.seriesNo + "-" + item.roundNo +"</td>");
					roundTrArray.push("				<td width='5%' class='over-hide'>"+ filterNull(item.atmosphere) +"</td>");
					roundTrArray.push("				<td width='5%' class='over-hide'>"+ filterNull(item.site) +"</td>");
					roundTrArray.push("				<td width='20%' class='over-hide' title='"+ filterNull(item.firstLocation) +"'>"+ filterNull(item.firstLocation) +"</td>");
					roundTrArray.push("				<td width='20%' class='over-hide' title='"+ filterNull(item.majorRoleNameList.join("|")) +"'>"+ filterNull(item.majorRoleNameList.join("|")) +"</td>");
					roundTrArray.push("				<td width='20%' class='over-hide' title='"+ filterNull(item.goods) +"'>"+ filterNull(item.goods) +"</td>");
					roundTrArray.push("				<td width='5%' class='over-hide'>"+ positionJson[item.position] +"</td>");
					roundTrArray.push("				<td width='20%' class='over-hide'><div class='pie-div' rate='"+ item.weight +"'></div></td>");
					roundTrArray.push("			</tr>");
				});
			}
			
			$("#roundListTbody").html(roundTrArray.join(""));
			
			$('#roundListDiv').modal('show');
			$("#roundTitle").text(roleName + "-" + goodsName);
			
			//绘制环形图
			$.each($(".pie-div"), function(index, item) {
				renderRing($(item)[0], $(item).attr("rate"));
			});
		}
	});
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

//加载按角色分类的广告统计信息
function loadRoleImplant() {
	var selectedGoods = $("#goodsListUl").find("li.active");
	var goodsIdList = [];
	if (selectedGoods) {
		$.each(selectedGoods, function(index, item) {
			goodsIdList.push($(item).attr("id"));
		});
	}
	
	$("#tcdPageCode").createPage({
		url: "/implantAnalyse/queryRoleImplant",
		data: {goodsIdList: goodsIdList},
		pageSize: 5,
		successFn: function(response) {
			if (response.status == 1) {
				alert(response.message);
				return;
			}
			
			var roleList = response.data.roleList;
			
			var roleDivArray = [];
			$.each(roleList, function(index, item) {
				var name = item.name;
				var goodsList = item.goodsImplantList;
				var totalRoundCount = item.totalRoundCount;
				var pieData = [];
				$.each(goodsList, function(index, goodsItem) {
					var singleData = {};
					singleData.name = goodsItem.goods;
					singleData.value = goodsItem.roundCount;
					singleData.id = goodsItem.id;
					singleData.roleId = item.id;
					singleData.roleName = item.name;
					
					pieData.push(singleData);
				});
				console.log(index)
				if (index%2 == 0) {
                    roleDivArray.push("<div style='display: inline-block;width: 49%;margin-right: 1%;' class='panel panel-default'>");
				}else if (index%2 == 1) {
                    roleDivArray.push("<div style='display: inline-block;width: 49%;margin-left: 1%;' class='panel panel-default'>");
				}
				roleDivArray.push("	<div class='panel-heading'>");
				roleDivArray.push("		<h4><div class='dobule-bias'><span class='one'></span><span class='two'></span></div>"+ name + "(" + totalRoundCount + ")——产品分类</h4>");
				roleDivArray.push("	</div>");
				roleDivArray.push("	<div class='panel-body panel-pie' pieData = '"+ JSON.stringify(pieData) +"'>");
				roleDivArray.push(" </div>");
				roleDivArray.push("</div>");
			});
			
			$("#roleListDiv").html(roleDivArray.join(""));
			
			$.each($(".panel-pie"), function(index, item) {
				var pieData = $(item).attr("pieData");
				
				rederPie($(item)[0], JSON.parse(pieData));
			});
		}
	});
	
	
	
	
	
	
//	var url = "/implantAnalyse/queryRoleImplant";
//	var successFn = function(response) {
//		if (response.status == 1) {
//			alert(response.message);
//			return;
//		}
//		
//		var roleList = response.data.roleList;
//		
//		var roleDivArray = [];
//		$.each(roleList, function(index, item) {
//			var name = item.name;
//			var goodsList = item.goodsImplantList;
//			var pieData = [];
//			$.each(goodsList, function(index, item) {
//				var singleData = {};
//				singleData.name = item.goods;
//				singleData.value = item.roundCount;
//				pieData.push(singleData);
//			});
//			
//			roleDivArray.push("<div class='panel panel-default'>");
//			roleDivArray.push("	<div class='panel-heading'>");
//			roleDivArray.push("		<h4><div class='dobule-bias'><span class='one'></span><span class='two'></span></div>"+ name +"——产品分类</h4>");
//			roleDivArray.push("	</div>");
//			roleDivArray.push("	<div class='panel-body panel-pie' pieData = '"+ JSON.stringify(pieData) +"'>");
//			roleDivArray.push(" </div>");
//			roleDivArray.push("</div>");
//		});
//		
//		$("#roleListDiv").html(roleDivArray.join(""));
//		
//		$.each($(".panel-pie"), function(index, item) {
//			var pieData = $(item).attr("pieData");
//			
//			rederPie($(item)[0], JSON.parse(pieData));
//		});
//	};
//	
//	var selectedGoods = $("#goodsListUl").find("li.active");
//	var goodsIdList = [];
//	if (selectedGoods) {
//		$.each(selectedGoods, function(index, item) {
//			goodsIdList.push($(item).attr("id"));
//		});
//	}
//	
//	doPost(url, {goodsIdList: goodsIdList}, successFn);
}