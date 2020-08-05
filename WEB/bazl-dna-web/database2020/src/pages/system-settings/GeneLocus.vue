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
            <router-link to="/setting/genelocus">入库基因位点数设置</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <Row class="title">
            <Col span="12">数据入库基因位点数设置</Col>
          </Row>
          <Table
              border
              :columns="tableCol"
              :data="tableData"
              class="bazl-table"
            ></Table>
        </div>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click="submitConditions">保存</button>
          </Row>
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
          width: '80',
          align: 'center'
        },
        {
          title: '入库数据类型',
          key: 'instoreDataTypeName',
          align: 'center'
        },
        {
          title: 'STR最少基因位点个数',
          // key: 'strLocusCount',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Input', {
                props: {
                  value: params.row.strLocusCount
                },
                style: {
                  width: '120px',
                  textAlign: 'center'
                },
                on: {
                  'on-blur': event => {
                    this.tableData[params.index].strLocusCount = event.target.value
                  }
                }
              })
            ])
          }
        },
        {
          title: 'Y-STR最少基因位点个数',
          // key: 'ystrLocusCount',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Input', {
                props: {
                  value: params.row.ystrLocusCount
                },
                style: {
                  width: '120px',
                  textAlign: 'center'
                },
                on: {
                  'on-blur': event => {
                    this.tableData[params.index].ystrLocusCount = event.target.value
                  }
                }
              })
            ])
          }
        }
      ],
      tableData: [{}, {}]
    }
  },
  methods: {
    getInstoreConditions() {
      this.$axios.post('/database/system/instore/instoreConditions', {
        params: {}
      }).then(res => {
        // console.log(res)
        this.tableData = res.result
      })
    },
    submitConditions() {
      const arr = this.tableData
      arr.forEach((item) => {
        delete item.instoreDataTypeName
      })
      const info = JSON.stringify(arr)
      // console.log(info, 'info')
      this.$axios.post('/database/system/instore/updateInstoreConditions', info, {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(res => {
        if (res.code === 1) {
          this.$Modal.success({
            title: '恭喜',
            content: '保存成功~'
          })
          this.getInstoreConditions()
        } else {
          this.$Modal.error({
            title: '抱歉',
            content: '保存失败~'
          })
        }
      })
    }
  },
  mounted() {
    this.getInstoreConditions()
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/setup';
</style>
