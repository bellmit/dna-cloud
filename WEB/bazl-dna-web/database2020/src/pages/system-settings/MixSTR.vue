<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/warehousing/mixstr">混合物证STR入库比对条件设置</router-link>
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
              <Checkbox v-model="autoCompareFlag" true-value='1' false-value='0'></Checkbox>自动比对</Checkbox>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="4" class="item-col-4">
              <span class="item-label">比对条件</span>
            </Col>
            <Col span="20" class="item-col-20">
              <span class="item-label">匹配下限</span>
              <Input type="number" class="item-input" v-model="lowestSameLimit"></Input>
              <span class="item-label" style="margin-left:30px;">容差上限</span>
              <Input type="number" class="item-input" v-model="mostDiffLimit"></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="4" class="item-col-4">
              <span class="item-label">比对目标数据类型</span>
            </Col>
            <Col span="20" class="item-col-20" style="height:auto;">
              <Row>
                <!-- <CheckboxGroup>
                  <Col span="4" class="item-col" v-for="item in dataTypes" :key="item.id">
                    <Checkbox>{{item.name}}</Checkbox>
                  </Col>
              </CheckboxGroup> -->
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
            </Col>
          </Row>
            <Row class="btn-row">
              <button class="btn btn-blue-bg" @click.prevent="updateMixCompareSettings">保存设置</button>
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
      autoCompareFlag: '0',
      lowestSameLimit: 1, // 匹配下限
      mostDiffLimit: 13, // 容差上限
      dataCheckAllList: [], // 已选数据
      instoreDataTypeList: [] // 目标数据类型
    }
  },
  mounted() {
    if (sessionStorage.getItem('instoreDataTypeList')) {
      // 入库数据类型
      const arr = sessionStorage.getItem('instoreDataTypeList')
      this.instoreDataTypeList = JSON.parse(arr)
    }
    this.getListMixTargetType()
  },
  methods: {
    checkAllGroupChangeData() {
      // console.log(this.dataCheckAllList, 'dataCheckAllList')
    },
    updateMixCompareSettings() {
      const arr = this.dataCheckAllList
      const newArr = []
      arr.forEach(item => {
        const obj = {}
        obj.code = item
        obj.checked = true
        newArr.push(obj)
      })
      const jsonstr = JSON.stringify(newArr)
      const info = {}
      info.autoCompareFlag = Number(this.autoCompareFlag)
      info.lowestSameLimit = this.lowestSameLimit
      info.mostDiffLimit = this.mostDiffLimit
      info.targetDataType = jsonstr
      const infoArr = []
      infoArr.push(info)
      this.$axios.post('/database/system/compare/updateMixCompareSettings', infoArr).then(res => {
        this.$Modal.success({
          title: '恭喜',
          content: '保存成功~'
        })
        this.getListMixTargetType()
      })
    },
    getListMixTargetType() {
      this.$axios.get('/database/system/compare/listMixTargetType').then(res => {
        console.log(res, '------')
        this.autoCompareFlag = String(res.result[0].autoCompareFlag)
        this.lowestSameLimit = res.result[0].lowestSameLimit
        this.mostDiffLimit = res.result[0].mostDiffLimit
        let arr = res.result[0].targetDataType
        arr = JSON.parse(arr)
        arr.forEach(item => {
          if (item.checked) {
            this.dataCheckAllList.push(item.code)
          }
        })
      })
    }
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
