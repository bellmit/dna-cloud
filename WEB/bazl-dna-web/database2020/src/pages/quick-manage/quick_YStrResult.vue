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
            <router-link to="/quickmanage/quick_YStrResult"
              >比中结果</router-link
            >
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
        </div>
      </Col>
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
        {
          title: '累计似然比率',
          key: 'LR',
          align: 'center'
        },
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
                      'quickYstrDetail',
                      JSON.stringify(params.row)
                    )
                    this.$router.push({
                      query: {
                        status: 0
                      },
                      path: '/quickmanage/quick_YStrDetail'
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
    const id = sessionStorage.getItem('QucikYstrResultId')
    this.id = id
    // console.log(this.id, 'id')
    this.getResult()
    clearInterval(this.timer)
    this.timer = setInterval(this.getResult, 6000)
  },
  methods: {
    getResult() {
      this.$axios
        .get('/database/rapid/ystr/findResultYstr', {
          params: {
            id: this.id,
            noLoading: true
          }
        })
        .then(res => {
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
        .catch(error => {
          console.log(error, 'error')
          this.loadingModal = false
          this.$Modal.error({
            title: '抱歉',
            content: error.data.message
          })
        })
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
    .sub {
      padding: 6px 15px;
      color: #333;
    }
  }
}
</style>
