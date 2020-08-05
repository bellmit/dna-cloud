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
            white-space: nowrap;
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

        .case-number tr td:nth-child(2), .case-number tr td:nth-child(4), .case-number tr td:nth-child(6), .case-number tr td:nth-child(8) {
            color: #0C81F5;
        }

        .case-number tr td:nth-child(3), .case-number tr td:nth-child(5), .case-number tr td:nth-child(7), .case-number tr td:nth-child(9) {
            color: #FF5A56;
        }

        /*.case-number tr:last-of-type td {
            color: #333;
            font-size: 14px;
            font-weight: bold;
        }
        .case-number tr:last-of-type td:nth-child(2) {
            color: #FF5A56;
        }*/

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
                <form id="consignationForm" action="<%=path%>/actionRate/violateWealthCaseStatsList"
                      class="form-horizontal tasi-form form-inline"
                      method="post">
                    <div class="row">
                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>送检单位</label>
                                <select class="form-control" name="delegateOrgCode">
                                    <option value="">请选择单位</option>
                                    <c:forEach items="${orgInfoList}" var="orgInfo" varStatus="cs">
                                        <option value="${orgInfo.orgCode}"
                                                <c:if test="${orgInfo.orgCode eq query.delegateOrgCode}">selected</c:if> >${orgInfo.orgName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>送检日期</label>
                                <input type="text" class="form-control form_datetime" readonly="readonly"
                                       name="delagateDatetimeStart"
                                       value="${query.delagateDatetimeStart}"
                                       placeholder="请选择开始时间">
                            </div>
                        </div>
                        <div class="col-md-3" style="padding: 0;">
                            <div class="form-group">
                                <label>至</label>
                                <input type="text" class="form-control form_datetime" readonly="readonly"
                                       name="delagateDatetimeEnd"
                                       value="${query.delagateDatetimeEnd}"
                                       placeholder="请选择结束时间">
                            </div>
                        </div>

                        <div class="col-md-3">
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
                    <div class="col-md-8 sec-title">
                        <div class="title-tab active" name="dnaActionRateBtn">
                            侵财类案件DNA综合作用率
                        </div>
                        <div class="title-tab" name="dnaTestRateStatsBtn">
                            侵财类案件DNA检验率
                        </div>
                    </div>
                    <div class="col-md-10 col-md-offset-10 export" name="dnaActionrateExpotrBtn">
                        <img src="../img/daochu.png" alt="" width="24px">
                        <span>导出excel</span>
                    </div>
                </div>
                <table class="table table-hover table-bordered bigTable table-striped"
                       style="table-layout: fixed;margin-top: 0;" id="dnaTestRateTable">
                    <thead>
                    <tr>
                        <th>送检单位</th>
                        <th>送检案件数</th>
                        <th>出鉴定书数</th>
                        <th>有物证入库但未出鉴定书的案件数</th>
                        <th>未出鉴定的案件数</th>
                        <th>计算结果</th>
                    </tr>
                    </thead>
                    <tbody id="dbSjCaseCountView" class="case-number">
                    <c:forEach items="${dbIvDnaActionRateViewList}" var="dnaActionRate" varStatus="s">
                        <tr>
                            <td>${dnaActionRate.delegateOrgName}</td>
                            <td>${dnaActionRate.sjCaseCount}</td>
                            <td>${dnaActionRate.issueCaseCount}</td>
                            <td>${dnaActionRate.rkNotIssueCaseCount}</td>
                            <td>${dnaActionRate.notIssueCaseCount}</td>
                            <td>${dnaActionRate.computedResult}</td>
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
        <div class="exportIng">
            <p>
                <img src="../img/loading.png" class="loading-img" alt="" width="90px">
            </p>
            <p style="font-size: 18px;font-weight: bold;color: #f9f2f4;margin-top: 15px;">
                加载中....
            </p>
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


        /*侵财类案件DNA综合作用率*/
        $("div[name='dnaActionRateBtn']").on("click", function () {
            location.href = "<%=path%>/actionRate/violateWealthCaseStatsList";
        });

        /*侵财类案件DNA检验率*/
        $("div[name='dnaTestRateStatsBtn']").on("click", function () {
            location.href = "<%=path%>/actionRate/dnaTestRateStatsList";
        });


        $("div[name='dnaActionrateExpotrBtn']").click(function () {
            var newUrl = "<%=path%>/actionRate/dnaActionrateExpotr";
            $("#consignationForm").attr('action', newUrl);
            $("#consignationForm").submit();

            var oldUrl = "<%=path%>/actionRate/violateWealthCaseStatsList";
            $("#consignationForm").attr('action', oldUrl);

        })

        $(".title-tab").click(function () {
            $(this).addClass("active").siblings().removeClass("active")
        })


        //导出成功模态框居中显示
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