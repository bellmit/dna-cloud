<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/15
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA案件委托送检系统</title>
    <link href="<%=path %>/js/layui-v2.2.45/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .bix{
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: white;
        }
        .record{
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            float: left;
            padding-bottom: 1%;
        }
        .recordName{
            color: #3586FA;
            font-weight:bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
            border-bottom: 1px solid #CCCCCC;
        }
        .recordName::before{
            content: " ";
            width:4px;
            display: block;
            height:18px;
            background:rgba(53,134,250,1);
            margin-right: 8px;
        }
        .recordBot{
            background: white;
            padding-top: 12px;
            float: left;
            width: 100%;
        }
        .mdIn{
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .mdIns{
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .mdIn p:nth-child(1){
            width: 28%;
            display: flex;
            justify-content: flex-end;
            margin: 0;
            color: #333333;
        }
        .mdIn p:nth-child(2){
            margin: 0;
            padding-right: 7%;
            margin-left: 10px;
            flex: 1;
        }
        .mdIns p:nth-child(1){
            width: 30%;
        }
        .mdIns p:nth-child(2){
            margin: 0;
            display: flex;
            justify-content: space-between;
        }
        .btnsf{
            display: flex;
            justify-content: space-between;
        }
        .liket{
            color: white;
            background: #2E83FE;
            width: 26%;
            border-radius: 2px;
            border: 1px solid #2E83FE;
            height: 35px;
            font-size: 14px;
            outline: none;
        }
        .rests{
            color: #267FFF;
            background: white;
            width: 26%;
            border-radius: 2px;
            border: 1px solid #0C81F5;
            height: 35px;
            font-size: 14px;
            outline: none;
        }
        .tabMax{
            width: 100%;
            padding: 10px 2%;
        }
        .topTan{
            width: 100%;
            display: flex;
        }
        .tabList{
            flex: 1;
            display: flex;
            margin: 0;
        }
        .tabList li{
            white-space: nowrap;
            padding: 10px;
            display: flex;
            font-size: 16px;
            align-items: center;
            color: #333333;
            font-family:PingFang SC;
            cursor: pointer;
        }
        .tabList li p{
            margin: 0;
        }
        .actives{
            border-radius: 5px 5px 0 0;
            font-family:PingFang SC;
            color: white !important;
            background: #1C7BFF;
            border: 1px solid #1C7BFF;
            margin-left: 5px;
        }
        .tabN{
            flex: 1;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            color:rgba(0,0,0,.4);
            font-family:Microsoft YaHei;
        }
        .tabN span{
            margin: 0 2px;
        }
        .tabNum{
            margin: 0;
            background: white;
            border-radius: 100px;
            color: #267FFF;
            width: 30px;
            height: 30px;
            text-align: center;
            line-height: 30px;
            margin-left: 5px !important;
        }
        .nowrapt{
             background:rgba(224,224,224,1);
             border:1px solid rgba(178,178,178,1);
             margin-left: 5px;
             border-radius: 5px 5px 0 0;
         }
        .tabl thead{
            background: #BAE0FF;
            color: #333333;
            font-size:14px;
            font-family:Microsoft YaHei;
            font-weight:bold;
            width: 100%;
        }
        .tabl tr th,td{
            text-align: center;
            overflow:hidden;		/* 内容超出宽度时隐藏超出部分的内容 */
            white-space:nowrap;		/* 不换行 */
            text-overflow:ellipsis;
        }
        table{
               table-layout:fixed;
           }
        .tabl tr th{
            white-space: nowrap;
        }
        .btn-icon+.btn-icon{
            margin: 0;
        }
        .isbo{
            color: white;
            margin: 0;
            background: #FF6146;
            width: 22px;
            height: 22px;
            border-radius: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 12px;
        }
        .isboa{
            color: white;
            margin: 0;
            background: #FF6146;
            width: 51px;
            height: 20px;
            border-radius: 50px;
            text-align: center;
            line-height: 20px;
            font-size: 12px;
        }
        .po{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .btns-date{
            background: url("<%=path%>/img/date.png") no-repeat center;
            font-size: 12px;
        }
        .btns-date:hover{
            color: #FFB900;
        }
        .btns-info{
            background: url("<%=path%>/img/ino.png") no-repeat center;
            font-size: 12px;
        }
        .btns-info:hover{
            color: #0076FF;
        }
        .btns-add{
            background: url("<%=path%>/img/adds.png") no-repeat center;
            font-size: 12px;
        }
        .btns-add:hover{
            color: #FF5851;
        }
        .remove,.edit{
            font-size: 12px;
        }
        .table{
            margin: 0;
        }
        .maxo{
            flex: 1;
        }
        .copo{
            color: #666666;
            color:rgba(102,102,102,1);
            font-family:Microsoft YaHei;
            font-size: 12px;
            display: flex;
            justify-content: center;
            align-items: center;
            background: #E8E8E8;
            line-height: 50px;
            margin: 0;
        }
        #tableBtn1,#tableBtn2,#tableBtn3{
            display: flex;
            align-items: center;
            outline: none;
        }
        .select{
            flex: 1;
            padding-right: 7%;
            margin-left: 10px;
        }
        .tad{
            white-space:nowrap;
            overflow: visible;
            padding: 8px 0px !important;
        }
        .tad button{
            width: 35%;
        }
    </style>
</head>
<body>
<%@ include file="../linkJs.jsp" %>
<div class="bix">
    <form id="consignationForm" action="<%=path %>/delegate/fugitivesRegManage" class="form-horizontal tasi-form"
          method="post">
        <div class="record">
            <div class="recordName">
                <span>案件查询</span>
            </div>
            <div class="recordBot">
                <div class="col-md-4 mdIn">
                    <p>委托书编号</p>
                    <p>
                        <input type="text" id="consignmentNo" name="consignmentNo" value="${query.consignmentNo}"
                               class="form-control inptyp" placeholder="请输入委托书编号">
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>委托开始时间</p>
                    <p>
                        <input type="text" name="delegateStartDatetime" id="delegateStartDatetime"
                               value="<fmt:formatDate value="${query.delegateStartDatetime}" pattern="yyyy-MM-dd"/>"
                               class="form-control form_datetime inptyp" placeholder="请输入委托开始时间" readonly>
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>受理编号</p>
                    <p>
                        <input type="text" id="caseNo" name="caseNo"
                               class="form-control inptyp" placeholder="请输入受理编号">
                    </p>
                </div>
            </div>
            <div class="recordBot">
                <div class="col-md-4 mdIn">
                    <p>受理时间</p>
                    <p>
                        <input type="text" name="acceptDatetime" id="acceptDatetime"
                               value="<fmt:formatDate value="${query.acceptDatetime}" pattern="yyyy-MM-dd"/>"
                               class="form-control form_datetime inptyp" placeholder="请输入受理开始时间" readonly>
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>在逃人员姓名</p>
                    <p>
                        <input type="text" id="fugitivesName" name="fugitivesName" value="${query.fugitivesName}"
                               class="form-control inptyp" placeholder="请输入在逃人员姓名">
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>证件号码</p>
                    <p>
                        <input type="text" id="personCard" name="entity.personCard"
                               class="form-control inptyp" placeholder="请输入在逃人员证件号码">
                    </p>
                </div>
            </div>
            <div class="recordBot">
                <div class="col-md-4 mdIn">
                    <p>在逃人员亲属姓名</p>
                    <p>
                        <input type="text" id="fugitivesRelationName" name="fugitivesRelationName"
                               value="${query.fugitivesRelationName}"
                               class="form-control inptyp" placeholder="请输入在逃人员亲属姓名">
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>亲属证件号码</p>
                    <p>
                        <input type="text" id="fugitivesRelationCard" name="fugitivesRelationCard"
                               value="${query.fugitivesRelationCard}"
                               class="form-control inptyp" placeholder="请输入在逃人员亲属证件号码">
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>检材受理编号</p>
                    <p>
                        <input type="text" id="sampleNo" name="sampleNo" value="${query.sampleNo}"
                               class="form-control inptyp" placeholder="请输入检材受理编号">
                    </p>
                </div>
            </div>
            <div class="recordBot">
                <div class="col-md-4 mdIn">
                    <p>检材名称</p>
                    <p>
                        <input type="text" id="sampleName" name="sampleName" value="${query.sampleName}"
                               class="form-control inptyp" placeholder="请输入检材名称">
                    </p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>委托人姓名</p>
                    <p><input type="text" id="delegator1Name" name="delegator1Name" value="${query.delegator1Name}"
                              class="form-control inptyp" placeholder="请输入委托人姓名">
                    <p>
                </div>
                <div class="col-md-4 mdIn">
                    <p>联系电话</p>
                    <p><input type="text" id="phone" name="phone" value="${query.phone}"
                              class="form-control inptyp" placeholder="请输入联系电话">
                    <p>
                </div>
            </div>
            <div class="recordBot">
                <div class="col-md-4 mdIn">
                    <p><input type="hidden" name="survey" id="survey" value="${survey}">
                        <input type="hidden" name="tableType" id="tableType" value="${tableType}"/></p>
                    <p class="btnsf">
                        <button class="liket" type="submit" id="addInformant">查询</button>
                        <button class="rests" name="reset">重置条件</button>
                    </p>
                </div>
            </div>
        </div>
    </form>
    <div class="maxo">
            <div class="record">
                <div class="recordName">
                    <span>查询结果</span>
                </div>
                <div class="tabMax">
                    <div class="topTan">
                        <ul class="tabList">
                            <button type="button" id="tableBtn1" class="btn nowrapt">待送检<p
                                    class="tabNum">${tableType1Cnt}</p></button>
                            <button type="button" id="tableBtn2" class="btn nowrapt">超时未送检<p
                                    class="tabNum">${tableType2Cnt}</p></button>
                            <button type="button" id="tableBtn3" class="btn nowrapt">已送检<p
                                    class="tabNum">${tableType3Cnt}</p></button>
                        </ul>
                        <p class="tabN"><span><img src="<%=path%>/img/xu.png" alt=""></span><span>总计：${caseInfoCount}个在逃人员，${caseInfoCount}次委托</span>
                        </p>
                    </div>
                    <div class="maxTab">
                        <table class="table table-striped tabl table-bordered">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>补送</th>
                                <th title="在逃人员姓名">在逃人员姓名</th>
                                <th>性别</th>
                                <th>证件号码</th>
                                <th>民族</th>
                                <th>籍贯</th>
                                <th>亲属信息</th>
                                <th title="疑似本人检材">疑似本人检材</th>
                                <th title="疑似本人使用物品">疑似本人使用物品</th>
                                <th title="委托人姓名">委托人姓名</th>
                                <th title="委托时间">委托时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="consignatioInfoListTbody">
                            <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>
                                        <c:if test="${caseInfo.appendFlag == '1'}">
                                            <div class="po"><p class="isbo"><i class="fa bu">补</i></p></div>
                                        </c:if>
                                    </td>
                                    <td>${caseInfo.fugitivesName}</td>
                                    <td>${caseInfo.fugitivesGenderName}</td>
                                    <td>${caseInfo.fugitivesCard}</td>
                                    <td>${caseInfo.fugitivesRace}</td>
                                    <td>${caseInfo.fugitivesNativePlace}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty caseInfo.relationFlag}">${caseInfo.relationFlag}人</c:when>
                                            <c:otherwise>无</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${caseInfo.suspectSampleFlag =='1'}">有</c:if>
                                        <c:if test="${caseInfo.suspectSampleFlag =='0'||caseInfo.suspectSampleFlag ==null}">无</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${caseInfo.suspectUseFlag =='1'}">有</c:if>
                                        <c:if test="${caseInfo.suspectUseFlag =='0'||caseInfo.suspectUseFlag ==null}">无</c:if>
                                    </td>
                                    <td>${caseInfo.delegator1Name}、${caseInfo.delegator2Name}</td>
                                    <td><fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/></td>
                                    <td class="tad">
                                        <input type="hidden" id="consignmentId" name="consignmentId" value="${caseInfo.consignmentId}">
                                        <input type="hidden" id="caseId" name="caseId" value="${caseInfo.entity.caseId}">
                                        <input type="hidden" name="orgId" value="${caseInfo.delegateOrgCode}">
                                        <c:choose>
                                            <c:when test="${tableType == '3'}">
                                                <button type="button" name="viewBtn"
                                                        class="btn-icon btns-info">查看详情
                                                </button>
                                                <button type="button" name="replacementBtn"
                                                        class="btn-icon btns-add">补送登记
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" name="editBtn"
                                                        class="btn-icon btns-date edit">预约送检
                                                </button>
                                                <button type="button" name="editBtn"
                                                        class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                                </button>
                                                <button type="button" name="delBtn"
                                                        class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div id="kkpager"></div>
                    </div>
                </div>
            </div>
    </div>
        <script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script>
        <script>
            $(function () {

                var tableType = $("#tableType").val();
                if (tableType == '1') {
                    $("#tableBtn1").removeClass("nowrapt").addClass("actives");
                } else if (tableType == '2') {
                    $("#tableBtn2").removeClass("nowrapt").addClass("actives");
                } else if (tableType == '3') {
                    $("#tableBtn3").removeClass("nowrapt").addClass("actives");
                }

                $("button[name='replacementBtn']").on("click",function(){
                    var caseId = $("input[name='caseId']", $(this).parent("td").parent("tr")).val();

                    //在逃人员委托
                    location.href="<%=path%>/caseQuery/replacementFugitivesCaseByCaseId?caseId=" + caseId;
                })

                //时间插件
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

                $("#tableBtn1").on("click", function () {
                    $("#tableType").val(1);
                    $("#page").val(1);
                    $("#consignationForm").submit();
                });
                $("#tableBtn2").on("click", function () {
                    $("#tableType").val(2);
                    $("#page").val(1);
                    $("#consignationForm").submit();
                });
                $("#tableBtn3").on("click", function () {
                    $("#tableType").val(3);
                    $("#page").val(1);
                    $("#consignationForm").submit();
                });

                kkpager.generPageHtml({
                    pno: ${pageInfo.page},
                    //总页码
                    total: ${pageInfo.pageCount},
                    //总数据条数
                    totalRecords: ${pageInfo.allRecordCount},
                    //链接前部
                    hrefFormer: '<%=path%>/delegate/fugitivesRegManage',
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
                $(".rests").on("click", function () {
                    $.each($(".inptyp"), function (i, v) {
                        $(v).val("")
                    })
                })

                $("button[name='editBtn']", "#consignatioInfoListTbody").on("click", function () {
                    var consignmentId = $("input[name='consignmentId']", $(this).parent("td")).val();
                    var caseId = $("input[name='caseId']", $(this).parent("td")).val();
                    location.href = "<%=path%>/delegate/editFugitivesReg?caseId=" + caseId + "&consignmentId=" + consignmentId;
                });

                //删除在逃人员信息
                $("button[name='delBtn']").on("click", function () {
                    var res = confirm("删除操作不可逆,您确定要删除这条记录吗！！");
                    if (res == true) {
                        var consignmentId = $("input[name='consignmentId']", $(this).parent("td")).val();
                        var caseId = $("input[name='caseId']", $(this).parent("td")).val();
                        $.ajax({
                            url: "<%=path%>/caseQuery/delCaseAndBring",
                            type: "post",
                            data: {
                                consignmentId: consignmentId,
                                caseId: caseId
                            },
                            dataType: "json",
                            success: function (data) {
                                if (data.code == 0) {
                                    alert(data.message)
                                    window.location.reload();
                                }
                            },
                            error: function (e) {
                                alert(e);
                            }
                        });
                    }
                });
            })
        </script>
</div>
</body>
</html>
