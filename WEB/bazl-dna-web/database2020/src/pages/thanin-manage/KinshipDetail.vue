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
            <router-link to="/thanin/kinship">亲缘比中</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/thanin/KinshipDetail">查看详情</router-link>
          </Col>
        </Row>
        <div class="thanin-part">
          <div class="title">
            比中情况
          </div>
          <!-- 0待审核 1确认比中  2 解除关联 -->
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
          <!-- show-summary 合计 -->
          <Table
            border
            :columns="tabSwitch"
            :data="listDetailData"
            class="bazl-table"
            :row-class-name="handleLast"
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
      SidebarParams: {
        open: ['3'],
        active: '3-1'
      },
      searchForm: {},
      vis: '占位',
      reviewPersonName: '',
      reviewFlag: '',
      reviewDatetime: '',
      compareDatetime: '',
      thaninInfoCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '父/母/子',
          key: 'relativeName',
          align: 'center'
        },
        {
          title: '检材类型',
          key: 'sampleTypeName',
          align: 'center'
        },
        {
          title: '检材信息',
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
        // {
        //   title: '序号',
        //   type: 'index',
        //   align: 'center',
        //   width: 80
        // },
        {
          title: '基因座',
          key: 'locusName',
          align: 'center'
        },
        {
          title: '等位基因',
          align: 'center',
          children: [
            {
              title: '父亲',
              key: 'fatherGeneAllele',
              align: 'center'
            },
            {
              title: '母亲',
              key: 'motherGeneAllele',
              align: 'center'
            },
            {
              title: '子女',
              key: 'childGeneAllele',
              align: 'center'
            }
          ]
        },
        {
          title: '亲权指数(PI值)',
          key: 'piVal',
          align: 'center'
        },
        {
          title: '父子亲权指数(PI值)',
          key: 'fatherPiVal',
          align: 'center'
        },
        {
          title: '母子亲权指数(PI值)',
          key: 'motherPiVal',
          align: 'center'
        }
      ],
      listDetailCol_1: [
        // {
        //   title: '序号',
        //   type: 'index',
        //   align: 'center',
        //   width: 80
        // },
        {
          title: '基因座',
          key: 'locusName',
          align: 'center'
        },
        {
          title: '等位基因',
          align: 'center',
          children: [
            {
              title: '单亲',
              key: 'singleParentGeneAllele',
              align: 'center'
            },
            {
              title: '子女',
              key: 'childGeneAllele',
              align: 'center'
            }
          ]
        },
        {
          title: '亲权指数(PI值)',
          key: 'piVal',
          align: 'center'
        }
      ],
      listDetailData: [],
      titleInfo: {},
      id: '',
      lastTr: null,
      tabFlag: false
    }
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    tabSwitch() {
      if (this.tabFlag) {
        return this.listDetailCol_1
      } else {
        return this.listDetailCol
      }
    }
  },
  mounted() {
    this.id = this.$route.query.id
    this.getStrGeneQuery()
  },
  methods: {
    getStrGeneQuery() {
      this.$axios
        .get('/database/match/relative/geneQuery', {
          params: {
            id: this.id
          }
        })
        .then(res => {
          // console.log(res, '详情数据')
          this.titleInfo = res.result.resultGeneInfo
          this.thaninInfoData = res.result.resultList
          this.piVal = res.result.resultGeneInfo.totalPiVal
          this.fatherPiVal = res.result.resultGeneInfo.totalFatherPiVal
          this.motherPiVal = res.result.resultGeneInfo.totalMotherPiVal
          this.tabFlag = res.result.resultGeneInfo.singleRelative
          this.listDetailData =
            res.result.resultGeneInfo.strRelativeCompareResultAlleleList
          if (this.tabFlag) {
            this.listDetailData.push({
              childGeneAllele: '累计亲权指数（CPI）',
              piVal: this.piVal
            })
          } else {
            this.listDetailData.push({
              childGeneAllele: '累计亲权指数（CPI）',
              piVal: this.piVal,
              fatherPiVal: this.fatherPiVal,
              motherPiVal: this.motherPiVal
            })
          }
          this.lastTr = this.listDetailData.length - 1
        })
    },
    handleLast(row, index) {
      // console.log(row)
      if (row.diffAllele && this.lastTr !== index) {
        return 'red-font'
      }
      if (this.lastTr === index) {
        return 'blue-font'
      }
    },
    // 返回上一步
    goBack() {
      this.$router.push({
        path: '/thanin/kinship'
      })
    },
    // 解除关联操作按钮
    handleLiftConfirm(type) {
      const arr = []
      arr.push(this.id)
      // console.log(arr)
      const info = {
        ids: arr,
        reviewFlag: type
      }
      this.$axios
        .post('/database/match/relative/updateResultCode', info)
        .then(res => {
          if (res.code === 1) {
            this.$Modal.success({
              title: '恭喜',
              content: '操作成功~'
            })
          } else {
            this.$Modal.error({
              title: '抱歉',
              content: '操作失败,请重试~'
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
// @import '../../assets/styles/base.css';
</style>
