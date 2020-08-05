<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据监控管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/monitoring/case">案件上报监控</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">查询条件</div>
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
                v-model="searchForm.caseAcceptNo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">检材编号</span>
              <Input
                type="text"
                placeholder="请输入检材编号"
                class="item-input"
                v-model="searchForm.sampleNo"
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
              <button class="btn btn-blue-bg" @click.prevent="getCaseReport">查询</button>
              <button class="btn btn-blue-border" @click="handleReset">重置</button>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">查询结果</div>
          <div class="tab-title">
            <span
              v-for="(item, index) in subTitles"
              :key="index"
              :class="[{ active: index == active }, item.class]"
              @click="handleTab(index)"
            >{{ item.title }}</span>
          </div>
          <!-- 待上报 -->
          <div class="wait tab-part" v-if="active == 0">
            <Row class="tab-des">
              <Col span="24">
                待上报案件数:
                <span>{{waitCaseCount}}</span>个, 待上报检材数:
                <span>{{waitSampleCount}}</span>个
              </Col>
            </Row>
            <Table border :columns="tableCol" :data="waitReportCaseList" class="bazl-table" size="small">
              <template slot-scope="{}" slot="action">
                <span class="blue-font" style="cursor: pointer" @click="goWait">入库操作</span>
              </template>
            </Table>
            <div class="page">
              <span>
                当前第{{ currentPageWait }}页/共{{ pageCountWait }}页/共{{
                allRecordCountWait
                }}条信息
              </span>
              <Page
                :total="allRecordCountWait"
                show-elevator
                prev-text="上一页"
                next-text="下一页"
                class-name="bazl_page"
                :current="currentPageWait"
                @on-change="changePageWait"
                :page-size="15"
              />
            </div>
          </div>
          <!-- 上报失败 -->
          <div class="wait tab-part" v-if="active == 1">
            <Row class="tab-des">
              <Col span="24">
                上报失败案件数:
                <span>{{failCaseCount}}</span>个, 上报失败检材数:
                <span>{{failSampleCount}}</span>个
              </Col>
            </Row>
            <Table border :columns="tableFailCol" :data="failedReportCaseList" class="bazl-table" size="small">
              <template slot-scope="{}" slot="action">
                <span class="blue-font" style="cursor: pointer" @click="goFali">查看详情</span>
              </template>
            </Table>
            <div class="page">
              <span>
                当前第{{ currentPageFail }}页/共{{ pageCountFail }}页/共{{
                allRecordCountFail
                }}条信息
              </span>
              <Page
                :total="allRecordCountFail"
                show-elevator
                prev-text="上一页"
                next-text="下一页"
                class-name="bazl_page"
                :current="currentPageFail"
                @on-change="changePageFail"
                :page-size="15"
              />
            </div>
          </div>
          <!-- 上报成功的 -->
          <div class="wait tab-part" v-if="active == 2">
            <Row class="tab-des">
              <Col span="24">
                上报成功案件数:
                <span>{{successCaseCount}}</span>个, 上报成功检材数:
                <span>{{successSampleCount}}</span>个
              </Col>
            </Row>
            <Table
              border
              :columns="tableSuccessCol"
              :data="successReportCaseList"
              class="bazl-table"
              size="small"
            >
            <template slot-scope="{}" slot="action">
              <span class="blue-font" style="cursor: pointer" @click="goSuccess">查看详情</span>
              <span class="blue-font" style="cursor: pointer;margin:0 8px">打印入库单</span>
            </template>
            </Table>
            <div class="page">
              <span>
                当前第{{ currentPageSuccess }}页/共{{ pageCountSuccess }}页/共{{
                allRecordCountSuccess
                }}条信息
              </span>
              <Page
                :total="allRecordCountSuccess"
                show-elevator
                prev-text="上一页"
                next-text="下一页"
                class-name="bazl_page"
                :current="currentPageSuccess"
                @on-change="changePageSuccess"
                :page-size="15"
              />
            </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import { tableCol, tableFailCol, tableSuccessCol } from '../../mock/table.js'
export default {
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
      tableCol,
      tableFailCol,
      tableSuccessCol,
      waitReportCaseList: [],
      failedReportCaseList: [],
      successReportCaseList: [],
      waitCaseCount: 0,
      waitSampleCount: 0,
      failCaseCount: 0,
      failSampleCount: 0,
      successCaseCount: 0,
      successSampleCount: 0,
      currentPageWait: 1,
      pageCountWait: 1,
      allRecordCountWait: 0,
      currentPageFail: 1,
      pageCountFail: 1,
      allRecordCountFail: 0,
      currentPageSuccess: 1,
      pageCountSuccess: 1,
      allRecordCountSuccess: 0,
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
    goWait() {
      this.$router.push({
        path: '/monitoring/case/wait'
      })
    },
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
    changePageWait(p) {
      this.currentPageWait = p
    },
    changePageFail(p) {
      this.currentPageFail = p
    },
    changePageSuccess(p) {
      this.currentPageSuccess = p
    },
    getCaseReport() {
      this.searchForm.pageIndex = this.currentPageWait
      this.$axios.post('/database/caseReport/queryCaseReport', this.searchForm).then(res => {
        // console.log(res)
        this.failedReportCaseList = res.result.failedReportCaseList
        this.successReportCaseList = res.result.successReportCaseList
        this.waitReportCaseList = res.result.waitReportCaseList
        this.waitCaseCount = res.result.waitReportCaseList[0].pageInfo.allRecordCount
        this.failCaseCount = res.result.failedReportCaseList[0].pageInfo.allRecordCount
        this.successCaseCount = res.result.successReportCaseList[0].pageInfo.allRecordCount
        this.pageCountWait = res.result.waitReportCaseList[0].pageInfo.pageCount
        this.allRecordCountWait = res.result.waitReportCaseList[0].pageInfo.allRecordCount
        this.currentPageWait = res.result.waitReportCaseList[0].pageInfo.curPage
        this.pageCountFail = res.result.failedReportCaseList[0].pageInfo.pageCount
        this.allRecordCountFail = res.result.failedReportCaseList[0].pageInfo.allRecordCount
        this.currentPageFail = res.result.failedReportCaseList[0].pageInfo.curPage
        this.pageCountSuccess = res.result.successReportCaseList[0].pageInfo.pageCount
        this.allRecordCountSuccess = res.result.successReportCaseList[0].pageInfo.allRecordCount
        this.currentPageSuccess = res.result.successReportCaseList[0].pageInfo.curPage
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamonitoring';
</style>
