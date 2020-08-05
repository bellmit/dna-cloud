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
        mdIns{
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
    <form id="consignationForm" action="<%=path %>/caseQuery/caseQuery" class="form-horizontal tasi-form"
          method="post">
    <div class="record">
        <div class="recordName">
            <span>案件查询</span>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>现场勘查编号</p>
                <p><input type="text" id="caseXkNo" name="entity.caseXkNo" value="${query.entity.caseXkNo}"
                       class="form-control inptyp" placeholder="请输入现场勘查编号"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案件三版本编号</p>
                <p><input type="text" id="xkAno" name="entity.xkAno" value="${query.entity.xkAno}"
                          class="form-control inptyp" placeholder="请输入案件三版本编号"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>现勘系统委托编号</p>
                <p><input type="text" id="consignationXkNo" name="entity.consignationXkNo" value="${query.entity.consignationXkNo}"
                          class="form-control inptyp" placeholder="请输入现勘系统委托编号"></p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>案件受理编号</p>
                <p><input type="text" id="caseNo" name="entity.caseNo" value="${query.entity.caseNo}"
                           class="form-control inptyp" placeholder="请输入案件受理编号"> </p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案件名称</p>
                <p><input type="text" id="caseName" name="entity.caseName" value="${query.entity.caseName}"
                          class="form-control inptyp" placeholder="请输入案件名称"> </p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案件人员姓名</p>
                <p><input type="text" id="casePersonName" name="casePersonName" value="${query.casePersonName}"
                          class="form-control inptyp" placeholder="请输入案件人员姓名"><p>
            </div>
            <%--<div class="col-md-4 mdIn">
                <p>案件性质</p>
                <div class="select">
                    <select id="caseProperty" name="entity.caseProperty"
                            value="${query.entity.caseProperty}" class="form-control inptyp">
                        <option value="">全部</option>
                        <c:forEach items="${casePropertyListLv1}" var="list" varStatus="cs">
                            <option value="${list.dictCode}"
                                    <c:if test="${list.dictCode eq query.entity.caseProperty}">selected</c:if>>${list.dictName}</option>
                        </c:forEach>
                    </select>
                    <select id="casePropertyLv2" name="entity.casePropertylv2"
                            value="${query.entity.casePropertylv2}" class="form-control inptyp" style="display:none;">
                        &lt;%&ndash;<option value="">全部</option>&ndash;%&gt;
                        <c:forEach items="${casePropertyListLv2}" var="list" varStatus="cs">
                            <option value="${list.dictCode}"
                                    <c:if test="${list.dictCode eq query.entity.casePropertylv2}">selected</c:if>>${list.dictName}</option>
                        </c:forEach>
                    </select>
                        &lt;%&ndash;<select  name="entity.caseProperty" id="caseProperty" class="form-control inptyp">
                            <option value="" disabled selected hidden>全部</option>
                            <c:forEach items="${casePropertyListLv1}" var="list" varStatus="s">
                                <option value="${list.dictCode}"
                                        <c:if test="${query.entity.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}
                                </option>
                            </c:forEach>
                        </select>&ndash;%&gt;
                        &lt;%&ndash;<select  name="casePropertyLv2" id="casePropertyLv2" &lt;%&ndash;style="display:none"&ndash;%&gt;>
                            &lt;%&ndash;<option value="" disabled selected hidden>请选择案发性质</option>&ndash;%&gt;
                            <c:forEach items="${casePropertyListLv2}" var="list" varStatus="s">
                                <option value="${list.dictCode}"
                                        <c:if test="${query.entity.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}
                                </option>
                            </c:forEach>
                        </select>&ndash;%&gt;
                </div>
            </div>--%>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>案件性质</p>
                <div class="select">
                    <select id="caseProperty" name="entity.caseProperty"
                            value="${query.entity.caseProperty}" class="form-control inptyp">
                        <option value="">全部</option>
                        <c:forEach items="${casePropertyListLv1}" var="list" varStatus="cs">
                            <option value="${list.dictCode}"
                                    <c:if test="${list.dictCode eq query.entity.caseProperty}">selected</c:if>>${list.dictName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-4 mdIn">
                <p>二级案件性质</p>
                <div class="select">
                    <select id="casePropertyLv2" name="entity.casePropertylv2"
                            value="${query.entity.casePropertylv2}" class="form-control inptyp">
                        <option value="">全部</option>
                        <c:forEach items="${casePropertyListLv2}" var="list" varStatus="cs">
                            <option value="${list.dictCode}"
                                    <c:if test="${list.dictCode eq query.entity.casePropertylv2}">selected</c:if>>${list.dictName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-4 mdIn">
                <p>委托人姓名</p>
                <p><input type="text" id="delegator1Name" name="delegator1Name" value="${query.delegator1Name}"
                          class="form-control inptyp" placeholder="请输入委托人姓名"><p>
            </div>
        </div>
        <%--<div class="recordBot">--%>
        <%--</div>--%>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>案发时间</p>
                <p><input type="text" name="entity.caseDatetime" id="caseDatetime"
                 value="<fmt:formatDate value="${query.entity.caseDatetime}" pattern="yyyy-MM-dd"/>"
                 class="form-control form_datetime inptyp" placeholder="请输入案发时间" readonly></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案发辖区</p>
                <p><input type="text" id="caseLocation" name="entity.caseLocation" value="${query.entity.caseLocation}"
                       class="form-control inptyp" placeholder="请输入案发辖区"><p>
                <%--<p>--%>
                    <%--<select class="form-control">--%>
                        <%--<option style="display: none" selected value="" disabled>请选择辖区</option>--%>
                        <%--<option>2</option>--%>
                    <%--</select>--%>
                <%--</p>--%>
            </div>
            <div class="col-md-4 mdIn">
                <p>联系电话</p>
                <p><input type="text" id="phone" name="phone" value="${query.phone}"
                          class="form-control inptyp" placeholder="请输入联系电话"><p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>证件号码</p>
                <p> <input type="text" id="casePersonCard" name="casePersonCard" value="${query.casePersonCard}"
                           class="form-control inptyp" placeholder="请输入证件号码"><p>
            </div>
            <div class="col-md-4 mdIn">
                <p>物证编号</p>
                <p><input type="text" id="wzSampleNo" name="wzSampleNo" value="${query.wzSampleNo}"
                           class="form-control inptyp" placeholder="请输入物证编号"><p>
            </div>
            <div class="col-md-4 mdIn">
                <p>检材受理编号</p>
                <p><input type="text" id="sampleNo" name="sampleNo" value="${query.sampleNo}"
                           class="form-control inptyp" placeholder="请输入检材受理编号"><p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>检材名称</p>
                <p><input type="text" id="sampleName" name="sampleName" value="${query.sampleName}"
                           class="form-control inptyp" placeholder="请输入检材名称"><p>
            </div>
            <div class="col-md-4 mdIn">
                <p><input type="hidden" name="survey" id="survey" value="${survey}">
                    <input type="hidden" name="tableType" id="tableType" value="${tableType}"/></p>
                <p class="btnsf"><button class="liket" type="submit" id="addInformant">查询</button>
                    <button class="rests" name="reset" >重置条件</button></p>
            </div>
        </div>
    </div>
  </form>
    <div class="record">
        <div class="recordName">
            <span>查询结果</span>
        </div>
        <div class="tabMax">
            <div class="topTan">
                <ul class="tabList">
                    <button type="button" id="tableBtn1" class="btn nowrapt">待送检<p class="tabNum">${tableType1Cnt}</p></button>
                    <button type="button"  id="tableBtn2" class="btn nowrapt">超时未送检<p class="tabNum">${tableType2Cnt}</p></button>
                    <button type="button"  id="tableBtn3" class="btn nowrapt">已送检<p class="tabNum">${tableType3Cnt}</p></button>
                </ul>
                <p class="tabN"><span><img src="<%=path%>/img/xu.png" alt=""></span><span>总计：${caseInfoCount}个案件 ${caseInfoCount}次委托</span></p>
            </div>
            <c:if test="${tableType == '1'}">
            <div class="maxTab">
                <table class="table table-striped tabl table-bordered">
                    <thead>
                    <tr>
                        <th width="55px">序号</th>
                        <th width="55px">补送</th>
                        <th>案件名称</th>
                        <th>案件性质</th>
                        <th>案发时间</th>
                        <th>案发地点</th>
                        <th>现勘编号</th>
                        <th>案件三版本编号</th>
                        <th width="80px">委托人姓名</th>
                        <th>委托登记时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="consignatioInfoListTbody">
                    <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                    <tr>
                        <td>${s.count}
                            <input type="hidden" value="${caseInfo.entity.entrustStatus}" >
                            <input type="hidden" value="${caseInfo.entity.caseXkNo}"></td>
                        <td title="${caseInfo.entity.caseNo}">
                            <c:if test="${caseInfo.appendFlag == '1'}">
                                <div class="po"><p class="isbo"> <i class="fa bu">补</i></p></div>
                            </c:if>
                        </td>
                        <td title="${caseInfo.entity.caseName}">
                            <a name="urlName">${caseInfo.entity.caseName}</a>
                        </td>
                        <td>${caseInfo.casePropertyName}</td>
                        <td><fmt:formatDate value="${caseInfo.entity.caseDatetime}" pattern="yyyy-MM-dd"/></td>
                        <td>${caseInfo.entity.caseLocation}</td>
                        <td>${caseInfo.entity.caseXkNo}</td>
                        <td>${caseInfo.entity.xkAno}</td>
                        <td>${caseInfo.delegator1Name}</td>
                        <td><fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/></td>
                        <td class="tad">
                            <input type="hidden" name="caseXkNo" value="${caseInfo.entity.caseXkNo}">
                            <input type="hidden" id="consignmentId2" name="consignmentId"
                                   value="${caseInfo.consignmentId}">
                            <input type="hidden" id="caseId2" name="caseId" value="${caseInfo.entity.caseId}">
                            <input type="hidden" id="entrustStatus" name="entrustStatus" value="${caseInfo.entity.entrustStatus}">
                            <input type="hidden" id="status" name="status" value="${caseInfo.status}">
                            <button type="button" name="editBtn"
                                    class="btn-icon btns-date edit" disabled>预约送检
                            </button>

                            <button type="button" name="delBtn1"
                                    class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                            </button>
                            <button type="button" name="editBtn1"
                                    class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div id="kkpager"></div>
            </div>
            </c:if>
            <c:if test="${tableType == '2'}">
            <div class="maxTab">
                <table class="table table-striped tabl table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>补送</th>
                        <th>案件性质</th>
                        <th>案件名称</th>
                        <th>案发时间</th>
                        <th>案发地点</th>
                        <th>现勘编号</th>
                        <th>案件三版本编号</th>
                        <th>委托人姓名</th>
                        <th>委托登记时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="consignatioInfoListTbody2">
                    <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                        <tr>
                            <td>${s.count}
                                <input type="hidden" value="${caseInfo.entity.entrustStatus}" >
                                <input type="hidden" value="${caseInfo.entity.caseXkNo}"></td>
                            <td title="${caseInfo.entity.caseNo}">
                                <c:if test="${caseInfo.appendFlag == '1'}">
                                    <div class="po"><p class="isbo"> <i class="fa bu">补</i></p></div>
                                </c:if>
                            </td>
                            <td title="${caseInfo.entity.caseName}">
                                <a name="urlName">${caseInfo.entity.caseName}</a>
                            </td>
                            <td>${caseInfo.casePropertyName}</td>
                            <td><fmt:formatDate value="${caseInfo.entity.caseDatetime}" pattern="yyyy-MM-dd"/></td>
                            <td>${caseInfo.entity.caseLocation}</td>
                            <td>${caseInfo.entity.caseXkNo}</td>
                            <td>${caseInfo.entity.xkAno}</td>
                            <td>${caseInfo.delegator1Name}</td>
                            <td><fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td class="tad">
                                <input type="hidden" name="caseXkNo" value="${caseInfo.entity.caseXkNo}">
                                <input type="hidden" id="consignmentId" name="consignmentId"
                                       value="${caseInfo.consignmentId}">
                                <input type="hidden" id="caseId" name="caseId" value="${caseInfo.entity.caseId}">
                                <input type="hidden" id="entrustStatus1" name="entrustStatus" value="${caseInfo.entity.entrustStatus}">
                                <input type="hidden" id="status1" name="status" value="${caseInfo.status}">
                                <button type="button" name="editBtn"
                                        class="btn-icon btns-date edit">预约送检
                                </button>

                                <button type="button" name="delBtn2"
                                        class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                </button>
                                <button type="button" name="editBtn2"
                                        class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div id="kkpager"></div>
            </div>
            </c:if>
            <c:if test="${tableType == '3'}">
            <div class="maxTab">
                <table class="table table-striped tabl table-bordered">
                    <thead>
                    <tr>
                        <th width="55px">序号</th>
                        <th>案件受理编号</th>
                        <th width="55px">补送</th>
                        <th>案件性质</th>
                        <th>案件名称</th>
                        <th>案发时间</th>
                        <th width="60px">案发地点</th>
                        <th>现勘编号</th>
                        <th>案件三版本编号</th>
                        <th>委托人姓名</th>
                        <th>委托登记时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="consignatioInfoListTbody3">
                    <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                        <tr>
                            <td>${s.count}
                                <input type="hidden" value="${caseInfo.entity.entrustStatus}" >
                                <input type="hidden" value="${caseInfo.entity.caseXkNo}"></td>
                            <td>${caseInfo.entity.caseNo}</td>
                            <td title="${caseInfo.entity.caseNo}">
                                <c:if test="${caseInfo.appendFlag == '1'}">
                                    <div class="po"><p class="isbo"> <i class="fa bu">补</i></p></div>
                                </c:if>
                            </td>
                            <td title="${caseInfo.entity.caseName}">
                                <a name="urlName">${caseInfo.entity.caseName}</a>
                            </td>
                            <td>${caseInfo.casePropertyName}</td>
                            <td><fmt:formatDate value="${caseInfo.entity.caseDatetime}" pattern="yyyy-MM-dd"/></td>
                            <td>${caseInfo.entity.caseLocation}</td>
                            <td>${caseInfo.entity.caseXkNo}</td>
                            <td>${caseInfo.entity.xkAno}</td>
                            <td>${caseInfo.delegator1Name}</td>
                            <td><fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td class="tad">
                                <input type="hidden" name="caseXkNo" value="${caseInfo.entity.caseXkNo}">
                                <input type="hidden" id="consignmentId3" name="consignmentId"
                                       value="${caseInfo.consignmentId}">
                                <input type="hidden" id="caseId3" name="caseId" value="${caseInfo.entity.caseId}">
                                <input type="hidden" id="entrustStatus3" name="entrustStatus" value="${caseInfo.entity.entrustStatus}">
                                <input type="hidden" id="status3" name="status" value="${caseInfo.status}">
                                <button type="button" name="editBtn3"
                                        class="btn-icon btns-info">查看详情
                                </button>
                                <button type="button" name="replacementBtn"
                                        class="btn-icon btns-add">补送登记
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div id="kkpager"></div>
            </div>
            </c:if>
        </div>
    </div>
</div>
<script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script>
<script>
    $(function () {

        var tableType = $("#tableType").val();
        if(tableType == '1'){
            $("#tableBtn1").removeClass("nowrapt").addClass("actives");
        }else if(tableType == '2'){
            $("#tableBtn2").removeClass("nowrapt").addClass("actives");
        }else if(tableType == '3'){
            $("#tableBtn3").removeClass("nowrapt").addClass("actives");
        }

        //重置
        $("button[name='reset']").on("click", function () {
            var survey = $("input[name='survey']").val();
            location.href = "<%=path%>/caseQuery/caseQuery?survey=" + survey;
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

           //删除
        $("button[name='delBtn1']", "#consignatioInfoListTbody").on("click", function () {
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


        //删除
        $("button[name='delBtn2']", "#consignatioInfoListTbody2").on("click", function () {
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


        //修改委托登记(待送检)
        $("button[name='editBtn1']", "#consignatioInfoListTbody").on("click", function () {
            var consignmentId = $("input[name='consignmentId']", $(this).parent("td")).val();
            var caseId = $("input[name='caseId']", $(this).parent("td")).val();
            var caseXkNo = $("input[name='caseXkNo']", $(this).parent("td")).val();
            var entrustStatus = $("input[name='entrustStatus']", $(this).parent("td")).val();
            var survey = $("input[name='survey']").val();
            if (entrustStatus == '0') {
                //现场委托
                location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
            }else if(entrustStatus == '1') {
                //非现场委托
                location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
            }else {
                if (entrustStatus != '0') {
                    location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
                } else {
                    location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
                }
            }
        });

        //添加补送
        $("button[name='replacementBtn']").on("click",function(){
            var caseId = $("input[name='caseId']", $(this).parent("td").parent("tr")).val();
            var caseXkNo = $("input[name='caseXkNo']", $(this).parent("td").parent("tr")).val();
            var entrustStatus = $("input[name='entrustStatus']", $(this).parent("td").parent("tr")).val();
            /*if(!caseXkNo){
             location.href="<%=path%>/caseQuery/replacementNonCaseByCaseId?caseId=" + caseId;
             }else{
             location.href="<%=path%>/caseQuery/replacementByCaseId?caseId=" + caseId;
             }*/

            if (entrustStatus == '0') {
                //现场委托
                location.href="<%=path%>/caseQuery/replacementByCaseId?caseId=" + caseId;
            }else if(entrustStatus == '1') {
                //非现场委托
                location.href="<%=path%>/caseQuery/replacementNonCaseByCaseId?caseId=" + caseId;
            }else if (entrustStatus == '2') {
                //在逃人员委托
                location.href="<%=path%>/caseQuery/replacementFugitivesCaseByCaseId?caseId=" + caseId;
            }else {
                if(!caseXkNo){
                    location.href="<%=path%>/caseQuery/replacementNonCaseByCaseId?caseId=" + caseId;
                }else{
                    location.href="<%=path%>/caseQuery/replacementByCaseId?caseId=" + caseId;
                }
            }
        })

        //修改委托登记（超时未送检）
        $("button[name='editBtn2']", "#consignatioInfoListTbody2").on("click", function () {
            var consignmentId = $("input[name='consignmentId']", $(this).parent("td")).val();
            var caseId = $("input[name='caseId']", $(this).parent("td")).val();
            var caseXkNo = $("input[name='caseXkNo']", $(this).parent("td")).val();
            var entrustStatus = $("input[name='entrustStatus']", $(this).parent("td")).val();
            var survey = $("input[name='survey']").val();
            if (entrustStatus == '0') {
                //现场委托
                location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
            }else if(entrustStatus == '1') {
                //非现场委托
                location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
            }else {
                if (entrustStatus != '0') {
                    location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
                } else {
                    location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
                }
            }
        });

        // 查看委托登记（已送检）
        $("button[name='editBtn3']", "#consignatioInfoListTbody3").on("click", function () {
            var consignmentId = $("input[name='consignmentId']", $(this).parent("td")).val();
            var caseId = $("input[name='caseId']", $(this).parent("td")).val();
            var caseXkNo = $("input[name='caseXkNo']", $(this).parent("td")).val();
            var entrustStatus = $("input[name='entrustStatus']", $(this).parent("td")).val();
            var survey = $("input[name='survey']").val();
            if (entrustStatus == '0') {
                //现场委托
                location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
            }else if(entrustStatus == '1') {
                //非现场委托
                location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
            }else {
                if (entrustStatus != '0') {
                    location.href = "<%=path%>/caseQuery/editNonCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId;
                } else {
                    location.href = "<%=path%>/caseQuery/editCaseAndBring?consignmentId=" + consignmentId + "&caseId=" + caseId + "&survey=" + survey;
                }
            }
        });

        $("#tableBtn1").on("click", function(){
            $("#tableType").val(1);
            $("#page").val(1);
            $("#consignationForm").submit();
        });
        $("#tableBtn2").on("click", function(){
            $("#tableType").val(2);
            $("#page").val(1);
            $("#consignationForm").submit();
        });
        $("#tableBtn3").on("click", function(){
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
        $(".rests").on("click",function () {
            $.each($(".inptyp"),function (i,v) {
                $(v).val("")
            })
        })
        $("#caseProperty").on("change", function () {
            var selectedParentCode = $("option:selected", $(this)).val();
            if (selectedParentCode != null && selectedParentCode != "") {
                var urlStr = "<%=path%>/delegate/getCasePropertyListByParentId.html?parentCode=" + selectedParentCode;
                $.ajax({
                    type: "get",
                    url: urlStr,
                    dataType: "json",
                    success: function (casePropertyListLv2) {
                        $("option:gt(0)", "#casePropertyLv2").remove();
                        var newHtml = "";
                        if (casePropertyListLv2.length > 0) {
                            var len = casePropertyListLv2.length;
                            for (var i = 0; i < len; i++) {
                                newHtml += "<option value='" + casePropertyListLv2[i].dictCode + "'>" + casePropertyListLv2[i].dictName + "</option>";
                            }
                            $("#casePropertyLv2").append(newHtml);
                            /*if(len > 1) {
                                document.getElementById('casePropertyLv2').style.display = 'block';
                            }else{
                                document.getElementById('casePropertyLv2').style.display = 'none';
                                document.getElementById('casePropertyLv2').value = "";
                            }*/
                        }
                    }
                });
            } else {
                $("option:gt(0)", "#casePropertyLv2").remove();
                document.getElementById('casePropertyLv2').style.display = 'none';
            }
        });
    })
</script>
</body>
</html>
