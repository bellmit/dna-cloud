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

        .export{
            font-size:14px;
            color: #0C81F5;
            font-weight:normal;
            cursor: pointer;
        }
        .time:before{
            content: '';
            display: inline-block;
            width:20px;
            height:20px;
            background: url("../img/time.png") no-repeat center;
            background-size:100%;
            vertical-align: middle;
        }
        .pipei-number{
            height:36px;
            line-height:36px;
            color: #FF9200;
            text-align: right;
        }
        .blue-font-btn:hover{
            color: #0C81F5;
            font-size:14px;
            font-weight:bold;
            background: #e7f2fe;
        }
        .look-detail{
            background: url("../img/look.png") no-repeat center;
            background-size:26px;
            width:100%;
            height:38px;
            display: inline-block;
            color: transparent;
        }
        .look-detail:hover{
            background: #fff6ea;
            color: #FDA228;
            width:100%;
            height:38px;
            display: inline-block;
        }
        tbody tr td:last-of-type{
            padding:0;
            height:38px;
            line-height:38px;
            text-align: center;
        }
        span.new{
            display: inline-block;
            width:20px;
            height:20px;
            line-height: 20px;
            text-align: center;
            border-radius:50%;
            background: #FF5A56;
            color: #fff;
        }
        .pink td{
            background: #ffeeee;;
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
<%--<ol class="breadcrumb">--%>
    <%--<li class="active">查询条件(串并案统计)</li>--%>
<%--</ol>--%>
<div class="modal fade" id="caseNature" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 1070px;">
        <div class="modal-content">
            <div class="modal-body">
                <%--<div class="checkbox-div">--%>
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
                <form id="consignationForm" action="<%=path %>/caseQuery/caseQuery" class="form-horizontal tasi-form form-inline"
                      method="post">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件名称</label>
                                <input type="text" class="form-control" placeholder="请输入案件名称">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件编号</label>
                                <input type="text" class="form-control" placeholder="请输入案件编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>案件性质</label>
                                <input type="text" name="caseType" value="${query.caseType}" class="form-control"
                                       data-toggle="modal" data-target="#caseNature">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>现勘编号</label>
                                <input type="text" class="form-control" placeholder="请输入现勘编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>受理时间</label>
                                <input type="text" class="form-control form_datetime" placeholder="请选择开始时间">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label style="width: 56px;">至</label>
                                <input type="text" class="form-control form_datetime" placeholder="请选择结束时间">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>样本编号</label>
                                <input type="text" class="form-control" placeholder="请输入样本编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label style="width: 56px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <button class="btn btn-blue" type="submit" id="addInformant">查询</button>
                                <button type="button" name="reset" class="btn btn-blue-border">重置</button>
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
                <div class="col-md-2">案件信息</div>
                <div class="col-md-7" style="text-align: right">
                    <img src="../img/echarts.png" alt="" width="24px">
                    <span>共2组涉及6案，比中3人</span>
                </div>
                <div class="col-md-1 col-md-offset-1 export" data-target="#exportSuccess" data-toggle="modal">
                    <img src="../img/daochu.png" alt="" width="24px">
                    <span>导出</span>
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6">
                        <span class="time">
                            比中时间：2019/12/12
                        </span>
                        <button class="btn blue-font-btn" style="margin-left: 26px;" >案件串并文件生成</button>
                    </div>
                    <div class="col-md-6 pipei-number">
                        匹配个数: 15,17
                    </div>
                </div>
                <table class="table table-hover table-bordered bigTable table-striped" style="table-layout: fixed;">
                    <thead>
                    <tr>
                        <th style="width: 55px;">序号</th>
                        <th>案件名称</th>
                        <th>案件破获状态</th>
                        <th>样本条码</th>
                        <%--<th style="width: 90px;">案发类型</th>--%>
                        <th >样本名称</th>
                        <th>样本类型</th>
                        <th>所属分局</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="consignatioInfoListTbody">
                    <tr>
                        <td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td>
                        <td>
                            <a href="" class="look-detail">查看明细</a>
                        </td>
                    </tr>
                    <tr>
                        <td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td>
                        <td>
                            <a href="" class="look-detail">查看明细</a>
                        </td>
                    </tr>
                    <tr class="pink">
                        <td>1<span class="new">新</span></td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td>
                        <td>
                            <a href="" class="look-detail">查看明细</a>
                        </td>
                    </tr>
                    <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                        <tr>
                            <td>${s.count}
                                <input type="hidden" value="${caseInfo.entity.entrustStatus}" >
                                <input type="hidden" value="${caseInfo.entity.caseXkNo}">
                            </td>
                            <td title="${caseInfo.entity.caseNo}">
                                <c:if test="${caseInfo.entity.entrustStatus == '0'and caseInfo.entity.caseXkNo==null}">
                                    <i class="fa bu">无K号</i>
                                </c:if>
                                <c:if test="${caseInfo.appendFlag == '1'}">
                                    <i class="fa bu">补(${caseInfo.replacementNum})</i>
                                </c:if>${caseInfo.entity.caseNo}
                            </td>
                            <td title="${caseInfo.entity.caseName}">
                                <a name="urlName">${caseInfo.entity.caseName}</a>
                            </td>
                            <td>${caseInfo.casePropertyName}</td>
                            <td><fmt:formatDate value="${caseInfo.entity.caseDatetime}" pattern="yyyy-MM-dd"/></td>
                                <%--<td>${caseInfo.caseTypeName}</td>--%>
                            <td><fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${caseInfo.delegator1Name},${caseInfo.delegator2Name}</td>
                            <td>${caseInfo.caseStatusName}</td>
                            <td>${caseInfo.acceptorId}</td>
                            <td title="${caseInfo.orgQualification}">${caseInfo.orgQualification}</td>
                            <td>
                                <input type="hidden" name="caseXkNo" value="${caseInfo.entity.caseXkNo}">
                                <input type="hidden" id="consignmentId" name="consignmentId"
                                       value="${caseInfo.consignmentId}">
                                <input type="hidden" id="caseId" name="caseId" value="${caseInfo.entity.caseId}">
                                <input type="hidden" id="entrustStatus" name="entrustStatus" value="${caseInfo.entity.entrustStatus}">
                                <input type="hidden" id="status" name="status" value="${caseInfo.status}">
                                <c:if test="${caseInfo.status == '01'||caseInfo.status == '05'}">
                                    <button type="button" name="editBtn"
                                            class="btn-icon btn-icon-blue btn-icon-bianji-blue ">编辑
                                    </button>
                                </c:if>
                                <c:if test="${caseInfo.status == '02' || caseInfo.status == '03'}">
                                    <button type="button" name="lookBtn"
                                            class="btn-icon btn-icon-yellow btn-icon-chakan-yellow">查看
                                    </button>
                                </c:if>

                                <c:if test="${caseInfo.caseStatusName == '未受理'}">
                                    <button type="button" name="delBtn"
                                            class="btn-icon btn-icon-red btn-icon-shanchu-red ">删除
                                    </button>
                                </c:if>
                            </td>
                            <td>
                                <button class="btn-font btn-font-green" name="delegateDocBtn">鉴定委托书</button>
                                <button class="btn-font btn-font-green" name="employDocBtn">聘请书</button>
                            </td>
                        </tr>
                    </c:forEach>
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

        kkpager.generPageHtml({
            pno: ${pageInfo.page},
            //总页码
            total: ${pageInfo.pageCount},
            //总数据条数
            totalRecords: ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer: '<%=path%>/caseQuery/caseQuery',
            //链接尾部
            //hrefLatter: '.html',
            getLink: function (page) {
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#consignationForm").serialize();
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
//        案件性质确定按钮
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