<template>
  <div style="margin-bottom:50px;">
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <router-link tag="b" to="/quickMatch">DNA分型快速比对</router-link>》
      <!-- <b>DNA分型快速比对</b>》 -->
      <span>混合型拆分</span>
    </div>
    <div v-if="messageShow" class="hybrid_box">
      <div class="hybrid_box_message">混合基因样本为空，不能进行分析比对</div>
    </div>
    <div class="hybrid_box" v-else>
      <!-- <div class="hybrid_box_title">
        <Icon type="ios-square case_title_icon" />混合型快速拆分比对
      </div> -->
      <div class="hybrid_box_middle" style="margin-top:10px">
        <div class="hybrid_middle_left">
          <h5>混合样本</h5>
          <li v-for="(item,index) in this.geneInfoListData" :key="index" style="width:100%;">
            <div :class="{leftBarStyle:leftBarClass==index}" @click="handelHybridData(item,index)">
              <p>
                <Icon type="md-home hybrid_left" />
              </p>
              <span>{{item.sampleNo}}</span>
              <span class="hybrid-M-span">M</span>
            </div>
          </li>
        </div>
        <div class="hybrid_middle_right">
          <div class="hybrid_parting">
            <div class="hybrid_parting_left">
              <div class="hybrid_parting_left_title">
                <p></p>混合样本峰图
              </div>
              <!-- transform: translateX(50%) -->
              <!-- :src="hybridImg?hybridImg:defaultImg"  width:85%;  @click="hybridImgModal = true" -->
              <div class="Img_con_ft">
                <!-- <img
                :src="SplitImgPath(this.hybridImg)?SplitImgPath(this.hybridImg):defaultImg"
                @click="hybridImgModal = true"
                style="margin-top: 10%;cursor:pointer; margin-left: 10%; width:65%; text-align:center"
                alt="图片加载失败"
                />-->
                <!-- <img :src="defaultImg"> -->
                <!-- {{ImgHtml}} -->
                <!-- <img  class="img_success" @click="hybridImgModal = true" :src="SplitImgPath(this.hybridImg)" alt="图片加载失败" v-if="SplitImgPath(this.hybridImg)">
                <img class="img_error" @click="hybridImgModal = true" :src="defaultImg" alt="" v-else>-->
                <div v-if="SplitImgPath(this.hybridImg)">
                  <div class="imgSuccesStyle">
                    <img
                      class="img_success"
                      @click="hybridImgModal = true"
                      :src="SplitImgPath(this.hybridImg)"
                      alt="图片加载失败"
                    />
                  </div>
                </div>
                <div v-else>
                  <div class="imgErrorStyle">
                    <p>
                      <img class="img_error" @click="hybridImgModal = true" :src="defaultImg" alt />
                    </p>
                    <p>您好，目前暂无数据显示</p>
                    <p>您可尝试其他操作</p>
                  </div>
                </div>
              </div>
            </div>
            <div class="hybrid_parting_right">
              <div class="hybrid_parting_right_title">
                <div>
                  <span>
                    <span></span>混合分型
                  </span>
                  <!-- <span class="input_style" style="color:rgb(255, 90, 86)!important;">
                    匹配下限：
                    <input type="text" v-model="mixSameCount" />
                  </span> -->
                </div>
              </div>
              <ul class="parting_box Scrollbar">
                <li v-for="(item,i) of this.result" :key="i">
                  <span>{{item.markerName}}</span>
                  <p>
                    <!-- grayColor 原背景色 -->
                    <span
                      v-for="(items,index) of item.allele"
                      @click="handelChoose(items,item,$event,i,index)"
                      :class="[items.identification == 0 ?chooseRed:'']"
                    >{{items.name}}{{index==item.allele.length-1? "":","}}</span>
                  </p>
                </li>
              </ul>
              <div class="borderStyle_gwc">
                <div class="gwc_con clearfix">
                  <!-- <span>
                    <button class="parting_button" @click="handelStorage">拆分样本加入比对</button>
                  </span>
                  <span>
                    <button class="parting_button" @click="Details_show">查看拆分样本并下一步</button>
                    <p>13</p>
                  </span> -->
                                      <button class="parting_button" @click="handelStorage">加入比对列表</button>
                    <!-- <button class="parting_button" @click="handleJoinList">加入比对列表</button> -->
                </div>
              </div>
            </div>
          </div>
          <div class="hybrid_middle_table_bottom" style=""></div>
          <!-- 2019-12-18添加设置条件 -->
          <div class="h5_title">
            <div>
              <p></p>设置条件
              
            </div>
          </div>
          <div class="margin-left" style="padding-left:30px;">
          <div class="">
            <span  class="title-span-style">性 &nbsp &nbsp &nbsp 别</span>
            <!-- 性别改成多选 -->
            <CheckboxGroup v-model="personSex" style="display:inline;">
                <Checkbox label="1">男</Checkbox>
                <Checkbox label="2">女</Checkbox>
            </CheckboxGroup>
            <!-- <RadioGroup @on-change="RadioChange">
              <CheckboxGroup>
                <Radio label="1" class size="large">
                  <font>男</font>
                </Radio>
                <Radio label="0" class size="large">
                  <font>女</font>
                </Radio>
              </CheckboxGroup>
            </RadioGroup> -->
            <!-- <span class="marginStyle title-span-style">入库起止时间</span> -->
            <!-- :value="value2" -->
            <!-- <Date-picker
              @on-change="DateChange"
              format="yyyy/MM/dd"
              type="daterange"
              placement="bottom-end"
              placeholder="选择日期"
            ></Date-picker> -->
            <!-- <span class="marginStyle title-span-style">容差</span>
            <Select @on-change="OneOptionChange" v-model="ModelOneOption" style="width:80px">
              <Option v-for="item in cityListOne" :value="item.value" :key="item.value">{{item.label}}</Option>
            </Select> -->
            <span class="marginStyle title-span-style">匹配下限</span>
            <Select @on-change="TwoOptionChange" v-model="ModelTwoOption" style="width:80px">
              <Option v-for="item in cityListTwo" :value="item.value" :key="item.value">{{item.label}}</Option>
            </Select>
          <!-- <span class="marginStyle title-span-style">匹配下限</span>
            <span class="input_style">
                <input type="text" v-model="ModelTwoOption"/>
           </span> -->
          </div>
          <div class="float-style clearfix">
            <div>
              <span  class="title-span-style">人员类型</span>
              <span @click="PersonnelModel_Show"   class="spanStyle">选择</span>
            </div>
             <div class="span-style Scrollbar">
                <span style="position:relative" v-for="(persItem,persIndex) in PersonneNewArray">{{persItem.Personnel}}
                  <p class="span-abs">✔</p>
                </span> 
                <!-- <span v-for="(spanItem,spanIndex) in citySitem.cityS">{{spanItem.city}}</span> -->
            </div> 
          </div>
          <div class="float-style clearfix">
            <div >
              <span  class="title-span-style">地 &nbsp &nbsp &nbsp 区</span> 
              <span class="spanStyle" @click="Area_Show">选择</span>
            </div>
            <div class="span-style Scrollbar">
                <div  v-for="(citySitem,CytisIde) in AreaNewArray">
                  <span style="position:relative">
                    {{citySitem.province}}
                     <p class="span-abs">✔</p>
                  </span> 
                  <span style="position:relative" v-for="(spanItem,spanIndex) in citySitem.cityS">
                    {{spanItem.city}}
                    <p class="span-abs">✔</p>
                  </span>
                </div>
            </div> 
          </div>
        </div>
          <!--原表格  -->
          <div class="h5_title clearfix">
            <div>
              <p></p>比对列表
              <!-- <span style="color:rgba(255,90,86,1) !important">比中总数:</span>
              <span class="span_" style="color:rgba(255,90,86,1) !important">{{total}}</span> -->
              <!-- <span>匹配下限:{{mixSameCount}}</span> -->
              <!-- <span class="input_style" style="color: rgb(255, 170, 6) !important;">匹配下限：<input type="text" v-model="mixSameCount"></span> -->
            </div>
            <!-- <div>
              <span>开始比对</span>
            </div>-->
          </div>
          <!-- <div class="hybrid_middle_right_tables">
            <Table
              class="right-table-Style"
              border
              stripe
              :columns="hybrid_head"
              :data="hybridList"
              height="300"
            ></Table>
          </div> -->
          <div style="text-align:center;padding:10px;">
            <Table border stripe class="table-scroll"  height="300" :columns="ComparisonTableCol" :data="ComparisonTableData"></Table>
          </div>
        </div>
      </div>
      <div class="footer_btn">
        <button @click="handleSubmit">提交比对</button>
      </div>
    </div>
    
    <Modal v-model="hybridImgModal" width="1104">
      <div>
        <!-- 原代码  <img :src="hybridImg?hybridImg:defaultImg" style="width:100%;" /> -->
        <img
          :src="SplitImgPath(this.hybridImg)?SplitImgPath(this.hybridImg):defaultImg"
          style="width:100%;"
        />
      </div>
      <p slot="footer"></p>
    </Modal>
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
    
    
    <!-- 地区选择 -->
    <Modal  class="model_con_Style" v-model="AreaShow" width="900" :closable="false">
      <div class="model-header">
         <span class>选择地区</span>
         <p class="all" @click="allCitys">全选</p>
      </div>
      <div class="model-Area-body">
        <div class="model-Area-ScrollBar Scrollbar">
          <ul>
            <!-- 省级城市 -->
            <li  class="li-style" v-for="(AreaItem,AreaIndex) in AreaList">
               <!-- @click="SelectArea(AreaIndex,AreaItem)"  -->
              <div :class="{'active':activeIndex == AreaIndex}"  @click="SelectArea(AreaIndex,AreaItem)">
                <!-- :class="activeClass == AreaIndex ? 'active':''" || :class="{'active':activeIndex == AreaIndex}"-->
                <span class="span-1">
                 ✔
                </span>
                 <span class="span-2">
                  {{AreaItem.regionalismName}}
                </span>
              </div>
            </li>
          </ul>
        </div>
        <!-- 地级城市 -->
        <div class="position-relative Scrollbar">
            <ul class="position-absolute">
              <li v-for="(cityItem,city_Index) in CitysList" >
                  <div  class="active-div"  @click="SelectCity(city_Index,cityItem)" :class="{'activeCity':cityItem.bOn}">
                      <span class="span-3">{{cityItem.regionalismName}}</span>
                      <span class="span-4">✔</span>
                      <!-- <span>{{cityItem.bOn}}</span> -->
                  </div>
              </li>
            </ul>
        </div>
      </div>
      <div class="model-footer">
        <p>
          <span @click="SendParamsToSubmitModel">确认</span>
          <span @click="AreaShow=false">取消</span>
        </p>
      </div>
    </Modal>
    <!-- 人员条件弹窗 -->
    <Modal  class="model_con_Style" v-model="PersonnelModel" width="600" :closable="false">
      <div class="model-header" style="position: relative;">
        <span class="">人员选择</span>
        <p class="all" @click="allSelection">全选</p>
      </div>
      <div class="PersonnelBody Scrollbar">
        <ul>
          <li  v-for="(PerItem,PerIndex) in PersonnelList" @click="PersonnelSelection(PerItem,PerIndex)" :class="{'PersonnelStyle':PerItem.flag}"  >
            <span class="span-per">{{PerItem.dictValue1}}</span>
            <span class="span-4">✔</span>
          </li>
        </ul>
      </div>
      <div class="model-footer">
        <p>
          <span @click="Perdetermine">确认</span>
          <span @click="PersonnelModel=false">取消</span>
        </p>
      </div>
    </Modal>
    <!-- 查看按钮 -->
    <!-- <Modal id="model_con" v-model="SeeDetailsModel">
      <p slot="header">
        <Icon type="ios-information-circle"></Icon>
        <span>详情信息</span>
      </p>
      <div style="text-align:center">
        <Table :columns="SeeDetailsCol" :data="SeeDetailsData"></Table>
      </div>
    </Modal> -->
        <!-- 比对进度弹窗 -->
    <Modal class="model_con_Style" v-model="ScheduleModel"  width="1000"  :closable="false">
      <div class="model-header">
        <span>比对进度</span>
      </div>
      <div class="schedule-body">
        <div class="schedule-title">
          <div class="title-child">
            <!-- <p>当前数据300万，预计比对时间30分钟。</p> -->
            <div class="">
              <span>
                <p></p>
              </span>
              <span>0%</span>
            </div>
          </div>
        </div>
        <div class="schedule-content">
          <div class="content-title">
            <span></span>
            <span>比中列表</span>
          </div>
            <Table
              class="table-scroll"
              border
              stripe
              :columns="ScheduleCol"
              :data="ScheduleData"
              height="350"
            ></Table>
          <div class="page">
          <span>
            当前第{{schedulePage}}页/共{{scheduleCount}}页/共{{scheduleAllCount}}条信息
          </span>
          <Page :total="scheduleAllCount" show-elevator prev-text="上一页" next-text="下一页" class-name="bazl_page" :current="schedulePage" @on-change="ScheduleChange" :page-size="15"/>
          </div>
        </div>
      </div>
      <!-- <div class="model-footer"> -->
        <!-- <p>
          <span @click="SendParamsToSubmitModel">确认</span>
          <span @click="AreaShow=false">取消</span>
        </p> -->
      <!-- </div> -->
    </Modal>
    <ModalDetail ref="modal" :modalDetail="modalDetail"></ModalDetail>
  </div>
</template>
<script>
import hybridServers from "../../servers/hybridServers";
import ModalDetail from "../../components/ModalDetail";
import homeServers from "../../servers/homeServer";
import userServers from "../../servers/userServers";
import caseServer from "../../servers/caseServer";
import quickMatchServers from '../../servers/quickMatch'
import { fcall } from "q";

export default {
  name: "QuickMixedSplit",
  components: { ModalDetail },
  filters: {
    comma(par) {
      return par + "ppp";
    }
  },
  data() {
    return {
            // 查看详情 model 所需参数
      modalDetail: {
          modalType: 2,
          modalInfo: [],
          modalTitle: [],
          modalData: [],
          modalTypeHeader: ''
     },
      schedulePage:1, // 比对进度 表格页码
      scheduleCount:1,// 比对进度 表格总页码
      scheduleAllCount:0, // 比对进度 表格数据总条数
      // 比对进度弹窗
      ScheduleModel:false,
      // 比对进度表格标题 弹出层 
      ScheduleCol:[
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "yb", 
          width: "150"
        },
        {
          title: "样本名称",
          key: "ym",
          // width: "120"
        },
        // {
        //   title: "Y-STR",
        //   key: "yr",
        //   // width: "80"
        //   render: (h, params) => {
        //     // console.log(params.row.yr);
        //     var Class = "";
        //     if(params.row.yr == "男"){
        //         Class = "span-style-mr";
        //     }else{
        //         Class = "span-style-men";
        //     }
        //     return h(
        //         "span",
        //           {
        //             class:Class,
        //             style: {
        //               display: "inline-block",
        //               backgroung:"rgba(0,118,255,1)",
        //             },
        //           },
        //          params.row.yr
        //       )
        //   }
        // },
        {
          title: "类型",
          key: "lx",
          // width: "80",
        },
        {
          title: "案件编号",
          key: "aj",
          // width: "120"
        },
        {
          title: "案件名称",
          key: "mc",
          // width: "120"
        },
        {
          title: "匹配位点个数",
          key: "pp",
          // width: "120"
        },
        {
          title: "容差个数",
          key: "rc",
          // width: "120"
        },
        {
          title: "操作",
          render: (h, params) => {
            return h("div", [
                h('Icon', {
                  props: {
                    type: "md-eye orage"
                  },
                  domProps: {
                     title: '查看详情'
                  },
                  on:{
                    click:(event)=>{

                    }
                  },
                }),
            ]);
          }
        }  
      ],
      // 比对进度表格数据
      // ScheduleData:[],
      ScheduleData:[
        {
          "yb":"CY-21932911-09009",
          "ym":"XXXX",
          "yr":"男",
          "lx":"人员类型",
          "aj":"Fy-220i4-kklow2",
          'mc':"其他类别案件",
          "pp":"2",
          "rc":"1",
        },
        {
          "yb":"CY-2459469-56666",
          "ym":"XXXX",
          "yr":"女",
          "lx":"XXX类型",
          "aj":"Fy-2www-567-3-",
          'mc':"XXX案件类型",
          "pp":"8",
          "rc":"5",
        },
                {
          "yb":"CY-2459469-56666",
          "ym":"XXXX",
          "yr":"女",
          "lx":"XXX类型",
          "aj":"Fy-2www-567-3-",
          'mc':"XXX案件类型",
          "pp":"8",
          "rc":"5",
        },
                        {
          "yb":"CY-2459469-56666",
          "ym":"XXXX",
          "yr":"女",
          "lx":"XXX类型",
          "aj":"Fy-2www-567-3-",
          'mc':"XXX案件类型",
          "pp":"8",
          "rc":"5",
        },
                        {
          "yb":"CY-2459469-56666",
          "ym":"XXXX",
          "yr":"女",
          "lx":"XXX类型",
          "aj":"Fy-2www-567-3-",
          'mc':"XXX案件类型",
          "pp":"8",
          "rc":"5",
        },
                        {
          "yb":"CY-2459469-56666",
          "ym":"XXXX",
          "yr":"女",
          "lx":"XXX类型",
          "aj":"Fy-2www-567-3-",
          'mc':"XXX案件类型",
          "pp":"8",
          "rc":"5",
        },
      ],
      // 地级市数据
      CitysList:[
     
      ],
      // 人员条件数据
      PersonnelList:[

      ],
      // 人员条件弹窗
      PersonnelModel:false,
      // 地区下标初始值
      Cityindex:-1,
      // 城市下标初始值
      activeIndex:0,
      //地区选择弹窗
      AreaShow:false,
      // 容差
      ModelOneOption: "",
      // 匹配下限
      ModelTwoOption: "",
      // 容差 数据
      cityListOne: [
        {
          value: "0",
          label: "0"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "6",
          label: "6"
        },
        {
          value: "8",
          label: "8"
        },
        {
          value: "10",
          label: "10"
        }
      ],
      // 匹配下限 数据
        cityListOne: [
        {
          value: "0",
          label: "0"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "6",
          label: "6"
        },
        {
          value: "8",
          label: "8"
        },
        {
          value: "10",
          label: "10"
        }
      ],
      // 匹配下限 数据
      cityListTwo: [
        {
          value: "5",
          label: "5"
        },
        {
          value: "6",
          label: "6"
        },
        {
          value: "7",
          label: "7"
        },
        {
          value: "8",
          label: "8"
        },
                {
          value: "9",
          label: "9"
        },
                {
          value: "10",
          label: "10"
        },
                {
          value: "11",
          label: "11"
        },
                {
          value: "12",
          label: "12"
        },
                {
          value: "13",
          label: "13"
        },
                {
          value: "14",
          label: "14"
        },
                {
          value: "15",
          label: "15"
        },
                {
          value: "16",
          label: "16"
        },
                {
          value: "17",
          label: "17"
        },
                {
          value: "18",
          label: "18"
        },
                        {
          value: "19",
          label: "19"
        },
                        {
          value: "20",
          label: "20"
        },
                        {
          value: "21",
          label: "21"
        },
                        {
          value: "22",
          label: "22"
        },
                        {
          value: "23",
          label: "23"
        },
                        {
          value: "24",
          label: "24"
        },
                        {
          value: "25",
          label: "25"
        },
                                {
          value: "26",
          label: "26"
        },
      ],
      //
      showstyle:false,
      // 地区列表
      AreaList:[
   
      ],
      // 快速比对记录 查看按钮model
      SeeDetailsModel: false,
      // 快速比对记录 查看详情表格标题
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
      // 快速比对记录 查看 详情表格数据
      SeeDetailsData: [],
      // 查看拆分样本并下一步 model
      SendComparisonModal: false,
      // 比对列表 表格数据
      ComparisonTableData:[],
      // 比对列表 表格标题
      ComparisonTableCol:[
       {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "sampleNo", 
        },
        {
          title: "分型",
          key: "queueType",
          render: (h, params) => {
            let type = ''
            if (params.row.queueType == 1) {
              type = '混合分型'
            } else if (params.row.queueType == 2) {
              type = '拆分分型'
            }
            return h('span', {
              prop: {

              }
            }, type)
          }
        },
        {
          title: "性别",
          key: "sex",
          render: (h, params) => {
            let sexVal = ''
            if (params.row.sex == '["1"]') {
              sexVal = '男'
            } else if (params.row.sex == '["2"]') {
              sexVal = '女'
            }else if(params.row.sex == '["1","2"]'){
              sexVal = "男,女"
            }
            return h('span', {
              prop: {

              }
            }, sexVal)
          }
        },
        {
          title: "地区",
          key: "districtName",
        },
        {
          title: "人员类型",
          key: "personTypeName",
        },
        {
          title: "创建时间",
          key: "datetime",
        },
        // {
        //   title: "容差",
        //   key: "condition",
        // },
        {
          title: "匹配下限",
          key: "mixsameCount",
        },
          {
                        title: '查看详情',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                     on: {
                                        click: (event) => {
                                          console.log("157")
                                            this.$refs.modal.modalShow = true
                                            this.modalDetail.modalTypeHeader = '基因分型'
                                            this.modalDetail.modalInfo = params.row.geneInfos.resultList
                                            this.modalDetail.modalTitle = params.row.sampleNo
                                            this.modalDetail.modalData = params.row;

                                        }
                                    }
                                }, '查看'),
                            ]);
                        }
                    },
                {
          title: "操作",
          key: "action",
          width: 120,
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: (event) => {
                                          console.log(params)
                                          let delId = {
                                            id:params.row.id
                                          }
                                           quickMatchServers.deleteLocusListInfoForId(delId).then(res => {
                                             if(res.code == 1){
                                               this.ComparisonTableData.splice(params.index, 1);
                                             }else{
                                               this.$Message.error("删除失败~")
                                             }
                                           })
                                        }
                                    }
                                }, '删除'),
                            ]);
                        }
        },
        // {
        //   title: "性别",
        //   render: (h, params) => {
        //     return h("div", [
        //       h(
        //         "Button",
        //         {
        //           props: {
        //             type: "primary",
        //             size: "small"
        //           },
        //           style: {
        //             marginRight: "5px"
        //           },
        //           on: {
        //             click: event => {
        //               this.SeeDetailsModel = true;
        //               this.SeeDetailsData = params.row.geneMap.resultList;
        //             }
        //           }
        //         },
        //         "查看"
        //       ),
        //       h(
        //         "Button",
        //         {
        //           props: {
        //             type: "error",
        //             size: "small"
        //           },
        //           on: {
        //             click: event => {
        //               let Idinfo = {
        //                 id: params.row.entity.id
        //               };
        //               hybridServers.deleteSpli(Idinfo).then(res => {
        //                 this.Details_show();
        //               });
        //             }
        //           }
        //         },
        //         "删除"
        //       )
        //     ]);
        //   }
        // }  
      ],

      // 快比对拆分表格数据
      geneListData: [],
      // 快比对拆分表格标题
      geneListCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "检材名称",
          key: "sampleName", // sampleNo
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    display: "inline-block"
                  }
                },
                params.row._index + 1 + " - " + params.row.sampleName
              )
            ]);
          }
        },
        {
          title: "比中状态",
          key: "",
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
                      let Idinfo = {
                        id: params.row.entity.id
                      };
                      hybridServers.deleteSpli(Idinfo).then(res => {
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
      loadingShow: false, //  loading model
      btnType_true: true,
      ListArrData: [],
      ListNumType: 0,
      grayColor: "grayColor",
      chooseRed: "chooseRed",
      leftBarClass: "",
      // modalDetail: {
      //   modalType: 0,
      //   modalTitle: "",
      //   modalInfo: [],
      //   modalData: [],
      //   modalHeader1: "",
      //   modalHeader2: "",
      //   modalBigTitle: "查看比中详情",
      //   modalSplitMixBtn: ""
      // },
      // 本案可疑来源样本 表格标题
      hybrid_head: [
        {
          title: "序号",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "同型比中分组",
          key: "groupName"
        },
        {
          title: "来源",
          key: "sampleType",
          render: (h, params) => {
            var imgPath;
            if (params.row.sampleFlag == "0") {
              imgPath = require("../../assets/img/hj.png");
            } else {
              imgPath = require("../../assets/img/ren.jpg");
            }
            return h("div", [
              h("img", {
                attrs: {
                  src: imgPath
                },
                style: {
                  width: "13px",
                  float: "left",
                  marginTop: "0px"
                },
                on: {
                  click: event => {
                    // console.log(params);
                  }
                }
              }),
              h(
                "span",
                {
                  style: {
                    // width: "13px",
                    // float: "left",
                    // marginTop: "5px"
                  },
                  on: {
                    click: event => {
                      console.log(params);
                    }
                  }
                },
                params.row.sampleType
              )
            ]);
          }
        },
        {
          title: "检材编号",
          key: "sampleNo"
        },
        {
          title: "检材名称",
          key: "sampleName"
        },
        {
          title: "基因座数量",
          key: "sumCount"
        },
        {
          title: "比中数",
          key: "ratio"
        },
        {
          title: "查看比中详情",
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
                    this.modalDetail.modalHeader1 = "混合样本等位基因";
                    this.modalDetail.modalHeader2 = "比中样本等位基因";
                    this.modalDetail.modalSplitMixBtn = "混合";
                    let detailInfo = {
                      sampleGeneId: params.row.mixedSampleGendId,
                      ratiogeneId: params.row.singlegeneId
                    };
                    homeServers.dataDetail(detailInfo).then(res => {
                      this.modalDetail.modalInfo =
                        res.data.viewRatioGeneDetails.resultList;
                      this.modalDetail.modalData =
                        res.data.viewRatioGeneDetails;
                    });
                  }
                }
              })
            ]);
          }
        },
        {
          title: "拆分比对",
          width: "180",
          render: (h, params) => {
            var img = require("../../assets/img/7708.png");
            let radioIndex = params.index;
            let id = params.row.id;
            let flag = false;
            if (this.currentChoose === id) {
              flag = true;
            } else {
              flag = false;
            }
            return h("div", [
              h("input", {
                class: "duoxuan",
                props: {},
                attrs: {
                  type: "checkbox",
                  name: "lowLight"
                },
                style: {
                  float: "left",
                  marginTop: "1px",
                  width: "18px",
                  height: "18px"
                },
                on: {
                  click: e => {
                    this.input_fun(params);
                  }
                }
              }),
              h("img", {
                attrs: {
                  src: img
                },
                style: {
                  width: "13px",
                  float: "left",
                  // float: "left",
                  marginTop: "3px",
                  marginLeft: "7px",
                  marginRight: "7px"
                }
              }),
              h(
                "span",
                {
                  style: {
                    float: "left",
                    fontSize: "14px",
                    fontWeight: "bold",
                    color: "rgba(255, 90, 86, 1)",
                    // float: "left",
                    cursor: "pointer"
                  },
                  on: {
                    click: event => {}
                  }
                },
                "自动拆分"
              )
            ]);
          }
        }
      ],
      // 本案可疑来源样本 表格数据
      hybridList: [],
      caseId: "", // 由上一个页面传递的参数， 用于此页面渲染数据
      result: [], //从前一页带入的list数据集合中基因座信息
      hybridImg: "",
      defaultImg: require("../../assets/img/break_err.png"),
      // defaultImg: require("../../assets/img/feng.jpg"),
      hybridImgModal: false,
      // leftTitle: [],
      geneInfoListData:[], //从前一页带入的list数据集合
      currentSampleNo:'', //当前选中的sampleNo
      currentMixedId:'',
      personSex:['1','2'],
      messageShow: false,
      hybrid_id: "",
      hybridName: "",
      hybridArr: [{ markerName: "" }],
      hybridIndex: 1,
      mixSameCount: 15,
      sampleId: [],
      caseNo: "",
      currentChoose: 0,
      allData: [], // 主动选择的基因位点参数
      userName: "",
      password: "",
      href: "",
      type_num: 0,
      total: 0, // 比中总数
      recordingId: "", // 快速比对记录接口 所需参数
      AreaNewArray:[] ,// 地区条件渲染参数 html
      AreaNewArrayCode:[], //向后端传地区参数
      AreaNewArrayName:[],
      PersonneNewArray:[], // 人条件渲染参数 html
      PersonneNewArrayCode:[],//向后端传人员参数
      PersonneNewArrayName:[],
      CitysInfo:"", // 省级城市ID，根据此条件查询省级下面地级市
      RadioType:"", // 性别
      LowerLimit:"", // 匹配下限
      Tolerance:"", // 容差 
      DateArray:[], // 时间
      parGenIfo:[],
      Comtype:null,
    };
  },
  created() {
    // console.log(this.SplitImgPath(this.hybridImg));
    this.href = window.location.href;
    this.userName = this.getUrlKey("userName");
    this.password = this.getUrlKey("passWord");
    this.if_url();
    if (sessionStorage.getItem("username") == null) {
      this.$router.go(0);
    }
  },
  computed: {},
  mounted() {
    // 页面初始数据
    // this.handelList();

    // 2019年12月覆盖
    this.geneInfoListData = this.$route.params.geneInfoData
    this.parGenIfo = this.geneInfoListData[0].geneInfoList
    this.result = this.geneInfoListData[0].geneInfoList
    console.log(this.result);
    this.currentSampleNo = this.geneInfoListData[0].sampleNo
    this.currentMixedId = this.geneInfoListData[0].mixedSampleId
          // 添加flag
        for (var i = 0; i < this.result.length; i++) {
          for (var k = 0; k < this.result[i].allele.length; k++) {
            this.result[i].allele[k].flag = false;
          }
        }
    // console.log(this.geneInfoListData);
    if(this.geneInfoListData[0].tableData != "" || this.geneInfoListData[0].tableData != undefined || this.geneInfoListData[0].tableData != null){
      this.ComparisonTableData = this.geneInfoListData[0].tableData;
    } 

  },
  methods: {
        // 地区全选
        allCitys(){
            var citySNum = 0; // 根据此值判断 数据中的bon状态
            for (let k = 0; k < this.CitysList.length; k++) {
                if (this.CitysList[k].bOn == true) {
                citySNum += 1;
                }
            }
            // 两层循环是因为第一层循环中程序还没走完。
            for (let i = 0; i < this.CitysList.length; i++) {
                if (citySNum == 0) {
                this.CitysList[i].bOn = true;
                } else if (citySNum == this.CitysList.length) {
                this.CitysList[i].bOn = false;
                } else {
                this.CitysList[i].bOn = true;
                }
            }
        },
    // 弹窗取消事件
      DisplayModal(){
        this.SendComparisonModal = false;
        this.PersonneNewArray = [];
        this.AreaNewArray = [];
      },
    // 人员条件选择 弹窗
    PersonnelModel_Show(){
      this.PersonnelModel = true;
      hybridServers.GetPersonnelData().then(res => {
          // console.log(res)
          this.PersonnelList = res.data;
          for(let New of this.PersonnelList){
            for(let Old of this.PersonneNewArray){
              if(Old.PerCode == New.dictKey){
                New.flag = true;
              }
            }
          }  
      });
    },
    // 人员条件 全选
    allSelection(){
      var allNum = 0; // 根据此值判断 数据中的flag装态
      for(let a = 0; a < this.PersonnelList.length; a++ ){
          if(this.PersonnelList[a].flag == true){
            /*
              为allNum赋值，如果flag存在true的状态那就 +1,
              最后在第二次循环中判断如果此值为0就代表当前flag全为false的状态，将flag改为true
              如果此值等于数据组的长度就代表当前flag全为true的状态，将flag改为false.
              如果不等于0,也不等于数据组的长度就代表当前flag的状态true和flag都存在，将flag统一为true
            */ 
            allNum += 1;
          }
        }
      // 两层循环是因为第一层循环中程序还没走完。
      for(let i = 0; i < this.PersonnelList.length; i++ ){
          if(allNum == 0 ){
            this.PersonnelList[i].flag = true;
          }else if(allNum == this.PersonnelList.length){
            this.PersonnelList[i].flag = false;
          }else{
            this.PersonnelList[i].flag = true;
          }
        }
      },
    // 人员选择按钮
    PersonnelSelection(item,index){
      if(item.flag == false){
        item.flag = true;
      }else{
        item.flag = false;
      }
    },
    // 人员选择确认按钮
    Perdetermine(){
      this.PersonnelModel = false;
      this.PersonneNewArray = [];  // 页面人员条件渲染参数
      // console.log(this.PersonnelList);
      // console.log(this.PersonnelList);
      for(let PersonnelItem of this.PersonnelList){
        if(PersonnelItem.flag == true){
          this.PersonneNewArray.push({
              Personnel:PersonnelItem.dictValue1,
              PerCode:PersonnelItem.dictKey
            });
        }
      }
    },
    // 提交比对参数
    SendComparisonParams(){
      this.SendComparisonModal = false; 
      //  console.log(this.AreaNewArray); // 选中的省级、地级城市参数
      //  console.log(this.PersonneNewArray); // 选中的人员参数
      //  console.log(this.RadioType); // 性别参数  男/1 --- 女/2
      //  console.log(this.LowerLimit); // 匹配下限参数
      //  console.log(this.Tolerance); // 容差参数
      //  console.log(this.DateArray); // 时间参数
      //  this.$router.push("/ComSchedule");
    },
    // 地区条件确认按钮 将设置的区域条件渲染在提交版块
    SendParamsToSubmitModel(){
      this.AreaShow = false;
      this.AreaNewArray = []; // 页面地区条件渲染参数
      var Areadata;  
      for (let AreaItem of this.AreaList){
        if(AreaItem.bOn == true){
          // 将选中的地区赋值
         Areadata = AreaItem.regionalismName;
        }
      }
      var Citydata = [];
      for(let CitysItem of this.CitysList){
          // bon为true时代表选中状态，将选中的那组地区添加到数组
          if(CitysItem.bOn == true){
            Citydata.push({
              city:CitysItem.regionalismName,
              cityCode:CitysItem.regionalismCode,
              })
          }
        }
        // 拼接渲染数据
        var data = {
          province:Areadata,
          cityS:Citydata
        }
      // 生成新数组渲染页面/条件容器
      this.AreaNewArray.push(data);
    },
    // 地区选择框点击事件
    Area_Show(){
      // 弹窗
      this.CitysInfo = {
        code:"",
      }
      this.AreaShow = true;
      // 获取省级参数
      hybridServers.GetCitysData(this.CitysInfo).then(res => { 
          if(res.code == 1){
            console.log(res)
            res.data[0].bOn = true // 将省级数据中第一条状态改为true，代表默认选中的省级城市。
            this.AreaList = res.data;
            this.CitysInfo.code = res.data[0].regionalismCode;
            hybridServers.GetCitysData(this.CitysInfo).then(res => {
            // 为地级市赋值
            if(res.code == 1){
              this.CitysList = res.data;
            }
          });
        }
      });
    },
    // 地级城市选择
    SelectCity(i,item){
      // 判断bon状态，为true时代表选中状态，为其添加class
      if(item.bOn == false){
        item.bOn = true;
      }else{
        item.bOn = false;
      }
    },
    // 省级城市切换
    SelectArea(index,item){
    if(item.bOn == false){
        item.bOn = true;
      }else{
        item.bOn = false;
      }
     // 切换省级城市时发送省级id 用于查询地级城市 
     this.activeIndex = index;
     this.CitysInfo.code = item.regionalismCode; // 省级ID
     hybridServers.GetCitysData(this.CitysInfo).then(res => {
          // 为地级市赋值
          // console.log(res);
          if(res.code == 1){
            this.CitysList = res.data;
          }
      });
    },
    // 容差监听事件
    OneOptionChange(val) {
      // console.log(val);
      this.Tolerance = val;
    },
    // 匹配下限监听事件
    TwoOptionChange(val) {
      // console.log(val);
      this.LowerLimit = val;
    },
    // 单选框监听事件
    RadioChange(val) {
      // console.log(val);
      this.RadioType = val;
    },
    // 选择日期监听事件
    DateChange(val) {
      // console.log(val);
      this.DateArray = val;
    },
    img_show() {
      this.hybridImgModal = true;
    },
    // 截取字符串
    find(str, cha, num) {
      var x = str.indexOf(cha);
      for (var i = 0; i < num; i++) {
        x = str.indexOf(cha, x + 1);
      }
      return x;
    },
    // 截取、拼接峰图路径
    SplitImgPath(old_ImgPath) {
      if (old_ImgPath == 0 || old_ImgPath == undefined || old_ImgPath == null) {
        return;
      }
      if (old_ImgPath.indexOf("f") == -1) {
        let New_ImgPath = old_ImgPath;
        return New_ImgPath;
      } else {
        let RepUrl = old_ImgPath.replace(/\/\//g, "/");
        let str_http = "http://";
        let str_host = ":70";
        let str_url = RepUrl.substring(
          this.find(RepUrl, "/", 0) + 1,
          this.find(RepUrl, "/", 1)
        );
        let str_url_last = RepUrl.substring(this.find(RepUrl, "/", 1));
        let New_ImgPath = str_http + "14.60.76.240" + str_host + str_url_last;
        return New_ImgPath;
      }
    },
    // 查看拆分样本并下一步 model
    Details_show() {
      this.SendComparisonModal = true;
      this.PersonneNewArray = [];
      this.AreaNewArray = [];
    },
    // 弹出窗取消按钮

    // 老系统跳转此页面 url地址参数判断
    if_url() {
      let url_ = this.href.indexOf("?");
      if (url_ == -1) {
        this.caseId = localStorage.getItem("caseId");
      } else {
        if (!this.caseId) {
          this.loadingShow = true;
        } else {
          this.loadingShow = false;
        }
        this.caseNo = this.getUrlKey("caseNo");
        this.handelLogin();
        this.handelSearch();
      }
    },
    // 老系统跳转此页面操作 渲染数据
    handelSearch() {
      let searchInfo = {
        caseNo: this.caseNo
      };
      caseServer.dataSearch(searchInfo).then(res => {
        // 查询条件判断
        if (res.code == 1) {
          // this.loadingShow = false;
          this.caseId = res.data.caseInfo.id;
          this.handelList();
        } else if (res.code == 0) {
          if (res.errorCode == -10007) {
            this.$Message.error(res.errorMessage);
          }
        }
      });
    },
    // 老系统跳转此页面操作 打开登录限制
    handelLogin() {
      let loginInfo = {
        loginName: this.userName,
        loginPassword: this.password
      };
      userServers.datalogin(loginInfo).then(res => {
        if (res.code == 1) {
          let data = res.data.personName;
          sessionStorage.setItem("loginfo", JSON.stringify(loginInfo));
          sessionStorage.setItem("username", data);
          // 此处使用sesstion存储用户信息，
          sessionStorage.user = true;
        } else {
          // this.$Message.error("登陆失败,请重试");
        }
      });
    },
    // 截取url参数操作
    getUrlKey(name) {
      return (
        decodeURIComponent(
          (new RegExp("[?|&]" + name + "=" + "([^&;]+?)(&|#|;|$)").exec(
            location.href
          ) || [, ""])[1].replace(/\+/g, "%20")
        ) || null
      );
    }, 
    // 自动拆分逻辑
    input_fun(params) {
      // 判断当前选中框的的状态
      if ($('input[name="lowLight"]').eq(params.index).prop("checked")) {
        $('input[name="lowLight"]').prop("checked", false);
        $('input[name="lowLight"]').eq(params.index).prop("checked", true);
        // 为1时直接使用后返回的参数，为0时使用主动点击选中的参数
        this.ListNumType = 1;
        // 参数
        this.hybrid_id = params.row.mixedSampleGendId;
        let splitInfo = {
          singleSampleGeneId: params.row.singlegeneId,
          mixedSampleGeneId: params.row.mixedSampleGendId
        };
        // 此请求后台会返回自动拆分状态下应选中的参数为哪些，依据标识判断 0/1。
        hybridServers.dataSplit(splitInfo).then(res => {
          this.result = res.data.list;
          // console.log(this.result);
          this.ListArrData = JSON.stringify(res.data.list);
          // 为数据添加flag，用于在handelStorage()中拼接数据使用。
          for (var i = 0; i < this.result.length; i++) {
            for (var k = 0; k < this.result[i].allele.length; k++) {
              this.result[i].allele[k].flag = false;
              if (this.result[i].allele[k].identification == "0") {
                this.result[i].allele[k].flag = true;
              }
            }
          }
        });
        // console.log(this.result);
        // 为数据中标识为0的添加 class 背景颜色。
        this.chooseRed = "chooseRed";
      } else {
        // 取消时清空选中状态 清空背景颜色 改变ListNumType状态
        $(".parting_box li p span").removeClass("chooseRed");
        this.chooseRed = " ";
        $(".parting_box li p span").removeClass("linelight");
        this.ListNumType = 0;
      }
    },
    //点击左侧查询数据
    handelHybridData(item, index) {
      console.log(this.Comtype)
      if(this.Comtype == 0){
            this.$Modal.info({
              title: "提示",
              content: "请先提交比对！"
            });  
            return;
      }
      console.log(this.LowerLimit);
      this.ComparisonTableData = [];
      this.AreaNewArray = [];
      this.PersonneNewArray = [];
      this.LowerLimit = '';
      this.ModelTwoOption = '';
      $('input[name="lowLight"]').prop("checked", false);
      $(".parting_box li p span").removeClass("chooseRed");
      this.allData = []; // 11/12
      this.result = this.geneInfoListData[index].geneInfoList
      this.leftBarClass = index;
      this.currentSampleNo = this.geneInfoListData[index].sampleNo
      this.currentMixedId = this.geneInfoListData[index].mixedSampleId
      // 添加flag
        for (var i = 0; i < this.result.length; i++) {
          for (var k = 0; k < this.result[i].allele.length; k++) {
            this.result[i].allele[k].flag = false;
          }
        }
      console.log(this.result);
      // return
      // this.hybrid_id = item.mixedSampleGeneVo.entity.id;
      // this.total = item.singleSampleCount;
      // this.leftBarClass = index;
      // this.sampleId = item.entity.id;
      // let leftInfo = {
      //   sampleId: this.sampleId
      // };
      // hybridServers.dataLeft(leftInfo).then(res => {
      //   this.hybridList = res.data.matchResultMixSingleList;
      //   this.result = res.data.mixedSampleGene.resultGeneInfo.resultList;
      //   this.hybridImg = res.data.mixedSampleGene.genePicture;
      // });
    },
    // 页面初始数据
    handelList() {
      this.loadingShow = true;
      let hybridInfo = {
        caseId: this.caseId
      };
      // console.log(hybridInfo);
      hybridServers.dataList(hybridInfo).then(res => {
        if (res.code == 1) {
          this.loadingShow = false;
        }
        if (res.data.status == 1) {
          this.messageShow = true;
        } else {
          this.caseNo = res.data.caseNo;
          this.messageShow = false;
          this.hybridList = res.data.resuleList[0][0].matchResultMixSingleList;
          this.result = res.data.resuleList[0][0].mixedSampleGeneVo.resultGeneInfo.resultList;
          // console.log(this.result);
          this.leftTitle = res.data.resuleList[0];
          this.hybridImg =
            res.data.resuleList[0][0].mixedSampleGeneVo.entity.genePicture;
          this.total = res.data.resuleList[0][0].singleSampleCount;
          this.mixSameCount = res.data.mixSameCount;
          this.sampleId = res.data.resuleList[0][0].entity.id;
          // this.hybrid_id = res.data.resuleList[0][0].matchResultMixSingleList[0].mixedSampleGendId;
          this.hybrid_id =
            res.data.resuleList[0][0].mixedSampleGeneVo.entity.id;
          // 为数据添加flag，用于在handelStorage()中拼接数据使用。
          for (var i = 0; i < this.result.length; i++) {
            for (var k = 0; k < this.result[i].allele.length; k++) {
              this.result[i].allele[k].flag = false;
            }
          }
        }
      });
    },
    //点击 获取基因数
    handelChoose(items, item, event, i, index) {
      // if (this.ListNumType == 2) {
      //   this.$Message.error("请先取消高亮状态！");
      //   return;
      // }
      
      this.ListNumType = 1;
      if (event.target.classList.contains("chooseRed")) {
        if (items.name == "Y") {
          event.target.classList.remove("chooseRed");
          event.target.previousSibling.classList.remove("chooseRed");
          this.result[i].allele[index].flag = false;
        }
        event.target.classList.remove("chooseRed");
        this.result[i].allele[index].flag = false;
      } else {
        if (items.name == "Y") {
          event.target.previousSibling.classList.add("chooseRed");
          event.target.classList.add("chooseRed");
          this.result[i].allele[index].flag = true;
        } else {
          event.target.classList.add("chooseRed");
          this.result[i].allele[index].flag = true;
          // 原判断在此处
          if (
            event.target.parentNode.getElementsByClassName("chooseRed")
              .length == 3
          ) {
            event.target.classList.remove("chooseRed");
            this.result[i].allele[index].flag = false;
            this.$Modal.info({
              title: "提示",
              content: "每个基因座最多选择两个位点值"
            });
          }
        }
      }
    },
    // 加入比对列表
    handelStorage() {
      var storageInfo; // 已拆分单个样本参数
      var resnum = 0;  // 判断条件
      // console.log(this.AreaNewArray)
      // 判断设置条件中是否有未选中
        if(this.AreaNewArray.length == 0  || this.PersonneNewArray.length == 0){
          this.$Modal.info({
            title: "提示",
            content: "请在设置条件中设置选项~"
          });
          return
        }
        if(!this.LowerLimit){
          this.$Modal.info({
            title: "提示",
            content: "请在设置条件中设置选项~"
          });
          return
        }
        for(let resItem  of this.result){
            for(let allitem of resItem.allele){
              if(allitem.flag == true){
                resnum += 1;
              }
            }
        } 
        // 处理地区
        this.AreaNewArrayCode = this.AreaNewArray[0].cityS.map(item => item.cityCode)
        this.AreaNewArrayName = this.AreaNewArray[0].cityS.map(item => item.city)
        // 人员处理
        this.PersonneNewArrayCode = this.PersonneNewArray.map(item => item.PerCode)
        this.PersonneNewArrayName = this.PersonneNewArray.map(item => item.Personnel)
        if(this.AreaNewArrayName.length > 1){
          this.AreaNewArrayName = this.AreaNewArrayName.join(",");
        }else{
          this.AreaNewArrayName = this.AreaNewArrayName.join("")
        }
        if(this.PersonneNewArrayName.length > 1){
          console.log("人员判断",this.PersonneNewArrayName.length);

          this.PersonneNewArrayName = this.PersonneNewArrayName.join(",");
        }else{
          this.PersonneNewArrayName = this.PersonneNewArrayName.join("")
        }

      if(resnum != 0){
        // 判断主动选中的基因参数数量是否正确
        // 判断主动选中的基因参数数量是否正确
        if ($(".chooseRed").parents("li").length < 6 ) {
            this.$Modal.info({
              title: "提示",
              content: "必须选中6个或者6个以上基因座！"
            });
          return;
        }
        // if ($(".parting_box li").length != $(".chooseRed").parents("li").length) {
        //   this.$Modal.info({
        //     title: "提示",
        //     content: "有基因座未选择位点值"
        //   });
        //   return;
        // }
        // 拼接主动点击的参数
        this.allData = [];
        for (let i of this.result) {
          let data = {
            markerName: i.markerName,
            allele: []
          };
          for (let j of i.allele) {
            if (j.flag == true) {
              data.allele.push({ name: j.name });
            }
          }
          this.allData.push(data);
        }
        console.log(this.allData);
        let strAllData = JSON.stringify(this.allData);
        // 清空style???
        var oSpan = $(".parting_box p span");
        for (var i = 0; i < oSpan.length; i++) {
          oSpan[i].style.color = "";
        }
        if (this.mixSameCount == "") {
          this.$Message.error("匹配下限不能为空！");
          return;
        }
        // 参数赋值
        console.log(this.PersonneNewArrayName,'namename')
        storageInfo = {
          queueType:2,
          mixedSampleId:this.currentMixedId,
          sampleNo: this.currentSampleNo,//样本编号
          sex:JSON.stringify(this.personSex),//男女
          district:JSON.stringify(this.AreaNewArrayCode), //地区
          diqu:this.AreaNewArrayName,
          personType:JSON.stringify(this.PersonneNewArrayCode),//人员类型
          renyuan:this.PersonneNewArrayName,
          mixsameCount: this.LowerLimit,
          geneInfo: strAllData // 此处使用主动选中的参数
        };
        console.log(storageInfo);
        quickMatchServers.addLocusListInfoForkitId(storageInfo).then(res => {
          if(res.code == 1){
            // console.log(res)
            if(res.data.code == 0){
              this.$Modal.success({
                title: "提示",
                content: "加入比对成功！"
              });
              this.ComparisonTableData = res.data.compareQueueList
              this.Comtype = res.data.QueueFlag;
            }else if(res.data.code == 1){
              this.$Modal.error({
                  title: "提示",
                  content: "请勿重复添加！"
                });
            }
          }else{
            this.$Modal.error({
                title: "提示",
                content: "操作失败！"
            });
          }
        })
      }else{
        let strAllData = JSON.stringify(this.result);
        // 参数赋值
        storageInfo = {
          queueType:1,
          mixedSampleId:this.currentMixedId,
          sampleNo: this.currentSampleNo,//样本编号
          sex:JSON.stringify(this.personSex),//男女
          district:JSON.stringify(this.AreaNewArrayCode), //地区
          diqu:this.AreaNewArrayName,
          personType:JSON.stringify(this.PersonneNewArrayCode),//人员类型
          renyuan:this.PersonneNewArrayName,
          mixsameCount: this.LowerLimit,
          geneInfo: strAllData // 此处使用主动选中的参数
        };  
        quickMatchServers.addLocusListInfoForkitId(storageInfo).then(res => {
          console.log(res);
          if(res.code == 1){
            // console.log(res)
            if(res.data.code == 0){
              this.$Modal.success({
                title: "提示",
                content: "加入比对成功！"
              });
              this.ComparisonTableData = res.data.compareQueueList;
              this.Comtype = res.data.QueueFlag;
            }else if(res.data.code == 1){
              this.$Modal.error({
                  title: "提示",
                  content: "请勿重复添加！"
                });
            }
          }else{
            this.$Message.error("操作失败！")
          }
        })
      }
      // if (!this.hybrid_id) {
      //   this.$Modal.info({
      //     title: "提示",
      //     content: "请先拆分对比"
      //   });
      // } else {
      //   hybridServers.dataStorage(storageInfo).then(res => {
      //     if (res.code == 0) {
      //       this.$Modal.error({
      //         title: "提示",
      //         content: "入库失败!"
      //       });
      //     }
      //     if (res.data.code == 1) {
      //       this.$Modal.error({
      //         title: "提示",
      //         content: "请勿重复入库!"
      //       });
      //     } else if (res.data.code == 0) {
      //       this.$Modal.success({
      //         title: "提示",
      //         content: "入库成功！"
      //       });
      //     }
      //   });
      // }
    },
    // strMix拆分按钮
    handelBreak() {
      var list = {
        sampleId: this.sampleId,
        caseNo: this.caseNo
      };
      let hybridId = JSON.stringify(list);
      localStorage.setItem("hybridId", hybridId);
      localStorage.setItem("id", this.hybrid_id);
      this.$router.push("/break");
    },
    // handelStorageHBD() {
     
    //   let storageHBDInFO = {
    //     mixedSampleGeneId: this.hybrid_id
    //   };
    //   hybridServers.dataStorageHBD(storageHBDInFO).then(res => {
      
    //     if (res.code == 0) {
    //       this.$Modal.error({
    //         title: "提示",
    //         content: "入库失败!"
    //       });
    //     }
    //     if (res.data.code == 1) {
    //       this.$Modal.error({
    //         title: "提示",
    //         content: "请勿重复入库!"
    //       });
    //     } else if (res.data.code == 0) {
    //       this.$Modal.success({
    //         title: "提示",
    //         content: "入库成功！"
    //       });
    //     }
    //   });
    // },
        // 比对进度弹窗
        ScheduleModel_Show(){
            this.ScheduleModel = true;
        },
        // 比对进度弹出层 表格分页监听事件
        ScheduleChange(val){
            console.log(val)
        },
    // 提交比对
    handleSubmit(){
      console.log(this.Comtype)
      if(this.ComparisonTableData.length >0){
        let idArr = []
        let idStr = ''
        idArr = this.ComparisonTableData.map(item => item.id)
        idStr = idArr.join(',')
        // let info = {
        //   ids:idStr
          
        // }
      var compare = {
          ids:idStr,
          sampleNo:this.currentSampleNo,
          geneInfo:JSON.stringify(this.parGenIfo),
          mixedSampleId:this.currentMixedId
        }
        console.log(compare);
        // console.log(compareQueueModel)
          // console.log(info)
          quickMatchServers.updateLocusListInfoForId(compare).then( res => {
            console.log(res);
            console.log("进入update提交比对！");
                if (res.code == 1) {
                    this.$Modal.success({
                        title: "提示",
                         content: "提交比对成功！请在比对记录查看结果。"
                    });
                    this.SendComparisonModal = false;
                    this.Comtype = res.data.QueueFlag;
                    this.AreaNewArray = [];
                    this.PersonneNewArray = [];
                    this.LowerLimit = '';
                    this.ModelTwoOption = '';
                    // this.com_num();
                    // this.ScheduleData = res.data.resultList;
                    // this.ScheduleModel_Show();
                } else {
                    this.$Modal.error({
                        title: "提示",
                        content: "提交失败！"
                    });
                }
        })
      }else{
        this.$Message.error("暂无可提交的数据")
      }
      
    }
  }
};
</script>
<style lang="less" scoped>
@import "../../assets/styles/hybrud";
@import "../../assets/styles/scrollbar";
.chooseRed {
  color: red !important;
  font-size: 18px !important;
  font-weight: bold !important;
}
.subCol > ul > li {
  margin: 0 -18px;
  list-style: none;
  text-align: center;
  padding: 9px;
  border-bottom: 1px solid #ccc;
  overflow-x: hidden;
}
.subCol > ul > li:last-child {
  border-bottom: none;
}
/*此处样式会覆盖引入样式*/
.gwc_con{
  width: 100% !important;
  text-align: right;
  padding-right: 30px;
}
// .hybrid_middle_left li span{
//   font-size: 12px;
//   font-weight: normal;
// }
.footer_btn{
  text-align: right;
  button{
    padding: 8px 15px;
    margin: 10px 15px;
    background: #0c81f5;
    color: #fff;
    font-size: 14px;
    font-weight: bold;
    border: none;
    outline: none;
    border-radius: 4px;
    cursor: pointer;
  }
}
</style>
