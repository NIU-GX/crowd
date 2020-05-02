<%--
  Created by IntelliJ IDEA.
  User: NGX
  Date: 2020/5/1
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%-- EL 表达式无法生效要添加  isELIgnored="false"  --%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
<%--    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>--%>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
    <%--这里要注意引入的顺序--%>
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <%--在按钮上添加onclick也可以实现功能--%>
<%--    <script type="text/javascript">--%>
<%--        $(function () {--%>
<%--            $('btn1').click(function () {--%>
<%--                window.history.back();--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网</a></div>
        </div>
    </div>
</nav>

<div class="container" style="text-align: center">
    <h2 class="form-signin-heading">
        <i class="glyphicon glyphicon-log-in"></i>尚筹网系统消息
    </h2>
    <h3>
        ${requestScope.exception.message}
    </h3>
    <button id="btn1" style="width: 150px; margin: 50px auto 0px auto"  class="btn btn-block btn-sm btn-success" onclick="window.history.back()">返回</button>
</div>

</body>
</html>
