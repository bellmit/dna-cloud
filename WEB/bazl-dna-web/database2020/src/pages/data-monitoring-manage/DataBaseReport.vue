<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据监控管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/monitoring/database">建库上报监控</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            查询条件
          </div>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">案件性质</span>
              <Select class="item-select" v-model="searchForm.caseProperty">
                <Option
                  v-for="item in casePropertyList"
                  :value="item.dictCode"
                  :key="item.dictCode"
                >{{item.dictName}}</Option>
              </Select>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件名称</span>
              <Input
                type="text"
                placeholder="请输入案件名称"
                class="item-input"
                v-model="searchForm.caseName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件受理编号</span>
              <Input
                type="text"
                placeholder="请输入案件受理编号"
                class="item-input"
                v-model="searchForm.caseNumber"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">检材编号</span>
              <Input
                type="text"
                placeholder="请输入案件受理编号"
                class="item-input"
                v-model="searchForm.sampleNumber"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">检材名称</span>
              <Input
                type="text"
                placeholder="请输入检材名称"
                class="item-input"
                v-model="searchForm.sampleName"
              ></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">受理人</span>
              <Select class="item-select" v-model="searchForm.receiver">
                <Option>2</Option>
                <Option>2</Option>
              </Select>
            </Col>
          </Row>
          <Row class="tc btn-row">
            <Col span="24" class="item-col">
              <button class="btn btn-blue-bg">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">重置</button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">查询结果</div>
          <Row class="tab-title">
            <Col span="12">
              <span
                v-for="(item, index) in subTitles"
                :key="index"
                :class="[{ active: index == active }, item.class]"
                @click="handleTab(index)"
                >{{ item.title }}</span
              >
            </Col>
            <Col span="12" class="tr">
              <button class="btn btn-blue-bg">批量上报</button>
            </Col>
          </Row>
          <!-- 待上报 -->
          <div class="wait tab-part" v-if="active == 0">
            <Row class="tab-des">
              <Col span="24">
                待上报案件数:
                <span>3</span>个, 待上报检材数: <span>22</span>个
              </Col>
            </Row>
            <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            >
              <template slot-scope="{}" slot="action">
                <span class="blue-font" style="cursor: pointer" @click="$refs.detailPage.open()">查看</span>
              </template>
            </Table>
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
          <!-- 上报失败 -->
          <div class="wait tab-part" v-if="active == 1">
            <Row class="tab-des">
              <Col span="24">
                上报失败案件数:
                <span>3</span>个, 上报失败检材数: <span>22</span>个
              </Col>
            </Row>
            <Table
              border
              :columns="tableFailCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            >
              <template slot-scope="{}" slot="action">
                <span class="blue-font" style="cursor: pointer" @click="goFali">查看详情</span>
              </template>
            </Table>
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
          <!-- 上报成功的 -->
          <div class="wait tab-part" v-if="active == 2">
            <Row class="tab-des">
              <Col span="24">
                上报成功案件数:
                <span>3</span>个, 上报成功检材数: <span>22</span>个
              </Col>
            </Row>
            <Table
              border
              :columns="tableSuccessCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            >
              <template slot-scope="{}" slot="action">
                <span class="blue-font" style="cursor: pointer" @click="goSuccess">查看详情</span>
                <span class="blue-font" style="cursor: pointer;margin:0 8px" >打印入库单</span>
              </template>
            </Table>
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
        </div>
        <Row>
          <DetailModal ref="detailPage"></DetailModal>
        </Row>
      </Col>
    </Row>
  </div>
</template>
<script>
import DetailModal from './detailModal'
import { tableFailCol, tableSuccessCol } from '../../mock/table.js'
export default {
  components: { DetailModal },
  data() {
    return {
      searchForm: {
        caseProperty: '',
        caseName: '',
        caseNumber: '',
        sampleNumber: '',
        sampleName: '',
        receiver: ''
      },
      active: 0,
      subTitles: [
        {
          title: '待上报案件',
          class: 'blue'
        },
        {
          title: '上报失败案件',
          class: 'yellow'
        },
        {
          title: '上报成功案件',
          class: 'green'
        }
      ],
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '建库人员类型',
          key: '',
          align: 'center'
        },
        {
          title: '血卡条码号',
          key: '',
          align: 'center'
        },
        {
          title: '姓名',
          key: '',
          align: 'center'
        },
        {
          title: '性别',
          key: '',
          align: 'center'
        },
        {
          title: '身份证号',
          key: '',
          align: 'center'
        },
        {
          title: '采集时间',
          key: '',
          align: 'center'
        },
        {
          title: '血卡位置',
          key: '',
          align: 'center'
        },
        {
          title: '详情',
          slot: 'action',
          align: 'center'
          // render: (h, params) => {
          //   return h(
          //     'span',
          //     {
          //       class: 'blue-font',
          //       style: {
          //         cursor: 'pointer'
          //       },
          //       on: {
          //         click: () => {
          //           this.$router.push({
          //             path: '/monitoring/case/wait'
          //           })
          //         }
          //       }
          //     },
          //     '查看'
          //   )
          // }
        },
        {
          title: 'STR',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Checkbox', {
                props: {},
                style: {},
                on: {}
              }),
              h('span', {}, '上报')
            ])
          }
        },
        {
          title: 'Y-STR',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Checkbox', {
                props: {},
                style: {},
                on: {}
              }),
              h('span', {}, '上报')
            ])
          }
        }
      ],
      tableFailCol,
      tableSuccessCol,
      tableData: [{}, {}],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      casePropertyList: []
    }
  },
  created() {
    if (sessionStorage.getItem('caseNatureQuery')) {
      // 案件性质
      const arr = sessionStorage.getItem('caseNatureQuery')
      this.casePropertyList = JSON.parse(arr)
    }
  },
  methods: {
    goFali() {
      this.$router.push({
        path: '/monitoring/case/fail'
      })
    },
    goSuccess() {
      this.$router.push({
        path: '/monitoring/case/success'
      })
    },
    handleReset() {
      Object.keys(this.searchForm).forEach(key => {
        this.searchForm[key] = ''
      })
    },
    handleTab(index) {
      this.active = index
    },
    changePage() {}
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamonitoring';
</style>
