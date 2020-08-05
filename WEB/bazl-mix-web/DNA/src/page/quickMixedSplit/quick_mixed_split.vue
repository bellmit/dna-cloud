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
      </div>-->
      <div class="hybrid_box_middle" style="margin-top:10px">
        <div class="hybrid_middle_left">
          <h5>混合样本</h5>
          <li
            v-for="(item, index) in this.geneInfoListData"
            :key="index"
            style="width:100%;"
          >
            <div
              :class="{ leftBarStyle: leftBarClass == index }"
              @click="handelHybridData(item, index)"
            >
              <p>
                <Icon type="md-home hybrid_left" />
              </p>
              <span>{{ item.sampleNo }}</span>
              <span v-if="item.flag == 0" class="hybrid-M-span">M</span>
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
                      action="api_iLabSTRmix/mix/main/uploadGenePicture"
                      style="display:inline;"
                      :before-upload="handleUploadFt"
                      :on-success="handleSuccessFt"
                      :data="fengTuParams"
                      name="file"
                      :headers="headers"
                      type="drag"
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
                    匹配下限：
                    <input type="text" v-model="mixSameCount" />
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
                <div class="gwc_con clearfix" style="position:relative;">
                  <!-- <span>
                    <button class="parting_button" @click="handelStorage">拆分样本加入比对</button>
                  </span>
                  <span>
                    <button class="parting_button" @click="Details_show">查看拆分样本并下一步</button>
                    <p>13</p>
                  </span>-->
                  <!-- <button class="parting_button" @click="handelStorage">加入比对列表</button> -->
                  <button
                    class="btn btn-blue-bg"
                    @click="handelStorage"
                    style="position:absolute;right:30px;"
                  >
                    加入比对列表
                  </button>
                  <!-- <button class="parting_button" @click="handleJoinList">加入比对列表</button> -->
                </div>
              </div>
            </div>
          </div>
          <div class="hybrid_middle_table_bottom" style></div>
          <!-- 2020-4-24添加已知贡献者 -->
          <div class="h5_title">
            <Row style="width:100%">
              <Col span="12">
                <p></p>
                已知贡献者列表
              </Col>
              <Col span="12" style="text-align:right;">
                <div
                  @click.prevent="addContributorModalShow"
                  style="float:right"
                  class="addBtn"
                >
                  <Icon
                    type="md-add-circle"
                    style="font-size:20px;color:#ffac00;"
                  />
                  <span style="cursor:pointer;margin:0;">添加已知贡献者</span>
                </div>
              </Col>
            </Row>
          </div>
          <div style="text-align:center;padding:10px;">
            <Table
              border
              :columns="contributorCol"
              :data="contributorData"
              class="light-blue-table table-scroll"
              size="small"
              style="margin:8px 0"
            ></Table>
          </div>
          <!-- 2019-12-18添加设置条件 -->
          <div class="h5_title">
            <div>
              <p></p>
              设置条件
            </div>
          </div>
          <div class="margin-left" style="padding-left:30px;">
            <div class>
              <span class="title-span-style">性 &nbsp &nbsp &nbsp 别</span>
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
              </span>-->
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
          <!--原表格  -->
          <div class="h5_title clearfix">
            <div>
              <p></p>
              比对列表
              <!-- <span style="color:rgba(255,90,86,1) !important">比中总数:</span>
              <span class="span_" style="color:rgba(255,90,86,1) !important">{{total}}</span>-->
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
          </div>-->
          <div style="text-align:center;padding:10px;">
            <Table
              border
              stripe
              class="light-blue-table table-scroll"
              height="300"
              :columns="ComparisonTableCol"
              :data="ComparisonTableData"
              size="small"
            ></Table>
          </div>
        </div>
      </div>
      <div class="footer_btn">
        <button @click="handleSubmit">提交比对</button>
      </div>
    </div>

    <Modal v-model="hybridImgModal" width="1104">
      <div>
        <img :src="hybridImg ? hybridImg : defaultImg" style="width:100%;" />
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
              <!-- @click="SelectArea(AreaIndex,AreaItem)"  -->
              <div
                :class="{ active: AreaItem.flag == true }"
                @click="SelectArea(AreaIndex, AreaItem)"
              >
                <!-- :class="activeClass == AreaIndex ? 'active':''" || :class="{'active':activeIndex == AreaIndex}"-->
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
    <!-- 人员条件弹窗 -->
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
    <!-- 查看按钮 -->
    <!-- <Modal id="model_con" v-model="SeeDetailsModel">
      <p slot="header">
        <Icon type="ios-information-circle"></Icon>
        <span>详情信息</span>
      </p>
      <div style="text-align:center">
        <Table :columns="SeeDetailsCol" :data="SeeDetailsData"></Table>
      </div>
    </Modal>-->
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
            <span
              >当前第{{ schedulePage }}页/共{{ scheduleCount }}页/共{{
                scheduleAllCount
              }}条信息</span
            >
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
    <Modal
      v-model="addContributorModal"
      width="880"
      class="light-blue-modal"
      :closable="false"
    >
      <div class="header">添加已知贡献者</div>
      <div class="modal-content">
        <Row style="padding:4px 0 10px;border-bottom:1px solid #ddd;">
          <span class="label">样本编号</span>
          <Input type="text" style="width:200px" v-model="mainSampleNo"></Input>
          <button
            class="btn btn-blue-bg"
            style="vertical-align:middle;"
            @click.prevent="showDataBaseSearchModal"
          >
            从数据库查询
          </button>
          <button
            class="btn btn-blue-border"
            @click.prevent="showCodisModal"
            style="vertical-align:middle;"
          >
            从codis导入
          </button>
        </Row>
        <Row style="padding:10px 0;">
          <p style="color:#999;margin-bottom:6px;">
            <Icon
              type="md-alert"
              style="color: rgb(255, 172, 0);font-size:16px;"
            />手工录入,请先填写样本编号和样本名称,再选择试剂盒~
          </p>
          <span class="label">样本编号</span>
          <Input
            type="text"
            style="width:200px;margin-right:15px;"
            :readonly="disEdit"
            v-model="addTorSampleNo"
            :class="{ 'warning-text': warningText }"
          ></Input>
          <span class="label">样本名称</span>
          <Input
            type="text"
            style="width:200px;margin-right:15px;"
            :readonly="disEdit"
            v-model="addTorSampleName"
          ></Input>
          <span class="label">试剂盒</span>
          <Select
            class="item-input"
            style="width:200px"
            v-model="addTorPanelCode"
            @on-change="changeIndexKit"
            :disabled="disEdit"
          >
            <Option v-for="kit in kitLocusList" :key="kit.id" :value="kit.id">{{
              kit.name
            }}</Option>
          </Select>
        </Row>
        <Table
          border
          :columns="addContributorCol"
          :data="addContributorData"
          class="light-blue-table"
          size="small"
          style="margin:8px 0"
          max-height="600"
        ></Table>
        <Row class="btn-row">
          <button class="btn btn-blue-bg" @click="addContributorToTable">
            确认
          </button>
          <button
            class="btn btn-blue-border"
            @click="addContributorModal = false"
          >
            关闭
          </button>
        </Row>
      </div>
    </Modal>
    <Modal
      v-model="lookContributorModal"
      width="880"
      class="light-blue-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">查看已知贡献者</div>
      <div class="modal-content">
        <Row style="padding:10px 0;">
          <span class="label">样本编号</span>
          <Input
            type="text"
            style="width:200px;margin-right:15px;"
            readonly
            v-model="lookPersonForm.sampleNo"
          ></Input>
          <span class="label">样本名称</span>
          <Input
            type="text"
            style="width:200px;margin-right:15px;"
            readonly
            v-model="lookPersonForm.sampleName"
          ></Input>
          <span class="label">试剂盒</span>
          <Select
            class="item-input"
            style="width:200px"
            v-model="lookPersonForm.panelId"
            @on-change="changeIndexKit"
            disabled="disabled"
          >
            <Option v-for="kit in kitLocusList" :key="kit.id" :value="kit.id">{{
              kit.name
            }}</Option>
          </Select>
        </Row>
        <Table
          border
          :columns="lookContributorCol"
          :data="lookPersonForm.geneInfo"
          class="light-blue-table"
          size="small"
          style="margin:8px 0"
          max-height="600"
        ></Table>
        <Row class="btn-row">
          <button
            class="btn btn-blue-border"
            @click="closeLookContributorModal"
          >
            关闭
          </button>
        </Row>
      </div>
    </Modal>
    <Modal
      v-model="importCodisModal"
      width="1000"
      class="light-blue-modal"
      :closable="false"
    >
      <div class="header">Codis文件导入</div>
      <div class="modal-content">
        <Row>
          <Col span="18">
            <Input
              type="text"
              readonly
              style="width:400px"
              v-model="fileName"
              :title="fileName"
            ></Input>
            <Upload
              action="api_iLabSTRmix/mix/codis/uploadCodis"
              style="display:inline;"
              :before-upload="handleUpload"
              :on-success="handleSuccess"
              :data="codisParams"
              name="codisFile"
              :headers="headers"
              multiple
              ref="upload"
            >
              <button
                class="btn btn-blue-border"
                style="vertical-align:middle;"
              >
                选择文件
              </button>
            </Upload>
            <button class="btn btn-blue-border" @click="isUpload">
              上传文件
            </button>
          </Col>
          <Col span="6" style="text-align:right;">
            <Icon
              type="ios-alert"
              style="font-size:20px;color:#ffa500;height:35px;line-height:35px;"
            />
            <span
              class="font-size font-weight"
              style="height:35px;line-height:35px;"
              >列表只显示单一样本</span
            >
          </Col>
        </Row>
        <Table
          border
          :columns="codisCol"
          :data="codisData"
          class="light-blue-table"
          size="small"
          style="margin:8px 0"
          max-height="600"
        ></Table>
        <Row class="btn-row">
          <button class="btn btn-blue-bg" @click.prevent="sureChooseCodisData">
            确认
          </button>
          <button class="btn btn-blue-border" @click="importCodisModal = false">
            关闭
          </button>
        </Row>
      </div>
    </Modal>
    <Modal
      v-model="dataBaseSearchModal"
      width="900"
      class="light-blue-modal"
      :closable="false"
    >
      <div class="header">数据库检索</div>
      <div class="modal-content">
        <Table
          border
          :columns="dataBaseSearchCol"
          :data="dataBaseSearchData"
          class="light-blue-table"
          size="small"
          style="margin:8px 0"
          max-height="600"
        ></Table>
        <Row class="btn-row">
          <button class="btn btn-blue-bg" @click.prevent="sureChooseDataSearch">
            确认
          </button>
          <button
            class="btn btn-blue-border"
            @click="dataBaseSearchModal = false"
          >
            关闭
          </button>
        </Row>
      </div>
    </Modal>
    <div v-show="loadingShow" class="loading_con">
      <div class="arc"></div>
      <h1>
        <span>LOADING</span>
      </h1>
    </div>
    <ModalDetail ref="modal" :modalDetail="modalDetail"></ModalDetail>
  </div>
</template>
<script>
import hybridServers from "../../servers/hybridServers";
import ModalDetail from "../../components/ModalDetail";
import homeServers from "../../servers/homeServer";
import userServers from "../../servers/userServers";
import caseServer from "../../servers/caseServer";
import quickMatchServers from "../../servers/quickMatch";
import upload from "../../servers/upload";
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
      PersonIndex: 0,
      citysIndex: 0,
      warningText: false,
      contributorCol: [
        {
          title: "序号",
          type: "index",
          width: 80,
          align: "center"
        },
        {
          title: "样本编号",
          key: "sampleNo",
          align: "center"
        },
        {
          title: "样本名称",
          key: "sampleName",
          align: "center"
        },
        {
          title: "位点个数",
          key: "geneCount",
          align: "center"
        },
        {
          title: "操作",
          key: "action",
          width: 120,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("span", { class: "hover-text" }, [
                h("Icon", {
                  class: "look_eye",
                  props: {
                    type: "md-eye",
                    size: "20",
                    color: "#FDAB2A"
                  }
                }),
                h(
                  "strong",
                  {
                    class: "text",
                    style: {
                      color: "#FDAB2A",
                      display: "none"
                    },
                    on: {
                      click: () => {
                        this.lookContributorModal = true;
                        if (typeof params.row.geneInfo == "string") {
                          params.row.geneInfo = JSON.parse(params.row.geneInfo);
                        }
                        this.lookPersonForm = Object.assign({}, params.row);
                      }
                    }
                  },
                  "查看"
                )
              ]),
              h(
                "span",
                { class: "hover-text", style: { marginLeft: "10px" } },
                [
                  h("Icon", {
                    class: "look_eye",
                    props: {
                      type: "ios-trash",
                      size: "20",
                      color: "#ff624e"
                    }
                  }),
                  h(
                    "strong",
                    {
                      class: "text",
                      style: {
                        color: "#ff624e",
                        display: "none"
                      },
                      on: {
                        click: () => {
                          this.$Modal.confirm({
                            title: "提醒",
                            content: "确定要删除吗?此操作不可逆",
                            okText: "确定",
                            cancelText: "我再想想",
                            onOk: () => {
                              if (params.row.id) {
                                let info = {
                                  id: params.row.id
                                };
                                quickMatchServers
                                  .removeContributor(info)
                                  .then(res => {
                                    if (res.code == 1) {
                                      this.$Modal.success({
                                        title: "恭喜",
                                        content: "删除成功~"
                                      });
                                      this.contributorData.splice(
                                        params.index,
                                        1
                                      );
                                    } else {
                                      this.$Modal.error({
                                        title: "抱歉",
                                        content: "删除失败~"
                                      });
                                    }
                                  });
                              } else {
                                this.contributorData.splice(params.index, 1);
                              }
                            }
                          });
                        }
                      }
                    },
                    "删除"
                  )
                ]
              )
            ]);
          }
        }
      ],
      contributorData: [],
      addContributorModal: false,
      lookContributorModal: false,
      addContributorCol: [
        {
          title: "序号",
          key: "index",
          width: 80,
          align: "center"
        },
        {
          title: "基因座名称",
          key: "name",
          align: "center"
        },
        {
          title: "基因位点",
          render: (h, params) => {
            return h("div", [
              h("Input", {
                style: {
                  width: "60px",
                  marginRight: "10px"
                },
                props: {
                  value: params.row.value[0] ? params.row.value[0] : "",
                  readonly: this.disEdit
                },
                on: {
                  "on-blur": event => {
                    this.addContributorData[params.index].value[0] =
                      event.target.value;
                  }
                }
              }),
              h("Input", {
                style: {
                  width: "60px"
                },
                props: {
                  value: params.row.value[1] ? params.row.value[1] : "",
                  readonly: this.disEdit
                },
                on: {
                  "on-blur": event => {
                    this.addContributorData[params.index].value[1] =
                      event.target.value;
                  }
                }
              })
            ]);
          }
        }
      ],
      addContributorData: [],
      addContributorDataBox: [],
      addTorPanelCode: "",
      addTorSampleNo: "",
      addTorSampleName: "",
      lookContributorCol: [
        {
          title: "序号",
          width: 80,
          type: "index",
          align: "center"
        },
        {
          title: "基因座名称",
          key: "name",
          align: "center"
        },
        {
          title: "基因位点",
          render: (h, params) => {
            return h("div", [
              h("Input", {
                style: {
                  width: "60px",
                  marginRight: "10px"
                },
                props: {
                  value: params.row.value[0],
                  readonly: true
                }
              }),
              h("Input", {
                style: {
                  width: "60px",
                  marginRight: "10px"
                },
                props: {
                  value: params.row.value[1],
                  readonly: true
                }
              })
            ]);
          }
        }
      ],
      lookContributorData: [{}, {}, {}, {}],
      lookPersonForm: {},
      mainSampleNo: "",
      importCodisModal: false,
      codisCol: [
        {
          title: "序号",
          type: "index",
          width: 80,
          align: "center"
        },
        {
          title: "样本编号",
          key: "sampleNo",
          align: "center"
        },
        {
          title: "位点个数",
          key: "geneCount",
          align: "center"
        },
        {
          title: "选择",
          key: "chose",
          width: 80,
          align: "center",
          render: (h, params) => {
            let id = params.row.sampleNo;
            let flag = false;
            if (this.dataCodisId === id) {
              flag = true;
            } else {
              flag = false;
            }
            let self = this;
            return h("div", [
              h("Radio", {
                props: {
                  value: flag
                },
                on: {
                  "on-change": () => {
                    self.dataCodisId = id;
                    this.chooseDataCodis = [];
                    this.chooseDataCodis.push(
                      this.codisData.find(x => x.sampleNo == this.dataCodisId)
                    );
                  }
                }
              })
            ]);
          }
        }
      ],
      codisData: [],
      dataBaseSearchModal: false,
      dataBaseSearchId: 0,
      dataCodisId: 0,
      chooseDataBase: [],
      chooseDataCodis: [],
      dataBaseSearchCol: [
        {
          title: "序号",
          type: "index",
          width: 80,
          align: "center"
        },
        {
          title: "样本编号",
          key: "sampleNo",
          align: "center"
        },
        {
          title: "样本名称",
          key: "sampleName",
          align: "center"
        },
        {
          title: "样本类型",
          key: "sampleTypeName",
          align: "center"
        },
        {
          title: "案件编号",
          key: "caseNo",
          align: "center"
        },
        {
          title: "案件名称",
          key: "caseName",
          align: "center"
        },
        {
          title: "试剂盒",
          key: "panelName",
          align: "center"
        },
        {
          title: "位点个数",
          key: "geneCount",
          align: "center"
        },
        {
          title: "选择",
          width: 80,
          align: "center",
          render: (h, params) => {
            let id = params.row.sampleNo;
            let flag = false;
            if (this.dataBaseSearchId == id) {
              flag = true;
            } else {
              flag = false;
            }
            let self = this;
            return h("div", [
              h("Radio", {
                props: {
                  value: flag
                },
                on: {
                  "on-change": () => {
                    self.dataBaseSearchId = id;
                    this.chooseDataBase = [];
                    this.chooseDataBase.push(
                      this.dataBaseSearchData.find(
                        x => x.sampleNo == this.dataBaseSearchId
                      )
                    );
                  }
                }
              })
            ]);
          }
        }
      ],
      dataBaseSearchData: [{}, {}, {}],
      fileName: "",
      codisParams: {
        types: "1" //查询单一
      },
      fengTuParams: {
        id: ""
        // id: '8a32811d3342ca3f0133904e245e0fa4'
      },
      headers: {},
      kitId: "",
      kitLocusList: [],
      disEdit: false,
      mixedSampleGeneId: "", //样本混合ID
      sampleId: "", //样本ID
      reagentName: "", //试剂盒名称
      contributorCount: "", //贡献者人数
      //genePicturePath: 'http://dummyimage.com/720x300',  //基因图谱上传后保存http地址
      genePicturePath: "", //基因图谱上传后保存http地址
      sampleName: "", //样本名称

      // 查看详情 model 所需参数
      modalDetail: {
        modalType: 2,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: ""
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
        //   render: (h, params) =>
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
      ScheduleData: [],
      // 地级市数据
      CitysList: [],
      // 人员条件数据
      PersonnelList: [],
      // 二级人员数据
      personSecondList: [],
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
          width: 65
        },
        {
          title: "样本编号",
          key: "sampleNo"
        },
        {
          title: "分型",
          key: "queueType",
          width: 65,
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
          width: 65,
          render: (h, params) => {
            let sexVal = "";
            if (params.row.sex == '["1"]') {
              sexVal = "男";
            } else if (params.row.sex == '["2"]') {
              sexVal = "女";
            } else if (params.row.sex == '["1","2"]') {
              sexVal = "男,女";
            }
            return h(
              "span",
              {
                prop: {}
              },
              sexVal
            );
          }
        },
        {
          title: "地区",
          width: 200,
          key: "districtName",
          render: (h, params) => {
            return h("p", [
              h(
                "Tooltip",
                {
                  props: { placement: "right-end", transfer: true }
                },
                [
                  h(
                    "p",
                    {
                      style: {
                        display: "inline-block",
                        width: params.column._width * 0.6 + "px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap"
                      }
                    },
                    params.row.districtName
                  ),
                  h(
                    "p",
                    {
                      slot: "content",
                      style: { whiteSpace: "normal", wordBreak: "break-all" }
                    },
                    params.row.districtName
                  )
                ]
              )
            ]);
          }
        },
        {
          title: "人员类型",
          key: "personTypeName",
          width: 200,
          render: (h, params) => {
            return h("p", [
              h(
                "Tooltip",
                {
                  props: { placement: "right-end", transfer: true }
                },
                [
                  h(
                    "p",
                    {
                      style: {
                        display: "inline-block",
                        width: params.column._width * 0.6 + "px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap"
                      }
                    },
                    params.row.personTypeName
                  ),
                  h(
                    "p",
                    {
                      slot: "content",
                      style: { whiteSpace: "normal", wordBreak: "break-all" }
                    },
                    params.row.personTypeName
                  )
                ]
              )
            ]);
          }
        },
        {
          title: "创建时间",
          key: "datetime"
        },
        // {
        //   title: "容差",
        //   key: "condition",
        // },
        {
          title: "容差",
          key: "condition",
          width: 65
        },
        {
          title: "匹配下限",
          key: "mixsameCount",
          width: 90
        },
        {
          title: "查看详情",
          key: "action",
          width: 90,
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
                      this.$refs.modal.modalShow = true;
                      if (params.row.queueType == 1) {
                        this.modalDetail.modalTypeHeader = "混合样本分型";
                      } else if (params.row.queueType == 2) {
                        this.modalDetail.modalTypeHeader = "拆分样本分型";
                      }
                      this.modalDetail.modalInfo =
                        params.row.geneInfos.resultList;
                      this.modalDetail.modalTitle = params.row.sampleNo;
                      this.modalDetail.modalData = params.row;
                    }
                  }
                },
                "查看"
              )
            ]);
          }
        },
        {
          title: "操作",
          key: "action",
          width: 90,
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: event => {
                      let delId = {
                        id: params.row.id
                      };
                      quickMatchServers
                        .deleteLocusListInfoForId(delId)
                        .then(res => {
                          if (res.code == 1) {
                            this.ComparisonTableData.splice(params.index, 1);
                          } else {
                            this.$Message.error("删除失败~");
                          }
                        });
                    }
                  }
                },
                "删除"
              )
            ]);
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
      geneInfoListData: [], //从前一页带入的list数据集合
      currentSampleNo: "", //当前选中的sampleNo
      currentMixedId: "",
      personSex: ["1", "2"],
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
      AreaNewArrayCode: [], //向后端传地区参数
      AreaNewArrayName: [],
      PersonneNewArray: [], // 人条件渲染参数 html
      PersonneNewArrayCode: [], //向后端传人员参数
      PersonneNewArrayName: [],
      CitysInfo: "", // 省级城市ID，根据此条件查询省级下面地级市
      RadioType: "", // 性别
      LowerLimit: "", // 匹配下限
      Tolerance: "", // 容差
      DateArray: [], // 时间
      parGenIfo: [],
      Comtype: null,
      arrList: [],
      uploadList: [],
      serveNo: []
    };
  },
  created() {
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
    this.headers.Authorization = localStorage.getItem("token");
    this.getKitList(); //获取试剂盒列表
    // 页面初始数据
    // this.handelList();
    this.geneInfoListData = this.$route.params.geneInfoData;
    for (let item of this.geneInfoListData) {
      item.flag = 0;
    }
    // 2020年4月28日新增
    if (this.geneInfoListData[0].contributorData) {
      this.contributorData = this.geneInfoListData[0].contributorData;
    }
    this.mixedSampleGeneId = this.geneInfoListData[0].mixedSampleId;
    this.fengTuParams.id = this.geneInfoListData[0].mixedSampleId;
    this.sampleId = this.geneInfoListData[0].sampleId;
    this.reagentName = this.geneInfoListData[0].reagentName;
    this.contributorCount = this.geneInfoListData[0].contributorCount;
    this.sampleName = this.geneInfoListData[0].sampleName;
    this.hybridImg = this.geneInfoListData[0].genePicture;
    // 2019年12月覆盖
    this.parGenIfo = this.geneInfoListData[0].geneInfoList;
    this.result = this.geneInfoListData[0].geneInfoList;
    this.currentSampleNo = this.geneInfoListData[0].sampleNo;
    // this.currentMixedId = this.geneInfoListData[0].mixedSampleId
    // 添加flag
    for (var i = 0; i < this.result.length; i++) {
      for (var k = 0; k < this.result[i].allele.length; k++) {
        this.result[i].allele[k].flag = false;
      }
    }
    if (
      this.geneInfoListData[0].tableData != "" ||
      this.geneInfoListData[0].tableData != undefined ||
      this.geneInfoListData[0].tableData != null
    ) {
      this.ComparisonTableData = this.geneInfoListData[0].tableData;
    }
  },
  methods: {
    // 2020年4月新增
    addContributorModalShow() {
      // 添加贡献者模态框显示
      this.addContributorModal = true;
      this.mainSampleNo = "";
      this.addTorPanelCode = "";
      this.addTorSampleNo = "";
      this.addTorSampleName = "";
      this.addContributorDataBox = [];
      this.addContributorData = [];
      this.dataBaseSearchId = 0;
      this.dataCodisId = 0;
      this.disEdit = false;
    },
    // codis文件上传之前
    handleUpload(file) {
      this.uploadList.push(file);
      let arr = [];
      this.uploadList.forEach(item => {
        arr.push(item.name);
      });
      this.fileName = arr.join("，");
      return false;
    },
    // 导入 钉子9
    isUpload() {
      if (this.uploadList.length === 0) {
        this.$Message.error("未选择上传文件");
        return false;
      }
      var formData = new FormData();
      for (let i = 0; i < this.uploadList.length; i++) {
        let item = this.uploadList[i];
        formData.append("codisFile", this.uploadList[i]);
      }
      formData.append("types", "1");
      upload.codisUpload(formData).then(res => {
        // codis文件上传成功后
        if (res.code == 1) {
          this.codisData = res.data;
          this.uploadList = [];
        } else {
          this.$Modal.error({
            title: "抱歉",
            content: res.errorMessage
          });
        }
      });
    },
    handleSuccess(res) {},
    handleUploadFt(file) {
      // 峰图上传前处理函数
    },
    handleSuccessFt(res) {
      // 峰图上传成功的回调
      if (res.code == 1) {
        this.hybridImg = res.result;
        // this.genePicturePath = res.result
      }
    },
    sureChooseDataSearch() {
      //对检索选择的临时数据进行处理然后渲染页面
      if (this.chooseDataBase.length > 0) {
        this.dataBaseSearchModal = false;
        this.addContributorDataBox = this.chooseDataBase;
        this.addContributorDataBox.forEach(item => {
          item.geneInfo = JSON.parse(item.geneInfo);
          item.geneInfo.forEach(i => {
            i.value = i.value.split("/");
          });
        });
        this.addContributorData = this.addContributorDataBox[0].geneInfo;
        this.addTorPanelCode = this.addContributorDataBox[0].panelId;
        this.addTorSampleNo = this.addContributorDataBox[0].sampleNo;
        this.addTorSampleName = this.addContributorDataBox[0].sampleName;
        this.disEdit = true;
      } else {
        this.$Modal.error({
          title: "抱歉",
          content: "请先选择要添加的样本~"
        });
      }
    },
    sureChooseCodisData() {
      //对codis导入选择的临时数据进行处理然后渲染页面
      if (this.chooseDataCodis.length > 0) {
        this.importCodisModal = false;
        this.addContributorDataBox = this.chooseDataCodis;
        this.addContributorDataBox.forEach(item => {
          item.geneInfo = [];
          item.geneInfoList.forEach(i => {
            let newGeneInfo = {};
            newGeneInfo.name = i.markerName;
            newGeneInfo.value = [];
            i.allele.forEach(j => {
              newGeneInfo.value.push(j.name);
            });
            item.geneInfo.push(newGeneInfo);
          });
        });
        this.addContributorData = this.addContributorDataBox[0].geneInfo;
        // this.addTorPanelCode = this.addContributorDataBox[0].panelId
        this.addTorSampleNo = this.addContributorDataBox[0].sampleNo;
        // this.addTorSampleName = this.addContributorDataBox[0].sampleName
        this.disEdit = true;
      } else {
        this.$Modal.error({
          title: "抱歉",
          content: "请先选择要添加的样本~"
        });
      }
    },
    showDataBaseSearchModal() {
      // 显示数据库检索模态框
      if (this.mainSampleNo.length > 5) {
        this.dataBaseSearchData = [];
        this.addTorPanelCode = "";
        this.addTorSampleNo = "";
        this.addTorSampleName = "";
        this.addContributorData = [];
        let dd = {
          sampleNo: this.mainSampleNo
        };
        this.loadingShow = true;
        hybridServers
          .getDataBaseBySampleNo(dd)
          .then(res => {
            this.loadingShow = false;
            this.dataBaseSearchModal = true;
            this.dataBaseSearchData = res.result;
          })
          .catch(error => {
            this.loadingShow = false;
          });
      } else {
        this.$Modal.info({
          title: "抱歉",
          content: "请至少输入6位检材编号"
        });
      }
    },
    showCodisModal() {
      // 显示codis导入模态框
      this.codisData = [];
      this.importCodisModal = true;
      this.fileName = "";
      this.addTorPanelCode = "";
      this.addTorSampleNo = "";
      this.addTorSampleName = "";
      this.addContributorData = [];
    },
    addContributorToTable() {
      // 添加贡献者数据到外层表格
      if (!this.addContributorDataBox[0].geneCount) {
        this.addContributorDataBox[0].geneCount = this.addContributorDataBox[0].geneInfo.length;
      }
      let self = this;
      if (this.addContributorDataBox.length > 0) {
        let obj = this.addContributorDataBox[0];
        this.addContributorModal = false;
        this.contributorData.push(obj);
      } else {
        this.$Modal.info({
          title: "抱歉",
          content: "暂无可添加的数据~"
        });
      }
    },
    changeIndexKit(value) {
      // 更换试剂盒时查询基因座
      // if(this.addTorSampleNo == '' || this.addTorSampleName == ''){
      //   this.$Modal.info({
      //     title: '提醒',
      //     content: '请先完善样本编号和样本名称~'
      //   })
      //   return false
      // }
      if (this.addTorSampleNo == "") {
        this.warningText = true;
        return false;
      }
      this.warningText = false;
      let info = {
        reagentKitId: value
      };
      quickMatchServers.getGeneNameByKitId(info).then(res => {
        let list = [];
        res.result.forEach(item => {
          let geneObj = {};
          geneObj.name = item.locusName;
          geneObj.value = ["", ""];
          list.push(geneObj);
        });
        let manualObj = {};
        manualObj.geneInfo = list;
        this.addContributorData = manualObj.geneInfo;
        manualObj.panelId = this.addTorPanelCode;
        manualObj.sampleNo = this.addTorSampleNo;
        manualObj.sampleName = this.addTorSampleName;
        this.addContributorDataBox.push(manualObj);
      });
    },
    getKitList() {
      // 获取试剂盒列表
      if (
        sessionStorage.getItem("kitLocusList") != undefined &&
        sessionStorage.getItem("kitLocusList")
      ) {
        const arr = sessionStorage.getItem("kitLocusList");
        this.kitLocusList = JSON.parse(arr);
      } else {
        quickMatchServers.getQuerykitLocusListInfo().then(res => {
          this.kitLocusList = res.result;
          sessionStorage.setItem("kitLocusList", JSON.stringify(res.result));
        });
      }
    },
    handleRemovePic() {
      // 删除峰图
      let info = {
        id: this.mixedSampleGeneId
      };
      quickMatchServers.removePic(info).then(res => {
        if (res.result) {
          this.hybridImg = null;
        }
      });
    },
    closeLookContributorModal() {
      // 关闭查看贡献者模态框,清空数据
      Object.keys(this.lookPersonForm).forEach(key => {
        this.lookPersonForm[key] = "";
      });
      this.lookPersonForm.geneInfo = [];
      this.lookContributorModal = false;
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
        if (this.PersonneNewArray.length == 0) {
          this.PersonIndex = 0;
          this.PersonnelList[0].flag = true;
          this.personSecondList = res.result[0].val;
          this.personSecondList.forEach(item => {
            item.isChecked = true;
          });
        } else {
          this.PersonnelList.forEach((item, index) => {
            this.PersonneNewArray.forEach((j, h) => {
              if (item.id === j.id) {
                item.flag = true;
                item.val.forEach((i, k) => {
                  if (i.dictKey === j.PerCode) {
                    i.isChecked = true;
                  }
                });
              }
            });
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
    },
    // 弹窗取消事件
    DisplayModal() {
      this.SendComparisonModal = false;
      this.PersonneNewArray = [];
      this.AreaNewArray = [];
    },
    // 提交比对参数
    SendComparisonParams() {
      this.SendComparisonModal = false;
    },
    // 地区选择框点击事件
    Area_Show() {
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
          this.citysIndex = 0;
          this.AreaList = res.data;
          if (this.AreaNewArray.length == 0) {
            this.citysIndex = 0;
            this.AreaList[0].flag = true;
            this.CitysList = res.data[0].city;
            this.CitysList.forEach(item => {
              item.isChecked = true;
            });
          } else {
            this.AreaList.forEach((item, index) => {
              this.AreaNewArray.forEach((j, h) => {
                if (item.id_code === j.id) {
                  item.flag = true;
                  item.city.forEach((i, k) => {
                    if (i.regionalismCode === j.cityCode) {
                      i.isChecked = true;
                    }
                  });
                }
              });
            });
          }
        }
      });
    },
    // 地级城市选择 二级
    SelectCity(item, index) {
      let i = this.AreaList[this.citysIndex].city[index];
      if (i.isChecked) {
        i.isChecked = false;
      } else {
        i.isChecked = true;
      }
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
    // 地区条件确认按钮 将设置的区域条件渲染在提交版块
    SendParamsToSubmitModel() {
      this.serveNo = [];
      this.AreaShow = false;
      this.AreaNewArray = []; // 页面地区条件渲染参数
      this.AreaList.forEach(i => {
        if (i.flag) {
          this.serveNo.push(i.personlism.regionalismCode);
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
      this.Tolerance = val;
    },
    // 匹配下限监听事件
    TwoOptionChange(val) {
      this.LowerLimit = val;
    },
    // 单选框监听事件
    RadioChange(val) {
      this.RadioType = val;
    },
    // 选择日期监听事件
    DateChange(val) {
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
      if (this.Comtype == 0) {
        this.$Modal.info({
          title: "提示",
          content: "请先提交比对！"
        });
        return;
      }
      this.contributorData = [];
      this.hybridImg = null;
      if (this.arrList != 0) {
        for (let data of this.arrList) {
          if (data.id == item.mixedSampleId) {
            this.contributorData = data.arr;
            this.hybridImg = data.img;
          }
        }
      } else {
        this.contributorData = [];
        this.hybridImg = null;
      }
      this.ComparisonTableData = [];
      this.AreaNewArray = [];
      this.PersonneNewArray = [];
      this.LowerLimit = "";
      this.ModelTwoOption = "";
      $('input[name="lowLight"]').prop("checked", false);
      $(".parting_box li p span").removeClass("chooseRed");
      this.allData = []; // 11/12
      this.result = this.geneInfoListData[index].geneInfoList;
      this.leftBarClass = index;
      this.currentSampleNo = this.geneInfoListData[index].sampleNo;
      this.mixedSampleGeneId = this.geneInfoListData[index].mixedSampleId;
      this.fengTuParams.id = this.geneInfoListData[index].mixedSampleId;
      this.sampleId = this.geneInfoListData[index].sampleId;
      this.reagentName = this.geneInfoListData[index].reagentName;
      this.contributorCount = this.geneInfoListData[index].contributorCount;
      this.sampleName = this.geneInfoListData[index].sampleName;
      // 添加flag
      for (var i = 0; i < this.result.length; i++) {
        for (var k = 0; k < this.result[i].allele.length; k++) {
          this.result[i].allele[k].flag = false;
        }
      }
    },
    // 页面初始数据
    handelList() {
      this.loadingShow = true;
      let hybridInfo = {
        caseId: this.caseId
      };
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
          this.result =
            res.data.resuleList[0][0].mixedSampleGeneVo.resultGeneInfo.resultList;
          this.leftTitle = res.data.resuleList[0];
          this.hybridImg =
            res.data.resuleList[0][0].mixedSampleGeneVo.entity.genePicture;
          this.total = res.data.resuleList[0][0].singleSampleCount;
          this.mixSameCount = res.data.mixSameCount;
          this.sampleId = res.data.resuleList[0][0].entity.id;
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
      this.ListNumType = 1;
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
    },
    // 加入比对列表
    handelStorage() {
      var resnum = 0; // 判断条件
      // 判断设置条件中是否有未选中
      if (this.AreaNewArray.length == 0 || this.PersonneNewArray.length == 0) {
        this.$Modal.info({
          title: "提示",
          content: "请在设置条件中设置选项~"
        });
        return;
      }
      if (!this.LowerLimit) {
        this.$Modal.info({
          title: "提示",
          content: "请在设置条件中设置选项~"
        });
        return;
      }
      if (!this.ModelOneOption) {
        this.$Modal.info({
          title: "提示",
          content: "请在设置条件中设置选项~"
        });
        return;
      }
      // 处理地区
      this.AreaNewArrayCode = this.AreaNewArray.map(item => item.cityCode);
      this.AreaNewArrayName = this.AreaNewArray.map(item => item.city);
      // 人员处理
      this.PersonneNewArrayCode = this.PersonneNewArray.map(
        item => item.PerCode
      );
      this.PersonneNewArrayName = this.PersonneNewArray.map(
        item => item.Personnel
      );
      if (this.AreaNewArrayName.length > 1) {
        this.AreaNewArrayName = this.AreaNewArrayName.join(",");
      } else {
        this.AreaNewArrayName = this.AreaNewArrayName.join("");
      }
      if (this.PersonneNewArrayName.length > 1) {
        this.PersonneNewArrayName = this.PersonneNewArrayName.join(",");
      } else {
        this.PersonneNewArrayName = this.PersonneNewArrayName.join("");
      }
      // 判断当前是否有主动选择的基因位点
      for (let resItem of this.result) {
        for (let allitem of resItem.allele) {
          if (allitem.flag == true) {
            resnum += 1;
          }
        }
      }
      if (resnum != 0) {
        if ($(".chooseRed").parents("li").length < 6) {
          this.$Modal.confirm({
            title: "提醒",
            content: "选中基因位点少于6位，是否继续加入比对?",
            okText: "确定",
            cancelText: "取消",
            onOk: () => {
              setTimeout(() => {
                this.handsendParams(2);
              }, 500);
            }
          });
        } else {
          this.handsendParams(2);
        }
      } else {
        this.handsendParams(1);
      }
    },
    // 加入比对 参数处理
    handsendParams(type) {
      var storageInfo; // 已拆分单个样本参数
      var strAllData;
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
        // 清空style???
        var oSpan = $(".parting_box p span");
        for (var i = 0; i < oSpan.length; i++) {
          oSpan[i].style.color = "";
        }
        // 参数赋值
        strAllData = JSON.stringify(this.allData);
        let geneInfo = JSON.stringify(this.result);
        storageInfo = {
          queueType: "2",
          condition: this.ModelOneOption,
          mixedSampleGeneId: this.mixedSampleGeneId,
          sampleNo: this.currentSampleNo, //样本编号
          sex: JSON.stringify(this.personSex), //男女
          district: JSON.stringify(this.AreaNewArrayCode), //地区
          districtName: this.AreaNewArrayName,
          personType: JSON.stringify(this.PersonneNewArrayCode), //人员类型
          personTypeName: this.PersonneNewArrayName,
          mixsameCount: this.LowerLimit,
          geneInfo: strAllData, // 此处使用主动选中的参数
          sampleId: this.sampleId,
          reagentName: this.reagentName,
          mixedSampleGeneId: this.mixedSampleGeneId,
          sampleName: this.sampleName,
          serveNo: this.serveNo.join(","),
          mixedSampleGene: {
            contributorCount: this.contributorCount,
            sampleId: this.sampleId,
            sampleNo: this.currentSampleNo, //样本编号
            sampleName: this.sampleName,
            reagentName: this.reagentName,
            geneInfo: geneInfo,
            genePicture: this.hybridImg
          } //拆分,需要传此原混合基因分型
        };
      } else {
        strAllData = JSON.stringify(this.result);
        // 参数赋值
        storageInfo = {
          queueType: "1",
          serveNo: this.serveNo.join(","),
          condition: this.ModelOneOption,
          sampleNo: this.currentSampleNo, //样本编号
          sex: JSON.stringify(this.personSex), //男女
          district: JSON.stringify(this.AreaNewArrayCode), //地区
          districtName: this.AreaNewArrayName,
          personType: JSON.stringify(this.PersonneNewArrayCode), //人员类型
          personTypeName: this.PersonneNewArrayName,
          mixsameCount: this.LowerLimit,
          geneInfo: strAllData, // 此处使用初始的参数
          sampleId: this.sampleId,
          reagentName: this.reagentName,
          contributorCount: this.contributorCount,
          genePicture: this.hybridImg,
          sampleName: this.sampleName,
          mixedSampleGeneId: this.mixedSampleGeneId
        };
      }
      let info = JSON.stringify(storageInfo);
      quickMatchServers.addLocusListInfoForkitId(info).then(res => {
        if (res.code == 1) {
          if (res.result.code == 0) {
            this.$Modal.success({
              title: "提示",
              content: "加入比对成功！"
            });
            this.ComparisonTableData = res.result.compareQueueList;
            this.Comtype = res.result.QueueFlag;
          } else if (res.result.code == 1) {
            this.$Modal.error({
              title: "提示",
              content: "请勿重复添加！"
            });
          }
        } else {
          this.$Modal.error({
            title: "提示",
            content: "操作失败！"
          });
        }
      });
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
    // 比对进度弹窗
    ScheduleModel_Show() {
      this.ScheduleModel = true;
    },
    // 比对进度弹出层 表格分页监听事件
    ScheduleChange(val) {},
    // 提交比对
    handleSubmit() {
      if (this.ComparisonTableData.length > 0) {
        let ids = [];
        let arr = this.ComparisonTableData;
        arr.forEach(item => {
          ids.push(item.id);
        });
        let contributorInfoList = [];
        this.contributorData.forEach(item => {
          let obj = {};
          if (item.sampleName == undefined) {
            obj.sampleName = null;
          } else {
            obj.sampleName = item.sampleName;
          }
          obj.mixedSampleGeneId = this.mixedSampleGeneId;
          obj.sampleNo = item.sampleNo;
          obj.geneCount = item.geneCount;
          let geneArr = [];
          item.geneInfo.forEach(i => {
            let gene = {};
            gene.markerName = i.name;
            gene.allele = [];
            i.value.forEach(j => {
              let geneAllele = {};
              geneAllele.name = j;
              gene.allele.push(geneAllele);
            });
            geneArr.push(gene);
          });
          obj.geneInfo = JSON.stringify(geneArr);
          contributorInfoList.push(obj);
        });
        let paramsCompare = {
          ids: ids,
          contributorInfoList: contributorInfoList
        };
        let compare = JSON.stringify(paramsCompare);
        quickMatchServers.updateLocusListInfoForId(compare).then(res => {
          if (res.code == 1) {
            this.$Modal.success({
              title: "提示",
              content: "提交比对成功！请在比对记录查看结果。"
            });
            this.SendComparisonModal = false;
            this.Comtype = res.result.QueueFlag;
            this.AreaNewArray = [];
            this.PersonneNewArray = [];
            this.LowerLimit = "";
            this.ModelTwoOption = "";
            for (let item of this.geneInfoListData) {
              if (item.mixedSampleId == this.mixedSampleGeneId) {
                item.flag = 1;
              }
            }
            let obj = {};
            obj.id = this.mixedSampleGeneId;
            obj.arr = this.contributorData;
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
          } else {
            this.$Modal.error({
              title: "提示",
              content: "提交失败！"
            });
          }
        });
      } else {
        this.$Message.error("暂无可提交的数据");
      }
    }
  }
};
</script>
<style lang="less" scoped>
@import "../../assets/styles/hybrud";
@import "../../assets/styles/scrollbar";
.addBtn {
  float: right;
  cursor: pointer;
}
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
.gwc_con {
  width: 100% !important;
  text-align: right;
  padding-right: 30px;
}
// .hybrid_middle_left li span{
//   font-size: 12px;
//   font-weight: normal;
// }
.footer_btn {
  text-align: right;
  button {
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
.light-blue-modal {
  .label {
    display: inline-block;
    color: #333;
    width: 50px !important;
  }
  .header {
    width: 100%;
    height: 50px !important;
    margin-bottom: 10px !important;
  }
}
/*峰图样式*/
.default-img {
  border: 1px dashed #ccc;
  width: 198px;
  height: 160px;
  margin: 40px auto;
}
</style>
