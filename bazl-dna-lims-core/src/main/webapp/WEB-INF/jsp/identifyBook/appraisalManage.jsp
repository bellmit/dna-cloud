<%@ include file="../include.jsp" %>
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
    <title>北京市公安局法医鉴定案件受理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .btn-red:hover {
            width: 100%;
        }

        #videoBox a {
            padding-top: 10px;
            padding-bottom: 10px
        }

        #videoBox button {
            padding: 10px 26px;
        }

        .bu {
            background: #fddddb;
            color: #fc5a56;
            padding: 5px;
            border-radius: 50%;
            font-size: 10px;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="row Modular">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>查询条件</div>
            </div>
            <div class="panel-body">
                <form id="consignationForm" action="<%=path %>/center/appraisalManage" class="form-horizontal tasi-form"
                      method="post">
                    <div class="row">
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>案件名称</label>
                                <input type="text" id="caseName" name="entity.caseName" value="${query.entity.caseName}"
                                       class="form-control"
                                       placeholder="请输入案件名称">
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>案件编号</label>
                                <input type="text" class="form-control" placeholder="请输入案件编号" id="caseNo"
                                       name="entity.caseNo" value="${query.entity.caseNo}">
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>案件性质</label>
                                <div class="select">
                                    <select id="caseProperty" name="entity.caseProperty"
                                            value="${query.entity.caseProperty}" class="form-control">
                                        <option value="">全部</option>
                                        <c:forEach items="${casePropertyList}" var="list" varStatus="cs">
                                            <option value="${list.dictCode}"
                                                    <c:if test="${list.dictCode eq query.entity.caseProperty}">selected</c:if>>${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>案件类型</label>
                                <div class="select">
                                    <select id="caseType" name="entity.caseType"
                                            value="${query.entity.caseType}" class="form-control">
                                        <option value="">全部</option>
                                        <c:forEach items="${caseTypeList}" var="list" varStatus="cs">
                                            <option value="${list.dictCode}"
                                                    <c:if test="${list.dictCode eq query.entity.caseType}">selected</c:if>>${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>委托单位</label>
                                <div class="select">
                                    <select id="delegateOrgCode" name="delegateOrgCode" value="${query.delegateOrgCode}"
                                            class="form-control">
                                        <option value="">全部</option>
                                        <c:forEach items="${orgInfoList}" var="list" varStatus="cs">
                                            <option value="${list.orgCode}"
                                                    <c:if test="${list.orgCode eq query.delegateOrgCode}">selected</c:if>>${list.orgName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>委托时间</label>
                                <div class="row">
                                    <div class="col-md-5 nopadding">
                                        <input type="text" id="delegateStartDatetime" name="delegateStartDatetime"
                                               class="form-control form_datetime"
                                               value="<fmt:formatDate value='${query.delegateStartDatetime}' pattern='yyyy-MM-dd'/>"
                                               placeholder="请选择委托时间"
                                               readonly="readonly">
                                    </div>
                                    <div class="col-md-2" style="margin-top: 7px;">至</div>
                                    <div class="col-md-5 nopadding">
                                        <input type="text" id="delegateEndDatetime" name="delegateEndDatetime"
                                               class="form-control form_datetime"
                                               value="<fmt:formatDate value='${query.delegateEndDatetime}' pattern='yyyy-MM-dd'/>"
                                               placeholder="请选择委托时间"
                                               readonly="readonly">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>委托人</label>
                                <input type="text" id="delegator1Name" name="delegator1Name"
                                       value="${query.delegator1Name}" class="form-control" placeholder="请输入委托人">
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>案件状态</label>
                                <div class="select">
                                    <select id="status" name="status" value="${query.status}"
                                            class="form-control">
                                        <option value="" selected>全部</option>
                                        <c:forEach items="${caseStatusList}" var="list" varStatus="cs">
                                            <option value="${list.dictCode}"
                                                    <c:if test="${list.dictCode eq query.status}">selected</c:if>>${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>现场勘验号</label>
                                <input type="text" id="caseXkNo" name="entity.caseXkNo" value="${query.entity.caseXkNo}"
                                       class="form-control" placeholder="请输入现场勘验号">
                            </div>
                        </div>

                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>现勘A号</label>
                                <input type="text" class="form-control" placeholder="请输入案件编号" id="xkANo"
                                       name="entity.xkANo" value="${query.entity.xkANo}">
                            </div>
                        </div>

                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>受理起止时间</label>
                                <div class="row">
                                    <div class="col-md-5 nopadding">
                                        <input type="text" id="acceptStartDatetime" name="acceptStartDatetime"
                                               class="form-control form_datetime"
                                               value="<fmt:formatDate value='${query.acceptStartDatetime}' pattern='yyyy-MM-dd'/>"
                                               placeholder="请选择受理时间"
                                               readonly="readonly">
                                    </div>
                                    <div class="col-md-2" style="margin-top: 7px;">至</div>
                                    <div class="col-md-5 nopadding">
                                        <input type="text" id="acceptEndDatetime" name="acceptEndDatetime"
                                               class="form-control form_datetime"
                                               value="<fmt:formatDate value='${query.acceptEndDatetime}' pattern='yyyy-MM-dd'/>"
                                               placeholder="请选择受理时间"
                                               readonly="readonly">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 seachInputBox">
                            <div class="form-group">
                                <label>受理人</label>
                                <div class="select">
                                    <select id="acceptorId" name="acceptorId" value="${query.acceptorId}"
                                            class="form-control">
                                        <option value="1001" selected>全部</option>
                                        <c:forEach items="${amPersonalInfoVoList}" var="list" varStatus="cs">
                                            <option value="${list.entity.personalId}"
                                                    <c:if test="${list.entity.personalId eq query.acceptorId}">selected</c:if>>${list.entity.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 seachInputBox">
                            <div class="form-group seachButtonBox">
                                <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                <input type="hidden" name="personalId" id="personalId" value="${operateUser.personalId}"/>
                                <input type="hidden" name="token" id="token"/>
                                <input type="hidden" name="loginname" id="loginname"/>
                                <input type="hidden" name="password" id="password"/>
                                <%--<input type="hidden" name="getTokenUrl" id="getTokenUrl" value="${limsConfigure.getTokenUrl}"/>
                                <input type="hidden" name="uploadIdentifyBookUrl" id="uploadIdentifyBookUrl" value="${limsConfigure.uploadIdentifyBookUrl}"/>--%>
                                <input type="hidden" name="jumpPageUrl" id="jumpPageUrl" value="${limsConfigure.jumpPageUrl}"/>
                                <button class="btn btn-blue" type="submit" id="addInformant">查询</button>
                                <a href="<%=path%>/center/appraisalManage" class="btn btn-blue-border">重置</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade popBox bigBox" id="selectReviewer" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">审核鉴定书</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-7">
                        <div class="form-group">
                            <label>检验人</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="titleOne" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${technicalTitlesList}" var="list">
                                        <option value="${list.dictCode}" >${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="inspector1" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${amPersonalInfoVoList}" var="amPersonalInfo">
                                        <option value="${amPersonalInfo.entity.personalId}" >${amPersonalInfo.entity.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-7">
                        <div class="form-group">
                            <label>复核人</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="titleTwo" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${technicalTitlesList}" var="list">
                                        <option value="${list.dictCode}" >${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="inspector2" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${amPersonalInfoVoList}" var="amPersonalInfo">
                                        <option value="${amPersonalInfo.entity.personalId}" >${amPersonalInfo.entity.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>授权签字人审核</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="titleThree" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${technicalTitlesList}" var="list">
                                        <option value="${list.dictCode}" >${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <div class="select">
                                <select id="inspector3" class="form-control titleThree">
                                    <option value="" disabled selected hidden>请选择</option>
                                    <c:forEach items="${amPersonalInfoVoList}" var="amPersonalInfo">
                                        <option value="${amPersonalInfo.entity.personalId}" >${amPersonalInfo.entity.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 CNAS">
                        <div class="form-group">
                            <label>CNAS</label>
                            <label class="radio-inline radio-sex">
                                <input type="radio" class="sex sexman" value="1" name="cnasSeal" checked>是
                            </label>
                            <label class="radio-inline  radio-sex">
                                <input type="radio" class="sex sexwoman" value="0" name="cnasSeal">否
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer clearfix">
                <button class="btn btn-lang  btn-blue addMaterialEvidencerlSample" type="button" id="confirmButton">确认</button>
                <button name="downLoadIdentifyBook" id="downLoadIdentifyBook" class="btn btn-lang btn-blue-border downLoadIdentifyBook">
                    <i class="fa fa-download" aria-hidden="true">下载鉴定书</i>
                </button>
                <button type="button" class="btn btn-lang btn-blue-border" data-dismiss="modal">取消</button>
                <input type="hidden" id="caseIdReviewer" name="caseIdReviewer">
                <input type="hidden" id="personalIdModel" name="personalIdModel">
                <input type="hidden" id="newFileDirModel" name="newFileDirModel">
                <input type="hidden" id="originalFileDirModel" name="originalFileDirModel">
            </div>
        </div>
    </div>
</div>
<div class="row Modular ">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading yellow">
                <div>案件列表</div>
            </div>
            <div class="panel-body">
                <table class="table table-hover table-bordered bigTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>案件编号</th>
                        <th>案件名称</th>
                        <th>案件性质</th>
                        <th>案发时间</th>
                        <th>委托单位</th>
                        <th>委托人</th>
                        <th>委托时间</th>
                        <th>案件状态</th>
                        <th>受理时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="caseInfoListTbody">
                    <c:forEach items="${caseInfoList}" var="list" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${list.entity.caseNo}</td>
                            <td title="${list.entity.caseName}">
                                <c:if test="${fn:length(list.entity.caseName) gt 13}">
                                    <a href="<%=path%>/center/vewDetailsCase?caseId=${list.entity.caseId}&consignmentId=${list.consignmentId}"
                                       target="ifm"> ${fn:substring(list.entity.caseName,0,12)} ...</a>
                                </c:if>
                                <c:if test="${fn:length(list.entity.caseName) lt 14}">
                                    <a href="<%=path%>/center/vewDetailsCase?caseId=${list.entity.caseId}&consignmentId=${list.consignmentId}"
                                       target="ifm"> ${list.entity.caseName}</a>
                                </c:if>
                            </td>
                            <td>${list.casePropertyName}</td>
                            <td><fmt:formatDate value="${list.entity.caseDatetime}" pattern="yyyy-MM-dd"/></td>
                            <td>${list.delegateOrgName}</td>
                            <td>${list.delegator1Name}、${list.delegator2Name}</td>
                            <td><fmt:formatDate value="${list.delegateDatetime}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${list.status eq '02'}">在检验</c:when>
                                    <c:when test="${list.status eq '03'}">已完成</c:when>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate value="${list.acceptDatetime}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <input type="hidden" id="caseId" name="caseId" value="${list.entity.caseId}">
                                <input type="hidden" id="consignmentId" name="consignmentId" value="${list.consignmentId}">
                                <input type="hidden" id="originalFileDir" name="originalFileDir" value="${list.originalFileDir}">
                                <input type="hidden" id="newFileDir" name="newFileDir" value="${list.newFileDir}">
                                <input type="hidden" id="existFlag" name="existFlag" value="${list.existFlag}">
                                <c:if test="${'YES' eq isAppraisal}">
                                    <button name="viewIdentifyBook" class="btn down btn-blue-border">
                                        查看鉴定书
                                    </button>
                                </c:if>
                                <c:if test="${'NO' eq isAppraisal}">
                                    <input type="button" class="btn-icon btn-icon-red  btn-icon-dayin-red dybtn"
                                           id="${list.consignmentId}" value="案件打印"/>
                                </c:if>
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
<%@ include file="../linkJs.jsp" %>
<script>
    $(function () {
        'use strict';
        //判断当前账户是否是法医中心
        if ("${user.orgId}".indexOf("110230") != -1) {
            $(".CNAS").removeClass("hide");
        }else {
            $(".CNAS").addClass("hide");
        }

        $(".clientSelect").searchableSelect();

        $("#addInformant").on("click", function () {
            $("#page").val(1);
            $('#consignationForm').submit();
        });

        $("#downLoadIdentifyBook").on("click", function(){
            var filePath = "";
            var newFilePath = $("#newFileDirModel").val();
            if (newFilePath.length == 0) {
                filePath = $("#originalFileDirModel").val();
            }else {
                filePath = newFilePath;
            }
            location.href = "<%=path%>/download?fileUrl=" + encodeURI(encodeURI(filePath));
        })

        $("button[name='viewIdentifyBook']", "#caseInfoListTbody").on("click", function () {
            var newFileDir = $("input[name='newFileDir']", $(this).parent()).val();
            $("#newFileDirModel").val(newFileDir);
            var originalFileDir = $("input[name='originalFileDir']", $(this).parent()).val();
            $("#originalFileDirModel").val(originalFileDir);
            var existFlag = $("input[name='existFlag']", $(this).parent()).val();
            if (existFlag == "1") {
                $(".downLoadIdentifyBook").removeClass("hide");
            }else {
                $(".downLoadIdentifyBook").addClass("hide");
            }

            var personalId = $("#personalId").val();
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            $("#personalIdModel").val(personalId);
            $("#caseIdReviewer").val(caseId);
            selectExaminer(caseId);
            $("#selectReviewer").modal("show")
        });

        function selectExaminer(caseId) {
            $.ajax({
                url :"<%=path%>/center/selectExaminer?caseId=" + caseId,
                type:"get",
                contentType:  "application/json; charset=utf-8",
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#inspector1").find("option[value='"+data.examiner.inspector1+"']").prop("selected", true);
                        $("#inspector2").find("option[value='"+data.examiner.inspector2+"']").prop("selected", true);
                        $("#inspector3").find("option[value='"+data.examiner.inspector3+"']").prop("selected", true);
                        $("#titleOne").find("option[value='"+data.examiner.titleOne+"']").prop("selected", true);
                        $("#titleTwo").find("option[value='"+data.examiner.titleTwo+"']").prop("selected", true);
                        $("#titleThree").find("option[value='"+data.examiner.titleThree+"']").prop("selected", true);
                        $("input[name='cnasSeal'][value='"+data.examiner.cnasSeal+"']").attr("checked","checked");
                    }else {
                        $("#inspector1").val("");
                        $("#inspector2").val("");
                        $("#inspector3").val("");
                        $("#titleOne").val("");
                        $("#titleTwo").val("");
                        $("#titleThree").val("");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        }

        $("#confirmButton").on("click", function () {
            //法医师职称
            var titleOne = $("#titleOne option:selected").val()
            var titleTwo = $("#titleTwo option:selected").val()
            var titleThree = $("#titleThree option:selected").val()
            if(titleOne == null || titleOne == ''){
                alert("请选择检验人职称");
                return false
            }
            if(titleTwo == null || titleTwo == ''){
                alert("请选择检查人职称");
                return false
            }
            if(titleThree == null || titleThree == ''){
                alert("请选择授权签字人职称");
                return false
            }

            var firstReviewer = $("#inspector1 option:selected").val();
            var secondReviewer = $("#inspector2 option:selected").val();
            var thirdReviewer = $("#inspector3 option:selected").val();
            if(firstReviewer == null || firstReviewer == '' || firstReviewer == undefined){
                alert("请选择检验人职称");
                return false
            }
            if(secondReviewer == null || secondReviewer == '' || secondReviewer == undefined){
                alert("请选择检查人职称");
                return false
            }
            if(thirdReviewer == null || thirdReviewer == '' || thirdReviewer == undefined){
                alert("请选择授权签字人职称");
                return false
            }

            if (firstReviewer == secondReviewer || firstReviewer == thirdReviewer || secondReviewer == thirdReviewer) {
                alert("检验人重复,请重新选择")
                return false;
            }
            saveExaminer();
        });

        function verificationToken(personalId, caseId) {
            $.ajax({
                url: "<%=path%>/center/getTokenUrl",
                type:"post",
                data: JSON.stringify({"alimsId": personalId}),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                async:false,
                success:function(data){
                    var token = data.token;
                    var loginname = data.loginname;
                    var password = data.password;;
                    $("#token").val(token);
                    $("#loginname").val(loginname);
                    $("#password").val(password);
                    if (data.success) {
                        $.ajax({
                            url: "<%=path%>/center/generateIdentifyBook?caseId=" + caseId,
                            type: "get",
                            dataType: "json",
                            success: function (data) {
                                if (data.success) {
                                    uploadIdentifyBook(data.orgInfo,data.parentOrgInfo,data.caseInfo,data.consignmentInfo,data.byteFile,$("#token").val());
                                } else {
                                    alert("生成失败！" + data.message);
                                }
                            },
                            error: function (e) {
                                alert("生成失败！" + e);
                            }
                        });
                    }else {
                        alert("操作失败！")
                    }
                },
                error: function(e){
                    alert(e);
                }
            })
        }

        function uploadIdentifyBook(orgInfo, parentOrgInfo, caseInfo, consignmentInfo, byteFile, token) {
            $(".downLoadIdentifyBook").removeClass("hide");
            var firstReviewer = $("#inspector1 option:selected").val();
            var secondReviewer = $("#inspector2 option:selected").val();
            var thirdReviewer = $("#inspector3 option:selected").val();

            var paramsData = {
                "ersReportInfo": uploadInfo(orgInfo, parentOrgInfo, caseInfo, consignmentInfo),
                "fileBinary": byteFile,
                "token": token,
                "defalutSigner": firstReviewer + "," + secondReviewer + "," + thirdReviewer
            };

            $.ajax({
                url: "<%=path%>/center/callUploadInterface",
                type:"post",
                data: JSON.stringify(paramsData),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                async:false,
                success:function(data){
                    if (data.message == "exist") {
                        alert("此鉴定书已上传！")
                    }else {
                        if (data.success) {
                            //跳转到签发平台
                            jumpPage();
                        }else {
                            alert("操作失败！")
                        }
                    }
                },
                error: function(e){
                    alert(e);
                }
            })
        }

        function saveExaminer() {
            //法医师职称
            var titleOne = $("#titleOne option:selected").val()
            var titleTwo = $("#titleTwo option:selected").val()
            var titleThree = $("#titleThree option:selected").val()
            if(titleOne == null || titleOne == ''){
                alert("请选择检验人职称");
                return false
            }
            if(titleTwo == null || titleTwo == ''){
                alert("请选择检查人职称");
                return false
            }
            if(titleThree == null || titleThree == ''){
                alert("请选择授权签字人职称");
                return false
            }

            var firstReviewer = $("#inspector1 option:selected").val();
            var secondReviewer = $("#inspector2 option:selected").val();
            var thirdReviewer = $("#inspector3 option:selected").val();
            if(firstReviewer == null || firstReviewer == '' || firstReviewer == undefined){
                alert("请选择检验人职称");
                return false
            }
            if(secondReviewer == null || secondReviewer == '' || secondReviewer == undefined){
                alert("请选择检查人职称");
                return false
            }
            if(thirdReviewer == null || thirdReviewer == '' || thirdReviewer == undefined){
                alert("请选择授权签字人职称");
                return false
            }

            if (firstReviewer == secondReviewer || firstReviewer == thirdReviewer || secondReviewer == thirdReviewer) {
                alert("检验人重复,请重新选择")
                return false;
            }
            var examiner = {};
            examiner.inspector1 = firstReviewer;
            examiner.inspector2 = secondReviewer;
            examiner.inspector3 = thirdReviewer;
            examiner.caseId = $("#caseIdReviewer").val();
            examiner.titleOne = titleOne;
            examiner.titleTwo = titleTwo;
            examiner.titleThree = titleThree;
            examiner.cnasSeal = $("input[name='cnasSeal']:checked").val();

            $.ajax({
                url :"<%=path%>/center/saveExaminer",
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(examiner),
                dataType : "json",
                async:false,
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        var personalId = $("#personalIdModel").val();
                        var caseIdReviewer = $("#caseIdReviewer").val();
                        //判断当前账户是否是法医中心
                        if ("${user.orgId}".indexOf("110230") != -1) {
                            verificationToken(personalId,caseIdReviewer);
                        }else {
                            generateIdentifyBook(caseIdReviewer);
                        }
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        }

        function uploadInfo(orgInfo, parentOrgInfo, caseInfo, consignmentInfo) {
            var ersReportInfo = {};
            ersReportInfo.caseBaseId = caseInfo.caseId;
            ersReportInfo.consignationMajorSn = caseInfo.caseNo;
            ersReportInfo.caseName = caseInfo.caseName;
            ersReportInfo.superOrgId = parentOrgInfo.orgId;
            ersReportInfo.superOrgName = parentOrgInfo.orgName;
            ersReportInfo.childOrgId = orgInfo.orgId;
            ersReportInfo.childOrgName = orgInfo.orgName;
            ersReportInfo.isAppend = consignmentInfo.appendFlag;
            ersReportInfo.cnasSeal = $("input[name='cnasSeal']:checked").val();

            console.log(ersReportInfo)
            return ersReportInfo;
        }

        function jumpPage() {
            var jumpPageUrl = $("#jumpPageUrl").val();
            var tempwindow = window.open('_blank');
            tempwindow.location = jumpPageUrl + "?loginname=" + $("#loginname").val() + "&password=" + $("#password").val();
        }

        $("button[name='generateIdentifyBook']", "#caseInfoListTbody").on("click", function () {
            if (!confirm("是否生成鉴定书！")) {
                return false;
            }

            var caseId = $("input[name='caseId']", $(this).parent()).val();
            generateIdentifyBook(caseId);
        });

        function generateIdentifyBook(obj) {
            $.ajax({
                url: "<%=path%>/center/generateIdentifyBook?caseId=" + obj,
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $(".downLoadIdentifyBook").removeClass("hide");
                        console.log(data.success)
                        downloadFile(data.filePath)
                    } else {
                        alert("生成失败！" + data.message);
                    }
                },
                error: function (e) {
                    alert("生成失败！");
                }
            });
        }

        function downloadFile(filePath) {
            location.href = "<%=path%>/download?fileUrl=" + encodeURI(encodeURI(filePath));
        }

        $("button[name='printFilesPhotos']", "#caseInfoListTbody").on("click", function () {
            var consignmentId = $("#consignmentId").val();

            $.ajax({
                url: "<%=path%>/dowmFileController/printFilesPhotos",
                data: {"consignmentId": consignmentId},
                type: "post",
                dataType: "json",
                success: function (data) {

                    if (data) {

                    } else if (!data) {
                        alert("打印资质照片失败！");
                    }

                }
//            error:function(e) {
//                debugger;
//                alert("下载失败！");
//            }
            });


        })


        kkpager.generPageHtml({
            pno: ${pageInfo.page},
            //总页码
            total: ${pageInfo.pageCount},
            //总数据条数
            totalRecords: ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer: '<%=path%>/center/appraisalManage',
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

        $(".dybtn").on("click", function () {
            var e = $(this).attr("id");
            location.href = "<%=path%>/center/caseFilePrint?consignmentId=" + e;
        })
    })
</script>
</body>

</html>