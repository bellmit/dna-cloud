<template>
  <div class="content-box">
    <Row>
      <!-- <Col span="4">
        <SideBar :SidebarParams="SidebarParams"></SideBar>
      </Col> -->
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>比中信息管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/thanin/kinship">亲缘比中</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row" :gutter="16">
            <Col span="8" class="item-col">
              <span class="item-label">案件名称</span>
              <Input
                type="text"
                placeholder="请输入案件名称"
                class="item-input"
                v-model.trim="searchForm.caseName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件受理编号</span>
              <Input
                type="text"
                placeholder="请输入"
                class="item-input"
                v-model.trim="searchForm.caseAcceptNo"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">现场勘验编号</span>
              <Input
                type="text"
                placeholder="请输入"
                class="item-input"
                v-model.trim="searchForm.sysXkNo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row" :gutter="16">
            <Col span="8" class="item-col">
              <span class="item-label">案件性质</span>
              <Select
                multiple
                placeholder="请选择样本类型"
                class="item-select"
                v-model="searchForm.casePropertys"
                @on-change="changeCaseType"
              >
                <Option
                  v-for="item in caseNatureQuery"
                  :value="item.dictCode"
                  :key="item.dictCode"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件受理时间</span>
              <DatePicker
                type="date"
                placeholder="开始时间"
                style="width: 120px;"
                v-model="searchForm.caseAcceptStartDatetime"
              ></DatePicker>
              <DatePicker
                type="date"
                placeholder="结束时间"
                style="width: 120px; margin-left: 10px;"
                v-model="searchForm.caseAcceptEndDatetime"
              ></DatePicker>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">委托单位</span>
              <span class="item-group">
                <Cascader
                  placeholder="请选择委托单位"
                  :data="selectByOrgInfo"
                  style="display:inline-block;width:250px;"
                  v-model="clientOrgList"
                  :change-on-select="true"
                ></Cascader>
              </span>
            </Col>
          </Row>
          <Row class="item-row" :gutter="16">
            <Col span="8" class="item-col">
              <span class="item-label">检材类型</span>
              <!-- <Select
                placeholder="请选择检材类型"
                class="item-select"
                v-model="searchForm.caseProperty"
              >
                <Option v-for="item in sampleTypeQuery"
                  :value="item.dictCode"
                  :key="item.dictCode">{{item.dictName}}</Option>
              </Select> -->
              <Select
                multiple
                placeholder="请选择检材类型"
                class="item-select"
                v-model="searchForm.sampleTypes"
              >
                <Option
                  v-for="item in sampleTypeQuery"
                  :value="item.dictCode"
                  :key="item.dictCode"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">检材编号</span>
              <Input
                type="text"
                placeholder="检材DNA编号"
                class="item-input"
                v-model.trim="searchForm.sampleNo"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">检材名称</span>
              <Input
                type="text"
                placeholder="检材名称"
                class="item-input"
                v-model.trim="searchForm.sampleName"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row" :gutter="16">
            <Col span="8" class="item-col">
              <span class="item-label">人员姓名</span>
              <Input
                type="text"
                placeholder="人员姓名"
                class="item-input"
                v-model.trim="searchForm.personName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">身份证号</span>
              <Input
                type="text"
                placeholder="身份证号"
                class="item-input"
                v-model.trim="searchForm.personCard"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">比中起止时间</span>
              <DatePicker
                type="date"
                placeholder="开始时间"
                style="width: 120px;"
                v-model="searchForm.compareStartDatetime"
              ></DatePicker>
              <DatePicker
                type="date"
                placeholder="结束时间"
                style="width: 120px; margin-left: 10px;"
                v-model="searchForm.compareEndDatetime"
              ></DatePicker>
            </Col>
          </Row>
          <Row class="item-row" :gutter="16">
            <Col span="8" class="item-col">
              <span class="item-label">审核状态</span>
              <Select
                placeholder="请选择审核状态"
                class="item-select"
                v-model="searchForm.caseStates"
              >
                <Option value="1">待复核</Option>
                <Option value="2">已复核</Option>
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">匹配数(大于等于)</span>
              <Input
                type="text"
                placeholder="请输入匹配数"
                class="item-input"
                v-model.trim="searchForm.matchLocusCount"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">比对结果类别</span>
              <Select
                placeholder="请选择比对结果类别"
                class="item-select"
                v-model="searchForm.caseProperty"
              >
                <Option value="1">人员-物证比中</Option>
                <Option value="2">物证-物证比中</Option>
                <Option value="3">人员比中</Option>
              </Select>
            </Col>
          </Row>
          <Row class="item-row" :gutter="16">
            <Col span="24" class="btn-row">
              <button
                class="btn btn-blue-bg"
                style="margin-right:10px;"
                @click="getStrResultQuery"
              >
                查询
              </button>
              <button class="btn btn-blue-border" @click="handleReset">
                重置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <Row class="title">
            <Col span="12" class="left-title">
              <span>比中结果列表</span>
              <button class="btn btn-blue-bg">批量导出确认比中结果Excel</button>
              <button class="btn btn-blue-border">案件串并文件生成</button>
            </Col>
            <Col span="12" class="right-title">
              <button
                class="btn btn-blue-border"
                @click.prevent="handleCheckAll"
              >
                全选
              </button>
              <button
                class="btn btn-red-bg"
                @click.prevent="handleLiftConfirmAll(2)"
              >
                解除关联
              </button>
              <button
                class="btn btn-blue-bg"
                @click.prevent="handleLiftConfirmAll(1)"
              >
                确认比中
              </button>
            </Col>
          </Row>
          <!-- v-mode="checkedGroup" -->
          <CheckboxGroup @on-change="changeCheck" v-model="checkedGroup">
            <div
              class="table-group"
              v-for="(item, index) in tableGroups"
              :key="item.id"
            >
              <Row class="group-name">
                <Col span="2">
                  <Checkbox :label="item.groupId"
                    >第{{ (index + 1) | NoToString }}组</Checkbox
                  >
                  <!-- <button class="btn btn-blue-bg">案件串并文件生成</button> -->
                </Col>
                <!-- <Col span="11" class="center-title">
                  <span>
                    比中类型:
                    <span class="blue-font">人员类型</span>
                  </span>
                  <span>
                    最新比中时间:
                    <span class="blue-font">{{item.groupMatchTime}}</span>
                  </span>
                  <span>
                    最多位点匹配数:
                    <span class="blue-font">{{item.groupMatchLocusCount}}</span>
                  </span>
                </Col> -->
                <Col span="11">
                  <Select style="width:120px;">
                    <Option>222</Option>
                    <Option>222</Option>
                    <Option>222</Option>
                  </Select>
                  <button class="btn btn-blue-bg" style="margin:0 10px;">
                    生成串并案鉴定书
                  </button>
                  <button class="btn btn-blue-border">打印资格证书</button>
                </Col>
              </Row>
              <Table
                border
                :columns="tableCol"
                :data="item.groupList"
                class="bazl-table"
                size="small"
                :span-method="handleSpan"
              ></Table>
            </div>
          </CheckboxGroup>
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
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  filters: {
    NoToString(n) {
      switch (n) {
        case 1:
          return '一'
        // break
        case 2:
          return '二'
        case 3:
          return '三'
        case 4:
          return '四'
        case 5:
          return '五'
        case 6:
          return '六'
        case 7:
          return '七'
        case 8:
          return '八'
        case 9:
          return '九'
        case 10:
          return '十'
        case 11:
          return '十一'
        case 12:
          return '十二'
        case 13:
          return '十三'
        case 14:
          return '十四'
        case 15:
          return '十五'
      }
    }
  },
  data() {
    return {
      SidebarParams: {
        open: ['3'],
        active: '3-1'
      },
      searchForm: {
        clientOrgList: []
      },
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 50
        },
        {
          title: '父/母/子',
          key: 'relationType',
          align: 'center',
          width: '60',
          render: (h, params) => {
            const text =
              params.row.relationType === '1'
                ? '父亲'
                : params.row.relationType === '2'
                  ? '母亲'
                  : '子女'
            return h('span', text)
          }
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '案件破获状态',
          key: 'caseStates',
          align: 'center',
          render: (h, params) => {
            // console.log(params.row.caseStates)
            // // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.row.caseStates === 0) {
              text = '未破获'
            } else {
              text = '已破获'
            }
            return h(
              'span',
              // {
              //   prop: {},
              //   style: {
              //     color: markSytel
              //   }
              // },
              text
            )
          }
        },
        {
          title: '比中案件名称',
          key: 'matchCaseName',
          align: 'center'
        },
        {
          title: '比中时间',
          key: 'matchDateTime',
          align: 'center'
        },
        {
          title: '比中样本编号',
          key: 'matchSampleNo',
          align: 'center'
        },
        {
          title: '比中样本名称',
          key: 'matchSampleName',
          align: 'center'
        },
        {
          title: '判定状态',
          key: 'reviewFlag',
          align: 'center',
          width: 60,
          render: (h, params) => {
            // console.log(params.row.caseStates)
            // eslint-disable-next-line no-unused-vars
            let text = ''
            let color = ''
            if (params.row.reviewFlag === 0) {
              text = '待复核'
              color = 'red'
            } else if (params.row.reviewFlag === 1) {
              text = '已比中'
              color = ''
            }
            return h(
              'span',
              {
                prop: {},
                style: {
                  color: color
                }
              },
              text
            )
          }
        },
        {
          title: '样本条码号',
          key: 'sampleNo',
          align: 'center'
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center'
        },
        {
          title: '入库样本类型',
          key: 'sampleTypeName',
          align: 'center'
        },
        {
          title: '所属分局',
          key: 'orgName',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                { class: 'hover-text', style: { marginLeft: '10px' } },
                [
                  h('Icon', {
                    props: {
                      type: 'ios-copy',
                      size: '20',
                      color: '#194481',
                      marginLeft: '20px'
                    }
                  }),
                  h(
                    'strong',
                    {
                      class: 'text',
                      style: {
                        color: '#194481',
                        display: 'none'
                      },
                      on: {
                        click: () => {
                          // const groupIndex = this.tableGroups.findIndex(
                          //     (value, index, arr) =>
                          //       value.groupId === params.row.groupId
                          //   ) + 1
                          // console.log(params.row.id)
                          this.$router.push({
                            path: '/thanin/KinshipDetail',
                            query: {
                              id: params.row.id
                            }
                          })
                        }
                      }
                    },
                    '查看详情'
                  )
                ]
              )
            ])
          }
        }
      ],
      tableData: [],
      tableGroups: [],
      checkedGroup: [],
      checkedList: [],
      caseNatureQuery: [], // 案件性质
      childDictItemList: [], // 二级案件性质
      sampleTypeQuery: [], // 检材类型
      personTypeQuery: [], // 人员类型
      populationQuery: [], // 种群类型
      personRaceList: [], // 民族类型
      labServerList: [], // 实验室列表
      instorDatetypeQuery: [], // 入库类型
      selectByOrgInfo: [], // 委托单位
      clientOrgList: []
    }
  },
  created() {
    if (sessionStorage.getItem('caseNatureQuery')) {
      // 案件性质
      const arr = sessionStorage.getItem('caseNatureQuery')
      this.caseNatureQuery = JSON.parse(arr)
      // console.log(111)
      // console.log(this.caseNatureQuery, '案件性质')
    }
    if (sessionStorage.getItem('sampleTypeQuery')) {
      // 检材类型
      const arr = sessionStorage.getItem('sampleTypeQuery')
      this.sampleTypeQuery = JSON.parse(arr)
      // console.log(this.sampleTypeQuery, '检材类型')
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
      // 委托单位
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
  },
  mounted() {
    // this.getStrResultQuery()
  },
  methods: {
    handleSpan(event, index) {
      if (event.columnIndex === 13) {
        // console.log('进入判断')
        return [3, 1]
      }
      // for (const item of this.tableGroups) {
      //   console.log(item.groupList.length, '位置11')
      // }
    },
    // 重置 查询条件
    handleReset() {
      Object.keys(this.searchForm).forEach(key => {
        this.searchForm[key] = ''
      })
      this.clientOrgList = []
    },
    changeCaseType() {},
    changePage() {},
    // 复选框
    changeCheck(val) {
      // console.log(val, 'cheadlafnfq123123')
      // console.log(this.tableGroups)
      for (const item of this.tableGroups) {
        if (item.groupId === val[0]) {
          for (const data of item.groupList) {
            this.checkedList.push(data.id)
          }
        }
      }
      // this.checkedGroup = ids
      // console.log(this.checkedGroup, '12312312')
    },
    // 全选按钮
    handleCheckAll() {
      // console.log(this.tableGroups, '1')
      // console.log(this.checkedGroup, '2')
      // console.log(this.checkedList, '3')
      // this.checkedGroup = [0, 1, 2]
      const groupids = []
      for (const x of this.tableGroups) {
        groupids.push(x.groupId)
      }
      // console.log(groupids, 'G')
      if (this.checkedGroup.length !== groupids.length) {
        this.checkedGroup = groupids
        for (const item of this.tableGroups) {
          for (const data of item.groupList) {
            this.checkedList.push(data.id)
          }
        }
      } else {
        // console.log('进入清空判断')
        this.checkedGroup = []
      }
    },
    // 查询数据
    getStrResultQuery() {
      this.searchForm.clientOrgList = []
      if (this.clientOrgList.length !== 0) {
        this.searchForm.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.searchForm.clientOrgList = []
      }
      this.$axios
        .post('/database/match/relative/resultQuery', this.searchForm)
        .then(res => {
          // console.log(res, '初始数据')
          this.tableGroups = res.result.resultList
          this.allRecordCount = res.result.pageInfo.allRecordCount
          this.pageCount = res.result.pageInfo.pageCount
          // this.currentPage = res.data.pageIndex
          // this.pageCount = Math.ceil(res.data.totalCount / 15)
        })
    },
    // 确认比中 解除关联操作按钮
    handleLiftConfirmAll(type) {
      if (this.checkedGroup.length > 0) {
        // const ids = this.checkedGroup.join(',')
        const info = {
          ids: this.checkedList,
          resultCode: type
        }
        this.$axios
          .post('/database/match/relative/updateResultCode', info)
          .then(res => {
            if (res.code === 1) {
              this.$Modal.success({
                title: '恭喜',
                content: '操作成功~'
              })
              this.getStrResultQuery()
              this.checkedList = []
            }
          })
      } else {
        this.$Modal.error({
          title: '抱歉',
          content: '请先选择要操作的组别！'
        })
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path === '/seesource' || from.path === '/seegroup') {
      to.meta.isBack = true
    } else {
      to.meta.isBack = false
    }
    next()
  },
  activated() {
    if (!this.$route.meta.isBack) {
      // this.handleReset()
      // this.getCaseQuery()
    } else {
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/homotype';
.center-title {
  height: 29px;
  line-height: 29px;
}
.center-title > span {
  margin: 0 5px;
}
</style>
