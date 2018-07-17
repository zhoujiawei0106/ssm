// 获取contentPath
var pathName = document.location.pathname;
var index = pathName.substr(1).indexOf("/");
var contentPaht = pathName.substr(0,index+1);

// 验证码图片刷新
function changeImage() {
    var img = document.getElementById("codeImage");
    img.src = "code?" + new Date().getTime();
}

// 页面加载完成
function loadSuccess() {
    $('#msg').show();
    $('#msg').html("您已被强制登出，请重新登陆!");
}

// 登陆请求成功
function loginSuccess(data) {
    if (data.flag) {
        window.location.href = "/index";
    } else {
        $('#msg').show();
        $('#msg').html(data.msg);
        $('#codeImage').click();
    }
}

// 登陆请求失败
function loginFail(XMLHttpRequest, textStatus, errorThrown) {
    console.log(XMLHttpRequest);
    console.log(textStatus);
    console.log(errorThrown);
    debugger;
}

// 登陆
function login() {
    // 非空校验
    if ($("#loginName").val() == '') {
        $('#msg').show();
        $('#msg').html("请输入用户名");
    } else if ($("#password").val() == '') {
        $('#msg').show();
        $('#msg').html("请输入密码");
    } else if ($("#code").val() == '') {
        $('#msg').show();
        $('#msg').html("请输入验证码");
    }

    var data = {
        loginName : $("#loginName").val(),
        password : $("#password").val(),
        code : $("#code").val()
    }

    common.ajaxRequest("POST", contentPaht + "/login", data, loginSuccess, loginFail);
}