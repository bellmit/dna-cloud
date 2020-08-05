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
    <title>北京市公安局DNA数据统计系统</title>
    <link rel="shortcut icon" href="<%=path%>/img/favicon.ico" type="image/x-icon"/>
    <%@ include file="linkCss.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <style>

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
                    <p>北京市公安局DNA数据统计系统</p>
                    <p>Beijing Forensic DNA Data Statistics Management System</p>
                </a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle header-nav-img" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
                            <c:choose>
                                <c:when test="${user.amPersonalInfo.policeNo eq null}">
                                    <img src="<%=path%>/img/touxiang1.png" alt="">${user.amPersonalInfo.fullName}
                                </c:when>
                                <c:otherwise>
                                    <img src="<%=path%>/img/touxiang1.png" alt="">${user.amPersonalInfo.fullName}(${user.amPersonalInfo.policeNo})
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <ul class="dropdown-menu">
                            <span class="caret"></span>
                            <li><a href="<%=path%>/manage/personalInformation" target="ifm">个人信息</a></li>
                            <li><a href="<%=path%>/loginOut">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

</header>
<main>
    <div class="leftTab">
        <div class="panel-group" id="accordion">
            <%--<shiro:user></shiro:user>--%>
            <c:forEach items="${permissionList}" var="list"  varStatus="status">
                <%--<shiro:hasPermission name="${list.permissionName}">--%>
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
                <%--</shiro:hasPermission>--%>
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


</script>
</body>

</html>