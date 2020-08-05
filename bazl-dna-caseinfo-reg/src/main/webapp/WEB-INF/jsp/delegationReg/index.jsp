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
        .clone{
            width: 100%;
            margin: 0;
            display: flex;
            justify-content: flex-end;
        }
        .utiName{
            width: 100%;
            display: flex;
            align-items: center;
            margin: 0;
        }
        .utiNamen{
            font-size:16px;
            font-family:Microsoft YaHei;
            font-weight:bold;
            margin: 0;
            color:rgba(51,51,51,1);
            padding-left: 10px;
        }
        .modal-body{
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .maxMain{
            width: 70%;
        }
        .modal-content{
            width: 70%;
        }
        .activet{
            width: 100%;
            border:1px dashed rgba(163,163,163,1);
            text-align: center;
            line-height: 35px;
            border-radius: 70px;
            margin-top: 10px;
            font-size:16px;
            font-family:Microsoft YaHei;
            font-weight:400;
            color:rgba(51,51,51,1);
            position: relative;
        }
        .actu{
            position: absolute;
            right: -10px;
            top: -20px;
            display: none;
        }
        .utiNames{
            width: 100%;
            display: flex;
            align-items: center;
            margin: 0;
            margin-top: 30px;
        }
        .boxInp{
            margin-top: 10px;
        }
        .boxInp input{
            text-align: center;
            height: 40px !important;
        }
        .boxBtn{
            width: 100%;
            display: flex;
            justify-content: space-around;
            padding-bottom: 20px;
        }
        .btn1{
            width:130px;
            height:42px;
            background:rgba(12,129,245,1);
            border: 0;
            color: white;
            font-size: 15px;
            border-radius: 5px;
            outline: #0c81f5;
        }
        .btn2{
            width:130px;
            height:42px;
            border:1px solid rgba(12,129,245,1);
            background: white;
            outline: #0c81f5;
            color: #0C81F5;
            font-size: 15px;
            border-radius: 5px;
        }
        .activet:hover{
            background: #3586FA;
            color: white;
            border: 1px solid #3586FA;
        }
        .warn{
            color: #FB6458;
            text-align: center;
        }
        .warn span{
            padding-left: 6px;
        }
    </style>
    <body>
    <!-- Modal1 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>
                    <div class="maxMain">
                        <div class="utiName">
                            <img src="<%=path%>/img/ju.png" alt="">
                            <p class="utiNamen">选择鉴定中心</p>
                        </div>
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
                        <p class="boxInp"><input type="text" id="caseXkNo" name="caseXkNo" class="form-control"
                                                 placeholder="请输入勘查号"></p>
                        <div class="boxBtn">
                            <button class="btn1" id="saveBtn">案件登记</button>
                            <button class="btn2" id="noSaveBtn" name="noSaveBtn">非现场案件登记</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal1 -->
    <%--<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
        <%--<div class="modal-dialog" role="document">--%>
            <%--<div class="modal-content">--%>
                <%--<div class="modal-body">--%>
                    <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                    <%--<div class="maxMain">--%>
                        <%--<div class="utiName">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">选择鉴定中心2</p>--%>
                        <%--</div>--%>
                        <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<div class="utiNames">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                        <%--</div>--%>
                        <%--<p class="boxInp"><input type="text"  id="caseXkNo1" name="caseXkNo" class="form-control"  placeholder="请输入勘察号"></p>--%>
                        <%--&lt;%&ndash;<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该现场勘验号对应案件已经登记并受理</span></p>&ndash;%&gt;--%>
                        <%--<div class="boxBtn">--%>
                            <%--&lt;%&ndash;<label class="hidden error">请输入现勘编号</label>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<button class="btn2" id="saveBtn">案件登记</button>&ndash;%&gt;--%>
                            <%--<button class="btn2">返回重新登记</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>


    <%--<!-- Modal2 -->--%>
    <%--<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
        <%--<div class="modal-dialog" role="document">--%>
            <%--<div class="modal-content">--%>
                <%--<div class="modal-body">--%>
                    <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                    <%--<div class="maxMain">--%>
                        <%--<div class="utiName">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">选择鉴定中心3</p>--%>
                        <%--</div>--%>
                        <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<div class="utiNames">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                        <%--</div>--%>
                        <%--<p class="boxInp"><input type="text" class="form-control" id="exampleInputName2" placeholder="请出入勘察号"></p>--%>
                        <%--<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该现场勘验号对应案件已经登记</span></p>--%>
                        <%--<div class="boxBtn">--%>
                            <%--<button class="btn2">返回重新登记</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

    <%--<!-- Modal3 -->--%>
    <%--<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
        <%--<div class="modal-dialog" role="document">--%>
            <%--<div class="modal-content">--%>
                <%--<div class="modal-body">--%>
                    <%--<p class="clone" data-dismiss="modal"><img src="<%=path%>/img/colse.png" alt=""></p>--%>
                    <%--<div class="maxMain">--%>
                        <%--<div class="utiName">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">选择鉴定中心4</p>--%>
                        <%--</div>--%>
                        <%--<p class="activet">${dnaLabName}<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<p class="activet">北京市公安司法鉴定中心<span class="actu"><img src="<%=path%>/img/dui.png" alt=""></span></p>--%>
                        <%--<div class="utiNames">--%>
                            <%--<img src="<%=path%>/img/ju.png" alt="">--%>
                            <%--<p class="utiNamen">输入案件现场勘查号</p>--%>
                        <%--</div>--%>
                        <%--<p class="boxInp"><input type="text" class="form-control" id="exampleInputName2" placeholder="请出入勘察号"></p>--%>
                        <%--<p class="warn"><img src="<%=path%>/img/warn (1).png" alt=""><span>该编号不存在</span></p>--%>
                        <%--<div class="boxBtn">--%>
                            <%--<button class="btn2">返回重新登记</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%@ include file="../linkJs.jsp" %>
    <script>
        $(function () {

            $(".activet").on("click", function () {
                if (!$(this).hasClass("active")) {
                    $(this).addClass("active").siblings().removeClass("active")
                    $(this).find("input").prop("checked", true)
                    $(this).siblings().find("input").prop("checked", false)
                }
            })

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


            $("button[name='noSaveBtn']").on("click",function(){
                location.href = '<%=path%>/delegate/noDnaRegs';
            });


            $('#myModal').modal("show");
            var oUl = document.getElementsByClassName('activet')
            var oUls = document.getElementsByClassName('actu')
            for(var i = 0;i < oUl.length;i++){
                oUl[i].index = i
                oUl[i].onclick = function () {
                    for (let j = 0;j < oUl.length;j++) {
                        $('.activet').eq(j).find('.actu').css('display','none')
                        $('.activet').eq(j).css({'background':'white',color:'black'})
                    }
                    $('.activet').eq(this.index).css({'background':'#0C81F5','color':'white'})
                    $('.activet').eq(this.index).find('.actu').css('display','block')
                }
            }
        })
    </script>
    </body>
</html>