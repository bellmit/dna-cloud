<template>
  <div class="hspage">
    <ShortCut v-show="isTopBar"></ShortCut>
    <div class="page-content">
      <Row class="row-content">
        <Col span="4">
          <SideBar v-show="isTopBar"></SideBar>
        </Col>
        <Col :span="num">
          <!-- <router-view /> -->
          <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!$route.meta.keepAlive"></router-view>
          <div class="footer">
            <CopyRight />
          </div>
        </Col>
      </Row>
    </div>
  </div>
</template>
<script>
import ShortCut from './ShortCut.vue'
import SideBar from './SideBar.vue'
import CopyRight from './CopyRight.vue'
export default {
  name: 'layout',
  data() {
    return {
      isTopBar: true,
      num: 20
    }
  },
  components: {
    ShortCut,
    SideBar,
    CopyRight
  },
  mounted() {
    this.isTopBar = JSON.parse(sessionStorage.getItem('isTopBar'))
    console.log(this.isTopBar, '查看当前转台 layout')
    if (this.isTopBar === null) {
      this.isTopBar = true
    }
    if (this.isTopBar) {
      this.num = 20
    } else {
      this.num = 24
    }
  }
}
</script>
<style lang="less">
.page-content {
  display: flex;
  // flex-direction: column;
  height: 100%;
  width: 100%;
  .row-content {
    width: 100%;
  }
  .footer {
    position: fixed;
  }
}
</style>
