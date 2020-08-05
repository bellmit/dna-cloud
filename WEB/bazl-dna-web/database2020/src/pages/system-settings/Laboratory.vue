<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/laboratory">实验室管理</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Form
            :model="baseInfo"
            label-position="right"
            :label-width="120"
            class="bazl-form"
          >
            <Row class="item-row">
              <Col class="item-col" span="7">
                <FormItem label="所属单位">
                  <!-- <Input v-model="baseInfo.orgId" placeholder="请输入"></Input> -->
                  <!-- <Select placeholder="全部单位" v-model="baseInfo.orgId">
                    <Option
                      v-for="item in selectByOrgInfo"
                      :key="item.id"
                      :value="item.id"
                      >{{ item.orgName }}</Option
                    >
                  </Select> -->
                  <span class="item-group">
                    <Cascader
                      placeholder="请选择所属单位"
                      change-on-select
                      :data="selectByOrgInfo"
                      v-model="clientOrgList"
                    ></Cascader>
                  </span>
                </FormItem>
              </Col>
              <Col class="item-col" span="7">
                <FormItem label="服务器编号">
                  <Input
                    v-model.trim="baseInfo.serverNumber"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="7">
                <FormItem label="DNA实验室名称">
                  <Input
                    v-model.trim="baseInfo.labName"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <!-- style="padding: 4px 0 0 60px;" -->
              <Col class="item-col" span="2" style="padding-top:1px">
                <button
                  type="button"
                  class="btn btn-blue-bg"
                  @click="handSearch"
                >
                  查询
                </button>
              </Col>
            </Row>
          </Form>
        </div>
        <div class="form-part">
          <Row class="title">
            <Col span="12">
              实验室列表
              <!-- <button
                class="btn btn-blue-bg mg-left-15"
                @click="handEdit(null)"
              >
                添加
              </button> -->
            </Col>
            <Col span="12" class="tr">
              <button class="btn btn-blue-bg" @click="handEdit(null)">
                添加
              </button>
            </Col>
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table bazl-table-title"
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
        <Row>
          <!-- <CopyRight></CopyRight> -->
        </Row>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      clientOrgList: [],
      baseInfo: {
        clientOrgList: []
      },
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 40
        },
        {
          title: '服务器编号',
          key: 'serverNumber',
          align: 'center'
        },
        {
          title: '所属单位名称',
          key: 'orgName',
          align: 'center'
        },
        {
          title: '鉴定中心全称',
          key: 'identifyName',
          align: 'center'
        },
        {
          title: '鉴定中心地址',
          key: 'identifyAddress',
          align: 'center'
        },
        {
          title: '鉴定中心电话',
          key: 'identifyPhone',
          align: 'center'
        },
        {
          title: 'DNA实验室名称',
          key: 'labName',
          align: 'center'
        },
        {
          title: 'DNA实验室级别',
          key: 'labLevel',
          align: 'center'
        },
        {
          title: 'DNA实验室负责人',
          key: 'labUser',
          align: 'center'
        },
        {
          title: '实验室人数',
          key: 'countLabUser',
          align: 'center',
          width: 80
        },
        {
          title: '服务器IP地址',
          key: 'serverIp',
          align: 'center'
        },
        {
          title: '授权访问IP范围',
          key: 'serverIpAddr',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          // width: 40,
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    marginRight: '10px',
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.handEdit(params.row.id)
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
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.handdelete(params.row.id)
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
      selectByOrgInfo: [], // 委托单位
      orgidList: []
    }
  },
  created() {
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
  },
  mounted() {
    // this.getSelectByOrgInfo()
    this.handInitData()
  },
  methods: {
    handleTab(index) {
      this.active = index
    },
    // 分页监听
    changePage(val) {
      this.currentPage = val
      this.handInitData()
    },
    // 页面初始数据
    handInitData() {
      console.log()
      this.baseInfo.clientOrgList = []
      if (this.clientOrgList.length !== 0) {
        this.baseInfo.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.baseInfo.clientOrgList = []
      }
      this.baseInfo.pageIndex = this.currentPage
      this.$axios.post('/system/lab/list', this.baseInfo).then(res => {
        // console.log(res)
        this.tableData = res.result.list
        this.pageCount = res.result.pageInfo.pageCount
        this.allRecordCount = res.result.pageInfo.allRecordCount
        // this.$Message.success('删除成功！')
      })
    },
    // 查询
    handSearch() {
      this.currentPage = 1
      this.handInitData()
    },
    // 添加/编辑详情
    handEdit(id) {
      this.$router.push({
        path: '/setting/laboratoryinfo',
        query: {
          id: id
        }
      })
    },
    handdelete(id) {
      this.$Modal.confirm({
        title: '提醒',
        content: '确定要删除吗?此操作不可逆',
        okText: '确定',
        cancelText: '我再想想',
        onOk: () => {
          this.$axios
            .get('/system/lab/delete/' + id)
            .then(res => {
              this.handInitData()
              this.$Message.success('删除成功！')
            })
            .catch(error => {
              setTimeout(() => {
                this.$Modal.error({
                  title: '抱歉',
                  content: error.data.message
                })
              }, 200)
              // this.$Message.error(error.data.message)
            })
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.item-col {
  .item-label {
    width: 80px !important;
  }
}
</style>
