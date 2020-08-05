<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA信息统计管理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .bu {
            background: #fddddb;
            color: #fc5a56;
            padding: 5px;
            border-radius: 50%;
            font-size: 10px;
            font-weight: 600;
        }

        .bigTable.table-bordered, .bigTable.table-bordered > tbody > tr > td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .export{
            font-size:14px;
            color: #0C81F5;
            font-weight:normal;
            cursor: pointer;
        }
        .case-number tr td:nth-child(3),.case-number tr td:nth-child(5){
            color: #0C81F5;
        }
        .case-number tr td:nth-child(4){
            color: #FF5A56;
        }
        .case-number tr:last-of-type td{
            color: #333;
            font-size:14px;
            font-weight:bold;
        }
        .case-number tr:last-of-type td:nth-child(2){
            color: #FF5A56;
        }
        /*案件性质开始*/
        .checkbox-div p {
            margin: 0;
            position: relative;
        }

        .checkbox-div {
            border-bottom: 1px dashed #e2e2e2;
            margin: 10px 0;
            padding-bottom: 10px;
        }

        .checkbox-div p span:first-of-type {
            font-size: 14px;
            font-weight: bold;
            color: #0279E2;
            border-left: 5px solid #0279E2;
            padding-left: 4px;
        }

        .checkbox-div .check-all {
            display: inline-block;
            width: 40px;
            height: 22px;
            line-height: 22px;
            text-align: center;
            background: #E8E8E8;
            color: #333;
            cursor: pointer;
        }

        .checkbox-div .check-all.active {
            color: #0076E6;
            background: #e3effc;
        }

        .checkbox-div .check-list {
            margin: 10px 0;
        }

        .checkbox-div .check-list li {
            display: inline-block;
            padding: 10px 14px;
            background: #E8E8E8;
            color: #333;
            font-size: 12px;
            border-radius: 37px;
            cursor: pointer;
            position: relative;
            margin-right: 14px;
        }

        .check-list li.active {
            background: #DDEEFF;
            border: 1px solid #0073EA;
            color: #0073EA;
            font-weight: bold;
        }

        .check-list li.active:before {
            content: '';
            display: inline-block;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background: url("../img/check.png") no-repeat center;
            background-size: 100%;
            position: absolute;
            top: -4px;
            right: -8px;
        }

        .check-first {
            display: inline-block;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background: url("../img/uncheck.png") no-repeat center;
            background-size: 100%;
            position: absolute;
            right: 0;
            cursor: pointer;
        }

        .checkbox-div p b.active {
            background: url("../img/check.png") no-repeat center;
            background-size: 100%;
        }

        .btn-blue-radius {
            width: 106px;
            height: 35px;
            line-height: 35px;
            font-size: 14px;
            border-radius: 35px;
            background: #0076E6;
            padding: 0;
            color: #fff;
            outline: none;
        }
        /*案件性质结束*/
    </style>
</head>

<body>
<div class="modal fade" id="caseNature" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 1070px;">
        <div class="modal-content">
            <div class="modal-body">
                <c:forEach items="${dictResult}" var="result" varStatus="s">
                    <div class="checkbox-div">
                        <p>
                            <span>${result.key}</span>
                            <span class="check-all">全选</span>
                        </p>
                        <c:if test="${result.key eq '盗窃'}">

                            <ul class="check-list">
                                <c:forEach items="${result.value}" var="resultValue" varStatus="c">
                                    <li <%--class="active"--%>>${resultValue.dictName}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
                </c:forEach>

            </div>
            <div class="row" style="text-align: center;margin: 15px 0;">
                <button class="btn btn-blue-radius" name="okCheck">确定</button>
            </div>
        </div>
    </div>
</div>
<div class="row Modular part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>
                    案件查询
                </div>
            </div>
            <div class="panel-body">
                <form id="consignationForm" action="<%=path%>/query/comprehensiveQueryList" class="form-horizontal tasi-form form-inline"
                      method="post">
                    <div class="row">
                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>受理时间</label>
                                <input type="text" class="form-control form_datetime" readonly="readonly"
                                       name="acceptDatetimeStart" value="${query.acceptDatetimeStart}" placeholder="请选择开始时间">
                            </div>
                        </div>
                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>至</label>
                                <input type="text" class="form-control form_datetime" readonly="readonly"
                                       name="acceptDatetimeEnd" value="${query.acceptDatetimeEnd}" placeholder="请选择结束时间">
                            </div>
                        </div>
                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>案件性质</label>
                                <div class="select">
                                    <input type="text" name="caseType" value="${query.caseType}" class="form-control"
                                           data-toggle="modal" data-target="#caseNature" placeholder="请选择案件性质">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3" <%--style="padding: 0;"--%>>
                            <div class="form-group">
                                <button class="btn btn-blue" type="submit" id="addInformant">查询</button>
                                <button type="reset" name="reset" class="btn btn-blue-border">重置</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="row Modular notAccepted part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue row" style="padding: 10px 15px;">
                <div class="col-md-9">统计列表</div>
                <div class="col-md-1 col-md-offset-1 export" name="comprehensiveQueryexportBtn">
                    <img src="../img/daochu.png" alt="" width="24px">
                    <span>导出</span>
                </div>
            </div>
            <div class="panel-body" style="padding: 0 0 15px 0;">
                <table class="table table-hover table-bordered bigTable table-striped" style="table-layout: fixed;margin-top: 0;">
                    <thead>
                    <tr>
                        <th style="width: 55px;">序号</th>
                        <th>送检单位</th>
                        <th>受理案件数</th>
                        <th>出具鉴定书数</th>
                        <th>送检物证检材数</th>
                    </tr>
                    </thead>
                    <tbody id="" class="case-number">
                    <c:forEach items="${dbComprehensiveCaseCountViewList}" var="result" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${result.key}</td>
                            <c:forEach items="${result.value}" var="resultValue" varStatus="c">
                                <td>${resultValue.caseNumber}</td>
                                <td>${resultValue.caseBooksNumber}</td>
                                <td>${resultValue.wzSampleNumber}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td>总计</td>
                        <c:forEach items="${totalList}" var="list" varStatus="s">
                            <td>${list}</td>
                        </c:forEach>
                    </tr>
                    </tbody>
                </table>

                <div class="row" style="padding: 0px">
                    <div class="col-md-12">
                        <div id="kkpager"></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="modal" id="exportSuccess" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">
                <p>
                    <img src="../img/success.png" alt="" width="82px">
                </p>
                <p style="font-size: 22px;font-weight: bold;color: #1BB29B;">
                    导出成功
                </p>
            </div>
        </div>
    </div>
</div>
<div class="height-60"></div>
<div class="row footer">
    Copyright© 2019 北京博安智联科技有限公司       
</div>
<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/entrustCurrency.js"></script>
<script>
    $(function () {


        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh',
            weekStart: 1,
            todayBtn: 1,
            minView: "month",
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });


        $(".title-tab").click(function () {
            $(this).addClass("active").siblings().removeClass("active")
        })

        //导出excel
        $("div[name='comprehensiveQueryexportBtn']").click(function () {
            var newUrl = "<%=path%>/query/exportComprehensiveQuery";
            $("#consignationForm").attr('action',newUrl);
            $("#consignationForm").submit();

            var oldUrl = "<%=path%>/query/comprehensiveQueryList";
            $("#consignationForm").attr('action',oldUrl);
        })

        //        导出成功模态框居中显示
        $('#exportSuccess').on('shown.bs.modal', function () {
            var $this = $(this);
            var dialog = $this.find('.modal-dialog');
            //此种方式，在使用动画第一次显示时有问题
            //解决方案，去掉动画fade样式
            var top = ($(window).height() - dialog.height()) / 2;
            dialog.css({
                marginTop:top
            });
        })
        //        案件性质
        $(".check-list").on("click", "li", function () {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active")
                if ($(this).parents("ul").find("li.active").length != $(this).parents("ul").find("li").length) {
                    $(this).parents(".checkbox-div").find(".check-all").removeClass("active")
                }
            } else {
                $(this).addClass("active")
                if ($(this).parents("ul").find("li.active").length == $(this).parents("ul").find("li").length) {
                    $(this).parents(".checkbox-div").find(".check-all").addClass("active")
                }
            }
        })
        $(".check-all").click(function () {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active")
                $(this).parents(".checkbox-div").find("li").removeClass("active")
            } else {
                $(this).addClass("active")
                $(this).parents(".checkbox-div").find("li").addClass("active")
            }
        })
        $(".check-first").click(function () {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active")
            } else {
                $(this).addClass("active")
            }
        })
        $("button[name='okCheck']").click(function () {
            var selectedList = []
            $(".checkbox-div").each(function(){
                if($(this).children(".check-list").length>0){
                    $(this).find("li.active").each(function () {
                        selectedList.push($(this).text())
                    })
                }else if($(this).find("span.active").length>0){
                    selectedList.push($(this).find("span.active").prev().text())
                }
            })
            $("input[name='caseType']").val(selectedList.join(","))
            $("#caseNature").modal("hide")
        })
    });
</script>
</body>

</html>