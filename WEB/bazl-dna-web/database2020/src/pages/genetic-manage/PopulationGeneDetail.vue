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
            <router-link to="/geneticmanage/PopulationGene"
              >种群基因频率管理</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/PopulationGeneDetail"
              >查看详情</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            基因频率方案
          </div>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">方案编号</span>
              <Input
                v-model="titleInfo.populationNo"
                type="text"
                class="item-input width-255px"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">方案名称</span>
              <Input
                v-model="titleInfo.populationName"
                type="text"
                class="item-input width-255px"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">方案类型</span>
              <Select
                class="item-select width-255px"
                v-model="titleInfo.geneType"
              >
                <Option value="1">STR</Option>
              </Select>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">统计国家</span>
              <Select
                placeholder="请选择国家"
                style="display:inline-block;"
                v-model="titleInfo.statsCountry"
                class="item-input width-255px"
              >
                <Option
                  v-for="item in personNationList"
                  :key="item.id"
                  :value="item.dictName"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">统计地区</span>
              <Input
                v-model="titleInfo.statsArea"
                type="text"
                class="item-input width-255px"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">统计民族</span>
              <Select
                placeholder="请选择民族"
                style="display:inline-block;"
                class="item-input width-255px"
                v-model="titleInfo.statsRace"
              >
                <Option
                  v-for="item in personRaceList"
                  :key="item.id"
                  :value="item.dictName"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">数据来源</span>
              <Input
                v-model="titleInfo.dataSource"
                type="text"
                class="item-input width-255px"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">统计时间</span>
              <DatePicker
                class="width-255px"
                type="date"
                placeholder="请选择统计时间"
                v-model="titleInfo.createDatetime"
              ></DatePicker>
              <!-- <Input
                v-model="titleInfo.createDatetime"
                type="text"
                class="item-input"
              ></Input> -->
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">录入时间</span>
              <DatePicker
                class="width-255px"
                type="date"
                placeholder="请选择录入时间"
                v-model="titleInfo.updateDatetime"
              ></DatePicker>
              <!-- <Input
                v-model="titleInfo.updateDatetime"
                type="text"
                class="item-input"
              ></Input> -->
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">基因座数目</span>
              <!-- v-model="titleInfo.locusCount" -->
              <Input
                type="number"
                class="item-input width-255px"
                disabled
                :value="numfun"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">累计个人识别能力</span>
              <Input
                v-model="titleInfo.cdpVal"
                type="number"
                class="item-input width-255px"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">累计非父排除概率</span>
              <Input
                v-model="titleInfo.cpe"
                type="number"
                class="item-input width-255px"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">方面描述</span>
              <Input
                v-model="titleInfo.populationDesc"
                type="textarea"
                :row="2"
                class="width-255px"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">备注信息</span>
              <Input
                v-model="titleInfo.remarks"
                type="textarea"
                :row="2"
                class="width-255px"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row"> </Row>
        </div>
        <div class="result-part">
          <div class="title">
            基因座一览
            <button class="btn btn-blue-bg" @click="handEditParams(1)">
              添加
            </button>
          </div>
          <Table
            size="small"
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table poptab tabscllor"
          ></Table>
          <!-- <div class="page">
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
        </div> -->
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click="handAllSave">保 存</button>
            <button class="btn btn-blue-border" @click="goBack">返 回</button>
          </Row>
        </div>
      </Col>
    </Row>
    <Modal
      v-model="geneInfoModal"
      width="800"
      class="bazl-modal rest-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="title blue-font">
        基因座名称: ( {{ geneName }} )
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="geneInfoModal = false"
        />
        <button
          class="btn btn-blue-bg"
          style="margin:0px 5px 0px 25px;height:30px;line-height:25px"
          @click="handAddGene"
        >
          添加
        </button>
        <span style="font-size:12px;color:red"
          >注：(添加的基因座名称必须统一)</span
        >
      </div>
      <Table
        border
        :columns="modalCol"
        :data="modalData"
        class="bazl-table"
      ></Table>
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="handModelSave">保 存</button>
        <button class="btn btn-blue-border" @click="geneInfoModal = false">
          关 闭
        </button>
      </Row>
    </Modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      titleInfo: {
        geneType: '1'
      },
      SidebarParams: {
        open: ['5'],
        active: '5-4'
      },
      geneName: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座名称',
          key: 'locusName',
          align: 'center'
        },
        {
          title: '位点个数',
          key: 'locusCount',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h('span', [
              h(
                'span',
                {
                  class: 'blue-font',
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.geneInfoModal = true
                      this.handEditParams(2, params)
                    }
                  }
                },
                '编辑'
              ),
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
                      this.handDeleteTab(params)
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
      geneInfoModal: false,
      geneConModal: false,
      modalCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 50
        },
        {
          title: '基因座名称',
          key: 'locusName',
          align: 'center',
          render: (h, params) => {
            // console.log(params.row.locusName)
            return h('div', [
              h(
                'Select',
                {
                  props: {
                    transfer: true,
                    placeholder: '请选择基因座',
                    size: 'small',
                    value: params.row.locusName
                  },
                  style: {
                    display: 'inline-block',
                    width: '100%',
                    marginRight: '6px'
                  },
                  on: {
                    'on-change': value => {
                      // console.log(value)
                      // if (this.modalData.length !== 0) {

                      // }
                      for (const i of this.tableData) {
                        if (i.locusName !== this.modalData[0].locusName) {
                          if (value === i.locusName) {
                            this.$Modal.warning({
                              title: '提示',
                              content: '当前基因座已存在，请重新选择！'
                            })
                            return false
                          }
                        }
                      }
                      // this.modalData[params.index].locusName = value
                      this.geneName = value
                      for (const item of this.modalData) {
                        item.locusName = value
                        for (const list of this.geneLists) {
                          if (item.locusName === list.locusName) {
                            item.locusId = list.id
                          }
                        }
                      }
                    }
                  }
                },
                this.geneLists.map(obj => {
                  return h('Option', {
                    props: {
                      id: obj.id,
                      value: obj.locusName,
                      label: obj.locusName
                      // disabled: obj.isDisabled
                    }
                  })
                })
              )
            ])
          }
        },
        {
          title: '频率',
          key: 'alleleValue',
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
                  value: params.row.alleleValue // 使用key的键值
                },
                on: {
                  'on-change': event => {
                    this.modalData[params.index].alleleValue =
                      event.target.value
                  }
                }
              })
            ])
          }
        },
        {
          title: '标准误差',
          key: 'probability',
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
                  value: params.row.probability // 使用key的键值
                },
                on: {
                  'on-change': event => {
                    this.modalData[params.index].probability =
                      event.target.value
                  }
                }
              })
            ])
          }
        },
        {
          title: '操作',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'red-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: () => {
                    this.handDelete(params)
                  }
                }
              },
              '删除'
            )
          }
        }
      ],
      modalData: [],
      id: '',
      personRaceList: [], // 民族
      personNationList: [], // 国家
      populaId: '',
      status: 1, // 保存基因信息 状态 1/添加 2/编辑
      tabIndex: 0 // 表格当前位置
    }
  },
  created() {
    // 民族类型
    if (sessionStorage.getItem('personRaceList')) {
      const arr = sessionStorage.getItem('personRaceList')
      this.personRaceList = JSON.parse(arr)
    }
    // 国家
    if (sessionStorage.getItem('personNationList')) {
      const arr = sessionStorage.getItem('personNationList')
      this.personNationList = JSON.parse(arr)
    }
  },
  mounted() {
    this.id = this.$route.query.id
    if (this.id) {
      this.handInitData()
    }
    this.handGetStrGene()
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    numfun() {
      return this.tableData.length
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 统计时间
    changecreateDatetime(val) {
      // console.log(val)
      this.titleInfo.createDatetime = val
    },
    // 录入时间
    changeupdateDatetime(val) {
      // console.log(val)
      this.titleInfo.updateDatetime = val
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
            this.modalData.splice(params.index, 1)
          } else {
            this.$axios
              .get('/database/frequency/deleteAlleleFrequencyById', {
                params: { alleleFrequencyId: params.row.id }
              })
              .then(res => {
                this.$Message.success('删除成功！')
                this.modalData.splice(params.index, 1)
              })
          }
        }
      })
    },
    // 删除
    handDeleteTab(params) {
      this.$Modal.confirm({
        title: '提醒',
        content: '确定要删除吗?此操作不可逆',
        okText: '确定',
        cancelText: '我再想想',
        onOk: () => {
          if (params.row.tabFlag) {
            this.tableData.splice(params.index, 1)
            this.tableData = [...this.tableData]
          } else {
            const info = {
              populationFrequencyId: this.populaId,
              locusName: params.row.locusName
            }
            this.$axios
              .post('/database/frequency/deleteAlleleFrequency', info)
              .then(res => {
                this.$Message.success('删除成功！')
                this.handInitData()
              })
          }
        }
      })
    },
    changePage() {},
    // addGeneInfo() {
    //   this.geneInfoModal = true
    // },
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('database/frequency/listAlleleFrequency', {
          params: { populationId: this.id }
        })
        .then(res => {
          // console.log(res)
          if (res.code === 1) {
            this.tableData = res.result.alleleFrequencyInfo
            for (const item of this.tableData) {
              item.tabFlag = false
            }
            // console.log(this.tableData.length)
            this.titleInfo = res.result.populationFrequencyInfo[0]
            this.populaId = res.result.populationFrequencyInfo[0].id
          }
        })
    },
    // 添加基因座
    handAddGene() {
      const obj = {
        alleleValue: '',
        probability: '',
        flag: true
      }
      if (this.modalData.length !== 0) {
        // console.log(this.modalData)
        obj.locusName = this.modalData[0].locusName
      } else {
        obj.locusName = ''
      }
      this.modalData.push(obj)
    },
    // 编辑
    handEditParams(type, params) {
      // console.log(type)
      if (type === 1) {
        this.modalData = []
        this.geneName = null
        this.status = 1
        // this.geneName = this.modalData[0].locusName
      } else {
        this.status = 2
        this.tabIndex = params.index
        this.modalData = params.row.alleleFrequencyInfoList
        for (const item of this.modalData) {
          item.flag = false
        }
        this.geneName = params.row.alleleFrequencyInfoList[0].locusName
      }
      this.geneInfoModal = true
    },
    // STR基因座
    handGetStrGene() {
      this.$axios.get('database/frequency/selectAllStrLocusInfo').then(res => {
        // console.log(res, '基因座')
        this.geneLists = res.result
        // console.log(this.geneLists)
      })
    },
    // 添加/编辑弹窗 保存按钮
    handModelSave() {
      // console.log(this.status)
      // console.log(this.modalData, '进入操作')
      if (this.status === 1) {
        const obj = {}
        obj.locusName = this.modalData[0]
        obj.locusCount = console.log(this.modalData[0].locusName, '------')
        this.tableData.push({
          locusName: this.modalData[0].locusName,
          locusCount: this.modalData.length,
          alleleFrequencyInfoList: this.modalData,
          tabFlag: true
        })
      } else {
        this.tableData[this.tabIndex].locusName = this.modalData[0].locusName
        this.tableData[this.tabIndex].locusCount = this.modalData.length
        this.tableData[this.tabIndex].alleleFrequencyInfoList = this.modalData
      }
      this.geneInfoModal = false
    },
    // 整体信息保存
    handAllSave() {
      this.titleInfo.locusCount = this.tableData.length
      const info = {
        populationFrequencyInfo: this.titleInfo,
        alleleFrequencyInfoList: this.tableData
      }
      this.$axios.post('database/frequency/saveFrequency', info).then(res => {
        // console.log(res, '保存操作000000009999')
        this.$Message.success('保存成功！')
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/genemanage.less';
.item-label {
  width: 120px !important;
}
.item-input,
.item-select {
  // width: 180px !important;
}
.rest-modal .title {
  height: 36px;
  line-height: 36px;
  border-bottom: 1px solid #3086c1;
  padding: 0 15px;
}
</style>
