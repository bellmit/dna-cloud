<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
            "yyyy-MM-dd");
    java.util.Date currentTime = new java.util.Date();
    String time = simpleDateFormat.format(currentTime).toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en" id="scrollData">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局法医鉴定案件受理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        body {
            overflow-x: hidden;
            position: relative;
        }

        .searchBox {
            width: 300px;
            margin-bottom: 0px;
            margin-right: 5px !important;
            margin-top: 2px;
        }

        .panel-heading > div:nth-child(1) + button {
            margin-top: -4px;
        }

        .searchBox + button {
            margin-top: -4px;
        }

        .searchBox input {
            font-weight: 400;
            background: #f4f4f4;
            border: 1px solid #f4f4f4;
            border-radius: 0px;
        }

        #materialEvidencerTbody > tr > td {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .bs-callout {
            padding: 20px;
            margin: 20px 0;
            border: 1px solid #eee;
            border-left-width: 5px;
            border-radius: 3px;
            background: #fff;
            padding-top: 10px;
        }

        .bs-callout-danger {
            border-left-color: #FF5A56;
        }

        .bs-callout-danger h4 {
            margin-top: 0px;
            font-size: 14px;
            color: #FF5A56;
            font-weight: 600;
        }

        .bs-callout p:last-child {
            margin-bottom: 0;
        }

        #autograph .modal-body p {
            text-align: center;
            margin-top: 30px;
            color: #9c9c9c;
            font-weight: 600;
        }

        #autograph .modal-body p code {
            background: transparent;
            color: #FF5A56;
        }

        #autograph .modal-body p i {
            color: #FF5A56;
            margin-right: 10px;
        }

        #signature {
            background-color: #f5f5f5;
            height: 320px;
            width: 793px;
            border: 2px dashed #eeeeee;
            position: relative;
        }

        .bg {
            width: 100%;
            height: 100%;
            position: absolute;
            background: transparent;
            color: #eaeaea;
            line-height: 320px;
            text-align: center;
            font-weight: 600;
            font-size: 50px;;
            pointer-events: none;

        }

        .bigBox .modal-content .modal-body {
            padding: 0px 42px;
            padding-top: 20px;

        }

        #autograph .modal-dialog {
            width: 873px;
            top: 5%;
        }

        #addAuthenticatorBig {
            text-align: center;
        }

        #addAuthenticatorBig > div {
            width: 480px;
            margin: 0 auto;
            margin-top: 20px;
        }

        #addAuthenticatorBig .modal-content {
            position: static;
            float: left;
        }

        #addAuthenticatorBig .form-group {
            margin-bottom: 5px;
        }

        #addAuthenticatorBig .modal-content {
            width: 480px;
            top: 20px;
            text-align: left;
        }

        #addAuthenticatorBig .sampleBox {
            width: 720px;
            top: -519px;
            display: none;
        }

        #addAuthenticatorBig .modal-content .modal-header {
            text-align: center;
        }

        #addAuthenticatorBig .personBox .modal-header h4 {
            font-size: 17px;
            color: #007ef9;
            font-weight: 600;
        }

        #addAuthenticatorBig .sampleBox .modal-header h4 {
            font-size: 17px;
            color: #ffa400;
            font-weight: 600;
        }

        #addAuthenticatorBig .personBox .modal-body {
            padding: 13px 50px;
            padding-bottom: 0px;
            max-height: 492px;
            overflow-y: auto;
        }

        #addAuthenticatorBig .sampleBox .modal-body {
            padding: 13px 50px;
        }

        #addAuthenticatorBig .personBox .modal-body .photoFile, #addAuthenticatorBig .modal-body .samplePhoto {
            display: none;
        }

        #addAuthenticatorBig .modal-content .modal-body .form-group:nth-child(1) img {
            height: 55px;
            background: #fff;
            cursor: pointer;
        }

        #addAuthenticatorBig .modal-content .modal-body .form-group:nth-child(1) img + button {
            margin: 0 20px;
        }

        #addAuthenticatorBig .modal-content .modal-footer {
            text-align: center;
            border: none;
            padding-top: 0px;
        }

        #addAuthenticatorBig .modal-content .modal-footer div {
            margin-bottom: 10px;
        }

        #addAuthenticatorBig .modal-content .samplePhotobox .col-md-2,
        #materialEvidencerlSampleBox .modal-content .samplePhotobox .col-md-2 {
            height: 85px;
            position: relative;
            padding: 0 7px;
        }

        #addAuthenticatorBig .modal-content .samplePhotobox .col-md-2 img,
        #materialEvidencerlSampleBox .modal-content .samplePhotobox .col-md-2 img {
            width: 100%;
            height: 100% !important;
            border: 2px solid #f3f3f3;
            padding: 5px;
        }

        #addAuthenticatorBig .modal-content .samplePhotobox .col-md-2 .fa-times-circle,
        #materialEvidencerlSampleBox .modal-content .samplePhotobox .col-md-2 .fa-times-circle {
            color: #f84c3d;
            position: absolute;
            right: 2px;
            top: -6px;
            font-size: 17px;
            cursor: pointer;
        }

        #addAuthenticatorBig .modal-content .samplePhotobox .col-md-2 .addsamplePhoto,
        #materialEvidencerlSampleBox .modal-content .samplePhotobox .col-md-2 .addsamplePhoto {
            width: 100%;
            height: 100%;
            background: #fff;
            color: #f3f3f3;
            border: 2px solid #f3f3f3;
            font-size: 35px;
        }

        .bv-form .has-error .idBox .help-block,
        .idBox small {
            position: absolute;
            top: -29px;
            left: 70px;
            color: #a94442;
        }

        td .select {
            width: 90px;
            float: left;
        }

        td .select + .select {
            margin-left: 5px;
        }

        #materialEvidencerTbody tr td:nth-child(2) {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .showPhoto {
            width: 27px;
            height: 27px;
            background: url("<%=path%>/img/icon1.png") no-repeat;
            background-position: -170px -278px;
            cursor: pointer;
        }

        #showPhotoBox > div {
            text-align: center;
            top: 10%;
        }

        #showPhotoBox {
            background: rgba(0, 0, 0, .5);
        }

        #showPhotoBox img {
            width: 500px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            padding: 4px;
            padding-left: 5px;
            position: relative;
        }

        .jiajiImg {
            position: absolute;
            right: 56px;
            top: 72px;
            z-index: 0;
            opacity: 0
        }

        tr td .select select {
            padding: 6px 4px;
        }

        tr td .select:after {
            right: 10px;
        }

        tr td:nth-child(7) .select:nth-child(1) {
            width: 62px;
            float: left;
        }

        tr td:nth-child(7) .select:nth-child(2) {
            width: 80px;
            float: left;
        }

        tr td:nth-child(7) .select + .select {
            margin-left: 2px;
        }

        tr td:nth-child(9) .select,
        tr td:nth-child(10) .select,
        tr td:nth-child(11) .select {
            width: 52px;
        }

        #materialEvidencerTbody .btn-icon + .btn-icon {
            margin-left: 2px;
        }

        #idCard + small {
            position: absolute;
            top: -29px;
            left: 70px;
            color: #a94442;
        }

        #addAuthenticatorBig .modal-content .modal-body .form-group:nth-child(1) span {
            color: #a94442;
            font-size: 11px;
        }

        .shadow {
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            z-index: 1050;
            /* display: none; */
            overflow: hidden;
            -webkit-overflow-scrolling: touch;
            outline: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, .6);
            z-index: 11111;
        }

        .shadow i {
            font-size: 80px;
            color: #fff;
            position: absolute;
            top: 30%;
            left: 50%;
            margin-left: -40px;
            transform: rotate(323deg);
            animation: myfirst 3s infinite;
        }

        .shadow span {
            font-size: 25px;
            color: #fff;
            position: absolute;
            top: 30%;
            /*left: 50%;*/
            /*margin-left: -40px;*/
            margin-top: 100px;
            width: 100%;
            text-align: center;
        }

        .clientSelect {
            position: absolute;
        }

        .jSignature {
            display: none;
        }

        /*案件进度*/
        .caseSchedule {
            z-index: 1;
            height: 85px;
            margin-top: 68px;
            left: 0px;
            margin-left: 0px;
            transition: all 1s;
            cursor: pointer;
            position: fixed;
        }

        .caseScheduleHidden {
            position: fixed;
            rigt: 0px;
            margin-left: -30px;
            margin-top: 68px;
        }

        .caseSchedule > div:last-child {
            background: #ff5a56;
            color: #fff;
            font-weight: 600;
            padding: 0 5px;
            writing-mode: vertical-lr;
            letter-spacing: 3px;
            border-radius: 5px 0 0 5px;
            height: 85px;
            position: fixed;
            width: 30px;
            text-align: center;
            right: 0;
            top: 68px;
        }

        .caseSchedule > div:nth-child(1) {
            height: 85px;
            background: #fff;
            border-radius: 0 5px 5px 0;
            position: fixed;
            top: 68px;
            right: 30px;
        }

        .caseSchedule ul {
            height: 85px;
            width: 690px;
            margin: 0 auto;
            border-top: 1px dashed #296fff;
            border-bottom: 1px dashed #296fff;
            border-left: 1px dashed #296fff;
        }

        .caseSchedule ul li {
            float: left;
            /*padding: 0 56px;*/
            margin-top: 50px;
            display: inline-block;
            color: #666;
            position: relative;
            width: 170px;
            text-align: center;
            font: normal normal normal 14px/1 FontAwesome;
        }

        .caseSchedule ul li + li::before {
            content: "";
            width: 100%;
            height: 1px;
            border-top: 1px dashed #999;
            position: absolute;
            left: -50%;
            top: -25px;
        }

        .caseSchedule ul li::after {
            content: "...";
            width: 27px;
            height: 27px;
            line-height: 19px;
            text-align: center;
            position: absolute;
            color: #fff;
            border-radius: 50%;
            background: #e4e4e4;
            font-weight: 600;
            letter-spacing: 2px;
            top: -39px;
            left: 50%;
            z-index: 1;
            margin-left: -14px;
        }

        .caseSchedule ul li.carryOut {
            color: #0c81f5;
            font-weight: 600;
        }

        .caseSchedule ul li.carryOut::after {
            content: "\f00c";
            background: #0c81f5;
            letter-spacing: 0px;
            line-height: 27px;
            font-size: 17px;
        }

        .caseSchedule ul li.getOn {
            color: #50c987;
            font-weight: 600;
        }

        .caseSchedule ul li.getOn::after {
            content: "\f00c";
            background: #50c987;
            letter-spacing: 0px;
            line-height: 27px;
            font-size: 17px;
        }

        .caseSchedule ul li p {
            font-size: 10px;
            color: #666;
            font-weight: 400;
            margin-top: 5px;
            text-align: center;
        }

        /*.caseSchedule ul li + li {*/
        /*padding-right: 112px;*/
        /*}*/
        .default-none{
            display: none;
        }


        .font-weight{
            font-size:16px;
            font-weight:bold;
            color: #333;
        }
        .font-yellow{
            font-size:16px;
            font-weight:bold;
            color: #FFB72C;
        }
        .little-tab{
            text-align: center;
        }
        .little-tab div{
            width:94px;
            height:34px;
            line-height:34px;
            border-radius: 4px;
            font-size:14px;
            color: #0C81F5;
            background: #e4f1fe;
            display: inline-block;
        }
        .btn-checkbox > li:hover{
            background: #f3f3f3;
            color: #333;
        }
        .btn-checkbox > li.active:hover{
            background: #007ef9;
            color: #fff;
        }
        .expedited{
            background: url("../img/expedited.png") no-repeat;
            width: 170px;
            height: 170px;
            position: absolute;
            z-index: 9;
            right: 120px;
            top: 40px;
            background-size: 100%;
            display: none;
        }
    </style>
</head>
<form id="saveform" enctype="multipart/form-data" method="post" class="form-inline">
    <div class="row Modular part" style="margin: 10px 15px;">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <input type="hidden" id="caseUrgentFlag" value="${limsCaseInfo.caseUrgentFlag}">
                    <input type="hidden" id="identifyRequirement" value="${limsConsignmentInfo.identifyRequirement}">
                    <div>案件信息</div>
                </div>
                <div class="panel-body">
                    <div class="expedited">

                    </div>
                    <form class="form-inline" id="caseSortform">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>案件编号</label>
                                    <input type="text" class="form-control" name="caseNo" value="${limsCaseInfo.caseNo}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>案件名称</label>
                                    <input type="text" name="caseName" value="${limsCaseInfo.caseName}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>案件性质</label>
                                    <input type="text" name="caseProperty" value="${limsCaseInfo.caseProperty}" class="form-control"
                                           data-toggle="modal" data-target="#caseNature" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>现勘编号</label>
                                    <input type="text" name="caseXkNo" value="${limsCaseInfo.caseXkNo}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>委托编号</label>
                                    <input type="text" name="sampleNo" value="${limsConsignmentInfo.consignmentNo}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>案发地点</label>
                                    <input type="text" name="acceptName" value="${limsCaseInfo.caseLocation}" class="form-control" placeholder="请输入受理人员" readonly>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>案发时间</label>
                                    <input type="text" name="orgName" value="<fmt:formatDate value='${limsCaseInfo.caseDatetime}' pattern='yyyy-MM-dd'/>" class="form-control" placeholder="请输入送检单位" readonly>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>简要案情</label>
                                    <input type="text" name="sampleName" value="${limsCaseInfo.caseBrief}" class="form-control" placeholder="请输入样本名称" style="width: 90%;" readonly>
                                </div>
                            </div>

                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular part">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>委托信息</div>
                </div>
                <div class="panel-body">
                    <div class="row inputBox">
                        <div class="col-md-2">
                            <div class="form-group">
                                <img src="../img/unit.png" alt="" width="28px">
                                <label class="unit font-weight">委托单位 :</label>
                                <span class="font-yellow">${limsConsignmentInfo.delegateOrgName}</span>
                            </div>
                        </div>

                    </div>
                    <div class="row inputBox">
                        <div class="col-md-12" style="margin-top: 60px;">
                            <div class="col-md-6">
                                <div class="col-md-12 personCard">
                                    <img src="<%=path%>/img/touxiang1.png" alt="">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>姓名</label>
                                                <input type="text" class="form-control" value="${limsConsignmentInfo.delegator1Name}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>职务</label>
                                                <input type="text" class="form-control" value="${limsConsignmentInfo.delegator1Position}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>证件</label>
                                                <input name="delegator1PaperworkType" id="delegator1PaperworkType"
                                                       type="text" class="form-control" name="clientIdType"
                                                       value="${limsConsignmentInfo.delegator1PaperworkType}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>证件号</label>
                                                <input name="delegator1PaperworkNo" id="delegator1PaperworkNo"
                                                       type="text" class="form-control" name="clientId"
                                                       value="${limsConsignmentInfo.delegator1PaperworkNo}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>电话</label>
                                                <input name="delegator1PhoneNumber" id="delegator1PhoneNumber"
                                                       type="text" class="form-control" name="clientPhone"
                                                       value="${limsConsignmentInfo.delegator1PhoneNumber}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row little-tab">
                                        <div>委托人01</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="col-md-12 personCard">
                                    <img src="<%=path%>/img/touxiang1.png" alt="">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>姓名</label>
                                                <input type="text" class="form-control" value="${limsConsignmentInfo.delegator2Name}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>职务</label>
                                                <input type="text" class="form-control" value="${limsConsignmentInfo.delegator2Position}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>证件</label>
                                                <input name="delegator2PaperworkType" id="delegator2PaperworkType"
                                                       type="text" class="form-control" name="clientIdType" value="${limsConsignmentInfo.delegator2PaperworkType}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>证件号</label>
                                                <input name="delegator2PaperworkNo" id="delegator2PaperworkNo"
                                                       type="text" class="form-control" name="clientId"
                                                       value="${limsConsignmentInfo.delegator2PaperworkNo}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>电话</label>
                                                <input name="delegator2PhoneNumber" id="delegator2PhoneNumber"
                                                       type="text" class="form-control" name="clientPhone"
                                                       value="${limsConsignmentInfo.delegator2PhoneNumber}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row little-tab">
                                        <div>委托人02</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular part">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>鉴定要求</div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <ul class="btn-checkbox clearfix jianding">
                                    <li class="pull-left " value="${limsConsignmentInfo.identifyRequirement}">同一鉴定</li>
                                    <li class="pull-left" value="${limsConsignmentInfo.identifyRequirement}">亲缘鉴定</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular part">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>被鉴定人信息</div>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable table-striped">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>人员类型</th>
                            <th>人员名称</th>
                            <th>性别</th>
                            <th>年龄</th>
                            <th>身份证号</th>
                        </tr>
                        </thead>
                        <tbody id="authenticatorTbody">
                        <c:forEach items="${limsPersonInfoList}" var="personInfo" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${personInfo.personTypeName}</td>
                            <td>${personInfo.personName}</td>
                            <td>${personInfo.personGenderName}</td>
                            <td>${personInfo.perosnAge}</td>
                            <td>${personInfo.personIdCard}</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular part">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>人员样本信息</div>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable table-striped">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>样本编号</th>
                            <th>样本名称</th>
                            <th>样本描述</th>
                            <th>包装方法</th>
                            <th>提取时间</th>
                            <th>提取方法</th>
                            <th>送检目的</th>
                            <th>关联人员</th>
                        </tr>
                        </thead>
                        <tbody id="personSampleTbody">
                        <c:forEach items="${limsSampleInfoDnaRyList}" var="rySample" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${rySample.sampleNo}</td>
                            <td>${rySample.sampleName}</td>
                            <td>${rySample.sampleDesc}</td>
                            <td>${rySample.samplePackingName}</td>
                            <td><fmt:formatDate value='${rySample.extractDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${rySample.extractMethodName}</td>
                            <td>${rySample.samplePurpose}</td>
                            <td>${rySample.personName}</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular part">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>物证检材信息</div>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable" style="table-layout: fixed;">
                        <thead>
                        <tr>
                            <th rowspan="1" style="width: 50px;">序号</th>
                            <th rowspan="2" style="width: 120px;">物证编号</th>
                            <th rowspan="2" style="width: 80px;">检材编号</th>
                            <th rowspan="2" style="width: 100px;">检材名称</th>
                            <th rowspan="2" style="width: 80px;">检材类型</th>
                            <th rowspan="2" style="width: 100px;">检材描述</th>
                            <th rowspan="2" style="width: 80px;">包装方法</th>
                            <th rowspan="2" style="width: 90px;">提取时间</th>
                            <th rowspan="2" style="width: 162px;">提取方法</th>
                            <th rowspan="2" style="width: 90px;">送检目的</th>
                        </tr>
                        </thead>
                        <tbody id="materialEvidencerTbody">
                        <c:forEach items="${limsSampleInfoDnaWzList}" var="wzSample" varStatus="s">
                            <tr>
                                <td>${s.index+1}</td>
                                <td title="${wzSample.evidenceNo}">${wzSample.evidenceNo}</td>
                                <td>${wzSample.sampleNo}</td>
                                <td>${wzSample.sampleName}</td>
                                <td>${wzSample.sampleTypeName}</td>
                                <td>${wzSample.sampleDesc}</td>
                                <td>${wzSample.samplePackingName}</td>
                                <td><fmt:formatDate value='${wzSample.extractDatetime}' pattern='yyyy-MM-dd'/></td>
                                <td>${wzSample.extractMethodName}</td>
                                <td>${wzSample.samplePurpose}</td>
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="height-60"></div>
<div class="row footer">
    Copyright© 2019 北京博安智联科技有限公司       
</div>
<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/entrustCurrency.js"></script>
<%--<script src="<%=path%>/lib/jSignature/jSignature.min.js"></script>--%>

<script>
    if($("#caseUrgentFlag").val() == '1'){
        $(".expedited").show()
    }
    var identifyRequirementVal = $("#identifyRequirement").val()
    if(identifyRequirementVal == '01'){
        $(".jianding li:first").addClass("active")
    }else if(identifyRequirementVal == '02'){
        $(".jianding li:last").addClass("active")
    }else if(identifyRequirementVal == '01,02'){
        $(".jianding li").addClass("active")
    }
    $(".caseSchedule").click(function () {
        if ($(this).hasClass("caseScheduleHidden")) {
            $(".caseSchedule").removeClass("caseScheduleHidden")
            $(".default-none").show("slow")
        } else {
            $(".default-none").hide("slow")
            $(this).addClass("caseScheduleHidden")
        }
    })

    function exec() {
        var printServerUrl = "http://" + $("#ipAddress").val() + ":927/HWPenSign?Open=1";
//        var printServerUrl = "http://192.168.8.142:927/HWPenSign?Open=1";
        console.log(printServerUrl);
        $.ajax({
            url: printServerUrl,
            timeout: 60000,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                debugger;
                console.log("获取客户端返回值" + data);
                $("#signPices").attr("src", "data:image/png;base64," + data.status.Pictrue);
            },
            error: function (e) {

                console.log("客户端未返回值" + e);
            }
        });

    }

    $(function () {

        $("#takePerson").val($("#delegator1Id").children("option:selected").html() + "," + $("#delegator2Id").children("option:selected").html())

        var identifyRequirement = '${consignatioInfo.identifyRequirement}';

        $.each(identifyRequirement.split(","), function (i, item) {
            $(".btn-checkbox").find("li[value='" + item + "']").addClass("active")
        })

        if ('${consignatioInfo.status}' != "01") {
            // $(".btn-box").addClass("hidden")
            $(".btn-box").find("button").html("更新")
        }


        if ($("#caseUrgentFlag").val() == 1) {
            $("input[name='caseUrgentFlag']").attr('checked', 'true')
            $(".jiajiImg").animate({
                opacity: '1',
                height: '150px',
                width: '150px'
            });
        }
        //加急特效
        $("#caseUrgentFlag").change(function () {
            if ($(this).is(":checked")) {
                $(".jiajiImg").animate({
                    opacity: '1',
                    height: '150px',
                    width: '150px'
                });
            } else {
                $(".jiajiImg").animate({
                    opacity: '0',
                    height: '175px',
                    width: '175px'
                });
            }
        })

        function scroll(form) {
            $(form).find("small[data-bv-result='INVALID']").each(function (index, ele) {
                if (index == 0) {
                    $("body,html").animate({
                        scrollTop: $(this).offset().top - 100
                    })
                    $(this).prev().focus()
                }
            })
        }

        retained();
        //回显留存
        function retained() {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active")
            } else {
                $(this).addClass("active")
                if ($(this).parents("tr").find(".isRefuse").hasClass("active")) {
                    $(this).parents("tr").find(".isRefuse").removeClass("active")
                    $(this).parents("tr").find(".isRefuse").siblings('input[name="isRefuse"]').val("")
                    $(this).parents("tr").find(".isRefuse").siblings('input[name="isRefuseCode"]').val("02")
                }
            }
            var retainArr = []
            $(".isRetain").each(function () {
                if ($(this).hasClass("active")) {
                    retainArr.push($(this).parents("tr").find("input[name='sampleName']").val())
                }
            })
            if (retainArr.length > 0 && retainArr.length !== $("#materialEvidencerTbody").children().length) {
                $(".zhanshi").find("span").eq(0).html("除(" + retainArr.join('、') + ")").next().html("均已取走")
            } else if (retainArr.length == $("#materialEvidencerTbody").children().length) {
                $(".zhanshi").find("span").eq(0).html("").next().html("均已留存")
            } else {
                $(".zhanshi").find("span").eq(0).html("").next().html("均已取走")
            }

        }

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

        //保存弹窗选择下拉
        $(".savechange").click(function () {
            $(".selectbox").eq(0).removeClass("hidden")
        })
        //保存弹窗选择下拉点击
        $(".selectbox").on('click', 'li', function () {
            $('.identificationCenter').html($(this).html())
            $(this).parents(".selectbox").addClass("hidden")
        })
        //现勘修改保存
        $(".modifySiteSurve").click(function () {
            $(".starBox").eq(1).find("span").html($("#siteSurveyBox").find("input").val())
            $('#siteSurveyBox').modal('hide')
        })
        // 现勘改关闭清空
        $('#siteSurveyBox').on('hidden.bs.modal', function (e) {
            $("#siteSurveyBox").find("input").val("")
        })

        $(document).on("scroll", function(){
            if ($(document).scrollTop() >= 112) {
                $(".fixedBox").removeClass("hidden")
                $(".caseSchedule ").css({
                    "position":"fixed",
                    "margin-top":"68px"
                })
                var currentScroll = $(this).scrollTop()
                var currentSection
                $(".Modular").each(function () {
                    var divPosition = $(this).offset().top - 62;
                    if (currentScroll > divPosition) {
                        currentSection = $(this).find(".panel-heading").children().html()
                    }
                })
                $(".fixedBox").find("li").each(function () {
                    if ($(this).html() == currentSection) {
                        $(this).addClass("now").siblings().removeClass("now")
                    }
                })
            } else {
                $(".fixedBox").addClass("hidden")
            }
        });

        // 顶部导航点击定位
        $(".fixedBox").find("li").click(function () {
            var that = $(this)
            $(".Modular").each(function () {
                if (that.html() == $(this).find(".panel-heading").children().html()) {
                    $("html,body").animate({
                        scrollTop: $(this).offset().top - 60
                    }, 500);
                }
            })
            that.addClass("liActive").siblings().removeClass("liActive")
        })
        //被鉴定人添加样本
        $("#addAuthenticatorBig").find("input[name='moveCheckbox']").change(function () {
            if ($(this).is(':checked')) {
                $("#addAuthenticatorBig").children().width(1200)
                $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                    "height": $("#addAuthenticatorBig").find(".modal-content").eq(0).height() + 2,
                }).fadeIn(800);
            } else {
                $("#addAuthenticatorBig").children().width(480)
                $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                    "display": "none",
                });
            }
        })


        //被鉴定人更多信息
        $("#addAuthenticatorBig").find(".moveBtn").click(function () {
            if ($(this).html() == "添加更多") {
                $("#addAuthenticatorBig").find(".moveInput").removeClass("hidden")
                $(this).html("清除关闭")
            } else {
                $("#addAuthenticatorBig").find(".moveInput").addClass("hidden").find("input").val('')
                $(this).html("添加更多")
            }
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                "height": $("#addAuthenticatorBig").find(".modal-content").eq(0).height() + 2,
            });
        })
        // 被鉴定人身份证切换
        $("#addAuthenticatorBig").find("input[name='noIdCheck']").change(function () {
            if ($("#addAuthenticatorBig").find(".has-error").length > 0) {
                var form = $("#addAuthenticatorBig").find("form")
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
            }
            if ($(this).is(":checked")) {
                $("#addAuthenticatorBig").find("input[name='idCard']").val("").prop("disabled", true)
                $("#addAuthenticatorBig").find("input[name='noIdCard']").val("").removeClass("hidden")
            } else {
                $("#addAuthenticatorBig").find("input[name='idCard']").val("").prop("disabled", false)
                $("#addAuthenticatorBig").find("input[name='noIdCard']").val("").addClass("hidden")
            }
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                "height": $("#addAuthenticatorBig").find(".modal-content").eq(0).height() + 2,
            });
        })
        //身份证验证
        function isCardNo(card) {
            var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            return pattern.test(card);
        }

        // 被鉴定人年龄计算
        $("#addAuthenticatorBig").find("input[name='idCard']").blur(function () {
            if ($.trim($(this).val()).length > 0 && isCardNo($.trim($(this).val()))) {
                $(this).siblings("small").addClass("hidden")
                $("#verification").addClass("btn-blue").removeClass("btn-gray")
                if ($(this).val().length == 18) {
                    if (Number($(this).val().substring(16, 17)) % 2 == 0) {
                        $(".personBox").find("input[name='sex'][value='02']").prop("checked", true)
                    } else {
                        $(".personBox").find("input[name='sex'][value='01']").prop("checked", true)
                    }
                    var year = Number($(this).val().substring(6, 10))
                    var myDate = new Date();
                    var Nowyear = Number(myDate.getFullYear())
                    $("#addAuthenticatorBig").find("input[name='year']").val(Nowyear - year).change()
                }
                else if ($(this).val().length == 15) {
                    if (Number($(this).val().substring(14, 15)) % 2 == 0) {
                        $(".personBox").find("input[name='sex'][value='02']").prop("checked", true)
                    } else {
                        $(".personBox").find("input[name='sex'][value='01']").prop("checked", true)
                    }
                    var year = Number("19" + $(this).val().substring(6, 8))
                    var myDate = new Date();
                    var Nowyear = Number(myDate.getFullYear())
                    $("#addAuthenticatorBig").find("input[name='year']").val(Nowyear - year).change()
                }
            } else {
                $("#verification").addClass("btn-gray").removeClass("btn-blue")
                $(this).siblings("small").removeClass("hidden")
            }
        })
        // 被鉴定人亲缘切换
        $("#addAuthenticatorBig").find("select[name='personType']").change(function () {
            if ($(this).find("option:selected").text().indexOf("亲属") != -1) {
                var headerHtml = $(this).find("option:selected").text().substring(0, $(this).find("option:selected").text().indexOf("亲属"))
                $("#addAuthenticatorBig").find("select[name='kinship']").parents(".form-group").removeClass("hidden").find("label").html("与" + headerHtml + "亲缘关系")
            } else {
                $("#addAuthenticatorBig").find("select[name='kinship']").val("").parents(".form-group").addClass("hidden")
            }
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                "height": $("#addAuthenticatorBig").find(".modal-content").eq(0).height() + 2,
            });
        })
        //被鉴定人照片添加
        $(".addphoto").click(function () {
            $(this).siblings("input[type='file']").val("").click()
        })
        $(".photoFile").change(function (e) {
            var file = e.target.files[0];
            if (file.size / 1024 > 500) {
                $(this).siblings("span").removeClass("hidden")
                $(this).val("")
            } else {
                $(this).siblings("span").addClass("hidden")
                var reader = new FileReader();
                console.log(file.size / 1024)
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    var dataURL = reader.result;
                    $("#personBase").val(dataURL)
                };
                $(this).siblings("button").html("修改")
                $(this).siblings("img").attr("src", URL.createObjectURL($(this)[0].files[0])).attr("alt", e.currentTarget.files[0].name);
            }

        })
        //被鉴定人样本照片添加
        $("#addAuthenticatorBig").find(".addsamplePhoto").click(function () {
            $(this).siblings("input[type='file']").val("").click()
        })
        $("#addAuthenticatorBig").find(".samplePhoto").change(function (e) {
            var file = e.target.files[0];
            if (file.size / 1024 > 500) {
                $(this).parents(".samplePhotobox").siblings("span").removeClass("hidden")
                $(this).val("")
            } else {
                $(this).parents(".samplePhotobox").siblings("span").addClass("hidden")
                var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    var dataURL = reader.result;
                    $("#sampleBase").val(dataURL)
                };
                $.each($(this)[0].files, function (i, item) {
                    var newPhoto = '<div class="col-md-2"><img src="' + URL.createObjectURL(item) + '" alt="' + e.currentTarget.files[0].name + '"><i class="fa fa-times-circle removePhoto" aria-hidden="true"></i></div>'
                    $("#addAuthenticatorBig").find(".addsamplePhoto").parent().before(newPhoto)
                })
            }
        })
        $("#addAuthenticatorBig").find(".samplePhotobox").on("click", ".removePhoto", function () {
            var that = $(this)
            $.each($("#addAuthenticatorBig").find(".samplePhoto")[0].files, function (i, item) {
                if (that.siblings('img').attr("src") == URL.createObjectURL(item)) {
                    delete $("#addAuthenticatorBig").find(".samplePhoto")[0].files.i
                }
            })
            $("#sampleBase").val("")
            $(this).parents(".col-md-2").eq(0).remove()
        })
        //检材照片添加
        $("#materialEvidencerlSampleBox").find(".addsamplePhoto").click(function () {
            $(this).siblings("input[type='file']").val("").click()
        })
        $("#materialEvidencerlSampleBox").find(".samplePhoto").change(function (e) {
            var file = e.target.files[0];
            if (file.size / 1024 > 500) {
                $(this).parents(".samplePhotobox").siblings("span").removeClass("hidden")
                $(this).val("")
            } else {
                $(this).parents(".samplePhotobox").siblings("span").addClass("hidden")
                var reader = new FileReader();
                reader.readAsDataURL(file); // 读出 base64
                reader.onloadend = function () {
                    var dataURL = reader.result;
                    $("#samplePicture").val(dataURL)
                };
                $.each($(this)[0].files, function (i, item) {
                    var newPhoto = '<div class="col-md-2"><img src="' + URL.createObjectURL(item) + '" alt="' + e.currentTarget.files[0].name + '"><i class="fa fa-times-circle removePhoto" aria-hidden="true"></i></div>'
                    $("#materialEvidencerlSampleBox").find(".addsamplePhoto").parent().before(newPhoto)
                })
            }
        })
        $("#materialEvidencerlSampleBox").find(".samplePhotobox").on("click", ".removePhoto", function () {
            var that = $(this)
            $.each($("#materialEvidencerlSampleBox").find(".samplePhoto")[0].files, function (i, item) {
                if (that.siblings('img').attr("src") == URL.createObjectURL(item)) {
                    delete  $("#materialEvidencerlSampleBox").find(".samplePhoto")[0].files.i
                }
            })
            $("#samplePicture").val("")
            $(this).parents(".col-md-2").eq(0).remove()
        })


        //样本名称关联
        $("#addAuthenticatorBig").find(".sampleBox").find("#sampleType").change(function () {
            if ($("#addAuthenticatorBig").find('select[name="relationPersonName"]').parents(".col-md-6").hasClass("hidden")) {
                var values = $("#addAuthenticatorBig").find("#personName").val() + $(this).children(":selected").html()
                $("#addAuthenticatorBig").find(".sampleBox").find("input[name='sampleName']").val(values)
                $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val(values)
                if ($("#personType").val()) {
                    $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val($("#personType").children(":selected").html() + values)
                }
            } else {
                var values = $("#addAuthenticatorBig").find('select[name="relationPersonName"]').find("option:selected").text() + $(this).children(":selected").html()
                $("#addAuthenticatorBig").find(".sampleBox").find("input[name='sampleName']").val(values)
                $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val($("#addAuthenticatorBig").find('select[name="relationPersonName"]').val() + values)
            }

        })
        $("#addAuthenticatorBig").find("#personType").change(function () {
            var values = $(this).children(":selected").html() + $("#personName").val()
            if ($("#sampleType").val()) {
                $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val(values + $("#sampleType").children(":selected").html())
            }
        })
        $("#addAuthenticatorBig").find("#personName").change(function () {
            var values = $(this).val()
            if ($("#personType").children(":selected").html() !== "请选择人员类型") {
                values = $("#personType").children(":selected").html() + values
            }
            if ($("#sampleType").children(":selected").html() !== "请选择样本类型") {
                values = values + $("#sampleType").children(":selected").html()
                $("#addAuthenticatorBig").find(".sampleBox").find("input[name='sampleName']").val($(this).val() + $("#sampleType").children(":selected").html())
            }
            $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val(values)
        })
        $("#addAuthenticatorBig").find('select[name="relationPersonName"]').change(function () {
            var values = $(this).val() + $(this).find("option:selected").text()
            if ($("#sampleType").children(":selected").html() !== "请选择样本类型") {
                $("#addAuthenticatorBig").find(".sampleBox").find("input[name='sampleName']").val($(this).find("option:selected").text() + $("#sampleType").children(":selected").html())
                $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val(values + $("#sampleType").children(":selected").html())
            } else {
                $("#addAuthenticatorBig").find(".sampleBox").find("input[name='sampleName']").val($(this).find("option:selected").text())
                $("#addAuthenticatorBig").find(".sampleBox").find("textarea[name='sampleDescribe']").val(values)
            }
        })

        //拒绝弹出框
        $("tbody").on("click", ".isRefuse", function () {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active")
                $(this).siblings("input").val("")
                $(this).siblings("input[name='isRefuseCode']").val("02")
            } else {
                $(this).addClass("active")
                $("#refuseBox").find("input[name='index']").val($(this).parents("tr").index())
                $("#refuseBox").find("input[name='parentId']").val($(this).parents("tbody").attr("id"))
                $("#refuse").val("");
                $("#refuseBox").modal("show")
            }
        })
        //拒绝弹出框
        $(".saveRefuse").click(function () {
            var parentId = $("#refuseBox").find("input[name='parentId']").val()
            var index = $("#refuseBox").find("input[name='index']").val()
            $("#" + parentId).find("tr").eq(index).find("input[name='isRefuse']").val($("#refuse").val())
            $("#" + parentId).find("tr").eq(index).find("input[name='isRefuseCode']").val("04")
            if ($("#" + parentId).find("tr").eq(index).find(".isRetain").hasClass("active")) {
                $("#" + parentId).find("tr").eq(index).find(".isRetain").removeClass("active")
                var retainArr = []
                $(".isRetain").each(function () {
                    if ($(this).hasClass("active")) {
                        retainArr.push($(this).parents("tr").find("input[name='sampleName']").val())
                    }
                })
                if (retainArr.length > 0 && retainArr.length !== $("#materialEvidencerTbody").children().length) {
                    $(".zhanshi").find("span").eq(0).html("除(" + retainArr.join('、') + ")").next().html("均已取走")
                } else if (retainArr.length == $("#materialEvidencerTbody").children().length) {
                    $(".zhanshi").find("span").eq(0).html("").next().html("均已留存")
                } else {
                    $(".zhanshi").find("span").eq(0).html("").next().html("均已取走")
                }
            }
            $("#refuseBox").modal("hide")
        })
        $(".closeRefuse").click(function () {
            var parentId = $("#refuseBox").find("input[name='parentId']").val()
            var index = $("#refuseBox").find("input[name='index']").val()
            $("#" + parentId).find("tr").eq(index).find(".isRefuse").removeClass("active")
            $("#refuseBox").modal("hide")

        })
        $("#refuseBox").on('hidden.bs.modal', function () {
            $("#refuseBox").find("input").val("")
        })

        //添加被鉴定人
        $("#openAddAuthenticatorBig").click(function () {
            $("#addAuthenticatorBig").find("input[name='moveCheckbox']").prop("checked", true)
            $("#addAuthenticatorBig").children().width(1200)
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css({
                "height": 542.6 + 2,
            }).fadeIn(800);
            $("#addAuthenticatorBig").modal("show")
        })
        //新添修改被鉴定人信息
        //全局变量标识
        var CLICKTAG1 = 0;
        $(".addAuthenticator").click(function () {
            if (CLICKTAG1 == 0) {
                CLICKTAG1 = 1;
                //this.disabled=true;
                $("button[name='querButton']").attr("disabled", true)
                // 等待2s后重置按钮可用
                setTimeout(function () {
                    CLICKTAG1 = 0;
                    $("button[name='querButton']").removeAttr("disabled")
                }, 2000);
            }
            var form = $(this).parents("form");
            if ($("#addAuthenticatorBig").find("input[name='noIdCheck']").is(":checked")) {
                form.bootstrapValidator({
                    live: 'disabled',
                    message: 'This value is not valid',
                    fields: {
                        year: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                },
                                regexp: {
                                    regexp: /^[0-9]*$/,
                                    message: '格式有误.'
                                },
                            },
                        },
                        noIdCard: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        personType: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        personName: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        sampleType: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        extractTime: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        year: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                },
                                regexp: {
                                    regexp: /^[0-9]*$/,
                                    message: '格式有误.'
                                },
                            },
                        },
                        kinship:{
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            },
                        }
                    }
                });
            } else {
                form.bootstrapValidator({
                    live: 'disabled',
                    message: 'This value is not valid',
                    fields: {
                        year: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                },
                                regexp: {
                                    regexp: /^[0-9]*$/,
                                    message: '格式有误.'
                                },
                            },
                        },
                        idCard: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                },
                                regexp: {
                                    regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                                    message: '身份证输入有误'
                                },
                            }
                        },
                        year: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                },
                                regexp: {
                                    regexp: /^[0-9]*$/,
                                    message: '格式有误.'
                                },
                            },
                        },
                        personType: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        personName: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        sampleType: {
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        extractTime: {
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            }
                        },
                        kinship:{
                            trigger: 'change',
                            validators: {
                                notEmpty: {
                                    message: "不能为空"
                                }
                            },
                        }
                    }
                });
            }
            form.bootstrapValidator('validate');
            scroll(form);
            if (form.data('bootstrapValidator').isValid()) {
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);

                var authenticatorBox = $("#addAuthenticatorBig").find(".modal-content").eq(0),
                        personType = authenticatorBox.find("select[name='personType']").val(),
                        personTypeName = authenticatorBox.find("select[name='personType']").find("option:selected").text(),
                        personName = authenticatorBox.find("input[name='personName']").val(),
                        sex = authenticatorBox.find("input[name='sex']:checked").val(),
                        idCard = authenticatorBox.find("input[name='idCard']").val(),
                        noIdCard = authenticatorBox.find("input[name='noIdCard']").val(),
                        nowId = "",
                        kinshipName = "",
                        year = authenticatorBox.find("input[name='year']").val(),
                        kinship = authenticatorBox.find("select[name='kinship']").val(),
                        personFileName = authenticatorBox.find("img").attr("alt"),
                        authenticatorIndex = authenticatorBox.find("input[name='index']").val(),
                        personCurrentAddress = authenticatorBox.find("input[name='personCurrentAddress']").val(),
                        personHeight = authenticatorBox.find("input[name='personHeight']").val(),
                        personWeight = authenticatorBox.find("input[name='personWeight']").val(),
                        personFrontPicture = $("#personBase").val(),
                        timeId = new Date().getTime(),
                        personLength = Number($("#authenticatorTbody").children().length) + 1;
                var sexName;
                if (sex == '01') {
                    sexName = '男'
                } else if (sex == '02') {
                    sexName = '女'
                }
                if (kinship == null) {
                    kinshipName = ''
                    kinship = ""
                } else {
                    kinshipName = authenticatorBox.find("select[name='kinship']").find("option:selected").text()
                }
                if (noIdCard !== "") {
                    nowId = '无身份证号(' + noIdCard + ')'
                } else {
                    nowId = idCard
                }

                if (authenticatorIndex != "") {
                    var tr = $("#authenticatorTbody").children("tr").eq(authenticatorIndex)
                    tr.find('input[name="personType"]').val(personType)
                    tr.find('input[name="personTypeName"]').val(personTypeName)
                    tr.find('input[name="personName"]').val(personName)
                    tr.find('input[name="sexName"]').val(sexName)
                    tr.find('input[name="sex"]').val(sex)
                    tr.find('input[name="nowId"]').val(nowId)
                    tr.find('input[name="idCard"]').val(idCard)
                    tr.find('input[name="noIdCard"]').val(noIdCard)
                    tr.find('input[name="year"]').val(year)
                    tr.find('input[name="kinship"]').val(kinship)
                    tr.find('input[name="kinshipName"]').val(kinshipName)
                    tr.find('input[name="personCurrentAddress "]').val(personCurrentAddress)
                    tr.find('input[name="personHeight"]').val(personHeight)
                    tr.find('input[name="personWeight"]').val(personWeight)
                    tr.find('input[name="personFileName"]').val(personFileName)
                    tr.find('input[name="personFrontPicture"]').val(personFrontPicture)
                    if (tr.find("input[name='personId']").length) {
                        $("#personSampleTbody").find("input[name='linkId'][value='" + tr.find("input[name='personId']").val() + "']").parents("tr").find("input[name='personName']").parent().html(personName + '<input type="hidden" name="personName" value="' + personName + '">')
                    } else {
                        $("#personSampleTbody").find("input[name='timeId'][value='" + tr.find("input[name='timeId']").val() + "']").parents("tr").find("input[name='personName']").parent().html(personName + '<input type="hidden" name="personName" value="' + personName + '">')
                    }
                    tr.children("td").each(function (i) {
                        if (tr.children("td").length - 1 !== i) {
                            var tdInput = $(this).children("input").clone()
                            if (i == 5) {
                                $(this).html(nowId)
                            } else {
                                $(this).html(tdInput.val())
                            }
                            $(this).append(tdInput)
                        }
                    })
                    $('#addAuthenticatorBig').modal('hide')
                    return true;
                } else {
                    //新添被鉴定人
                    var personTr = '<tr>'
                    personTr += '<td>' + personLength + '</td>'
                    personTr += '<td>' + personTypeName + '<input type="hidden" name="personTypeName" value="' + personTypeName + '"/></td>'
                    personTr += '<td>' + personName + '<input type="hidden" name="personName" value="' + personName + '"/></td>'
                    personTr += '<td>' + sexName + '<input type="hidden" name="sexName" value="' + sexName + '"/></td>'
                    personTr += '<td>' + year + '<input type="hidden" name="year" value="' + year + '"/></td>'
                    personTr += '<td>' + nowId + '<input type="hidden" name="idCard" value="' + idCard + '"/><input type="hidden" name="noIdCard" value="' + noIdCard + '"/></td>'
                    personTr += '<td>' + kinshipName + '<input type="hidden" name="kinshipName" value="' + kinshipName + '"/></td>'
                    personTr += '<td><div class="showPhoto"></div></td>'
                    personTr += '<td>'
                    personTr += '<button type="button" class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑</button>'
                    personTr += '<button type="button" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除</button>'
                    personTr += '<input type="hidden" name="personType" value="' + personType + '"/>'
                    personTr += '<input type="hidden" name="sex" value="' + sex + '"/>'
                    personTr += '<input type="hidden" name="kinship" value="' + kinship + '"/>'
                    personTr += '<input type="hidden" name="personCurrentAddress" value="' + personCurrentAddress + '"/>'
                    personTr += '<input type="hidden" name="personHeight" value="' + personHeight + '"/>'
                    personTr += '<input type="hidden" name="personFileName" value="' + personFileName + '"/>'
                    personTr += '<input type="hidden" name="personWeight" value="' + personWeight + '"/>'
                    personTr += '<input type="hidden" name="personFrontPicture" value="' + personFrontPicture + '"/>'
                    personTr += '<input type="hidden" name="timeId" value="' + timeId + '"/>'
                    personTr += '</td>'
                    personTr += '</tr>'
                    $("#authenticatorTbody").append(personTr)
                }
                var personSampleBox = $("#addAuthenticatorBig").find(".sampleBox"),
                        sampleType = personSampleBox.find('select[name="sampleType"]').val(),
                        sampleTypeName = personSampleBox.find('select[name="sampleType"]').find("option:selected").text(),
                        sampleName = personSampleBox.find('input[name="sampleName"]').val(),
                        samplePacking = personSampleBox.find('select[name="samplePacking"]').val(),
                        samplePackingName = personSampleBox.find('select[name="samplePacking"]').find("option:selected").text(),
                        extractTime = personSampleBox.find('input[name="extractTime"]').val(),
                        extractMethod = personSampleBox.find('select[name="extractMethod"]').val(),
                        sampleCarrier = personSampleBox.find('select[name="sampleCarrier"]').val(),
                        inspectionObjective = personSampleBox.find('input[name="inspectionObjective"]').val(),
                        sampleDescribe = personSampleBox.find('textarea[name="sampleDescribe"]').val(),
                        sampleIndex = personSampleBox.find('input[name="index"]').val(),
                        relationPersonName = personSampleBox.find('select[name="relationPersonName"]').find("option:selected").text(),
                        sampleDnaPicture = $("#sampleBase").val(),
                        sampleLength = Number($("#personSampleTbody").children().length) + 1;
                var extractMethodBox = personSampleBox.find('select[name="extractMethod"]').parent().clone()
                var sampleCarrierBox = personSampleBox.find('select[name="sampleCarrier"]').parent().clone()
                extractMethodBox.find("select").val(extractMethod)
                sampleCarrierBox.find("select").val(sampleCarrier)
                if (authenticatorBox.find("input[name='moveCheckbox']").is(':checked')) {
                    //添加样本
                    var personSampleTr = '<tr>'
                    personSampleTr += '<td>' + sampleLength + '</td>'
                    personSampleTr += '<td>' + sampleTypeName + '<input type="hidden" name="sampleTypeName" value="' + sampleTypeName + '"/></td>'
                    personSampleTr += '<td>' + sampleName + '<input type="hidden" name="sampleName" value="' + sampleName + '"/></td>'
                    personSampleTr += '<td>' + sampleDescribe + '<input type="hidden" name="sampleDescribe" value="' + sampleDescribe + '"/></td>'
                    personSampleTr += '<td>' + samplePackingName + '<input type="hidden" name="samplePackingName" value="' + samplePackingName + '"/></td>'
                    personSampleTr += '<td>' + extractTime + '<input type="hidden" name="extractTime" value="' + extractTime + '"/></td>'
                    personSampleTr += '<td></td>'
                    personSampleTr += '<td>' + inspectionObjective + '<input type="hidden" name="inspectionObjective" value="' + inspectionObjective + '"/></td>'
                    personSampleTr += '<td>' + personName + '<input type="hidden" name="personName" value="' + personName + '"/></td>'
                    personSampleTr += '<td><div class="isRefuse"></div><input type="hidden" name="isRefuse" value=""/><input type="hidden" name="isRefuseCode" value="02"/></td>'
                    personSampleTr += '<td>'
                    personSampleTr += '<button class="btn-icon btn-icon-blue btn-icon-bianji-blue edit" type="button">编辑</button>'
                    personSampleTr += '<button type="button" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除</button>'
                    personSampleTr += '<input type="hidden" name="sampleType" value="' + sampleType + '"/>'
                    personSampleTr += '<input type="hidden" name="samplePacking" value="' + samplePacking + '"/>'
                    personSampleTr += '<input type="hidden" name="extractMethod" value="' + extractMethod + '"/>'
                    personSampleTr += '<input type="hidden" name="sampleCarrier" value="' + sampleCarrier + '"/>'
                    personSampleTr += '<input type="hidden" name="personLength" value="' + personLength + '"/>'
                    personSampleTr += '<input type="hidden" name="sampleDnaPicture" value="' + sampleDnaPicture + '"/>'
                    personSampleTr += '<input type="hidden" name="addFlag" value="1"/>'//判断样本是否添加
                    personSampleTr += '<input type="hidden" name="timeId" value="' + timeId + '"/>'
                    personSampleTr += '</td>'
                    personSampleTr += '</tr>'
                    $("#personSampleTbody").append(personSampleTr)
                    $("#personSampleTbody").children().eq(sampleLength - 1).find("td").eq(6).append(extractMethodBox)
                    $("#personSampleTbody").children().eq(sampleLength - 1).find("td").eq(6).append(sampleCarrierBox)
                }
                $('#addAuthenticatorBig').modal('hide')

            }
        })
        //被鉴定人照片展示
        $("#authenticatorTbody").on("mouseenter", ".showPhoto", function () {
            if (!$(this).parents("tr").find("input[name='personFrontPicture']").val()) {
                $(this).css("cursor", "no-drop").attr("title", "暂无图片")
            } else {
                $(this).css("cursor", "pointer").removeAttr("title")
            }
        })
        $("#authenticatorTbody").on("click", ".showPhoto", function () {
            if ($(this).parents("tr").find("input[name='personFrontPicture']").val()) {
                $("#showPhotoBox").find("img").attr("src", $(this).parents("tr").find("input[name='personFrontPicture']").val())
                $("#showPhotoBox").modal("show")
            }
        })
        $("#addAuthenticatorBig").on("click", "img", function () {
            $("#showPhotoBox").find("img").attr("src", $(this).attr("src"))
            $("#showPhotoBox").modal("show")
        })
        $("#materialEvidencerlSampleBox").on("click", "img", function () {
            $("#showPhotoBox").find("img").attr("src", $(this).attr("src"))
            $("#showPhotoBox").modal("show")
        })
        $("#showPhotoBox").on('show.bs.modal', function (e) {
            $("#showPhotoBox").css("z-index", 1050 + 1000);
        });
        //修改被鉴定人
        $("#authenticatorTbody").on("click", ".edit", function () {
            $("#addAuthenticatorBig").find("input[name='moveCheckbox']").parents(".form-group ").addClass("hidden")
            var authenticatorTr = $(this).parents("tr"),
                    authenticatorBox = $("#addAuthenticatorBig").find(".modal-content").eq(0),
                    personType = authenticatorTr.find("input[name='personType']").val(),
                    personName = authenticatorTr.find("input[name='personName']").val(),
                    sexName = authenticatorTr.find("input[name='sexName']").val(),
                    idCard = authenticatorTr.find("input[name='idCard']").val(),
                    noIdCard = authenticatorTr.find("input[name='noIdCard']").val(),
                    year = authenticatorTr.find("input[name='year']").val(),
                    kinship = authenticatorTr.find("input[name='kinship']").val(),
                    personCurrentAddress = authenticatorTr.find("input[name='personCurrentAddress']").val(),
                    personHeight = authenticatorTr.find("input[name='personHeight']").val(),
                    personWeight = authenticatorTr.find("input[name='personWeight']").val(),
                    personFrontPicture = authenticatorTr.find("input[name='personFrontPicture']").val(),
                    index = authenticatorTr.index();
            if (noIdCard) {
                authenticatorBox.find("input[type='checkbox']").prop("checked", true)
                authenticatorBox.find("input[name='noIdCard']").removeClass("hidden")
            }
            if (kinship) {
                authenticatorBox.find("select[name='kinship']").parents(".form-group ").removeClass("hidden")
            }
            if (sexName == '男') {
                authenticatorBox.find("input[name='sex'][value='01']").prop("checked", true)
            } else if (sexName == '女') {
                authenticatorBox.find("input[name='sex'][value='02']").prop("checked", true)
            }
            console.log("ffggg" + personFrontPicture)
            if (personFrontPicture != "") {
                authenticatorBox.find("img").attr("src", personFrontPicture)
            }
            authenticatorBox.find(".addphoto").html("修改")
            authenticatorBox.find("select[name='personType']").val(personType)
            authenticatorBox.find("input[name='personName']").val(personName)
            authenticatorBox.find("input[name='idCard']").val(idCard)
            authenticatorBox.find("input[name='noIdCard']").val(noIdCard)
            authenticatorBox.find("input[name='year']").val(year)
            authenticatorBox.find("input[name='index']").val(index)
            authenticatorBox.find("input[name='personCurrentAddress']").val(personCurrentAddress)
            authenticatorBox.find("input[name='personHeight']").val(personHeight)
            authenticatorBox.find("input[name='personWeight']").val(personWeight)
            authenticatorBox.find("select[name='kinship']").val(kinship)
            $("#addAuthenticatorBig").modal('show')
        })

        //添加样本
        $(".addsampleInfo").click(function () {
            if ($("#authenticatorTbody").children().length > 0) {
                $("#addAuthenticatorBig").find('select[name="relationPersonName"]').parents(".col-md-6").removeClass('hidden')
                $("#addAuthenticatorBig").find(".modal-content").eq(0).css("display", 'none');
                $("#addAuthenticatorBig").find(".modal-content").eq(1).css("display", 'block').height("auto");
                $("#addAuthenticatorBig").find(".modal-content").eq(1).find(".modal-footer").removeClass("hidden");
                $("#addAuthenticatorBig").find('select[name="relationPersonName"]').append("<option value=''>请选择关联人员</option>")
                $("#authenticatorTbody").children().each(function () {
                    var newOption = '<option timeId="' + $(this).find("input[name='timeId']").val() + '"  linkId="' + $(this).find("input[name='personId']").val() + '" value="' + $(this).find("input[name='personTypeName']").val() + '">' + $(this).find("input[name='personName']").val() + '</option>'
                    $("#addAuthenticatorBig").find('select[name="relationPersonName"]').append(newOption)
                })
                $("#addAuthenticatorBig").children().width(720)
                $("#addAuthenticatorBig").modal("show")
            } else {
                alert("请先添加被鉴定人")
            }

        })
        //样本保存
        //全局变量标识
        var CLICKTAG = 0;
        $(".addSampleBtn").click(function () {

            if (CLICKTAG == 0) {
                CLICKTAG = 1;
                //this.disabled=true;
                $("button[name='querenSample']").attr("disabled", true)
                // 等待2s后重置按钮可用
                setTimeout(function () {
                    CLICKTAG = 0;
                    $("button[name='querenSample']").removeAttr("disabled")
                }, 2000);
            }
            var form = $(this).parents("form");
            form.bootstrapValidator({
                live: 'disabled',
                message: 'This value is not valid',
                fields: {
                    relationPersonName: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    sampleType: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    extractTime: {
                        trigger: 'change',
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                }
            });
            form.bootstrapValidator('validate');
            scroll(form);
            if (form.data('bootstrapValidator').isValid()) {
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
                var personSampleBox = $("#addAuthenticatorBig").find(".sampleBox"),
                        sampleType = personSampleBox.find('select[name="sampleType"]').val(),
                        sampleTypeName = personSampleBox.find('select[name="sampleType"]').find("option:selected").text(),
                        sampleName = personSampleBox.find('input[name="sampleName"]').val(),
                        samplePacking = personSampleBox.find('select[name="samplePacking"]').val(),
                        samplePackingName = personSampleBox.find('select[name="samplePacking"]').find("option:selected").text(),
                        extractTime = personSampleBox.find('input[name="extractTime"]').val(),
                        extractMethod = personSampleBox.find('select[name="extractMethod"]').val(),
                        sampleCarrier = personSampleBox.find('select[name="sampleCarrier"]').val(),
                        inspectionObjective = personSampleBox.find('input[name="inspectionObjective"]').val(),
                        sampleDescribe = personSampleBox.find('textarea[name="sampleDescribe"]').val(),
                        sampleIndex = personSampleBox.find('input[name="index"]').val(),
                        relationPersonName = personSampleBox.find('select[name="relationPersonName"]').find("option:selected").text(),
                        sampleDnaPicture = $("#sampleBase").val(),
                        timeId = personSampleBox.find('select[name="relationPersonName"]').find("option:selected").attr("timeId"),
                        linkId = personSampleBox.find('select[name="relationPersonName"]').find("option:selected").attr("linkId");
                var extractMethodBox = personSampleBox.find('select[name="extractMethod"]').parent().clone()
                var sampleCarrierBox = personSampleBox.find('select[name="sampleCarrier"]').parent().clone()
                extractMethodBox.find("select").val(extractMethod)
                sampleCarrierBox.find("select").val(sampleCarrier)
                if (sampleIndex != "") {
                    var tr = $("#personSampleTbody").children("tr").eq(sampleIndex)
                    tr.find('input[name="sampleType"]').val(sampleType)
                    tr.find('input[name="sampleTypeName"]').val(sampleTypeName)
                    tr.find('input[name="sampleName"]').val(sampleName)
                    tr.find('input[name="samplePacking"]').val(samplePacking)
                    tr.find('input[name="samplePackingName"]').val(samplePackingName)
                    tr.find('input[name="extractTime"]').val(extractTime)
                    tr.find('input[name="extractMethod"]').val(extractMethod)
                    tr.find('input[name="sampleCarrier"]').val(sampleCarrier)
                    tr.find('input[name="inspectionObjective"]').val(inspectionObjective)
                    tr.find('input[name="sampleDescribe"]').val(sampleDescribe)
                    tr.find('input[name="sampleDnaPicture"]').val(sampleDnaPicture)
                    tr.children("td").each(function (i) {
                        if (tr.children("td").length - 1 !== i) {
                            var tdInput = $(this).children("input").clone()
                            if (i == 9) {

                            } else if (i == 6) {
                                $(this).find("select[name='extractMethod']").val(extractMethod)
                                $(this).find("select[name='sampleCarrier']").val(sampleCarrier)
                            } else {
                                $(this).html(tdInput.val())
                                $(this).append(tdInput)
                            }

                        }
                    })
                }
                else {
                    //添加样本
                    var sampleLength = Number($("#personSampleTbody").children().length) + 1
                    var personSampleTr = '<tr>'
                    personSampleTr += '<td>' + sampleLength + '</td>'
                    personSampleTr += '<td>' + sampleTypeName + '<input type="hidden" name="sampleTypeName" value="' + sampleTypeName + '"/></td>'
                    personSampleTr += '<td>' + sampleName + '<input type="hidden" name="sampleName" value="' + sampleName + '"/></td>'
                    personSampleTr += '<td>' + sampleDescribe + '<input type="hidden" name="sampleDescribe" value="' + sampleDescribe + '"/></td>'
                    personSampleTr += '<td>' + samplePackingName + '<input type="hidden" name="samplePackingName" value="' + samplePackingName + '"/></td>'
                    personSampleTr += '<td>' + extractTime + '<input type="hidden" name="extractTime" value="' + extractTime + '"/></td>'
                    personSampleTr += '<td></td>'
                    personSampleTr += '<td>' + inspectionObjective + '<input type="hidden" name="inspectionObjective" value="' + inspectionObjective + '"/></td>'
                    personSampleTr += '<td>' + relationPersonName + '<input type="hidden" name="personName" value="' + relationPersonName + '"/></td>'
                    personSampleTr += '<td><div class="isRefuse"></div><input type="hidden" name="isRefuse" value=""/><input type="hidden" name="isRefuseCode" value="02"/></td>'
                    personSampleTr += '<td>'
                    personSampleTr += '<button class="btn-icon btn-icon-blue btn-icon-bianji-blue edit" type="button">编辑</button>'
                    personSampleTr += '<button type="button" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除</button>'
                    personSampleTr += '<input type="hidden" name="sampleType" value="' + sampleType + '"/>'
                    personSampleTr += '<input type="hidden" name="samplePacking" value="' + samplePacking + '"/>'
                    personSampleTr += '<input type="hidden" name="extractMethod" value="' + extractMethod + '"/>'
                    personSampleTr += '<input type="hidden" name="sampleCarrier" value="' + sampleCarrier + '"/>'
                    personSampleTr += '<input type="hidden" name="sampleDnaPicture" value="' + sampleDnaPicture + '"/>'
                    personSampleTr += '<input type="hidden" name="linkId" value="' + linkId + '"/>'
                    personSampleTr += '<input type="hidden" name="timeId" value="' + timeId + '"/>'
                    personSampleTr += '</td>'
                    personSampleTr += '</tr>'
                    $("#personSampleTbody").append(personSampleTr)
                    $("#personSampleTbody").children().eq(sampleLength - 1).find("td").eq(6).append(extractMethodBox)
                    $("#personSampleTbody").children().eq(sampleLength - 1).find("td").eq(6).append(sampleCarrierBox)
                }
                $('#addAuthenticatorBig').modal('hide')
            }
        })
        $("body").on("change", "select", function () {
            $(this).parents("tr").find("input[name='" + $(this).attr("name") + "']").val($(this).val())
        })
        //修改样本
        $("#personSampleTbody").on("click", ".edit", function () {
            $("#addAuthenticatorBig").find(".modal-content").eq(0).css("display", 'none');
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css("display", 'block').height("auto");
            $("#addAuthenticatorBig").find(".modal-content").eq(1).find(".modal-footer").removeClass("hidden");
            $("#addAuthenticatorBig").find(".modal-content").eq(1).find("input[name='sampleNo']").parents(".col-md-6").removeClass("hidden")

            $("#addAuthenticatorBig").children().width(720)
            var authenticatorTr = $(this).parents("tr"),
                    authenticatorBox = $("#addAuthenticatorBig").find(".modal-content").eq(1),
                    sampleType = authenticatorTr.find("input[name='sampleType']").val(),
                    sampleName = authenticatorTr.find("input[name='sampleName']").val(),
                    samplePacking = authenticatorTr.find("input[name='samplePacking']").val(),
                    extractTime = authenticatorTr.find("input[name='extractTime']").val(),
                    extractMethod = authenticatorTr.find("input[name='extractMethod']").val(),
                    sampleCarrier = authenticatorTr.find("input[name='sampleCarrier']").val(),
                    inspectionObjective = authenticatorTr.find("input[name='inspectionObjective']").val(),
                    sampleDnaPicture = authenticatorTr.find("input[name='sampleDnaPicture']").val(),
                    sampleDescribe = authenticatorTr.find("input[name='sampleDescribe']").val(),
                    sampleNo = authenticatorTr.find("input[name='sampleNo']").val(),
                    sampleIndex = authenticatorTr.index();

            authenticatorBox.find("input[name='sampleNo']").val(sampleNo)
            authenticatorBox.find("select[name='sampleType']").val(sampleType)
            authenticatorBox.find("input[name='sampleName']").val(sampleName)
            authenticatorBox.find("select[name='samplePacking']").val(samplePacking)
            authenticatorBox.find("input[name='extractTime']").val(extractTime)
            authenticatorBox.find("select[name='extractMethod']").find("option[value='" + extractMethod + "']").prop("selected", true)
            authenticatorBox.find("select[name='sampleCarrier']").find("option[value='" + sampleCarrier + "']").prop("selected", true)
            authenticatorBox.find("input[name='inspectionObjective']").val(inspectionObjective)
            authenticatorBox.find("textarea[name='sampleDescribe']").val(sampleDescribe)
            if (sampleDnaPicture) {
                var imgBox = ' <div class="col-md-2">'
                imgBox += '<img class="sampleDnaPicture" src="' + sampleDnaPicture + '" alt="">'
                imgBox += '<i class="fa fa-times-circle removePhoto" aria-hidden="true"></i>'
                imgBox += ' </div>'
            }
            $("#addAuthenticatorBig").find(".samplePhotobox").children().eq(0).before(imgBox)
            $("#sampleBase").val(sampleDnaPicture)
            authenticatorBox.find("input[name='index']").val(sampleIndex)
            $("#addAuthenticatorBig").modal('show')
        })
        //人员样本关闭清空
        $("#addAuthenticatorBig").on('hidden.bs.modal', function (e) {
            if ($("#addAuthenticatorBig").find(".has-error").length > 0) {
                var form = $("#addAuthenticatorBig").find("form")
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
            }
            $("#addAuthenticatorBig").find('input[name="sampleNo"]').parents(".col-md-6").addClass('hidden')
            $("#addAuthenticatorBig").find('select[name="relationPersonName"]').children().remove()
            $("#addAuthenticatorBig").find('select[name="relationPersonName"]').parents(".col-md-6").addClass('hidden')
            $("#addAuthenticatorBig").find("input[type='text']").val("")
            $("#addAuthenticatorBig").find("input[type='hidden']").val("")
            $("#addAuthenticatorBig").find(".modal-content").eq(1).find("input[name='inspectionObjective']").val("")
            $("#addAuthenticatorBig").find("input[type='radio'][value='01']").prop("checked", true)
            $("#addAuthenticatorBig").find("input[type='checkbox']:checked").prop("checked", false)
            $("#addAuthenticatorBig").find("input[name='idCard']").prop("disabled", false)
            $("#addAuthenticatorBig").find("input[name='noIdCard']").addClass("hidden")
            $("#photoFile").val("")
            $("#addAuthenticatorBig").find("select").val("")
            $("#addAuthenticatorBig").find("textarea").val("")
            $("#addAuthenticatorBig").find("select[name='kinship']").parents(".form-group").addClass("hidden")
            $("#addAuthenticatorBig").find(".moveInput").addClass("hidden")
            $("#addAuthenticatorBig").find(".modal-content").eq(0).css("display", "block")
            $("#addAuthenticatorBig").find(".modal-content").eq(1).css("display", "none")
            $("#addAuthenticatorBig").find(".modal-content").eq(0).find("img").attr("src", '<%=path%>/img/policeman.png')
            $("#addAuthenticatorBig").find("input[name='moveCheckbox']").parents(".form-group ").removeClass("hidden")
            $("#addAuthenticatorBig").find(".modal-content").eq(1).find(".modal-footer").addClass("hidden")
            $("#addAuthenticatorBig").find(".moveBtn").html("添加更多")
            $("#addAuthenticatorBig").find(".addphoto").html("添加照片")
            $("#addAuthenticatorBig").find("select[name='samplePacking']").val("01")
            $("#addAuthenticatorBig").find("select[name='extractMethod']").val("01")
            $("#addAuthenticatorBig").find("select[name='sampleCarrier']").val("01")
            $("#addAuthenticatorBig").find("input[name='inspectionObjective']").val("DNA检验")
            $("#addAuthenticatorBig").find("input[name='extractTime']").val("<%=time%>")

            $("#addAuthenticatorBig").find(".addsamplePhoto").parent().siblings().remove()
            $("#addAuthenticatorBig").find("input[name='idCard']").siblings().addClass("hidden")
            $(".samplePhoto").parents(".samplePhotobox").siblings("span").addClass("hidden")
            $(".photoFile").siblings("span").addClass("hidden")
            $("#addAuthenticatorBig").children().width(480)
        })

        // 拆分检材信息
        $(".addMaterialEvidencer").click(function () {
            $("#materialEvidencerlSampleBox").modal('show')
        })

        // 检材信息
        $("#materialEvidencerTbody").on("click", ".edit", function () {
            var sampleTypeName = $(this).parents("tr").find('input[name="sampleTypeName"]').val(),
                    sampleName = $(this).parents("tr").find("input[name='sampleName']").val(),
                    samplePackingName = $(this).parents("tr").find('input[name="samplePackingName"]').val(),
                    extractTime = $(this).parents("tr").find('input[name="extractTime"]').val(),
                    extractMethod = $(this).parents("tr").find('input[name="extractMethod"]').val(),
                    sampleCarrier = $(this).parents("tr").find('input[name="sampleCarrier"]').val(),
                    inspectionObjective = $(this).parents("tr").find('input[name="inspectionObjective"]').val(),

                    isRefuse = $(this).parents("tr").find('input[name="isRefuse"]').val(),
                    isRefuseCode = $(this).parents("tr").find('input[name="isRefuseCode"]').val(),
                    sampleNo = $(this).parents("tr").find('input[name="sampleNo"]').val(),
                    evidenceNo = $(this).parents("tr").find('input[name="evidenceNo"]').val(),
                    samplePicture = $(this).parents("tr").find('input[name="samplePicture"]').val(),
                    index = $(this).parents("tr").index(),
                    sampleDescribe = $(this).parents("tr").find('input[name="sampleDescribe"]').val();

            if (sampleTypeName !== "") {
                $("#materialEvidencerlSampleBox").find('select[name="sampleType"]').children("option:contains('" + sampleTypeName + "')").prop("selected", true)
            } else {
                $("#materialEvidencerlSampleBox").find('select[name="sampleType"]').children("option[value='']").prop("selected", true)
            }
            if (samplePackingName !== "") {
                $("#materialEvidencerlSampleBox").find('select[name="samplePacking"]').children("option:contains('" + samplePackingName + "')").prop("selected", true)
            } else {
                $("#materialEvidencerlSampleBox").find('select[name="samplePacking"]').children("option[value='']").prop("selected", true)
            }
            if (extractMethod !== "") {
                $("#materialEvidencerlSampleBox").find('select[name="extractMethod"]').val(extractMethod)
            } else {
                $("#materialEvidencerlSampleBox").find('select[name="extractMethod"]').children("option[value='']").prop("selected", true)
            }
            if (sampleCarrier !== "") {
                $("#materialEvidencerlSampleBox").find('select[name="sampleCarrier"]').val(sampleCarrier)
            } else {
                $("#materialEvidencerlSampleBox").find('select[name="sampleCarrier"]').children("option[value='']").prop("selected", true)
            }
            if (samplePicture) {
                var imgBox = ' <div class="col-md-2">'
                imgBox += '<img class="sampleDnaPicture" src="' + samplePicture + '" alt="">'
                imgBox += '<i class="fa fa-times-circle removePhoto" aria-hidden="true"></i>'
                imgBox += ' </div>'
                $("#materialEvidencerlSampleBox").find(".samplePhotobox").children().eq(0).before(imgBox)
            }
            $("#materialEvidencerlSampleBox").find('input[name="sampleNo"]').val(sampleNo).parents(".col-md-6").removeClass("hidden")
            $("#materialEvidencerlSampleBox").find('input[name="extractMethod"]').val(extractMethod)
            $("#materialEvidencerlSampleBox").find('input[name="sampleCarrier"]').val(sampleCarrier)
            $("#materialEvidencerlSampleBox").find('select[name="extractMethod"]').val(extractMethod)
            $("#materialEvidencerlSampleBox").find('select[name="sampleCarrier"]').val(sampleCarrier)
            $("#materialEvidencerlSampleBox").find('input[name="isRefuse"]').val(isRefuse)
            $("#materialEvidencerlSampleBox").find('input[name="isRefuseCode"]').val(isRefuseCode)
            $("#materialEvidencerlSampleBox").find('input[name="parentIndex"]').val("")
            $("#materialEvidencerlSampleBox").find('input[name="evidenceNo"]').val(evidenceNo)
            $("#materialEvidencerlSampleBox").find('input[name="sampleName"]').val(sampleName)
            $("#materialEvidencerlSampleBox").find('input[name="extractTime"]').val(extractTime)
            $("#materialEvidencerlSampleBox").find('input[name="inspectionObjective"]').val(inspectionObjective)
            $("#materialEvidencerlSampleBox").find('textarea[name="sampleDescribe"]').val(sampleDescribe)
            $("#materialEvidencerlSampleBox").find('input[name="index"]').val(index)
            $("#samplePicture").val(samplePicture)
            $("#materialEvidencerlSampleBox").modal('show')
        })
        //全局变量标识
        var CLICKTAG2 = 0;
        $('.addMaterialEvidencerlSample').click(function () {
            if (CLICKTAG2 == 0) {
                CLICKTAG2 = 1;
                //this.disabled=true;
                $("button[name='wzSampleButton']").attr("disabled", true)
                // 等待2s后重置按钮可用
                setTimeout(function () {
                    CLICKTAG2 = 0;
                    $("button[name='wzSampleButton']").removeAttr("disabled")
                }, 2000);
            }
            var form = $(this).parents("form");
            form.bootstrapValidator({
                live: 'disabled',
                message: 'This value is not valid',
                fields: {
                    sampleType: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    extractTime: {
                        trigger: 'change',
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    sampleName: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                }
            });
            form.bootstrapValidator('validate');
            scroll(form);
            if (form.data('bootstrapValidator').isValid()) {
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
                var sampleTypeName = $("#materialEvidencerlSampleBox").find('select[name="sampleType"]').children("option:selected").html(),
                        sampleType = $("#materialEvidencerlSampleBox").find('select[name="sampleType"]').val(),
                        sampleName = $("#materialEvidencerlSampleBox").find('input[name="sampleName"]').val(),
                        samplePackingName = $("#materialEvidencerlSampleBox").find('select[name="samplePacking"]').children("option:selected").html(),
                        samplePacking = $("#materialEvidencerlSampleBox").find('select[name="samplePacking"]').val(),
                        extractTime = $("#materialEvidencerlSampleBox").find('input[name="extractTime"]').val(),
                        extractMethod = $("#materialEvidencerlSampleBox").find('select[name="extractMethod"]').val(),
                        sampleCarrier = $("#materialEvidencerlSampleBox").find('select[name="sampleCarrier"]').val(),
                        inspectionObjective = $("#materialEvidencerlSampleBox").find('input[name="inspectionObjective"]').val(),
                        sampleDescribe = $("#materialEvidencerlSampleBox").find('textarea[name="sampleDescribe"]').val(),
                        isRefuse = $("#materialEvidencerlSampleBox").find('input[name="isRefuse"]').val(),
                        isRefuseCode = $("#materialEvidencerlSampleBox").find('input[name="isRefuseCode"]').val(),
                        samplePicture = $("#samplePicture").val(),
                        length = $("#materialEvidencerTbody").children().length + 1,
                        index = $("#materialEvidencerlSampleBox").find('input[name="index"]').val();

                if (index != "") {
                    var tr = $("#materialEvidencerTbody").children("tr").eq(index)
                    tr.find('input[name="sampleTypeName"]').val(sampleTypeName)
                    tr.find('input[name="sampleType"]').val(sampleType)
                    tr.find('input[name="sampleName"]').val(sampleName)
                    tr.find('input[name="samplePackingName"]').val(samplePackingName)
                    tr.find('input[name="samplePacking"]').val(samplePacking)
                    tr.find('input[name="sampleDescribe"]').val(sampleDescribe)
                    tr.find('input[name="extractTime"]').val(extractTime)
                    tr.find('input[name="extractMethod"]').val(extractMethod)
                    tr.find('input[name="sampleCarrier"]').val(sampleCarrier)
                    tr.find('input[name="inspectionObjective"]').val(inspectionObjective)
                    tr.find('input[name="samplePicture"]').val(samplePicture)
                    tr.children("td").each(function (i) {
                        if (tr.children("td").length - 1 !== i) {
                            var tdInput = $(this).children("input").clone()
                            if (i == 6) {
                                $(this).find("select[name='extractMethod']").val(extractMethod)
                                $(this).find("select[name='sampleCarrier']").val(sampleCarrier)
                            } else if (i == 11) {
                                tr.find('input[name="isRefuse"]').val(isRefuse)
                                tr.find('input[name="isRefuseCode"]').val(isRefuseCode)
                            } else if (i == 12) {

                            } else {
                                $(this).html(tdInput.val())
                                $(this).append(tdInput)
                            }

                        }
                    })
                } else {
                    var Box = '<div class="select"><select class="form-control" required="" name="extractMethod"> <option value="01" selected="">擦</option> <option value="02">吸</option> <option value="03">粘</option> <option value="04">剪</option> <option value="05">刮</option> <option value="06">其他</option> </select> </div>'
                    Box += '<div class="select"><select class="form-control" required="" name="sampleCarrier"> <option value="01" selected="">棉签</option> <option value="02">粘取器</option> <option value="03">烟蒂</option> <option value="04">血卡</option><option value="05">实物</option><option value="06">唾液卡</option></select></div>'
                    var newTr = '<tr>'
                    newTr += '<td>' + length + '</td>'
                    newTr += '<td></td>'
                    newTr += '<td>' + sampleTypeName + '<input type="hidden" name="sampleTypeName" value="' + sampleTypeName + '"></td>'
                    newTr += '<td>' + sampleName + '<input type="hidden" name="sampleName" value="' + sampleName + '"></td>'
                    newTr += '<td>' + sampleDescribe + '<input type="hidden" name="sampleDescribe" value="' + sampleDescribe + '"></td>'
                    newTr += '<td>' + samplePackingName + '<input type="hidden" name="samplePackingName" value="' + samplePackingName + '"></td>'
                    newTr += '<td>' + extractTime + '<input type="hidden" name="extractTime" value="' + extractTime + '"></td>'
                    newTr += '<td>' + Box + '</td>'
                    newTr += '<td>' + inspectionObjective + '<input type="hidden" name="inspectionObjective" value="' + inspectionObjective + '"></td>'
                    newTr += '<td><div class="select"><select class="form-control"  name="preMethod1Result" id="preMethod1Result"><option value="">无</option><option value="+">+</option><option value="-">-</option></select></div></td>'
                    newTr += '<td><div class="select"><select class="form-control"  name="preMethod2Result" id="preMethod2Result"><option value="">无</option><option value="+">+</option><option value="-">-</option></select></div></td>'
                    newTr += '<td><div class="select"><select class="form-control"  name="preMethod3Result" id="preMethod3Result"><option value="">无</option><option value="+">+</option><option value="-">-</option></select></div></td>'
                    newTr += '<td><div class="isRefuse"></div><input type="hidden" name="isRefuse" value=""><input type="hidden" name="isRefuseCode" value="02"></td>'
                    newTr += '<td><div class="isRetain"></div></td>'
                    newTr += '<td>'
                    newTr += '<button type="button" class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑 </button>'
                    newTr += '<button type="button" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除</button>'
                    newTr += '<input type="hidden" name="sampleId" value="">'
                    newTr += '<input type="hidden" name="sampleType" value="' + sampleType + '">'
                    newTr += '<input type="hidden" name="samplePacking" value="' + samplePacking + '">'
                    newTr += '<input type="hidden" name="extractMethod" value="' + extractMethod + '">'
                    newTr += '<input type="hidden" name="sampleCarrier" value="' + sampleCarrier + '">'
                    newTr += '<input type="hidden" name="samplePicture" value="' + samplePicture + '">'
                    newTr += '</td>'
                    newTr += '</tr>'
                    $("#materialEvidencerTbody").append(newTr)
                    $("#materialEvidencerTbody").children().eq(length - 1).find("select[name='extractMethod']").val(extractMethod)
                    $("#materialEvidencerTbody").children().eq(length - 1).find("select[name='sampleCarrier']").val(sampleCarrier)
                }
                $('#materialEvidencerlSampleBox').modal('hide')
            }
        })

        $('#materialEvidencerlSampleBox').on('hidden.bs.modal', function (e) {
            $("#materialEvidencerlSampleBox").find('input[name="sampleNo"]').parents(".col-md-6").addClass("hidden")
            $("#materialEvidencerlSampleBox").find("input[type='text']").val("")
            $("#materialEvidencerlSampleBox").find("input[type='hidden']").val("")
            $("#materialEvidencerlSampleBox").find("select").val("")
            $("#materialEvidencerlSampleBox").find("textarea").val("")
            $("#materialEvidencerlSampleBox").find("select[name='extractMethod']").val("01")
            $("#materialEvidencerlSampleBox").find("select[name='sampleCarrier']").val("01")
            $("#materialEvidencerlSampleBox").find("select[name='samplePacking']").val("01")
            $("#materialEvidencerlSampleBox").find("input[name='inspectionObjective']").val("DNA检验")
            $("#materialEvidencerlSampleBox").find("input[name='extractTime']").val("<%=time%>")

            $("#materialEvidencerlSampleBox").find(".addsamplePhoto").parent().siblings().remove()
            $("#materialEvidencerlSampleBox").find(".modal-body").find(".col-md-12").find("span").addClass("hidden")
            if ($('#materialEvidencerlSampleBox').find(".has-error").length > 0) {
                var form = $("#saveform ")
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
            }
        })
        $('.jianding').on('click', 'li', function () {
            if ($('.jianding li').hasClass('active')) {
                $('#jianding').hide()
            } else {
                $('#jianding').show()
            }
        })
        $(".autographBtn").click(function () {
            if (!$('.jianding li').hasClass('active')) {
                $('#jianding').show()
                $("html, body").animate({
                    scrollTop: $("#delegator2PhoneNumber").offset().top
                }, {duration: 500, easing: "swing"});
                return false
            } else {
                $('#jianding').hide()
            }
            $(".clientSelect").css("display", "block")
            var form = $("#saveform ")
            form.bootstrapValidator({
                live: 'disabled',
                message: 'This value is not valid',
                fields: {
                    areaOrgCode: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    delegator1Id: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    delegator2Id: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    caseName: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    caseLocation: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    caseDatetime: {
                        trigger: 'change',
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    delegator2PhoneNumber: {
                        validators: {
                            regexp: {
                                regexp: /^1[34578]\d{9}$/,
                                message: '电话格式有误'
                            }
                        }
                    },
                    delegator1PhoneNumber: {
                        validators: {
                            regexp: {
                                regexp: /^1[34578]\d{9}$/,
                                message: '电话格式有误'
                            }
                        }
                    },
                    caseProperty: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                    caseBrief: {
                        validators: {
                            notEmpty: {
                                message: "不能为空"
                            }
                        }
                    },
                }
            });
            form.bootstrapValidator('validate');
            scroll(form);
            if (form.data('bootstrapValidator').isValid()) {
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
                $("#autograph").modal("show")
                $(".clientSelect").css("display", "none")
            }
        })
        //获取委托信息
        function getConsignmentInfo() {
            var consignmentInfo = {};

            consignmentInfo.areaOrgCode = $("option:selected", "#areaOrgCode").val();//所属辖区code

            consignmentInfo.delegateOrgCode = $("#delegateOrgCode").val();//委托单位code
            consignmentInfo.delegateOrgName = $("#delegateOrgName").val();//委托单位名称
            consignmentInfo.consignmentNo = $("#consignmentNo").val();//委托编号
            consignmentInfo.appendFlag = $("#appendFlag").val();//补送标识
            //委托人信息1
            consignmentInfo.delegator1Id = $("option:selected", "#delegator1Id").val();//委托人1id
            consignmentInfo.delegator1Name = $("option:selected", "#delegator1Id").text();//委托人1
            consignmentInfo.delegator1Position = $("#delegator1Position").val();//职务
            consignmentInfo.delegator1PaperworkType = $("#delegator1PaperworkType").val();//z证件
            consignmentInfo.delegator1PaperworkNo = $("#delegator1PaperworkNo").val();//证件号
            consignmentInfo.delegator1PhoneNumber = $("#delegator1PhoneNumber").val();//电话
            //委托人信息2
            consignmentInfo.delegator2Id = $("option:selected", "#delegator2Id").val();//委托人2id
            consignmentInfo.delegator2Name = $("option:selected", "#delegator2Id").text();//委托人2
            consignmentInfo.delegator2Position = $("#delegator2Position").val();//职务
            consignmentInfo.delegator2PaperworkType = $("#delegator2PaperworkType").val();//z证件
            consignmentInfo.delegator2PaperworkNo = $("#delegator2PaperworkNo").val();//证件号
            consignmentInfo.delegator2PhoneNumber = $("#delegator2PhoneNumber").val();//电话
            consignmentInfo.autographPicture = $("input[name='signatureBase']").val()
            //取走人
            consignmentInfo.takePerson = $("#takePerson").val();//取走人
            //鉴定要求
            var identifyRequirementArr = []
            $(".btn-checkbox").children(".active").each(function (i, item) {
                identifyRequirementArr.push($(this).attr("value"))
            })
            consignmentInfo.identifyRequirement = identifyRequirementArr.join(",")

            //鉴定类别
            consignmentInfo.identifyType = "DNA鉴定";//DNA鉴定

            consignmentInfo.consignmentId = $("#consignmentId").val();//委托id

            //FOB
            consignmentInfo.preMethod1Result = $("option:selected", "#preMethod1Result").val();//预实验方法1结果(FOB)
            //PSA
            consignmentInfo.preMethod2Result = $("option:selected", "#preMethod2Result").val();//预实验方法2结果(PSA)
            //联苯胺
            consignmentInfo.preMethod3Result = $("option:selected", "#preMethod3Result").val();//预实验方法3结果(联苯胺)

            return consignmentInfo;
        }

        /*
         $("[name='consignmentNo']").blur(function () {
         var consignmentNo = $("[name='consignmentNo']").val();
         $.ajax({
         url: "
        <%=path%>/delegate/testConsignmentNo?consignmentNo=" + consignmentNo,
         type: "post",
         dataType: "json",
         success: function (data) {
         if (data.success == true || data.success == 'true') {
         alert("该委托编号已存在！")
         $("[name='consignmentNo']").val('');
         }
         },
         error: function (e) {
         alert(e);
         }
         });
         });*/
        //获取案件信息
        function getCaseInfoDna() {
            var caseInfoDna = {};

            caseInfoDna.caseName = $("#caseName").val();//案件名称
            caseInfoDna.caseLocation = $("#caseLocation").val();//案发地址
            caseInfoDna.caseDatetime = $("#caseDatetime").val().trim();//案发时间
            caseInfoDna.caseType = $("option:selected", "#caseType").val();//案件类型
            caseInfoDna.caseProperty = $("option:selected", "#caseProperty").val();//案发性质
            caseInfoDna.caseLevel = $("option:selected", "#caseLevel").val();//案发性质
            caseInfoDna.caseBrief = $("#caseBrief").val();//简要案情
            caseInfoDna.caseRemark = $("#caseRemark").val();//其他说明
            caseInfoDna.caseXkNo = '${xkNo}';//现堪编号

            caseInfoDna.caseId = $("#caseId").val();//案件id

            caseInfoDna.majorNo = $("#majorNo").val();//专业
            caseInfoDna.majorType = $("#majorType").val();//专业类型

            //加急  0.加急  1.不加急
            var caseUrgentFlag = $("#caseUrgentFlag")
            if ($("#caseUrgentFlag").is(":checked")) {
                caseInfoDna.caseUrgentFlag = "1";
            } else {
                caseInfoDna.caseUrgentFlag = "0";
            }

            return caseInfoDna;
        }

        //被鉴定人信息
        function getLimsPersonInfo() {
            var limsPersonInfoArr = new Array();
            $("#authenticatorTbody").find("tr").each(function () {
                var that = $(this)
                limsPersonInfo = {};
                limsPersonInfo.personType = $(this).find("input[name='personType']").val()
                limsPersonInfo.personName = $(this).find("input[name='personName']").val()
                limsPersonInfo.personGender = $(this).find("input[name='sex']").val()
                limsPersonInfo.perosnAge = $(this).find("input[name='year']").val()
                limsPersonInfo.personIdCard = $(this).find("input[name='idCard']").val()
                limsPersonInfo.idCardFlag = limsPersonInfo.personIdCard == "" ? 1 : 0
                limsPersonInfo.noIdCardDesc = $(this).find("input[name='noIdCard']").val()
                limsPersonInfo.relationType = $(this).find("input[name='kinship']").val()
                limsPersonInfo.personCurrentAddress = $(this).find("input[name='personCurrentAddress']").val()
                limsPersonInfo.personHeight = $(this).find("input[name='personHeight']").val()
                limsPersonInfo.personWeight = $(this).find("input[name='personWeight']").val()
                limsPersonInfo.personId = $(this).find("input[name='personId']").val()
                limsPersonInfo.personFrontPicture = $(this).find("input[name='personFrontPicture']").val()
                limsPersonInfo.sampleInfoDnaList = []
                var linkId = $(this).find("input[name='personId']").val(),
                        timeId = $(this).find("input[name='timeId']").val();

                $("#personSampleTbody").find("tr").each(function () {
                    var sampleInfoDna = {}
                    if ($(this).find("input[name='linkId']").length > 0 && $(this).find("input[name='linkId']").val() == linkId) {
                        sampleInfoDna.sampleType = $(this).find("input[name='sampleType']").val()
                        sampleInfoDna.sampleName = $(this).find("input[name='sampleName']").val()
                        sampleInfoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val()
                        sampleInfoDna.samplePacking = $(this).find("input[name='samplePacking']").val()
                        sampleInfoDna.extractDatetime = $(this).find("input[name='extractTime']").val().trim();
                        sampleInfoDna.extractMethod = $(this).find("input[name='extractMethod']").val()
                        sampleInfoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val()
                        sampleInfoDna.sampleId = $(this).find("input[name='sampleId']").val()
                        sampleInfoDna.sampleStatus = $(this).find("input[name='isRefuseCode']").val()
                        sampleInfoDna.refuseReason = $(this).find("input[name='isRefuse']").val()
                        sampleInfoDna.sampleDnaPicture = $(this).find("input[name='sampleDnaPicture']").val()
                        sampleInfoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val()
                        sampleInfoDna.sampleDnaPicture = $(this).find("input[name='sampleBase']").val()
                        limsPersonInfo.sampleInfoDnaList.push(sampleInfoDna);
                    }else if ($(this).find("input[name='timeId']").length > 0 && $(this).find("input[name='timeId']").val() == timeId) {
                        sampleInfoDna.sampleType = $(this).find("input[name='sampleType']").val()
                        sampleInfoDna.sampleName = $(this).find("input[name='sampleName']").val()
                        sampleInfoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val()
                        sampleInfoDna.samplePacking = $(this).find("input[name='samplePacking']").val()
                        sampleInfoDna.extractDatetime = $(this).find("input[name='extractTime']").val().trim();
                        sampleInfoDna.extractMethod = $(this).find("input[name='extractMethod']").val()
                        sampleInfoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val()
                        sampleInfoDna.sampleStatus = $(this).find("input[name='isRefuseCode']").val()
                        sampleInfoDna.refuseReason = $(this).find("input[name='isRefuse']").val()
                        sampleInfoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val()
                        sampleInfoDna.addFlag = $(this).find("input[name='addFlag']").val()
                        sampleInfoDna.sampleId = $(this).find("input[name='sampleId']").val()
                        sampleInfoDna.sampleDnaPicture = $(this).find("input[name='sampleBase']").val()
                        limsPersonInfo.sampleInfoDnaList.push(sampleInfoDna);
                    }
                })
                limsPersonInfoArr.push(limsPersonInfo);
            })
            return limsPersonInfoArr;
        }

        //检材信息
        function getSampleInfoDna() {
            var sampleInfoDnaArr = new Array();
            $("#materialEvidencerTbody").find("tr").each(function () {
                var that = $(this)
                samplefoDna = {};
                samplefoDna.sampleType = $(this).find("input[name='sampleType']").val();
                samplefoDna.sampleName = $(this).find("input[name='sampleName']").val();
                samplefoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val();
                samplefoDna.samplePacking = $(this).find("input[name='samplePacking']").val();
                samplefoDna.extractDatetime = $(this).find("input[name='extractTime']").val();
                samplefoDna.extractMethod = $(this).find("input[name='extractMethod']").val();
                samplefoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val();
                samplefoDna.sampleIdwz = $(this).find("input[name='sampleId']").val();
                samplefoDna.evidenceNo = $(this).find("input[name='evidenceNo']").val();
                samplefoDna.preMethod1Result = $(this).find("select[name='preMethod1Result']").val();
                samplefoDna.preMethod2Result = $(this).find("select[name='preMethod2Result']").val();
                samplefoDna.preMethod3Result = $(this).find("select[name='preMethod3Result']").val();
                if ($(this).find(".isRetain").hasClass("active")) {
                    samplefoDna.isRetain = "1"
                } else {
                    samplefoDna.isRetain = "0"
                }
                samplefoDna.sampleStatus = $(this).find("input[name='isRefuseCode']").val()
                samplefoDna.refuseReason = $(this).find("input[name='isRefuse']").val()
                samplefoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val();
                samplefoDna.sampleMaterialPicture = $(this).find("input[name='samplePicture']").val();
                sampleInfoDnaArr.push(samplefoDna);
            })
            return sampleInfoDnaArr;
        }

        //删除被鉴定人信息
        var personIds = "";
        var sampleIds = "";
        $("#authenticatorTbody").on("click", ".remove", function () {
            var personId = $(this).siblings("input[name='personId']").val();
            if (personId != undefined) {
                personIds += "," + personId;
            }
            //$(this).parents("tr").remove()
            if ($(this).parents("tr").find("input[name='personId']").length) {
                $("#personSampleTbody").find("input[name='linkId'][value='" + $(this).parents("tr").find("input[name='personId']").val() + "']").each(function () {
                    if ($(this).parents("tr").find("input[name='sampleId']").val() != "") {
                        sampleIds += "," + $(this).parents("tr").find("input[name='sampleId']").val()
                    }
                    $(this).parents("tr").remove()
                })
            } else {
                $("#personSampleTbody").find("input[name='timeId'][value='" + $(this).parents("tr").find("input[name='timeId']").val() + "']").each(function () {
                    $(this).parents("tr").remove()
                })
            }
            $("#personSampleTbody").find("tr").each(function (i) {
                $(this).children().eq(0).html(i + 1)
            })
            var trNode = $(this).parent().parent();
            $($(this).parent().parent().nextAll("tr")).each(function () {
                var a = $(this).find("td").eq(0).text();
                $(this).find("td").eq(0).text(a - 1);
            });
            trNode.remove();
        });
        //删除样本信息

        $("#personSampleTbody").on("click", ".remove", function () {
            var sampleId = $(this).siblings("input[name='sampleId']").val();
            if (sampleId != undefined) {
                sampleIds += "," + sampleId;
            }
            console.log(sampleIds)
            //$(this).parents("tr").remove()
            var trNode = $(this).parent().parent();
            $($(this).parent().parent().nextAll("tr")).each(function () {
                var a = $(this).find("td").eq(0).text();
                $(this).find("td").eq(0).text(a - 1);
            });
            trNode.remove();
        });
        //删除检材信息
        var sampleIdWzs = "";
        $("#materialEvidencerTbody").on("click", ".remove", function () {
            var sampleIdWz = $(this).siblings("input[name='sampleId']").val();
            if (sampleIdWz != "") {
                sampleIdWzs += "," + sampleIdWz;
            }
            //删除检材信息，序号自动调整
            var trNode = $(this).parent().parent();
            $($(this).parent().parent().nextAll("tr")).each(function () {
                var a = $(this).find("td").eq(0).text();
                var b = a.replace(/(^\s*)|(\s*$)/g, "").length;
                if (b < 5) {
                    $(this).find("td").eq(0).text(a - 1);
                }
            });
            trNode.remove();
        });


        //人员照片单张
        var files;
        $("#photoFile").on("change", function () {
            var t_files = this.files;
            files = t_files;
            var str = new Array("");
            for (var i = 0; i < t_files.length; i++) {
                str.push(t_files[i].name);
            }
            str = str.join(",").substring(1);
            $("#personInfoFileTxt").val(str);
        });

        //检材多张图片上传拼接
        var files;
        $("#samplePhoto").on("change", function () {

            var t_files = this.files;
            files = t_files;
            var str = new Array("");
            for (var i = 0; i < t_files.length; i++) {
                str.push(t_files[i].name);
            }

            str += str.join(",").substring(1);
            $("#sampleInfoFileTxt").val(str);

        });


        //获取委托人1信息
        $("#delegator1Id").change(function () {
            var delegator1Id = $("option:selected", "#delegator1Id").val()
            loadAmPeronsalInfoByDelegator1Id(delegator1Id);
        });

        var loadAmPeronsalInfoByDelegator1Id = function (delegator1Id) {
            var urlStr = "<%=path%>/center/queryAmPersonalInfo?parentId=" + delegator1Id;
            $.ajax({
                type: "get",
                url: urlStr,
                dataType: "json",
                success: function (amPersonalInfo) {
                    if (amPersonalInfo.length != 0) {
                        $("#delegator1PaperworkNo").val(amPersonalInfo.policeNo);
                        $("#delegator1PhoneNumber").val(amPersonalInfo.phoneNumber);
                        $("#delegator1Position").val(amPersonalInfo.position);
                    }
                }
            });
        };
        //获取委托人2信息
        $("#delegator2Id").change(function () {
            var delegator2Id = $("option:selected", "#delegator2Id").val()
            loadAmPeronsalInfoByDelegator1Id1(delegator2Id);
        });

        var loadAmPeronsalInfoByDelegator1Id1 = function (delegator2Id) {
            var urlStr = "<%=path%>/center/queryAmPersonalInfo?parentId=" + delegator2Id;
            $.ajax({
                type: "get",
                url: urlStr,
                dataType: "json",
                success: function (amPersonalInfo) {
                    if (amPersonalInfo.length != 0) {
                        $("#delegator2PaperworkNo").val(amPersonalInfo.policeNo);
                        $("#delegator2PhoneNumber").val(amPersonalInfo.phoneNumber);
                        $("#delegator2Position").val(amPersonalInfo.position);
                    }
                }
            });
        };

        function EditPersonInfoInfoRow(obj) {
            var $curTR = $(obj).parents("tr");
            var personType = $("input[name='personType']", $curTR).val();
            var personName = $("input[name='personName']", $curTR).val();
            var personGender = $("input[name='personGender']", $curTR).val();
            var personIdCard = $("input[name='personIdCard']", $curTR).val();
            var idCardFlag = $("input[name='idCardFlag']", $curTR).val();
            var noIdCardDesc = $("input[name='noIdCardDesc']", $curTR).val();
            var perosnAge = $("input[name='perosnAge']", $curTR).val();
            var personHeight = $("input[name='personHeight']", $curTR).val();
            var personWeight = $("input[name='personWeight']", $curTR).val();
            var personCurrentAddress = $("input[name='personCurrentAddress']", $curTR).val();
            var personFrontPicture = $("input[name='personFrontPicture']", $curTR).val();
            var personFrontPicturePath = $("input[name='personFrontPicturePath']", $curTR).val();

            $("#addAuthenticatorBig").find('select[name="personType"]').val(personType)
            $("#addAuthenticatorBig").find('input[name="personName"]').val(personName)
            $("#addAuthenticatorBig").find("input[name='sex'][value='" + personGender + "']").prop("checked", true)
            $("#addAuthenticatorBig").find('input[name="year"]').val(perosnAge)
            $("#addAuthenticatorBig").find("input[name='idCard']").val(personIdCard)
            $("#addAuthenticatorBig").find('input[name="noIdCard"]').val(noIdCardDesc)
            $("#addAuthenticatorBig").find('input[name="personHeight"]').val(personHeight)
            $("#addAuthenticatorBig").find('input[name="personWeight"]').val(personWeight)
            $("#addAuthenticatorBig").find('input[name="personCurrentAddress"]').val(personCurrentAddress)
            $("#addAuthenticatorBig").modal('show')
        }

        $('#saveBox').on('show.bs.modal', function (e) {
            $('#saveBox').find(".caseName").html($('#caseName').val());
            $('#saveBox').find(".caseBrief").html($('#caseBrief').val());

            var myDate = new Date();//获取系统当前时间
            var month = Number(myDate.getMonth()) + 1
            var times = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
            $('#saveBox').find(".times").html(times)
        })


    })
</script>
</body>

</html>