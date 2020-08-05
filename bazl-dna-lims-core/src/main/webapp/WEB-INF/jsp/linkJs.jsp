<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/lib/jquery.searchableSelect/jquery.searchableSelect.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrapvalidator-master-0.52/js/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/lib/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/echarts/echarts-liquidfill.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/kkpager-master/kkpager.js"></script>
<script src="${pageContext.request.contextPath}/lib/steps.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<div class="modal fade" id="shadow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog" role="document">
        <i class="fa fa-spinner" aria-hidden="true"></i>
        <span>请稍等</span>
    </div>
</div>
<script type="text/javascript">
    var linkIp = "http://192.168.1.199:70";
    var synthehourseUrl = "/bazlDnaData/datamanage/builddatabase";      //数据综合管理>在逃人员鉴定管理
    var synthesharUrl = "/bazlDnaData/datamanage/case";                 //数据综合管理>建库数据管理
    var quickkinshipUrl = "/bazlDnaData/quickmanage/Kinship";           //快速比对管理>亲缘比对
    var quickmixStrUrl = "/bazlDnaData/quickmanage/STR";                //快速比对管理>混合STR比对
    var quicksameUrl = "/bazlDnaData/quickmanage/Homotype";             //快速比对管理>同型比对
    var quickYstrUrl = "/bazlDnaData/quickmanage/Y_STR";                //快速比对管理>Y_STR比对
    var warehomotypeUrl = "/bazlDnaData/thanin/homotype";               //入库比对管理>同型比对
    var wareaffinityUrl = "/bazlDnaData/thanin/kinship";                //入库比对管理>亲缘比对
    var warehouseYstrUrl = "/bazlDnaData/thanin/ystr";                  //入库比对管理>Y-STR比对
    var configKitManageUrl = "/bazlDnaData/geneticmanage/Kit";          //基础配置管理>试剂盒管理
    var configpopulaUrl = "/bazlDnaData/geneticmanage/PopulationGene";   //基础配置管理>种群基因频率
    var configStrConfigUrl = "/bazlDnaData/setting/warehousing/str";       //基础配置管理>STR自动比对设置
    var configStrGeneUrl = "/bazlDnaData/geneticmanage/STR";               //基础配置管理>STR基因座管理
    var configwareUrl = "/bazlDnaData/quickmanage/Homotype";               //基础配置管理>数据入库条件设置
    var configYstrConfigUrl = "/bazlDnaData/setting/warehousing/ystr";     //基础配置管理>Y-STR自动比对设置
    var configYstrGeneUrl = "/bazlDnaData/geneticmanage/YSTR";             //基础配置管理>Y-STR基因座管理
    var qualityControlInfo = "/bazlDnaData/datamanage/QualityControl";     //质量控制管理
</script>