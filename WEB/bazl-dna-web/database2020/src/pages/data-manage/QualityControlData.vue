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
            <router-link to="/qualitycontrol">质控数据管理</router-link>
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
                <Select placeholder="请选择质控样本类别" style="width:260px;" v-model="searchForm.qcSampleType">
                   <Option v-for="item in qcSampleTypeList" :key="item.id" :value="item.dictCode">{{item.dictName}}</Option>
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
              <Input type="text" placeholder="请输入样本编号" style="width:140px;" v-model="searchForm.qcSampleNo"></Input>
            </Col>
            <Col span="10">
              <span class="item-label">样本名称</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcSampleNameCondition">
                    <Radio label="0">包含</Radio>
                    <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input type="text" placeholder="请输入样本名称" style="width:140px;" v-model="searchForm.qcSampleName"></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="14">
              <span class="item-label">质控人员类别</span>
              <span class="item-group">
                <Select placeholder="请选择质控人员类别" style="width:260px;" v-model="searchForm.qcPersonType">
                   <Option v-for="item in qcPersonTypeList" :key="item.id" :value="item.dictCode">{{item.dictName}}</Option>
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
              <Input type="text" placeholder="请输入姓名" style="width:140px;" v-model="searchForm.qcPersonName"></Input>
            </Col>
            <Col span="10">
              <span class="item-label">身份证号</span>
              <span class="item-group">
                <RadioGroup v-model="searchForm.qcPersonIdcardNoCondition">
                    <Radio label="0">包含</Radio>
                    <Radio label="1">等于</Radio>
                </RadioGroup>
              </span>
              <Input type="text" placeholder="请输入身份证号" style="width:140px;" v-model="searchForm.qcPersonIdcardNo"></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">所属单位名称</span>
              <Input type="text" placeholder="请输入所属单位名称" style="width:260px;" v-model="searchForm.qcPersonOrgName"></Input>
            </Col>
          </Row>
          <Row class="tc">
            <button class="btn btn-blue-bg" @click.prevent="getSelectList">执行查询</button>
            <button class="btn btn-red-line" style="margin-left:10px"  @click.prevent="handleReset">全部清空</button>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">查询结果</div>
          <Table border  :columns="searchResultCol" :data="searchResultData" class="bazl-table" size="small"></Table>
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
  data () {
    return {
      SidebarParams: {
        open: ['2'],
        active: '2-3'
      },
      qcSampleTypeList: [],
      qcPersonTypeList: [],
      searchForm: {},
      searchResultCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center'
        },
        {
          title: '案件受理编号',
          key: 'caseAcceptNo',
          align: 'center'
        },
        {
          title: '现勘系统编号(K号)',
          key: 'sysXkNo',
          align: 'center'
        },
        {
          title: '三版本编号(A号)',
          key: 'sysCaseAno',
          align: 'center'
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '案件性质',
          key: 'caseProperty',
          align: 'center'
        },
        {
          title: '委托单位',
          key: 'consignOrgNameString',
          align: 'center'
        },
        {
          title: '受理单位',
          key: 'acceptOrgNameString',
          align: 'center'
        },
        {
          title: '受理时间',
          key: '',
          align: 'center'
        },
        {
          title: '受理人',
          key: 'acceptName',
          align: 'center'
        },
        {
          title: '破获状态',
          key: '',
          align: 'center'
        },
        {
          title: '更多详情',
          key: 'action',
          align: 'center',
          className: 'blue-font',
          render: (h, params) => {
            return h('div', [
              h('span', {
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: event => {
                    this.$router.push({
                      name: 'SeeCaseDetails'
                    })
                  }
                }
              }, '查看')
            ])
          }
        }
      ],
      searchResultData: [{}, {}],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1
    }
  },
  methods: {
    handleReset () {
      Object.keys(this.searchForm).forEach(key => { this.searchForm[key] = '' })
    },
    getSelectList() {
      this.searchForm.pageIndex = this.currentPage
      this.searchForm.rows = 15
      this.$axios.post('/qc/selectList', this.searchForm).then(res => {
        if (res.code === 200) {
          // console.log(res)
          this.searchResultData = res.data.qcSampleInfoList
          this.allRecordCount = res.data.totalCount
          this.currentPage = res.data.pageIndex
          this.pageCount = Math.ceil(res.data.totalCount / 15)
        }
      })
    },
    changePage () {}
  },
  created() {
    if (sessionStorage.getItem('qcSampleTypeList')) { // 案件性质
      const arr = sessionStorage.getItem('qcSampleTypeList')
      this.qcSampleTypeList = JSON.parse(arr)
    }
    if (sessionStorage.getItem('qcPersonTypeList')) { // 案件性质
      const arr = sessionStorage.getItem('qcPersonTypeList')
      this.qcPersonTypeList = JSON.parse(arr)
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamanage';
</style>
