<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA信息统计管理系统</title>
    <%@ include file="../linkCss.jsp" %>
    <style>
        .bu {
            background: #fddddb;
            color: #fc5a56;
            padding: 5px;
            border-radius: 50%;
            font-size: 10px;
            font-weight: 600;
        }

        .bigTable.table-bordered, .bigTable.table-bordered > tbody > tr > td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .export {
            font-size: 14px;
            color: #0C81F5;
            font-weight: normal;
            cursor: pointer;
        }

        .time:before {
            content: '';
            display: inline-block;
            width: 20px;
            height: 20px;
            background: url("../img/time.png") no-repeat center;
            background-size: 100%;
            vertical-align: middle;
        }

        .pipei-number {
            height: 36px;
            line-height: 36px;
            color: #FF9200;
            text-align: right;
        }

        .blue-font-btn:hover {
            color: #0C81F5;
            font-size: 14px;
            font-weight: bold;
            background: #e7f2fe;
        }

        .look-detail {
            background: url("../img/delete.png") no-repeat center;
            background-size: 22px;
            width: 100%;
            height: 38px;
            display: inline-block;
            color: transparent;
            cursor: pointer;
        }

        .look-detail:hover {
            background: #fff6ea;
            color: #FF5A56;
            width: 100%;
            height: 38px;
            display: inline-block;
            font-weight: bold;
        }

        tbody tr td:last-of-type {
            /*padding: 0 !important;*/
            height: 38px;
            line-height: 38px;
            text-align: center;
        }

        .btn-icon-bianji-blue {
            background-position: -34px -62px;
        }

        .btn-icon-shanchu-red {
            background-position: -98px -61px;
        }

        .btn-icon-chakan-yellow {
            background-position: -34px -270px;
        }
    </style>
</head>

<body>
<div class="row Modular part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue">
                <div>
                    样本查询条件
                </div>
            </div>
            <div class="panel-body">
                <form id="consignationForm" action="<%=path%>/sample/sampleDelete"
                      class="form-horizontal tasi-form form-inline"
                      method="post">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>样本编号</label>
                                <input type="text" class="form-control" name="sampleNo" value="${sampleNo}"
                                       placeholder="请输入样本编号">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <button class="btn btn-blue" type="submit" id="addInformant"
                                        style="margin-right: 20px;">查询
                                </button>
                                <button type="button" name="reset" class="btn btn-blue-border">重置</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="row Modular notAccepted part">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading blue row" style="padding: 10px 15px;">
                <div class="col-md-2">样本信息</div>
            </div>
            <div class="panel-body" style="padding: 0;">
                <c:choose>
                    <c:when test="${limsSampleInfoDnaList.size() eq 0}">
                        <div class="no-data" style="text-align: center;padding: 20px 0;">
                            <p>
                                <img src="../img/none.png" alt="" width="180px">
                            </p>
                            <p style="color: #666;font-size: 18px;">
                                暂无数据，您可重新查询或<a href="<%=path%>/main/home" style="color: #0C81F5;">返回首页</a>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-hover table-bordered bigTable table-striped"
                               style="table-layout: fixed;margin-top: 0;">
                            <thead>
                            <tr>
                                <th style="width: 55px;">序号</th>
                                <th>样本编号</th>
                                <th>样本名称</th>
                                <th style="width:100px;">样本类型</th>
                                <th>入库编号</th>
                                <th style="width:100px;">入库人</th>
                                <th style="width:130px;">入库时间</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="consignatioInfoListTbody">
                            <c:forEach items="${limsSampleInfoDnaList}" var="sample" varStatus="c">
                                <tr>
                                    <td>${c.index+1}</td>
                                    <td>${sample.sampleNo}</td>
                                    <td><input type="hidden" name="sampleName"
                                               value="${sample.sampleName}"> ${sample.sampleName}</td>
                                    <td>${sample.sampleTypeName}</td>
                                    <td>${sample.sampleRkNo}</td>
                                    <td>${sample.instoredPerson}</td>
                                    <td><fmt:formatDate value='${sample.instoredDatetime}' pattern='yyyy-MM-dd'/></td>
                                    <td>
                                        <input type="hidden" name="sampleNo" value="${sample.sampleNo}">
                                        <input type="hidden" name="delegateOrg" value="${sample.delegateOrg}">
                                        <input type="hidden" name="sampleType" value="${sample.sampleType}">

                                        <button type="button" name="updateSampleBtn" target="ifm"
                                                class="btn-icon btn-icon-blue  btn-icon-bianji-blue">
                                            编辑
                                        </button>
                                        <button type="button" name="viewGeneTypigBtn"
                                                class="btn-icon btn-icon-yellow btn-icon-chakan-yellow">
                                            查看
                                        </button>
                                        <button type="button" name="delBtn"
                                                class="btn-icon btn-icon-red  btn-icon-shanchu-red">删除
                                        </button>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<div class="modal fade popBox bigBox" id="sampleModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <form action="" id="addSampleform">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">样本信息</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>样本编号</label>
                                <input type="text" class="form-control" name="sampleNo">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>样本名称</label>
                                <input type="text" class="form-control" name="sampleName">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label>检材类型</label>
                                <div class="select">
                                    <select class="form-control" name="sampleType">
                                        <option value="">请选择检材类型</option>
                                        <c:forEach items="${dictItemList}" var="sampleList">
                                            <option value="${sampleList.dictCode}"
                                                    <c:if test="${sampleType eq sampleList.dictCode}">selected</c:if>>${sampleList.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer clearfix">
                    <input type="hidden" name="oldSampleNo">
                    <button class="btn btn-lang  btn-blue addMaterialEvidencerlSample" type="button"
                            name="addSampleButton">保存
                    </button>
                    <button type="button" class="btn btn-lang btn-blue-border" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="modal fade popBox bigBox" id="sampleGeneModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <form action="" id="addSampleGeneform">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">基因型信息</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <div class="col-sm-11">
                            <table class="table table-striped table-advance table-bordered table-hover"
                                   style="table-layout: fixed;width: 50%">
                                <thead>
                                <tr>
                                    <th style="width: 100px;">基因座名称</th>
                                    <th style="width: 100px;">基因型1</th>
                                    <th style="width: 100px;">基因型2</th>
                                    <th style="width: 100px;">基因型3</th>
                                    <th style="width: 100px;">基因型4</th>
                                </tr>
                                </thead>
                                <tbody id="panelTbody">

                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
                <div class="modal-footer clearfix">
                    <input type="hidden" name="geneSampleNo">
                    <input type="hidden" name="geneId">

                    <button class="btn btn-lang  btn-blue addMaterialEvidencerlSample" type="button"
                            name="saveGeneButton">保存
                    </button>
                    <button type="button" class="btn btn-lang btn-blue-border" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </form>
</div>


<div class="modal" id="delSuccess" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">
                <p>
                    <img src="../img/success.png" alt="" width="82px">
                </p>
                <p style="font-size: 22px;font-weight: bold;color: #1BB29B;">
                    删除成功
                </p>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="saveSampleSuccess" tabindex="-1" role="dialog" aria-labelledby="exportSuccess">
    <div class="modal-dialog" role="document" style="width: 456px;">
        <div class="modal-content">
            <div class="modal-body" style="padding: 90px 0;text-align: center">
                <p>
                    <img src="../img/success.png" alt="" width="82px">
                </p>
                <p style="font-size: 22px;font-weight: bold;color: #1BB29B;">
                    操作成功
                </p>
            </div>
        </div>
    </div>
</div>

<div class="height-60"></div>
<div class="row footer">
    Copyright© 2019 北京博安智联科技有限公司       
</div>
<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/entrustCurrency.js"></script>
<script>
    $(function () {
        /*删除*/
        $("button[name='delBtn']", "#consignatioInfoListTbody").on("click", function () {
            var sampleNo = $("input[name='sampleNo']", "#consignatioInfoListTbody").val();
            var delegateOrg = $("input[name='delegateOrg']", "#consignatioInfoListTbody").val();
            if (!confirm("确认删除吗")) {
                return;
            }
            $.ajax({
                url: "<%=path%>/sample/deleteSample?sampleNo=" + sampleNo + "&delegateOrg=" + delegateOrg,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $("#delSuccess").modal("show");
                        setTimeout(function () {
                            location.href = "<%=path%>/sample/sampleDelete";
                        }, 2000);

                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        });

        /*编辑*/
        $("button[name='updateSampleBtn']", "#consignatioInfoListTbody").on("click", function () {

            var sampleNo = $("input[name='sampleNo']", $(this).parents("tr")).val();
            var sampleName = $("input[name='sampleName']", $(this).parents("tr")).val();
            var sampleType = $("input[name='sampleType']", $(this).parents("tr")).val();

            $("input[name='sampleNo']", "#addSampleform").val(sampleNo);
            $("input[name='oldSampleNo']", "#addSampleform").val(sampleNo);
            $("input[name='sampleName']", "#addSampleform").val(sampleName);
            $("select[name='sampleType']", "#addSampleform").val(sampleType);
            $("#sampleModal").modal("show");

        });

        //修改样本编号判重
        $("input[name='sampleNo']", "#addSampleform").on("change", function () {
            var sampleNo = $("input[name='sampleNo']", "#addSampleform").val();
            $.ajax({
                url: "<%=path%>/sample/verificationSampleNo?sampleNo=" + sampleNo,
                type: "post",
                dataType: "json",
                success: function (data) {
                    console.log(data)
                    if (data.success || data.success == true || data.success == "true") {
                        alert("样本编号已存在，请重新输入！")
                        $("input[name='sampleNo']", "#addSampleform").focus();
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        });

        /**
         * 保存修改样本信息
         */
        $("button[name='addSampleButton']", "#addSampleform").on("click", function () {

            var sampleNo = $("input[name='sampleNo']", "#addSampleform").val();
            var oldSampleNo = $("input[name='oldSampleNo']", "#addSampleform").val();
            var sampleName = $("input[name='sampleName']", "#addSampleform").val();
            var sampleType = $("select[name='sampleType']", "#addSampleform").val();

            $.ajax({
                url: "<%=path%>/sample/saveSampleInfo?sampleNo=" + sampleNo + "&sampleName=" + sampleName + "&sampleType=" + sampleType + "&oldSampleNo=" + oldSampleNo,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $("#saveSampleSuccess").modal("show");
                        setTimeout(function () {
                            location.href = "<%=path%>/sample/sampleDelete?sampleNo=" + sampleNo;
                        }, 2000);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });

        });

        //查看基因型
        $("button[name='viewGeneTypigBtn']", "#consignatioInfoListTbody").on("click", function () {
            var sampleNo = $("input[name='sampleNo']", "#consignatioInfoListTbody").val();

            $.ajax({
                url: "<%=path%>/sample/querySampleGene?sampleNo=" + sampleNo,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        var geneSampleNo = data.sampleNo;
                        var geneId = data.geneId;
                        var geneResultList = data.geneResult;

                        $("input[name='geneSampleNo']", "#addSampleGeneform").val(geneSampleNo);
                        $("input[name='geneId']", "#addSampleGeneform").val(geneId);

                        var i = 0;
                        if (geneResultList != undefined) {
                            $.each(geneResultList, function (key, values) {
                                var orgName = key;
                                var newRowHtml = "<td><input type='hidden' name='markerName' id='markerName' value=" + orgName + ">" + orgName + "</td>";
                                var j = 1;
                                $.each(values, function (n, list) {
                                    var name = "gene" + j++;
                                    newRowHtml += "<td><input type='text' name='"+name+"' id='"+name+"' class='form-control' tabindex=" + (i + 1) + " value=" + list + "></td>";
                                });
                                $("#panelTbody").append("<tr>" + newRowHtml + "</tr>");
                            });

                        }

                        $("#sampleGeneModal").modal("show");
                    }
                },
                error: function (e) {
                    alert("查看基因型信息失败！");
                }
            });
        });

        /*保存基因型*/
        $("button[name='saveGeneButton']", "#addSampleGeneform").on("click", function () {
            var geneSampleNo = $("input[name='geneSampleNo']", "#addSampleGeneform").val();
            var geneId = $("input[name='geneId']", "#addSampleGeneform").val();

            var geneInfo = GetGeneInfo();

            if (geneInfo == null) {
                alert("至少输入一个基因型!");
                return false;
            }

            var params = {
                "geneId": geneId,
                "sampleNo": geneSampleNo,
                "geneInfoList": geneInfo
            };

            $.ajax({
                url: "<%=path%>/sample/saveSampleGene",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $("#saveSampleSuccess").modal("show");
                        setTimeout(function () {
                            location.href = "<%=path%>/sample/sampleDelete?sampleNo=" + geneSampleNo;
                        }, 2000);
                    }
                },
                error: function (data, e) {
                    alert("更新基因型信息失败!");
                }
            });
        });


        function GetGeneInfo() {

            var geneArr = new Array();

            var $geneInfoTR = $("tr", "#panelTbody");
            var geneInfoCnt = $geneInfoTR.length;
            var geneLength = "";
            for (var i = 0; i < geneInfoCnt; i++) {
                var geneInfo = {};
                geneInfo.geneName = $("input[name='markerName']", $geneInfoTR[i]).val();
                geneInfo.geneVal1 = $("input[name='gene1']", $geneInfoTR[i]).val();
                geneInfo.geneVal2 = $("input[name='gene2']", $geneInfoTR[i]).val();
                geneInfo.geneVal3 = $("input[name='gene3']", $geneInfoTR[i]).val();
                geneInfo.geneVal4 = $("input[name='gene4']", $geneInfoTR[i]).val();

                geneArr.push(geneInfo);
                geneLength += geneInfo.geneVal1 + geneInfo.geneVal2 + geneInfo.geneVal3 + geneInfo.geneVal4;
            }

            if (geneLength.length == 0) {
                return;
            } else {
                return geneArr;
            }

        }

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

    });
</script>
</body>

</html>