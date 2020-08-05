<template>
  <div>
    <div class="home_title"><router-link tag="b" to="/">首页</router-link> 》 <span>数据库管理</span></div>
    <div class="data_box">
      <div class="data_title">
        <span></span><span>查询条件</span>
      </div>
      <ul class="data_search">
        <!-- <li>
          <div><p></p></div><span>案件编号</span>
          <Input class="data_search_input" type="text" placeholder="请输入案件编号" v-model="data_search.caseNo"/>
        </li>
        <li><div><p style="background:rgba(176,214,252,1);"></p></div>
          <span>案件名称</span>
          <Input class="data_search_input" type="text" placeholder="请输入案件名称" v-model="data_search.caseName"/>
        </li> -->
        <li>
          <div><p style="background: #FF5A55FF"></p></div>
          <span>检材编号</span>
          <Input class="data_search_input" type="text" placeholder="请输入检材编号" v-model="data_search.sampleNo"/>
        </li>
        <li>
          <div><p></p></div>
          <span>检材名称</span>
          <Input class="data_search_input" type="text" placeholder="请输入检材名称" v-model="data_search.sampleName"/>
          <Button class="data_search_button" @click="handelSearch">查询</Button>
        </li>
      </ul>
    </div>
    <div class="data_bac">
      <div class="data_table">
        <div class="data_table_left"><p></p><span>混合库样本</span></div>
        <Button class="delete" @click="handelBatchDelete">批量删除</Button>
      </div>
      <div v-if="data_list.length != 0">
        <div class="data_table_bottom">
          <Table
            border
            stripe
            class="data_table_list light-blue-table"
            :columns="table_colums"
            :data="data_list"
            @on-selection-change="selectChange"

          >
          </Table>
          <div class="page">
            <span>
              当前第{{currentPage}}页/共{{pageCount}}页/共{{allRecordCount}}条信息
            </span>
            <Page :total="allRecordCount" show-elevator prev-text="上一页" next-text="下一页" class-name="bazl_page" :current="currentPage" @on-change="changePage" :page-size="15"/>
          </div>
        </div>
      </div>
      <div class="error-con-data" v-else>
       <div>
          <Table 
            border
            stripe
            class="data_table_list display-none"
            :columns="table_colums"
          ></Table>
            <div class="borderStyle">
               <div>
                  <p><img src="../../assets/img/break_err.png" alt=""></p>
                  <p>您好，目前暂无数据显示</p>
                  <p>您可尝试其他操作</p>
                </div>
            </div>
        </div>
    </div>
    </div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
    <div v-show="loadingShow" class="loading_con"> 
      <div class="arc"></div>
      <h1><span>LOADING</span></h1>
    </div>
  </div>
</template>
<script>
  import dataServers from '../../servers/dataServers'
  import ModalDetail from "../../components/ModalDetail";
  import caseServer from "../../servers/caseServer";
  export default {
    name:"dataBase_box",
    components: {ModalDetail},
    data(){
      return{
        modalDetail:{
          modalInfo:[],
          modalData:[],
          modalType:2,
          modalTitle:"",
          modalName:'',
          modalTypeHeader:""

        },
        loadingShow:false,
        table_colums:[
          {
            title:'选择',
            type: 'selection',
            width:'60'
          },
          {
            title:"序号",
            width:"80",
            render:(h,params)=>{
              return h('span',{

              },params.index+1)
            }
          },
          {
            title:"检材编号",
            key:"sampleNo"
          },
          {
            title:"检材名称",
            key:"sampleName"
          },
          {
            title:"案件编号",
            key:"caseNo",
            width:"220"
          },
          {
            title:"案件名称",
            key:"caseName"
          },
          // {
          //   title:"试剂盒",
          //   key:"reagentName"
          // },
          // {
          //   title:"板号",
          //   key:"boardBarcode"
          // },
          {
            title:'查看混合分型',
            width:"130",
            render:(h,params)=>{
              return h('div',[
              h('img', {
                        attrs: {
                            src: require("../../assets/img/cxx.png")
                          },
                        style: {
                          width: "22px",
                          marginLeft:"20px",
                          marginRight:"3px",
                          cursor: "pointer",
                        },
                        domProps: {
                          title: '查看混合分型'
                        },
                        on:{
                          click:(event)=>{
                            let caseInfo = {
                              id:params.row.id,
                            };
                            dataServers.dataQueryMixedDetails(caseInfo).then(res=>{
                                if(res.code == 1){
                                  this.$refs.modal.modalShow = true;
                                  this.modalDetail.modalTitle = params.row.sampleNo
                                  this.modalDetail.modalInfo = res.result.geneInfoDetail;
                                  this.modalDetail.modalTypeHeader = "混合样本分型";
                                  this.modalDetail.modalData = res.result
                                }else{

                                }
                              })
                          }
                        }
                }),
              ])
            }
          },
          {
            title:"比中详情",
             width:"120",
            align:"center",
            render:(h,params) => {
              return h('div',[
                h('Icon', {
                  style:{
                    // color:"#007EF9",
                  },
                  props: {
                    type: "md-eye orage",
                  },
                  domProps: {
                     title: '查看比中详情'
                  },
                  on:{
                    click:(event)=>{
                      this.$router.push({
                        path:'/thanDetails',
                        query:{
                              id: params.row.id
                          }
                      })
                      // let Id = params.row.id
                      // let routeData = this.$router.resolve({
                      //     name: "thanDetails",
                      //     query: {
                      //         id: Id,
                      //     }
                      // });
                      // window.open(routeData.href, "_blank");
                    }
                  }
                }),
              ])
            }
            
          },

          {
            title:"操作",
            align:"center",
            width:"80",
            render:(h,params)=>{
              return h('div',[
                h('Icon',{
                  props: {
                    type:'ios-trash icon_delect'
                  },
                  on:{
                    click:event=>{
                      let removeInfo = {
                        id:params.row.id
                      };
                      dataServers.dataRemove(removeInfo).then(res=>{
                        this.handelDataList()
                      })
                    }
                  }
                })
              ])
            }
          },
          
        ],
        data_list:[],
        data_search:{
          caseNo:"",
          caseName:"",
          sampleNo:"",
          sampleName:""
        },
        currentPage:1,
        pageCount:1,
        allRecordCount:0,
        geneIds:[],
        geneId:[]
      }
    },
    mounted() {
      this.handelDataList()
    },
    methods:{
      // 数据库管理 初始数据
      handelDataList(){
        let searchInfo ={
          // caseNo      :$.trim(this.data_search.caseNo),
          // caseName    :$.trim(this.data_search.caseName),
          sampleNo    :$.trim(this.data_search.sampleNo),
          sampleName  :$.trim(this.data_search.sampleName),
          offset     :this.currentPage
        };
        let info = JSON.stringify(searchInfo);
        this.loadingShow = true;
        dataServers.dataList(info).then(res=>{ 
          if(res.code == 1){
            this.loadingShow = false;
            this.data_list = res.result.mixedSampleGeneArrayList;
            this.pageCount = res.result.pageInfo.pageCount;
            this.allRecordCount = res.result.pageInfo.allRecordCount;
          }else{
            this.loadingShow = false;
            // this.$Message.error('列表获取失败，请重试！')
          }
        }).catch(error => {
          (error)
          this.loadingShow = false;
        }) 
      },
      handelSearch(){
        this.currentPage = 1;
        this.handelDataList();
      },
      changePage(page){
        this.currentPage = page
         this.handelDataList();
      },
      selectChange(select){
        this.geneIds = select.map(ids => ids.id)
      },
      handelBatchDelete(){
        if(this.geneIds.length > 0){
          this.$Modal.confirm({
              title: '删除提醒',
              content: '<p>该操作不可撤回,确定要删除吗?</p>',
              onOk: () => {
                  let deleteInfo = {
                    ids:this.geneIds.join(",")
                  }
                  dataServers.dataBatchDelete(deleteInfo)
                  .then(res => {
                    if(res.code == 1){
                      this.$Message.success('删除成功！')
                      this.handelDataList()
                    }else{
                      this.$Message.error('删除失败,请重试！')
                    }
                  })
              },
              onCancel: () => {
                  
              }
          });
          
        }else{
          this.$Message.error('请先选择删除样本!')
        }
        
      }
    }
  }
</script>
<style lang="less">
  @import "../../assets/styles/data";
  @import "../../assets/styles/error";
  @import "../../assets/styles/loading";
</style>
