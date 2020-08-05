<template>
  <div id="app">
    <keep-alive>
        <Top_nav v-if="!$route.meta.keepAlive"></Top_nav>
    </keep-alive>
    <router-view></router-view>
  </div>
</template>

<script>
import MsgPromptServer from "@/servers/MsgPromptServer";
import Top_nav from "./components/top-nav";
export default {
  components: {Top_nav},
  name: "App",
  data() {
    return {
      userName: ""
    };
  },
  mounted() {
    // // 此处调用消息接口 解决初次进入页面需要手动刷新才有消息条数。
    // if (this.$route.path == "/login") {
    //   this.Msg = false;
    // } else {
    //   // document.body.style.background = "#e8e8e8";
    //   // clearInterval(this.timeId);
    //   // this.MsgNumberFun();
    //   // this.setIntervalOp()
    // }
    // if (this.$route.path == "/") {
    //   // document.body.parentNode.style.overflow = "hidden"
    // }
  },

  beforeDestroy() {
    // 清除定时
    clearInterval(this.timeId);
  },
  watch: {
    $route(to, from) {
      // if(to.path == '/'){
      //   document.body.parentNode.style.overflow = "hidden"
      // }else{
      //   document.body.parentNode.style.overflow = "visible"
      // }
      if (to.path == "/login") {
      } else if (to.path != "/login") {
        document.body.style.background = "#CCCCCC";
      }
    }
  },
  created() {
    if (sessionStorage.user == "true") {
    } else {
      // == "http://localhost:8083/iLabSTRmix/hybrid?caseId=4777461a-d6aa-46cf-afe5-c0d7460b99f6&userName=admin&passWord=1"
      let url_val = window.location.href;
      let serch = url_val.indexOf("?");
      if (serch != -1) {
        sessionStorage.user == "true";
      } else {
        this.$router.push("/login");
      }
    }
  },
  methods: {
    // 点击任意区域关闭消息框 @click="handOffMsgcon"
    handOffMsgcon(event) {
      var msgCon = document.getElementById("Msg-Container");
      var top = document.getElementById("Top_bar");
      if (msgCon && top) {
        if (!msgCon.contains(event.target) && !top.contains(event.target)) {
          msgCon.className = "Msg-Container animated bounceOut";
        }
      }
    }
  }
};
</script>
<style lang="less">
.model_con_Style {
  .ivu-modal-body {
    padding: 0;
  }
  .ivu-modal-footer {
    display: none;
  }
}
html,body,#app{
  height: 100%;
}
body{
  background: #cccccc !important;
  overflow: visible !important;
}
* {
  margin: 0;
  padding: 0;
  list-style: none;
}
@import "./assets/styles/Msg";
@import "./assets/styles/table_style";
@import "./assets/styles/base";
@import "./assets/styles/swiper";
</style>
