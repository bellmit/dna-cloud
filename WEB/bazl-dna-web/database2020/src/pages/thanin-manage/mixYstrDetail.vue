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
            <router-link to="/thanin/homotype">Y-STR比中</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/thanin/homotypeSourceDetail"
              >查看源案件详情</router-link
            >
          </Col>
        </Row>
        <div class="thanin-part">
          <div class="title">
            比中情况
          </div>
          <Row class="item-row">
            <Col span="6" class="item-col">
              <span class="item-label">审核人: </span>
              <span>{{ titleInfo.reviewPersonName }}</span>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">审核状态: </span>
              <span>{{
                titleInfo.reviewResultCode === '1' ? '已比中' : '待审核'
              }}</span>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">审核时间: </span>
              <span>{{ titleInfo.reviewTime }}</span>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">比中时间: </span>
              <span>{{ titleInfo.matchTime }}</span>
            </Col>
          </Row>
          <Table
            border
            :columns="thaninInfoCol"
            :data="thaninInfoData"
            class="bazl-table"
          ></Table>
        </div>
        <div class="list-part">
          <div class="title">
            列表详情
          </div>
          <Table
            border
            :columns="listDetailCol"
            :data="listDetailData"
            class="bazl-table"
            :row-class-name="handclassName"
          ></Table>
          <Row class="btn-row">
            <button
              class="btn btn-yellow-bg"
              @click.prevent="handleLiftConfirm(1)"
            >
              确认比中
            </button>
            <button
              class="btn btn-red-bg"
              @click.prevent="handleLiftConfirm(0)"
            >
              解除关联
            </button>
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回上一页
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
      length: '',
      SidebarParams: {
        open: ['3'],
        active: '3-1'
      },
      searchForm: {},
      vis: '占位',
      thaninInfoCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '样本类型',
          key: 'sampleType',
          align: 'center'
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '提交人',
          key: 'storePersonName',
          align: 'center'
        },
        {
          title: 'Panel',
          key: 'panelName',
          align: 'center'
        }
      ],
      thaninInfoData: [{}, {}],
      listDetailCol: [
        {
          title: '序号',
          // type: 'index',
          align: 'center',
          width: 80,
          render: (h, params) => {
            // console.log(params, '1231231index')
            if (params.index === this.length - 1) {
              // console.log('进入判断')
              return h(
                'span',
                {
                  prop: {},
                  style: {
                    // color: '#00439E'
                  }
                },
                '合计'
              )
            } else {
              return h(
                'span',
                {
                  prop: {},
                  style: {
                    // color: 'blue-font'
                  }
                },
                params.index + 1
              )
            }
          }
        },
        {
          title: '基因座',
          key: 'alleleKey',
          align: 'center'
        },
        {
          title: '等位基因',
          align: 'center',
          children: [
            {
              title: ' ',
              key: 'alleleValue',
              align: 'center',
              renderHeader: (h, params) => {
                return h('div', this.sampleNo)
              }
            },
            {
              title: ' ',
              key: 'matchAlleleValue',
              align: 'center',
              renderHeader: (h, params) => {
                return h('div', this.matchSampleNo)
              }
            }
          ]
        },
        {
          title: 'LR',
          key: 'LR',
          align: 'center'
        }
      ],
      listDetailData: [],
      groupId: null,
      id: null,
      titleInfo: {},
      matchSampleNo: '',
      sampleNo: ''
    }
  },
  created() {},
  mounted() {
    this.id = this.$route.query.id
    this.groupId = this.$route.query.groupId
    this.getStrGeneQuery()
  },
  methods: {
    // 行样式
    handclassName(row, index) {
      // console.log(index)
      if (index === this.length - 1) {
        return 'blue-font'
      }
      if (row.diffAllele) {
        return 'red-font'
      }
    },
    // 页面初始数据
    getStrGeneQuery() {
      this.$axios
        .get('/database/match/ystr/geneQuery', {
          params: {
            id: this.id
          }
        })
        .then(res => {
          // console.log(res, '123123')
          this.thaninInfoData = res.result.resultList
          this.listDetailData = res.result.resultGeneInfo.alleleList
          this.titleInfo = res.result.resultGeneInfo
          this.sampleNo = res.result.resultGeneInfo.sampleNo
          this.matchSampleNo = res.result.resultGeneInfo.matchSampleNo
          this.listDetailData.push({
            alleleKey: res.result.resultGeneInfo.matchLocusCount,
            LR: res.result.resultGeneInfo.totalLR
          })
          this.length = this.listDetailData.length
          // this.listDetailData =
          //   res.result.resultGeneInfo.strSameCompareResultAlleleList
          // if (res.code === 200) {
          //   this.thaninInfoData = res.data.resultList
          //   this.reviewPersonName = res.data.resultList[0].reviewPersonName
          //   this.reviewFlag = res.data.resultList[0].reviewFlag
          //   this.reviewDatetime = res.data.resultList[0].reviewDatetime
          //   this.compareDatetime = res.data.resultList[0].compareDatetime
          // }
        })
    },
    goBack() {
      this.$router.go(-1)
      // this.$router.push({
      //   name: 'SeeGroupDetails'
      // })
    },
    handleLiftConfirm(type) {
      const ids = []
      ids.push(this.id)
      // console.log(ids)
      const info = {
        ids: ids,
        resultCode: type
      }
      this.$axios
        .post('/database/match/ystr/updateResultCode', info)
        .then(res => {
          if (res.code === 1) {
            this.$Modal.success({
              title: '恭喜',
              content: '操作成功！'
            })
            this.getStrGeneQuery()
          } else {
            this.$Modal.error({
              title: '抱歉',
              content: '操作失败,请重试！'
            })
          }
        })
    }
  },
  beforeRouteLeave(to, from, next) {
    if (to.path === '/seegroup') {
      to.meta.keepAlive = true
    }
    next()
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/seesourcecase';
</style>
