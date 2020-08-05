<template>
  <div class="content-box">
    <Row>
      <!-- <Col span="4">
        <SideBar :SidebarParams="SidebarParams"></SideBar>
      </Col> -->
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24" v-show="status_0">
            当前位置:
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Y_STR">Y-STR比对</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quick_YStrResult"
              >比中结果</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quick_YStrDetail"
              >比中结果详情</router-link
            >
          </Col>
          <Col span="24" v-show="status_1">
            当前位置:
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Y_STR">Y-STR比对</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quickYstrRecord"
              >比中记录查询</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quickYstrRecordView"
              >比中结果</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/quick_YStrDetail"
              >比中结果详情</router-link
            >
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            比中结果详情
          </div>
          <Row class="sub">
            <Col span="7" class="item-col"
              >比中实验室名称:{{ labServerName }}</Col
            >
            <Col span="5" class="item-col"
              >比中数据类型:{{ personTypeName }}</Col
            >
            <Col span="6" class="item-col"
              >匹配位点个数:{{ matchedLocusCount }}</Col
            >
          </Row>
          <Row class="sub">
            <Col span="7" class="item-col"
              >比中样本编号:{{ matchSampleNo }}</Col
            >
            <Col span="5" class="item-col"
              >比中样本名称:{{ matchSampleName }}</Col
            >
            <Col span="6" class="item-col">比中案件编号:{{ matchCaseNo }}</Col>
            <Col span="6" class="item-col"
              >比中案件名称:{{ matchCaseName }}</Col
            >
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
          ></Table>
          <!-- <div class="add-tr">累计似然率: ( {{ visNo }} )</div> -->
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
      status_0: false,
      status_1: false,
      SidebarParams: {
        open: ['4'],
        active: '4-1'
      },
      labServerName: '',
      personTypeName: '',
      matchedLocusCount: '',
      matchSampleNo: '',
      matchSampleName: '',
      matchCaseNo: '',
      matchCaseName: '',
      visNo: 0,
      tableCol: [
        {
          title: '基因座名称',
          // key: 'name',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === true ? 'red-font' : ''
              },
              params.row.alleleKey
            )
          }
        },
        {
          title: '比对源样本',
          key: 'gene1',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === true ? 'red-font' : ''
              },
              params.row.alleleValue
            )
          }
        },
        {
          title: '比中样本',
          key: 'gene2',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === true ? 'red-font' : ''
              },
              params.row.matchAlleleValue
            )
          }
        }
        // {
        //   title: '似然率(LR)',
        //   key: 'LR',
        //   align: 'center'
        // }
      ],
      tableData: [],
      result: {}
    }
  },
  created() {
    console.log(this.$route.query.status, '-=-==-=')
    const status = this.$route.query.status
    if (status === 1) {
      this.status_1 = true
      this.status_0 = false
    } else {
      this.status_1 = false
      this.status_0 = true
    }
  },
  mounted() {
    const info = sessionStorage.getItem('quickYstrDetail')
    if (info !== 'undefined') {
      this.result = JSON.parse(info)
      // console.log(this.result, '初始数据')
    }
  },
  watch: {
    result: {
      handler: function(newVal, oldVal) {
        // console.log(newVal)
        this.labServerName = newVal.labServerName
        this.personTypeName = newVal.personTypeName
        this.matchedLocusCount = newVal.matchedLocusCount
        this.matchSampleNo = newVal.matchSampleNo
        this.matchSampleName = newVal.matchSampleName
        this.matchCaseNo = newVal.matchCaseNo
        this.matchCaseName = newVal.matchCaseName
        this.tableData = newVal.geneMap.alleleList
        // this.visNo = newVal.totalLR
      },
      deep: true
      // immediate: true
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
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
    .sub {
      padding: 6px 15px;
      color: #333;
    }
    .add-tr {
      margin-top: 6px;
      color: #00439e;
      text-align: right;
      padding-right: 30px;
      font-size: 16px;
    }
  }
}
</style>
