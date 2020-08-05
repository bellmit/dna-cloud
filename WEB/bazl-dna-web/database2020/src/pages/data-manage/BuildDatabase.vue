<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/builddatabase"
              >建库数据管理</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">查询条件设置</div>
          <div class="item-title" style="border-top:none;">
            人员信息
          </div>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">建库人员类别</span>
              <span class="item-group">
                <Select
                  placeholder="请选择试建库人员类别"
                  v-model="searchInfo.criminalPersonType"
                  style="width:260px;"
                  :label-in-value="true"
                >
                  <Option
                    v-for="item in selectByCriminalPersonType"
                    :key="item.dictCode"
                    :value="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">姓名</span>
              <span class="item-group">
                <RadioGroup v-model="searchInfo.personNameCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入姓名"
                style="width:140px;"
                v-model="searchInfo.personName"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">身份证号</span>
              <span class="item-group">
                <RadioGroup v-model="searchInfo.personIdcardNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入身份证号"
                style="width:140px;"
                v-model="searchInfo.personIdcardNo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">性别</span>
              <span class="item-group">
                <Select
                  placeholder="请选择性别"
                  style="width:260px;"
                  v-model="searchInfo.personSex"
                >
                  <Option value="1">男</Option>
                  <Option value="2">女</Option>
                  <!-- <Option value="3">未知</Option> -->
                </Select>
              </span>
            </Col>
            <Col span="10">
              <span class="item-label">民族</span>
              <span class="item-group">
                <Select
                  placeholder="请选择民族"
                  v-model="searchInfo.personRace"
                  style="width:260px;"
                  :label-in-value="true"
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
          <Collapse>
            <div class="collapse-wrap" v-show="isActive">
              <slot>
                <div class="item-title">
                  采集信息
                </div>
                <Row class="item-row">
                  <Col span="8">
                    <span class="item-label">采集单位</span>
                    <span class="item-group">
                      <Cascader
                        :change-on-select="true"
                        :data="selectByOrgInfo"
                        style="width:260px;"
                        v-model="clientOrgList"
                      ></Cascader>
                    </span>
                  </Col>
                  <Col span="8">
                    <span class="item-label">采集人</span>
                    <Input
                      type="text"
                      placeholder="请输入姓名"
                      style="width:125px;"
                      v-model="searchInfo.collectPersonName"
                    ></Input>
                    <Input
                      type="text"
                      placeholder="请输入联系电话"
                      style="width:125px;margin-left:10px;"
                      v-model="searchInfo.collectPersonPhone"
                    ></Input>
                  </Col>
                  <Col span="8">
                    <span class="item-label">采集时间</span>
                    <span class="item-group">
                      <DatePicker
                        type="date"
                        placeholder="开始时间"
                        style="width: 125px"
                        v-model="searchInfo.collectDatetimeStartDate"
                        @on-change="changeStartTime"
                      ></DatePicker>
                      <DatePicker
                        type="date"
                        placeholder="结束时间"
                        style="width: 125px;margin-left:10px;"
                        v-model="searchInfo.collectDatetimeEndDate"
                      ></DatePicker>
                    </span>
                  </Col>
                </Row>
                <div class="item-title">
                  样本信息
                </div>
                <Row class="item-row">
                  <Col span="14">
                    <span class="item-label">DNA条码号</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchInfo.sampleLabNoCondition">
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入DNA条码号"
                      style="width:260px;"
                      v-model="searchInfo.sampleLabNo"
                    ></Input>
                  </Col>
                  <Col span="10">
                    <span class="item-label">国家库DNA编号</span>
                    <span class="item-group">
                      <RadioGroup v-model="searchInfo.nationSysNoCondition">
                        <Radio label="0">包含</Radio>
                        <Radio label="1">等于</Radio>
                      </RadioGroup>
                    </span>
                    <Input
                      type="text"
                      placeholder="请输入国家库编号"
                      style="width:260px;"
                      v-model="searchInfo.nationSysNo"
                    ></Input>
                  </Col>
                </Row>
                <div class="item-title">
                  其他
                </div>
                <Row class="item-row">
                  <Col span="14">
                    <span class="item-label">DNA实验室</span>
                    <span class="item-group">
                      <Select
                        placeholder="请选择DNA实验室"
                        style="display:inline-block;width:260px;"
                        v-model="searchInfo.labServerNo"
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
                  <Col span="10">
                    <span class="item-label">受理时间</span>
                    <span class="item-group">
                      <DatePicker
                        type="date"
                        placeholder="开始时间"
                        style="width: 125px"
                        v-model="searchInfo.acceptStartDate"
                      ></DatePicker>
                      <DatePicker
                        type="date"
                        placeholder="结束时间"
                        style="width: 125px;margin-left:10px;"
                        v-model="searchInfo.acceptEndDate"
                      ></DatePicker>
                    </span>
                  </Col>
                </Row>
                <Row class="tc">
                  <button class="btn btn-blue-bg" @click.prevent="handSearch">
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
        </div>
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
  data() {
    return {
      SidebarParams: {
        open: ['2'],
        active: '2-2'
      },
      isActive: true,
      searchForm: {},
      searchResultCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: '50'
        },
        {
          title: '人员类型',
          key: 'criminalPersonTypeName',
          align: 'center'
        },
        {
          title: '姓名',
          key: 'personName',
          align: 'center',
          width: '60'
        },
        {
          title: '性别',
          key: 'personGenderName',
          align: 'center',
          width: '60'
        },
        {
          title: '身份证号',
          key: 'personIdcardNo',
          align: 'center'
        },
        {
          title: '民族',
          key: 'personRaceName',
          align: 'center'
        },
        {
          title: '籍贯地',
          key: 'nativePlaceName',
          align: 'center'
        },
        {
          title: '样本DNA编号',
          key: 'sampleLabNo',
          align: 'center'
        },
        // {
        //   title: '样本类型',
        //   key: 'sampleTypeName',
        //   align: 'center'
        // },
        {
          title: '样本包装',
          key: 'samplePackage',
          align: 'center'
        },
        {
          title: '采集单位',
          key: 'collectOrgName',
          align: 'center'
        },
        {
          title: '采集时间',
          key: 'collectDatetime',
          align: 'center'
        },
        {
          title: '详情',
          key: 'action',
          align: 'center',
          width: '60',
          // className: 'blue-font',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  style: {
                    cursor: 'pointer',
                    color: '#00439E'
                  },
                  on: {
                    click: event => {
                      this.$router.push({
                        path: '/datamanage/BuildDatabaseDetail',
                        query: {
                          id: params.row.id
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
      searchInfo: {
        clientOrgList: []
      }, // 查询条件
      selectByCriminalPersonType: [], // 建库人员类型
      personRaceList: [], // 民族类型
      selectByOrgInfo: [], // 委托单位
      selectByOrg: [], // 二级单位
      labServerList: [], // 实验室列表
      orgidList: [],
      clientOrgList: [] // 委托单位
    }
  },
  created() {
    if (sessionStorage.getItem('selectByCriminalPersonType')) {
      // 质控样本类型
      const arr = sessionStorage.getItem('selectByCriminalPersonType')
      // console.log(arr)
      this.selectByCriminalPersonType = JSON.parse(arr)
    }
    if (sessionStorage.getItem('labServerList')) {
      // DNA实验室
      const arr = sessionStorage.getItem('labServerList')
      this.labServerList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personRaceList')) {
      // 民族类型
      const arr = sessionStorage.getItem('personRaceList')
      this.personRaceList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectByOrgInfo')) {
      //
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
  },
  mounted() {
    sessionStorage.setItem('caseDataOpen', '1')
  },
  methods: {
    changeStartTime(val) {
      // console.log(val)
    },
    changePage(val) {
      this.currentPage = val
      this.handInit()
    },
    // 重置查询条件
    handleReset() {
      // Object.keys(this.searchInfo).forEach(key => {
      //   if (key !== 'pageIndex') {
      //     this.searchInfo[key] = ''
      //   }
      // })
      this.searchInfo = {}
      this.clientOrgList = []
    },
    // 查询
    handSearch() {
      this.currentPage = 1
      this.handInit()
    },
    // 页面初始数据
    handInit() {
      this.searchInfo.clientOrgList = []
      if (this.clientOrgList.length !== 0) {
        this.searchInfo.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.searchInfo.clientOrgList = []
      }
      this.searchInfo.pageIndex = this.currentPage
      this.$axios
        .post('/database/criminal/criminalQuery', this.searchInfo)
        .then(res => {
          // console.log(res)
          this.isActive = false
          this.searchResultData = res.result.criminalList
          this.allRecordCount = res.result.pageInfo.allRecordCount
          this.pageCount = res.result.pageInfo.pageCount
        })
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path === '/datamanage/BuildDatabaseDetail') {
      to.meta.isBack = true
      to.meta.keepAlive = true
    } else {
      to.meta.isBack = false
      to.meta.keepAlive = false
    }
    next()
  },
  activated() {
    if (!this.$route.meta.isBack) {
      this.handleReset()
      this.searchResultData = []
      this.currentPage = 1
      this.pageCount = 1
      this.allRecordCount = 0
      this.isActive = true
    } else {
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamanage';
</style>
