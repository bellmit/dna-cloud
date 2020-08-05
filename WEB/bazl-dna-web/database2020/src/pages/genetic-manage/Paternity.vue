<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>遗传学基因数据管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/Paternity"
              >亲权指数管理</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            设置条件
          </div>
          <Row class="item-row">
            <Col span="6" class="item-col">
              <span class="item-label">亲缘关系</span>
              <Select
                class="item-select"
                v-model="tabSwitchStatus"
                @on-change="handSelectSample"
                style="width:200px"
              >
                <Option value="1">父·母·子(三联体)</Option>
                <Option value="2">父/子(单亲)</Option>
                <Option value="3">母/子(单亲)</Option>
              </Select>
            </Col>
            <Col span="6" class="item-col">
              <span style="width:50px !important" class="item-label"
                >试剂盒</span
              >
              <Select
                class="item-select"
                style="width:200px"
                v-model="panelName"
                :label-in-value="true"
                placeholder="请选择试剂盒"
                @on-change="changePanel"
              >
                <Option
                  v-for="item in panelNameSTR"
                  :key="item.id"
                  :value="item.id"
                  >{{ item.panelName }}</Option
                >
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span style="width:120px !important" class="item-label"
                >基因频率计算方案</span
              >
              <Select
                class="item-select"
                style="width:200px"
                v-model="population"
              >
                <Option
                  v-for="item in populationQuery"
                  :key="item.id"
                  :value="item.id"
                  >{{ item.populationName }}</Option
                >
              </Select>
            </Col>
            <Col span="4" class="item-col">
              <button class="btn btn-blue-bg" @click="handCalculation">
                计 算
              </button>
              <button class="btn btn-blue-border" @click="handrest">
                重 置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <Row class="title">
            <Col span="24"
              >计算结果
              <div
                style="float:right;margin-right:5px;font-size:18px;color: #00439E;"
              >
                <span>累积亲权指数：</span>
                <span>{{ totalLR }}</span>
              </div>
            </Col>
          </Row>
          <Table
            border
            :columns="handleTabSwitch"
            :data="tableData"
            class="bazl-table"
            size="small"
          ></Table>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      totalLR: 0, // 累积似然率总数
      titleInfo: {},
      geneLists: [
        { name: 'asd1', code: '1' },
        { name: 'asd2', code: '2' },
        { name: 'asd3', code: '3' }
      ],
      tableCol0: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'title1',
          align: 'center',
          width: '100',
          Attributes: 'Mr',
          // className: 'tabStyle',
          render: (h, params) => {
            // // eslint-disable-next-line no-unused-vars
            let markSytel = ''
            // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.index !== 0) {
              text = params.row.title1
              if (params.row.flag === true) {
                markSytel = 'red'
              } else {
                markSytel = ''
              }
            } else {
              markSytel = ''
              text = ''
            }
            return h(
              'span',
              {
                prop: {},
                style: {
                  color: markSytel
                }
              },
              text
            )
          }
        },
        {
          title: '父亲样本',
          key: 'val1',
          align: 'center',
          // width: '250',
          // Attributes: 'Mr',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px'
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput1 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput1 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params)
                        this.numfunc = params.column._index
                        // console.log(this.numfunc, 'pamdsam_index')
                        this.handExtract(1, this.titleInfo.sampleInput1)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '母亲样本',
          key: 'val2',
          align: 'center',
          // width: '250',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px',
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput2 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput2 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params.index)
                        this.numfunc = params.column._index
                        this.handExtract(2, this.titleInfo.sampleInput2)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                // 钉子-1
                // params.row.value1.forEach(item => {
                //   console.log(item, ',,,,,,,,')
                // })
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '子女样本',
          key: 'val3',
          align: 'center',
          // width: '250',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px',
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput3 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput3 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params.index)
                        this.numfunc = params.column._index
                        this.handExtract(3, this.titleInfo.sampleInput3)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val3[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val3[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val3[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val3[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '亲权指数',
          key: 'LR',
          align: 'center',
          width: '100'
        }
      ],
      tableCol1: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'title1',
          align: 'center',
          width: '100',
          Attributes: 'Mr',
          // className: 'tabStyle',
          render: (h, params) => {
            // // eslint-disable-next-line no-unused-vars
            let markSytel = ''
            // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.index !== 0) {
              text = params.row.title1
              if (params.row.flag === true) {
                markSytel = 'red'
              } else {
                markSytel = ''
              }
            } else {
              markSytel = ''
              text = ''
            }
            return h(
              'span',
              {
                prop: {},
                style: {
                  color: markSytel
                }
              },
              text
            )
          }
        },
        {
          title: '父亲样本',
          key: 'val1',
          align: 'center',
          // width: '250',
          // Attributes: 'Mr',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px'
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput1 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput1 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params)
                        this.numfunc = params.column._index
                        // console.log(this.numfunc, 'pamdsam_index')
                        this.handExtract(1, this.titleInfo.sampleInput1)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '子女样本',
          key: 'val3',
          align: 'center',
          // width: '250',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px',
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput2 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput2 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params.index)
                        this.numfunc = params.column._index
                        this.handExtract(2, this.titleInfo.sampleInput2)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '亲权指数',
          key: 'LR',
          align: 'center',
          width: '100'
        }
      ],
      tableCol2: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'title1',
          align: 'center',
          width: '100',
          Attributes: 'Mr',
          // className: 'tabStyle',
          render: (h, params) => {
            // // eslint-disable-next-line no-unused-vars
            let markSytel = ''
            // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.index !== 0) {
              text = params.row.title1
              if (params.row.flag === true) {
                markSytel = 'red'
              } else {
                markSytel = ''
              }
            } else {
              markSytel = ''
              text = ''
            }
            return h(
              'span',
              {
                prop: {},
                style: {
                  color: markSytel
                }
              },
              text
            )
          }
        },
        {
          title: '母亲样本',
          key: 'val1',
          align: 'center',
          // width: '250',
          // Attributes: 'Mr',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px'
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput1 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput1 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params)
                        this.numfunc = params.column._index
                        // console.log(this.numfunc, 'pamdsam_index')
                        this.handExtract(1, this.titleInfo.sampleInput1)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val1[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val1[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '子女样本',
          key: 'val3',
          align: 'center',
          // width: '250',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    // marginLeft: '4px',
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.titleInfo.sampleInput2 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.titleInfo.sampleInput2 = event.target.value
                    }
                  }
                }),
                h(
                  'Button',
                  {
                    style: {
                      marginLeft: '6px'
                    },
                    props: {
                      type: 'primary'
                    },
                    on: {
                      click: () => {
                        // console.log(params.index)
                        this.numfunc = params.column._index
                        this.handExtract(2, this.titleInfo.sampleInput2)
                      }
                    }
                  },
                  '提取'
                )
              ])
            } else {
              return h('div', [
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '10px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[0] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[0] = event.target.value
                    }
                  }
                }),
                h('span', '/'),
                h('Input', {
                  class: 'tabInput',
                  style: {
                    marginRight: '15px',
                    width: '50px'
                  },
                  props: {
                    type: 'text',
                    // placeholder: '请输入样本编号'
                    value: params.row.val2[1] // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      this.tableData[params.index].val2[1] = event.target.value
                    }
                  }
                })
              ])
            }
          }
        },
        {
          title: '亲权指数',
          key: 'LR',
          align: 'center',
          width: '100'
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      arr: [],
      personRaceList: [], // 民族类型
      populationQuery: [], // 种群下拉框
      panelName1: '',
      panelName2: '',
      panelName3: '',
      numfunc: '',
      sampleInput1: '', // 表格输入框值
      sampleInput2: '',
      sampleInput3: '',
      tabSwitchStatus: '1', // 表格切換状态码 样本关系
      population: 1, // 种群下拉框 参数 v-model
      extractGeneList: [], // 提取 基因信息
      panelName: '', // 试剂盒名称
      flag: false,
      panelval: null
    }
  },
  created() {
    if (sessionStorage.getItem('populationQuery')) {
      // 民族类型
      const arr = sessionStorage.getItem('populationQuery')
      this.populationQuery = JSON.parse(arr)
    }
    if (sessionStorage.getItem('personRaceList')) {
      // 民族类型
      const arr = sessionStorage.getItem('personRaceList')
      this.personRaceList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('panelNameSTR')) {
      //
      const arr = sessionStorage.getItem('panelNameSTR')
      this.panelNameSTR = JSON.parse(arr)
    }
  },
  mounted() {
    this.handInitData()
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    handleTabSwitch() {
      if (this.tabSwitchStatus === '1') {
        return this.tableCol0
      } else if (this.tabSwitchStatus === '2') {
        return this.tableCol1
      } else {
        return this.tableCol2
      }
    }
  },
  watch: {
    flag(val, oldVal) {
      // 普通的watch监听
      // console.log(val, 'watch----')
      // console.log('新知-----', val, oldVal)
      if (val) {
        if (this.panelval) {
          this.handpanel(this.panelval)
        }
      }
    }
    // deep: true
    // immediate: true
  },
  methods: {
    // 重置页面数据
    handrest() {
      // console.log(this.tableData, '清空---')
      this.panelName = ''
      this.titleInfo = {}
      this.handInitData()
      this.tableData = [...this.tableData]
    },
    handleTab(index) {
      this.active = index
    },
    // 页面初始数据
    handInitData() {
      // {params: { geneType: 2 }}
      this.$axios.get('database/likeLiHood/queryStrLocusInfo', {}).then(res => {
        // console.log(res, '初始基因座')
        sessionStorage.setItem('arrA', JSON.stringify(res.result))
        const arr = []
        for (const item of res.result) {
          const obj = {
            title1: item.locusName,
            val1: [],
            val2: [],
            val3: []
          }
          arr.push(obj)
        }
        // 在数组前插入一条数
        arr.unshift({})
        this.tableData = arr
        // console.log(this.tableData, '初始表格')
        this.flag = true
      })
    },
    // 计算按钮
    handCalculation() {
      console.log(this.tableData)
      if (!this.population) {
        this.$Message.error('请先选择基因频率计算方案！')
        return false
      } else {
        const LikeLiHoodCalcModel = {
          populationFrequencyId: this.population,
          sampleRelation: this.tabSwitchStatus,
          strFatherGeneInfo: {
            geneInfo: {}
          },
          strMotherGeneInfo: {
            geneInfo: {}
          },
          strChildGeneInfo: {
            geneInfo: {}
          }
        }
        const jsonStr1 = {}
        const jsonStr2 = {}
        const jsonStr3 = {}
        for (const item of this.tableData) {
          if (item !== this.tableData[0]) {
            jsonStr1[item.title1] = item.val1.join('/')
            jsonStr2[item.title1] = item.val2.join('/')
            jsonStr3[item.title1] = item.val3.join('/')
          }
        }
        if (this.tabSwitchStatus === '1') {
          LikeLiHoodCalcModel.strFatherGeneInfo.geneInfo = JSON.stringify(
            jsonStr1
          )
          LikeLiHoodCalcModel.strMotherGeneInfo.geneInfo = JSON.stringify(
            jsonStr2
          )
          LikeLiHoodCalcModel.strChildGeneInfo.geneInfo = JSON.stringify(
            jsonStr3
          )
        } else if (this.tabSwitchStatus === '2') {
          LikeLiHoodCalcModel.strFatherGeneInfo.geneInfo = JSON.stringify(
            jsonStr1
          )
          LikeLiHoodCalcModel.strMotherGeneInfo.geneInfo = null
          LikeLiHoodCalcModel.strChildGeneInfo.geneInfo = JSON.stringify(
            jsonStr2
          )
        } else {
          LikeLiHoodCalcModel.strFatherGeneInfo.geneInfo = null
          LikeLiHoodCalcModel.strMotherGeneInfo.geneInfo = JSON.stringify(
            jsonStr1
          )
          LikeLiHoodCalcModel.strChildGeneInfo.geneInfo = JSON.stringify(
            jsonStr2
          )
        }
        console.log(LikeLiHoodCalcModel)
        this.$axios
          .post('/database/likeLiHood/parentalIndex', LikeLiHoodCalcModel)
          .then(res => {
            const arr = JSON.parse(res.result).alleleList
            this.totalLR = JSON.parse(res.result).totalPI
            console.log(arr, '计算似然比')
            for (const item of this.tableData) {
              for (const data of arr) {
                if (item !== this.tableData[0]) {
                  if (item.title1 === data.alleleKey) {
                    console.log('进入赋值判断11')
                    item.LR = data.PI
                  }
                }
              }
            }
            this.tableData = [...this.tableData]
          })
      }
    },
    // 提取按钮
    handExtract(type, sampNo) {
      if (sampNo) {
        this.$axios
          .get('database/likeLiHood/queryGeneInfo', {
            params: { sampleNo: sampNo, geneType: 1 }
          })
          .then(res => {
            console.log(res, '查看请求数据顺序')
            this.extractGeneList = []
            for (const item of JSON.parse(res.result.geneInfo)) {
              const obj = {
                locusName: item.name,
                value: item.value
              }
              this.extractGeneList.push(obj)
            }
            this.tableData.forEach(i => {
              this.extractGeneList.forEach(k => {
                if (i !== this.tableData[0]) {
                  if (i.title1 === k.locusName) {
                    if (type === 1) {
                      i.val1 = k.value
                    } else if (type === 2) {
                      i.val2 = k.value
                    } else {
                      i.val3 = k.value
                    }
                  }
                }
              })
            })
            this.tableData = [...this.tableData]
          })
      } else {
        this.$Message.error('请先输入样本编号！')
      }
    },
    // 试剂盒下拉框 监听事件
    changePanel(val) {
      // console.log(val, 'xialakaung--123123123123')
      if (val) {
        this.flag = false
        this.panelName = val.value
        this.panelval = val
        this.totalLR = 0
        this.handInitData()
        this.tableData = [...this.tableData]
      }
    },
    handpanel(val) {
      // console.log('进入排序操作111')
      this.$axios
        .get('database/panel/locusListByPanel', {
          params: {
            panelId: val.value
          }
        })
        .then(res => {
          // console.log(res, '试剂盒')
          this.extractGeneList = []
          for (const item of res.result) {
            const obj = {
              locusName: item.locusName,
              value: []
            }
            this.extractGeneList.push(obj)
          }
          // eslint-disable-next-line no-undef
          const sesArr = JSON.parse(sessionStorage.getItem('arrA'))
          //  eslint-disable-next-line prefer-const
          let index = []
          // eslint-disable-next-line no-unused-vars
          let newArr = []
          // eslint-disable-next-line prefer-const
          let textArr = []
          for (var i = 0; i < sesArr.length; i++) {
            sesArr[i].flag = false
            for (var k = 0; k < this.extractGeneList.length; k++) {
              this.extractGeneList[k].flag = true
              if (sesArr[i].locusName === this.extractGeneList[k].locusName) {
                index.push(i)
              }
            }
          }
          for (var n = 0; n < index.length; n++) {
            sesArr.splice(index[n] - n, 1)
          }
          for (const item of sesArr) {
            const obj = {
              flag: item.flag,
              locusName: item.locusName,
              value: []
            }
            textArr.push(obj)
          }
          // setTimeout(() => {}, 500)
          newArr = this.extractGeneList.concat(textArr)
          // console.log(newArr, '最新数据---')
          for (var j = 0; j < this.tableData.length; j++) {
            if (j !== 0) {
              this.tableData[j].flag = newArr[j - 1].flag
              this.tableData[j].title1 = newArr[j - 1].locusName
              this.tableData[j].val1 = newArr[j - 1].value
              this.tableData[j].val2 = newArr[j - 1].value
              this.tableData[j].val3 = newArr[j - 1].value
            }
          }
          this.tableData = [...this.tableData]
        })
    },
    changePage() {},
    // 亲缘关系下拉框
    handSelectSample(val) {
      // console.log(val)
      this.tabSwitchStatus = val
      // console.log('下拉框')
      this.titleInfo = {}
      this.panelName = ''
      this.totalLR = 0
      this.handInitData()
    }
  }
}
</script>
<style lang="less" scoped>
.item-col {
  .item-label {
    width: 80px !important;
  }
}
</style>
