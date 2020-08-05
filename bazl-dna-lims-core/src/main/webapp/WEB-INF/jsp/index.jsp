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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>DNA实验室信息管理系统</title>
    <link rel="shortcut icon" href="<%=path%>/img/favicon.ico" type="image/x-icon"/>
    <%@ include file="linkCss.jsp" %>
    <style>
        html,
        body {
            width: 100%;
            height: 100%;
            padding: 0px;
            margin: 0px;
        }

        header {
            z-index: 1;
        }

        main {
            height: 99%;
            padding-top: 57px;
        }

        main > .leftTab {
            min-height: 100%;
            background: url("<%=path%>/img/leftBar.png") no-repeat;
            background-size: cover;
            position: absolute;
            width: 190px;
            padding-top: 57px;
            top: 0px;
        }

        iframe {
            background: #ffffff;
            padding-left: 190px;
        }

        .leftTab .panel-group .panel {
            border-radius: 0px;
            background: transparent;
            border: none;
            border-bottom: 1px solid #405778;
        }

        .leftTab .panel-group .panel + .panel {
            margin-top: 0px;
        }
        .leftTab activ{
            background: ;
        }
        .leftTab .panel-heading {
            padding: 0px;
            background: transparent;
        }

        .leftTab .panel-heading a {
            padding: 10px 15px;
            display: block;
            color: #fff;
            height: 42px;
            line-height: 22px;
            position: relative;
            text-decoration: none;
        }

        .leftTab .panel-heading a i {
            float: right;
            margin-top: 3px;
        }

        .leftTab .panel-heading a:hover,
        .leftTab .panel.active .panel-heading a {
            background: linear-gradient(to right, #0957e9, #0ffcfa);
        }

        .leftTab .accordion-body a:hover,
        .leftTab .panel.active .ablue {
            background: linear-gradient(to right, #0957e9, #0ffcfa);
        }

        .leftTab .panel-heading a:hover,
        .leftTab .panel.active a {
            /*background: linear-gradient(to right, #0957e9, #0ffcfa);*/
        }

        .accordion-inner a {
            text-decoration: none;
            width: 100%;
            display: block;
            color: #fff;
            padding-left: 30px;
            position: relative;
            height: 42px;
            line-height: 42px;
            /*background: linear-gradient(to right, rgba(9, 87, 233, .5), rgba(15, 252, 520, .5));*/
        }

        .accordion-inner a:before {
            content: '';
            display: inline-block;
            width: 8px;
            height: 8px;
            background: gray;
            border-radius: 50%;
            position: absolute;
            left: 15px;
            top: 16px;
        }

        .accordion-inner a:hover {
            background: #036d97;
        }

        .accordion-inner a:hover:before,
        .ablue::before {
            background: #fff !important;
        }

        .ablue {
            background: #04214b;
        }

        .er {
            background: gainsboro;
        }

        .accordion-innerx1 a {
            width: 100%;
            display: block;
            height: 42px;
            line-height: 42px;
            padding-left: 30px;
            position: relative;
            color: #fff;
            text-decoration: none;
            background: linear-gradient(to right, rgba(9, 87, 233, .5), rgba(15, 252, 520, .5));
        }

        .accordion-innerx1 a:before {
            content: '';
            display: inline-block;
            width: 8px;
            height: 8px;
            background: gray;
            border-radius: 50%;
            position: absolute;
            left: 15px;
            top: 16px;
        }

        .accordion-innerx1 a:hover {
            background: #04214b;
        }

        .accordion-innerx1 a:hover:before,
        .ablue::before {
            background: #fff !important;
        }


        .leftTab .accordion-body a:hover, .leftTab .panel.active .ablue {
             background:#1b6d85;
        }
        #bs-example-navbar-collapse-1 li:nth-child(1){
            color: #fff;
            font-size: 18px;
            font-weight: 400;
            margin-top: 5px;
        }

        .bottomdiv{
            width: 39px;
            line-height: 18px;
            letter-spacing: 7px;
            color: #ff5a56;
            background: #fff5f5;
            border: 1px solid #ff5a56;
            position: fixed;
            text-align: center;
            bottom: 340px;
            right: 0;
            border-radius: 3px;
            padding: 5px;
            cursor: pointer;
        }
        .read{
            letter-spacing: 0;
            margin: 0;
        }
        .textdiv{
            height: 300px;
            width: 300px;
            background: white;
            position: fixed;
            bottom: 20px;
            right: 20px;
            border: 1px solid #00ceff;
            border-radius: 5px;
            overflow: hidden;

        }
        .textdiv button{
            position: absolute;
            right: 2px;
            top: 2px;
            border: none;
            background: #f3f8ff;
            color: #ffb72c;
            height: 25px;
            line-height: 25px;
            width: 40px;
        }
        .textlist{
            height: 245px;
            overflow-y: auto;
        }
        .title{
            height: 35px;
            background: #f3f8ff;
            text-align: center;
            line-height: 35px;
            color: #1879df;
            font-size: 16px;
        }
        .noedtext{
            margin: 5px 10px;
            color: #1bb29b;
            border-bottom: 1px dashed #1bb29b;
        }
        .noedtext input{
            float: right;
            border: none;
            background: #2ab7a2;
            color: white;
            width: 40px;
            border-radius: 5px;
        }
        .atext{
            text-decoration: underline;
            display: block;
            cursor: pointer;
        }
        div.dnaLabNameDiv{
            float: right;
            margin: 10px 15px 10px 30px;
            font-weight: bold;
            font-size: 20px;
            color: #FFFF00;
        }

        .mess{
            margin: 0;
            padding-left: 20px;
        }
        .messs a img{
            width: 25px;
        }
        .messs{
            position: relative;
        }
        .nums{
            position: absolute;
            width:19px;
            height:19px;
            border-radius:50%;
            background: #FF0000;
            text-align: center;
            line-height: 19px;
            top: 0;
            left: 20px;
        }
        .users{
            height: 100%;
        }
        .users a{
            height: 100%;
        }
        .panel-title li{
            display: flex;
            width: 100%;
        }
        .panel-title li a{
            display: flex;
            display: inline-block;
            padding-left: 10px;
            width: 100%;
        }
        .container-fluid{
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .container-fluid::before{
            content: normal !important;
        }
        .container-fluid::after{
            content: normal !important;
        }
    </style>
</head>

<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">
                    <p>北京市公安局DNA智慧实验室信息管理系统</p>
                    <p>DNA intelligent laboratory information management system of Beijing Public Security Bureau</p>
                </a>
                <div class="dnaLabNameDiv">【${dnaLabName}】</div>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navst navbar-nav navbar-right">
                    <li class="messs">
                        <a href="#" class="dropdown-toggle header-nav-img" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <img src="<%=path%>/img/mess.png" alt="">
                            <p class="mess">消息</p>
                        </a>
                        <p class="nums">5</p>
                    </li>
                    <li class="dropdown users">
                        <a href="#" class="dropdown-toggle header-nav-img" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <img src="<%=path%>/img/touxiang1.png" alt="">（${user.amPersonalInfo.fullName}）
                    </a>
                    <ul class="dropdown-menu">
                        <span class="caret"></span>
                        <li><a href="<%=path%>/manage/personalInformation" target="ifm">个人信息</a></li>
                    </ul>
                    </li>
                    <li class="messs">
                        <a href="<%=path%>/loginOut" class="dropdown-toggle header-nav-img" role="button"
                           aria-haspopup="true" aria-expanded="false" >
                            <img src="<%=path%>/img/out.png" alt="">
                            <p class="mess">退出</p>
                        </a>
                    </li>
                </ul>
                <%--
                <ul class="nav navbar-nav navbar-right hidden">
                    <li class="active"><a href="./home.html" target="ifm">DNA案件</a></li>
                    <li><a href="./identificationStatusQuery/identificationStatusQuery.html" target="ifm">毒化案件</a></li>
                    <li><a href="./principalManagement/principalManagement.html" target="ifm">病理案件</a></li>
                    <li><a href="./principalManagement/principalManagement.html" target="ifm">临床案件</a></li>
                </ul>
                --%>
            </div>
            <div class="bottomdiv">
                <img src="<%=path%>/img/TG.png" alt="">
                消息<p class="read">(0)</p>
            </div>
            <div class="textdiv" style="display: none">
                <button>关闭</button>
                <div class="title">
                    <div>消息提示</div>
                </div>
                <div class="textlist">

                </div>
                <%--<div class="noedtext">--%>
                    <%--<span>入库提醒</span>--%>
                    <%--<input type="button" value="已读"></input>--%>
                    <%--<p>案件编号+检材:编号+已入库成功!</p>--%>
                <%--</div>--%>

            </div>
        </div>
    </nav>

</header>
<main>
    <div class="leftTab">
        <div class="panel-group" id="accordion">
            <shiro:user></shiro:user>
            <c:forEach items="${permissionList}" var="list"  varStatus="status">
                <shiro:hasPermission name="${list.permissionName}">
                    <c:if test="${list.permissionList == null}">
                        <div class="panel panel-default <c:if test="${list.activeFlag == '1'}">active</c:if>">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <c:choose>
                                        <c:when test="${fn:contains(list.permissionLink,'http://')}">
                                            <a href="${list.permissionLink}" target="ifm">${list.permissionName}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="<%=path%>${list.permissionLink}" target="ifm">${list.permissionName}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </h4>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${list.permissionList != null}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${status.index}" class="collapsed">${list.permissionName}
                                        <i class="fa fa-chevron-right" aria-hidden="true"></i>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse${status.index}" class="accordion-body collapse" aria-expanded="false"
                                 style="height: 0px;">
                                <div class="accordion-inner" style="color:#fff">
                                    <c:forEach items="${list.permissionList}" var="nodes">
                                        <c:choose>
                                            <c:when test="${fn:contains(nodes.permissionLink,'http://')}">
                                                <a href="${nodes.permissionLink}" target="ifm">${nodes.permissionName}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<%=path%>${nodes.permissionLink}" target="ifm">${nodes.permissionName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </shiro:hasPermission>
            </c:forEach>
        </div>
    </div>
    <iframe src="<%=path%>/main/home" name="ifm" frameborder="0" width="100%" height="100%"></iframe>
</main>
<%@ include file="linkJs.jsp" %>
<script>
    $(".navbar-nav").eq(1).find("li").click(function () {
        $(this).addClass("active").siblings().removeClass("active")
    })
    $(".navbar-nav").eq(0).find("li").click(function () {
        $(".navbar-nav").eq(1).find("li").removeClass("active")
    })
    $(".panel").click(function () {
        $(this).addClass("active").siblings().removeClass("active")
    })
    $(".accordion-inner").find("a").click(function () {
        $(this).addClass("ablue").siblings().removeClass("ablue")
    })
    var str={"userid":$(".userid").text()};
    $(".bottomdiv").on("click",function(){
        $(".textdiv").css("display","block");
        textlist();
        timelyFun();
    });
    function textlist(){
        $(".textlist").html("");
        $.ajax({
            type : "post",
            url : "<%=path%>/MobileNews/getUserNewsList",
            async : false,
            data:JSON.stringify(str),
            contentType:"application/json; charset=utf-8",
            dataType : "json",
            success: function (data) {
                //console.log(data.data.newsList);
                var nlist = data.data.newsList;
                var textdiv = "";
                $.each(nlist,function(n,value) {
                    var a="";
                    if(value.messageType == 3){
                       a = "<%=path%>/center/stateMonitoring?sampleNo="+value.caseId;
                    }
                    if(value.messageType == 4){
                        a = "<%=path%>/queryCompareResult/querySameCompareResultList?caseId="+value.caseId;
                    }
                    if(value.messageType == 5){
                        a = "<%=path%>/queryCompareResult/queryRelativeCompareResultList?caseId="+value.caseId;
                    }
                    textdiv += "<div class='noedtext' id="+value.id+"><span>"+value.title+"</span><input type='button' onclick='delt(this)' value='已读'/><a onclick=\"openNewWin(\'"+a+"\')\" class='atext'>"+value.content+"</a></div>";
                });
                $(".textlist").append(textdiv);
            },
            error:function (data) {
                console.log("请求错误！");
            }
        });

    }
    function delt(e){
        var idname={"id":$(e).parents(".noedtext").attr("id")};
        $.ajax({
            type : "post",
            url : "<%=path%>/MobileNews/DeleteMobileNewsById",
            async : false,
            data:JSON.stringify(idname),
            contentType:"application/json; charset=utf-8",
            dataType : "json",
            success: function (data) {
                textlist();
                timelyFun();
            },
            error:function (data) {
                console.log("请求错误！");
            }
        });

    }
    function openNewWin(url) {
        // alert(url);
        window.open(url);
    }

    $(".textdiv button").on("click",function () {
        $(".textdiv").css("display","none");
        timelyFun();
    })
    //定时访问后台未读条数

    function funcTest(){
       window.setInterval("timelyFun()",60000);
    }
    $(document).ready(function(){
        timelyFun();
    })
    window.onload = funcTest;
    function timelyFun() {
        var readtext = $(".read").html();
        $.ajax({
            type : "post",
            url : "<%=path%>/MobileNews/getUserMobileNewsNumber",
            async : false,
            data:JSON.stringify(str),
            contentType:"application/json; charset=utf-8",
            dataType : "json",
            success: function (data) {
                if(readtext != "("+data.data.NOTREADING+")" ){
                    $(".read").text("("+data.data.NOTREADING+")");
                    $(".textdiv").css("display","block");
                    textlist();
                }
            },
            error:function (data) {
                console.log("请求错误！");
            }
        });
    }





</script>
</body>

</html>