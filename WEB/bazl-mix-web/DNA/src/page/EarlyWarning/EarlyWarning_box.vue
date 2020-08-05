<template>
  <div class="Ew-box">
    <div class="home_title">
      <router-link tag="b" to="/">首页 》</router-link>
      <router-link tag="b" to="/MatchRecord">比对记录 》</router-link>
      <span>比中预警</span>
    </div>
    <!-- <div class="Ew-header">
      <div class="search">
        <span class="margin-left"></span>
        <span>混合样本比中预警</span>
      </div>
    </div>-->
    <div class="Ew-body">
      <div class="Ew-header">
        <div class="search Ew-div_1">
          <span class="margin-left"></span>
          <span>基本信息</span>
        </div>
      </div>
      <div class="titleCon padding-15">
        <div class="Ew-div_2">
          <p class="p_style">
            <span class="befor">混合样本名称：</span>
            <span>{{ titleInfo.queueSampleName }}</span>
          </p>
          <p class="p_style">
            <span class="befor">混合样本编号： </span>
            <span>{{ titleInfo.queueSampleNo }}</span>
          </p>
          <p class="p_style">
            <span class="befor">基因位点个数：{{ titleInfo.geneCount }} </span>
          </p>
          <p class="p_style">
            <span class="befor"
              >（{{ titleInfo.mixCount }}）个人混合，已知贡献者（{{
                titleInfo.contributorCount
              }}）</span
            >
          </p>
          <p class="p_style">
            <span class="befor">匹配下限：{{ titleInfo.mixSameCount }} </span>
          </p>
          <p class="p_style">
            <span class="befor">容差：{{ titleInfo.condition }} </span>
          </p>
          <!-- :content="titleInfo.personName" -->
          <Tooltip placement="top">
            <p slot="content" style="whiteSpace: normal;wordBreak: break-all;">
              {{ titleInfo.personName }}
            </p>
            <p class="overP p_style">
              <span class="befor">人员类型：{{ titleInfo.personName }} </span>
            </p>
          </Tooltip>
          <!-- :content="titleInfo.district" -->
          <Tooltip  placement="top" >
            <p slot="content" style="whiteSpace: normal;wordBreak: break-all;">
              {{ titleInfo.district }}
            </p>
            <p class="overP p_style"  style="mix-width:50px">
              <span class="befor">地区范围：{{ titleInfo.district }} </span>
            </p>
          </Tooltip>
        </div>
        <div class="container" style="padding:5px">
          <div class="gene con-span">
            <span class="title-span">基因座</span>
            <span v-for="item in mixGene">{{ item.name }}</span>
          </div>
          <div class="geneNum con-span">
            <span class="title-span">源分型</span>
            <span v-for="item in mixGene">{{ item.value }}</span>
          </div>
          <div class="contr con-span" v-for="item in contributorInfoList">
            <span class="title-span">贡献者</span>
            <span v-for="k in item.geneInfo">{{ k.value }}</span>
          </div>
        </div>
      </div>
      <div class="Ew-header" style="margin:0px">
        <div class="search Ew-div_1">
          <span class="margin-left"></span>
          <span>比中详情列表</span>
        </div>
        <div class="Ew-div_3">
          <i-button class="btn-ew red-rw" @click="handtable(0)"
            >有效比中</i-button
          >
          <i-button class="btn-ew yellow-rw" @click="handtable(1)"
            >无效比中</i-button
          >
          <!-- <i-button class="btn-ew" type="primary" @click="downloadExl"
            >导出比对记录</i-button
          > -->
        </div>
      </div>
      <div class="table-container">
        <div class="table-container">
          <div v-show="partOne">
            <div class="yangben">
              <span></span>
            </div>
            <div v-if="tableOneData != 0">
              <Table
                @on-selection-change="selectChange"
                stripe
                border
                :columns="tableOneCol"
                :data="tableOneData"
                size="small"
                class="details_table light-blue-table"
              ></Table>
              <div class="page">
                <span
                  >当前第{{ tableOneCurrentPage }}页/共{{
                    tableOnePageCount
                  }}页/共{{ tableOneCount }}条信息</span
                >
                <Page
                  :total="tableOneCount"
                  show-elevator
                  prev-text="上一页"
                  next-text="下一页"
                  class-name="bazl_page"
                  :current="tableOneCurrentPage"
                  @on-change="tableOneChangePage"
                  :page-size="15"
                />
              </div>
            </div>
            <div class="error-con" v-else>
              <div class="margin-bom">
                <Table
                  border
                  class="display-none"
                  :columns="tableOneCol"
                ></Table>
                <div class="borderStyle">
                  <div>
                    <p>
                      <img src="../../assets/img/break_err.png" alt />
                    </p>
                    <p>您好，目前暂无数据显示</p>
                    <p>您可尝试其他操作</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-show="partTwo">
            <div v-if="tableTwoData.length != 0">
              <Table
                stripe
                border
                :columns="tableTwoCol"
                :data="tableTwoData"
                class="details_table"
              ></Table>
              <div class="page">
                <span
                  >当前第{{ tableTwoCurrentPage }}页/共{{
                    tableTwoPageCount
                  }}页/共{{ tableTwoCount }}条信息</span
                >
                <Page
                  :total="tableTwoCount"
                  show-elevator
                  prev-text="上一页"
                  next-text="下一页"
                  class-name="bazl_page"
                  :current="tableTwoCurrentPage"
                  @on-change="tableTwoChangePage(val)"
                  :page-size="15"
                />
              </div>
            </div>
            <div class="error-con" v-else>
              <div>
                <Table
                  border
                  class="display-none"
                  :columns="tableTwoCol"
                ></Table>
                <div class="borderStyle">
                  <div>
                    <p>
                      <img src="../../assets/img/break_err.png" alt />
                    </p>
                    <p>您好，目前暂无数据显示</p>
                    <p>您可尝试其他操作</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-show="partThree">
            <div v-if="tableThreeData.length != 0">
              <Table
                stripe
                border
                :columns="tableThreeCol"
                :data="tableThreeData"
                class="details_table"
              ></Table>
              <div class="page">
                <span
                  >当前第{{ tableThreeCurrentPage }}页/共{{
                    tableThreePageCount
                  }}页/共{{ tableThreeCount }}条信息</span
                >
                <Page
                  :total="allRecordCountThree"
                  show-elevator
                  prev-text="上一页"
                  next-text="下一页"
                  class-name="bazl_page"
                  :current="tableThreeCurrentPage"
                  @on-change="tablechangePageThree(val)"
                  :page-size="15"
                />
              </div>
            </div>
            <div class="error-con" v-else>
              <div>
                <Table
                  border
                  class="display-none"
                  :columns="tableThreeCol"
                ></Table>
                <div class="borderStyle">
                  <div>
                    <p>
                      <img src="../../assets/img/break_err.png" alt />
                    </p>
                    <p>您好，目前暂无数据显示</p>
                    <p>您可尝试其他操作</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="Ew-footer">
      <p @click="GOTOBACK">返回上一步</p>
    </div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
  </div>
</template>
<script>
import EarlyWarningServer from "@/servers/EarlyWarningServer";
import ModalDetail from "../../components/ModalDetail";
import expandRow from "./table-expand.vue";
import { tbData } from "./tableData.js";
export default {
  name: "EarlyWarning",
  components: { ModalDetail, expandRow },
  data() {
    return {
      mixGene: [],
      contributorInfoList: [],
      titleInfo: {},
      geneList: [],
      tbData,
      table_List: [
        {
          a1: ["123", "123", "123"],
          a2: "xxx血样",
          a3: "违法犯罪人员",
          a4: "xxx",
          a5: "1252330141041=23",
          a6: ["太原", "山西省厅", "xxxx"],
          a7: ["sdsd", "sdfsdfsdfsdf", "sdfsdfsdf"],
          a8: "15",
          name: "比中（张云刚分型）",
          info1: [
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            }
          ],
          info2: [
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            }
          ],
          info3: [
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            },
            {
              name: "sdsDD1232",
              val: "12/11"
            }
          ]
        }
      ],
      // 表格1 2 3状态显示
      partOne: true,
      partTwo: false,
      partThree: false,
      tableOneCount: 0, // 表格1 总条数
      tableTwoCount: 0, // 表格2 总条数
      tableThreeCount: 0, // 表格3 总条数
      tableOneCurrentPage: 1, // 表格1 页码
      tableTwoCurrentPage: 1, // 表格2 页码
      tableThreeCurrentPage: 1, // 表格3 页码
      tableOnePageCount: 0, // 表格1 总页数
      tableTwoPageCount: 0, // 表格2 总页数
      tableThreePageCount: 0, // 表格3 总页数
      // 表格 1 标题
      tableOneCol: [
        {
          title: "选择",
          type: "selection",
          width: "50"
        },
        {
          title: "序号",
          width: "75",
          render: (h, params) => {
            if (params.row.effectFlag == "1") {
              return h("span", { class: "CaseNo-rel" }, [
                h(
                  "span",
                  {
                    class: ""
                  },
                  params.index + 1
                ),
                h(
                  "span",
                  {
                    class: "CaseNo-abs ivu-red",
                    style: {
                      marginLeft: "5px"
                    }
                  },
                  "有"
                )
              ]);
            } else if (params.row.effectFlag == "0") {
              return h("span", { class: "CaseNo-rel" }, [
                h(
                  "span",
                  {
                    class: ""
                  },
                  params.index + 1
                ),
                h(
                  "span",
                  {
                    class: "CaseNo-abs ivu-yellow"
                  },
                  "无"
                )
              ]);
            } else {
              return h("span", {}, params.index + 1);
              //   else {
              //   return  h(
              //     "span",
              //     {
              //       class:"",
              //     },
              //   params.row.proportionCaseNo)
              // }
            }
          }
        },
        {
          title: "案件编号",
          key: "proportionCaseNo"
        },
        {
          title: "案件名称",
          key: "proportionCaseName"
        },
        {
          title: "样本实验室编号",
          key: "proportionSampleNo"
        },
        {
          title: "样本名称",
          key: "proportionSampleName"
        },
        {
          title: "比中地区",
          key: "proportionDistrict",
          width: "120",
          render: (h, params) => {
            return h("p", [
              h(
                "Tooltip",
                {
                  props: { placement: "right-end", transfer: true }
                },
                [
                  h(
                    "p",
                    {
                      style: {
                        display: "inline-block",
                        width: params.column._width * 0.6 + "px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap"
                      }
                    },
                    params.row.proportionDistrict
                  ),
                  h(
                    "p",
                    {
                      slot: "content",
                      style: { whiteSpace: "normal", wordBreak: "break-all" }
                    },
                    params.row.proportionDistrict
                  )
                ]
              )
            ]);
          }
        },
        {
          title: "入库类型",
          key: "proportionPersonnel",
          width: "120"
        },
        {
          title: "姓名",
          key: "proportionPersonName",
          // width: "80"
        },
        {
          title: "身份证号",
          key: "idCardNo"
        },
        {
          title: "匹配位点个数",
          key: "ratio",
          width: "130"
        },
        {
          type: "expand",
          title: "查看详情",
          width: 110,
          render: (h, params) => {
            return h(expandRow, {
              props: {
                row: params.row
              }
            });
          }
        }
        // {
        //   title: "比中详情",
        //   align: "center",
        //   width: "100",
        //   render: (h, params) => {
        //     return h("div", [
        //       h("Icon", {
        //         props: {
        //           type: "md-eye orage"
        //         },
        //         domProps: {
        //           title: "查看比中详情"
        //         },
        //         on: {
        //           click: event => {
        //             this.tableOneData[params.index]['_expanded'] = true
        //             let info = {
        //               id: params.row.id,
        //               compareId: params.row.compareId
        //             };
        //             this.$refs.modal.modalShow = true;
        //             this.modalDetail.modalType = 0;
        //             this.modalDetail.modalHeader1 = "混合样本等位基因";
        //             this.modalDetail.modalHeader2 = "比中样本等位基因";
        //             this.modalDetail.modalSplitMixBtn = "混合";
        //             EarlyWarningServer.SeeQueryMatchResult(info).then(res => {
        //               this.modalDetail.modalInfo =
        //                 res.result.stringObjectMap.resultList;
        //               this.modalDetail.modalTitle =
        //                 params.row.proportionSampleNo;
        //               this.modalDetail.modalData = res.result;
        //             });
        //           }
        //         }
        //       })
        //     ]);
        //   }
        // }
      ],
      // 表格 2 标题
      tableTwoCol: [
        {
          title: "序号",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "案件编号",
          key: "proportionCaseNo"
          // width:"220"
        },
        {
          title: "案件名称",
          key: "proportionCaseName"
        },
        {
          title: "检材编号",
          key: "queueSampleNo"
        },
        {
          title: "比中样本编号",
          key: "proportionSampleNo"
        },
        {
          title: "比中人员姓名",
          key: "proportionSampleName"
        },
        {
          title: "比中时间",
          key: "comparisonTime"
        },
        {
          title: "比中位点",
          key: "ratio"
        },
        {
          title: "比中详情",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage"
                },
                domProps: {
                  title: "查看比中详情"
                },
                on: {
                  click: event => {
                    this.$refs.modal.modalShow = true;
                    this.modalDetail.modalType = 0;
                    this.modalDetail.modalHeader1 = "混合样本等位基因";
                    this.modalDetail.modalHeader2 = "比中样本等位基因";
                    this.modalDetail.modalSplitMixBtn = "混合";
                    EarlyWarningServer.SeeQueryMatchResult(idIn_fo).then(
                      res => {
                        this.modalDetail.modalInfo = res.data.matchResults;
                        this.modalDetail.modalTitle =
                          params.row.proportionSampleNo;
                        this.modalDetail.modalData = res.data;
                      }
                    );
                  }
                }
              })
            ]);
          }
        }
      ],
      // 表格 3 标题
      tableThreeCol: [
        {
          title: "序号",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "案件编号",
          key: "proportionCaseNo"
          // width:"220"
        },
        {
          title: "案件名称",
          key: "proportionCaseName"
        },
        {
          title: "检材编号",
          key: "queueSampleNo"
        },
        {
          title: "比中样本编号",
          key: "proportionSampleNo"
        },
        {
          title: "比中人员姓名",
          key: "proportionSampleName"
        },
        {
          title: "比中时间",
          key: "comparisonTime"
        },
        {
          title: "比中位点",
          key: "ratio"
        },
        {
          title: "比中详情",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage"
                },
                domProps: {
                  title: "查看比中详情"
                },
                on: {
                  click: event => {
                    let idIn_fo = {
                      id: params.row.compareId
                    };
                    this.$refs.modal.modalShow = true;
                    this.modalDetail.modalType = 0;
                    this.modalDetail.modalHeader1 = "混合样本等位基因";
                    this.modalDetail.modalHeader2 = "比中样本等位基因";
                    this.modalDetail.modalSplitMixBtn = "混合";
                    EarlyWarningServer.SeeQueryMatchResult(idIn_fo).then(
                      res => {
                        this.modalDetail.modalInfo = res.data.matchResults;
                        this.modalDetail.modalTitle =
                          params.row.proportionSampleNo;
                        this.modalDetail.modalData = res.data;
                      }
                    );
                  }
                }
              })
            ]);
          }
        }
      ],
      // 表格 1 参数
      tableOneData: [
        // {
        //   aj: "c223130--21091yy",
        //   mx: "XXXX",
        //   bh: "c3--9000",
        //   cj: "cBBBBB",
        //   sj: "1号",
        //   bht: "035"
        // }
      ],
      // 表格 2 参数
      tableTwoData: [],
      // 表格 3 参数
      tableThreeData: [],
      num_one: 0,
      num_two: 0,
      num_three: 0,
      // 查看详情弹出窗
      modalDetail: {
        modalType: 1,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: ""
      },
      // 案件状态
      activeBtn: 0,
      // 按钮渲染参数
      BtnList: [
        { btnName: "案件检材嫌疑人比中" },
        { btnName: "案件受害人比中" },
        { btnName: "质控人员检材污染比中" }
      ],
      table_index: 0,
      _id: "",
      test_id_Lsit: [],
      queueType: ""
    };
  },
  mounted() {
    this._id = this.$route.params.id;
    this.queueType = this.$route.params.queueType;
    this.tableData();
  },

  beforeDestroy() {},

  created() {},
  computed: {},
  methods: {
    show(index, id, compareId) {
      this.tableOneData[index].isShow = false;
      EarlyWarningServer.SeeQueryMatchResult({
        id: id,
        compareId: compareId
      }).then(res => {
        this.geneList = res.result.stringObjectMap.resultList;
      });
    },
    showClose(index) {
      this.tableOneData[index].isShow = true;
    },
    downloadExl() {
      let info = {
        compareId: this._id
      };
      location.href =
        "api_iLabSTRmix/mix/export/exportCompare?compareId=" + this._id;
    },
    GOTOBACK() {
      this.$router.go(-1);
    },
    // 有效无效按钮事件
    handtable(type) {
      if (this.test_id_Lsit.length == 0) {
        this.$Message.error("请先选择需要操作的数据！");
        return;
      } else {
        let idList = JSON.stringify(this.test_id_Lsit);
        if (type == 0) {
          EarlyWarningServer.idList(idList).then(res => {
            if (res.code == 1) {
              this.$Message.success("修改成功！");
              this.tableData();
            } else {
              this.$Message.error("操作失败！");
            }
          });
        } else {
          EarlyWarningServer.idList_2(idList).then(res => {
            if (res.code == 1) {
              this.$Message.success("修改成功！");
              this.tableData();
            } else {
              this.$Message.error("操作失败！");
            }
          });
        }
      }
    },
    //表格复选框事件
    selectChange(select) {
      if (select) {
        this.test_id_Lsit = select.map(aa => aa.id);
      }
    },
    // 表格数据量
    NumberBadge(index) {
      if (index == 0) {
        return this.num_one;
      } else if (index == 1) {
        return this.num_two;
      } else if (index == 2) {
        return this.num_three;
      }
    },
    tableData() {
      let type_info = {
        page: this.tableOneCurrentPage,
        compareId: this._id
      };
      console.log(type_info)
      let item = JSON.stringify(type_info)
      EarlyWarningServer.QueryMatchResultList(item).then(res => {
        if (res.code == 1) {
          res.data.matchResults.map(item => {
            item.isShow = true;
            item.geneInfo = JSON.parse(item.geneInfo);
          });
          this.tableOneData = res.data.matchResults;
          this.mixGene = JSON.parse(res.data.geneInfo);
          this.contributorInfoList = res.data.contributorInfoList;
          this.tableOnePageCount = res.data.pageInfo.pageCount;
          this.tableOneCount = res.data.pageInfo.allRecordCount;
          this.titleInfo = res.data;
          for (let item of this.contributorInfoList) {
            item.geneInfo = JSON.parse(item.geneInfo);
          }
          this.titleInfo.geneCount = JSON.parse(res.data.geneInfo).length;
          this.contributorInfoList = [...this.contributorInfoList];
        } else {
          this.$Message.error("列表获取失败！");
        }
      });
    },
    // 表格1 分页监听事件
    tableOneChangePage(val) {
      this.tableOneCurrentPage = val;
      this.tableData();
    },
    // 表格2 分页监听事件
    tableTwoChangePage(val) {
      let type = "受害人";
      this.table_index = 1;
      let pageType = this.tableTwoCurrentPage;
      this.tableData(type, pageType);
    },
    // 表格3 分页监听事件
    tableThreeChangePage(val) {
      let type = "质控人";
      this.table_index = 0;
      let pageType = this.tableThreeCurrentPage;
      this.tableData(type, pageType);
    },
    // 导航按钮切换事件
    BtnSwitch(i) {
      this.activeBtn = i;
      if (i == 0) {
        this.partOne = true;
        this.partTwo = false;
        this.partThree = false;
        let type = "嫌疑人";
        this.table_index = i;
        this.tableData(type);
      } else if (i == 1) {
        this.partTwo = true;
        this.partOne = false;
        this.partThree = false;
        let type = "受害人";
        this.table_index = i;
        this.tableData(type);
      } else if (i == 2) {
        this.partThree = true;
        this.partOne = false;
        this.partTwo = false;
        let type = "质控人";
        this.table_index = i;
        this.tableData(type);
      }
    },
    handelSearch() {}
  }
};
</script>
<style lang="less">
@import "../../assets/styles/title";
@import "../../assets/styles/EarlyWarning";
.details_table {
  .ivu-table-expanded-cell {
    padding: 0 !important;
  }
}
</style>
