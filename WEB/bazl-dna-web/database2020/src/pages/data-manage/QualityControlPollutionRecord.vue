<template>
  <div>
    <Row>
      <!-- <Col span="4">
        <SideBar :SidebarParams="SidebarParams"></SideBar>
      </Col> -->
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControl"
              >质控数据管理</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControlPollutionRecord"
              >查看污染记录</router-link
            >
          </Col>
        </Row>
        <div class="part-detail viewgene">
          <Row class="title">
            <Col span="12">
              比中样本列表
            </Col>
          </Row>
          <Row style="padding:0 15px">
            <Col span="12">共比中 {{ allRecordCount }}条 </Col>
            <Col span="12" class="tr">
              比中案件
              <span class="blue-font"> {{ caseCount }}条, </span>
              比中样本
              <span class="blue-font"> {{ totalCount }}个 </span>
            </Col>
          </Row>
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
      SidebarParams: {
        open: ['2'],
        active: '2-3'
      },
      count: 0,
      caseCount: 0,
      sampleCount: 0,
      id: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
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
          title: '样本类型',
          key: 'sampleTypeName',
          align: 'center'
        },
        {
          title: '上样板号',
          key: 'matchBoardNo',
          align: 'center'
        },
        {
          title: '试剂盒名称',
          key: 'panelName',
          align: 'center'
        },
        {
          title: '匹配位点个数',
          key: 'matchLocusCount',
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
                    this.$router.push({
                      path: '/datamanage/qtyCtrlDetails',
                      query: {
                        initList: params.row
                      }
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
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      totalCount: 0
    }
  },
  mounted() {
    this.id = this.$route.query.id
    // console.log(this.id, 'id')
    this.getPollution()
  },
  methods: {
    goBack() {
      // this.$router.go(-1)
      this.$router.push({
        path: '/datamanage/QualityControl'
      })
    },
    changePage(index) {
      this.currentPage = index
      this.getPollution()
    },
    getPollution() {
      const info = {
        id: this.id,
        pageIndex: this.currentPage
        // rows: 15
      }
      this.$axios
        .post('/database/qcPollute/selectListByQcSampleId', info)
        .then(res => {
          // console.log(res, '质控')
          this.tableData = res.result.qcSamplePolluteRecordVoList
          this.allRecordCount = res.result.pageInfo.allRecordCount
          this.pageCount = res.result.pageInfo.pageCount
          this.totalCount = res.result.totalCount
          this.caseCount = res.result.caseCount
        })
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path === '/datamanage/qtyCtrlDetails') {
      to.meta.isBack = true
    } else {
      to.meta.isBack = false
    }
    next()
  },
  // beforeRouteLeave(to, from, next) {
  //   if (to.path === '/datamanage/QualityControl') {
  //     to.meta.keepAlive = true
  //   }
  //   next()
  // },
  activated() {
    if (!this.$route.meta.isBack) {
      // 初始化数据 此时条件为false
      this.id = this.$route.query.id
      // console.log(this.id)
      this.getPollution()
    } else {
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/viewgenotyping';
</style>
