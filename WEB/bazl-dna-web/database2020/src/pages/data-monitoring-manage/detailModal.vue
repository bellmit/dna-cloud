<template>
  <Modal
    v-model="seePerson"
    width="800"
    class="bazl-modal case-modal"
    :closable="false"
    :mask-closable="false"
  >
    <div class="header">查看人员信息
            <Icon type="md-close-circle" class="modal-close" @click="seePerson = false"/>
    </div>
    <div class="model-content">
      <div class="model-part">
        <div class="model-title">人员基本信息</div>
        <div class="input-content">
          <Form
            :model="seePersonBase"
            label-position="right"
            :label-width="100"
            class="bazl-form"
            ref="seePersonBase"
          >
            <Row class="item-row">
              <FormItem label="人员类型" prop="personTypeName">
                <Input v-model="seePersonBase.personTypeName" style="width:156px;"></Input>
              </FormItem>
            </Row>
            <Row>
              <Col span="16">
                <Row :gutter="16" class="item-row">
                  <Col span="12">
                    <FormItem label="姓名">
                      <Input v-model="seePersonBase.personName"></Input>
                    </FormItem>
                  </Col>
                  <Col span="12">
                    <FormItem label="曾用名(别名)">
                      <Input v-model="seePersonBase.personAlias"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="12">
                    <FormItem label="性别">
                      <Input v-model="seePersonBase.personGenderName"></Input>
                    </FormItem>
                  </Col>
                  <Col span="12">
                    <FormItem label="民族">
                      <Input v-model="seePersonBase.personRaceName"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="12">
                    <FormItem label="证件号码">
                      <Input v-model="seePersonBase.personIdcardNo"></Input>
                    </FormItem>
                  </Col>
                  <Col span="12">
                    <FormItem label="电话号码">
                      <Input v-model="seePersonBase.phoneNumber"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="12">
                    <FormItem label="籍贯">
                      <Input v-model="seePersonBase.personAddr"></Input>
                    </FormItem>
                  </Col>
                  <Col span="12">
                    <FormItem label="现在住址">
                      <Input v-model="seePersonBase.input1"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="12">
                    <FormItem label="身高">
                      <Input v-model="seePersonBase.personHeight"></Input>
                    </FormItem>
                  </Col>
                  <Col span="12">
                    <FormItem label="体重">
                      <Input v-model="seePersonBase.personWeight"></Input>
                    </FormItem>
                  </Col>
                </Row>
              </Col>
              <Col span="8">
                <div class="modal-img-box">
                  <!-- <img src="https://i.ibb.co/K0fFZb7/image.jpg" alt="" /> -->
                  <img src alt />
                </div>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
      <hr class="gray-line" />
      <div class="model-part">
        <div class="model-title">人员检材信息</div>
        <div class="input-content">
          <Form
            :model="seePersonSample"
            label-position="right"
            :label-width="100"
            class="bazl-form"
          >
            <Row class="item-row">
              <Col span="8">
                <FormItem label="DNA检材编号">
                  <Input v-model="seePersonSample.sampleLabNo"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="检材名称">
                  <Input v-model="seePersonSample.sampleName"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="检材类型">
                  <Input v-model="seePersonSample.sampleTypeName"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col span="8">
                <FormItem label="检材包装">
                  <Input v-model="seePersonSample.samplePackage"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="检材描述">
                  <Input v-model="seePersonSample.sampleDesc"></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
      <hr class="gray-line" />
      <div class="model-part">
        <div class="model-title">检材基因信息</div>
        <div class="input-content">
          <Row class="item-row">
            <Col span="8" class="item-col">
              <span class="item-label">试剂盒</span>
              <Input class="item-input" v-model="seePersonPanel" readonly></Input>
            </Col>
            <Col span="8" class="item-col">
              <span class="item-label">检出位点个数</span>
              <Input class="item-input" v-model="seePersonLocus" readonly></Input>
            </Col>
            <Col span="8" class="toggle-gene">
              <span
                :class="{ active: this.activePersonGene === 1 }"
                @click="handleSeePersonGene(1)"
              >STR</span>
              <span
                :class="{ active: this.activePersonGene === 2 }"
                @click="handleSeePersonGene(2)"
              >YSTR</span>
            </Col>
          </Row>
        </div>
        <Row class="tc" :gutter="16">
          <Col span="12">
            <div class="gene-title">查看基因分型</div>
            <Table
              border
              :columns="seePersonGeneCol"
              :data="seePersonGeneData"
              class="bazl-table light-thead"
              v-show="seePersonGeneData.length != 0"
            ></Table>
          </Col>
          <Col span="12">
            <div class="gene-title">查看图谱</div>
            <img :src="seePersonGeneImage" alt v-show="seePersonGeneImage != '' " />
          </Col>
          <Col span="24" v-show="seePersonGeneData.length == 0">
            <img src="../../assets/img/earth.png" alt style="width:240px;margin-top:20px;" />
            <p>暂无数据....</p>
          </Col>
        </Row>
        <Row class="btn-row">
          <button class="btn btn-blue-bg" @click="seePerson = false">关闭</button>
        </Row>
      </div>
    </div>
  </Modal>
</template>
<script>
export default {
  data() {
    return {
      seePerson: false,
      seePersonBase: {
      },
      seePersonSample: {
      },
      seePersonPanel: '',
      seePersonLocus: '',
      activePersonGene: 1,
      seePersonGeneData: [],
      seePersonGeneImage: '',
      seePersonGeneCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center'
        },
        {
          title: '基因座',
          align: 'center',
          key: 'name'
        },
        {
          title: '等位基因',
          align: 'center',
          key: 'value'
        }
      ]
    }
  },
  methods: {
    open() {
      this.seePerson = true
    }
  }
}
</script>>
<style lang="less" scoped>
    @import '../../assets/styles/seecasedetails';
</style>
