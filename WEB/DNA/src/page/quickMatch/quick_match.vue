<template>
  <div>
    <div class="first">
      <div class="home_title">
        <router-link tag="b" to="/">首页</router-link>》
        <span>DNA分型快速比对</span>
      </div>
      <div class="header">
        <Row class="home_tab_title">
          <Col span="8" class="header_col" v-for="(item,index) in btnList" :key="index">
            <button class="tab_btn" :class="{'active': index == active}" @click="handleBtn(index)">
              <!-- <img :src="index == active ? item.link : item.src" alt="" class="btn_img"> -->
              <!-- <img :src="{'active': index == item.link}" alt="" class="btn_img"> -->
              {{item.btnName}}
            </button>
            <div
              class="triangle_right_blue"
              v-show="index == active && index != btnList.length-1"
              style="top:15px;right:-30px;z-index:5;"
            ></div>
          </Col>
        </Row>
      </div>
      <div class="main">
        <div v-show="active == 0" class="content_box">
          <div class="part" v-for="(item,index) in partList" :key="index">
            <Row class="kit_name">
              <Col span="8">
                <span class="cube"></span>
                <span class="font_weight">试剂盒:</span>
                <Select style="width:200px" @on-change="changeKit($event,index)" class="kitSelect">
                  <Option
                    v-for="item in kitNameList"
                    :value="item.id"
                    :key="item.id"
                  >{{ item.name }}</Option>
                </Select>
              </Col>
              <Col span="8">
                <span class="font_weight">样本编号:</span>
                <Input disabled style="width:200px;" :value="item.sampleNo" name="sampleNo" />
                <input type="hidden" :value="item.mixedSampleId" name="mixedSampleId" />
              </Col>
              <Col span="8" style="text-align:right;">
                <Button
                  type="error"
                  style="width:70px;height:40px;background:#FFE2E0;font-weight:bold;color:#F64E44;border:none;font-size:16px;"
                  @click="delGeneGroup(index)"
                >删除</Button>
              </Col>
            </Row>
            <Table
              size="small"
              border
              stripe
              :columns="kitTable"
              :data="item.kitTableData"
              class="table_data"
            ></Table>
          </div>
          <div class="foot_btn">
            <button class="blue_border_btn" @click="addGenotyping">添加基因分型</button>
            <button class="blue_btn" @click="handleNext">下一步</button>
            <button class="red_btn" @click="removeAll">全部删除</button>
          </div>
        </div>
        <div v-show="active == 1" class="content_box codisBox">
          <Row class="kit_name">
            <Col span="6">
              <span class="cube"></span>
              <span class="font_weight">试剂盒:</span>
              <Select style="width:200px" class="kitSelect">
                <Option v-for="item in kitNameList" :value="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </Col>
            <Col span="6">
              <Input type="text" style="width:200px;" :value="fileName" disabled />
              <Upload
                multiple
                action="api_iLabSTRmix/codis/uploadCodis"
                :on-success="upSuccess"
                :data="codisData"
                :name="codisFile"
                :format="['dat']"
                style="display:inline;"
                class="DNA-label codis_upload"
              >
                <Button icon="ios-cloud-upload-outline" type="primary">导入</Button>
              </Upload>
            </Col>
          </Row>
          <Table
            border
            stripe
            :columns="codisCol"
            :data="codisTableData"
            @on-selection-change="handleSelect"
            class="table_data codis-style"
            style="margin:10px 0;"
          ></Table>
          <div class="foot_btn">
            <button class="blue_btn" @click="handleCodisNext">下一步</button>
          </div>
        </div>
        <div v-show="active == 2">
          <div class="break_box">
            <div class="break_content">
              <div class="break_box_title">
                <!-- 
                            此处编号待定
                            <p>
                              <Icon type="ios-list icon_name" />样本编号:11111
                            </p> 
                -->
                <p>
                  <span></span>STRmix拆分报告
                  <Upload
                    :action="uploadUrl"
                    :show-upload-list="false"
                    class="break_box_upload"
                    :before-upload="beforUpload"
                    :format="['pdf']"
                    :name="'splitReportFiles'"
                    :on-success="handelBreakSuccess"
                  >
                    <Button class="break_upload_btn">导入</Button>
                  </Upload>
                </p>
                <p>
                  <span></span>贡献者数量
                  <button class="break_content_count">{{this.breakList.contributorCount}}</button>
                </p>
                <!-- <div class="chai_btn_two" v-show="chaiShowBtn">
                                            <span @click="Details_show">快速拆分比对记录</span>
                </div>-->
                <!-- <div class="chai_btn_two">
                                    <Button type="primary" @click="lookSampleNext">查看拆分样本并进行下一步
                                        </Button>
                                    <Badge :count="sampleBadge" class="badge_p"></Badge>
                </div>-->
                <div class="chai_btn" v-show="chaiShowBtn">
                  <div class="posi-rel">
                    <span @click="lookSampleNext">查看拆分样本并下一步</span>
                    <div class="posi-abs">{{sampleBadge}}</div>
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
                  <span style="color:#0C81F5;">STRmix拆分报告</span>后在进行查看
                </p>
              </div>
              <!-- ---before -->
              <div class="break_box_success" v-show="!breakShow">
                <div class="break_box_success_title">
                  <!-- <button>查看拆分样本并下一步</button> -->
                  <!-- <div v-for="(item,index) in breakList.strMixRapiGeneComparisonListVos" class="float_div" :key="item.index">
                                                <Icon type="ios-notifications icon_success_title" /> {{index+1}}号贡献者权重≥
                                                <Select style="display:inline-block;float:none;width:80px;" placeholder="99%" @on-change="choosePercent($event,item,index)" :label-in-value="true">
                                                  <Option
                                                    v-for="result in item.moreThanTheCountList"
                                                    :value="result.moreThanThevalue"
                                                    :key="result.percent"
                                                  >{{result.percent}}</Option>
                                                </Select>的基因位点数
                                                <span>({{item.val?item.val:item.moreThanTheCountList[9].moreThanThevalue}})</span>个
                                                <p @click="handelBreakContrast(item,index)">快速比对</p>
                  </div>-->

                  <!-- <div v-for="(item,index) of breakList.strMixRapiGeneComparisonListVos" class="float_div" :key="item.index">
                                            <Icon type="ios-notifications icon_success_title"/>
                                            {{index+1}}号贡献者权重≥
                                                <Select style="display:inline-block;float:none;width:80px;" v-model="item.moreThanTheCountList[9].moreThanThevalue" @on-change="choosePercent($event,item)" :label-in-value="true">
                                                    <Option v-for="item of item.moreThanTheCountList" :value="item.moreThanThevalue" :key="item.percent">{{item.percent}}</Option>
                                                    
                                                </Select>
                                            的基因位点数<span>({{item.val?item.val:item.moreThanTheCountList[9].moreThanThevalue}})</span>个
                                            <p @click="handelBreakContrast(item)">快速比对</p>
                  </div>-->
                </div>
                <div class="break_success_content">
                  <ul class="break_success_text">
                    <div class="break_box_text_title">
                      <span></span>基因座名称
                    </div>
                    <li class="break_text_first">基因座</li>
                    <div
                      v-for="(item,index) of breakList.mixedSampleGeneList"
                      :class="'break_text_li data_index'+index"
                    >
                      <li>{{item.markerName}}</li>
                    </div>
                  </ul>
                  <ul class="break_success_text1 break_success_parting">
                    <div class="break_box_text_title">
                      <span></span>混合分型
                    </div>
                    <li class="break_text_first">分型</li>
                    <div
                      v-for="(item,index) of breakList.mixedSampleGeneList"
                      :class="'break_text_li data_index'+index"
                    >
                      <li>{{item.geneStr1}}</li>
                    </div>
                  </ul>

                  <ul
                    class="break_success_table"
                    v-for="(item,index) of breakList.strMixRapiGeneComparisonListVos"
                    :name="'break_success_table'+index"
                  >
                    <div class="break_success_table_title">
                      <p>
                        <span></span>
                        {{index+1}}号贡献者（{{item.contributorWeight}}）
                      </p>
                      <Button type="primary" @click="quickCheck('break_success_table'+index)">一键选中</Button>
                      <Button
                        class="break_table_btn"
                        @click="chaifenruku('break_success_table'+index)"
                        :name="'break_success_table'+index"
                      >加入比对</Button>
                    </div>
                    <li class="break_table_title_one">
                      <p>
                        <Checkbox></Checkbox>
                      </p>
                      <p>等位基因</p>
                      <p>权重</p>
                      <p>权重≥99%</p>
                    </li>

                    <div
                      v-for="(items,index) in item.contributorGeneList"
                      :class="'break_table_display data_index'+index"
                    >
                      <li v-for="(i,index) in items.geneAlleleList">
                        <!-- <Checkbox><Checkbox @on-change="changeGene(item,items,$event)" class="checkQuery" :indeterminate="indeterminate"></Checkbox></p> -->
                        <p>
                          <input type="checkbox" class="checkQuery" name="checkQuery" />
                        </p>
                        <p>{{i.allele}}</p>
                        <p>{{i.weights}}</p>
                        <p>{{i.ninetyNinePercentWeights}}</p>
                        <!--                                    <span class="oSpan">{{(index+1)*31}}</span>-->
                      </li>
                    </div>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <Modal v-model="geneListModal" @on-ok="sureRuku()">
            <p slot="header" style="text-align:center">
              <Icon type="ios-information-circle"></Icon>
              <span>查看已选基因信息</span>
            </p>
            <div style="text-align:center">
              <Table :columns="geneListCol" :data="geneListData"></Table>
            </div>
            <!-- <div slot="footer">
            </div>-->
          </Modal>
          <Modal class="model_con" v-model="geneListModal_two">
            <!-- style="text-align:center -->
            <p slot="header">
              <Icon type="ios-information-circle"></Icon>
              <span>快速拆分比对记录</span>
            </p>
            <div style="text-align:center">
              <Table :columns="geneListCol_two" :data="geneListData_two"></Table>
            </div>
          </Modal>
          <div v-show="loadingShow" class="loading_con">
            <div class="arc"></div>
            <h1>
              <span>LOADING</span>
            </h1>
          </div>
          <!-- 查看按钮 -->
          <Modal class="model_con" v-model="SeeDetailsModel">
            <p slot="header">
              <Icon type="ios-information-circle"></Icon>
              <span>详情信息</span>
            </p>
            <div style="text-align:center">
              <Table :columns="SeeDetailsCol" :data="SeeDetailsData"></Table>
            </div>
          </Modal>
        </div>
      </div>
    </div>
    <!-- 下一步 -->
    <div class="second"></div>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
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
            <CheckboxGroup v-model="personSex" style="display:inline;" @on-change="changePersonSex">
              <Checkbox label="1">男</Checkbox>
              <Checkbox label="2">女</Checkbox>
            </CheckboxGroup>
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
            <span class="marginStyle title-span-style">匹配下限</span>
            <Select @on-change="TwoOptionChange" v-model="ModelTwoOption" style="width:80px">
              <Option
                v-for="item in cityListTwo"
                :value="item.value"
                :key="item.value"
              >{{item.label}}</Option>
            </Select>
          </div>
          <div class="float-style clearfix">
            <div>
              <span class="title-span-style">人员类型</span>
              <span @click="PersonnelModel_Show" class="spanStyle">选择</span>
            </div>
            <div class="span-style Scrollbar">
              <span style="position:relative" v-for="(persItem,persIndex) in PersonneNewArray">
                {{persItem.Personnel}}
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
              <div v-for="(citySitem,CytisIde) in AreaNewArray">
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
    <Modal class="model_con_Style" v-model="AreaShow" width="900" :closable="false">
      <div class="model-header">
        <!-- <span>选择地区</span> -->
        <span class>选择地区</span>
        <p class="all" @click="allCitys">全选</p>
      </div>
      <div class="model-Area-body">
        <div class="model-Area-ScrollBar Scrollbar">
          <ul>
            <!-- 省级城市 -->
            <li class="li-style" v-for="(AreaItem,AreaIndex) in AreaList">
              <!-- @click="SelectArea(AreaIndex,AreaItem)"  -->
              <div :class="{'active':activeIndex == AreaIndex}" @click="SelectArea(AreaIndex,AreaItem)">
                <!-- :class="activeClass == AreaIndex ? 'active':''" -->
                <span class="span-1">✔</span>
                <span class="span-2">{{AreaItem.regionalismName}}</span>
              </div>
            </li>
          </ul>
        </div>
        <!-- 地级城市 -->
        <div class="position-relative Scrollbar">
          <ul class="position-absolute">
            <li v-for="(cityItem,city_Index) in CitysList">
              <div
                class="active-div"
                @click="SelectCity(city_Index,cityItem)"
                :class="{'activeCity':cityItem.bOn}"
              >
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
    <Modal class="model_con_Style" v-model="PersonnelModel" width="600" :closable="false">
      <div class="model-header" style="position: relative;">
        <span class>人员选择</span>
        <p class="all" @click="allSelection">全选</p>
      </div>
      <div class="PersonnelBody Scrollbar">
        <ul>
          <li
            v-for="(PerItem,PerIndex) in PersonnelList"
            @click="PersonnelSelection(PerItem,PerIndex)"
            :class="{'PersonnelStyle':PerItem.flag}"
          >
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
    <!-- 比对进度弹窗 -->
    <Modal class="model_con_Style" v-model="ScheduleModel" width="1000" :closable="false">
      <div class="model-header">
        <span>比对进度</span>
      </div>
      <div class="schedule-body">
        <div class="schedule-title">
          <div class="title-child">
            <!-- <p>当前数据300万，预计比对时间30分钟。</p> -->
            <div class>
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
            <span>当前第{{schedulePage}}页/共{{scheduleCount}}页/共{{scheduleAllCount}}条信息</span>
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
      </p>-->
      <!-- </div> -->
    </Modal>
  </div>
</template>

<script>
import hybridServers from "../../servers/hybridServers";
import serve from "../../servers/serve";
import breakServers from "../../servers/breakServers";
import quickMatchServers from "../../servers/quickMatch";
import ModalDetail from "../../components/ModalDetail";
import hybridSplitServers from "../../servers/hybridSplitServers";

export default {
  name: "quickMatch",
  components: { ModalDetail },
  data() {
    return {
      sam: "",
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
      loadingShow: false,
      isShow: false,
      // inputValue:""
      modalDetail: {
        modalType: 2,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: ""
      },
      btnList: [
        {
          src: require("../../assets/img/check.png"),
          link: require("../../assets/img/checked.png"),
          btnName: "手工录入DNA分型"
        },
        {
          src: require("../../assets/img/check.png"),
          link: require("../../assets/img/checked.png"),
          btnName: "Codis导入DNA分型"
        },
        {
          src: require("../../assets/img/check.png"),
          link: require("../../assets/img/checked.png"),
          btnName: "STRmix报告导入"
        }
      ],
      active: 0,
      partList: [{ sampleNo: "", mixedSampleId: "", kitTableData: [] }],
      kitName: "",
      kitNameList: [],
      sampleNo: "",
      exList: [1, 1, 1, 1, 1, 1],
      kitTable: [
        {
          title: "序号",
          width: "120",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "基因座名称",
          key: "locusName",
          className: "locusName",
          width: "180"
        },
        {
          title: "基因位点",
          key: "",
          render: (h, params) => {
            console.log(params);
            if (params.row.locusName == "AMELOGENIN") {
              return h("div", [
                h("span", [
                  h("Input", {
                    props: {
                      value: "X"
                    },
                    style: {
                      textAlign: "center",
                      padding: "4px",
                      width: "60px",
                      // height:'20px',
                      display: "inline-block"
                      // border:"1px solid #000;"
                    },
                    // props:{
                    //   vlaue:'X'
                    // },
                    class: "geneVal"
                  }),
                  h("Input", {
                    style: {
                      textAlign: "center",
                      padding: "4px",
                      width: "60px",
                      // height:'20px',
                      display: "inline-block"
                      // border:"1px solid #000;"
                    },
                    class: "geneVal"
                  })
                ])
              ]);
            } else {
              return h("div", [
                h(
                  "span",
                  this.exList.map(item => {
                    return h("Input", {
                      style: {
                        textAlign: "center",
                        padding: "4px",
                        width: "60px",
                        // height:'20px',
                        display: "inline-block"
                        // border:"1px solid #000;"
                      },
                      class: "geneVal"
                    });
                  })
                )
              ]);
            }
          }
        }
      ],
      kitTableData: [{}],

      // codis导入
      codisFile: "codisFile",
      fileName: "",
      codisData: {
        // file:[],
        // Orgid:'34234234',
        // reagentId:'',
        // reagentName:'',
        // codisType:1,
        // userId:'123',
        // loginName:'1232',
        // sameCode:1
      },
      codisCol: [
        {
          type: "selection",
          width: "60px"
        },
        {
          title: "序号",
          type: "index",
          width: "80px",
          className: "table_border_right"
        },
        // {
        //     title: '是否混合分型',
        //     key: '',
        //     className:'table_border_right'
        // },
        {
          title: "样本编号",
          key: "sampleNo",
          className: "table_border_right"
        },
        {
          title: "位点个数",
          key: "genenum",
          className: "table_border_right"
        },
        {
          title: "操作",
          key: "action",
          width: 120,
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
                      this.$refs.modal.modalShow = true;
                      this.modalDetail.modalTypeHeader = "混合分型";
                      this.modalDetail.modalInfo = params.row.geneInfoBeanList;
                      this.modalDetail.modalTitle = params.row.sampleNo;
                      // console.log(params)
                      this.modalDetail.modalData = params.row;
                    }
                  }
                },
                "查看"
              )
            ]);
          }
        }
      ],
      codisTableData: [],
      codisDataNext: [],

      //STR导入
      // 快速拆分比对记录 model
      geneListModal_two: false,
      // 快速拆分比对记录  查看按钮 model
      SeeDetailsModel: false,
      // 快速拆分比对记录  查看详情 表格标题
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
      // 快速拆分比对记录  查看详情 表格数据
      SeeDetailsData: [],
      // 快速拆分比对记录 表格标题
      geneListCol_two: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "贡献者",
          key: "contributorName",
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    display: "inline-block"
                  }
                },
                params.row.contributorName + "-号贡献者"
              )
            ]);
          }
        },
        {
          title: "比中状态",
          key: "bz",
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
                      console.log(params);
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
                      let Id_info = {
                        id: params.row.id
                      };
                      hybridSplitServers.deleteSpli(Id_info).then(res => {
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
      // 快速拆分比对记录 表格数据
      geneListData_two: [],
      Contributor: 1,
      chaiShowBtn: false,
      FileName: "",
      loadingShow: false,
      genneId: "",
      mainId: "", //2019年12月24上传文件成功后返回ID,后续加入比对需要传入的混合id也是此ID
      value: "",
      breakShow: true,
      // uploadData:{},
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
      // 拆分入库 model
      geneListModal: false,
      // 拆分入库 表格标题
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
      // 拆分入库 表格数据
      geneListData: [],
      checkTrueFalse: false,
      indeterminate: false,
      strNum: 1,
      map_num: 0,
      // 查看拆分样本并下一步弹窗数据
      SendComparisonModal: false,
      personSex: [],
      PersonneNewArray: [],
      AreaNewArray: [],
      // 地级市数据
      CitysList:[

      ],
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
        }
      ],
      //
      showstyle: false,
      // 地区列表
      AreaList: [],
      // 快速比对记录 查看按钮model
      SeeDetailsModel: false,
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
      ComparisonTableData: [
        // { "mixsameCount": '4' },
        // {  "mixsameCount": '6' }, {  "mixsameCount": '15' }
      ],
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
            return h("span", {}, "拆分");
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
                    // console.log(event)
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
                      let index = params.index;
                      this.Area_Show(event, index);
                      // console.log(index)
                    }
                  }
                },
                "修改"
              ),
              h(
                "span",
                {
                  class: "edit_table",
                  domProps: {
                    title: params.row.diqu
                  }
                },
                params.row.diqu
              )
            ]);
          }
        },
        {
          title: "人员类型",
          key: "renyuan",
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
                      let index = params.index;
                      this.PersonnelModel_Show(event, index, params);
                      // console.log(index)
                    }
                  }
                },
                "修改"
              ),
              h(
                "span",
                {
                  class: "edit_table",
                  domProps: {
                    title: params.row.renyuan
                  }
                },
                params.row.renyuan
              )
            ]);
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
                        console.log(params);
                        this.$refs.modal.modalShow = true;
                        this.modalDetail.modalTypeHeader = "混合样本分型";
                        this.modalDetail.modalInfo =
                          res.data.GeneMap.resultList;
                        this.modalDetail.modalTitle = params.row.sampleNo;
                        this.modalDetail.modalData = params.row;
                      } else {
                        this.$Message.error("出错啦~");
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
                        this.$Message.error("操作失败！");
                      }
                    });
                  }
                }
              },
              "删除"
            );
          }
        }
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
      personTypeCode: [], //比对列表中保存人员类型的code值
      personTypeName: [], //比对列表中保存人员类型的name值
      districtCode: [], //比对列表中保存地区的code值
      districtName: [], //比对列表中保存地区的name值
      tablePersonIndex: "", //比对列表中人员index
      tablePersonBoolean: false,
      tableAreaIndex: "",
      tableAreaBoolean: false,
      // 快比对拆分表格数据
      geneListData: [],
      sampleBadge: 0
    };
  },

  mounted() {
    this.handleKitName();
  },
  updated() {
    //STR拆分
    var _this = this;
    var geneListInfo;
    $(".break_success_table .break_table_display .checkQuery").click(
      function() {
        // console.log($(this).parents('.break_success_table').attr('name'))
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
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
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
            // console.log(_this.geneList0);
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
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
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
            // console.log(_this.geneList1);
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
            // console.log(geneListInfo.findIndex(item => item.geneName == geneInfo.geneName),'能不能找到')
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
            // console.log(_this.geneList2);
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
            // console.log(_this.geneList3);
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
      // $('.break_success_table').css('color','red')
    }
  },
  methods: {
    handleBtn(index) {
      this.active = index;
    },
    handleKitName() {
      quickMatchServers.querykitLocusListInfo().then(res => {
        if (res.code == 1) {
          let list = JSON.parse(res.data);
          this.kitNameList = list.data;
          // console.log(this.kitNameList)
        } else {
          this.$Message.warning("请求错误!");
        }
      });
    },
    changeKit($event, index) {
      // console.log($event)
      // console.log(index)
      let listInfo = {
        reagentKitId: $event
      };
      quickMatchServers.querySimleNofoForkitId().then(res => {
        console.log(res);
        if (res.code == 1) {
          // console.log(res.data)
          this.partList[index].sampleNo = res.data.nextSampleNoVal;
          this.partList[index].mixedSampleId = res.data.mixedSampleId;
          // console.log(res.data)
        } else {
          this.$Message.warning("请求错误!");
        }
      });
      quickMatchServers.queryLocusListInfoForkitId(listInfo).then(res => {
        console.log(res);
        if (res.code == 1) {
          let list = JSON.parse(res.data);
          console.log(list.data);
          this.partList[index].kitTableData = list.data;
        } else {
          this.$Message.warning("请求错误!");
        }
      });
    },
    addGenotyping() {
      this.partList.push({ sampleNo: "", mixedSampleId: "", kitTableData: [] });
    },
    delGeneGroup(index) {
      // console.log(index)
      this.partList.splice(index, 1);
    },
    // 下一步
    handleNext() {
      var that = this;
      let bigList = [];
      $(".part").each(function() {
        let geneInfoData = {};
        geneInfoData.sampleNo = $(this)
          .find('input[name="sampleNo"]')
          .val();
        geneInfoData.mixedSampleId = $(this)
          .find('input[name="mixedSampleId"]')
          .val();
        geneInfoData.kitName = $(this)
          .find(".kitSelect input")
          .val();
        geneInfoData.geneInfoList = [];
        $(this)
          .find(".ivu-table-body tbody tr")
          .each(function() {
            let geneName = {};
            geneName.markerName = $(this)
              .find(".locusName span")
              .text();
            geneName.allele = [];
            $(this)
              .find(".geneVal")
              .each(function() {
                if (
                  $(this)
                    .find("input")
                    .val() != ""
                ) {
                  let wd = {};
                  wd.name = $(this)
                    .find("input")
                    .val();
                  geneName.allele.push(wd);
                }
              });
            geneInfoData.geneInfoList.push(geneName);
          });
        bigList.push(geneInfoData);
      });
      console.log(bigList);
      var bon_null = false;
      var bon_3 = false;
      for (let i = 0; i < bigList.length; i++) {
        var num = 0;
        for (let k = 0; k < bigList[i].geneInfoList.length; k++) {
          if (
            bigList[i].geneInfoList[k].allele.length > 3 ||
            bigList[i].geneInfoList[k].allele.length == 3
          ) {
            num += 1;
          }
          if (bigList[i].geneInfoList[k].allele.length == 0) {
            bon_null = true;
          }
        }
        if (num == 0) {
          bon_3 = true;
        }
      }
      if (bon_null == true) {
        this.$Modal.error({
          title: "提示",
          content: "基因座位点不能为空！"
        });
      } else if (bon_3 == true) {
        this.$Modal.error({
          title: "提示",
          content: "基因座位点至少有一组输入3个"
        });
      } else if (bon_3 == false && bon_null == false) {
        this.$router.push({
          path: "/quickMixedSplit",
          name: "QuickMixedSplit",
          params: {
            geneInfoData: bigList
          }
        });
      }
    },
    removeAll() {
      this.partList = [];
    },
    // !codis导入
    upSuccess(res, file, fileList) {
      this.fileName = file.name;
      // console.log(res, file, fileList)
      if (res.code == 1) {
        this.codisTableData = res.data;
      } else {
        this.$Message.error("上传出错,请重试");
      }
    },
    handleSelect(params) {
      // console.log(params)
      this.codisDataNext = params;
    },
    handleCodisNext() {
      let kitName = $(".codisBox")
        .find(".kitSelect input")
        .val();
      if (this.codisDataNext.length > 0) {
        this.$router.push({
          path: "/quickMixedSplit",
          name: "QuickMixedSplit",
          params: {
            geneInfoData: this.codisDataNext,
            kitName: kitName
          }
        });
      } else {
        this.$Message.error("请先选择要处理的数据");
      }
    },

    // !STR拆分
    // 查看拆分样本并下一步 model
    lookSampleNext() {
      this.SendComparisonModal = true;
      this.PersonneNewArray = [];
      this.AreaNewArray = [];
      this.personSex = [];
      this.ModelTwoOption = "";
      let viewInfo = {
        mixedSampleId: this.mainId
      };
      hybridServers.ViewAndNext(viewInfo).then(res => {
        this.ComparisonTableData = res.data;
      });
    },
    // 快速比对结果按钮
    Details_show() {
      this.geneListModal_two = true;
      let file_Name = {
        fileName: this.FileName
      };
      hybridSplitServers.recording(file_Name).then(res => {
        this.geneListData_two = res.data;
      });
    },

    /*
          下拉框监听事件 发生改变时，返回当前选中的参数 例：‘label:95%’,
          vlaue:10,label代表当前选中了百分之多少，vlaue代表有多少个参数大于等于95%
        */
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
      // return
      switch (item.percent) {
        case "90%":
          contrast = JSON.stringify(item.moreGeneList0);
          //   console.log(contrast);
          break;
        case "91%":
          contrast = JSON.stringify(item.moreGeneList1);
          //   console.log(contrast);
          break;
        case "92%":
          contrast = JSON.stringify(item.moreGeneList2);
          //   console.log(contrast);
          break;
        case "93%":
          contrast = JSON.stringify(item.moreGeneList3);
          //   console.log(contrast);
          break;
        case "94%":
          contrast = JSON.stringify(item.moreGeneList4);
          // console.log(constants);
          break;
        case "95%":
          // 参数改动
          contrast = JSON.stringify(item.moreGeneList5);
          // contrast = JSON.stringify(item.moreThanTheGeneList6);
          break;
        case "96%":
          contrast = JSON.stringify(item.moreGeneList6);
          //   console.log(contrast);
          break;
        case "97%":
          contrast = JSON.stringify(item.moreGeneList7);
          //  console.log(contrast);
          break;
        case "98%":
          contrast = JSON.stringify(item.moreGeneList8);
          // console.log(contrast);
          break;
        default:
        case "99%":
          contrast = JSON.stringify(item.moreGeneList9);
          // console.log(contrast);
          break;
      }
      if (item.percent) {
        percent = item.percent;
      } else if (item.percent == null) {
        percent = "99%";
      }
      sessionStorage.setItem("contrast", contrast);
      sessionStorage.setItem("percent", percent);
      this.Contributor = index + 1;
      this.$router.push({
        name: "QuickComparison",
        params: { fileName: this.FileName, Contributor: this.Contributor }
      });
    },
    //上传成功
    handelBreakSuccess(response, file) {
      console.log(response);
      if (response.code == 1) {
        this.sam = response.data.sampleNo;
        this.chaiShowBtn = true;
        this.map_num = 1;
        this.FileName = file.name;
        this.loadingShow = false;
        this.breakList = response.data;
        this.breakShow = false;
        this.type = 2;
        // this.genneId = response.data.strMixRapiGeneComparisonListVos[0].geneId;
        this.mainId = response.data.mixId;
        // this.sampleBadge = 0
        this.com_num();
      } else {
        this.chaiShowBtn = false;
      }
    },
    //文件上传之前
    beforUpload(files) {
      const FileExt = files.name.replace(/.+\./, ""); //取得文件的后缀名
      if (FileExt != "pdf") {
        this.$Message.error("当前只支持上传PDF格式文件!");
        return false;
      } else {
        this.map_num = 0;
        this.breakList = [];
        this.loadingShow = true;
        this.uploadUrl = "api_iLabSTRmix/main/stitchingComparisonVo";
        // this.uploadData = {
        //     sampleId:this.breakSampleId.sampleId,
        //     caseId:this.sample.caseId
        // };
        let promise = new Promise(resolve => {
          this.$nextTick(function() {
            resolve(true);
          });
        });
        return promise;
      }
    },
    // breakDataList(){//初始化数据
    //     let breakSampleId ={
    //         sampleId:this.breakSampleId.sampleId,
    //         caseNo:this.breakSampleId.caseNo
    //     };
    //     breakServers.breakList(breakSampleId).then(res=>{
    //         console.log(res);
    //         this.sample = res.data
    //     })
    // },
    quickCheck(par) {
      var zz = par.substring(par.length - 1);
      let that = this;
      var genefirstInfo;
      // 更改 11/6
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
            // console.log("第三种情况");
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", false);
            that["geneList" + zz] = [];
          }
        });
      /*
              原判断
              $("ul[name=" + par + "]")
                .find(".break_table_display")
                .each(function(num, text) {
                  if (
                    $(this)
                      .find("input")
                      .prop("checked")
                  ) {
                    $(this)
                      .find("li")
                      .each(function() {
                        $(this)
                          .find("p:eq(0) input")
                          .prop("checked", false);
                      });
                    that["geneList" + zz] = [];
                  } else {
                    $(this)
                      .find("li:eq(0) p:eq(0) input")
                      .prop("checked", true);
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
                  }
                });
            */
    },
    chaifenruku(par) {
      let str = par;
      let strSUb = str.substring(19);
      this.strNum = parseInt(strSUb) + 1;
      // console.log(this.strNum);
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
    // 比中条数
    com_num() {
      let mix = {
        mixedSampleId: this.mainId
      };
      console.log(mix);
      hybridServers.SelectCount(mix).then(res => {
        console.log(res);
        this.sampleBadge = res.data;
      });
    },
    // 加入比对 确定按钮事件
    sureRuku() {
      // let str = $(".break_success_table_title p").text();
      // console.log(str);
      //  let str =  $(this).parent().parent().attr("name");
      //  console.log(str);
      let comparisonInfo = {
        sampleNo: this.sam,
        mixedSampleId: this.mainId,
        queueType: "2", // 1
        condition: "0",
        fileName: this.FileName,
        contributorName: this.strNum,
        geneInfo: JSON.stringify(this.geneListData)
        // genneId: this.genneId
      };
      breakServers.dataComparison(comparisonInfo).then(res => {
        if (res.code == 0) {
          this.$Modal.error({
            title: "提示",
            content: "操作失败!"
          });
        }
        if (res.data.code == 1) {
          this.$Modal.error({
            title: "提示",
            content: "请勿重复添加!"
          });
        } else if (res.data.code == 0) {
          // this.sampleBadge = this.sampleBadge + 1
          this.$Modal.success({
            title: "提示",
            content: "加入比对成功！"
          });
          this.com_num();
        }
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
        this.tablePersonIndex = index;
        this.tablePersonBoolean = true;
      }
      if (index == 0) {
        this.tablePersonIndex = index;
        this.tablePersonBoolean = true;
      }
      this.PersonnelModel = true;
      hybridServers.GetPersonnelData().then(res => {
        // console.log(res)
        this.PersonnelList = res.data;
        if (this.tablePersonBoolean) {
          let arr = params.row.renyuan.split(",");
          var newdata = [];
          for (let i = 0; i < arr.length; i++) {
            newdata.push({
              Personnel: arr[i]
            });
          }
          for (let New of this.PersonnelList) {
            for (let Old of newdata) {
              if (Old.Personnel == New.dictValue1) {
                New.flag = true;
              }
            }
          }
        } else {
          for (let New of this.PersonnelList) {
            for (let Old of this.PersonneNewArray) {
              if (Old.PerCode == New.dictKey) {
                New.flag = true;
              }
            }
          }
        }
      });
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
      // 两层循环是因为第一层循环中程序还没走完。
      for (let i = 0; i < this.PersonnelList.length; i++) {
        if (allNum == 0) {
          this.PersonnelList[i].flag = true;
        } else if (allNum == this.PersonnelList.length) {
          this.PersonnelList[i].flag = false;
        } else {
          this.PersonnelList[i].flag = true;
        }
      }
    },
    // 人员选择按钮
    PersonnelSelection(item, index) {
      if (item.flag == false) {
        item.flag = true;
      } else {
        item.flag = false;
      }
    },
    // 人员选择确认按钮
    Perdetermine() {
      // console.log(this.tablePersonIndex, 'quedingdshih')
      if (this.tablePersonBoolean) {
        // console.log(this.tablePersonIndex, 'quedijinlai进来了吗ngdshih')

        let indexPerson = Number(this.tablePersonIndex);
        // console.log(this.tablePersonIndex,'indexPerson')
        this.PersonnelModel = false;
        let personArr = [];
        let code = [];
        let name = [];
        for (let PersonnelItem of this.PersonnelList) {
          if (PersonnelItem.flag == true) {
            personArr.push({
              Personnel: PersonnelItem.dictValue1,
              PerCode: PersonnelItem.dictKey
            });
          }
        }
        code = personArr.map(item => item.PerCode);
        name = personArr.map(item => item.Personnel);
        this.ComparisonTableData = this.ComparisonTableData.map(
          (item, index, value) => {
            if (index == indexPerson) {
              item.personType = code;
              item.renyuan = name.join(",");
            }
            return item;
          }
        );
      } else {
        this.PersonnelModel = false;
        this.PersonneNewArray = []; // 页面人员条件渲染参数
        // console.log(this.PersonnelList);
        // console.log(this.PersonnelList);
        for (let PersonnelItem of this.PersonnelList) {
          if (PersonnelItem.flag == true) {
            this.PersonneNewArray.push({
              Personnel: PersonnelItem.dictValue1,
              PerCode: PersonnelItem.dictKey
            });
          }
        }
        // console.log(this.PersonneNewArray);
        this.personTypeCode = this.PersonneNewArray.map(item => item.PerCode);
        this.personTypeName = this.PersonneNewArray.map(item => item.Personnel);
        // console.log('psersonTypeCode', this.personTypeCode)
        // console.log('psersonTypeName', this.personTypeName)
        this.ComparisonTableData = this.ComparisonTableData.map(item => {
          item.personType = this.personTypeCode;
          item.renyuan = this.personTypeName.join(",");
          return item;
        });
      }
    },
    // 比对进度弹窗
    ScheduleModel_Show() {
      this.ScheduleModel = true;
    },
    // 比对进度弹出层 表格分页监听事件
    ScheduleChange(val) {
      console.log(val);
    },
    // 提交比对参数
    SendComparisonParams() {
      // let array = this.ComparisonTableData.map(item => {
      //   item.geneInfo = JSON.parse(item.geneInfo)
      //   item.geneInfo = JSON.stringify(item.geneInfo)
      //   item.sex = JSON.stringify(item.sex)
      //   item.personType = JSON.stringify(item.personType)
      //   item.renyuan = JSON.stringify(item.renyuan)
      //   item.district = JSON.stringify(item.district)
      //   item.diqu = JSON.stringify(item.diqu)
      //   return item
      // })

      // let array = this.ComparisonTableData.map(item => {

      for (let item of this.ComparisonTableData) {
        if (item.sex == null) {
          this.$Modal.error({
            title: "提示",
            content: "请补充性别条件~"
          });
          return false;
        } else {
          item.sex = JSON.stringify(item.sex);
        }
        if (item.personType == null) {
          this.$Modal.error({
            title: "提示",
            content: "请补充人员条件~"
          });
          return false;
        } else {
          item.personType = JSON.stringify(item.personType);
        }
        if (item.district == null) {
          this.$Modal.error({
            title: "提示",
            content: "请补充地区条件~"
          });
          return false;
        } else {
          item.district = JSON.stringify(item.district);
        }

        item.geneInfo = JSON.parse(item.geneInfo);
        item.geneInfo = JSON.stringify(item.geneInfo);
        // item.renyuan = JSON.stringify(item.renyuan)
        // item.diqu = JSON.stringify(item.diqu)
        item.renyuan = item.renyuan; //JSON.stringify()
        item.diqu = item.diqu; //JSON.stringify()
        // return item
      }
      // })

      // let dd = JSON.stringify(this.ComparisonTableData)
      var compareQueueModel = JSON.stringify({
        compareQueueList: this.ComparisonTableData
      });
      // console.log(dd, 'dddddddddddddd')
      serve.updateLocusListInfoForId(compareQueueModel).then(res => {
        console.log(res);
        if (res.code == 1) {
          this.$Modal.success({
            title: "提示",
            content: "提交比对成功！请在比对记录查看结果。"
          });
          this.SendComparisonModal = false;
          this.com_num();
          // this.ScheduleData = res.data.resultList;
          // this.ScheduleModel_Show();
        } else {
          this.$Modal.error({
            title: "提示",
            content: "出错了"
          });
        }
      });
      // quickMatchServers.updateLocusListInfoForId(dd).then(res => {

      // })

      return;

      // console.log(this.AreaNewArray); // 选中的省级、地级城市参数
      // console.log(this.PersonneNewArray); // 选中的人员参数
      // console.log(this.RadioType); // 性别参数  男/1 --- 女/2
      // console.log(this.LowerLimit); // 匹配下限参数
      // console.log(this.Tolerance); // 容差参数
      // console.log(this.DateArray); // 时间参数
      //  this.$router.push("/ComSchedule");
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
    // 地区条件确认按钮 将设置的区域条件渲染在提交版块
    SendParamsToSubmitModel() {
      if (this.tableAreaBoolean) {
        let indexArea = Number(this.tableAreaIndex);
        this.AreaShow = false;
        let areaArr = [];
        let Areadata;
        let code = [];
        let name = [];
        for (let AreaItem of this.AreaList) {
          if (AreaItem.bOn == true) {
            // 将选中的地区赋值
            Areadata = AreaItem.regionalismName;
          }
          let Citydata = [];
          for (let CitysItem of this.CitysList) {
            // bon为true时代表选中状态，将选中的那组地区添加到数组
            if (CitysItem.bOn == true) {
              Citydata.push({
                city: CitysItem.regionalismName,
                cityCode: CitysItem.regionalismCode
              });
            }
          }
          // 拼接渲染数据
          let data = {
            province: Areadata,
            cityS: Citydata
          };
          // 生成新数组渲染页面/条件容器
          areaArr.push(data);
          // console.log(this.AreaNewArray,'112121212')
          // 重新给数组赋值
          code = areaArr[0].cityS.map(item => item.cityCode);
          name = areaArr[0].cityS.map(item => item.city);
          this.ComparisonTableData = this.ComparisonTableData.map(
            (item, index, value) => {
              if (index == indexArea) {
                item.district = code;
                item.diqu = name.join(",");
              }
              return item;
            }
          );
        }
      } else {
        this.AreaShow = false;
        this.AreaNewArray = []; // 页面地区条件渲染参数
        var Areadata;
        for (let AreaItem of this.AreaList) {
          if (AreaItem.bOn == true) {
            // 将选中的地区赋值
            Areadata = AreaItem.regionalismName;
          }
        }
        var Citydata = [];
        for (let CitysItem of this.CitysList) {
          // bon为true时代表选中状态，将选中的那组地区添加到数组
          if (CitysItem.bOn == true) {
            Citydata.push({
              city: CitysItem.regionalismName,
              cityCode: CitysItem.regionalismCode
            });
          }
        }
        // 拼接渲染数据
        var data = {
          province: Areadata,
          cityS: Citydata
        };
        // 生成新数组渲染页面/条件容器
        this.AreaNewArray.push(data);
        // console.log(this.AreaNewArray,'112121212')
        // 重新给数组赋值
        this.districtCode = this.AreaNewArray[0].cityS.map(
          item => item.cityCode
        );
        this.districtName = this.AreaNewArray[0].cityS.map(item => item.city);
        // console.log('diqucode', this.districtCode)
        // console.log('diquname', this.districtName)
        this.ComparisonTableData = this.ComparisonTableData.map(item => {
          item.district = this.districtCode;
          item.diqu = this.districtName.join(",");
          return item;
        });
      }
    },
    // 地区选择框点击事件
    Area_Show(event, index) {
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
          if(res.code == 1){
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
    SelectCity(i, item) {
      // 判断bon状态，为true时代表选中状态，为其添加class
      if (item.bOn == false) {
        item.bOn = true;
      } else {
        item.bOn = false;
      }
    },
    // // 省级城市切换
    SelectArea(index, item) {
        if (item.bOn == false) {
            item.bOn = true;
        } else {
            item.bOn = false;
        }
        // 切换省级城市时发送省级id 用于查询地级城市
        this.activeIndex = index;
        this.CitysInfo.code = item.regionalismCode; // 省级ID
        hybridServers.GetCitysData(this.CitysInfo).then(res => {
          // 为地级市赋值
          if(res.code == 1){
            this.CitysList = res.data;
          }
        });
    },
    // 容差监听事件
    OneOptionChange(val) {
      // console.log(val);
      // this.Tolerance = val;
    },
    // 匹配下限监听事件
    TwoOptionChange(val) {
      // console.log(val);
      // let mixsameCount = val
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.mixsameCount = val;
        return item;
      });
      // this.LowerLimit = val;
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
    // 设置条件中男女
    changePersonSex(params) {
      this.personSex = params;
      // console.log(this.personSex)
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.sex = this.personSex;
        return item;
      });
    }
  }
};
</script>

<style lang="less">
@import "../../assets/styles/case";
@import "../../assets/styles/break";
@import "../../assets/styles/hybrud";
@import "../../assets/styles/scrollbar";
.header {
  height: 100px;
  background: #fff;
  margin: 10px 0 6px 0;
  display: inline-block;
  width: 100%;
  .home_tab_title {
    margin: 30px 0;
  }
  .header_col {
    text-align: center;
    .tab_btn {
      width: 80%;
      height: 50px;
      border-radius: 30px;
      border: none;
      background: #cccccc;
      color: #333;
      font-size: 20px;
      font-weight: bold;
      outline: none;
      position: relative;
      cursor: pointer;
      .btn_img {
        position: absolute;
        right: -25px;
        width: 50px;
        top: -25px;
      }
    }
    .tab_btn.active {
      background: #007ef9;
      color: #fff;
    }
  }
}

.main {
  background: #fff;
  /* .part { */
  /* padding: 10px 15px; */
  .kit_name {
    // margin: 8px 0;
    .cube:nth-child(2n + 1) {
      display: inline-block;
      width: 14px;
      height: 14px;
      background: #007afd;
    }
    .cube:nth-child(2n + 2) {
      display: inline-block;
      width: 14px;
      height: 14px;
      background: #ffa100;
    }
    .font_weight {
      font-size: 16px;
      font-weight: bold;
      color: #333;
    }
  }
  /*}*/
}

.table_data {
  margin: 10px 0;
}
.table_data .ivu-input {
  height: 25px !important;
}
.table_data .ivu-table-small td {
  height: 20px !important;
}
.add_input {
  width: 60px;
}

.foot_btn {
  height: 60px;
  line-height: 60px;
  text-align: right;
  button {
    margin: 0 10px;
  }
}

.blue_border_btn {
  width: 130px;
  height: 40px;
  line-height: 40px;
  color: #007ef9;
  background: #fff;
  font-size: 16px;
  font-weight: bold;
  border: 2px solid #007ef9;
  border-radius: 6px;
  outline: none;
  cursor: pointer;
}

.blue_btn {
  width: 130px;
  height: 40px;
  line-height: 40px;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: #007ef9;
  border: none;
  border-radius: 6px;
  outline: none;
  cursor: pointer;
}

.red_btn {
  width: 130px;
  height: 40px;
  line-height: 40px;
  color: #f94c3d;
  font-size: 16px;
  font-weight: bold;
  background: #ffe2e0;
  border-radius: 6px;
  border: none;
  outline: none;
  cursor: pointer;
}

.content_box {
  padding: 10px 15px;
  min-height: 1000px;
}

.ivu-upload-list {
  display: none;
}

.table_border_right {
  border-right: 1px solid #ddd;
}

.chai_btn_two {
  position: absolute;
  right: 0;
}
.chai_btn {
  // margin-left: 350px;
  position: absolute;
  right: 20px;
  span {
    display: inline-block;
    width: 180px;
    height: 30px;
    line-height: 30px !important;
    // border:1px solid #0c81f5;
    text-align: center;
    border-radius: 3px;
    cursor: pointer;
    background: #edf6ff;
    font-weight: bold;
    color: #fff;
    background: #0c81f5;
  }
  span:hover {
    background: #fff;
    color: #0c81f5;
    border: 1px solid #0c81f5;
  }
}
.badge_p {
  position: absolute;
  top: -12px;
  right: 4px;
}

// 可编辑表格样式
.edit_table {
  width: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
  vertical-align: middle;
}
.geneVal {
  input {
    border: 1px solid #000;
  }
}
.codis-style .ivu-table-body {
  width: 100%;
  height: 500px !important;
  overflow-y: scroll;
  overflow-x: hidden;
}

// 滚动条样式
.codis-style .ivu-table-body::-webkit-scrollbar-track-piece {
  background-color: #f8f8f8;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条的宽度*/
.codis-style .ivu-table-body::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}
/*滚动条的设置*/
.codis-style .ivu-table-body::-webkit-scrollbar-thumb {
  background-color: #dddddd;
  background-clip: padding-box;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条鼠标移上去*/
.codis-style .ivu-table-body::-webkit-scrollbar-thumb:hover {
  background-color: #bbb;
}
</style>
