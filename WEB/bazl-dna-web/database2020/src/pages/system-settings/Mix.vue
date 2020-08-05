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
            <router-link to="/setupmix">混合物证STR入库比对条件设置</router-link>
          </Col>
        </Row>
        <div class="result-part">
          <Row class="title">
            <Col span="12">混合物证STR入库比对条件设置</Col>
          </Row>
          <Row class="item-row">
            <Col span="4" class="item-col-4">
              <span class="item-label">是否入库自动比对</span>
            </Col>
            <Col span="20" class="item-col-20">
              <Checkbox>自动比对</Checkbox>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="4" class="item-col-4">
              <span class="item-label">比对条件</span>
            </Col>
            <Col span="20" class="item-col-20">
              <span class="item-label">匹配下限</span>
              <Input type="text" class="item-input"></Input>
              <span class="item-label" style="margin-left:30px;">容差上限</span>
              <Input type="text" class="item-input"></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="4" class="item-col-4">
              <span class="item-label">比对目标数据类型</span>
            </Col>
            <Col span="20" class="item-col-20" style="height:auto;">
              <Row>
                <CheckboxGroup>
                  <Col span="4" class="item-col" v-for="item in dataTypes" :key="item.id">
                    <Checkbox>{{item.name}}</Checkbox>
                  </Col>
              </CheckboxGroup>
              </Row>
            </Col>
          </Row>
            <Row class="btn-row">
              <button class="btn btn-blue-bg">保存设置</button>
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
      SidebarParams: {
        open: ['7'],
        active: '7-3'
      },
      dataTypes: [
        {
          name: '现场物证'
        },
        {
          name: '无名'
        },
        {
          name: '失踪人口'
        },
        {
          name: '违法犯罪人员'
        },
        {
          name: '现场物证'
        },
        {
          name: '质控人与'
        },
        {
          name: '现场物证'
        },
        {
          name: '失踪人口亲属'
        },
        {
          name: '嫌疑人亲属'
        },
        {
          name: '现场物证'
        },
        {
          name: '受害人亲属'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        },
        {
          name: '现场物证'
        }
      ]
    }
  },
  methods: {
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/setup';
.item-row{
  margin: 6px 0;
  padding: 0 15px;
  .item-col-4{
    .item-label{
      color: #333;
      display: inline-block;
      width:140px;
      height: 32px;
      line-height: 32px;
    }
    .item-input{
      width: 200px;
    }
  }
  .item-col-20{
    height: 32px;
    line-height: 32px;
    .item-label{
      color: #333;
      display: inline-block;
      width: 80px;
    }
    .item-input{
      width: 120px;
    }
  }
}
</style>
