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
            <Icon type="md-arrow-forward" />
            <router-link to="/quickmanage/KinshipRecord"
              >比中记录查询</router-link
            >
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">
            查询条件
          </div>
          <div class="content">
            <Form :model="searchForm" :label-width="140">
            <Row>
              <Col span="8">
                <FormItem label="实验室名称">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="样本编号">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="样本名称">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem label="案件编号">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="案件名称">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="数据类型">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem label="待比对样本亲缘身份">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="目标样本亲缘身份">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="提交时间">
                    <Input v-model="searchForm.input" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
        </Form>
        <Row class="btn-row">
          <button class="btn btn-blue-bg">查询</button>
          <button class="btn btn-blue-border">重置</button>
        </Row>
          </div>
          <div class="title">
            比中结果列表
          </div>
          <div class="content">
            <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table"
            size="small"
          ></Table>
          </div>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回
            </button>
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
      searchForm: {},
      id: '',
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 40
        },
        {
          title: '样本编号',
          key: 'labServerName',
          align: 'center'
        },
        {
          title: '样本名称',
          key: 'personTypeName',
          align: 'center'
        },
        {
          title: '匹配下限',
          key: 'matchSampleNo',
          align: 'center',
          width: 60
        },
        {
          title: '容差',
          key: 'matchSampleName',
          align: 'center',
          width: 40
        },
        {
          title: '目标数据类型',
          key: 'matchCaseNo',
          align: 'center'
        },
        {
          title: '比对实验室范围',
          key: 'matchCaseName',
          align: 'center'
        },
        {
          title: '待比对样本亲缘身份',
          key: 'matchedLocusCount',
          align: 'center'
        },
        {
          title: '目标样本亲缘身份',
          key: '',
          align: 'center'
        },
        {
          title: '比中样本数量',
          key: '',
          align: 'center'
        },
        {
          title: '提交时间',
          key: '',
          align: 'center'
        },
        {
          title: '比中详情',
          key: '',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('span', {
                class: 'blue-font',
                style: {
                  cursor: 'pointer',
                  marginRight: '10px'
                },
                on: {
                  click: (event) => {
                    this.$router.push({
                      path: '/quickmanage/KinshipRecordView'
                    })
                  }
                }
              }, '查看'),
              h('span', {
                class: 'green-font',
                style: {
                  cursor: 'pointer'
                }
              }, '重新比对')
            ])
          }
        }
      ],
      tableData: [{}],
      timer: '',
      caseCount: 0,
      evidenceCount: 0,
      personCount: 0,
      count: 0, // 比中总条数
      loadingModal: true
    }
  },
  mounted() {
  },
  methods: {
    getResult() {
    },
    goBack() {
      this.$router.push({
        path: '/quickmanage/Kinship'
      })
    }
  },
  beforeDestroy() {
    clearInterval(this.timer)
  }
}
</script>
<style lang="less" scoped>
.part {
  padding: 4px 10px;
  margin-bottom: 50px;
  height: calc(100vh - 100px);
  overflow-y: scroll;
  .nav {
    margin: 4px 0;
  }
  .result-part {
    background-color: #fff;
    border-radius: 4px;
    padding-bottom: 15px;
    .title {
      border-bottom: 1px solid #3086c1;
      padding: 8px;
    }
    .content{
      padding:10px 15px;
    }
    .sub {
      padding: 6px 15px;
      color: #333;
    }
  }
}
</style>
