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
    </div> -->
    <div class="Ew-body">
      <!-- <div class="BtnContainer">
        <ul>
          <li
            v-for="(btnitem,btnindex) in BtnList"
            @click="BtnSwitch(btnindex)"
            :class="{'activeBtnStyle': btnindex == activeBtn}"
          >
            <span>{{btnitem.btnName}}</span>
            <span>{{NumberBadge(btnindex)}}</span>
          </li>
        </ul>
      </div> -->
      <div class="Ew-header">
        <div class="search">
          <span class="margin-left"></span>
          <span>比对记录列表</span>
        </div>
      </div>
      <div class="table-container">
        <div v-show="partOne">
          <div v-if="tableOneData != 0">
            <Table stripe border :columns="tableOneCol" :data="tableOneData" class="details_table"></Table>
            <div class="page">
              <span>当前第{{tableOneCurrentPage}}页/共{{tableOnePageCount}}页/共{{tableOneCount}}条信息</span>
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
              <Table border class="display-none" :columns="tableOneCol"></Table>
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
            <Table stripe border :columns="tableTwoCol" :data="tableTwoData" class="details_table"></Table>
            <div class="page">
              <span>当前第{{tableTwoCurrentPage}}页/共{{tableTwoPageCount}}页/共{{tableTwoCount}}条信息</span>
              <!-- 此处更改page-size为动态值 10/17-->
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
              <Table border class="display-none" :columns="tableTwoCol"></Table>
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
              <span>当前第{{tableThreeCurrentPage}}页/共{{tableThreePageCount}}页/共{{tableThreeCount}}条信息</span>
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
              <Table border class="display-none" :columns="tableThreeCol"></Table>
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
    <div class="Ew-footer">
      <p @click="GOTOBACK">返回上一步</p>
    </div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
  </div>
</template>

<script>
import EarlyWarningServer from "@/servers/EarlyWarningServer";
import ModalDetail from "../../components/ModalDetail";
export default {
  name: "EarlyWarning",
  components: { ModalDetail },
  data() {
    return {
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
          title: "序号",
          width:"80",
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
          title: "比中人员类型",
          key: "proportionPersonnel"
        }, 
       {
          title: "比中时间",
          key: "datetime"
        },
         {
          title: "比中位点",
          key: "ratio",
          width:"100"
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
                    // console.log(params)
                    let idIn_fo = {
                      id:params.row.compareId,
                    }
                      this.$refs.modal.modalShow = true;
                      this.modalDetail.modalType = 0;
                      this.modalDetail.modalHeader1 = "混合样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "混合";
                     EarlyWarningServer.SeeQueryMatchResult(idIn_fo).then(res => {
                      // console.log(res);
                      this.modalDetail.modalInfo = res.data.matchResults[0].geneInfos.resultList;
                      this.modalDetail.modalTitle = params.row.proportionSampleNo;
                      this.modalDetail.modalData = res.data;
                    }); 
                  }
                }
              })
            ]);
          }
        }
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
                    // console.log(params)
                    let idIn_fo = {
                      id:params.row.compareId,
                    }
                      this.$refs.modal.modalShow = true;
                      this.modalDetail.modalType = 0;
                      this.modalDetail.modalHeader1 = "混合样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "混合";
                     EarlyWarningServer.SeeQueryMatchResult(idIn_fo).then(res => {
                      // console.log(res);
                      this.modalDetail.modalInfo = res.data.matchResults;
                      this.modalDetail.modalTitle = params.row.proportionSampleNo;
                      this.modalDetail.modalData = res.data;
                    }); 
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
                    // console.log(params)
                    let idIn_fo = {
                      id:params.row.compareId,
                    }
                      this.$refs.modal.modalShow = true;
                      this.modalDetail.modalType = 0;
                      this.modalDetail.modalHeader1 = "混合样本等位基因";
                      this.modalDetail.modalHeader2 = "比中样本等位基因";
                      this.modalDetail.modalSplitMixBtn = "混合";
                     EarlyWarningServer.SeeQueryMatchResult(idIn_fo).then(res => {
                      // console.log(res);
                      this.modalDetail.modalInfo = res.data.matchResults;
                      this.modalDetail.modalTitle = params.row.proportionSampleNo;
                      this.modalDetail.modalData = res.data;
                    }); 
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
      num_one:0,
      num_two:0,
      num_three:0,
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
        {btnName: "案件检材嫌疑人比中"},
        { btnName: "案件受害人比中" },
        { btnName: "质控人员检材污染比中" }
      ],
      table_index:0,
      _id:"",
    };
  },
  mounted() {
    this._id = this.$route.params.id;
    // this.WarningChange_params();
    // let type = "嫌疑人"
    // let pageType = this.tableOneCurrentPage
    this.tableData()
  },

  beforeDestroy() {},

  created() {},
  computed:{
  },
  methods: {
    GOTOBACK() {
      this.$router.go(-1);
    },
    // 表格数据量
    NumberBadge(index) {
      // console.log(index);
      if(index == 0){
        return this.num_one; 
        console.log(this.num_one);
      }else if(index == 1){
        return this.num_two; 
        console.log(this.num_two);
      }else if(index == 2){
        return this.num_three; 
        console.log(this.num_three);
      }
    },
    tableData() {
      let type_info = {
        // id:type,
        page:this.tableOneCurrentPage,
        compareId:this._id
      }
      console.log(type_info);
      EarlyWarningServer.QueryMatchResultList(type_info).then(res => {
        console.log(res)
        if(res.code == 1 ){
          this.tableOneData = res.data.matchResults;
          this.tableOnePageCount = res.data.pageInfo.pageCount;
          this.tableOneCount = res.data.pageInfo.allRecordCount;
          // this.num_one = res.data.count;  
          // this.num_two = res.data.count1;
          // this.num_three = res.data.count2
          // if(this.table_index == 0){
          //   this.tableOneData = res.data.matchResults;
          //   this.tableOnePageCount = res.data.pageInfo.pageCount;
          //   this.tableOneCount = res.data.pageInfo.allRecordCount;
          // }else if(this.table_index == 1){
          //   this.tableTwoData = res.data.matchResults;
          //   this.tableTwoPageCount = res.data.pageInfo.pageCount;
          //   this.tableTwoCount = res.data.pageInfo.allRecordCount;
          // }else if(this.table_index == 2){
          //   this.tableThreeData= res.data.matchResults;
          //   this.tableThreePageCount = res.data.pageInfo.pageCount;
          //   this.tableThreeCount = res.data.pageInfo.allRecordCount;    
          // }
        }else{
          this.$Message.error("列表获取失败！");
        }
      });
    },
    // 表格1 分页监听事件
    tableOneChangePage(val) {
      // let type = "嫌疑人";
      console.log(val);
      this.tableOneCurrentPage = val;
      // this.table_index = 0;
      this.tableData()
    },
    // 表格2 分页监听事件
    tableTwoChangePage(val) {
      let type = "受害人";
      this.table_index = 1;
      let pageType = this.tableTwoCurrentPage
      this.tableData(type,pageType)
    },
    // 表格3 分页监听事件
    tableThreeChangePage(val) {
      let type = "质控人";
      this.table_index = 0;
      let pageType = this.tableThreeCurrentPage
      this.tableData(type,pageType)
    },
    // 导航按钮切换事件
    BtnSwitch(i) {
      // console.log(i);
      this.activeBtn = i;
      if (i == 0) {
        this.partOne = true;
        this.partTwo = false;
        this.partThree = false;
        let type = "嫌疑人";
        this.table_index = i;
        this.tableData(type)
      } else if (i == 1) {
        this.partTwo = true;
        this.partOne = false;
        this.partThree = false;
        let type = "受害人";
        this.table_index = i;
        this.tableData(type)
      } else if (i == 2) {
        this.partThree = true;
        this.partOne = false;
        this.partTwo = false;
        let type = "质控人";
        this.table_index = i;
        this.tableData(type)
      }
    },
    handelSearch() {}
  }
};
</script>
<style lang="less">
@import "../../assets/styles/title";
@import "../../assets/styles/EarlyWarning";
</style>
