<template>
  <div>
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <!-- <router-link tag="b" to="/HybridSplit">混合型快速拆分对比</router-link>》 -->
      <router-link tag="b" to="/HybridSplit">STRMIX拆分</router-link>》
      <span>快速对比</span>
    </div>
    <div class="contrast_box">
      <div class="contrast_title">
        <Icon type="ios-square case_title_icon" />快速对比
      </div>
      <div class="contrast_min">
        <div class="contrast_min_left">
          <Table
            class="contrast_min_left_table"
            :columns="contrast_head"
            :data="table_list"
            :row-class-name="contrast_table_class"
            border
          ></Table>
        </div>
        <!-- 多选条件容器 -->
        <div class="contrast_min_right" v-if="LCDShow">
          <div class="contrast_min_right_box">
            <div class="contrast_right_title">快速比对设置</div>
          </div>
          <div class="contrast_right_min">
            <div class="contrast_right_all">
              <div class="contrast_right_all_title">
                <span></span>比对目标类型
              </div>
              <Checkbox
                label="香蕉"
                class="contrast_right_all_checked_first"
                size="large"
                @on-change="handelCheckboxAll"
                v-model="checkAllValue"
              >
                <span class="contrast_all_span">全选</span>
              </Checkbox>
              <br />
              <CheckboxGroup v-model="checkValue" @on-change="handelCheckTarget">
                <Checkbox label="0" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">现场物证</span>
                </Checkbox>
                <br />
                <Checkbox label="01" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">嫌疑人</span>
                </Checkbox>
                <br />
                <Checkbox label="03" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">受害人</span>
                </Checkbox>
                <br />
                <Checkbox label="99" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">违法犯罪人员</span>
                </Checkbox>
                <br />
                <Checkbox label="02" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">嫌疑人亲属</span>
                </Checkbox>
                <br />
                <Checkbox label="04" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">受害人亲属</span>
                </Checkbox>
                <br />
                <Checkbox label="3" class="contrast_right_all_checked" size="large">
                  <span class="contrast_all_span">质控样本库</span>
                </Checkbox>
                <br />
              </CheckboxGroup>
            </div>

            <div class="contrast_right_conditions">
              <div class="contrast_right_conditions_title" style="width:400;">
                <RadioGroup style="width:400px;" @on-change="handelCheckboxConditions">
                  <div class="contrast_right_all_title" style="color: rgba(255, 102, 96, 1)">
                    <span style="background:rgba(255,90,86,1);"></span>比中条件
                  </div>
                  <!-- v-model="Conditions" -->
                  <CheckboxGroup>
                    <Radio label="0" class="contrast_right_all_checked_first" size="large">
                      <font></font>
                    </Radio>
                    <span class="contrast_all_span">无容差比中</span>
                  </CheckboxGroup>
                  <div class="contrast_right_result">
                    <Radio
                      label="7"
                      class="contrast_right_all_checked right_span"
                      size="large"
                      style="float: left"
                    >
                      <span></span>
                    </Radio>
                    <input v-model="InputVlaue" type="text" class="contrast_right_input" />
                    <span style="float: left;margin-top: 20px;font-size: 14px;font-weight: bold">个容差</span>
                    <p class="contrast_right_p">
                      <Select v-model="list_select" size="large">
                        <Option value="全容差">全容差</Option>
                      </Select>
                    </p>
                  </div>
                </RadioGroup>
              </div>
            </div>
          </div>
        </div>
        <!-- 比对结果表格容器 -->
        <div class="contrast_min_right" v-else>
          <div class="case_title_text">
            <span></span>
            <span :v-model="span_value">{{this.span_value}}</span>
          </div>
          <!-- stripe -->
          <div class="data_table_bottom" style="margin-bottom:50px;">
            <Table
              class="data_table_list"
              :columns="table_colums"
              :data="data_list"
              size="small"
              stripe
              border
              :show-header="true"
            ></Table>
            <div class="page_box">
              <div>
                <Page
                  @on-change="PageChange"
                  :total="allCount"
                  :page-size="pageSize"
                  show-elevator
                  prev-text="上一页"
                  next-text="下一页"
                  :current="currentPage"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 提交参数 -->
      <div class="contrast_footer" v-show="caseShow">
        <Button id="allBtn" class="contrast_footer_btn" @click="handelLibraryComparison">提交全库比对</Button>
        <Button class="contrast_footer_btn" @click="handelBack">返回</Button>
      </div>
      <!-- 全库对比返回上一步 -->
      <div class="contrast_footer_2" v-show="DisplayDOM">
        <Button class="contrast_footer_btn_show" @click="Return">返回上一步</Button>
      </div>
      <!-- 提交参数页面  返回上一页提示模态框 -->
      <Modal v-model="modal10" :header-hide="true" class-name="contrast-center-modal">
        <div class="contrast_modal">
          <h5>确认返回吗？</h5>
          <Button class="contrast_modal_btn modal_btn_first" @click="handelBackTrue">确认</Button>
          <Button class="contrast_modal_btn" @click="handelBackFalse">返回</Button>
        </div>
      </Modal>
      <!-- 按钮假动作 -->
      <Modal v-model="Btnmodel" :header-hide="true" class-name="btnModel">
        <div class="contrast_modal">
          <h5>正在比对，请在首页查看结果！</h5>
        </div>
      </Modal>
    </div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
  </div>
</template>
<script>
import QuickComparisonServer from "../../servers/QuickComparisonServer";
import ModalDetail from "../../components/ModalDetail";
// import { constants } from "os";
export default {
  //   name: "QuickComparisonBox",
  components: { ModalDetail },
  data() {
    return {
      modalDetail: {
        modalInfo: [],
        modalTitle: "",
        modalData: [],
        modalType: 0,
        modalHeader1: "",
        modalHeader2: "",
        modalSplitMixBtn: "",
        modalBigTitle: ""
      },
      span_value: "",
      caseShow: true,
      DisplayDOM: false,
      backModel: false,
      modal10: false,
      Btnmodel:false,
      LCDShow: true,
      contrast_head: [
        {
          title: "基因座",
          key: "geneName",
          align: "center"
        },
        {
          title: "",
          key: "allele",
          align: "center"
        }
        // {
        //     renderHeader:(h, params) => {
        //         return h('div', [
        //             h('strong', this.percent)
        //         ]);
        //     },
        //     key:'allele',
        //      align:"center"
        // }
      ],
      table_list: [],
      checkAllValue: false,
      checkValue: [],
      list_select: "全容差",
      Conditions: [],
      InputVlaue: "",
      fromData: {
        allele: []
      },
      FileName:"",
      Contributor:0,
      percent: "", // 百分比值 95%
      caseId: "", // 案件id
      changeValue: "", // 比中条件
      // 表格页面配置
      allCount: 0, //数据总条数  在:total中绑定
      currentPage: 1, //当前页码
      pageSize: 15, // 每页数据条数
      // 表格数据
      data_list: [],
      // 表格标题
      table_colums: [
        {
          title: "序号",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "目标库别",
          key: "sampleFlag"
        },
        {
          title: "样本编号",
          key: "sampleNo"
        },
        {
          title: "样本名称",
          key: "sampleName"
        },
        {
          title: "案件编号",
          key: "caseNo"
        },
        {
          title: "案件名称",
          key: "caseName"
        },
        {
          title: "匹配位点数",
          key: "sameCount"
        },
        {
          title: "查看比中详情",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage"
                },
                on: {
                  click: event => {
                    this.$refs.modal.modalShow = true;
                    this.modalDetail.modalTitle = params.row.sampleNo;
                    this.modalDetail.modalHeader1 = "拆分样本等位基因";
                    this.modalDetail.modalHeader2 = "比中样本等位基因";
                    // this.modalDetail.modalSplitMixBtn = "拆分1111";
                    this.modalDetail.modalBigTitle = "查看比中详情";
                    let Info = {
                      genneId: params.row.entity.id,
                      geneInfo: JSON.stringify(this.table_list)
                    };
                    QuickComparisonServer.ComparisonDetails(Info).then(res => {
                      // console.log(res);
                      //  this.modalDetail.modalData.push(res.data.splitdSampleGeneImagePath);
                      //  this.modalDetail.modalData.push(res.data.splitdSampleGeneImagePath);
                      // this.modalDetail.modalInfo = res.data.viewRatioGeneDetails.resultList;
                      // this.modalDetail.modalData = res.data.viewRatioGeneDetails;
                      let modelDataObj = JSON.stringify({
                        "splitdSampleGeneImagePath":res.data.splitdSampleGeneImagePath,
                        "ratioSampleGeneImagePath":res.data.ratioSampleGeneImagePath
                      });
                      let data = JSON.parse(modelDataObj);
                      this.modalDetail.modalData = data;
                      this.modalDetail.modalInfo = res.data.resultList;
                    });
                  }
                }
              })
            ]);
          }
        }
      ]
    };
  },
  created() {
    this.table_list = JSON.parse(sessionStorage.getItem("contrast"));
    this.percent = sessionStorage.getItem("percent");
    this.caseId = localStorage.getItem("caseId");
    this.contrast_head[1].title = "权重≥" + this.percent;
    this.FileName = this.$route.params.fileName;
    this.Contributor = this.$route.params.Contributor;
    // console.log(this.FileName)
    // console.log(this.Contributor);
  },
  methods: {
    Return() {
      this.checkAllValue = false;
      this.checkValue = []; //点击返回上一步时 清空多选 清空容差 清空输入框的值
      this.Conditions = [];
      this.InputVlaue = "";
      this.DisplayDOM = false;
      this.caseShow = true;
      this.LCDShow = true;
    },
    // 比中条件监听事件
    handelCheckboxConditions(val) {
      this.changeValue = val;
      // console.log(val);
      // if(val == 7){
      //    this.
      //    this.Conditions.push(this.InputVlaue)
      // }else if(val == 1){
      //   this.Conditions.push(0);
      // }
    },
    //分页监听事件
    PageChange(page) {
      this.currentPage = page;
      this.handelLibraryComparison();
    },
    // 全库比对
    handelLibraryComparison() {
      // this.Btnmodel = true;
      this.Conditions = [];
      if (this.changeValue == 0) {
        this.Conditions.push(this.changeValue);
      } else {
        // this.InputVlaue
        this.Conditions.push(this.InputVlaue);
      }
      // 全库对比条件
      let compareContribution = {
        geneInfo: JSON.stringify(this.table_list),
        targetType: JSON.stringify(this.checkValue),
        condition: JSON.stringify(this.Conditions),
        fileName:this.FileName,
        contributorName:this.Contributor,
        weight:this.percent
        // caseId: this.caseId 
        // curPage: this.currentPage,
      };
      // console.log(compareContribution);
      QuickComparisonServer.ComparisonDetails(compareContribution).then(res => {
        // console.log(res);
        if (res.code == 1) {
          this.Btnmodel = true;
        } else {
          this.$Message.error('参数错误，请重试！')
        }
      });
    },
    // 多选框监听事件
    handelCheckTarget(val) {},
    //全选
    handelCheckboxAll() {
      if (this.checkAllValue == true) {
        this.checkValue = ["0", "01", "03", "04", "02", "99", "3"];
      } else {
        this.checkValue = [];
      }
    },
    contrast_table_class(row, index) {
      if (index % 2 == 0) {
        return "contrast_left_table_blue";
      } else {
        return "contrast_left_table_whit";
      }
    },
    table_class(row, index) {
      if (index === 0) {
        return "table_blue";
      } else {
        // return "contrast_left_table_whit";
      }
    },
    //返回上一页
    handelBack() {
      this.modal10 = true;
    },
    handelBackTrue() {
      this.$router.push("/HybridSplit");
    },
    handelBackFalse() {
      this.modal10 = false;
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/contrast";
.btnModel{
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 200px;
  overflow: hidden;
  .ivu-modal{
    width:400px !important;
  }
  .ivu-modal-footer{
    display: none !important;
  }
  .contrast_modal{
    h5{
      font-size: 18px;
      // color:rgba(255,90,85,1);
      text-align: center;
      margin-top: 5px;
    }
  }
}
</style>
