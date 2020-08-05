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
            <span>遗传学基因数据管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/PopulationGene"
              >种群基因频率管理</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col span="6" class="item-col">
              <span class="item-label">方案名称</span>
              <Input
                v-model="searchInfo.populationName"
                type="text"
                placeholder="请输入方案名称"
                class="item-input"
              ></Input>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">统计国家</span>
              <Select
                placeholder="请选择国家"
                style="display:inline-block;"
                v-model="searchInfo.statsCountry"
                class="item-input"
              >
                <Option
                  v-for="item in personNationList"
                  :key="item.id"
                  :value="item.dictName"
                  >{{ item.dictName }}</Option
                >
              </Select>
              <!-- <Input
                type="text"
                placeholder="请输入统计国家"
                class="item-input"
              ></Input> -->
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">统计民族</span>
              <Select
                placeholder="请选择民族"
                style="display:inline-block;"
                class="item-input"
                v-model="searchInfo.statsRace"
              >
                <Option
                  v-for="item in personRaceList"
                  :key="item.id"
                  :value="item.dictName"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
            <Col span="6" class="item-col">
              <button class="btn btn-blue-bg" @click="handSearch">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">
                重置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <Row class="title">
            <Col span="12" class="tl">
              基因频率信息
              <button class="btn btn-blue-bg" @click="addhtml">添加</button>
            </Col>
            <Col span="12" class="tr">
              总体描述表格内容<span class="blue-font">{{ allRecordCount }}</span
              >条
            </Col>
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
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
export default {
  data() {
    return {
      searchInfo: {
        pageIndex: 1,
        populationName: null, // 方案名称
        statsCountry: null, // 统计国家
        statsRace: null // 统计民族
      },
      SidebarParams: {
        open: ['5'],
        active: '5-4'
      },
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '方案编号',
          // className: 'blue-font',
          key: 'populationNo',
          align: 'center'
        },
        {
          title: '方案名称',
          key: 'populationName',
          align: 'center'
        },
        {
          title: '统计国家',
          key: 'statsCountry',
          align: 'center'
        },
        {
          title: '统计民族',
          key: 'statsRace',
          align: 'center'
        },
        {
          title: '统计时间',
          key: 'statsDatetime',
          align: 'center'
        },
        {
          title: '基因座数目',
          key: 'locusCount',
          align: 'center'
        },
        {
          title: 'TDP',
          key: 'cdpVal',
          align: 'center'
        },
        {
          title: 'CPE',
          key: 'cpe',
          align: 'center'
        },
        {
          title: '数据来源',
          key: 'dataSource',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h('span', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.$router.push({
                        path: '/geneticmanage/PopulationGeneDetail',
                        query: {
                          id: params.row.id
                        }
                      })
                    }
                  }
                },
                '编辑'
              ),
              h(
                'span',
                {
                  class: 'red-font',
                  style: {
                    cursor: 'pointer',
                    marginLeft: '10px'
                  },
                  on: {
                    click: () => {
                      this.handDelete(params)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      personNationList: [], // 国家
      personRaceList: [] // 民族
    }
  },
  created() {
    // 民族类型
    if (sessionStorage.getItem('personRaceList')) {
      const arr = sessionStorage.getItem('personRaceList')
      this.personRaceList = JSON.parse(arr)
    }
    // 国家
    if (sessionStorage.getItem('personNationList')) {
      const arr = sessionStorage.getItem('personNationList')
      this.personNationList = JSON.parse(arr)
    }
  },
  mounted() {
    this.handInitData()
  },
  methods: {
    addhtml() {
      this.$router.push({
        path: '/geneticmanage/PopulationGeneDetail'
      })
    },
    // 表格分页监听
    changePage(val) {
      // console.log(val)
      this.searchInfo.pageIndex = val
      this.handInitData()
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .post('database/frequency/listAllAlleleFrequency', this.searchInfo)
        .then(res => {
          // console.log(res)
          if (res.code === 1) {
            this.tableData = res.result.allAlleleFrequencyList
            this.allRecordCount = res.result.pageInfo.allRecordCount
            this.pageCount = res.result.pageInfo.pageCount
          }
        })
    },
    // 查询
    handSearch() {
      // get  params: {}
      this.searchInfo.pageIndex = 1
      this.handInitData()
    },
    // 重置查询条件
    handleReset() {
      Object.keys(this.searchInfo).forEach(key => {
        if (key !== 'pageIndex') {
          this.searchInfo[key] = null
        }
      })
    },
    // 编辑弹窗
    handEditGene(params) {
      this.editGene = true
      this.saveLsit = []
      this.formInfo = {
        id: params.row.id, // id
        locusName: params.row.locusName, // 基因座名称
        nationalLocusName: params.row.nationalLocusName, // 其他名称
        coreLocusFlag: params.row.coreLocusFlag, // 是否为核心基因座
        valueScope: params.row.valueScope, // 基因座取值范围
        locusOrd: params.row.locusOrd, // 基因座顺序
        remarks: null // 备注
      }
      // console.log(this.formInfo)
    },
    // 保存编辑内容
    handSave() {
      const strDnaLocusInfo = this.formInfo
      this.saveLsit.push(strDnaLocusInfo)
      this.$axios
        .post('database/locus/updateStrGeneInfo', this.saveLsit)
        .then(res => {
          // console.log(res)
          if (res.code === 1) {
            this.editGene = false
            this.searchInfo.pageIndex = 1
            this.handInitData()
          }
        })
    },
    // 删除
    handDelete(params) {
      this.$Modal.confirm({
        title: '提醒',
        content: '确定要删除吗?此操作不可逆',
        okText: '确定',
        cancelText: '我再想想',
        onOk: () => {
          this.$axios
            .get('/database/frequency/deletePopulationFrequency', {
              params: { populationId: params.row.id }
            })
            .then(res => {
              this.$Message.success('删除成功！')
              this.handInitData()
            })
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/genemanage.less';
.item-input,
.item-select {
  width: 180px !important;
}
</style>
