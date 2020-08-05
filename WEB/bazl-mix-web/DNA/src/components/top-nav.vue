<template>
  <div class="top-bar" id="Top_bar">
    <div class="top-container">
      <div class="top-left">
        <img src="../assets/img/logo.png" alt />
        <div class="top-left-text">
          <p>DNA混合样本数据库比对分析系统</p>
          <p>DNA Mixed Sample Database Comparison and Analysis System</p>
        </div>
      </div>

      <div class="top-right">
        <div>
          <ul class="tr-ul">
            <!-- :class="{ 'classStyle':index == active}" -->
            <!-- active-class="router-link-active" exact   -->
            <router-link
              :class="{ active: $route.meta.index == index }"
              v-for="(item, index) in topList"
              :key="index"
              tag="li"
              :to="item.path"
              @click.native="top_click(index)"
            >
              <p>
                <span>
                  <img :src="item.img" alt />
                </span>
                <span>{{ item.name }}</span>
              </p>
            </router-link>
          </ul>
        </div>
        <div class="Msg">
          <div @click="msgModel" class="first-child-style">
            <span>
              <img src="../assets/img/tz.png" alt />
            </span>
            <span class="posi-msg">{{ MsgNumber }}</span>
          </div>
        </div>
        <!-- <span>{{this.username}},{{this.hoursTip}}</span> -->
        <div>
          <div>
            <div title="退出登录" @click="LogOutOp" class="first-child-style">
              <span>
                <img src="../assets/img/login-out.png" alt />
              </span>
              <span>注销</span>
            </div>
          </div>
          <div class="touxiang">
            <div class="lastDiv-child-style">
              <span>
                <img src="../assets/img/touxiang.png" alt />
              </span>
              <!-- username -->
              <span>{{ username }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- :class="[Msg_Show==true?'animated bounceOutRight':'']" -->
    <div
      v-show="Msg_Show"
      id="Msg-Container"
      class="Msg-Container"
      ref="Animated"
    >
      <div class="Msg-herder">
        <span>比中提醒</span>
        <span>( {{ numX }} )</span>
      </div>
      <div class="Msg-body Scrollbar">
        <ul>
          <li v-for="(item, index) in MsgList" :key="index">
            <span>{{ index + 1 }}</span>
            <!-- :title="item.content" -->
            <span title="点击可查看详情" @click="Seemsg(item)">
              {{ item.content }}
              <!-- Number(items.status)  -->
              <!-- <b
                :class="{'msg-zn-back': item.messageType == 3, 'msg-kb-back': item.messageType == 4}" class="posi-b"
              >{{item.msgtitle}}</b> -->
            </span>

            <!-- <img title="删除本条消息" src="../assets/img/gb.png" alt=""> -->
            <span @click="DeleteMsgOp(item.id)" title="删除本条消息">×</span>
          </li>
        </ul>
      </div>
      <div class="Msg-footer">
        <div>
          <span @click="OffMsgCon">收起</span>
          <span @click="DeleteAllMsg">全部清空</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import userServers from "../servers/userServers";
import MsgPromptServer from "../servers/MsgPromptServer";
export default {
  name: "top_nav",
  data() {
    return {
      topList: [
        {
          path: "/",
          img: require("../assets/img/xz001.png"),
          name: "首页"
        },
        {
          path: "/quickMatch",
          img: require("../assets/img/xz002.png"),
          name: "DNA分析快速比对"
        },
        {
          path: "/case",
          img: require("../assets/img/baobiao.png"),
          name: "案件智能分析"
        },
        {
          path: "/MatchRecord",
          img: require("../assets/img/xs.png"),
          name: "比对记录"
        },
        {
          path: "/data",
          img: require("../assets/img/xz003.png"),
          name: "混合数据库管理"
        }
        // {
        //   path: "/user",
        //   img: require("../assets/img/xz003.png"),
        //   name: "用户管理"
        // }
      ],
      username: "",
      logOutInfo: {},
      hoursTip: "",
      Msg_Show: false,
      timeId: "",
      MsgList: [],
      MsgNumber: 0,
      numX: 0,
      userId: "",
      isActive: 0
    };
  },
  beforeDestroy() {
    // 清除定时
    clearInterval(this.timeId);
    // this.$destroy();
  },
  created() {
    // JSON.parse()
    this.userId = sessionStorage.getItem("userId");
    this.logOutInfo = JSON.parse(sessionStorage.getItem("loginfo"));
    setTimeout(() =>{
      this.username = sessionStorage.getItem("username");
    },300)
    // console.log(this.username, "查看用户1");
    // console.log(this.logOutInfo, "查看用户2");
    // console.log(this.userId, "查看用户3");
    this.getMycount();
  },
  mounted() {
    clearInterval(this.timeId);
    // this.MsgRemind();
    this.numfun();
    this.setIntervalOp();
  },
  // watch: {
  //   username(val, oldVal) {
  //     // // 普通的watch监听
  //     console.log(val, "watch----");
  //     this.username = val;
  //     // console.log('新知-----', val, oldVal)
  //     // if (val) {
  //     //   if (this.panelval) {
  //     //     // console.log('进入判断')
  //     //     this.handpanel(this.panelval)
  //     //   }
  //     // }
  //   },
  //   deep: true,
  //   immediate: true
  // },
   watch: {
    username(val, newVal) {
      if (val) {
          this.username = val
      }
    },
    // deep: true,
    // immediate: true
  },
  methods: {
    top_click(index) {
      this.isActive = index;
    },
    msgModel() {
      this.Msg_Show = true;
      this.$refs.Animated.className = "Msg-Container animated bounceInDown bloMsgModle";
      this.MsgRemind();
    },
    // 消息全部清空
    DeleteAllMsg() {
      MsgPromptServer.DeleteAllMsgSever().then(res => {
        if (res.code == 1) {
          this.$Message.success("消息成功清空！");
          this.MsgRemind();
        } else {
          this.$Message.error("操作失败！");
        }
      });
    },
    // 删除消息
    DeleteMsgOp(this_id) {
      let idInfo = { id: this_id };
      MsgPromptServer.DeleteMsgServer(idInfo).then(res => {
        if (res.code == 1) {
          this.$Message.success("！");
          this.MsgRemind();
        } else {
          this.$Message.error(res.errorMessage);
        }
      });
    },
    // 定时调用消息接口  刷新消息条数
    setIntervalOp() {
      this.timeId = setInterval(() => {
        this.numfun();
      }, 6000);
    },
    // 消息框显示 获取消息内容
    MsgRemind() {
      let info = {
        userId: this.userId,
        page: 1,
        state: 0
      };
      MsgPromptServer.MsgPromptServer(info).then(res => {
        if (res.code == 1) {
          this.MsgList = res.result.list;
        } else {
          // this.$Message.error(res.errorMessage);
        }
      });
    },
    numfun() {
      MsgPromptServer.GetMsgNumberSever().then(res => {
        if (res.code == 1) {
          this.numX = res.result.count;
          if (res.result.count > 100 || res.result.count == 100) {
            this.MsgNumber = "99+";
          } else {
            this.MsgNumber = res.result.count;
          }
          if (res.result.mobileNew == 1) {
            this.msgModel();
          }
        } else {
          // this.$Message.error(res.errorMessage);
        }
      });
    },
    // 查看消息 同时删除消息
    Seemsg(item) {
      let idInfo = { id: item.id };
      MsgPromptServer.seeMsgtoDetail(idInfo).then(res => {
        if (res.code == 1) {
          // this.MsgRemind();
          this.$router.push({
            path: "/EarlyWarning",
            name: "EarlyWarning",
            params: {
              id: item.compareId
            }
          });
        } else {
          this.$Message.error("操作失败！");
        }
      });
    },
    // 收起消息模板
    OffMsgCon() {
      let ids = [];
      this.MsgList.forEach(item => {
        ids.push(item.id);
      });
      let idLit = JSON.stringify(ids);
      this.$refs.Animated.className = "Msg-Container animated bounceOut disMsgModle";
      MsgPromptServer.msgModel(idLit).then(res => {});
    },
    getMycount() {
      let date = new Date();
      if (date.getHours() >= 0 && date.getHours() < 12) {
        this.hoursTip = "上午好";
      } else if (date.getHours() >= 12 && date.getHours() < 18) {
        this.hoursTip = "下午好";
      } else {
        this.hoursTip = "晚上好";
      }
    },
    // 退出登录
    LogOutOp() {
      clearInterval(this.timeId);
      localStorage.clear();
      sessionStorage.clear();
      this.$destroy();
      this.$router.push("/login");
    }
  }
};
</script>
<style lang="less" scoped>
@import "../assets/styles/Msg";
@import "../assets/styles/scrollbar";
.top-bar {
  // margin-top:15px;
  height: 56px;
  width: 100%;
  background: url("../assets/img/top-bg.png") no-repeat left;
  background-size: cover;
  overflow: hidden;
  .top-container {
    height: 100%;
    width: 100%;
    padding: 0 15px;
    .top-left {
      width: 30%;
      float: left;
      display: inline-block;
      .top-left-text {
        display: inline-block;
        height: 56px;
        color: #fff;
        margin-left: 10px;
        p:first-child {
          font-size: 18px;
          letter-spacing: 1px;
          text-align: left;
        }
        p:nth-child(2) {
          // font-size: 14px;
          text-align: left;
        }
      }
      img {
        width: 40px;
      }
    }
    .top-right {
      width: 70%;
      height: 100%;
      float: right;
      div:nth-child(1) {
        float: left;
      }
      .Msg {
        float: left;
        width: 50px;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
        cursor: pointer;
        div {
          .posi-msg {
            display: inline-block;
            color: #fff;
            text-align: center;
            line-height: 22px;
            width: 22px;
            height: 22px;
            background: #ff624e;
            border-radius: 50%;
            position: absolute;
            top: 2px;
            right: 5px;
          }
        }
      }
      div:nth-child(3) {
        float: right;
        height: 100%;
        div {
          height: 100%;
          display: flex;
          align-items: center;
          font-size: 14px;
          color: #fff;
          .first-child-style {
            cursor: pointer;
            margin-right: 25px;
            img {
              width: 30px;
              margin-right: 5px;
            }
          }
          .lastDiv-child-style {
            img {
              width: 40px;
              margin-right: 5px;
            }
          }
        }
      }
      .tr-ul {
        height: 56px;
        // margin-left: 100px;
        li {
          float: left;
          height: 100%;
          display: flex;
          align-items: center;
          cursor: pointer;
          p {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 155px;
            height: 30px;
            font-size: 14px;
            color: #fff;
            font-family: "微软雅黑";
            border-right: 1px solid #ccc;
            img {
              width: 20px;
              height: 20px;
              margin-right: 5px;
            }
          }
        }
        li:hover {
          background: rgba(98, 158, 255, 0.4);
          p {
            font-size: 14px;
            font-weight: bold;
          }
        }
      }
    }
  }
  @media screen and (max-width: 1800px) {
    .top-container {
      .top-right {
        .tr-ul {
          margin: 0;
          li {
            p {
              width: 130px;
              font-size: 12px;
              img {
                width: 22px;
                height: 22px;
                margin-right: 3px;
              }
            }
          }
          li:hover {
            background: rgba(98, 158, 255, 0.4);
            p {
              font-size: 12px;
              font-weight: bold;
            }
          }
        }
      }
    }
  }
  .disMsgModle{
    display: none;
  }
  .bloMsgModle{
    display: block;
  }
  .active {
    background: rgba(98, 158, 255, 0.4) !important;
  }
}
</style>
