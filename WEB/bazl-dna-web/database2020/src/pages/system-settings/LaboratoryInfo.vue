<template>
  <div class="content-box">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>系统设置</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/laboratory">实验室管理</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/setting/laboratoryinfo">实验室信息</router-link>
          </Col>
        </Row>
        <div class="form-part">
          <div class="title">
            基本信息
          </div>
          <Form
            :model="baseInfo"
            label-position="right"
            :label-width="140"
            class="bazl-form"
          >
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="所属单位名称">
                  <!-- <Input v-model="baseInfo.input1" placeholder="请输入"></Input> -->
                  <!-- <Select placeholder="请选择所属单位" v-model="baseInfo.orgId">
                    <Option
                      v-for="item in selectByOrgInfo"
                      :key="item.id"
                      :value="item.id"
                      >{{ item.orgName }}</Option
                    >
                  </Select> -->
                  <span class="item-group">
                    <Cascader
                      change-on-select
                      :data="selectByOrgInfo"
                      v-model="clientOrgList"
                    ></Cascader>
                  </span>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="鉴定中心全称">
                  <Input
                    v-model="baseInfo.identifyName"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="鉴定中心电话">
                  <Input
                    v-model="baseInfo.identifyPhone"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="16">
                <FormItem label="鉴定中心地址">
                  <Input
                    v-model="baseInfo.identifyAddress"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="DNA实验室名称">
                  <Input
                    v-model="baseInfo.labName"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="DNA实验室级别">
                  <Input
                    v-model="baseInfo.labLevel"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="实验室负责人">
                  <Input
                    v-model="baseInfo.labUser"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="实验室负责人电话">
                  <Input
                    v-model="baseInfo.labPhone"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="实验室服务器编号">
                  <Input
                    v-model="baseInfo.serverNumber"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <FormItem label="系统服务器IP地址">
                  <Input
                    v-model="baseInfo.serverIp"
                    placeholder="请输入"
                  ></Input>
                </FormItem>
              </Col>
              <Col class="item-col" span="8">
                <FormItem label="授权访问IP范围">
                  <Input
                    v-model="baseInfo.serverIpLeft"
                    placeholder="请输入"
                    style="width:48%"
                  ></Input>
                  <span style="display:inline-block;width:2%;">-</span>
                  <Input
                    v-model="baseInfo.serverIpRight"
                    placeholder="请输入"
                    style="width:48%"
                  ></Input>
                </FormItem>
              </Col>
            </Row>
          </Form>
        </div>
        <div class="form-part">
          <Row class="title">
            <Col span="12" class="tr">
              <button class="btn btn-blue-bg" @click="handSaveBaseInfo">
                保 存
              </button>
            </Col>
          </Row>
        </div>
        <div class="form-part">
          <Row class="title">
            <Col span="12">
              实验室人员
            </Col>
            <Col span="12" class="tr">
              <button class="btn btn-blue-bg" @click="handaddPerson(1)">
                添加
              </button>
            </Col>
          </Row>
          <Table
            border
            :columns="tableCol"
            :data="tableData"
            class="bazl-table bazl-table-title"
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
          <div class="form-part">
            <Row class="title" style="border:none;margin-top:15px">
              <Col span="12" class="tr">
                <button class="btn btn-blue-bg" @click="goBack">
                  返 回
                </button>
              </Col>
            </Row>
          </div>
        </div>
        <Row>
          <CopyRight></CopyRight>
        </Row>
      </Col>
    </Row>
    <Modal
      v-model="personInfoModal"
      width="800"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        实验室人员信息
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="personInfoModal = false"
        />
      </div>
      <div class="content padding-15px">
        <Form :model="personInfo" label-position="right" :label-width="90">
          <Row class="item-row">
            <Col class="item-col" span="8">
              <FormItem label="姓名">
                <Input v-model="personInfo.nickName"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="性别">
                <!-- <Input v-model="personInfo.sex"></Input> -->
                <Select v-model="personInfo.sex">
                  <Option value="1">男</Option>
                  <Option value="2">女</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <FormItem label="身份证号">
                <Input v-model="personInfo.idCard"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="警号">
                <Input v-model="personInfo.policeCard"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="联系电话">
                <Input v-model="personInfo.userPhone"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <FormItem label="学历">
                <Input v-model="personInfo.education"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="16">
              <FormItem label="毕业院校">
                <Input v-model="personInfo.userSchool"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <FormItem label="职务">
                <Input v-model="personInfo.userPost"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="职称">
                <Input v-model="personInfo.userJob"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="授权人签字">
                <Input v-model="personInfo.authSign"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8" class="item-col">
              <FormItem label="资质证书">
                <!-- <img
                  src="https://i.ibb.co/K0fFZb7/image.jpg"
                  alt=""
                  style="width:160px;"
                /> -->
                <div v-if="img_path" class="img-con border-dashed">
                  <img
                    @click="imgModal = true"
                    :src="img_path"
                    alt=""
                    class="cursor"
                  />
                </div>
                <div v-else class="img-con border-dashed">
                  <img :src="img_err" alt="" />
                </div>
                <div class="upload">
                  <!-- /bazlDnaMix/api_iLabSTRmix -->
                  <Upload
                    ref="upload"
                    :format="['jpg', 'jpeg', 'png']"
                    :before-upload="handBeforeUpload"
                    :on-success="handSuccessUpload"
                    :headers="headers"
                    :on-error="handErrorUpload"
                    multiple
                    type="drag"
                    :name="'multipartFile'"
                    class="fileBtnstyle"
                    action="/api/system/file/upload"
                  >
                    <div class="fileBtnstyle">上传资质证书</div>
                  </Upload>
                </div>
              </FormItem>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <FormItem label="系统登录名">
                <Input v-model="personInfo.systemUserName"></Input>
              </FormItem>
            </Col>
            <Col class="item-col" span="8">
              <FormItem label="系统角色">
                <Input v-model="personInfo.systemRoleName"></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <hr class="gray-line" />
      <Row class="btn-row">
        <button class="btn btn-blue-bg" @click="handSavePersonInfo">
          保存
        </button>
        <button class="btn btn-blue-border" @click="personInfoModal = false">
          取消
        </button>
      </Row>
    </Modal>
    <Modal
      v-model="imgModal"
      width="820"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看资质证书
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="imgModal = false"
        />
      </div>
      <div class="contai">
        <img :src="img_path" alt="" />
      </div>
    </Modal>
  </div>
</template>
<script>
import CopyRight from '../../components/CopyRight'
export default {
  components: { CopyRight },
  data() {
    return {
      clientOrgList: [],
      imgModal: false,
      img_err: require('../../assets/img/earth.png'),
      baseInfo: {
        clientOrgList: []
      },
      personInfoModal: false,
      personInfo: {},
      tableCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 40
        },
        {
          title: '姓名',
          key: 'nickName',
          align: 'center'
        },
        {
          title: '性别',
          key: 'sex',
          align: 'center',
          render: (h, params) => {
            const text = params.row.sex === 1 ? '男' : '女'
            return h('span', text)
          }
        },
        {
          title: '身份证号',
          key: 'idCard',
          align: 'center'
        },
        {
          title: '警号',
          key: 'policeCard',
          align: 'center'
        },
        {
          title: '联系电话',
          key: 'userPhone',
          align: 'center'
        },
        {
          title: '职务',
          key: 'userPost',
          align: 'center'
        },
        {
          title: '职称',
          key: 'userJob',
          align: 'center'
        },
        {
          title: '资质证书',
          key: 'certPath',
          align: 'center'
        },
        {
          title: '授权签字人',
          key: 'authSign',
          align: 'center',
          width: 80
        },
        {
          title: '学历',
          key: 'education',
          align: 'center'
        },
        {
          title: '系统登录名',
          key: 'systemUserName',
          align: 'center'
        },
        {
          title: '系统角色',
          key: 'systemRoleName',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h(
                'span',
                {
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: event => {
                      this.handaddPerson(2, params.row.id)
                    }
                  }
                },
                '编辑'
              ),
              h(
                'span',
                {
                  class: 'red-font',
                  style: {
                    marginLeft: '5px',
                    cursor: 'pointer'
                  },
                  on: {
                    click: event => {
                      this.handDelPerson(params.row.id)
                    }
                  }
                },
                '删除'
              )
            ])
          }
        }
      ],
      tableData: [],
      currentPage: 1,
      pageCount: 1,
      allRecordCount: 1,
      id: '',
      selectByOrgInfo: [],
      headers: {
        Authorization: ''
      },
      baseId: '',
      img_path: '',
      orgidList: []
    }
  },
  created() {
    if (sessionStorage.getItem('selectByOrgInfo')) {
      // 委托单位最新
      const arr = sessionStorage.getItem('selectByOrgInfo')
      this.selectByOrgInfo = JSON.parse(arr)
    }
    this.headers.Authorization = sessionStorage.getItem('accessToken')
  },
  mounted() {
    this.id = this.$route.query.id
    if (this.id) {
      this.handInitData()
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    handleTab(index) {
      this.active = index
    },
    // 保存实验室信息
    handSaveBaseInfo() {
      this.baseInfo.clientOrgList = []
      console.log(this.clientOrgList[this.clientOrgList.length - 1])
      if (this.clientOrgList.length !== 0) {
        this.baseInfo.clientOrgList.push(
          this.clientOrgList[this.clientOrgList.length - 1]
        )
      } else {
        this.baseInfo.clientOrgList = []
      }
      this.$axios.post('/system/lab/save', this.baseInfo).then(res => {
        // console.log(res)
        this.$Message.success('保存成功！')
        this.baseId = res.result.id
        this.id = res.result.id
      })
    },
    // 保存人员信息
    handSavePersonInfo() {
      this.personInfo.certPath = this.img_path
      this.$axios.post('/system/lab/saveLabUser', this.personInfo).then(res => {
        console.log(res)
        this.$Message.success('保存成功！')
        this.handInitData()
        this.personInfoModal = false
      })
    },
    changePage() {},
    // 页面初始数据
    handInitData() {
      this.$axios.get('/system/lab/get/' + this.id).then(res => {
        console.log(res, 'xianqing123312')
        this.baseInfo = res.result.sysLab
        this.baseId = res.result.sysLab.id
        this.tableData = res.result.syslabUserList
        this.clientOrgList = res.result.sysLab.clientOrgList
        console.log(this.clientOrgList, '查看双向绑定---')
      })
    },
    // 添加/编辑人员列表 弹窗
    handaddPerson(type, id) {
      if (!this.baseId) {
        this.$Modal.error({
          title: '抱歉',
          content: '请先保存实验室信息！'
        })
        return false
      }
      console.log(this.baseId, '12312312')
      if (type === 1) {
        Object.keys(this.personInfo).forEach(key => {
          this.personInfo[key] = ''
        })
        this.img_path = ''
        this.personInfo.labId = this.baseId
        this.personInfo.id = ''
      } else {
        this.personInfo.labId = this.baseId
        this.$axios.get('/system/lab/getLabUser/' + id).then(res => {
          console.log(res, '编辑回显')
          this.personInfo = res.result
          this.personInfo.sex = String(res.result.sex)
          this.img_path = res.result.certPath
        })
      }
      this.personInfoModal = true
    },
    // 删除实验室人员
    handDelPerson(id) {
      this.$Modal.confirm({
        title: '提醒',
        content: '确定要删除吗?此操作不可逆',
        okText: '确定',
        cancelText: '我再想想',
        onOk: () => {
          this.$axios
            .get('/system/lab/deleteLabUser/' + id)
            .then(res => {
              console.log(res, '删除')
              this.$Message.success('删除成功！')
              this.handInitData()
            })
        }
      })
    },
    // 上传前
    handBeforeUpload() {},
    // 上传成功
    handSuccessUpload(res) {
      console.log(res)
      this.img_path = res.result
    },
    // 上传失败
    handErrorUpload() {}
  }
}
</script>
<style lang="less" scoped>
.item-col {
  .item-label {
    width: 80px !important;
  }
}
.img-con {
  width: 175px;
  height: 160px;
  margin-bottom: 10px;
  img {
    width: 100%;
    height: 100%;
  }
}
.contai {
  width: 100%;
  height: 650px;
  padding: 15px;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>
