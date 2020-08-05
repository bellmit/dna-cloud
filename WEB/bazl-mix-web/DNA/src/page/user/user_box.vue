<template>
  <div>
    <div class="home_title">
      <router-link tag="b" to="/">首页</router-link> 》 <span>用户管理</span>
    </div>
    <div class="case_title" style="color: #000;">
      <Icon type="ios-square case_title_icon" />查询条件
    </div>
    <Form
      :model="formInfo"
      label-position="right"
      :label-width="100"
      class="bg bazl-form"
    >
      <Row>
        <Col span="5">
          <FormItem label="用户名">
            <Input v-model="formInfo.userName"></Input>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="真实姓名">
            <Input v-model="formInfo.realName"></Input>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="单位名称">
            <!--  @on-change="changeConnectName" -->
            <Select
              placeholder="请选择单位名称"
              v-model="formInfo.orgId"
            >
              <Option
                v-for="item in companyList"
                :value="item.id"
                :key="item.id"
                >{{ item.orgName }}</Option
              >
            </Select>
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="电话号码">
            <Input v-model="formInfo.input1"></Input>
          </FormItem>
        </Col>
        <Col span="4">
          <button
            class="btn btn-blue-bg"
            style="margin-left:100px;"
            @click.prevent="getSearch"
          >
            查询
          </button>
        </Col>
      </Row>
      <!-- <Row>
        <Col span="8">
          <button
            class="btn btn-blue-bg"
            style="margin-left:100px;"
            @click.prevent="getSearch"
          >
            查询
          </button>
        </Col>
      </Row> -->
    </Form>
    <div class="bg">
      <Row class="case_title" style="color: #000;">
        <Col span="12">
          <Icon type="ios-square case_title_icon" />用户信息
        </Col>
        <Col span="12" style="text-align:right;padding-right:15px;">
          <button
            class="btn btn-blue-border"
            style="height:32px;line-height:32px;padding:0 15px;"
            @click="addUser"
          >
            添加用户
          </button>
        </Col>
      </Row>
      <div style="padding:0 15px;">
        <div v-show="tableData.length != 0" style="min-height:700px;">
          <Table
            class="light-blue-table"
            :columns="tableCol"
            :data="tableData"
            border
            stripe
          >
          </Table>
          <div class="page" style="padding:8px 0">
            <span>
              当前第{{ currentPage }}页/共{{ pageCount }}页/共{{
                allCount
              }}条信息
            </span>
            <Page
              :total="allCount"
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
        <div class="case_data_null" v-show="tableData.length == 0">
          <div class="borderStyle">
            <div>
              <p><img src="../../assets/img/break_err.png" alt="" /></p>
              <p>您好，目前暂无数据显示</p>
              <p>您可尝试其他操作</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Modal
      v-model="userModal"
      width="680"
      class="light-blue-modal user-modal"
      :closable="false"
    >
      <div style="text-align:center">
        <div class="user-modal-title">
          {{ titleFlag }}
        </div>
        <div class="user-modal-content">
          <div class="sub-title-blue">
            <Icon
              type="md-arrow-forward"
              style="font-size:14px;color:#105AFB"
            />用户信息
          </div>
          <Form
            :model="userForm"
            label-position="right"
            :label-width="100"
            class="bg bazl-form"
            ref="userForm"
          >
            <Row>
              <Col span="12">
                <FormItem label="用户名称" prop="userName">
                  <Input
                    v-model="userForm.userName"
                    @on-blur="checkUser"
                  ></Input>
                  <Input
                    v-model="userForm.userName"
                    type="text"
                    v-show="false"
                  ></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="电话号码" prop="phone">
                  <Input v-model="userForm.phone"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="12">
                <FormItem label="真实姓名" prop="realName">
                  <Input v-model="userForm.realName"></Input>
                  <Input
                    v-model="userForm.realName"
                    type="text"
                    v-show="false"
                  ></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="单位名称" prop="connectName">
                  <Select
                    placeholder="请选择单位"
                    v-model="userForm.connectName"
                    @on-change="changeConnectName"
                  >
                    <Option
                      v-for="item in companyList"
                      :value="item.orgCode"
                      :key="item.orgCode"
                      >{{ item.orgName }}</Option
                    >
                  </Select>
                </FormItem>
              </Col>
            </Row>
            <Row v-show="titleFlag != '编辑'">
              <Col span="12">
                <FormItem label="密码" prop="password">
                  <Input
                    v-model="userForm.password"
                    type="text"
                    @on-focus="changeType"
                  ></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="确认密码" prop="isPassword">
                  <Input
                    v-model="userForm.isPassword"
                    @on-blur="checkPassword"
                    type="text"
                    @on-focus="changeType"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <hr class="gray-line" />
          <div class="sub-title-yellow">
            <Icon
              type="md-arrow-forward"
              style="font-size:14px;color:#F7AF2E"
            />服务器信息
          </div>
          <Form
            :model="dataForm"
            label-position="right"
            :label-width="100"
            class="bg bazl-form"
            ref="dataForm"
          >
            <Row>
              <Col span="12">
                <FormItem label="数据服务器IP" prop="ip">
                  <Input v-model="dataForm.ip"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="数据库端口" prop="port">
                  <Input v-model="dataForm.port"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="12">
                <FormItem label="数据库实例" prop="dbName">
                  <Input v-model="dataForm.dbName"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="数据库用户名" prop="userName">
                  <Input v-model="dataForm.userName"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="12">
                <FormItem label="数据库密码" prop="password">
                  <Input v-model="dataForm.password"></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <hr class="gray-line" />
          <div style="padding:15px 0">
            <button
              class="btn btn-blue-bg"
              style="margin-right:10px"
              @click="testConnection"
            >
              测试
            </button>
            <button class="btn btn-blue-bg" @click="saveUser">保存</button>
          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>
<script>
import userServers from "../../servers/userServers";
import md5 from "js-md5";
export default {
  name: "userBox",
  data() {
    return {
      titleFlag: "注册",
      editPassword: "",
      formInfo: {},
      tableCol: [
        {
          title: "序号",
          type: "index",
          width: "60"
        },
        {
          title: "机构代码",
          render: (h, params) => {
            let text = "";
            if (params.row.sysOrganization) {
              text = params.row.sysOrganization.orgCode;
            } else {
              text = "";
            }
            return h("span", {}, text);
          }
        },
        {
          title: "单位名称",
          render: (h, params) => {
            let text = "";
            if (params.row.sysOrganization) {
              text = params.row.sysOrganization.orgName;
            } else {
              text = "";
            }
            return h("span", {}, text);
          }
        },
        {
          title: "用户名",
          key: "userName"
        },
        // {
        //   title: "密码",
        //   key: "password"
        // },
        {
          title: "数据库用户名",
          // key: "",
          render: (h, params) => {
            let text = "";
            if (params.row.sysOrganization) {
              if (params.row.sysOrganization.dataSourceConfig) {
                text = params.row.sysOrganization.dataSourceConfig.userName;
              } else {
                text = "";
              }
            } else {
              text = "";
            }
            return h("span", {}, text);
          }
        },
        {
          title: "数据库密码",
          // key: "",
          render: (h, params) => {
            let text = "";
            if (params.row.sysOrganization) {
              if (params.row.sysOrganization.dataSourceConfig) {
                text = params.row.sysOrganization.dataSourceConfig.password;
              } else {
                text = "";
              }
            } else {
              text = "";
            }
            return h("span", {}, text);
          }
        },
        {
          title: "行政级别",
          key: "",
          render: (h, params) => {
            let text = "";
            if (params.row.sysOrganization) {
              text = params.row.sysOrganization.orgCreditCode;
            } else {
              text = "";
            }
            return h("span", {}, text);
          }
        },
        {
          title: "角色",
          key: "roleNames"
        },
        {
          title: "审核",
          width: "160",
          render: (h, params) => {
            if (params.row.status == "1") {
              return h("div", [
                h(
                  "div",
                  {
                    style: {
                      background: "#DAEDFE",
                      borderRadius: "4px",
                      padding: "4px 10px",
                      display: "inline-block"
                    }
                  },
                  [
                    h("Icon", {
                      props: {
                        type: "ios-checkmark-circle"
                      },
                      style: {
                        fontSize: "18px",
                        color: "#157EF9"
                      }
                    }),
                    h(
                      "span",
                      {
                        style: {
                          color: "#157EF9",
                          verticalAlign: "middle",
                          marginLeft: "4px"
                        }
                      },
                      "已通过"
                    )
                  ]
                ),
                h(
                  "button",
                  {
                    class: "btn btn-red-bg",
                    style: {
                      height: "24px",
                      lineHeight: "24px",
                      padding: "0",
                      minWidth: "40px",
                      marginLeft: "4PX",
                      display: "inline-block"
                    },
                    on: {
                      click: event => {
                        let par = {
                          userId: params.row.id,
                          status: "0"
                        };
                        let info = JSON.stringify(par);
                        userServers.editStatus(info).then(res => {
                          if (res.code == 1) {
                            this.$Modal.success({
                              title: "恭喜",
                              content: "操作成功~"
                            });
                            this.getUserList();
                          } else {
                            this.$Modal.error({
                              title: "抱歉",
                              content: "操作失败,请重试~"
                            });
                          }
                        });
                      }
                    }
                  },
                  "拒绝"
                )
              ]);
            } else if (params.row.status == "0") {
              return h("div", [
                h(
                  "div",
                  {
                    style: {
                      background: "#FED1CF",
                      borderRadius: "4px",
                      padding: "4px 10px",
                      display: "inline-block"
                    }
                  },
                  [
                    h("Icon", {
                      props: {
                        type: "ios-close-circle"
                      },
                      style: {
                        fontSize: "18px",
                        color: "#FC5343"
                      }
                    }),
                    h(
                      "span",
                      {
                        style: {
                          color: "#FC5343",
                          verticalAlign: "middle",
                          marginLeft: "4px"
                        }
                      },
                      "已拒绝"
                    )
                  ]
                ),
                h(
                  "button",
                  {
                    class: "btn btn-blue-bg",
                    style: {
                      height: "24px",
                      lineHeight: "24px",
                      padding: "0",
                      minWidth: "40px",
                      marginLeft: "4px",
                      display: "inline-block"
                    },
                    on: {
                      click: event => {
                        let par = {
                          userId: params.row.id,
                          status: "1"
                        };
                        let info = JSON.stringify(par);
                        userServers.editStatus(info).then(res => {
                          if (res.code == 1) {
                            this.$Modal.success({
                              title: "恭喜",
                              content: "操作成功~"
                            });
                            this.getUserList();
                          } else {
                            this.$Modal.error({
                              title: "抱歉",
                              content: "操作失败,请重试~"
                            });
                          }
                        });
                      }
                    }
                  },
                  "通过"
                )
              ]);
            }
          }
        },
        {
          title: "操作",
          width: "220",
          render: (h, params) => {
            return h("div", [
              h("span", [
                h("Icon", {
                  props: {
                    type: "ios-create"
                  },
                  style: {
                    color: "#2283F1",
                    fontSize: "18px"
                  }
                }),
                h(
                  "span",
                  {
                    style: {
                      cursor: "pointer",
                      marginRight: "8px",
                      color: "#333333"
                    },
                    on: {
                      click: event => {
                        console.log(params.row);
                        this.titleFlag = "编辑";
                        this.userModal = true;
                        // this.dataForm = params.row.sysOrganization ? params.row.sysOrganization.dataSourceConfig : {}
                        if (params.row.sysOrganization) {
                          if (params.row.sysOrganization.dataSourceConfig) {
                            this.dataForm =
                              params.row.sysOrganization.dataSourceConfig;
                          } else {
                            this.dataForm = {};
                          }
                        } else {
                          this.dataForm = {};
                        }
                        this.userForm.userName = params.row.userName;
                        this.userForm.phone = params.row.phone;
                        this.userForm.realName = params.row.realName;
                        this.userForm.connectName = params.row.sysOrganization
                          ? params.row.sysOrganization.orgCode
                          : "";
                        this.userForm.id = params.row.id;
                        this.userForm.status = params.row.status;
                      }
                    }
                  },
                  "编辑"
                )
              ]),
              h("span", [
                h("Icon", {
                  props: {
                    type: "ios-trash"
                  },
                  style: {
                    color: "#F85956",
                    fontSize: "18px"
                  }
                }),
                h(
                  "span",
                  {
                    style: {
                      cursor: "pointer",
                      color: "#333"
                    },
                    on: {
                      click: e => {
                        this.$Modal.confirm({
                          title: "提醒",
                          content: "确定要删除吗?此操作不可逆",
                          okText: "确定",
                          cancelText: "我再想想",
                          onOk: () => {
                            let info = params.row.id;
                            userServers.delUser(info).then(res => {
                              if (res.code == 1) {
                                this.$Modal.success({
                                  title: "恭喜",
                                  content: "删除成功~"
                                });
                                this.getUserList();
                              } else {
                                this.$Modal.error({
                                  title: "抱歉",
                                  content: "删除失败,请重试~"
                                });
                              }
                            });
                          }
                        });
                      }
                    }
                  },
                  "删除"
                )
              ]),
              h(
                "span",
                {
                  class: "",
                  style: {
                    padding: "4px 10px",
                    background: "#007EF9",
                    borderRadius: "4px",
                    cursor: "pointer",
                    color: "#fff",
                    marginLeft: "4px"
                  },
                  on: {
                    click: () => {
                      this.$Modal.confirm({
                        render: h => {
                          return h("Input", {
                            props: {
                              value: this.editPassword,
                              autofocus: true,
                              placeholder: "请输入新密码"
                            },
                            on: {
                              input: val => {
                                this.editPassword = val;
                              }
                            }
                          });
                        },
                        onOk: () => {
                          let password = md5(this.editPassword);
                          let item = {
                            userId: params.row.id,
                            p: password
                          };
                          let info = JSON.stringify(item);
                          userServers.editPassword(info).then(res => {
                            if (res.code == 1) {
                              // this.$Modal.success({
                              //   title: '恭喜',
                              //   content: '操作成功~'
                              // })
                              this.$Message.success({
                                background: true,
                                content: "操作成功~"
                              });
                            } else {
                              // this.$Modal.error({
                              //   title: '抱歉',
                              //   content: '操作失败~'
                              // })
                              this.$Message.error({
                                background: true,
                                content: "操作失败~"
                              });
                            }
                          });
                        }
                      });
                    }
                  }
                },
                "修改密码"
              )
            ]);
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allCount: 0,
      userModal: false,
      userForm: {},
      dataForm: {},
      companyList: []
    };
  },
  mounted() {
    this.getCompanyList();
    this.getUserList();
  },
  methods: {
    changeType(e) {
      e.srcElement.type = "password";
    },
    changePage(p) {
      this.currentPage = p;
      this.getUserList();
    },
    getCompanyList() {
      let info = {};
      let item = JSON.stringify(info);
      userServers.companyList(item).then(res => {
        if (res.code == 1) {
          console.log(res.result);
          this.companyList = res.result.list;
        }
      });
    },
    getSearch() {
      this.currentPage = 1;
      this.getUserList();
    },
    getUserList() {
      let info = this.formInfo;
      info.pageIndex = this.currentPage;
      let item = JSON.stringify(info);
      userServers.getUserList(item).then(res => {
        if (res.code == 1) {
          console.log(res);
          this.tableData = res.result.list;
          this.pageCount = res.result.pageInfo.pageCount;
          this.allCount = res.result.pageInfo.allRecordCount;
        }
      });
    },
    addUser() {
      this.userModal = true;
      this.titleFlag = "注册";
      this.userForm.id = "";
      this.$refs["userForm"].resetFields();
      this.$refs["dataForm"].resetFields();
    },
    checkUser() {
      if (!this.userForm.userName) {
        console.log("kongde");
        return false;
      }
      let info = {
        userName: this.userForm.userName
      };
      let item = JSON.stringify(info);
      userServers.checkUserName(item).then(res => {
        if (res.code == 1) {
          if (res.result == 1) {
            this.$Modal.error({
              title: "抱歉",
              content: "该用户名已经注册~"
            });
          } else if (res.result == 0) {
            console.log("可以注册~");
          }
        }
      });
    },
    changeConnectName() {
      if (this.userForm.connectName) {
        let user = {
          connectName: this.userForm.connectName
        };
        // let info = JSON.stringify(user)
        userServers.getConnectName(user).then(res => {
          if (res.code == 1) {
           
            console.log(res.result);
            if(res.result){
               this.dataForm = res.result;
            }else{
              this.$refs['dataForm'].resetFields()
            }
          }
        });
      }
    },
    checkPassword() {
      if (this.userForm.password != this.userForm.isPassword) {
        this.$Modal.error({
          title: "抱歉",
          content: "两次输入密码不一致,请重新输入~"
        });
        this.$set(this.userForm, "isPassword", null);
        this.$forceUpdate();
      }
    },
    saveUser() {
      console.log(this.userForm.userName, typeof this.userForm.userName);
      if (this.titleFlag == "注册") {
        if (this.userForm.password != this.userForm.isPassword) {
          this.$Modal.error({
            title: "抱歉",
            content: "两次输入密码不一致,请重新输入~"
          });
          return false;
        }
        this.userForm.status = "0";
        this.userForm.password = md5(this.userForm.password);
      }
      if (!this.userForm.userName) {
        this.$Modal.error({
          title: "抱歉",
          content: "请输入用户名~"
        });
        return false;
      }
      let info = {
        userInfo: this.userForm,
        dataSource: this.dataForm
      };
      let item = JSON.stringify(info);
      userServers.registerUser(item).then(res => {
        if (res.code == 1) {
          this.userModal = false;
          this.$Modal.success({
            title: "恭喜",
            content: "操作成功~"
          });
          this.getUserList();
        } else {
          this.$Modal.error({
            title: "抱歉",
            content: "操作失败~"
          });
        }
      });
    },
    testConnection() {
      let info = {
        ip: this.dataForm.ip,
        port: this.dataForm.port,
        dbName: this.dataForm.dbName,
        userName: this.dataForm.userName,
        password: this.dataForm.password,
        dsType: "oracle" // 数据库类型，打包时需更改为 oracle ，本地为 mysql
        // dsType:'mysql'
      };
      // let info = { // 测试用的数据
      //     ip:"192.168.1.121",
      //     port:1521,
      //     dbName:"orcl",
      //     dsType:"oracle",
      //     userName:"strMix",
      //     password:"strMix"
      // }
      let item = JSON.stringify(info);
      userServers.isConnection(item).then(res => {
        if (res.code == 1) {
          this.$Modal.success({
            title: "恭喜",
            content: "测试成功~"
          });
        } else {
          this.$Modal.error({
            title: "抱歉",
            content: "测试失败~"
          });
        }
      });
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/case";
.bg {
  background-color: #fff;
}
.user-modal-title {
  background-color: #3a86f9;
  color: #fff;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  font-weight: bold;
}
.sub-title-blue {
  width: 120px;
  margin-left: 30px;
  color: #105afb;
  font-size: 14px;
  font-weight: bold;
  margin: 8px 0 4px 30px;
  border-radius: 4px;
  background: linear-gradient(to right, #fff, #bcd2fd, #fff);
}
.sub-title-yellow {
  width: 120px;
  margin-left: 30px;
  color: #f7af2e;
  font-size: 14px;
  font-weight: bold;
  margin: 8px 0 4px 30px;
  border-radius: 4px;
  background: linear-gradient(to right, #fff, #fcedc6, #fff);
}
</style>
