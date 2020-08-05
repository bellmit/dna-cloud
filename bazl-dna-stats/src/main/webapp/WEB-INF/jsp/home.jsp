<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA数据统计系统</title>
    <%@ include file="linkCss.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <style>
        .bazl-tab {
            text-align: center;
        }

        .bazl-tab span {
            display: inline-block;
            width: 256px;
            height: 42px;
            line-height: 42px;
            color: #999;
            font-size: 16px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            cursor: pointer;
        }

        .bazl-tab span:first-child {
            border-radius: 42px 0 0 42px;
        }

        .bazl-tab span:last-child {
            border-radius: 0 42px 42px 0;
        }

        .bazl-tab span.active {
            background: #1c84f2;
            border: 1px solid #1c84f2;
            color: #fff;
            font-weight: bold;
        }

        #infringementCase {
            height: 318px;
            margin-top: 46px;
        }

        .box-number {
            /*width:116px;*/
            /*height:140px;*/
            /*display: inline-block;*/
            float: left;
            padding: 15px 40px;
        }

        .box-number:hover {
            background: #fffaec;
        }

        .box-number:hover p {
            font-size: 14px !important;
            font-weight: bold !important;
            color: #333 !important;
        }

        .box-number p:first-of-type {
            color: #666;
            font-size: 12px;
        }

        .box-number:first-child p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/blue-echarts.png") no-repeat center;
        }

        .box-number:first-child:hover p:nth-last-of-type(2) {
            background: url("../img/blue-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(2) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/green-echarts.png") no-repeat center;
        }

        .box-number:nth-child(2):hover p:nth-last-of-type(2) {
            background: url("../img/green-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(3) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/yellow-echarts.png") no-repeat center;
        }

        .box-number:nth-child(3):hover p:nth-last-of-type(2) {
            background: url("../img/yellow-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(4) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/blue-echarts.png") no-repeat center;
        }

        .box-number:nth-child(4):hover p:nth-last-of-type(2) {
            background: url("../img/blue-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(5) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/green-echarts.png") no-repeat center;
        }

        .box-number:nth-child(5):hover p:nth-last-of-type(2) {
            background: url("../img/green-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(6) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/yellow-echarts.png") no-repeat center;
        }

        .box-number:nth-child(6):hover p:nth-last-of-type(2) {
            background: url("../img/yellow-echarts-active.png") no-repeat center;
        }

        .box-number:nth-child(7) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/red-echarts.png") no-repeat center;
        }

        .box-number:nth-child(7):hover p:nth-last-of-type(2) {
            background: url("../img/red-echarts-active.png") no-repeat center;
        }
        .box-number:nth-child(8) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/blue-echarts.png") no-repeat center;
        }

        .box-number:nth-child(8):hover p:nth-last-of-type(2) {
            background: url("../img/blue-echarts-active.png") no-repeat center;
        }
        .box-number:nth-child(9) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/green-echarts.png") no-repeat center;
        }

        .box-number:nth-child(9):hover p:nth-last-of-type(2) {
            background: url("../img/green-echarts-active.png") no-repeat center;
        }
        .box-number:nth-child(10) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/yellow-echarts.png") no-repeat center;
        }

        .box-number:nth-child(10):hover p:nth-last-of-type(2) {
            background: url("../img/yellow-echarts-active.png") no-repeat center;
        }
        .box-number:nth-child(11) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/red-echarts.png") no-repeat center;
        }

        .box-number:nth-child(11):hover p:nth-last-of-type(2) {
            background: url("../img/red-echarts-active.png") no-repeat center;
        }
        .box-number:nth-child(12) p:nth-last-of-type(2) {
            height: 30px;
            background: url("../img/blue-echarts.png") no-repeat center;
        }

        .box-number:nth-child(12):hover p:nth-last-of-type(2) {
            background: url("../img/blue-echarts-active.png") no-repeat center;
        }
        .margin-left-15 {
            margin-left: -15px;
        }

        .littlt-text {
            padding: 0;
            margin-bottom: -4px;
            font-size: 12px;
            color: #999;
        }

        .littlt-text:before {
            content: '';
            display: inline-block;
            width: 94%;
            border-top: 1px solid #e9eaf0;
            margin-right: 6px;
        }
        .loading-img{
            animation: rotation 5s linear infinite;
        }
        @-webkit-keyframes rotation {
            from {-webkit-transform: rotate(0deg);
            }to {
                 -webkit-transform: rotate(360deg);
             }
        }
        .select::after{
            top:10%;
        }
        .listYear{
            padding-bottom: 30px;
        }
    </style>
</head>

<body>
<div class="statistics">
    <div class="col-md-6">
        <div class="col-md-6">&nbsp;</div>
        <div class="col-md-6">
            <div class="select">
                <select id="acceptOrgId1"  style="border: 1px solid #d4d4d4; color: #000;" class="col-md-6 col-md-offset-6">
                    <option value="">全部实验室</option>
                    <c:forEach items="${laboratoryInfoList}" var="laboratory">
                        <option value="${laboratory.orgId}" <c:if test="${laboratory.orgId eq acceptOrgId}">selected</c:if>>${laboratory.dnaLabName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-6" id="casePropertyStatsDiv1">
        <c:forEach items="${caseStatsList}" var="result" varStatus="s">
            <input type="hidden" name="${result.caseProperty}" zhcn="${result.casePropertyName}" value="${result.caseCount}">
        </c:forEach>

        <ul class="nav navbar-nav navbar-right listYear year">
            <c:forEach items="${yearList}" var="yearList" varStatus="s">
                <li value="${yearList}" name="yearBtn"
                    <c:if test="${year eq yearList}">class="active year-item"</c:if>>${yearList}年
                </li>
            </c:forEach>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" role="button"
                   style="cursor: not-allowed">更多<span class="caret"></span>
                </a>
            </li>
        </ul>
    </div>
</div>
<div class="row part">
    <div class="col-md-12">
        <div class="row statistics">
            <p class="title">
                <span class="case-title">案件性质统计</span>
            </p>
            <div class="col-md-12" id="caseClassification"></div>
        </div>
    </div>
</div>
<div class="row part">
    <div class="col-md-12 overflow-hidden">
        <div class="row statistics">
            <p class="title">
                <span class="case-title">侵财案件数量统计</span>
            </p>
            <div class="col-md-6">
                <%--<div class="col-md-6">--%>
                    <%--<div class="select">--%>
                        <%--<select id="acceptOrgId2" style="border: 1px solid #d4d4d4; color: #000;" class="col-md-6 col-md-offset-6">--%>
                            <%--<option value="">全部实验室</option>--%>
                            <%--<c:forEach items="${laboratoryInfoList}" var="laboratory">--%>
                                <%--<option value="${laboratory.orgId}" <c:if test="${laboratory.orgId eq acceptOrgId}">selected</c:if>>${laboratory.dnaLabName}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
            <div class="col-md-6">
                <input type="hidden" name="ivYear" value="${ivYear}">
                <%--<ul class="nav navbar-nav invadingWealthYear ">--%>
                    <%--<c:forEach items="${yearList}" var="yearList" varStatus="s">--%>
                        <%--<li value="${yearList}" name="invadingWealthBtn" id="invadingWealthBtn"--%>
                            <%--<c:if test="${ivYear eq yearList}">class="active year-item"</c:if>>${yearList}年--%>
                        <%--</li>--%>
                    <%--</c:forEach>--%>
                    <%--<li class="dropdown">--%>
                        <%--<a href="#" class="dropdown-toggle" style="cursor: not-allowed;">更多<span--%>
                                <%--class="caret"></span></a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            </div>
           <%-- <hr class="line-dash">--%>
            <input type="hidden" name="jsonVal" id="jsonVal" value='${assetsCaseStatsOrgJsonList}'>
            <%--<div class="bazl-tab invadingWealth">
                <input type="hidden" name="jsonVal" id="jsonVal" value='${assetsCaseStatsOrgJsonList}'>
                <span class="active" value="01" name="invadingBtn">送检案件总数</span>
                <span value="02" name="invadingBtn">检出（入库）现场物证的案件数</span>
                <span value="03" name="invadingBtn">送检样本总数</span>
                <span value="04" name="invadingBtn">入库样本总数</span>

            </div>--%>
            <div class="col-md-12" id="infringementCase"></div>
        </div>
    </div>
    <div class="col-md-12 littlt-text">
        类别
    </div>
    <div class="col-md-12 margin-left-15" id="category">
        <c:forEach items="${assetsCaseStatsList}" var="assetsCaseStats" varStatus="s">
            <div class="box-number">
                <p>${assetsCaseStats.casePropertyName}</p>
                <p>
                    <img src="" alt="">
                </p>
                <p>${assetsCaseStats.caseCount}个</p>
            </div>
        </c:forEach>

    </div>
</div>
<div class="modal" id="loading" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <%--<div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">--%>
                <div class="exportIng">
                    <p>
                        <img src="../img/loading.png" class="loading-img" alt="" width="90px">
                    </p>
                    <p style="font-size: 18px;font-weight: bold;color: #f9f9f9;margin-top: 15px;">
                        加载中....
                    </p>
                </div>
            <%--</div>
        </div>--%>
    </div>
</div>
<div class="height-60"></div>
<div class="row footer">
    Copyright© 2019 北京博安智联科技有限公司       
</div>
<%@ include file="linkJs.jsp" %>
<script>
    $(function () {

        $(".year li.year-item").click(function () {
            $(this).addClass("active").siblings().removeClass("active")
        });
        $(".bazl-tab span").click(function () {
            $(this).addClass("active").siblings().removeClass("active")
        });

        var dataValArr = new Array();
        var dataLabelArr = new Array();

        $("input[type='hidden']", "#casePropertyStatsDiv1").each(function(){
            dataValArr.push($(this).val());
            dataLabelArr.push($(this).attr("zhcn"));
        });
//
//        var GKZW = $("input[name='GKZW']").val();
//        var DG = $("input[name='DG']").val();
//        var QT = $("input[name='QT']").val();
//        var SHZS = $("input[name='SHZS']").val();
//        var QY = $("input[name='QY']").val();
//        var QJA = $("input[name='QJA']").val();
//        var DQGD = $("input[name='DQGD']").val();
//        var DQ = $("input[name='DQ']").val();
//        var QD = $("input[name='QD']").val();
//        var SH = $("input[name='SH']").val();
//        var FZCSW = $("input[name='FZCSW']").val();
//        var ZSRK = $("input[name='ZSRK']").val();
//        var QJ = $("input[name='QJ']").val();
//        var XS = $("input[name='XS']").val();
//        var JTSG = $("input[name='JTSG']").val();

        //案件分类汇总
//        var data = [XS, SH, QJ, QJA, FZCSW, SHZS, ZSRK, DG, JTSG, DQGD, QY, QD, GKZW, DQ, QT];
//        var xdata = ['凶杀', '伤害', '抢劫', '强奸', '非正常死亡', '伤害致死', '走失人口', '打拐', '交通事故', '盗抢工地', '亲缘', '抢夺', '高空坠物', '盗窃', '其他'];
        var lineColor = ['#61A7F6', '#296fff', '#ffc108', '#49c6b6']
        var caseClassificationCharts = echarts.init(document.getElementById('caseClassification'));
        var caseClassificationChartsOption = {
            grid: {
                left: 30,
                right: 50,
                top: 10,
                bottom: 30,
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                data: dataLabelArr,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: lineColor[0], //X下轴颜色
                        type: 'dashed'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    color: '#616161', //X下轴字体颜色
                },
                boundaryGap: false,
            },
                {
                    type: 'category',
                    data: dataValArr,
                    axisLabel: {
                        show: true,
                        backgroundColor: '#f6fafe',
                        textStyle: {
                            color: "#1681F5",
                            padding: [4, 10, 4, 10],
                            borderRadius: 10,
                        },

                    },
                    axisTick: {
                        alignWithLabel: false
                    },
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: 'yellow'
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
                }
            ],
            yAxis: {
                type: 'value',
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
            },
            tooltip: {
                backgroundColor: "green",
                trigger: 'axis',
                showContent: false,
            },
            series: [{
                data: dataValArr,
                type: 'line',
                symbol: 'emptyCircle',
                symbolSize: 5,
                color: lineColor[0], //拐点颜色
                lineStyle: {
                    color: lineColor[0] //折线颜色
                },
                label: {
                    show: false,
                },
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0.22,
                        color: lineColor[0]
                    }, {
                        offset: 0.98,
                        color: '#fff'
                    }]), //折线区域颜色
                    opacity: 0.5
                }
            }]
        };
        caseClassificationCharts.setOption(caseClassificationChartsOption);
        //侵财案件统计
        <%--var infringementCaseData = <%= session.getAttribute("jsonVal")%>--%>

        var infringementCaseData = eval( $("#jsonVal").val());

        var infringementCaseDataX = [],
                infringementCaseDataY = [];
        infringementCaseData.map(function (a, b) {
            if (b == 0) {
                infringementCaseDataX.push("");
                infringementCaseDataY.push("");
            }
            infringementCaseDataX.push(a.orgName);
            infringementCaseDataY.push(a.caseCount);
        });

        var infringementCase = echarts.init(document.getElementById('infringementCase'));
        var infringementCaseOption = {
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
                data: infringementCaseDataX,
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#999999'
                    }
                },
                axisLabel:{
                    interval:0,
//                    rotate:40,
//                    textStyle: {
//                        fontSize: "15",
//                        fontWeight: "bold"
//
//                    }
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
                data: infringementCaseDataY,
                axisLabel: {
                    interval: 0,
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
                            borderColor: "#FFB72C",
                            borderWidth: 1,
                            align: 'center',
//                        backgroundColor: "#fff9f8",
                            color: "#FFB72C"
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
                        color: '#999999'
                    }
                },
                axisLabel: {
                    color: '#999999'
                }
            },],
            series: [{
                name: '案件占比',
                type: 'bar',
                barWidth: '30%',
                xAxisIndex: 0,
                yAxisIndex: 0,
                itemStyle: {
                    normal: {
                        barBorderRadius: [30, 30, 0, 0],
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
                data: infringementCaseDataY,
            }]
        };
        infringementCase.setOption(infringementCaseOption);


    $("li[name='yearBtn']").click(function () {
        var year = $(this).attr('value');
        var acceptOrgId= $("option:selected", "#acceptOrgId1").val();
        location.href = "<%=path%>/main/home?year=" + year + "&acceptOrgId=" + acceptOrgId;
    });

    $("li[name='invadingWealthBtn']").click(function () {
        var year = $(this).attr('value');
        var acceptOrgId= $("option:selected", "#acceptOrgId2").val();
        location.href = "<%=path%>/main/home?year=" + year + "&acceptOrgId=" + acceptOrgId;
/*
        var year = $(this).attr('value');
        var choiceType = $(".invadingWealth").find("span.active").attr("value")
        $('#loading').modal('show')
        centerModal('loading')
        $.ajax({
            url: "<%=path%>/main/invadingWealth?year=" + year +"&choiceType="+choiceType ,
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.success || data.success == true || data.success == "true") {
                    var ivYear = data.ivYear;
                    $(".invadingWealthYear ").find("li[value='" + ivYear + "']").addClass("active").siblings().removeClass("active");
                    var dataStats = jQuery.parseJSON(data.dataStats);
                    var name = []
                    var val = []
                    for(var i in dataStats){
                        name.push(dataStats[i].NAME)
                        val.push(dataStats[i].VALUE)
                    }
                    var option = {
                        xAxis: [{
                            data: name
                        },{
                            data:val
                        }],
                        series:[
                            {

                                data:val
                            }
                        ]
                    };
                    // ECharts重绘
                    infringementCase.setOption(option);

                    //清除div下的内容
                    $("#category").empty();
                    var sjCaseCategoryList = data.sjCaseCategoryList;
                    if (sjCaseCategoryList != undefined) {
                        $.each(sjCaseCategoryList, function (key, values) {
                            var newRowHtml = "<p>" + key + "</p>";
                            newRowHtml += "<p img src='' alt=''></p>";
                            newRowHtml += "<p>" + values + "</p>";
                            $("#category").append("<div class='box-number'>" + newRowHtml + "</div>");
                        });
                    }
                    $('#loading').modal('hide')
                }
            },
            error: function (e) {
                alert(e);
            }
        });*/
    });

    $("span[name='invadingBtn']").click(function () {
        var choiceType = $(this).attr('value');
        var year = $(".invadingWealthYear").find("li.active").attr("value")
        $('#loading').modal('show')
        centerModal('loading')
        $.ajax({
            url: "<%=path%>/main/invadingWealth?year=" + year +"&choiceType="+choiceType ,
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.success || data.success == true || data.success == "true") {
                    var ivYear = data.ivYear;
                    $(".invadingWealthYear ").find("li[value='" + ivYear + "']").addClass("active").siblings().removeClass("active");

                    var dataStats = jQuery.parseJSON(data.dataStats);
                    var name = []
                    var val = []
                    for(var i in dataStats){
                        name.push(dataStats[i].NAME)
                        val.push(dataStats[i].VALUE)
                    }
                    var option = {
                                xAxis: [{
                                    data: name
                                },{
                                    data:val
                                }],
                        series:[
                            {

                                data:val
                            }
                        ]
                            };
                            // ECharts重绘
                            infringementCase.setOption(option);
                    $('#loading').modal('hide')
                }

                //清除div下的内容
                $("#category").empty();
                var sjCaseCategoryList = data.sjCaseCategoryList;
                if (sjCaseCategoryList != undefined) {
                    $.each(sjCaseCategoryList, function (key, values) {
                        var newRowHtml = "<p>" + key + "</p>";
                        newRowHtml += "<p img src='' alt=''></p>";
                        newRowHtml += "<p>" + values + "</p>";
                        $("#category").append("<div class='box-number'>" + newRowHtml + "</div>");
                    });
                }

            },
            error: function (e) {
                alert(e);
            }
        });
    });

        function centerModal(id){
            $("#"+id).on('shown.bs.modal', function () {
                var $this = $(this);
                var dialog = $this.find('.modal-dialog');
                //此种方式，在使用动画第一次显示时有问题
                //解决方案，去掉动画fade样式
                var top = ($(window).height() - dialog.height()) / 2;
                dialog.css({
                    marginTop:top
                });
            })
        }

    })
</script>
</body>

</html>
