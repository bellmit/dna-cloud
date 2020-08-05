<template>
  <div>
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <router-link tag="b" to>混合型快速拆分对比</router-link>》
      <span>STRMIX拆分</span>
    </div>
    <div class="case_title">
      <Icon type="ios-square case_title_icon" />混合分型STR拆分
    </div>
    <div class="break_box">
      <div class="break_content">
        <div class="break_box_title">
          <!-- 
            此处编号待定
            <p>
              <Icon type="ios-list icon_name" />样本编号:11111
            </p> 
          -->
          <p>
            <span></span>STRmix拆分报告
            <Upload
              :action="uploadUrl"
              :show-upload-list="false"
              class="break_box_upload"
              :before-upload="beforUpload"
              :format="['pdf']"
              :name="'splitReportFiles'"
              :on-success="handelBreakSuccess"
            >
              <Button class="break_upload_btn">导入</Button>
            </Upload>
          </p>
          <p>
            <span></span>贡献者数量
            <button class="break_content_count">{{this.breakList.contributorCount}}</button>
          </p>
          <div class="chai_btn_two" v-show="chaiShowBtn">
            <span @click="Details_show">快速拆分比对记录</span>
          </div>
        </div>
        <div class="break_box_err" v-show="breakShow">
          <img src="../../assets/img/break_err.png" class="brear_err_img" />
          <br />
          <p>您好，目前暂无数据显示</p>
          <br />
          <p>您可先导入STRmix拆分报告后在进行查看</p>
        </div>
        <!-- ---before -->
        <div class="break_box_success" v-show="!breakShow">
          <div class="break_box_success_title">
            <div
              v-for="(item,index) in breakList.strMixRapiGeneComparisonListVos"
              class="float_div"
              :key="item.index"
            >
              <Icon type="ios-notifications icon_success_title" />
              {{index+1}}号贡献者权重≥
              <Select
                style="display:inline-block;float:none;width:80px;"
                placeholder="99%"
                @on-change="choosePercent($event,item,index)"
                :label-in-value="true"
              >
                <Option
                  v-for="result in item.moreThanTheCountList"
                  :value="result.moreThanThevalue"
                  :key="result.percent"
                >{{result.percent}}</Option>
              </Select>的基因位点数
              <span>({{item.val?item.val:item.moreThanTheCountList[9].moreThanThevalue}})</span>个
              <p @click="handelBreakContrast(item,index)">快速比对</p>
            </div>
            <!-- <div v-for="(item,index) of breakList.strMixRapiGeneComparisonListVos" class="float_div" :key="item.index">
                            <Icon type="ios-notifications icon_success_title"/>
                            {{index+1}}号贡献者权重≥
                                <Select style="display:inline-block;float:none;width:80px;" v-model="item.moreThanTheCountList[9].moreThanThevalue" @on-change="choosePercent($event,item)" :label-in-value="true">
                                    <Option v-for="item of item.moreThanTheCountList" :value="item.moreThanThevalue" :key="item.percent">{{item.percent}}</Option>
                                    
                                </Select>
                            的基因位点数<span>({{item.val?item.val:item.moreThanTheCountList[9].moreThanThevalue}})</span>个
                            <p @click="handelBreakContrast(item)">快速比对</p>
            </div>-->
          </div>
          <div class="break_success_content">
            <ul class="break_success_text">
              <div class="break_box_text_title">
                <span></span>基因座名称
              </div>
              <li class="break_text_first">基因座</li>
              <div
                v-for="(item,index) of breakList.mixedSampleGeneList"
                :class="'break_text_li data_index'+index"
              >
                <li>{{item.markerName}}</li>
              </div>
            </ul>
            <ul class="break_success_text1 break_success_parting">
              <div class="break_box_text_title">
                <span></span>混合分型
              </div>
              <li class="break_text_first">分型</li>
              <div
                v-for="(item,index) of breakList.mixedSampleGeneList"
                :class="'break_text_li data_index'+index"
              >
                <li>{{item.geneStr1}}</li>
              </div>
            </ul>

            <ul
              class="break_success_table"
              v-for="(item,index) of breakList.strMixRapiGeneComparisonListVos"
              :name="'break_success_table'+index"
            >
              <div class="break_success_table_title">
                <p>
                  <span></span>
                  {{index+1}}号贡献者（{{item.contributorWeight}}）
                </p>
                <Button type="primary" @click="quickCheck('break_success_table'+index)">一键选中</Button>
                <Button
                  class="break_table_btn"
                  @click="chaifenruku('break_success_table'+index)"
                  :name="'break_success_table'+index"
                >拆分入库</Button>
              </div>
              <li class="break_table_title_one">
                <p>
                  <Checkbox></Checkbox>
                </p>
                <p>等位基因</p>
                <p>权重</p>
                <p>权重≥99%</p>
              </li>

              <div
                v-for="(items,index) in item.contributorGeneList"
                :class="'break_table_display data_index'+index"
              >
                <li v-for="(i,index) in items.geneAlleleList">
                  <!-- <Checkbox><Checkbox @on-change="changeGene(item,items,$event)" class="checkQuery" :indeterminate="indeterminate"></Checkbox></p> -->
                  <p>
                    <input type="checkbox" class="checkQuery" name="checkQuery" />
                  </p>
                  <p>{{i.allele}}</p>
                  <p>{{i.weights}}</p>
                  <p>{{i.ninetyNinePercentWeights}}</p>
                  <!--                                    <span class="oSpan">{{(index+1)*31}}</span>-->
                </li>
              </div>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <Modal v-model="geneListModal" @on-ok="sureRuku()">
      <p slot="header" style="text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>查看已选基因信息</span>
      </p>
      <div style="text-align:center">
        <Table :columns="geneListCol" :data="geneListData"></Table>
      </div>
      <!-- <div slot="footer">
      </div>-->
    </Modal>
    <Modal class="model_con" v-model="geneListModal_two">
      <!-- style="text-align:center -->
      <p slot="header">
        <Icon type="ios-information-circle"></Icon>
        <span>快速拆分比对记录</span>
      </p>
      <div style="text-align:center">
        <Table :columns="geneListCol_two" :data="geneListData_two"></Table>
      </div>
    </Modal>
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
    <!-- 查看按钮 -->
    <Modal class="model_con" v-model="SeeDetailsModel">
      <p slot="header">
        <Icon type="ios-information-circle"></Icon>
        <span>详情信息</span>
      </p>
      <div style="text-align:center">
        <Table :columns="SeeDetailsCol" :data="SeeDetailsData"></Table>
      </div>
    </Modal>
  </div>
</template>
<script>
import hybridSplitServers from "../../servers/hybridSplitServers";
import { constants } from "zlib";
// import breakServers from '../../servers/breakServers'
export default {
  name: "breakBox",
  data() {
    return {
      // 快速拆分比对记录 model
      geneListModal_two: false,
      // 快速拆分比对记录  查看按钮 model
      SeeDetailsModel: false,
      // 快速拆分比对记录  查看详情 表格标题
      SeeDetailsCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "基因座",
          key: "markerName" // sampleNo
        },
        {
          title: "拆分等位基因",
          key: "allele" // sampleNo
        }
      ],
      // 快速拆分比对记录  查看详情 表格数据
      SeeDetailsData: [],
      // 快速拆分比对记录 表格标题
      geneListCol_two: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "贡献者",
          key: "contributorName",
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    display: "inline-block"
                  }
                },
                params.row.contributorName + "-号贡献者"
              )
            ]);
          }
        },
        {
          title: "比中状态",
          key: "bz",
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  on: {
                    click: event => {}
                  }
                },
                "比对中"
              )
            ]);
          }
        },
        {
          title: "操作",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: event => {
                      console.log(params);
                      this.SeeDetailsModel = true;
                      this.SeeDetailsData = params.row.geneMap.resultList;
                    }
                  }
                },
                "查看"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: event => {
                      let Id_info = {
                        id: params.row.id
                      };
                      hybridSplitServers.deleteSpli(Id_info).then(res => {
                        this.Details_show();
                      });
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      // 快速拆分比对记录 表格数据
      geneListData_two: [],
      Contributor: 1,
      chaiShowBtn: false,
      FileName: "",
      loadingShow: false,
      genneId: "",
      value: "",
      breakShow: true,
      // uploadData:{},
      uploadUrl: "",
      breakSampleId: "",
      sample: [],
      breakList: [],
      type: 1,
      geneList0: [],
      geneList1: [],
      geneList2: [],
      geneList3: [],
      geneList4: [],
      geneListFirst: [],
      // 拆分入库 model
      geneListModal: false,
      // 拆分入库 表格标题
      geneListCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "基因座",
          key: "geneName"
        },
        {
          title: "等位基因",
          key: "allele"
        }
      ],
      // 拆分入库 表格数据
      geneListData: [],
      checkTrueFalse: false,
      indeterminate: false,
      strNum:1,
      map_num:0
    };
  },
  created() {
    // this.breakSampleId = JSON.parse(localStorage.getItem('hybridId'));
    // this.breakDataList()
  },
  mounted() {},
  updated() {
    var _this = this;
    var geneListInfo;
    $(".break_success_table .break_table_display .checkQuery").click(
      function() {
        // console.log($(this).parents(".break_success_table").attr("name"));
        if (
          $(this)
            .parents(".break_success_table")
            .attr("name") == "break_success_table0"
        ) {
          geneListInfo = _this.geneList0;
          if ($(this).is(":checked")) {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table0'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
            $(this)
              .parents("li")
              .siblings()
              .find('input[name="checkQuery"]')
              .prop("checked", false);
            geneListInfo.push(geneInfo);
            // console.log(_this.geneList0);
          } else {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table0'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
          }
        } else if (
          $(this)
            .parents(".break_success_table")
            .attr("name") == "break_success_table1"
        ) {
          geneListInfo = _this.geneList1;
          if ($(this).is(":checked")) {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table1'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
            $(this)
              .parents("li")
              .siblings()
              .find('input[name="checkQuery"]')
              .prop("checked", false);
            geneListInfo.push(geneInfo);
            // console.log(_this.geneList1);
          } else {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table1'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
          }
        } else if (
          $(this)
            .parents(".break_success_table")
            .attr("name") == "break_success_table2"
        ) {
          geneListInfo = _this.geneList2;
          if ($(this).is(":checked")) {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table2'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
            $(this)
              .parents("li")
              .siblings()
              .find('input[name="checkQuery"]')
              .prop("checked", false);
            geneListInfo.push(geneInfo);
            // console.log(_this.geneList2);
          } else {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table2'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
          }
        } else if (
          $(this)
            .parents(".break_success_table")
            .attr("name") == "break_success_table3"
        ) {
          geneListInfo = _this.geneList3;
          if ($(this).is(":checked")) {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table3'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
            $(this)
              .parents("li")
              .siblings()
              .find('input[name="checkQuery"]')
              .prop("checked", false);
            geneListInfo.push(geneInfo);
            // console.log(_this.geneList3);
          } else {
            let geneInfo = {};
            geneInfo.allele = $(this)
              .parent("p")
              .next()
              .text();
            let index = $(
              "ul[name='break_success_table3'] .break_table_display"
            ).index($(this).parents(".break_table_display"));
            geneInfo.geneName = $(".break_success_text .break_text_li")
              .eq(index)
              .text();
            if (
              geneListInfo.findIndex(
                item => item.geneName == geneInfo.geneName
              ) != -1
            ) {
              geneListInfo.splice(
                geneListInfo.findIndex(
                  item => item.geneName == geneInfo.geneName
                ),
                1
              );
            }
          }
        }
      }
    );
    if (this.breakShow == true) {

    } else {
        if(this.map_num == 0){
            return;
        }
        this.breakList.mixedSampleGeneList.map((item, index) => {
            var css = $(".data_index" + index);
            this.cssHeight(css);
        });
    }
  },
  methods: {
    // 快速比对结果按钮
    Details_show() {
      this.geneListModal_two = true;
      let file_Name = {
        fileName: this.FileName
      };
      hybridSplitServers.recording(file_Name).then(res => {
        this.geneListData_two = res.data;
      });
    },
    /*
      下拉框监听事件 发生改变时，返回当前选中的参数 例：‘label:95%’,
      vlaue:10,label代表当前选中了百分之多少，vlaue代表有多少个参数大于等于95%
    */
    choosePercent(val, item, index) {
      item.percent = val.label;
      item.val = val.value;
      this.$forceUpdate();
    },
    changeGene(item, items, event) {},
    cssHeight(hMax) {
      var h_max = 0;
      hMax.each(function() {
        var h = $(this).height();
        h_max = h_max < h ? h : h_max;
      });
      hMax.css("height", h_max + 2);
    },
    //快速对比
    handelBreakContrast(item, index) {
      var contrast;
      var percent;
      // return
      switch (item.percent) {
        case "90%":
          contrast = JSON.stringify(item.moreGeneList0);
          //   console.log(contrast);
          break;
        case "91%":
          contrast = JSON.stringify(item.moreGeneList1);
          //   console.log(contrast);
          break;
        case "92%":
          contrast = JSON.stringify(item.moreGeneList2);
          //   console.log(contrast);
          break;
        case "93%":
          contrast = JSON.stringify(item.moreGeneList3);
          //   console.log(contrast);
          break;
        case "94%":
          contrast = JSON.stringify(item.moreGeneList4);
          // console.log(constants);
          break;
        case "95%":
          // 参数改动
          contrast = JSON.stringify(item.moreGeneList5);
          // contrast = JSON.stringify(item.moreThanTheGeneList6);
          break;
        case "96%":
          contrast = JSON.stringify(item.moreGeneList6);
          //   console.log(contrast);
          break;
        case "97%":
          contrast = JSON.stringify(item.moreGeneList7);
          //  console.log(contrast);
          break;
        case "98%":
          contrast = JSON.stringify(item.moreGeneList8);
          // console.log(contrast);
          break;
        default:
        case "99%":
          contrast = JSON.stringify(item.moreGeneList9);
          // console.log(contrast);
          break;
      }
      if (item.percent) {
        percent = item.percent;
      } else if (item.percent == null) {
        percent = "99%";
      }
      sessionStorage.setItem("contrast", contrast);
      sessionStorage.setItem("percent", percent);
      this.Contributor = index + 1;
      this.$router.push({
        name: "QuickComparison",
        params: { fileName: this.FileName, Contributor: this.Contributor }
      });
    },
    //上传成功
    handelBreakSuccess(response, file) {
      // console.log(response);
      if (response.code == 1) {
        this.chaiShowBtn = true;
        this.map_num = 1;
      } else {
        this.chaiShowBtn = false;
      }
      this.FileName = file.name;
      this.loadingShow = false;
      this.breakList = response.data;
      this.breakShow = false;
      this.type = 2;
      this.genneId = response.data.strMixRapiGeneComparisonListVos[0].geneId;
    },
    //文件上传之前
    beforUpload(files) {
      this.map_num = 0;
      this.breakList = [];
      this.loadingShow = true;
      this.uploadUrl = "api_iLabSTRmix/main/stitchingComparisonVo";
      // this.uploadData = {
      //     sampleId:this.breakSampleId.sampleId,
      //     caseId:this.sample.caseId
      // };
      let promise = new Promise(resolve => {
        this.$nextTick(function() {
          resolve(true);
        });
      });
      return promise;
    },
    // breakDataList(){//初始化数据
    //     let breakSampleId ={
    //         sampleId:this.breakSampleId.sampleId,
    //         caseNo:this.breakSampleId.caseNo
    //     };
    //     breakServers.breakList(breakSampleId).then(res=>{
    //         console.log(res);
    //         this.sample = res.data
    //     })
    // },
    quickCheck(par) {
      var zz = par.substring(par.length - 1);
      let that = this;
      var genefirstInfo;
      // 更改 11/6
      var sum =
        $("ul[name=" + par + "]").find("input[type='checkbox']").length - 1;
      var list = $("ul[name=" + par + "]").find(
        "input[type='checkbox']:checked"
      ).length;
      $("ul[name=" + par + "]")
        .find(".break_table_display")
        .each(function(num, text) {
          if (list == 0) {
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", true);
            // that['geneList'+ zz]=[]
            genefirstInfo = that["geneList" + zz];
            let geneInfo = {};
            geneInfo.allele = $(this)
              .find("li:first-child p:eq(1)")
              .text();
            geneInfo.geneName = $(this)
              .parents(".break_success_content")
              .find(".break_text_li:eq(" + num + ") li")
              .text();
            genefirstInfo.push(geneInfo);
          } else if (list == sum) {
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", false);
            that["geneList" + zz] = [];
          } else if (list != 0 && list != sum) {
            // console.log("第三种情况");
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", false);
            that["geneList" + zz] = [];
          }
        });
      /*
        原判断
        $("ul[name=" + par + "]")
          .find(".break_table_display")
          .each(function(num, text) {
            if (
              $(this)
                .find("input")
                .prop("checked")
            ) {
              $(this)
                .find("li")
                .each(function() {
                  $(this)
                    .find("p:eq(0) input")
                    .prop("checked", false);
                });
              that["geneList" + zz] = [];
            } else {
              $(this)
                .find("li:eq(0) p:eq(0) input")
                .prop("checked", true);
              genefirstInfo = that["geneList" + zz];
              let geneInfo = {};
              geneInfo.allele = $(this)
                .find("li:first-child p:eq(1)")
                .text();
              geneInfo.geneName = $(this)
                .parents(".break_success_content")
                .find(".break_text_li:eq(" + num + ") li")
                .text();
              genefirstInfo.push(geneInfo);
            }
          });
      */
    },
    chaifenruku(par) {
      let str = par
      let strSUb = str.substring(19);
      this.strNum = parseInt(strSUb)+1;
      // console.log(this.strNum);
      var geneLength = $(".break_success_text .break_text_li").length;
      if (par == "break_success_table0") {
        if (this.geneList0.length < geneLength) {
          this.$Message.error("有基因座未进行选中");
          return false;
        }
        this.geneListModal = true;
        this.geneListData = this.geneList0;
      } else if (par == "break_success_table1") {
        if (this.geneList1.length < geneLength) {
          this.$Message.error("有基因座未进行选中");
          return false;
        }
        this.geneListModal = true;
        this.geneListData = this.geneList1;
      } else if (par == "break_success_table2") {
        if (this.geneList2.length < geneLength) {
          this.$Message.error("有基因座未进行选中");
          return false;
        }
        this.geneListModal = true;
        this.geneListData = this.geneList2;
      } else if (par == "break_success_table3") {
        if (this.geneList3.length < geneLength) {
          this.$Message.error("有基因座未进行选中");
          return false;
        }
        this.geneListModal = true;
        this.geneListData = this.geneList3;
      } else if (par == "break_success_table4") {
        if (this.geneList4.length < geneLength) {
          this.$Message.error("有基因座未进行选中");
          return false;
        }
        this.geneListModal = true;
        this.geneListData = this.geneList4;
      }
    },
    sureRuku() {
      // let str = $(".break_success_table_title p").text();
      // console.log(str);
    //  let str =  $(this).parent().parent().attr("name");
    //  console.log(str);
      let comparisonInfo = {
        condition: "0",
        fileName: this.FileName,
        contributorName: this.strNum,
        geneInfo: JSON.stringify(this.geneListData)
        // genneId: this.genneId
      };
      hybridSplitServers.allCompare(comparisonInfo).then(res => {
        if (res.code == 0) {
            this.$Modal.error({
              title: "提示",
              content: "入库失败!"
            });
          }
        if (res.data.code == 1) {
          this.$Modal.error({
            title: "提示",
            content: "请勿重复入库!"
          });
        } else if (res.data.code == 0) {
          this.$Modal.success({
            title: "提示",
            content: "入库成功！"
          });
        }
      });
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/title";
@import "../../assets/styles/break";
</style>
