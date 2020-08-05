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
            <router-link to="/thanin/homotype">同型比中</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/thanin/homotypeDetail">查看详情</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">源案件名称</span>
              <Input
                type="text"
                placeholder="请输入源案件名称"
                class="item-input"
                v-model.trim="searchInfo.caseName"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">检材名称</span>
              <Input
                type="text"
                placeholder="请输入检材名称"
                class="item-input"
                v-model.trim="searchInfo.sampleName"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">检验人</span>
              <Select
                placeholder="请选择检验人"
                style="display:inline-block;width:260px;"
                v-model.trim="searchInfo.personName"
              >
                <!-- <Option
                  v-for="item in sampleTypeQuery"
                  :value="item.dictCode"
                  :key="item.dictCode"
                  >{{ item.dictName }}</Option
                > -->
              </Select>
            </Col>
          </Row>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click="handSarch">查询</button>
            <button class="btn btn-blue-border" @click.prevent="handleReset">
              重置
            </button>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">
            查看详情
          </div>
          <Row class="sub">
            <Col span="12" class="left-sub"> 第{{ group }}组串并案列表 </Col>
            <Col span="12" class="right-sub">
              比中案件<span class="blue-font">{{ caseCount }}</span
              >条， 比中人员<span class="blue-font">{{ personCount }}</span
              >个， 比中物证<span class="blue-font">{{ sampleCount }}</span
              >个
            </Col>
          </Row>
          <Table
            border
            :columns="searchResultCol"
            :data="searchResultData"
            class="bazl-table"
          ></Table>
          <div class="page">
            <span>
              当前第{{ searchInfo.pageIndex }}页/共{{ pageCount }}页/共{{
                allRecordCount
              }}条信息
            </span>
            <Page
              :total="allRecordCount"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="searchInfo.pageIndex"
              @on-change="changePage"
              :page-size="15"
            />
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
// import Common from '../../components/Common'
export default {
  data() {
    return {
      SidebarParams: {
        open: ['3'],
        active: '3-1'
      },
      searchForm: {},
      vis: '占位',
      searchResultCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '源案件名称',
          key: 'caseName'
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center'
        },
        {
          title: '比中案件',
          key: 'compareCaseName',
          align: 'center'
        },
        {
          title: '比中样本',
          key: 'compareSampleName',
          align: 'center'
        },
        {
          title: '样本类型',
          key: 'sampleType'
        },
        {
          title: '检验人',
          key: 'personName',
          align: 'center'
        },
        {
          title: '状态',
          key: 'reviewFlag',
          align: 'center'
        },
        {
          title: '提交时间',
          key: 'createDatetime',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          width: 220,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  style: { color: '#F4A711', cursor: 'pointer' },
                  on: {
                    click: () => {
                      this.handleLiftConfirm(1, params.row.id)
                    }
                  }
                },
                '确认比中'
              ),
              h(
                'span',
                {
                  style: {
                    color: '#DF3E28',
                    cursor: 'pointer',
                    margin: '0 6px'
                  },
                  on: {
                    click: () => {
                      this.handleLiftConfirm(0, params.row.id)
                    }
                  }
                },
                '解除关联'
              ),
              h(
                'span',
                {
                  style: { color: '#00439E', cursor: 'pointer' },
                  on: {
                    click: () => {
                      // console.log(params.row)
                      this.$router.push({
                        path: '/thanin/homotypeSourceDetail',
                        query: {
                          id: params.row.id,
                          groupId: params.row.groupId
                        }
                      })
                    }
                  }
                },
                '查看详情'
              )
            ])
          }
        }
      ],
      searchResultData: [{}, {}, {}],
      groupId: '',
      group: '',
      currentPage: 1,
      allRecordCount: 0,
      pageCount: 0,
      caseCount: '0',
      personCount: '0',
      sampleCount: '0',
      searchInfo: {
        id: null,
        pageIndex: 1
      }
    }
  },
  created() {
    this.searchInfo.id = this.$route.query.id
    // console.log(this.searchInfo.id, 'id123131')
  },
  mounted() {
    this.handInitData()
  },
  methods: {
    handleReset() {
      Object.keys(this.searchForm).forEach(key => {
        this.searchForm[key] = ''
      })
    },
    handSarch() {
      this.searchInfo.id = null
      this.searchInfo.pageIndex = 1
      this.handInitData()
    },
    // 页面初始数据
    handInitData() {
      this.$axios.post('database/match/same/detailQuery', this.searchInfo).then(res => {
        // console.log(res)
        this.searchResultData = res.result.resultList
        this.allRecordCount = res.result.pageInfo.allRecordCount
        this.pageCount = res.result.pageInfo.pageCount
      })
    },
    changePage(index) {
      // this.currentPage = index
      this.searchInfo.pageIndex = index
    },
    handleLiftConfirm(type, id) {
      this.$axios
        .get('/database/match/same/updateReviewFlag', {
          params: {
            id: id,
            reviewFlag: type
          }
        })
        .then(res => {
          if (res.code === 1) {
            this.$Modal.success({
              title: '恭喜',
              content: '操作成功！'
            })
          } else {
            this.$Modal.error({
              title: '抱歉',
              content: '操作失败,请重试！'
            })
          }
        })
    }
  }
  // beforeRouteEnter(to, from, next) {
  //   if (from.path === '/seesource') {
  //     to.meta.isBack = true
  //   } else {
  //     to.meta.isBack = false
  //   }
  //   next()
  // },
  // beforeRouteLeave(to, from, next) {
  //   if (to.path === '/homotype') {
  //     to.meta.keepAlive = true
  //   }
  //   next()
  // },
  // activated() {
  //   if (!this.$route.meta.isBack) {
  //     this.groupId = this.$route.params.groupId
  //     this.group = Common.SectionToChinese.bind(this)(this.$route.params.group)
  //     this.getStrDatailQuery()
  //   } else {
  //     this.$route.meta.isBack = false // 重置详情页标识isBack
  //   }
  // }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/seegroup';
</style>
