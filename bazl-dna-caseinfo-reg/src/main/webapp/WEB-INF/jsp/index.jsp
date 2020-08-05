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
    <title>北京市公安局DNA案件委托送检系统</title>
    <link rel="shortcut icon" href="<%=path%>/img/favicon.ico" type="image/x-icon" />
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

    .navbar{
        background: url("<%=path%>/img/top-bg.png");
        width: 100%;
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
    width: 200px;
    padding-top: 68px;
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

    .leftTab .panel-heading {
    padding: 0px;
        padding-left: 10px;
    background: transparent;
    }
    .leftTab .actives{
        background: linear-gradient(to right, #0957e9, #0ffcfa);
    }
    .leftTab .panel-heading a {
    padding: 10px 15px;
    display: block;
    color: #fff;
        padding-left: 30px;
    height: 42px;
    line-height: 22px;
    position: relative;
    text-decoration: none;
    }

    .leftTab .panel-heading a i {
    float: right;
    margin-top: 3px;
    }

    .leftTab .panel-heading:hover{
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

    .accordion-inner .acti{
        background: #1b6d85 !important;
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
    .navst{
        color: #fff;
        font-size: 16px;
        width: 30%;
        display: flex;
    }
    @media screen and (min-width: 1300px) and (max-width: 1500px) {
        .navst{
            color: #fff;
            font-size: 16px;
            width: 40%;
            display: flex;
        }
    }
    @media screen and (min-width: 1500px) and (max-width: 1700px) {
        .navst{
            color: #fff;
            font-size: 16px;
            width: 35%;
            display: flex;
        }
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

    .noedtext input{
    float: right;
    border: none;
    background: #2ab7a2;
    color: white;
    width: 40px;
    border-radius: 5px;
    }

    .tipsA {
        position: relative;
    }

    .tips {
        padding-top: 20px;
        position: absolute;
        background: #fff;
        width: 276px;
        box-shadow: 0px 6px 10px 3px #f3f3f3;
        right: -50%;
        margin-right: -73px;
        display: none;
    }

    .tips ul {
        overflow: hidden;
    }

    .tips .caret {
        position: absolute;
        border-top: 8px dashed #fff;
        border-right: 8px solid transparent;
        border-left: 8px solid transparent;
        top: -8px;
        transform: rotate(180deg);
        left: 50%;
    }

    .tips li {
        padding: 15px 35px;
        position: relative;
        cursor: pointer;
    }

    .tips li::before {
        content: "";
        position: absolute;
        background: #3698fa;
        width: 12px;
        height: 12px;
        border-radius: 50%;
        left: 15px;
        top: 20px;
        z-index: 1;
    }

    .tips li:nth-child(4n-1)::before {
        background: #ff7a75;
    }

    .tips li:nth-child(4n-2)::before {
        background: #fec548;
    }

    .tips li:nth-child(4n-3)::before {
        background: #3698fa;
    }

    .tips li:nth-child(4n-4)::before {
        background: #00b39b;
    }

    .tips li:nth-child(n+2):after {
        content: "";
        height: 100%;
        border-left: 2px dashed #f1f1f1;
        position: absolute;
        left: 20px;
        top: -49px;
    }

    .tips li p:nth-child(1) {
        color: #000;
        font-size: 16px;
    }

    .tips li p:nth-child(2) {
        color: #838383;
        margin: 0px
    }

    .tips li p:nth-child(2) span:nth-child(2) {
        float: right;
        color: #ff5951;
    }

    .tips li:hover {
        background: #f6f6f6;
    }

    .noNews {
        text-align: center;
        border-bottom: 1px dashed #dcdcdc;
        padding-bottom: 20px;
        color: #dcdcdc;
    }

    .btn-noBg {
        background: #fff;
        color: #666666
    }

    .btn-noBg.active,
    .btn-noBg:active {
        box-shadow: none
    }

    .btn-noBg.active.focus,
    .btn-noBg.active:focus,
    .btn-noBg.focus,
    .btn-noBg:active.focus,
    .btn-noBg:active:focus,
    .btn-noBg:focus {
        outline: none;
    }
    .navbar{
        background: url("<%=path%>/img/top-bg.png");
    }
        .boxHead{
            padding: 0px 20px;
        }
        .mess{
            margin: 0;
            padding-left: 20px;
        }
        .messs a img{
            width: 25px;
        }
           .nav li a{
               width: 100%;
           }
        .messs{
            position: relative;
            flex: 1;
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
            height: 45px;
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
        .mar{
            width: auto !important;
        }
        .mar a{
            background: white !important;
        }
    </style>
</head>

<body>
<header>
    <link href="${pageContext.request.contextPath}/css/newstyle.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <nav class="navbar navbar-default">
        <div class="container-fluid boxHead">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">
                    <p>北京市公安局DNA案件委托送检系统</p>
                    <p>DNA case submission system of Beijing Public Security Bureau</p>
                </a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navst navbar-nav navbar-right">
                    <li class="messs">
                        <a href="#" class="dropdown-toggle header-nav-img" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <p><img src="<%=path%>/img/mess.png" alt=""></p>
                            <p class="mess">消息</p>
                        </a>
                        <p class="nums">5</p>
                    </li>
                    <li class="dropdown users">
                        <a href="#" class="dropdown-toggle header-nav-img" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <img src="<%=path%>/img/touxiang1.png" alt="">
                          （${dnaLabName}）
                        </a>
                        <ul class="dropdown-menu mar">
                            <span class="caret"></span>
                            <li style="background: white"><a href="<%=path%>/manage/personalInformation" target="ifm">个人信息</a></li>
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


            <%--<li class="tipsA">--%>
                <%--<a href="javascript:;"><i class="fa fa-commenting" aria-hidden="true"></i><span--%>
                        <%--class="badge">0</span></a>--%>
                <%--<div class="tips">--%>
                    <%--<p class="noNews hidden">暂无消息通知</p>--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<a href="<%=path%>/stateQuery/search?text=FT2017WZ0031" target="ifm">--%>
                                <%--<p>您还没有消息</p>--%>
                                <%--&lt;%&ndash;--%>
                                                                        <%--<p>--%>
                                                                            <%--<span>16分钟前</span>--%>
                                                                            <%--<span>已受理</span>--%>
                                                                        <%--</p>--%>
                                <%--&ndash;%&gt;--%>
                            <%--</a>--%>
                        <%--</li>--%>
                        <%--&lt;%&ndash;--%>
                                                        <%--<li>--%>
                                                            <%--<a href="<%=path%>/stateQuery/search?text=FYB1700156-2017WZ0156" target="ifm">--%>
                                                                <%--<p>FYB1700156-2017WZ0156</p>--%>
                                                                <%--<p>--%>
                                                                    <%--<span>16分钟前</span>--%>
                                                                    <%--<span>已受理</span>--%>
                                                                <%--</p>--%>
                                                            <%--</a>--%>
                                                        <%--</li>--%>
                                                        <%--<li>--%>
                                                            <%--<a href="<%=path%>/stateQuery/search?text=DX2017WZ0029" target="ifm">--%>
                                                                <%--<p>DX2017WZ0029</p>--%>
                                                                <%--<p>--%>
                                                                    <%--<span>16分钟前</span>--%>
                                                                    <%--<span>已受理</span>--%>
                                                                <%--</p>--%>
                                                            <%--</a>--%>
                                                        <%--</li>--%>
                                                        <%--<li>--%>
                                                            <%--<a href="<%=path%>/stateQuery/search?text=DX2017WZ0027" target="ifm">--%>
                                                                <%--<p>DX2017WZ0027</p>--%>
                                                                <%--<p>--%>
                                                                    <%--<span>16分钟前</span>--%>
                                                                    <%--<span>已受理</span>--%>
                                                                <%--</p>--%>
                                                            <%--</a>--%>
                                                        <%--</li>--%>
                        <%--&ndash;%&gt;--%>
                    <%--</ul>--%>

                    <%--<span class="caret"></span>--%>
                    <%--<div>--%>
                        <%--<button type="button" class="btn btn-noBg btn-lg btn-block clean">清除通知</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
           <%--</li>--%>
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
                    <div class="panel listSide listAct panel-default <c:if test="${list.activeFlag == '1'}">active</c:if>">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <li class="${list.styleName}">
                                <a href="<%=path%>${list.permissionLink}" target="ifm">${list.permissionName}</a>
                                </li>
                            </h4>
                        </div>
                    </div>
                </c:if>
                <c:if test="${list.permissionList != null}">
                    <div class="panel listSide panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <li class="${list.styleName}">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse${status.index}" class="collapsed">${list.permissionName}
                                    <i class="fa fa-chevron-right" aria-hidden="true"></i>
                                </a>
                                </li>
                            </h4>
                        </div>
                        <div id="collapse${status.index}" class="accordion-body collapse" aria-expanded="false"
                             style="height: 0px;">
                            <div class="accordion-inner">
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
    $(".navbar-nav").find(".dropdown").mouseenter(function () {
        $(this).addClass("open").find('.dropdown-toggle').attr("aria-expanded", "true")
    })
    $(".navbar-nav").find(".dropdown").mouseleave(function () {
        $(this).removeClass("open").find('.dropdown-toggle').attr("aria-expanded", "false")
    })

    $(".tipsA").find("a").click(function () {
        if ($(".tips").css("display") == "none") {
            $(".tips").slideDown()
            if ($(".tips").find("li").length > 0) {
                $(".tips").find(".noNews").addClass("hidden")
            } else {
                $(".tips").find(".noNews").removeClass("hidden")
            }
        } else {
            $(".tips").slideUp()
        }

    })
    $(".tipsA").siblings().children().click(function () {
        $(".tips").slideUp()
    })
    $(".clean").click(function () {
        if ($(".tips").find("li").length > 0) {
            $(".tips").find("li").each(function (i, e) {
                if (i == $(".tips").find("ul").children().length - 1) {
                    $(this).delay(100 * i).animate({
                        left: '-10px',
                    }, 1000).animate({
                        left: '460px'
                    }, 500, function () {
                        $(".tips").slideUp()
                        $(".tips").find("li").remove();
                        $(".tipsA").find(".badge").remove();
                    });
                } else {
                    $(this).delay(100 * i).animate({
                        left: '-10px',
                    }, 1000).animate({
                        left: '460px'
                    }, 500);
                }
            })
        } else {
            $(".tips").slideUp()
        }
    })
    $(".tips").find("li").click(function () {
        $(this).delay(100).animate({
            left: '-10px',
        }, 1000).animate({
            left: '460px'
        }, 500, function () {
            $(".tips").slideUp()
            $(this).remove();
            $(".tipsA").find(".badge").html(Number($(".tipsA").find(".badge").html()) - 1);
        });
    })
    $(".listSide>.panel-heading").on("click",function () {
        $(".listSide>.panel-heading").removeClass("actives")
        $(this).addClass("actives")
    })
    $(".accordion-inner").find("a").click(function () {
        $(".accordion-inner").find("a").removeClass("acti").parents(".listSide").children(".panel-heading")
        $(".listSide>.panel-heading").removeClass("actives")
        $(this).addClass("acti")
        $(this).parents(".listSide").children(".panel-heading").addClass("actives")
    })
    $(".listAct").on("click",function () {
        $(".accordion-inner").find("a").removeClass("acti")
    })
</script>
</body>

</html>
