$(function () {

    // 全部集数的收起展示
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"));

    circularHalfRadio($(".ceshi"), $(".ceshi").attr("rate") || 80);

    //加载集次列表
    loadSeriesNoList();
});

//加载集次列表
function loadSeriesNoList() {
    var url = "/playRound/querySeriesNoList";
    var successFn = function (response) {
        if (response.status == 1) {
            modelWindow(response.message, 0);
            return;
        }

        var seriesNoList = response.data.seriesNoList;
        var seriesNoLiArray = [];
        $.each(seriesNoList, function (index, item) {
            if (index == 0) {
                seriesNoLiArray.push("<li class='active' seriesNo=" + item + " onclick='clickSeriesNo(this)'>");
                seriesNoLiArray.push("<i class='icon iconfont'>&#xe600;</i>");
            } else {
                seriesNoLiArray.push("<li seriesNo=" + item + " onclick='clickSeriesNo(this)'>");
            }

            seriesNoLiArray.push("	<span>第" + item + "集</span>");
            seriesNoLiArray.push("</li>");

        });

        $("#seriesNoUl").html(seriesNoLiArray.join(''));

        loadViewList();
    };
    doPost(url, {}, successFn);
}

//集次点击事件
function clickSeriesNo(own) {
    $(own).siblings("li").removeClass("active");
    if ($(own).hasClass("active")) {
        $(own).find("i").remove();
    } else {
        $(own).append("<i class='icon iconfont'>&#xe600;</i>");
    }
    $(own).toggleClass("active");

    loadViewList();
}

//加载场次列表
function loadViewList() {
    var seriesNo = $("#seriesNoUl").find("li.active").attr("seriesNo");

    $("#tcdPageCode").createPage({
        url: "/implantAnalyse/queryRoundGoodsImplant",
        data: {seriesNo: seriesNo},
        pageSize: 10,
        successFn: function (response) {
            if (response.status == 1) {
                modelWindow(response.msssage)
                return;
            }

//            var positionJson = {};
//            positionJson["0"] = "台词";
//            positionJson["1"] = "地点";
//            positionJson["2"] = "台词+地点";

            var roundList = response.data.roundList;
            var viewTrArray = [];
            $("#seriesTitle").text("第" + seriesNo + "集");
            $.each(roundList, function (index, item) {
                viewTrArray.push("			<tr>");
                viewTrArray.push("				<td><a class='text-primary' roundId=\"" + item.id + "\" href='javascript:void(0)' onclick='showViewDetail(this)'>" + item.seriesNo + "-" + item.roundNo + "</a></td>");
                viewTrArray.push("				<td class='over-hide'>" + filterNull(item.atmosphere) + "</td>");
                viewTrArray.push("				<td class='over-hide'>" + filterNull(item.site) + "</td>");
                viewTrArray.push("				<td class='over-hide' title='" + filterNull(item.firstLocation) + "'>" + filterNull(item.firstLocation) + "</td>");
                viewTrArray.push("				<td class='over-hide' title='" + filterNull(item.majorRoleNameList.join("|")) + "'>" + filterNull(item.majorRoleNameList.join("|")) + "</td>");
                viewTrArray.push("				<td class='over-hide' title='" + filterNull(item.goods) + "'>" + filterNull(item.goods) + "</td>");
                viewTrArray.push("				<td width='26%' class='over-hide pie-td'></td>");
                viewTrArray.push("			</tr>");
            });

            $("#playContentList").html(viewTrArray.join(""));

            //导出角色分类列表
            $("#playCountListDiv .export-role-tab").click(function() {
            	var goodNames ="剧本内容";
                exportRoleTab(seriesNo,roundList,goodNames);
            })
            //绘制环形图
            // $.each($(".pie-td"), function(index, item) {
            // 	renderRing($(item)[0], $(item).attr("rate"));
            // });

            //绘制百分比
//            $.each($(".pie-td"), function (index, value) {
//                percentageNum(value, $(value).attr("rate"))
//            });
        }
    });

}

//导出角色分类列表
function exportRoleTab(seriesNo,roundList,roleName){
	if(roundList.length > 0){
		window.location.href=basePath+"/implantAnalyse/exportRoundGoodsImplant?seriesNo="+seriesNo+"&roleNames="+roleName;
	}else{
		modelWindow("没有可导出的数据!",0);
	}
}

//显示场次详情弹窗
function showViewDetail(own) {
    var roundId = $(own).attr("roundId");

    xiaotuObj.roundId = roundId;
    // 获取剧本内容信息
    getPlayContentInfo(roundId)

    //给对应的tr添加active标识
    $("#playContentList").find("tr").removeClass("active");
    $(own).parent("td").parent("tr").addClass("active");

    //打开弹窗
    $("#viewDetailDiv").modal("show");
    $("#seriesRoundNo").text($(own).text());

    // 重新刷一下剧本分析的内容
    refreshAnalyContent()
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
                data: [
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