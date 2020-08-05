<template>
  <div class="content-box">
    <Row>
      <!-- <Col span="4">
        <SideBar :SidebarParams="SidebarParams"></SideBar>
      </Col> -->
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>遗传学基因数据管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/Kit">试剂盒管理</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒名称</span>
              <Input
                type="text"
                placeholder="请输入试剂盒名称"
                class="item-input"
                v-model="searchInfo.panelName"
              ></Input>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒别名</span>
              <Input
                type="text"
                placeholder="请输入试剂盒别名"
                class="item-input"
                v-model="searchInfo.aliasName"
              ></Input>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒类型</span>
              <Select
                placeholder="请选择试剂盒"
                style="display:inline-block;"
                class="item-input"
                v-model="searchInfo.panelType"
              >
                <Option value="1">STR</Option>
                <Option value="2">Y-STR</Option>
              </Select>
            </Col>
            <Col span="6" class="item-col">
              <button class="btn btn-blue-bg" @click="handSearch">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">重置</button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">
            查询结果<button
              @click="handaddReagent(1)"
              class="btn btn-blue-bg mg-left-15"
            >
              添加
            </button>
          </div>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
          ></Table>
          <div class="page">
            <span>
              当前第{{ searchInfo.pageIndex }}页/共{{ pageCount }}页/共{{
                allRecordCount
              }}条信息
            </span>
            <Page
              :total="allRecordCount"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="searchInfo.pageIndex"
              @on-change="changePage"
              :page-size="15"
            />
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchInfo: {
        pageIndex: 1,
        panelName: null, // 试剂盒名称
        aliasName: null, // 试剂盒别名
        panelType: null //  试剂盒类型
      },
      SidebarParams: {
        open: ['5'],
        active: '5-3'
      },
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '试剂盒名称',
          // className: 'blue-font',
          key: 'panelName',
          align: 'center'
        },
        {
          title: '试剂盒厂商',
          key: 'producerName',
          align: 'center'
        },
        {
          title: '试剂盒别名',
          key: 'aliasName',
          align: 'center'
        },
        {
          title: '试剂盒类型',
          key: 'panelTyp',
          align: 'center',
          render: (h, params) => {
            let type = ''
            if (params.row.panelType) {
              if (params.row.panelType === 1) {
                type = 'STR'
              } else if (params.row.panelType === 2) {
                type = 'Y-STR'
              }
            } else {
              type = null
            }
            return h(
              'span',
              {
                prop: {}
              },
              type
            )
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'blue-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: () => {
                    // console.log(params.row.id)
                    this.$router.push({
                      path: '/geneticmanage/KitEdit',
                      query: {
                        id: params.row.id
                      }
                    })
                  }
                }
              },
              '编辑'
            )
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1
    }
  },
  created() {},
  mounted() {
    this.handInitData()
  },
  methods: {
    //
    handaddReagent(type) {
      this.$router.push({
        path: '/geneticmanage/KitEdit',
        query: {
          id: null
        }
      })
    },
    // 表格分页监听
    changePage(val) {
      // console.log(val)
      this.searchInfo.pageIndex = val
      this.handInitData()
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .post('database/panel/selectPanelInfoList', this.searchInfo)
        .then(res => {
          // console.log(res)
          if (res.code === 1) {
            this.tableData = res.result.dnaPanelInfoList
            this.allRecordCount = res.result.pageInfo.allRecordCount
            this.pageCount = res.result.pageInfo.pageCount
          }
        })
    },
    // 查询
    handSearch() {
      // get  params: {}
      this.searchInfo.pageIndex = 1
      this.handInitData()
    },
    // 重置查询条件
    handleReset() {
      Object.keys(this.searchInfo).forEach(key => {
        if (key !== 'pageIndex') {
          this.searchInfo[key] = ''
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/genemanage.less';
.item-input,
.item-select {
  width: 180px !important;
}
</style>
