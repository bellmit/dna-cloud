<template>
  <Row class="bg">
    <Col span="24" class="box">
      <div class="loginTitle">
        <p class="tilogo">
          <img src="../assets/img/logo.png" alt />
        </p>
        <div class="loginName">
          <p class="loginName1">全国公安机关DNA数据库实战应用中心云服务平台</p>
          <p class="loginName2">
            Cloud service platform of national public security DNA database
            practical application center
          </p>
        </div>
      </div>
      <div class="loginBody">
        <div class="loginsline">
          <div class="ball">
            <img src="../assets/img/ball.png" alt="" />
          </div>
          <div class="loginInp">
            <p class="wecome">欢迎登录</p>
            <div class="username">
              <span>
                <img src="../assets/img/logins-user.png" alt />
              </span>
              <input
                placeholder="请输入用户名"
                v-model="userInfo.loginName"
                type="text"
              />
            </div>
            <div class="username">
              <span>
                <img src="../assets/img/logins-s.png" alt />
              </span>
              <input
                placeholder="请输入密码"
                v-model="userInfo.loginPassword"
                type="password"
              />
            </div>
            <div class="username">
              <span>
                <img src="../assets/img/logins-s.png" alt />
              </span>
              <input
                @keyup.enter="loginFn"
                placeholder="请输入验证码"
                v-model="vser"
                type="text"
              />
              <div class="vserify">
                <p class="vserifynum">{{ num }}</p>
                <span @click="vseriFn">
                  <img src="../assets/img/ref.png" alt />
                </span>
              </div>
            </div>
            <Button type="primary" class="btn" @click="loginFn">登录</Button>
            <Button type="primary" class="btn1">PKI登录</Button>
            <p class="want">忘记密码？</p>
          </div>
        </div>
      </div>
      <p class="companyName">Copyright © 2020 北京博安智联科技有限公司</p>
    </Col>
  </Row>
</template>
<script>
export default {
  name: 'logins',
  data() {
    return {
      num: '',
      vser: '',
      userInfo: {
        loginName: '',
        loginPassword: ''
      }
    }
  },
  methods: {
    vseriFn() {
      const str = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]
      this.num = ''
      for (let i = 0; i < 4; i++) {
        var charIndex = Math.floor(Math.random() * str.length)
        this.num += charIndex
      }
    },
    loginFn() {
      this.$axios
        .post(
          '/system/auth/login',
          {
            userName: this.userInfo.loginName,
            password: this.userInfo.loginPassword
          },
          { noLoading: true }
        )
        .then(res => {
          // console.log(res, '登录---')
          if (this.vser !== '') {
            if (this.num === this.vser) {
              sessionStorage.setItem('parentId', res.result.parentId)
              sessionStorage.setItem('accessToken', res.result.accessToken)
              sessionStorage.setItem('userId', res.result.userId)
              sessionStorage.setItem('realName', res.result.realName)
              if (res.result.orgList) {
                // eslint-disable-next-line prefer-const
                let str = res.result.orgList.join(',')
                sessionStorage.setItem('orgList', str)
              }
              // console.log(res)
              this.$router.push({
                path: '/home'
              })
            } else {
              this.$Modal.error({
                title: '验证码输入错误'
              })
              this.vseriFn()
            }
          } else {
            this.$Modal.error({
              title: '验证码不能为空'
            })
            this.vseriFn()
          }
        })
        .catch(error => {
          // console.log(error, 'error')
          this.$Modal.error({
            title: '抱歉',
            content: error.data.message
          })
          this.vseriFn()
        })
    }
  },
  mounted() {
    this.vseriFn()
  }
}
</script>
<style lang="less" scoped>
@import '../assets/styles/logins';
</style>
