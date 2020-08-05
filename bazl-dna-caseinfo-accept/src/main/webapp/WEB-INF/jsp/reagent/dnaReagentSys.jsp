<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>北京市公安局DNA智慧实验室信息管理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .BlankDiv {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="BlankDiv">
    <div class="col-md-12">&nbsp;</div>
    <%--<form id="redirectForm" action="stats/limsDataStatsRedirect" target="_blank"></form>--%>
</div>
<%@ include file="../linkJs.jsp" %>
<script>

    <%--var ip = '${requestIpaddr}';--%>
    var linkUrl = ${dnaReagentUrl};
    var userName = '${loginUser.loginName}';
    var userPwd = '${loginUser.loginPassword}';


    openDataStats();

    function openDataStats(){

        //TODO
        // var fullURL = linkUrl + "?loginName=" + userName + "&loginPassword=" + userPwd;
        var fullURL = linkUrl + "?loginName=admin&loginPassword=1";

        var link = $("<a></a>").attr("href", fullURL).attr("target","_blank");
        console.log(link);
        $("body").append(link);

        link[0].click();
        link.remove();


//        var newWindow = window.open("about:blank");
//        console.log('newWindow',newWindow);
//        newWindow.location.href = fullURL;
    }
</script>
</body>

</html>