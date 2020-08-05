<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/16
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA案件委托送检系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .box{
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
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
        .serchInp{
            display: flex;
            justify-content: center;
            width: 100%;
        }
        .serchInp input{
            width: 60%;
        }
        .btnserch{
            width: 5%;
            background: #0088FF;
            outline: none;
            color: white;
            border: 0;
        }
    </style>
</head>
<body>
<div class="box">
    <div class="record">
        <div class="recordName">
            <span>快速查询</span>
        </div>
        <div class="serchInp">
            <input type="text" class="form-control" placeholder="Text input">
            <button class="btnserch" type="button">查询</button>
        </div>
    </div>
</div>
<%@ include file="../linkJs.jsp" %>
</body>
</html>
