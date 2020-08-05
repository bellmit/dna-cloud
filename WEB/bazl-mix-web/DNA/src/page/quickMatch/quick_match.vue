<template>
  <div>
    <div class="first">
      <div class="home_title">
        <router-link tag="b" to="/">首页</router-link>》
        <span>DNA分型快速比对</span>
      </div>
      <div style="width:100%;height:10px;background:#ccc"></div>
      <div class="main">
        <div class="header main-left">
          <Row class="home_tab_title">
            <Col
              class="header_col"
              v-for="(item, index) in btnList"
              :key="index"
            >
              <button
                class="tab_btn"
                :class="{ active: index == active }"
                @click="handleBtn(index)"
              >
                <!-- <img
                  :src="index == active ? item.link : item.src"
                  alt=""
                  class="btn_img"
                /> -->
                <!-- <img
                  :src="{ active: index == item.link }"
                  alt=""
                  class="btn_img"
                /> -->
                {{ item.btnName }}
              </button>
              <div
                class="triangle_right_blue"
                v-show="index == active && index != btnList.length - 1"
                style="top:15px;right:-30px;z-index:5;"
              ></div>
            </Col>
          </Row>
        </div>
        <div class="main-right">
          <div v-show="active == 0" class="content_box">
            <div class="part">
              <Row class="kit_name">
                <Col span="8">
                  <span class="cube"></span>
                  <span class="font_weight">快速检索</span>
                  <!-- <Select style="width:200px" @on-change="changeKit($event,index)" class="kitSelect">
                  <Option
                    v-for="item in kitNameList"
                    :value="item.id"
                    :key="item.id"
                  >{{ item.name }}</Option>
                </Select> -->
                </Col>
                <!-- <Col span="8">
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
              </Col> -->
              </Row>
              <div
                style="padding:6px 30px;border-bottom:1px solid #D0CDD0;display:flex"
              >
                <div
                  style="display:flex;justify-content: flex-start;align-items:center"
                >
                  <button
                    class="btn"
                    style="border:1px solid #FFA400;color:#FFA400;background:#fff;margin:0 10px"
                    @click="addSampleGroup"
                  >
                    <Icon
                      type="md-add-circle"
                      style="font-size:24px;color:#FFA400;"
                    />
                    添加DNA混合分型
                  </button>
                  <button
                    class="btn btn-blue-border"
                    @click.prevent="handleImportCodisModal"
                  >
                    codis文件导入
                  </button>
                </div>
                <div
                  style="display:flex;justify-content: flex-end;flex:1;align-items:center"
                >
                  <span class="font-size;">样本编号</span>
                  <Input
                    style="width:200px; margin:0 10px;"
                    name="sampleNo"
                    v-model="mainSampleNo"
                  />
                  <button
                    class="btn btn-blue-bg"
                    style="margin:0 10px"
                    @click.prevent="handleDataBaseSearch"
                  >
                    数据库检索
                  </button>
                </div>
              </div>
              <Row class="kit_name">
                <Col span="8">
                  <span class="cube"></span>
                  <span class="font_weight">混合样本</span>
                </Col>
              </Row>
              <div
                class="item-part"
                v-for="(part, index) in sampleGroups"
                :key="index"
              >
                <Row class="item-row">
                  <Col span="6" class="item-col">
                    <span class="item-label item-style">样本编号</span>
                    <Input
                      type="text"
                      class="item-input"
                      :readonly="part.editSampleInfo"
                      v-model="part.sampleNo"
                    ></Input>
                  </Col>
                  <Col span="6" class="item-col">
                    <span class="item-label">样本名称</span>
                    <Input
                      type="text"
                      class="item-input"
                      :readonly="part.editSampleInfo"
                      v-model="part.sampleName"
                    ></Input>
                  </Col>
                  <Col span="6" class="item-col">
                    <span class="item-label">贡献者数量</span>
                    <!-- <RadioGroup class="item-input" v-model="part.mixPerson" @on-change="changeIndexMixPerson(index,$event)">
                    <Radio label="2" class="font-size">2人</Radio>
                    <Radio label="3" class="font-size">3人</Radio>
                  </RadioGroup> -->
                    <span
                      v-for="(itemPerson, itemPersonIndex) in mixPersonList"
                      :key="itemPerson.number"
                      class="mix-person"
                      :class="{
                        'active-mix': itemPersonIndex + 2 == part.mixPerson
                      }"
                      @click="changeIndexMixPerson(index, itemPersonIndex + 2)"
                    >
                      <Icon type="md-checkmark-circle" />
                      {{ itemPerson.number }}人
                    </span>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="6" class="item-col">
                    <span class="item-label">试剂盒名称</span>
                    <Select
                      class="item-input"
                      v-model="part.kitId"
                      @on-change="changeIndexKit(index, $event)"
                      :label-in-value="true"
                    >
                      <Option
                        v-for="kit in kitLocusList"
                        :key="kit.id"
                        :value="kit.id"
                        >{{ kit.name }}</Option
                      >
                    </Select>
                  </Col>
                  <Col span="6">
                    <button
                      class="btn btn-blue-border"
                      @click.prevent="addGeneBtn(index)"
                    >
                      添加基因座
                    </button>
                  </Col>
                  <Col span="12" style="text-align:right;">
                    <button
                      class="btn btn-gray-bg"
                      @click.prevent="handleRemoveThis(part, index)"
                    >
                      删除
                    </button>
                  </Col>
                </Row>
                <Row class="gene-box">
                  <Col
                    span="6"
                    v-for="(item, index) in part.geneInfo"
                    :key="index"
                    class="gene-col"
                    v-show="part.mixPerson == 2"
                  >
                    <span class="gene-name">{{ item.name }}</span>
                    <Input class="gene-input" v-model="item.value[0]"></Input>
                    <Input class="gene-input" v-model="item.value[1]"></Input>
                    <Input class="gene-input" v-model="item.value[2]"></Input>
                    <Input class="gene-input" v-model="item.value[3]"></Input>
                  </Col>
                  <Col
                    span="8"
                    v-for="(item, index) in part.geneInfo"
                    :key="item.id"
                    class="gene-col"
                    v-show="part.mixPerson == 3"
                  >
                    <span class="gene-name">{{ item.name }}</span>
                    <Input class="gene-input" v-model="item.value[0]"></Input>
                    <Input class="gene-input" v-model="item.value[1]"></Input>
                    <Input class="gene-input" v-model="item.value[2]"></Input>
                    <Input class="gene-input" v-model="item.value[3]"></Input>
                    <Input class="gene-input" v-model="item.value[4]"></Input>
                    <Input class="gene-input" v-model="item.value[5]"></Input>
                  </Col>
                  <div v-show="part.addGene">
                    <Icon
                      type="md-add-circle"
                      style="font-size:24px;color:#ccc;"
                    />
                    <span
                      class="font-size"
                      style="height:32px;line-height:32px;"
                      >添加</span
                    >
                    <Select
                      filterable
                      remote
                      :remote-method="
                        query => {
                          remoteMethod(query, index);
                        }
                      "
                      @on-change="addGeneName(index, $event)"
                      style="width:210px"
                    >
                      <Option
                        v-for="(option, i) in part.geneNames"
                        :value="option.locusName"
                        :key="i"
                        >{{ option.locusName }}</Option
                      >
                    </Select>
                  </div>
                </Row>
              </div>
              <Row style="padding:10px 15px;"> </Row>
              <!-- <Table
              size="small"
              border
              stripe
              :columns="kitTable"
              :data="item.kitTableData"
              class="table_data"
            ></Table> -->
            </div>
            <div class="foot_btn">
              <div style="width:100%;height:10px;background:#ccc"></div>
              <!-- <button class="blue_border_btn" @click="addGenotyping">添加基因分型</button> -->
              <button class="blue_btn" @click="handleNext">
                下一步提交分析
              </button>
              <!-- <button class="red_btn" @click="removeAll">全部删除</button> -->
            </div>
          </div>
          <div v-show="active == 1 || active == 2">
            <div class="break_box">
              <div class="break_content">
                <div class="break_box_title">
                  <!-- 
                            此处编号待定
                            <p>
                              <Icon type="ios-list icon_name" />样本编号:11111
                            </p> 
                -->
                  <!-- 钉子 111 -->
                  <p>
                    <Icon type="ios-list icon_name" />
                    样本编号:{{ sam }}
                  </p>
                  <p v-if="active == 1">
                    <span></span>STRmix拆分报告
                    <Upload
                      :action="'api_iLabSTRmix/mix/report/strmixReportFile'"
                      :show-upload-list="false"
                      class="break_box_upload"
                      :before-upload="beforUpload"
                      :format="['pdf']"
                      :name="'splitReportFiles'"
                      :on-success="handelBreakSuccess"
                      :headers="headers"
                      multiple
                    >
                      <Button class="break_upload_btn">导入</Button>
                    </Upload>
                  </p>
                  <p v-else="active == 2">
                    <span></span>GPM拆分报告
                    <Upload
                      :action="'api_iLabSTRmix/mix/report/gpmReportFile'"
                      :show-upload-list="false"
                      class="break_box_upload"
                      :before-upload="beforUpload"
                      :format="['pdf']"
                      :name="'splitReportFiles'"
                      :on-success="handelBreakSuccess"
                      :headers="headers"
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
                      <div class="posi-abs">{{ sampleBadge }}</div>
                    </div>
                  </div>
                </div>
                <div class="break_box_err" v-show="breakShow">
                  <img
                    src="../../assets/img/break_err.png"
                    class="brear_err_img"
                  />
                  <br />
                  <p>您好，目前暂无数据显示</p>
                  <br />
                  <p>
                    您可先导入
                    <span v-show="active == 1" style="color:#0C81F5;"
                      >STRmix拆分报告</span
                    ><span v-show="active == 2" style="color:#0C81F5;"
                      >GPM拆分报告</span
                    >后在进行查看
                  </p>
                </div>
                <!-- ---before -->
                <div class="break_box_success" v-show="!breakShow">
                  <div class="break_box_success_title"></div>
                  <div class="break_success_content">
                    <ul class="break_success_text">
                      <div class="break_box_text_title">
                        <span></span>基因座名称
                      </div>
                      <li class="break_text_first">基因座</li>
                      <div
                        v-for="(item, index) of breakList.mixedSampleGeneList"
                        :class="'break_text_li data_index' + index"
                      >
                        <li>{{ item.markerName }}</li>
                      </div>
                    </ul>
                    <ul class="break_success_text1 break_success_parting">
                      <div class="break_box_text_title">
                        <span></span>混合分型
                      </div>
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
                      v-for="(item,
                      index) of breakList.strMixRapiGeneComparisonListVos"
                      :name="'break_success_table' + index"
                    >
                      <div class="break_success_table_title">
                        <p>
                          <span></span>
                          {{ index + 1 }}号贡献者
                          <strong v-if="active == 1"
                            >（{{ item.contributorWeight }}）</strong
                          >
                          <strong v-else="active == 2"></strong>
                        </p>
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
                <Table
                  :columns="geneListCol_two"
                  :data="geneListData_two"
                ></Table>
              </div>
            </Modal>
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
            <CheckboxGroup
              v-model="personSex"
              style="display:inline;"
              @on-change="changePersonSex"
            >
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
              <!-- @click="SelectArea(AreaIndex,AreaItem)"  -->
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
    <!-- 人员条件弹窗 -->
    <!-- <Modal
      class="model_con_Style"
      v-model="PersonnelModel"
      width="600"
      :closable="false"
    >
      <div class="model-header" style="position: relative;">
        <span class>人员选择</span>
        <p class="all" @click="allSelection">全选</p>
      </div>
      <div class="PersonnelBody Scrollbar">
        <ul>
          <li
            v-for="(PerItem, PerIndex) in PersonnelList"
            @click="PersonnelSelection(PerItem, PerIndex)"
            :class="{ PersonnelStyle: PerItem.flag }"
          >
            <span class="span-per">{{ PerItem.dictValue1 }}</span>
            <span class="span-4">✔</span>
          </li>
        </ul>
      </div>
      <div class="model-footer">
        <p>
          <span @click="Perdetermine">确认</span>
          <span @click="PersonnelModel = false">取消</span>
        </p>
      </div>
    </Modal> -->
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
              <!-- 钉子-6 -->
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
    <!-- codis导入模态框 -->
    <Modal
      v-model="importCodisModal"
      width="1000"
      class="light-blue-modal"
      :closable="false"
    >
      <div class="header" style="width:100%">Codis文件导入</div>
      <div class="modal-content">
        <Row>
          <Col span="18" style="margin-top:10px">
            <Input
              class="fileName"
              :title="fileName"
              type="text"
              readonly
              style="width:400px"
              v-model="fileName"
            ></Input>
            <!-- api_iLabSTRmix/mix/codis/uploadCodis -->
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
            <button
              style="background-color: #007EF9;color:#fff !important"
              class="btn btn-blue-border"
              @click="isUpload"
            >
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
              >列表只显示混合样本</span
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
          @on-selection-change="changeCodisData"
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
      width="1000"
      class="light-blue-modal model_style_2020"
      :closable="true"
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
          @on-selection-change="changeDataBaseSearchData"
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
  </div>
</template>

<script>
import hybridServers from "../../servers/hybridServers";
import serve from "../../servers/serve";
import breakServers from "../../servers/breakServers";
import quickMatchServers from "../../servers/quickMatch";
import upload from "../../servers/upload";
import ModalDetail from "../../components/ModalDetail";
import hybridSplitServers from "../../servers/hybridSplitServers";

export default {
  name: "quickMatch",
  components: { ModalDetail },

  data() {
    return {
      kitInitObj: {
        label: "Identifiler",
        value: "0A7077FC85914E1B929830F4521C2197"
      },
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
          btnName: "DNA混合分型录入"
        },
        // {
        //   src: require("../../assets/img/check.png"),
        //   link: require("../../assets/img/checked.png"),
        //   btnName: "Codis导入DNA分型"
        // },
        {
          src: require("../../assets/img/check.png"),
          link: require("../../assets/img/checked.png"),
          btnName: "STRmix拆分报告导入"
        },
        {
          src: require("../../assets/img/check.png"),
          link: require("../../assets/img/checked.png"),
          btnName: "GPM拆分报告导入"
        }
      ],
      active: 0,
      partList: [{ sampleNo: "", mixedSampleId: "", kitTableData: [] }],
      kitName: "",
      kitNameList: [],
      sampleNo: "",
      exList: [1, 1, 1, 1, 1, 1],
      geneList: [
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "234FERWE",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "DDSF2179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "EWR123",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "D8S1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "SDF2",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "23WF",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "SDF23",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "WER32",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        },
        {
          name: "C8ES1179",
          value1: "24",
          value2: "33",
          value3: "22",
          value4: "31"
        }
      ],
      geneNames: [],
      sampleGroups: [{}],
      importCodisModal: false,
      currentid: 0,
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
          key: "genenum",
          align: "center"
        },
        {
          title: "选择",
          key: "chose",
          width: 70,
          align: "center",
          type: "selection"
        }
      ],
      codisData: [],
      dataBaseSearchModal: false,
      dataBaseSearchCol: [
        {
          title: "序号",
          type: "index",
          width: 70,
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
          key: "kitName",
          align: "center"
        },
        // {
        //   title: "位点个数",
        //   key: "",
        //   align: "center",
        //   width: 90
        // },
        {
          title: "选择",
          type: "selection",
          width: 60,
          align: "center"
        }
      ],
      dataBaseSearchData: [{}, {}, {}],
      mainSampleNo: "",
      kitLocusList: [],
      chooseDataResult: [],
      chooseCodisDataResult: [],
      mixPersonList: [
        {
          number: "2"
        },
        {
          number: "3"
        }
      ],
      fileName: "",
      codisParams: {
        types: "0" //查询混合
      },
      headers: {},
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

      codisData1: {
        // file:[],
        // Orgid:'34234234',
        // reagentId:'',
        // reagentName:'',
        // codisType:1,
        // userId:'123',
        // loginName:'1232',
        // sameCode:1
      },
      codisCol1: [
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
      headers: {
        Authorization: ""
      },
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
      ComparisonTableData: [],
      // 比对列表 表格标题 钉子-3
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
                        if (params.row.queueType == 1) {
                          this.modalDetail.modalTypeHeader = "混合样本分型";
                        } else if (params.row.queueType == 2) {
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
      sampleBadge: 0,
      initGeneInfo: [],
      numCON: "",
      uploadList: [],
      attachments: "",
      personSecondList: [],
      citysIndex: 0
    };
  },
  created() {
    this.headers.Authorization = localStorage.getItem("token");
  },
  mounted() {
    if (sessionStorage.getItem("quickMatch")) {
      this.active = sessionStorage.getItem("quickMatch");
    }
    this.getKitList();
    this.headers.Authorization = localStorage.getItem("token");
    this.reagentKitId = this.kitInitObj.value;
    this.initKit(0, this.kitInitObj);
  },
  updated() {
    //STR拆分
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
      // $('.break_success_table').css('color','red')
    }
  },
  methods: {
    handleBtn(index) {
      sessionStorage.setItem("quickMatch", index);
      this.refresh();
    },
    refresh() {
      this.$router.push({
        path: "/refresh",
        query: {
          t: Date.now()
        }
      });
    },
    handleKitName() {
      // 试剂盒,20200428版本弃用
      quickMatchServers.querykitLocusListInfo().then(res => {
        if (res.code == 1) {
          let list = JSON.parse(res.data);
          this.kitNameList = list.data;
        } else {
          this.$Message.warning("请求错误!");
        }
      });
    },
    remoteMethod(query, index) {
      //远程搜索
      const info = {
        locusName: query
      };
      if (query !== "") {
        quickMatchServers.getRemoteGeneName(info).then(res => {
          this.sampleGroups[index].geneNames = res.result;
          this.$forceUpdate();
        });
      }
    },
    addGeneName(index, value) {
      //每组添加基因座
      let newGeneName = {};
      newGeneName.name = value;
      newGeneName.value = [];
      this.sampleGroups[index].geneInfo.push(newGeneName);
    },
    handleRemoveThis(ddd, index) {
      //每组删除
      this.sampleGroups.splice(index, 1);
    },
    addSampleGroup() {
      //添加基因座
      this.sampleGroups.push({});
    },
    handleDataBaseSearch() {
      //根据样本编号进行数据库检索
      if (this.mainSampleNo.length > 5) {
        this.loadingShow = true;
        this.dataBaseSearchData = [];
        let info = {
          sampleNo: this.mainSampleNo
        };
        quickMatchServers
          .getSelectSampleNoList(info)
          .then(res => {
            this.dataBaseSearchModal = true;
            this.loadingShow = false;
            this.dataBaseSearchData = res.result;
          })
          .catch(error => {
            this.loadingShow = false;
          });
      } else {
        this.$Modal.info({
          title: "抱歉",
          content: "请至少输入6位检材编号~"
        });
      }
    },
    changeDataBaseSearchData(list) {
      //选择检索后的数据保存到临时数组
      this.chooseDataResult = list;
    },
    changeCodisData(list) {
      //从codis导入后的数据保存到临时数组
      this.chooseCodisDataResult = list;
    },
    sureChooseDataSearch() {
      //对检索选择的临时数据进行处理然后渲染页面
      this.dataBaseSearchModal = false;
      this.sampleGroups = [];
      this.chooseDataResult.forEach(item => {
        item.geneInfo = JSON.parse(item.entity.geneInfo);
        item.mixId = item.entity.id;
        item.sampleId = item.entity.sampleId;
        item.mixPerson = "2";
        item.editSampleInfo = true; // 为true是只读状态
        item.addGene = false;
        item.geneInfo.forEach(i => {
          if (i.value.length > 4) {
            item.mixPerson = "3";
          }
        });
      });
      this.sampleGroups = this.chooseDataResult;
    },
    sureChooseCodisData() {
      //对codis导入选择的临时数据进行处理然后渲染页面
      this.importCodisModal = false;
      this.sampleGroups = [];
      this.chooseCodisDataResult.forEach(item => {
        item.geneInfo = [];
        item.mixPerson = "2";
        item.editSampleInfo = true; // 为true是只读状态
        item.addGene = false;
        item.geneInfoList.forEach(i => {
          let newGeneInfo = {};
          newGeneInfo.name = i.markerName;
          newGeneInfo.value = [];
          i.allele.forEach(j => {
            newGeneInfo.value.push(j.name);
          });
          item.geneInfo.push(newGeneInfo);
        });
        item.geneInfo.forEach(k => {
          if (k.value.length > 4) {
            item.mixPerson = "3";
          }
        });
      });
      this.sampleGroups = this.chooseCodisDataResult;
    },
    changeIndexMixPerson(index, value) {
      //贡献者人数切换
      let flag = true;
      if (value == "2") {
        this.sampleGroups[index].geneInfo.forEach(item => {
          if (item.value.length >= 5) {
            flag = false;
          }
        });
      }
      if (flag) {
        this.sampleGroups[index].mixPerson = value;
      } else {
        this.$Modal.error({
          title: "抱歉",
          content: "根据位点信息分析贡献者数量应是3人,不可修改为2人"
        });
      }
      this.$forceUpdate();
    },
    addGeneBtn(index) {
      //添加基因座按钮显示
      this.sampleGroups[index].addGene = true;
      this.$forceUpdate();
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
    changeIndexKit(index, value) {
      this.reagentKitId = value.value;
      this.initKit(index, value);
    },
    initKit(index, value) {
      //试剂盒改变渲染基因座和mixid
      let info = {
        reagentKitId: this.reagentKitId
      };
      quickMatchServers.getGeneNameByKitId(info).then(res => {
        const list = [];
        res.result.forEach(item => {
          let itemGene = {};
          itemGene.name = item.locusName;
          itemGene.value = [];
          list.push(itemGene);
        });
        if (this.sampleGroups[index].geneInfo) {
          list.forEach(item => {
            if (
              this.sampleGroups[index].geneInfo.find(x => x.name == item.name)
            ) {
              this.sampleGroups[index].geneInfo
                .find(x => x.name == item.name)
                .value.forEach(i => {
                  item.value.push(i);
                });
            }
          });
        } else {
          this.sampleGroups[index].mixPerson = "2";
          this.sampleGroups[index].editSampleInfo = false; // 为true是只读状态
        }
        if (!this.sampleGroups[index].mixId) {
          // 手工录入的基因型没有混合ID,此处调用接口生成混合ID
          quickMatchServers.getSampleMixId().then(res => {
            this.sampleGroups[index].mixId = res.result.mixedSampleId;
          });
        }
        this.sampleGroups[index].geneInfo = list;
        this.sampleGroups[index].kitId = value.value;
        this.sampleGroups[index].kitName = value.label;
        this.$forceUpdate(); // 强制刷新视图
      });
    },
    handleImportCodisModal() {
      //导入codis模态框展示
      this.importCodisModal = true;
      this.codisData = [];
      this.fileName = "";
      return false;
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
      formData.append("types", "0");
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
    changeKit($event, index) {
      // 试剂盒改变方法-20200428版本弃用
      let listInfo = {
        reagentKitId: $event
      };
      quickMatchServers.querySimleNofoForkitId().then(res => {
        if (res.code == 1) {
          this.partList[index].sampleNo = res.data.nextSampleNoVal;
          this.partList[index].mixedSampleId = res.data.mixedSampleId;
        } else {
          this.$Message.warning("请求错误!");
        }
      });
      quickMatchServers.queryLocusListInfoForkitId(listInfo).then(res => {
        if (res.code == 1) {
          let list = JSON.parse(res.data);
          this.partList[index].kitTableData = list.data;
        } else {
          this.$Message.warning("请求错误!");
        }
      });
    },
    addGenotyping() {
      // 添加基因分型-20200428版本弃用
      this.partList.push({ sampleNo: "", mixedSampleId: "", kitTableData: [] });
    },
    delGeneGroup(index) {
      // 删除基因分型-20200428版本弃用
      this.partList.splice(index, 1);
    },
    // 下一步
    handleNext() {
      let paramsArray = [];
      this.sampleGroups.forEach(item => {
        let itemObj = {};
        itemObj.geneInfoList = [];
        itemObj.reagentName = item.kitName;
        itemObj.mixedSampleId = item.mixId;
        itemObj.sampleId = item.sampleId;
        itemObj.contributorCount = item.mixPerson;
        itemObj.sampleNo = item.sampleNo;
        itemObj.sampleName = item.sampleName;
        item.geneInfo.forEach(i => {
          let gene = {};
          gene.markerName = i.name;
          gene.allele = [];
          i.value.forEach(j => {
            let geneAllele = {};
            geneAllele.flag = false;
            geneAllele.name = j;
            gene.allele.push(geneAllele);
          });
          itemObj.geneInfoList.push(gene);
        });
        paramsArray.push(itemObj);
      });
      this.$router.push({
        path: "/quickMixedSplit",
        name: "QuickMixedSplit",
        params: {
          geneInfoData: paramsArray
        }
      });
    },
    removeAll() {
      this.partList = [];
    },
    // !codis导入
    upSuccess(res, file, fileList) {
      this.fileName = file.name;
      if (res.code == 1) {
        this.codisTableData = res.data;
      } else {
        this.$Message.error("上传出错,请重试");
      }
    },
    handleSelect(params) {
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
      this.ModelOneOption = "";
      let viewInfo = {
        mixedSampleId: this.mainId
      };
      hybridServers.ViewAndNext(viewInfo).then(res => {
        this.ComparisonTableData = res.result;
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
    //上传成功
    handelBreakSuccess(response, file) {
      if (response.code == 1) {
        this.loadingShow = false;
        this.sam = response.result.sampleNo;
        this.chaiShowBtn = true;
        this.map_num = 1;
        this.FileName = file.name;
        this.breakList = response.result;
        this.breakShow = false;
        this.type = 2;
        // this.genneId = response.data.strMixRapiGeneComparisonListVos[0].geneId;
        this.mainId = response.result.mixId;
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
        let promise = new Promise(resolve => {
          this.$nextTick(function() {
            resolve(true);
          });
        });
        return promise;
      }
    },
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
            $(this)
              .find("li:eq(0) p:eq(0) input")
              .prop("checked", false);
            that["geneList" + zz] = [];
          }
        });
    },
    chaifenruku(par) {
      let str = par;
      let strSUb = str.substring(19);
      this.strNum = parseInt(strSUb) + 1;
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
      hybridServers.SelectCount(mix).then(res => {
        this.sampleBadge = res.result.count;
      });
    },
    // 加入比对 确定按钮事件
    sureRuku() {
      let comparisonInfo = {
        sampleNo: this.sam,
        mixedSampleId: this.mainId,
        queueType: "2", // 1
        fileName: this.FileName,
        contributorName: this.strNum,
        geneInfo: JSON.stringify(this.geneListData),
        mixedSampleGene: {
          sampleNo: this.sam,
          contributorCount: this.numCON,
          geneInfo: JSON.stringify(this.initGeneInfo)
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
          this.PersonnelList.forEach(i => {
            i.val.forEach(k => {
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
    // 容差监听事件
    OneOptionChange(val) {
      this.ComparisonTableData = this.ComparisonTableData.map(item => {
        item.condition = val;
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
    // 比对进度弹窗
    ScheduleModel_Show() {
      this.ScheduleModel = true;
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
    }
  }
};
</script>

<style lang="less">
@import "../../assets/styles/case";
@import "../../assets/styles/break";
@import "../../assets/styles/hybrud";
@import "../../assets/styles/scrollbar";
.first {
  background: #fff;
}
.main:after {
  content: "";
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.main {
  width: 100%;
  height: 100%;
  min-height: 850px;
  background: #fff;
  display: flex;
  flex: 1;
  .main-left {
    float: left;
    width: 15%;
    background: #fff;
  }
  .main-right {
    float: right;
    width: 85%;
    min-height: 500px;
    left: 15%;
    border-left: 1px solid #ccc;
  }
  .header {
    padding: 0 10px;
    background: #fff;
    .home_tab_title {
      margin: 30px 0;
    }
    .header_col {
      // text-align: center;
      display: inline-block;
      width: 462px;
      width: 300px;
      margin: 10px 0;
      .tab_btn {
        width: 60%;
        height: 32px;
        border-radius: 30px;
        border: none;
        background: #cccccc;
        color: #333;
        font-size: 16px;
        font-weight: bold;
        outline: none;
        position: relative;
        cursor: pointer;
        .btn_img {
          position: absolute;
          right: -6px;
          width: 20px;
          top: -6px;
        }
      }
      .tab_btn.active {
        background: #007ef9;
        color: #fff;
      }
    }
  }
  .kit_name {
    height: 40px;
    padding: 6px 15px;
    border-bottom: 1px solid #d0cdd0;
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
  // padding: 10px 0;
  text-align: right;
  position: fixed;
  bottom: 0;
  left: 0;
  background-color: #fff;
  width: 100%;
  height: 60px;
  button {
    margin: 5px 10px;
  }
}
.item-part {
  margin: 12px 0;
  .item-row {
    padding: 0 15px;
    margin: 8px 0;
    .item-col {
      .item-style::before {
        display: inline-block;
        content: "";
        width: 0;
        height: 0;
        border-top: 6px solid transparent;
        border-left: 6px solid #fc4a34;
        border-bottom: 6px solid transparent;
        margin-right: 4px;
      }
      .item-label {
        font-size: 14px;
        color: #333;
        display: inline-block;
        width: 80px;
        height: 32px;
        line-height: 32px;
      }
      .item-input {
        display: inline-block;
        width: 200px;
        input {
          background-color: #f4f4f4 !important;
          border: 1px solid #cccccc !important;
          box-sizing: border-box;
        }
      }
    }
  }
  .gene-box {
    border: 1px solid #ddd;
    border-radius: 4px;
    margin: 6px 15px;
    padding: 10px 15px;
    .gene-col {
      margin: 8px 0;
      .gene-name {
        display: inline-block;
        color: #010101;
        min-width: 80px;
        font-size: 14px;
        height: 32px;
        line-height: 32px;
      }
      .gene-name::before {
        display: inline-block;
        content: "";
        width: 8px;
        height: 8px;
        background-color: #9fc8fd;
        margin-right: 4px;
        border-radius: 50%;
      }
      .gene-input {
        display: inline-block;
        width: 50px;
        input {
          border: 1px solid #999999 !important;
          box-sizing: border-box;
        }
      }
    }
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
  // min-height: 570px;
  margin-bottom: 70px;
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
  width: 70%;
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

.mix-person {
  margin: 0 15px;
  cursor: pointer;
  font-size: 14px;
  i {
    color: #ccc;
    font-size: 24px;
  }
}
.mix-person.active-mix {
  i {
    color: #007afd;
  }
}
</style>
