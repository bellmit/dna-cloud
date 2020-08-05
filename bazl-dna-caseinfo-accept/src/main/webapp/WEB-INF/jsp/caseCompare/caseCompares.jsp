<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/15
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局法医鉴定案件受理系统</title>
    <link href="<%=path %>/js/layui-v2.2.45/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .bix{
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
            padding-right: 2%;
        }
        .recordName::before{
            content: " ";
            width:4px;
            display: block;
            height:18px;
            background:rgba(53,134,250,1);
            margin-right: 8px;
        }
        .recordBot{
            background: white;
            padding-top: 12px;
            float: left;
            width: 100%;
        }
        .mdIn{
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        mdIns{
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .mdIn p:nth-child(1){
            width: 28%;
            display: flex;
            justify-content: flex-end;
            margin: 0;
            color: #333333;
        }
        .mdIn p:nth-child(2){
            margin: 0;
            padding-right: 7%;
            margin-left: 10px;
            flex: 1;
        }
        .mdIns p:nth-child(1){
            width: 30%;
        }
        .mdIns p:nth-child(2){
            margin: 0;
            display: flex;
            justify-content: space-between;
        }
        .btnsf{
            display: flex;
            justify-content: space-between;
        }
        .liket{
            color: white;
            background: #2E83FE;
            width: 26%;
            border-radius: 2px;
            border: 1px solid #2E83FE;
            height: 35px;
            font-size: 14px;
            outline: none;
        }
        .rests{
            color: #267FFF;
            background: white;
            width: 26%;
            border-radius: 2px;
            border: 1px solid #0C81F5;
            height: 35px;
            font-size: 14px;
            outline: none;
        }
        .tabMax{
            width: 100%;
            padding: 10px 2%;
        }
        .tabList li{
            white-space: nowrap;
            padding: 10px;
            display: flex;
            font-size: 16px;
            align-items: center;
            color: #333333;
            font-family:PingFang SC;
            cursor: pointer;
        }
        .tabList li p{
            margin: 0;
        }
        .actives{
            border-radius: 5px 5px 0 0;
            font-family:PingFang SC;
            color: white !important;
            background: #1C7BFF;
            border: 1px solid #1C7BFF;
            margin-left: 5px;
        }
        .tabN span{
            margin: 0 2px;
        }
        .tabNum{
            margin: 0;
            background: white;
            border-radius: 100px;
            color: #267FFF;
            width: 30px;
            height: 30px;
            text-align: center;
            line-height: 30px;
            margin-left: 5px !important;
        }
        .nowrapt{
            background:rgba(224,224,224,1);
            border:1px solid rgba(178,178,178,1);
            margin-left: 5px;
            border-radius: 5px 5px 0 0;
        }
        .tabl thead{
            background: #BAE0FF;
            color: #333333;
            font-size:14px;
            font-family:Microsoft YaHei;
            font-weight:bold;
            width: 100%;
        }
        .tabl tr th,td{
            text-align: center;
        }
        .isbo{
            color: white;
            margin: 0;
            background: #FF6146;
            width: 22px;
            height: 22px;
            border-radius: 50px;
            text-align: center;
            line-height: 22px;
            font-size: 12px;
        }
        .isboa{
            color: white;
            margin: 0;
            background: #FF6146;
            width: 51px;
            height: 20px;
            border-radius: 50px;
            text-align: center;
            line-height: 20px;
            font-size: 12px;
        }
        .po{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .btns-date{
            background: url("<%=path%>/img/date.png") no-repeat center;
        }
        .btns-date:hover{
            color: #FFB900;
        }
        .table{
            margin: 0;
        }
        .maxo{
            flex: 1;
            background: white;
        }
        .copo{
            color: #666666;
            font-family:Microsoft YaHei;
            font-size: 12px;
            display: flex;
            justify-content: center;
            align-items: center;
            background: white;
            line-height: 50px;
            margin: 0;
            margin-top: 10px;
        }
        .filet{
            display: flex;
            align-items: center;
            width: 23%;
        }
        .filet select{
            width: 80%;
            margin-left: 10px;
        }
        .bte{
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
        .filer{
            display: flex;
            align-items: center;
            color: #333333;
            font-weight: 400;
        }
        .filer img{
            padding-right: 5px;
        }
        .psersi{
            font-size: 12px;
            display: flex;
            align-items: center;
            color: #267FFF;
            margin: 0;
            cursor: pointer;
        }
        .boos{
            font-size: 12px;
            display: flex;
            align-items: center;
            color: #FFAB00;
            margin: 0;
            cursor: pointer;
        }
        .topp{
            font-size: 12px;
            display: flex;
            align-items: center;
            color: #00B39B;
            margin: 0;
            cursor: pointer;
        }
        .psersi,.boos,.topp img{
            padding-right: 5px;
        }
        .psersi img{
            padding-right: 5px;
        }
        .boos img{
            padding-right: 5px;
        }
        .imgBoxs{
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: space-around;
            align-items: center;
        }
    </style>
</head>
<body>
<%@ include file="../linkJs.jsp" %>
<div class="bix">
    <div class="record">
        <div class="recordName">
            <span>案件查询</span>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>案件受理编号</p>
                <p><input type="text" class="form-control"  placeholder="请输入编号"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案件名称</p>
                <p><input type="text" class="form-control"  placeholder="请输入名称"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>案件性质</p>
                <p>
                    <select class="form-control">
                        <option style="display: none" selected value="" disabled>请选择性质</option>
                        <option>2</option>
                    </select>
                </p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>委托单位</p>
                <p>
                    <select class="form-control">
                        <option style="display: none" selected value="" disabled>清选择单位</option>
                        <option>2</option>
                    </select>
                </p>
            </div>
            <div class="col-md-4 mdIn">
                <p>现场勘验编号</p>
                <p><input type="text" class="form-control"  placeholder="请输入号码"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>检材编号</p>
                <p><input type="text" class="form-control"  placeholder="请输入编号"></p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>检材名称</p>
                <p><input type="text" class="form-control"  placeholder="请输入名称"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>人员身份证号</p>
                <p><input type="text" class="form-control"  placeholder="请输入号码"></p>
            </div>
            <div class="col-md-4 mdIn">
                <p>受理人员</p>
                <p><select class="form-control">
                    <option style="display: none" selected value="" disabled>请选择人员</option>
                    <option>2</option>
                </select>
                </p>
            </div>
        </div>
        <div class="recordBot">
            <div class="col-md-4 mdIn">
                <p>受理时间</p>
                <p>
                    <select class="form-control">
                        <option style="display: none" selected value="" disabled>请选择时间</option>
                        <option>2</option>
                    </select>
                </p>
            </div>
            <div class="col-md-4 mdIn">
                <p></p>
                <p class="btnsf"><button class="liket">查询</button><button class="rests">重置条件</button></p>
            </div>
        </div>
    </div>
    <div class="maxo">
        <div class="record">
            <div class="recordName">
                <div class="bte">
                    <span>案件列表</span>
                    <div class="filet">
                        <span class="filer"><img src="<%=path%>/img/filter.png" alt="">筛选</span>
                        <select class="form-control">
                            <option>在检验</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="tabMax">
                <div class="maxTab">
                    <table class="table table-striped tabl table-bordered">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>案件受理编号</th>
                            <th>案件名称</th>
                            <th>案件性质</th>
                            <th>委托单位</th>
                            <th>受理人</th>
                            <th>受理时间</th>
                            <th>受理检材总数</th>
                            <th>检出分型检材个数</th>
                            <th>案件状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>01</td>
                            <td>FYA200001-2020WZ0001</td>
                            <td>王晓婷被盗案件</td>
                            <td>入室盗窃</td>
                            <td>房山分局</td>
                            <td>张晓鹏</td>
                            <td>2020-05-13</td>
                            <td>12</td>
                            <td>12</td>
                            <td>已完成</td>
                            <td>
                                <div class="imgBoxs">
                                    <p class="psersi"><img src="<%=path%>/img/pers.png" alt="">本案比对</p>
                                    <p class="boos"><img src="<%=path%>/img/boos.png" alt="">案件鉴定书</p>
                                    <p class="topp"><img src="<%=path%>/img/topp.png" alt="">案件上报</p>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div id="demos1"></div>
                </div>
            </div>
        </div>
    </div>
    <p class="copo">Copyright© 2020 北京博安智联科技有限公司</p>
</div>
<script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script>
<script>
    $(function () {
        $(".maxTab:gt(0)").css('display','none')
        $(".tabList>li").on("click", function () {
            $(this).siblings().addClass('nowrapt').removeClass('actives')
            $(this).addClass('actives').removeClass('nowrapt')
            $(this).siblings().find('p').removeClass('tabNum')
            $(this).siblings().find('p').removeClass()
            $(this).siblings().find('p').html('('+100+')')
            $(this).find('p').addClass('tabNum')
            $(this).find('p').html(100)
            let indexs = $(this).index()
            $.each($('.maxTab'),function (i,v) {
                $(v).hide()
                if(indexs === i){
                    $(v).show()
                }
            })
        })
    })
</script>
</body>
