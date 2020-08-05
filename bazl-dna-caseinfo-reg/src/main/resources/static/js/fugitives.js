var personIds = "";
var sampleIds = "";
//在逃人员亲属信息
$("#fugitivesInfoRelationTbody").on("click", ".remove", function () {
    var sampleId = $(this).siblings("input[name='sampleId']").val();
    if (sampleId != undefined && sampleId != "") {
        sampleIds += "," + sampleId;
    }
    console.log(sampleIds)
    //$(this).parents("tr").remove()
    var trNode = $(this).parent().parent();
    $($(this).parent().parent().nextAll("tr")).each(function () {
        var a = $(this).find("td").eq(0).text();
        $(this).find("td").eq(0).text(a - 1);
    });
    trNode.remove();
});
//疑似在逃人员信息
$("#fugitivesInfoSuspectedTbody").on("click", ".remove", function () {
    var sampleId = $(this).siblings("input[name='sampleId']").val();
    if (sampleId != undefined && sampleId != "") {
        sampleIds += "," + sampleId;
    }
    console.log(sampleIds)
    //$(this).parents("tr").remove()
    var trNode = $(this).parent().parent();
    $($(this).parent().parent().nextAll("tr")).each(function () {
        var a = $(this).find("td").eq(0).text();
        $(this).find("td").eq(0).text(a - 1);
    });
    trNode.remove();
});
//删除检材信息
var sampleIdWzs = "";
$("#suspectedSampleTbody").on("click", ".remove", function () {
    var sampleIdWz = $(this).siblings("input[name='sampleId']").val();
    if (sampleIdWz != "") {
        sampleIdWzs += "," + sampleIdWz;
    }
    //$(this).parents("tr").remove()
    //删除检材信息，序号自动调整
    var trNode = $(this).parent().parent();
    $($(this).parent().parent().nextAll("tr")).each(function () {
        var a = $(this).find("td").eq(0).text();
        $(this).find("td").eq(0).text(a - 1);
    });
    trNode.remove();
});
//获取委托信息
function getConsignmentInfo() {
    var consignmentInfo = {};

    consignmentInfo.consignmentNo = $("[name='consignmentNo']").val();

    consignmentInfo.areaOrgCode = $("option:selected", "#areaOrgCode").val();//所属辖区code

    consignmentInfo.delegateOrgCode = $("#delegateOrgCode").val();//委托单位code
    consignmentInfo.delegateOrgName = $("#delegateOrgName").val();//委托单位名称
    //委托人信息1
    consignmentInfo.delegator1Id = $("option:selected", "#delegator1Id").val();//委托人1id
    consignmentInfo.delegator1Name = $("option:selected", "#delegator1Id").text();//委托人1
    consignmentInfo.delegator1Position = $("#delegator1Position").val();//职务
    consignmentInfo.delegator1PaperworkType = $("#delegator1PaperworkType").val();//z证件
    consignmentInfo.delegator1PaperworkNo = $("#delegator1PaperworkNo").val();//证件号
    consignmentInfo.delegator1PhoneNumber = $("#delegator1PhoneNumber").val();//电话
    //委托人信息2
    consignmentInfo.delegator2Id = $("option:selected", "#delegator2Id").val();//委托人2id
    consignmentInfo.delegator2Name = $("option:selected", "#delegator2Id").text();//委托人2
    consignmentInfo.delegator2Position = $("#delegator2Position").val();//职务
    consignmentInfo.delegator2PaperworkType = $("#delegator2PaperworkType").val();//z证件
    consignmentInfo.delegator2PaperworkNo = $("#delegator2PaperworkNo").val();//证件号
    consignmentInfo.delegator2PhoneNumber = $("#delegator2PhoneNumber").val();//电话

    //鉴定要求
    var identifyRequirementArr = []
    $(".btn-checkbox").children(".active").each(function (i, item) {
        identifyRequirementArr.push($(this).attr("value"))
    })
    consignmentInfo.identifyRequirement = identifyRequirementArr.join(",")

    //鉴定类别
    consignmentInfo.identifyType = "DNA鉴定";//DNA鉴定
    //原鉴定情况
    consignmentInfo.preIdentifyDesc = $("#preIdentifyDesc").val();
    //重新鉴定原因
    consignmentInfo.reidentifyReason = $("#reidentifyReason").val();

    consignmentInfo.consignmentId = $("#consignmentId").val();//委托id

    console.log(consignmentInfo);
    return consignmentInfo;
}
//获取案件信息
function getCaseInfoDna() {
    var caseInfoDna = {};
    //涉案信息
    caseInfoDna.caseName = $("#exampleFormControlTextarea1").val();
    caseInfoDna.caseId = $("#caseId").val();
    console.log("js编号");
    console.log(caseInfoDna)
    return caseInfoDna;
}
//在逃人员信息
function getFugitivesInfo() {
    var fugitivesInfo = {};

    fugitivesInfo.id = $("#fugitivesId").val();

    return fugitivesInfo;
}
//获取在逃人员亲属和疑似在逃人员信息
function getLimsPersonInfo() {
    var limsPersonInfoArr = new Array();
    //在逃人员亲属信息和样本
    $("#fugitivesInfoRelationTbody").find("tr").each(function () {
        limsPersonInfo = {};
        // limsPersonInfo.personType = $(this).find("input[name='personType']").val()
        limsPersonInfo.personType = "08"
        limsPersonInfo.personName = $(this).find("input[name='personName']").val()
        limsPersonInfo.personGender = $(this).find("input[name='personGender']").val()
        limsPersonInfo.personIdCard = $(this).find("input[name='idCard']").val()
        limsPersonInfo.relationType = $(this).find("input[name='kinship']").val()
        limsPersonInfo.personCurrentAddress = $(this).find("input[name='address']").val()
        limsPersonInfo.personHeight = $(this).find("input[name='height']").val()
        limsPersonInfo.personWeight = $(this).find("input[name='weight']").val()
        limsPersonInfo.certificateType = $(this).find("input[name='certificateType']").val()
        limsPersonInfo.nation = $(this).find("input[name='nation']").val()
        limsPersonInfo.personRemark = $(this).find("input[name='remark']").val()

        //人员id
        limsPersonInfo.personId = $(this).find("input[name='personId']").val()
        //圖片
        limsPersonInfo.personFrontPicture = $(this).find("input[name='personBase']").val()
        //base64
        limsPersonInfo.sampleInfoDnaList = []

        var sampleInfoDna = {}
        sampleInfoDna.sampleType = $(this).find("input[name='sampleType']").val()
        sampleInfoDna.sampleName = $(this).find("input[name='sampleName']").val()
        sampleInfoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val()
        sampleInfoDna.samplePacking = $(this).find("input[name='samplePacking']").val()
        sampleInfoDna.extractDatetime = $(this).find("input[name='extractTime']").val();
        sampleInfoDna.extractMethod = $(this).find("input[name='extractMethod']").val()
        sampleInfoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val()
        sampleInfoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val()
        sampleInfoDna.sampleId = $(this).find("input[name='sampleId']").val()
        sampleInfoDna.sampleDnaPicture = $(this).find("input[name='sampleBase']").val()
        limsPersonInfo.sampleInfoDnaList.push(sampleInfoDna);

        limsPersonInfoArr.push(limsPersonInfo);
    })
    //疑似在逃人员信息和样本
    $("#fugitivesInfoSuspectedTbody").find("tr").each(function () {
        limsPersonInfo = {};
        // limsPersonInfo.personType = $(this).find("input[name='personType']").val()
        limsPersonInfo.personType = "07"
        limsPersonInfo.personName = $(this).find("input[name='personName']").val()
        limsPersonInfo.personGender = $(this).find("input[name='personGender']").val()
        limsPersonInfo.personIdCard = $(this).find("input[name='idCard']").val()
        limsPersonInfo.personCurrentAddress = $(this).find("input[name='address']").val()
        limsPersonInfo.personHeight = $(this).find("input[name='height']").val()
        limsPersonInfo.personWeight = $(this).find("input[name='weight']").val()
        limsPersonInfo.certificateType = $(this).find("input[name='certificateType']").val()
        limsPersonInfo.nation = $(this).find("input[name='nation']").val()
        limsPersonInfo.personRemark = $(this).find("input[name='remark']").val()

        //人员id
        limsPersonInfo.personId = $(this).find("input[name='personId']").val()
        //圖片
        limsPersonInfo.personFrontPicture = $(this).find("input[name='personBase']").val()
        //base64
        limsPersonInfo.sampleInfoDnaList = []

        var sampleInfoDna = {}
        sampleInfoDna.sampleType = $(this).find("input[name='sampleType']").val()
        sampleInfoDna.sampleName = $(this).find("input[name='sampleName']").val()
        sampleInfoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val()
        sampleInfoDna.samplePacking = $(this).find("input[name='samplePacking']").val()
        sampleInfoDna.extractDatetime = $(this).find("input[name='extractTime']").val();
        sampleInfoDna.extractMethod = $(this).find("input[name='extractMethod']").val()
        sampleInfoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val()
        sampleInfoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val()
        sampleInfoDna.sampleId = $(this).find("input[name='sampleId']").val()
        sampleInfoDna.sampleDnaPicture = $(this).find("input[name='sampleBase']").val()
        limsPersonInfo.sampleInfoDnaList.push(sampleInfoDna);

        limsPersonInfoArr.push(limsPersonInfo);
    })
    console.log(limsPersonInfoArr)
    return limsPersonInfoArr;
}
//获取检材信息
function getSampleInfoDna() {
    var sampleInfoDnaArr = new Array();
    $("#suspectedSampleTbody").find("tr").each(function () {
        samplefoDna = {};
        samplefoDna.sampleType = $(this).find("input[name='sampleType']").val();
        samplefoDna.sampleName = $(this).find("input[name='sampleName']").val();
        samplefoDna.sampleDesc = $(this).find("input[name='sampleDescribe']").val();
        samplefoDna.samplePacking = $(this).find("input[name='samplePacking']").val();
        samplefoDna.extractDatetime = $(this).find("input[name='extractTime']").val();
        samplefoDna.extractMethod = $(this).find("input[name='extractMethod']").val();
        samplefoDna.sampleCarrier = $(this).find("input[name='sampleCarrier']").val();
        samplefoDna.samplePurpose = $(this).find("input[name='inspectionObjective']").val();
        samplefoDna.sampleIdwz = $(this).find("input[name='sampleId']").val();
        samplefoDna.sampleId = $(this).find("input[name='sampleId']").val();
        samplefoDna.extractDatetime = $(this).find("input[name='extractTime']").val();
        samplefoDna.sampleMaterialPicture = $(this).find("input[name='samplePicture']").val();
        samplefoDna.coreTakenStats = $(this).find("select[name='coreTakenStats']").val();
        sampleInfoDnaArr.push(samplefoDna);
    })
    return sampleInfoDnaArr;
}

//验证滚动1
function scroll(form) {
    $(form).find("small[data-bv-result='INVALID']").each(function (index, ele) {
        if (index == 0) {
            $("body,html").animate({
                scrollTop: $(this).offset().top - 100
            })
            $(this).prev().focus()
        }
    })
}
// 页面滚动定位
window.onscroll = function () {
    if ($(document).scrollTop() >= 112) {
        $(".fixedBox").removeClass("hidden")
        var currentScroll = $(this).scrollTop()
        var currentSection
        $(".Modular").each(function () {
            var divPosition = $(this).offset().top - 62;
            if (currentScroll > divPosition) {
                currentSection = $(this).find(".panel-heading").children().html()
            }
        })
        $(".fixedBox").find("li").each(function () {
            if ($(this).html() == currentSection) {
                $(this).addClass("now").siblings().removeClass("now")
            }
        })
    } else {
        $(".fixedBox").addClass("hidden")
    }
}
// 顶部导航点击定位
$(".fixedBox").find("li").click(function () {
    var that = $(this)
    $(".Modular").each(function () {
        if (that.html() == $(this).find(".panel-heading").children().html()) {
            $("html,body").animate({
                scrollTop: $(this).offset().top - 60
            }, 500);
        }
    })
    that.addClass("liActive").siblings().removeClass("liActive")
})

function Format(now, mask) {
    var d = now;
    var zeroize = function (value, length) {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };

    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd':
                return d.getDate();
            case 'dd':
                return zeroize(d.getDate());
            case 'ddd':
                return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd':
                return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M':
                return d.getMonth() + 1;
            case 'MM':
                return zeroize(d.getMonth() + 1);
            case 'MMM':
                return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM':
                return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy':
                return String(d.getFullYear()).substr(2);
            case 'yyyy':
                return d.getFullYear();
            case 'h':
                return d.getHours() % 12 || 12;
            case 'hh':
                return zeroize(d.getHours() % 12 || 12);
            case 'H':
                return d.getHours();
            case 'HH':
                return zeroize(d.getHours());
            case 'm':
                return d.getMinutes();
            case 'mm':
                return zeroize(d.getMinutes());
            case 's':
                return d.getSeconds();
            case 'ss':
                return zeroize(d.getSeconds());
            case 'l':
                return zeroize(d.getMilliseconds(), 3);
            case 'L':
                var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt':
                return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT':
                return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z':
                return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default:
                return $0.substr(1, $0.length - 2);
        }
    });
}

