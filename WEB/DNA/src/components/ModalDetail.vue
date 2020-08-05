<template>
  <Modal
    v-model="modalShow"
    class-name="result_vertical-center-modal"
    :header-hide="true"
  >
    <div class="modal_title">
      <p>{{dataName}}:{{dataTitle}}</p>
      <p>{{dataBigTitle}}</p>
      <p></p>
    </div>
    <div class="result_modal">
      <div class="over_heigth">
        <Table
          class="result_modal_table"
          :border="false"
          :columns="modal_title"
          :row-class-name="analyseMocalClassName"
          :data="dataList"
          @on-close="handelClose"
        >
        </Table>
      </div>
      <div class="result_modal_table_right">
        <div class="result_modal_title">峰图</div>
        <div class="img_cursor"  v-if="this.modalDetail.modalType==0">
          <img @click="ImgModal = true"  :src="dataImg" alt="0-图片加载失败" v-if="imgType==0">
          <img @click="ImgModal = true"  :src="dataImg1" alt="1-图片加载失败" v-else>
        </div>
        <div v-else-if="this.modalDetail.modalType==1 || this.modalDetail.modalType==2">
          <img @click="ImgModal = true" :src="dataImg2" alt="图片加载失败">
        </div>  
         <!-- 等于0显示按钮 -->
         <div v-show="BtnTypeshow">
            <p v-if="this.modalDetail.modalType==0">
                <i-button class="modal_title_button" @click="handelImgStatus(0)" :class="{modal_detail_active:imgType==0}">{{dataSplitMixBtn}}</i-button>
                <i-button class="modal_title_button" @click="handelImgStatus(1)" :class="{modal_detail_active:imgType==1}">比中</i-button>
              </p>
              <p v-else></p>
         </div>
      </div>
    </div>
    <div>{{dataBtnType}}</div>
    <!-- 图片放大 -->
    <Modal class="ImgModal_width" v-model="ImgModal" width="1104">
        <div>
          <div class="img_cursor"  v-if="this.modalDetail.modalType==0">
            <img  :src="dataImg" alt="0-图片加载失败" v-if="imgType==0">
            <img  :src="dataImg1" alt="1-图片加载失败" v-else>
          </div>
          <div class="img_cursor" v-else-if="this.modalDetail.modalType==1 || this.modalDetail.modalType==2">
            <img  :src="dataImg2" alt="图片加载失败">
          </div>
        </div>
        <p slot="footer"></p>
    </Modal>
  </Modal>
</template>
<script>

  export default {
    name:"ModalDetail",
    props:['modalDetail'],
    data(){
      return{
        ImgModal:false,
        modalShow:false,
        modalBigTitle:'查看基因型',
        modal_title:[],
        modalList:[],
        BtnTypeshow:true,
        // BtnType:0,
        imgType:0,
        modalName:'比中检材编号',
        modalHeader1:'拆分等位基因',
        modalHeader2:'比中等位基因',
        modalTypeHeader:'单一检材分型',
        modalSplitMixBtn:'拆分',
      }
    },
    computed:{
      dataShow(){
        return this.modalDetail.modalShow
      },
      dataBtnType(){
        if(this.modalShow==true){
          console.log(this.modalDetail.modalData);
          if(this.modalDetail.modalType == 0){
             this.modal_title= [
                    {
                      title:"序号",
                      width:"80",
                      render:(h,params)=>{
                        return h('span',{

                        },params.index+1)
                      }
                    },
                    {
                      title:"基因座",
                      key:"markerName",
                      // width:"140",
                    },
                    {
                      title:"拆分等位基因",
                      key:"geneStr1",
                      renderHeader:(h,params) => {
                        return h('div',this.dataHeader1)
                      }
                    },
                    {
                      title:"比中等位基因",
                      key:"geneStr2",
                      renderHeader:(h,params) => {
                        return h('div',this.dataHeader2)
                      }
                    },
                ]
          }
          if (this.modalDetail.BtnType == 9) {
                this.BtnTypeshow = false;
                if(this.modalDetail.modalType == 2){
                  this.modal_title= [
                    {
                      title:"序号",
                      width:"80",
                      render:(h,params)=>{
                        return h('span',{

                        },params.index+1)
                      }
                    },
                    {
                      title:"基因座",
                      key:"markerName",
                      // width:"140",
                    },
                    {
                      title:"混合检材分型",
                      key:"allele",
                      renderHeader:(h,params) => {
                      return h('div',this.dataTypeHeader)
                      }
                      // width:"140"
                    },
                ]
              }else{
                  this.modal_title= [
                    {
                      title:"序号",
                      width:"80",
                      render:(h,params)=>{
                        return h('span',{

                        },params.index+1)
                      }
                    },
                    {
                      title:"基因座",
                      key:"markerName",
                      // width:"140",
                    },
                    {
                      title:"拆分等位基因",
                      key:"geneStr1",
                      renderHeader:(h,params) => {
                        return h('div',this.dataHeader1)
                      }
                    },
                    {
                      title:"比中等位基因",
                      key:"geneStr2",
                      renderHeader:(h,params) => {
                        return h('div',this.dataHeader2)
                      }
                    },
                ]
              }
            }else{
              this.BtnTypeshow = true;
            }
         }
      },
      dataList(){
        if(this.modalShow==true){
          return this.modalDetail.modalInfo
        }else{
          return this.modalList = [];
        }
      },
      dataTitle(){
        return this.modalDetail.modalTitle
      },
      dataTypeHeader(){
        return this.modalDetail.modalTypeHeader
      },
      dataHeader1(){
        return this.modalDetail.modalHeader1
      },
      dataHeader2(){
        return this.modalDetail.modalHeader2
      },
      dataBigTitle(){
        return this.modalDetail.modalBigTitle
      },
      dataSplitMixBtn(){
        return this.modalDetail.modalSplitMixBtn
      },
      dataName(){
        if(this.modalDetail.modalName){
          return this.modalDetail.modalName
        }else{
          return '检材编号'
        }
        
      },
          /*
            geneImagePath（？？？）
            ratioSampleGeneImagePath 比中状态峰图路径全放在此字段内  无峰图为0   
            splitdSampleGeneImagePath 混合、拆分峰图 放到此字段，无峰图为0   
          */ 
      dataImg(){
        console.log(this.modalDetail.modalData);
        if(this.modalDetail.modalData.splitdSampleGeneImagePath == 0){
          this.modalDetail.modalData.splitdSampleGeneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.splitdSampleGeneImagePath
  
        }else{
          this.modalDetail.modalData.splitdSampleGeneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.splitdSampleGeneImagePath
          // return this.SplitImgPath(this.modalDetail.modalData.splitdSampleGeneImagePath);
        }
      },
      dataImg1(){
        if(this.modalDetail.modalData.ratioSampleGeneImagePath == 0){
          this.modalDetail.modalData.ratioSampleGeneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.ratioSampleGeneImagePath;
        }else{     
          this.modalDetail.modalData.splitdSampleGeneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.splitdSampleGeneImagePath
          // return this.SplitImgPath(this.modalDetail.modalData.ratioSampleGeneImagePath);
        }
      },
      dataImg2(){
         if(this.modalDetail.modalData.geneImagePath == 0){
          this.modalDetail.modalData.geneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.geneImagePath;
        }else{
          this.modalDetail.modalData.splitdSampleGeneImagePath = require('../assets/img/break_err.png')
          return this.modalDetail.modalData.splitdSampleGeneImagePath
          // return this.SplitImgPath(this.modalDetail.modalData.geneImagePath);
        }
      }
    },
    watch:{

    },

    created(){
      if(this.modalDetail.modalType==0){
        this.modal_title= [
          {
            title:"序号",
            width:"80",
            render:(h,params)=>{
              return h('span',{

              },params.index+1)
            }
          },
          {
            title:"基因座",
            key:"markerName",
            // width:"140",
          },
          {
            title:"拆分等位基因",
            key:"geneStr1",
            renderHeader:(h,params) => {
              return h('div',this.dataHeader1)
            }
          },
          {
            title:"比中等位基因",
            key:"geneStr2",
            renderHeader:(h,params) => {
              return h('div',this.dataHeader2)
            }
          },
        ]
      }else if(this.modalDetail.modalType==1){
        this.modal_title= [
          {
            title:"序号",
            width:"80",
            render:(h,params)=>{
              return h('span',{

              },params.index+1)
            }
          },
          {
            title:"基因座",
            key:"markerName",
            // width:"140",
          },
          {
            title:"单一检材分型",
            key:"allele",
            renderHeader:(h,params) => {
              return h('div',this.dataTypeHeader)
            }
          },
        ]
      }else if(this.modalDetail.modalType == 2){
        this.modal_title= [
          {
            title:"序号",
            width:"80",
            render:(h,params)=>{
              return h('span',{

              },params.index+1)
            }
          },
          {
            title:"基因座",
            key:"markerName",
            // width:"140",
          },
          {
            title:"混合检材分型",
            key:"allele",
            // renderHeader:(h,params) => {
            //   return h('div',this.dataTypeHeader)
            // }
          },
        ]
      }

    },
    mounted(){
    },
    beforeUpdate(){
        // this.handelDataList();
    },
    updated(){

      // console.log(this.modalDetail.modalInfo.mixedSampleSuspectsDetails.resultList)
    },
    methods:{
      find(str,cha,num){
        var x=str.indexOf(cha);
        for(var i=0;i<num;i++){
            x=str.indexOf(cha,x+1);
        }
        return x;
      },
      SplitImgPath(old_ImgPath){        
          // 旧-1 old_ImgPath = "ftp://14.168.81.131//upload//2019-09-29-YXN_1571903025.png"
          // 旧-2 old_ImgPath = "ftp://192.33.60.88//upload//2019/1571903025.png"
          // 新-1 New_ImgPath = "http://14.168.81.131:70/upload/2019-09-29-YXN_1571903025.png"
          // 新-2 New_ImgPath = "http://192.33.60.88:70/upload/2019/1571903025.png"
          // old_ImgPath = "ftp://14.168.81.131//upload//2019-09-29-YXN_1571903025.png"
          console.log(old_ImgPath);
          if (old_ImgPath == 0 || old_ImgPath == undefined){
              return;
          }
          if(old_ImgPath.indexOf("f") == -1){
              let New_ImgPath  = old_ImgPath
              return New_ImgPath;
          }else{
            let RepUrl = old_ImgPath.replace(/\/\//g,'/')
            let str_http = "http://";
            let str_host = ":70"  
            let str_url = RepUrl.substring(this.find(RepUrl,"/",0)+1,this.find(RepUrl,"/",1))
            let str_url_last = RepUrl.substring(this.find(RepUrl,"/",1))
            let New_ImgPath = str_http+"14.60.76.240"+str_host+str_url_last; 
            console.log(New_ImgPath);
            return New_ImgPath;
          }
      },
      handelDataList(){
      },
      //
      handelImgStatus(status){
            this.imgType = status;
        },                  
      handelDataList(){
      },
      handelClose(){
      },
      analyseMocalClassName(row,index){
        if(index%4==0){
          return "analyse_modal_table_pink"
        }else if(index%4==1){
          return "analyse_modal_table_blue"
        }else if(index%4==2){
          return "analyse_modal_table_violet"
        }else if(index%4==3){
          return "analyse_modal_table_gray"
        }else {
          return ""
        }
      },
    }
  }
</script>
<style lang="less">
    .img_cursor{
      img{
        cursor: pointer;
      }
    }
    .ImgModal_width{
      img{
        width: 100%;
      }
    }
    .modal_detail_active{
        background: rgba(12, 129, 245, 1) !important;
        color: #FFFFFF !important;
    }
    .modal_detail_active:hover{
      background: rgba(12, 129, 245, 1) !important;
      color: #FFFFFF !important;
    }
  .result_vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    .ivu-modal{
      width: 1000px !important;
      top: 0;
      font-family:Microsoft YaHei;
      font-weight:bold;
      .ivu-modal-header-inner{
        font-size:16px;
        color:rgba(12,129,245,1);
        text-align: center;
      }
    }
    .ivu-modal-footer{
      display: none !important;
    }
  }
  .ivu-table .analyse_modal_table_pink td{
    background:rgba(255,214,214,1);
  }
  .ivu-table .analyse_modal_table_blue td{
    background:rgba(226,239,255,1);
  }
  .ivu-table .analyse_modal_table_violet td{
    background:rgba(238,226,255,1);
  }
  .ivu-table .analyse_modal_table_gray td{
    background:rgba(248,248,248,1);
  }
  .modal_title{
    height:45px;
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    font-weight: bold;
    p{
      // width: 240px;
      text-align: center;
      margin-top: 6px;
    }
    p:nth-child(1){
      color: orange;
    }
    p:nth-child(2){
      color: #0C81F5FF;
    }
  }
  .result_modal_page{
    float: right;
  }

  .result_modal{
    width: 100%;
    overflow: hidden;
    display: flex;
  }
  .over_heigth{
      // overflow: auto !important;
      // overflow-x: hidden !important;
      // max-height: 1000px !important;
      // border: 1px solid #ccc !important;
    overflow: auto !important;
    overflow-x: hidden !important;
    max-height: 650px !important;
    border-right: 1px #dcdee2 solid !important;
    }
  .result_modal_table{
    width:510px;
    th{
      background:rgba(246,251,255,1)!important;
      height: 45px!important;
    }
    td{
      height: 31px !important;
    }
  }
  .result_modal_table_right{
    width: 510px;
    text-align: center;
    position: relative;
    .result_modal_title{
      width: 100%;
      background:rgba(246,251,255,1);
      height: 45px;
      line-height: 45px;
      font-size: 13px;
      text-indent: 20px;
    }
    img{
      width: 50%;
    }
    p{
      position: absolute;
      right: 0;
      top: 0;
      height: 45px;
      .modal_title_button{
        width:80px;
        height:42px;
        border-radius:3px 0px 0px 3px;
        margin: 0;
        padding: 0;
        font-weight:bold;
        font-size:14px;
        line-height: 10px !important;
        color:rgba(12,129,245,1);
      }
    }
  }
  .ivu-table .demo-table-info-row td{
    background-color: #2db7f5;
    color: #fff;
  }
  .ivu-table .demo-table-error-row td{
    background-color: #ff6600;
    color: #fff;
  }
  .demo-table-red-row{
    background-color: red;
    color: #fff;
  }
</style>
