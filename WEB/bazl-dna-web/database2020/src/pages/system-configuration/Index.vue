<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/configuration">数据转换配置</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            检索条件
          </div>
          <Row class="item-row">
            <Col span="7" class="item-col">
              <span class="item-label">单位名称</span>
              <span class="item-group">
                <Cascader
                  style="width:70%  "
                  placeholder="请选择所属单位"
                  :data="selectByOrgInfo"
                  v-model="orgidList"
                ></Cascader>
              </span>
              <!-- <Select
                style="width:220px"
                placeholder="全部单位"
                v-model="searchInfo.orgName"
              >
                <Option
                  v-for="item in selectByOrgInfo"
                  :key="item.orgCode"
                  :value="item.orgCode"
                  >{{ item.orgName }}</Option
                >
              </Select> -->
            </Col>
            <Col span="7" class="item-col">
              <span class="item-label">单位编号</span>
              <Input
                type="text"
                placeholder="请输入编码"
                class="item-input"
                v-model="searchInfo.orgCode"
              ></Input>
            </Col>
            <Col span="7" class="item-col">
              <button class="btn btn-blue-bg" @click.prevent="getList">
                查询
              </button>
              <button class="btn btn-blue-border" @click.prevent="rest">
                重置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <Row class="title">
            <Col span="12"
              >查询结果
              <button
                class="btn btn-blue-bg mg-left-15"
                @click.prevent="handAdd_EditConfig(null)"
              >
                添加
              </button>
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
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchInfo: {},
      orgName: '法医',
      orgCode: '16000000',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '单位名称',
          key: 'orgName',
          align: 'center'
        },
        {
          title: '单位编号',
          key: 'orgCode',
          align: 'center'
        },
        {
          title: '联系电话',
          key: 'businessPhone',
          align: 'center'
        },
        {
          title: '负责人',
          key: 'businessName',
          align: 'center'
        },
        {
          title: '服务IP',
          // key: 'dataSourceConfig.ip',
          align: 'center',
          render: (h, params) => {
            return h('span', {}, params.row.dataSourceConfig.ip)
          }
        },
        {
          title: '用户名',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('span', {}, params.row.dataSourceConfig.userName)
          }
        },
        {
          title: '密码',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('span', {}, params.row.dataSourceConfig.password)
          }
        },
        {
          title: '数据状态',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('span', {}, params.row.status === '1' ? '有效' : '无效')
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer',
                    margin: '0 8px'
                  },
                  on: {
                    click: event => {
                      this.handAdd_EditConfig(params.row.id)
                    }
                  }
                },
                '查看'
              ),
              h(
                'span',
                {
                  class: 'green-font',
                  style: {
                    cursor: 'pointer'
                  }
                },
                '更新'
              )
            ])
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 0,
      selectByOrgInfo: [] // 委托单位
    }
  },
  created() {
    if (sessionStorage.getItem('selectByOrgInfo')) {
      //
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    //  添加/编辑配置
    handAdd_EditConfig(id) {
      this.$router.push({
        path: '/setting/method',
        query: {
          id: id
        }
      })
    },
    handSerch() {
      this.currentPage = 1
      this.getList()
    },
    rest() {
      this.orgName = ''
      this.orgCode = ''
    },
    changePage(p) {
      this.currentPage = p
      this.getList()
    },
    getList() {
      this.searchInfo.pageIndex = this.currentPage
      this.$axios.post('/system/org/list', this.searchInfo).then(res => {
        this.tableData = res.result.list
        this.pageCount = res.result.pageInfo.pageCount
        this.currentPage = res.result.pageInfo.curPage
        this.allRecordCount = res.result.pageInfo.allRecordCount
      })
    }
  }
}
</script>
<style lang="less" scoped>
.item-group{
  display: inline-block;
}
.item-col {
  .item-label {
    width: 80px !important;
  }
}
</style>
