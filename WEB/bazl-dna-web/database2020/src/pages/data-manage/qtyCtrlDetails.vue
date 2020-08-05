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
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControl"
              >质控数据管理</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControlPollutionRecord"
              >查看污染记录</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/qtyCtrlDetails"
              >查看比中结果</router-link
            >
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            比中结果详情
          </div>
          <Row class="sub">
            <Col span="6" class="item-col"
              >比中实验室名称: {{ titleinfo.labServerName }}</Col
            >
            <Col span="6" class="item-col"
              >比中数据类型: {{ titleinfo.personTypeName }}</Col
            >
            <Col span="6" class="item-col"
              >匹配位点个数: {{ titleinfo.matchLocusCount }}</Col
            >
          </Row>
          <Row class="sub">
            <Col span="6" class="item-col"
              >比中样本编号: {{ titleinfo.matchSampleNo }}</Col
            >
            <Col span="6" class="item-col"
              >比中样本名称: {{ titleinfo.matchSampleName }}</Col
            >
            <Col span="6" class="item-col"
              >比中案件编号: {{ titleinfo.matchCaseNo }}</Col
            >
            <Col span="6" class="item-col"
              >比中案件名称: {{ titleinfo.matchCaseName }}</Col
            >
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
          ></Table>
          <div class="add-tr">累计似然率:{{ visNo }}</div>
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
      vis: '朝阳分局的违法犯罪人员',
      visNo: '',
      tableCol: [
        {
          title: '基因座名称',
          key: 'name',
          align: 'center'
        },
        {
          title: '比对源样本',
          key: 'gene1',
          align: 'center'
        },
        {
          title: '比中样本',
          key: 'gene2',
          align: 'center'
        },
        {
          title: '似然率(LR)',
          key: '',
          align: 'center'
        }
      ],
      tableData: [],
      titleinfo: {}
    }
  },
  // beforeRouteLeave (to, from, next) {
  //   if (to.path === '/datamanage/QualityControlPollutionRecord') {
  //     to.meta.keepAlive = true
  //   }
  //   next()
  // },
  mounted() {
    this.tableData = this.$route.query.initList.geneMap.resultList
    this.titleinfo = this.$route.query.initList
    // console.log(this.titleinfo)
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
