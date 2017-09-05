var channelRankCharts;
var channelSubjectMarketCharts;
var channelCityCharts;
var peopleSpreadCharts;

$(function () {
	
	//初始化图标控件
	channelRankCharts = echarts.init($("#channelRank")[0], "wonderland");
	channelSubjectMarketCharts = echarts.init($("#channelSubjectMarket")[0], "wonderland");
	channelCityCharts = echarts.init($("#channelCity")[0], "wonderland");
	peopleSpreadCharts = echarts.init($("#peopleSpread")[0], "wonderland");
});

//查询数据
function parentQueryData(params) {
	if (params.channelLevelList.length > 0 || params.channelIdList.length > 1) {
        modelWindow("只能选择一个频道")
		return;
	}
	
	params.channelId = params.channelIdList[0];
	
	//加载频道排名
	loadChannelRank(params);
	
	//加载题材分布
	loadChannelSubjectMarket(params);
	
	//加载分城贡献
	loadChannelCity(params);

	//加载人群分布
	loadPeopleSpread(params);
}

//加载频道排名
function loadChannelRank(params) {
	channelRankCharts.showLoading();
	
	var url = "/channelDataAnalyse/queryChannelRank";
	var successFn = function(response) {
		if (response.status == 1) {
            modelWindow(response.msssage)
			return;
		}
		
		var channelRank = response.data.channelRank;
		
		var xData = [];
		var yData = [];
		
		$.each(channelRank, function(index, item) {
			xData.push(item.label + "(" + item.index + ")");
			
			if (item.ichannelid == params.channelId) {
				yData.push({
					value: item.rate, 
					itemStyle: {
						normal: {color: "#7bd9a5"}
					}
				});
			} else {
				yData.push(item.rate);
			}
		});

		if (channelRank.length > 0) {
			$("#channelRank").removeClass("hidden");;
			$("#channelRank").next("div").addClass("hidden");;
			
			option = {
				title : {
			        text: params.channelNames,
			        left: "center"
			    },
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
			        bottom: '0',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : xData
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
			channelRankCharts.setOption(option);
			channelRankCharts.hideLoading();
		} else {
			$("#channelRank").addClass("hidden");
			$("#channelRank").next("div").removeClass("hidden");
		}
	};
	doPost(url, JSON.stringify(params), successFn, null, null, null, null, null, "application/json;charset=utf-8");
}

//加载频道题材市场
function loadChannelSubjectMarket(params) {
	channelSubjectMarketCharts.showLoading();
	
	var url = "/channelDataAnalyse/queryChannelSubjectMark";
	var successFn = function(response) {
		if (response.status == 1) {
            modelWindow(response.msssage)
			return;
		}
		var subjectMark = response.data.subjectMark;
		
		var totalPage = 0;
		var totalRate000 = 0;
		$.each(subjectMark, function(index, item) {
			totalPage = add(totalPage, item.totalpage);
			totalRate000 = add(totalRate000, item.rate000);
		});
		
		var avgPage = divide(totalPage, subjectMark.length);
		var avgRate000 = divide(totalRate000, subjectMark.length);
		
		
		var importantMarketData = [];
		var possibleMarketData = [];
		var saturatedMarketData = [];
		var borderMarketData = [];
		$.each(subjectMark, function(index, item) {
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
		
		if (subjectMark.length > 0) {
			$("#channelSubjectMarket").removeClass("hidden");
			$("#channelSubjectMarket").next("div").addClass("hidden");
			
			option = {
			    title : {
			        text: params.channelNames,
			        left: "center"
			    },
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
			            label: {
			            	normal: {
			            		show: true,
				            	position: "top",
				            	formatter: function(params) {
				            		return params.data[2];
				            	}
			            	}
			            },
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
			            label: {
			            	normal: {
			            		show: true,
				            	position: "top",
				            	formatter: function(params) {
				            		return params.data[2];
				            	}
			            	}
			            },
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
			            label: {
			            	normal: {
			            		show: true,
				            	position: "top",
				            	formatter: function(params) {
				            		return params.data[2];
				            	}
			            	}
			            },
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
			            label: {
			            	normal: {
			            		show: true,
				            	position: "top",
				            	formatter: function(params) {
				            		return params.data[2];
				            	}
			            	}
			            },
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

			channelSubjectMarketCharts.setOption(option);
			channelSubjectMarketCharts.hideLoading();
		} else {
			$("#channelSubjectMarket").addClass("hidden");
			$("#channelSubjectMarket").next("div").removeClass("hidden");
		}
		
		
	};
	doPost(url, JSON.stringify(params), successFn, null, null, null, null, null, "application/json;charset=utf-8");
}

//加载分城贡献
function loadChannelCity(params) {
	channelCityCharts.hideLoading();
	var url = "/channelDataAnalyse/queryChannelCity";
	var successFn = function(response) {
		if (response.status == 1) {
            modelWindow(response.msssage)
			return;
		}
		
		var xData = [];
		var yData = [];
		var channelCity = response.data.channelCity;
		$.each(channelCity, function(index, item) {
			var areaName = item.areaname;
			var favor = multiply(item.favor, 100);
			
			xData.push(areaName);
			yData.push(favor);
			
		});
		
		if (channelCity.length > 0) {
			$("#channelCity").removeClass("hidden");
			$("#channelCity").next("div").addClass("hidden");
			
			option = {
				title : {
			        text: params.channelNames,
			        left: "center"
			    },
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
				
			channelCityCharts.setOption(option);
			channelCityCharts.hideLoading();
		} else {
			$("#channelCity").addClass("hidden");
			$("#channelCity").next("div").removeClass("hidden");
		}
	};
	
	doPost(url, JSON.stringify(params), successFn, null, null, null, null, null, "application/json;charset=utf-8");
}

//加载人群分布
function loadPeopleSpread(params) {
	peopleSpreadCharts.showLoading();
	
	var url = "/channelDataAnalyse/queryChannelPeopleSpread";
	var successFn = function(response) {
		if (response.status == 1) {
            modelWindow(response.msssage)
			return;
		}
		
		var ageSpread = response.data.ageSpread;
		var earnSpread = response.data.earnSpread;
		var eduSpread = response.data.eduSpread;
		
		console.log(ageSpread);
		
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
					text: params.channelNames + "-人群分布（年龄、收入、教育水平）",
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
	
	doPost(url, JSON.stringify(params), successFn, null, null, null, null, null, "application/json;charset=utf-8");
	
}