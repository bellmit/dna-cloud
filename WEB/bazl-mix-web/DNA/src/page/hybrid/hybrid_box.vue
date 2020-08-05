<template>
  <div style="margin-bottom:50px;">
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link>》
      <span>混合型快速拆分比对</span>
    </div>
    <div v-if="messageShow" class="hybrid_box">
      <div class="hybrid_box_message">混合基因样本为空，不能进行分析比对</div>
    </div>
    <div class="hybrid_box" v-else style="padding-top:10px">
      <!-- <div class="hybrid_box_title">
        <Icon type="ios-square case_title_icon" />混合型快速拆分比对
      </div>-->
      <div class="hybrid_box_middle">
        <div class="hybrid_middle_left Scrollbar">
          <h5>混合样本</h5>
          <li v-for="(item, index) in this.leftTitle" :key="index">
            <div
              :class="{ leftBarStyle: leftBarClass == index }"
              @click="handelHybridData(item, index)"
            >
              <p>
                <Icon type="md-home hybrid_left" />
              </p>
              <span>{{ item.mixedSampleGeneVo.sampleNo }}</span>
              <span
                v-if="item.mixedSampleGeneVo.flag == 0"
                class="hybrid-M-span"
                >M</span
              >
              <strong v-else class="suss-span">
                <img src="../../assets/img/suss.png" alt="" />
                已处理
              </strong>
            </div>
          </li>
        </div>
        <div class="hybrid_middle_right">
          <div class="hybrid_parting">
            <div class="hybrid_parting_left">
              <div class="hybrid_parting_left_title">
                <p></p>
                混合样本峰图
              </div>
              <div class="Img_con_ft">
                <div class="img-rel" v-if="hybridImg">
                  <div class="imgSuccesStyle">
                    <img
                      class="img_success"
                      @click="hybridImgModal = true"
                      :src="hybridImg"
                      alt="图片加载失败"
                    />
                  </div>
                  <!-- SplitImgPath(this.hybridImg) -->
                  <span
                    title="删除峰图"
                    class="img-absi"
                    @click="handleRemovePic"
                    >X</span
                  >
                </div>
                <div v-else>
                  <div class="imgErrorStyle">
                    <p>
                      <img
                        class="img_error"
                        @click="hybridImgModal = true"
                        :src="defaultImg"
                        alt
                      />
                    </p>
                    <p>您好，目前暂无数据显示</p>
                    <p>您可尝试其他操作</p>
                  </div>
                  <div class="upload-con">
                    <Upload
                      ref="upload"
                      :format="['jpg', 'jpeg', 'png']"
                      :before-upload="handleBeforeUpload"
                      name="file"
                      multiple
                      type="drag"
                      class="fileBtnstyle"
                      action="api_iLabSTRmix/mix/main/uploadGenePicture"
                      :on-success="handelBreakSuccess"
                      :headers="headers"
                      :data="uploadData_ft"
                    >
                      <div class="fileBtnstyle">手动上传峰图</div>
                    </Upload>
                  </div>
                </div>
              </div>
            </div>
            <div class="hybrid_parting_right">
              <div class="hybrid_parting_right_title">
                <div>
                  <span> <span></span>混合分型 </span>
                  <!-- <span class="input_style" style="color:rgb(255, 90, 86)!important;">
                    最少拆分位点个数：
                    <input
                      style="color:rgb(255, 90, 86)!important;"
                      type="text"
                      v-model="mixCount"
                    />
                  </span>-->
                </div>
                <div class="parting_right">
                  <!-- <span @click="onStrMix">启动STRmix</span> -->
                  <span @click="handelBreak(0)">GPM报告导入</span>
                  <span @click="handelBreak(1)">STRmix报告导入</span>
                  <!-- <p @click="onStrMix">
                    启动STRmix
                  </p>-->
                  <!-- <p @click="handelBreak" style="color:rgba(12,129,245,1) !important">
                    <img src="../../assets/img/7710.png" />STR MIX拆分
                  </p>-->
                  <!-- <p>|</p> -->
                  <!-- <span @click="handelStorageHBD">
                    <img src="../../assets/img/712.png" />入混合库
                  </span>-->
                </div>
              </div>
              <ul class="parting_box Scrollbar">
                <li v-for="(item, i) of this.result" :key="i">
                  <span>{{ item.markerName }}</span>
                  <p>
                    <!-- grayColor 原背景色 -->
                    <span
                      v-for="(items, index) of item.allele"
                      @click="handelChoose(items, item, $event, i, index)"
                      :class="[items.identification == 0 ? chooseRed : '']"
                      >{{ items.name
                      }}{{ index == item.allele.length - 1 ? "" : "," }}</span
                    >
                  </p>
                </li>
              </ul>
              <div class="borderStyle_gwc">
                <div class="gwc_con clearfix">
                  <span>
                    <button class="parting_button" @click="handelStorage(1)">
                      不拆分直接比对
                    </button>
                  </span>
                  <span class="span-cf">
                    <button class="parting_button" @click="handelStorage(2)">
                      拆分样本加入比对
                    </button>
                  </span>
                  <span class="span-ck">
                    <button class="parting_button" @click="Details_show">
                      查看拆分样本并下一步
                    </button>
                    <p>{{ Com_num }}</p>
                  </span>
                </div>
              </div>
            </div>
          </div>
          <div class="hybrid_middle_table_bottom"></div>
          <!--原表格  -->
          <div class="h5_title clearfix">
            <div>
              <p></p>
              本案可疑来源样本
              <span style="color:rgba(255,90,86,1) !important">比中总数:</span>
              <span class="span_" style="color:rgba(255,90,86,1) !important">{{
                total
              }}</span>
              <!-- <span>匹配下限:{{mixSameCount}}</span> -->
              <!-- <span class="input_style" style="color: rgb(255, 170, 6) !important;">匹配下限：<input type="text" v-model="mixSameCount"></span> -->
            </div>
            <!-- <div>
              <span>开始比对</span>
            </div>-->
          </div>
          <div class="hybrid_middle_right_tables">
            <Table
              class="right-table-Style table-scroll light-blue-table"
              border
              stripe
              size="small"
              :columns="hybrid_head"
              :data="hybridList"
              height="300"
            ></Table>
          </div>
        </div>
      </div>
    </div>
    <Modal v-model="hybridImgModal" width="1104">
      <div>
        <!-- 原代码  <img :src="hybridImg?hybridImg:defaultImg" style="width:100%;" /> -->
        <img :src="hybridImg ? hybridImg : defaultImg" style="width:100%;" />
      </div>
      <p slot="footer"></p>
    </Modal>
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
    <!-- 查看拆分样本弹窗 -->
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
                <Radio label="2" class size="large">
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
              <Option
                v-for="item in cityListOne"
                :value="item.value"
                :key="item.value"
              >{{item.label}}</Option>
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
              <div>
                <span
                  style="position:relative"
                  v-for="(spanItem, spanIndex) in AreaNewArray"
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
          <span @click="DisplayModal">取消</span>
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
        <!-- <span>选择地区</span> -->
        <span class>选择地区</span>
        <!-- <p class="all" @click="allCitys">全选</p> -->
      </div>
      <div class="model-Area-body">
        <div class="model-Area-ScrollBar Scrollbar">
          <ul>
            <!-- 省级城市 -->
            <li class="li-style" v-for="(AreaItem, AreaIndex) in AreaList">
              <!-- @click="SelectArea(AreaIndex,AreaItem)" 当前只有吉林省，去掉地区切换事件，全国地区时需在下面div中加上此事件 ，2020-1-19 -->
              <div
                :class="{ active: AreaItem.flag == true }"
                @click="SelectArea(AreaIndex, AreaItem)"
              >
                <!-- :class="activeClass == AreaIndex ? 'active':''" -->
                <span class="span-1">✔</span>
                <span class="span-2">{{ AreaItem.province }}</span>
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
    <ModalDetail ref="modal" :modalDetail="modalDetail"></ModalDetail>
  </div>
</template>
<script>
import hybridServers from "../../servers/hybridServers";
import ModalDetail from "../../components/ModalDetail";
import homeServers from "../../servers/homeServer";
import userServers from "../../servers/userServers";
import caseServer from "../../servers/caseServer";
import serve from "../../servers/serve";
import { fcall } from "q";

export default {
  name: "hybridBox",
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
        modalTitle: "",
        modalInfo: [],
        modalData: [],
        // modalHeader1: "",
        // modalHeader2: "",
        modalBigTitle: "查看比中详情",
        modalSplitMixBtn: ""
      },
      schedulePage: 1, // 比对进度 表格页码
      scheduleCount: 1, // 比对进度 表格总页码
      scheduleAllCount: 0, // 比对进度 表格数据总条数
      // 比对进度弹窗
      ScheduleModel: false,
      // 比对进度表格标题 弹出层
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
      // 比中条数
      Com_num: 0,
      // 地级城市数据
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
      //
      showstyle: false,
      // 地区列表
      AreaList: [],
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
          key: "queueType",
          width: "80",
          render: (h, params) => {
            let type = "";
            if (params.row.queueType == 1) {
              type = "混合";
            } else if (params.row.queueType == 2) {
              type = "拆分";
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
                        this.modalDetail.modalType = 2;
                        this.modalDetail.BtnType = 9;
                        if (params.row.queueType == "1") {
                          this.modalDetail.modalTypeHeader = "混合样本分型";
                        } else {
                          this.modalDetail.modalTypeHeader = "拆分样本分型";
                        }
                        // this.modalDetail.modalTypeHeader = "混合样本分型";
                        this.modalDetail.modalInfo =
                          res.result.GeneMap.resultList;
                        this.modalDetail.modalTitle = params.row.sampleNo;
                        this.modalDetail.modalData = res.result;
                      } else {
                        this.$Message.error("操作失败！");
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
                        this.$Message.success("删除成功！");
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
      loadingShow: false, //  loading model
      btnType_true: true,
      ListArrData: [],
      ListNumType: 0,
      grayColor: "grayColor",
      chooseRed: "chooseRed",
      leftBarClass: "",
      sampleName: "", // 样本名称
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
                  click: event => {}
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
                    click: event => {}
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
          key: "sampleCount"
        },
        {
          title: "比中数",
          key: "sameCount"
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
                    this.modalDetail.modalType = 0;
                    this.modalDetail.modalTitle = params.row.sampleNo;
                    this.modalDetail.modalHeader1 = "混合样本等位基因";
                    this.modalDetail.modalHeader2 = "比中样本等位基因";
                    this.modalDetail.modalSplitMixBtn = "混合";
                    this.modalDetail.modalInfo =
                      params.row.newGeneMap.resultList;
                    this.modalDetail.modalData = params.row;
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
      result: [],
      hybridImg: "",
      defaultImg: require("../../assets/img/break_err.png"),
      // defaultImg: require("../../assets/img/feng.jpg"),
      hybridImgModal: false,
      leftTitle: [],
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
      AreaNewArray: [], // 地区条件渲染参数 html
      PersonneNewArray: [], // 人条件渲染参数 html
      CitysInfo: "", // 省级城市ID，根据此条件查询省级下面地级市
      // RadioType:"", // 性别 单选
      personSex: [], // 多选
      LowerLimit: "", // 匹配下限
      Tolerance: "", // 容差
      DateArray: [], // 时间
      personTypeCode: [], //比对列表中保存人员类型的code值
      personTypeName: [], //比对列表中保存人员类型的name值
      districtCode: [], //比对列表中保存地区的code值
      districtName: [], //比对列表中保存地区的name值
      tablePersonIndex: "", //比对列表中人员index
      tableAreaBoolean: false,
      tablePersonBoolean: false,
      tableAreaIndex: "",
      newArry: [],
      mixEntityGeninfo: "",
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
      parGeninfo: [],
      mixCount: "",
      allListObj: {},
      reagentName: "",
      uploadData_ft: {
        id: ""
      },
      headers: {
        Authorization: ""
      },
      contributorCount: "",
      arrList: [],
      personSecondList: [],
      citysIndex:0,
      // this.AreaNewArrayCode = this.AreaNewArray[0].cityS.map(item => item.cityCode)
    };
  },
  created() {
    this.headers.Authorization = localStorage.getItem("token");
    this.caseId = localStorage.getItem('caseId')
    this.allListObj = JSON.parse(localStorage.getItem("allListObj"));
    console.log(this.caseId)
  },
  computed: {},
  mounted() {
    // 页面初始数据
    this.handelList();
  },
  methods: {
    // 删除峰图
    handleRemovePic() {
      let info = {
        id: this.mixedSampleGeneId
      };
      quickMatchServers.removePic(info).then(res => {
        if (res.result) {
          // this.genePicturePath = ''
          this.hybridImg = null;
        }
      });
    },
    // 上传图片前
    handleBeforeUpload() {},
    // 上传成功
    handelBreakSuccess(res) {
      this.hybridImg = res.result;
    },
    // 启动strmix
    onStrMix() {
      $.ajax({
        type: "get",
        url: "http://localhost:928/StartRun?ProgramName=baidunetdisk.exe",
        dataType: "json",
        timeout: 3000,
        success: function(res) {},
        error(err) {}
      });
    },
    // 比对进度弹出层 表格分页监听事件
    ScheduleChange(val) {},
    // 设置条件中男女
    changePersonSex(params) {
      this.personSex = params;
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.sex = this.personSex;
        return item;
      });
    },
    // 容差监听事件
    OneOptionChange(val) {
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.condition = val;
        return item;
      });
    },
    // 匹配下限监听事件
    TwoOptionChange(val) {
      // let mixsameCount = val
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.mixsameCount = val;
        return item;
      });
      // this.LowerLimit = val;
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
      let i = this.PersonnelList[this.PersonIndex].val[index];
      if (i.isChecked) {
        i.isChecked = false;
      } else {
        i.isChecked = true;
      }
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
    // 比对进度弹窗
    ScheduleModel_Show() {
      this.ScheduleModel = true;
    },
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
        // return item
      }
      var compareQueueModel = {
        compareQueueList: this.ComparisonTableData,
        compare: {
          sampleNo: this.sampleNo,
          geneInfo: JSON.stringify(this.parGeninfo),
          mixedSampleId: this.hybrid_id
        }
      };
      let info = JSON.stringify(compareQueueModel);
      serve.updateLocusListInfoForId(info).then(res => {
        if (res.code == 1) {
          this.$Modal.success({
            title: "提示",
            content: "提交比对成功！请在比对记录查看结果。"
          });
          this.SendComparisonModal = false;
          this.com_num();
          for (let item of this.leftTitle) {
            if (item.mixedSampleGeneVo.entity.id == this.hybrid_id) {
              item.mixedSampleGeneVo.flag = 1;
            }
          }
          let obj = {};
          obj.id = this.hybrid_id;
          obj.img = this.hybridImg;
          if (this.arrList != 0) {
            for (let item of this.arrList) {
              if (item.id == obj.id) {
                item.arr = obj.arr;
                item.img = obj.img;
              } else {
                this.arrList.push(obj);
              }
            }
          } else {
            this.arrList.push(obj);
          }
          // this.ScheduleData = res.data.resultList;
          // this.ScheduleModel_Show();
        } else {
          this.$Modal.error({
            title: "提示",
            content: "提交比对失败！"
          });
        }
      });
      return;
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
      this.personSex = [];
      this.ModelTwoOption = "";
      this.ModelOneOption = "";
      let viewInfo = {
        mixedSampleId: this.hybrid_id
      };
      hybridServers.ViewAndNext(viewInfo).then(res => {
        this.ComparisonTableData = res.result;
      });
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
      //  let New_ImgPath = require("../../assets/img/feng.jpg");
      //  return New_ImgPath;
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
      if (
        $('input[name="lowLight"]')
          .eq(params.index)
          .prop("checked")
      ) {
        $('input[name="lowLight"]').prop("checked", false);
        $('input[name="lowLight"]')
          .eq(params.index)
          .prop("checked", true);
        // 为1时直接使用后返回的参数，为0时使用主动点击选中的参数
        // this.ListNumType = 1;
        // 参数

        // this.hybrid_id = params.row.mixedSampleGendId;
        let splitInfo = {
          sampleGeneInfo: params.row.entity.geneInfo,
          mixSampleGeneInfo: this.mixEntityGeninfo
        };
        let info = JSON.stringify(splitInfo);
        // 此请求后台会返回自动拆分状态下应选中的参数为哪些，依据标识判断 0/1。
        hybridServers.dataSplit(info).then(res => {
          this.result = res.result;
          // this.ListArrData = JSON.stringify(res.data.list);
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
        // 为数据中标识为0的添加 class 背景颜色。
        this.chooseRed = "chooseRed";
      } else {
        // 取消时清空选中状态 清空背景颜色 改变ListNumType状态
        $(".parting_box li p span").removeClass("chooseRed");
        this.chooseRed = " ";
        $(".parting_box li p span").removeClass("linelight");
        // 为数据添加flag，用于在handelStorage()中拼接数据使用。
        for (var i = 0; i < this.result.length; i++) {
          for (var k = 0; k < this.result[i].allele.length; k++) {
            this.result[i].allele[k].flag = false;
          }
        }
        // this.ListNumType = 0;
      }
    },
    //点击左侧查询数据
    handelHybridData(item, index) {
      $('input[name="lowLight"]').prop("checked", false);
      $(".parting_box li p span").removeClass("chooseRed");
      this.hybridImg = item.mixedSampleGeneVo.geneImagePath;
      if (this.hybridImg == "0") {
        this.hybridImg = null;
      }
      if (this.arrList != 0) {
        for (let data of this.arrList) {
          if (data.id == item.mixedSampleGeneVo.entity.id) {
            this.hybridImg = data.img;
          }
        }
      } else {
        this.hybridImg = null;
      }
      this.allData = []; // 11/12
      this.leftBarClass = index;
      this.hybridList = item.singleSampleGeneVos;
      this.hybrid_id = item.mixedSampleGeneVo.entity.id;
      this.sampleId = item.mixedSampleGeneVo.entity.sampleId;
      this.mixEntityGeninfo = item.mixedSampleGeneVo.entity.geneInfo;
      this.uploadData_ft.id = item.mixedSampleGeneVo.entity.id;
      this.total = item.mixedSampleGeneVo.ratio;
      this.sampleNo = item.mixedSampleGeneVo.sampleNo;
      this.parGeninfo = JSON.parse(item.mixedSampleGeneVo.newGeneInfo);
      this.result = JSON.parse(item.mixedSampleGeneVo.newGeneInfo);
      this.reagentName = item.mixedSampleGeneVo.entity.reagentName; // 试剂盒名称
      this.sampleName = item.mixedSampleGeneVo.sampleName; // 样本名称
      this.com_num();
      // 添加flag
      for (var i = 0; i < this.result.length; i++) {
        for (var k = 0; k < this.result[i].allele.length; k++) {
          this.result[i].allele[k].flag = false;
        }
      }
    },
    // 页面初始数据
    handelList() {
      let MainpageBean = this.allListObj;
      this.loadingShow = true;
      let hybridInfo = {
        caseId: this.caseId
      };
      let item = JSON.stringify(MainpageBean);
      hybridServers
        .dataList(item)
        .then(res => {
          if (res.code == 1) {
            if (res.result.status == 1) {
              this.messageShow = true;
            } else {
              this.loadingShow = false;
              // this.allListObj.caseInfo =
              this.sampleNo =
                res.result.autoanalysis[0].mixedSampleGeneVo.sampleNo; // 检材编号
              this.caseNo = res.result.autoanalysis[0].mixedSampleGeneVo.caseNo; // 案件编号
              this.messageShow = false; // 无混合样本展示html
              this.hybridList = res.result.autoanalysis[0].singleSampleGeneVos; // 本案可疑来源表格数据
              this.mixEntityGeninfo =
                res.result.autoanalysis[0].mixedSampleGeneVo.entity.geneInfo; // 自动拆分功能所需参数
              this.parGeninfo = JSON.parse(
                res.result.autoanalysis[0].mixedSampleGeneVo.newGeneInfo
              ); // 基因信息初始值
              this.result = JSON.parse(
                res.result.autoanalysis[0].mixedSampleGeneVo.newGeneInfo
              ); // 混合基因位点数据
              for (let item of res.result.autoanalysis) {
                item.mixedSampleGeneVo.flag = 0;
              }
              this.leftTitle = res.result.autoanalysis; // 页面左侧展示数据
              this.hybridImg =
                res.result.autoanalysis[0].mixedSampleGeneVo.geneImagePath; // 页面峰图字段
              if (this.hybridImg == "0") {
                this.hybridImg = null;
              }
              this.total = res.result.autoanalysis[0].mixedSampleGeneVo.ratio; // 比中总数
              this.sampleId =
                res.result.autoanalysis[0].mixedSampleGeneVo.entity.sampleId; // ???
              this.hybrid_id =
                res.result.autoanalysis[0].mixedSampleGeneVo.entity.id; // 混合id
              this.uploadData_ft.id =
                res.result.autoanalysis[0].mixedSampleGeneVo.entity.id;
              this.reagentName =
                res.result.autoanalysis[0].mixedSampleGeneVo.entity.reagentName; // 试剂盒名称
              this.sampleName =
                res.result.autoanalysis[0].mixedSampleGeneVo.sampleName; // 样本名称
              this.com_num();
              // 为数据添加flag，用于在handelStorage()中拼接数据使用。
              for (var i = 0; i < this.result.length; i++) {
                for (var k = 0; k < this.result[i].allele.length; k++) {
                  this.result[i].allele[k].flag = false;
                }
              }
            }
          } else {
            this.loadingShow = false;
          }
        })
        .catch(error => {
          this.loadingShow = false;
        });
    },
    //点击 获取基因数
    handelChoose(items, item, event, i, index) {
      // if (this.ListNumType == 2) {
      //   this.$Message.error("请先取消高亮状态！");
      //   return;
      // }
      // this.ListNumType = 1;
      if (event.target.classList.contains("chooseRed")) {
        if (items.name == "Y") {
          event.target.classList.remove("chooseRed");
          event.target.previousSibling.classList.remove("chooseRed");
          this.result[i].allele[index].flag = false;
          this.result[i].allele[0].flag = false;
        }
        event.target.classList.remove("chooseRed");
        this.result[i].allele[index].flag = false;
      } else {
        if (items.name == "Y") {
          event.target.previousSibling.classList.add("chooseRed");
          event.target.classList.add("chooseRed");
          this.result[i].allele[index].flag = true;
          this.result[i].allele[0].flag = true;
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
      // this.mixCount = $(".chooseRed").parents("li").length;
    },
    handparams(type) {
      var storageInfo; // 已拆分单个样本参数
      var strAllData;
      var x = 0;
      for (let item of this.result) {
        if (item.allele.length > 4) {
          x += 1;
        }
      }
      if (x != 0) {
        this.contributorCount = "3";
      } else {
        this.contributorCount = "2";
      }
      if (type == 2) {
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
        strAllData = JSON.stringify(this.allData);
        let geneInfo = JSON.stringify(this.result);
        storageInfo = {
          queueType: "2",
          mixedSampleGeneId: this.hybrid_id,
          sampleId: this.sampleId, //"样本id",
          sampleNo: this.sampleNo, //"样本编号",
          reagentName: this.reagentName, // 试剂盒名称
          geneInfo: strAllData, //主动拼接参数 "基因信息",
          genePicture: null, //图谱位置 url http://XXXXX
          sampleName: this.sampleName,
          mixedSampleGene: {
            contributorCount: this.contributorCount,
            sampleId: this.sampleId,
            sampleNo: this.sampleNo,
            sampleName: this.sampleName,
            reagentName: this.reagentName,
            geneInfo: geneInfo,
            genePicture: this.hybridImg
          }
        };
      } else {
        // 不拆分
        strAllData = JSON.stringify(this.result);
        // 参数赋值
        storageInfo = {
          queueType: "1",
          contributorCount: this.contributorCount,
          mixedSampleGeneId: this.hybrid_id,
          sampleName: this.sampleName,
          sampleId: this.sampleId, //"样本id",
          sampleNo: this.sampleNo, //"样本编号",
          reagentName: this.reagentName, // 试剂盒名称
          geneInfo: strAllData, // 此处使用初始参数 "基因信息",
          genePicture: this.hybridImg //图谱位置 url http://XXXXX
        };
      }
      // 清空style???
      var oSpan = $(".parting_box p span");
      for (var i = 0; i < oSpan.length; i++) {
        oSpan[i].style.color = "";
      }
      let item = JSON.stringify(storageInfo);
      hybridServers.dataStorage(item).then(res => {
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
    },
    // 拆分样本加入比对
    handelStorage(type) {
      if (type == 2) {
        if ($(".chooseRed").parents("li").length == 0) {
          this.$Modal.info({
            title: "提示",
            content: "请先选择基因位点！"
          });
          return;
        } else if ($(".chooseRed").parents("li").length < 6) {
          this.$Modal.confirm({
            title: "提醒",
            content: "选中基因位点少于6位，是否继续加入比对?",
            okText: "确定",
            cancelText: "取消",
            onOk: () => {
              setTimeout(() => {
                this.handparams(2);
              }, 500);
            }
          });
        } else {
          this.handparams(2);
        }
      } else {
        this.handparams(1);
      }
    },
    // 比中条数
    com_num() {
      let mix = {
        mixedSampleId: this.hybrid_id
      };
      hybridServers.SelectCount(mix).then(res => {
        this.Com_num = res.result.count;
      });
    },
    // strMix拆分按钮
    handelBreak(type) {
      var list = {
        sampleId: this.sampleId,
        caseNo: this.caseNo
      };
      let hybridId = JSON.stringify(list);
      localStorage.setItem("hybridId", hybridId);
      localStorage.setItem("sampleNo", this.sampleNo);
      localStorage.setItem("sampleName", this.sampleName);
      localStorage.setItem("reagentName", this.reagentName);
      localStorage.setItem("id", this.hybrid_id);
      localStorage.setItem("genInfo", this.mixEntityGeninfo);
      if (type == 0) {
        this.$router.push("/reportGpm");
      } else {
        this.$router.push("/break");
      }
    }
    // 拆分样本加入比对
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
    // }
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
</style>
