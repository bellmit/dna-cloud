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
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA案件委托送检系统</title>
    <%@ include file="linkCss.jsp" %>
</head>
<style>
    body {
        margin: 0;
        padding: 0;
        background: white;
    }

    .main {
        width: 100%;
        height: 100%;
        padding-top: 20px;
        display: flex;
        background: white;
        flex-direction: column;
    }

    .fast {
        width: 100%;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        padding-left: 21px;
        color: rgba(51, 51, 51, 1);
    }

    .entra {
        width: 100%;
        display: flex;
        justify-content: space-between;
    }

    .case {
        width: 33%;
        background-size: 100% 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #fff;
        position: relative;
        cursor: pointer;
    }

    .case > img {
        width: 100%;
    }

    .caseName {
        padding-left: 10px;
    }

    .cases {
        position: absolute;
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .entrust {
        width: 100%;
        display: flex;
        padding: 0 21px;
    }

    .entrust > div {
        flex: 1;
    }

    .entrust > div:nth-child(1) {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        line-height: 22px;
        color: rgba(51, 51, 51, 1);
    }

    .entrust > div:nth-child(2) {
        text-align: center;
        display: flex;
        justify-content: center;
    }

    .unitname {
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        line-height: 22px;
        color: rgba(153, 153, 153, 1);
    }

    .escape {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        line-height: 22px;
        color: rgba(51, 51, 51, 1);
        padding-left: 30px;
    }

    .entrust > div:nth-child(3) {
        text-align: center;
        display: flex;
        justify-content: flex-end;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        line-height: 22px;
        color: rgba(153, 153, 153, 1);
        padding-right: 30px;
    }

    .boxMax {
        flex: 1;
        display: flex;
        justify-content: space-around;
        padding: 0 15px;
    }

    .boxLeft {
        width: 49%;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 0px 11px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
    }

    .boxTops {
        width: 100%;
        height: 50%;
        display: flex;
        border-bottom: 1px dashed rgba(0, 0, 0, .3);
    }

    .boxBottom {
        width: 100%;
        height: 50%;
    }

    .boxRight {
        width: 49%;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 0px 11px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        display: flex;
        flex-direction: column;
    }

    .pois {
        position: absolute;
        bottom: 20px;
        left: 50%;
        margin-left: -50px;
        font-weight: 500;
    }

    .poiss {
        position: absolute;
        bottom: 20px;
        left: 50%;
        margin-left: -40px;
        font-weight: 500;
    }

    .boxlefty {
        flex: 1;
        position: relative;
    }

    .units {
        display: inline-block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        margin-right: 5px;
    }

    .quna {
        display: inline-block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        margin-right: 5px;
    }

    .clone {
        width: 100%;
        margin: 0;
        display: flex;
        justify-content: flex-end;
    }

    .utiName {
        width: 100%;
        display: flex;
        align-items: center;
        margin: 0;
    }

    .utiNamen {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        margin: 0;
        color: rgba(51, 51, 51, 1);
        padding-left: 10px;
    }

    .modal-body {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .maxMain {
        width: 70%;
    }

    .modal-content {
        width: 70%;
    }

    .conBox {
        width: 30%;
    }

    .conBoxs {
        width: 25%;
        display: flex;
        height: 100%;
    }

    .activet {
        width: 100%;
        border: 1px dashed rgba(163, 163, 163, 1);
        text-align: center;
        line-height: 35px;
        border-radius: 70px;
        margin-top: 10px;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: rgba(51, 51, 51, 1);
        position: relative;
    }

    .actu {
        position: absolute;
        right: -10px;
        top: -20px;
        display: none;
    }

    .utiNames {
        width: 100%;
        display: flex;
        align-items: center;
        margin: 0;
        margin-top: 30px;
    }

    .boxInp {
        margin-top: 10px;
    }

    .boxInp input {
        text-align: center;
        height: 40px !important;
    }

    .boxBtn {
        width: 100%;
        display: flex;
        justify-content: space-around;
        padding-bottom: 20px;
    }

    .btn1 {
        width: 130px;
        height: 42px;
        background: rgba(12, 129, 245, 1);
        border: 0;
        color: white;
        font-size: 15px;
        border-radius: 5px;
        outline: #0c81f5;
    }

    .btn2 {
        width: 130px;
        height: 42px;
        border: 1px solid rgba(12, 129, 245, 1);
        background: white;
        outline: #0c81f5;
        color: #0C81F5;
        font-size: 15px;
        border-radius: 5px;
    }

    .activet:hover {
        background: #3586FA;
        color: white;
        border: 1px solid #3586FA;
    }

    .warn {
        color: #FB6458;
        text-align: center;
    }

    .warn span {
        padding-left: 6px;
    }

    .srsele {
        margin: 10px 0;
    }

    .sili {
        display: flex;
        width: 100%;
        justify-content: space-between;
    }

    .wrand {
        margin: 0;
        color: #FB6458;
        font-size: 12px;
        display: flex;
        align-items: center;
    }

    .wrand::before {
        content: url("<%=path%>/img/warn (1).png");
        padding-top: 3px;
        padding-right: 5px;
    }

    .moBox {
        display: flex;
        width: 100%;
        justify-content: center;
    }

    .hint {
        color: rgba(51, 51, 51, 1);
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        display: flex;
        justify-content: center;
    }

    .hint::before {
        content: url("<%=path%>/img/warn (1).png");
        padding-top: 3px;
        padding-right: 5px;
        display: flex;
        justify-content: center;
    }

    .goPer {
        flex: 1;
        padding: 25% 0;
        text-align: center;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: rgba(255, 98, 77, 1);
    }

    .clientSelect {
        display: block;
    !important;
        position: absolute;
    }
</style>
<body>
<div class="main">
    <div class="fast">快速登记入口</div>
    <div class="entra">
        <div class="case" data-toggle="modal" data-target="#myModal1">
            <img src="<%=path%>/img/case.png" alt="">
            <div class="cases">
                <span><img src="<%=path%>/img/filds.png" alt=""></span>
                <span class="caseName">案件鉴定登记</span>
            </div>
        </div>
        <%--<!-- Modal -->--%>
        <%--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
            <%--<div class="modal-dialog" role="document">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-body">--%>
                        <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                        <%--<div class="maxMain">--%>
                            <%--<div class="utiName">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">选择鉴定中心</p>--%>
                            <%--</div>--%>
                            <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span>--%>
                            <%--</p>--%>
                            <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png"--%>
                                                                                  <%--alt=""></span></p>--%>
                            <%--<div class="utiNames">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                            <%--</div>--%>
                            <%--<p class="boxInp"><input type="text" class="form-control" placeholder="请出入勘察号"></p>--%>
                            <%--<div class="boxBtn">--%>
                                <%--<button class="btn1">确认</button>--%>
                                <%--<button class="btn2" data-dismiss="modal">非现场案件登记</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <!-- Modal1 -->
        <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>
                        <div class="maxMain">
                            <div class="utiName">
                                <img src="<%=path%>/img/ju.png" alt="">
                                <p class="utiNamen">选择鉴定中心</p>
                            </div>

                            <c:if test="${orgInfos != null}">
                                <ul class="btn-checkboxFugitive clearfix btn-checkbox-yellow">
                                    <c:forEach items="${orgInfos}" var="org" varStatus="s">

                                        <p class="activet">${org.orgQualification}
                                            <input type="radio" title="${org.orgQualification}" value="${org.orgId}"
                                                   class="hide"
                                                   name="orgQualificationFugitives"><span class="actu"><img
                                                    src="<%=path%>/img/dui.png" alt=""></span>
                                        </p>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <p class="boxInp"><input type="text" id="caseXkNo" name="caseXkNo" class="form-control"
                                                     placeholder="请输入勘查号"></p>
                            <div class="boxBtn">
                                <button class="btn2" id="saveBtn">案件登记</button>
                                <button class="btn2" id="noSaveBtn" name="noSaveBtn">非现场案件登记</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <%--<!-- Modal2 -->--%>
        <%--<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
            <%--<div class="modal-dialog" role="document">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-body">--%>
                        <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                        <%--<div class="maxMain">--%>
                            <%--<div class="utiName">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">选择鉴定中心</p>--%>
                            <%--</div>--%>
                            <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span>--%>
                            <%--</p>--%>
                            <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png"--%>
                                                                                  <%--alt=""></span></p>--%>
                            <%--<div class="utiNames">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                            <%--</div>--%>
                            <%--<p class="boxInp"><input type="text" class="form-control" id="exampleInputName3"--%>
                                                     <%--placeholder="请出入勘察号"></p>--%>
                            <%--<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该现场勘验号对应案件已经登记</span></p>--%>
                            <%--<div class="boxBtn">--%>
                                <%--<button class="btn2" data-dismiss="modal">返回重新登记</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--<!-- Modal3 -->--%>
        <%--<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
            <%--<div class="modal-dialog" role="document">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-body">--%>
                        <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                        <%--<div class="maxMain">--%>
                            <%--<div class="utiName">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">选择鉴定中心</p>--%>
                            <%--</div>--%>
                            <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span>--%>
                            <%--</p>--%>
                            <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png"--%>
                                                                                  <%--alt=""></span></p>--%>
                            <%--<c:if test="${orgInfos != null}">--%>
                                <%--<ul class="btn-checkboxFugitive clearfix btn-checkbox-yellow">--%>
                                    <%--<c:forEach items="${orgInfos}" var="org" varStatus="s">--%>

                                        <%--<p class="activet">${org.orgQualification}--%>
                                            <%--<input type="radio" title="${org.orgQualification}" value="${org.orgId}"--%>
                                                   <%--class="hide"--%>
                                                   <%--name="orgQualificationFugitives"><span class="actu"><img--%>
                                                    <%--src="<%=path%>/img/dui.png" alt=""></span>--%>
                                        <%--</p>--%>
                                    <%--</c:forEach>--%>
                                <%--</ul>--%>
                            <%--</c:if>--%>
                            <%--<div class="utiNames">--%>
                                <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                                <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                            <%--</div>--%>
                            <%--<p class="boxInp"><input type="text" class="form-control" id="exampleInputName2"--%>
                                                     <%--placeholder="请出入勘察号"></p>--%>
                            <%--<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该编号不存在</span></p>--%>
                            <%--<div class="boxBtn">--%>
                                <%--<button class="btn2" data-dismiss="modal">返回重新登记</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <!-- Modal4 -->
        <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog moBox" role="document">
                <div class="modal-content conBox">
                    <div class="modal-body">
                        <p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>
                        <div class="maxMain" style="width: 100%">
                            <div class="utiName">
                                <img src="<%=path%>/img/ju.png" alt="">
                                <p class="utiNamen">选择鉴定中心</p>
                            </div>
                            <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                            <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                            <c:if test="${orgInfos != null}">
                                <ul class="btn-checkboxFugitive clearfix btn-checkbox-yellow">
                                    <c:forEach items="${orgInfos}" var="org" varStatus="s">

                                        <p class="activet">${org.orgQualification}
                                            <input type="radio" title="${org.orgQualification}" value="${org.orgId}"
                                                   class="hide"
                                                   name="orgQualificationFugitives"><span class="actu"><img
                                                    src="<%=path%>/img/dui.png" alt=""></span>
                                        </p>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <%--<p class="activet">北京市海淀区公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>
                            <p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                            <div class="utiNames">
                                <img src="<%=path%>/img/ju.png" alt="">
                                <div class="sili">
                                    <p class="utiNamen">选择在逃人员信息</p>
                                    <p class="wrand">如未找到信息？</p>
                                </div>
                            </div>

                            <p class="boxInp">
                                <input type="text" id="searchFugitives" name="searchFugitives" class="form-control"
                                                     placeholder="请输入在逃名单">
                            </p>
                            <div id="checkBoxdiv" class="hidden">
                                <table class="table table-hover table-bordered bigTable">
                                    <thead>
                                    <tr>
                                        <th style="width: 70px;">序号</th>
                                        <th style="width: 100px;">姓名</th>
                                        <th>身份证号</th>
                                    </tr>
                                    </thead>
                                    <tbody id="checkBoxdivTbody">

                                    </tbody>
                                </table>
                                <%--<button class="btn btn-blue" id="checkBoxdivBtn" type="button">
                                    确定
                                </button>--%>

                            </div>
                            <%--<div class="select srsele">
                                <select class="form-control clientSelect" name="fugitives" id="fugitives">
                                    <option value="">请选人员姓名</option>
                                    <c:forEach items="${fugitivesInfoList}" var="list" varStatus="s">
                                        <option value="${list.id}">${list.personName}</option>
                                    </c:forEach>
                                </select>
                            </div>--%>
                            <%--<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该现场勘验号对应案件已经登记并受理</span></p>--%>
                            <div class="boxBtn">
                                <%--<label class="hidden error">请输入现勘编号</label>--%>
                                <button class="btn1" id="saveFugitivesBtn">开始登记</button>
                                <button class="btn2" data-dismiss="modal">关闭</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-content conBoxs" style="display: none">
                    <div class="modal-body">
                        <p class="clone colnes"><img src="<%=path%>/img/colse.png" alt=""></p>
                        <div class="maxMain">
                            <p class="hint">系统提示</p>
                            <p class="goPer">在逃人员信息由鉴定中心创建或导入，
                                请联系对应负责人！！</p>
                        </div>
                        <div class="boxBtn">
                            <%--<label class="hidden error">请输入现勘编号</label>--%>
                            <button class="btn1 btny">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="case" data-toggle="modal" data-target="#myModal4">
            <img src="<%=path%>/img/goto.png" alt="">
            <div class="cases">
                <span><img src="<%=path%>/img/emit.png" alt=""></span>
                <span class="caseName">在逃人员鉴定登记</span>
            </div>
        </div>
        <div class="case">
            <img src="<%=path%>/img/go-to.png" alt="">
            <div class="cases">
                <span><img src="<%=path%>/img/center.png" alt=""></span>
                <span class="caseName">送检预约</span>
            </div>
        </div>
    </div>
    <div class="entrust">
        <div>案件鉴定委托-汇总统计</div>
        <div><span class="unitname">单位／件</span>
            <p class="escape">在逃人员鉴定委托-汇总统计</p></div>
        <div>单位／个</div>
    </div>
    <div class="boxMax">
        <div class="boxLeft">
            <div class="boxTops">
                <div style="width: 50%;height: 100%" id="caseNum">
                </div>
                <div style="width: 50%;height: 100%" id="sampleNum">
                </div>
            </div>
            <div class="boxBottom">
                <div style="width: 100%;height: 100%" id="caseClassification"></div>
            </div>
        </div>
        <div class="boxRight">
            <div class="boxTops">
                <div class="boxlefty">
                    <div style="width: 100%;height: 100%" id="detectionRate"></div>
                    <p class="pois"><span class="units" style="background: #ff5a56;"></span>在逃人员总数</p>
                </div>
                <div class="boxlefty">
                    <div style="width: 100%;height: 100%" id="detectionRates"></div>
                    <p class="poiss"><span class="quna" style="background: #1BB29B;"></span>亲缘人员总数</p>
                </div>
            </div>
            <div class="boxBottom">
                <div style="width: 100%;height: 100%" id="sampleClassification"></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="linkJs.jsp" %>
<script src="<%=path%>/js/searchFugitives.js"></script>
<script>
    $(function () {

        $(".activet").on("click", function () {
            if (!$(this).hasClass("active")) {
                $(this).addClass("active").siblings().removeClass("active")
                $(this).find("input").prop("checked", true)
                $(this).siblings().find("input").prop("checked", false)
            }
        })

        $("#saveFugitivesBtn").on("click", function () {
            fugitivesReg();
        })

        $(document).keydown(function(event){
            if(event.keyCode==13){
                $("#saveFugitivesBtn").click();
            }
        });

    function fugitivesReg() {
            var orgId = $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").val() == undefined ? "" : $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").val();
            var orgQualification = $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").attr("title") == undefined ? "" : $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").attr("title");
            var fugitivesId = $('input:radio[name="checkOne"]:checked').val();

            if (fugitivesId == null || fugitivesId == "" || fugitivesId == undefined) {
                alert("请选择在逃人员！");
                return false;
            }

            location.href = "<%=path%>/delegate/fugitivesReg?orgId=" + orgId + "&fugitivesId=" + fugitivesId;
        }

        $(".clientSelect").searchableSelect();
        var caseNum = echarts.init(document.getElementById('caseNum'));
        console.log(caseNum)
        var caseNumOption = {
            title: [{
                text: ${caseCnt},
                subtext: '案件数',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontWeight: 'normal',
                    color: '#9bbe0f',
                    fontSize: '30'
                },
                subtextStyle: {
                    fontSize: 15,
                }
            }],
            legend: {
                selectedMode: false,
                itemWidth: 10,
                itemHeight: 10,
                itemGap: 10,
                show: true,
                bottom: 30,
                icon: "circle",
                borderRadius: '100',
                data: ['案件总数'],
                textStyle: {
                    fontSize: 14,
                    color: '#000'
                }
            },
            color: ['#d9d9d9'],
            series: [{
                name: 'Line 1',
                type: 'pie',
                clockWise: true,
                radius: ['45%', '55%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                hoverAnimation: false,
                data: [{
                    value: 70,
                    name: '案件总数',
                    itemStyle: {
                        normal: {
                            color: { // 完成的圆环的颜色
                                colorStops: [{
                                    offset: 0,
                                    color: '#9ec017' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#c7e93f' // 100% 处的颜色
                                }]
                            },
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '02',
                    value: 30
                }]
            }]
        };
        caseNum.setOption(caseNumOption);

        var sampleNum = echarts.init(document.getElementById('sampleNum'));
        var sampleNumOption = {
            title: {
                text: ${evidenceCnt},
                subtext: '检材数',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontWeight: 'normal',
                    color: '#346fec',
                    fontSize: '30'
                },
                subtextStyle: {
                    fontSize: 15,
                }
            },
            color: ['#d9d9d9'],
            legend: {
                selectedMode: false,
                itemWidth: 10,
                itemHeight: 10,
                itemGap: 10,
                show: true,
                bottom: 30,
                icon: "circle",
                borderRadius: '100',
                data: ['物证检材总数'],
                textStyle: {
                    fontSize: 14,
                    color: '#000'
                }
            },

            series: [{
                name: 'Line 1',
                type: 'pie',
                clockWise: true,
                radius: ['45%', '55%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                hoverAnimation: false,
                data: [{
                    value: 70,
                    name: '物证检材总数',
                    itemStyle: {
                        normal: {
                            color: {
                                colorStops: [{
                                    offset: 0,
                                    color: '#5d61fe'
                                }, {
                                    offset: 1,
                                    color: '#077fd8'
                                }]
                            },
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '02',
                    value: 30
                }]
            }]
        };
        sampleNum.setOption(sampleNumOption);

        var caseClassification = echarts.init(document.getElementById('caseClassification'));
        var caseClassificationOption = {
            grid: {
                left: '5%',
                right: '5%',
                bottom: '5%',
                top: '10%',
                height: '85%',
                containLabel: true,
                z: 22
            },
            xAxis: [{
                type: 'category',
                gridIndex: 0,
                boundaryGap: false,
                data: ['', '已送检\n\n委托数量', '已送检补\n\n送委托数量', '待送检\n\n委托数量', '待送检补\n\n送委托数量', '超时未送\n\n检委托数量', '已出鉴定\n\n书委托数量'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#999999'
                    }
                },
                axisLabel: {
                    interval: 0,//0：全部显示，1：间隔为1显示对应类目，2：依次类推，（简单试一下就明白了，这样说是不是有点抽象）
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#999', //x轴辅助线颜色
                        type: 'dashed'
                    }
                },
                boundaryGap: false,
            }, {
                type: 'category',
                data: ['', ${submissionCaseCnt}, ${bsSubmissionCaseCnt}, ${waitSubmissionCaseCnt}, ${waitBsCaseCnt}, ${timeOutcaseInfoCnt}, ${outAppraisalBookCnt}],
                axisLabel: {
                    show: true,
                    backgroundColor: '#f6fafe',
                    textStyle: {
                        color: "#1681F5",
                        padding: [4, 10, 4, 10],
                        borderRadius: 10,
                    },
//                    formatter: function (value) {
//                        if (value !== "") {
//                            return '{Sunny|' + value + '}';
//                        } else {
//                            return '{no|' + value + '}';
//                        }
//                    },
//                    rich: {
//                        value: {
//                            lineHeight: 30,
//                        },
//                        Sunny: {
//                            width: 20,
//                            height: 10,
//                            padding: 5,
//                            borderRadius: 40,
//                            align: 'center',
//                            backgroundColor: "#f6fafe",
//                            color: "#1681F5"
//                        },
//                        no: {
//                            width: 0,
//                            height: 0,
//                            padding: 0,
//                            borderRadius: 40,
//                            align: 'center',
//                            backgroundColor: "#000",
//                            color: "#FF5C45"
//                        }
//                    }
                },
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: 'transparent'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#999', //x轴辅助线颜色
                        type: 'dashed'
                    }
                },
                boundaryGap: false,
            }],
            yAxis: [{
                type: 'value',
                gridIndex: 0,
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#999', //x轴辅助线颜色
                        type: 'dashed'
                    }
                },
                axisLabel: {
                    color: '#999999'
                }
            },],
            series: [{
                name: '案件占比',
                type: 'bar',
                barWidth: '20%',
                xAxisIndex: 0,
                yAxisIndex: 0,
                itemStyle: {
                    normal: {
                        barBorderRadius: [10, 10, 0, 0],
                        color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                    offset: 0,
                                    color: '#00feff'
                                },
                                    {
                                        offset: 0.5,
                                        color: '#027eff'
                                    },
                                    {
                                        offset: 1,
                                        color: '#0286ff'
                                    }
                                ]
                        )
                    }
                },
                data: ['', ${submissionCaseCnt}, ${bsSubmissionCaseCnt}, ${waitSubmissionCaseCnt}, ${waitBsCaseCnt}, ${timeOutcaseInfoCnt}, ${outAppraisalBookCnt}],
            }]
        };
        caseClassification.setOption(caseClassificationOption);

        var detectionRate = echarts.init(document.getElementById('detectionRate'));
        var detectionRateOption = {
            title: {
                text: ${fugitivesCnt},
                subtext: '在逃人员总数',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontWeight: 'normal',
                    color: '#FF5A56',
                    fontSize: '30'
                },
                subtextStyle: {
                    fontSize: 15,
                }
            },
            color: ['#d9d9d9'],

            series: [{
                name: 'Line 1',
                type: 'pie',
                clockWise: true,
                radius: ['45%', '55%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                hoverAnimation: false,
                data: [{
                    value: 70,
                    name: '物证检材总数',
                    itemStyle: {
                        normal: {
                            color: {
                                colorStops: [{
                                    offset: 0,
                                    color: '#FEA19D'
                                }, {
                                    offset: 1,
                                    color: '#FF5A56'
                                }]
                            },
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '02',
                    value: 30
                }]
            }]
        };
        detectionRate.setOption(detectionRateOption);

        var detectionRates = echarts.init(document.getElementById('detectionRates'));
        var detectionRatesOption = {
            title: {
                text: ${relationPersonCnt},
                subtext: '亲缘人员总数',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontWeight: 'normal',
                    color: '#1BB29B',
                    fontSize: '30'
                },
                subtextStyle: {
                    fontSize: 15,
                }
            },
            color: ['#d9d9d9'],

            series: [{
                name: 'Line 1',
                type: 'pie',
                clockWise: true,
                radius: ['45%', '55%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                hoverAnimation: false,
                data: [{
                    value: 70,
                    name: '物证检材总数',
                    itemStyle: {
                        normal: {
                            color: {
                                colorStops: [{
                                    offset: 0,
                                    color: '#1BB29B'
                                }, {
                                    offset: 1,
                                    color: '#1BB29B'
                                }]
                            },
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '02',
                    value: 30
                }]
            }]
        };
        detectionRates.setOption(detectionRatesOption);
        var sampleClassification = echarts.init(document.getElementById('sampleClassification'));
        var sampleClassificationOption = {
            grid: {
                left: '5%',
                right: '5%',
                bottom: '5%',
                top: '10%',
                height: '85%',
                containLabel: true,
                z: 22
            },
            xAxis: [{
                type: 'category',
                gridIndex: 0,
                data: ['', '已送检\n\n委托数量', '已送检补\n\n送委托数量', '待送检\n\n委托数量', '待送检补\n\n送委托数量', '超时未送\n\n检委托数量', '已出鉴定\n\n书委托数量'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#999999'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#999', //x轴辅助线颜色
                        type: 'dashed'
                    }
                },
                boundaryGap: false,
            }, {
                type: 'category',
                data: ['', ${fugitivesCaseCnt}, ${bsFugitivesCaseCnt}, ${waitFugitivesCaseCnt}, ${waitBsFugitivesCnt}, ${timeOutFugitivesCnt}, ${selectCntByAppraisalBook}],
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#000",
                    },
                    formatter: function (value) {
                        if (value !== "") {
                            return '{Sunny|' + value + '}';
                        } else {
                            return '{no|' + value + '}';
                        }
                    },
                    rich: {
                        value: {
                            lineHeight: 30,
                        },
                        Sunny: {
                            width: 50,
                            height: 15,
                            padding: 5,
                            borderRadius: 40,
                            align: 'center',
                            backgroundColor: "#fff9f8",
                            color: "#FF5C45"
                        },
                        no: {
                            width: 0,
                            height: 0,
                            padding: 0,
                            borderRadius: 40,
                            align: 'center',
                            backgroundColor: "#000",
                            color: "#FF5C45"
                        }
                    }
                },
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: 'transparent'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#999', //x轴辅助线颜色
                        type: 'dashed'
                    }
                },
                boundaryGap: false
            }],
            yAxis: [{
                type: 'value',
                gridIndex: 0,
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#999999'
                    }
                },
                axisLabel: {
                    color: '#999999'
                }
            }],
            series: [{
                name: '案件占比',
                type: 'bar',
                barWidth: '20%',
                xAxisIndex: 0,
                yAxisIndex: 0,
                itemStyle: {
                    normal: {
                        barBorderRadius: [10, 10, 0, 0],
                        color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [
                                    {
                                        offset: 0,
                                        color: '#FFDB45'
                                    }, {
                                        offset: 1,
                                        color: '#FF5A45'
                                    }
                                ]
                        )
                    }
                },
                data: ['', ${fugitivesCaseCnt}, ${bsFugitivesCaseCnt}, ${waitFugitivesCaseCnt}, ${waitBsFugitivesCnt}, ${timeOutFugitivesCnt}, ${selectCntByAppraisalBook}],
            }]
        };
        sampleClassification.setOption(sampleClassificationOption);
        var oUl = document.getElementsByClassName('activet')
        var oUls = document.getElementsByClassName('actu')
        for (var i = 0; i < oUl.length; i++) {
            oUl[i].index = i
            oUl[i].onclick = function () {
                for (let j = 0; j < oUl.length; j++) {
                    $('.activet').eq(j).find('.actu').css('display', 'none')
                    $('.activet').eq(j).css({'background': 'white', color: 'black'})
                }
                $('.activet').eq(this.index).css({'background': '#0C81F5', 'color': 'white'})
                $('.activet').eq(this.index).find('.actu').css('display', 'block')
            }
        }

        //输入现堪号点击确认
        $("#saveBtn").on("click", function () {
            var caseXkNo = $("#caseXkNo").val();
            if (caseXkNo == "") {
                // $("#siteSurveyBox").find(".modal-footer").next().slideDown().html("<span>现勘编号不能为空</span>")
                alert("勘查号不能为空！");
            } else {
                var orgId = $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").val() == undefined ? "" : $(".btn-checkboxFugitive").find(".active").find("input[name='orgQualificationFugitives']").val();
                var orgQualification = $(".btn-checkbox").find(".active").find("input[name='orgQualification']").attr("title") == undefined ? "" : $(".btn-checkbox").find(".active").find("input[name='orgQualification']").attr("title");

                if (orgId == null || orgId == "" || orgId == undefined) {
                    alert("请选择鉴定中心！");
                    return false;
                }

                $.ajax({
                    url: "<%=path%>/delegate/validateXkNo?caseXkNo=" + caseXkNo + "&orgId=" + orgId,
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success == true) {
                            location.href = "<%=path%>/delegate/dnaReg.html?xkNo=" + caseXkNo + "&orgId=" + orgId;
                        } else {
                            $("#siteSurveyBox").find(".modal-footer").next().slideDown().html("<span>该现勘编号已送检" + orgQualification + "</span>")
                        }
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            }

        });
        $(".btny,.colnes").on('click', function () {
            $(".conBoxs").fadeOut()
        })


        $("button[name='noSaveBtn']").on("click", function () {
            location.href = '<%=path%>/delegate/noDnaRegs';
        });

        $('.wrand').on('click', function () {
            $(".conBoxs").fadeIn()
        })
    })
</script>
</body>

</html>


