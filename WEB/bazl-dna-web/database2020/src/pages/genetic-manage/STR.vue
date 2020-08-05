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
            <span>基础遗传数据管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/geneticmanage/STR">STR基因座管理</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">基因座名称</span>
              <Input
                type="text"
                placeholder="请输入基因座名称"
                class="item-input"
                v-model.trim="searchInfo.locusName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">核心基因座</span>
              <Select
                class="item-select"
                v-model.trim="searchInfo.coreLocusFlag"
              >
                <Option value="1">是</Option>
                <Option value="0">否</Option>
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <button class="btn btn-blue-bg" @click="handSearch">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">
                重置
              </button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">查询结果</div>
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
    <Modal
      v-model="editGene"
      width="600"
      class="bazl-modal rest-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">编辑基因座
            <Icon type="md-close-circle" class="modal-close" @click="editGene = false"/>
      </div>
      <Row class="item-row">
        <Col span="24">
          <span class="item-label">基因座名称</span>
          <Input
            type="text"
            class="item-input"
            v-model.trim="formInfo.locusName"
          ></Input>
        </Col>
      </Row>
      <Row class="item-row">
        <Col span="24">
          <span class="item-label">其他名称</span>
          <Input
            type="text"
            class="item-input"
            v-model.trim="formInfo.nationalLocusName"
          ></Input>
        </Col>
      </Row>
      <Row class="item-row">
        <Col span="24">
          <span class="item-label" style="width:140px;">是否为核心基因座</span>
          <RadioGroup v-model.trim="formInfo.coreLocusFlag">
            <Radio :label="1">是</Radio>
            <Radio :label="0">否</Radio>
          </RadioGroup>
        </Col>
      </Row>
      <!-- <Row class="item-row">
        <Col span="24">
          <span class="item-label">取值范围</span>
          <Input
            type="text"
            class="item-input"
            v-model.trim="formInfo.valueScope"
          ></Input>
        </Col>
      </Row> -->
      <Row class="item-row">
        <Col span="24">
          <span class="item-label">基因座顺序</span>
          <Input
            type="text"
            class="item-input"
            v-model.trim="formInfo.locusOrd"
          ></Input>
        </Col>
      </Row>
      <Row class="item-row">
        <Col span="24">
          <span class="item-label">备注</span>
          <Input
            type="textarea"
            class="item-input"
            style="width:400px;"
            :rows="2"
            placeholder="备注信息"
            v-model.trim="formInfo.remarks"
          ></Input>
        </Col>
      </Row>
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="handSave">保存</button>
        <button class="btn btn-blue-border" @click="editGene = false">
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
      searchInfo: {
        pageIndex: 1,
        locusName: null, // 基因座名称
        coreLocusFlag: null // 是否为核心基因座
      },
      formInfo: {},
      editGene: false,
      SidebarParams: {
        open: ['5'],
        active: '5-1'
      },
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
          title: '其他名称',
          key: 'nationalLocusName',
          align: 'center'
        },
        {
          title: '核心基因座',
          key: 'coreLocusFlag',
          align: 'center',
          render: (h, params) => {
            let type = ''
            if (params.row.coreLocusFlag === 1) {
              type = '是'
            } else if (params.row.coreLocusFlag === 0) {
              type = '否'
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
        // {
        //   title: '基因座取值范围',
        //   key: 'valueScope',
        //   align: 'center',
        //   width: '260',
        //   render: (h, params) => {
        //     return h('div', [
        //       h(
        //         'Tooltip',
        //         {
        //           props: { placement: 'right-end' }
        //         },
        //         [
        //           h(
        //             'span',
        //             {
        //               style: {
        //                 display: 'inline-block',
        //                 width: params.column._width * 0.6 + 'px',
        //                 overflow: 'hidden',
        //                 textOverflow: 'ellipsis',
        //                 whiteSpace: 'nowrap'
        //               }
        //             },
        //             params.row.valueScope
        //           ),
        //           h(
        //             'span',
        //             {
        //               slot: 'content',
        //               style: { whiteSpace: 'normal', wordBreak: 'break-all' }
        //             },
        //             params.row.valueScope
        //           )
        //         ]
        //       )
        //     ])
        //   }
        // },
        {
          title: '基因座顺序',
          key: 'locusOrd',
          align: 'center'
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
                    this.handEditGene(params)
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
      allRecordCount: 1,
      saveLsit: []
    }
  },
  created() {},
  mounted() {
    this.handInitData()
  },
  methods: {
    // 表格分页监听
    changePage(val) {
      console.log(val)
      this.searchInfo.pageIndex = val
      this.handInitData()
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .post('database/locus/listAllStrLocus', this.searchInfo)
        .then(res => {
          console.log(res)
          if (res.code === 1) {
            this.tableData = res.result.strDnaLoucsInfoList
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
    },
    // 编辑弹窗
    handEditGene(params) {
      this.editGene = true
      this.saveLsit = []
      this.formInfo = params.row
      // console.log(this.formInfo)
    },
    // 保存编辑内容
    handSave() {
      const strDnaLocusInfo = this.formInfo
      this.saveLsit.push(strDnaLocusInfo)
      this.$axios
        .post('database/locus/updateStrGeneInfo', this.saveLsit)
        .then(res => {
          // console.log(res)
          this.$Message.success('保存成功！')
          this.editGene = false
          this.searchInfo.pageIndex = 1 //
          this.handInitData()
        })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/genemanage.less';
</style>
