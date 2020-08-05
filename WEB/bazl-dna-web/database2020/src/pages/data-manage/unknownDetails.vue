/* eslint-disable no-unused-vars */
<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>委托管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/unknown">身份不明人员</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/unknownDetails">身份不明人员详情</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            案件信息
          </div>
          <div class="content">
            <Form :model="caseInfo" :label-width="100">
              <Row>
                <Col span="8">
                  <FormItem label="案件名称">
                    <Input v-model="caseInfo.caseName" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案件类型">
                    <Select
                      v-model="caseInfo.caseType"
                      disabled
                      placeholder=" "
                    >
                      <Option value="01">刑事</Option>
                      <Option value="02">民事</Option>
                      <Option value="03">其他</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案件编号">
                    <Input v-model="caseInfo.caseAcceptNo" readonly></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="案件性质">
                    <Select
                      multiple
                      v-model="searchForm.caseProperty"
                      disabled
                      placeholder=" "
                    >
                      <Option
                        v-for="item in casePropertyList"
                        :value="item.dictCode"
                        :key="item.dictCode"
                        >{{ item.dictName }}</Option
                      >
                    </Select>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案发地点">
                    <Input v-model="caseInfo.scenePlace" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案发时间">
                    <DatePicker
                      type="date"
                      style="width: 100%"
                      v-model="caseInfo.occurrenceDatetime"
                      disabled
                    ></DatePicker>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="24">
                  <FormItem label="简要案情">
                    <Input
                      v-model="caseInfo.caseSummary"
                      type="textarea"
                      :rows="3"
                      readonly
                    ></Input>
                  </FormItem>
                </Col>
              </Row>
            </Form>
          </div>
          <div class="title">
            委托信息
            <span class="font-black" style="margin-left:3%">
              <span style="margin-right:10px">所属辖区</span>
              <Input
                v-model="consignmentInfo.consignOrgName"
                readonly
                style="width:300px"
              ></Input>
            </span>
            <span class="font-black" style="margin-left:3%">
              <span style="margin-right:10px">鉴定要求</span>
              <Input
                v-model="consignmentInfo.identifyRequirement"
                readonly
                style="width:300px"
              ></Input>
            </span>
          </div>
          <div class="content con-padding">
            <Row :gutter="12">
              <Col span="12">
                <div class="entrust-con">
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">姓名</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson1Name"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                    <Col span="12">
                      <span class="label-span">职务</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson1PositionName"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">证件</span>
                      <span>
                        <Input
                          v-model="
                            consignmentInfo.consignPerson1CertificateType
                          "
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                    <Col span="12">
                      <span class="label-span">证件号</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson1CertificateNo"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">电话</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson1Phone"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <div class="entrust-img">
                    <img src="../../assets/img/touxiang.png" alt="" />
                  </div>
                  <div class="entrust-text">
                    委托人01
                  </div>
                </div>
              </Col>
              <Col span="12">
                <div class="entrust-con">
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">姓名</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson2Name"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                    <Col span="12">
                      <span class="label-span">职务</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson2PositionName"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">证件</span>
                      <span>
                        <Input
                          v-model="
                            consignmentInfo.consignPerson2CertificateType
                          "
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                    <Col span="12">
                      <span class="label-span">证件号</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson2CertificateNo"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <Row class="mg-top-10px">
                    <Col span="12">
                      <span class="label-span">电话</span>
                      <span>
                        <Input
                          v-model="consignmentInfo.consignPerson2Phone"
                          readonly
                          class="width-input"
                        ></Input>
                      </span>
                    </Col>
                  </Row>
                  <div class="entrust-img">
                    <img src="../../assets/img/touxiang.png" alt="" />
                  </div>
                  <div class="entrust-text">
                    委托人02
                  </div>
                </div>
              </Col>
            </Row>
          </div>
          <div class="title">
            人员列表
          </div>
          <div class="content">
            <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            ></Table>
            <!-- <div class="page">
              <span>
                当前第{{ currentPage }}页/共{{ pageCount }}页/共{{
                  allRecordCount
                }}条信息
              </span>
              <Page
                :total="allRecordCount"
                show-elevator
                prev-text="上一页"
                next-text="下一页"
                class-name="bazl_page"
                :current="currentPage"
                @on-change="changePage"
                :page-size="15"
              />
            </div> -->
          </div>
          <Row class="btn-row">
            <button class="btn btn-blue-border" @click="goBack">返 回</button>
          </Row>
        </div>
      </Col>
    </Row>
    <!-- 物证检材信息开始 -->
    <Modal
      v-model="detailsModal"
      width="500"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看详情
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="detailsModal = false"
        />
      </div>
      <div class="title" style="padding:8px;border-bottom: 1px dashed #00439E;">
        人员信息
      </div>
      <div class="model-content details_Modal">
        <Form :model="personInfo" :label-width="70" label-position="left">
          <Row>
            <Col span="24">
              <FormItem :label="label.name">
                <Input v-model="personInfo.personName" readonly></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem :label="label.sex">
                <Select
                  v-model="personInfo.personGender"
                  disabled
                  placeholder=" "
                >
                  <Option value="1">男</Option>
                  <Option value="2">女</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="身份证号">
                <Input v-model="personInfo.personIdcardNo" readonly></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem :label="label.nationality">
                <Select
                  v-model="personInfo.nationality"
                  disabled
                  placeholder=" "
                >
                  <Option
                    v-for="item in personNationList"
                    :value="item.dictCode"
                    :key="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="人员类型">
                <Select
                  multiple
                  v-model="personInfo.personType"
                  disabled
                  placeholder=" "
                >
                  <Option
                    v-for="item in personTypeQuery"
                    :value="item.dictCode"
                    :key="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem :label="label.remark">
                <Input
                  v-model="personInfo.personType"
                  type="textarea"
                  :rows="3"
                  readonly
                ></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div class="title" style="padding:8px;border-bottom: 1px dashed #00439E;">
        样本信息
      </div>
      <div class="model-content details_Modal">
        <Form :model="searchForm" :label-width="70" label-position="left">
          <Row>
            <Col span="24">
              <FormItem label="样本编号">
                <Input v-model="sampleInfo.sampleLabNo" readonly></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="样本名称">
                <Input v-model="sampleInfo.sampleName" readonly></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="样本类型">
                <Select
                  v-model="sampleInfo.sampleType"
                  disabled
                  placeholder=" "
                >
                  <Option
                    v-for="item in qcSampleTypeList"
                    :value="item.dictCode"
                    :key="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="亲缘关系">
                <Select
                  placeholder=" "
                  disabled
                  v-model="sampleInfo.relationTypeCode"
                >
                  <Option
                    v-for="item in selectByTargetReationType"
                    :key="item.dictCode"
                    :value="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem :label="label.remark">
                <Input
                  v-model="searchForm.input"
                  type="textarea"
                  :rows="3"
                  readonly
                ></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
    </Modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      label: {
        name: `${'姓\xa0\xa0\xa0\xa0\xa0\xa0\xa0名'}`,
        sex: `${'性\xa0\xa0\xa0\xa0\xa0\xa0\xa0别'}`,
        nationality: `${'国\xa0\xa0\xa0\xa0\xa0\xa0\xa0籍'}`,
        remark: `${'备\xa0\xa0\xa0\xa0\xa0\xa0\xa0注'}`
      },
      caseInfo: {}, // 案件信息
      consignmentInfo: {}, // 委托信息
      searchForm: {},
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 0,
      id: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '姓名',
          key: 'personName',
          align: 'center'
        },
        {
          title: '性别',
          key: 'personGender',
          align: 'center',
          render: (h, params) => {
            // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.row.personGender === '1') {
              text = '男'
            } else if (params.row.personGender === '2') {
              text = '女'
            } else {
              text = ''
            }
            return h('span', {}, text)
          }
        },
        {
          title: '身份证号',
          key: 'personIdcardNo:',
          align: 'center'
        },
        {
          title: '人员类型',
          key: 'personType',
          align: 'center'
        },
        {
          title: '操作',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer',
                    marginRight: '10px'
                  },
                  on: {
                    click: () => {
                      this.viewDetails(params.row.id, 1)
                    }
                  }
                },
                '查看详情'
              )
            ])
          }
        }
      ],
      tableCol_1: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '样本编号',
          key: 'labServerName',
          align: 'center'
        },
        {
          title: '样本名称',
          key: 'personTypeName',
          align: 'center'
        },
        {
          title: '样本类型',
          key: 'matchSampleNo',
          align: 'center'
        },
        {
          title: '亲缘关系',
          key: 'matchSampleName',
          align: 'center'
        },
        {
          title: '样本状态',
          key: 'matchSampleName',
          align: 'center'
        },
        {
          title: '操作',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer',
                    marginRight: '10px'
                  },
                  on: {
                    click: () => {
                      this.viewDetails(params.row.id, 2)
                    }
                  }
                },
                '查看详情'
              )
            ])
          }
        }
      ],
      tableData: [],
      tableData_1: [],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      name: '', // 详情模板标题
      loadingModal: true,
      casePropertyList: [], // 案件性质
      selectRegion: [], // 所属辖区
      personNationList: [], // 国籍
      personTypeQuery: [], // 人员类型
      qcSampleTypeList: [], // 样本类型
      selectByTargetReationType: [], // 亲缘关系
      detailsModal: false,
      personInfo: {},
      sampleInfo: {}
    }
  },
  created() {
    if (sessionStorage.getItem('selectByTargetReationType')) {
      // 目标亲缘身份
      const arr = sessionStorage.getItem('selectByTargetReationType')
      this.selectByTargetReationType = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcSampleTypeList')) {
      // 样本
      const arr = sessionStorage.getItem('qcSampleTypeList')
      this.qcSampleTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personTypeQuery')) {
      // 人员类型
      const arr = sessionStorage.getItem('personTypeQuery')
      this.personTypeQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personNationList')) {
      // 国籍
      const arr = sessionStorage.getItem('personNationList')
      this.personNationList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('caseNatureQuery')) {
      // 案件性质
      const arr = sessionStorage.getItem('caseNatureQuery')
      this.casePropertyList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectRegion')) {
      // 案件性质
      const arr = sessionStorage.getItem('selectRegion')
      this.selectRegion = JSON.parse(arr)
      console.log(this.selectRegion)
    }
    this.id = this.$route.query.id
  },
  mounted() {
    this.getInitResult()
  },
  methods: {
    // 页面初始数据
    getInitResult() {
      this.searchForm.pageIndex = this.currentPage
      this.$axios.get('/database/caseunidentified/get/' + this.id).then(res => {
        console.log(res)
        this.caseInfo = res.result.caseInfo
        this.consignmentInfo = res.result.consignmentInfo
        this.tableData = res.result.missingList
        this.tableData_1 = res.result.missingRelativeList
      })
    },
    // 查看详情
    viewDetails(id, type) {
      this.detailsModal = true
      this.$axios.get('/database/caseunidentified/getInfo/' + id).then(res => {
        console.log(res)
        this.personInfo = res.result.casePersonInfo
        this.sampleInfo = res.result.dnaSampleInfo
        this.sampleInfo.relationTypeCode = res.result.relationTypeCode
      })
    },
    // 查询
    handSearch() {
      this.currentPage = 1
      this.getInitResult()
    },
    // 重置 查询条件
    handleReset() {
      Object.keys(this.searchForm).forEach(key => {
        this.searchForm[key] = ''
      })
    },
    // 表格 分页监听
    changePage(page) {
      this.currentPage = page
    },
    goBack() {
      this.$router.push({
        path: '/datamanage/unknown'
      })
    }
  }
}
</script>
<style lang="less" scoped>
.details_Modal {
  padding: 15px 50px;
}
.con-padding {
  padding: 40px 15px 15px 15px !important;
}
.entrust-con {
  position: relative;
  width: 100%;
  height: 200px;
  border: 1px dashed #3086c1;
  border-radius: 5px;
  padding: 30px;
  .label-span {
    display: inline-block;
    width: 50px;
    text-align: right;
    margin-right: 5px;
  }
  .width-input {
    width: 70%;
  }
  .entrust-img {
    position: absolute;
    top: -32.5px;
    left: 50%;
    margin-left: -32.5px;
    width: 65px;
    height: 65px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .entrust-text {
    position: absolute;
    bottom: 0;
    left: 50%;
    margin-left: -30px;
    width: 60px;
    height: 25px;
    border-radius: 3px;
    background: #eeeff8;
    font-size: 12px;
    color: royalblue;
    text-align: center;
    line-height: 25px;
  }
}
.part {
  padding: 4px 10px;
  margin-bottom: 50px;
  height: calc(100vh - 100px);
  overflow-y: scroll;
  .nav {
    margin: 4px 0;
  }
  .result-part {
    background-color: #fff;
    border-radius: 4px;
    padding-bottom: 15px;
    .title {
      border-bottom: 1px solid #3086c1;
      padding: 8px;
    }
    .content {
      padding: 10px 15px;
    }
    .sub {
      padding: 6px 15px;
      color: #333;
    }
  }
}
</style>
