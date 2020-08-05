<template>
  <div>
    <div class="home_title"><router-link tag="b" to="/">首页</router-link> 》 <span>案件智能快速分析</span></div>
    <div class="case_title">
      <Icon type="ios-square case_title_icon"/>案件智能快速分析
    </div>
    <div class="case_search">
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
      >
      </i-input>
      <Button class="case_search_btn" @click="handelSearch" >确定</Button>
    </div>
    <div class="case_title" style="color: #000;">
      <Icon type="ios-square case_title_icon"/>导入检材、样本列表
    </div>
    <div class="case_data" v-show="isShow">
      <div class="case_single">
        <h5>单一样本</h5>
        <Table
          class="table-case-scroll"
          :columns="case_head"
          :data="singleList"
          border
          stripe
        >
        </Table>
        <h5>混合样本</h5>
        <Table
          class="table-case-scroll"
          :columns="case_head_mix"
          :data="mixedList"
          border
          stripe
        >
        </Table>
      </div>
    </div>
    <div class="case_data_null" v-show="!isShow">
      <div class="borderStyle">
        <div>
          <p><img src="../../assets/img/break_err.png" alt=""></p>
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
      <h1><span>LOADING</span></h1>
    </div>
  </div>
</template>
<script>
  import caseServers from '../../servers/caseServer'
  import ModalDetail from "../../components/ModalDetail";
  export default {
    name:"caseBox",
    components: {ModalDetail},
    data(){
      return{
        loadingShow:false,
        isShow:false,
        // inputValue:""
        modalDetail:{
          modalType:1,
          modalInfo:[],
          modalTitle:[],
          modalData:[],
          modalTypeHeader:''
        },
        caseId:"", //"c72b4832-fe56-47b0-be00-e45f1939bebc",
        // search:"CY2019WZ0723",   // !写死了一个编号
        search:"",   // !写死了一个编号
        case_head:[
          {
            title:"序号",
            width:80,
            render:(h,params)=>{
              return h('div',{

              },params.index+1)
            }
          },
          {
            title:"案件编号",
            key:"caseNo"
          },
          {
            title:"案件名称",
            key:"caseName"
          },
          {
            title:"检材/样本编号",
            key:"sampleNo"
          },
          {
            title:"检材/样本名称",
            key:"sampleName"
          },
          {
            title:"试剂盒",
            render:(h,params)=>{
              return h('span',{
              },params.row.entity.reagentName)
            }
            // reagentName
          },
          {
            title:"板号",
            render:(h,params)=>{
              return h('span',{
              },params.row.entity.boardBarcode)
            }
            // boardBarcode
          },
          {
            title:"查看分型",
            render:(h,params)=>{
              return h('div',[
                h('Icon', {
                  props: {
                    type: "md-eye orage"
                  },
                  on:{
                    click:(event)=>{
                      this.$refs.modal.modalShow = true
                      let detailInfo = {
                        geneID:params.row.entity.id
                      };
                      this.modalDetail.modalTitle = params.row.sampleNo
                      this.modalDetail.modalTypeHeader = '单一样本分型'
                      caseServers.dataModalDetail(detailInfo)
                        .then(res=>{
                          this.modalDetail.modalInfo = res.data.viewRatioGeneDetails.resultList;
                          this.modalDetail.modalData = res.data.viewRatioGeneDetails
                        })
                    }
                  },
                }),
              ])
            }
          },
        ],
        case_head_mix:[
          {
            title:"序号",
            width:80,
            render:(h,params)=>{
              return h('div',{

              },params.index+1)
            }
          },
          {
            title:"案件编号",
            key:"caseNo"
          },
          {
            title:"案件名称",
            key:"caseName"
          },
          {
            title:"检材/样本编号",
            key:"sampleNo"
          },
          {
            title:"检材/样本名称",
            key:"sampleName"
          },
          {
            title:"试剂盒",
            render:(h,params)=>{
              return h('span',{

              },params.row.entity.reagentName)
            }
            // reagentName
          },
          {
            title:"板号",
            render:(h,params)=>{
              return h('span',{

              },params.row.entity.boardBarcode)
            }
            // boardBarcode
          },
          {
            title:"查看分型",
            render:(h,params)=>{
              return h('div',[
                h('Icon', {
                  props: {
                    type: "md-eye orage"
                  },
                  on:{
                    click:(event)=>{
                      this.$refs.modal.modalShow = true;
                      let detailInfo = {
                        geneID:params.row.entity.id
                      };
                      this.modalDetail.modalTitle = params.row.sampleNo
                      this.modalDetail.modalTypeHeader = '混合样本分型'
                      caseServers.dataModalDetail(detailInfo)
                        .then(res=>{
                          this.modalDetail.modalInfo = res.data.viewRatioGeneDetails.resultList;
                          this.modalDetail.modalData = res.data.viewRatioGeneDetails
                        })
                    }
                  }
                }),
              ])
            }
          },
        ],
        singleList:[],
        mixedList:[],
        caseNo:""
      }
    },
    mounted() {
    },
    methods:{
      handelSearch(){
        if(!this.search){
          this.$Message.error('请输入案件编号')
          return 
        }
        let searchInfo = {
          caseNo:$.trim(this.search)
        };
        // console.log(searchInfo);
          this.loadingShow = true;
          caseServers.dataSearch(searchInfo).then(res=>{
            // console.log(res);
              // 查询条件判断
              if(res.code == 1){
                this.loadingShow = false;
                this.isShow = true;
                this.singleList = res.data.singleSampleGenesList;
                this.mixedList = res.data.mixedSampleGeneList;
                this.caseId = res.data.caseInfo.id;
                // console.log(this.mixedList);
                // console.log(this.singleList);
                if(this.singleList.length == 0 && this.mixedList.length == 0 ){
                    this.$Message.error("单一样本、混合样本数据为空！")
                }
              }else if(res.code == 0){
                this.loadingShow = false
                if(res.errorCode == -10007){
                  this.$Message.error(res.errorMessage)
                }
              }
            })
        // FYB1801084-2018WZ1084
      },
      handelHybrid(){
        // console.log(this.caseId);
        if(!this.caseId){
          this.$Message.error('请先查询案件')
        }else{
          localStorage.setItem('caseId',this.caseId);
          this.$router.push('/hybrid')
        }
      }
    }
  }
</script>
<style lang="less">
@import "../../assets/styles/case";
</style>
