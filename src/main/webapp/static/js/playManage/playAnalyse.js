/**
 * Created by Administrator on 2017/6/21.
 */

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
    // 集场的信息
    seriesNoList: '',
    // 搜索框中input的值
    inputValue: '',
    // 搜索框中的全部的下拉列表值
    searchContent: '',
    // 保存剧本解析内容的数组
    analyArr: [],
    // 如果bookmark有值，第一进入页面，不触发场的点击
    bookmarkFlag: 1,
    // 是否是上下翻页切换场
    changeOverSpaceFlag: 1
}

$(function () {

    // 开启loading动画
    $(".show-loading-shade").css("display", "block")

    //检查是否有场次信息
    checkHasRound();

    // 集跟场的收起展示
    clickShowOrHide($(".project-list-four .four-content .four-middle"), $(".project-list-five"))

    // 右侧tab栏切换
    rightTabChange()

    // 因为要走各个接口的ajax所以得分开调用
    // 场景信息
    // 单选的
    // 气氛的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.air"), "single-radio", ".scene-info-con")
    // 内外的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.in-out"), "single-radio", ".scene-info-con")
    // 主场景的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.main-scene"), "single-radio", ".scene-info-con")

    // 多选的
    // 主要演员的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.main-actor"), "multiple-checkbox", ".scene-info-con")
    // 特约演员的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.special-actor"), "multiple-checkbox", ".scene-info-con")
    // 群众演员的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.public-actor"), "multiple-checkbox", ".scene-info-con")
    // 服装的调用函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.dress"), "multiple-checkbox", ".scene-info-con")
    // 道具的函数
    singleMultipleSelect($(".anal-content>.anal-right>.scene-info>.stage"), "multiple-checkbox", ".scene-info-con")

    // 右侧多选 input输入框中回车事件
    // // 主要演员
    multpInputEnterTh($('.anal-content .anal-right .scene-info .main-actor .con-box .con-name input'))
    // 特约演员
    multpInputEnterTh($('.anal-content .anal-right .scene-info .special-actor .con-box .con-name input'))
    // 群众演员
    multpInputEnterTh($('.anal-content .anal-right .scene-info .public-actor .con-box .con-name input'))
    // 服装
    multpInputEnterTh($('.anal-content .anal-right .scene-info .dress .con-box .con-name input'))
    // 道具
    multpInputEnterTh($('.anal-content .anal-right .scene-info .stage .con-box .con-name input'))


    // 剧本标记
    // 产品名称
    singleMultipleSelect($(".anal-content>.anal-right>.play-sign>.play-sign-bottom>.product-name"), "multiple-checkbox", ".play-sign-bottom-con")
    // 角色
    singleMultipleSelect($(".anal-content>.anal-right>.play-sign>.play-sign-bottom>.role"), "multiple-checkbox", ".play-sign-bottom-con")

    // 右侧多选 input输入框中回车事件
    // 产品名称
    multpInputEnterTh($('.anal-content .anal-right .play-sign .product-name .con-box .con-name input'))
    // 角色
    multpInputEnterTh($('.anal-content .anal-right .play-sign .role .con-box .con-name input'))


    // 广告植入
    // 单选的
    // 植入手法
    singleMultipleSelect($(".anal-content>.anal-right>.ad-import>.ad-import-bottom>.import-ploy"), "single-radio", ".ad-import-bottom-con")
    // 产品
    singleMultipleSelect($(".anal-content>.anal-right>.ad-import>.ad-import-bottom>.product"), "single-radio", ".ad-import-bottom-con")
    // 多选的
    // 角色
    singleMultipleSelect($(".anal-content>.anal-right>.ad-import>.ad-import-bottom>.role"), "multiple-checkbox", ".ad-import-bottom-con")

    // 右侧多选 input输入框中回车事件
    // 角色
    multpInputEnterTh($('.anal-content .anal-right .ad-import .ad-import-bottom .role .con-box .con-name input'))


    // 当点击需要输入的框时，关闭中间的搜索框
    // 场景信息
    clickImportCloseSearch($(".anal-content>.anal-right>.scene-info>.import>input"))
    // 剧本标记
    clickImportCloseSearch($(".anal-content>.anal-right>.play-sign>.play-sign-bottom>.import>input"))
    // 广告植入
    clickImportCloseSearch($(".anal-content>.anal-right>.ad-import>.ad-import-bottom>.import>input"))

    // 关闭中间的搜索栏
    closeSearchBox()

    // 当文本内容加载出来之后 鼠标选中的文本
    contentLoadFinish()

    // 点击出现的标签填到右侧
    clickLabelFillOffside()

    // 获取集次列表
    getGatherSpaceList()

    // 保存场景信息
    saveSceneInfo()

    // 添加剧本标记
    addPlaySign()

    // 保存广告植入
    saveAdImport()

    // 当关键词的input框获取焦点，并且有键盘的弹起的时候，给input上的flag设置成true
    focusKeyupInputToTrue()

    // 右侧三部分的取消按钮事件
    clickCancelFunc()

    // 左侧切换场
    leftTabSpace($(".anal-content .anal-left .page-turning .top"), "prev")
    leftTabSpace($(".anal-content .anal-left .page-turning .down"), "next")

    // 删除某一场信息
    removeRound()

    // 角色列表
    pickRoleList()

    // 关闭角色列表
    closePickRoleList()
})

//检查是否有场次信息
function checkHasRound() {
    var url = "/playRound/checkHasRound";
    var successFn = function (response) {
        if (response.status == 1) {
            alert(response.message);
            return;
        }
        if (response.data) {
            $(".project-list-four .four-content .four-right .upload-play-t")
                .before('<button class="change-play">下载剧本</button>')

            // 下载剧本
            $(".project-list-four .four-content .four-right .change-play").click(function () {
                // window.location.href = basePath + '/play/downloadPlay';
                window.location.href = basePath + '/playContent/downloadPlay';
            })
        }
    };
    doPost(url, {}, successFn);
}

// 文档点击事件
function docuClickThing() {
    $(document).click(function (e) {
        if ($(e.target).parents(".anal-content>.anal-left>.anal-left-content").length == 0) {
            $(".anal-menu").hide(150);
            $(".anal-menu").attr('xs', 'f');

            $(document).off("click");
        }

        setTimeout(function () {
            if ($(".anal-menu").attr("xs") == 'f') {
                $(".anal-content .anal-left .anal-left-content").html(xiaotuObj.contentText.replace('<b>' + getSelectText() + '</b>', getSelectText()));

                // 刷新剧本内容的时候都要给
                refreshAnalyContentOffice()
            } else {
            }
        }, 160)
    })
}

// 左侧切换场
function leftTabSpace(dom, prevOrNext) {
    dom.click(function () {
        var activeSpace = $(".project-list-five .five-content .five-content-space .space-right li.active");
        var activeGather = $(".project-list-five .five-content .five-content-gather .gather-right li.active");
        if (prevOrNext == "prev") {
            // 上一场
            // 开启loading动画
            $(".show-loading-shade").css("display", "block")
            if (activeSpace.prev()[0]) {
                activeSpace.removeClass("active")
                activeSpace.prev().addClass("active")
                activeSpace.prev().trigger("click")
            } else if (activeGather.prev()[0]) {
                xiaotuObj.changeOverSpaceFlag = 2
                activeGather.prev().trigger("click")
                xiaotuObj.changeOverSpaceFlag = 1
            } else {
                // 关闭loading动画
                $(".show-loading-shade").css("display", "none")
                modelWindow("没有上一场信息", 1000)
            }
        } else if (prevOrNext == "next") {
            // 下一场
            activeSpace.next().trigger("click")
            // 开启loading动画
            $(".show-loading-shade").css("display", "block")
            if (activeSpace.next()[0]) {
                activeSpace.removeClass("active")
                activeSpace.next().addClass("active")
            } else if (activeGather.next()[0]) {
                activeGather.next().trigger("click")
            } else {
                // 关闭loading动画
                $(".show-loading-shade").css("display", "none")
                modelWindow("没有下一场信息", 1000)
            }
        }
    })
}

// 右侧tab栏切换
function rightTabChange() {
    $(".anal-content>.anal-right>.anal-right-title>h5").click(function () {
        $(".anal-content .anal-right .anal-search").addClass("hide")
        $(".anal-content>.anal-right>.anal-right-title>h5").removeClass("active")
        $(this).addClass("active")
        $(".anal-content>.anal-right>.anal-right-content").addClass("hide")
        $(".anal-content>.anal-right").find("." + $(this).attr("reval")).removeClass("hide")

        // 移除剧本标记角色框中的值
        $(".anal-content .anal-right .play-sign .play-sign-bottom .role .con-box .con-name span").parents(".con-name").remove()
        // 移除广告植入角色框中的值
        $(".anal-content .anal-right .ad-import .ad-import-bottom .role .con-box .con-name span").parents(".con-name").remove()
    })
}

// 气氛、内外、主场景都是单选。
// 主要演员、特约演员、群众演员、服装、道具都是多选
function singleMultipleSelect(dom, type, string) {
    var singleValue = '', multipleValue = [];
    dom.find(".con-box>.con-name>input").click(function () {
        var _this = this;
        // 给点击的dom添加一个状态，方便后面跟中间的查询数据联动
        // dom.siblings(".scene-info-con").attr("status", "")
        dom.siblings(string).attr("status", "")
        dom.attr("status", "click-status")
        $(".anal-content>.anal-right>.anal-search").removeClass("hide")
        var title = dom.find("span").html();
        $(".anal-content>.anal-right>.anal-search>.anal-search-title>h5").html(title);

        // 获取中间搜索所有下拉信息
        getDropdownSceneInfo(_this)

        if (type == "single-radio") {
            // 单选
            singleValue = dom.find(".con-box>.con-name>input").val();
            $(".anal-content>.anal-right>.anal-search>.single-radio").removeClass("hide")
            $(".anal-content>.anal-right>.anal-search>.multiple-checkbox").addClass("hide")
        } else if (type == "multiple-checkbox") {
            // 多选
            dom.find(".con-box>.con-name").each(function () {
                multipleValue.push($(this).find("span").html())
            })
            $(".anal-content>.anal-right>.anal-search>.single-radio").addClass("hide")
            $(".anal-content>.anal-right>.anal-search>.multiple-checkbox").removeClass("hide")
        } else {
        }

        // 初始加载的时候根据右边刷新搜索中radio的值
        $(".anal-content>.anal-right>.anal-search>.single-radio>.single-radio-con").find("input").prop("checked", false);
        $(".anal-content>.anal-right>.anal-search>.single-radio>.single-radio-con").each(function (index, value) {
            if ($(value).find("span").html() == singleValue) {
                $(value).find("input").prop("checked", true);
            }
        })
        singleValue = '';
        // 初始加载的时候根据右边刷新搜索中checkbox的值
        $(".anal-content>.anal-right>.anal-search>.multiple-checkbox>.multiple-checkbox-con").find("input").prop("checked", false);
        $(".anal-content>.anal-right>.anal-search>.multiple-checkbox>.multiple-checkbox-con").each(function (index, value) {
            for (var j = 0; j < multipleValue.length; j++) {
                if ($(value).find("span").html() == multipleValue[j]) {
                    $(value).find("input").prop("checked", true);
                }
            }
        })
        multipleValue = [];

        // 场景信息
        // 单选事件函数
        singleLinkageEvent($(".anal-content>.anal-right>.anal-search>.single-radio>.single-radio-con>input"),
            $(".anal-content>.anal-right>.scene-info"))
        // 多选事件函数
        multipleLinkageEvent($(".anal-content>.anal-right>.anal-search>.multiple-checkbox>.multiple-checkbox-con"),
            $(".anal-content>.anal-right>.scene-info"))


        // 剧本标记
        // 多选事件函数
        multipleLinkageEvent($(".anal-content>.anal-right>.anal-search>.multiple-checkbox>.multiple-checkbox-con"),
            $(".anal-content>.anal-right>.play-sign"))


        // 广告植入
        // 单选事件函数
        singleLinkageEvent($(".anal-content>.anal-right>.anal-search>.single-radio>.single-radio-con>input"),
            $(".anal-content>.anal-right>.ad-import"))
        // 多选事件函数
        multipleLinkageEvent($(".anal-content>.anal-right>.anal-search>.multiple-checkbox>.multiple-checkbox-con"),
            $(".anal-content>.anal-right>.ad-import"))

        return false;
    })
}

// 获取中间搜索所有下拉信息
function getDropdownSceneInfo(that) {
    xiaotuObj.inputValue = '';
    $(".anal-content .anal-right .anal-search .anal-search-sear input").val(xiaotuObj.inputValue)
    var lrdropdownlist = $(that).attr("lrdropdownlist");
    var sdtype = $(that).attr("sdtype");
    var flag = $(that).parents(".left-right-linkage").attr("flag");
    doPost(basePath + '/playRound/queryAllDropDownList', {}, function (data) {
        if (data.status == 0) {
            if (data.data) {
                var data = data.data[lrdropdownlist];
                var html = '';
                $(".anal-content .anal-right .anal-search .single-radio").addClass("hide");
                $(".anal-content .anal-right .anal-search .multiple-checkbox").addClass("hide");
                if (sdtype == "radio") {
                    // 单选
                    if (lrdropdownlist == "goodsList") {
                        for (var i = 0; i < data.length; i++) {
                            html += '<div class="single-radio-con"><input name="single-r" type="radio"><span gid="' + data[i].id + '">' + data[i].goods + '</span></div>'
                        }
                    } else if (lrdropdownlist == 'implantModeList') {
                        for (var i = 0; i < data.length; i++) {
                            html += '<div class="single-radio-con"><input name="single-r" type="radio"><span gid="' + data[i].id + '">' + data[i].name + '</span></div>'
                        }
                    } else {
                        for (var i = 0; i < data.length; i++) {
                            html += '<div class="single-radio-con"><input name="single-r" type="radio"><span>' + data[i] + '</span></div>'
                        }
                    }
                    $(".anal-content .anal-right .anal-search .single-radio").html(html);
                    $(".anal-content .anal-right .anal-search .single-radio").attr("flag", flag);
                    $(".anal-content .anal-right .anal-search .single-radio").removeClass("hide");
                    $(".anal-content .anal-right .anal-search .multiple-checkbox").removeClass("exist");
                    $(".anal-content .anal-right .anal-search .single-radio").addClass("exist");
                } else if (sdtype == "checkbox") {
                    if (lrdropdownlist == "goodsList") {
                        for (var j = 0; j < data.length; j++) {
                            html += '<div class="multiple-checkbox-con"><input name="multiple-c" type="checkbox"><span gid="' + data[j].id + '">' + data[j].goods + '</span></div>'
                        }
                    } else if (lrdropdownlist == 'implantModeList') {
                        for (var j = 0; j < data.length; j++) {
                            html += '<div class="multiple-checkbox-con"><input name="multiple-c" type="checkbox"><span gid="' + data[j].id + '">' + data[j].name + '</span></div>'
                        }
                    } else {
                        for (var j = 0; j < data.length; j++) {
                            html += '<div class="multiple-checkbox-con"><input name="multiple-c" type="checkbox"><span>' + data[j] + '</span></div>'
                        }
                    }
                    $(".anal-content .anal-right .anal-search .multiple-checkbox").html(html);
                    $(".anal-content .anal-right .anal-search .multiple-checkbox").attr("flag", flag);
                    $(".anal-content .anal-right .anal-search .multiple-checkbox").removeClass("hide");
                    $(".anal-content .anal-right .anal-search .single-radio").removeClass("exist");
                    $(".anal-content .anal-right .anal-search .multiple-checkbox").addClass("exist");
                } else {
                }

                // 中间单选多选的模糊搜索
                vagueDataSearch()
            }
        }
    }, function (error) {
    }, false, function (complete) {
    })
}

// 中间单选多选的模糊搜索
function vagueDataSearch() {
    // 输入框dom
    var $inputDom = $(".anal-content .anal-right .anal-search .anal-search-sear input");
    // 原始数据容器
    var existDom = $(".anal-content .anal-right .anal-search .exist");
    // 循环的div上的类名
    var mark = existDom.attr("mark"), inputVal;
    $inputDom.off("keyup")
    $inputDom.keyup(function () {
        // input输入框中的值
        xiaotuObj.inputValue = inputVal = $(this).val();
        inputVal = inputVal.trim();
        if (!!inputVal) {
            // 有值
            existDom.find("." + mark).addClass("hide");
            existDom.find("." + mark).each(function (index, value) {
                var str = $(value).find("span").html();
                if (str.indexOf(inputVal) != -1) {
                    // 匹配
                    $(value).removeClass("hide");
                }
            })
        } else {
            // 无值
            existDom.find("." + mark).removeClass("hide");
        }
    })
}

// 关闭中间的搜索栏
function closeSearchBox() {
    $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").click(function () {
        $(this).parents(".anal-search").addClass("hide")
    })

    $("body").click(function (e) {
        if ($(e.target).parents(".anal-content>.anal-right>.anal-search").length == 0) {
            $(".anal-content>.anal-right>.anal-search").addClass("hide");
        }
    })
}

// 单选事件函数
function singleLinkageEvent(dom1, dom2) {
    var flag = dom1.parents(".single-radio").attr("flag");
    dom1.click(function () {
        var valueHtml = $(this).siblings("span").html();
        dom2.find("." + flag + ">.con-box>.con-name>input").val(valueHtml);
        if (!!$(this).siblings("span").attr("gid")) {
            var gid = $(this).siblings("span").attr("gid");
            dom2.find("." + flag + ">.con-box>.con-name>input").attr("gid", gid);
        }
    })
}

// 多选事件函数
function multipleLinkageEvent(dom1, dom2) {
    // 点击checkbox更新数组中的内容，并且更新右侧框中的内容
    var flag = dom1.parents(".multiple-checkbox").attr("flag");

    if (dom2.find("." + flag + ">.con-box>.con-name>i")[0]) {
        // judgeDomEvent(dom2.find("." + flag + ">.con-box>.con-name>i"), "click");  发现之后改的
        dom2.find("." + flag + ">.con-box>.con-name>i").off("click")

        // 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值
        dom2.find("." + flag + ">.con-box>.con-name>i").click(function () {
            var arrList = [];
            $(this).parents(".con-name").remove();
            // dom2.find("." + flag + ">.con-box>.con-name[from='from-checkbox']").each(function (index, value) {
            dom2.find("." + flag + ">.con-box>.con-name>span").parents(".con-name").each(function (index, value) {
                arrList.push($(value).find("span").html());
            })
            if (!!arrList.length) {
                dom1.find("input").prop("checked", false);
                dom1.each(function (index, value) {
                    for (var j = 0; j < arrList.length; j++) {
                        if ($(value).find("span").html() == arrList[j]) {
                            $(value).find("input").prop("checked", true);
                        }
                    }
                })
            } else {
                dom1.find("input").prop("checked", false);
            }
            return false;
        })
    }
    dom1.find("input").click(function () {
        if ($(this).is(":checked")) {
            var html = $(this).siblings("span").html();
            var gid = $(this).siblings("span").attr("gid");
            var htmlDate = '<div from="from-checkbox" class="con-name"><span gid="' + gid + '">' + html + '</span>' +
                '<i class="icon iconfont">&#xe61c;</i></div>';
            dom2.find("." + flag + ">.con-box>.con-name>input").parents(".con-name").before(htmlDate);
        } else {
            var html = $(this).siblings("span").html();
            dom2.find("." + flag + ">.con-box>.con-name[from='from-checkbox']").each(function (index, value) {
                if (html == $(value).find("span").html()) {
                    $(value).remove()
                }
            })
        }

        if (dom2.find("." + flag + ">.con-box>.con-name>i")[0]) {
            // judgeDomEvent(dom2.find("." + flag + ">.con-box>.con-name>i"), "click");  发现之后改的
            dom2.find("." + flag + ">.con-box>.con-name>i").off("click")

            // 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值
            dom2.find("." + flag + ">.con-box>.con-name>i").click(function () {
                var arrList = [];
                $(this).parents(".con-name").remove();
                dom2.find("." + flag + ">.con-box>.con-name[from='from-checkbox']").each(function (index, value) {
                    arrList.push($(value).find("span").html());
                })
                if (!!arrList.length) {
                    dom1.find("input").prop("checked", false);
                    dom1.each(function (index, value) {
                        for (var j = 0; j < arrList.length; j++) {
                            if ($(value).find("span").html() == arrList[j]) {
                                $(value).find("input").prop("checked", true);
                            }
                        }
                    })
                } else {
                    dom1.find("input").prop("checked", false);
                }
                return false;
            })
        }
    })
}

// 给多选框中的叉注册点击事件  删除标签
function multipleChaClick() {
    var $i = $(".anal-content .anal-right .anal-right-content .multiple .con-box .con-name i");
    if ($i[0]) {
        $i.click(function () {
            $(this).parents(".con-name").remove();
            return false;
        })
    }
}

// 当点击需要输入的框时，关闭中间的搜索框
function clickImportCloseSearch(dom) {
    dom.focus(function () {
        $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").trigger("click")
    })
}

// 右侧多选 input输入框中回车事件
function multpInputEnterTh(dom) {
    dom.click(function (e) {
        var flag = true;
        dom.parents(".con-name").siblings(".con-name").each(function (index, value) {
            var html = $(value).find("span").html();
            if (dom.val() == html) {
                flag = false;
                dom.val("");
            }
        })
        if (flag) {
            dom.off("keydown")
            dom.keydown(function (e) {
                if (e && e.keyCode == 13) {
                    dom.parents(".con-name").before('<div class="con-name"><span>' + dom.val() + '</span>' +
                        '<i onclick="handleImportClose(this)" class="icon iconfont">&#xe61c;</i></div>')
                    dom.val("");
                }
            })
        }
        return false;
    })
}

// 多选 给手动输入的标签添加删除事件
function handleImportClose(that) {
    $(that).parents(".con-name").remove();
    return false;
}

// 当文本内容加载出来之后 鼠标选中的文本
function contentLoadFinish() {
    $(".anal-content>.anal-left>.anal-left-content").mouseup(function (e) {
        document.execCommand('bold');

        if (!!getSelectText()) {
            $(".anal-menu").css({
                left: getPageXY(e).x + 'px',
                top: getPageXY(e).y + 'px'
            }).show(200)
            $(".anal-menu").attr('xs', 't');

            // 文档点击事件
            setTimeout(function () {
                docuClickThing()
            }, 210)
        } else {
            $(".anal-menu").hide(150);
            $(".anal-menu").attr('xs', 'f');

            $(document).off("click");
        }
        setTimeout(function () {
            if ($(".anal-menu").attr("xs") == 'f') {
                // 当被选中文本然后出来的几个菜单列表消失的时候，重新刷一下剧本内容
                $(".anal-content .anal-left .anal-left-content").html(xiaotuObj.contentText.replace('<b>' + getSelectText() + '</b>', getSelectText()));

                // 刷新剧本内容的时候都要给
                refreshAnalyContentOffice()
            } else {
            }
        }, 160)
        return false;
    })
}

// 鼠标选中的文本
function getSelectText() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        //  ie 返回 ture
        // for IE， IE的话用这一段
        var sel = window.selection.createRange();
        var content = sel.text;
    } else {
        //  别的浏览器 返回 false
        // for FF，不是IE的话用这一段
        var content = window.getSelection();
    }
    var text = content.toString();

    if (text != "") {
        return text;
    } else {
        return false;
    }
};

// 取到剧本标记选中的词在文本中第几次出现
function getPsTextNum() {
    var dom = $(".anal-content .anal-left .anal-left-content");
    var contentText = dom.html();
    var str = getSelectText();

    var num = 0;
    var flag = true;
    var len = str.length;
    var place1 = contentText.indexOf(str) - 3;
    var place2 = contentText.indexOf('<b>');

    while (place1 <= place2) {
        if (flag) {
            flag = false;
            place1 = contentText.indexOf(str) + len;
            if (contentText.indexOf(str) == -1) {
                place1 = place2 + 1000;
            }
        } else {
            if (contentText.indexOf(str, place1) == -1) {
                place1 = place2 + 1000;
            } else {
                place1 = contentText.indexOf(str, place1) + len;
            }
        }
        num++;
    }
    return num;
}

// 鼠标位置
function getPageXY(ev) {
    Ev = ev || window.event;
    if (ev.pageX || ev.pageY) {
        return {x: ev.pageX, y: ev.pageY};
    }
    var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    return {
        x: ev.clientX + scrollLeft,
        y: ev.clientY + scrollTop
    };
}

// 点击出现的标签填到右侧
function clickLabelFillOffside() {
    $(".anal-menu>li>a").click(function (e) {
        $(".anal-content>.anal-right>.anal-right-title>.anal-right-tl").removeClass("active")
        $(".anal-content>.anal-right>.anal-right-title").find("." + $(this).attr("show") + "-title").addClass("active")
        $(".anal-content>.anal-right>.anal-right-content").addClass("hide");
        $(".anal-content>.anal-right").find('.' + $(this).attr("show")).removeClass("hide");
        var divDomVal = $(this).attr("val");
        if ($(this).attr("show") == "scene-info") {
            // 场景信息
            var sceneInfoDom = $(".anal-content>.anal-right>.scene-info"), secneInfoArr = [];
            if ($(this).attr("type") == "single") {
                // 单选的
                sceneInfoDom.find("." + divDomVal).find("input").val(getSelectText())
            } else if ($(this).attr("type") == "multiple") {
                // 多选的
                sceneInfoDom.find("." + divDomVal + ">.con-box>.con-name>span")
                    .parents(".con-name")
                    .each(function (index, value) {
                        secneInfoArr.push($(value).find("span").html());
                    })

                if (secneInfoArr.join("").indexOf(getSelectText()) != -1) {
                    alert("请勿重复添加");
                    return;
                }
                sceneInfoDom.find("." + divDomVal)
                    .find("input")
                    .parents(".con-name")
                    .before('<div class="con-name"><span>' + getSelectText() + '</span><i onclick="handleImportClose(this)" ' +
                        'class="icon iconfont">&#xe61c;</i></div>')
            } else {
            }
            $(".anal-menu").hide(150);
            $(".anal-menu").attr('xs', 'f');

            $(document).off("click");

        } else if ($(this).attr("show") == "play-sign") {
            // 剧本标记
            var playSignInput = $(".anal-content>.anal-right>.play-sign").find("." + divDomVal).find("input");
            playSignInput.val(getSelectText())
            playSignInput.attr("word_x", getPsTextNum())
            // 这个标识用来判断保存剧本标记接口要不要传id
            // modify为false的时候，说明是从左侧划词来的关键词或填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            playSignInput.attr("modify", 'false')

            $(".anal-menu").hide(150)
            $(".anal-menu").attr('xs', 'f')
            // $("body").trigger("click")

            $(document).off("click");

        } else if ($(this).attr("show") == "ad-import") {
            // 广告植入
            $(".anal-content>.anal-right>.ad-import").find("." + divDomVal).find("input").val(getSelectText())
            $(".anal-menu").hide(150)
            $(".anal-menu").attr('xs', 'f')

            $(document).off("click");

        } else {
        }
        setTimeout(function () {
            if ($(".anal-menu").attr("xs") == 'f') {
                // 当被选中文本然后出来的几个菜单列表消失的时候，重新刷一下剧本内容
                $(".anal-content .anal-left .anal-left-content").html(xiaotuObj.contentText.replace('<b>' + getSelectText() + '</b>', getSelectText()));

                // 刷新剧本内容的时候都要给
                refreshAnalyContentOffice()
            }
        }, 160)
    })
}

// 获取集次列表
function getGatherSpaceList() {
    var url = '/playRound/querySeriesRoundNos'
    doPost(basePath + url, "", function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                var seriesNoList = data.seriesNoList;
                xiaotuObj.seriesNoList = seriesNoList;
                var html = '';
                for (var item in seriesNoList) {
                    html += '<li gatherId="' + item + '"><a href="javascript:;">' + item + "(" + seriesNoList[item].length + ')</a>' +
                        '<i class="icon iconfont">&#xe600;</i></li>';
                }
                // 渲染集
                $(".project-list-five .five-content .five-content-gather .gather-right").html(html);
                $(".project-list-five .five-content .five-content-gather .gather-right").find("li:eq(0)").addClass("active");

                $(".project-list-five .five-content .five-content-gather .gather-right li").click(function () {
                    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");
                    $(this).addClass("active").siblings("li").removeClass("active");
                    var num = parseInt($(this).attr("gatherId")), spaceHtml = '', gatherNo, spaceNo;

                    // 拿到当前的集
                    $(".project-list-five .five-content .five-content-gather .gather-right li").each(function (index, value) {
                        if ($(value).hasClass("active")) {
                            gatherNo = $(value).attr("gatherid");
                        }
                    })

                    for (var i = 0; i < seriesNoList[num].length; i++) {
                        spaceHtml += '<li flagId="' + seriesNoList[num][i].id + '"><a href="javascript:;">' + seriesNoList[num][i].roundNo + '</a>' +
                            '<i class="icon iconfont">&#xe600;</i></li>';
                    }
                    // 渲染场  只有当点击集的时候才能渲染场
                    $(".project-list-five .five-content .five-content-space .space-right").html(spaceHtml);
                    // 点击场的信息
                    $(".project-list-five .five-content .five-content-space .space-right li").click(function () {

                        // 开启loading动画
                        $(".show-loading-shade").css("display", "block");

                        $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");
                        $(this).addClass("active").siblings("li").removeClass("active")

                        var param = $(this).attr("flagId");

                        xiaotuObj.roundId = param;

                        // 获取剧本内容信息  第一次操作
                        getPlayContentInfo(param);

                        // 拿到当前的场
                        $(".project-list-five .five-content .five-content-space .space-right li").each(function (index, value) {
                            if ($(value).hasClass("active")) {
                                spaceNo = $(value).find("a").html();
                            }
                        })

                        $(".project-list-four .four-content .four-middle span").html(gatherNo + "(" + spaceNo + ")")
                    })

                    if (xiaotuObj.bookmarkFlag == 2) {
                        return;
                    }
                    // 当点击集的时候也触发一下点击场，来拿到xiaotuObj.rounId
                    if (xiaotuObj.changeOverSpaceFlag == 1) {
                        // 如果为1说明是点击场来看场的信息
                        $(".project-list-five .five-content .five-content-space .space-right li:eq(0)").trigger("click");
                    } else if (xiaotuObj.changeOverSpaceFlag == 2) {
                        // 如果为2说明是点击的上下页来找上下场信息  只有上一场需要拿到当前集的最后一场
                        $(".project-list-five .five-content .five-content-space .space-right li:last-child").trigger("click");
                    }else {}
                })

                // 如果bookmarks中有值就走这个书签页
                if (!!data.bookmarks) {
                    var ji = data.bookmarks.seriesNo;
                    var changid = data.bookmarks.id;
                    var jiDom = $(".project-list-five .five-content .five-content-gather .gather-right li");
                    jiDom.each(function (index, value) {
                        if ($(value).attr("gatherid") == ji) {
                            xiaotuObj.bookmarkFlag = 2;
                            $(value).trigger("click")
                            xiaotuObj.bookmarkFlag = 1;
                        }
                    })
                    var changDom = $(".project-list-five .five-content .five-content-space .space-right li");
                    changDom.each(function (index, value) {
                        if ($(value).attr("flagid") == changid) {
                            $(value).trigger("click")
                        }
                    })
                    $(".project-list-four .four-content .four-middle span").html(ji + "(" + data.bookmarks.roundNo + ")")
                } else {
                    // 点击第一集 点击第一场
                    $(".project-list-five .five-content .five-content-gather .gather-right li:eq(0)").trigger("click");
                }
            }
        }
    }, function (error) {
    }, true, function (complete) {
        // 关闭loading动画
        $(".show-loading-shade").css("display", "none");
    })
}

// 获取剧本内容信息
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
                // $(".anal-content .anal-left .anal-left-totleword span").html(data.wordCount)
                $(".anal-content .anal-left .anal-left-totleword").html('字数 : <span>' + data.wordCount + '</span>字')
            }
        }
    }, function (error) {
    }, true, function (complete) {

        // 关闭loading动画
        $(".show-loading-shade").css("display", "none")

        // 获取场景详细信息（获取演员列表）
        getSpaceDetails(param)

        // 获取标记列表
        getMarkList(param)

        // 获取广告分析列表
        getAdAnalList(param)

        // 获取广告分析算法标记内容
        getAdAnalArimCon(param)

        // 左侧颜色数据的操作
        operateColorData()

        // 右侧内容的填充
        operateRightData()
    })
}

// 获取场景信息列表
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
        }
    }, function (error) {
    }, false, function (complete) {
    })
}

// 删除某一场信息
function removeRound() {
    $(".anal-content .anal-right .anal-right-content .remove").click(function () {
        modelWindow("确定要删除本场信息？");
        // $("#modal-header").html('<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        //     '<span aria-hidden="true">&times;</span></button><h4>确定要删除本场信息？</h4>');
        // $("#modal-body").remove();
        $("#modal-footer").css("display", "block");
        $("#modal-footer").html('<button type="button" class="btn btn-primary"  data-dismiss="modal">确定</button>' +
            '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>')
        $("#modal-footer .btn-primary").click(function () {
            var url = "/playRound/removeRound";
            doPost(url, {id: xiaotuObj.roundId}, function (data) {
                if (data.status == 0) {
                    location.reload();
                } else {
                    modelWindow(data.message);
                }
            })
        })
    })
}

// 获取标记列表
function getMarkList(param) {
    var url = '/playMark/queryMarkList';
    var params = {
        // roundId: xiaotuObj.roundId
        roundId: param
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 标记
                xiaotuObj.signList = data.markList;
            }
        }
    }, function (error) {
    }, false, function (complete) {
    })
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
        }
    }, function (error) {
    }, false, function (complete) {
    })
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
    }, function (error) {}, false, function (complete) {})
}

// 左侧颜色数据的操作
function operateColorData() {
    // 整段内容
    var html = $(".anal-content .anal-left .anal-left-content").html(), analyDom,
        analLeftContentDom = $(".anal-content .anal-left .anal-left-content"), conWidth = analLeftContentDom.width();
    // 主要演员
    if (!!xiaotuObj.mainActorList.length) {
        for (var i = 0; i < xiaotuObj.mainActorList.length; i++) {
            html = html.replaceAll(xiaotuObj.mainActorList[i], '<span class="main-actor main-actor-flag">'
                + xiaotuObj.mainActorList[i] + '</span>')
        }
    }
    // 特约演员
    if (!!xiaotuObj.specialActorList.length) {
        for (var j = 0; j < xiaotuObj.specialActorList.length; j++) {
            html = html.replaceAll(xiaotuObj.specialActorList[j], '<span class="special-actor special-actor-flag">'
                + xiaotuObj.specialActorList[j] + '</span>')
        }
    }
    // 群众演员
    if (!!xiaotuObj.publicActorList.length) {
        for (var k = 0; k < xiaotuObj.publicActorList.length; k++) {
            html = html.replaceAll(xiaotuObj.publicActorList[k], '<span class="public-actor public-actor-flag">'
                + xiaotuObj.publicActorList[k] + '</span>')
        }
    }
    // 道具
    if (!!xiaotuObj.stageList.length) {
        for (var l = 0; l < xiaotuObj.stageList.length; l++) {
            html = html.replaceAll(xiaotuObj.stageList[l], '<span class="stage stage-flag">' + xiaotuObj.stageList[l] + '</span>')
        }
    }
    // 服装
    if (!!xiaotuObj.dressList.length) {
        for (var m = 0; m < xiaotuObj.dressList.length; m++) {
            html = html.replaceAll(xiaotuObj.dressList[m], '<span class="dress dress-flag">' + xiaotuObj.dressList[m] + '</span>')
        }
    }
    // 广告分析
    if (!!xiaotuObj.adAnalArimConList.length) {
        xiaotuObj.analyArr = [];
        for (var g = 0; g < xiaotuObj.adAnalArimConList.length; g++) {
            xiaotuObj.analyArr.push(xiaotuObj.adAnalArimConList[g].tag);
            html = html.replaceAll(xiaotuObj.adAnalArimConList[g].word, '<span flag="' + g + '" class="ad-anal ad-anal-flag">'
                + xiaotuObj.adAnalArimConList[g].word + '<span class="ad-anal-child"></span></span>')
        }
    }
    // 标记
    if (!!xiaotuObj.signList.length) {
        for (var n = 0; n < xiaotuObj.signList.length; n++) {
            var word = xiaotuObj.signList[n].word;
            var len = word.length;
            var word_x = xiaotuObj.signList[n].word_x;
            if (html.indexOf(word) != -1) {
                // 指定字符在整个字符串中出现的位置
                var num = findIndex(html, word, word_x, len);
                var html1 = html.substring(0, num)
                var html2 = html.substr(num + len)
                html = html1.concat('<span class="sign sign-flag">' + word + '<span class="sign-child">' + word_x + '</span></span>', html2);
            }
        }
    }

    xiaotuObj.contentText = html;
    $(".anal-content .anal-left .anal-left-content").html(html);

    // 刷新剧本内容的时候都要给
    refreshAnalyContentOffice()

    // 判断dom有没有绑定点击事件，防止多次注册点击事件
    // judgeDomEvent($(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword"), 'click');  发现之后改的
    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").off("click")

    // 点击颜色值的时候切换剧本中颜色值的状态变化
    changeColorStatusTrans()

}

// 刷新剧本内容的时候都要给
function refreshAnalyContentOffice() {
    var analLeftContentDom = $(".anal-content .anal-left .anal-left-content"), conWidth = analLeftContentDom.width(),
        analyDom = $(".anal-content .anal-left .anal-left-content .ad-anal");
    analyDom.mouseenter(function () {
        var g = parseInt($(this).attr("flag"))
        $(this).find(".ad-anal-child").html(xiaotuObj.analyArr[g])
        var spanWidth = $(this).find("span").width();
        // conWidth是analLeftContentDom的宽度
        var minusWidth = spanWidth * (mouseMoveThing(analLeftContentDom) / conWidth);
        $(this).find(".ad-anal-child").css("left", -(minusWidth - 20))
    }).mouseleave(function () {
        $(this).find(".ad-anal-child").html("");
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

// 点击颜色值的时候切换剧本中颜色值的状态变化
function changeColorStatusTrans() {
    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").click(function () {
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

            xiaotuObj.contentText = $(".anal-content .anal-left .anal-left-content").html();
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

            xiaotuObj.contentText = $(".anal-content .anal-left .anal-left-content").html();
        }
    })
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

    // 渲染右侧场景信息的内容
    refreshSceneContent()

    // 剧本标记
    // 渲染剧本标记上面的列表
    refreshPlaySignTop()

    // 广告植入
    // 渲染广告植入上面的列表
    refreshAdImportTop()

}

// 渲染右侧场景信息的内容
function refreshSceneContent() {
    // 场景信息
    var mAHtml = '', sAHtml = '', pAHtml = '', dHtml = '', sHtml = '';
    $(".anal-content .anal-right .scene-info .ji input").val(xiaotuObj.sceneInfoContent.seriesNo);
    $(".anal-content .anal-right .scene-info .chang input").val(xiaotuObj.sceneInfoContent.roundNo);
    $(".anal-content .anal-right .scene-info .air .con-box input").val(xiaotuObj.sceneInfoContent.atmosphere);
    $(".anal-content .anal-right .scene-info .in-out .con-box input").val(xiaotuObj.sceneInfoContent.site);
    $(".anal-content .anal-right .scene-info .main-scene .con-box input").val(xiaotuObj.sceneInfoContent.firstLocation);
    // 主要演员
    for (var mai = 0; mai < xiaotuObj.sceneInfoContent.majorRoleNameList.length; mai++) {
        mAHtml += '<div from="from-checkbox" class="con-name"><span>' + xiaotuObj.sceneInfoContent.majorRoleNameList[mai] +
            '</span><i class="icon iconfont">&#xe61c;</i></div>';
    }
    var mAinput = $(".anal-content .anal-right .scene-info .main-actor .con-box .con-name");
    var mAspan = mAinput.find("input").parents(".con-name").siblings(".con-name");
    if (mAspan[0]) {
        mAspan.remove();
    }
    mAinput.before(mAHtml);
    // 特约演员
    for (var sai = 0; sai < xiaotuObj.sceneInfoContent.guestRoleNameList.length; sai++) {
        sAHtml += '<div from="from-checkbox" class="con-name"><span>' + xiaotuObj.sceneInfoContent.guestRoleNameList[sai] +
            '</span><i class="icon iconfont">&#xe61c;</i></div>';
    }
    var sAinput = $(".anal-content .anal-right .scene-info .special-actor .con-box .con-name");
    var sAspan = sAinput.find("input").parents(".con-name").siblings(".con-name");
    if (sAspan[0]) {
        sAspan.remove();
    }
    sAinput.before(sAHtml);
    // 群众演员
    for (var pai = 0; pai < xiaotuObj.sceneInfoContent.massRoleNameList.length; pai++) {
        pAHtml += '<div from="from-checkbox" class="con-name"><span>' + xiaotuObj.sceneInfoContent.massRoleNameList[pai] +
            '</span><i class="icon iconfont">&#xe61c;</i></div>';
    }
    var pAinput = $(".anal-content .anal-right .scene-info .public-actor .con-box .con-name");
    var pAspan = pAinput.find("input").parents(".con-name").siblings(".con-name");
    if (pAspan[0]) {
        pAspan.remove();
    }
    pAinput.before(pAHtml);
    // 服装
    for (var di = 0; di < xiaotuObj.sceneInfoContent.clothesNameList.length; di++) {
        dHtml += '<div from="from-checkbox" class="con-name"><span>' + xiaotuObj.sceneInfoContent.clothesNameList[di] +
            '</span><i class="icon iconfont">&#xe61c;</i></div>'
    }
    var dinput = $(".anal-content .anal-right .scene-info .dress .con-box .con-name");
    var dspan = dinput.find("input").parents(".con-name").siblings(".con-name");
    if (dspan[0]) {
        dspan.remove();
    }
    dinput.before(dHtml);
    // 道具
    for (var si = 0; si < xiaotuObj.sceneInfoContent.propNameList.length; si++) {
        sHtml += '<div from="from-checkbox" class="con-name"><span>' + xiaotuObj.sceneInfoContent.propNameList[si] +
            '</span><i class="icon iconfont">&#xe61c;</i></div>'
    }
    var sinput = $(".anal-content .anal-right .scene-info .stage .con-box .con-name");
    var sspan = sinput.find("input").parents(".con-name").siblings(".con-name");
    if (sspan[0]) {
        sspan.remove();
    }
    sinput.before(sHtml);

    // 给多选框中的叉注册点击事件  删除标签
    multipleChaClick()
}

// 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
function getPlayContentInfoTwo(param) {
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
                // $(".anal-content .anal-left .anal-left-totleword span").html(data.wordCount)
                $(".anal-content .anal-left .anal-left-totleword").html('字数 : <span>' + data.wordCount + '</span>字')
            }
        }
    }, function (error) {
    }, true, function (complete) {

        // 关闭loading动画
        $(".show-loading-shade").css("display", "none")

        // 获取场次详细信息（获取演员列表）
        getSpaceDetails(param)

        // 获取标记列表
        getMarkList(param)

        // 获取广告分析列表
        getAdAnalList(param)

        // 左侧颜色数据的操作
        operateColorData()
    })
}

// 保存场景信息
function saveSceneInfo() {
    var dom = $(".anal-content .anal-right .scene-info");
    $(".anal-content .anal-right .scene-info .save").click(function () {
        var mainActor = [], specialActor = [], publicActor = [], dress = [], stage = [];
        dom.find(".main-actor .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                mainActor.push(val);
            }
        })
        dom.find(".special-actor .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                specialActor.push(val);
            }
        })
        dom.find(".public-actor .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                publicActor.push(val);
            }
        })
        dom.find(".dress .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                dress.push(val);
            }
        })
        dom.find(".stage .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                stage.push(val);
            }
        })

        // 获取场次id
        var jiNum = parseInt(dom.find(".ji input").val());
        var changNum = dom.find(".chang input").val();
        var changdeId;
        if (!!xiaotuObj.seriesNoList[jiNum]) {
            var conArr = xiaotuObj.seriesNoList[jiNum];
            var len = conArr.length;
            for (var i = 0; i < len; i++) {
                if (conArr[i].roundNo == changNum) {
                    changdeId = conArr[i].id;
                }
            }
            if (!changdeId) {
                modelWindow("请输入已有的集跟场 1");
                return;
            }
        } else {
            modelWindow("请输入已有的集跟场 2");
            return;
        }

        var data = {
            id: changdeId,
            seriesNo: parseInt(dom.find(".ji input").val()),
            roundNo: dom.find(".chang input").val(),
            atmosphere: dom.find(".air input").val(),
            site: dom.find(".in-out input").val(),
            firstLocation: dom.find(".main-scene input").val(),
            majorRoleNameList: mainActor,
            guestRoleNameList: specialActor,
            massRoleNameList: publicActor,
            clothesNameList: dress,
            propNameList: stage,
            remark: dom.find(".remake input").val()
        }
        doPost(basePath + '/playRound/updateRound', JSON.stringify(data), function (data) {
            if (!!data) {
                if (data.status == 0) {

                    // 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
                    getPlayContentInfoTwo(xiaotuObj.roundId)
                    // 重新更新左侧数据之后，让上面颜色重置
                    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");

                    modelWindow('保存成功', 1000)
                } else {
                    modelWindow(data.message)
                }
            }
        }, function (error) {
        }, true, function (complete) {
        }, null, null, 'application/json;charset=utf-8')
    })
}

// 当关键词的input框获取焦点，并且有键盘的弹起的时候，给input上的flag设置成true
function focusKeyupInputToTrue() {
    $(".anal-content .anal-right .play-sign .play-sign-bottom .keyword input").focus(function () {
        var that = $(this);
        $(document).keyup(function () {
            that.attr("word_x", '1');
            // 这个标识用来判断保存剧本标记接口要不要传id
            // modify为false的时候，说明是从左侧划词来的关键词或填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            that.attr("modify", "false")
        });
    })
}

// 保存剧本标记
function addPlaySign() {
    var dom = $(".anal-content .anal-right .play-sign");
    $(".anal-content .anal-right .play-sign .add").click(function () {
        var productNameArr = [], productNameArrTwo = [], productNameStr = '', roleArr = [], roleStr = '',
            keyword = dom.find('.keyword input').val(), gid = '', obj = {};
        flagid = $(".project-list-five .five-content .five-content-space li.active").attr("flagid"), arrList = [];
        dom.find(".product-name .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                var gid = $(value).find("span").attr("gid");
                obj = {
                    'id': gid,
                    'goods': val
                }
                productNameArr.push(obj);
                productNameArrTwo.push(val);
                productNameStr = productNameArrTwo.join(",");
            }
        })
        dom.find(".role .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                roleArr.push(val);
                roleStr = roleArr.join(",");
            }
        })

        $(".anal-content .anal-right .play-sign .play-sign-top  .play-sign-con").each(function (index, value) {
            if (keyword == $(value).find(".keyword").html()) {
                arrList.push(keyword);
            }
        })

        var len = dom.find('.play-sign-bottom .keyword input').attr("word_x");
        var dataObj = {
            roundId: flagid,
            word: keyword,
            word_x: len,
            goodsList: productNameArr,
            roleNameList: roleArr,
            description: dom.find(".play-sign-bottom .describe input").val()
        }
        var inputDom = $(".anal-content .anal-right .play-sign .play-sign-bottom .keyword input");
        if (inputDom.attr("modify") == 'true') {
            // 这个标识用来判断保存剧本标记接口要不要传id
            // modify为false的时候，说明是从左侧划词来的关键词或填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            dataObj.id = inputDom.attr("signid")
        }

        doPost(basePath + '/playMark/saveMark', JSON.stringify(dataObj), function (data) {
            if (data.status == 0) {

                // 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
                getPlayContentInfoTwo(xiaotuObj.roundId)
                // 重新更新左侧数据之后，让上面颜色重置
                $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");

                // 重新渲染剧本标记上面的列表
                refreshPlaySignTop()

                // 保存成功之后
                // 清空关键字
                $(".anal-content .anal-right .play-sign .play-sign-bottom .keyword input").val('');
                // 清空产品名称
                $(".anal-content .anal-right .play-sign .play-sign-bottom .product-name .con-box .con-name span").parents(".con-name").remove();
                // 清空角色
                $(".anal-content .anal-right .play-sign .play-sign-bottom .role .con-box .con-name span").parents(".con-name").remove();
                // 清空描述
                $(".anal-content .anal-right .play-sign .play-sign-bottom .describe input").val('');

                // 把关键字input框上的signid给移除
                inputDom.attr("signid", '')

                // 关闭中间的搜索数据
                $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").trigger("click")

            } else {
                modelWindow(data.message);
            }
        }, function (error) {
        }, true, function (complete) {
        }, null, null, 'application/json;charset=utf-8')
    })
}

// 渲染剧本标记上面的列表
function refreshPlaySignTop() {
    var url = '/playMark/queryMarkList';
    var params = {
        // roundId: xiaotuObj.roundId
        roundId: xiaotuObj.roundId
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 剧本标记的内容
                xiaotuObj.playSignContent = data;
                var dom = $(".anal-content .anal-right .play-sign .play-sign-top .play-sign-con")
                if (dom[0]) {
                    dom.remove();
                }
                var pSHtml = '', pSContent = data.markList, kGoodsList = '', kGoodsListId = '', lRoleNameList = '';

                for (var k = 0; k < pSContent.length; k++) {

                    if (pSContent[k].roleNameList == null) {
                        pSContent[k].roleNameList = []
                    }

                    pSHtml += '<ul signid="' + pSContent[k].id + '" class="play-sign-con">';
                    pSHtml += '<li class="order">' + (k + 1) + '</li>';
                    pSHtml += '<li title="' + pSContent[k].word + '" word_x="' + pSContent[k].word_x + '" class="keyword">' + pSContent[k].word + '</li>';
                    for (var g = 0; g < pSContent[k].goodsList.length; g++) {
                        if (g == 0) {
                            kGoodsList += pSContent[k].goodsList[g].goods
                            kGoodsListId += pSContent[k].goodsList[g].id
                        } else {
                            kGoodsList += ',' + pSContent[k].goodsList[g].goods
                            kGoodsListId += ',' + pSContent[k].goodsList[g].id
                        }
                    }
                    pSHtml += '<li idList="' + kGoodsListId + '" title="' + kGoodsList + '" class="product-name">' + kGoodsList + '</li>';
                    for (var l = 0; l < pSContent[k].roleNameList.length; l++) {
                        if (l == 0) {
                            lRoleNameList += pSContent[k].roleNameList[l];
                        } else {
                            lRoleNameList += ',' + pSContent[k].roleNameList[l];
                        }
                    }
                    pSHtml += '<li title="' + lRoleNameList + '" class="role">' + lRoleNameList + '</li>';
                    pSHtml += '<li title="' + pSContent[k].description + '" class="describe">' + pSContent[k].description + '</li>';
                    pSHtml += '<li class="operate"><i class="icon iconfont pen">&#xe66d;</i><i class="icon iconfont remove">&#xe61b;</i></li></ul>';
                    kGoodsList = '', lRoleNameList = '';
                }
                $(".anal-content .anal-right .play-sign .play-sign-top").append(pSHtml);

                // 修改剧本标记
                updatePlaySign()

                // 删除剧本标记
                removePlaySign()
            }
        }
    })
}

// 修改剧本标记
function updatePlaySign() {
    var penDom = $(".anal-content .anal-right .play-sign .play-sign-top .play-sign-con .operate .pen");
    if (penDom[0]) {
        // 判断dom有没有绑定点击事件，防止多次注册点击事件
        // judgeDomEvent(penDom, 'click');  发现之后改的
        penDom.off("click")

        penDom.click(function () {
            // 关闭中间的搜索数据
            $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").trigger("click")

            var domUl = $(this).parents(".play-sign-con"), domOperate = $(this).parents(".operate");
            var keyWord = domOperate.siblings(".keyword").html(),
                word_x = domOperate.siblings(".keyword").attr("word_x");
            var productName = domOperate.siblings(".product-name").html(),
                productNameId = domOperate.siblings(".product-name").attr("idList"),
                role = domOperate.siblings(".role").html(), describe = domOperate.siblings(".describe").html();
            // 关键字输入框
            var keyWordInputDom = $(".anal-content .anal-right .play-sign .play-sign-bottom .keyword input");
            // 产品名称输入框
            var productNameDom = $(".anal-content .anal-right .play-sign .play-sign-bottom .product-name .con-box");
            // 角色输入框
            var roleDom = $(".anal-content .anal-right .play-sign .play-sign-bottom .role .con-box");
            var describeInputDom = $(".anal-content .anal-right .play-sign .play-sign-bottom .describe input");
            keyWordInputDom.val(keyWord)
            keyWordInputDom.attr("word_x", domOperate.siblings(".keyword").attr("word_x"))
            keyWordInputDom.attr("signid", domUl.attr("signid"))
            describeInputDom.val(describe)
            if (productNameDom.find("span").parents(".con-name")[0]) {
                productNameDom.find("span").parents(".con-name").remove()
            }
            if (roleDom.find("span").parents(".con-name")[0]) {
                roleDom.find("span").parents(".con-name").remove()
            }
            if (productName.indexOf(",") == -1) {
                productNameDom.find("input").parents(".con-name")
                    .before('<div form="from-checkbox" class="con-name"><span gid="' + productNameId + '">' + productName + '</span><i class="icon iconfont">&#xe61c;</i></div>')
            } else {
                var proArr = productName.split(","), proArrHtml = '', proArrId = productNameId.split(",");
                for (var i = 0; i < proArr.length; i++) {
                    proArrHtml += '<div form="from-checkbox" class="con-name"><span gid="' + proArrId[i] + '">' + proArr[i] + '</span><i class="icon iconfont">&#xe61c;</i></div>';
                }
                productNameDom.find("input").parents(".con-name").before(proArrHtml)
            }
            if (role.indexOf(",") == -1) {
                roleDom.find("input").parents(".con-name")
                    .before('<div form="from-checkbox" class="con-name"><span>' + role + '</span><i class="icon iconfont">&#xe61c;</i></div>')
            } else {
                var roleArr = role.split(","), roleArrHtml = '';
                for (var j = 0; j < roleArr.length; j++) {
                    roleArrHtml += '<div class="con-name"><span>' + roleArr[j] + '</span><i class="icon iconfont">&#xe61c;</i></div>'
                }
                roleDom.find("input").parents(".con-name").before(roleArrHtml)
            }
            // 这个标识用来判断保存剧本标记接口要不要传id
            // modify为false的时候，说明是从左侧划词来的关键词或填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            keyWordInputDom.attr("modify", "true")

            // 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值  .product-name跟.role的
            clickXRefreshArrBox($(".anal-content>.anal-right>.anal-search>.multiple-checkbox"),
                $(".anal-content>.anal-right>.play-sign>.play-sign-bottom>.product-name"))
            clickXRefreshArrBox($(".anal-content>.anal-right>.anal-search>.multiple-checkbox"),
                $(".anal-content>.anal-right>.play-sign>.play-sign-bottom>.role"))
        })
    }
}

// 删除剧本标记
function removePlaySign() {
    var removeDom = $(".anal-content .anal-right .play-sign .play-sign-top .play-sign-con .operate .remove");
    if (removeDom[0]) {
        // 判断dom有没有绑定点击事件，防止多次注册点击事件
        // judgeDomEvent(removeDom, 'click');  发现之后改的
        removeDom.off("click")

        removeDom.click(function () {
            var that = this;
            var signid = $(that).parents('.play-sign-con').attr("signid");
            doPost(basePath + '/playMark/removeMark', {id: signid}, function (data) {
                if (data.status == 0) {
                    var dom = $(".anal-content .anal-right .play-sign .play-sign-top .play-sign-con")
                    if (dom[0]) {
                        dom.each(function (index, value) {
                            $(value).find(".order").html(index + 1)
                        })
                    }

                    $(that).parents('.play-sign-con').remove();

                    // 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
                    getPlayContentInfoTwo(xiaotuObj.roundId)
                    // 重新更新左侧数据之后，让上面颜色重置
                    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");
                }
            })
        })
    }
}

// 保存广告植入
function saveAdImport() {
    $(".anal-content .anal-right .ad-import .ad-import-bottom .save").click(function () {
        var roleArr = [], importType = {}, goodsList = {},
            dom = $(".anal-content .anal-right .ad-import .ad-import-bottom"), name = dom.find('.name>input').val(),
            flagid = $(".project-list-five .five-content .five-content-space li.active").attr("flagid");
        dom.find(".role .con-box .con-name").each(function (index, value) {
            if ($(value).find("span")[0]) {
                var val = $(value).find("span").html();
                roleArr.push(val);
            }
        })
        importType = {
            id: dom.find(".import-ploy input").attr("gid"),
            name: dom.find(".import-ploy input").val()
        }
        goodsList = {
            id: dom.find(".product input").attr("gid"),
            name: dom.find(".product input").val()
        }
        var dataObj = {
            name: name,
            playRound: {id: flagid},
            roleNameList: roleArr,
            implantMode: importType,
            goods: goodsList,
            desc: dom.find(".describe input").val()
        }
        var inputDom = $(".anal-content .anal-right .ad-import .ad-import-bottom .import input");
        if (inputDom.attr("modify") == 'true') {
            // 这个标识用来判断保存广告植入接口要不要传id
            // modify为false的时候，说明是填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            dataObj.id = inputDom.attr("signid")
        } else {

        }
        doPost(basePath + '/implantRecord/saveRecord', JSON.stringify(dataObj), function (data) {
            if (data.status == 0) {

                // 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
                getPlayContentInfoTwo(xiaotuObj.roundId)
                // 重新更新左侧数据之后，让上面颜色重置
                $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");

                // 渲染广告植入上面的列表
                refreshAdImportTop()

                // 保存成功之后
                // 清空名字
                dom.find(".name input").val('');
                // 清空植入手法
                dom.find(".role .con-box .con-name span").parents(".con-name").remove();
                // 清空植入手法
                dom.find(".import-ploy .con-box .con-name input").val('');
                // 清空产品
                dom.find(".product .con-box .con-name input").val('');
                // 清空描述
                dom.find(".describe input").val('');

                // 把关键字input框上的signid给移除
                inputDom.attr("signid", '')

                // 关闭中间的搜索数据
                $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").trigger("click")

            } else {
                modelWindow(data.message);
            }
        }, function (error) {
        }, true, function (complete) {
        }, null, null, 'application/json;charset=utf-8')
    })
}

// 渲染广告植入上面的列表
function refreshAdImportTop() {
    var url = '/implantRecord/queryRecordList';
    var params = {
        roundId: xiaotuObj.roundId
    }
    doPost(basePath + url, params, function (data) {
        if (data.status == 0) {
            var data = data.data;
            if (data) {
                // 广告植入的内容
                xiaotuObj.adImportContent = data;
                var aIHtml = '', aIContent = data.recordList, sRoleNameList = '';
                for (var s = 0; s < aIContent.length; s++) {

                    if (aIContent[s].roleNameList == null) {
                        aIContent[s].roleNameList = []
                    }

                    aIHtml += '<div signid="' + aIContent[s].id + '" class="ad-import-top-con"><div class="con-title">';
                    aIHtml += '<h6>' + aIContent[s].name + '</h6>';
                    aIHtml += '<span signid="' + aIContent[s].implantMode.id + '" class="stage">' + aIContent[s].implantMode.name + '</span>';
                    aIHtml += '<span signid="' + aIContent[s].goods.id + '" class="phone">' + aIContent[s].goods.goods + '</span>';
                    aIHtml += '<i class="icon iconfont remove">&#xe61b;</i><i class="icon iconfont pen">&#xe66d;</i></div><div class="con-con">';
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
                $(".anal-content .anal-right .ad-import .ad-import-top").html(aIHtml);

                // 修改广告植入
                updateAdImport()

                // 删除广告植入
                removeAdImport()
            }
        }
    })
}

// 修改广告植入
function updateAdImport() {
    var penDom = $(".anal-content .anal-right .ad-import .ad-import-top .ad-import-top-con .con-title .pen");
    if (penDom[0]) {
        // 判断dom有没有绑定点击事件，防止多次注册点击事件
        // judgeDomEvent(penDom, 'click');  发现之后改的
        penDom.off("click")

        penDom.click(function () {
            // 关闭中间的搜索数据
            $(".anal-content>.anal-right>.anal-search>.anal-search-title>i").trigger("click")
            var domTop = $(this).parents(".ad-import-top-con"), name = domTop.find(".con-title h6").html(),
                importType = domTop.find(".con-title .stage").html(), goods = domTop.find(".con-title .phone").html(),
                roleList = domTop.find(".con-con span i").html(), describe = domTop.find(".con-describe i").html();
            var domBottom = $(".anal-content .anal-right .ad-import .ad-import-bottom"),
                nameInput = domBottom.find(".name input"),
                roleInputDom = domBottom.find(".role .con-box .con-name input"),
                importPloy = domBottom.find(".import-ploy .con-box .con-name input"),
                product = domBottom.find(".product .con-box .con-name input"),
                describeBot = domBottom.find(".describe input"), roleSpanDom = domBottom.find(".role .con-box");
            nameInput.val(name)
            nameInput.attr("signid", domTop.attr("signid"))
            importPloy.attr("gid", domTop.find(".con-title .stage").attr("signid"))
            importPloy.val(importType)
            product.attr("gid", domTop.find(".con-title .phone").attr("signid"))
            product.val(goods)
            describeBot.val(describe)
            if (roleSpanDom.find(".con-name span").parents(".con-name")[0]) {
                roleSpanDom.find(".con-name span").parents(".con-name").remove()
            }
            if (roleList.indexOf(",") == -1) {
                roleInputDom.parents(".con-name")
                    .before('<div class="con-name"><span>' + roleList + '</span><i class="icon iconfont">&#xe61c;</i></div>')
            } else {
                var roleListArr = roleList.split(","), roleListHtml = '';
                for (var i = 0; i < roleListArr.length; i++) {
                    roleListHtml += '<div class="con-name"><span>' + roleListArr[i] + '</span><i class="icon iconfont">&#xe61c;</i></div>'
                }
                roleInputDom.parents(".con-name").before(roleListHtml)
            }
            // 这个标识用来判断保存广告植入接口要不要传id
            // modify为false的时候，说明是填写的关键词，不需要传id
            // 为true的时候，说明是从上面修改来的关键词，需要传id
            nameInput.attr("modify", "true")

            // 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值
            clickXRefreshArrBox($(".anal-content>.anal-right>.anal-search>.multiple-checkbox"),
                $(".anal-content>.anal-right>.ad-import>.ad-import-bottom>.role"))
        })
    }
}

// 删除广告植入
function removeAdImport() {
    var removeDom = $(".anal-content .anal-right .ad-import .ad-import-top .ad-import-top-con .con-title .remove");
    if (removeDom[0]) {
        // 判断dom有没有绑定点击事件，防止多次注册点击事件
        // judgeDomEvent(removeDom, 'click');  发现之后改的
        removeDom.off("click")

        removeDom.click(function () {
            var that = this;
            var signid = $(that).parents('.ad-import-top-con').attr("signid");
            doPost(basePath + '/implantRecord/removeRecord', {id: signid}, function (data) {
                if (data.status == 0) {
                    $(that).parents('.ad-import-top-con').remove();

                    // 剧本分析或者广告植入保存或者删除之后更新左侧数据(获取剧本内容信息)
                    getPlayContentInfoTwo(xiaotuObj.roundId)
                    // 重新更新左侧数据之后，让上面颜色重置
                    $(".anal-content .anal-left .anal-left-title .anal-color .anal-color-keyword").removeClass("active");
                } else {
                    modelWindow(data.message)
                }
            })
        })
    }
}

// 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值
function clickXRefreshArrBox(dom1, dom2) {
    if (dom2.find(".con-box>.con-name>i")[0]) {
        // judgeDomEvent(dom2.find(".con-box>.con-name>i"), "click");  发现之后改的
        dom2.find(".con-box>.con-name>i").off("click")

        // 点击右侧框中叉更新其中内容及更新数组中的内容并且同步checkbox的值
        dom2.find(".con-box>.con-name>i").click(function () {
            var arrList = [];
            $(this).parents(".con-name").remove();
            dom2.find(".con-box>.con-name[from='from-checkbox']").each(function (index, value) {
                arrList.push($(value).find("span").html());
            })
            if (!!arrList.length) {
                dom1.find("input").prop("checked", false);
                dom1.each(function (index, value) {
                    for (var j = 0; j < arrList.length; j++) {
                        if ($(value).find("span").html() == arrList[j]) {
                            $(value).find("input").prop("checked", true);
                        }
                    }
                })
            } else {
                dom1.find("input").prop("checked", false);
            }
            return false;
        })
    }
}

// 右侧三部分的取消按钮事件
function clickCancelFunc() {
    // 场景信息的取消按钮
    $(".anal-content .anal-right .scene-info .cancel").click(function () {
        // 渲染右侧场景信息的内容
        refreshSceneContent()
    })
    // 剧本标记的取消按钮
    var playSign = $(".anal-content .anal-right .play-sign .play-sign-bottom")
    playSign.find(".cancel").click(function () {
        playSign.find(".keyword input").val("");
        playSign.find(".product-name .con-box .con-input").siblings(".con-name").remove();
        playSign.find(".role .con-box .con-input").siblings(".con-name").remove();
        playSign.find(".describe input").val("");
    })
    // 广告分析的取消按钮
    var adImport = $(".anal-content .anal-right .ad-import .ad-import-bottom")
    adImport.find(".cancel").click(function () {
        adImport.find(".name input").val("");
        adImport.find(".role .con-box .con-input").siblings(".con-name").remove();
        adImport.find(".import-ploy .con-box .con-name input").val("");
        adImport.find(".product .con-box .con-name input").val("");
        adImport.find(".describe input").val("");
    })
}

// 角色列表
function pickRoleList() {
    $(".project-list-four .four-content .four-right .pick-role").click(function () {
        $(".shade-role").css({
            "backgroundColor": "rgba(0,0,0,0.3)",
            "opacity": "1",
            "transition": "all 0.15s ease-in",
            "zIndex": "100"
        })
        $(".shade-role .shade-loading").css("display", "block")

        doPost(basePath + '/playContent/extractRoleList', {}, function (data) {
            $(".shade-role .shade-loading").css("display", "none")
            $(".shade-role .shade-con-role").css({
                "opacity": "1",
                "top": "50%",
                "marginTop": "-300px",
                "transition": "all 0.3s ease-in",
                "zIndex": "100"
            })
            if (data.status == 0) {
                var data = data.data
                var html = "", roleList = data.roleList, len = roleList.length;
                for (var i = 0; i < len; i++) {
                    html += '<li><a href="javascript:;"><span class="content">'+roleList[i].roleName+'</span>' +
                        '<span class="gather">('+roleList[i].roundCount+')</span></a>' +
                        '<i class="icon iconfont">&#xe600;</i></li>';
                }
                $(".shade-role .shade-con-role .panel-body h5 span").html(len)
                $(".shade-role .shade-con-role .panel-body .role-details").html(html);

                // 全部按钮
                $(".shade-role .shade-con-role .panel-body .role-all li").click(function () {
                    if ($(this).hasClass("active")) {
                        $(this).removeClass("active")
                        $(".shade-role .shade-con-role .panel-body .role-details li").removeClass("active")
                    }else {
                        $(this).addClass("active")
                        $(".shade-role .shade-con-role .panel-body .role-details li").addClass("active")
                    }
                })
                // 每个按钮
                $(".shade-role .shade-con-role .panel-body .role-details li").click(function () {
                    if ($(this).hasClass("active")) {
                        $(this).removeClass("active")
                    }else {
                        $(this).addClass("active")
                    }
                })

                // 确定按钮
                $(".shade-role .shade-con-role .panel-foot .confirm-btn").click(function () {
                    var roleListArr = [];
                    $(".shade-role .shade-con-role .panel-body .role-details li").each(function (index, value) {
                        var hasActive = $(value).hasClass("active");
                        var text = $(value).find(".content").html();
                        if (hasActive) {
                            roleListArr.push(text)
                        }
                    })
                    if (roleListArr.length != 0) {
                        doPost(basePath + '/playRole/saveAnalyseRole', {roleList: roleListArr}, function (data) {
                            if (data.status == 0) {
                                // 关闭角色列表
                                $(".shade-role .shade-con-role .panel-heading h4 i").click()
                                modelWindow('角色添加成功', 1000)
                            }else {
                                modelWindow(data.message)
                            }
                        }, function (error) {
                            console.log(error)
                        })
                    } else {
                        modelWindow("请在角色表中选中要加入的角色", 1000)
                    }
                })
                // 取消按钮
                $(".shade-role .shade-con-role .panel-foot .cancel-btn").click(function () {
                    // 关闭角色列表
                    $(".shade-role .shade-con-role .panel-heading h4 i").click()
                })
            }
        }, function (error) {
            console.log(error)
        })

    })
}

// 关闭角色列表
function closePickRoleList() {
    $(".shade-role .shade-con-role .panel-heading h4 i").click(function () {
        $(".shade-role").css({
            "backgroundColor": "transparent",
            "opacity": "0",
            "transition": "all 0.15s ease-in"
        })
        $(".shade-role .shade-con-role").css({
            "opacity": "0",
            "top": "-600px",
            "marginTop": "0px",
            "transition": "all 0.3s ease-in",
            "zIndex": "-111"
        })
        setTimeout(function () {
            $(".shade-role").css({
                "zIndex": "-111"
            })
        }, 150)
    })
}