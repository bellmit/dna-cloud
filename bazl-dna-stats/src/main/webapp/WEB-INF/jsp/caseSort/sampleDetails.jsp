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

      .sample-title span:first-of-type{
          font-size:16px;
          font-weight:bold;
          color: #333;
      }
        .sample-title span:nth-last-of-type(1){
            font-size:16px;
            font-weight:bold;
            color: #FFB72C;
        }


    </style>
</head>

<body>
<div class="row Modular part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>
                    案件基本情况
                </div>
            </div>
            <div class="panel-body">
                    <div class="row">
                        <div class="col-md-4 sample-title">
                            <span>案件编号：</span>
                            <span>${dbCaseSortStatsView.caseNo}</span>
                        </div>
                        <div class="col-md-4 sample-title">
                            <span>案件名称：</span>
                            <span>${dbCaseSortStatsView.caseName}</span>
                        </div>
                        <div class="col-md-4 sample-title">
                            <span>现勘号：</span>
                            <span>${dbCaseSortStatsView.caseXkNo}</span>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</div>
<div class="row Modular notAccepted part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>检材列表</div>
            </div>
            <div class="panel-body" style="padding: 0;">
                <table class="table table-hover table-bordered bigTable table-striped" style="table-layout: fixed;margin-top: 0;">
                    <thead>
                    <tr>
                        <th style="width: 55px;">序号</th>
                        <th>样本编号</th>
                        <th>样本名称</th>
                        <th>入库编号</th>
                        <th>比中样本编号</th>
                        <th>比中样本名称</th>
                    </tr>
                    </thead>
                    <tbody id="dbCaseSortStatsViewListTbody">
                    <c:choose>
                    <c:when test="${dbCaseSortStatsViewList.size() eq 0}">
                        </tbody>
                    </table>
                    <div class="no-data" style="text-align: center;padding: 20px 0;">
                        <p>
                            <img src="../img/none.png" alt="" width="180px">
                        </p>
                        <p style="color: #666;font-size: 18px;">
                            暂无数据，您可重新查询或<a href="<%=path%>/main/home" style="color: #0C81F5;">返回首页</a>
                        </p>
                    </div>
                    </c:when>
                <c:otherwise>
                    <c:forEach items="${dbCaseSampleList}" var="caseSample" varStatus="s">
                    <tr>
                        <td>${s.index+1}</td>
                        <td>${caseSample.sampleNo}</td>
                        <td>${caseSample.sampleName}</td>
                        <td>${caseSample.gjkSysNo}</td>
                        <td>${caseSample.sampleBNo}</td>
                        <td>${caseSample.sampleBName}</td>
                    </tr>
                    </c:forEach>
                    </tbody>
                    </table>
                </c:otherwise>
                </c:choose>
                <div class="row" style="padding: 0px">
                    <div class="col-md-12">
                        <div id="kkpager"></div>
                    </div>
                </div>

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

        kkpager.generPageHtml({
            pno: ${pageInfo.page},
            //总页码
            total: ${pageInfo.pageCount},
            //总数据条数
            totalRecords: ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer: '<%=path%>/caseSort/caseSortStatsList',
            //链接尾部
            //hrefLatter: '.html',
            getLink: function (page) {
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#caseSortform").serialize();
            }
            , lang: {
                firstPageText: '首页',
                firstPageTipText: '首页',
                lastPageText: '尾页',
                lastPageTipText: '尾页',
                prePageText: '上一页',
                prePageTipText: '上一页',
                nextPageText: '下一页',
                nextPageTipText: '下一页',
                totalPageBeforeText: '共',
                totalPageAfterText: '页',
                currPageBeforeText: '当前第',
                currPageAfterText: '页',
                totalInfoSplitStr: '/',
                totalRecordsBeforeText: '共',
                totalRecordsAfterText: '条数据',
                gopageBeforeText: '&nbsp;转到',
                gopageButtonOkText: '确定',
                gopageAfterText: '页',
                buttonTipBeforeText: '第',
                buttonTipAfterText: '页'
            }
        });

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
        $("#consignatioInfoListTbody tr td:last-of-type").mouseenter(function (e) {
            $(".hover-text").text($(this).text())
            $(".triangle-popup").css("top", e.pageY)
            $(".triangle-popup").show()
        })
        $("#consignatioInfoListTbody tr td:last-of-type").mouseleave(function () {
            $(".triangle-popup").hide()
        })
    });
</script>
</body>

</html>