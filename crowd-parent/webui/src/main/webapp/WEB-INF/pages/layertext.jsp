<%--
  Created by IntelliJ IDEA.
  User: NGX
  Date: 2020/5/1
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../../jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="../../layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                layer.msg("layer test");
            });
            $("#btn2").click(function () {
                var requestBody = JSON.stringify("nnnnnn");
                $.ajax({
                    url: 'send',
                    type: 'post',
                    data: requestBody,
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'text',
                    success: function (data) {
                        layer.msg(data)
                    },
                    error: function (error) {
                        layer.msg(error)
                    }
                });
            });
        })
    </script>
</head>
<body>

    <button id="btn1" >layer test</button>
    <button id="btn2">error test</button>
    <a href="loging">login</a>
</body>
</html>
