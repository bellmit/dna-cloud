<template>
  <div id="Hbody" ref="hbody">
    <div id="loginbody">
      <div class="BIAN">
        <div id="topleft"></div>
        <div id="topB"></div>
        <div id="topright"></div>
      </div>
      <div id="lgheader">
        <img src="../../assets/img/logo.png" />
        <!-- <h2>DNA混合样本数据库比对分析系统</h2> -->
        <h2>DNA混合分型自动拆分比对数据库系统</h2>
      </div>
      <div class="loginbtn" id="lgbtn" v-show="isShow">
        
        <Button
          class="upbtn"
          id="MY"
          type="primary"
          style="margin-top:20px;margin-bottom:20px;"
        >密钥登录</Button>
        <Button
          class="downbtn"
          id="CODE"
          type="warning"
          ghost
          @click="handelIsShow"
          style="margin-bottom:20px;"
        >账号登录</Button>
        <Button
          class="upbtn"
          type="primary"
          style="margin-top:20px;margin-bottom:20px;"
          @click="addUser"
        >用户注册</Button>
      </div>
      <div class="loginbtn" id="lginp" v-show="!isShow">
        <input id="worybtn" type="button" v-show="username_show" @click="handelClearUser" />
        <input id="worybtn1" type="button" v-show="!username_show" />
        <Input
          type="text"
          class="loginput login_username"
          v-model="username"
          size="large"
          placeholder="请输入您的账号"
          @on-focus="handelUserFocus"
        />
        <Input
          type="password"
          id="lgpwd"
          class="loginput"
          v-model="password"
          size="large"
          placeholder="请输入您的密码"
        />
        <div id="fogdiv">
          <i-Switch v-model="switch1" size="small" @on-change="changeAutoLogin" />
          <p>自动登录</p>
          <a>忘记密码？</a>
        </div>
        <Button class="upbtn" id="DL" type="primary" @click="handelLogin">登录</Button>
        <Button class="downbtn" id="Back" type="warning" ghost @click="handelIsShow">返回上一步</Button>
      </div>
      <div id="logtxt" :class="{activeText:activeText}">
        <p>北京博安智联科技有限公司</p>
        <p>服务热线：400-011-5530</p>
        <img src="../../assets/img/liu_LOGO.png" />
        <p style="color:#FFB91C">下载谷歌浏览器：Windows XP版 Win7/8/10版</p>
      </div>
      <div class="BIANbtm">
        <div id="btmleft"></div>
        <div id="btmB"></div>
        <div id="btmright"></div>
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
          注册
        </div>
        <div class="user-modal-content">
          <div class="sub-title-blue"><Icon type="md-arrow-forward" style="font-size:14px;color:#105AFB"/>用户信息</div>
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
                  <Input v-model="userForm.userName" @on-blur="checkUser"></Input>
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
                <FormItem label="密码" prop="password">
                  <Input v-model="userForm.password" type="password"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="真实姓名" prop="realName">
                  <Input v-model="userForm.realName"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="12">
                <FormItem label="确认密码" prop="isPassword">
                  <Input v-model="userForm.isPassword" @on-blur="checkPassword" type="password"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="单位名称" prop="connectName">
                  <Select placeholder="请选择单位" v-model="userForm.connectName" @on-change="changeConnectName">
                      <Option v-for="item in companyList" :value="item.orgCode" :key="item.orgCode">{{ item.orgName }}</Option>
                  </Select>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <hr class="gray-line" />
          <div class="sub-title-yellow"><Icon type="md-arrow-forward" style="font-size:14px;color:#F7AF2E"/>服务器信息</div>
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
            <button class="btn btn-blue-bg" style="margin-right:10px" @click="testConnection">测试</button>
            <button class="btn btn-blue-bg" @click="saveUser">保存</button>
          </div>
        </div>
      </div>
      <div slot="footer">
        
      </div>
    </Modal>
  </div>
</template>
<script>
import userServers from "../../servers/userServers";
import md5 from "js-md5"
export default {
  name: "login",
  data() {
    return {
      bodyBgImage: "url(" + require("../../assets/img/liu_BACK.png") + ")",
      isShow: true,
      activeText: true,
      username_show: false,
      switch1: false,
      username: "",
      password: "",
      userModal: false,
      userForm: {},
      dataForm: {},
      companyList: []
    };
  },
  beforeCreate() {

},
  created(){
   
  },
  mounted() {
    this.getCompanyList()
    // this.setBodyBackGround();
    var lett = this;
    document.onkeydown = function (e){
      if(lett.$refs.hbody){
        var  key = window.event.keyCode ? window.event.keyCode : window.event.which;
        if(key == 13){
          lett.handelLogin();
          e.preventDefault();
        }
      }
    }
  },
  methods: {
    setBodyBackGround() {
      document.body.style.backgroundSize = "100% 100%";
      document.body.style.backgroundRepeat = "no-repeat";
      document.body.style.backgroundPosition = "center";
      document.body.style.backgroundImage = this.bodyBgImage;
      document.body.style.overflow = "hidden";
      $(".login_html").css("height", "100%");
    },
    // 清除背景图
    clearBodyBackGround() {
      document.body.style.backgroundImage = "";
    },
    handelIsShow() {
      this.isShow = !this.isShow;
      this.activeText = !this.activeText;
    },
    handelUserFocus() {
      this.username_show = true;
    },
    handelClearUser() {
      this.username = "";
    },
    handelLogin() {
      let loginInfo = {
        userName: $.trim(this.username),
        password: $.trim(this.password),
      }
      let item = JSON.stringify(loginInfo)
      userServers.datalogin(item).then(res => {
        if (res.code == 1) {
          let data = res.result.realName;
          let token = res.result.accessToken;
          let userId = res.result.userId;
          sessionStorage.setItem("userId",userId);
          sessionStorage.setItem("loginfo", item);
          sessionStorage.setItem("username", data);
          localStorage.setItem("token",token);
          this.$Message.success("登陆成功!欢迎您:" + res.result.realName);
          this.$router.push({
            path: "/",
            name: "Home",
            params: {
              userName: res.result.realName
            }
          });
          // 此处使用sesstion存储用户信息，
          sessionStorage.user = true;
        } else {
          this.$Message.error(res.message);
        }
      });
    },
    changeAutoLogin(status) {
      if (status) {
        if (this.username && this.password) {
          localStorage.username = this.username;
          localStorage.password = this.password;
        }
      } else {
        this.$Message.error("请完善用户名,密码");
      }
    },
    autoLogin() {
      if (localStorage.username && localStorage.password) {
        this.username = localStorage.username;
        this.password = localStorage.password;
        this.handelLogin();
      }
    },
    addUser() {
      this.userModal = true
      this.$refs['userForm'].resetFields()
      this.$refs['dataForm'].resetFields()
    },
    checkUser() {
      if(!this.userForm.userName){
        console.log('kongde')
        return false
      }
      let info = {
        userName: this.userForm.userName
      }
      let item = JSON.stringify(info)
      userServers.checkUserName(item).then(res => {
        if(res.code == 1){
          if(res.result ==1){
            this.$Modal.error({
              title: '抱歉',
              content: '该用户名已经注册~'
            })
          } else if (res.result == 0){
            console.log('可以注册~')
          }
        }
      })
    },
    getCompanyList() {
      let info = {}
      let item = JSON.stringify(info)
      userServers.companyList(item).then(res => {
        if(res.code == 1){
          console.log(res.result)
          this.companyList = res.result.list
        }
      })
    },
    changeConnectName() {
      if(this.userForm.connectName){
        let user = {
          connectName: this.userForm.connectName
        }
        // let info = JSON.stringify(user)
        userServers.getConnectName(user).then(res => {
          if(res.code == 1) {
            if(res.result){
              this.dataForm = res.result
            }else{
              this.$refs["dataForm"].resetFields();
            }
          }
        })
      }
    },
    checkPassword() {
      if(this.userForm.password != this.userForm.isPassword) {
        this.$Modal.error({
          title: '抱歉',
          content: '两次输入密码不一致,请重新输入~'
        })
        this.$set(this.userForm, 'isPassword', null)
        this.$forceUpdate()
      }
    },
    saveUser() {
      console.log(this.userForm.userName, typeof this.userForm.userName)
      if(!this.userForm.userName){
        this.$Modal.error({
          title: '抱歉',
          content: '请输入用户名~'
        })
        return false
      }
      if(this.userForm.password != this.userForm.isPassword) {
        this.$Modal.error({
          title: '抱歉',
          content: '两次输入密码不一致,请重新输入~'
        })
        return false
      }
      this.userForm.status = '0'
      this.userForm.password = md5(this.userForm.password)
      let info = {
        userInfo: this.userForm,
        dataSource: this.dataForm
      }
      let item = JSON.stringify(info)
      userServers.registerUser(item).then(res => {
        if(res.code == 1) {
          this.userModal = false
          this.$Modal.success({
            title: '恭喜',
            content: '注册成功~'
          })
        } else {
          this.$Modal.error({
            title: '抱歉',
            content: '注册失败~'
          })
        }
      })
    },
    testConnection() {
      let info = {
        ip: this.dataForm.ip,
        port: this.dataForm.port,
        dbName: this.dataForm.dbName,
        userName: this.dataForm.userName,
        password: this.dataForm.password,
        dsType: "oracle", // 数据库类型，打包时需更改为 oracle ，本地为 mysql
      }
      // let info = { // 测试用的数据
      //     ip:"192.168.1.121",
      //     port:1521,
      //     dbName:"orcl",
      //     dsType:"oracle",
      //     userName:"strMix",
      //     password:"strMix"
      // }
      let item = JSON.stringify(info)
      userServers.isConnection(item).then(res => {
        if(res.code == 1){
          this.$Modal.success({
            title: '恭喜',
            content: '测试成功~'
          })
        } else {
          this.$Modal.error({
            title: '抱歉',
            content: '测试失败~'
          })
        }
      })
    }
  },

  beforeDestroy() {
    // this.clearBodyBackGround();
  }
};
</script>
<style >
@import "../../assets/styles/login.less";
.user-modal-title {
  background-color: #3a86f9;
  color: #fff;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  font-weight: bold;
}
.sub-title-blue{
  width: 120px;
  margin-left: 30px;
  color: #105AFB;
  font-size: 14px;
  font-weight: bold;
  margin: 8px 0 4px 30px;
  border-radius: 4px;
  background: linear-gradient(to right,#fff,#BCD2FD,#fff)
}
.sub-title-yellow{
  width: 120px;
  margin-left: 30px;
  color: #F7AF2E;
  font-size: 14px;
  font-weight: bold;
  margin: 8px 0 4px 30px;
  border-radius: 4px;
  background: linear-gradient(to right,#fff,#FCEDC6,#fff)
}
</style>
