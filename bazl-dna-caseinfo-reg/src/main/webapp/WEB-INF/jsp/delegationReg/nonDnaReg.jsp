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
    <link href="<%=path %>/js/layui-v2.2.45/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <title>北京市公安局DNA案件委托送检系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>


        select {
            -webkit-appearance: menu;
        }

        .record {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
        }

        .recordl {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0;
        }

        .recordls {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0 4% 0;
        }

        .records {
            width: 100%;
            background: white;
            border-radius: 5px;
            display: flex;
            align-items: center;
            padding: 1% 0;
        }

        .record:first-child {
            margin: 0;
        }

        .recordName {
            color: #3586FA;
            font-weight: bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
            border-bottom: 1px solid #CCCCCC;
        }

        .recordName::before {
            content: " ";
            width: 4px;
            display: block;
            height: 18px;
            background: rgba(53, 134, 250, 1);
            margin-right: 8px;
        }

        .recordNames::before {
            content: " ";
            width: 4px;
            display: block;
            height: 18px;
            background: rgba(53, 134, 250, 1);
            margin-right: 8px;
        }

        .recordName img {
            margin-left: 20px;
        }

        .rowst {
            padding-bottom: 2%;
            border-bottom: 1px solid #CCCCCC;
        }

        .rowsy {
            background: white;
            width: 100%;
            display: flex;
            align-items: center;
        }

        .rowst, .rowst form {
            width: 100%;
            display: flex;
            align-items: center;
        }

        .lay {
            display: flex;
            align-items: center;
            color: #333333;
            font-weight: 400;
            font-size: 14px;
            margin: 0;
            padding: 10px 0;
            padding-left: 2%;
            white-space: nowrap;
        }

        .lays {
            width: 30%;
            display: flex;
            align-items: center;
            color: #333333;
            font-weight: 400;
            font-size: 14px;
            margin: 0;
            padding: 5px 0;
            padding-left: 1%;
            white-space: nowrap;
        }

        .lay input {
            width: 60%;
            margin-left: 10px;
        }

        .lay select {
            width: 60%;
            margin-left: 10px;
        }

        .lay textarea {
            width: 60%;
            margin-left: 10px;
        }

        .lays select {
            width: 80%;
            margin-left: 10px;
        }

        .lays input {
            width: 80%;
            margin-left: 10px;
        }

        .titl {
            display: flex;
            white-space: nowrap;
            align-items: center;
        }

        .titl select {
            width: 80%;
            margin-left: 10px;
        }

        .titl input {
            width: 80%;
            margin-left: 10px;
        }

        label {
            margin: 0;
        }

        .persOne {
            position: absolute;
            bottom: -20px;
            text-align: center;
            background: #E3F1FF;
            color: #0C81F5;
            left: 50%;
            width: 12%;
            padding: 1% 0;
            margin-left: -55px;
        }

        .botts {
            padding-bottom: 4%;
        }

        .recordNames {
            color: #3586FA;
            font-weight: bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
        }

        /*.listBox {*/
        /*flex: 1;*/
        /*display: flex;*/
        /*}*/

        /*.listBox p {*/
        /*width: 10%;*/
        /*text-align: center;*/
        /*border: 1px solid rgba(0, 126, 249, 1);*/
        /*color: rgba(0, 126, 249, 1);*/
        /*line-height: 40px;*/
        /*margin: 0;*/
        /*margin-left: 20px;*/
        /*border-radius: 100px;*/
        /*position: relative;*/
        /*}*/

        .layc {
            width: 100%;
            padding-left: 2%;
            display: flex;
            align-items: center;
            white-space: nowrap;
        }

        .layc textarea {
            margin-left: 10px;
            width: 53%;
        }

        .usert {
            display: flex;
            align-items: center;
        }

        #test2, #test1, #test3, #test4 {
            border: 1px solid rgba(204, 204, 204, 1);
            background: rgba(235, 235, 235, 1);
            color: rgb(0, 0, 0, .45);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100px;
            width: 100px;
            border-radius: 5px;
            margin-left: 10px;
        }

        #demo2 img {
            width: 100%;
            height: auto;
        }

        #demo3 img {
            width: 100%;
            height: auto;
        }

        #demo1 img {
            width: 100%;
            height: auto;
        }

        #demo4 img {
            width: 100%;
            height: auto;
        }

        #demo2, #demo1, #demo3, #demo4 {
            display: flex;
            align-items: center;
        }

        #demo1 div {
            width: 127px;
            margin-left: 10px;
        }

        #demo2 div {
            width: 127px;
            margin-left: 10px;
        }

        #demo3 div {
            width: 127px;
            margin-left: 10px;
        }

        #demo4 div {
            width: 127px;
            margin-left: 10px;
        }

        .adds {
            font-size: 40px;
        }

        .layui-quote-nm {
            border: 0;
        }

        .layui-elem-quote {
            padding: 0;
            padding-left: 10px;
            margin: 0;
        }

        blockquote {
            padding: 0;
            margin: 0 !important;
            width: auto;
            display: flex;
            align-items: center;
        }

        .addt {
            font-size: 12px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            width: 70px;
            background: rgba(12, 129, 245, 1);
            border: 1px solid rgba(12, 129, 245, 1);
            color: white;
            line-height: 30px;
            margin-left: 15px;
            border-radius: 5px;
        }

        .tabs thead {
            background: #BAE0FF;
            font-weight: 700;
            font-size: 14px;
        }

        .tabs th {
            text-align: center;
        }

        .tabs td {
            text-align: center;
            font-size: 12px;
        }

        .las > p {
            padding-top: 10px;
        }

        .backs {
            border: 1px solid rgba(0, 126, 249, 1);
            width: 9%;
            color: #007EF9;
            border-radius: 5px;
            background: white;
            padding: 10px 0;
        }

        .suer {
            background: #0C81F5;
            color: white;
            border: 1px solid #0C81F5;
            border-radius: 5px;
            width: 9%;
            padding: 10px 0;
            margin-right: 10px;
        }

        .pig {
            position: absolute;
            right: -5px;
            top: -10px;
            display: none;
        }

        .moTitle {
            background: #B9E0FF;
            margin: 0;
            color: #1681F5;
            font-size: 16px;
            font-weight: bold;
            text-align: center;
        }

        .modal-dialog {
            width: 48%;
            line-height: 50px;
        }

        .repo {
            width: 40%;
            line-height: 50px;
        }

        .modal-body {
            width: 100%;
            padding: 0;
            display: flex;
            flex-direction: column;
        }

        /*.listBox {*/
        /*flex: 1;*/
        /*display: flex;*/
        /*}*/

        /*.listBox p {*/
        /*width: 10%;*/
        /*text-align: center;*/
        /*border: 1px solid rgba(0, 126, 249, 1);*/
        /*color: rgba(0, 126, 249, 1);*/
        /*line-height: 40px;*/
        /*margin: 0;*/
        /*margin-left: 20px;*/
        /*border-radius: 100px;*/
        /*position: relative;*/
        /*}*/

        .infoName {
            display: flex;
            width: 100%;
        }

        .infoBody {
            flex: 1;
            display: flex;
            border-top: 1px dashed #000000;
            border-bottom: 1px dashed #000000;
        }

        .crew {
            flex: 1;
            border-right: 1px dashed #000000;
            padding: 2% 6%;
        }

        .crews {
            flex: 1;
            padding: 2% 6%;
        }

        .crewss {
            flex: 1;
            padding: 2% 13%;
        }

        .perImg {
            font-size: 14px;
            font-family: Microsoft YaHei;
            color: rgba(0, 0, 0, 1);
            font-weight: 400;
            margin: 0;
        }

        .perImgs {
            font-size: 14px;
            font-family: Microsoft YaHei;
            color: rgba(0, 0, 0, 1);
            font-weight: 400;
            margin: 0;
            width: 25%;
            display: flex;
            justify-content: flex-end;
        }

        .perInp {
            width: 100%;
            display: flex;
            align-items: center;
        }

        .perInpname {
            margin: 0;
            display: flex;
            align-items: center;
        }

        .perInpname1 {
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            width: 25%;
            white-space: nowrap;
        }

        .inpo {
            margin: 0;
            flex: 1;
            padding-left: 20px;
            display: flex;
        }

        .inpo input {
            width: 100%;

        }

        .lett {
            display: inline-block;
            padding: 0 15px;
        }

        .lettd {
            display: inline-block;
            padding: 0 28px;
        }

        .radio-inline {
            margin: 0 !important;
            display: flex;
            align-items: center;
        }

        .inpos {
            margin: 0 !important;
            display: flex;
            align-items: center;
        }

        .inpos label {
            display: flex;
            align-items: center;
            flex: 1;
        }

        .inpos label span {
            padding-left: 20px;
        }

        .radio-inline span {
            padding-left: 30px;
        }

        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio] {
            margin: 0 !important;
        }

        .btns {
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 1% 0;
        }

        .btns button:first-child {
            width: 13%;
            background: #2E83FE;
            color: white;
            height: 40px;
            line-height: 40px;
            border-radius: 3px;
            font-size: 16px;
            border: 1px solid #2E83FE;
            margin-right: 5px;
        }

        .centerInformation {
            background: #f5f5f5;
            padding: 25px;
            position: relative;
        }

        #lyInput {
            width: 100%;
        }

        .centerInformation .caret {
            position: absolute;
            transform: rotate(180deg);
            top: -8px;
            left: 20%;
            margin-left: -8px;
            color: #f5f5f5;
            border-top: 8px dashed;
            border-right: 8px solid transparent;
            border-left: 8px solid transparent;
        }

        .centerInformation p {
            font-weight: 600;
        }

        .centerInformation p span {
            font-weight: 400;
        }

        .centerInformation p:nth-last-child(2) span {
            color: #ff7a74
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
            width: 55px;
            background: #fff;
            cursor: pointer;
        }

        #addAuthenticatorBig .modal-content .modal-body .form-group:nth-child(1) img + button {
            margin: 0 20px;
            margin-right: 5px;
        }

        #addAuthenticatorBig .modal-content .modal-body .form-group:nth-child(1) span,
        #materialEvidencerlSampleBox .modal-content .modal-body .form-group:nth-child(1) span {
            color: #a94442;
            font-size: 11px;
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

        #showPhotoBox {
            background: rgba(0, 0, 0, .5);
        }

        #showPhotoBox > div {
            text-align: center;
            top: 25%;
        }

        .bv-form .small-consignment .help-block {
            position: absolute;
            margin-top: 2%;
            top: -7px;
            /* left: 100%; */
            right: 140px;
            color: #a94442;
        }

        .starBox .form-group {
            display: inline-block;
            width: 300px;
        }

        .starBox small {
            left: 245px !important;
            top: -5px !important;
            right: 0px !important;
        }

        .starBox .form-group input {
            font-weight: 600;
        }

        .clientSelect {
            display: block;
        !important;
            position: absolute;
        }

        #caseName + small {
            top: 28px;
            left: 384px;
        }

        .loading {
            font-size: 100px;
            position: fixed;
            top: 50%;
            left: 50%;
            color: #fff;
            margin-left: -50px;
            margin-top: -50px;
        }

        #nav {
            padding: 0px;

        }

        #nav .breadcrumb {
            margin-bottom: 0px;
            background: #fff;
        }

        #nav .breadcrumb > li + li:before {
            content: ">>";
            color: #ccc;
        }

        #nav .breadcrumb > li a {
            font-size: 13px;
            color: #0a6def;
        }

        #nav .breadcrumb > .active {
            font-size: 13px;
            color: #49535e;
        }

        .record {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
        }

        .recordl {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0;
        }

        .recordls {
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0 4% 0;
        }

        .records {
            width: 100%;
            background: white;
            border-radius: 5px;
            display: flex;
            align-items: center;
            padding: 1% 0;
        }

        .record:first-child {
            margin: 0;
        }

        .recordName {
            color: #3586FA;
            font-weight: bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
            border-bottom: 1px solid #CCCCCC;
        }

        .recordName::before {
            content: " ";
            width: 4px;
            display: block;
            height: 18px;
            background: rgba(53, 134, 250, 1);
            margin-right: 8px;
        }

        .recordNames::before {
            content: " ";
            width: 4px;
            display: block;
            height: 18px;
            background: rgba(53, 134, 250, 1);
            margin-right: 8px;
        }

        .recordNames {
            color: #3586FA;
            font-weight: bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
        }

        .recordName img {
            margin-left: 20px;
        }

        .clearfix:after {
            display: block;
            clear: both;
            content: "";
            visibility: hidden;
            height: 0
        }

        .clearfix {
            zoom: 1
        }

        ul, li {
            list-style: none;
            margin-bottom: 0 !important;
        }

        .qrm-input {
            outline: none;
            border: none;
            height: 36px;

            position: absolute;
            left: 4px;
            top: 0;
            padding: 0 10px;
        }

        .qrm-input-border {
            position: relative;
        }

        .qrm-pinming {
            height: 38px;
            line-height: 40px;
            border: 1px solid #dddddd;
            border-radius: 5px;
            position: relative;
            width: 300px;
            background-image: url("<%=path%>/img/qrm-arrow-down.png");
            background-repeat: no-repeat;
            background-position: 275px;
        }

        .qrm-pinming:hover {
            cursor: pointer;
        }

        .qrm-pinming input:hover {
            cursor: pointer;
        }

        .qrm-pinming-panel {
            position: absolute;
            top: 52px;
            left: -1px;
            z-index: 99999;
            width: 350px;
            height: 210px;
            /*border: 1px solid #409EFF;*/
            background: #ffffff;
        }

        .qrm-border1 {
            float: left;
            width: 33%;
            height: 208px;
            /*overflow-y: scroll;*/
            border-right: 1px solid #f1f1f1;
            box-shadow: 0 0px 5px #f1f1f1;
            position: absolute;
            top: 50px;
            z-index: 10;
            /*margin-left: 5px;*/
            background: white;
        }

        .qrm-border2 {
            float: left;
            width: 33%;
            height: 208px;
            /*overflow-y: scroll;*/
            border-right: 1px solid #f1f1f1;
            box-shadow: 0 0px 5px #f1f1f1;
            position: absolute;
            top: 50px;
            left: 100px;
            background: white;
            /*margin-left: 5px;*/
            z-index: 10;
        }

        .qrm-border3 {
            float: left;
            width: 33%;
            height: 208px;
            /*overflow-y: scroll;*/
            border-right: 1px solid #f1f1f1;
            box-shadow: 0 0px 5px #f1f1f1;
            position: absolute;
            top: 50px;
            background: white;
            left: 200px;
            z-index: 10;
            /*margin-left: 5px;*/
        }

        .qrm-lev {
            float: left;
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 12px;
        }

        .qrm-lev > li {
            width: 100%;
            position: relative;
            display: block;
            padding-left: 10px;
            height: 35px;
            line-height: 35px;
        }

        .qrm-lev > li:hover {
            background: #F5F7FA;
            /*color: #409EFF;*/
        }

        .qrm-arrow-right {
            display: inline-block;
            width: 4px;
            height: 7px;
            background: url("<%=path%>/img/qrm-arrow-right.png") no-repeat;
            position: absolute;
            right: 7px;
            top: 14px;
        }

        .qrm-lev-1 > li.active {
            background: #F5F7FA;
            color: #409EFF;

        }

        .qrm-lev-2 > li.active {
            background: #F5F7FA;
            color: #409EFF;

        }

        .qrm-lev-3 > li.active {
            background: #F5F7FA;
            color: #409EFF;

        }

        .qrm-lev-4 > li.active {
            background: #F5F7FA;
            color: #409EFF;
        }

        .box {
            margin-left: 10px;
        }

        .bv-form .has-error .help-block {
            position: absolute;
            top: 2px;
            left: 250px;
        }

        .sext {
            display: flex;
        }

        .sex::before {
            top: -10px;
        }
        .actList{
            display: flex;
            align-items: center;
        }
        .actList li{
            height: 40px;
            display: flex !important;
            align-items: center;
        }
    </style>
</head>
<!-- 委托人添加 -->
<div class="modal fade popBox xsBox" id="addClientBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">委托人信息</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>委托人姓名</label>
                    <input type="text" class="form-control" placeholder="请输入委托人姓名" name="clientName">
                </div>
                <div class="form-group">
                    <label>职务</label>
                    <input type="text" class="form-control" placeholder="请输入职务" name="clientDuties">
                </div>
                <div class="form-group">
                    <label>联系方式</label>
                    <input type="text" class="form-control" placeholder="请输入职务" name="clientPhone">
                </div>
                <div class="form-group">
                    <label>证件类型</label>
                    <div class="select">
                        <select class="form-control" required name="clientIdType">
                            <option value="" disabled selected hidden>请选择证件类型</option>
                            <option value="警官证">警官证</option>
                            <option value="身份证">身份证</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label>证件号</label>
                    <input type="text" class="form-control" placeholder="请输入证件号" name="clientId">
                </div>
            </div>
            <div class="modal-footer clearfix" style="padding-bottom: 20px">
                <input type="hidden" name="index">
                <button class="btn btn-lang btn-blue pull-left addClient">确认</button>
                <button type="button" class="btn btn-lang pull-right btn-blue-border" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!--添加人员样本-->
<div class="modal fade" id="addAuthenticatorBig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="clearfix">
        <form action="">
            <div class="modal-content personBox">
                <div class="modal-header">
                    <h4 class="modal-title">案件人员信息</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <img class="personFrontPicture" src="<%=path%>/img/policeman.png" alt=""/>
                        <button class="btn btn-gray btn-sm addphoto" type="button">添加照片</button>
                        <span class="hidden">上传图片过大，请上传小于500Kb的图片</span>
                        <input type="file" id="photoFile" name="photoFile" class="photoFile" accept="image/*"/>
                        <input type="hidden" id="personBase" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>身份证号</label>
                        <div class="row">
                            <div class="col-md-7 nopadding idBox">
                                <input type="text" class="form-control" placeholder="请输入身份证号"
                                       name="idCard" id="idCard">
                                <small class="help-block hidden">身份证输入有误
                                </small>
                            </div>
                            <div class="col-md-2 nopadding">
                                <label class="custom-control checkbox-inline">
                                    <input class="custom-control-input" type="checkbox"
                                           name="noIdCheck">
                                    <span class="custom-control-label">无</span>
                                </label>
                            </div>
                            <div class="col-md-3 nopadding" style="text-align: right;">
                                <button class="btn btn-gray btn-sm" type="button" id="verification">
                                    核验
                                </button>
                            </div>
                        </div>
                        <input type="text" class="form-control hidden" placeholder="请输入无身份证号原因"
                               style="margin-top: 5px;"
                               name="noIdCard">
                    </div>
                    <div class="form-group">
                        <label>人员类型</label>
                        <div class="select">
                            <select class="form-control" required name="personType" id="personType">
                                <option value="" disabled selected hidden>请选择人员类型</option>
                                <c:forEach items="${personTypeList}" var="list">
                                    <option value="${list.dictCode}">${list.dictName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>人员名称</label>
                        <input type="text" class="form-control" placeholder="请输入人员名称"
                               name="personName"
                               id="personName">
                    </div>
                    <div class="form-group sext">
                        <label>性别</label>
                        <label class="radio-inline radio-sex">
                            <input type="radio" class="sex sexman" value="01" name="sex" checked><span>男性</span>
                        </label>
                        <label class="radio-inline  radio-sex">
                            <input type="radio" class="sex sexwoman" value="02" name="sex"><span>女性</span>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>年龄</label>
                        <input type="text" class="form-control" placeholder="请输入年龄" name="year"
                               id="year">
                    </div>
                    <%--<div class="form-group hidden">
                        <label>亲缘关系</label>

                        <div class="select">
                            <select class="form-control" required name="kinship">
                                <option value="" disabled selected hidden>请选择亲缘关系</option>
                                <c:forEach items="${relationTypeList}" var="list">
                                    <option value="${list.dictCode}">${list.dictName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label>亲缘身份</label>
                        <div class="select">
                            <select class="form-control" required name="relationType" id="relationType">
                                <option value="" disabled selected hidden>请选择人员类型</option>
                                <c:forEach items="${relationTypeList}" var="list">
                                    <option value="${list.dictCode}">${list.dictName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <%--<div class="perInp">
                        <p class="perInpname">亲缘身份</p>
                        <div class="select">
                            <select class="form-control inpTypes" required name="relationType" id="relationType">
                                <option value="" disabled selected hidden>请选择身份</option>
                                <c:forEach items="${relationTypeList}" var="list">
                                    <option value="${list.dictCode}">${list.dictName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label>是否为事主</label>
                        <label class="custom-control checkbox-inline">
                            <input class="custom-control-input" type="checkbox" name="inlineCheckbox1"  id="inlineCheckbox1">
                            <span class="custom-control-label">是</span>
                        </label>
                    </div>
                    <div class="form-group ">
                        <label style="color: #ff7570">是否同时送检人员样本信息</label>
                        <label class="custom-control checkbox-inline">
                            <input class="custom-control-input" type="checkbox" name="moveCheckbox">
                            <span class="custom-control-label">是</span>
                        </label>
                    </div>
                    <div class="form-group moveInput hidden">
                        <label>住址</label>
                        <input type="text" class="form-control" placeholder="请输入住址" id="personCurrentAddress"
                               name="personCurrentAddress">
                    </div>
                    <div class="form-group moveInput hidden">
                        <label>身高</label>
                        <input type="text" class="form-control" placeholder="请输入身高"
                               name="personHeight">
                    </div>
                    <div class="form-group moveInput hidden">
                        <label>体重</label>
                        <input type="text" class="form-control" placeholder="请输入体重"
                               name="personWeight">
                    </div>
                </div>
                <div class="modal-footer">
                    <div>
                        <button class="btn btn-yellow btn-sm moveBtn" type="button">添加更多</button>
                    </div>
                    <input type="hidden" name="index"/>
                    <input type="hidden" name="addflag" value="0"/>
                    <button type="button" class="btn btn-blue btn-lang addAuthenticator" name="qrinfoButton">确认</button>
                    <button type="button" class="btn btn-blue-border  btn-lang"
                            data-dismiss="modal">取消
                    </button>
                </div>
            </div>
            <div class="modal-content sampleBox">
                <div class="modal-header">
                    <h4 class="modal-title">样本信息</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 hidden">
                            <div class="form-group">
                                <label>关联人员</label>
                                <div class="select">
                                    <select class="form-control" required name="relationPersonName">

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>样本类型</label>
                                <div class="select">
                                    <select class="form-control" required name="sampleType"
                                            id="sampleType">
                                        <option value="" disabled selected hidden>请选择样本类型</option>
                                        <c:forEach items="${sampleTypeList}" var="sampleList">
                                            <option value="${sampleList.dictCode}">${sampleList.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>样本名称</label>
                                <input type="text" class="form-control" placeholder="请输入样本名称"
                                       name="sampleName">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>包装</label>

                                <div class="select">
                                    <select class="form-control" required name="samplePacking">
                                        <c:forEach items="${packMethodList}" var="packMethod">
                                            <option value="${packMethod.dictCode}">${packMethod.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>提取时间</label>
                                <input type="text" class="form-control form_datetime" id="extract1Time"
                                       placeholder="请输入提取时间" name="extractTime" readonly>
                            </div>
                        </div>
                        <div class="col-md-3" style="padding-right: 2px;">
                            <div class="form-group">
                                <label>提取方法</label>
                                <div class="select">
                                    <select class="form-control" required name="extractMethod">
                                        <c:forEach items="${extractMethodList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3" style=" margin-top: 20px;padding-left: 2px;">
                            <div class="form-group">
                                <div class="select">
                                    <select class="form-control" required name="sampleCarrier">
                                        <c:forEach items="${sampleCarrierList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>送检目的</label>
                                <input class="form-control" placeholder="请输入送检目的"
                                       name="inspectionObjective" value="DNA检验">
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>样本描述</label>
                                                <textarea class="form-control" rows="3" placeholder="请输入样本描述"
                                                          name="sampleDescribe"></textarea>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>添加照片</label>
                                <span class="hidden">上传图片过大，请上传小于500Kb的图片</span>
                                <div class="row samplePhotobox">
                                    <div class="col-md-2">
                                        <button class="btn addsamplePhoto" type="button">
                                            <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                        </button>
                                        <input type="file" name="samplePhoto" id="samplePhoto" class="samplePhoto"
                                               accept="image/*"/>
                                        <input type="hidden" id="sampleBase" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer hidden">
                    <input type="hidden" name="index"/>
                    <button type="button" class="btn btn-blue btn-lang addSampleBtn" name="addSampButton">确认</button>
                    <button type="button" class="btn btn-blue-border  btn-lang" data-dismiss="modal">
                        取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 检材信息 -->
<div class="modal fade popBox bigBox" id="materialEvidencerlSampleBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="">
                <div class="modal-header">
                    <h4 class="modal-title">物证检材信息</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>检材类型</label>
                                <div class="select">
                                    <select class="form-control" required name="sampleType">
                                        <option value="" disabled selected hidden>请选择检材类型</option>
                                        <c:forEach items="${sampleTypeList}" var="sampleList">
                                            <option value="${sampleList.dictCode}">${sampleList.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>检材名称</label>
                                <input type="text" class="form-control" placeholder="请输入检材名称" name="sampleName">
                            </div>
                        </div>
                        <%--<div class="col-md-6">

                            <div class="form-group">
                                <label>物证编号</label>
                                <input type="text" class="form-control" placeholder="请输入编号" name="evidenceNo">
                            </div>
                        </div>--%>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>包装方法</label>
                                <div class="select">
                                    <select name="samplePacking" id="samplePacking" class="form-control" required>
                                        <option value="">请选包装方法</option>
                                        <c:forEach items="${packMethodList}" var="packMethod">
                                            <option value="${packMethod.dictCode}">${packMethod.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>提取时间</label>
                                <input type="text" class="form-control form_datetime" placeholder="请输入提取时间"
                                       id="extract2Time"
                                       name="extractTime" readonly>
                            </div>
                        </div>
                        <div class="col-md-3" style="padding-right: 2px;">
                            <div class="form-group">
                                <label>提取方法</label>
                            </div>
                            <div class="select">
                                <select class="form-control" required name="extractMethod">
                                    <c:forEach items="${extractMethodList}" var="list">
                                        <option value="${list.dictCode}"
                                                <c:if test="${list.dictCode eq sampleInfo.extractMethod}">selected</c:if>>${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3" style=" margin-top: 50px;padding-left: 2px;">
                            <div class="form-group">
                                <div class="select">
                                    <select class="form-control" required name="sampleCarrier">
                                        <c:forEach items="${sampleCarrierList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>送检目的</label>
                                <input class="form-control" placeholder="请输入送检目的" name="inspectionObjective">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>是否中心现场提取</label>
                                <div class="select">
                                    <select name="coreTakenStats" id="coreTakenStats" name="coreTakenStats"  class="form-control"required>
                                        <option value="" disabled selected>请选择</option>
                                        <option value="1">是</option>
                                        <option value="0" >否</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>检材描述</label>
                            <textarea class="form-control" rows="3" placeholder="请输入检材描述"
                                      name="sampleDescribe"></textarea>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>添加照片</label>
                                <span class="hidden">上传图片过大，请上传小于500Kb的图片</span>
                                <div class="row samplePhotobox">
                                    <div class="col-md-2">
                                        <button class="btn addsamplePhoto" type="button">
                                            <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                        </button>
                                        <input type="file" name="samplePhoto" class="samplePhoto hidden"
                                               accept="image/*">
                                        <input type="hidden" class="form-control">
                                        <input type="hidden" id="samplePicture" class="form-control" value="">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <input type="hidden" name="index">
                    <input type="hidden" name="evidenceNo">
                    <input type="hidden" name="parentIndex">
                    <button class="btn btn-lang  btn-blue addMaterialEvidencerlSample" type="button"
                            name="wzSampleButton">确认
                    </button>
                    <button type="button" class="btn btn-lang btn-blue-border" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 保存弹窗 -->
<div class="modal fade popBox saveBox" id="saveBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">确认鉴定中心</h4>
            </div>
            <div class="modal-body">
                <div class="row inputBox">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>案件名称 :</label>
                            <label class="caseName"></label>
                        </div>
                    </div>
                </div>
                <div class="row inputBox">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>案件基本情况 :</label>
                            <label class="caseBrief"></label>
                        </div>
                    </div>
                </div>
                <div class="row inputBox">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>委托时间 :</label>
                            <label class="times"></label>
                        </div>
                    </div>
                </div>
                <div class="row inputBox">
                    <div class="col-md-12">
                        <div class="form-group clearfix">
                            <label class="col-md-2 nopadding">鉴定中心:</label>
                            <div class="col-md-10 nopadding">
                                <div class="form-group" style="margin-top:0px;">
                                    <ul class="btn-checkbox clearfix btn-checkbox-yellow orgname actList" style="display: flex">
                                        <c:forEach items="${orgInfos}" var="org" varStatus="o">
                                            <li
                                                    <c:if test="${checkedOrgId == org.orgId || checkedOrgId == 'undefined'}">class="active"</c:if>>${org.orgQualification}
                                                <input type="hidden" name="orgname" value="${org.orgQualification}">
                                                <input type="radio" name="orgQualification">
                                                <input type="hidden" name="orgAddressName"
                                                       value="${org.orgAddress}"/>
                                                <input type="hidden" name="orgContactPhoneName"
                                                       value="${org.orgContactPhone}"/>
                                                <input type="hidden" name="orgCodeName" value="${org.orgId}">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-12">
                            </div>
                        </div>
                        <div class="centerInformation">
                            <span class="caret"></span>
                            <div>
                                <%--显示地址和联系方式--%>
                                <c:if test="${not empty orgInfo.orgQualification}">
                                    <p>中心地址 :
                                        <span class="orgAddressSpanId">${orgInfo.orgAddress}</span></p>
                                    <p><br>联系方式 :
                                        <span class="orgContactPhoneSpanId">${orgInfo.orgContactPhone}</span></p>
                                </c:if>
                                <c:if test="${empty orgInfo.orgQualification}">
                                    <p>中心地址 :
                                        <span>${forensicCenterorg.orgAddress}</span></p>
                                    <p><br>联系方式 :
                                        <span>${forensicCenterorg.orgContactPhone}</span></p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer clearfix">
                <input type="hidden" name="" value="">
                <button type="button" id="save" name="saveInfo" class="btn btn-lang  btn-blue modifySiteSurve"
                        data-dismiss="modal">确认
                </button>
                <button type="button" class="btn btn-lang  btn-blue-border" data-dismiss="modal">取消</button>
            </div>
            <div style="display: none"></div>
        </div>
    </div>
</div>
</div>
<%--照片展示--%>
<div class="modal fade" id="showPhotoBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <img/>
    </div>
</div>
<body>
<form id="saveform" enctype="multipart/form-data" method="post" class="bv-form">
    <div class="box">
        <div class="record">
            <div class="recordName">
                <span style="font-size: 13px;">委托信息</span>
                <span><img src="<%=path%>/img/fan.png" alt=""></span>
                <div class="form-group lays">
                    <label>委托单位 :</label>
                    <input type="hidden" id="consignmentId" value="${consignatioInfo.consignmentId}">
                    <input type="hidden" id="delegateOrgCode" name="delegateOrgCode"
                           value="${subOrgInfo.orgCode}">
                    <input type="hidden" id="delegateOrgName" name="delegateOrgName"
                           value="${subOrgInfo.orgName}">
                    <label>${subOrgInfo.orgName}</label>
                </div>
                <div class="form-group lays">
                    <label class="font-weight: 400">所属辖区 :</label>
                    <div class="col-sm-9" style="margin-top: -7px;">
                        <div class="select">
                            <select class="form-control clientSelect" name="areaOrgCode" id="areaOrgCode">
                                <option value="">请选择所属辖区</option>
                                <c:forEach items="${orgInfoList}" var="list" varStatus="s">
                                    <option value="${list.orgCode}"
                                            <c:if test="${consignatioInfo.areaOrgCode eq list.orgCode}">selected</c:if>>${list.orgName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group lays">
                    <label>委托书编号</label>
                    <input type="text" class="form-control" id="consignmentNo" name="consignmentNo"
                           value="${consignatioInfo.consignmentNo}" placeholder="请输入编号">
                </div>
            </div>
            <div class="rowst">
                <div class="col-md-12" style="margin-top: 60px;">
                    <div class="col-md-6">
                        <div class="col-md-12 botts personCard">
                            <img src="<%=path%>/img/touxiang1.png" alt="">
                            <p class="persOne">委托人(一)</p>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group titl">
                                        <label>姓名</label>
                                        <i class="required">*</i>
                                        <div class="select">
                                            <select class="form-control clientSelect" name="delegator1Id"
                                                    id="delegator1Id">
                                                <option value="">请选委托人</option>
                                                <c:forEach items="${personalInfoList}" var="list" varStatus="s">
                                                    <option value="${list.personalId}"
                                                            <c:if test="${consignatioInfo.delegator1Id eq list.personalId}">selected</c:if>>${list.fullName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>职务</label>
                                        <div class="select">
                                            <select class="form-control" name="delegator1Position"
                                                    id="delegator1Position">
                                                <option value="">请选择职务</option>
                                                <c:forEach items="${positionList}" var="list" varStatus="s">
                                                    <option value="${list.dictCode}"
                                                            <c:if test="${consignatioInfo.delegator1Position eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>证件</label>
                                        <input name="delegator1PaperworkType" id="delegator1PaperworkType"
                                               type="text" class="form-control" name="clientIdType"
                                               value="警官证">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group titl">
                                        <label>证件号</label>
                                        <input name="delegator1PaperworkNo" id="delegator1PaperworkNo"
                                               type="text" class="form-control" name="clientId"
                                               value="${consignatioInfo.delegator1PaperworkNo}">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>电话</label>
                                        <input name="delegator1PhoneNumber" id="delegator1PhoneNumber"
                                               type="text" class="form-control" name="clientPhone"
                                               value="${consignatioInfo.delegator1PhoneNumber}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="col-md-12 botts personCard">
                            <img src="<%=path%>/img/touxiang1.png" alt="">
                            <p class="persOne">委托人(二)</p>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>姓名</label>
                                        <i class="required">*</i>
                                        <div class="select">
                                            <select class="form-control clientSelect" name="delegator2Id"
                                                    id="delegator2Id">
                                                <option value="">请选委托人</option>
                                                <c:forEach items="${personalInfoList}" var="list" varStatus="s">
                                                    <option value="${list.personalId}"
                                                            <c:if test="${consignatioInfo.delegator2Id eq list.personalId}">selected</c:if>>${list.fullName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>职务</label>
                                        <div class="select">
                                            <select class="form-control" name="delegator2Position"
                                                    id="delegator2Position">
                                                <option value="">请选择职务</option>
                                                <c:forEach items="${positionList}" var="list" varStatus="s">
                                                    <option value="${list.dictCode}"
                                                            <c:if test="${consignatioInfo.delegator2Position eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>证件</label>
                                        <input name="delegator2PaperworkType" id="delegator2PaperworkType"
                                               type="text" class="form-control" name="clientIdType"
                                               value="警官证">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>证件号</label>
                                        <input name="delegator2PaperworkNo" id="delegator2PaperworkNo"
                                               type="text" class="form-control" name="clientId"
                                               value="${consignatioInfo.delegator2PaperworkNo}">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>电话</label>
                                        <input name="delegator2PhoneNumber" id="delegator2PhoneNumber"
                                               type="text" class="form-control" name="clientPhone"
                                               value="${consignatioInfo.delegator2PhoneNumber}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="records">
        <div class="recordNames">
            <span>鉴定要求</span>
            <button style="margin-left: 20px" class="btn btn-blue checkboxAll" type="button">全选</button>
        </div>
        <div class="panel-body">
            <div class="row" style="display: flex;align-items: center">
                <div class="col-md-12">
                    <div class="form-group" style="margin-bottom: 0">
                        <ul class="btn-checkbox clearfix">
                            <li class="pull-left " value="01">同一鉴定
                                <input type="checkbox" name="identifyRequirement" checked img class="pig"
                                       src="<%=path%>/img/dui.png" alt="">
                            </li>
                            <li class="pull-left" value="02">亲缘鉴定
                                <input type="checkbox" name="identifyRequirement" img class="pig"
                                       src="<%=path%>/img/dui.png" alt="">
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <%--<div class="listBox">--%>
        <%--<p class="lis">同一认定 <img class="pig" src="<%=path%>/img/dui.png" alt=""></p>--%>
        <%--<p class="lis">亲缘鉴定 <img class="pig" src="<%=path%>/img/dui.png" alt=""></p>--%>
        <%--</div>--%>
    </div>
    <div class="row Modular">
        <div class="col-md-12">
            <div class="record">
                <p class="recordName">案件现场信息</p>
                <div class="rowsy">
                    <div class="form-group lay col-md-4">
                        <label style="font-weight: 400">案件名称</label>
                        <input type="text" name="caseName" id="caseName" value="${limsCaseInfo.caseName}"
                               class="form-control" placeholder="请输入案件名称">
                    </div>
                    <div class="form-group lay col-md-4">
                        <label style="font-weight: 400">案件性质</label>
                        <select class="form-control" name="caseProperty" id="caseProperty">
                            <option value="" disabled selected hidden>请选择案发性质</option>
                            <c:forEach items="${casePropertyListLv1}" var="list" varStatus="s">
                                <option value="${list.dictCode}"
                                        <c:if test="${limsCaseInfo.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}
                                </option>
                            </c:forEach>
                        </select>
                        <select class="form-control" name="casePropertyLv2" id="casePropertyLv2">
                            <option value="" disabled selected hidden>请选择案发性质</option>
                            <c:forEach items="${casePropertyListLv2}" var="list" varStatus="s">
                                <option value="${list.dictCode}"
                                        <c:if test="${limsCaseInfo.casePropertylv2 eq list.dictCode}">selected</c:if>>${list.dictName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group lay col-md-4 ddd">
                        <label style="font-weight: 400">案发辖区</label>
                        <input type="text" name="caseLocation" id="caseLocation"
                               value="${limsCaseInfo.caseLocation}" class="form-control" placeholder="请输入案发地点">
                    </div>
                </div>
                <div class="rowsy">
                    <div class="form-group lay col-md-4">
                        <label style="font-weight: 400">案发地点</label>
                        <input type="text" name="caseLocation" id="caseLocation2"
                               value="${limsCaseInfo.caseLocation}" class="form-control" placeholder="请输入案发地点">
                    </div>
                    <div class="form-group lay col-md-4">
                        <label style="font-weight: 400">案发时间</label>
                        <i class="required">*</i>
                        <input type="text" name="caseDatetime" id="caseDatetime"
                               value="<fmt:formatDate value='${limsCaseInfo.caseDatetime}' pattern='yyyy-MM-dd '/>"
                               class="form-control form_datetime" placeholder="请输入案发时间">
                    </div>
                    <div class="form-group lay col-md-4">
                        <label style="font-weight: 400">备注说明</label>
                          <textarea name="caseRemark" id="caseRemark" class="form-control" rows="1"
                                    placeholder="请输入其他说明">${limsCaseInfo.caseRemark}</textarea>
                    </div>
                </div>
                <div class="rowsy">
                    <div class="form-group layc">
                        <label style="font-weight: 400">简要案情</label>
                        <i class="required">*</i>
                                <textarea name="caseBrief" id="caseBrief" value="${limsCaseInfo.caseBrief}"
                                          class="form-control" rows="3"
                                          placeholder="请输入简要案情">${limsCaseInfo.caseBrief}</textarea>
                    </div>
                </div>
                <div class="rowsy">
                    <div class="form-group layc">
                        <label style="font-weight: 400">现场照片</label>
                        <div class="layui-upload usert">
                            <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                <div class="layui-upload-list" id="demo1"></div>
                            </blockquote>
                            <div id="test1"><span class="adds">+</span><span>添加图片</span></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--<div class="row Modular">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading green">
                    <div>被鉴定人信息</div>
                    <button class="btn btn-blue" id="openAddAuthenticatorBig2" type="button">
                        添加
                    </button>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>人员类型</th>
                            <th>人员名称</th>
                            <th>性别</th>
                            <th>年龄</th>
                            <th>身份证号</th>
                            <th>亲缘关系</th>
                            <c:if test="${consignatioInfo.status != '02'&&consignatioInfo.status != '03'}">
                                <th>操作</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody id="authenticatorTbody">
                        <c:forEach items="${limsPersonInfoList}" var="limsPersonInfo" varStatus="s">
                            <tr>
                                <td>${s.count}</td>
                                <td>${limsPersonInfo.personTypeName}
                                    <input type="hidden" name="personTypeName" value="${limsPersonInfo.personTypeName}">
                                </td>
                                <td>${limsPersonInfo.personName}<input type="hidden" name="personName"
                                                                       value="${limsPersonInfo.personName}"></td>
                                <td>${limsPersonInfo.personGenderName}<input type="hidden" name="sexName"
                                                                             value="${limsPersonInfo.personGenderName}">
                                </td>
                                <td>${limsPersonInfo.perosnAge}<input type="hidden" name="year"
                                                                      value="${limsPersonInfo.perosnAge}"></td>
                                <td><c:if test="${not empty limsPersonInfo.personIdCard}">
                                    ${limsPersonInfo.personIdCard}
                                </c:if>
                                    <c:if test="${empty limsPersonInfo.personIdCard}">
                                        无身份证号(${limsPersonInfo.noIdCardDesc})
                                    </c:if><input type="hidden" name="idCard"
                                                  value="${limsPersonInfo.personIdCard}">
                                    <input type="hidden" name="noIdCard"
                                           value="${limsPersonInfo.noIdCardDesc}">
                                </td>
                                <td>${limsPersonInfo.relationTypeName}<input type="hidden" name="kinshipName"
                                                                             value="${limsPersonInfo.relationTypeName}">
                                </td>
                                <c:if test="${consignatioInfo.status != '02'&&consignatioInfo.status != '03'}">
                                    <td>
                                        <button type="button" name="editBtn"
                                                class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                        </button>
                                        <button type="button" name="delBtn"
                                                class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                        </button>
                                        <input type="hidden" name="personId" value="${limsPersonInfo.personId}">
                                        <input type="hidden" name="idCardFlag" value="${limsPersonInfo.idCardFlag}">
                                        <input type="hidden" name="sex" value="${limsPersonInfo.personGender}">
                                        <input type="hidden" name="noIdCardDesc" value="${limsPersonInfo.noIdCardDesc}">
                                        <input type="hidden" name="personHeight" value="${limsPersonInfo.personHeight}">
                                        <input type="hidden" name="personWeight" value="${limsPersonInfo.personWeight}">
                                        <input type="hidden" name="personCurrentAddress"
                                               value="${limsPersonInfo.personCurrentAddress}">
                                        <input type="hidden" name="personFrontPicturePath"
                                               value="${limsPersonInfo.personFrontPicturePath}">
                                        <input type="hidden" name="personBase"
                                               value="${limsPersonInfo.personFrontPicture}"/>
                                        <input type="hidden" name="personType" value="${limsPersonInfo.personType}">
                                        <input type="hidden" name="kinship" value="${limsPersonInfo.relationType}">
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>--%>
    <div class="row Modular">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>案件人员信息</div>
                    <button class="btn btn-blue" id="openAddAuthenticatorBig" type="button">添加</button>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>人员类型</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>身份证号</th>
                            <th>亲缘身份</th>
                            <th>是否为事主</th>
                            <th>检材类型</th>
                            <th>检材名称</th>
                            <th>提取时间</th>
                            <th>包装方法</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="personSampleTbody">
                        <c:forEach items="${limsPersonInfoList}" var="limsPersonInfo" varStatus="s">
                            <tr>
                                <td>${s.count}</td>
                                <td>${limsPersonInfo.personTypeName}
                                    <input type="hidden" name="personTypeName" value="${limsPersonInfo.personTypeName}">
                                    <input type="hidden" name="personType" value="${limsPersonInfo.personType}">
                                </td>
                                <td>${limsPersonInfo.personName}<input type="hidden" name="personName"
                                                                       value="${limsPersonInfo.personName}"></td>
                                <td>${limsPersonInfo.personGenderName}<input type="hidden" name="sexName"
                                                                             value="${limsPersonInfo.personGenderName}">
                                </td>
                                <td><c:if test="${not empty limsPersonInfo.personIdCard}">
                                    ${limsPersonInfo.personIdCard}
                                </c:if>
                                    <c:if test="${empty limsPersonInfo.personIdCard}">
                                        无身份证号(${limsPersonInfo.noIdCardDesc})
                                    </c:if><input type="hidden" name="idCard"
                                                  value="${limsPersonInfo.personIdCard}">
                                    <input type="hidden" name="noIdCard"
                                           value="${limsPersonInfo.noIdCardDesc}">
                                </td>
                                <td>${limsPersonInfo.relationTypeName}<input type="hidden" name="kinshipName"
                                                                             value="${limsPersonInfo.relationTypeName}">
                                </td>
                                <td>
                                    <div class="victimSelect">
                                        <select class="form-control" name="coreVictimStats" id="coreVictimStats"
                                                required>
                                            <option value="1"
                                                    <c:if test="${'1' eq limsPersonInfo.coreVictimStats}">selected</c:if>>
                                                是
                                            </option>
                                            <option value="0"
                                                    <c:if test="${'0' eq limsPersonInfo.coreVictimStats || limsPersonInfo.coreVictimStats == null }">selected</c:if>>
                                                否
                                            </option>
                                        </select>
                                        <c:if test="${'1' eq limsPersonInfo.coreVictimStats}">
                                            <input type="hidden" name="inlineCheckbox1" value="on">
                                        </c:if>
                                    </div>
                                </td>
                                <td>${limsPersonInfo.sampleTypeName}
                                    <input type="hidden" name="sampleTypeName" value="${limsPersonInfo.sampleTypeName}">
                                </td>
                                <td>${limsPersonInfo.sampleName}
                                    <input type="hidden" name="sampleName" value="${limsPersonInfo.sampleName}">
                                </td>
                                <td><fmt:formatDate value="${limsPersonInfo.extractDatetime}" pattern="yyyy-MM-dd"/>
                                    <input type="hidden" name="extractTime"
                                           value="<fmt:formatDate value="${limsPersonInfo.extractDatetime}" pattern="yyyy-MM-dd" />">
                                </td>
                                <td>${limsPersonInfo.samplePackingName}<input type="hidden" name="samplePackingName"
                                                                              value="${limsPersonInfo.samplePackingName}">
                                </td>
                                <td class="las">
                                    <button type="button" name="editBtn"
                                            class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                    </button>
                                    <button type="button" name="delBtn"
                                            class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row Modular">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading blue">
                    <div>物证检材信息</div>
                    <button class="btn btn-blue addMaterialEvidencer" type="button">添加</button>
                </div>
                <div class="panel-body nopadding">
                    <table class="table table-hover table-bordered bigTable">
                        <thead>
                        <tr>
                            <th>序号</th>
                           <%-- <th style="width: 300px;">物证编号</th>--%>
                            <th>物证（检材）名称</th>
                            <th>检材类型</th>

                            <%--<th style="width: 250px;">检材描述</th>--%>
                            <th>包装方法</th>
                            <th>提取时间</th>
                            <th style="width: 180px;">是否中心提取<i class="required">*</i></th>
                            <%--<th style="width: 215px;">提取方法</th>--%>
                            <%--<th>送检目的</th>--%>
                            <c:if test="${consignatioInfo.status != '02'&&consignatioInfo.status != '03'}">
                                <th style="width: 150px">操作</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody id="materialEvidencerTbody">
                        <c:forEach items="${limsSampleInfoList}" var="sampleInfo" varStatus="s">
                            <tr id="${sampleInfo.sampleId}">
                                <td>${s.count}</td>
                                <%--<td>${sampleInfo.evidenceNo}--%>
                                    <input type="hidden" name="evidenceNo" value="${sampleInfo.evidenceNo}">
                                    <input type="hidden" name="evidenceSerialNo" value="${sampleInfo.evidenceSerialNo}">
                                </td>
                                <td>${sampleInfo.sampleName}
                                    <input type="hidden" name="sampleName" value="${sampleInfo.sampleName}">
                                </td>
                                <td>${sampleInfo.sampleTypeName}
                                    <input type="hidden" name="sampleTypeName" value="${sampleInfo.sampleTypeName}">
                                </td>
                                    <%--<td>${sampleInfo.sampleDesc}
                                        <input type="hidden" name="sampleDescribe" value="${sampleInfo.sampleDesc}">
                                    </td>--%>
                                <td>${sampleInfo.samplePackingName}
                                    <input type="hidden" name="samplePackingName"
                                           value="${sampleInfo.samplePackingName}">
                                </td>
                                <td><fmt:formatDate value="${sampleInfo.extractDatetime}" pattern="yyyy-MM-dd"/>
                                    <input type="hidden" name="extractTime"
                                           value="<fmt:formatDate value="${sampleInfo.extractDatetime}" pattern="yyyy-MM-dd" />">
                                </td>
                                <td>
                                    <div class="coreTakenSelect">
                                        <select class="form-control" name="coreTakenStats" required>
                                            <option value=" "></option>
                                            <option value="1"
                                                    <c:if test="${'1' eq sampleInfo.coreTakenStats}">selected</c:if>>是
                                            </option>
                                            <option value="0"
                                                    <c:if test="${'0' eq sampleInfo.coreTakenStats}">selected</c:if>>否
                                            </option>
                                        </select>
                                    </div>
                                </td>
                                    <%--<td>
                                        <div class="select">
                                            <select class="form-control" required name="extractMethod">
                                                <c:forEach items="${extractMethodList}" var="list">
                                                    <option value="${list.dictCode}"
                                                            <c:if test="${list.dictCode eq sampleInfo.extractMethod}">selected</c:if>>${list.dictName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="select">
                                            <select class="form-control" required name="sampleCarrier">

                                                <c:forEach items="${sampleCarrierList}" var="list">
                                                    <option value="${list.dictCode}"
                                                            <c:if test="${list.dictCode eq sampleInfo.sampleCarrier}">selected</c:if>>${list.dictName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </td>--%>
                                    <%--<td>${sampleInfo.samplePurpose}<input type="hidden" name="inspectionObjective"
                                                                          value="${sampleInfo.samplePurpose}"></td>--%>
                                <c:if test="${consignatioInfo.status != '02'&&consignatioInfo.status != '03'}">
                                    <td>
                                            <%--<button type="button" class="btn-icon btn-icon-yellow btn-icon-split-yellow split">
                                                拆分
                                            </button>--%>
                                        <button type="button" class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">
                                            编辑
                                        </button>
                                        <button type="button" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">
                                            删除
                                        </button>
                                        <input type="hidden" name="sampleId" value="${sampleInfo.sampleId}">
                                        <input type="hidden" name="sampleType" value="${sampleInfo.sampleType}">
                                        <input type="hidden" name="samplePacking" value="${sampleInfo.samplePacking}">
                                        <input type="hidden" name="extractMethod" value="${sampleInfo.extractMethod}">
                                        <input type="hidden" name="sampleCarrier" value="${ sampleInfo.sampleCarrier}">
                                        <input type="hidden" name="sampleDescribe" value="${sampleInfo.sampleDesc}">
                                        <input type="hidden" name="inspectionObjective" value="${sampleInfo.samplePurpose}">
                                        <input type="hidden" name="samplePicture"
                                               value="${ sampleInfo.sampleMaterialPicture}">
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row btn-box ">
        <div class="col-md-12">
            <button type="button" class="btn btn-blue btn-lang saveBoxBtn">提交</button>
            <button type="button" name="quxiao" class="btn btn-blue-border btn-lang">取消</button>
        </div>
    </div>
    <div class="modal fade" id="CaseSucceedModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        消息提示
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal tasi-form">
                        <div class="form-group m-bot20">
                            <div class="col-md-12 text-center">
                                <h3 class="alert alert-success"><Strong>保存成功！</Strong></h3>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn_blue_border" type="button" id="\"><i
                            class="fa fa-reply"></i> 返 回
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="CaseErrorModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        消息提示
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal tasi-form">
                        <div class="form-group m-bot20">
                            <div class="col-md-12 text-center">
                                <h3 class="alert alert-success"><Strong>保存失败！</Strong></h3>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn_blue_border" type="button" id=""><i
                            class="fa fa-reply"></i> 返 回
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>
<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/entrustCurrency.js"></script>
<script src="<%=path%>/js/modifyAdd.js"></script>
<script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script> <!-- LAYUI JS  -->
<script>
    $(function () {

        $("#pageHome").on("click", function () {
            window.location = "<%=path%>/main/home";
            parent.$("#bs-example-navbar-collapse-1").children().eq(1).children().eq(0).addClass("active").siblings().removeClass("active")
        });

        if ('${consignatioInfo.appendFlag}' == '1') {
            $(".Modular").eq(2).find("input[type='text']").prop("disabled", true)
            $(".Modular").eq(2).find("textarea").prop("disabled", true)
            $(".Modular").eq(2).find("select").prop("disabled", true)
        }
        if ('${consignatioInfo.status}' == '02' || '${consignatioInfo.status}' == '03') {
            $(".saveBoxBtn").parents(".btn-box").addClass("hidden")
        }
        <%--if('${consignatioInfo.identifyRequirement}'==""){--%>
        <%--$(".btn-checkbox").find("li[value='01']").addClass("active").find("input").prop("checked", true)--%>
        <%--}else {--%>
        <%--var btnCheckeds = '${consignatioInfo.identifyRequirement}'.split(",");--%>
        <%--var msg = "";--%>
        <%--for(var i=1;i<btnCheckeds.length;i++){--%>
        <%--msg+=btnCheckeds[i]+",";--%>
        <%--}--%>
        <%--var btnChecked=msg.split(",")--%>
        <%--$.each(btnChecked, function (i, item) {--%>
        <%--$(".btn-checkbox").find("li[value='" + item + "']").addClass("active").find("input").prop("checked", true)--%>
        <%--})--%>
        <%--}--%>
        if ('${consignatioInfo.identifyRequirement}' == "") {
            $(".btn-checkbox").find("li[value='01']").addClass("active").find("input").prop("checked", true)
        } else {
            if ('${consignatioInfo.status}' == "01") {
                var btnCheckeds = '${consignatioInfo.identifyRequirement}'.split(",");
                var msg = "";
                for (var i = 1; i < btnCheckeds.length; i++) {
                    msg += btnCheckeds[i] + ",";
                }
                var btnChecked = msg.split(",")
                $.each(btnChecked, function (i, item) {
                    $(".btn-checkbox").find("li[value='" + item + "']").addClass("active").find("input").prop("checked", true)
                })
            } else {
                var btnCheckeds = '${consignatioInfo.identifyRequirement}'.split(",");
                var msg = "";
                for (var i = 0; i < btnCheckeds.length; i++) {
                    msg += btnCheckeds[i] + ",";
                }
                var btnChecked = msg.split(",")
                $.each(btnChecked, function (i, item) {
                    $(".btn-checkbox").find("li[value='" + item + "']").addClass("active").find("input").prop("checked", true)
                })
            }

        }


        //照片展示
        $("#addAuthenticatorBig").on("click", "img", function () {
            $("#showPhotoBox").find("img").attr("src", $(this).attr("src"))
            $("#showPhotoBox").modal("show")
        })
        $("#showPhotoBox").on('show.bs.modal', function (e) {
            $("#showPhotoBox").css("z-index", 1050 + 1000);
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

        //保存弹窗选择下拉
        $(".savechange").click(function () {
            $(".selectbox").eq(0).removeClass("hidden")
        })
        //保存弹窗选择下拉点击
        $(".selectbox").on('click', 'li', function () {
            var orgAddressName = $(this).find("input[name='orgAddressName']").val()
            var orgContactPhoneName = $(this).find("input[name='orgContactPhoneName']").val()
            //获取选择单位的id
            var orgCodeName = $(this).find("input[name='orgCodeName']").val()
            $(".centerInformation").find("p").find(".orgAddressSpanId").html(orgAddressName)
            $(".centerInformation").find("p").find(".orgContactPhoneSpanId").html(orgContactPhoneName)
            //将获取的orgCodeName赋值
            $(".identificationCenter").find("input[name='orgCodeName']").val(orgCodeName);
            $('.identificationCenter').html($(this).html())
            $(this).parents(".selectbox").addClass("hidden")
        })

        //判断回显1不可编辑
        $(".victimSelect").each(function () {
            if ($(this).find("#coreVictimStats option:selected").val() != "") {
                $(this).find("#coreVictimStats").prop("disabled", true)
            }
        })
        /**
         * 保存整个页面信息
         */

        var url = "<%=path%>/delegate/submitNonDelegate";


        $("button[name='saveInfo']").on("click", function () {
            var form = $("#saveform ")

            /*if($("#manualWtsNoBox").is(":checked")){
             $("#consignmentNo").val() =="";
             alert("委托书编号不能为空！");
             return;
             }*/
            var orgname = $(".orgname").find("input[name='orgname']").val();

            if ($("option:selected", "#areaOrgCode").val() == "") {
                alert("所属辖区不能为空！");
                return;
            }

            var caseInfoDna = getCaseInfoDna();
            var limsConsignmentInfo = getConsignmentInfo();
            var sampleInfoDnaList = getSampleInfoDna();
            var limsPersonInfoList = getLimsPersonInfo();
            var evaluationCenter = $("#saveBox").find(".btn-checkbox").children(".active").find("input[name='orgCodeName']").val();

            console.log("personInfo" + limsPersonInfoList)

            var paramsData = {
                "orgname": orgname,
                "caseInfoDna": caseInfoDna,
                "consignmentInfo": limsConsignmentInfo,
                "sampleInfoDnaList": sampleInfoDnaList,
                "limsPersonInfoList": limsPersonInfoList,
                "personIds": personIds,
                "sampleIds": sampleIds,
                "sampleIdWzs": sampleIdWzs,
                "evaluationCenter": evaluationCenter,
            };


            // var  paramsData= {
            //     "caseInfoDna":{"caseName":"大兴亦庄火灾100","caseLocation":"大兴亦庄","caseDatetime":"2019-03-05","caseType":"01","caseProperty":"11","caseBrief":"2019年大兴亦庄发生火灾2019年大兴亦庄发生火灾2019年大兴亦庄发生火灾2019年大兴亦庄发生火灾2019年大兴亦庄发生火灾2019年大兴亦庄发生火灾","caseRemark":"","caseXkNo":"","caseId":"","majorNo":"DNA鉴定","majorType":"01"},
            //       "consignmentInfo":{"consignmentNo":"委托书编号自动生成","areaOrgCode":"110095140000","delegateOrgCode":"110115000000","delegateOrgName":"大兴分局","delegator1Id":"7b0bddee-d6ff-4d36-8d31-754dfb244b0b","delegator1Name":"刘立超","delegator1Position":"11","delegator1PaperworkType":"警官证","delegator1PaperworkNo":"055720","delegator1PhoneNumber":"18518851059","delegator2Id":"e738f639-689a-4aa0-8dc8-948f6d0403e6","delegator2Name":"宋树敬","delegator2Position":"01","delegator2PaperworkType":"警官证","delegator2PaperworkNo":"029212","delegator2PhoneNumber":"18518851076","identifyRequirement":"01","identifyType":"DNA鉴定","consignmentId":""},"sampleInfoDnaList":[{"sampleType":"03","sampleName":"脱落细胞1","sampleDesc":"脱落细胞1","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞2","sampleDesc":"脱落细胞2","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞3","sampleDesc":"脱落细胞3","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞4","sampleDesc":"脱落细胞4","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞5","sampleDesc":"脱落细胞5","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞6","sampleDesc":"脱落细胞6脱落细胞6脱落细胞6脱落细胞6脱落细胞6脱落细胞6脱落细胞6","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞7","sampleDesc":"脱落细胞7脱落细胞7脱落细胞7","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞8","sampleDesc":"脱落细胞8脱落细胞8脱落细胞8","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞9","sampleDesc":"脱落细胞8脱落细胞8脱落细胞9","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞10","sampleDesc":"脱落细胞10脱落细胞10","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞11","sampleDesc":"脱落细胞11脱落细胞11脱落细胞11","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞12","sampleDesc":"脱落细胞12脱落细胞12脱落细胞12","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞13","sampleDesc":"脱落细胞13脱落细胞13","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞14","sampleDesc":"脱落细胞14脱落细胞14脱落细胞14脱落细胞14","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞15","sampleDesc":"脱落细胞15脱落细胞15脱落细胞15","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞16","sampleDesc":"脱落细胞16脱落细胞16脱落细胞16","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞17","sampleDesc":"脱落细胞17脱落细胞17脱落细胞17","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞18","sampleDesc":"脱落细胞18脱落细胞18脱落细胞18","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞19","sampleDesc":"脱落细胞19脱落细胞19","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞20","sampleDesc":"脱落细胞19脱落细胞20脱落细胞20脱落细胞20","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞21","sampleDesc":"脱落细胞21脱落细胞21脱落细胞21脱落细胞21","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞22","sampleDesc":"脱落细胞22脱落细胞22脱落细胞22","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞23","sampleDesc":"脱落细胞23脱落细胞23脱落细胞23","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞24","sampleDesc":"脱落细胞24脱落细胞24脱落细胞24脱落细胞24","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞25","sampleDesc":"脱落细胞25脱落细胞25脱落细胞25","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞26","sampleDesc":"脱落细胞26脱落细胞26脱落细胞26","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞27","sampleDesc":"脱落细胞27脱落细胞27脱落细胞27","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞28","sampleDesc":"脱落细胞28脱落细胞28脱落细胞28脱落细胞28","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞29","sampleDesc":"脱落细胞29脱落细胞29脱落细胞29","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞30","sampleDesc":"脱落细胞30脱落细胞30脱落细胞30","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞31","sampleDesc":"脱落细胞31脱落细胞31脱落细胞31","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞32","sampleDesc":"脱落细胞32脱落细胞32脱落细胞32脱落细胞32","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞33","sampleDesc":"脱落细胞33脱落细胞33脱落细胞33","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞34","sampleDesc":"脱落细胞34脱落细胞34脱落细胞34脱落细胞34","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞35","sampleDesc":"脱落细胞35脱落细胞35脱落细胞35","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞36","sampleDesc":"脱落细胞36脱落细胞36脱落细胞36","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞37","sampleDesc":"脱落细胞37脱落细胞37脱落细胞37","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞38","sampleDesc":"脱落细胞38脱落细胞38脱落细胞38","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞39","sampleDesc":"脱落细胞39脱落细胞39脱落细胞39","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞40","sampleDesc":"脱落细胞40脱落细胞40脱落细胞40","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞41","sampleDesc":"脱落细胞41脱落细胞41脱落细胞41脱落细胞41","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞42","sampleDesc":"脱落细胞42脱落细胞42脱落细胞42","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞4","sampleDesc":"脱落3细胞4脱落3细胞4脱落3细胞4","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞44","sampleDesc":"脱落3细胞44脱落3细胞44","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞45","sampleDesc":"脱落3细胞45脱落3细胞45脱落3细胞45","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞46","sampleDesc":"脱落3细胞46脱落3细胞46","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞47","sampleDesc":"脱落3细胞47脱落3细胞47脱落3细胞47","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞48","sampleDesc":"脱落3细胞48","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞49","sampleDesc":"脱落3细胞49脱落3细胞49脱落3细胞49","samplePacking":"01","extractDatetime":"2019-03-06","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞50","sampleDesc":"脱落3细胞50脱落3细胞50","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞51","sampleDesc":"脱落3细胞51脱落3细胞51脱落3细胞51","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞52","sampleDesc":"脱落3细胞52脱落3细胞52脱落3细胞52","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞53","sampleDesc":"脱落3细胞53脱落3细胞53脱落3细胞53","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞54","sampleDesc":"脱落3细胞54脱落3细胞54脱落3细胞54","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞55","sampleDesc":"脱落3细胞55脱落3细胞55脱落3细胞55","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞56","sampleDesc":"脱落3细胞56脱落3细胞56脱落3细胞56","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落3细胞57","sampleDesc":"脱落3细胞57脱落3细胞57脱落3细胞57","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞58","sampleDesc":"脱落细胞58脱落细胞58脱落细胞58","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞59","sampleDesc":"脱落细胞59脱落细胞59脱落细胞59","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞60","sampleDesc":"脱落细胞60脱落细胞60脱落细胞60脱落细胞60","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞61","sampleDesc":"脱落细胞61脱落细胞61脱落细胞61","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞62","sampleDesc":"脱落细胞62脱落细胞62脱落细胞62","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞63","sampleDesc":"脱落细胞63脱落细胞63脱落细胞63","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞64","sampleDesc":"脱落细胞64脱落细胞64脱落细胞64","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞65","sampleDesc":"脱落细胞65脱落细胞65脱落细胞65","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞66","sampleDesc":"脱落细胞66脱落细胞66脱落细胞66脱落细胞66脱落细胞66","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞67","sampleDesc":"脱落细胞67脱落细胞67脱落细胞67脱落细胞67","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞68","sampleDesc":"脱落细胞68脱落细胞68脱落细胞68","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞68脱落细胞69","sampleDesc":"脱落细胞68脱落细胞69脱落细胞68脱落细胞69脱落细胞68脱落细胞69","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞70","sampleDesc":"脱落细胞70脱落细胞70","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞71","sampleDesc":"脱落细胞70脱落细胞70脱落细胞71脱落细胞71脱落细胞71","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞72","sampleDesc":"脱落细胞72脱落细胞72脱落细胞72","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞73","sampleDesc":"脱落细胞73脱落细胞73脱落细胞73","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞74","sampleDesc":"脱落细胞74脱落细胞74脱落细胞74脱落细胞74","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞75","sampleDesc":"脱落细胞75脱落细胞75脱落细胞75","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞76","sampleDesc":"脱落细胞76脱落细胞76脱落细胞76","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞77","sampleDesc":"脱落细胞77脱落细胞77","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞78","sampleDesc":"脱落细胞78脱落细胞78脱落细胞78脱落细胞78","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},{"sampleType":"03","sampleName":"脱落细胞79","sampleDesc":"脱落细胞79脱落细胞79脱落细胞79","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞80","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞81","sampleDesc":"脱落细胞80脱落细胞80脱落细胞81","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞82","sampleDesc":"脱落细胞80脱落细胞80脱落细胞82","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞83","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞84","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞85","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞86","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞87","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞88","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞89","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞90","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞91","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞92","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞93","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞94","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞95","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞96","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞97","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞98","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞99","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""},
            //         {"sampleType":"03","sampleName":"脱落细胞100","sampleDesc":"脱落细胞80脱落细胞80脱落细胞80","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","sampleIdwz":"","evidenceNo":"","sampleMaterialPicture":""}
            //         ],
            //     "limsPersonInfoList":[{"personType":"04","personName":"12312","personGender":"01","perosnAge":"21","personIdCard":"421182199809141311","idCardFlag":0,"noIdCardDesc":"","relationType":"06","personCurrentAddress":"","personHeight":"","personWeight":"","personFrontPicture":"","sampleInfoDnaList":[{"sampleType":"01","sampleName":"12312血样","sampleDesc":"受害人亲属12312血样","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","addFlag":"1","sampleDnaPicture":""}]},{"personType":"04","personName":"123123","personGender":"01","perosnAge":"29","personIdCard":"421182199009111311","idCardFlag":0,"noIdCardDesc":"","relationType":"06","personCurrentAddress":"","personHeight":"","personWeight":"","personFrontPicture":"","sampleInfoDnaList":[{"sampleType":"01","sampleName":"123123血样","sampleDesc":"受害人亲属123123血样","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","addFlag":"1","sampleDnaPicture":""}]},{"personType":"01","personName":"123","personGender":"01","perosnAge":"28","personIdCard":"421182199109141311","idCardFlag":0,"noIdCardDesc":"","relationType":"","personCurrentAddress":"","personHeight":"","personWeight":"","personFrontPicture":"","sampleInfoDnaList":[{"sampleType":"01","sampleName":"123血样","sampleDesc":"嫌疑人123血样","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","addFlag":"1","sampleDnaPicture":""}]},{"personType":"01","personName":"22222","personGender":"01","perosnAge":"27","personIdCard":"421182199209141311","idCardFlag":0,"noIdCardDesc":"","relationType":"","personCurrentAddress":"","personHeight":"","personWeight":"","personFrontPicture":"","sampleInfoDnaList":[{"sampleType":"01","sampleName":"22222血样","sampleDesc":"嫌疑人22222血样","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","addFlag":"1","sampleDnaPicture":""}]},{"personType":"01","personName":"3333","personGender":"01","perosnAge":"27","personIdCard":"421182199209141311","idCardFlag":0,"noIdCardDesc":"","relationType":"","personCurrentAddress":"","personHeight":"","personWeight":"","personFrontPicture":"","sampleInfoDnaList":[{"sampleType":"01","sampleName":"3333血样","sampleDesc":"嫌疑人3333血样","samplePacking":"01","extractDatetime":"2019-03-05","extractMethod":"01","sampleCarrier":"01","samplePurpose":"DNA检验","addFlag":"1","sampleDnaPicture":""}]}],
            //       "personIds":"","sampleIds":"","evaluationCenter":"110230000000"};

            console.log(paramsData)

            $('#saveform').ajaxSubmit({
                url: url,
                type: "post",
                //contentType: "application/json",
                data: {"paramsData": JSON.stringify(paramsData)},
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        /*parent.$("#bs-example-navbar-collapse-1").children().eq(1).children().eq(3).addClass("active").siblings().removeClass("active")
                         location.href = '
                        <%=path%>/caseQuery/caseQuery';*/
                        console.log(data.caseId)
                        console.log(data.consignmentId)
                        location.href = '<%=path%>/caseQuery/updateCaseWord?caseId=' + data.caseId + '&consignmentId=' + data.consignmentId;
                    } else if (data.success || data.success == false || data.success == "false") {
                        $("#CaseErrorModal").modal('show');
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
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

            /*var t_files = this.files;
             files = t_files;
             var sampleInfoFileTxt = $("#sampleInfoFileTxt").val();
             sampleInfoFileTxt += t_files[0].name + ",";
             $("#sampleInfoFileTxt").val(sampleInfoFileTxt);*/
        });

        //获取委托人1信息
        $("#delegator1Id").change(function () {
            var delegator1Id = $("option:selected", "#delegator1Id").val()
            loadAmPeronsalInfoByDelegator1Id(delegator1Id);
        });

        var loadAmPeronsalInfoByDelegator1Id = function (delegator1Id) {
            var urlStr = "<%=path%>/delegate/queryAmPersonalInfo.html?parentId=" + delegator1Id;
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
            var urlStr = "<%=path%>/delegate/queryAmPersonalInfo.html?parentId=" + delegator2Id;
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

        layui.use('layer', function () {
            var layer = layui.layer;

            function loading() {
                //示范一个公告层
                layer.open({
                    type: 1
                    , title: false //不显示标题栏
                    , closeBtn: false
                    , shade: 0.4
                    , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    , btnAlign: 'c'
                    , moveType: 1 //拖拽模式，0或者1
                    , content: '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop loading">&#xe63d;</i>'
                });
            }

            //核验
            $("#verification").click(function () {
                var idcard = $("#idCard").val();
                var sysType = "01";
                if (idcard == "") {
                    alert("请输入身份证号！");
                    return;
                }
                //弹出层
                loading();
                $.ajax({
                    type: "get",
                    url: "http://192.168.0.107:8080/BazlRkxxService/bazl/rkxx/idcard.html?idcard=" + idcard + "&sysType=" + sysType,
                    dataType: "jsonp",
                    processData: false,
                    async: true,//异步
                    success: function (data) {
                        var personInfo = data.result;
                        if ("1" == data.code) {
                            $.each(personInfo, function (i, person) {
                                $.each(person, function (i, ps) {
                                    var personName = ps.name;
                                    var gender = ps.gender;
                                    var allFullAddr = ps.allFullAddr;
                                    $("input[id='personName']").val(personName);
                                    if (null != allFullAddr) {
                                        $("input[id='personCurrentAddress']").val(allFullAddr);
                                    }
                                    //关闭弹出层
                                    layer.closeAll();
                                });
                            });
                        } else {
                            alert("没有对应的人员信息!");
                            layer.closeAll();
                            $("#idCard").val('');
                        }
                    },
                    error: function () {
                        alert("检验失败！");
                        layer.closeAll();
                    }
                });

            });
        });

        $("[name='consignmentNo']").blur(function () {
            var consignmentNo = $("[name='consignmentNo']").val();
            var delegateOrgCode = $("#delegateOrgCode").val();
            $.ajax({
                url: "<%=path%>/delegate/testConsignmentNo?consignmentNo=" + consignmentNo + "&delegateOrgCode=" + delegateOrgCode,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success == true || data.success == 'true') {
                        alert("该委托编号已存在！")
                        $("[name='consignmentNo']").val('');
                        /*$("[name='WtsNoBoxs']").prop("checked",false)*/
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        });

        if ('${consignatioInfo.status}' == '02') {
            $("#openAddAuthenticatorBig").addClass("hidden")
            $(".addsampleInfo").addClass("hidden")
            $(".addMaterialEvidencer").addClass("hidden")
        }

        //取消按钮
        $("button[name='quxiao']").on("click", function () {
            var areaOrgCode = $("#areaOrgCode").val();
            if (areaOrgCode == "") {
                location.href = "<%=path%>/delegate/caseReg";
            } else {
                location.href = "<%=path%>/caseQuery/caseQuery";
            }
        })

//        let list = document.getElementsByClassName('listBox')[0].getElementsByTagName('p')
//        for (let i = 0; i < list.length; i++) {
//            list[i].index = i
//            list[i].onclick = function () {
//                for (let j = 0; j < list.length; j++) {
//                    $('.pig').eq(j).css('display', 'none')
//                    $('.lis').eq(j).css({'background': 'white', 'color': '#0C81F5'})
//                }
//                $('.pig').eq(this.index).css('display', 'block')
//                $('.lis').eq(this.index).css({'background': '#0C81F5', 'color': 'white'})
//            }
//        }
        //控制边框高亮
        $(".qrm-input-border").click(function () {
            if ($(".qrm-pinming").css("background-image").indexOf("qrm-arrow-down") !== -1) {
                if ($(".qrm-input").val() == '') {
                    $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-top.png)");
                    $(".qrm-pinming").css("border", "1px solid #409EFF");
                    $(".qrm-border1").show();
                } else {
                    var s = $(".qrm-input").val()
                    console.log(s)
                    var n = (s.split('/')).length - 1;
                    console.log(n)
                    if (n == 1) {
                        $(".qrm-border1").show();
                        $(".qrm-border2").show();
                    } else if (n == 2) {
                        $(".qrm-border1").show();
                        $(".qrm-border2").show();
                        $(".qrm-border3").show();
                    }
                    $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-top.png)");
                    $(".qrm-pinming").css("border", "1px solid #409EFF");
                }
            } else {
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");
            }
        });
        //第一层
        var lev1;
        var lev2;
        var lev3;
        var lev4;
        //第一层 事件代理
        $("body").on("click", ".qrm-lev-1>li", function () {
            //控制背景颜色高亮
            $(this).addClass("active").siblings("li").removeClass("active");
            // 先将input中的值置空
            lev1 = "";
            lev2 = "";
            lev3 = "";
            lev4 = "";
            // 获取当前点击的li的子元素的HTML节点 将获取的节点放到页面显示的第二级中
            var html1 = $(this).children(".li-zi-1").html();
            $(".qrm-lev-2").html(html1);
            $(".qrm-border2").show();
            $(".qrm-border3").hide();
            $(".qrm-lev-3").html("");
//      $(".qrm-lev-4").html("");
            //获取当前点击的li的span中的值 将值传到input中
            lev1 = $(this).children("span").html();
//      $(".qrm-input").val("");
//      $(".qrm-input").val(lev1);
        });
        //第二层 事件代理

        $("body").on("click", ".qrm-lev-2>li", function () {
            $(this).addClass("active").siblings("li").removeClass("active");
//      console.log($(this).children(".li-zi-2").html())
            if ($(this).children(".li-zi-2").html() == undefined) {
                lev2 = $(this).children("span").html();
                $(".qrm-input").val(lev1 + "/" + lev2);
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");
            } else {
                var html2 = $(this).children(".li-zi-2").html();
                lev2 = $(this).children("span").html();
                $(".qrm-border3").show();
                $(".qrm-lev-3").html(html2);
            }
            if ($(this).parent().parent().next().children(".qrm-lev").html() == "") {
                // 去掉输入框的高亮状态
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");
            }
//      $(".qrm-lev-4").html("");
//      $(".qrm-input").val(lev1+"/"+lev2);
        });
        //第三层 事件代理
        $("body").on("click", ".qrm-lev-3>li", function () {
            $(this).addClass("active").siblings("li").removeClass("active");
            var html3 = $(this).children(".li-zi-3").html();
            lev3 = $(this).children("span").html();
//      $(".qrm-lev-4").html(html3);
            $(".qrm-input").val(lev1 + "/" + lev2 + "/" + lev3);
            $(".qrm-border1").hide();
            $(".qrm-border2").hide();
            $(".qrm-border3").hide();
            $(".qrm-pinming").css("border", "1px solid #ddd");
            $(".qrm-pinming").css("background-image", "url(images/qrm-arrow-down.png)");
            if ($(this).parent().parent().next().children(".qrm-lev").html() == "") {
                // 去掉输入框的高亮状态
                $(".qrm-input").val(lev1 + "/" + lev2);
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");
            }
        });
        //第三层 事件代理
//  $("body").on("click",".qrm-lev-4>li",function () {
//      $(this).addClass("active").siblings("li").removeClass("active");
//      lev4=$(this).children("span").html();
//      $(".qrm-pinming-panel").hide();
//      $(".qrm-pinming").css("border","1px solid #ddd");
//      $(".qrm-pinming").css("background-image","url(images/qrm-arrow-down.png)");
//      $(".qrm-input").val(lev1+"/"+lev2+"/"+lev3+"/"+lev4);
//  });

//给四个区域绑定点击事件 判断当前的下一个区域 如果为空 点击当前区域 qrm-pinming-panel 隐藏 并且把input高亮去掉
        $("body").on("click", ".qrm-lev>li", function () {
            if ($(this).parent().parent().next().children(".qrm-lev").html() == "") {
                // 去掉输入框的高亮状态
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");
            }
        })
        //点击空白处隐藏div
        $(document).click(function (event) {
            var x1 = $('.box');  // 设置目标区域
            if (!x1.is(event.target) && x1.has(event.target).length === 0) { // Mark 1
                $(".qrm-border1").hide();
                $(".qrm-border2").hide();
                $(".qrm-border3").hide();
                $(".qrm-pinming").css("border", "1px solid #ddd");
                $(".qrm-pinming").css("background-image", "url(<%=path%>/img/qrm-arrow-down.png)");


                //$('#divTop').slideUp('slow');  //滑动消失


//         $('.qrm-pinming-panel').hide(300);     //淡出消失
            }
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
                           /* if (len > 1) {
                                document.getElementById('casePropertyLv2').style.display = 'block';
                            } else {
                                document.getElementById('casePropertyLv2').style.display = 'none';
                                document.getElementById('casePropertyLv2').value = "";
                            }*/
                        }
                    }
                });
            } else {
                $("option:gt(0)", "#casePropertyLv2").remove();
            }
        });
    });

</script>
</body>

</html>