<template>
  <div>
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <span>案件智能快速分析</span>
    </div>
    <!-- <div class="case_title">
      <Icon type="ios-square case_title_icon"/>案件智能快速分析
    </div>-->
    <div class="case_search" style="margin-top:10px">
      <p>
        <Icon type="md-alert case_search_p" />您可以在此输入案件编号，系统会将lims系统的案件导入进来
      </p>
      <i-input
        @keyup.enter.native="handelSearch()"
        type="text"
        class="case_search_input"
        v-model="search"
        autocomplete="on"
        size="large"
        placeholder="请输入案件编号"
      ></i-input>
      <Button class="case_search_btn" @click="handelSearch">确定</Button>
    </div>
    <div class="case_title" style="color: #000;">
      <Icon type="ios-square case_title_icon" />导入检材、样本列表
    </div>
    <div class="case_data" v-show="isShow">
      <div class="case_single">
        <h5>单一样本</h5>
        <Table
          class="table-case-scroll light-blue-table"
          :columns="case_head"
          :data="singleList"
          border
          stripe
        ></Table>
        <h5>混合样本</h5>
        <Table
          class="table-case-scroll light-blue-table"
          :columns="case_head_mix"
          :data="mixedList"
          border
          stripe
        ></Table>
      </div>
    </div>
    <div class="case_data_null" v-show="!isShow">
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
    <div class="case_analysis">
      <Button class="case_analysis_button" @click="handelHybrid">开始自动分析</Button>
    </div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
  </div>
</template>
<script>
import caseServers from "../../servers/caseServer";
import ModalDetail from "../../components/ModalDetail";
import userServers from "../../servers/userServers";
export default {
  name: "caseBox",
  components: { ModalDetail },
  data() {
    return {
      href: "",
      userName: "",
      password: "",
      loadingShow: false,
      isShow: false,
      // inputValue:""
      modalDetail: {
        modalType: 1,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: "",
      },
      caseId: "", //"c72b4832-fe56-47b0-be00-e45f1939bebc",
      // search:"CY2019WZ0723",   // !写死了一个编号
      search: "", // !写死了一个编号
      case_head: [
        {
          title: "序号",
          width: 80,
          render: (h, params) => {
            return h("div", {}, params.index + 1);
          },
        },
        {
          title: "案件编号",
          key: "caseNo",
        },
        {
          title: "案件名称",
          key: "caseName",
        },
        {
          title: "检材/样本编号",
          key: "sampleNo",
        },
        {
          title: "检材/样本名称",
          key: "sampleName",
        },
        {
          title: "试剂盒",
          render:(h,params)=>{
            return h("span", {}, params.row.entity.reagentName);
          }
          // key: "reagentName",
        },
        {
          title: "板号",
          // key: "boardBarcode",
          render:(h,params)=>{
            return h("span", {}, params.row.entity.boardBarcode);
          }
          // boardBarcode
        },
        {
          title: "查看分型",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage",
                },
                on: {
                  click: (event) => {
                    this.$refs.modal.modalShow = true;
                    this.modalDetail.modalTitle = params.row.sampleNo;
                    this.modalDetail.modalTypeHeader = "单一样本分型";
                    this.modalDetail.modalInfo = params.row.geneMap.resultList;
                    this.modalDetail.modalData = params.row;
                  },
                },
              }),
            ]);
          },
        },
      ],
      case_head_mix: [
        {
          title: "序号",
          width: 80,
          render: (h, params) => {
            return h("div", {}, params.index + 1);
          },
        },
        {
          title: "案件编号",
          key: "caseNo",
        },
        {
          title: "案件名称",
          key: "caseName",
        },
        {
          title: "检材/样本编号",
          key: "sampleNo",
        },
        {
          title: "检材/样本名称",
          key: "sampleName",
        },
        {
          title: "试剂盒",
          render: (h, params) => {
            return h("span", {}, params.row.entity.reagentName);
          },
          // reagentName
        },
        {
          title: "板号",
          render: (h, params) => {
            return h("span", {}, params.row.entity.boardBarcode);
          },
          // boardBarcode
        },
        {
          title: "查看分型",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage",
                },
                on: {
                  click: (event) => {
                    this.$refs.modal.modalShow = true;
                    let detailInfo = {
                      geneID: params.row.entity.id,
                    };
                    this.modalDetail.modalTitle = params.row.sampleNo;
                    this.modalDetail.modalTypeHeader = "混合样本分型";
                    this.modalDetail.modalInfo = params.row.geneMap.resultList;
                    this.modalDetail.modalData = params.row;
                    // caseServers.dataModalDetail(detailInfo)
                    //   .then(res=>{
                    //     this.modalDetail.modalInfo = res.data.viewRatioGeneDetails.resultList;
                    //     this.modalDetail.modalData = res.data.viewRatioGeneDetails
                    //   })
                  },
                },
              }),
            ]);
          },
        },
      ],
      singleList: [],
      mixedList: [],
      caseNo: "",
      allListObj: {
        caseInfo: {},
        mixedSampleGeneList: [],
        singleSampleGenesList: [],
      },
    };
  },
  created() {
    this.href = window.location.href;
    this.userName = this.getUrlKey("users");
    this.password = this.getUrlKey("pass");
  },
  mounted() {
    this.if_url();
  },
  methods: {
    // 老系统跳转此页面 url地址参数判断
    if_url() {
      let url_ = this.href.indexOf("?");
      if (url_ == -1) {
      } else {
        this.handelLogin();
        this.getResult();
        //         this.handelSearch();
      }
    }, // 截取url参数操作
    getUrlKey(name) {
      return (
        decodeURIComponent(
          (new RegExp("[?|&]" + name + "=" + "([^&;]+?)(&|#|;|$)").exec(
            location.href
          ) || [, ""])[1].replace(/\+/g, "%20")
        ) || null
      );
    }, // 老系统跳转此页面操作 打开登录限制
    handelLogin() {
      let loginInfo = {
        userName: $.trim(this.userName),
        password: $.trim(this.password),
      };
      console.log(loginInfo);
      let item = JSON.stringify(loginInfo);
      userServers.datalogin(item).then((res) => {
        if (res.code == 1) {
          console.log(res);
          sessionStorage.setItem("username",res.result.realName)
          let data = res.result.realName;
          let token = res.result.accessToken;
          let userId = res.result.userId;
          sessionStorage.setItem("userId", userId);
          sessionStorage.setItem("loginfo", item);
          sessionStorage.setItem("username", data);
          localStorage.setItem("token", token);
          // this.$Message.success("登陆成功!欢迎您:" + res.result.realName);
          // this.$router.push({
          //   path: "/",
          //   name: "Home",
          //   params: {
          //     userName: res.result.realName,
          //   },
          // }); // 此处使用sesstion存储用户信息，
          sessionStorage.user = true;
        } else {
          this.$Message.error(res.message);
        }
      });
    },
    getResult() {
      let info = {
        caseId: this.getUrlKey("caseId"),
        geneIdList: this.getUrlKey("str").split(","),
      };
      console.log(info);
      let item = JSON.stringify(info);
      caseServers.findMixAndCaseNo(item).then((res) => {
        if (res.code == 1) {
          this.isShow = true;
          console.log(res.result.singleSampleGenesList)
          this.singleList = res.result.singleSampleGenesList;
          this.mixedList = res.result.mixedSampleGeneList;
          this.allListObj.caseInfo = res.result.caseInfo;
          this.allListObj.mixedSampleGeneList = res.result.mixedSampleGeneList;
          this.allListObj.singleSampleGenesList = res.result.singleSampleGenesList;
          this.caseId = res.result.caseInfo.id;
        }
      });
    },
    handelSearch() {
      if (!this.search) {
        this.$Message.error("请输入案件编号");
        return;
      }
      let searchInfo = {
        caseNo: $.trim(this.search),
      };
      this.loadingShow = true;
      caseServers
        .dataSearch(searchInfo)
        .then((res) => {
          // 查询条件判断
          if (res.code == 1) {
            this.loadingShow = false;
            this.allListObj.caseInfo = res.result.caseInfo;
            this.allListObj.mixedSampleGeneList =
              res.result.mixedSampleGeneList;
            this.allListObj.singleSampleGenesList =
              res.result.singleSampleGenesList;
            this.isShow = true;
            this.singleList = res.result.singleSampleGenesList;
            this.mixedList = res.result.mixedSampleGeneList;
            this.caseId = res.result.caseInfo.id;
            if (this.singleList.length == 0 && this.mixedList.length == 0) {
              this.$Message.error("单一样本、混合样本数据为空！");
            }
          } else {
            this.loadingShow = false;
            this.$Message.error(res.message);
          }
        })
        .catch((error) => {
          this.loadingShow = false;
        });
      // FYB1801084-2018WZ1084
    },
    handelHybrid() {
      if (!this.caseId) {
        this.$Message.error("请先查询案件");
      } else {
        // localStorage.setItem('caseId',this.caseId);
        let list = JSON.stringify(this.allListObj);
        localStorage.setItem("allListObj", list);
        this.$router.push("/hybrid");
      }
    },
  },
};
</script>
<style lang="less">
@import "../../assets/styles/case";
// @import "../../assets/styles/base";
</style>
