var PHONE = /^1(3|4|5|7|8)\d{9}$/;

function checkUser() {
    if (!phoneValidate($("#tel").val())) {
        return;
    }
    if ($("#password").val() == null || $("#password").val() == "") {
        $(".pass-point").css("display", "block");
        return false;
    }

    $("#formid").submit();
}
$(document).keydown(function (e) {
    if(e && e.keyCode == 13) {
        checkUser();
    }
})
//正则验证手机号
var phoneValidate = function (phone) {
    if (PHONE.test(phone)) {

        return true;
    } else {
        $(".tel-point").css("display", "block");
        return false;
    }
};

//隐藏手机账号或密码错误提示
function tel_none() {
    $(".tel-point").css("display", "none");
    $(".error-point").css("display", "none");
}

function pass_none() {
    $(".pass-point").css("display", "none");
    $(".error-point").css("display", "none");
}