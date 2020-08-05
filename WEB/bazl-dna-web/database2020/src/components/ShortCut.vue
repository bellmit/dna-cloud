<template>
  <div>
    <div class="top-bar">
      <div class="top-container">
        <div class="top-left">
          <img src="../assets/img/logo.png" alt="" />
          <div class="top-left-text">
            <p>
              全国公安机关DNA数据库实战应用中心云服务平台
            </p>
            <p>
              DNA Comparison Management System
            </p>
          </div>
          <span class="org-name">
            {{ orgList }}
          </span>
        </div>
        <div class="top-right">
          <!-- <span>{{username}},下午好</span> -->
          <div class="login-out" @click.prevent="loginOut">
            <!-- <img src="../assets/img/login-out.png" alt=""> -->
            注销
          </div>
          <div class="touxiang">
            <img src="../assets/img/user.png" alt="" />
            {{ userName }}
          </div>
        </div>
      </div>
    </div>
    <!-- <router-view></router-view> -->
  </div>
</template>
<script>
export default {
  data() {
    return {
      userName: '',
      orgList: ''
    }
  },
  created() {
    this.userName = sessionStorage.getItem('realName')
    this.orgList = sessionStorage.getItem('orgList')
    // if (
    //   sessionStorage.getItem('realName') &&
    //   sessionStorage.getItem('realName') !== 'undefined'
    // ) {
    //   this.userName = sessionStorage.getItem('realName')
    // }
    // if (
    //   sessionStorage.getItem('orgList') &&
    //   sessionStorage.getItem('orgList') !== 'undefined'
    // ) {
    //   this.orgList = sessionStorage.getItem('orgList')
    // }
    // console.log(this.userName, this.orgList)
  },
  mounted() {},
  methods: {
    loginOut() {
      sessionStorage.clear() // 退出登录 清空所有缓存
      sessionStorage.removeItem('realName')
      sessionStorage.removeItem('accessToken')
      this.$router.push({
        path: '/'
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../assets/styles/shortcut';
</style>
