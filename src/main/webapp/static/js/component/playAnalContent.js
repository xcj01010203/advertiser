var xiaotuObj = {
    // 场信息的id
    roundId: '',
    // 剧本内容
    contentText: '',
    // 主要演员
    mainActorList: '',
    // 特约演员
    specialActorList: '',
    // 群众演员
    publicActorList: '',
    // 道具
    stageList: '',
    // 服装
    dressList: '',
    // 标记
    signList: '',
    // 剧本解析
    adAnalList: '',
    // 初始加载的时候给第一场设置一次样式
    countNum: 0,
    // 场景信息的内容
    sceneInfoContent: '',
    // 剧本标记的内容
    playSignContent: '',
    // 广告植入的内容
    adImportContent: '',
    // 广告分析算法结果字段
    adAnalArimCon: '',
    // 广告分析算法结果列表
    adAnalArimConList: '',
    // 选中文字的位置
    selectPosition: 0,
}

$(function () {

    // 右侧tab栏切换
    rightTabChange()

})

// 右侧tab栏切换
function rightTabChange() {
    $(".anal-content>.anal-content-con>.anal-right>.anal-right-title>h5").off("click");
    $(".anal-content>.anal-content-con>.anal-right>.anal-right-title>h5").click(function () {
        $(".anal-content>.anal-content-con>.anal-right>.anal-right-title>h5").removeClass("active")
        $(this).addClass("active")
        $(".anal-content>.anal-content-con>.anal-right>.anal-right-content").addClass("hide")
        $(".anal-content>.anal-content-con>.anal-right").find("." + $(this).attr("reval")).removeClass("hide")
    })
}

/**
 * 获取剧本内容信息
 * @param param 场的id
 */
function getPlayContentInfo(param) {
    var url = '/playContent/queryPlayContent';
    var params = {roundId: param}
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 标题
                $(".anal-content .anal-left .anal-left-title h4").html(data.title.replace(/(\r\n)|(\n)/g, '<br>'))
                // 内容
                $(".anal-content .anal-left .anal-left-content").html(data.content.replace(/(\r\n)|(\n)/g, '<br>'));
                // 字数
                $(".anal-content .anal-left .anal-left-totleword span").html(data.wordCount);

                // 获取场景信息列表
                getSpaceDetails(param)

                // 获取标记列表
                getMarkList(param)

                // 获取广告分析列表
                getAdAnalList(param)

                // 获取广告分析算法标记内容
                getAdAnalArimCon(param)

                // 左侧颜色数据的操作
                operateColorData()

                // 右侧内容的填充 包括那三部分
                operateRightData()
            }
        } else {
            modelWindow(data.message, 0);
        }
    })
}

/**
 * 获取场次详细信息（获取演员列表）
 * @param param 场的id
 */
function getSpaceDetails(param) {
    var url = '/playRound/queryRoundDetail';
    var params = {
        roundId: param
    };
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 场景信息的内容
                xiaotuObj.sceneInfoContent = data;
                // 主要演员
                xiaotuObj.mainActorList = data.majorRoleNameList
                // 特约演员
                xiaotuObj.specialActorList = data.guestRoleNameList
                // 群众演员
                xiaotuObj.publicActorList = data.massRoleNameList
                // 道具
                xiaotuObj.stageList = data.propNameList
                // 服装
                xiaotuObj.dressList = data.clothesNameList
            }
        } else {
            modelWindow(data.message, 0);
        }
    }, function () {
    }, false)
}

// 获取标记列表
function getMarkList(param) {
    var url = '/playMark/queryMarkList';
    var params = {
        roundId: param
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 剧本标记的内容
                xiaotuObj.playSignContent = data;
                // 剧本标记
                xiaotuObj.signList = data.markList;
            }
        } else {
            modelWindow(data.message)
        }
    }, function () {
    }, false)
}

// 获取广告分析列表
function getAdAnalList(param) {
    var url = '/implantRecord/queryRecordList';
    var params = {
        roundId: param
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 广告分析的内容
                xiaotuObj.adImportContent = data;
                // 广告分析
                xiaotuObj.adAnalList = data.recordList;
            }
        } else {
            modelWindow(data.message)
        }
    }, function () {
    }, false)
}

// 获取广告分析算法标记内容
function getAdAnalArimCon(param) {
    var url = '/implantAnalyse/queryRoundResult';
    var params = {
        roundId: param
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 广告分析算法结果
                xiaotuObj.adAnalArimCon = data;
                // 广告分析算法结果
                xiaotuObj.adAnalArimConList = data.resultList;
            }
        }
    }, function () {

    }, false)
}

/**
 * 左侧颜色数据的操作
 */
function operateColorData() {
    // 整段内容
    var html = $(".anal-content .anal-left .anal-left-content").html(), analyArr = [], analyDom,
        analLeftContentDom = $(".anal-content .anal-left .anal-left-content"), conWidth = analLeftContentDom.width();
    // 主要演员
    if (!!xiaotuObj.mainActorList.length) {
        for (var i = 0; i < xiaotuObj.mainActorList.length; i++) {
            html = html.replaceAll(xiaotuObj.mainActorList[i], '<span class="main-actor main-actor-flag">' + xiaotuObj.mainActorList[i] + '</span>')
        }
    }
    // 特约演员
    if (!!xiaotuObj.specialActorList.length) {
        for (var i = 0; i < xiaotuObj.specialActorList.length; i++) {
            html = html.replaceAll(xiaotuObj.specialActorList[i], '<span class="special-actor special-actor-flag">' + xiaotuObj.specialActorList[i] + '</span>')
        }
    }
    // 群众演员
    if (!!xiaotuObj.publicActorList.length) {
        for (var i = 0; i < xiaotuObj.publicActorList.length; i++) {
            html = html.replaceAll(xiaotuObj.publicActorList[i], '<span class="public-actor public-actor-flag">' + xiaotuObj.publicActorList[i] + '</span>')
        }
    }
    // 道具
    if (!!xiaotuObj.stageList.length) {
        for (var i = 0; i < xiaotuObj.stageList.length; i++) {
            html = html.replaceAll(xiaotuObj.stageList[i], '<span class="stage stage-flag">' + xiaotuObj.stageList[i] + '</span>')
        }
    }
    // 服装
    if (!!xiaotuObj.dressList.length) {
        for (var i = 0; i < xiaotuObj.dressList.length; i++) {
            html = html.replaceAll(xiaotuObj.dressList[i], '<span class="dress dress-flag">' + xiaotuObj.dressList[i] + '</span>')
        }
    }
    // 标记
    if (!!xiaotuObj.signList.length) {
        for (var k = 0; k < xiaotuObj.signList.length; k++) {
            var word = xiaotuObj.signList[k].word;
            var len = word.length;
            var word_x = xiaotuObj.signList[k].word_x;
            if (html.indexOf(word) != -1) {
                // 指定字符在整个字符串中出现的位置
                var num = findIndex(html, word, word_x, len);
                var html1 = html.substring(0, num)
                var html2 = html.substr(num + len)
                html = html1.concat('<span class="sign sign-flag">' + word + '<span class="sign-child">' + word_x + '</span></span>', html2);
            }
        }
    }
    // 广告分析
    if (!!xiaotuObj.adAnalArimConList.length) {
        for (var g = 0; g < xiaotuObj.adAnalArimConList.length; g++) {
            analyArr.push(xiaotuObj.adAnalArimConList[g].tag);
            // html = html.replaceAll(xiaotuObj.adAnalArimConList[g].word, '<span title="'+xiaotuObj.adAnalArimConList[g].tag+'" ' +
            html = html.replaceAll(xiaotuObj.adAnalArimConList[g].word, '<span ' +
                'flag="' + g + '" class="ad-anal ad-anal-flag">' + xiaotuObj.adAnalArimConList[g].word + '<span class="ad-anal-child"></span></span>')
        }
    }

    xiaotuObj.contentText = html;
    analLeftContentDom.html(html);

    analyDom = analLeftContentDom.find(".ad-anal");
    analyDom.mouseenter(function () {
        var g = parseInt($(this).attr("flag"))
        $(this).find(".ad-anal-child").html(analyArr[g])
        var spanWidth = $(this).find("span").width();
        // 632是analLeftContentDom的宽度
        var minusWidth = spanWidth * (mouseMoveThing(analLeftContentDom) / 632);
        $(this).find(".ad-anal-child").css("left", -(minusWidth - 20))
    }).mouseleave(function () {
        $(this).find(".ad-anal-child").html("")
    })

    // dom解绑事件
    $(".anal-content .anal-left .anal-color .anal-color-keyword").off("click");

    $(".anal-content .anal-left .anal-color .anal-color-keyword").click(function () {
        if ($(this).hasClass(("active"))) {
            $(this).removeClass("active")
            if ($(this).attr('flag') == "sign") {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .addClass($(this).attr('flag')).find(">.sign-child").removeClass("hide")
            } else if ($(this).attr('flag') == "ad-anal") {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .addClass($(this).attr('flag')).find(">.ad-anal-child").removeClass("hide")
            } else {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .addClass($(this).attr('flag'))
            }
        } else {
            $(this).addClass("active")
            if ($(this).attr('flag') == "sign") {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .removeClass($(this).attr('flag')).find(">.sign-child").addClass("hide")
            } else if ($(this).attr('flag') == "ad-anal") {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .removeClass($(this).attr('flag')).find(">.ad-anal-child").addClass("hide")
            } else {
                $(".anal-content .anal-left .anal-left-content").find('.' + $(this).attr('flag') + '-flag')
                    .removeClass($(this).attr('flag'))
            }
        }
    })
}

// 鼠标移入某个黄色区域时返回鼠标在整个文本区域的位置
function mouseMoveThing(dom) {
    dom.mousemove(function () {
        dom.off("mousemove")
        return event.offsetX;
    })
    return event.offsetX;
}

/**
 * 指定字符在整个字符串中出现的位置
 * @param str 整个字符串
 * @param cha 标记词汇
 * @param num 在字符串中第几次出现
 * @param len 标记词汇的长度
 * @returns {Number|*|number} 出现的位置
 */
function findIndex(str, cha, num, len) {
    var x = str.indexOf(cha);
    for (var i = 0; i < num - 1; i++) {
        x = str.indexOf(cha, x + len);
    }
    return x;
}

// 右侧内容的填充 包括那三部分
function operateRightData() {
    // 场景信息
    var dom = $(".anal-content .anal-right .anal-right-content");
    dom.find(".ji .two").html(xiaotuObj.sceneInfoContent.seriesNo)
    dom.find(".chang .two").html(xiaotuObj.sceneInfoContent.roundNo)
    dom.find(".air .two").html(xiaotuObj.sceneInfoContent.atmosphere)
    dom.find(".in-out .two").html(xiaotuObj.sceneInfoContent.site)
    dom.find(".main-scene .two").html(xiaotuObj.sceneInfoContent.firstLocation)

    dom.find(".main-actor .two").html(xiaotuObj.mainActorList.join(","))
    dom.find(".special-actor .two").html(xiaotuObj.specialActorList.join(","))
    dom.find(".public-actor .two").html(xiaotuObj.publicActorList.join(","))
    dom.find(".stage .two").html(xiaotuObj.stageList.join(","))
    dom.find(".dress .two").html(xiaotuObj.dressList.join(","))
    dom.find(".remark .two").html(xiaotuObj.sceneInfoContent.remark)

    // 渲染剧本标记上面的列表
    refreshPlaySignTop()

    // 渲染广告植入上面的列表
    refreshAdImportTop()

}

// 渲染剧本标记上面的列表
function refreshPlaySignTop() {
    // 剧本标记的内容
    if (!xiaotuObj.playSignContent) {
        return;
    }
    var data = xiaotuObj.playSignContent;
    var dom = $(".anal-content .anal-right .play-sign .play-sign-con")
    if (dom[0]) {
        dom.remove();
    }
    var pSHtml = '', pSContent = data.markList, kGoodsList = '', lRoleNameList = '';
    if (pSContent.length > 0) {
        pSHtml += '<ul class="play-sign-title clearfix"><li class="order">序号</li><li class="keyword">关键词</li>' +
            '<li class="product-name">产品名称</li><li class="role">角色</li><li class="describe">描述</li></ul>';
    }
    for (var k = 0; k < pSContent.length; k++) {
        pSHtml += '<ul signid="' + pSContent[k].id + '" class="play-sign-con">';
        pSHtml += '<li class="order">' + (k + 1) + '</li>';
        pSHtml += '<li title="' + pSContent[k].word + '" word_x="' + pSContent[k].word_x + '" class="keyword">' + pSContent[k].word + '</li>';
        for (var g = 0; g < pSContent[k].goodsList.length; g++) {
            if (g == 0) {
                kGoodsList += pSContent[k].goodsList[g].goods
            } else {
                kGoodsList += ',' + pSContent[k].goodsList[g].goods
            }
        }
        pSHtml += '<li title="' + kGoodsList + '" class="product-name">' + kGoodsList + '</li>';
        for (var l = 0; l < pSContent[k].roleNameList.length; l++) {
            if (l == 0) {
                lRoleNameList += pSContent[k].roleNameList[l];
            } else {
                lRoleNameList += ',' + pSContent[k].roleNameList[l];
            }
        }
        pSHtml += '<li title="' + lRoleNameList + '" class="role">' + lRoleNameList + '</li>';
        pSHtml += '<li title="' + pSContent[k].description + '" class="describe">' + pSContent[k].description + '</li></ul>';
        kGoodsList = '', lRoleNameList = '';
    }
    $(".anal-content .anal-right .play-sign").html(pSHtml);
}

// 渲染广告植入上面的列表
function refreshAdImportTop() {
    // 广告植入的内容
    if (!xiaotuObj.playSignContent) {
        return;
    }
    var data = xiaotuObj.adImportContent;
    var aIHtml = '', aIContent = data.recordList, sRoleNameList = '';
    for (var s = 0; s < aIContent.length; s++) {
        aIHtml += '<div signid="' + aIContent[s].id + '" class="ad-import-con"><div class="con-title">';
        aIHtml += '<h6>' + aIContent[s].name + '</h6>';
        aIHtml += '<span signid="' + aIContent[s].implantMode.id + '" class="stage">' + aIContent[s].implantMode.name + '</span>';
        aIHtml += '<span signid="' + aIContent[s].goods.id + '" class="phone">' + aIContent[s].goods.goods + '</span>';
        aIHtml += '</div><div class="con-con">';
        for (var d = 0; d < aIContent[s].roleNameList.length; d++) {
            if (d == 0) {
                sRoleNameList += aIContent[s].roleNameList[d];
            } else {
                sRoleNameList += ',' + aIContent[s].roleNameList[d];
            }
        }
        aIHtml += '<span>角色：<i>' + sRoleNameList + '</i></span></div>';
        aIHtml += '<div class="con-describe">描述：<i>' + aIContent[s].desc + '</i></div></div>';

        sRoleNameList = '';
    }
    $(".anal-content .anal-right .ad-import").html(aIHtml);
}

// 重新刷一下剧本分析的内容
function refreshAnalyContent() {

    refreshAnalyContentClick("main-actor")
    refreshAnalyContentClick("special-actor")
    refreshAnalyContentClick("public-actor")
    refreshAnalyContentClick("stager")
    refreshAnalyContentClick("dress")
    refreshAnalyContentClick("sign")
    refreshAnalyContentClick("ad-anal")

    $(".anal-content .anal-left .anal-color .anal-color-keyword").removeClass("active");

}

// 重新刷一下剧本分析的内容函数
function refreshAnalyContentClick(str) {
    if ($(".anal-content .anal-left .anal-left-content ." + str + "-flag").hasClass(str)) {
        $(".anal-content .anal-left .anal-color ." + str).trigger("click")
    }
}