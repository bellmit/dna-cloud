<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/configuration">数据转换配置</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/method">配置方法</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            配置方法
          </div>
          <div class="sub-title">
            基本信息
          </div>
          <Form :model="baseInfo" label-position="right" :label-width="120" class="bazl-form">
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="单位名称">
                  <Input v-model="baseInfo.orgName" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="机构代码">
                  <Input v-model="baseInfo.orgCode" placeholder="请输入" @on-blur="checkCode"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="行政级别">
                  <Select v-model="baseInfo.orgCreditCode">
                    <Option value="beijing">New York</Option>
                    <Option value="shanghai">London</Option>
                    <Option value="shenzhen">Sydney</Option>
                  </Select>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="负责人姓名">
                  <Input v-model="baseInfo.businessName" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="负责人电话">
                  <Input v-model="baseInfo.businessPhone" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <div class="sub-title">
            对接业务信息
          </div>
          <Form :model="dataSourceConfig" label-position="right" :label-width="120" class="bazl-form">
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="数据服务器IP">
                  <Input v-model="dataSourceConfig.ip" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="数据库端口">
                  <Input v-model="dataSourceConfig.port" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="数据库实例">
                  <Input v-model="dataSourceConfig.dbType" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="数据库用户名">
                  <Input v-model="dataSourceConfig.userName" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="数据库密码">
                  <Input v-model="dataSourceConfig.password" placeholder="请输入"></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
         <Row class="btn-row tc">
          <Col span="24">
            <button class="btn btn-blue-border" @click.prevent="testLink">测试连接</button>
            <button class="btn btn-blue-border">数据转换</button>
            <button class="btn btn-blue-bg" @click.prevent="handleSave">提交</button>
          </Col>
        </Row>
        </div>
        <Row class="btn-row-fixed">
          <!-- <CopyRight></CopyRight> -->
          <button class="btn btn-blue-bg" style="padding:4px 20px">提交</button>
        </Row>
      </Col>
    </Row>
    <Modal v-model="methodModal" width="400" class="bazl-modal" :closable="false" :mask-closable="false">
      <div class="content">
        <p v-show="methodModalFlag == '1'">
          恭喜您,测试连接成功!
        </p>
        <p v-show="methodModalFlag == '0'">
          您的配置信息有误,请重新配置
        </p>
        <p v-show="methodModalFlag == '2'">
          数据初始化开始,大概需要30S,请等候
        </p>
      </div>
      <Row class="btn-row">
        <button class="btn btn-blue-bg"  @click="methodModal = false" v-show="methodModalFlag == '1'">关闭</button>
        <button class="btn btn-blue-bg"  @click="methodModal = false" v-show="methodModalFlag == '0'">确定</button>
        <button class="btn btn-blue-bg" v-show="methodModalFlag == '2'">后台运行</button>
        <button class="btn btn-blue-border" @click="methodModal = false" v-show="methodModalFlag == '2'">取消</button>
      </Row>
    </Modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      id: '',
      methodModal: false,
      methodModalFlag: '1',
      codeRepeat: false,
      baseInfo: {
        status: '1',
        orgType: '1',
        id: ''
      },
      dataSourceConfig: {}
    }
  },
  mounted() {
    this.id = this.$route.query.id
    if (this.id) {
      this.getMethod()
    }
  },
  methods: {
    handleSave() {
      this.dataSourceConfig.port = Number(this.dataSourceConfig.port)
      this.baseInfo.dataSourceConfig = this.dataSourceConfig
      this.$axios.post('/system/org/save', this.baseInfo).then(res => {
        this.$Modal.success({
          title: '恭喜',
          content: '提交成功~'
        })
      })
    },
    checkCode() {
      this.$axios.post('/system/org/checkCode', {
        type: 'orgCode',
        code: this.baseInfo.orgCode,
        orgId: ''
      }).then(res => {
        // console.log(res)
      }).catch(error => {
        console.log(error)
        this.$Modal.error({
          title: '抱歉',
          content: '机构代码不允许重复!'
        })
        this.baseInfo.orgCode = ''
      })
    },
    testLink() {
      this.$axios.post('/system/org/isConnection', {
        ip: this.dataSourceConfig.ip,
        port: this.dataSourceConfig.port,
        dbType: this.dataSourceConfig.dbType,
        userName: this.dataSourceConfig.userName,
        password: this.dataSourceConfig.password
      }).then(() => {
        this.methodModalFlag = 1
        this.methodModal = true
      }).catch(() => {
        this.methodModalFlag = 0
        this.methodModal = true
      })
    },
    getMethod() {
      this.$axios.get('/system/org/get/' + this.id).then(res => {
        console.log(res, 'eres------')
        this.baseInfo = res.result
        this.dataSourceConfig = res.result.dataSourceConfig
      })
    }
  }
}
</script>
<style lang="less" scoped>
.form-part{
  height: 80vh;
}
.content{
  padding:30px 0;
  text-align: center;
  border-bottom: 1px solid #ddd;
  p{
    color: #333;
  }
}
.sub-title{
  color: #00439E;
  border-bottom: 1px solid #D2D2D2;
  padding: 8px;
}
.sub-title::before{
  display: inline-block;
  content: '';
  width: 4px;
  height: 12px;
  background: #00439E;
  margin-right: 2px;
}
.item-col{
  .item-label{
    width:40px !important;
  }
}
.btn-row-fixed{
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-left: -10px;
  text-align: center;
  background: #fff;
  padding: 8px;
}
</style>
