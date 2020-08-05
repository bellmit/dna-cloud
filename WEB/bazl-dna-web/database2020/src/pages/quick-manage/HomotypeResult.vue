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
            <router-link to="/quickmanage/HomotypeResult">比中结果</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            比中结果列表
          </div>
          <Row class="sub">
            <Col span="12" class="tl">
              共比中<span class="blue-font">{{ count }}</span
              >条
            </Col>
            <Col span="12" class="tr"
              >比中案件<span class="blue-font">{{ caseCount }}</span
              >条,比中人员<span class="blue-font">{{ personCount }}</span
              >个,比中物证<span class="blue-font">{{ evidenceCount }}</span
              >个</Col
            >
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
          ></Table>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回
            </button>
          </Row>
          <Modal
            v-model="loadingModal"
            width="400"
            class="bazl-modal"
            :closable="false"
            :mask-closable="false"
          >
            <div class="header"></div>
            <div class="content" style="padding:30px 85px">
              <h2>正在比对中，请稍后...</h2>
            </div>
            <Row class="btn-row">
              <!-- <button class="btn btn-blue-bg">
            保存
          </button>
          <button class="btn btn-blue-border" @click="loadingModal = false">
            取消
          </button> -->
            </Row>
          </Modal>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      vis: '220',
      id: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
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
          title: '匹配位点个数',
          key: 'matchedLocusCount',
          align: 'center'
        },
        // {
        //   title: '累计似然比率',
        //   key: '',
        //   align: 'center'
        // },
        {
          title: '比中详情',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'blue-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: () => {
                    // console.log(params.row)
                    sessionStorage.setItem(
                      'quickStrDetail',
                      JSON.stringify(params.row)
                    )
                    this.$router.push({
                      query: {
                        status: 0
                      },
                      path: '/quickmanage/HomotypeResultDetail'
                    })
                  }
                }
              },
              '查看'
            )
          }
        }
      ],
      tableData: [],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      loadingModal: true
    }
  },
  mounted() {
    // this.id = this.$route.params.id
    const id = sessionStorage.getItem('HomotypeResultId')
    this.id = id
    // console.log(this.id, 'id')
    this.getResult()
    clearInterval(this.timer)
    this.timer = setInterval(this.getResult, 5000)
  },
  methods: {
    getResult() {
      this.$axios
        .get('/database/rapid/str/findResultSame', {
          params: {
            id: this.id,
            noLoading: true
          }
        })
        .then(res => {
          // this.count = res.result.count
          // this.caseCount = res.result.caseCount
          // this.evidenceCount = res.result.evidenceCount
          // this.personCount = res.result.personCount
          // this.tableData = res.result.quickResultList
          if (res.result.queueStatus === '1') {
            this.loadingModal = false
            clearInterval(this.timer)
            this.$Message.success({
              background: true,
              content: '比对结束，请查看比对结果！',
              duration: 3
            })
            this.count = res.result.count
            this.caseCount = res.result.caseCount
            this.evidenceCount = res.result.evidenceCount
            this.personCount = res.result.personCount
            this.tableData = res.result.quickResultList
          }
        })
    },
    goBack() {
      this.$router.push({
        path: '/quickmanage/Homotype'
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
    .sub {
      padding: 6px 15px;
      color: #333;
    }
  }
}
</style>
