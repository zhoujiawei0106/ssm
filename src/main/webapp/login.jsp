<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>注册界面</title>
        <script type="text/javascript">
            function changeImage() {
                var img = document.getElementById("codeImage");
                img.src = "code?" + new Date().getTime();
            }
        </script>
    </head>

    <body>
        <form action="LoginServlet" method="get">
            姓名：<input type="text" name="name"><br/>
            密码：<input type="password" name="pwd"><br/>
            验证码：<input type="text" name="code">
            <img id="codeImage" src="code"><a href="javascript:changeImage();">看不清</a><br/>
            <input type="submit" value="注册"><br/>
        </form>
    </body>
</html>