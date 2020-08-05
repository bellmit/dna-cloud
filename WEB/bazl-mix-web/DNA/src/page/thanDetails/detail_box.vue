<template>
  <div class="dataHome">
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link> 》<router-link
        tag="b"
        to="/data"
        >数据库管理</router-link
      >
      》 <span>比中详情</span>
    </div>
    <div class="details_header">
      <!-- <Row class="details_title">
              <Col span="8" class="detail_col" v-for="(item,index) in btnList" :key="index">
                <button class="tab_btn" :class="{'active': index == active}"  @click="handleBtn(index)">
                  {{item.btnName}}
                  <Badge :count="detailsBadge(index)" class-name="details_badge"></Badge>
                </button>
              </Col>
            </Row> -->
      <ul>
        <li
          v-for="(btnitem, btnindex) in BtnList"
          @click="BtnSwitch(btnindex)"
          :class="{ activeBtnStyle: btnindex == activeBtn }"
        >
          <span>{{ btnitem.btnName }}</span>
          <span>{{ NumberBadge(btnindex) }}</span>
        </li>
      </ul>
    </div>
    <div class="details_body">
      <div v-show="partOne">
        <div v-if="thanDetailsData.length != 0">
          <Table
            stripe
            border
            :columns="thanDetailsCol"
            :data="thanDetailsData"
            class="details_table light-blue-table"
          ></Table>
          <div class="page">
            <span>
              当前第{{ currentPage }}页/共{{ pageCount }}页/共{{
                allRecordCount
              }}条信息
            </span>
            <Page
              :total="allRecordCount"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="currentPage"
              @on-change="changePage"
              :page-size="15"
            />
          </div>
        </div>
        <div class="error-con" v-else>
          <div>
            <Table
              border
              class="display-none"
              :columns="thanDetailsCol"
            ></Table>
            <div class="borderStyle">
              <div>
                <p><img src="../../assets/img/break_err.png" alt="" /></p>
                <p>您好，目前暂无数据显示</p>
                <p>您可尝试其他操作</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-show="partTwo">
        <div v-if="thanDetailsDataTwo.length != 0">
          <Table
            stripe
            border
            :columns="thanDetailsColTwo"
            :data="thanDetailsDataTwo"
            class="details_table light-blue-table"
          ></Table>
          <div class="page">
            <span>
              当前第{{ currentPageTwo }}页/共{{ pageCountTwo }}页/共{{
                allRecordCountTwo
              }}条信息
            </span>
            <!-- 此处更改page-size为动态值 10/17-->
            <Page
              :total="allRecordCountTwo"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="currentPageTwo"
              @on-change="changePageTwo"
              :page-size="pagesizeTwo"
            />
          </div>
        </div>
        <div class="error-con" v-else>
          <div>
            <Table
              border
              class="display-none"
              :columns="thanDetailsCol"
            ></Table>
            <div class="borderStyle">
              <div>
                <p><img src="../../assets/img/break_err.png" alt="" /></p>
                <p>您好，目前暂无数据显示</p>
                <p>您可尝试其他操作</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-show="partThree">
        <div v-if="thanDetailsDataThree.length != 0">
          <Table
            stripe
            border
            :columns="thanDetailsColThree"
            :data="thanDetailsDataThree"
            class="details_table light-blue-table"
          ></Table>
          <div class="page">
            <span>
              当前第{{ currentPageThree }}页/共{{ pageCountThree }}页/共{{
                allRecordCountThree
              }}条信息
            </span>
            <Page
              :total="allRecordCountThree"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="currentPageThree"
              @on-change="changePageThree"
              :page-size="15"
            />
          </div>
        </div>
        <div class="error-con" v-else>
          <div>
            <Table
              border
              class="display-none"
              :columns="thanDetailsCol"
            ></Table>
            <div class="borderStyle">
              <div>
                <p><img src="../../assets/img/break_err.png" alt="" /></p>
                <p>您好，目前暂无数据显示</p>
                <p>您可尝试其他操作</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ModalDetail ref="modal" :modalDetail="modalDetail"></ModalDetail>
  </div>
</template>
<script>
import thanDetailServers from "../../servers/thanDetailsServers";
import ModalDetail from "../../components/ModalDetail";

export default {
  name: "than_details",
  components: {
    ModalDetail
  },
  data() {
    return {
      caseId: "",
      matchResultSplitSingleVoCount: 0,
      mixedSingleCompareResultCount: 0,
      mixedSplitedCompareResult: 0,
      currentTab: 1,
      partOne: true,
      partTwo: false,
      partThree: false,
      modalDetail: {
        modalType: 0,
        modalTitle: "",
        modalInfo: [],
        modalData: [],
        modalName: "",
        modalHeader1: "",
        modalHeader2: "",
        modalSplitMixBtn: "",
        modalBigTitle: ""
      },
      BtnList: [
        { btnName: "拆分分型比中单一样本列表" },
        { btnName: "混合分型比中单一样本列表" },
        { btnName: "拆分分型比中混合样本列表" }
      ],
      active: 0,
      thanDetailsCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "mixedSampleNo"
        },
        // {
        //   title:'样本名称',
        //   key:'splitSampleName'
        // },
        {
          title: "位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.thanInsiteNum);
          }
        },
        {
          title: "比中样本编号",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleNo);
          }
        },
        {
          title: "比中样本名称",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleName);
          }
        },
        {
          title: "比中样本位点个数",
          key: "thanInsiteNum",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSiteNum);
          }
        },
        {
          title: "匹配位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.ratio);
          }
        },
        {
          title: "查看详情",
          key: "action",
          className: "hover_text",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                class: "look_eye",
                props: {
                  type: "md-eye",
                  size: "26",
                  color: "#FFAA06"
                }
              }),
              h(
                "strong",
                {
                  class: "look_text",
                  style: {
                    color: "#FFB72C",
                    display: "none"
                  },
                  on: {
                    click: () => {
                      this.$refs.modal.modalShow = true;
                      this.modalDetail.modalTitle = params.row.mixedSampleNo;
                      this.modalDetail.modalHeader1 = "拆分样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "拆分";
                      this.modalDetail.modalBigTitle = "查看比中详情";
                      let detailInfo = {
                        id: params.row.entity.id,
                        compareId: params.row.entity.compareId
                      };
                      thanDetailServers
                        .QueryMatchResultInfo(detailInfo)
                        .then(res => {
                          this.modalDetail.modalInfo =
                            res.result.stringObjectMap.resultList;
                          this.modalDetail.modalData = res.result;
                        });
                    }
                  }
                },
                "查看详情"
              )
            ]);
          }
        }
      ],
      thanDetailsData: [],
      thanDetailsColTwo: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "mixedSampleNo"
        },
        // {
        //   title:'样本名称',
        //   key:'splitSampleName'
        // },
        {
          title: "位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.thanInsiteNum);
          }
        },
        {
          title: "比中样本编号",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleNo);
          }
        },
        {
          title: "比中样本名称",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleName);
          }
        },
        {
          title: "比中样本位点个数",
          key: "thanInsiteNum",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSiteNum);
          }
        },
        {
          title: "匹配位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.ratio);
          }
        },
        {
          title: "查看详情",
          key: "action",
          className: "hover_text",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                class: "look_eye",
                props: {
                  type: "md-eye",
                  size: "26",
                  color: "#FFAA06"
                }
              }),
              h(
                "strong",
                {
                  class: "look_text",
                  style: {
                    color: "#FFB72C",
                    display: "none"
                  },
                  on: {
                    click: () => {
                      this.$refs.modal.modalShow = true;
                      // this.modalDetail.modalTitle = params.row.splitedSampleNo;
                      this.modalDetail.modalTitle = params.row.mixedSampleNo;
                      this.modalDetail.modalHeader1 = "拆分样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "拆分";
                      this.modalDetail.modalBigTitle = "查看比中详情";
                      let detailInfo = {
                        id: params.row.entity.id,
                        compareId: params.row.entity.compareId
                      };
                      thanDetailServers
                        .QueryMatchResultInfo(detailInfo)
                        .then(res => {
                          this.modalDetail.modalInfo =
                            res.result.stringObjectMap.resultList;
                          this.modalDetail.modalData = res.result;
                        });
                    }
                  }
                },
                "查看详情"
              )
            ]);
          }
        }
      ],
      thanDetailsDataTwo: [],
      thanDetailsColThree: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "mixedSampleNo"
        },
        // {
        //   title:'样本名称',
        //   key:'splitSampleName'
        // },
        {
          title: "位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.thanInsiteNum);
          }
        },
        {
          title: "比中样本编号",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleNo);
          }
        },
        {
          title: "比中样本名称",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSampleName);
          }
        },
        {
          title: "比中样本位点个数",
          key: "thanInsiteNum",
          render: (h, params) => {
            return h("span", params.row.entity.proportionSiteNum);
          }
        },
        {
          title: "匹配位点个数",
          key: "",
          render: (h, params) => {
            return h("span", params.row.entity.ratio);
          }
        },
        {
          title: "查看详情",
          key: "action",
          className: "hover_text",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                class: "look_eye",
                props: {
                  type: "md-eye",
                  size: "26",
                  color: "#FFAA06"
                }
              }),
              h(
                "strong",
                {
                  class: "look_text",
                  style: {
                    color: "#FFB72C",
                    display: "none"
                  },
                  on: {
                    click: () => {
                      this.$refs.modal.modalShow = true;
                      // this.modalDetail.modalTitle = params.row.splitedSampleNo;
                      this.modalDetail.modalTitle = params.row.mixedSampleNo;
                      this.modalDetail.modalHeader1 = "拆分样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "拆分";
                      this.modalDetail.modalBigTitle = "查看比中详情";
                      let detailInfo = {
                        id: params.row.entity.id,
                        compareId: params.row.entity.compareId
                      };
                      thanDetailServers
                        .QueryMatchResultInfo(detailInfo)
                        .then(res => {
                          this.modalDetail.modalInfo =
                            res.result.stringObjectMap.resultList;
                          this.modalDetail.modalData = res.result;
                        });
                    }
                  }
                },
                "查看详情"
              )
            ]);
          }
        }
      ],
      thanDetailsDataThree: [],
      currentPage: 1,
      allRecordCount: 0,
      pageCount: 0,
      currentPageTwo: 1,
      allRecordCountTwo: 0,
      pageCountTwo: 0,
      pagesizeTwo: 15, // 添加 pagesize
      currentPageThree: 1,
      allRecordCountThree: 0,
      pageCountThree: 0,
      activeBtn: 0,
      num_one: 0,
      num_two: 0,
      num_three: 0
    };
  },
  created() {},
  mounted() {
    this.caseId = this.$route.query.id;
    this.handleSplitSingle();
  },
  methods: {
    handelPageChange() {},
    // detailsBadge(index){
    //   if (index == 0) {
    //     return this.matchResultSplitSingleVoCount
    //   }else if (index == 1) {
    //     return this.mixedSingleCompareResultCount
    //   }else if(index == 2){
    //     return this.mixedSplitedCompareResult
    //   }
    // },
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
    BtnSwitch(index) {
      this.activeBtn = index;
      this.currentTab = index;
      if (index == 0) {
        this.handleSplitSingle();
        this.partOne = true;
        this.partTwo = false;
        this.partThree = false;
      } else if (index == 1) {
        this.handleMixedSingle();
        this.partOne = false;
        this.partTwo = true;
        this.partThree = false;
      } else if (index == 2) {
        this.handleMixedSplited();
        this.partOne = false;
        this.partTwo = false;
        this.partThree = true;
      }
    },
    changePage(page) {
      this.currentPage = page;
      this.handleSplitSingle();
    },
    changePageTwo(page) {
      this.currentPageTwo = page;
      this.handleMixedSingle();
    },
    changePageThree(page) {
      this.currentPageThree = page;
      this.handleMixedSplited();
    },
    handleSplitSingle() {
      let splitSingleInfo = {
        mixedSampleId: this.caseId,
        offset: this.currentPage, // 此处改动 分页
        resultType: "04"
      };
      let info = JSON.stringify(splitSingleInfo);
      thanDetailServers.mixedSingleCompare(info).then(res => {
        if (res.code == 1) {
          this.num_one = res.result.splitedCount;
          this.num_two = res.result.mixedCount;
          this.num_three = res.result.splitedMixedCount;
          this.thanDetailsData = res.result.matchResultList;
          this.pageCount = res.result.pageInfo.pageCount;
          this.allRecordCount = res.result.pageInfo.allRecordCount;
        } else {
          // this.$Message.error('列表获取失败！')
        }
      });
    },
    handleMixedSingle() {
      let mixedSingleInfo = {
        mixedSampleId: this.caseId,
        offset: this.currentPageTwo,
        resultType: "01"
      };
      let info = JSON.stringify(mixedSingleInfo);
      thanDetailServers.mixedSingleCompare(info).then(res => {
        if (res.code == 1) {
          this.num_one = res.result.splitedCount;
          this.num_two = res.result.mixedCount;
          this.num_three = res.result.splitedMixedCount;
          this.thanDetailsDataTwo = res.result.matchResultList;
          this.pageCountTwo = res.result.pageInfo.pageCount;
          this.allRecordCountTwo = res.result.pageInfo.allRecordCount;
          // if(res.data.matchResults.length == 0 || res.data.matchResults == undefined || res.data.matchResults == null ){
          //     this.$Message.error("当前列表无数据！")
          // }else{

          // }
        } else {
          // this.$Message.error('列表获取失败！')
        }
      });
    },
    handleMixedSplited() {
      let mixedSplitedInfo = {
        mixedSampleId: this.caseId,
        offset: this.currentPageThree,
        resultType: "02"
      };
      let info = JSON.stringify(mixedSplitedInfo);
      thanDetailServers.mixedSingleCompare(info).then(res => {
        if (res.code == 1) {
          this.num_one = res.result.splitedCount;
          this.num_two = res.result.mixedCount;
          this.num_three = res.result.splitedMixedCount;
          this.thanDetailsDataThree = res.result.matchResultList;
          this.pageCountThree = res.result.pageInfo.pageCount;
          this.allRecordCountThree = res.result.pageInfo.allRecordCount;
        } else {
          // this.$Message.error('列表获取失败')
        }
      });
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/thandetail";
</style>
