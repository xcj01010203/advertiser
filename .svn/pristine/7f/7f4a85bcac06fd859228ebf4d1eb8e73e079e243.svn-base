
// 点击有下拉箭头的显示或者隐藏下面
function clickShowOrHide(dom1, dom2) {
    dom1.click(function() {
        var dom1Siblings = dom1.parent().parent(".five-siblings").siblings(".five-siblings")
        dom1Siblings.find(".down").removeClass("hide");
        dom1Siblings.find(".top").addClass("hide");
        dom1Siblings.find(".five-siblings-con").addClass("hide")
        if(dom1.find(".top").hasClass("hide")) {
            dom1.find(".top").removeClass("hide");
            dom1.find(".down").addClass("hide");
            dom2.removeClass("hide");
        }else {
            dom1.find(".top").addClass("hide");
            dom1.find(".down").removeClass("hide");
            dom2.addClass("hide");
        }
        return false;
    })
}

// 生成css圆形半分比进度条
function circularHalfRadio(dom, num) {
    dom.html($('<div class="circularHalfRadio"> <div class="circle_left"> <div class="clip_left"></div> ' +
        '</div> <div class="circle_right"> <div class="clip_right"></div> </div> <div class="mask"> ' +
        '<span>'+num+'</span>% </div> </div>'))
    $(".circularHalfRadio").css({
        width: "90px",
        height: "90px",
        position: "relative",
        borderRadius: "50%",
        background: "#e7e7e7",
        margin: "0 auto"
    })
    $(".circularHalfRadio .clip_left, .clip_right").css({
        width: "90px",
        height: "90px",
        position: "absolute",
        top: "0px",
        left: "0px"
    })
    $(".circularHalfRadio .circle_left, .circle_right").css({
        width: "90px",
        height: "90px",
        position: "absolute",
        borderRadius: "50%",
        top: "0px",
        left: "0px",
        background: "#93d9d0"
    })
    $(".circularHalfRadio .circle_right, .clip_right").css({
        clip: "rect(0,auto,auto,45px)"
    })
    $(".circularHalfRadio .circle_left, .clip_left").css({
        clip: "rect(0,45px,auto,0)"
    })
    $(".circularHalfRadio .mask").css({
        width: "62px",
        height: "62px",
        borderRadius: "50%",
        left: "14px",
        top: '14px',
        background: "#FFF",
        position: "absolute",
        textAlign: "center",
        lineHeight: "62px",
        fontSize: "14px"
    })
    if( (100-num) <= 50 ){
        $('.circle_right').css('transform','rotate('+((100 - num)*3.6)+'deg)');
    }else{
        $('.circle_right').css({
            'transform':'rotate(0deg)',
            "background":"#e7e7e7"
        });
        $('.circle_left').css('transform','rotate('+((100 - num + 50)*3.6)+'deg)');
    }
    $(".circularHalfRadio .mask span").text(num);
}

// 双斜线
function dobuleBias() {
    var html = $('<div class=".dobule-bias"><span class="one"></span><span class="two"></span></div>')
    $(".dobule-bias").css({
        "display": "inline-block",
        "height": "13px",
        "width": "23px",
        "position": "relative",
        "overflow": "hidden"
    })
    $(".dobule-bias span").css({
        "display": "inline-block",
        "width": "4px",
        "height": "20px",
        "backgroundColor": "#29d9c2",
        "transform": "rotate(30deg)",
        "position": "absolute"
    })
    $(".dobule-bias .one").css({
        "top": "-2px",
        "left": "4px"
    })
    $(".dobule-bias .two").css({
        "top": "-2px",
        "left": "13px"
    })
    return html;
}

// 摸态窗体
/**
 *
 * @param string  提示用户的信息
 * @param time  数字或字符串  可以不传  传的时间就是关闭时间，不传需要手动关闭
 */
function modelWindow(string, time) {
    $('#myModal').modal({backdrop: 'static', keyboard: false, toggle: 'toggle'});
    $("#modal-header").html('<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span></button>')
    $("#modal-body").html(string)
    $("#modal-footer").html("")
    $("#modal-footer").css("display", "none")
    if(!!time) {
        setTimeout(function () {
            $("#modal-content>.modal-header>button").trigger("click");
        }, time)
    }
}

//过滤Null的数据
function filterNull(value) {
	return value || "";
}

function modelWindowParam(string,time,mthondName){
	$('#myModal').modal({backdrop: 'static', keyboard: false, toggle: 'toggle'});
    $("#modal-body").html(string);
    if ($("#modal-footer")[0]) {
    	$("#modal-footer").remove()
    }
    $("#modal-content").append('<div id="modal-footer" class="modal-footer"></div>');
    if(mthondName ==null){
    	var btnsHtml = '<button type="button" class="btn btn-primary">确定</button><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>';
    }else{
    	var btnsHtml = '<button type="button" class="btn btn-primary" onclick="'+mthondName+'();">确定</button><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>';
    }
    $("#modal-content").find("#modal-footer").html(btnsHtml);
    if(!!time) {
        setTimeout(function () {
            $("#modal-content>.modal-header>button").trigger("click");
        }, time)
    }
}

/**
 * 判断某个dom节点是否绑定了某个事件，并删除此事件
 * @param dom jquery节点
 * @param event 绑定什么事件
 */
function judgeDomEvent(dom, event){
    var objEvt = $._data(dom[0], "events")
    if (objEvt && objEvt[event]) {
        dom.off("click");
    } else {
    }
}
