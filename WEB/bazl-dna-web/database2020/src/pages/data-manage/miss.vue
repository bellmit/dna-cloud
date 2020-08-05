<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>委托管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/miss">失踪人员</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            查询条件
          </div>
          <div class="content">
            <Form :model="searchForm" :label-width="100">
              <Row>
                <Col span="8">
                  <FormItem label="案件编号">
                    <Input
                      v-model="searchForm.caseAcceptNo"
                      placeholder="请输入案件编号"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="委托编号">
                    <Input
                      placeholder="请输入委托编号"
                      v-model="searchForm.consignmentNo"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="委托时间">
                    <DatePicker
                      v-model="dateTime"
                      type="daterange"
                      split-panels
                      placeholder="请选择委托时间"
                      style="width: 100%"
                      @on-change="changeDateTime"
                    ></DatePicker>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="案件名称">
                    <Input
                      placeholder="请输入案件名称"
                      v-model="searchForm.caseName"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="委托单位">
                    <Cascader
                      placeholder="请选择委托单位"
                      :data="selectByOrgInfo"
                      v-model="clientOrgList"
                      :change-on-select="true"
                    ></Cascader>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="送检人">
                    <Input
                      placeholder="请输入送检人"
                      v-model="searchForm.consignPersonName"
                    ></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <!-- <Col span="8">
                  <FormItem label="委托状态">
                    <Select
                      placeholder="请选择委托状态"
                      class="item-select"
                      v-model="searchForm.caseStates"
                    >
                      <Option value="1">状态-1</Option>
                      <Option value="2">状态-2</Option>
                    </Select>
                  </FormItem>
                </Col> -->
                <Col span="8">
                  <FormItem label="案件性质">
                    <Select
                      multiple
                      placeholder="请选择案件性质 - 支持多选"
                      v-model="searchForm.caseProperty"
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
                <Col span="6" offset="2">
                  <button
                    @click.prevent="handSearch"
                    class="btn btn-blue-bg"
                    style="margin-right:20px;"
                  >
                    查询
                  </button>
                  <button
                    class="btn btn-blue-border"
                    @click.prevent="handleReset"
                  >
                    重置
                  </button>
                </Col>
              </Row>
            </Form>
          </div>
          <div class="title">
            查询结果
          </div>
          <div class="content">
            <Table
              border
              :columns="tableCol"
              :data="tableData"
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
          <!-- <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回
            </button>
          </Row> -->
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dateTime: [],
      searchForm: {
        clientOrgList: [],
        consignmentRegStartDatetime: '',
        consignmentRegEndDatetime: ''
      },
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
          title: '委托编号',
          key: 'consignmentNo',
          align: 'center'
        },
        {
          title: '案件编号',
          key: 'caseAcceptNo',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '委托时间',
          key: 'consignmentRegDatetime',
          align: 'center'
        },
        {
          title: '委托单位',
          key: 'consignOrgName',
          align: 'center'
        },
        // {
        //   title: '委托状态',
        //   key: 'matchCaseName',
        //   align: 'center'
        // },
        {
          title: '送检人1',
          key: 'consignPerson1Name',
          align: 'center'
        },
        {
          title: '送检人2',
          key: 'consignPerson2Name',
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
                      this.$router.push({
                        path: '/datamanage/missDetails',
                        query: {
                          id: params.row.id
                        }
                      })
                    }
                  }
                },
                '查看'
              ),
              h(
                'span',
                {
                  class: 'green-font',
                  style: {
                    cursor: 'pointer',
                    marginRight: '10px'
                  }
                },
                '下载'
              )
            ])
          }
        }
      ],
      tableData: [],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      loadingModal: true,
      selectByOrgInfo: [], // 委托单位
      clientOrgList: [],
      casePropertyList: [] // 案件性质
    }
  },
  created() {
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
    if (sessionStorage.getItem('caseNatureQuery')) {
      // 案件性质
      const arr = sessionStorage.getItem('caseNatureQuery')
      this.casePropertyList = JSON.parse(arr)
    }
  },
  mounted() {
    this.getInitResult()
  },
  methods: {
    // 委托时间监听事件
    changeDateTime(val) {
      // console.log(val)
      // if (val) {
      //   this.searchForm.consignmentRegStartDatetime = val[0]
      //   this.searchForm.consignmentRegEndDatetime = val[1]
      // }
    },
    // 页面初始数据
    getInitResult() {
      this.searchForm.clientOrgList = []
      this.searchForm.consignmentRegStartDatetime = this.dateTime[0]
      this.searchForm.consignmentRegEndDatetime = this.dateTime[1]
      if (this.clientOrgList.length !== 0) {
        this.searchForm.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.searchForm.clientOrgList = []
      }
      this.searchForm.pageIndex = this.currentPage
      this.$axios
        .post('/database/casemissing/list', this.searchForm)
        .then(res => {
          console.log(res)
          this.tableData = res.result.list
          this.pageCount = res.result.pageInfo.pageCount
          this.allRecordCount = res.result.pageInfo.allRecordCount
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
      this.clientOrgList = []
      this.dateTime = []
    },
    // 表格 分页监听
    changePage(page) {
      this.currentPage = page
      this.getInitResult()
    },
    goBack() {
      this.$router.push({
        path: '/quickmanage/HomotypeRecord'
      })
    }
  }
}
</script>
<style lang="less" scoped>
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
