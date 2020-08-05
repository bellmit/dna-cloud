<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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

        .circle {
            display: inline-block;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background: #ffe4e4;
            color: #FF5A56;
            text-align: center;
        }

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

        .triangle-popup {
            display: none;
            position: fixed;
            right: 30px;
            z-index: 99;
            margin-left: -61px;
            width: 100px;
            padding: 10px 20px;
            border: 1px solid #0C81F5;
            border-radius: 4px;
            color: #444;
            background-color: #fff;
            width: 276px;
        }

        .triangle-popup span {
            position: absolute;
            left: 50%;
            top: -10px;
            margin-left: -10px;
            display: block;
            width: 0;
            height: 0;
            border-width: 0 10px 10px;
            border-style: solid;
            border-color: transparent transparent #0C81F5;
        }

        .triangle-popup span em {
            position: absolute;
            left: -10px;
            top: 1px;
            display: block;
            width: 0;
            height: 0;
            border-width: 0 10px 10px;
            border-style: solid;
            border-color: transparent transparent #fff;
        }

        .hover-text {
            font-size: 14px;
            line-height: 24px;
        }

        .hover-text:before {
            content: '';
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 10px;
            background: #0C81F5;
        }

        .export {
            font-size: 14px;
            color: #0C81F5;
            font-weight: normal;
            cursor: pointer;
        }
    </style>
</head>

<body>
<div class="row Modular part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>
                    案件查询条件
                </div>
            </div>
            <div class="panel-body">
                <form action="<%=path%>/caseSort/caseSortStatsList" class="form-inline" id="caseSortform" method="post">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件编号</label>
                                <input type="text" class="form-control" name="caseNo" value="${query.caseNo}"
                                       placeholder="请输入案件编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件名称</label>
                                <input type="text" name="caseName" value="${query.caseName}" class="form-control"
                                       placeholder="请输入案件名称">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件性质</label>
                                <input type="text" name="caseType" value="${query.caseType}" class="form-control"
                                       data-toggle="modal" data-target="#caseNature" placeholder="请选择案件性质">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>现勘编号</label>
                                <input type="text" name="caseXkNo" value="${query.caseXkNo}" class="form-control"
                                       placeholder="请输入现勘编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>样本编号</label>
                                <input type="text" name="sampleNo" value="${query.sampleNo}" class="form-control"
                                       placeholder="请输入样本编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>受理人员</label>
                                <input type="text" name="acceptName" value="${query.acceptName}" class="form-control"
                                       placeholder="请输入受理人员">
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>送检单位</label>
                                <select class="form-control" name="orgName">
                                        <option value="" >请选择单位</option>
                                    <c:forEach items="${delagateOrgList}" var="org" varStatus="cs">
                                        <option value="${org.orgName}" <c:if test="${org.orgName eq query.orgName}">selected</c:if> >${org.orgName}</option>
                                    </c:forEach>
                                </select>

                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>样本名称</label>
                                <input type="text" name="sampleName" value="${query.sampleName}" class="form-control"
                                       placeholder="请输入样本名称">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件状态</label>
                                <div class="select">
                                    <select class="form-control" name="caseStatusName">
                                        <option value="">请选择</option>
                                        <option value="待受理"
                                                <c:if test="${'待受理' eq query.caseStatusName}">selected</c:if>>待受理
                                        </option>
                                        <option value="未领取"
                                                <c:if test="${'未领取' eq query.caseStatusName}">selected</c:if>>未领取
                                        </option>
                                        <option value="被拒绝"
                                                <c:if test="${'被拒绝' eq query.caseStatusName}">selected</c:if>>被拒绝
                                        </option>
                                        <option value="未受理"
                                                <c:if test="${'待受理' eq query.caseStatusName}">selected</c:if>>未受理
                                        </option>
                                        <option value="在检验"
                                                <c:if test="${'在检验' eq query.caseStatusName}">selected</c:if>>在检验
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>受理时间</label>
                                <input type="text" name="acceptDatetimeStart" value="${query.acceptDatetimeStart}"
                                       class="form-control form_datetime" readonly="readonly" placeholder="请选择开始时间">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label style="width: 56px;">至</label>
                                <input type="text" name="acceptDatetimeEnd" value="${query.acceptDatetimeEnd}"
                                       class="form-control form_datetime" readonly="readonly" placeholder="请选择结束时间">
                            </div>
                        </div>
                        <div class="col-md-4">
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
                                    <li>${resultValue.dictName}</li>
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
<div class="row Modular notAccepted part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue row" style="padding: 10px 15px;">
                <div class="col-md-9">案件信息</div>
                <div class="col-md-1 col-md-offset-1 export" name="exportBtn" type="submit">
                    <img src="../img/daochu.png" alt="" width="24px;">
                    <span>导出</span>
                </div>
            </div>
            <div class="panel-body" style="padding: 0;">
                <table class="table table-hover table-bordered bigTable table-striped"
                       style="table-layout: fixed;margin-top: 0;">
                    <thead>
                    <tr>
                        <th style="width: 55px;">序号</th>
                        <th>案件编号</th>
                        <th>案件名称</th>
                        <th style="width: 90px;">案件性质</th>
                        <th style="width: 230px;">现勘编号</th>
                        <th>样本数量</th>
                        <th style="width: 90px;">受理日期</th>
                        <th>送检单位</th>
                        <th>案情简要</th>
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
                    <tr>
                    <c:forEach items="${dbCaseSortStatsViewList}" var="caseSortStats" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>
                                <a href="<%=path%>/caseSort/caseDetails?caseNo=${caseSortStats.caseNo}">${caseSortStats.caseNo}</a>
                            </td>
                            <td>${caseSortStats.caseName}</td>
                            <td>${caseSortStats.caseType}</td>
                            <td>${caseSortStats.caseXkNo}</td>
                            <td>
                                <a href="<%=path%>/caseSort/sampleDetails?caseNo=${caseSortStats.caseNo}">
                                    <span class="circle">${caseSortStats.sampleCount}</span>(个)</a>
                            </td>
                            <td>${caseSortStats.acceptDatetime}</td>
                            <td>${caseSortStats.orgName}</td>
                            <td>${caseSortStats.caseDigest}</td>
                        </tr>
                    </c:forEach>
                    </tr>
                    </tbody>
                    </table>
                </c:otherwise>
                </c:choose>
                <div class="triangle-popup"><span><em></em></span>
                    <p class="hover-text">
                        案情简要~
                    </p>
                </div>
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
        <div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">
                <div class="exportIng">
                    <p>
                        <img src="../img/loading.png" class="loading-img" alt="" width="90px">
                    </p>
                    <p style="font-size: 18px;font-weight: bold;color: #0C81F5;margin-top: 15px;">
                        加载中....
                    </p>
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
        $("button[name='okCheck']").click(function () {
            var selectedList = []
            $(".checkbox-div").each(function () {
                if ($(this).children(".check-list").length > 0) {
                    $(this).find("li.active").each(function () {
                        selectedList.push($(this).text())
                    })
                } else if ($(this).find("span.active").length > 0) {
                    selectedList.push($(this).find("span.active").prev().text())
                }
            });

            $("input[name='caseType']").val(selectedList.join(","));
            $("#caseNature").modal("hide");
        })
        $("#dbCaseSortStatsViewListTbody tr td:last-of-type").mouseenter(function (e) {
            var scrollValue = $(window).scrollTop()
            $(".hover-text").text($(this).text())
            $(".triangle-popup").css("top", e.pageY - scrollValue)
            $(".triangle-popup").show()
        })
        $("#dbCaseSortStatsViewListTbody tr td:last-of-type").mouseleave(function () {
            $(".triangle-popup").hide()
        })

        //导出excel
        $("div[name='exportBtn']").click(function () {

            /*$.ajax({
                type: "post",
                dataType: "json",
                url: "<%=path%>/caseSort/exportCaseSort" ,
                data: $('#caseSortform').serialize(),
                success: function (data) {
                    console.log(data)
                    if (data.success || data.success == true || data.success == "true") {
                        console.log("导出成功！")
                    }
                },
                error : function() {
                    alert("导出excel错误！");
                }
            });*/

            var newUrl = "<%=path%>/caseSort/exportCaseSort";
            $("#caseSortform").attr('action', newUrl);
            $("#caseSortform").submit();

            var oldUrl = "<%=path%>/caseSort/caseSortStatsList";
            $("#caseSortform").attr('action', oldUrl);
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

        /*$("button[name='reset']").click(function () {
            $('input[name="acceptDatetimeStart"]').val('');
            $('input[name="acceptDatetimeEnd"]').val('');
            $("#caseSortform").reset();
        });*/


    });
</script>
</body>

</html>