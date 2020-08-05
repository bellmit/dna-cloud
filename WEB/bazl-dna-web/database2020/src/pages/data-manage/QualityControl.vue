<template>
  <div>
    <Row>
      <!-- <Col span="4">
        <SideBar :SidebarParams="SidebarParams"></SideBar>
      </Col> -->
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControl"
              >质控数据管理</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">查询条件设置</div>
          <div class="item-title" style="border-top:none;">
            添加信息
          </div>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">质控样本类别</span>
              <span class="item-group">
                <Select
                  placeholder="请选择质控样本类别"
                  style="width:260px;"
                  v-model="searchForm.qcSampleType"
                >
                  <Option
                    v-for="item in qcSampleTypeList"
                    :key="item.id"
                    :value="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">样本编号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcSampleNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入样本编号"
                style="width:140px;"
                v-model="searchForm.qcSampleNo"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">样本名称</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcSampleNameCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入样本名称"
                style="width:140px;"
                v-model="searchForm.qcSampleName"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">质控人员类别</span>
              <span class="item-group">
                <Select
                  placeholder="请选择质控人员类别"
                  style="width:260px;"
                  v-model="searchForm.qcPersonType"
                >
                  <Option
                    v-for="item in qcPersonTypeList"
                    :key="item.id"
                    :value="item.dictCode"
                    >{{ item.dictName }}</Option
                  >
                </Select>
              </span>
            </Col>
            <Col span="10">
              <span class="item-label">性别</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcPersonGender">
                  <Radio label="1">男性</Radio>
                  <Radio label="2">女性</Radio>
                </RadioGroup>
              </span>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">姓名</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcPersonNameCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入姓名"
                style="width:140px;"
                v-model="searchForm.qcPersonName"
              ></Input>
            </Col>
            <Col span="10">
              <span class="item-label">身份证号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcPersonIdcardNoCondition">
                  <Radio label="0">包含</Radio>
                  <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input
                type="text"
                placeholder="请输入身份证号"
                style="width:140px;"
                v-model="searchForm.qcPersonIdcardNo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">所属单位名称</span>
              <Input
                type="text"
                placeholder="请输入所属单位名称"
                style="width:260px;"
                v-model="searchForm.qcPersonOrgName"
              ></Input>
              <!-- <span class="item-group">
                 <Cascader
                :data="selectByOrgInfo"
                style="width:260px;"
                v-model="orgidList"
              ></Cascader>
              </span> -->
            </Col>
          </Row>
          <Row class="tc">
            <button class="btn btn-blue-bg" @click.prevent="getSelectList">
              执行查询
            </button>
            <button
              class="btn btn-red-line"
              style="margin-left:10px"
              @click.prevent="handleReset"
            >
              全部清空
            </button>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">
            查询结果
            <button
              class="btn btn-blue-bg"
              style="margin-left:15px;"
              @click.prevent="addSample(null)"
            >
              添加
            </button>
          </div>
          <Table
            border
            :columns="searchResultCol"
            :data="searchResultData"
            class="bazl-table"
            size="small"
          ></Table>
          <div class="page">
            <span>
              当前第{{ currentPage }}页/共{{ pageCount }}页/共{{
                allRecordCount
              }}条信息
            </span>
            <Page
              :total="allRecordCount"
              show-elevator
              prev-text="上一页"
              next-text="下一页"
              class-name="bazl_page"
              :current="currentPage"
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
      SidebarParams: {
        open: ['2'],
        active: '2-3'
      },
      qcSampleTypeList: [],
      qcPersonTypeList: [],
      searchForm: {
      },
      sampleForm: {},
      addEditSample: false,
      modalTitle: '',
      panelList: [],
      genelist: [],
      importCodisModal: false,
      fileName: '',
      currentid: 0,
      codisCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '样本编号',
          key: 'sampleNo',
          align: 'center'
        },
        {
          title: '位点个数',
          key: 'locusCount',
          align: 'center'
        },
        {
          title: '选择',
          key: 'chose',
          width: 70,
          align: 'center',
          render: (h, params) => {
            const id = params.row.sampleNo
            let flag = false
            if (this.currentid === id) {
              flag = true
            } else {
              flag = false
            }
            const self = this
            return h('div', [
              h('Radio', {
                props: {
                  value: flag
                },
                on: {
                  'on-change': () => {
                    const arr = this.codisData
                    self.currentid = id
                    const chooseArr = arr.find(x => x.sampleNo === id)
                    // console.log(chooseArr)
                    this.chooseCodisData = chooseArr.geneInfo
                    // this.choosePersonData.push(arr.find(x => x.personId ==this.choosePersonId ))
                    // console.log(self.currentid,params.row.personId)
                  }
                }
              })
            ])
          }
        }
      ],
      codisData: [],
      chooseCodisData: [],
      searchResultCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: '50'
        },
        {
          title: '实验室',
          key: 'labServerName',
          align: 'center'
        },
        {
          title: '质控样本类型',
          key: 'qcSampleTypeName',
          align: 'center'
        },
        {
          title: '质控样本名称',
          key: 'qcSampleName',
          align: 'center'
        },
        {
          title: '质控样本编号',
          key: 'qcSampleName',
          align: 'center'
        },
        {
          title: '人员类型',
          key: 'qcPersonTypeName',
          align: 'center'
        },
        {
          title: '人员姓名',
          key: 'qcPersonName',
          align: 'center',
          width: '60'
        },
        {
          title: '性别',
          key: 'qcPersonGenderName',
          align: 'center',
          width: '60'
        },
        {
          title: '身份证号',
          key: 'qcPersonIdcardNo',
          align: 'center'
        },
        {
          title: '单位名称',
          key: 'qcPersonOrgName',
          align: 'center'
        },
        {
          title: '试剂盒名称',
          key: 'strPanelName',
          align: 'center'
        },
        {
          title: '污染记录',
          key: '',
          align: 'center',
          width: '100',
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                { class: 'hover-text', style: { marginLeft: '10px' } },
                [
                  h('Icon', {
                    props: {
                      type: 'md-list-box',
                      size: '20',
                      color: '#00439E',
                      marginLeft: '20px'
                    }
                  }),
                  h(
                    'strong',
                    {
                      class: 'text',
                      style: {
                        color: '#00439E',
                        display: 'none'
                      },
                      on: {
                        click: () => {
                          // console.log(params.row)
                          this.$router.push({
                            path: '/datamanage/QualityControlPollutionRecord',
                            query: {
                              id: params.row.id
                            }
                          })
                        }
                      }
                    },
                    '查看污染记录'
                  )
                ]
              )
            ])
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          // className: 'blue-font',
          render: (h, params) => {
            return h('div', [
              h('span', { class: 'hover-text' }, [
                h('Icon', {
                  class: 'look_eye',
                  props: {
                    type: 'ios-paper',
                    size: '20',
                    color: '#2396D6'
                  }
                }),
                h(
                  'strong',
                  {
                    class: 'text',
                    style: {
                      color: '#2396D6',
                      display: 'none'
                    },
                    on: {
                      click: () => {
                        this.addSample(params.row.id)
                      }
                    }
                  },
                  '编辑'
                )
              ]),
              h(
                'span',
                { class: 'hover-text', style: { marginLeft: '10px' } },
                [
                  h('Icon', {
                    props: {
                      type: 'ios-trash',
                      size: '24',
                      color: '#D1635A',
                      marginLeft: '20px'
                    }
                  }),
                  h(
                    'strong',
                    {
                      class: 'text',
                      style: {
                        color: '#D1635A',
                        display: 'none'
                      },
                      on: {
                        click: () => {
                          // console.log(params, '-----')
                          this.$Modal.confirm({
                            title: '提醒',
                            content: '确定要删除吗?此操作不可逆',
                            okText: '确定',
                            cancelText: '我再想想',
                            onOk: () => {
                              this.$axios
                                .get('/database/qc/deleteById', {
                                  params: {
                                    id: params.row.id
                                  }
                                })
                                .then(res => {
                                  // console.log(res)
                                  if (res.result) {
                                    this.$Modal.success({
                                      title: '恭喜',
                                      content: '删除成功~'
                                    })
                                    this.getSelectList()
                                  } else {
                                    this.$Modal.error({
                                      title: '抱歉',
                                      content: '删除失败~'
                                    })
                                  }
                                })
                            }
                          })
                        }
                      }
                    },
                    '删除'
                  )
                ]
              )
            ])
          }
        }
      ],
      searchResultData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 0,
      selectByOrgInfo: [],
      orgidList: []
    }
  },
  created() {
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
    sessionStorage.setItem('caseDataOpen', '1')
    if (sessionStorage.getItem('qcSampleTypeList')) {
      // 质控样本类型
      const arr = sessionStorage.getItem('qcSampleTypeList')
      this.qcSampleTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcPersonTypeList')) {
      // 案件性质
      const arr = sessionStorage.getItem('qcPersonTypeList')
      this.qcPersonTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personTypeQuery')) {
      // 人员类型
      const arr = sessionStorage.getItem('personTypeQuery')
      this.personTypeQuery = JSON.parse(arr)
    }
  },
  methods: {
    // 重置查询条件
    handleReset() {
      // Object.keys(this.searchForm).forEach(key => {
      //   this.searchForm[key] = ''
      // })
      this.searchForm = {}
    },
    // 添加质控样本
    addSample(id) {
      // console.log(id, '7273273273')
      this.$router.push({
        path: '/datamanage/addQtyCtrlSam',
        query: {
          id: id
        }
      })
    },
    // 执行查询 获取列表数据
    getSelectList() {
      this.searchForm.pageIndex = this.currentPage
      this.searchForm.rows = 15
      this.$axios.post('/database/qc/selectList', this.searchForm).then(res => {
        if (res.code === 1) {
          // console.log(res)
          this.searchResultData = res.result.qcSampleInfoList
          this.allRecordCount = res.result.pageInfo.allRecordCount
          // this.currentPage = res.result.pageInfo.curPage
          this.pageCount = res.result.pageInfo.pageCount
        }
      })
    },
    changePage(index) {
      this.currentPage = index
      this.getSelectList()
    }
  },
  beforeRouteEnter(to, from, next) {
    if (
      from.path === '/datamanage/QualityControlPollutionRecord' ||
      from.path === '/datamanage/addQtyCtrlSam' ||
      from.path === '/datamanage/qtyCtrlDetails'
    ) {
      // console.log('进入判断')
      to.meta.keepAlive = true
      to.meta.isBack = true
    } else {
      to.meta.isBack = false
      to.meta.keepAlive = true
    }
    next()
  },
  activated() {
    if (!this.$route.meta.isBack) {
      this.searchResultData = []
      this.currentPage = 1
      this.pageCount = 1
      this.allRecordCount = 0
      this.handleReset()
    } else {
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamanage';
.gene-part {
  border: 1px dashed #3086c1;
  border-radius: 6px;
  margin: 15px;
  padding: 10px 15px;
  padding-bottom: 25px;
  text-align: center;
  .gene-col {
    margin-top: 8px;
  }
  .gene-label {
    min-width: 60px;
    display: inline-block;
  }
}
.gene-part .gene-row .gene-col .gene-input {
  width: 60px;
}
.codis-modal {
  .item-row {
    padding: 8px 15px;
    .item-label {
      display: inline-block;
      width: 80px;
      color: #333;
    }
    .item-input,
    .item-select {
      width: 220px;
    }
  }
}
</style>
