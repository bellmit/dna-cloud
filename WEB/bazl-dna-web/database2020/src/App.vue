<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
</template>
<script>
import Common from './components/Common'
export default {
  name: 'App',
  data() {
    return {
      isStatus: false
    }
  },
  created() {},
  mounted() {
    // sessionStorage.setItem('isTopBar', true)
    window.addEventListener('message', event => {
      if (event.data.status) {
        this.login(event.data.status)
      } else {
        sessionStorage.setItem('isTopBar', true)
        window.parent.postMessage('0', '*')
      }
    })
    // console.log(sessionStorage.getItem('isTopBar'), 'APP组件 查看状态')
  },
  methods: {
    login(status) {
      this.$axios
        .post(
          '/system/auth/login',
          {
            userName: 'admin',
            password: '1'
          },
          { noLoading: true }
        )
        .then(res => {
          console.log('进入登录操作-===')
          sessionStorage.setItem('parentId', res.result.parentId)
          sessionStorage.setItem('accessToken', res.result.accessToken)
          sessionStorage.setItem('userId', res.result.userId)
          sessionStorage.setItem('realName', res.result.realName)
          sessionStorage.setItem('isTopBar', false)
          window.parent.postMessage('1', '*')
          setTimeout(() => {
            Common.populationQuery.bind(this)()
            Common.instoreDataTypeList.bind(this)() // 入库数据类型
            Common.sampleTypeQuery.bind(this)() // 检材类型
            Common.personTypeQuery.bind(this)() // 案件人员类型
            Common.panelNameQuery.bind(this)() // 调用试剂盒STR/YSTR
            Common.personRaceList.bind(this)() // 民族
            Common.personNationList.bind(this)() // 国籍
            Common.caseNatureQuery.bind(this)() // 案件性质
            Common.labServerList.bind(this)() // 实验室列表
            Common.qcSampleTypeList.bind(this)() // 质控样本类型
            Common.qcPersonTypeList.bind(this)() // 质控人员类别
            Common.selectByCriminalPersonType.bind(this)() // 建库人员类别
            Common.selectByPersonRelationType.bind(this)() // 人员亲缘类别
            Common.qcSexPersonType.bind(this)() // 质控人员性别
            Common.selectLabName.bind(this)() // 比对实验室范围
            Common.selectByOrgInfo.bind(this)() // 委托单位
            Common.selectRegion.bind(this)() // 所属辖区
            // Common.selectByOrg.bind(this)() // 委托单位二级
            Common.selectByTargetReationType.bind(this)() // 目标亲缘类型
            Common.certificateType.bind(this)() // 证件类型
          }, 500)
          // eslint-disable-next-line no-unused-vars
          let path = ''
          switch (status) {
            case '01':
              path = '/quickmanage/Homotype'
              break
            case '02':
              path = '/quickmanage/Kinship'
              break
            case '03':
              path = '/quickmanage/STR'
              break
            case '04':
              path = '/quickmanage/Y_STR'
              break
            case '05':
              path = '/thanin/homotype'
              break
            case '06':
              path = '/thanin/kinship'
              break
            case '07':
              path = '/thanin/ystr'
              break
            case '08':
              path = '/bazlDnaData/datamanage/case'
              break
            case '09':
              path = '/bazlDnaData/datamanage/builddatabase'
              break
            case '1001':
              path = '/setting/genelocus'
              break
            case '1002':
              path = '/setting/warehousing/str'
              break
            case '1003':
              path = '/setting/warehousing/ystr'
              break
            case '1004':
              path = '/geneticmanage/Kit'
              break
            case '1005':
              path = '/geneticmanage/STR'
              break
            case '1006':
              path = '/geneticmanage/YSTR'
              break
            case '1007':
              path = '/geneticmanage/PopulationGene'
              break
            default:
              break
          }
          this.$router.push({
            path: path
          })
          // if (status === '01') {
          //   this.$router.push({
          //     path: '/quickmanage/Homotype'
          //   })
          // } else if (status === '02') {
          //   this.$router.push({
          //     path: '/quickmanage/Kinship'
          //   })
          // } else if (status === '03') {
          //   this.$router.push({
          //     path: '/quickmanage/STR'
          //   })
          // } else if (status === '04') {
          //   this.$router.push({
          //     path: '/quickmanage/Y_STR'
          //   })
          // } else if (status === '05') {
          //   this.$router.push({
          //     path: '/thanin/homotype'
          //   })
          // } else if (status === '06') {
          //   this.$router.push({
          //     path: '/thanin/kinship'
          //   })
          // } else if (status === '07') {
          //   this.$router.push({
          //     path: '/thanin/ystr'
          //   })
          // }
        })
        .catch(error => {
          this.$Modal.error({
            title: '抱歉',
            content: error.data.message
          })
        })
    }
  }
}
</script>
<style lang="less">
#app {
  height: 100%;
  overflow: hidden;
}
@import './assets/styles/public';
@import './assets/styles/tab_height';
@import './assets/styles/tab_scrollbar';
</style>
