// 获取contentPath
var pathName = document.location.pathname;
var index = pathName.substr(1).indexOf("/");
var contentPaht = pathName.substr(0,index+1);
var flag = false;

// 验证码图片刷新
function changeImage() {
    var img = document.getElementById("codeImage");
    img.src = "code?" + new Date().getTime();
}

// 页面加载完成
function loadSuccess() {
    $('#msg').show();
    $('#msg').html("您已被强制登出，请重新登陆!");

    // 获取系统支持的语言
    common.ajaxRequest("POST", contentPaht + "/languages", "", true, languageData, common.errorFunction);
}

// 登陆请求成功
function loginSuccess(data) {
    if (data.flag) {
        window.location.href = contentPath + data.data;
        return true;
    } else {
        $('#msg').show();
        $('#msg').html("<lable>" + data.msg +"</lable>");
        $('#codeImage').click();
    }
}

// 登陆
function login() {
    // 非空校验
    if (common.isEmpty($("#loginName").val())) {
        $('#msg').show();
        $('#msg').html("请输入用户名");
        return;
    } else if (common.isEmpty($("#password").val())) {
        $('#msg').show();
        $('#msg').html("请输入密码");
        return;
    } else if (common.isEmpty($("#code").val())) {
        $('#msg').show();
        $('#msg').html("请输入验证码");
        return;
    }

    var data = {
        loginName : $("#loginName").val(),
        password : $("#password").val(),
        code : $("#code").val()
    }

    common.ajaxRequest("POST", contentPaht + "/login", data, false, loginSuccess, common.errorFunction);
}

function languageData(data) {
    for (var i in data) {
        var option = "<option value='" + data[i].codeId + "'>"  + data[i].codeValue + "</option>";
        $('#language').append(option);
    }
}