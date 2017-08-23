$(function () {



    //获取项目列表
    loadProjectList();

    if (GetRequest().type == undefined) {
        // 全部
        $(".project-list ul li:eq(0)").addClass("active").siblings("li").removeClass("active")
    } else if (GetRequest().type == 1) {
        // 电视剧
        $(".project-list ul li:eq(1)").addClass("active").siblings("li").removeClass("active")
    } else if (GetRequest().type == 2) {
        // 电影
        $(".project-list ul li:eq(2)").addClass("active").siblings("li").removeClass("active")
    } else if (GetRequest().type == 3) {
        // 网剧
        $(".project-list ul li:eq(3)").addClass("active").siblings("li").removeClass("active")
    } else if (GetRequest().type == 6) {
        // 网大
        $(".project-list ul li:eq(4)").addClass("active").siblings("li").removeClass("active")
    } else {
    }

    // LOGO图片
    $(".header .center-margin > a").attr("href", "javascript:;");
    // $(".header .center-margin > a").css("cursor", "default");
    $(".header .center-margin > a img").css("cursor", "default");
    // 我的项目
    $(".header .center-margin .header-content .header-play a").attr("href", "javascript:;");
    $(".header .center-margin .header-content .header-play a").css({
        "color": "#29d9c2",
        "cursor": "default"
    });
    // 具体项目名称
    $(".header .center-margin .header-content .header-name a").css("color", "#333");
});

//项目列表
function loadProjectList() {
	$(".header-name").empty();
	
    var type = $("#type").val();
    var name = $("#name").val();
    $("#tcdPageCode").createPage({
        url: "/project/queryProjectList",
        data: {type: type, name: name},
        pageSize: 7,
        successFn: function (response) {
            if (response.status == 1) {
                alert(response.message);
                return;
            }

            var projectList = response.data.projectList;
            var typeJson = {};
            typeJson["1"] = "电视剧";
            typeJson["2"] = "电影";
            typeJson["3"] = "网剧";
            typeJson["6"] = "网大";
            
            var analyseStatusJson = {};
            analyseStatusJson["0"] = "分析成功";
            analyseStatusJson["1"] = "分析失败";
            analyseStatusJson["2"] = "正在分析";

            var projectDivArray = [];
            $.each(projectList, function (index, item) {
                var analyseDesc = "";
                if (item.analyseStatus != null) {
                    analyseDesc = analyseStatusJson[item.analyseStatus];
                } else {
                    analyseDesc = "未分析";
                }
                var subjectName = "";
                if (item.subject) {
                    subjectName = item.subject.name;
                }
                projectDivArray.push("<div class='project-con'>");
                projectDivArray.push("<div class='project-con-two'>");
                projectDivArray.push("	<div class='project-con-top'>");
                projectDivArray.push("		 <div class='if-analyse'>");
                projectDivArray.push("			<div class='dobule-bias'><span class='one'></span><span class='two'></span></div>");
                projectDivArray.push("			<p>" + analyseDesc + "</p>");
                projectDivArray.push("		</div>");
                projectDivArray.push("		<h4 title='" + item.name + "' class='project-name over-hide'>" + item.name + "</h4>");
                projectDivArray.push("		<div class='cut-off-rule'></div>");
                projectDivArray.push("		<div class='label-type'>" + typeJson[item.type] + "-" + subjectName + "</div>");
                projectDivArray.push("	</div>");
                projectDivArray.push("	<div class='project-con-bottom'>");
                projectDivArray.push("		<div class='tv-company over-hide' title="+ filterNull(item.company) +">" + filterNull(item.company) + "</div>");
                projectDivArray.push("		<div class='tv-time'>" + new Date(item.createtime).format("yyyy-MM-dd") + "</div>");
                projectDivArray.push("		<a href='javascript:;' flagid=\"" + item.id + "\" class='see-detail'>查看详情</a>");
                projectDivArray.push("	</div>");
                projectDivArray.push("	</div>");
                projectDivArray.push("	<div class='shade hide'>");
                projectDivArray.push("		<i class='icon iconfont' flagid=\"" + item.id + "\">&#xe61c;</i>");
                projectDivArray.push("	</div>");
                projectDivArray.push("</div>");
            });
            $("#projectList").empty();
            $("#projectList").append(projectDivArray.join(''));

            // 当项目列表渲染之后，给删除项目注册点击事件
            registerRemoveThing()

            //进入项目
            enterProject()

            //删除项目
            deleteProject()

        }
    });
}

//过滤Null的数据
function filterNull(value) {
    return value ? value : "";
}

//新建项目
function addProject() {
    window.location.href = "/base/forward/project/newProject";
}

// 当项目列表渲染之后，给删除项目注册点击事件
function registerRemoveThing() {
    var remove = $(".project-content .project-con-first .project-con-bottom");
    remove.find("i").click(function () {
        var shade = $("#projectList .project-con .shade")
        if (shade.hasClass("hide")) {
            shade.removeClass("hide")
            remove.find("p").html("放弃删除")
        } else {
            shade.addClass("hide")
            remove.find("p").html("删除项目")
        }
    })
}

//进入项目
function enterProject() {
    $("#projectList .project-con .project-con-bottom>a").click(function () {

        var id = $(this).attr("flagid");
        var url = "/project/enterProject";
        var successCall = function (response) {
            if (response.status == 1) {
                alert(response.message);
                return;
            }
            window.location.href = "/base/forward/playManage/basicInfo";
        };
        doPost(url, {id: id}, successCall);
    })
}

//删除项目
function deleteProject() {
    $("#projectList .project-con .shade i").click(function () {
        var that = this;
        $("#confirm-remove-sm").modal("show");
        $("#confir-remove").click(function () {
            var flagid = $(that).attr("flagid");
            var shade = $(that).parents(".shade");
            var url = "/project/removeProject";
            var successCall = function (response) {
                if (response.status == 1) {
                    modelWindow(response.message)
                    return;
                }
                // shade.parents(".project-con").remove();
                location.reload()
            };
            doPost(url, {id: flagid}, successCall);
        })
    })
}

// 获取url里面的参数
function GetRequest() {
    var url = window.location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            //就是这句的问题
            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
            //之前用了unescape()
            //才会出现乱码
        }
    }
    return theRequest;
}
