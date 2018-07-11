<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>--%>
        <title>注册界面</title>
        <script type="text/javascript">
            function changeImage() {
                var img = document.getElementById("codeImage");
                img.src = "code?" + new Date().getTime();
            }
        </script>
    </head>

    <body>
        <form action="/login" method="post">
            <div>
                <label>用户名：</label>
                <input type="text" name="loginName">
            </div>
            <div>
                <label>密码：</label>
                <input type="password" name="password">
            </div>
            <div>
                <label>验证码：</label>
                <input type="text" name="code">
                <img id="codeImage" src="code">
                    <a href="javascript:changeImage();"></a>
                </img>
            </div>
            <div>
                <input type="submit" value="登陆">
                <input type="submit" value="注册">
            </div>
        </form>
    </body>
</html>