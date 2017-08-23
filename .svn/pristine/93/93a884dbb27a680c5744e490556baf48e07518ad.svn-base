var subjectRankCharts;
var subjectMarketPosCharts;
var subjectCityCharts;
var peopleSpreadCharts;

$(function () {
	
	//初始化图标控件
	subjectRankCharts = echarts.init($("#subjectRank")[0], "wonderland");
	subjectMarketPosCharts = echarts.init($("#subjectMarketPos")[0], "wonderland");
	subjectCityCharts = echarts.init($("#subjectCity")[0], "wonderland");
	peopleSpreadCharts = echarts.init($("#peopleSpread")[0], "wonderland");
	
});

//查询数据
function parentQueryData(params) {
	
	//加载题材排名
	loadSubjectRank(params);
	
	//加载题材市场定位
	loadSubjectMarketPos(params);
	
	//加载分城贡献
	loadChannelCity(params);

	//加载人群分布
	loadPeopleSpread(params);
}

//加载题材排名
function loadSubjectRank(params) {
	subjectRankCharts.showLoading();
	var url = "/subjectDataAnalyse/querySubjectRank";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		
		var subjectRank = response.data.subjectRank;
		
		var xData = [];
		var yData = [];
		
		$.each(subjectRank, function(index, item) {
			xData.push(item.subjectname + "(" + item.workcount + ")");
			yData.push(item.rate);
		});

		if (subjectRank.length > 0) {
			$("#subjectRank").removeClass("hidden");;
			$("#subjectRank").next("div").addClass("hidden");;
			
			option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {
			            type : 'shadow'
			        },
			        extraCssText: 'text-align: left;'
			    },
			    grid: {
			        left: '3%',
			        right: '3%',
			        bottom: '5%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : xData,
			            axisLabel: {
			            	rotate : 20
			            }
			        }
			    ],
			    yAxis : [
			        {
			        	name: "收视率(%)",
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			        	name: "收视率(%)",
			            type:'bar',
			            label: {
			            	normal: {
			            		show: true,
			            		position: "top",
			            		formatter: '{c}'
			            	}
			            },
			            data: yData
			        }
			    ]
			};
			subjectRankCharts.setOption(option);
			subjectRankCharts.hideLoading();
		} else {
			$("#subjectRank").addClass("hidden");
			$("#subjectRank").next("div").removeClass("hidden");
		}
	};
	doPost(url, params, successFn);
}

//加载题材市场定位
function loadSubjectMarketPos(params) {
	subjectMarketPosCharts.showLoading();
	
	var url = "/subjectDataAnalyse/querySubjectMarketPos";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		var marketPos = response.data.marketPos;
		
		var totalPage = 0;
		var totalRate000 = 0;
//		var myChannelName = "";
		$.each(marketPos, function(index, item) {
			totalPage = add(totalPage, item.totalpage);
			totalRate000 = add(totalRate000, item.rate000);
			
//			if (item.ichannelid == params.channelId) {
//				myChannelName = item.channelname;
//			}
		});
		
		var avgPage = divide(totalPage, marketPos.length);
		var avgRate000 = divide(totalRate000, marketPos.length);
		
		
		var importantMarketData = [];
		var possibleMarketData = [];
		var saturatedMarketData = [];
		var borderMarketData = [];
		$.each(marketPos, function(index, item) {
			var rate000 = item.rate000;
			var totalpage = item.totalpage;
			var subjectname = item.subjectname;
			
			if (rate000 >= avgRate000 && totalpage >= avgPage) {
				importantMarketData.push([totalpage, rate000, subjectname]);
			}
			if (rate000 >= avgRate000 && totalpage < avgPage) {
				possibleMarketData.push([totalpage, rate000, subjectname]);
			}
			if (rate000 < avgRate000 && totalpage < avgPage) {
				saturatedMarketData.push([totalpage, rate000, subjectname]);
			}
			if (rate000 < avgRate000 && totalpage >= avgPage) {
				borderMarketData.push([totalpage, rate000, subjectname]);
			}
		});
		
		if (marketPos.length > 0) {
			$("#subjectMarketPos").removeClass("hidden");
			$("#subjectMarketPos").next("div").addClass("hidden");
			
			option = {
//			    title : {
//			        text: myChannelName,
//			        left: "center"
//			    },
			    grid: {
			        left: '3%',
			        right: '5%',
			        bottom: '10%',
			        containLabel: true
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: function (params) {
			        	return params.data[2] + " " + params.seriesName + "<br/>总集数：" + params.data[0] + "<br/>千人收视：" + params.data[1].toFixed(3);
			        },
			        axisPointer : {
			            type : 'shadow'
			        },
			        extraCssText: 'text-align: left;'
			    },
			    legend: {
			        data: ['重点市场', '潜力市场', '饱和市场', '边缘市场'],
			        left: 'center',
			        top: 'bottom',
			        itemWidth: 14
			    },
			    xAxis : [
			        {
			        	name: "总集数",
			            type : 'value',
			            splitLine: {
			                show: false
			            }
			        }
			    ],
			    yAxis : [
			        {
			        	name: "平均收视率(000)",
			            type : 'value',
			            scale:true,
			            splitLine: {
			                show: false
			            }
			        }
			    ],
			    series : [
			        {
			            name:'重点市场',
			            type:"scatter",
			            symbol: "circle",
			            data: importantMarketData,
			            markLine : {
		                	silent: true,
			                lineStyle: {
			                    normal: {
			                        type: 'dashed'
			                    }
			                },
			                data : [
			                    {yAxis: avgRate000},
			                    {xAxis: avgPage}
			                ]
			            }
			        },
			        {
			            name:'潜力市场',
			            type:"scatter",
			            symbol: "rect",
			            data: possibleMarketData,
			            markLine : {
		                	silent: true,
			                lineStyle: {
			                    normal: {
			                        type: 'dashed'
			                    }
			                },
			                data : [
			                    {yAxis: avgRate000},
			                    {xAxis: avgPage}
			                ]
			            }
			        },
			        {
			            name:'饱和市场',
			            type:"scatter",
			            symbol: "triangle",
			            data: saturatedMarketData,
			            markLine : {
		                	silent: true,
			                lineStyle: {
			                    normal: {
			                        type: 'dashed'
			                    }
			                },
			                data : [
			                    {yAxis: avgRate000},
			                    {xAxis: avgPage}
			                ]
			            }
			        },
			        {
			            name:'边缘市场',
			            type:"scatter",
			            symbol: "diamond",
			            data: borderMarketData,
			            markLine : {
		                	silent: true,
			                lineStyle: {
			                    normal: {
			                        type: 'dashed'
			                    }
			                },
			                data : [
			                    {yAxis: avgRate000},
			                    {xAxis: avgPage}
			                ]
			            }
			        }
			    ]
			};

			subjectMarketPosCharts.setOption(option);
			subjectMarketPosCharts.hideLoading();
		} else {
			$("#subjectMarketPos").addClass("hidden");
			$("#subjectMarketPos").next("div").removeClass("hidden");
		}
		
		
	};
	doPost(url, params, successFn);
}

//加载分城贡献
function loadChannelCity(params) {
	subjectCityCharts.showLoading();
	
	var url = "/subjectDataAnalyse/querySubjectCity";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		
		var xData = [];
		var yData = [];
//		var myChannelName = "";
		var subjectCity = response.data.subjectCity;
		$.each(subjectCity, function(index, item) {
			var areaName = item.areaname;
			var favor = multiply(item.favor, 100);
			
			xData.push(areaName);
			yData.push(favor);
			
//			if (item.ichannelid == params.channelId) {
//				myChannelName = item.channelname;
//			}
		});
		
		if (subjectCity.length > 0) {
			$("#subjectCity").removeClass("hidden");
			$("#subjectCity").next("div").addClass("hidden");
			
			option = {
//				title : {
//			        text: "",
//			        left: "center"
//			    },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {
			            type : 'shadow'
			        },
			        extraCssText: 'text-align: left;'
			    },
			    grid: {
			        left: '3%',
			        right: '3%',
			        bottom: '5%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data: xData,
			            axisLabel: {
			            	rotate : 20
			            }
			        }
			    ],
			    yAxis : [
			        {
			        	name: "偏好(%)",
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			        	name: "偏好(%)",
			            type:'bar',
			            label: {
			            	normal: {
			            		show: true,
			            		position: "top",
			            		formatter: '{c}'
			            	}
			            },
			            data: yData,
			            markLine : {
			            	silent: true,
			                lineStyle: {
			                    normal: {
			                        type: 'dashed'
			                    }
			                },
			                data : [
			                    {
			                    	name: 'Y 轴值为 100 的水平线',
			                        yAxis: 100
			                    }
			                ]
			            }
			        }
			    ]
			};
				
			subjectCityCharts.setOption(option);
			subjectCityCharts.hideLoading();
		} else {
			$("#subjectCity").addClass("hidden");
			$("#subjectCity").next("div").removeClass("hidden");
		}
	};
	
	doPost(url, params, successFn);
}

//加载人群分布
function loadPeopleSpread(params) {
	peopleSpreadCharts.showLoading();
	
	var url = "/subjectDataAnalyse/querySubjectPeopleSpread";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		
		var ageSpread = response.data.ageSpread;
		var earnSpread = response.data.earnSpread;
		var eduSpread = response.data.eduSpread;
		
		//年龄
		var maxAgeFavor = 0;
		var ageFavorArray = [];
		$.each(ageSpread, function(index, item) {
			ageFavorArray.push(multiply(item.favor, 100));
			if (maxAgeFavor < item.favor) {
				maxAgeFavor = item.favor;
			}
		});
		maxAgeFavor = multiply(maxAgeFavor, 150);
		
		//收入
		var maxEarnFavor = 0;
		var earnFavorArray = [];
		$.each(earnSpread, function(index, item) {
			earnFavorArray.push(multiply(item.favor, 100));
			if (maxEarnFavor < item.favor) {
				maxEarnFavor = item.favor;
			}
		});
		maxEarnFavor = multiply(maxEarnFavor, 150);
		
		//教育水平
		var maxEduFavor = 0;
		var eduFavorArray = [];
		$.each(eduSpread, function(index, item) {
			eduFavorArray.push(multiply(item.favor, 100));
			if (maxEduFavor < item.favor) {
				maxEduFavor = item.favor;
			}
		});
		maxEduFavor = multiply(maxEduFavor, 150);
		
		if (ageSpread.length > 0 || earnSpread.length > 0 || eduSpread.length > 0) {
			$("#peopleSpread").removeClass("hidden");
			$("#peopleSpread").next("div").addClass("hidden");
			
			option = {
				title: {
					text: "年龄、收入、教育水平",
					left: "center"
				},
				tooltip: {
			        extraCssText: 'text-align: left;'
				},
			    radar: [
			        {
			        	indicator: [
		                    { text: '4-14', max: maxAgeFavor},
							{ text: '15-24', max: maxAgeFavor},
							{ text: '25-34', max: maxAgeFavor},
							{ text: '35-44', max: maxAgeFavor},
							{ text: '45-54', max: maxAgeFavor},
							{ text: '55-64', max: maxAgeFavor},
							{ text: '64+', max: maxAgeFavor}
		                ],
		                center: ['16%','50%'],
		                radius: "60%"
			        },
			        {
			        	indicator: [
		                    { text: '0-2000', max: maxEarnFavor},
							{ text: '2000-4000', max: maxEarnFavor},
							{ text: '4000-6000', max: maxEarnFavor},
							{ text: '6000+', max: maxEarnFavor}
		                ],
		                center: ['50%','50%'],
		                radius: "60%"
			        },
			        {
			        	indicator: [
		                    { text: '受过正规教育', max: maxEduFavor},
							{ text: '小学', max: maxEduFavor},
							{ text: '初中', max: maxEduFavor},
							{ text: '高中', max: maxEduFavor},
							{ text: '大学及以上', max: maxEduFavor},
		                ],
		                center: ['83%','50%'],
		                radius: "60%"
			        }
			    ],
			    series: [
			    {
			        type: 'radar',
			        data : [
			            {
			            	name: "年龄分布（偏好%）",
			                value : ageFavorArray,
			                symbolSize: 5
			            }
			        ]
			    },
			    {
			    	type: 'radar',
		            radarIndex: 1,
		            data: [
		                {
			            	name: "收入分布（偏好%）",
		                    value: earnFavorArray,
			                symbolSize: 5
		                }
		            ]
			    },
			    {
			    	type: 'radar',
		            radarIndex: 2,
		            data: [
		                {
			            	name: "教育水平分布（偏好%）",
		                    value: eduFavorArray,
			                symbolSize: 5
		                }
		            ]
			    }]
			};
			peopleSpreadCharts.setOption(option);
			peopleSpreadCharts.hideLoading();
		} else {
			$("#peopleSpread").addClass("hidden");
			$("#peopleSpread").next("div").removeClass("hidden");
		}
	};
	
	doPost(url, params, successFn);
	
}