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
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/KitEdit"
              >{{ title }}试剂盒信息</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            基本信息
          </div>
          <Row class="item-row">
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒名称</span>
              <Input
                type="text"
                placeholder="请输入试剂盒名称"
                class="item-input"
                v-model.trim="panelInfo.panelName"
              ></Input>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒别名</span>
              <Input
                type="text"
                placeholder="请输入试剂盒别名"
                class="item-input"
                v-model.trim="panelInfo.aliasName"
              ></Input>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">试剂盒类型</span>
              <Select
                @on-change="changeGene"
                class="item-select"
                v-model.trim="panelInfo.panelType"
                :disabled="disabled"
              >
                <Option :value="1">STR</Option>
                <Option :value="2">Y-STR</Option>
              </Select>
            </Col>
            <Col span="6" class="item-col">
              <span class="item-label">编号</span>
              <Input
                type="text"
                placeholder="请输入试剂盒别名"
                class="item-input"
              ></Input>
            </Col>
          </Row>
          <!-- <Row class="item-row">
            <Col span="24" class="item-col">
              <span class="item-label">试剂盒描述</span>
              <Input type="textarea" :row="2" style="width:90%"></Input>
            </Col>
          </Row> -->
        </div>
        <div class="result-part">
          <div class="title">
            查询结果
            <button class="btn btn-blue-bg" @click="handaddGene">
              添加基因座
            </button>
          </div>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
          ></Table>
        </div>
        <div class="saveBtn">
          <div>
            <button class="btn btn-blue-bg" @click="handSavebtn">保 存</button>
            <button class="btn btn-blue-border" @click="goBack">返 回</button>
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
      id: 0,
      SidebarParams: {
        open: ['5'],
        active: '5-3'
      },
      title: '',
      panelInfo: {
        panelType: 1 // 默认str
      },
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          // className: 'blue-font',
          key: 'locusName',
          align: 'center',
          render: (h, params) => {
            // console.log(this.tableData[params.index].locusName)
            return h('div', [
              h(
                'Select',
                {
                  props: {
                    filterable: true,
                    transfer: true,
                    placeholder: '请选择基因座',
                    size: 'small',
                    value: this.tableData[params.index].locusName
                  },
                  style: {
                    display: 'inline-block',
                    width: '100%',
                    marginRight: '6px'
                  },
                  on: {
                    'on-change': value => {
                      // console.log(value)
                      for (const i of this.tableData) {
                        if (value === i.locusName) {
                          // console.log(i.locusName)
                          this.$Modal.warning({
                            title: '提示',
                            content: '当前基因座已存在，请重新选择！'
                          })
                          return false
                        }
                      }
                      this.tableData[params.index].locusName = value
                      for (const item of this.tableData) {
                        for (const list of this.geneLists) {
                          if (item.locusName === list.locusName) {
                            item.locusId = list.id
                          }
                        }
                      }
                      // var s = new Set()
                      // this.tableData.forEach(i => s.add(i.locusName))
                      // console.log(s, '12313123123')
                      // console.log(this.tableData, '------------tab')
                      // if (this.tableData.length !== s.size) {
                      //   this.$Modal.error({
                      //     title: '抱歉',
                      //     content: '当前基因座已存在，请重新选择！'
                      //   })
                      //   // this.$Message.error('当前基因座已存在，请重新选择！')
                      //   return false
                      // }
                      // const arr = []
                      // for (const item of this.tableData) {
                      //   arr.push({ name: item.locusName })
                      // }
                      // for (const list of this.geneLists) {
                      //   for (const arry of arr) {
                      //     if (arry.name === list.locusName) {
                      //       console.log('进入判断1')
                      //       list.isDisabled = true
                      //     } else {
                      //       console.log('进入判断2')
                      //       list.isDisabled = false
                      //     }
                      //   }
                      // }
                      // const arrd = []
                      // this.tableData.forEach(item => {
                      //   arrd.push(item.geneVal)
                      // })
                      // console.log(arrd, this.tableData, 'arrd')
                      // this.geneLists.forEach(item => {
                      //   if (arrd.indexOf(item.locusName) !== -1) {
                      //     item.isDisabled = true
                      //     console.log(item, '说明已经有选择了的了')
                      //   } else {
                      //     console.log(item, '说明这个还没选')
                      //     item.isDisabled = false
                      //   }
                      // })
                    }
                  }
                },
                this.geneLists.map(obj => {
                  return h('Option', {
                    props: {
                      id: obj.id,
                      value: obj.locusName,
                      label: obj.locusName,
                      disabled: obj.isDisabled
                    }
                  })
                })
              )
            ])
          }
        },
        {
          title: '基因座顺序',
          key: 'locusOrd',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Input', {
                style: {
                  marginRight: '10px',
                  width: '80px'
                },
                props: {
                  type: 'text',
                  // placeholder: '请输入样本编号'
                  value: params.row.locusOrd // 使用key的键值
                },
                on: {
                  'on-change': event => {
                    this.tableData[params.index].locusOrd = event.target.value
                  }
                }
              })
            ])
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 80,
          render: (h, params) => {
            return h('span', [
              h(
                'span',
                {
                  class: 'red-font',
                  style: {
                    cursor: 'pointer',
                    marginLeft: '10px'
                  },
                  on: {
                    click: () => {
                      this.handDelete(params)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      geneLists: [],
      strLocusInfo: [],
      ystrLocusInfo: [],
      disabled: false
    }
  },
  computed: {},
  created() {
    this.id = this.$route.query.id
    this.getGene()
    if (this.id) {
      this.handInitData()
      this.disabled = true
    }
    // console.log(this.panelInfo.panelType)
  },
  mounted() {
    // console.log(this.panelInfo.panelType)
    if (this.panelInfo.panelType === 1) {
      // console.log('进入str判断')
      this.geneLists = this.strLocusInfo
    } else {
      // console.log('进入ystr判断')
      this.geneLists = this.ystrLocusInfo
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 删除基因座
    handDelete(params) {
      this.$Modal.confirm({
        title: '提醒',
        content: '确定要删除吗?此操作不可逆',
        okText: '确定',
        cancelText: '我再想想',
        onOk: () => {
          if (params.row.flag) {
            this.$Message.success('删除成功！')
            this.tableData.splice(params.index, 1)
          } else {
            this.$axios
              .get('database/panel/deletePanelLoucsById', {
                params: { id: params.row.id }
              })
              .then(res => {
                this.$Message.success('删除成功！')
                this.handInitData()
              })
          }
        }
      })
    },
    // 添加基因座
    handaddGene() {
      this.tableData.push({ locusName: '', locusOrd: this.tableData.length + 1, flag: true })
      // console.log(this.tableData)
    },
    changePage() {},
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('database/panel/panelLocusInfoQuery', {
          params: { dnaPanelId: this.id }
        })
        .then(res => {
          console.log(res, '进入方法')
          if (res.code === 1) {
            this.tableData = res.result.locusInfoList
            this.tableData.forEach(item => {
              item.flag = false
            })
            res.result.panelInfo.panelType.toString()
            this.panelInfo = res.result.panelInfo
            this.getGene()
          }
        })
    },
    // 保存按钮
    handSavebtn() {
      if (!this.panelInfo.panelName || !this.panelInfo.aliasName) {
        this.$Modal.warning({
          title: '提示',
          content: '试剂盒名称或试剂盒别名不能为空！'
        })
      } else {
        const panelInfoModel = {
          panelInfo: this.panelInfo,
          locusInfoList: this.tableData
        }
        this.$axios
          .post('database/panel/savePanelLoucsInfoQuery', panelInfoModel)
          .then(res => {
            // console.log(res)
            if (res.code === 1) {
              this.$Message.success('保存成功！')
            }
          })
      }
    },
    // 获取str/ytrs基因座
    getGene() {
      this.$axios.get('database/panel/selectAllStrLocusInfo').then(res => {
        // this.geneLists = res.result.strLocusInfo
        this.strLocusInfo = res.result.strLocusInfo
        this.ystrLocusInfo = res.result.ystrLocusInfo
        if (this.panelInfo.panelType === 1) {
          // console.log('进入str判断')
          this.geneLists = this.strLocusInfo
        } else {
          // console.log('进入ystr判断')
          this.geneLists = this.ystrLocusInfo
          // console.log(this.ystrLocusInfo, '12312')
        }
      })
    },
    changeGene(val) {
      // console.log(val)
      this.geneLists = []
      if (val === 1) {
        this.geneLists = this.strLocusInfo
      } else {
        this.geneLists = this.ystrLocusInfo
      }
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
