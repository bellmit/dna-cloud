<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Homotype">同型比对</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/HomotypeRecord"
              >比对记录查询</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/HomotypeRecordView"
              >比对结果</router-link
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
                  <FormItem label="样本编号">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="样本名称">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="数据类型">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="案件编号">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="案件名称">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="实验室名称">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="匹配下限">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="容差个数">
                    <Input v-model="searchForm.input" readonly></Input>
                  </FormItem>
                </Col>
              </Row>
            </Form>
          </div>
          <div class="title">
            比中结果列表
          </div>
          <div class="content">
            <Row>
              <Col span="24"
                >共比中案件 ({{ titleInfo.countCaseInfo }}) 条，比中人员 ({{
                  titleInfo.countPersonType
                }}) 个，比中物证 ({{ titleInfo.countPersonType }}) 个</Col
              >
            </Row>
            <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            ></Table>
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
      titleInfo: {},
      searchForm: {},
      id: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 40
        },
        {
          title: '比中实验室名称',
          key: 'labServerName',
          align: 'center'
        },
        {
          title: '比中数据类型',
          key: 'personTypeName',
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
          title: '比中案件编号',
          key: 'matchCaseNo',
          align: 'center'
        },
        {
          title: '比中案件名称',
          key: 'matchCaseName',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'matchedLocusCount',
          align: 'center'
        },
        {
          title: '比中位点个数',
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
                      sessionStorage.setItem(
                        'quickStrDetail',
                        JSON.stringify(params.row)
                      )
                      this.$router.push({
                        query: {
                          status: 1
                        },
                        path: '/quickmanage/HomotypeResultDetail'
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
      tableData: [{}],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      loadingModal: true
    }
  },
  created() {
    this.id = this.$route.query.id
  },
  mounted() {
    this.getInitResult()
  },
  methods: {
    // 页面初始数据
    getInitResult() {
      this.$axios.get('/database/rapid/str/get/' + this.id).then(res => {
        console.log(res)
        this.tableData = res.result.list
        this.titleInfo = res.result
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
