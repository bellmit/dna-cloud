<template>
  <div class="content-box">
    <Row>
      <Col span="24" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/user">用户管理</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">姓名</span>
              <Input
                type="text"
                placeholder="请输入姓名"
                class="item-input"
                v-model.trim="searchForm.realName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">登录账号</span>
              <Input
                type="text"
                placeholder="请输入登录名"
                class="item-input"
                v-model.trim="searchForm.userName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <button class="btn btn-blue-bg" @click="handSearch">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">
                重置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <Row class="title">
            <Col span="12">查询结果</Col>
            <Col span="12" class="tr">
              <button class="btn btn-blue-bg" @click="handaddUserModel(0)">
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
    <Modal
      v-model="editPassword"
      width="600"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        修改密码
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="editPassword = false"
        />
      </div>
      <div class="content" style="padding: 15px 60px">
        <Form :model="editPasswordForm" label-position="left" :label-width="80">
          <FormItem label="新密码">
            <Input v-model="editPasswordForm.password" type="password"></Input>
          </FormItem>
          <FormItem label="确认密码">
            <Input
              v-model="editPasswordForm.passwordAlign"
              type="password"
              @on-blur="changeEditPassword"
            ></Input>
          </FormItem>
        </Form>
        <Row class="btn-row">
          <button class="btn btn-blue-bg" @click="submitEditPassword">
            提交
          </button>
        </Row>
      </div>
    </Modal>
    <Modal
      v-model="addUserModal"
      width="600"
      class="bazl-modal userModel"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        添加用户
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="addUserModal = false"
        />
      </div>
      <div class="content" style="padding:15px 60px">
        <Form
          :model="addUserForm"
          label-position="left"
          :label-width="80"
          ref="addUserForm"
          :rules="userRule"
        >
          <FormItem label="用户姓名" class="bef">
            <Input v-model="addUserForm.realName"></Input>
          </FormItem>
          <FormItem label="登录账号" prop="userName">
            <!-- @on-blur.prevent="changeInputBlur" -->
            <Input
              v-model="addUserForm.userName"
              @blur.native.capture="changeInputBlur"
            ></Input>
            <Input
              v-model="addUserForm.userName"
              type="text"
              v-show="false"
            ></Input>
          </FormItem>
          <FormItem label="登录密码" prop="password" v-show="inputShow">
            <Input
              :disabled="Disable"
              type="password"
              v-model="addUserForm.password"
            ></Input>
          </FormItem>
          <FormItem label="确认密码" prop="iSpassword" v-show="inputShow">
            <Input
              :disabled="Disable"
              type="password"
              v-model="addUserForm.iSpassword"
              @on-blur="changePassword"
            ></Input>
          </FormItem>
          <FormItem label="证件类型" class="bef">
            <Select v-model="addUserForm.idType" placeholder="请选择证件类型">
              <Option
                v-for="item in certificateType"
                :key="item.id"
                :value="item.id"
                >{{ item.dictName }}</Option
              >
            </Select>
          </FormItem>
          <FormItem label="证件号码" class="bef">
            <Input v-model="addUserForm.idCard"></Input>
          </FormItem>
          <FormItem label="联系电话" class="bef">
            <Input v-model="addUserForm.phone"></Input>
          </FormItem>
          <FormItem label="电子邮箱" class="bef">
            <Input v-model="addUserForm.email"></Input>
          </FormItem>
          <FormItem label="所属单位" class="bef">
            <span class="item-group">
              <Cascader
                placeholder="请选择所属单位"
                :data="selectByOrgInfo"
                v-model="clientOrgList"
                change-on-select
              ></Cascader>
            </span>
          </FormItem>
          <FormItem label="是否启用" class="bef">
            <Select v-model="addUserForm.status">
              <Option value="1">是</Option>
              <Option value="0">否</Option>
            </Select>
          </FormItem>
          <!-- <hr> -->
          <FormItem label="角色分配" prop="roles">
            <Select
              placeholder="请分配角色 - 支持多选"
              @on-change="changeRole"
              v-model="addUserForm.roles"
              :label-in-value="true"
              multiple
            >
              <Option
                v-for="item in roleList"
                :key="item.id"
                :value="item.id"
                >{{ item.roleName }}</Option
              >
            </Select>
          </FormItem>
          <FormItem label="备注" class="bef">
            <Input v-model="addUserForm.remark"></Input>
          </FormItem>
          <!-- <FormItem label="角色描述" class="bef">
            <Input v-model="addUserForm.roleDes"></Input>
          </FormItem> -->
        </Form>
      </div>
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="handAddUser('addUserForm')">
          保存
        </button>
        <button class="btn btn-blue-border" @click="addUserModal = false">
          取消
        </button>
      </Row>
    </Modal>
  </div>
</template>
<script>
// eslint-disable-next-line no-unused-vars
// import md5 from 'js-md5'
export default {
  data() {
    return {
      geneList: [
        {
          name: 'DS092'
        },
        {
          name: 'DE987'
        },
        {
          name: 'UI8777'
        },
        {
          name: 'HJ9797'
        }
      ],
      fatherList: [],
      fatherList1: [
        {
          name: '是收到'
        },
        {
          name: '胜多负少'
        },
        {
          name: '收到'
        },
        {
          name: '是收到'
        },
        {
          name: '胜多负少'
        },
        {
          name: '收到'
        }
      ],
      sonList: [
        {
          name: '儿子1号'
        },
        {
          name: '儿子2号'
        },
        {
          name: '儿子3号'
        }
      ],
      list1: [],
      editPassword: false,
      editPasswordForm: {},
      addUserModal: false,
      addUserForm: {
        id: '',
        status: '1',
        userName: null,
        clientOrgList: []
      },
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '姓名',
          key: 'realName',
          align: 'center'
        },
        {
          title: '登录账号',
          key: 'userName',
          align: 'center'
        },
        // {
        //   title: '密码',
        //   key: 'password',
        //   align: 'center'
        // },
        {
          title: '单位名称',
          key: 'orgName',
          align: 'center'
        },
        {
          title: '电话',
          key: 'phone',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            // console.log(params.row)
            // eslint-disable-next-line no-unused-vars
            let text = ''
            // eslint-disable-next-line no-unused-vars
            let color = ''
            if (params.row.status === '1') {
              text = '已启用'
              color = '#199C53'
            } else {
              text = '未启用'
              color = '#aaa'
            }
            return h('div', [
              h(
                'span',
                {
                  // class: 'blue-font',
                  style: {
                    // cursor: 'pointer',
                    marginRight: '10px'
                  },
                  on: {
                    click: () => {
                      console.log(params.row)
                      this.editPassword = true
                      this.editPasswordForm.password = ''
                      this.editPasswordForm.passwordAlign = ''
                      this.userId = params.row.id
                    }
                  }
                },
                '修改密码'
              ),
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.handaddUserModel(1, params.row.id)
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
                      this.$Modal.confirm({
                        title: '提醒',
                        content: '确定要删除吗?此操作不可逆',
                        okText: '确定',
                        cancelText: '我再想想',
                        onOk: () => {
                          this.$axios
                            .get('/system/user/delete/' + params.row.id)
                            .then(res => {
                              // console.log(res, '删除')
                              this.$Message.success('删除成功！')
                              this.handInitData()
                            })
                        }
                      })
                    }
                  }
                },
                '删除'
              ),
              h(
                'span',
                {
                  // class: color,

                  style: {
                    cursor: 'pointer',
                    color: color
                  }
                },
                text
              )
            ])
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      searchForm: {},
      roleList: [],
      paramsId: '',
      roles: [],
      inputShow: true, // 登录密码输入框 状态切换
      userRule: {
        userName: [
          { required: true, message: '请填写登录账号', trigger: 'blur' }
        ],
        roles: [
          {
            type: 'array',
            required: true,
            message: '请选择角色类型',
            trigger: 'blur'
            // trigger: 'change'
          }
        ],
        // roleDes: [
        //   { required: true, message: '请填写案发地点', trigger: 'blur' }
        // ],
        password: [
          { required: true, message: '请填写登录密码', trigger: 'blur' }
        ],
        iSpassword: [
          { required: true, message: '请填写登录密码', trigger: 'blur' }
        ]
      },
      userId: '',
      Disable: false,
      certificateType: [],
      selectByOrgInfo: [],
      orgidList: [],
      clientOrgList: [] // 委托单位
    }
  },
  mounted() {
    if (sessionStorage.getItem('certificateType')) {
      // 案件性质
      const arr = sessionStorage.getItem('certificateType')
      this.certificateType = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
    this.GetRoleSelect()
    this.handInitData()
  },
  methods: {
    // 表格分页
    changePage(val) {
      this.currentPage = val
      this.handInitData()
    },
    // 查询
    handSearch() {
      this.currentPage = 1
      this.handInitData()
    },
    // 重置查询条件
    handleReset() {
      Object.keys(this.searchForm).forEach(key => {
        this.searchForm[key] = ''
      })
    },
    // 页面初始数据
    handInitData() {
      this.searchForm.pageIndex = this.currentPage
      this.$axios.post('/system/user/list', this.searchForm).then(res => {
        // console.log(res, '----初始')
        this.tableData = res.result.list
        this.pageCount = res.result.pageInfo.pageCount
        this.allRecordCount = res.result.pageInfo.allRecordCount
      })
    },
    // 添加/编辑 弹窗
    handaddUserModel(type, id) {
      this.$refs.addUserForm.resetFields()
      if (type === 0) {
        this.title_text = '添加角色'
        this.Disable = false
        Object.keys(this.addUserForm).forEach(key => {
          this.addUserForm[key] = ''
        })
        this.clientOrgList = []
        this.addUserForm.status = '1'
      } else {
        this.Disable = true
        this.title_text = '编辑角色'
        this.$axios.get('/system/user/get/' + id).then(res => {
          // console.log(res, '修改')
          this.addUserForm = res.result.userInfo
          this.addUserForm.idType = parseInt(res.result.userInfo.idType)
          this.addUserForm.id = res.result.userInfo.id
          this.addUserForm.iSpassword = res.result.userInfo.password
          this.clientOrgList = res.result.userInfo.clientOrgList
          console.log()
        })
      }
      this.addUserModal = true
    },
    // 添加/编辑用户信息 弹窗 保存按钮
    handAddUser(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          if (!this.Disable) {
            this.addUserForm.password = this.$md5(this.addUserForm.password)
          }
          this.addUserForm.clientOrgList = []
          if (this.clientOrgList.length !== 0) {
            this.addUserForm.clientOrgList.push(
              this.clientOrgList[this.clientOrgList.length - 1]
            )
          } else {
            this.addUserForm.clientOrgList = []
          }
          const info = {
            userInfo: this.addUserForm,
            roles: this.addUserForm.roles
          }
          this.$axios.post('/system/user/save', info).then(res => {
            this.$Message.success('保存成功！')
            this.handInitData()
            this.addUserModal = false
          })
        } else {
          this.$Modal.warning({
            title: '提示',
            content: '请完善用户信息！'
          })
        }
      })
    },
    // 获取角色下拉框
    GetRoleSelect() {
      this.$axios.post('/system/role/list', {}).then(res => {
        // console.log(res, '----角色下拉框')
        this.roleList = res.result.list
      })
    },
    // 角色下拉框 监听事件
    changeRole(val) {
      console.log(val)
      // this.addUserForm.roleDes = val.label
    },
    // 登录账号 输入框 失焦事件 判断重复账号
    changeInputBlur(val) {
      if (this.addUserForm.userName) {
        const info = {
          id: this.addUserForm.id,
          userName: this.addUserForm.userName
        }
        this.$axios
          .post('/system/user/checkUserName', info, { noLoading: true })
          .then(res => {
            if (res.result !== 0) {
              this.$Modal.error({
                title: '抱歉',
                content: '当前登录账号已存在，请重新输入！'
              })
            }
          })
      }
    },
    // 确认密码 输入框 失焦事件
    changePassword(val) {
      console.log('视角事件')
      if (this.addUserForm.password !== this.addUserForm.iSpassword) {
        this.$Modal.error({
          title: '抱歉',
          content: '两次密码输入不一致，请重新输入！'
        })
      }
    },
    changeEditPassword() {
      if (
        this.editPasswordForm.password !== this.editPasswordForm.passwordAlign
      ) {
        this.$Modal.error({
          title: '抱歉',
          content: '两次密码输入不一致，请重新输入！'
        })
        this.editPasswordForm.passwordAlign = ''
      }
    },
    submitEditPassword() {
      if (this.editPasswordForm.passwordAlign) {
        // eslint-disable-next-line prefer-const
        let info = {
          userId: this.userId,
          p: this.editPasswordForm.passwordAlign
        }
        console.log(info)
        this.$axios.post('/system/user/editPassword', info).then(res => {
          console.log(res)
          this.$Message.success('修改成功！')
          this.editPassword = false
        })
      } else {
        this.$Modal.info({
          title: '抱歉',
          content: '请确定密码是否正确~'
        })
      }
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
.class-table {
  .class-table-col > span {
    color: #fff;
    background: #00439e;
    display: inline-block;
    width: 100%;
    height: 36px;
    line-height: 36px;
    font-weight: bold;
    font-size: 12px;
  }
  .class-table-col {
    p {
      height: 40px;
      line-height: 40px;
      border: 1px solid #ddd;
      margin-top: -1px;
      margin-right: -1px;
    }
    .class-btn {
      width: 60px;
      height: 32px;
      line-height: 32px;
      padding: 0;
      margin-left: 10px;
    }
  }
}
</style>
