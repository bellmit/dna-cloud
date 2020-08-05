<%@ include file="../include.jsp" %>
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
    <title>北京市公安局法医鉴定案件受理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .btn-red:hover {
            width: 100%;
        }
        body{
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        .boxs{
            flex: 1;
        }
        #videoBox a {
            padding-top: 10px;
            padding-bottom: 10px
        }

        #videoBox button {
            padding: 10px 26px;
        }

        .bu {
            background: #fddddb;
            color: #fc5a56;
            padding: 5px;
            border-radius: 50%;
            font-size: 10px;
            font-weight: 600;
        }
        .filt{
            display: flex;
            align-items: center;
            white-space: nowrap;
        }
        .select{
            padding-left: 10px;
        }
        .filt input{
            margin-left: 10px;
        }
        .analyList{
            margin: 0;
            display: flex;
        }
        .analyList li{
            margin-left: 5px;
            background:rgba(224,224,224,1);
            border:1px solid rgba(178,178,178,1);
            border-radius:5px 5px 0px 0px;
            padding: 10px;
            text-align: center;
            cursor: pointer;
        }
        .analyList li:first-child{
            margin: 0;
        }
        .tabList{
            margin: 0;
        }
        .analyList .actio{
            color: white;
            background: #006EFF;
            border: 1px solid #006EFF;
        }
        .Modular .panel-body{
            padding:10px 30px 0px 30px;
        }
        .btnst button{
            width: 20%;
        }
        .foot{
            width: 100%;
            height: 5%;
            display: flex;
            justify-content: center;
            align-items: center;
            background: white;
            margin: 0;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="row Modular">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>案件查询</div>
                <input type="hidden" name="actionName" id="actionName" value="<%=path%>/center/analysisExperiment">
            </div>
            <div class="panel-body">
                <form id="consignationForm" action="<%=path%>/center/analysisExperiment" class="form-horizontal tasi-form"
                      method="post">
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group filt">
                            <label>案件受理编号</label>
                            <input type="text" class="form-control" placeholder="案件受理编号" id="boardNo" name="entity.boardNo" value="${query.entity.boardNo}">
                        </div>
                    </div>
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group filt">
                            <label>检材编号</label>
                            <input type="text" class="form-control" placeholder="请输入编号" id="sampleNo" name="sampleNo" value="${query.sampleNo}">
                        </div>
                    </div>
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group filt">
                            <label>扩增试剂盒</label>
                            <input type="text" class="form-control" placeholder="请选择试剂盒" id="sampleNo" name="sampleNo" value="${query.sampleNo}">
                        </div>
                    </div>
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group filt">
                            <label>电泳板号</label>
                            <input type="text" class="form-control" placeholder="请输入板号" id="sampleNo" name="sampleNo" value="${query.sampleNo}">
                        </div>
                    </div>
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group filt">
                            <label>电泳时间</label>
                            <input type="text" id="acceptStartDatetime" name="acceptStartDatetime"  class="form-control form_datetime"
                            value="" placeholder="请选择时间" >
                                <%--<select id="auditor" name="entity.analysisPerson" value="analysisPerson"
                                        class="form-control">
                                    <option value="" selected>全部</option>
                                    <c:forEach items="${labAnalysisInfos}" var="list" varStatus="cs">
                                        <option value="${list.analysisPerson}" <c:if test="${list.analysisPerson eq query.entity.analysisPerson}">selected</c:if>>
                                                ${list.analysisPerson}
                                        </option>
                                    </c:forEach>
                                </select>--%>
                        </div>
                    </div>
                    <div class="col-md-4 seachInputBox">
                        <div class="form-group btnst">
                            <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                            <button class="btn btn-blue" type="submit" id="addInformant">查询</button>
                            <button type="button" name="reset" class="btn btn-blue-border">重置条件</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="row Modular boxs">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>查询结果</div>
            </div>
            <div class="panel-body">
                <ul class="analyList">
                    <li class="actio"><a href="<%=path%>/center/analysisExperiment" style="color:#f5f7f9;">待分析（12）</a></li>
                    <li><a href="<%=path%>/center/testToReview">待确认审核（36）</a></li>
                    <li>已确认审核（52）</li>
                </ul>
                <div style="float:right; margin:5px 10px; text-decoration: underline;">
                    <a href="<%=path%>/center/codisImport">CODIS导入</a>
                </div>
                <table class="table table-hover table-bordered tabList bigTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>检材板号</th>
                        <th>检材总数</th>
                        <th>未编码检材个数</th>
                        <th>实验人</th>
                        <th>电泳时间</th>
                        <th>电泳仪器</th>
                        <th>扩增试剂盒</th>
                        <th>审核时间</th>
                        <th>审核人</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labAnalysisInfoList}" var="list" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${list.entity.boardNo}</td>
                            <td><fmt:formatDate value='${list.entity.createDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td>${list.entity.analysisPerson}</td>
                            <td>${list.entity.sampleCount}</td>
                            <td>${list.entity.machineNo}</td>
                            <td>
                                <a href="<%=path%>/center/enterAnalysis?id=${list.entity.id}"
                                   target="ifm"  class="btn btn-green btn-xs">结果分析</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%@ include file="../pagefoot.jsp" %>
            </div>
        </div>
    </div>
</div>
<p class="foot">Copyright© 2020 北京博安智联科技有限公司        </p>
<%@ include file="../linkJs.jsp" %>
<script src="${pageContext.request.contextPath}/js/page.js" ></script>
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

        //重置
        $("button[name='reset']").on("click", function () {
            location.href = "<%=path%>/center/analysisExperiment";
        })
        $(".analyList li").on("click",function () {
            $(this).siblings().removeClass()
            $(this).addClass("actio")
        })
    })
</script>
</body>

</html>