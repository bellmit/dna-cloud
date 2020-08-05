<template>
  <div style="width:100%">
    <Row>
      <i-col span="20" class="part">
        <Row class="nav">
          <i-col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/QualityControl"
              >质控数据管理</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/addQtyCtrlSam">{{
              title
            }}</router-link>
          </i-col>
        </Row>
        <div class="form-part">
          <div class="title">质控样本信息</div>
          <div class="border-bom">
            <Form :model="searchInfo" :label-width="120" style="padding:15px;">
              <Row>
                <i-col span="8">
                  <FormItem label="姓 名">
                    <i-input
                      type="text"
                      placeholder="请输入姓名"
                      v-model="searchInfo.qcPersonName"
                    ></i-input>
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="性 别">
                    <Select
                      placeholder="请选择性别"
                      v-model="searchInfo.qcPersonGender"
                    >
                      <Option value="1">男</Option>
                      <Option value="2">女</Option>
                    </Select>
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="身份证号">
                    <i-input
                      type="text"
                      placeholder="请输入身份证号"
                      v-model="searchInfo.qcPersonIdcardNo"
                    ></i-input>
                  </FormItem>
                </i-col>
              </Row>
              <Row>
                <i-col span="8">
                  <FormItem label="样本名称">
                    <i-input
                      type="text"
                      placeholder="请输入质控样本名称"
                      v-model="searchInfo.qcSampleName"
                    ></i-input>
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="样本编号">
                    <i-input
                      type="text"
                      placeholder="请输入质控样本编号"
                      v-model="searchInfo.qcSampleNo"
                    ></i-input>
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="样本类型">
                    <Select
                      placeholder="请选择质控样本类别"
                      v-model="searchInfo.qcSampleType"
                    >
                      <Option
                        v-for="item in qcSampleTypeList"
                        :key="item.id"
                        :value="item.dictCode"
                        >{{ item.dictName }}</Option
                      >
                    </Select>
                  </FormItem>
                </i-col>
              </Row>
              <Row>
                <i-col span="8">
                  <FormItem label="人员类型">
                    <Select
                      placeholder="请选择质控人员类别"
                      v-model="searchInfo.qcPersonType"
                    >
                      <Option
                        v-for="item in qcPersonTypeList"
                        :key="item.id"
                        :value="item.dictCode"
                        >{{ item.dictName }}</Option
                      >
                    </Select>
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="单位名称">
                    <Input
                      type="text"
                      placeholder="请输入单位名称"
                      v-model="searchInfo.qcPersonOrgName"
                    ></Input>
                    <!-- <span class="item-group">
                      <Cascader
                        :change-on-select="true"
                        :data="selectByOrgInfo"
                        v-model="searchInfo.clientOrgList"
                      ></Cascader>
                    </span> -->
                    <!-- <Select
                      filterable
                      @on-change="changeOrgId"
                      label-in-value
                      v-model="searchInfo.qcPersonOrgId"
                      @on-query-change="changeQuery"
                    >
                      <Option
                        v-for="item in selectByOrgInfo"
                        :key="item.id"
                        :value="item.id"
                        >{{ item.orgName }}</Option
                      >
                    </Select> -->
                  </FormItem>
                </i-col>
                <i-col span="8">
                  <FormItem label="DNA实验室">
                    <Select
                      placeholder="请选择DNA实验室"
                      v-model="searchInfo.labServerNo"
                    >
                      <Option
                        v-for="item in labServerList"
                        :key="item.id"
                        :value="item.labServerNo"
                        >{{ item.labServerName }}</Option
                      >
                    </Select>
                  </FormItem>
                </i-col>
              </Row>
            </Form>
          </div>
          <div class="title">样本基因信息</div>
          <div>
            <div class="reagent-title">
              <div>
                <button
                  @click="Switch_strYstr(index)"
                  v-for="(item, index) in btnList"
                  :key="index"
                  :class="{ tR_active: tR_active == index }"
                  class="stR_ystR"
                >
                  {{ item.name }}
                </button>
              </div>
              <div v-if="strShow">
                试剂盒名称
                <Select
                  placeholder="请选择试剂盒"
                  v-model="searchInfo.strPanelId"
                  style="width:160px;margin:0 15px;"
                  @on-change="changeStrPanel"
                  :label-in-value="true"
                >
                  <Option
                    v-for="(item, index) in strPanelList"
                    :key="index"
                    :value="item.id"
                    >{{ item.panelName }}</Option
                  >
                </Select>
              </div>
              <div v-else>
                试剂盒名称
                <Select
                  placeholder="请选择试剂盒"
                  v-model="searchInfo.ystrPanelId"
                  style="width:160px;margin:0 15px;"
                  @on-change="changeYstrPanel"
                  :label-in-value="true"
                >
                  <Option
                    v-for="(item, index) in ystrPanelList"
                    :key="index"
                    :value="item.id"
                    >{{ item.panelName }}</Option
                  >
                </Select>
              </div>
              <div>
                <button
                  style="margin:0 15px;"
                  class="btn btn-blue-bg"
                  @click.prevent="handleImportCodisModal"
                >
                  从CODIS导入
                </button>
              </div>
            </div>
            <div v-show="strShow" class="padding-15px">
              <!-- <h5>STR</h5> -->
              <Row :gutter="16">
                <i-col span="12" class="gene-border">
                  <Row class="gene-row padding-8px">
                    <i-col
                      span="12"
                      class="gene-col"
                      v-for="(item, index) in EditStrGeneList"
                      :key="index"
                    >
                      <span class="gene-label">{{ item.name }}</span>
                      <span v-for="(data, i) in item.arr" :key="i">
                        <i-input v-model="data.name" class="gene-input" />
                        <span v-show="i !== item.arr.length - 1">/</span>
                      </span>
                    </i-col>
                  </Row>
                </i-col>
                <i-col span="12">
                  <Row class="gene-row">
                    <i-col span="24" class="gene-col">
                      <div class="viewFt-title">查看图谱</div>
                      <div v-if="imgPath" class="viewFt img-suss">
                        <img src="" alt="" />
                      </div>
                      <div v-else class="viewFt img-err">
                        <div>
                          <img :src="img_err" alt="" />
                          <p>当前无峰图，您可查看其它信息</p>
                        </div>
                      </div>
                    </i-col>
                  </Row>
                </i-col>
              </Row>
            </div>
            <div v-show="ystrShow" class="padding-15px">
              <!-- <h5>YSTR</h5> -->
              <Row :gutter="16">
                <i-col span="15" class="gene-border">
                  <Row class="gene-row padding-8px">
                    <i-col
                      span="12"
                      class="gene-col"
                      v-for="(item, index) in EditYStrGeneList"
                      :key="index"
                    >
                      <span class="gene-label">{{ item.name }}</span>
                      <span v-for="(data, i) in item.arr" :key="i"
                        ><i-input
                          class="gene-input"
                          v-model="data.name"
                        />/</span
                      >
                      <Icon
                        @click="handaddGene(item, index)"
                        class="icon-add"
                        type="md-add-circle"
                        title="添加基因位点"
                      />
                      <!-- <Icon type="plus-circled" /> -->
                      <!-- <span><Icon type="plus-circled" style="font-size:12px" /></span> -->
                    </i-col>
                  </Row>
                </i-col>
                <i-col span="9">
                  <Row class="gene-row">
                    <i-col span="24" class="gene-col">
                      <div class="viewFt-title">查看图谱</div>
                      <div v-if="imgPath" class="viewFt img-suss">
                        <img src="" alt="" />
                      </div>
                      <div v-else class="viewFt img-err">
                        <div>
                          <img :src="img_err" alt="" />
                          <p>当前无峰图，您可查看其它信息</p>
                        </div>
                      </div>
                    </i-col>
                  </Row>
                </i-col>
              </Row>
            </div>
            <div class="saveBtn">
              <button class="btn btn-blue-bg" @click="handsaveInfo">
                保 存
              </button>
            </div>
          </div>
        </div>
        <Modal
          v-model="importCodisModal"
          width="1000"
          class="bazl-modal codis-modal"
          :closable="false"
          :mask-closable="false"
        >
          <div class="header">
            CODIS导入
            <Icon
              type="md-close-circle"
              class="modal-close"
              @click="importCodisModal = false"
            />
          </div>
          <Row class="item-row">
            <!-- <Col span="8">
          <span class="item-label">试剂盒选择</span>
          <Select class="item-select">
            <Option>2</Option>
            <Option>3</Option>
          </Select>
        </Col> -->
            <i-col span="24">
              <i-input
                class="item-input"
                type="text"
                readonly
                v-model="fileName"
              ></i-input>
              <!-- /bazlDnaMix/api_iLabSTRmix/ -->
              <Upload
                action="/bazlDnaMix/api_iLabSTRmix/database/codis/uploadCodis"
                style="display:inline;"
                :before-upload="handleUpload"
                :on-success="handleSuccess"
                :name="'codisFile'"
                :headers="headers"
              >
                <button class="btn btn-blue-bg" style="margin-left:15px;">
                  文件浏览
                </button>
              </Upload>
            </i-col>
          </Row>
          <Table
            border
            :columns="codisCol"
            :data="codisData"
            class="bazl-table"
          ></Table>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="submitCodis">
              确定
            </button>
            <button
              class="btn btn-blue-border"
              @click.prevent="closeImportCodisModal"
            >
              关闭
            </button>
          </Row>
        </Modal>
      </i-col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      img_err: require('../../assets/img/earth.png'),
      imgPath: null,
      SidebarParams: {
        open: ['2'],
        active: '2-3'
      },
      // 选试剂盒时主动为每一条添加数组，下标为2
      GeneList: [],
      id: '',
      btnList: [
        {
          name: 'STR'
        },
        {
          name: 'Y-STR'
        }
      ],
      // codis导入 弹窗
      importCodisModal: false,
      currentid: '',
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
            const id = params.row.uuidString
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
                    self.currentid = id
                    console.log(params, '单选框')
                    this.GeneList = params.row.geneInfoList
                    // this.id_jishi = params.row.idjishi
                  }
                }
              })
            ])
          }
        }
      ],
      selectByOrgInfo: [], // 单位名称
      qcPersonTypeList: [], // 质控人员类型
      qcSampleTypeList: [], // 样本类型
      labServerList: [], // DNA实验室
      codisData: [],
      // 质控样本信息
      searchInfo: {},
      fileName: '', // codis导入文件名字
      tR_active: 0, // str/ystr容器切换按钮
      titleInfo: {}, // 质控样本信息条件
      headers: {
        Authorization: '' // 请求头
      },
      EditStrGeneList: [], // str基因信息
      EditYStrGeneList: [], // ystr 基因信息
      reagentShow: true, // str ystr 试剂盒切换状态 为true显示 str试剂盒下拉框
      strShow: true, // str容器 状态
      ystrShow: false, // ystr容器
      STRreagentName: '', // str试剂盒名称 v-model
      YSTRreagentName: '', // ystr试剂盒名称 v-model
      strPanelList: [], // str试剂盒数据
      ystrPanelList: [], // ystr试剂盒数据
      apiPath: '',
      title: '',
      orgidList: ''
    }
  },
  beforeRouteEnter(to, from, next) {
    if (from.path === '/viewpollution' || from.path === '/viewcasegeno') {
      to.meta.isBack = true
    } else {
      to.meta.isBack = false
    }
    next()
  },
  activated() {
    if (!this.$route.meta.isBack) {
      this.getPanelList()
      this.getSelectList()
    } else {
      this.$route.meta.isBack = false // 重置详情页标识isBack
    }
  },
  created() {
    this.headers.Authorization = sessionStorage.getItem('accessToken')
    console.log(this.headers.Authorization)
    // this.getSelectByOrgInfo() // 单位名称
    if (sessionStorage.getItem('qcSampleTypeList')) {
      // 案件性质
      const arr = sessionStorage.getItem('qcSampleTypeList')
      this.qcSampleTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcPersonTypeList')) {
      // 案件性质
      const arr = sessionStorage.getItem('qcPersonTypeList')
      this.qcPersonTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcPersonTypeList')) {
      // 人员类型
      const arr = sessionStorage.getItem('qcPersonTypeList')
      this.qcPersonTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcSampleTypeList')) {
      // 样本
      const arr = sessionStorage.getItem('qcSampleTypeList')
      this.qcSampleTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('panelNameSTR')) {
      // STR试剂盒
      const arr = sessionStorage.getItem('panelNameSTR')
      this.panelList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('panelNameSTR')) {
      // STR试剂盒
      const arr = sessionStorage.getItem('panelNameSTR')
      this.strPanelList = JSON.parse(arr)
      // console.log(this.panelList, 'str')
    }
    if (sessionStorage.getItem('panelNameYSTR')) {
      // YSTR试剂盒
      const arr = sessionStorage.getItem('panelNameYSTR')
      this.ystrPanelList = JSON.parse(arr)
      // console.log(this.panelList, 'ystr')
      // console.log(this.ystrPanelList, 'ystrPanelList数组')
    }
    if (sessionStorage.getItem('labServerList')) {
      //
      const arr = sessionStorage.getItem('labServerList')
      this.labServerList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectByOrgInfo')) {
      //
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
  },
  mounted() {
    this.id = this.$route.query.id
    // console.log(this.id, '111111')
    if (this.id) {
      this.title = '编辑质控样本'
      this.handInitData()
    } else {
      this.title = '添加质控样本'
    }
  },
  methods: {
    //  单位名称 下拉框 获取主动输入值
    changeQuery(val) {
      // console.log(val)
      if (val) {
        this.searchInfo.qcPersonOrgName = val
      }
    },
    // 单位名称下拉框
    changeOrgId(val) {
      // console.log(val)
    },
    // ystr 添加基因位点按钮
    handaddGene(item, index) {
      // console.log(item)
      // console.log(index)
      if (item.arr.length > 4 || item.arr.length === 4) {
        this.$Message.error('Y-STR基因位点最多添加4个！')
      } else {
        item.arr.push({})
      }
    },
    // 保存页面信息按钮
    handsaveInfo() {
      this.searchInfo.strGeneInfo = this.handforin(this.EditStrGeneList)
      this.searchInfo.ystrGeneInfo = this.handforin(this.EditYStrGeneList)
      // if (this.orgidList.length !== 0) {
      //   this.searchInfo.qcPersonOrgId = this.orgidList.pop()
      // } else {
      //   this.searchInfo.qcPersonOrgId = ''
      // }
      this.$axios.post('/database/qc/insertAdd', this.searchInfo).then(res => {
        if (res.result) {
          this.$Message.success('保存成功！')
        } else {
          this.$Message.error('操作失败！')
        }
      })
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('/database/qc/updatePageList', {
          params: {
            id: this.id
          }
        })
        .then(res => {
          // console.log(res, '编辑页面')
          this.searchInfo = res.result
          this.searchInfo.qcPersonOrgId = Number(res.result.qcPersonOrgId)
          this.EditStrGeneList = this.handforInit(
            JSON.parse(res.result.strGeneInfo)
          )
          this.EditYStrGeneList = this.handforInit(
            JSON.parse(res.result.ystrGeneInfo)
          )
          // console.log(this.EditYtrGeneList)
          // this.EditYtrGeneList = [...this.EditYtrGeneList]
        })
    },
    // codis 导入确认按钮
    submitCodis() {
      this.importCodisModal = false
      if (this.strShow) {
        this.EditStrGeneList = this.handforInit(this.GeneList)
        this.searchInfo.strPanelName = null
        this.searchInfo.strPanelId = null
      } else {
        this.EditYStrGeneList = this.handforInit(this.GeneList)
        this.searchInfo.ystrPanelName = null
        this.searchInfo.ystrPanelId = null
      }
      // console.log(this.GeneList, '确认')
    },
    // 循环 处理初始基因位点信息数据格式
    handforInit(oldArr) {
      const newArr = []
      for (const item of oldArr) {
        const obj = {
          name: item.name,
          arr: []
        }
        for (const data of item.value) {
          if (item.value.length === 0) {
            obj.arr.push({}, {})
          } else if (item.value.length !== 0 && item.value.length < 2) {
            obj.arr.push({ name: data }, {})
          } else {
            obj.arr.push({ name: data })
          }
        }
        newArr.push(obj)
      }
      return newArr
    },
    // 循环处理str ystr基因数据
    handforin(oldArr) {
      // console.log(oldArr, '进入循环操作')
      const arr1 = []
      for (const item of oldArr) {
        const obj1 = {
          name: item.name,
          value: []
        }
        for (const data of item.arr) {
          if (data.name) {
            obj1.value.push(data.name)
          } else {
            obj1.value = []
          }
        }
        arr1.push(obj1)
      }
      const jsonstr = {}
      arr1.forEach(item => {
        if (item.value.length !== 0) {
          jsonstr[item.name] = item.value.join('/')
        } else {
          jsonstr[item.name] = ''
        }
      })
      const info = JSON.stringify(jsonstr)
      return info
    },
    // str/ystr 切换按钮
    Switch_strYstr(index) {
      // console.log(index, typeof index)
      this.tR_active = index
      // console.log(
      //   this.STRreagentName,
      //   typeof this.STRreagentName,
      //   'STRreagentNameSTRreagentName',
      //   this.YSTRreagentName,
      //   typeof this.YSTRreagentName
      // )
      if (index === 0) {
        this.strShow = true
        this.ystrShow = false
      } else if (index === 1) {
        // console.log(this.EditYtrGeneList)
        this.ystrShow = true
        this.strShow = false
      }
    },
    // 唤醒codis弹窗
    handleImportCodisModal() {
      this.importCodisModal = true
      this.codisData = []
      this.fileName = ''
    },
    // codis 导入前
    handleUpload(file) {
      // console.log(file)
      this.fileName = file.name
    },
    // codis 导入成功
    handleSuccess(res) {
      // console.log(res)
      this.codisData = res.result
    },
    // codis弹窗 关闭按钮
    closeImportCodisModal() {
      this.importCodisModal = false
      this.codisData = []
      this.fileName = ''
    },
    // str下拉框 监听
    changeStrPanel(params) {
      console.log('进入判断str')
      this.handGetGeneList(params, 0)
    },
    // ystr下拉框 监听
    changeYstrPanel(params) {
      // console.log('进入判断YSTr')
      this.handGetGeneList(params, 1)
    },
    handGetGeneList(params, num) {
      // console.log(params)
      if (params) {
        if (num === 0) {
          this.EditStrGeneList = []
          this.searchInfo.strPanelName = params.label
          this.searchInfo.strPanelId = params.value
        } else {
          this.EditYStrGeneList = []
          this.searchInfo.ystrPanelName = params.label
          this.searchInfo.ystrPanelId = params.value
        }
        this.$axios
          .get('/database/panel/locusListByPanel', {
            params: {
              panelId: params.value
            }
          })
          .then(res => {
            // console.log(res)
            // arr.forEach(item => {
            //   jsonstr[item.name] = item.value.join('/')
            // })
            for (const item of res.result) {
              const obj = {
                name: item.locusName,
                arr: [{}, {}]
              }
              if (num === 0) {
                this.EditStrGeneList.push(obj)
              } else {
                this.EditYStrGeneList.push(obj)
              }
            }
          })
      }
    }
    // // 单位名称
    // getSelectByOrgInfo() {
    //   this.$axios
    //     .get('/database/comm/dict/selectByOrgInfo', {
    //       params: {
    //         orgLevel: '2'
    //       }
    //     })
    //     .then(res => {
    //       console.log(res, '-----单位名称')
    //       this.selectByOrgInfo = res.result
    //     })
    // }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamanage';
@import '../../assets/styles/addQtyCtrlSam';
@import '../../assets/styles/same.less';
</style>
