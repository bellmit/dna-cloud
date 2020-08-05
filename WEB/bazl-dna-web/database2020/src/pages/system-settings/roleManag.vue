<template>
  <div>
    <!-- <ShortCut></ShortCut> -->
    <Row>
      <!-- <Col span="4">
        <SideBar></SideBar>
      </Col> -->
      <Col span="24" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/settings/authorityEdit">权限管理</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/settings/roleManag">角色管理</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <Row class="pad-left title">
            <Col span="8">
              <span class="item-label mg-right-15" style="color:#000"
                >角色名称</span
              >
              <Input
                type="text"
                placeholder="请输入角色名称"
                style="width:260px;"
                v-model.trim="titleInfo.roleName"
                @on-blur="changeInputBlur"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label mg-right-15" style="color:#000"
                >角色描述</span
              >
              <Input
                type="text"
                placeholder="请输入角色描述"
                style="width:260px;"
                v-model.trim="titleInfo.roleDes"
              ></Input>
            </Col>
          </Row>
          <Row class="pad-left">
            <Col span="24">
              <Tree :data="data1" show-checkbox ref="tree"></Tree>
            </Col>
          </Row>
          <Row class="pad-left footer-btn">
            <button class="btn btn-blue-bg" @click="handsave">保 存</button>
            <button class="btn btn-blue-border" @click="goBack">返 回</button>
          </Row>
        </div>
        <Row>
          <CopyRight></CopyRight>
        </Row>
      </Col>
    </Row>
  </div>
</template>
<script>
// import ShortCut from '@/components/ShortCut.vue'
// import SideBar from '../../components/SideBar'
import CopyRight from '../../components/CopyRight'
export default {
  components: { CopyRight },
  data() {
    return {
      titleInfo: {
        status: '1'
      },
      searchForm: {},
      data1: [],
      treeId: ''
    }
  },
  created() {
    this.treeId = this.$route.query.id
    console.log(this.treeId, 'idididi12312s')
  },
  mounted() {
    this.handInitData()
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 保存设置
    handsave() {
      if (!this.titleInfo.roleName || !this.titleInfo.roleDes) {
        this.$Modal.warning({
          title: '提示',
          content: '角色名称或角色描述不能为空！'
        })
        return false
      }
      const ids = []
      const treeList = this.$refs.tree.getCheckedAndIndeterminateNodes()
      // console.log(treeList, 'treelist=====123123')
      treeList.forEach(item => {
        ids.push(item.id)
      })
      this.titleInfo.id = this.treeId
      const info = {
        menuIds: ids,
        menuType: '3',
        sysRole: this.titleInfo
      }
      this.$axios.post('/system/role/saveAuthority', info).then(res => {
        // console.log(res, '----树形')
        this.$Message.success('保存成功！')
      })
    },
    // 处理回显数据
    getTreeList(allList, checkList) {
      /*
        为获取到的树状菜单数据添加选中状态，使用选中id与树状菜单数据中的id做比较，相同则添加选中状态
        checkList为需要勾选的数据 ，allList为整个需要渲染Tree组件的初始化数据，需要深拷贝处理
    */
      const list = []
      allList.forEach(menu => {
        for (let i = 0, len = checkList.length; i < len; i++) {
          if (menu.children) {
            menu.children.forEach(child => {
              if (child.id === checkList[i] && !child.children) {
                child.checked = true
              } else {
                if (child.children) {
                  child.children.forEach(item => {
                    if (item.id === checkList[i]) {
                      item.checked = true
                    }
                  })
                }
              }
            })
          } else {
            if (checkList[i] === menu.id) {
              menu.checked = true
            }
          }
        }
        list.push(menu)
      })
      // console.log(list, 'lisit----')
      return list
    },
    // 角色名称输入框 失焦事件
    changeInputBlur(val) {
      // console.log(val)
      const info = {
        id: this.treeId,
        roleName: this.titleInfo.roleName
      }
      this.$axios.post('/system/role/checkRoleName', info).then(res => {
        // console.log(res)
        if (res.result === 1) {
          this.$Modal.warning({
            title: '提示',
            content: '角色名称已存在，请重新输入！'
          })
        }
      })
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('/system/role/authorityList', {
          params: { id: this.treeId, menuType: '3' }
        })
        .then(res => {
          // console.log(res, '----树形')
          // eslint-disable-next-line no-unused-vars
          let arr = []
          arr = JSON.stringify(res.result.treeList)
          if (this.treeId) {
            this.ids = res.result.menuList
            this.titleInfo = res.result.sysRole
            this.data1 = this.getTreeList(JSON.parse(arr), this.ids)
          } else {
            this.data1 = JSON.parse(arr)
          }
        })
    }
  }
}
</script>
<style lang="less" scoped></style>
