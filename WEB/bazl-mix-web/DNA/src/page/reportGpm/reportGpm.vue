<template>
  <div>
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <router-link tag="b" to="/hybrid">混合型快速拆分对比</router-link>》
      <span>GPM报告拆分</span>
    </div>
    <div class="case_title">
      <div><Icon type="ios-square case_title_icon" />混合分型STR拆分</div>
    </div>
    <div class="break_box">
      <div class="break_content">
        <div class="break_box_title">
          <p>
            <Icon type="ios-list icon_name" />
            样本编号:{{ sampleNo }}
          </p>
          <p>
            <span></span>GPM报告文件
            <!-- :action="uploadUrl" api_iLabSTRmix/mix/main/strMixSplitReportFileList -->
            <Upload
              :action="uploadUrl"
              :show-upload-list="false"
              class="break_box_upload"
              :before-upload="beforUpload"
              :data="uploadData"
              :format="['pdf']"
              :name="'splitReportFiles'"
              :on-success="handelBreakSuccess"
              :headers="headers"
              :on-error="handUploadError"
            >
              <Button class="break_upload_btn">导入</Button>
            </Upload>
          </p>
          <p>
            <span></span>贡献者数量
            <button class="break_content_count">
              {{ this.breakList.contributorCount }}
            </button>
          </p>
          <div class="chai_btn" v-show="chaiShowBtn">
            <div class="posi-rel">
              <span @click="Details_show()">查看拆分样本并下一步</span>
              <div class="posi-abs">{{ Com_num_ber }}</div>
            </div>
          </div>
        </div>
        <div class="break_box_err" v-show="breakShow">
          <img src="../../assets/img/break_err.png" class="brear_err_img" />
          <br />
          <p>您好，目前暂无数据显示</p>
          <br />
          <p>
            您可先导入
            <span style="color:#0C81F5;">GPM拆分报告</span>后在进行查看
          </p>
        </div>
        <!-- 
                    <span>({{item.moreThanTheCountList[9].moreThanThevalue}})</span>
                    111
                    v-model="item.moreThanTheCountList[9].moreThanThevalue"
                    
                    <Option v-for="item of item.moreThanTheCountList" :value="item.moreThanThevalue" :key="item.percent">{{item.percent}}</Option>
                
        -->
        <div class="break_box_success" v-show="!breakShow">
          <!-- <div class="break_box_success_title">
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
            </div>
          </div> -->
          <div class="break_success_content">
            <ul class="break_success_text">
              <div class="break_box_text_title"><span></span>基因座名称</div>
              <li class="break_text_first">基因座</li>
              <div
                v-for="(item, index) of breakList.mixedSampleGeneList"
                :class="'break_text_li data_index' + index"
              >
                <li>{{ item.markerName }}</li>
              </div>
            </ul>
            <ul class="break_success_text1 break_success_parting">
              <div class="break_box_text_title"><span></span>混合分型</div>
              <li class="break_text_first">分型</li>
              <div
                v-for="(item, index) of breakList.mixedSampleGeneList"
                :class="'break_text_li data_index' + index"
              >
                <li>{{ item.geneStr1 }}</li>
              </div>
            </ul>

            <ul
              class="break_success_table"
              v-for="(item, index) of breakList.strMixRapiGeneComparisonListVos"
              :name="'break_success_table' + index"
            >
              <div class="break_success_table_title">
                <p>
                  <span></span>
                  {{ index + 1 }}号贡献者
                </p>
                <!-- <div class="abs-right">  （{{item.contributorWeight}}） -->
                <Button
                  type="primary"
                  @click="quickCheck('break_success_table' + index)"
                  >一键选中</Button
                >
                <Button
                  class="break_table_btn"
                  @click="chaifenruku('break_success_table' + index)"
                  :name="'break_success_table' + index"
                  >加入比对</Button
                >
                <!-- </div> -->
              </div>
              <li class="break_table_title_one">
                <p>
                  <Checkbox></Checkbox>
                </p>
                <p>等位基因</p>
                <p>权重</p>
                <p>权重≥99%</p>
              </li>
              <!-- 钉子 -->
              <div
                v-for="(items, index) in item.contributorGeneList"
                :class="'break_table_display data_index' + index"
              >
                <li v-for="(i, index) in items.geneAlleleList">
                  <!-- <Checkbox><Checkbox @on-change="changeGene(item,items,$event)" class="checkQuery" :indeterminate="indeterminate"></Checkbox></p> -->
                  <p>
                    <input
                      type="checkbox"
                      class="checkQuery"
                      name="checkQuery"
                    />
                  </p>
                  <p>{{ i.allele }}</p>
                  <p>{{ i.weights }}</p>
                  <p>{{ i.ninetyNinePercentWeights }}</p>
                  <!--<span class="oSpan">{{(index+1)*31}}</span>-->
                </li>
              </div>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!-- sureRuku(aa) -->
    <Modal class="model_one" v-model="geneListModal" @on-ok="sureRuku()">
      <p slot="header" style="text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>查看已选基因信息</span>
      </p>
      <div style="text-align:center">
        <Table :columns="geneListCol" :data="geneListData"></Table>
      </div>
    </Modal>
    <!-- LOADING ... -->
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
    <!-- 查看拆分样本记录 -->
    <Modal
      class="model_con_Style"
      v-model="SendComparisonModal"
      :closable="false"
      width="1200"
      height="500"
    >
      <div class="model-header">
        <span>比对列表</span>
      </div>
      <div class="model-body">
        <div class="title-div-style">
          <span></span>
          <span class="title-span-style">设置条件</span>
        </div>
        <div class="margin-left">
          <div class>
            <span class="title-span-style">性 &nbsp &nbsp &nbsp 别</span>
            <!-- 性别改成多选 -->
            <CheckboxGroup
              v-model="personSex"
              style="display:inline;"
              @on-change="changePersonSex"
            >
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
            </RadioGroup>-->
            <!-- <span class="marginStyle title-span-style">入库起止时间</span> -->
            <!-- :value="value2" -->
            <!-- <Date-picker
              @on-change="DateChange"
              format="yyyy/MM/dd"
              type="daterange"
              placement="bottom-end"
              placeholder="选择日期"
            ></Date-picker>-->
            <!-- <span class="marginStyle title-span-style">容差</span>
            <Select @on-change="OneOptionChange" v-model="ModelOneOption" style="width:80px">
              <Option v-for="item in cityListOne" :value="item.value" :key="item.value">{{item.label}}</Option>
            </Select>-->
            <span class="marginStyle title-span-style">容差</span>
            <Select
              @on-change="OneOptionChange"
              v-model="ModelOneOption"
              style="width:80px"
            >
              <Option
                v-for="item in cityListOne"
                :value="item.value"
                :key="item.value"
                >{{ item.label }}</Option
              >
            </Select>
            <span class="marginStyle title-span-style">匹配下限</span>
            <Select
              @on-change="TwoOptionChange"
              v-model="ModelTwoOption"
              style="width:80px"
            >
              <Option
                v-for="item in cityListTwo"
                :value="item.value"
                :key="item.value"
                >{{ item.label }}</Option
              >
            </Select>
            <!-- <span class="marginStyle title-span-style">匹配下限</span>
            <span class="input_style">
                <input type="text" v-model="ModelTwoOption"/>
           </span> -->
          </div>
          <div class="float-style clearfix">
            <div>
              <span class="title-span-style">人员类型</span>
              <span @click="PersonnelModel_Show" class="spanStyle">选择</span>
            </div>
            <div class="span-style Scrollbar">
              <span
                style="position:relative"
                v-for="(persItem, persIndex) in PersonneNewArray"
              >
                {{ persItem.Personnel }}
                <p class="span-abs">✔</p>
              </span>
              <!-- <span v-for="(spanItem,spanIndex) in citySitem.cityS">{{spanItem.city}}</span> -->
            </div>
          </div>
          <div class="float-style clearfix">
            <div>
              <span class="title-span-style">地 &nbsp &nbsp &nbsp 区</span>
              <span class="spanStyle" @click="Area_Show">选择</span>
            </div>
            <div class="span-style Scrollbar">
              <div v-for="(citySitem, CytisIde) in AreaNewArray">
                <!-- <span style="position:relative">
                  {{ citySitem.province }}
                  <p class="span-abs">✔</p>
                </span> -->
                <span
                  style="position:relative"
                  v-for="(spanItem, spanIndex) in citySitem.cityS"
                >
                  {{ spanItem.city }}
                  <p class="span-abs">✔</p>
                </span>
              </div>
            </div>
          </div>
        </div>
        <hr />
        <div>
          <div class="title-div-style">
            <span></span>
            <span class="title-span-style">快速拆分比对记录</span>
          </div>
          <div style="text-align:center;padding:10px;">
            <Table
              border
              stripe
              class="table-scroll"
              height="250"
              :columns="ComparisonTableCol"
              :data="ComparisonTableData"
            ></Table>
          </div>
        </div>
      </div>
      <div class="model-footer">
        <p>
          <span @click="SendComparisonParams">提交比对</span>
          <span @click="SendComparisonModal = false">取消</span>
        </p>
      </div>
      <p slot="footer"></p>
    </Modal>
    <!-- 地区选择 -->
    <Modal
      class="model_con_Style"
      v-model="AreaShow"
      width="900"
      :closable="false"
    >
      <div class="model-header">
        <span class>选择地区</span>
        <!-- <p class="all" @click="allCitys">全选</p> -->
      </div>
      <div class="model-Area-body">
        <div class="model-Area-ScrollBar Scrollbar">
          <ul>
            <!-- 省级城市 -->
            <li class="li-style" v-for="(AreaItem, AreaIndex) in AreaList">
              <div
                :class="{ active: AreaItem.flag == true }"
                @click="SelectArea(AreaIndex, AreaItem)"
              >
                <span class="span-1">✔</span>
                <span class="span-2">{{ AreaItem.province}}</span>
              </div>
            </li>
          </ul>
        </div>
        <!-- 地级城市 -->
        <div class="position-relative Scrollbar">
          <ul class="position-absolute">
            <li v-for="(cityItem, city_Index) in CitysList">
              <div
                class="active-div"
                @click="SelectCity(cityItem, city_Index)"
                :class="{ activeCity: cityItem.isChecked }"
              >
                <span class="span-3">{{ cityItem.regionalismName }}</span>
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
          <span @click="AreaShow = false">取消</span>
        </p>
      </div>
    </Modal>
    <!-- 人员弹窗 钉子2 -->
    <Modal
      class="model_con_Style"
      v-model="PersonnelModel"
      width="900"
      :closable="false"
    >
      <div class="model-header" style="position: relative;">
        <span class>人员选择</span>
        <!-- <p class="all" @click="allSelection">全选</p> -->
      </div>
      <div class="model-Area-body">
        <div class="model-Area-ScrollBar Scrollbar">
          <ul>
            <li class="li-style" v-for="(item, index) in PersonnelList">
              <div
                :class="{ active: item.flag == true }"
                @click="SelectPerson(index, item)"
              >
                <span class="span-1">
                  ✔
                </span>
                <span class="span-2">
                  {{ item.name }}
                </span>
              </div>
            </li>
          </ul>
        </div>
        <div class="position-relative Scrollbar">
          <ul class="position-absolute">
            <li v-for="(item, index) in personSecondList">
              <div
                class="active-div"
                :class="{ activeCity: item.isChecked }"
                @click="choosePerson(item, index)"
              >
                <span class="span-3">{{ item.dictValue1 }}</span>
                <span class="span-4">✔</span>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div class="model-footer">
        <p>
          <span @click="Perdetermine">确认</span>
          <span @click="PersonnelModel = false">取消</span>
        </p>
      </div>
    </Modal>
    <!-- 比对进度弹窗 -->
    <Modal
      class="model_con_Style"
      v-model="ScheduleModel"
      width="1000"
      :closable="false"
    >
      <div class="model-header">
        <span>比对进度</span>
      </div>
      <div class="schedule-body">
        <div class="schedule-title">
          <div class="title-child">
            <!-- <p>当前数据300万，预计比对时间30分钟。</p> -->
            <div>
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
              当前第{{ schedulePage }}页/共{{ scheduleCount }}页/共{{
                scheduleAllCount
              }}条信息
            </span>
            <Page
              :total="scheduleAllCount"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="schedulePage"
              @on-change="ScheduleChange"
              :page-size="15"
            />
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
import ModalDetail from "../../components/ModalDetail";
import breakServers from "../../servers/breakServers";
import hybridServers from "../../servers/hybridServers";
import serve from "../../servers/serve";
import { fcall } from "q";
export default {
  name: "breakBox",
  components: { ModalDetail },
  data() {
    return {
      schedulePage: 1, // 比对进度 表格页码
      scheduleCount: 1, // 比对进度 表格总页码
      scheduleAllCount: 0, // 比对进度 表格数据总条数
      // 比对进度弹窗
      ScheduleModel: false,
      // 比对进度表格标题
      ScheduleCol: [
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
          key: "ym"
          // width: "120"
        },
        // {
        //   title: "Y-STR",
        //   key: "yr",
        //   // width: "80"
        //   render: (h, params) => {
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
          key: "lx"
          // width: "80",
        },
        {
          title: "案件编号",
          key: "aj"
          // width: "120"
        },
        {
          title: "案件名称",
          key: "mc"
          // width: "120"
        },
        {
          title: "匹配位点个数",
          key: "pp"
          // width: "120"
        },
        {
          title: "容差个数",
          key: "rc"
          // width: "120"
        },
        {
          title: "操作",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage"
                },
                domProps: {
                  title: "查看详情"
                },
                on: {
                  click: event => {}
                }
              })
            ]);
          }
        }
      ],
      // 比对进度表格数据
      // ScheduleData:[],
      ScheduleData: [
        {
          yb: "CY-21932911-09009",
          ym: "XXXX",
          yr: "男",
          lx: "人员类型",
          aj: "Fy-220i4-kklow2",
          mc: "其他类别案件",
          pp: "2",
          rc: "1"
        },
        {
          yb: "CY-2459469-56666",
          ym: "XXXX",
          yr: "女",
          lx: "XXX类型",
          aj: "Fy-2www-567-3-",
          mc: "XXX案件类型",
          pp: "8",
          rc: "5"
        },
        {
          yb: "CY-2459469-56666",
          ym: "XXXX",
          yr: "女",
          lx: "XXX类型",
          aj: "Fy-2www-567-3-",
          mc: "XXX案件类型",
          pp: "8",
          rc: "5"
        },
        {
          yb: "CY-2459469-56666",
          ym: "XXXX",
          yr: "女",
          lx: "XXX类型",
          aj: "Fy-2www-567-3-",
          mc: "XXX案件类型",
          pp: "8",
          rc: "5"
        },
        {
          yb: "CY-2459469-56666",
          ym: "XXXX",
          yr: "女",
          lx: "XXX类型",
          aj: "Fy-2www-567-3-",
          mc: "XXX案件类型",
          pp: "8",
          rc: "5"
        },
        {
          yb: "CY-2459469-56666",
          ym: "XXXX",
          yr: "女",
          lx: "XXX类型",
          aj: "Fy-2www-567-3-",
          mc: "XXX案件类型",
          pp: "8",
          rc: "5"
        }
      ],
      // 查看详情 model 所需参数
      modalDetail: {
        modalType: 2,
        modalTitle: "",
        modalInfo: [],
        modalData: [],
        modalHeader1: "",
        modalHeader2: "",
        modalBigTitle: "查看比中详情",
        modalSplitMixBtn: "",
        BtnType: 9
      },
      map_num: 0,
      // 查看拆分样本并下一步 model
      SendComparisonModal: false,
      // 比对列表 表格数据
      ComparisonTableData: [],
      // 比对列表 表格标题
      ComparisonTableCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "sampleNo",
          width: "120"
        },
        {
          title: "分型",
          key: "",
          width: "80",
          render: (h, params) => {
            let type = "";
            if (params.row.queueType == 1) {
              type = "混合分型";
            } else if (params.row.queueType == 2) {
              type = "拆分分型";
            }
            return h(
              "span",
              {
                prop: {}
              },
              type
            );
          }
        },
        {
          title: "性别",
          key: "sex",
          width: "120px",
          render: (h, params) => {
            return h(
              "Select",
              {
                props: {
                  transfer: true,
                  size: "small",
                  multiple: true,
                  value: this.ComparisonTableData[params.index].sex
                },
                on: {
                  "on-change": event => {
                    this.ComparisonTableData[params.index].sex = event;
                  }
                }
              },
              this.sexList.map(item => {
                return h(
                  "Option",
                  {
                    props: {
                      value: item.value
                    }
                  },
                  item.name
                );
              })
            );
          }
        },
        {
          title: "地区",
          key: "diqu",
          render: (h, params) => {
            return h("div", { class: "renderTab" }, [
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
                      let index = params.index;
                      this.Area_Show(event, index, params);
                    }
                  }
                },
                "修改"
              ),
              h(
                "Tooltip",
                {
                  props: { placement: "right-end", transfer: true }
                },
                [
                  h(
                    "span",
                    {
                      style: {
                        display: "inline-block",
                        width: params.column._width * 0.5 + "px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap"
                      }
                    },
                    params.row.diqu
                  ),
                  h(
                    "span",
                    {
                      slot: "content",
                      style: { whiteSpace: "normal", wordBreak: "break-all" }
                    },
                    params.row.diqu
                  )
                ]
              )
            ]);
          }
        },
        {
          title: "人员类型",
          key: "renyuan",
          render: (h, params) => {
            return h("div",{ class: "renderTab" }, [
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
                      let index = params.index;
                      this.PersonnelModel_Show(event, index, params);
                    }
                  }
                },
                "修改"
              ),
              h(
                "Tooltip",
                {
                  props: { placement: "right-end", transfer: true }
                },
                [
                  h(
                    "span",
                    {
                      style: {
                        display: "inline-block",
                        width: params.column._width * 0.5 + "px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap"
                      }
                    },
                    params.row.renyuan
                  ),
                  h(
                    "span",
                    {
                      slot: "content",
                      style: { whiteSpace: "normal", wordBreak: "break-all" }
                    },
                    params.row.renyuan
                  )
                ]
              )
              // h(
              //   "span",
              //   {
              //     class: "edit_table",
              //     domProps: {
              //       title: params.row.renyuan
              //     }
              //   },
              //   params.row.renyuan
              // )
            ]);
          }
        },
        {
          title: "容差",
          key: "condition",
          width: "90",
          render: (h, params) => {
            return h(
              "Select",
              {
                props: {
                  transfer: true,
                  size: "small",
                  value: this.ComparisonTableData[params.index].condition
                },
                on: {
                  "on-change": event => {
                    this.ComparisonTableData[params.index].condition = event;
                  }
                }
              },
              this.cityListOne.map(item => {
                return h(
                  "Option",
                  {
                    props: {
                      value: item.value
                    }
                  },
                  item.label
                );
              })
            );
          }
        },
        {
          title: "匹配下限",
          key: "mixsameCount",
          width: "90",
          render: (h, params) => {
            return h(
              "Select",
              {
                props: {
                  transfer: true,
                  size: "small",
                  value: this.ComparisonTableData[params.index].mixsameCount
                },
                on: {
                  "on-change": event => {
                    this.ComparisonTableData[params.index].mixsameCount = event;
                  }
                }
              },
              this.cityListTwo.map(item => {
                return h(
                  "Option",
                  {
                    props: {
                      value: item.value
                    }
                  },
                  item.label
                );
              })
            );
          }
        },
        {
          title: "查看详情",
          key: "action",
          width: "100",
          render: (h, params) => {
            return h(
              "Button",
              {
                props: {
                  type: "warning",
                  size: "small"
                },
                on: {
                  click: event => {
                    let index = params.index;
                    let idInfo = {
                      id: params.row.id
                    };
                    hybridServers.SeeDetails(idInfo).then(res => {
                      if (res.code == 1) {
                        this.$refs.modal.modalShow = true;
                        // this.modalDetail.modalTypeHeader = "混合样本分型";
                        if (params.row.queueType == 1) {
                          this.modalDetail.modalTypeHeader = "混合样本分型";
                        } else if (params.row.queueType == 2) {
                          this.modalDetail.modalTypeHeader = "拆分样本分型";
                        }
                        this.modalDetail.modalInfo =
                          res.result.GeneMap.resultList;
                        this.modalDetail.modalTitle = params.row.sampleNo;
                        this.modalDetail.modalData = res.result;
                      } else {
                        this.$Message.error("操作失败!");
                      }
                    });
                  }
                }
              },
              "查看"
            );
          }
        },
        {
          title: "操作",
          key: "action",
          width: "80",
          render: (h, params) => {
            return h(
              "Button",
              {
                props: {
                  type: "error",
                  size: "small"
                },
                on: {
                  click: event => {
                    let idInfo = {
                      id: params.row.id
                    };
                    hybridServers.Datequeue(idInfo).then(res => {
                      if (res.code == 1) {
                        this.ComparisonTableData.splice(params.index, 1);
                        this.com_num();
                      } else {
                        this.$Message.error("删除失败");
                      }
                    });
                  }
                }
              },
              "删除"
            );
          }
        }
      ],
      // 样本编号
      sampleNo: "",
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
      // 样本编号
      sampleNo: "",
      showstyle: false,
      SeeDetailsModel: false,
      SeeDetailsCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "基因座",
          key: "markerName" //
        },
        {
          title: "拆分等位基因",
          key: "allele" //
        }
      ],
      SeeDetailsData: [],
      chaiShowBtn: false,
      geneListModal_two: false,
      geneListCol_two: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "检材名称",
          key: "sampleName",
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
                      breakServers.deleteSpli(Idinfo).then(res => {
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
      Id: "",
      geneListData_two: [],
      FileName: "",
      loadingShow: false,
      Model: "",
      // spanValue:"",
      genneId: "",
      value: "",
      breakShow: true,
      uploadData: {},
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
      geneListModal: false,
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
      geneListData: [],
      checkTrueFalse: false,
      indeterminate: false,
      idRec: "", // 快速比对记录 所需参数
      personSex: [],
      recordingId: "", // 快速比对记录接口 所需参数
      AreaNewArray: [], // 地区条件渲染参数 html
      PersonneNewArray: [], // 人条件渲染参数 html
      CitysInfo: "", // 省级城市ID，根据此条件查询省级下面地级市
      // RadioType:"", // 性别 单选
      // personSex: ["1", "2"], // 多选
      LowerLimit: "", // 匹配下限
      Tolerance: "", // 容差
      DateArray: [], // 时间
      // 地级市数据
      CitysList: [],
      // 人员条件数据
      PersonnelList: [],
      // 人员条件弹窗
      PersonnelModel: false,
      // 地区下标初始值
      Cityindex: -1,
      // 城市下标初始值
      activeIndex: 0,
      //地区选择弹窗
      AreaShow: false,
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
          value: "1",
          label: "1"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "3",
          label: "3"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "5",
          label: "5"
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
        }
      ],
      // 地区列表
      AreaList: [],
      personTypeCode: [], //比对列表中保存人员类型的code值
      personTypeName: [], //比对列表中保存人员类型的name值
      districtCode: [], //比对列表中保存地区的code值
      districtName: [], //比对列表中保存地区的name值
      tablePersonIndex: "", //比对列表中人员index
      tableAreaIndex: "",
      tableAreaBoolean: false,
      tablePersonBoolean: false,
      Com_num_ber: 0,
      // 表格中渲染男女
      sexList: [
        {
          value: "1",
          name: "男"
        },
        {
          value: "2",
          name: "女"
        }
      ],
      headers: {
        Authorization: ""
      },
      pdfGeneInfo: "",
      mix_id: "",
      initGeneInfo: [],
      reagentName: "", //试剂盒名称
      sampleName: "", // 样本名称
      numCON: "",
      personSecondList: [],
      citysIndex: 0
      // 混合样本  ID
      // mixedSampleId:"",
    };
  },
  created() {
    this.breakSampleId = JSON.parse(localStorage.getItem("hybridId"));
    this.idRec = localStorage.getItem("id");
    this.headers.Authorization = localStorage.getItem("token");
    this.sampleNo = localStorage.getItem("sampleNo");
    this.reagentName = localStorage.getItem("reagentName");
    this.sampleName = localStorage.getItem("sampleName");
    this.pdfGeneInfo = localStorage.getItem("genInfo");
  },
  mounted() {},
  updated() {
    var _this = this;
    var geneListInfo;
    $(".break_success_table .break_table_display .checkQuery").click(
      function() {
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
      if (this.map_num == 0) {
        return;
      }
      this.breakList.mixedSampleGeneList.map((item, index) => {
        var css = $(".data_index" + index);
        this.cssHeight(css);
      });
    }
  },
  methods: {
    // 比中条数
    com_num() {
      let mix = {
        mixedSampleId: this.idRec
      };
      hybridServers.SelectCount(mix).then(res => {
        this.Com_num_ber = res.result.count;
      });
    },
    // 设置条件中男女
    changePersonSex(params) {
      this.personSex = params;
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.sex = this.personSex;
        return item;
      });
    },
    // 匹配下限监听事件
    TwoOptionChange(val) {
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.mixsameCount = val;
        return item;
      });
    },
    // 弹窗取消事件
    DisplayModal() {
      this.SendComparisonModal = false;
      this.PersonneNewArray = [];
      this.AreaNewArray = [];
    },
    // 人员条件选择 弹窗
    PersonnelModel_Show(event, index, params) {
      this.tablePersonIndex = "";
      this.tablePersonBoolean = false;
      if (index) {
        this.tablePersonBoolean = true;
        this.tablePersonIndex = index;
      }
      if (index == 0) {
        this.tablePersonBoolean = true;
        this.tablePersonIndex = index;
      }
      this.PersonnelModel = true;
      hybridServers.GetPersonnelData().then(res => {
        res.result.map((item, index) => {
          item.id = index;
          item.flag = false;
          item.val.map(val => {
            val.id = index;
            val.isChecked = false;
          });
        });
        this.PersonnelList = res.result;
        if (this.tablePersonBoolean) {
          this.PersonnelList.map(i => {
            i.val.map(k => {
              params.row.personType.map(j => {
                if (k.dictKey == j) {
                  i.flag = true;
                  this.PersonIndex = i.id;
                  this.personSecondList = i.val;
                  k.isChecked = true;
                }
              });
            });
          });
        } else {
          this.PersonIndex = 0;
          this.PersonnelList[0].flag = true;
          this.personSecondList = this.PersonnelList[0].val;
          this.personSecondList.forEach(item => {
            item.isChecked = true;
          });
        }
        this.PersonnelList = [...this.PersonnelList];
      });
    },
    // 人员一级选择
    SelectPerson(id, item) {
      if (item.flag) {
        item.flag = false;
        item.val.forEach(k => {
          k.isChecked = false;
        });
      } else {
        item.flag = true;
        item.val.forEach(k => {
          k.isChecked = true;
        });
      }
      this.PersonIndex = id;
      this.PersonnelList.forEach(item => {
        if (item.id == id) {
          this.personSecondList = item.val;
        }
      });
    },
    // 人员单选
    choosePerson(item, index) {
      this.PersonnelList.forEach(item => {
        item.val.forEach(k => {
          if (k.dictKey == item.dictKey) {
            this.PersonIndex = item.id;
          }
        });
      });
      // this.PersonnelList[this.PersonIndex].val[index]
      let i = this.PersonnelList[this.PersonIndex].val[index];
      if (i.isChecked) {
        i.isChecked = false;
      } else {
        i.isChecked = true;
      }
      // if (item.isChecked) {
      //   item.isChecked = false;
      // } else {
      //   item.isChecked = true;
      // }
    },
    // 人员条件 全选
    allSelection() {
      var allNum = 0; // 根据此值判断 数据中的flag装态
      for (let a = 0; a < this.PersonnelList.length; a++) {
        if (this.PersonnelList[a].flag == true) {
          /*
              为allNum赋值，如果flag存在true的状态那就 +1,
              最后在第二次循环中判断如果此值为0就代表当前flag全为false的状态，将flag改为true
              如果此值等于数据组的长度就代表当前flag全为true的状态，将flag改为false.
              如果不等于0,也不等于数据组的长度就代表当前flag的状态true和flag都存在，将flag统一为true
            */

          allNum += 1;
        }
      }
      // // 两层循环是因为第一层循环中程序还没走完。
      for (let i = 0; i < this.PersonnelList.length; i++) {
        if (allNum == 0) {
          this.PersonnelList[i].flag = true;
        } else if (allNum == this.PersonnelList.length) {
          this.PersonnelList[i].flag = false;
        } else {
          this.PersonnelList[i].flag = true;
        }
      }
      this.PersonnelList.forEach(item => {
        if (item.flag) {
          item.val.forEach(k => {
            k.isChecked = true;
          });
        } else {
          item.val.forEach(k => {
            k.isChecked = false;
          });
        }
      });
    },
    // 人员选择确认按钮 钉子5
    Perdetermine() {
      this.PersonnelModel = false;
      this.PersonneNewArray = []; // 页面人员条件渲染参数
      this.PersonnelList.forEach(i => {
        i.val.forEach(k => {
          if (k.isChecked) {
            this.PersonneNewArray.push({
              Personnel: k.dictValue1,
              PerCode: k.dictKey,
              id: k.id
            });
          }
        });
      });
      let name = [];
      let code = [];
      this.PersonneNewArray.map(item => {
        name.push(item.Personnel);
        code.push(item.PerCode);
      });
      if (this.tablePersonBoolean) {
        this.ComparisonTableData[this.tablePersonIndex].renyuan = name.join(
          ","
        );
        this.ComparisonTableData[this.tablePersonIndex].personType = code;
      } else {
        this.ComparisonTableData.map(item => {
          item.renyuan = name.join(",");
          item.personType = code;
        });
      }
      this.ComparisonTableData = [...this.ComparisonTableData];
    },
    // 比对进度弹窗
    ScheduleModel_Show() {
      this.ScheduleModel = true;
    },
    // 比对进度弹出层 表格分页监听事件
    ScheduleChange(val) {},
    // 提交比对参数
    SendComparisonParams() {
      for (let item of this.ComparisonTableData) {
        if (
          item.sex == null ||
          item.personType == null ||
          item.district == null ||
          item.condition == null
        ) {
          this.$Modal.error({
            title: "提示",
            content: "请在设置条件中设置选项~"
          });
          return false;
        } else {
          item.sex = JSON.stringify(item.sex);
          item.personType = JSON.stringify(item.personType);
          item.district = JSON.stringify(item.district);
        }

        item.geneInfo = JSON.parse(item.geneInfo);
        item.geneInfo = JSON.stringify(item.geneInfo);
        item.renyuan = item.renyuan; //JSON.stringify()
        item.diqu = item.diqu; //JSON.stringify()
      }
      var compareQueueModel = JSON.stringify({
        compareQueueList: this.ComparisonTableData
      });
      serve.updateLocusListInfoForId(compareQueueModel).then(res => {
        if (res.code == 1) {
          this.$Modal.success({
            title: "提示",
            content: "提交比对成功！请在比对记录查看结果。"
          });
          this.SendComparisonModal = false;
          this.com_num();
        } else {
          this.$Modal.error({
            title: "提示",
            content: "出错了"
          });
        }
      });
      return;
    },
    // 地区选择框点击事件
    Area_Show(event, index, params) {
      this.tableAreaIndex = "";
      this.tableAreaBoolean = false;
      if (index) {
        this.tableAreaIndex = index;
        this.tableAreaBoolean = true;
      }
      if (index == 0) {
        this.tableAreaIndex = index;
        this.tableAreaBoolean = true;
      }
      // 弹窗
      this.CitysInfo = {
        code: ""
      };
      this.AreaShow = true;
      // 获取省级参数
      hybridServers.GetCitysData(this.CitysInfo).then(res => {
        if (res.code == 1) {
          res.data.forEach((item, index) => {
            item.id_code = index;
            item.flag = false;
            item.city.forEach(val => {
              val.id_code = index;
              val.isChecked = false;
            });
          });
          this.AreaList = res.data;
          if (this.tableAreaBoolean) {
            this.AreaList.forEach(i => {
              i.city.forEach(k => {
                params.row.district.map(j => {
                  if (k.regionalismCode == j) {
                    i.flag = true;
                    this.citysIndex = i.id_code;
                    this.CitysList = i.city;
                    k.isChecked = true;
                  }
                });
              });
            });
          } else {
            this.citysIndex = 0;
            this.AreaList[0].flag = true;
            this.CitysList = this.AreaList[0].city;
            this.CitysList.forEach(item => {
              item.isChecked = true;
            });
          }
          this.AreaList = [...this.AreaList];
        }
      });
    },
    // 省级城市切换 一级选择
    SelectArea(index, item) {
      if (item.flag) {
        item.flag = false;
        item.city.forEach(k => {
          k.isChecked = false;
        });
      } else {
        item.flag = true;
        item.city.forEach(k => {
          k.isChecked = true;
        });
      }
      this.citysIndex = index;
      this.AreaList.forEach(item => {
        if (item.id_code == index) {
          this.CitysList = item.city;
        }
      });
    },
    // 地级城市选择 二级选择
    SelectCity(item, index) {
      let i = this.AreaList[this.citysIndex].city[index];
      if (i.isChecked) {
        i.isChecked = false;
      } else {
        i.isChecked = true;
      }
    },
    // 地区条件 确认按钮 将设置的区域条件渲染在提交版块
    SendParamsToSubmitModel() {
      this.AreaShow = false;
      this.AreaNewArray = [];
      let serveNo = [];
      let name = [];
      let code = [];
      this.AreaList.forEach(i => {
        if (i.flag) {
          serveNo.push(i.personlism.regionalismCode);
        }
        i.city.forEach(k => {
          if (k.isChecked) {
            this.AreaNewArray.push({
              city: k.regionalismName,
              cityCode: k.regionalismCode,
              id: k.id_code
            });
          }
        });
      });
      this.AreaNewArray.map(item => {
        name.push(item.city);
        code.push(item.cityCode);
      });
      if (this.tableAreaBoolean) {
        this.ComparisonTableData[this.tableAreaIndex].diqu = name.join(",");
        this.ComparisonTableData[this.tableAreaIndex].district = code;
        this.ComparisonTableData[this.tableAreaIndex].serveNo = serveNo.join(
          ","
        );
      } else {
        this.ComparisonTableData.map(item => {
          item.diqu = name.join(",");
          item.district = code;
          item.serveNo = serveNo.join(",");
        });
      }
      this.ComparisonTableData = [...this.ComparisonTableData];
    },
    // 地区全选
    allCitys() {
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
    // 容差监听事件
    OneOptionChange(val) {
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.condition = val;
        return item;
      });
    },
    // 单选框监听事件
    RadioChange(val) {
      this.RadioType = val;
    },
    // 选择日期监听事件
    DateChange(val) {
      this.DateArray = val;
    },
    // 查看拆分样本并下一步 model
    Details_show() {
      this.SendComparisonModal = true;
      this.PersonneNewArray = [];
      this.AreaNewArray = [];
      this.ModelOneOption = "";
      this.ModelTwoOption = "";
      let viewInfo = {
        mixedSampleId: this.idRec
      };
      hybridServers.ViewAndNext(viewInfo).then(res => {
        this.ComparisonTableData = res.result;
      });
    },
    // 下拉框监听事件 发生改变时，返回当前选中的参数 例：‘label:95%’, vlaue:10,label代表当前选中了百分之多少，
    // vlaue代表有多少个参数大于等于95%
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
      switch (item.percent) {
        case "90%":
          contrast = JSON.stringify(item.moreGeneList0);
          break;
        case "91%":
          contrast = JSON.stringify(item.moreGeneList1);
          break;
        case "92%":
          contrast = JSON.stringify(item.moreGeneList2);
          break;
        case "93%":
          contrast = JSON.stringify(item.moreGeneList3);
          break;
        case "94%":
          contrast = JSON.stringify(item.moreGeneList4);
          break;
        case "95%":
          // 参数改动
          contrast = JSON.stringify(item.moreGeneList5);
          break;
        case "96%":
          contrast = JSON.stringify(item.moreGeneList6);
          break;
        case "97%":
          contrast = JSON.stringify(item.moreGeneList7);
          break;
        case "98%":
          contrast = JSON.stringify(item.moreGeneList8);
          break;
        default:
        case "99%":
          contrast = JSON.stringify(item.moreGeneList9);
          break;
      }
      if (item.percent == null) {
        item.percent = "99%";
        percent = item.percent;
      } else if (item.percent) {
        percent = item.percent;
      }
      sessionStorage.setItem("contrast", contrast);
      sessionStorage.setItem("percent", percent);
      this.$router.push({
        name: "Contrast",
        params: { fileName: this.FileName, Contributor: index + 1 }
      });
    },
    // 上传失败
    handUploadError(error) {},
    //上传成功
    handelBreakSuccess(response, file) {
      if (response.code == 1) {
        this.loadingShow = false;
        this.chaiShowBtn = true;
        this.map_num = 1;
        this.FileName = file.name;
        this.breakList = response.result;
        this.breakShow = false;
        this.type = 2;
        // this.genneId = response.result.strMixRapiGeneComparisonListVos[0].geneId;
        let geneData = response.result.mixedSampleGeneList;
        this.initGeneInfo = [];
        for (let item of response.result.mixedSampleGeneList) {
          let arr = item.geneStr1.split(",");
          let obj = {
            markerName: item.markerName,
            allele: []
          };
          for (let data of arr) {
            obj.allele.push({ name: data });
          }
          this.initGeneInfo.push(obj);
        }
        let x = 0;
        for (let item of geneData) {
          let a = item.geneStr1.split(",");
          if (a.length > 4) {
            x += 1;
          }
        }
        if (x != 0) {
          this.numCON = "3";
        } else {
          this.numCON = "2";
        }
        this.com_num();
      } else {
        this.loadingShow = false;
        this.chaiShowBtn = false;
      }
    },
    // breakList //文件上传之前
    beforUpload(files) {
      const FileExt = files.name.replace(/.+\./, ""); //取得文件的后缀名
      if (FileExt != "pdf") {
        this.$Message.error("当前只支持上传PDF格式文件!");
        return false;
      } else {
        this.map_num = 0;
        this.breakList = [];
        this.loadingShow = true;
        this.uploadUrl = "api_iLabSTRmix/mix/report/gpmMixSplitReportFileList";
        this.uploadData = {
          geneInfo: this.pdfGeneInfo
        };
        let promise = new Promise(resolve => {
          this.$nextTick(function() {
            resolve(true);
          });
        });
        return promise;
      }
    },
    // 一键选中点击事件  钉子
    quickCheck(par) {
      var zz = par.substring(par.length - 1);
      let that = this;
      var genefirstInfo;
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
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", false);
            that["geneList" + zz] = [];
          }
        });
    },
    //
    chaifenruku(par) {
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
    // 加入比对 确定按钮
    sureRuku() {
      let comparisonInfo = {
        geneInfo: JSON.stringify(this.geneListData),
        queueType: 2,
        sampleNo: this.sampleNo,
        mixedSampleId: this.idRec,
        sampleName: this.sampleName,
        mixedSampleGene: {
          contributorCount: this.numCON,
          sampleId: this.breakSampleId.sampleId,
          sampleNo: this.sampleNo,
          sampleName: this.sampleName,
          reagentName: this.reagentName,
          geneInfo: JSON.stringify(this.initGeneInfo),
          genePicture: null
        }
      };
      let info = JSON.stringify(comparisonInfo);
      breakServers.dataComparison(info).then(res => {
        if (res.code == 0) {
          this.$Modal.error({
            title: "提示",
            content: "操作失败!"
          });
        }
        if (res.result.code == 1) {
          this.$Modal.error({
            title: "提示",
            content: "请勿重复添加!"
          });
        } else if (res.result.code == 0) {
          this.$Modal.success({
            title: "提示",
            content: "加入比对成功！"
          });
          this.com_num();
        }
      });
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/title";
@import "../../assets/styles/break";
@import "../../assets/styles/hybrud";
@import "../../assets/styles/scrollbar";
</style>
