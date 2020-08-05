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
  </div>
</template>
<script>
import userServers from "../../servers/userServers";
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
      password: ""
    };
  },
  beforeCreate() {

},
  created(){
   
  },
  mounted() {
    this.setBodyBackGround();
    var lett = this;
    document.onkeydown = function (e){
      if(lett.$refs.hbody){
        var  key = window.event.keyCode ? window.event.keyCode : window.event.which;
        if(key == 13){
          console.log("进入键盘事件");
          lett.handelLogin();
          e.preventDefault();
        }
      }
    }
  },
  methods: {
    // onkeydown(){
    //   let key = window.event.keyCode ? window.event.keyCode : window.event.which
    //   console.log(key);
    //   if(key == 13){
    //     console.log("按下回车");
    //     this.handelLogin();
    //   }
    //   // e.preventDefault();
    // },
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
        loginName: $.trim(this.username),
        loginPassword: $.trim(this.password),
      };
      userServers.datalogin(loginInfo).then(res => {
        // console.log("");
        // console.log(res.data);
        if (res.code == 1) {
          let data = res.data.personName;
          sessionStorage.setItem("loginfo", JSON.stringify(loginInfo));
          sessionStorage.setItem("username", data);
          this.$Message.success("登陆成功!欢迎您:" + res.data.personName);
          this.$router.push({
            path: "/",
            name: "Home",
            params: {
              userName: res.data.personName
            }
          });
          // 此处使用sesstion存储用户信息，
          sessionStorage.user = true;
        } else {
          this.$Message.error("登陆失败,请重试");
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
    }
  },

  beforeDestroy() {
    this.clearBodyBackGround();
  }
};
</script>
<style >
@import "../../assets/styles/login.less";
</style>
