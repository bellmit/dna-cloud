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
            <Icon type="md-arrow-forward" />
            <router-link to="/monitoring/case/wait">入库操作</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            案件信息
          </div>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">案件性质</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件名称</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">案件受理编号</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">检材编号</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">检材名称</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">受理人</span>
              <Input type="text" readonly class="item-input"></Input>
            </Col>
          </Row>
        </div>
        <div class="result-part">
          <div class="title">检材列表</div>
          <div class="wait tab-part">
            <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
              size="small"
            ></Table>
            <!-- <div class="page">
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
            </div> -->
            <Row class="tc btn-row">
              <Col span="24">
                <button class="btn btn-blue-bg">提交</button>
                <button class="btn btn-blue-border" @click="back">返回</button>
              </Col>
            </Row>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '检材类型',
          key: '',
          align: 'center'
        },
        {
          title: '检材编号',
          className: 'blue-font',
          key: '',
          align: 'center'
        },
        {
          title: '检材名称',
          key: '',
          align: 'center'
        },
        {
          title: '入库数据类型',
          key: '',
          width: 440,
          renderHeader: (h, params) => {
            return h('div', [
              h('span', '入库数据类型'),
              h(
                'span',
                {
                  class: 'red-font'
                },
                '(重新上报需与上一次 上报数据类型一致，否则无法上报国家库)'
              )
            ])
          },
          render: (h, params) => {
            return h('div', [
              h(
                'Select',
                {
                  props: {
                    transfer: true,
                    placeholder: '请选择'
                  },
                  style: {
                    display: 'inline-block',
                    width: '120px',
                    margin: '0 10px'
                  },
                  on: {
                    'on-change': value => {}
                  }
                },
                this.selects.map(obj => {
                  return h('Option', {
                    props: { value: obj.name }
                  })
                })
              ),
              h('span', [
                h('span', '亲缘身份:'),
                h(
                  'Select',
                  {
                    props: {
                      transfer: true,
                      placeholder: '请选择'
                    },
                    style: {
                      display: 'inline-block',
                      width: '120px',
                      margin: '0 10px'
                    },
                    on: {
                      'on-change': value => {}
                    }
                  },
                  this.selects.map(obj => {
                    return h('Option', {
                      props: { value: obj.name }
                    })
                  })
                )
              ])
            ])
          }
        },
        {
          title: 'STR',
          key: '',
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
          key: '',
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
      tableData: [{}, {}],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      selects: [
        {
          name: 'test1',
          code: 1
        },
        {
          name: 'test2',
          code: 2
        }
      ]
    }
  },
  methods: {
    changePage() {},
    back() {
      this.$router.go(-1)
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/datamonitoring';
</style>
