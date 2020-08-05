<template>
  <div>
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/case">案件数据管理</router-link>
          </Col>
        </Row>
        <Form class="form-part" ref="searchForm" :model="searchForm">
          <div class="title">查询条件设置</div>
          <div class="item-title" style="border-top:none;">
            案件数据项 <i class="iconfont iconweituo"></i>
          </div>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">案件名称</span>
              <span class="item-group">
                <RadioGroup v-model.trim="searchForm.caseNameCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入案件名称"
                style="width:260px;"
                v-model="searchForm.caseName"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">案件类型</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.caseType">
                  <Radio label="01">刑事</Radio>
                  <Radio label="02">民事</Radio>
                  <Radio label="03">其他</Radio>
                </RadioGroup>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">案件受理编号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.caseAcceptNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入案件受理编号"
                style="width:260px;"
                v-model="searchForm.caseAcceptNo"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">案件性质</span>
              <span class="item-group">
                <Select
                  multiple
                  placeholder="请选择案件性质 - 支持多选"
                  style="width:260px;"
                  v-model="searchForm.casePropertyList"
                >
                  <Option
                    v-for="item in casePropertyList"
                    :value="item.dictCode"
                    :key="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">案件现场K号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.sysXkNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入案件现场K号"
                style="width:260px;"
                v-model="searchForm.sysXkNo"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">案发地点</span>
              <span class="item-group">
                <Input
                  type="text"
                  placeholder="请输入案发地点"
                  style="width:260px;"
                  v-model="searchForm.scenePlace"
                ></Input>
                <!-- <Select placeholder="" style="display:inline-block;width:260px;" v-model="searchForm.scenePlace">
                  <Option>2</Option>
                  <Option>2</Option>
                  <Option>2</Option>
                </Select> -->
                <!-- <Select placeholder="" style="display:inline-block;width:125px;margin-left:10px;">
                  <Option>2</Option>
                  <Option>2</Option>
                  <Option>2</Option>
                </Select> -->
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">案件三版本A号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.sysCaseAnoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入案件三版本A号"
                style="width:260px;"
                v-model="searchForm.sysCaseAno"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">案发时间</span>
              <span class="item-group">
                <DatePicker
                  type="date"
                  placeholder="开始时间"
                  style="width: 125px"
                  v-model="searchForm.occurrenceStratDatetime"
                ></DatePicker>
                <DatePicker
                  type="date"
                  placeholder="结束时间"
                  style="width: 125px;margin-left:10px;"
                  v-model="searchForm.occurrenceEndDatetime"
                ></DatePicker>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">国家库案件编号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.nationSysNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入国家库案件编号"
                style="width:260px;"
                v-model="searchForm.nationSysNo"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">是否命案</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.isLifeCase">
                  <Radio label="2">全部</Radio>
                  <Radio label="0">是</Radio>
                  <Radio label="1">否</Radio>
                </RadioGroup>
              </span>
            </Col>
          </Row>
          <Collapse>
            <div class="collapse-wrap" v-show="isActive">
              <slot>
                <div class="item-title">
                  委托数据项
                </div>
                <Row class="item-row">
                  <Col span="14">
                    <span class="item-label">委托单位</span>
                    <span class="item-group">
                      <Cascader
                        placeholder="请选择委托单位"
                        @on-change="changeCascader"
                        :data="selectByOrgInfo"
                        style="width:330px;"
                        v-model="clientOrgList"
                        :change-on-select="true"
                      ></Cascader>
                    </span>
                  </Col>
                  <Col span="10">
                    <span class="item-label">委托人</span>
                    <Input
                      type="text"
                      placeholder="姓名"
                      style="width:120px"
                      v-model="searchForm.consignPersonName"
                    ></Input>
                    <Input
                      type="text"
                      placeholder="联系电话"
                      style="width:160px;margin-left:15px;"
                      v-model="searchForm.consignPersonPhone"
                    ></Input>
                  </Col>
                </Row>
                <div class="item-title">
                  检材数据项
                </div>
                <Row class="item-row">
                  <Col span="14">
                    <span class="item-label">检材实验室编号</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchForm.sampleNoCondition">
                        <Radio label="1">包含</Radio>
                        <Radio label="2">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入DNA条码号"
                      style="width:260px;"
                      v-model="searchForm.sampleNo"
                    ></Input>
                  </Col>
                  <Col span="10">
                    <span class="item-label">检材名称</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchForm.sampleNameCondition">
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入检材名称"
                      style="width:130px;"
                      v-model="searchForm.sampleName"
                    ></Input>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="14">
                    <span class="item-label">现勘物证编号</span>
                    <span class="item-group">
                      <RadioGroup
                        v-model="searchForm.sampleEvidenceNoCondition"
                      >
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入现勘物证编号"
                      style="width:260px;"
                      v-model="searchForm.sampleEvidenceNo"
                    ></Input>
                  </Col>
                  <Col span="10">
                    <span class="item-label">检材类型</span>
                    <span class="item-group">
                      <Select
                        multiple
                        placeholder="请选择检材类型 - 支持多选"
                        style="display:inline-block;width:260px;"
                        v-model="searchForm.sampleTypeList"
                      >
                        <Option
                          v-for="item in sampleTypeQuery"
                          :value="item.dictCode"
                          :key="item.dictCode"
                          >{{ item.dictName }}</Option
                        >
                      </Select>
                    </span>
                  </Col>
                </Row>
                <div class="item-title">
                  人员数据项
                </div>
                <Row class="item-row">
                  <Col span="10">
                    <span class="item-label">姓名</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchForm.personNameCondition">
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入姓名"
                      style="width:260px;"
                      v-model="searchForm.personName"
                    ></Input>
                  </Col>
                  <Col span="7">
                    <span class="item-label">性别</span>
                    <span class="item-group">
                      <Select
                        placeholder=""
                        style="display:inline-block;width:230px;"
                        v-model="searchForm.personSex"
                      >
                        <Option value="1">男</Option>
                        <Option value="2">女</Option>
                        <Option value="3">未知</Option>
                      </Select>
                    </span>
                  </Col>
                  <Col span="7">
                    <span class="item-label">民族</span>
                    <span class="item-group">
                      <Select
                        placeholder=""
                        style="display:inline-block;width:230px;"
                        v-model="searchForm.personRace"
                      >
                        <Option
                          v-for="item in personRaceList"
                          :key="item.id"
                          :value="item.dictCode"
                          >{{ item.dictName }}</Option
                        >
                      </Select>
                    </span>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="10">
                    <span class="item-label">身份证号</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchForm.personIdcardNoCondition">
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入身份证号"
                      style="width:260px;"
                      v-model="searchForm.personIdcardNo"
                    ></Input>
                  </Col>
                  <Col span="8">
                    <span class="item-label">人员类别</span>
                    <span class="item-group">
                      <Select
                        placeholder="案件人员"
                        style="display:inline-block;width:230px;"
                        v-model="searchForm.personType"
                      >
                        <Option
                          v-for="item in personTypeQuery"
                          :value="item.dictCode"
                          :key="item.dictCode"
                          >{{ item.dictName }}</Option
                        >
                      </Select>
                    </span>
                  </Col>
                </Row>
                <div class="item-title">
                  其他数据项
                </div>
                <Row class="item-row">
                  <Col span="8">
                    <span class="item-label">DNA实验室</span>
                    <span class="item-group">
                      <Select
                        placeholder=""
                        style="display:inline-block;width:260px;"
                        v-model="searchForm.labServerNo"
                      >
                        <Option
                          v-for="item in labServerList"
                          :key="item.id"
                          :value="item.labServerNo"
                          >{{ item.labServerName }}</Option
                        >
                      </Select>
                    </span>
                  </Col>
                  <Col span="8">
                    <span class="item-label">受理人</span>
                    <span class="item-group">
                      <Input
                        type="text"
                        placeholder="请输入"
                        style="width:260px;"
                        v-model="searchForm.acceptorName"
                      ></Input>
                    </span>
                  </Col>
                  <Col span="8">
                    <span class="item-label">受理时间</span>
                    <span class="item-group">
                      <DatePicker
                        type="date"
                        placeholder="开始时间"
                        style="width: 125px"
                        v-model="searchForm.acceptStartDate"
                      ></DatePicker>
                      <DatePicker
                        type="date"
                        placeholder="结束时间"
                        style="width: 125px;margin-left:10px;"
                        v-model="searchForm.acceptEndDate"
                      ></DatePicker>
                    </span>
                  </Col>
                </Row>
                <Row class="tc">
                  <button class="btn btn-blue-bg" @click.prevent="getCaseQuery">
                    执行查询
                  </button>
                  <button
                    class="btn btn-red-line"
                    style="margin-left:10px"
                    @click.prevent="handleReset"
                  >
                    全部清空
                  </button>
                </Row>
              </slot>
            </div>
          </Collapse>
          <Row class="tc" style="margin-top:10px;">
            <button
              @click.prevent="isActive = !isActive"
              class="btn btn-blue-border"
            >
              展开/收起查询条件
            </button>
          </Row>
        </Form>
        <div class="result-part">
          <div class="title">查询结果</div>
          <Table
            border
            :columns="searchResultCol"
            :data="searchResultData"
            class="bazl-table"
            size="small"
          ></Table>
          <div class="page">
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
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import Collapse from '../../components/collapse'
export default {
  components: { Collapse },
  name: 'case',
  data() {
    return {
      SidebarParams: {
        open: ['2'],
        active: '2-1'
      },
      isActive: true,
      searchForm: {},
      searchResultCol: [
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
        },
        {
          title: '案件受理编号',
          key: 'caseAcceptNo',
          align: 'center'
        },
        {
          title: '现勘系统编号(K号)',
          key: 'sysXkNo',
          align: 'center',
          width: 120
        },
        {
          title: '三版本编号(A号)',
          key: 'sysCaseAno',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '案件性质',
          key: 'casePropertyName',
          align: 'center'
        },
        {
          title: '委托单位',
          key: 'consignOrgName',
          align: 'center'
        },
        {
          title: '受理单位',
          key: 'labServerName',
          align: 'center'
        },
        {
          title: '受理时间',
          key: 'acceptDatetime',
          align: 'center'
        },
        {
          title: '受理人',
          key: 'acceptPersonName',
          align: 'center',
          width: '60'
        },
        {
          title: '破获状态',
          key: 'caseCrackStatusName',
          align: 'center',
          width: '60',
          render: (h, params) => {
            return h('span', params.row.caseStatus === 0 ? '未破获' : '已破获')
          }
        },
        {
          title: '更多详情',
          key: 'action',
          // className: 'blue-font',
          align: 'center',
          width: '60',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  style: {
                    color: '#00439E',
                    cursor: 'pointer'
                  },
                  on: {
                    click: event => {
                      this.$router.push({
                        path: '/datamanage/caseDetail',
                        query: {
                          caseId: params.row.id,
                          consignmentId: params.row.consignmentId
                        }
                      })
                    }
                  }
                },
                '查看'
              )
            ])
          }
        }
      ],
      searchResultData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 0,
      casePropertyList: [], // 案件性质
      childDictItemList: [], // 二级案件性质
      sampleTypeQuery: [], // 检材类型
      personTypeQuery: [], // 人员类型
      populationQuery: [], // 种群类型
      personRaceList: [], // 民族类型
      labServerList: [], // 实验室列表
      instorDatetypeQuery: [], // 入库类型
      selectByOrgInfo: [], // 委托单位
      selectByOrg: [], // 二级
      // data3: []
      changeFlag: false,
      orgidList: [],
      clientOrgList: [], // 委托单位
      data3: [
        {
          value: 'beijing',
          label: '北京',
          children: [
            {
              value: 'gugong',
              label: '故宫'
              // children: null
            },
            {
              value: 'tiantan',
              label: '天坛'
            },
            {
              value: 'wangfujing',
              label: '王府井'
            }
          ]
        },
        {
          value: 'jiangsu',
          label: '江苏',
          children: [
            {
              value: 'nanjing',
              label: '南京',
              children: [
                {
                  value: 'fuzimiao',
                  label: '夫子庙'
                }
              ]
            },
            {
              value: 'suzhou',
              label: '苏州',
              children: [
                {
                  value: 'zhuozhengyuan',
                  label: '拙政园'
                },
                {
                  value: 'shizilin',
                  label: '狮子林'
                }
              ]
            }
          ]
        }
      ]
    }
  },
  mounted() {},
  methods: {
    handleChangeOnSelect(value) {
      console.log(value)
      this.changeFlag = value
    },
    changeCascader(value, selectedData) {
      console.log(value, '参数一')
      console.log(selectedData, '参数二')
    },
    changePage(index) {
      this.currentPage = index
      this.getCaseQuery()
    },
    // 重置查询条件
    handleReset() {
      // Object.keys(this.searchForm).forEach(key => {
      //   this.searchForm[key] = ''
      // })
      this.searchForm = {}
      this.clientOrgList = []
    },
    getCaseQuery() {
      this.searchForm.clientOrgList = []
      if (this.clientOrgList.length !== 0) {
        this.searchForm.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.searchForm.clientOrgList = []
      }
      console.log()
      this.searchForm.pageIndex = this.currentPage
      this.searchForm.rows = 15
      console.log(this.searchForm, '查看参数')
      this.$axios
        .post('/database/caseinfo/caseQuery', this.searchForm)
        .then(res => {
          // console.log(res)
          this.isActive = false
          this.searchResultData = res.result.caseInfoVoList
          this.allRecordCount = res.result.pageInfo.allRecordCount
          this.pageCount = res.result.pageInfo.pageCount
        })
    },
    changeCaseType(val) {
      // 一级案件性质方法,暂不使用
      for (let i = 0; i < this.casePropertyList.length; i++) {
        if (val === this.casePropertyList[i].id) {
          this.childDictItemList = this.casePropertyList[i].childDictItemList
        }
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    from.meta.keepAlive = false
    from.meta.isBack = false
    // this.$destroy(true)
    next()
  },
  // beforeRouteEnter(to, from, next) {
  //   if (from.path === '/datamanage/caseDetail') {
  //     to.meta.keepAlive = true
  //     to.meta.isBack = true
  //   } else {
  //     to.meta.keepAlive = false
  //     to.meta.isBack = false
  //   }
  //   console.log('进入huancun判断', to.meta.keepAlive)
  //   next()
  // },
  activated() {
    if (!this.$route.meta.isBack) {
      console.log('进入清空')
      // console.log(this.$route.meta.isBack)
      this.handleReset()
      this.searchResultData = []
      this.currentPage = 1
      this.pageCount = 1
      this.allRecordCount = 0
      this.isActive = true
    } else {
      // this.$route.meta.keepAlive = false
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  },
  // deactivated() {
  //   this.$destroy(true)
  // }
  created() {
    // this.$forceUpdate()
    if (sessionStorage.getItem('caseNatureQuery')) {
      // 案件性质
      const arr = sessionStorage.getItem('caseNatureQuery')
      this.casePropertyList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('sampleTypeQuery')) {
      // 检材类型
      const arr = sessionStorage.getItem('sampleTypeQuery')
      this.sampleTypeQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personTypeQuery')) {
      // 人员类型
      const arr = sessionStorage.getItem('personTypeQuery')
      this.personTypeQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('instorDatetypeQuery')) {
      // 入库类型
      const arr = sessionStorage.getItem('instorDatetypeQuery')
      this.instorDatetypeQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('populationQuery')) {
      // 种群类型
      const arr = sessionStorage.getItem('populationQuery')
      this.populationQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personRaceList')) {
      // 民族类型
      const arr = sessionStorage.getItem('personRaceList')
      this.personRaceList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('labServerList')) {
      //
      const arr = sessionStorage.getItem('labServerList')
      this.labServerList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
      // console.log(this.selectByOrgInfo)
      console.log('case,查看委托单位', this.selectByOrgInfo)
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamanage';
</style>
