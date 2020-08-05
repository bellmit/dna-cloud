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
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Kinship">亲缘比对</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/KinshipResult"
              >比中结果列表</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/KinshipResultDetail"
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
            :row-class-name="handleLast"
            v-show="singTableFlag == 0"
          ></Table>
          <Table
            border
            :columns="singleTableCol"
            :data="singleTableData"
            class="bazl-table"
            :row-class-name="handleLast"
            v-show="singTableFlag == 1"
          ></Table>
          <!-- <Row class="add-tr">
            <Col span="4" offset="11">
              累计亲权指数（CPI）
            </Col>
            <Col span="3">
              {{visNo}}
            </Col>
            <Col span="3">
              {{visNo}}
            </Col>
            <Col span="3">
              {{visNo}}
            </Col>
          </Row> -->
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
        open: ['4'],
        active: '4-1'
      },
      result: {},
      labServerName: '',
      personTypeName: '',
      matchedLocusCount: '',
      matchSampleNo: '',
      matchSampleName: '',
      matchCaseNo: '',
      matchCaseName: '',
      visNo: '4.5E18',
      tableCol: [
        {
          title: '基因座名称',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.locusName
            )
          }
        },
        {
          title: '(父)样本等位基因',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.fatherGeneAllele
            )
          }
        },
        {
          title: '(母)样本等位基因',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.motherGeneAllele
            )
          }
        },
        {
          title: '(子女)样本等位基因',
          key: 'childGeneAllele',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.childGeneAllele
            )
          }
        },
        {
          title: '亲权指数',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.piVal
            )
          }
        },
        {
          title: '父子亲权指数',
          key: 'fatherPiVal',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.fatherPiVal
            )
          }
        },
        {
          title: '母子亲权指数',
          key: 'motherPiVal',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.motherPiVal
            )
          }
        }
      ],
      singleTableCol: [
        {
          title: '基因座名称',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.locusName
            )
          }
        },
        {
          title: '单亲样本等位基因',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.singleParentGeneAllele
            )
          }
        },
        {
          title: '(子女)样本等位基因',
          key: 'childGeneAllele',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.childGeneAllele
            )
          }
        },
        {
          title: '亲权指数',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: params.row.diffAllele === TextTrackCue ? 'red-font' : ''
              },
              params.row.piVal
            )
          }
        }
      ],
      TextTrackCue: true,
      singTableFlag: 0,
      lastTr: 0,
      tableData: [],
      singleTableData: []
    }
  },
  mounted() {
    const info = sessionStorage.getItem('quickKinshipDetail')
    if (info !== 'undefined') {
      this.result = JSON.parse(info)
    }
  },

  watch: {
    result: {
      handler: function(newVal, oldVal) {
        this.labServerName = newVal.labServerName
        this.personTypeName = newVal.personTypeName
        this.matchedLocusCount = newVal.matchedLocusCount
        this.matchSampleNo = newVal.matchSampleNo
        this.matchSampleName = newVal.matchSampleName
        this.matchCaseNo = newVal.matchCaseNo
        this.matchCaseName = newVal.matchCaseName
        const geneJson = JSON.parse(newVal.matchedGeneDetails)
        // console.log(geneJson, '----geneJson-----')
        const obj = {}
        obj.childGeneAllele = '累计亲权指数（CPI）'
        obj.piVal = geneJson.totalPiVal
        obj.fatherPiVal = geneJson.totalFatherPiVal
        obj.motherPiVal = geneJson.totalMotherPiVal
        if (geneJson.singleRelative === true) {
          this.singleTableData = geneJson.strRelativeCompareResultAlleleList
          this.singTableFlag = 1
          this.singleTableData.push(obj)
          this.lastTr = this.singleTableData.length - 1
        } else {
          this.tableData = geneJson.strRelativeCompareResultAlleleList
          this.singTableFlag = 0
          this.tableData.push(obj)
          this.lastTr = this.tableData.length - 1
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    handleLast(row, index) {
      // console.log(index, this.lastTr, typeof index)
      if (this.lastTr === index) {
        return 'blue-font'
      }
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
