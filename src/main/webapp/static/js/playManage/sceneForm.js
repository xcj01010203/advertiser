

$(function () {

    var xiaotuObj = {
        prevNum: 1,
        nextNum: 1,
        num: ''
    }

    //集次的展开和隐藏
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"));

    //加载集次列表
    loadSeriesNoList();

    // 高级搜索
    highSearch()

    // 查询函数
    selectFunc()

    // 清空函数
    emptyFunc()

    // 关闭高级搜索
    closeHighSearch()

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
            seriesNoLiArray.push("<span>第" + item + "集</span>");
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

//加载场景列表
function loadViewList() {
    var selectedSeriesNo = $("#seriesNoUl").find("li.active");
    var seriesNo = "";
    if (selectedSeriesNo) {
        $.each(selectedSeriesNo, function (index, item) {
        	seriesNo = $(item).attr("seriesNo");
        });
    }

    $("#tcdPageCode").createPage({
        url: "/playRound/queryRoundList",
        data: {seriesNo: seriesNo},
        contentType: 'application/json;charset=utf-8',
        pageSize: 20,
        successFn: function (response) {
            if (response.status == 1) {
                modelWindow(response.msssage)
                return;
            }

            var roundListTrArray = [];
            var roundList = response.data.roundList;

            $("#seriesTitle").text("第" + seriesNo + "集");

            $.each(roundList, function (index, item) {
                roundListTrArray.push("			<tr>");
                roundListTrArray.push("				<td width='5%'><a class='text-primary' roundId=\"" + item.id + "\" href='javascript:void(0)' onclick='showViewDetail(this)'>" + item.seriesNo + "-" + item.roundNo + "</a></td>");
                roundListTrArray.push("				<td width='5%' class='over-hide'>" + filterNull(item.atmosphere) + "</td>");
                roundListTrArray.push("				<td width='5%' class='over-hide'>" + filterNull(item.site) + "</td>");
                roundListTrArray.push("				<td width='15%' class='over-hide' title='" + filterNull(item.firstLocation) + "'>" + filterNull(item.firstLocation) + "</td>");
                roundListTrArray.push("				<td width='5%' class='over-hide'>" + filterNull(item.pageCount) + "</td>");
                roundListTrArray.push("				<td width='20%' class='over-hide' title='" + filterNull(item.majorRoleNameList.join("|")) + "'>" + filterNull(item.majorRoleNameList.join("|")) + "</td>");
                roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.guestRoleNameList.join("|")) + "'>" + filterNull(item.guestRoleNameList.join("|")) + "</td>");
                roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.massRoleNameList.join("|")) + "'>" + filterNull(item.massRoleNameList.join("|")) + "</td>");
                roundListTrArray.push("				<td width='20%' class='over-hide' title='" + filterNull(item.propNameList.join("|")) + "'>" + filterNull(item.propNameList.join("|")) + "</td>");
                roundListTrArray.push("				<td width='20%' class='over-hide'></td>");
                roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.remark) + "'>" + filterNull(item.remark) + "</td>");
                roundListTrArray.push("			</tr>");
            });
            $("#seriesViewListTbody").html(roundListTrArray.join(""));
            if (xiaotuObj.prevNum == 2) {
                // 如果为2或者3，说明是在模态窗点击的
                // 2 找当前集的上一页
                $("#seriesViewListTbody tr:last-child td:first-child a").click()
                xiaotuObj.prevNum = 1;
            } else if (xiaotuObj.prevNum == 3) {
                // 如果为2或者3，说明是在模态窗点击的
                // 3 找上一集的最后一页的最后一条数据
                if ($("#tcdPageCode a.weiye")[0]) {
                    $("#tcdPageCode a.weiye").click()
                    xiaotuObj.prevNum = 4
                } else {
                    $("#seriesViewListTbody tr:last-child td:first-child a").click()
                    xiaotuObj.prevNum = 1;
                }
            } else if (xiaotuObj.prevNum == 4) {
                $("#seriesViewListTbody tr:last-child td:first-child a").click()
                xiaotuObj.prevNum = 1;
            } else {
            }

            if (xiaotuObj.nextNum == 2) {
                // 如果为2或者3，说明是在模态窗点击的
                // 2 找当前集的下一页
                $("#seriesViewListTbody tr:first-child td:first-child a").click()
                xiaotuObj.nextNum = 1;
            } else if (xiaotuObj.nextNum == 3) {
                // 如果为2或者3，说明是在模态窗点击的
                // 3 找下一集的第一页的第一条数据
                $("#seriesViewListTbody tr:first-child td:first-child a").click()
                xiaotuObj.nextNum = 1;
            } else {
            }
        }
    });
}

//显示场次详情弹窗
function showViewDetail(own) {
    var roundId = $(own).attr("roundId");

    xiaotuObj.roundId = roundId;
    // 获取剧本内容信息
    getPlayContentInfo(roundId);

    //给对应的tr添加active标识
    $("#seriesViewListTbody").find("tr.active").removeClass("active");
    $(own).parent("td").parent("tr").addClass("active");

    //打开弹窗
    $("#viewDetailDiv").modal("show");
    $("#seriesRoundNo").text($(own).text());
}

//上一场
function preView() {
    //找到有active标识的tr
    var activeTr = $("#seriesViewListTbody").find("tr.active");
    var prevTr = activeTr.prev("tr");
    var activeGather = $(".project-list-five .five-content .five-content-gather li.active");
    var prevGather = activeGather.prev("li");

    if (prevTr[0]) {
        prevTr.find("td").eq(0).find("a").click();
    } else if ($("#tcdPageCode a.prevPage")[0]) {
        xiaotuObj.prevNum = 2;
        $("#tcdPageCode a.prevPage").click()
    } else if (prevGather[0]) {
        prevGather.click();
        xiaotuObj.prevNum = 3;
    } else {
        modelWindow("已经是第一场了")
        $("#myModal").css("zIndex", 2000)
    }

    $(".anal-content .anal-left .anal-color .anal-color-keyword").removeClass("active");
}

//下一场
function nextView() {
    //找到有active标识的tr
    var activeTr = $("#seriesViewListTbody").find("tr.active");
    var nextTr = activeTr.next("tr");
    var activeGather = $(".project-list-five .five-content .five-content-gather li.active");
    var nextGather = activeGather.next("li");

    if (nextTr[0]) {
        nextTr.find("td").eq(0).find("a").click();
    } else if ($("#tcdPageCode a.nextPage")[0]) {
        xiaotuObj.nextNum = 2;
        $("#tcdPageCode a.nextPage").click()
    } else if (nextGather[0]) {
        nextGather.click();
        xiaotuObj.nextNum = 3;
    } else {
        modelWindow("已经是最后一场了")
        $("#myModal").css("zIndex", 2000)
    }

    $(".anal-content .anal-left .anal-color .anal-color-keyword").removeClass("active");
}

// 高级搜索
function highSearch() {
    $(".project-list-four .four-content .four-right .high-search").click(function () {
        $(".shade").css({
            "backgroundColor": "rgba(0,0,0,0.5)",
            "opacity": "1",
            "transition": "all 0.15s ease-in",
            "zIndex": "100"
        })
        $(".shade .shade-con").css({
            "opacity": "1",
            "top": "50%",
            "marginTop": "-300px",
            "transition": "all 0.3s ease-in",
            "zIndex": "100"
        })

        // 获取列表
        getSceneList()
    })
}

// 获取列表
function getSceneList() {
    doPost(basePath + '/playRound/queryAllDropDownList', {}, function (data) {
        if (data.status == 0) {
            if (data.data) {
                var data = data.data;
                // 内外景
                var siteList = data.siteList, siteListHtml = '';
                for (var siteListI = 0; siteListI < siteList.length; siteListI++) {
                    siteListHtml += '<li>' + siteList[siteListI] + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .in-out .con-ul").html(siteListHtml);
                // 气氛
                var atmosphereList = data.atmosphereList, atmosphereListHtml = '';
                for (var atmosphereListI = 0; atmosphereListI < atmosphereList.length; atmosphereListI++) {
                    atmosphereListHtml += '<li>' + atmosphereList[atmosphereListI] + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .air .con-ul").html(atmosphereListHtml);
                // 主场景
                var firstLocationList = data.firstLocationList, firstLocationListHtml = '';
                for (var firstLocationListI = 0; firstLocationListI < firstLocationList.length; firstLocationListI++) {
                    firstLocationListHtml += '<li>' + firstLocationList[firstLocationListI] + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .main-scene .con-ul").html(firstLocationListHtml);
                // 主要演员
                var majorRoleNameList = data.majorRoleNameList, majorRoleNameListHtml = '';
                for (var majorRoleNameListI = 0; majorRoleNameListI < majorRoleNameList.length; majorRoleNameListI++) {
                    majorRoleNameListHtml += '<li mulId="'+majorRoleNameList[majorRoleNameListI].id+'">' + majorRoleNameList[majorRoleNameListI].name + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .main-actor .con-ul").html(majorRoleNameListHtml);
                // 特约演员
                var guestRoleNameList = data.guestRoleNameList, guestRoleNameListHtml = '';
                for (var guestRoleNameListI = 0; guestRoleNameListI < guestRoleNameList.length; guestRoleNameListI++) {
                    guestRoleNameListHtml += '<li mulId="'+guestRoleNameList[guestRoleNameListI].id+'">' + guestRoleNameList[guestRoleNameListI].name + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .special-actor .con-ul").html(guestRoleNameListHtml);
                // 群众演员
                var massRoleNameList = data.massRoleNameList, massRoleNameListHtml = '';
                for (var massRoleNameListI = 0; massRoleNameListI < massRoleNameList.length; massRoleNameListI++) {
                    massRoleNameListHtml += '<li mulId="'+massRoleNameList[massRoleNameListI].id+'">' + massRoleNameList[massRoleNameListI].name + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .public-actor .con-ul").html(massRoleNameListHtml);
                // 道具
                var propNameList = data.propNameList, propNameListHtml = '';
                for (var propNameListI = 0; propNameListI < propNameList.length; propNameListI++) {
                    propNameListHtml += '<li mulId="'+propNameList[propNameListI].id+'">' + propNameList[propNameListI].name + '</li>';
                }
                $(".shade .shade-con .panel-body .panel-body-lr .stage .con-ul").html(propNameListHtml);

                // 点击input输入框时展示列表及关闭列表
                showHideList()

                // 获取列表之后，给每一项li添加事件
                getListAddClick()
            }
        }
    }, function (error) {
        console.log(error)
    })
}

// 点击input输入框时展示列表及关闭列表
function showHideList() {
    var conInput = $(".shade .shade-con .panel-body .panel-body-lr .scene-info-con .con-box .con-input input");
    var conUl = $(".shade .shade-con .panel-body .panel-body-lr .scene-info-con .con-ul");
    conInput.click(function () {
        conUl.hide(100)
        setTimeout(function () {
            $(this).parents(".con-box").siblings(".con-ul").show(200)
        }.bind(this), 120)
        return false;
    })

    $(".shade .shade-con").click(function (e) {
        if (!$(e.target).parents(".scene-info-con")[0]) {
            conUl.hide(100)
        }
    })
}

// 获取列表之后，给每一项li添加事件
function getListAddClick() {
    var li = $(".shade .shade-con .panel-body .panel-body-lr .scene-info-con .con-ul li");
    li.click(function () {
        var html = $(this).html(), mulId = $(this).attr("mulId"), flag = false;
        var conBox = $(this).parents(".con-ul").siblings(".con-box");
        var conName = conBox.find(".con-name"), conInput = conBox.find(".con-input");
        if (conName.length != 0) {
            conName.each(function (index, value) {
                var text = $(value).find("span").html();
                if (html == text) {
                    flag = true;
                    // 这里是跳出循环，相当于for循环中的break;
                    return false;
                }
            })
            if (flag) {
                modelWindow("请勿重复添加", 1000);
            } else {
                conInput.before('<div mulId="'+mulId+'" class="con-ni con-name"><span>' + html + '</span><i class="icon iconfont">&#xe61c;</i></div>');

                // 主要演员一个或者两个及两个以上的情况
                mainActorCheck()

                // 每一项的删除事件
                removeConName()
            }
        } else {
            conInput.before('<div mulId="'+mulId+'" class="con-ni con-name"><span>' + html + '</span><i class="icon iconfont">&#xe61c;</i></div>');

            // 主要演员一个或者两个及两个以上的情况
            mainActorCheck()

            // 每一项的删除事件
            removeConName()
        }
    })
}

// 主要演员一个或者两个及两个以上的情况
function mainActorCheck() {
    var mainActorDom = $(".shade .shade-con .panel-body .panel-body-lr .main-actor .con-box .con-name");
    var conMainActor = $(".shade .shade-con .panel-body .panel-body-lr .xuan-tian .con-main-actor");
    var $label = conMainActor.find("label");

    if (mainActorDom.length > 0) {
        if (mainActorDom.length == 1) {
            conMainActor.attr("flag", "show")
            conMainActor.find(".one").show(200)
            conMainActor.find(".two").hide(200)
            xiaotuObj.num = 1;
        } else if (mainActorDom.length >= 2) {
            conMainActor.attr("flag", "show")
            conMainActor.find("label").show(200)
        } else {}
    } else {
        conMainActor.attr("flag", "hide")
        conMainActor.find("label").hide(200)
    }

    if (conMainActor.attr("flag") == "show") {
        $label.click(function () {
            $label.each(function (index, value) {
                var item = $(value).find("input")
                if (item.is(":checked")) {
                    xiaotuObj.num = item.attr("num")
                    return false;
                }else {
                    xiaotuObj.num = ""
                }
            })
        })
    } else {
        $label.off("click")
        xiaotuObj.num = ""
    }
}

// 每一项的删除事件
function removeConName() {
    var i = $(".shade .shade-con .panel-body .panel-body-lr .xuan-tian .con-box .con-name i");
    i.off("click")
    i.click(function () {
        $(this).parents(".con-name").remove();

        // 主要演员一个或者两个及两个以上的情况
        mainActorCheck()

        var conName = $(".shade .shade-con .panel-body .panel-body-lr .xuan-tian .con-box .con-name");
        var input = $(".shade .shade-con .panel-body .panel-body-lr .main-actor-check input");
        if (conName.length == 1) {
            if (input.eq(0).is(":checked") || input.eq(1).is(":checked")) {
                return;
            }
            input.removeAttr("checked")
            input.eq(0).prop("checked", true)
        }
    })
}

// 查询函数
function selectFunc() {
    var panelBodyLr = $(".shade .shade-con .panel-body .panel-body-lr");
    // 集场区间
    var jiChangSectionOne = panelBodyLr.find(".ji-chang-section input:eq(0)");
    var jiChangSectionTwo = panelBodyLr.find(".ji-chang-section input:eq(1)");
    var jiChangSectionThree = panelBodyLr.find(".ji-chang-section input:eq(2)");
    var jiChangSectionFour = panelBodyLr.find(".ji-chang-section input:eq(3)");
    // 集场编号
    var jiChangNumber = panelBodyLr.find(".ji-chang-number input");


    $(".shade .shade-con .panel-foot .search-btn").click(function () {
        // 主要演员
        var mainActor = panelBodyLr.find(".main-actor .con-box .con-name"), mainActorArr = [];
        // 特约演员
        var specialActor = panelBodyLr.find(".special-actor .con-box .con-name"), specialActorArr = [];
        // 群众演员
        var publicActor = panelBodyLr.find(".public-actor .con-box .con-name"), publicActorArr = [];
        // 主场景
        var mainScene = panelBodyLr.find(".main-scene .con-box .con-name"), mainSceneArr = [];
        // 内外景
        var inOut = panelBodyLr.find(".in-out .con-box .con-name"), inOutArr = [];
        // 气氛
        var air = panelBodyLr.find(".air .con-box .con-name"), airArr = [];
        // 道具
        var stage = panelBodyLr.find(".stage .con-box .con-name"), stageArr = [];

        // 必须参数 如果没值 不发请求
        // 集场编号
        var jiChangNumberVal = jiChangNumber.val().trim();
        if (!checkString(jiChangNumberVal)) {
            modelWindow('请输入正确的集场编号', 1000);
            return;
        }

        // 非必须参数，如果值不对不发请求
        // 集场区间
        var jiChangSectionOneVal = jiChangSectionOne.val().trim();
        var jiChangSectionTwoVal = jiChangSectionTwo.val().trim();
        var jiChangSectionThreeVal = jiChangSectionThree.val().trim();
        var jiChangSectionFourVal = jiChangSectionFour.val().trim();
        // 开始集
        if (!isNumber(jiChangSectionOneVal)) {
            modelWindow('请输入正确的开始集数', 1000);
            return;
        }
        // 开始场
        if (!isNumber(jiChangSectionTwoVal)) {
            modelWindow('请输入正确的开始场数', 1000);
            return;
        }
        // 结束集
        if (!isNumber(jiChangSectionThreeVal)) {
            modelWindow('请输入正确的结束集数', 1000);
            return;
        }
        // 结束场
        if (!isNumber(jiChangSectionFourVal)) {
            modelWindow('请输入正确的结束场数', 1000);
            return;
        }

        // 主要演员
        domCreatArrId(mainActor, mainActorArr)
        // 特约演员
        domCreatArrId(specialActor, specialActorArr)
        // 群众演员
        domCreatArrId(publicActor, publicActorArr)
        // 道具
        domCreatArrId(stage, stageArr)
        // 主场景
        domCreatArrName(mainScene, mainSceneArr)
        // 内外景
        domCreatArrName(inOut, inOutArr)
        // 气氛
        domCreatArrName(air, airArr)

        jiChangSectionOneVal = jiChangSectionOneVal == '' ? '' : parseInt(jiChangSectionOneVal);
        jiChangSectionTwoVal = jiChangSectionTwoVal == '' ? '' : parseInt(jiChangSectionTwoVal);
        jiChangSectionThreeVal = jiChangSectionThreeVal == '' ? '' : parseInt(jiChangSectionThreeVal);
        jiChangSectionFourVal = jiChangSectionFourVal == '' ? '' : parseInt(jiChangSectionFourVal);


        var dataObj = {
            startSeriesNo: jiChangSectionOneVal,
            startRoundNo: jiChangSectionTwoVal,
            endSeriesNo: jiChangSectionThreeVal,
            endRoundNo: jiChangSectionFourVal,
            seriesRoundNos: jiChangNumberVal,
            // 气氛
            atmosphereList: airArr,
            // 内外景
            siteList: inOutArr,
            // 主场景
            firstLocationList: mainSceneArr,
            // 道具
            propIdList: stageArr,
            // 主要演员
            majorRoleIdList: mainActorArr,
            // 主演出现逻辑
            majorRoleSearchMode: Number(xiaotuObj.num),
            // 特约演员
            guestRoleIdList: specialActorArr,
            // 群众演员
            massRoleIdList: publicActorArr
        }

        $("#tcdPageCode").createPage({
            url: "/playRound/queryRoundList",
            data: dataObj,
            contentType: 'application/json;charset=utf-8',
            pageSize: 20,
            successFn: function (response) {
                if (response.status == 1) {
                    modelWindow(response.message);
                    return;
                }

                // 关闭高级搜索
                $(".shade .shade-con .panel-heading h4 i").click()

                // 移除集上的类
                $("#seriesNoUl").find("li.active").removeClass("active")

                var roundListTrArray = [];
                var roundList = response.data.roundList;

                // 清空集数
                $("#seriesTitle").html('检索结果');

                $.each(roundList, function (index, item) {
                    roundListTrArray.push("			<tr>");
                    roundListTrArray.push("				<td width='5%'><a class='text-primary' roundId=\"" + item.id + "\" href='javascript:void(0)' onclick='showViewDetail(this)'>" + item.seriesNo + "-" + item.roundNo + "</a></td>");
                    roundListTrArray.push("				<td width='5%' class='over-hide'>" + filterNull(item.atmosphere) + "</td>");
                    roundListTrArray.push("				<td width='5%' class='over-hide'>" + filterNull(item.site) + "</td>");
                    roundListTrArray.push("				<td width='15%' class='over-hide' title='" + filterNull(item.firstLocation) + "'>" + filterNull(item.firstLocation) + "</td>");
                    roundListTrArray.push("				<td width='20%' class='over-hide' title='" + filterNull(item.majorRoleNameList.join("|")) + "'>" + filterNull(item.majorRoleNameList.join("|")) + "</td>");
                    roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.guestRoleNameList.join("|")) + "'>" + filterNull(item.guestRoleNameList.join("|")) + "</td>");
                    roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.massRoleNameList.join("|")) + "'>" + filterNull(item.massRoleNameList.join("|")) + "</td>");
                    roundListTrArray.push("				<td width='20%' class='over-hide' title='" + filterNull(item.propNameList.join("|")) + "'>" + filterNull(item.propNameList.join("|")) + "</td>");
                    roundListTrArray.push("				<td width='10%' class='over-hide' title='" + filterNull(item.remark) + "'>" + filterNull(item.remark) + "</td>");
                    roundListTrArray.push("			</tr>");
                });
                $("#seriesViewListTbody").html(roundListTrArray.join(""));
                if (xiaotuObj.prevNum == 2) {
                    // 如果为2或者3，说明是在模态窗点击的
                    // 2 找当前集的上一页
                    $("#seriesViewListTbody tr:last-child td:first-child a").click()
                    xiaotuObj.prevNum = 1;
                } else if (xiaotuObj.prevNum == 3) {
                    // 如果为2或者3，说明是在模态窗点击的
                    // 3 找上一集的最后一页的最后一条数据
                    if ($("#tcdPageCode a.weiye")[0]) {
                        $("#tcdPageCode a.weiye").click()
                        xiaotuObj.prevNum = 4
                    } else {
                        $("#seriesViewListTbody tr:last-child td:first-child a").click()
                        xiaotuObj.prevNum = 1;
                    }
                } else if (xiaotuObj.prevNum == 4) {
                    $("#seriesViewListTbody tr:last-child td:first-child a").click()
                    xiaotuObj.prevNum = 1;
                } else {
                }

                if (xiaotuObj.nextNum == 2) {
                    // 如果为2或者3，说明是在模态窗点击的
                    // 2 找当前集的下一页
                    $("#seriesViewListTbody tr:first-child td:first-child a").click()
                    xiaotuObj.nextNum = 1;
                } else if (xiaotuObj.nextNum == 3) {
                    // 如果为2或者3，说明是在模态窗点击的
                    // 3 找下一集的第一页的第一条数据
                    $("#seriesViewListTbody tr:first-child td:first-child a").click()
                    xiaotuObj.nextNum = 1;
                } else {
                }
            }
        });
    })
}

// 校验集场区间
function isNumber(str) {
    if (str == '') {
        return true;
    } else if (typeof(str) == 'string') {
        str = Number(str)
        return (str | 0) === str;
    } else {
        return false;
    }
}

// 校验集场编号
function checkString(str) {
    if (typeof(str) == 'string') {
        if (str == '') {
            return true;
        } else if (str.indexOf(",") == -1) {
            if (str.indexOf("-") == -1) {
                return false;
            } else {
                var arr = str.split("-")
                if (arr[0] != '' && arr[1] != '') {
                    arr[0] = Number(arr[0])
                    arr[1] = Number(arr[1])
                    return ((arr[0] | 0) === arr[0] && (arr[1] | 0) === arr[1])
                } else {
                    return false;
                }
            }
        } else {
            var arrList = str.split(",");
            for (var i = 0; i < arrList.length; i++) {
                if (arrList[i] == "") {
                    continue;
                }
                var item = arrList[i].split("-");
                if (item[0] != '' && item[1] != '') {
                    item[0] = Number(item[0])
                    item[1] = Number(item[1])
                    if ((item[0] | 0) === item[0] && (item[1] | 0) === item[1]) {

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    } else {
        return false;
    }
}

// dom循环生成数组Id
function domCreatArrId(dom, arr) {
    dom.each(function (index, value) {
        arr.push($(value).attr("mulId"))
    })
}

// dom循环生成数组Name
function domCreatArrName(dom, arr) {
    dom.each(function (index, value) {
        arr.push($(value).find("span").html())
    })
}

// 清空函数
function emptyFunc() {
    var panelBodyLr = $(".shade .shade-con .panel-body .panel-body-lr");
    var mainActorCheck = $(".shade .shade-con .panel-body .panel-body-lr .main-actor-check .con-main-actor");
    // 不让手动输入
    panelBodyLr.find(".scene-info-con .con-box .con-input input").focus(function () {
        $(this).blur();
    })

    $(".shade .shade-con .panel-foot .empty-btn").click(function () {
        // 集场区间
        panelBodyLr.find(".ji-chang-section input").val('');
        // 集场编号
        panelBodyLr.find(".ji-chang-number input").val('');
        // 单选或者多选
        panelBodyLr.find(".xuan-tian .con-box .con-name").remove();
        // 主演的单选
        mainActorCheck.find("label").hide()
    })
}

// 关闭高级搜索
function closeHighSearch() {
    $(".shade .shade-con .panel-heading h4 i").click(function () {
        $(".shade").css({
            "backgroundColor": "transparent",
            "opacity": "0",
            "transition": "all 0.15s ease-in"
        })
        $(".shade .shade-con").css({
            "opacity": "0",
            "top": "-600px",
            "marginTop": "0px",
            "transition": "all 0.3s ease-in",
            "zIndex": "-111"
        })
        setTimeout(function () {
            $(".shade").css({
                "zIndex": "-111"
            })
        }, 150)

        // $(".shade .shade-con .panel-foot .empty-btn").click()
    })
}