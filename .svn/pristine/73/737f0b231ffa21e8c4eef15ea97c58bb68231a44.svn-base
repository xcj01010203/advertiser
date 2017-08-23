var menuName; //菜单名称
var menuLv;   //菜单等级
var mid;   //菜单父ID
var fidMenuName; //上一级单名称
var menuId;  //菜单编号
var menuURL;  //菜单地址
var menuExplain; //菜单描述
var cssName;  //菜单图标样式
var fid; //菜单父ID
var ids = [];

var menuModel = {};

$(document).ready(function () {
    getMenuList();

})

//加载菜单信息列表
function getMenuList() {
    $.ajax({
        url: basePath + "/menu/queryMenu",
        type: "POST",
        dataType: "json",
        success: function (data) {
            var html = '';
            if (data.status == 0) {
                var data = data.data;
                if (data.menu) {
                    for (var i = 0; i < data.menu.length; i++) {
                        html += '<ul flag="one" menuLv="' + data.menu[i].menuLv + '" fid="' + data.menu[i].fid + '" id="' + data.menu[i].id + '" class="one userManage-menu userManage-td">';
                        html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data.menu[i].id + '"/>'
                            + data.menu[i].menuId + '</li>';
                        if (!!data.menu[i].nodes && data.menu[i].nodes.length != 0) {
                            html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].menuName + '</span></a></li>';
                        } else {
                            html += '<li class="menu-name"><a href="javascript:;"><span>' + data.menu[i].menuName + '</span></a></li>';
                        }
                        html += '<li class="menu-describe">' + data.menu[i].menuExplain + '</li>';
                        html += '<li title="' + data.menu[i].menuURL + '" class="menu-url">' + data.menu[i].menuURL + '</li>';
                        html += '<li class="menu-icon">' + data.menu[i].cssName + '</li>';
                        html += '</ul>';
                        if (!!data.menu[i].nodes && data.menu[i].nodes.length != 0) {
                            for (var j = 0; j < data.menu[i].nodes.length; j++) {
                                html += '<ul flag="two" menuLv="' + data.menu[i].nodes[j].menuLv + '" fid="' + data.menu[i].nodes[j].fid + '" id="' + data.menu[i].nodes[j].id + '" class="two userManage-menu userManage-td hide">';
                                html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data.menu[i].nodes[j].id + '"/>'
                                    + data.menu[i].nodes[j].menuId + '</li>';
                                if (!!data.menu[i].nodes[j].nodes && data.menu[i].nodes[j].nodes.length != 0) {
                                    html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                        '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].nodes[j].menuName + '</span></a></li>';
                                } else {
                                    html += '<li class="menu-name"><a href="javascript:;"><span>' + data.menu[i].nodes[j].menuName + '</span></a></li>';
                                }
                                html += '<li class="menu-describe">' + data.menu[i].nodes[j].menuExplain + '</li>';
                                html += '<li title="' + data.menu[i].nodes[j].menuURL + '" class="menu-url">' + data.menu[i].nodes[j].menuURL + '</li>';
                                html += '<li class="menu-icon">' + data.menu[i].nodes[j].cssName + '</li>';
                                html += '</ul>';
                                if (!!data.menu[i].nodes[j].nodes && data.menu[i].nodes[j].nodes.length != 0) {
                                    for (var k = 0; k < data.menu[i].nodes[j].nodes.length; k++) {
                                        html += '<ul flag="three" menuLv="' + data.menu[i].nodes[j].nodes[k].menuLv + '" fid="' + data.menu[i].nodes[j].nodes[k].fid + '" id="' + data.menu[i].nodes[j].nodes[k].id + '" class="three userManage-menu userManage-td hide">';
                                        html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data.menu[i].nodes[j].nodes[k].id + '"/>'
                                            + data.menu[i].nodes[j].nodes[k].menuId + '</li>';
                                        if (!!data.menu[i].nodes[j].nodes[k].nodes && (data.menu[i].nodes[j].nodes[k].nodes.length != 0)) {
                                            html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                                '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].nodes[j].nodes[k].menuName + '</span></a></li>';
                                        } else {
                                            html += '<li class="menu-name"><a href="javascript:;"><span>' + data.menu[i].nodes[j].nodes[k].menuName + '</span></a></li>';
                                        }
                                        html += '<li class="menu-describe">' + data.menu[i].nodes[j].nodes[k].menuExplain + '</li>';
                                        html += '<li title="' + data.menu[i].nodes[j].nodes[k].menuURL + '" class="menu-url">' + data.menu[i].nodes[j].nodes[k].menuURL + '</li>';
                                        html += '<li class="menu-icon">' + data.menu[i].nodes[j].nodes[k].cssName + '</li>';
                                        html += '</ul>';
                                        if (!!data.menu[i].nodes[j].nodes[k].nodes && data.menu[i].nodes[j].nodes[k].nodes.length != 0) {
                                            for (var g = 0; g < data.menu[i].nodes[j].nodes[k].nodes.length; g++) {
                                                html += '<ul flag="four" menuLv="' + data.menu[i].nodes[j].nodes[k].nodes[g].menuLv + '" fid="' + data.menu[i].nodes[j].nodes[k].nodes[g].fid + '" id="' + data.menu[i].nodes[j].nodes[k].nodes[g].id + '" class="four userManage-menu userManage-td hide">';
                                                html += '<li class="menu-num"><input name="project-type" value="type1" type="checkbox" id="' + data.menu[i].nodes[j].nodes[k].nodes[g].id + '"/>'
                                                    + data.menu[i].nodes[j].nodes[k].nodes[g].menuId + '</li>';
                                                if (!!data.menu[i].nodes[j].nodes[k].nodes[g].nodes && (data.menu[i].nodes[j].nodes[k].nodes[g].nodes.length != 0)) {
                                                    html += '<li class="menu-name"><a href="javascript:;"><i class="icon iconfont you">&#xe60a;</i>' +
                                                        '<i class="icon iconfont yxia hide">&#xe600;</i><span>' + data.menu[i].nodes[j].nodes[k].nodes[g].menuName + '</span></a></li>';
                                                } else {
                                                    html += '<li class="menu-name"><a href="javascript:;"><span>' + data.menu[i].nodes[j].nodes[k].nodes[g].menuName + '</span></a></li>';
                                                }
                                                html += '<li class="menu-describe">' + data.menu[i].nodes[j].nodes[k].nodes[g].menuExplain + '</li>';
                                                html += '<li class="menu-url">' + data.menu[i].nodes[j].nodes[k].nodes[g].menuURL + '</li>';
                                                html += '<li class="menu-icon">' + data.menu[i].nodes[j].nodes[k].nodes[g].cssName + '</li>';
                                                html += '</ul>';
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            $(".trend-draw").html(html);

            // 树形插件的事件
            treeEvent('one', 'two')
            treeEvent('two', 'three')
            treeEvent('three', 'four')

            // 打开所有，关闭所有树形结构
            closeOrOpenAll()

            //新增菜单 获取readio选中值
            readioSelect()

            // 父级到子级全选
            // parentToChildren()
            parentToChildren("one")
            parentToChildren("two")
            parentToChildren("three")
            parentToChildren("four")

        },
        error: function () {

        }
    });
}

//是否有二级菜单转换
function numToString(num) {
    if (num == 0) {
        return '否';
    }
    return '是';
}
//是否进行功能控制转换
function numToStringIsPerm(num) {
    if (num == 0) {
        return '不控制';
    }
    return '控制';
}

// 树形插件的事件
function treeEvent(str1, str2) {
    $(".trend-draw ." + str1 + " .menu-name a").click(function () {
        var len;
        if ($(this).parents("." + str1).next("." + str2).hasClass("hide")) {
            len = $(this).parents("." + str1).nextUntil("." + str1).length;
            for (var i = 0; i < len; i++) {
                if ($($(this).parents("." + str1).nextUntil("." + str1)[i]).attr("flag") == str2) {
                    $($(this).parents("." + str1).nextUntil("." + str1)[i]).removeClass("hide")
                }
            }
            $(this).find(".you").addClass("hide")
            $(this).find(".yxia").removeClass("hide")
        } else {
            $(this).parents("." + str1).nextUntil("." + str1).addClass("hide")
            $(this).find(".you").removeClass("hide")
            $(this).find(".yxia").addClass("hide")
        }
    })
}

//显示或隐藏添加窗口
function addButton(type) {
    if ($("input[name='project-type']:checked").length > 1) {
        modelWindow("只能选择一个菜单!", 0);
    } else {
        if (type == "icon iconfont" || type == "btn-cancel") {
            $("#addPop").attr("style", "display:none;");
            $("#addShade").attr("style", "display:none;");
            window.location.href = basePath + "/base/forward/user/menuManage";
            
        }else {
            var flag = $(".trend-draw ul .menu-num input").is(':checked');
            if (flag) {
                modelWindowParam("确定在该菜单下创建子菜单吗", 0, null);
            } else {
                modelWindowParam("确定要创建一级菜单吗", 0, null);
            }
            $("#modal-footer .btn-primary").click(function () {
                $("#addShade").removeClass("hide")
                $("#modal-content>.modal-header>button").trigger("click");
            })

        }

    }

    var menu = {};
    var url = basePath + "/menu/queryMaxMenuId";
    menu.menuId = menuId;
    menu.menuLv = menuLv;
    doPostAjax(url, menu, function (data) {
        $("#menuId").val(data.menuId);
        $("#fid").val(menuName);
    })

}

//显示或隐藏修改窗口
function updateButton(type) {
    $("#menuIds").val(menuId);
    $("#menuNames").val(menuName);
    $("#fids").val(fid == "" ? "" : fidMenuName);
    $("#menuURLs").val(menuURL);
    $("#menuExplains").val(menuExplain);
    $("#cssNames").val(cssName);

    if ($("input[name='project-type']:checked").length == 0 || $("input[name='project-type']:checked").length > 1) {
        modelWindow("请选择要修改的菜单!", 0);
    } else {
        if (type == "userManage-box-btn") {
            $("#updatePop").attr("style", "display:block;");
            $("#updateShade").attr("style", "display:block;");
        }
        if (type == "icon iconfont" || type == "btn-cancel") {
            $("#updatePop").attr("style", "display:none;");
            $("#updateShade").attr("style", "display:none;");
        }
    }

}

//添加新的菜单
function addMenu() {
    menuModel.menuId = $("#menuId").val();
    menuModel.menuName = $("#menuName").val();
    menuModel.menuExplain = $("#menuExplain").val();
    menuModel.fid = mid;
    if (mid != null && mid != "" && mid != 'undefined') {
        menuLv++;
    } else if (menuLv == 'undefined' || menuLv == null || menuLv == "") {
        menuLv = 0;
    } else {
        menuLv
    }
    menuModel.menuLv = menuLv;
    menuModel.menuURL = $("#menuURL").val();
    menuModel.cssName = $("#cssName").val();

    var url = basePath + "/menu/saveMenu";
    doPostAjax(url, menuModel, function (data) {
        window.location.href = basePath + "/base/forward/user/menuManage";
    })

    //新增成功隐藏窗口
    $("#addPop").css("display", "none");
    $("#addShade").css("display", "none");

}

function updateMenu() {
    var menu = {};
    menu.menuId = $("#menuIds").val();
    menu.menuName = $("#menuNames").val();
    menu.menuExplain = $("#menuExplains").val();
    menu.id = mid;
    menu.menuURL = $("#menuURLs").val();
    menu.cssName = $("#cssNames").val();
    var url = basePath + "/menu/updateMenu";
    //调用base.js  ajax
    doPost(url, menu, function (data) {
        modelWindow("修改成功", 0);
        getMenuList();
    })
    //修改成功隐藏窗口
    $("#updatePop").css("display", "none");
    $("#updateShade").css("display", "none");

}

//删除菜单信息
function deleteButton() {
    //复选框未选中时提示
    if ($("input[name='project-type']:checked").length == 0) {
        modelWindow("请选择一行记录进行删除", 0);
    } else {
        modelWindowParam("该操作将删除该节点以及该节点下面的所有节点，确认删除？", 0, "removeModel");
    }
}

var menuIdstr = "";
function removeModel() {
    menuIdstr = ids.join(",")
    var url = basePath + "/menu/removeMenu";
    //调用base.js  ajax
    doPost(url, {menuids: menuIdstr}, function (data) {
    	modelWindow("删除成功!", 0);
    	window.location.href= basePath+"/base/forward/user/menuManage";
     })
    menuIdstr = '';
}

// 打开所有，关闭所有树形结构
function closeOrOpenAll() {
    $(".new-project .right .project-info .userManage-box .close-all").click(function () {
        $(".trend-draw .two").addClass("hide")
        $(".trend-draw .three").addClass("hide")
        $(".trend-draw .four").addClass("hide")
        $(".trend-draw a .you").removeClass("hide")
        $(".trend-draw a .yxia").addClass("hide")
    })
    $(".new-project .right .project-info .userManage-box .open-all").click(function () {
        $(".trend-draw .two").removeClass("hide")
        $(".trend-draw .three").removeClass("hide")
        $(".trend-draw .four").removeClass("hide")
        $(".trend-draw a .you").addClass("hide")
        $(".trend-draw a .yxia").removeClass("hide")
    })
}

//新增菜单 判断readio是否选中
function readioSelect() {
    //判断readio是否选中
    $(".project-info .trend-draw .menu-num input").click(function () {
        $(".project-info .trend-draw .userManage-td").each(function (index, value) {
            if ($(value).find("input[name='project-type']").is(':checked')) {
                menuName = $(this).find(".menu-name a span").html();
                menuLv = $(this).attr("menulv");
                menuURL = $(this).find(".menu-url").text();
                menuExplain = $(this).find(".menu-describe").html();
                cssName = $(this).find(".menu-icon").html();
                mid = $(this).attr("id");
                fid = $(this).attr("fid");
                menuId = $(this).find(".menu-num").text();


                var valueOne = $(value).attr("flag"), valueTwo = $(value).attr("flag");
                var value = $(value);
                while (valueOne == valueTwo) {
                    valueTwo = value.prev().attr("flag");
                    value = value.prev();
                }
                fidMenuName = value.find(".menu-describe").html();
            }

        })
    })
}


//父级到子级全选
function parentToChildren(str) {
    $(".trend-draw ." + str + " .menu-num input").click(function () {
        ids = [];
        var len = $(this).parents("." + str).nextUntil("." + str).length;
        if ($(this).is(":checked")) {
            for (var j = 0; j < len; j++) {
                var child = $($(this).parents("." + str).nextUntil("." + str)[j]).attr("flag");
                var $inputDom = $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input");
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "three") {
                    if (child == "four") {
                        $inputDom.attr("disabled", "true");
                    }
                } else if (str == "four") {
                }

                $inputDom.removeAttr("checked");
            }
        } else {
            for (var j = 0; j < len; j++) {
                var child = $($(this).parents("." + str).nextUntil("." + str)[j]).attr("flag");
                var $inputDom = $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input");
                if (str == "one") {
                    if (child == "two" || child == "three" || child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "two") {
                    if (child == "three" || child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "three") {
                    if (child == "four") {
                        $inputDom.removeAttr("disabled");
                    }
                } else if (str == "four") {
                }

                $inputDom.removeAttr("checked");
            }
        }
        $(".project-info .trend-draw .menu-num input").each(function (index, value) {
            if ($(value).is(":checked")) {
                ids.push($(value).parents("ul").attr("id"));
                var cStr = $(value).parents("ul").attr("flag");
                for (var j = 0; j < 3; j++) {
                    var child = $($(value).parents("."+cStr).nextUntil("." + cStr)[j]).attr("flag");
                    var $inputDom = $($(value).parents("."+cStr).nextUntil("." + cStr)[j]).find(".menu-num input");
                    if (cStr == "one") {
                        if (child == "two" || child == "three" || child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "two") {
                        if (child == "three" || child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "three") {
                        if (child == "four") {
                            var id = $inputDom.attr("id");
                            ids.push(id);
                        }
                    } else if (cStr == "four") {

                    }
                }
            }
        });

        // $(".project-info .trend-draw .menu-num input").each(function (index, value) {
        //     var len = $(this).parents("." + str).nextUntil("." + str).length;
        //     if ($(this).is(":checked")) {
        //         var id_fid = "";
        //         for (var i = 0; i < len; i++) {
        //             var child = $($(this).parents("." + str).nextUntil("." + str)[i]).attr("flag")
        //             if (str == "one") {
        //                 if (child == "two" || child == "three" || child == "four") {
        //                     id_fid = $($(this).parents("." + str).nextUntil("." + str)[i]).find(".menu-num input").prop("checked", "true")
        //                 }
        //             } else if (str == "two") {
        //                 if (child == "three" || child == "four") {
        //                     id_fid = $($(this).parents("." + str).nextUntil("." + str)[i]).find(".menu-num input").prop("checked", "true")
        //                 }
        //             } else if (str == "three") {
        //                 if (child == "four") {
        //                     id_fid = $($(this).parents("." + str).nextUntil("." + str)[i]).find(".menu-num input").prop("checked", "true")
        //                 }
        //             } else {
        //             }
        //         }
        //     } else {
        //         for (var j = 0; j < len; j++) {
        //             var child = $($(this).parents("." + str).nextUntil("." + str)[j]).attr("flag")
        //             if (str == "one") {
        //                 if (child == "two" || child == "three" || child == "four") {
        //                     $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input").removeAttr("checked")
        //                 }
        //             } else if (str == "two") {
        //                 if (child == "three" || child == "four") {
        //                     $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input").removeAttr("checked")
        //                 }
        //             } else if (str == "three") {
        //                 if (child == "four") {
        //                     $($(this).parents("." + str).nextUntil("." + str)[j]).find(".menu-num input").removeAttr("checked")
        //                 }
        //             } else {
        //             }
        //         }
        //     }
        // });

        // $(".project-info .trend-draw ul").each(function (index, value) {
        //     var isCheck = $(value).find(".menu-num input").is(":checked");
        //     if (isCheck) {
        //         idsArr.push($(value).attr("id"))
        //     }
        // })
    })
}


function checkboxArr() {
    ids = [];
    $(".trend-draw ul").each(function (index, value) {
        if ($(value).find("li input").is(":checked")) {
            ids.push($(value).find("li input").attr("id"))
        }
    })
}