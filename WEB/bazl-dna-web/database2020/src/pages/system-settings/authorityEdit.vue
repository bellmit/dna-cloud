<template>
  <div>
    <!-- <ShortCut></ShortCut> -->
    <Row>
      <!-- <Col span="4">
        <SideBar></SideBar>
      </Col> -->
      <Col span="24" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/settings/authorityEdit">权限管理</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <Row class="title">
            <Col span="12"
              >角色信息
              <button
                class="btn btn-blue-bg mg-left-15"
                @click="handaddAuth(null)"
              >
                添加角色
              </button></Col
            >
            <!-- <Col span="12" class="tr"> </Col> -->
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
        <Row>
          <CopyRight></CopyRight>
        </Row>
      </Col>
    </Row>
    <Modal
      v-model="addAuthModal"
      width="600"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">{{ title_text }}
        <Icon type="md-close-circle" class="modal-close" @click="addAuthModal = false"/>
      </div>
      <div class="content">
        <!-- <p class="line-title">
          角色信息
        </p> -->
        <Form :model="addAuthForm" label-position="left" :label-width="80">
          <FormItem label="角色名称">
            <Input v-model="addAuthForm.roleName"></Input>
          </FormItem>
          <FormItem label="角色描述">
            <Input v-model="addAuthForm.roleDes"></Input>
          </FormItem>
          <!-- <hr> -->
          <!-- <FormItem label="角色分配">
          <Select v-model="addUserForm.select">
              <Option value="beijing">New York</Option>
              <Option value="shanghai">London</Option>
              <Option value="shenzhen">Sydney</Option>
            </Select>
        </FormItem> -->
        </Form>
      </div>
      <hr class="gray-line" />
      <!-- <div class="content">
        <p class="line-title">
          权限分配
        </p>
        <div class="auth-part">

          </CheckboxGroup>
        </div>
      </div> -->
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="handaddAuth">保存</button>
        <button class="btn btn-blue-border" @click="addAuthModal = false">
          取消
        </button>
      </Row>
    </Modal>
  </div>
</template>
<script>
// import ShortCut from '@/components/ShortCut.vue'
// import SideBar from '../../components/SideBar'
import CopyRight from '../../components/CopyRight'
export default {
  components: { CopyRight },
  data() {
    return {
      addAuthModal: false,
      addAuthForm: {
        status: '1'
      },
      indeterminate: true,
      checkAll: false,
      checkAllGroup: ['1', '3'],
      list: [
        {
          name: '管理员',
          code: '1'
        },
        {
          name: '队长',
          code: '2'
        },
        {
          name: '职员',
          code: '3'
        }
      ],
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '角色名称',
          key: 'roleName',
          align: 'center'
        },
        {
          title: '角色描述',
          key: 'roleDes',
          align: 'center'
          // renderHeader: (h, params) => {
          //   return h(
          //     'span',
          //     {
          //       style: {
          //         margin: '0 15px'
          //       }
          //     },
          //     '角色描述'
          //   )
          // }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 200,
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      console.log(params)
                      this.handaddAuth(params.row.id)
                      // this.title_text = '编辑角色'
                      // this.addAuthForm = params.row
                      // this.addAuthModal = true
                      // this.paramsID = params.row.id
                      // this.handaddAuth(params.row.id)
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
                    margin: '0 10px',
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.handDelete(params.row.id)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      tableData: [{}, {}],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      searchForm: {},
      paramsID: '',
      title_text: ''
    }
  },
  mounted() {
    this.handInitData()
  },
  methods: {
    // 删除角色
    handDelete(id) {
      this.$axios.get('/system/role/delete/' + id).then(res => {
        this.handInitData()
        this.$Message.success('删除成功！')
      })
    },
    // 添加/编辑用户
    handaddAuth(id) {
      // console.log(id)
      this.$router.push({
        path: '/settings/roleManag',
        query: {
          id: id
        }
      })
    },
    // 页面初始数据
    handInitData() {
      this.searchForm.pageIndex = this.currentPage
      this.$axios.post('/system/role/list', this.searchForm).then(res => {
        // console.log(res, '----角色')
        this.allRecordCount = res.result.pageInfo.allRecordCount
        this.pageCount = res.result.pageInfo.pageCount
        this.tableData = res.result.list
      })
    },
    changePage(val) {
      this.currentPage = val
      this.handInitData()
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
.content {
  padding: 15px 60px;
  .line-title::before {
    content: '';
    display: inline-block;
    width: 4px;
    height: 12px;
    background: #00439e;
  }
  .line-title {
    color: #00439e;
  }
}
</style>
