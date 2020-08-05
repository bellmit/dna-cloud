<template>
  <div class="content-box">
    <Row>
      <Col span="24" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据监控管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/monitoring/compare">数据比对监控</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <div class="title">快比列表</div>
          <div class="tab-part">
            <Table border :columns="tableCol" :data="tableData" class="bazl-table" size="small"></Table>
          </div>
          <div class="title">自动比列表</div>
          <div class="tab-part">
            <Table border :columns="tableCol" :data="tableData" class="bazl-table" size="small"></Table>
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
          title: '#',
          align: 'center',
          width: 180,
          key: 'name'
        },
        {
          title: '待比对',
          align: 'center',
          key: 'num1'
        },
        {
          title: '已比对',
          align: 'center',
          key: 'num2'
        },
        {
          title: '比对失败',
          align: 'center',
          key: 'num3'
        }
      ],
      tableData: [
        {
          name: '同型比对',
          num1: 21,
          num2: 45,
          num3: 32
        },
        {
          name: '三联体亲缘比对',
          num1: 21,
          num2: 45,
          num3: 32
        },
        {
          name: '单亲亲缘比对',
          num1: 21,
          num2: 45,
          num3: 32
        },
        {
          name: '同型Y-STR比对',
          num1: 21,
          num2: 45,
          num3: 32
        }
      ]
    }
  }
}
</script>
