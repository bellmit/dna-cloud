<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2018/12/19
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
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
            /*white-space: nowrap;*/
            text-overflow: ellipsis;
        }

        .sec-title {
            padding-left: 30px;
        }

        .title-tab {
            display: inline-block;
            padding: 8px 18px;
            font-size: 14px;
            color: #484848;
            background: #f0f0f0;
            margin-right: 30px;
            position: relative;
            cursor: pointer;
            border-radius: 4px;
        }

        .title-tab > span {
            display: none;
            position: absolute;
            right: -20px;
            top: -12px;
            color: #fff;
            background: #ff5a56;
            padding: 0 10px;
            height: 24px;
            line-height: 24px;
            border-radius: 12px;
        }

        .title-tab.active {
            background: #fff;
            border: 1px solid #0c81f5;
            color: #0c81f5;
            font-weight: bold;
        }

        .title-tab.active > span {
            display: inline-block;
            font-weight: normal;
        }

        .export {
            font-size: 14px;
            color: #0C81F5;
            font-weight: normal;
            cursor: pointer;
        }

        .case-number tr td:nth-child(3), .case-number tr td:nth-child(5), .case-number tr td:nth-child(7), .case-number tr td:nth-child(9) {
            color: #0C81F5;
        }

        .case-number tr td:nth-child(4), .case-number tr td:nth-child(6), .case-number tr td:nth-child(8), .case-number tr td:nth-child(10) {
            color: #FF5A56;
        }

        .case-number tr:last-of-type td {
            color: #333;
            font-size: 14px;
            font-weight: bold;
        }

        .case-number tr:last-of-type td:nth-child(2) {
            color: #FF5A56;
        }

        .loading-img {
            animation: rotation 5s linear infinite;
        }

        @-webkit-keyframes rotation {
            from {
                -webkit-transform: rotate(0deg);
            }
            to {
                -webkit-transform: rotate(360deg);
            }
        }

        .success-div {
            display: none;
        }
    </style>
</head>

<body>
<div class="row Modular part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>
                    案件查询
                </div>
            </div>
            <div class="panel-body">
                <form id="consignationForm" action="<%=path%>/violateWealth/violateWealthCaseStatsList"
                      class="form-horizontal tasi-form form-inline"
                      method="post">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>受理单位</label>
                                <div class="select">
                                    <select id="acceptOrgId" style="border: 1px solid #d4d4d4; color: #000;">
                                        <option value="">全部实验室</option>
                                        <c:forEach items="${laboratoryInfoList}" var="laboratory">
                                            <option value="${laboratory.orgId}" <c:if test="${laboratory.orgId eq acceptOrgId}">selected</c:if>>${laboratory.dnaLabName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>受理时间</label>
                                <input type="text" name="acceptDatetimeStart" value="${query.acceptDatetimeStart}"
                                       class="form-control form_datetime" readonly="readonly" placeholder="请选择开始时间">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label style="width: 56px;">至</label>
                                <input type="text" name="acceptDatetimeEnd" value="${query.acceptDatetimeEnd}"
                                       class="form-control form_datetime" readonly="readonly" placeholder="请选择结束时间">
                            </div>
                        </div>
                        <div class="col-md-2">
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
            <div class="panel-heading blue">
                <div>案件数量</div>
            </div>
            <div class="panel-body" style="padding: 15px 0;">
                <div class="row">
                    <div class="col-md-8 sec-title checkDiv">
                        <div class="title-tab active" name="sjCaseCountBtn">
                            送检案件总数<span name="sjCaseTotalCount">${globalTotalCount}</span>
                        </div>
                        <div class="title-tab" name="rkWzCaseCountBtn">
                            检材（入库）现场物证的案件数 <span name="rkWzCaseTotalCount"></span>
                        </div>
                        <div class="title-tab" name="sjSampleCountBtn">
                            送检样本总数 <span name="sjSampleTotalCount"></span>
                        </div>
                        <div class="title-tab" name="rkSampleCountBtn">
                            入库样本总数 <span name="rkSampleTotalCount"></span>
                        </div>

                        <div class="title-tab" name="EffectiveRatioBtn">
                            DNA有效比中率</span>
                        </div>

                    </div>
                    <div class="col-md-2 col-md-offset-1 export" name="invadingWealthexportBtn">
                        <span style="font-weight:bold;font-size: 20px;"><img src="../img/export.png" style="width: 25px;" >导出Excel</span>
                    </div>
                </div>
                <table id="iwCaseTable" class="table table-hover table-bordered bigTable table-striped"
                       style="table-layout: fixed;margin-top: 0;">
                    <thead>
                    <tr>
                        <th style="width: 55px;">序号</th>
                        <th style="width: 180px;">送检单位</th>
                        <c:forEach items="${assetsDictList}" var="dict">
                            <th>${dict.dictName}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody id="dbSjCaseCountView" class="case-number">
                        <c:forEach items="${caseStatsResultList}" var="result" varStatus="s">
                            <tr>
                                <td>${s.index+1}</td>
                                <td>${result.orgName}</td>
                                <c:forEach items="${result.caseCountList}" var="resultValue" varStatus="c">
                                    <td>${resultValue}</td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<div class="modal" id="exportSuccess" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">
                <div class="exportIng">
                    <p>
                        <img src="../img/loading.png" class="loading-img" alt="" width="90px">
                    </p>
                    <p style="font-size: 18px;font-weight: bold;color: #0C81F5;margin-top: 15px;">
                        导出中....
                    </p>
                </div>
                <div class="success-div">
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
</div>
<div class="modal" id="loading" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <%--<div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">--%>
        <div class="exportIng">
            <p>
                <img src="../img/loading.png" class="loading-img" alt="" width="90px">
            </p>
            <p style="font-size: 18px;font-weight: bold;color: #f9f2f4;margin-top: 15px;">
                加载中....
            </p>
        </div>
        <%--</div>
    </div>--%>
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
        centerModal('loading')
        centerModal('exportSuccess')
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

        /*送检案件数*/
        $("div[name='sjCaseCountBtn']").on("click", function () {
            var acceptDatetimeStart = $("input[name='acceptDatetimeStart']").val();
            var acceptDatetimeEnd = $("input[name='acceptDatetimeEnd']").val();

            $('#loading').modal('show')
            centerModal('loading')
            location.href = "<%=path%>/violateWealth/violateWealthCaseStatsList?acceptDatetimeStart=" + acceptDatetimeStart + "&acceptDatetimeEnd=" + acceptDatetimeEnd;
        });

        /*入库物证案件数*/
        $("div[name='rkWzCaseCountBtn']").on("click", function () {
            var acceptDatetimeStart = $("input[name='acceptDatetimeStart']").val();
            var acceptDatetimeEnd = $("input[name='acceptDatetimeEnd']").val();
            $('#loading').modal('show')
            centerModal('loading')
            $.ajax({
                url: "<%=path%>/violateWealth/ruWzcaseCountList?acceptDatetimeStart=" + acceptDatetimeStart + "&acceptDatetimeEnd=" + acceptDatetimeEnd,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        //清空tbody
                        $("#dbSjCaseCountView").html("");

                        var acceptDatetimeStart = data.acceptDatetimeStart;
                        var acceptDatetimeEnd = data.acceptDatetimeEnd;
                        var totalCount = data.totalCount;
                        var totalList = data.totalList;
                        var dbSjCaseCountViewList = data.dbSjCaseCountViewList;
                        if (acceptDatetimeStart != undefined) {
                            $("input[name='acceptDatetimeStart']").val(acceptDatetimeStart);
                        }
                        if (acceptDatetimeEnd != undefined) {
                            $("input[name='acceptDatetimeEnd']").val(acceptDatetimeEnd);
                        }
                        if (totalCount != undefined) {
                            $("span[name='rkWzCaseTotalCount']").text(totalCount);
                        }
                        if (dbSjCaseCountViewList != undefined) {
                            var index = 1;
                            $.each(dbSjCaseCountViewList, function (key, values) {
                                var orgName = key
                                var newRowHtml = "<td>" + index++ + "</td>";
                                newRowHtml += "<td>" + orgName + "</td>";
                                $.each(values, function (n, list) {
                                    newRowHtml += "<td>" + list.number + "</td>";
                                });
                                $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                            });

                        }
                        if (totalList != undefined) {
                            var newRowHtml = "<td></td>";
                            newRowHtml += "<td>总计</td>";
                            $.each(totalList, function (n, list) {
                                newRowHtml += "<td>" + list + "</td>";
                            });
                            $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                        }
                        $('#loading').modal('hide')
                    }
                }
            });

        });
        /*送检样本数*/
        $("div[name='sjSampleCountBtn']").on("click", function () {
            var acceptDatetimeStart = $("input[name='acceptDatetimeStart']").val();
            var acceptDatetimeEnd = $("input[name='acceptDatetimeEnd']").val();
            $('#loading').modal('show')
            centerModal('loading')
            $.ajax({
                url: "<%=path%>/violateWealth/sjSampleCountList?acceptDatetimeStart=" + acceptDatetimeStart + "&acceptDatetimeEnd=" + acceptDatetimeEnd,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        //清空tbody
                        $("#dbSjCaseCountView").html("");

                        var acceptDatetimeStart = data.acceptDatetimeStart;
                        var acceptDatetimeEnd = data.acceptDatetimeEnd;
                        var totalCount = data.totalCount;
                        var totalList = data.totalList;
                        var dbSjCaseCountViewList = data.dbSjCaseCountViewList;
                        if (acceptDatetimeStart != undefined) {
                            $("input[name='acceptDatetimeStart']").val(acceptDatetimeStart);
                        }
                        if (acceptDatetimeEnd != undefined) {
                            $("input[name='acceptDatetimeEnd']").val(acceptDatetimeEnd);
                        }
                        if (totalCount != undefined) {
                            $("span[name='sjSampleTotalCount']").text(totalCount);
                        }
                        if (dbSjCaseCountViewList != undefined) {
                            var index = 1;
                            $.each(dbSjCaseCountViewList, function (key, values) {
                                var orgName = key
                                var newRowHtml = "<td>" + index++ + "</td>";
                                newRowHtml += "<td>" + orgName + "</td>";
                                $.each(values, function (n, list) {
                                    newRowHtml += "<td>" + list.number + "</td>";
                                });
                                $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                            });

                        }
                        if (totalList != undefined) {
                            var newRowHtml = "<td></td>";
                            newRowHtml += "<td>总计</td>";
                            $.each(totalList, function (n, list) {
                                newRowHtml += "<td>" + list + "</td>";
                            });
                            $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                        }
                        $('#loading').modal('hide')
                    }
                }
            });
        });
        /*入库样本数*/
        $("div[name='rkSampleCountBtn']").on("click", function () {
            var acceptDatetimeStart = $("input[name='acceptDatetimeStart']").val();
            var acceptDatetimeEnd = $("input[name='acceptDatetimeEnd']").val();
            $('#loading').modal('show')
            centerModal('loading')
            $.ajax({
                url: "<%=path%>/violateWealth/ruSampleCountList?acceptDatetimeStart=" + acceptDatetimeStart + "&acceptDatetimeEnd=" + acceptDatetimeEnd,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        //清空tbody
                        $("#dbSjCaseCountView").html("");

                        var acceptDatetimeStart = data.acceptDatetimeStart;
                        var acceptDatetimeEnd = data.acceptDatetimeEnd;
                        var totalCount = data.totalCount;
                        var totalList = data.totalList;
                        var dbSjCaseCountViewList = data.dbSjCaseCountViewList;
                        if (acceptDatetimeStart != undefined) {
                            $("input[name='acceptDatetimeStart']").val(acceptDatetimeStart);
                        }
                        if (acceptDatetimeEnd != undefined) {
                            $("input[name='acceptDatetimeEnd']").val(acceptDatetimeEnd);
                        }
                        if (totalCount != undefined) {
                            $("span[name='rkSampleTotalCount']").text(totalCount);
                        }
                        if (dbSjCaseCountViewList != undefined) {
                            var index = 1;
                            $.each(dbSjCaseCountViewList, function (key, values) {
                                var orgName = key
                                var newRowHtml = "<td>" + index++ + "</td>";
                                newRowHtml += "<td>" + orgName + "</td>";
                                $.each(values, function (n, list) {
                                    newRowHtml += "<td>" + list.number + "</td>";
                                });
                                $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                            });

                        }
                        if (totalList != undefined) {
                            var newRowHtml = "<td></td>";
                            newRowHtml += "<td>总计</td>";
                            $.each(totalList, function (n, list) {
                                newRowHtml += "<td>" + list + "</td>";
                            });
                            $("#dbSjCaseCountView").append("<tr>" + newRowHtml + "</tr>");
                        }
                        $('#loading').modal('hide')
                    }
                }
            });
        });

        /*有效比中率*/
        $("div[name='EffectiveRatioBtn']").on("click", function () {
            var acceptDatetimeStart = $("input[name='acceptDatetimeStart']").val();
            var acceptDatetimeEnd = $("input[name='acceptDatetimeEnd']").val();
            $('#loading').modal('show')
            centerModal('loading')
            $.ajax({
                url: "<%=path%>/violateWealth/effectiveRatioList?acceptDatetimeStart=" + acceptDatetimeStart + "&acceptDatetimeEnd=" + acceptDatetimeEnd,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        //清空table
                        $("#iwCaseTable").html("");

                        var dbStatsCaseInfoViewList = data.dbStatsCaseInfoViewList;

                        var tableHtml = "<table class='table table-hover table-bordered bigTable table-striped' style='table-layout: fixed;margin-top: 0;'><thead><tr><th>分局名称</th><th>案件总数</th><th>入库案件总数</th><th>物证入库比中嫌疑人案件数</th><th>物证串并案件数</th><th>其他入库案件数</th></tr></thead><tbody id='caseTbody' class='case-number'></tbody></table>"
                        $("#iwCaseTable").append(tableHtml);

                        if (dbStatsCaseInfoViewList != undefined) {
                            for (var i = 0; i < dbStatsCaseInfoViewList.length; i++) {
                                var delegateOrgName = dbStatsCaseInfoViewList[i].delegateOrgName;
                                var caseCnt = dbStatsCaseInfoViewList[i].caseCnt;
                                var rkCaseCnt = dbStatsCaseInfoViewList[i].rkCaseCnt;
                                var bzXyrCaseCnt = dbStatsCaseInfoViewList[i].bzXyrCaseCnt;
                                var bzWzCaseCnt = dbStatsCaseInfoViewList[i].bzWzCaseCnt;
                                var otherCaseCnt = dbStatsCaseInfoViewList[i].otherCaseCnt;

                                var newRowHtml = "<td>" + delegateOrgName + "</td>";
                                newRowHtml += "<td>" + caseCnt + "</td>";
                                newRowHtml += "<td>" + rkCaseCnt + "</td>";
                                newRowHtml += "<td>" + bzXyrCaseCnt + "</td>";
                                newRowHtml += "<td>" + bzWzCaseCnt + "</td>";
                                newRowHtml += "<td>" + otherCaseCnt + "</td>";
                                $("#caseTbody").append("<tr>" + newRowHtml + "</tr>");
                            }
                        }

                        $('#loading').modal('hide')
                    }
                }
            });
        });

        $("div[name='invadingWealthexportBtn']").click(function () {

            var idx = $(".checkDiv .active").index();
            if (idx == '4') {
                var newUrl = "<%=path%>/violateWealth/effectiveRatio";
                $("#consignationForm").attr('action', newUrl);
                $("#consignationForm").submit();
            } else {
                var newUrl = "<%=path%>/violateWealth/exportInvadingWealth";
                $("#consignationForm").attr('action', newUrl);
                $("#consignationForm").submit();
            }

            var oldUrl = "<%=path%>/violateWealth/violateWealthCaseStatsList";
            $("#consignationForm").attr('action', oldUrl);

        })
        $(".title-tab").click(function () {
            $(this).addClass("active").siblings().removeClass("active")
        })
//        导出成功模态框居中显示

        function centerModal(id) {
            $("#" + id).on('shown.bs.modal', function () {
                var $this = $(this);
                var dialog = $this.find('.modal-dialog');
                //此种方式，在使用动画第一次显示时有问题
                //解决方案，去掉动画fade样式
                var top = ($(window).height() - dialog.height()) / 2;
                dialog.css({
                    marginTop: top
                });
            })
        }
    });
</script>
</body>

</html>