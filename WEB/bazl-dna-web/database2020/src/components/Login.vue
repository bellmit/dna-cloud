<template>
  <Row class="bg">
    <Col span="24" class="box">
      <div class="login-box">
        <p class="logo">
          <img src="../assets/img/logo.png" alt="" />
        </p>
        <p class="title-zh">
          全国公安机关DNA数据库实战应用中心云服务平台
        </p>
        <p class="title-us">
          DNA Comparison Management System
        </p>
        <p class="user-name">
          <Input
            placeholder="请输入用户名"
            v-model="userInfo.loginName"
          ></Input>
        </p>
        <p class="password">
          <Input
            placeholder="请输入密码"
            v-model="userInfo.loginPassword"
            @keyup.enter.native="login"
            type="password"
          ></Input>
        </p>
        <!-- <p class="switch-is">
          <i-switch v-model="switch1" @on-change="change"/>记住密码
        </p> -->
        <p class="login-btn">
          <button class="btn" @click="login">登录</button>
        </p>
        <p class="copy-right">
          北京博安智联科技有限公司
        </p>
        <p class="phone">
          技术支持:400-011-5530
        </p>
        <p class="logo-w">
          <img src="../assets/img/logo-w.png" alt="" />
        </p>
        <p class="download">下载Chrome浏览器:<a>Windows XP版 Win7/8/9</a></p>
      </div>
    </Col>
  </Row>
</template>
<script>
export default {
  data() {
    return {
      switch1: false,
      userInfo: {
        loginName: '',
        loginPassword: ''
      }
    }
  },
  methods: {
    change(status) {
      // this.$Message.info('开关状态：' + status);
    },
    login() {
      console.log('进入事件----')
    //   this.$axios
    //     .post(
    //       '/system/auth/login',
    //       {
    //         userName: this.userInfo.loginName,
    //         password: this.userInfo.loginPassword
    //       },
    //       { noLoading: true }
    //     )
    //     .then(res => {
    //       sessionStorage.setItem('accessToken', res.result.accessToken)
    //       sessionStorage.setItem('userId', res.result.userId)
    //       sessionStorage.setItem('realName', res.result.realName)
    //       console.log(res)
    //       this.$router.push({
    //         path: '/home',
    //         name: 'Home',
    //         params: {}
    //       })
    //     })
    //     .catch(error => {
    //       console.log(error, 'error')
    //       this.$Modal.error({
    //         title: '抱歉',
    //         content: error.data.message
    //       })
    //     })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../assets/styles/login.less';
</style>
