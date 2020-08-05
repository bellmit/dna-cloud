<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/13
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>北京市公安局DNA案件委托送检系统</title>
    <%@ include file="../linkCss.jsp" %>
</head>
<style>
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
        margin-bottom: 10px;
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
        padding: 20px 0;
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
    .conBox {
        width: 30%;
    }

    .conBoxs {
        width: 25%;
        display: flex;
        height: 100%;
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
</style>
<body>
<!-- Modal4 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog moBox" role="document">
        <div class="modal-content conBox">
            <div class="modal-body">
                <p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>
                <div class="maxMain" style="width: 100%">
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
                    <div class="utiNames">
                        <img src="<%=path%>/img/ju.png" alt="">
                        <div class="sili">
                            <p class="utiNamen">选择在逃人员信息</p>
                            <p class="wrand">未找到信息？</p>
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
                    </div>
                    <%--<div class="select srsele">
                        <select class="form-control clientSelect" name="fugitives" id="fugitives">
                            <option value="">请选人员姓名</option>
                            <c:forEach items="${fugitivesInfoList}" var="list" varStatus="s">
                                <option value="${list.id}">${list.personName}</option>
                            </c:forEach>
                        </select>
                    </div>--%>
                    <div class="boxBtn">
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


<%@ include file="../linkJs.jsp" %>
<script src="<%=path%>/js/searchFugitives.js"></script>
<script>
    $(function () {
        $(".clientSelect").searchableSelect();
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

        //输入现堪号点击确认
        $("#saveBtn").on("click", function () {
            var caseXkNo = $("#caseXkNo").val();
            if (caseXkNo == "") {
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


        $("button[name='noSaveBtn']").on("click", function () {
            location.href = '<%=path%>/delegate/noDnaRegs';
        });


        $('#myModal').modal("show");
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
        $('.wrand').on('click', function () {
            $(".conBoxs").fadeIn()
        })
        $(".btny,.colnes").on('click', function () {
            $(".conBoxs").fadeOut()
        })

    })
</script>
</body>
</html>