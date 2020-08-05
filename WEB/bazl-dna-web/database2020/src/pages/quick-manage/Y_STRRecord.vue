<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Y_STR">Y-STR比对</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quickYstrRecord"
              >比中记录查询</router-link
            >
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
                  <FormItem label="实验室名称">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="样本编号">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="样本名称">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="案件编号">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案件名称">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="数据类型">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="提交时间">
                    <Input
                      v-model="searchForm.input"
                      placeholder="请输入"
                    ></Input>
                  </FormItem>
                </Col>
                <Col span="8" offset="2">
                  <button class="btn btn-blue-bg" style="margin-right:20px;">
                    查询
                  </button>
                  <button class="btn btn-blue-border">重置</button>
                </Col>
              </Row>
            </Form>
          </div>
          <div class="title">
            比中结果列表
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
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回
            </button>
          </Row>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchForm: {},
      id: '',
      tableCol: [
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
          title: '提交时间',
          key: 'matchSampleNo',
          align: 'center'
        },
        {
          title: '拆分个数',
          key: 'matchSampleName',
          align: 'center'
        },
        {
          title: '比中数量',
          key: 'matchCaseNo',
          align: 'center'
        },
        {
          title: '案件编号',
          key: 'matchCaseName',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'matchedLocusCount',
          align: 'center'
        },
        {
          title: '比中详情',
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
                    click: event => {
                      this.$router.push({
                        query: {
                          id: params.row.id
                        },
                        path: '/quickmanage/quickYstrRecordView'
                      })
                    }
                  }
                },
                '查看'
              ),
              h(
                'span',
                {
                  class: 'green-font'
                },
                '重新比对'
              )
            ])
          }
        }
      ],
      tableData: [{}],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      loadingModal: true,
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 0
    }
  },
  mounted() {
    this.getInitResult()
  },
  methods: {
    // 页面初始数据
    getInitResult() {
      this.searchForm.pageIndex = this.currentPage
      this.$axios
        .post('/database/rapid/ystr/list', this.searchForm)
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
        path: '/quickmanage/Y_STR'
      })
    }
  },
  beforeDestroy() {
    clearInterval(this.timer)
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
