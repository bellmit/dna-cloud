<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>遗传学基因数据管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/LikelihoodRatio"
              >似然率管理</router-link
            >
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            设置条件
          </div>
          <Row class="item-row">
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
                placeholder="请选择计算方案"
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
            <Col span="24">计算结果
             <div
            style="float:right;margin-right:5px;font-size:18px;color: #00439E;"
          >
            <span>累积似然率：</span>
            <span>{{ totalLR }}</span>
          </div>
            </Col>
          </Row>
          <Table
            border
            :columns="tableCol0"
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
      geneType: '',
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
          Attributes: 'Mr',
          // className: 'tabStyle',
          width: 100,
          render: (h, params) => {
            // // eslint-disable-next-line no-unused-vars
            let markSytel = ''
            // eslint-disable-next-line no-unused-vars
            let text = ''
            if (params.index !== 0) {
              text = params.row.title1
              if (
                //  && params.column._index === this.numfunc - 1
                params.row.flag === true
              ) {
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
          title: '样本一',
          key: 'val1',
          align: 'center',
          // width: '300',
          // Attributes: 'Mr',
          render: (h, params) => {
            if (params.index === 0) {
              return h('div', [
                h('span', '样本提取：'),
                h('Input', {
                  style: {
                    marginLeft: '10px',
                    width: '160px'
                  },
                  props: {
                    type: 'text',
                    placeholder: '请输入样本编号',
                    value: this.sampleInput1 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.sampleInput1 = event.target.value
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
                        this.handExtract(1, this.sampleInput1)
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
          title: '样本二',
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
                    value: this.sampleInput2 // 使用key的键值
                  },
                  on: {
                    'on-change': event => {
                      // console.log(event.target.value)
                      this.sampleInput2 = event.target.value
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
                        this.handExtract(2, this.sampleInput2)
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
          title: '似然比率',
          key: 'LR',
          align: 'center',
          width: 100
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      arr: [],
      panelNameSTR: [], // 试剂盒下拉框
      personRaceList: [],
      initGeneList: [],
      populationQuery: [], // 种群下拉框
      panelName1: '',
      panelName2: '',
      panelName3: '',
      numfunc: '',
      sampleInput1: '', // 表格输入框值
      sampleInput2: '',
      sampleInput3: '',
      tabSwitchStatus: '0', // 表格切換状态码 样本关系
      population: 1, // 种群下拉框 参数 v-model
      extractGeneList: [], // 提取 基因信息
      panelName: '', // 试剂盒名称
      panelval: null,
      flag: false
    }
  },
  created() {
    if (sessionStorage.getItem('populationQuery')) {
      // 种群
      const arr = sessionStorage.getItem('populationQuery')
      this.populationQuery = JSON.parse(arr)
      // console.log(this.populationQuery, '种群')
    }
    // sessionStorage.setItem('arrA', JSON.stringify(this.initGeneList))
    if (sessionStorage.getItem('panelNameSTR')) {
      //
      const arr = sessionStorage.getItem('panelNameSTR')
      this.panelNameSTR = JSON.parse(arr)
    }
    // console.log(this.panelNameSTR, 'str-----')
  },
  mounted() {
    this.handInitData()
  },
  watch: {
    flag(val, oldVal) {
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
    handleTab(index) {
      this.active = index
    },
    // 重置页面数据
    handrest() {
      // console.log(this.tableData, '清空---')
      this.panelName = ''
      this.sampleInput1 = ''
      this.sampleInput2 = ''
      this.totalLR = 0
      this.handInitData()
      this.tableData = [...this.tableData]
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('/database/likeLiHood/queryStrLocusInfo', {})
        .then(res => {
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
          // // 在数组前插入一条
          arr.unshift({})
          this.tableData = arr
          // console.log(this.tableData, '初始表格')
          this.flag = true
        })
    },
    // 计算按钮
    handCalculation() {
      if (!this.population) {
        this.$Message.error('请先选择基因频率计算方案！')
        return false
      } else {
        const LikeLiHoodCalcModel = {
          populationFrequencyId: this.population,
          strSampleAGeneInfo: {
            // sampleNo:
            geneInfo: {}
          },
          strSampleBGeneInfo: {
            geneInfo: {}
          }
        }
        const jsonStr1 = {}
        const jsonStr2 = {}
        for (const item of this.tableData) {
          if (item !== this.tableData[0]) {
            jsonStr1[item.title1] = item.val1.join('/')
            jsonStr2[item.title1] = item.val2.join('/')
          }
        }
        LikeLiHoodCalcModel.strSampleAGeneInfo.geneInfo = JSON.stringify(
          jsonStr1
        )
        LikeLiHoodCalcModel.strSampleBGeneInfo.geneInfo = JSON.stringify(
          jsonStr2
        )
        // console.log(LikeLiHoodCalcModel)
        this.$axios
          .post('/database/likeLiHood/calcLikeLiHood', LikeLiHoodCalcModel)
          .then(res => {
            // console.log(JSON.parse(res.result))
            const arr = JSON.parse(res.result).alleleList
            this.totalLR = JSON.parse(res.result).totalLR
            // console.log(arr, '----计算')
            for (const item of this.tableData) {
              for (const data of arr) {
                if (item !== this.tableData[0]) {
                  if (item.title1 === data.alleleKey) {
                    // console.log('进入赋值判断11')
                    item.LR = data.LR
                  }
                }
              }
            }
            this.tableData = [...this.tableData]
            // console.log(this.tableData, '新数组')
          })
        // }
      }
    },
    // 提取按钮
    handExtract(type, sampNo) {
      if (sampNo) {
        this.$axios
          .get('/database/likeLiHood/queryGeneInfo', {
            params: { sampleNo: sampNo, geneType: 1 }
          })
          .then(res => {
            // console.log(res, '查看请求数据顺序')
            this.extractGeneList = []
            for (const item of JSON.parse(res.result.geneInfo)) {
              const obj = {
                locusName: item.name,
                value: item.value
              }
              this.extractGeneList.push(obj)
            }
            // console.log(this.extractGeneList, '提取数据')
            // console.log(this.tableData, '表格数据')
            this.tableData.forEach(i => {
              this.extractGeneList.forEach(k => {
                if (i !== this.tableData[0]) {
                  console.log()
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
            }
          }
          this.tableData = [...this.tableData]
        })
    },
    changePage() {},
    // 样本关系下拉框
    handSelectSample(val) {
      // console.log(val)
      this.tabSwitchStatus = val
      this.tableData.forEach(item => {
        item.val1 = []
        item.val2 = []
        item.val3 = []
      })
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
