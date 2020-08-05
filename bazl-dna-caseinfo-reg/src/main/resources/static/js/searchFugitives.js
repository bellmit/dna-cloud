//tr绑定点击事件
function bindTr(obj) {
    //先清空之前选中的信息
    $('input[type=radio][name="checkOne"]:checked').attr("checked", false);
    $(obj).find('input:radio[name="checkOne"]').attr("checked", "checked");
}
//在逃人员input点击
$("#searchFugitives").on("click",function(){
    if($("#checkBoxdiv").hasClass("hidden")){
        var searchFugitives = $("#searchFugitives").val();
        if (searchFugitives != null && searchFugitives != "") {
            $("#checkBoxdiv").removeClass("hidden")
        }
    }else{
        $("#checkBoxdiv").addClass("hidden")
    }
})
//监听在逃人员input内容
$("#searchFugitives").bind("input propertychange", function() {
    $("#checkBoxdiv").removeClass("hidden");
    var searchFugitives = $("#searchFugitives").val();
    var urlStr = "../delegate/fugitivesSearch?searchFugitives=" + searchFugitives;
    $.ajax({
        type: "get",
        url: urlStr,
        dataType: "json",
        success: function(data) {
            console.log(data);
            if(data.success || data.success == true || data.success == "true") {
                var checktr = ""
                $.each(data.fugitivesInfoVoList,function(num,index){
                    checktr +="<tr onclick='bindTr(this)'>"+
                        "<td><input type='radio' name='checkOne' value='"+index.entity.id+"'><label for='checkOne'>"+(num+1)+"</label></td>"+
                        "<td>"+index.entity.personName+"</td>"+
                        "<td>"+index.entity.personCard+
                        "<input type='hidden' name='fugitivesId' value='"+index.entity.id+"'/>"+
                        "<input type='hidden' name='personTypeName' value='"+index.personTypeName+"'/>"+
                        "<input type='hidden' name='personName' value='"+index.entity.personName+"'/>"+
                        "<input type='hidden' name='personCard' value='"+index.entity.personCard+"'/>"+
                        "<input type='hidden' name='personGenderName' value='"+index.personGenderName+"'/>"+
                        "<input type='hidden' name='personAge' value='"+index.entity.personAge+"'/>"+
                        "<input type='hidden' name='fugitiveNo' value='"+index.entity.fugitiveNo+"'/>"+
                        "<input type='hidden' name='personType' value='"+index.entity.personType+"'/>"+
                        "<input type='hidden' name='personGender' value='"+index.entity.personGender+"'/>"+
                        "<input type='hidden' name='timeId' value='"+new Date(index.entity.createDatetime).getTime()+"'/>"+
                        "</td>"+
                        "</tr>";
                })
                $("#checkBoxdivTbody").html(checktr);
            }else {
                alert("查询失败!");
            }
        }
    });

});