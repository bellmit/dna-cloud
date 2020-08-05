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
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/warehousing/str">STR入库比对条件设置</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <Row class="title">
            <Col span="12">STR入库比对条件设置</Col>
            <Col span="12" class="tr">
              <RadioGroup v-model="classTitleRadio">
                <Radio label="1" class="item-radio">只看同型比对设置</Radio>
                <Radio label="2" class="item-radio">只看亲缘-三联体比对设置</Radio>
                <Radio label="3" class="item-radio">只看亲缘-单亲比对设置</Radio>
              </RadioGroup>
            </Col>
          </Row>
          <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
              :span-method="handleSpan"
              size="small"
              :row-class-name="rowClassName"
              id="change-table-class"
            ></Table>
            <Row class="btn-row">
              <button class="btn btn-blue-bg" @click.prevent="submitSettingSTR">保存设置</button>
            </Row>
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
        open: ['7.0', '7-2'],
        active: '7-2-1'
      },
      classTitleRadio: '1',
      tableCol: [
        {
          title: '入库数据类型',
          key: 'instoreDataName',
          align: 'center',
          className: 'bg-td'
        },
        {
          title: '是否入库自动比对',
          align: 'center',
          width: '120',
          children: [
            {
              renderHeader: (h, params) => {
                return h('span', {}, '')
              },
              align: 'center',
              render: (h, params) => {
                // console.log(params, 'params')
                if (params.index % 4 === 1) {
                  return h('span', {}, '亲缘比对')
                } else {
                  return h('span', {}, '同型比对')
                }
              }
            },
            {
              renderHeader: (h, params) => {
                return h('span', {}, '')
              },
              width: '100',
              render: (h, params) => {
                if (params.index % 4 === 0) {
                  return h('div', [
                    h('Checkbox', {
                      props: {
                        value: Boolean(params.row.autoCompareFlag)
                      },
                      style: {},
                      on: {
                        'on-change': val => {
                          this.tableData[params.index].autoCompareFlag = Number(val)
                        }
                      }
                    }),
                    h('span', {}, '同型比对')
                  ])
                } else if (params.index % 4 === 1) {
                  return h('div', [
                    h('Checkbox', {
                      props: {
                        value: Boolean(params.row.autoCompareFlag)
                      },
                      style: {},
                      on: {
                        'on-change': val => {
                          this.tableData[params.index].autoCompareFlag = Number(val)
                        }
                      }
                    }),
                    h('span', {}, '三联体比对')
                  ])
                } else if (params.index % 4 === 2) {
                  return h('div', [
                    h('Checkbox', {
                      props: {
                        value: Boolean(params.row.autoCompareFlag)
                      },
                      style: {},
                      on: {}
                    }),
                    h('span', {
                      'on-change': val => {
                        this.tableData[params.index].autoCompareFlag = Number(val)
                      }
                    }, '父子比对')
                  ])
                } else if (params.index % 4 === 3) {
                  return h('div', [
                    h('Checkbox', {
                      props: {
                        value: Boolean(params.row.autoCompareFlag)
                      },
                      style: {},
                      on: {}
                    }),
                    h('span', {
                      'on-change': val => {
                        this.tableData[params.index].autoCompareFlag = Number(val)
                      }
                    }, '母子比对')
                  ])
                }
              }
            }
          ]
        },
        {
          title: '比对条件',
          align: 'center',
          children: [
            {
              title: '匹配下限',
              align: 'center',
              render: (h, params) => {
                return h('div', [
                  h('Input', {
                    props: {
                      value: params.row.lowestSameLimit
                    },
                    style: {
                      width: '50px'
                    },
                    on: {
                      'on-blur': event => {
                        this.tableData[params.index].lowestSameLimit = event.target.value
                      }
                    }
                  })
                ])
              }
            },
            {
              title: '容差上限',
              align: 'center',
              render: (h, params) => {
                return h('div', [
                  h('Input', {
                    props: {
                      value: params.row.mostDiffLimit
                    },
                    style: {
                      width: '50px'
                    },
                    on: {
                      'on-blur': event => {
                        this.tableData[params.index].mostDiffLimit = event.target.value
                      }
                    }
                  })
                ])
              }
            }
          ]
        },
        {
          title: '比对目标数据类型',
          align: 'center',
          children: [
            {
              title: '现场物证',
              align: 'center',
              render: (h, params) => {
                if (params.row.id) {
                  return h('Checkbox', {
                    props: {
                      value: params.row.physicalEvidence === 'false' ? Boolean(0) : Boolean(1)
                    },
                    style: {},
                    on: {
                      'on-change': val => {
                        this.tableData[params.index].physicalEvidence =
                          String(val)
                        // console.log(this.tableData, '-----')
                      }
                    }
                  })
                } else {
                  return h('span', {}, '—')
                }
              }
            },
            {
              title: '违法犯罪人员',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.criminalsPerson === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].criminalsPerson =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '受害人',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.victimPerson === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].victimPerson =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '失踪人口',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.missingPerson === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].missingPerson =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '无名尸',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.unknownCorpses === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].unknownCorpses =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '嫌疑人亲属',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.relativesOfSuspects === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].relativesOfSuspects =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '受害人亲属',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.relativesOfVictims === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].relativesOfVictims =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '失踪人口亲属',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.relativesOfMissings === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].relativesOfMissings =
                        String(val)
                    }
                  }
                })
              }
            },
            {
              title: '基础库',
              key: '',
              align: 'center',
              render: (h, params) => {
                return h('Checkbox', {
                  props: {
                    value: params.row.basicLibrary === 'false' ? Boolean(0) : Boolean(1)
                  },
                  style: {},
                  on: {
                    'on-change': val => {
                      this.tableData[params.index].basicLibrary =
                        String(val)
                    }
                  }
                })
              }
            }
          ]
        }
      ],
      tableData: []
    }
  },
  mounted() {
    this.getSettingSTR()
  },
  methods: {
    handleSpan ({ row, column, rowIndex, columnIndex }) {
      // console.log({ row, column, rowIndex, columnIndex })
      // console.log(column, columnIndex)
      if (columnIndex === 0 && rowIndex % 4 === 0) {
        return [4, 1]
      } else if (columnIndex === 0 && rowIndex !== 0) {
        return [0, 0]
      }
      if (columnIndex === 1 && rowIndex % 4 === 1) {
        return [3, 1]
      } else if (columnIndex === 1 && rowIndex % 4 === 1) {
        return [0, 0]
      } else if (columnIndex === 1 && rowIndex % 4 === 2) {
        return [0, 0]
      } else if (columnIndex === 1 && rowIndex % 4 === 3) {
        return [0, 0]
      }
    },
    rowClassName(row, index) {
      if (this.classTitleRadio === '1') {
        if (index % 4 === 0) {
          return 'active-row'
        } else {
          return 'no-active-row'
        }
      } else if (this.classTitleRadio === '2') {
        if (index % 4 === 1) {
          return 'active-row'
        } else {
          return 'no-active-row'
        }
      } else if (this.classTitleRadio === '3') {
        if (index % 4 === 2 || index % 4 === 3) {
          return 'active-row'
        } else if (index % 4 === 1) {
          return 'no-active-row this-first-active'
        } else {
          return 'no-active-row'
        }
      }
    },
    getSettingSTR() {
      this.$axios.get('/database/system/compare/listStrTargetType').then(res => {
        res.result.forEach(item => {
          if (!item.physicalEvidence) {
            item.physicalEvidence = ''
          }
        })
        this.tableData = res.result
      })
    },
    submitSettingSTR() {
      const arr = this.tableData
      const array = []
      arr.forEach(item => {
        const objArr = {}
        objArr.instoreDataType = item.instoreDataType || null
        objArr.autoCompareFlag = item.autoCompareFlag || null
        objArr.lowestSameLimit = item.lowestSameLimit || null
        objArr.mostDiffLimit = item.mostDiffLimit || null
        objArr.compareMode = item.compareMode || null
        objArr.id = item.id || null
        item.instoreDataName = null
        objArr.targetDataType = []
        if (item.physicalEvidence) {
          const obj = {}
          obj.code = 'physicalEvidence'
          obj.checked = item.physicalEvidence
          objArr.targetDataType.push(obj)
        }
        if (item.mixEvidence) {
          const obj = {}
          obj.code = 'mixEvidence'
          obj.checked = item.mixEvidence
          objArr.targetDataType.push(obj)
        }
        if (item.criminalsPerson) {
          const obj = {}
          obj.code = 'criminalsPerson'
          obj.checked = item.criminalsPerson
          objArr.targetDataType.push(obj)
        }
        if (item.victimPerson) {
          const obj = {}
          obj.code = 'victimPerson'
          obj.checked = item.victimPerson
          objArr.targetDataType.push(obj)
        }
        if (item.missingPerson) {
          const obj = {}
          obj.code = 'missingPerson'
          obj.checked = item.missingPerson
          objArr.targetDataType.push(obj)
        }
        if (item.unknownCorpses) {
          const obj = {}
          obj.code = 'unknownCorpses'
          obj.checked = item.unknownCorpses
          objArr.targetDataType.push(obj)
        }
        if (item.relativesOfSuspects) {
          const obj = {}
          obj.code = 'relativesOfSuspects'
          obj.checked = item.relativesOfSuspects
          objArr.targetDataType.push(obj)
        }
        if (item.relativesOfVictims) {
          const obj = {}
          obj.code = 'relativesOfVictims'
          obj.checked = item.relativesOfVictims
          objArr.targetDataType.push(obj)
        }
        if (item.relativesOfMissings) {
          const obj = {}
          obj.code = 'relativesOfMissings'
          obj.checked = item.relativesOfMissings
          objArr.targetDataType.push(obj)
        }
        if (item.basicLibrary) {
          const obj = {}
          obj.code = 'basicLibrary'
          obj.checked = item.basicLibrary
          objArr.targetDataType.push(obj)
        }
        objArr.targetDataType = JSON.stringify(objArr.targetDataType)
        array.push(objArr)
      })
      this.$axios
        .post('/database/system/compare/updateStrCompareSettings', array).then(res => {
          this.$Modal.success({
            title: '恭喜',
            content: '保存成功~'
          })
          this.getSettingSTR()
        }).catch(() => {
          this.$Modal.error({
            title: '抱歉',
            content: '保存失败~'
          })
        })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/setup';
</style>
