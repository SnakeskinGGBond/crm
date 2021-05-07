<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
    <script type="text/javascript">
        $(function () {
            //为创建按钮绑定事件,打开添加操作的模态窗口
            $("#addBtn").click(function () {

                $(".time").datetimepicker({
                    minView: "month",
                    language: "zh-CN",
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });
                /*
                    操作模态窗口的方式:
                        找到需要操作的模态窗口的jquery对象,调用modal方法,为该方法传递参数,show:打开窗口,hide:关闭窗口

                 */
                //alert("123");
                //走后台,获取用户列表
                $.ajax({
                    url: "activity/getUserList",
                    type: "get",
                    dataType: "json",
                    success: function (resp) {
                        let html = "<option></option>"
                        $.each(resp, function (index, element) {
                            html += "<option value='" + element.id + "'>" + element.name + "</option>"
                        })
                        $("#create-marketActivityOwner").html(html);
                        //将当前登录用户设置为下拉框默认选项
                        var id = "${user.id}";
                        $("#create-marketActivityOwner").val(id);
                        $("#createActivityModal").modal("show");
                    }
                })
            })

            //为模态窗口的保存按钮绑定事件,执行添加操作
            $("#saveBtn").click(function () {
                $.ajax({
                    url: "activity/save",
                    data: {
                        "owner": $.trim($("#create-marketActivityOwner").val()),
                        "name": $.trim($("#create-marketActivityName").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (resp) {
                        if (resp.success) {
                            //添加成功
                            //清空添加操作模态窗口中数据
                            /*
                                jquery对象和dom对象相互转换
                             */
                            $("#activityAddForm")[0].reset();
                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");
                            //刷新市场活动信息列表(局部刷新)
                            /*
                            * $("#activityPage").bs_pagination('getOption', 'currentPage')
                            *   操作后停留在当前页
                            *
                            * $("#activityPage").bs_pagination('getOption', 'rowsPerPage')
                            *   操作后维持已经设置好的每页展现的记录数
                            *
                            * */
                            pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            //添加失败
                            alert(resp.msg);
                        }
                    }
                })
            })

            //页面加载完毕后触发一个方法,默认展开第一页,每页展示2条数据
            pageList(1, 5);

            //为查询按钮绑定事件,触发pageList方法
            $("#searchBtn").click(function () {
                //点击查询按钮时,应该将搜索框中的信息保存起来,保存到隐藏域中
                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));
                pageList(1, 2);
            })

            //为全选的复选框绑定事件,触发全选操作
            $("#qx").click(function () {
                $("input[name=xz]").prop("checked", this.checked)
            })

            /*
                动态生成的元素,不能以普通绑定事件的形式进行操作
                动态生成的元素,要以on方法的形式来触发事件
                语法:
                    $(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)
             */
            $("#activityBody").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
            })

            //为删除按钮绑定事件
            $("#deleteBtn").click(function () {
                //找到复选框中打√的复选框jquery对象
                var $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择需要删除的记录");
                } else {
                    if (confirm("确定删除所选的记录吗?")) {
                        //url:activity/delete?id=xxx&id=xxx&id=xxx
                        //拼接参数
                        let param = "";
                        //遍历$xz
                        $.each($xz, function (index, element) {
                            param += "id=" + $(element).val();
                            //如果不是最后一个元素,追加一个&
                            if (index < $xz.length - 1) {
                                param += "&";
                            }
                        })
                        //alert(param);
                        $.ajax({
                            url: "activity/delete",
                            data: param,
                            type: "post",
                            dataType: "json",
                            success: function (resp) {
                                if (resp.success) {
                                    pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                                } else {
                                    alert(resp.msg);
                                }
                            }
                        })
                    }
                }
            })

            //为修改按钮绑定事件,打开修改操作的模态窗口
            $("#editBtn").click(function () {
                $(".time").datetimepicker({
                    minView: "month",
                    language: "zh-CN",
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });
                let $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择要修改的记录!")
                } else if ($xz.length > 1) {
                    alert("只能选择一条记录进行修改!")
                } else {
                    //alert("1");
                    var id = $xz.val()
                    $.ajax({
                        url: "activity/getUserListAndActivity",
                        data: {
                            "id": id
                        },
                        type: "get",
                        dataType: "json",
                        success: function (resp) {
                            /*
                                resp
                                    用户列表
                                    市场活动对象
                                    {"userList":[{用户1},{2},{3}..],"activity":{市场活动对象}}
                             */
                            let html = "<option></option>";
                            $.each(resp.userList, function (index, element) {
                                html += "<option value='" + element.id + "'>" + element.name + "</option>";
                            })
                            $("#edit-marketActivityOwner").html(html);
                            //处理单条activity
                            $("#edit-marketActivityOwner").val(resp.activity.owner);
                            $("#edit-marketActivityName").val(resp.activity.name);
                            $("#edit-startTime").val(resp.activity.startDate);
                            $("#edit-endTime").val(resp.activity.endDate);
                            $("#edit-cost").val(resp.activity.cost);
                            $("#edit-describe").val(resp.activity.description);
                            $("#edit-id").val(resp.activity.id);
                            //所有数据加载完毕后,打开模态窗口
                            $("#editActivityModal").modal("show");
                        }
                    })
                }
            })

            //为更新按钮绑定事件
            $("#updateBtn").click(function () {
                $.ajax({
                    url: "activity/update",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-marketActivityOwner").val()),
                        "name": $.trim($("#edit-marketActivityName").val()),
                        "startDate": $.trim($("#edit-startTime").val()),
                        "endDate": $.trim($("#edit-endTime").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-describe").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (resp) {
                        if (resp.success) {
                            //刷新市场活动信息列表(局部刷新)
                            /*
                            * $("#activityPage").bs_pagination('getOption', 'currentPage')
                            *   操作后停留在当前页
                            *
                            * $("#activityPage").bs_pagination('getOption', 'rowsPerPage')
                            *   操作后维持已经设置好的每页展现的记录数
                            *
                            * */
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),
                                $("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
                            //关闭修改模块的模态窗口
                            $("#editActivityModal").modal("hide");
                        } else {
                            //添加失败
                            alert(resp.msg);
                        }
                    }
                })
            })
        });

        /**
         * 分页函数
         * 对于所有的关系新该数据库,做前端分页相关的基础组件
         * 发出ajax请求到后台,从后台取得最新的市场活动信息列表数据,通过响应回来的数据,局部刷新市场活动信息列表
         * @param pageNo 页码
         * @param pageSize 每页展现的记录数
         */
        function pageList(pageNo, pageSize) {
            //取消全选的勾
            $("#qx").prop("checked", false);
            //查询前,将隐藏域中保存的信息取出来,重新赋予到搜索框中
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));
            $.ajax({
                url: "activity/pageList",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val())
                },
                type: "get",
                dataType: "json",
                success: function (resp) {
                    /*
                        resp
                            1. 市场活动信息列表
                            2. 分页插件需要查询出来的总记录数,{"total":100}

                            {"total":100,"dataList":[{市场活动1},{2},{3}]}
                     */
                    let html = "";
                    $.each(resp.dataList, function (index, element) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + element.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"onclick="window.location.href=\'activity/detail?id=' + element.id + '\';">' + element.name + '</a></td>';
                        html += '<td>' + element.owner + '</td>';
                        html += '<td>' + element.startDate + '</td>';
                        html += '<td>' + element.endDate + '</td>';
                        html += '</tr>';
                    })
                    $("#activityBody").html(html);
                    //计算总页数
                    var totalPages = resp.total % pageSize == 0 ? resp.total / pageSize : parseInt(resp.total / pageSize) + 1;
                    //数据处理完毕后,结合分页插件,对前端展现分页相关信息
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: resp.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,
                        /**
                         * 该回调函数在点击分页组件的时候触发
                         * @param event
                         * @param data
                         */
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });

                }
            })
        }
    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="activityAddForm" class="form-horizontal" role="form">
                    <%--所有者--%>
                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 270px;">
                            <select class="form-control" id="create-marketActivityOwner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>
                    <%--开始,结束日期--%>
                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control time" id="create-startDate" readonly
                                   style="cursor: pointer"/>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control time" id="create-endDate" readonly
                                   style="cursor: pointer"/>
                        </div>
                    </div>
                    <%--成本--%>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <%--描述--%>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <%--关闭,保存
                data-dismiss="modal" 关闭模态窗口
            --%>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id"/>
                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 270px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control" id="edit-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control time" id="edit-startTime" style="cursor: pointer"
                                   readonly>
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control time" id="edit-endTime" style="cursor: pointer"
                                   readonly>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 270px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <%--关于文本域textarea:
                                1. 一定以标签对的形式来呈现,正常状态下标签对要紧挨着,不要有空格
                                2. textarea虽然是以标签对的形式来呈现的,但是也是属于表单元素范畴,
                                    我们对textarea的取值和赋值操作应该统一使用val()方法,而不是html()方法
                                3.
                            --%>
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="searchBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <%--
                    点击创建按钮,观察两个属性和属性值
                    data-toggle="modal" 表示触发该按钮,将打开一个模态窗口

                    data-target="#createActivityModal" 表示要打开那个模态窗口,通过#id的形式找到该窗口

                    以属性和属性值的方式卸载了button元素中,用来打开模态窗口
                    但是这样做是有问题的:
                        没有办法对按钮的功能进行扩充,所以未来的时机项目中,对于处罚模态窗口的操作,不卸载元素中
                        应该由js代码来操作

                --%>
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <%--市场活动信息列表详情--%>
                <tbody id="activityBody">
                <%--<tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>
                <tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage">

            </div>
        </div>

    </div>

</div>
</body>
</html>