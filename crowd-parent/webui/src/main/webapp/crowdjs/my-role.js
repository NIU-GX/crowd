// 声明 显示确认模态框的函数
function showConfirmModal(roleArray) {
    // 打开模态框
    $("#confirmModal").modal("show");
    // 清除模态框
    $("#roleNameDiv").empty();
    window.roleIdArray = [];

    // 遍历roleArray数组
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br/>");

        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}

// 显示分页
function generatePage() {
    // 1. 获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2. 填充表格
    fillTableBody(pageInfo);
}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url": "role/getpage",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        /*设置为同步*/
        "async": false,
        "dataType": "json",
        "success": function (response) {
            var pageInfo = response.data;

            fillTableBody(pageInfo);
        },
        "error": function (response) {

        }
    });
    console.log(ajaxResult);

    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;
    // 如果当前请求失败，状态码不是200
    if (statusCode !== 200) {
        layer.msg("服务器端程序调用失败，响应状态码：" + statusCode + "说明信息：" + ajaxResult.statusText);
        return null;
    }
    // 如果状态码是200，说明请求成功
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;

    if (result == 'FAILED') {
        layer.msg(resultEntity.message);
        return null;
    }
    // 确认result为成功后 获取pageInfo
    var pageInfo = resultEntity.data;
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除rolePageBody里面的内容
    $("#rolePageBody").empty();
    $("#Pagination").empty();

    // 判断pageInfo是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉没有查询到你要的数据</td>>/tr>");
        return;
    }
    // 使用pageInfo的list填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>"
        var checkboxTd = "<td><input class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>"


        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>"
        // 通过button标签的id属性，吧roleId的值传递给单击响应函数
        var pencilBtn = "<button type='button' id='" + roleId + "' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>"
        var dangerBtn = "<button type='button' id='" + roleId + "' class='btn btn-danger btn-xs deleteBtn'><i class=' glyphicon glyphicon-remove'></i></button>"

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + dangerBtn + "</td>"

        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>"
        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generatorNavigator(pageInfo);
}

// 生成分页页码导航条
function generatorNavigator(pageInfo) {
    // 先获取总记录数
    var totalRecord = pageInfo.total;
    // 声明一个JSON对象存储Pagination要设置的属性
    var properties = {
        "num_edge_entries": 2,								// 边缘页数
        "num_display_entries": 5,								// 主体页数
        "callback": paginationCallBack,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
        "items_per_page": pageInfo.pageSize,	// 每页要显示的数据的数量
        "current_page": pageInfo.pageNum - 1,	// Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
        "prev_text": "上一页",									// 上一页按钮上显示的文本
        "next_text": "下一页"									// 下一页按钮上显示的文本
    };

    // 调用pagibation（）
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generatePage();
    // 取消页码超链接的默认行为
    return false;
}

