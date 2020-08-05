<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/14
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="<%=path %>/js/layui-v2.2.45/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <title>北京市公安局DNA案件委托送检系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        body{
            padding: 0;
            margin: 0;
            padding: 5px 13px 0 20px;
        }
        select{
            -webkit-appearance: normal !important;
        }
        .box{
            width: 100%;
            height: 100%;
        }
        .record{
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
        }
        .recordl{
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0;
        }
        .recordls{
            width: 100%;
            background: white;
            border-radius: 5px;
            margin-top: 10px;
            padding: 10px 0 4% 0;
        }
        .records{
            width: 100%;
            background: white;
            border-radius: 5px;
            display: flex;
            align-items: center;
            padding: 1% 0;
        }
        .record:first-child{
            margin: 0;
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
        .recordNames::before{
            content: " ";
            width:4px;
            display: block;
            height:18px;
            background:rgba(53,134,250,1);
            margin-right: 8px;
        }
        .reNames{
            color: #3586FA;
            font-weight:bold;
            font-size: 16px;
            flex: 1;
            padding-left: 6%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
            border-bottom: 1px solid #CCCCCC;
        }
        .reNamesr{
            color: #3586FA;
            font-weight:bold;
            font-size: 16px;
            flex: 1;
            padding-left: 6%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
        }
        .reNamesr::before{
            content: " ";
            width:4px;
            display: block;
            height:18px;
            background:rgba(53,134,250,1);
            margin-right: 8px;
        }
        .reNames::before{
            content: " ";
            width:4px;
            display: block;
            height:18px;
            background:rgba(53,134,250,1);
            margin-right: 8px;
        }
        .recordName img{
            margin-left: 20px;
        }
        .rowst{
            padding-bottom: 2%;
            border-bottom: 1px solid #CCCCCC;
        }
        .rowst,.rowst form{
            width: 100%;
            display: flex;
            align-items: center;
        }
        .lays{
            width: 27%;
            display: flex;
            align-items: center;
            color: #333333;
            font-weight:400;
            font-size:14px;
            margin: 0;
            padding: 5px 0;
            padding-left: 1%;
            white-space: nowrap;
        }
        .lay input{
            width: 60%;
            margin-left: 10px;
        }
        .lay select{
            width: 60%;
            margin-left: 10px;
        }
        .lay textarea{
            width: 60%;
            margin-left: 10px;
        }
        .lays select{
            width: 80%;
            margin-left: 10px;
        }
        .lays input{
            width: 80%;
            margin-left: 10px;
        }
        .titl{
            display: flex;
            white-space: nowrap;
            align-items: center;
        }
        .titl select{
            width: 80%;
            margin-left: 10px;
        }
        .titl input{
            width: 80%;
            margin-left: 10px;
        }
        label{
            margin: 0;
        }
        .persOne{
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
        .botts{
            padding-bottom: 4%;
        }
        .recordNames{
            color: #3586FA;
            font-weight:bold;
            font-size: 16px;
            padding-left: 2%;
            display: flex;
            align-items: center;
            line-height: 40px;
            margin: 0;
        }
        .listBox{
            flex: 1;
            display: flex;
        }
        .listBox p{
            width: 10%;
            text-align: center;
            border:1px solid rgba(0,126,249,1);
            color:rgba(0,126,249,1);
            line-height: 40px;
            margin: 0;
            margin-left: 20px;
            border-radius:100px;
            position: relative;
        }
        .layc textarea{
            margin-left: 10px;
            width: 53%;
        }
        #test2,#test1,#test3,#test4,#test5   {
            border: 1px solid rgba(204, 204, 204, 1);
            background: rgba(235, 235, 235, 1);
            color: rgb(0, 0, 0, 45);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100px;
            width: 110px;
            border-radius: 5px;
        }
        #demo2 img{
            width: 100%;
            height: auto;
        }
        #demo1 img{
            width: 100%;
            height: auto;
        }
        #demo3 img{
            width: 100%;
            height: auto;
        }
        #demo4 img{
            width: 100%;
            height: auto;
        }
        #demo5 img{
            width: 100%;
            height: auto;
        }
        #test2,#test1,#test3,#test4,#test5 {
            display: flex;
            align-items: center;
        }
        #demo2  div{
            width: 127px;
            margin-left: 10px;
        }
        #demo3  div{
            width: 127px;
            margin-left: 10px;
        }
        #demo4  div{
            width: 127px;
            margin-left: 10px;
        }
        #demo5  div{
            width: 127px;
            margin-left: 10px;
        }
        #demo1  div{
            width: 127px;
            margin-left: 10px;
        }
        .adds{
            font-size:40px;
        }
        .layui-quote-nm{
            border:0;
        }
        .layui-elem-quote{
            padding: 0;
            padding-left: 10px;
            margin: 0;
        }
        blockquote{
            padding: 0;
            margin: 0 !important;
            width: auto;
            display: flex;
            align-items: center;
        }
        .addt{
            font-size:12px;
            font-family:Microsoft YaHei;
            font-weight:400;
            width: 70px;
            background:rgba(12,129,245,1);
            border:1px solid rgba(12,129,245,1);
            color: white;
            line-height: 30px;
            margin-left: 15px;
            border-radius: 5px;
        }
        .tabs{
            padding: 0 2%;
        }
        .tabs thead{
            background: #BAE0FF;
            font-weight: 700;
            font-size: 14px;
        }
        .tabs th{
            text-align: center;
        }
        .tabs td{
            text-align: center;
            font-size: 12px;
        }
        .las{
            display: flex;
            align-items: center;
            justify-content: center;
            align-items: center;
        }
        .las>p{
            padding-top: 10px;
        }
        .fixeds{
            width: 100%;
            background: white;
            position: fixed;
            bottom: 0;
            z-index: 100;
            height: 7%;
            box-shadow:0px 0px 14px rgba(0,0,0,0.15);
            display: flex;
            justify-content: flex-end;
            align-items: center;
            padding-right: 5%;
        }
        .backs{
            border:1px solid rgba(0,126,249,1);
            width: 9%;
            color: #007EF9;
            border-radius: 5px;
            background: white;
            padding: 10px 0;
        }
        .suer{
            background: #0C81F5;
            color: white;
            border: 1px solid #0C81F5;
            border-radius: 5px;
            width: 9%;
            padding: 10px 0;
            margin-right: 10px;
        }
        .pig{
            position: absolute;
            right: -5px;
            top: -10px;
            display: none;
        }
        .runPer{
            width: 100%;
            display: flex;
            padding: 1% 2%;
        }
        .perInfo{
            display: flex;
            flex: 1;
        }
        .perTwos{
            flex: 1;
        }
        .perOne{
            color:rgba(51,51,51,1);
            letter-spacing:30px;
            font-size:14px;
            font-weight:bold;
            margin: 0;
        }
        .perOnet{
            padding-right: 20px;
        }
        .perOnes{
            color:rgba(51,51,51,1);
            font-size:14px;
            font-weight:bold;
            margin: 0;
            padding-right: 30px;
        }
        .perTwo{
            color:rgba(26,26,26,1);
            font-size:14px;
            margin: 0;
        }
        .perOne::before{
            content: url("<%=path%>/img/warn (4).png");
            padding-right: 10px;
        }
        .perOnes::before{
            content: url("<%=path%>/img/warn (4).png");
            padding-right: 10px;
        }
        .perOnet::before{
            content: url("<%=path%>/img/warn (4).png");
            padding-right: 10px;
        }
        .perLeft{
            flex: 1;
            display: flex;
            flex-direction: column;
            border-right:1px dashed rgba(0,0,0,1);
        }
        .perRight{
            flex: 1;
            padding-left: 2%;
        }
        .maxLeft{
            display: flex;
            flex: 1;
            margin-top: 15px;
        }
        .maxLeft:first-child{
            margin: 0;
        }
        .moTitle{
            background: #B9E0FF;
            margin: 0;
            color: #1681F5;
            font-size: 16px;
            font-weight:bold;
            text-align: center;
        }
        .modal-dialog{
            width: 48%;
            line-height: 50px;
        }
        .repo{
            width: 40%;
            line-height: 50px;
        }
        .modal-body{
            width: 100%;
            padding: 0;
            display: flex;
            flex-direction: column;
        }
        .infoName{
            display: flex;
            width: 100%;
        }
        .infoBody{
            flex: 1;
            display: flex;
            border-top: 1px dashed #000000;
            border-bottom: 1px dashed #000000;
        }
        .crew{
            flex: 1;
            border-right: 1px dashed #000000;
            padding: 2% 6%;
        }
        .crews{
            flex: 1;
            padding: 2% 6%;
        }
        .crewss{
            flex: 1;
            padding: 2% 13%;
        }
        .perImg{
            font-size:14px;
            font-family:Microsoft YaHei;
            color:rgba(0,0,0,1);
            font-weight:400;
            margin: 0;
        }
        .perImgs{
            font-size:14px;
            font-family:Microsoft YaHei;
            color:rgba(0,0,0,1);
            font-weight:400;
            margin: 0;
            width: 25%;
            display: flex;
            justify-content: flex-end;
        }
        .perInp{
            width: 100%;
            display: flex;
            align-items: center;
        }
        .perInpname{
            margin: 0;
            display: flex;
            align-items: center;
        }
        .perInpname1{
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            width: 25%;
            white-space: nowrap;
        }
        .inpo{
            margin: 0;
            flex: 1;
            padding-left: 20px;
            display: flex;
        }
        .inpo input{
            width: 100%;

        }
        .lett{
            display: inline-block;
            padding: 0 15px;
        }
        .lettd{
            display: inline-block;
            padding: 0 28px;
        }
        .radio-inline{
            margin: 0 !important;
            display: flex;
            align-items: center;
        }
        .inpos{
            margin: 0 !important;
            display: flex;
            align-items: center;
        }
        .radio-inline span{
            padding-left: 30px;
        }
        .checkbox input[type=checkbox], .checkbox-inline input[type=checkbox], .radio input[type=radio], .radio-inline input[type=radio]{
            margin: 0 !important;
        }
        .btns{
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 1% 0;
        }
        .btns button:first-child{
            width: 13%;
            background: #2E83FE;
            color: white;
            height: 40px;
            line-height: 40px;
            border-radius: 3px;
            font-size:16px;
            border: 1px solid #2E83FE;
            margin-right: 5px;
        }
        .btns button:nth-child(2){
            width: 13%;
            background: white;
            color: #267FFF;
            height: 40px;
            margin-left: 5px;
            line-height: 40px;
            border-radius: 3px;
            font-size:16px;
            border:1px solid rgba(12,129,245,1);
        }
        .usert{
            display: flex;
            align-items: center;
        }
    </style>
</head>
<body>
<!--添加在逃人员亲属-->
<form>
    <div class="modal fade" id="fugitivesModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <p class="moTitle fugitivesModel"></p>
                    <div class="infoName">
                        <div class="reNamesr">
                            <span>人员信息</span>
                        </div>
                        <div class="reNamesr">
                            <span>检材信息</span>
                        </div>
                    </div>
                    <div class="infoBody">
                        <div class="crew">
                            <div class="layui-upload usert">
                                <p class="perImg">人员照片</p>
                                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                    <div class="layui-upload-list" id="demo1"></div>
                                </blockquote>
                                <div id="test1"><span class="adds">+</span><span>添加图片</span></div>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">姓 <i class="lett"></i> 名</p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="personName" name="personName" placeholder="请输入姓名">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">性<i class="lett"></i> 别</p>
                                <p class="inpos">
                                    <label class="radio-inline">
                                        <input type="radio" name="personGender" value="01"><span>男</span>
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="personGender" value="02"><span>女</span>
                                    </label>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">证件号码</p>
                                <p class="inpo">
                                    <select class="form-control" id="certificateType" name="certificateType">
                                        <option value="" disabled selected hidden>选择证件类型</option>
                                        <c:forEach items="${cardTypeList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname"> <i class="lettd"></i> </p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="personIdCard" name="personIdCard" placeholder="请输入证件号码">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">民<i class="lett"></i>族</p>
                                <p class="inpo">
                                    <select class="form-control" id="nation" name="nation">
                                        <option value="" disabled selected hidden>请选择民族</option>
                                        <c:forEach items="${raceList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">家庭住址</p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="address" name="address" placeholder="请输入家庭住址">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">身<i class="lett"></i>高 </p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="height" name="height" placeholder="请输入身高">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">体<i class="lett"></i>重</p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="weight" name="weight" placeholder="请输入体重">
                                </p>
                            </div>
                            <div class="perInp kinship">
                                <p class="perInpname">亲缘身份</p>
                                <p class="inpo">
                                    <select class="form-control" required id="kinship" name="kinship">
                                        <option value="" disabled selected hidden>请选择亲缘关系</option>
                                        <c:forEach items="${relationTypeList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">备 <i class="lett"></i>  注</p>
                                <p class="inpo">
                                    <textarea  placeholder="请输入备注" class="form-control" rows="3" id="remark" name="remark"></textarea>
                                </p>
                            </div>
                        </div>
                        <div class="crews">
                            <div class="layui-upload usert">
                                <p class="perImg">检材照片</p>
                                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                    <div class="layui-upload-list" id="demo2"></div>
                                </blockquote>
                                <div id="test2"><span class="adds">+</span><span>添加图片</span></div>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">检材类型</p>
                                <p class="inpo">
                                    <select class="form-control" required id="sampleType" name="sampleType">
                                        <option value="" disabled selected hidden>请选择检材类型</option>
                                        <c:forEach items="${sampleTypeList}" var="sampleList">
                                            <option value="${sampleList.dictCode}">${sampleList.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">检材名称</p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="sampleName" name="sampleName" placeholder="请输入名称">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">提取时间</p>
                                <p class="inpo">
                                    <input type="text" class="form-control form_datetime" placeholder="请输入提取时间"
                                           id="extractTime" name="extractTime" readonly>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">提取方法</p>
                                <p class="inpo">
                                    <select class="form-control" required id="extractMethod" name="extractMethod">
                                        <c:forEach items="${extractMethodList}" var="list">
                                            <option value="${list.dictCode}"
                                                    <c:if test="${list.dictCode eq sampleInfo.extractMethod}">selected</c:if>>${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname"><i class="lettd"></i></p>
                                <p class="inpo">
                                    <select class="form-control" required id="sampleCarrier" name="sampleCarrier">
                                        <c:forEach items="${sampleCarrierList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">包装方法</p>
                                <p class="inpo">
                                    <select id="samplePacking" name="samplePacking" class="form-control" required>
                                        <option value="">请选包装方法</option>
                                        <c:forEach items="${packMethodList}" var="packMethod">
                                            <option value="${packMethod.dictCode}">${packMethod.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname">检材描述</p>
                                <p class="inpo">
                                    <textarea placeholder="请输入描述" class="form-control" id="sampleDescribe" name="sampleDescribe" rows="4"></textarea>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="btns">
                        <button type="button" id="relationBtn">保存</button>
                        <button type="button"  data-dismiss="modal">关闭</button>
                        <input type="hidden" name="personId" value=""/>
                        <input type="hidden" name="sampleId" value=""/>
                        <input type="hidden" name="operateType" value=""/>
                        <input type="hidden" name="tableRownum" value=""/>
                        <input type="hidden" name="category" value=""/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<form>
    <div class="modal fade" id="suspectedSampleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog repo" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <p class="moTitle">添加/编辑疑似在逃人员物品信息</p>
                    <div class="infoName">
                        <div class="reNamesr">
                            <span>物证检材信息</span>
                        </div>
                    </div>
                    <div class="infoBody">
                        <div class="crewss">
                            <div class="layui-upload usert">
                                <p class="perImgs">检材照片</p>
                                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                                    <div class="layui-upload-list" id="demo5"></div>
                                </blockquote>
                                <div id="test5"><span class="adds">+</span><span>添加图片</span></div>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">物证（检材）名称</p>
                                <p class="inpo">
                                    <input type="text" class="form-control" id="sampleNameSuspected" name="sampleNameSuspected" placeholder="请输入名称">
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">检材类型</p>
                                <p class="inpo">
                                    <select class="form-control" required id="sampleTypeSuspected" name="sampleTypeSuspected">
                                        <option value="" disabled selected hidden>请选择检材类型</option>
                                        <c:forEach items="${sampleTypeList}" var="sampleList">
                                            <option value="${sampleList.dictCode}">${sampleList.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">提取时间</p>
                                <p class="inpo">
                                    <input type="text" class="form-control form_datetime" placeholder="请输入提取时间"
                                           id="extractTimeSuspected" name="extractTimeSuspected" readonly>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">提取方法</p>
                                <p class="inpo">
                                    <select class="form-control" required id="extractMethodSuspected" name="extractMethodSuspected">
                                        <c:forEach items="${extractMethodList}" var="list">
                                            <option value="${list.dictCode}"
                                                    <c:if test="${list.dictCode eq sampleInfo.extractMethod}">selected</c:if>>${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname"><i class="lettd" style="padding: 0px 46px;"></i></p>
                                <p class="inpo">
                                    <select class="form-control" required id="sampleCarrierSuspected" name="sampleCarrierSuspected">
                                        <c:forEach items="${sampleCarrierList}" var="list">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">包装方法</p>
                                <p class="inpo">
                                    <select id="samplePackingSuspected" name="samplePackingSuspected" class="form-control" required>
                                        <option value="">请选包装方法</option>
                                        <c:forEach items="${packMethodList}" var="packMethod">
                                            <option value="${packMethod.dictCode}">${packMethod.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <div class="perInp">
                                <p class="perInpname1">检材描述</p>
                                <p class="inpo">
                                    <textarea placeholder="请输入描述" class="form-control" id="sampleDescribeSuspected"
                                              name="sampleDescribeSuspected" rows="4"></textarea>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="btns">
                        <button type="button" id="sampleBtn">保存</button>
                        <button type="button" data-target="#myModal2" data-dismiss="modal">关闭</button>
                        <input type="hidden" name="sampleId" value=""/>
                        <input type="hidden" name="operateTypeSuspected" value=""/>
                        <input type="hidden" name="tableRownumSuspected" value=""/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<form id="saveform" enctype="multipart/form-data" method="post" >
    <input type="hidden" name="orgname" value="${orgInfo.orgQualification}">
    <input type="hidden" name="orgAddressName" value="${orgInfo.orgAddress}"/>
    <input type="hidden" name="orgContactPhoneName" value="${orgInfo.orgContactPhone}"/>
    <input type="hidden" name="orgCodeName" value="${orgInfo.orgId}">
    <div class="box">
        <div class="fixeds">
            <button class="suer saveBoxBtn" type="button" name="saveInfo">确认无误，提交</button>
            <button class="backs" type="button" onclick="window.history.go(-1);">返回上一步</button>
        </div>
        <div class="record">
            <div class="recordName">
                <span>委托信息</span>
                <span><img src="<%=path%>/img/fan.png" alt=""></span>
                <div class="form-group lays">
                    <label>委托单位 :</label>
                    <input type="hidden" id="consignmentId" value="${consignmentInfo.consignmentId}">
                    <input type="hidden" id="appendFlag" value="${appendFlag}">
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
                                            <c:if test="${consignmentInfo.areaOrgCode eq list.orgCode}">selected</c:if>>${list.orgName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group lays">
                    <label for="exampleInputPassword1s">委托书编号</label>
                    <input type="text" class="form-control" id="exampleInputPassword1s"  name= "consignmentNo" placeholder="请输入编号"
                           value="${consignmentInfo.consignmentNo}">
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
                                                            <c:if test="${consignmentInfo.delegator1Id eq list.personalId}">selected</c:if>>${list.fullName}</option>
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
                                                            <c:if test="${consignmentInfo.delegator1Position eq list.dictCode}">selected</c:if>>${list.dictName}</option>
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
                                               value="${consignmentInfo.delegator1PaperworkNo}">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>电话</label>
                                        <input name="delegator1PhoneNumber" id="delegator1PhoneNumber"
                                               type="text" class="form-control" name="clientPhone"
                                               value="${consignmentInfo.delegator1PhoneNumber}">
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
                                                            <c:if test="${consignmentInfo.delegator2Id eq list.personalId}">selected</c:if>>${list.fullName}</option>
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
                                                            <c:if test="${consignmentInfo.delegator2Position eq list.dictCode}">selected</c:if>>${list.dictName}</option>
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
                                               value="${consignmentInfo.delegator2PaperworkNo}">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group  titl">
                                        <label>电话</label>
                                        <input name="delegator2PhoneNumber" id="delegator2PhoneNumber"
                                               type="text" class="form-control" name="clientPhone"
                                               value="${consignmentInfo.delegator2PhoneNumber}">
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
                                <li class="pull-left identicalIdentification" value="01">同一鉴定
                                    <input type="checkbox" name="identifyRequirement" checked img class="pig" src="<%=path%>/img/dui.png" alt="">
                                </li>
                                <li class="pull-left geneticIdentification" value="02">亲缘鉴定
                                    <input type="checkbox" name="identifyRequirement" img class="pig" src="<%=path%>/img/dui.png" alt="">
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="record">
            <p class="recordName">在逃人员信息</p>
            <div class="runPer">
                <div class="perLeft">
                    <div class="maxLeft">
                        <div class="perInfo">
                            <p class="perOne">姓名</p>
                            <p class="perTwo">${fugitivesInfo.personName}</p>
                            <input type="hidden" id="fugitivesId" name="fugitivesId" value="${fugitivesInfo.id}">
                        </div>
                        <div class="perInfo">
                            <p class="perOne">民族</p>
                            <p class="perTwo">${fugitivesInfo.nation}</p>
                        </div>
                    </div>
                    <div class="maxLeft">
                        <div class="perInfo">
                            <p class="perOne">性别</p>
                            <p class="perTwo">${fugitivesInfo.personGenderName}</p>
                        </div>
                        <div class="perInfo">
                            <p class="perOnes">证件号码</p>
                            <p class="perTwo">${fugitivesInfo.personCard}</p>
                        </div>
                    </div>
                    <div class="maxLeft">
                        <div class="perInfo">
                            <p class="perOne">籍贯</p>
                            <p class="perTwo">${fugitivesInfo.nativePlace}</p>
                        </div>
                    </div>
                </div>
                <div class="perRight">
                    <div class="perInfo">
                        <p class="perOnet">涉案信息</p>
                        <p class="perTwos">
                            <input type="hidden" id="caseId" value="${caseInfo.caseId}">
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="4" readonly>${fugitivesInfo.involvedName}</textarea>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="recordl">
            <div class="recordNames">
                <span>在逃人员亲属信息</span>
                <button type="button" class="addt" id="openAddFugitivesBig">添加</button>
            </div>
            <div class="tabs">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>身份证号</th>
                        <th>亲缘身份</th>
                        <th>检材类型</th>
                        <th>检材名称</th>
                        <th>提取时间</th>
                        <th>包装方法</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="fugitivesInfoRelationTbody">
                    <c:forEach items="${fugitivesInfoRelationList}" var="list" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${list.personInfo.personName}</td>
                            <td>${list.personInfo.personGenderName}</td>
                            <td>${list.personInfo.personIdCard}</td>
                            <td>${list.personInfo.relationTypeName}</td>
                            <td>${list.sampleTypeName}</td>
                            <td>${list.sampleName}</td>
                            <td><fmt:formatDate value='${list.extractDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${list.samplePackingName}</td>
                            <c:if test="${consignmentInfo.status != '02'&&consignmentInfo.status != '03'}">
                                <td>
                                    <button type="button" name="editBtn"
                                            class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                    </button>
                                    <button type="button" name="delBtn"
                                            class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                    </button>
                                    <input type="hidden" name="personId" value="${list.personInfo.personId}">
                                    <input type="hidden" name="personName" value="${list.personInfo.personName}">
                                    <input type="hidden" name="idCardFlag" value="${list.personInfo.idCardFlag}">
                                    <input type="hidden" name="personGender" value="${list.personInfo.personGender}">
                                    <input type="hidden" name="idCard" value="${list.personInfo.personIdCard}">
                                    <input type="hidden" name="address" value="${list.personInfo.personCurrentAddress}">
                                    <input type="hidden" name="certificateType" value="${list.personInfo.certificateType}">
                                    <input type="hidden" name="nation" value="${list.personInfo.nation}">
                                    <input type="hidden" name="height" value="${list.personInfo.personHeight}">
                                    <input type="hidden" name="weight" value="${list.personInfo.personWeight}">
                                    <input type="hidden" name="kinship" value="${list.personInfo.relationType}">
                                    <input type="hidden" name="remark" value="${list.sampleRemark}">
                                    <input type="hidden" name="sampleType" value="${list.sampleType}">
                                    <input type="hidden" name="sampleName" value="${list.sampleName}">
                                    <input type="hidden" name="extractMethod" value="${list.extractMethod}">
                                    <input type="hidden" name="sampleCarrier" value="${list.sampleCarrier}">
                                    <input type="hidden" name="samplePacking" value="${list.samplePacking}">
                                    <input type="hidden" name="sampleDescribe" value="${list.sampleDesc}">
                                    <input type="hidden" name="extractTime" value="<fmt:formatDate value='${list.extractDatetime}' pattern='yyyy-MM-dd'/>">
                                    <input type="hidden" name="sampleId" value="${list.sampleId}">
                                    <input type="hidden" name="samplePurpose" value="${list.samplePurpose}">
                                    <input type="hidden" name="sampleDnaPicture" value="${list.sampleDnaPicture}">
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="recordl">
            <div class="recordNames">
                <span>疑似在逃人员信息</span>
                <button type="button" class="addt" id="openAddSuspectFugitives">添加</button>
            </div>
            <div class="tabs">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>身份证号</th>
                        <th>检材类型</th>
                        <th>检材名称</th>
                        <th>提取时间</th>
                        <th>包装方法</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="fugitivesInfoSuspectedTbody">
                    <c:forEach items="${fugitivesInfoSuspectedList}" var="list" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${list.personInfo.personName}</td>
                            <td>${list.personInfo.personGenderName}</td>
                            <td>${list.personInfo.personIdCard}</td>
                            <td>${list.sampleTypeName}</td>
                            <td>${list.sampleName}</td>
                            <td><fmt:formatDate value='${list.extractDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${list.samplePackingName}</td>
                            <c:if test="${consignmentInfo.status != '02'&&consignmentInfo.status != '03'}">
                                <td>
                                    <button type="button" name="editBtn"
                                            class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                    </button>
                                    <button type="button" name="delBtn"
                                            class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                    </button>
                                    <input type="hidden" name="personId" value="${list.personInfo.personId}">
                                    <input type="hidden" name="personName" value="${list.personInfo.personName}">
                                    <input type="hidden" name="idCardFlag" value="${list.personInfo.idCardFlag}">
                                    <input type="hidden" name="personGender" value="${list.personInfo.personGender}">
                                    <input type="hidden" name="idCard" value="${list.personInfo.personIdCard}">
                                    <input type="hidden" name="address" value="${list.personInfo.personCurrentAddress}">
                                    <input type="hidden" name="certificateType" value="${list.personInfo.certificateType}">
                                    <input type="hidden" name="nation" value="${list.personInfo.nation}">
                                    <input type="hidden" name="height" value="${list.personInfo.personHeight}">
                                    <input type="hidden" name="weight" value="${list.personInfo.personWeight}">
                                    <input type="hidden" name="kinship" value="${list.personInfo.relationType}">
                                    <input type="hidden" name="remark" value="${list.sampleRemark}">
                                    <input type="hidden" name="extractTime" value="<fmt:formatDate value='${list.extractDatetime}' pattern='yyyy-MM-dd'/>">
                                    <input type="hidden" name="sampleId" value="${list.sampleId}">
                                    <input type="hidden" name="sampleType" value="${list.sampleType}">
                                    <input type="hidden" name="sampleName" value="${list.sampleName}">
                                    <input type="hidden" name="extractMethod" value="${list.extractMethod}">
                                    <input type="hidden" name="sampleCarrier" value="${list.sampleCarrier}">
                                    <input type="hidden" name="samplePacking" value="${list.samplePacking}">
                                    <input type="hidden" name="sampleDescribe" value="${list.sampleDesc}">
                                    <input type="hidden" name="samplePurpose" value="${list.samplePurpose}">
                                    <input type="hidden" name="sampleDnaPicture" value="${list.sampleDnaPicture}">
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="recordls">
            <div class="recordNames">
                <span>疑似在逃人员物品信息</span>
                <button type="button" class="addt" id="suspectedFugitivesSample">添加</button>
            </div>
            <div class="tabs">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>物证（检材）名称</th>
                        <th>检材类型</th>
                        <th>提取时间</th>
                        <th>包装方法</th>
                        <th>是否中心现场提取</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="suspectedSampleTbody">
                    <c:forEach items="${sampleInfoDnaList}" var="sampleInfoDna" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${sampleInfoDna.sampleName}</td>
                            <td>${sampleInfoDna.sampleTypeName}</td>
                            <td><fmt:formatDate value='${sampleInfoDna.extractDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${sampleInfoDna.samplePackingName}</td>
                            <td>
                                <div class="victimSelect">
                                    <select class="form-control" name="coreTakenStats" id="coreTakenStats"  required>
                                        <option value="1" <c:if test="${sampleInfoDna.coreVictimStats == 1 || sampleInfoDna.coreVictimStats == 2}">selected</c:if>>是</option>
                                        <option value="0"<c:if test="${'0' eq sampleInfoDna.coreVictimStats}">selected</c:if>>否</option>
                                    </select>
                                </div>
                            </td>
                            <c:if test="${consignmentInfo.status != '02'&&consignmentInfo.status != '03'}">
                                <td>
                                    <input type="hidden" name="sampleType" value="${sampleInfoDna.sampleType}">
                                    <input type="hidden" name="sampleName" value="${sampleInfoDna.sampleName}">
                                    <input type="hidden" name="sampleDescribe" value="${sampleInfoDna.sampleDesc}">
                                    <input type="hidden" name="samplePacking" value="${sampleInfoDna.samplePacking}">
                                    <input type="hidden" name="extractTime" value="<fmt:formatDate value='${sampleInfoDna.extractDatetime}' pattern='yyyy-MM-dd'/>">
                                    <input type="hidden" name="extractMethod" value="${sampleInfoDna.extractMethod}">
                                    <input type="hidden" name="sampleCarrier" value="${sampleInfoDna.sampleCarrier}">
                                    <input type="hidden" name="inspectionObjective" value="${sampleInfoDna.samplePurpose}">
                                    <input type="hidden" name="sampleId" value="${sampleInfoDna.sampleId}">
                                    <input type="hidden" name="linkId" value="${sampleInfoDna.linkId}">
                                    <button type="button" name="editBtn"
                                            class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑
                                    </button>
                                    <button type="button" name="delBtn"
                                            class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除
                                    </button>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>
<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/entrustCurrency.js"></script>
<script src="<%=path%>/js/fugitives.js"></script>
<script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script> <!-- LAYUI JS  -->
<script>
    $(function () {
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

        /**
         * 保存整个页面信息
         */
        var url = "<%=path%>/delegate/submitFugitivesDelegate";
        $("button[name='saveInfo']").on("click", function () {
            var form = $("#saveform ")

            var orgname = $(".orgname").find("input[name='orgname']").val();

            if ($("option:selected", "#areaOrgCode").val() == "") {
                alert("所属辖区不能为空！");
                return;
            }

            var evidence = getSampleInfoDna();
            for(var i = 0; i < evidence.length;i++){
                var coreTaken =  evidence[i].coreTakenStats;
                if(coreTaken == " " || coreTaken == null){
                    alert("是否为中心提取，为必填项，不能选空");
                    return false;
                }
            }

            var caseInfoDna = getCaseInfoDna();
            var fugitivesInfo = getFugitivesInfo();
            var limsConsignmentInfo = getConsignmentInfo();
            var sampleInfoDnaList = getSampleInfoDna();
            var limsPersonInfoList = getLimsPersonInfo();
            var evaluationCenter = $("input[name='orgCodeName']").val();
            var appendFlag = $("#appendFlag").val();

            console.log("personInfo"+limsPersonInfoList)

            var paramsData = {
                "orgname": orgname,
                "caseInfoDna": caseInfoDna,
                "fugitivesInfo": fugitivesInfo,
                "consignmentInfo": limsConsignmentInfo,
                "sampleInfoDnaList": sampleInfoDnaList,
                "limsPersonInfoList": limsPersonInfoList,
                "personIds": personIds,
                "sampleIds": sampleIds,
                "sampleIdWzs": sampleIdWzs,
                "evaluationCenter": evaluationCenter,
                "appendFlag": appendFlag
            };
            console.log(JSON.stringify(paramsData))

            $('#saveform').ajaxSubmit({
                url: url,
                type: "post",
                //contentType: "application/json",
                data: {"paramsData": JSON.stringify(paramsData)},
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        console.log(data.caseId)
                        console.log(data.consignmentId)
                        location.href = '<%=path%>/caseQuery/updateCaseWord?caseId=' + data.caseId + '&consignmentId=' + data.consignmentId;
                    } else if (data.success || data.success == false || data.success == "false") {
                        alert("保存失败！")
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        });

        //添加在逃人员亲属信息
        $("#openAddFugitivesBig").click(function () {
            $(".fugitivesModel").text("添加/编辑在逃人员亲属信息");
            //移出亲属关系下拉框
            $(".kinship").removeClass("hide");
            $("input[name='operateType']", "#fugitivesModel").val("add");
            $("input[name='tableRownum']", "#fugitivesModel").val(0);
            $("input[name='category']", "#fugitivesModel").val("relation");
            $("#fugitivesModel").modal('show');
        })

        //添加疑似在逃人员信息
        $("#openAddSuspectFugitives").click(function () {
            $(".fugitivesModel").text("添加/编辑疑似在逃人员信息");
            //隐藏亲属关系下拉框
            $(".kinship").addClass("hide");
            $("input[name='operateType']", "#fugitivesModel").val("add");
            $("input[name='tableRownum']", "#fugitivesModel").val(0);
            $("input[name='category']", "#fugitivesModel").val("suspected");
            $("#fugitivesModel").modal('show');
        })

        //添加亲属在逃人员
        $("#relationBtn").on("click",function(){
            addRelationPerson();
            $("#demo1").html("")
            $("#demo2").html("")
        })

        function addRelationPerson() {
            var category = $("input[name='category']", "#fugitivesModel").val();
            var personId = $("input[name='personId']", "#fugitivesModel").val();
            var personName = $("input[id='personName']", "#fugitivesModel").val();
            var personGender = $("input[name='personGender']:checked", "#fugitivesModel").val();
            var sexName;
            if (personGender == '01') {
                sexName = '男'
            } else if (personGender == '02') {
                sexName = '女'
            }
            var idCard = $("input[id='personIdCard']", "#fugitivesModel").val();
            var address = $("input[id='address']", "#fugitivesModel").val();
            var height = $("input[id='height']", "#fugitivesModel").val();
            var weight = $("input[id='weight']", "#fugitivesModel").val();
            var certificateType = $("select[id='certificateType']", "#fugitivesModel").find("option:selected").val();
            var nation = $("select[id='nation']", "#fugitivesModel").find("option:selected").val();
            var kinship = $("select[id='kinship']", "#fugitivesModel").find("option:selected").val();
            var kinshipName = $("select[id='kinship']", "#fugitivesModel").find("option:selected").text();
            var remark = $("textarea[id='remark']", "#fugitivesModel").val();
            var sampleType = $("select[id='sampleType']", "#fugitivesModel").find("option:selected").val();
            var sampleTypeName = $("select[id='sampleType']", "#fugitivesModel").find("option:selected").text();
            var sampleName = $("input[id='sampleName']", "#fugitivesModel").val();
            var extractTime = $("input[id='extractTime']", "#fugitivesModel").val();
            var extractMethod = $("select[id='extractMethod']", "#fugitivesModel").find("option:selected").val();
            var extractMethodName = $("select[id='extractMethod']", "#fugitivesModel").find("option:selected").text();
            var sampleCarrier = $("select[id='sampleCarrier']", "#fugitivesModel").find("option:selected").val();
            var sampleCarrierName = $("select[id='sampleCarrier']", "#fugitivesModel").find("option:selected").text();
            var samplePacking = $("select[id='samplePacking']", "#fugitivesModel").find("option:selected").val();
            var samplePackingName = $("select[id='samplePacking']", "#fugitivesModel").find("option:selected").text();
            var sampleDescribe = $("textarea[name='sampleDescribe']", "#fugitivesModel").val();
            var sampleId = $("input[name='sampleId']", "#fugitivesModel").val();

            var newRowHtml = "<td></td>";
            newRowHtml += "<td>" + personName + "</td>";
            newRowHtml += "<td>" + sexName + "</td>";
            newRowHtml += "<td>" + idCard + "</td>";
            //只有亲属的时候才有亲缘关系
            if ("relation" == category) {
                newRowHtml += "<td>" + kinshipName + "</td>";
            }
            newRowHtml += "<td>" + sampleTypeName + "</td>";
            newRowHtml += "<td>" + sampleName + "</td>";
            newRowHtml += "<td>" + extractTime + "</td>";
            newRowHtml += "<td>" + samplePackingName + "</td>";
            newRowHtml += "<td>";
            newRowHtml += "<input type='hidden' name='sampleId' value='" + sampleId + "'/>";
            newRowHtml += "<input type='hidden' name='personId' value='" + personId + "'/>";
            newRowHtml += "<input type='hidden' name='personName' value='" + personName + "'/>";
            newRowHtml += "<input type='hidden' name='personGender' value='" + personGender + "'/>";
            newRowHtml += "<input type='hidden' name='idCard' value='" + idCard + "'/>";
            newRowHtml += "<input type='hidden' name='address' value='" + address + "'/>";
            newRowHtml += "<input type='hidden' name='height' value='" + height + "'/>";
            newRowHtml += "<input type='hidden' name='weight' value='" + weight + "'/>";
            newRowHtml += "<input type='hidden' name='certificateType' value='" + certificateType + "'/>";
            newRowHtml += "<input type='hidden' name='nation' value='" + nation + "'/>";
            newRowHtml += "<input type='hidden' name='kinship' value='" + kinship + "'/>";
            newRowHtml += "<input type='hidden' name='remark' value='" + remark + "'/>";
            newRowHtml += "<input type='hidden' name='sampleType' value='" + sampleType + "'/>";
            newRowHtml += "<input type='hidden' name='sampleName' value='" + sampleName + "'/>";
            newRowHtml += "<input type='hidden' name='extractTime' value='" + extractTime + "'/>";
            newRowHtml += "<input type='hidden' name='extractMethod' value='" + extractMethod + "'/>";
            newRowHtml += "<input type='hidden' name='sampleCarrier' value='" + sampleCarrier + "'/>";
            newRowHtml += "<input type='hidden' name='samplePacking' value='" + samplePacking + "'/>";
            newRowHtml += "<input type='hidden' name='sampleDescribe' value='" + sampleDescribe + "'/>";
            newRowHtml += '<button type="button" name="editBtn" class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑';
            newRowHtml += '<button type="button" name="delBtn" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除';
            newRowHtml += "</td>";

            var operateType = $("input[name='operateType']", "#fugitivesModel").val();
            //判断是在逃人员亲属还是疑似在逃人员
            if ("relation" == category) {
                if ("add" == operateType) {
                    $("#fugitivesInfoRelationTbody").append("<tr>" + newRowHtml + "</tr>");
                } else {
                    var trIdx = $("input[name='tableRownum']", "#fugitivesModel").val();
                    $("tr:eq(" + trIdx + ")", "#fugitivesInfoRelationTbody").html(newRowHtml);
                }

                //更新在逃人员亲属序号
                generateRelationIdx();
                //在逃人员亲属编辑
                $("button[name='editBtn']", "#fugitivesInfoRelationTbody").on("click", function () {
                    $("input[name='category']", "#fugitivesModel").val("relation");
                    EditPersonRow(this);
                });
            }else {
                if ("add" == operateType) {
                    $("#fugitivesInfoSuspectedTbody").append("<tr>" + newRowHtml + "</tr>");
                } else {
                    var trIdx = $("input[name='tableRownum']", "#fugitivesModel").val();
                    $("tr:eq(" + trIdx + ")", "#fugitivesInfoSuspectedTbody").html(newRowHtml);
                }

                //更新疑似在逃人员序号
                generateSuspectedIdx();
                //疑似在逃人员编辑
                $("button[name='editBtn']", "#fugitivesInfoSuspectedTbody").on("click", function () {
                    $("input[name='category']", "#fugitivesModel").val("suspected");
                    EditPersonRow(this);
                });
            }
            $("#fugitivesModel").modal('hide');
        }

        //在逃人员亲属编辑
        $("button[name='editBtn']", "#fugitivesInfoRelationTbody").on("click", function () {
            $("input[name='category']", "#fugitivesModel").val("relation");
            EditPersonRow(this);
        });

        //疑似在逃人员编辑
        $("button[name='editBtn']", "#fugitivesInfoSuspectedTbody").on("click", function () {
            $("input[name='category']", "#fugitivesModel").val("suspected");
            EditPersonRow(this);
        });

        function EditPersonRow(obj) {
            var $curTR = $(obj).parents("tr");
            var person = {};
            person.sampleId = $("input[name='sampleId']", $curTR).val();
            person.personId = $("input[name='personId']", $curTR).val();
            person.personName = $("input[name='personName']", $curTR).val();
            person.personGender = $("input[name='personGender']", $curTR).val();
            person.idCard = $("input[name='idCard']", $curTR).val();
            person.address = $("input[name='address']", $curTR).val();
            person.height = $("input[name='height']", $curTR).val();
            person.weight = $("input[name='weight']", $curTR).val();
            person.certificateType = $("input[name='certificateType']", $curTR).val();
            person.nation = $("input[name='nation']", $curTR).val();
            person.kinship = $("input[name='kinship']", $curTR).val();
            person.remark = $("input[name='remark']", $curTR).val();
            person.sampleType = $("input[name='sampleType']", $curTR).val();
            person.sampleName = $("input[name='sampleName']", $curTR).val();
            person.extractTime = $("input[name='extractTime']", $curTR).val();
            person.extractMethod = $("input[name='extractMethod']", $curTR).val();
            person.sampleCarrier = $("input[name='sampleCarrier']", $curTR).val();
            person.samplePacking = $("input[name='samplePacking']", $curTR).val();
            person.sampleDescribe = $("input[name='sampleDescribe']", $curTR).val();

            var trIdx = $curTR.index();
            newPersonRow(person, "edit", trIdx);
        }
        function newPersonRow(person, operateType, rownum) {
            var category = $("input[name='category']", "#fugitivesModel").val();
            if ("relation" == category) {
                //移出亲属关系下拉框
                $(".kinship").removeClass("hide");
            }else {
                //隐藏亲属关系下拉框
                $(".kinship").addClass("hide");
            }
            $("input[name='sampleId']", "#fugitivesModel").val(person.sampleId);
            $("input[name='personId']", "#fugitivesModel").val(person.personId);
            $("input[name='personName']", "#fugitivesModel").val(person.personName);
            $("input[name='personGender'][value='"+person.personGender+"']", "#fugitivesModel").prop("checked", true);
            $("input[name='personIdCard']", "#fugitivesModel").val(person.idCard);
            $("input[name='address']", "#fugitivesModel").val(person.address);
            $("input[name='height']", "#fugitivesModel").val(person.height);
            $("input[name='weight']", "#fugitivesModel").val(person.weight);
            $("select[name='certificateType']", "#fugitivesModel").val(person.certificateType);
            $("select[name='nation']", "#fugitivesModel").val(person.nation);
            $("select[name='kinship']", "#fugitivesModel").val(person.kinship);
            $("textarea[id='remark']", "#fugitivesModel").val(person.remark);
            $("select[name='sampleType']", "#fugitivesModel").val(person.sampleType);
            $("input[name='sampleName']", "#fugitivesModel").val(person.sampleName);
            $("input[name='extractTime']", "#fugitivesModel").val(person.extractTime);
            $("select[name='extractMethod']", "#fugitivesModel").val(person.extractMethod);
            $("select[name='sampleCarrier']", "#fugitivesModel").val(person.sampleCarrier);
            $("select[name='samplePacking']", "#fugitivesModel").val(person.samplePacking);
            $("textarea[name='sampleDescribe']", "#fugitivesModel").val(person.sampleDescribe);
            $("input[name='operateType']", "#fugitivesModel").val(operateType);
            $("input[name='tableRownum']", "#fugitivesModel").val(rownum);

            $("#fugitivesModel").modal('show');
        }

        //处理在逃人员亲属序号
        function generateRelationIdx() {
            $("tr", "#fugitivesInfoRelationTbody").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });
        }
        //处理疑似在逃人员序号
        function generateSuspectedIdx() {
            $("tr", "#fugitivesInfoSuspectedTbody").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });
        }

        //在逃人员亲属信息关闭清空
        $("#fugitivesModel").on('hidden.bs.modal', function (e) {
            if ($("#fugitivesModel").find(".has-error").length > 0) {
                var form = $("#fugitivesModel").find("form")
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
            }
            $("#fugitivesModel").find("input[type='text']").val("")
            $("#fugitivesModel").find("input[type='hidden']").val("")
            $("#fugitivesModel").find("input[type='radio']:checked").prop("checked", false)
            $("#fugitivesModel").find("input[type='checkbox']:checked").prop("checked", false)
            $("#photoFile").val("")
            $("#fugitivesModel").find("select").val("")
            $("#fugitivesModel").find("textarea").val("")
            $("#fugitivesModel").find("select[name='extractMethod']").val("01")
            $("#fugitivesModel").find("select[name='sampleCarrier']").val("01")
            $("#fugitivesModel").find("select[name='samplePacking']").val("01")
        })

        //添加疑似在逃人员物品信息
        $("#suspectedFugitivesSample").click(function () {
            $("input[name='operateTypeSuspected']", "#suspectedSampleModal").val("add");
            $("input[name='tableRownumSuspected']", "#suspectedSampleModal").val(0);
            $("#suspectedSampleModal").modal('show');
        })
        //添加疑似在逃人员物品信息
        $("#sampleBtn").on("click",function(){
            addSuspectedSample();
        })
        function addSuspectedSample() {
            var sampleId = $("input[name='sampleId']", "#suspectedSampleModal").val();
            var sampleNameSuspected = $("input[id='sampleNameSuspected']", "#suspectedSampleModal").val();
            var sampleTypeSuspected = $("select[id='sampleTypeSuspected']", "#suspectedSampleModal").find("option:selected").val();
            var sampleTypeSuspectedName = $("select[id='sampleTypeSuspected']", "#suspectedSampleModal").find("option:selected").text();
            var extractTimeSuspected = $("input[id='extractTimeSuspected']", "#suspectedSampleModal").val();
            var extractMethodSuspected = $("select[id='extractMethodSuspected']", "#suspectedSampleModal").find("option:selected").val();
            var extractMethodSuspectedName = $("select[id='extractMethodSuspected']", "#suspectedSampleModal").find("option:selected").text();
            var sampleCarrierSuspected = $("select[id='sampleCarrierSuspected']", "#suspectedSampleModal").find("option:selected").val();
            var sampleCarrierSuspectedName = $("select[id='sampleCarrierSuspected']", "#suspectedSampleModal").find("option:selected").text();
            var samplePackingSuspected = $("select[id='samplePackingSuspected']", "#suspectedSampleModal").find("option:selected").val();
            var samplePackingSuspectedName = $("select[id='samplePackingSuspected']", "#suspectedSampleModal").find("option:selected").text();
            var sampleDescribeSuspected = $("textarea[id='sampleDescribeSuspected']", "#suspectedSampleModal").val();

            var newRowHtml = "<td></td>";
            newRowHtml += "<td>" + sampleNameSuspected + "</td>";
            newRowHtml += "<td>" + sampleTypeSuspectedName + "</td>";
            newRowHtml += "<td>" + extractTimeSuspected + "</td>";
            newRowHtml += "<td>" + samplePackingSuspectedName + "</td>";
            newRowHtml += '<td><div class="select">' +
                '<select name="coreTakenStats" class="coreTakenSelect form-control">' +
                '<option value="" selected></option><option value="0">否</option>' +
                '<option value="1">是</option></select></div></td>';
            newRowHtml += "<td>";
            newRowHtml += "<input type='hidden' name='sampleId' value='" + sampleId + "'/>";
            newRowHtml += "<input type='hidden' name='sampleName' value='" + sampleNameSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='sampleType' value='" + sampleTypeSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='extractTime' value='" + extractTimeSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='extractMethod' value='" + extractMethodSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='sampleCarrier' value='" + sampleCarrierSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='samplePacking' value='" + samplePackingSuspected + "'/>";
            newRowHtml += "<input type='hidden' name='sampleDescribe' value='" + sampleDescribeSuspected + "'/>";
            newRowHtml += '<button type="button" name="editBtn" class="btn-icon btn-icon-blue btn-icon-bianji-blue edit">编辑';
            newRowHtml += '<button type="button" name="delBtn" class="btn-icon btn-icon-red btn-icon-shanchu-red remove">删除';
            newRowHtml += "</td>";

            var operateType = $("input[name='operateTypeSuspected']", "#suspectedSampleModal").val();
            if ("add" == operateType) {
                $("#suspectedSampleTbody").append("<tr>" + newRowHtml + "</tr>");
            } else {
                var trIdx = $("input[name='tableRownumSuspected']", "#suspectedSampleModal").val();
                $("tr:eq(" + trIdx + ")", "#suspectedSampleTbody").html(newRowHtml);
            }
            //更新序号
            generateSampleIdx();

            $("button[name='editBtn']", "#suspectedSampleTbody").on("click", function () {
                EditSampleRow(this);
            });

            $("#suspectedSampleModal").modal('hide');
        }

        $("button[name='editBtn']", "#suspectedSampleTbody").on("click", function () {
            EditSampleRow(this);
        });

        function EditSampleRow(obj) {
            var $curTR = $(obj).parents("tr");
            var sample = {};
            sample.sampleId=$("input[name='sampleId']",$curTR).val();
            sample.sampleType = $("input[name='sampleType']", $curTR).val();
            sample.sampleName = $("input[name='sampleName']", $curTR).val();
            sample.extractTime = $("input[name='extractTime']", $curTR).val();
            sample.extractMethod = $("input[name='extractMethod']", $curTR).val();
            sample.sampleCarrier = $("input[name='sampleCarrier']", $curTR).val();
            sample.samplePacking = $("input[name='samplePacking']", $curTR).val();
            sample.sampleDescribe = $("input[name='sampleDescribe']", $curTR).val();

            var trIdx = $curTR.index();
            newSampleRow(sample, "edit", trIdx);
        }

        function newSampleRow(sample, operateType, rownum) {
            $("input[name='sampleId']","#suspectedSampleModal").val(sample.sampleId);
            $("select[name='sampleTypeSuspected']", "#suspectedSampleModal").val(sample.sampleType);
            $("input[name='sampleNameSuspected']", "#suspectedSampleModal").val(sample.sampleName);
            $("input[name='extractTimeSuspected']", "#suspectedSampleModal").val(sample.extractTime);
            $("select[name='extractMethodSuspected']", "#suspectedSampleModal").val(sample.extractMethod);
            $("select[name='sampleCarrierSuspected']", "#suspectedSampleModal").val(sample.sampleCarrier);
            $("select[name='samplePackingSuspected']", "#suspectedSampleModal").val(sample.samplePacking);
            $("textarea[name='sampleDescribeSuspected']", "#suspectedSampleModal").val(sample.sampleDescribe);
            $("input[name='operateTypeSuspected']", "#suspectedSampleModal").val(operateType);
            $("input[name='tableRownumSuspected']", "#suspectedSampleModal").val(rownum);
            $("#suspectedSampleModal").modal('show');
        }

        //处理疑似在逃人员物品序号
        function generateSampleIdx() {
            $("tr", "#suspectedSampleTbody").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });
        }
        //疑似在逃人员物品信息关闭清空
        $("#suspectedSampleModal").on('hidden.bs.modal', function (e) {
            if ($("#suspectedSampleModal").find(".has-error").length > 0) {
                var form = $("#suspectedSampleModal").find("form")
                form.data('bootstrapValidator').destroy();
                form.data('bootstrapValidator', null);
            }
            $("#suspectedSampleModal").find("input[type='text']").val("")
            $("#suspectedSampleModal").find("input[type='hidden']").val("")
            $("#suspectedSampleModal").find("input[type='radio']:checked").prop("checked", false)
            $("#suspectedSampleModal").find("input[type='checkbox']:checked").prop("checked", false)
            $("#photoFile").val("")
            $("#suspectedSampleModal").find("select").val("")
            $("#suspectedSampleModal").find("textarea").val("")
            $("#suspectedSampleModal").find("select[name='extractMethodSuspected']").val("01")
            $("#suspectedSampleModal").find("select[name='sampleCarrierSuspected']").val("01")
            $("#suspectedSampleModal").find("select[name='samplePackingSuspected']").val("01")
        })

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

        //鉴定要求
        if ('${consignmentInfo.identifyRequirement}' == "01") {
            $(".identicalIdentification").addClass("active").find("input").prop("checked", true)
        }
        if ('${consignmentInfo.identifyRequirement}' == "02") {
            $(".geneticIdentification").addClass("active").find("input").prop("checked", true)
        }
        if ('${consignmentInfo.identifyRequirement}' == "" || '${consignmentInfo.identifyRequirement}' == null || '${consignmentInfo.identifyRequirement}' == undefined ) {
            $(".geneticIdentification").addClass("active").find("input").prop("checked", true)
        }

        /*if ('${consignmentInfo.identifyRequirement}' == "") {
            $(".btn-checkbox").find("li[value='02']").addClass("active").find("input").prop("checked", true)
        } else {
            if ('${consignmentInfo.status}' == "01") {
                var btnCheckeds = '${consignmentInfo.identifyRequirement}'.split(",");
                var msg = "";
                for (var i = 1; i < btnCheckeds.length; i++) {
                    msg += btnCheckeds[i] + ",";
                }
                var btnChecked = msg.split(",")
                $.each(btnChecked, function (i, item) {
                    $(".btn-checkbox").find("li[value='" + item + "']").addClass("active").find("input").prop("checked", true)
                })
            } else {
                var btnCheckeds = '${consignmentInfo.identifyRequirement}'.split(",");
                var msg = "";
                for (var i = 0; i < btnCheckeds.length; i++) {
                    msg += btnCheckeds[i] + ",";
                }
                var btnChecked = msg.split(",")
                $.each(btnChecked, function (i, item) {
                    $(".btn-checkbox").find("li[value='" + item + "']").addClass("active").find("input").prop("checked", true)
                })
            }

        }*/

        layui.use('upload', function(){
            var $ = layui.jquery
                ,upload = layui.upload;
            //多图片上传
            upload.render({
                elem: '#test1'
                ,url: 'https://httpbin.org/post' //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo1').append('<div>'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</div>')
                    });
                }
                ,done: function(res){
                    //上传完毕
                }
            });
        });
        layui.use('upload', function(){
            var $ = layui.jquery
                ,upload = layui.upload;
            //多图片上传
            upload.render({
                elem: '#test2'
                ,url: 'https://httpbin.org/post' //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo2').append('<div>'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</div>')
                    });
                }
                ,done: function(res){
                    //上传完毕
                }
            });
        });
        layui.use('upload', function(){
            var $ = layui.jquery
                ,upload = layui.upload;
            //多图片上传
            upload.render({
                elem: '#test3'
                ,url: 'https://httpbin.org/post' //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo3').append('<div>'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</div>')
                    });
                }
                ,done: function(res){
                    //上传完毕
                }
            });
        });
        layui.use('upload', function(){
            var $ = layui.jquery
                ,upload = layui.upload;
            //多图片上传
            upload.render({
                elem: '#test4'
                ,url: 'https://httpbin.org/post' //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo4').append('<div>'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</div>')
                    });
                }
                ,done: function(res){
                    //上传完毕
                }
            });
        });
        layui.use('upload', function(){
            var $ = layui.jquery
                ,upload = layui.upload;
            //多图片上传
            upload.render({
                elem: '#test5'
                ,url: 'https://httpbin.org/post' //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo5').append('<div>'+'<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+'</div>')
                    });
                }
                ,done: function(res){
                    //上传完毕
                }
            });
        });
    })
</script>
</body>
</html>
