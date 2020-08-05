<template>
  <div class="top-bar" id="Top_bar">
    <div class="top-container">
      <div class="top-left">
        <img src="../assets/img/logo.png" alt />
        <div class="top-left-text">
          <p>DNA混合分型自动拆分比对数据库系统</p>
          <p>DNA-mix automatic split and comparison database system</p>
        </div>
      </div>
      <div class="top-right">
        <div class="Msg">
          <div @click="MsgRemind"  class="first-child-style">
              <span class="margin-right"><img src="../assets/img/lingdang.png" alt /></span>
              <span class="hover-span">
                比对消息
                <p class="Msg-p">{{MsgNumber}}</p>
              </span>
          </div>
        </div>
        <!-- <span>{{this.username}},{{this.hoursTip}}</span> -->
        <div>
          <div title="退出登录"  @click="LogOutOp" class="first-child-style login-out">
            <span class="margin-right"><img style="width:30px;" src="../assets/img/login-out.png" alt /></span>
            <span>注销</span>
          </div>
        </div>
        <div class="touxiang">
          <div class="lastDiv-child-style">
            <span class="margin-right"><img src="../assets/img/touxiang.png" alt /></span>
            <span>{{this.username}}</span>
          </div>
        </div> 
      </div>
    </div>
    <!-- :class="[Msg_Show==true?'animated bounceOutRight':'']" -->
    <div v-show="Msg_Show" id="Msg-Container" class="Msg-Container" ref="Animated">
      <div class="Msg-herder">
        <span>比中提醒</span>
        <span>( {{numX}} )</span>
      </div>
      <div class="Msg-body Scrollbar">
        <ul>
          <li v-for="(item,index) in MsgList">
            <span>{{index+1}}</span>
            <span :title="item.content">
              {{item.content}}
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
      username: "",
      logOutInfo: {},
      hoursTip:"",
      Msg_Show:false,
      timeId:"",
      MsgList:[],
      MsgNumber:0,
      numX:0,

    };
  },
  created() {
    this.logOutInfo = JSON.parse(sessionStorage.getItem("loginfo"));
    this.username = sessionStorage.getItem("username");
    this.getMycount();
  },
  beforeDestroy() {
    // 清除定时
    clearInterval(this.timeId);
  },
  mounted(){
      clearInterval(this.timeId);
      this.MsgNumberFun();
      this.setIntervalOp();
      // this.numFun();   
  },
  methods: {
    // 99+
    // numFun(){
    //   if(this.MsgNumber > 100 || this.MsgNumber == 100){
    //      this.MsgNumber = "99+";
    //      console.log("进入判断");
    //   }else if(this.MsgNumber  < 100){
    //      this.MsgNumber;
    //   }
    // },
    // 消息全部清空
    DeleteAllMsg() {
      MsgPromptServer.DeleteAllMsgSever().then(res => {
          console.log(res);
          this.MsgRemind();
          if(res.code == 1){
            this.$Message.success("消息成功清空！");
            this.MsgRemind();
          }else{
            this.$Message.error("操作失败！");
          }
      });
    },
    // 删除消息
    DeleteMsgOp(this_id) {
      let idInfo = { id: this_id };
      MsgPromptServer.DeleteMsgServer(idInfo).then(res => {
        if (res.code == 1) {
          this.MsgRemind();
          this.MsgNumberFun();
        } else {
          this.$Message.error(res.errorMessage);
        }
      });
    },
    // 消息数量接口 
    MsgNumberFun() {
      MsgPromptServer.GetMsgNumberSever().then(res => {
        this.numX = res.data.NOTREADING;
        if(res.data.NOTREADING > 100 || res.data.NOTREADING == 100){
          this.MsgNumber = "99+" 
        }else{
          this.MsgNumber = res.data.NOTREADING;
        }
      });
    },
    // 定时调用消息接口  刷新消息条数
    setIntervalOp() {
      this.timeId = setInterval(() => {
        this.MsgNumberFun();
      }, 60000);
    },
    // 消息框显示 获取消息内容
    MsgRemind(){
      this.Msg_Show = true;
      this.$refs.Animated.className = 'Msg-Container animated bounceInDown'
      MsgPromptServer.MsgPromptServer().then(res => {
        // console.log(res);
        if (res.code == 1) {
          this.MsgList = res.data;
        } else {
          this.$Message.error(res.errorMessage);
        }
      });
    },
    // 删除消息
    OffMsgCon(){
      // this.Msg_Show = false;
      this.$refs.Animated.className = 'Msg-Container animated bounceOut'
    },
    getMycount(){
  　　let date=new Date();
  　　if(date.getHours()>=0&&date.getHours()<12){
  　　　　this.hoursTip="上午好"
  　　}else if(date.getHours()>=12&&date.getHours()<18){
  　　　　this.hoursTip="下午好"
  　　}else{
  　　　　this.hoursTip="晚上好"
  　　}
    },
    // 退出登录
    LogOutOp() {
      userServers.LogOutServer(this.logOutInfo).then(res => {
        if (res.code == 1) {
          sessionStorage.user = false;
          this.$router.push({
            path: "login"
          });
        } else {
          this.$Message.error("退出失败！");
        }
      });
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
      float: left;
      display: inline-block;
      .top-left-text {
        display: inline-block;
        height: 56px;
        color: #fff;
        margin-left: 10px;
        p:first-child {
          font-size: 22px;
          letter-spacing: 7px;
          text-align: left;
        }
        p:nth-child(2) {
          font-size: 14px;
          text-align: left;
        }
      }
      img {
        width: 40px;
      }
    }
    .top-right {
      width: 500px;
      height: 100%;
      float: right;
      font-size: 14px;
      color: #fff;
      font-family: '微软雅黑';
      font-weight: bold;
      div{
        float: left;
        width: 33%;
        height: 100%;
        display: flex;
        justify-content:flex-end;
      }
      .Msg{
        div:first-child{
           span:first-child{

            }
            span:last-child{
              position: relative;
              p{
                position: absolute;
                width: 25px;
                height: 25px;
                background:rgba(255,99,84,1);
                border-radius: 12.5px;
                font-size: 10px !important;
                top: -15px;
                right: -25px;
                text-align: center;
                line-height: 25px;
              }
            }
        }
      }
      .login-out{

      }
      .touxiang{
        img{
          width: 40px;
        }
      }
      // div:nth-child(1){

      // }
      
        //       img {
        //   width: 40px;
        //   vertical-align: middle;
        // }
      // float: right;
      // font-size: 14px;
      // span:first-child {
      //   color: #edac2d;
      //   font-size: 14px;
      //   line-height: 56px;
      // }
      // .login-out {
      //   margin-left: 30px;
      //   display: inline-block;
      //   color: #fff;
      //   cursor: pointer;
      //   img {
      //     background: url("../assets/img/login-out.png") no-repeat center;
      //     vertical-align: middle;
      //   }
      // }
      // .touxiang {
      //   display: inline-block;
      //   margin-left: 30px;
      //   color: #fff;
      //   // cursor: pointer;
      //   img {
      //     width: 40px;
      //     vertical-align: middle;
      //   }
      // }
    }
    .margin-right{
      margin-right: 5px;
    }
   .login-out:hover{
      // border: 1px dashed red;
      color:brown !important;
    }
  }
}
.first-child-style{
    width: 55% !important;
    height: 100% !important;
    display: flex !important;
    align-items: center !important;
    cursor: pointer;   
}
.first-child-style:hover{
  // color: #ff6354
  // .Msg-p{
  //   color: #ff6354;
  // }
  .hover-span{
    color: #ff6354;
    .Msg-p{
      color: #fff;
    }
  }
}
.lastDiv-child-style{
    width: 80% !important;
    height: 100% !important;
    display: flex !important;
    align-items: center !important;
}
</style>
