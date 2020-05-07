<%--
  Created by IntelliJ IDEA.
  User: NGX
  Date: 2020/5/5
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<head>
    <%@include file="include-head.jsp"%>
</head>
<body>
    <%@include file="include-nav.jsp"%>
    <%@include file="include-sidebar.jsp"%>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <ol class="breadcrumb">
            <li><a href="admin/to/main">首页</a></li>
            <li><a href="admin/getpage">数据列表</a></li>
            <li class="active">修改</li>
        </ol>
        <div class="panel panel-default">
            <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
            <div class="panel-body">
                <form role="form" action="admin/edit" method="post">
                    <input type="hidden" name="id" value="${admin.id}">
                    <input type="hidden" name="pageNum" value="${param.pageNum}">
                    <input type="hidden" name="keyword" value="${param.keyword}">
                    <p>
                        ${requestScope.exception.message}
                    </p>
                    <div class="form-group">
                        <label for="exampleInputPassword1">登陆账号</label>
                        <input type="text" class="form-control" id="exampleInputPassword1" name="loginAcount" value="${admin.loginAcount}">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">用户名称</label>
                        <input type="text" class="form-control" id="exampleInputPassword1" name="userName" value="${admin.userName}">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">邮箱地址</label>
                        <input type="email" class="form-control" id="exampleInputEmail1" name="email" value="${admin.email}">
                        <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                    </div>
                    <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                    <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
