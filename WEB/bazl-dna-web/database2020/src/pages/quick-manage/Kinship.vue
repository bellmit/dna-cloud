<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>快速比对管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/Kinship">亲缘比对</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            亲缘快速比对
            <button
              class="btn btn-blue-bg"
              style="font-size:14px;position:absolute;right:20px;"
              @click="goRecord"
            >
              比对记录查询
            </button>
          </div>
          <div class="item-title" style="border-top:none;">
            亲缘比对模式
          </div>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label" style="width:140px;"
                >待比对样本亲缘身份</span
              >
              <Select
                style="width:220px"
                v-model="toCompareKinship"
                @on-change="changeKinship"
              >
                <!-- <Option value="01">父、母</Option>
                <Option value="02">父</Option>
                <Option value="03">母</Option>
                <Option value="04">子/女</Option>
                <Option value="05">父、子女</Option>
                <Option value="06">母、子女</Option> -->
                <Option
                  v-for="item in selectByTargetReationType"
                  :key="item.dictCode"
                  :value="item.dictCode"
                  >{{ item.dictName }}</Option
                >
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label" style="width:140px;"
                >目标样本亲缘身份</span
              >
              <Select
                style="width:220px;"
                v-model="targetKinshipId"
                @on-change="changeTargetKinship"
              >
                <Option
                  v-for="item in targetIds"
                  :key="item.dictCode"
                  :value="item.dictCode"
                  >{{ item.dictName }}</Option
                >
              </Select>
              <!-- <Input
                type="text"
                readonly
                class="item-input"
                v-model="comparMode"
              ></Input> -->
            </Col>
          </Row>
          <div v-for="(item, index) in sampleList" :key="item.id">
            <div class="item-title">待比对亲缘样本{{ index + 1 }}</div>
            <Row class="item-row" :gutter="16">
              <Col class="item-col" span="4">
                <span class="item-label">亲缘身份</span>
                <!-- <Select placeholder="请选择亲缘身份" class="item-select" v-model="searchForm.caseProperty">
                    <Option>2</Option>
                    <Option>2</Option>
                    <Option>2</Option>
                  </Select> -->
                <Input
                  type="text"
                  readonly
                  class="item-input"
                  v-model="item.kinship"
                  style="width:80px"
                ></Input>
              </Col>
              <Col class="item-col" span="20">
                <span class="item-label">样本编号</span>
                <Input
                  style="width:200px"
                  type="text"
                  placeholder="请输入样本编号"
                  class="item-input"
                  v-model="item.sampleNo"
                ></Input>
                <button
                  class="btn btn-blue-bg"
                  @click.prevent="searchDataBase(index, $event, 'lims')"
                >
                  Lims数据库检索
                </button>
                <button
                  class="btn btn-yel-bg"
                  @click.prevent="searchDataBase(index, $event, '')"
                >
                  DNA数据库检索
                </button>
                <button
                  class="btn btn-blue-border"
                  @click.prevent="importCodis(index, $event)"
                >
                  从CODIS导入
                </button>
                <!-- <button
                  class="btn btn-blue-bg"
                  @click.prevent="searchDataBase(index, $event)"
                >
                  数据库检索
                </button>
                <button
                  class="btn btn-blue-bg"
                  @click.prevent="importCodis(index, $event)"
                >
                  从CODIS导入
                </button> -->
                <span class="item-label" style="margin-left:20px;"
                  >样本名称</span
                >
                <Input
                  style="width:200px"
                  type="text"
                  placeholder="请输入样本名称"
                  class="item-input"
                  v-model="item.sampleName"
                ></Input>
              </Col>
              <!-- <Col class="item-col" span="6">
              </Col> -->
              <!-- <Col class="item-col" span="7">
              </Col> -->
            </Row>
            <Row class="item-row" :gutter="16">
              <Col class="item-col" span="24">
                <span class="item-label">试剂盒名称</span>
                <Select
                  placeholder="请选择试剂盒"
                  class="item-select"
                  v-model="item.panelId"
                  :label-in-value="true"
                  @on-change="changeIndexKit(index, $event)"
                >
                  <Option
                    v-for="item in panelNameSTR"
                    :key="item.id"
                    :value="item.id"
                    >{{ item.panelName }}</Option
                  >
                </Select>
                <button
                  class="btn btn-blue-border"
                  @click="addMixedSTRLocus(index, $event)"
                >
                  添加基因座
                </button>
              </Col>
              <!-- <Col span="6" class="item-col">
              </Col> -->
            </Row>
            <hr class="gray-line" />
            <div class="gene-part">
              <Row :gutter="16" class="gene-row">
                <Col
                  span="6"
                  class="gene-col"
                  v-for="i in item.genelist"
                  :key="i.id"
                >
                  <span class="gene-label">{{ i.name }}</span>
                  <Input class="gene-input" v-model="i.value[0]" />/
                  <Input class="gene-input" v-model="i.value[1]" />
                </Col>
              </Row>
            </div>
          </div>
          <div class="item-title">
            比对条件设置
          </div>
          <div class="data-type">
            <Row class="item-sub">
              <span class="little-title">目标数据类型</span>
              <Checkbox
                class="check-all"
                style="color:#3086C1;"
                :indeterminate="indeterminateData"
                :value="checkAllData"
                @click.prevent.native="handleCheckAllData"
                >全选</Checkbox
              >
            </Row>
            <Row class="item-row">
              <CheckboxGroup
                v-model="dataCheckAllList"
                @on-change="checkAllGroupChangeData"
              >
                <Col
                  span="3"
                  class="item-col"
                  v-for="item in instoreDataTypeList"
                  :key="item.typeCode"
                >
                  <Checkbox :label="item.typeCode">{{
                    item.typeName
                  }}</Checkbox>
                </Col>
              </CheckboxGroup>
            </Row>
            <hr class="gray-line" />
            <Row class="item-sub">
              <span class="little-title">比对实验室范围</span>
              <Checkbox
                class="check-all"
                style="color:#3086C1;"
                :indeterminate="indeterminateLab"
                :value="checkAllLab"
                @click.prevent.native="handleCheckAllLab"
                >全选</Checkbox
              >
            </Row>
            <Row class="item-row">
              <CheckboxGroup
                v-model="labCheckAllList"
                @on-change="checkAllGroupChangeLab"
              >
                <Col
                  span="4"
                  class="item-col"
                  v-for="item in selectLabName"
                  :key="item.labServerNo"
                >
                  <Checkbox :label="item.labServerNo">{{
                    item.labServerName
                  }}</Checkbox>
                </Col>
              </CheckboxGroup>
            </Row>
          </div>
          <hr class="gray-line" />
          <Row class="inline-form">
            <Col class="item-col">
              <Row>
                <Col span="3">
                  <span class="item-label">性别</span>
                  <Checkbox
                    style="inline-block"
                    :indeterminate="sexIndeterminate"
                    :value="sexCheckAll"
                    @click.prevent.native="handleSexCheckAll"
                    >全选</Checkbox
                  >
                </Col>
                <Col span="2">
                  <CheckboxGroup
                    v-model="sexCheckAllGroup"
                    @on-change="sexCheckAllGroupChange"
                  >
                    <Checkbox label="1">男</Checkbox>
                    <Checkbox label="2">女</Checkbox>
                  </CheckboxGroup>
                </Col>
              </Row>
            </Col>
          </Row>
          <Row :gutter="16" class="inline-form">
            <Col span="8" class="item-col">
              <span class="item-label">种群范围</span>
              <Select
                placeholder="请选择"
                class="item-select"
                v-model="populationFrequencyId"
              >
                <Option
                  v-for="item in selectByPopulationInfo"
                  :key="item.id"
                  :value="item.id"
                  >{{ item.populationName }}</Option
                >
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">匹配下限</span>
              <Input
                type="number"
                placeholder="请输入匹配下限"
                class="item-input"
                v-model="lowestSameLimit"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">容差上限</span>
              <Input
                type="number"
                placeholder="请输入容差上限"
                class="item-input"
                v-model="mostDiffLimit"
              ></Input>
            </Col>
          </Row>
          <hr class="gray-line" />
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click="submitAddMixedSTRLocus">
              提交比对
            </button>
          </Row>
        </div>
      </Col>
    </Row>
    <Modal
      v-model="addGene"
      width="800"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        添加基因座
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="addGene = false"
        />
      </div>
      <Row class="item-sub">
        <Col span="6">
          <span>增加比对位点</span>
          <Checkbox
            class="check-all"
            style="color:#3086C1;"
            :indeterminate="indeterminateAddLocus"
            :value="checkAllAddLocus"
            @click.prevent.native="handleCheckAllAddLocus"
            >全选</Checkbox
          >
        </Col>
        <Col span="12">
          <Input
            placeholder="请输入基因座名称"
            class="item-input"
            v-model.trim="geneText"
            style="width:220px;"
          ></Input>
          <button
            @click="handSearchGene"
            style="height:32px;margin-left:20px"
            class="btn btn-blue-bg"
          >
            确认
          </button>
        </Col>
      </Row>
      <Row class="item-row">
        <CheckboxGroup
          v-model="addLocusCheckAllList"
          @on-change="checkAllGroupChangeAddLocus"
        >
          <Col
            span="6"
            class="item-col"
            v-for="item in addMixedSTRLocusList"
            :key="item.id"
          >
            <Checkbox :label="item.locusName"></Checkbox>
          </Col>
        </CheckboxGroup>
      </Row>
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="submitAddLocus">确认</button>
        <button class="btn btn-blue-border" @click="addGene = false">
          关闭
        </button>
      </Row>
    </Modal>
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
        <Col span="8">
          <Input
            class="item-input"
            type="text"
            readonly
            v-model="fileName"
          ></Input>
          <!-- /bazlDnaMix/api_iLabSTRmix/ -->
          <!-- action="/bazlDnaMix/api_iLabSTRmix/database/codis/uploadCodis" -->
          <Upload
            action="/bazlDnaMix/api_iLabSTRmix/database/codis/uploadCodis"
            style="display:inline;"
            :before-upload="handleUpload"
            :on-success="handleSuccess"
            name="codisFile"
            :headers="headers"
          >
            <button class="btn btn-blue-bg" style="margin-left:15px;">
              文件浏览
            </button>
          </Upload>
        </Col>
      </Row>
      <Table
        border
        :columns="codisCol"
        :data="codisData"
        class="bazl-table"
        size="small"
      ></Table>
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click.prevent="submitCodisData">
          确定
        </button>
        <button class="btn btn-blue-border" @click="importCodisModal = false">
          关闭
        </button>
      </Row>
    </Modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      geneText: '',
      sexCheckAllGroup: ['1', '2'], // 性别
      sexCheckAll: true, // 性别全选
      sexIndeterminate: false, // 性别全选
      toCompareKinship: '', // 待比对样本亲缘身份
      comparMode: '',
      searchForm: {},
      sampleList: [],
      currentItemIndex: '',
      samaplePersonNo: '2',
      sampleNo: '',
      // sampleNo: 'LZ110222041688',
      mixPerson: '2',
      sampleName: '',
      panelId: 0,
      panelName: '',
      mixPersonList: [
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        },
        {
          name: 'D8S1179'
        }
      ],
      fileName: '',
      headers: {
        Authorization: ''
      },
      currentid: '',
      codisCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '是否混合分型',
          key: 'mixFlag',
          align: 'center'
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
            return h('div', [
              h('Radio', {
                props: {
                  value: flag
                },
                on: {
                  'on-change': () => {
                    const arr = this.codisData
                    this.currentid = id
                    this.codisDataSelectd = []
                    this.codisDataSelectd.push(
                      arr.find(x => x.uuidString === this.currentid)
                    )
                    // console.log(this.codisDataSelectd, '-----9090909090')
                  }
                }
              })
            ])
          }
        }
      ],
      codisData: [],
      codisDataSelectd: [],
      addGene: false,
      importCodisModal: false,
      addMixedSTRLocusList: [], // 添加的基因座列表
      indeterminateAddLocus: true,
      checkAllAddLocus: false,
      addLocusCheckAllList: [], // 添加 选中的基因座
      indeterminateData: true, // 目标数据全选标识
      checkAllData: false, // 目标数据全选
      dataCheckAllList: [], // 目标数据选中
      indeterminateLab: true, // 比对实验室全选标识
      checkAllLab: false, // 比对实验室全选
      labCheckAllList: [], // 比对实验室选中
      instoreDataTypeList: [], // 入库数据类型
      selectLabName: [], // 比对实验室范围
      selectByPopulationInfo: [], // 种群列表
      populationFrequencyId: 1, // 种群值
      mostDiffLimit: 1,
      lowestSameLimit: 13,
      sex: '',
      compareModeCode: '',
      targetKinshipId: '',
      selectByTargetReationType: [],
      targetIds: [],
      panelNameSTR: [] // 试剂盒列表
    }
  },
  created() {},
  mounted() {
    if (sessionStorage.getItem('panelNameSTR')) {
      // STR试剂盒列表
      const arr = sessionStorage.getItem('panelNameSTR')
      this.panelNameSTR = JSON.parse(arr)
    }
    if (sessionStorage.getItem('instoreDataTypeList')) {
      // 入库数据类型
      const arr = sessionStorage.getItem('instoreDataTypeList')
      this.instoreDataTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectLabName')) {
      // 案件人员类型
      const arr = sessionStorage.getItem('selectLabName')
      this.selectLabName = JSON.parse(arr)
    }
    if (sessionStorage.getItem('selectByTargetReationType')) {
      // 目标亲缘身份
      const arr = sessionStorage.getItem('selectByTargetReationType')
      this.selectByTargetReationType = JSON.parse(arr)
    }
    this.getSelectByPopulationInfo() // 获取种群下拉框
    this.headers.Authorization = sessionStorage.getItem('accessToken')
  },
  methods: {
    goRecord() {
      this.$router.push({
        path: '/quickmanage/KinshipRecord'
      })
    },
    // 添加基因座 - 弹窗 - 输入框 - 确认按钮
    handSearchGene() {
      // eslint-disable-next-line no-unused-vars
      let num = 0
      this.addMixedSTRLocusList.forEach(item => {
        if (item.locusName === this.geneText) {
          num += 1
          if (this.addLocusCheckAllList.indexOf(item.locusName) === -1) {
            this.addLocusCheckAllList.push(item.locusName)
          }
        }
      })
      if (num === 0) {
        this.$Modal.warning({
          title: '提示',
          content: '此基因座不存在，请重新输入！'
        })
      } else {
        // this.addLocusCheckAllList.push(this.geneText)
      }
    },
    // 目标样本亲缘身份 下拉框
    changeTargetKinship(index) {
      // console.log(this.toCompareKinship, 'toCompareKinship')
      // console.log(index, '？？？？')
      switch (index) {
        case '01':
          sessionStorage.setItem('quickKinshipThanin', '父亲')
          break
        case '02':
          sessionStorage.setItem('quickKinshipThanin', '母亲')
          break
        case '03':
          sessionStorage.setItem('quickKinshipThanin', '子女')
          break
        case '04':
          sessionStorage.setItem('quickKinshipThanin', '父-子女')
          break
        case '05':
          sessionStorage.setItem('quickKinshipThanin', '母、子女')
          break
        case '06':
          sessionStorage.setItem('quickKinshipThanin', '子女')
          break
        default:
          sessionStorage.setItem('quickKinshipThanin', '')
          break
      }
      const beforeID = this.toCompareKinship
      // console.log(sessionStorage.getItem('quickKinshipThanin'))
      if (beforeID === '01' && index === '03') {
        // 父>子女
        this.compareModeCode = 3
      } else if (beforeID === '01' && index === '05') {
        // 父>母-子女
        this.compareModeCode = 2
      } else if (beforeID === '02' && index === '03') {
        // 母>子女
        this.compareModeCode = 3
      } else if (beforeID === '02' && index === '04') {
        // 母>父-子女
        this.compareModeCode = 2
      } else if (beforeID === '03' && index === '01') {
        // 子女>父
        this.compareModeCode = 3
      } else if (beforeID === '03' && index === '02') {
        // 子女>母
        this.compareModeCode = 3
      } else if (beforeID === '03' && index === '06') {
        // 子女>父母
        this.compareModeCode = 2
      } else if (beforeID === '04' && index === '02') {
        // 父-子女>母
        this.compareModeCode = 2
      } else if (beforeID === '05' && index === '01') {
        // 母-子女>父
        this.compareModeCode = 2
      } else if (beforeID === '06' && index === '03') {
        // 父母>子女
        this.compareModeCode = 2
      }
    },
    changeKinship(index) {
      console.dir(index)
      this.sampleList = []
      this.targetIds = []
      switch (index) {
        case '01':
          this.selectByTargetReationType.forEach(item => {
            if (item.dictCode === '03' || item.dictCode === '05') {
              this.targetIds.push(item)
            }
          })
          this.sampleList.push({ genelist: [] })
          this.sampleList[0].kinship = '父亲'
          this.sampleList[0].pendingSampleaRelation = '01'
          sessionStorage.setItem('quickKinshipSource', '父亲')
          break
        case '02':
          this.selectByTargetReationType.forEach(item => {
            if (item.dictCode === '03' || item.dictCode === '04') {
              this.targetIds.push(item)
            }
          })
          this.sampleList.push({ genelist: [] })
          this.sampleList[0].kinship = '母亲'
          this.sampleList[0].pendingSampleaRelation = '02'
          sessionStorage.setItem('quickKinshipSource', '母亲')
          sessionStorage.setItem('quickKinshipThanin', '父、子女')
          break
        case '03':
          this.selectByTargetReationType.forEach(item => {
            if (
              item.dictCode === '01' ||
              item.dictCode === '02' ||
              item.dictCode === '06'
            ) {
              this.targetIds.push(item)
            }
          })
          this.sampleList.push({ genelist: [] })
          this.sampleList[0].kinship = '子女'
          this.sampleList[0].pendingSampleaRelation = '03'
          sessionStorage.setItem('quickKinshipSource', '子女')
          break
        case '04':
          this.selectByTargetReationType.forEach(item => {
            if (item.dictCode === '02') {
              this.targetIds.push(item)
            }
          })
          this.targetKinshipId = '02'
          this.sampleList.push({ genelist: [] }, { genelist: [] })
          this.sampleList[0].kinship = '父亲'
          this.sampleList[1].kinship = '子女'
          this.sampleList[0].pendingSampleaRelation = '01'
          this.sampleList[1].pendingSamplebRelation = '03'
          sessionStorage.setItem('quickKinshipSource', '父、子女')
          break
        case '05':
          this.selectByTargetReationType.forEach(item => {
            if (item.dictCode === '01') {
              this.targetIds.push(item)
            }
          })
          this.targetKinshipId = '01'
          this.sampleList.push({ genelist: [] }, { genelist: [] })
          this.sampleList[0].kinship = '母亲'
          this.sampleList[1].kinship = '子女'
          this.sampleList[0].pendingSampleaRelation = '02'
          this.sampleList[1].pendingSamplebRelation = '03'
          sessionStorage.setItem('quickKinshipSource', '母、子女')
          break
        case '06':
          this.selectByTargetReationType.forEach(item => {
            if (item.dictCode === '03') {
              this.targetIds.push(item)
            }
          })
          this.targetKinshipId = '03'
          this.sampleList.push({ genelist: [] }, { genelist: [] })
          this.sampleList[0].kinship = '父亲'
          this.sampleList[0].pendingSampleaRelation = '01'
          this.sampleList[1].kinship = '母亲'
          this.sampleList[1].pendingSamplebRelation = '02'
          sessionStorage.setItem('quickKinshipSource', '父母')
          break
        default:
          break
      }
    },
    importCodis(index) {
      this.currentItemIndex = index
      this.importCodisModal = true
      this.codisData = []
      this.fileName = ''
    },
    handleUpload(file) {
      // codis文件上传之前
      this.fileName = file.name
    },
    handleSuccess(res) {
      // codis文件上传成功后
      // console.log(res, 'srsdfdsf')
      this.codisData = res.result
    },
    submitCodisData() {
      if (this.codisDataSelectd.length > 0) {
        this.sampleList[
          this.currentItemIndex
        ].genelist = this.codisDataSelectd[0].geneInfoList
        this.sampleList[
          this.currentItemIndex
        ].sampleNo = this.codisDataSelectd[0].sampleNo
        this.importCodisModal = false
        // console.log(this.sampleList, 'kdskdfsf')
      } else {
        this.$Modal.info({
          title: '提示',
          content: '请先选择要导入的数据~'
        })
      }
    },
    // 提交比对
    submitAddMixedSTRLocus() {
      // console.log(this.toCompareKinship, '待比对样本')
      // console.log(this.targetKinshipId, '目标样本')
      console.log(this.sexCheckAllGroup, '性别----')
      if (!this.toCompareKinship || !this.targetKinshipId) {
        this.$Modal.warning({
          title: '提示',
          content: '请先选择亲缘比对模式！'
        })
        return false
      } else {
        // eslint-disable-next-line no-unused-vars
        let num = 0
        // eslint-disable-next-line prefer-const
        let text1 = ''
        // eslint-disable-next-line prefer-const
        let text2 = ''
        if (this.toCompareKinship === '01' && this.targetKinshipId === '03') {
          num = 3 // 父亲 子女
          text1 = '父亲'
          text2 = '子女'
        } else if (
          this.toCompareKinship === '01' &&
          this.targetKinshipId === '05' // 父亲 母子'
        ) {
          num = 2
          text1 = '父亲'
          text2 = '母/子女'
        } else if (
          this.toCompareKinship === '02' &&
          this.targetKinshipId === '03' // 母亲 子女
        ) {
          num = 3
          text1 = '母亲'
          text2 = '子女'
        } else if (
          this.toCompareKinship === '02' &&
          this.targetKinshipId === '04' // 母亲 父子
        ) {
          num = 2
          text1 = '母亲'
          text2 = '父子/女'
        } else if (
          this.toCompareKinship === '03' &&
          this.targetKinshipId === '01'
        ) {
          num = 3 // 子女  父亲
          text1 = '子女'
          text2 = '父亲'
        } else if (
          this.toCompareKinship === '03' &&
          this.targetKinshipId === '02'
        ) {
          num = 3 // 子女 母亲
          text1 = '子女'
          text2 = '母亲'
        } else if (
          this.toCompareKinship === '03' &&
          this.targetKinshipId === '06'
        ) {
          num = 2 // 子女 父母
          text1 = '子女'
          text2 = '父母'
        } else if (
          this.toCompareKinship === '04' &&
          this.targetKinshipId === '02'
        ) {
          num = 2 // 父子女 - 母亲
          text1 = '父子/女'
          text2 = '母亲'
        } else if (
          this.toCompareKinship === '05' &&
          this.targetKinshipId === '01'
        ) {
          num = 2 // 母子女 - 父亲
          text1 = '母子/女'
          text2 = '父亲'
        } else if (
          this.toCompareKinship === '06' &&
          this.targetKinshipId === '03'
        ) {
          num = 2 // 父母 - 子女
          text1 = '父母'
          text2 = '子女'
        }
        // console.log(num)
        sessionStorage.setItem('toCompareKinship', text1)
        sessionStorage.setItem('targetKinshipId', text2)
        const obj = {}
        obj.mostDiffLimit = this.mostDiffLimit
        obj.lowestSameLimit = this.lowestSameLimit
        obj.targetDataType = this.dataCheckAllList
        obj.targetLabServerNo = this.labCheckAllList
        obj.sex = this.sexCheckAllGroup
        const arr1 = this.sampleList[0].genelist
        let jsonstr1 = {}
        arr1.forEach(item => {
          jsonstr1[item.name] = item.value.join('/')
        })
        jsonstr1 = JSON.stringify(jsonstr1)

        const info = {}
        info.pendingSampleaRelation = this.sampleList[0].pendingSampleaRelation
        info.pendingSampleaNo = this.sampleList[0].sampleNo
        info.pendingSampleaName = this.sampleList[0].sampleName
        info.pendingSampleaGeneInfo = jsonstr1
        info.panelaId = this.sampleList[0].panelId
        info.panelaName = this.sampleList[0].panelName
        info.compareParams = JSON.stringify(obj)
        info.populationFrequencyId = this.populationFrequencyId
        // info.compareMode = this.compareModeCode
        info.compareMode = num
        info.targetReation = this.targetKinshipId
        if (this.sampleList.length > 1) {
          const arr2 = this.sampleList[1].genelist
          let jsonstr2 = {}
          arr2.forEach(item => {
            jsonstr2[item.name] = item.value.join('/')
          })
          jsonstr2 = JSON.stringify(jsonstr2)
          info.pendingSamplebRelation = this.sampleList[1].pendingSamplebRelation
          info.pendingSamplebNo = this.sampleList[1].sampleNo
          info.pendingSamplebName = this.sampleList[1].sampleName
          info.pendingSamplebGeneInfo = jsonstr2
          info.panelbId = this.sampleList[1].panelId
          info.panelbName = this.sampleList[1].panelName
        }
        // console.log(info.compareMode, '数值')
        // console.log(info, '对象')
        this.$axios
          .post('/database/rapid/relative/submitSTRCompareQueue', info)
          .then(res => {
            // console.log(res)
            sessionStorage.setItem('qucikKinshipResultId', res.result)
            this.$router.push({
              path: '/quickmanage/KinshipResult'
            })
          })
      }
    },
    searchDataBase(index, type, dataType) {
      this.currentItemIndex = index
      // console.log(
      //   this.sampleList[index].sampleNo,
      //   'this.sampleListthis.sampleList'
      // )
      if (!this.sampleList[index].sampleNo) {
        this.$Modal.info({
          title: '抱歉',
          content: '样本编号不能为空~'
        })
        return false
      }
      if (this.sampleList[index].sampleNo.length > 5) {
        console.log('进入盘地暖---')
        this.$axios
          .get('/database/rapid/str/selectSTRSampleNo', {
            params: {
              dataType: dataType,
              sampleNo: this.sampleList[index].sampleNo
            }
          })
          .then(res => {
            // const obj = res.result
            // obj.mixPerson = ''
            // obj.genelist = JSON.parse(obj.geneInfo)
            // obj.genelist.forEach(item => {
            //   if (item.value.length > 4) {
            //     obj.mixPerson = '3'
            //   } else {
            //     obj.mixPerson = '2'
            //   }
            // })
            const obj = res.result
            if (!obj) {
              this.$Modal.info({
                title: '抱歉',
                content: '暂未查询到基因信息~'
              })
              return false
            }
            obj.geneInfo = JSON.parse(obj.geneInfo)
            obj.geneInfo.forEach(item => {
              if (!item.value) {
                item.value = []
              }
            })
            // this.sampleList[this.currentItemIndex].genelist = JSON.parse(res.result.geneInfo)
            // this.sampleList[this.currentItemIndex].sampleName = res.result.sampleName
            // this.sampleList[this.currentItemIndex].panelId = res.result.panelId
            // this.sampleList[this.currentItemIndex].panelName = res.result.panelName

            this.sampleList[this.currentItemIndex].genelist = obj.geneInfo
            this.sampleList[this.currentItemIndex].sampleName = obj.sampleName
            this.sampleList[this.currentItemIndex].panelId = obj.panelId
            this.sampleList[this.currentItemIndex].panelName = obj.panelName
            this.$forceUpdate()
            // this.genelist = obj.genelist
            // this.sampleName = obj.sampleName
            // this.panelId = obj.panelId
            // this.panelName = obj.panelName
            // console.log(this.sampleList, 'kdskdfsf')
          })
      } else {
        this.$Modal.info({
          title: '抱歉',
          content: '请至少输入6位检材编号~'
        })
      }
    },
    // changeIndexMixPerson(index, value) {
    //   // 贡献者人数切换
    //   console.log(value, typeof value)
    //   let flag = true
    //   if (value === 2) {
    //     this.genelist.forEach(item => {
    //       if (item.value.length >= 5) {
    //         flag = false
    //       }
    //     })
    //   }
    //   if (flag) {
    //     this.mixPerson = value
    //   } else {
    //     this.$Modal.error({
    //       title: '抱歉',
    //       content: '根据位点信息分析贡献者数量应是3人,不可修改为2人'
    //     })
    //   }
    //   this.$forceUpdate()
    // },
    changeIndexKit(index, obj) {
      this.currentItemIndex = index
      this.sampleList[index].panelName = obj.label
      // console.log(this.sampleList, obj, '----------------')
      this.$axios
        .get('/database/panel/locusListByPanel', {
          params: {
            panelId: obj.value
          }
        })
        .then(res => {
          // console.log(res)
          const list = []
          res.result.forEach(item => {
            const itemGene = {}
            itemGene.name = item.locusName
            itemGene.value = []
            list.push(itemGene)
          })
          if (this.sampleList[this.currentItemIndex].genelist) {
            list.forEach(item => {
              if (
                this.sampleList[this.currentItemIndex].genelist.find(
                  x => x.name === item.name
                )
              ) {
                this.sampleList[this.currentItemIndex].genelist
                  .find(x => x.name === item.name)
                  .value.forEach(i => {
                    item.value.push(i)
                  })
              }
            })
          } else {
          }
          this.sampleList[this.currentItemIndex].genelist = list
        })
    },
    getSelectByPopulationInfo() {
      // 获取种群下拉框
      this.$axios
        .get('/database/comm/dict/selectByPopulationInfo', {
          params: {
            geneType: '1'
          }
        })
        .then(res => {
          this.selectByPopulationInfo = res.result
        })
    },
    addMixedSTRLocus(index) {
      this.currentItemIndex = index
      this.addGene = true
      this.geneText = ''
      this.addMixedSTRLocusList = []
      this.indeterminateAddLocus = true
      this.checkAllAddLocus = false
      this.addLocusCheckAllList = []
      this.$axios
        .get('/database/rapid/mix/addMixedSTRLocus', {
          params: {
            type: '1'
          }
        })
        .then(res => {
          // console.log(res)
          this.addMixedSTRLocusList = res.result
        })
    },
    handleCheckAllAddLocus() {
      const arr = this.addMixedSTRLocusList
      if (this.indeterminateAddLocus) {
        this.checkAllAddLocus = false
      } else {
        this.checkAllAddLocus = !this.checkAllAddLocus
      }
      this.indeterminateAddLocus = false
      if (this.checkAllAddLocus) {
        const newArr = []
        arr.forEach(item => {
          newArr.push(item.locusName)
        })
        this.addLocusCheckAllList = newArr
      } else {
        this.addLocusCheckAllList = []
      }
    },
    checkAllGroupChangeAddLocus(data) {
      // console.log(data, 'data')
      if (data.length === this.addMixedSTRLocusList.length) {
        this.indeterminateAddLocus = false
        this.checkAllAddLocus = true
      } else if (data.length > 0) {
        this.indeterminateAddLocus = true
        this.checkAllAddLocus = false
      } else {
        this.indeterminateAddLocus = false
        this.checkAllAddLocus = false
      }
    },
    handleCheckAllData() {
      const arr = this.instoreDataTypeList
      if (this.indeterminateData) {
        this.checkAllData = false
      } else {
        this.checkAllData = !this.checkAllData
      }
      this.indeterminateData = false
      if (this.checkAllData) {
        const newArr = []
        arr.forEach(item => {
          newArr.push(item.typeCode)
        })
        this.dataCheckAllList = newArr
      } else {
        this.dataCheckAllList = []
      }
    },
    checkAllGroupChangeData(data) {
      // console.log(data, 'data')
      if (data.length === this.instoreDataTypeList.length) {
        this.indeterminateData = false
        this.checkAllData = true
      } else if (data.length > 0) {
        this.indeterminateData = true
        this.checkAllData = false
      } else {
        this.indeterminateData = false
        this.checkAllData = false
      }
    },

    handleCheckAllLab() {
      const arr = this.selectLabName
      if (this.indeterminateLab) {
        this.checkAllLab = false
      } else {
        this.checkAllLab = !this.checkAllLab
      }
      this.indeterminateLab = false
      if (this.checkAllLab) {
        const newArr = []
        arr.forEach(item => {
          newArr.push(item.labServerNo)
        })
        this.labCheckAllList = newArr
      } else {
        this.labCheckAllList = []
      }
    },
    checkAllGroupChangeLab(data) {
      // console.log(data, 'data')
      if (data.length === this.selectLabName.length) {
        this.indeterminateLab = false
        this.checkAllLab = true
      } else if (data.length > 0) {
        this.indeterminateLab = true
        this.checkAllLab = false
      } else {
        this.indeterminateLab = false
        this.checkAllLab = false
      }
    },
    submitAddLocus() {
      // console.log(
      //   this.addLocusCheckAllList,
      //   'addLocusCheckAllListaddLocusCheckAllList'
      // )
      this.addLocusCheckAllList.forEach(item => {
        const obj = {}
        obj.name = item
        obj.value = []
        // this.genelist.push(obj)
        this.sampleList[this.currentItemIndex].genelist.push(obj)
      })
      this.addGene = false
    },
    // 性别 监听事件
    sexCheckAllGroupChange(data) {
      console.log(data)
      if (data.length === 2) {
        this.sexIndeterminate = false
        this.sexCheckAll = true
      } else if (data.length > 0) {
        this.sexIndeterminate = true
        this.sexCheckAll = false
      } else {
        this.sexIndeterminate = false
        this.sexCheckAll = false
      }
    },
    // 全选按钮
    handleSexCheckAll() {
      if (this.sexIndeterminate) {
        this.sexCheckAll = false
      } else {
        this.sexCheckAll = !this.sexCheckAll
      }
      this.sexIndeterminate = false

      if (this.sexCheckAll) {
        this.sexCheckAllGroup = ['1', '2']
      } else {
        this.sexCheckAllGroup = []
      }
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/same.less';
.part .form-part .gene-part .gene-row .gene-col .gene-label {
  width: 100px !important;
}
</style>
