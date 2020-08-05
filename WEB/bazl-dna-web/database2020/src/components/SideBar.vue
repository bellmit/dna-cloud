<template>
  <div class="left-nav">
    <Menu ref="side_menu" theme="dark" :accordion="true" :active-name="$route.name"
      :open-names="openNames"       @on-open-change="openItem"
      @on-select="menuSelected">
        <template  v-for="(menuItem, menuIndex) in menuList">
          <MenuItem
        v-if="!menuItem.children || menuItem.children.length == 0"
        :key="menuIndex"
        :name="menuItem.path"
        :to="menuItem.path"
        :index="menuItem.path"
      >
        <i :class="[' iconfont', menuItem.icon]" />
        <span>{{ menuItem.name }}</span>
      </MenuItem>
      <Submenu v-else :name="menuItem.name" :key="menuIndex">
        <template slot="title">
          <i :class="[' iconfont', menuItem.icon]" />
          <span>{{ menuItem.name }}</span>
        </template>
        <div v-for="(item, index) in menuItem.children" :key="index">
          <MenuItem
            v-if="!item.children || item.children.length == 0"
            :name="item.path"
            :to="item.path"
            :index="item.path"
            >{{ item.name }}</MenuItem
          >
          <Submenu v-else :name="index">
            <template slot="title">
              <span>{{ item.name }}</span>
            </template>
            <MenuItem
              v-for="(j, i) in item.children"
              :key="i"
              :name="j.path"
              :to="j.path"
              :index="j.path"
            >
              {{ j.name }}
            </MenuItem>
          </Submenu>
        </div>
      </Submenu>
        </template>
      </Menu>
  </div>
</template>
<script>
export default {
  data() {
    return {
      currentName: '',
      menuList1: [
        {
          name: '首页',
          path: '/home',
          icon: 'iconshouye',
          index: '1'
        },
        {
          name: '数据综合管理',
          icon: 'iconshujuzongheguanli',
          num: '1',
          children: [
            {
              name: '案件数据管理',
              path: '/datamanage/case',
              icon: '',
              num: '1-1'
            },
            {
              name: '建库数据管理',
              path: '/datamanage/builddatabase',
              icon: '',
              num: '1-2'
            },
            {
              name: '质控数据管理',
              path: '/datamanage/QualityControl',
              icon: '',
              num: '1-3'
            }
          ]
        },
        {
          name: '比中信息管理',
          icon: 'iconbizhongxinxiguanli',
          children: [
            {
              name: '同型比中',
              path: '/thanin/homotype',
              icon: ''
            },
            {
              name: '亲缘比中',
              path: '/thanin/kinship',
              icon: ''
            },
            {
              name: 'Y-STR比中', // y-str
              path: '/thanin/ystr',
              icon: ''
            }
          ]
        },
        {
          name: '快速比对管理',
          icon: 'iconkuaisubidui',
          children: [
            {
              name: '同型比对',
              path: '/quickmanage/Homotype'
            },
            {
              name: '亲缘比对',
              path: '/quickmanage/Kinship'
            },
            {
              name: '混合STR比对',
              path: '/quickmanage/STR'
            },
            {
              name: 'Y-STR比对',
              path: '/quickmanage/Y_STR'
            }
          ]
        },
        {
          name: '基础遗传数据管理',
          icon: 'iconjiyinshujuguanli',
          children: [
            {
              name: 'STR基因座管理',
              path: '/geneticmanage/STR'
            },
            {
              name: 'Y-STR基因座管理',
              path: '/geneticmanage/YSTR'
            },
            {
              name: '试剂盒管理',
              path: '/geneticmanage/Kit'
            },
            {
              name: '种群基因频率管理',
              path: '/geneticmanage/PopulationGene'
            },
            {
              name: '似然率管理',
              path: '/geneticmanage/LikelihoodRatio'
            },
            {
              name: '亲权指数管理',
              path: '/geneticmanage/Paternity'
            }
          ] // 亲权指数管理
        },
        {
          name: '数据监控管理',
          icon: 'iconshujujiankongguanli',
          children: [
            {
              name: '案件上报监控',
              path: '/monitoring/case'
            },
            {
              name: '建库上报监控',
              path: '/monitoring/database'
            },
            {
              name: '数据比对监控',
              path: '/monitoring/compare'
            }
          ]
        },
        {
          name: '系统管理',
          icon: 'iconxitongguanli',
          num: '6',
          children: [
            {
              name: '入库基因座位点数设置',
              path: '/setting/genelocus'
            },
            {
              name: '入库比对设置',
              num: '6-2',
              children: [
                {
                  name: 'STR入库比对设置',
                  path: '/setting/warehousing/str'
                },
                {
                  name: 'Y-STR比对设置',
                  path: '/setting/warehousing/ystr'
                },
                {
                  name: '混合STR比对设置',
                  path: '/setting/warehousing/mixstr'
                }
              ]
            },
            {
              name: '用户管理',
              path: '/setting/user'
            },
            {
              name: '权限管理',
              path: '/setting/authority'
            },
            {
              name: '实验室管理',
              path: '/setting/laboratory'
            }
          ]
        },
        {
          name: '系统配置',
          path: '/setting/configuration',
          icon: 'iconxitongpeizhi'
        }
      ],
      menuList: [],
      openNames: []
    }
  },
  created() {
    if (sessionStorage.getItem('openNames') && sessionStorage.getItem('openNames') !== 'undefined') {
      this.openNames = sessionStorage.getItem('openNames').split(',')
    }
    if (
      sessionStorage.getItem('menulist') &&
      sessionStorage.getItem('menulist') !== 'undefined'
    ) {
      this.menuList = JSON.parse(sessionStorage.getItem('menulist'))
      // console.log(this.menuList)
    } else {
      this.$axios.get('/system/auth/menu/3', {}).then(res => {
        this.menuList = res.result
        sessionStorage.setItem('menulist', JSON.stringify(res.result))
        // console.log(this.menuList)
      })
    }
  },
  methods: {
    openItem(params) {
      sessionStorage.setItem('openNames', params)
      this.openNames = params
      // console.log(this.$route, this.$route.name, '$route$route')
    },
    menuSelected(name) {
    }
  },
  moundted() {
    this.openNames = sessionStorage.getItem('openNames').split(',')
  },
  updated() {
    if (sessionStorage.getItem('linkFlag') === '1') {
      this.openNames = ['比中信息管理']
      sessionStorage.setItem('linkFlag', '0')
    }
    if (sessionStorage.getItem('caseDataOpen') === '1') {
      this.openNames = ['数据综合管理']
      sessionStorage.setItem('caseDataOpen', '0')
    }
    this.$nextTick(() => {
      this.$refs.side_menu.updateOpened()
      this.$refs.side_menu.updateActiveName()
    })
  }
}
</script>
<style lang="less">
.left-nav {
  width: 100%;
  // width:300px!important;
  display: flex;
  flex-direction: column;
}
.ivu-menu {
  width: 100% !important;
  // min-width: 255px!important;
}
.ivu-menu-dark {
  background: #2f363e !important;
}
.ivu-menu-dark.ivu-menu-vertical .ivu-menu-item,
.ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu-title {
  color: #fff !important;
  font-size: 16px !important;
}

.ivu-menu-dark.ivu-menu-vertical .ivu-menu-opened {
  background: none !important;
}
// .ivu-menu-dark .ivu-menu-vertical .ivu-menu-opened > .ivu-menu > div:hover a{
//   color: #2d8cf0 !important;
// }
.ivu-menu .ivu-menu-item:hover {
  color: #2d8cf0 !important;
}
.ivu-menu-item-active.ivu-menu-opened.ivu-menu-child-item-active
  > .ivu-menu
  > div
  > .ivu-menu-item-active.ivu-menu-item-selected {
  color: #2d8cf0 !important;
  background: none !important;
}
.left-nav {
  height: calc(100vh - 20px);
  // background: #515a6e;
  background: #2f363e;
}
// .ivu-menu-dark.ivu-menu-vertical .ivu-menu-item-active:not(.ivu-menu-submenu) {
//   border-right: none;
//   color: #fff;
//   background: #2d8cf0 !important;
// }
// .ivu-menu-vertical > a .ivu-menu-item-active{
//   background: #364a5f !important;
//   // color: #fff !important;
// }
// .ivu-menu-dark.ivu-menu-vertical .ivu-menu .ivu-menu-item{
//   // background: #fff !important;
//   // color: #2d8cf0 !important;
// }
.ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active,
.ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active:hover {
  background: none !important;
}
.ivu-menu-dark.ivu-menu-vertical .ivu-menu-item-active:not(.ivu-menu-submenu) {
  background: none !important;
  color: #2d8cf0 !important;
}
.ivu-menu-vertical .ivu-menu-submenu-title-icon {
  right: 0 !important;
}
.ivu-menu-dark.ivu-menu-vertical.ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active, .ivu-menu-dark.ivu-menu-vertical .ivu-menu-submenu .ivu-menu-item-active:hover{
  color: #2d8cf0 !important;
  background: none !important;
}
</style>
