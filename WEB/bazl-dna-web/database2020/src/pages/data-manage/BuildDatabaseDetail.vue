<template>
  <div>
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/builddatabase"
              >建库数据管理</router-link
            >
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/BuildDatabaseDetail"
              >查看建库人员详情</router-link
            >
          </Col>
        </Row>
        <div class="part-detail">
          <div class="title">
            建库人员详情
          </div>
          <div class="item-title" style="border-top: none;">
            人员信息
            <span style="position:absolute;right:20px;">
              <Icon type="ios-arrow-dropdown-circle" style="font-size:20px;color:#00439E;cursor:pointer;" @click="isPersonActive = !isPersonActive" v-show="isPersonActive == false"/>
              <Icon type="ios-arrow-dropup-circle" style="font-size:20px;color:#00439E;cursor:pointer;"  @click="isPersonActive = !isPersonActive" v-show="isPersonActive == true"/>
            </span>
          </div>
          <Collapse>
            <div class="for-sample" v-show="isPersonActive">
            <Row class="item-row">
              <Col class="item-col" span="8">
                <span class="item-label">人员类型</span>
                <Input
                  v-model="formInfo.criminalPersonTypeName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">现在住址</span>
                <Input
                  v-model="formInfo.presentAddress"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">备注信息</span>
                <Input
                  v-model="formInfo.remarks"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            <Row class="item-row">
              <Col span="8">
                <span class="item-label">姓 名</span>
                <Input
                  v-model="formInfo.personName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">性 别</span>
                <Input
                  v-model="formInfo.personGenderName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">身份证号</span>
                <Input
                  v-model="formInfo.personIdcardNo"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <span class="item-label">身 高</span>
                <Input
                  v-model="formInfo.personHeight"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">体 重</span>
                <Input
                  v-model="formInfo.personWeight"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">其他特征</span>
                <Input
                  v-model="formInfo.personAppearance"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <span class="item-label">国 籍</span>
                <Input type="text" readonly style="width: 260px;"></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">民 族</span>
                <Input
                  v-model="formInfo.personRaceName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">籍 贯</span>
                <Input
                  v-model="formInfo.nativePlaceName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
          </div>
          </Collapse>
          <div class="item-title">
            样本信息
            <span style="position:absolute;right:20px;">
              <Icon type="ios-arrow-dropdown-circle" style="font-size:20px;color:#00439E;cursor:pointer;" @click="isSampleActive = !isSampleActive" v-show="isSampleActive == false"/>
              <Icon type="ios-arrow-dropup-circle" style="font-size:20px;color:#00439E;cursor:pointer;"  @click="isSampleActive = !isSampleActive" v-show="isSampleActive == true"/>
            </span>
          </div>
          <Collapse>
          <div class="for-sample" v-show="isSampleActive">
            <Row class="item-row">
              <Col span="8">
                <span class="item-label">入库数据类型</span>
                <Input
                  v-model="formInfo.instoreDataTypeName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">样本DNA编号</span>
                <Input
                  v-model="formInfo.sampleLabNo"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col span="8">
                <span class="item-label">样本入国家库编号</span>
                <Input
                  v-model="formInfo.nationSysNo"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <span class="item-label">样本名称</span>
                <Input
                  v-model="formInfo.sampleName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">样本类型</span>
                <Input
                  v-model="formInfo.sampleTypeName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">样本包装</span>
                <Input
                  v-model="formInfo.samplePackage"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            <Row class="item-row">
              <Col class="item-col" span="8">
                <span class="item-label">采集时间</span>
                <Input
                  v-model="formInfo.collectDatetime"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
              <Col class="item-col" span="8">
                <span class="item-label">采集单位</span>
                <Input
                  v-model="formInfo.collectOrgName"
                  type="text"
                  readonly
                  style="width: 260px;"
                ></Input>
              </Col>
            </Row>
            </div>
          </Collapse>
            <div class="part-detail viewgene">
              <Row class="title">
                <Col span="12">
                  查看基因分型
                </Col>
                <Col span="8">
                  <div>
                    <span>试剂盒: </span><span>{{ name }}</span>
                    <span style="margin-left: 35px;">检出位点数: </span
                    ><span>{{ num }}</span>
                  </div>
                  <!-- <div>
                    <span>试剂盒:</span><span>{{ item.geneType.dnaPanelName  }}</span>
                    <span style="margin-left: 15px;">检出位点数:</span
                    ><span>{{ item.geneType.locusCount }}</span>
                  </div> -->
                </Col>
                <Col span="3" class="tr view-tab">
                  <button
                    @click="switch_strYstr_btn(index)"
                    v-for="(item, index) in btnList"
                    :key="index"
                    :class="{ tR_active: tR_active == index }"
                    class="stR_ystR"
                  >
                    {{ item.name }}
                  </button>
                </Col>
              </Row>
              <Row :gutter="16">
                <!-- <Col span="24">
                  <Row class="no-anything">
                    <Col span="24" class="no-anything-img">
                      <img src="../../assets/img/earth.png" alt="" />
                    </Col>
                    <Col span="24" class="no-anything-text">
                      暂无数据
                    </Col>
                  </Row>
                </Col> -->
                <Col span="10">
                  <div class="bg-title">查看基因分型</div>
                  <div v-if="tableData.length !== 0">
                    <!-- tab_detail tabscllor -->
                    <Table
                      border
                      size="small"
                      :columns="geneColSTR"
                      :data="tableData"
                      class="bazl-table light-thead gene-table-hover"
                    ></Table>
                  </div>
                  <!--                    style="height:405px;border:1px solid #ccc;  text-align: center;border-radius:5px" hide -->
                  <div
                    v-else
                  >
                    <Table
                      border
                      size="small"
                      :columns="geneColSTR"
                      class="bazl-table light-thead"
                    ></Table>
                    <!-- <h4 class="mg-top-10px">暂无数据</h4> -->
                    <!-- <div class="viewFt img-err">
                      <div>
                        <img :src="img_err" alt="" />
                        <p>当前无峰图，您可查看其它信息</p>
                      </div>
                    </div> -->
                  </div>
                </Col>
                <Col span="14">
                <!-- viewFt -->
                  <div class="bg-title">查看图谱</div>
                  <div v-if="img_path" class="atlas">
                    <img :src="atlasImgSTR" alt="" />
                  </div>
                  <div v-else class="img-err">
                    <div>
                      <img :src="img_err" alt="" />
                      <p>当前无峰图，您可查看其它信息</p>
                    </div>
                  </div>
                </Col>
              </Row>
              <!-- </div> -->
              <Row class="btn-row">
                <button class="btn btn-blue-bg" @click.prevent="goBack">
                  返回
                </button>
              </Row>
            </div>
        </div>
      </Col>
    </Row>
    <!-- <Modal v-model="seePic" width="500" class="bazl-modal" :closable="false">
      <div class="header">
        查看照片
      </div>
      <div v-show="this.previewPictures.length === 0" style="text-align:center;heigth:400px;line-height:400px;font-size:16px;">
        暂无照片
      </div>
      <div v-show="this.previewPictures.length !== 0">
        <Carousel style="width:500px;height:400px;">
            <CarouselItem v-for="item in previewPictures" :key="item.index"  style="width:500px;height:400px;" >
                <img :src="item[sampleImage]" alt="" style="width:inherit;height:inherit;">
            </CarouselItem>
        </Carousel>
      </div>
    </Modal> -->
  </div>
</template>
<script>
import Collapse from '../../components/collapse'
export default {
  components: { Collapse },
  data() {
    return {
      isPersonActive: true,
      isSampleActive: true,
      img_err: require('../../assets/img/earth.png'),
      img_path: null,
      formInfo: {},
      tR_active: 0, // str/ystr容器切换按钮
      btnList: [
        {
          name: 'STR'
        },
        {
          name: 'Y-STR'
        }
      ],
      SidebarParams: {
        open: ['2'],
        active: '2-2'
      },
      personId: '1',
      tableindex: 0,
      criminalPersonInfoVo: {},
      consignmentInfo: {},
      caseImageList: [],
      previewPictures: [],
      geneColSTR: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'name',
          align: 'center'
        },
        {
          title: '等位基因',
          key: 'value',
          align: 'center'
        }
      ],
      geneDataSTR: [],
      geneColYSTR: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'name',
          align: 'center'
        },
        {
          title: '等位基因',
          key: 'value',
          align: 'center'
        }
      ],
      geneDataYSTR: [],
      atlasImgSTR: '',
      atlasImgYSTR: '',
      sampleId: '',
      types: [
        {
          name: 'STR'
        },
        {
          name: 'YSTR'
        }
      ],
      active: 0,
      dnaPanelNameSTR: '',
      dnaPanelNameYSTR: '',
      locusCountSTR: '',
      locusCountySTR: '',
      tableData: [],
      criminalSampleInfoVoList: [
        {
          id: 1,
          instoreDataType: '入库类型代码',
          sampleType: '样本类型代码',
          sampleLabNo: 'TLCD1600061042',
          sampleName: '潘小东',
          samplePackage: '样本包装',
          nationSysNo: '国家库编号',
          collectOrgName: '采集单位名称',
          collectDatetime: '采集时间',
          instoreDataTypeName: '违法犯罪人员',
          genetype: [
            {
              id: 6,
              geneType: '基因类型代码',
              geneTypeName: 'str',
              dnaPanelName: '试剂盒名称11111',
              locusCount: '位点个数',
              isShow: false,
              geneInfoDetail: [
                {
                  name: 'AMEL1111',
                  value: 'X/Y'
                },
                {
                  name: 'D3S1358',
                  value: '16/17'
                },
                {
                  name: 'D13S317',
                  value: '12/12'
                },
                {
                  name: 'D7S820',
                  value: '8/11'
                },
                {
                  name: 'D16S539',
                  value: '10/11'
                },
                {
                  name: 'Penta E',
                  value: '13/16'
                },
                {
                  name: 'TPOX',
                  value: '8/11'
                },
                {
                  name: 'THO1',
                  value: '9/9'
                },
                {
                  name: 'D2S1338',
                  value: '20/20'
                },
                {
                  name: 'CSF1PO',
                  value: '10/10'
                },
                {
                  name: 'D19S433',
                  value: '13/14.2'
                },
                {
                  name: 'vWA',
                  value: '14/15'
                },
                {
                  name: 'D5S818',
                  value: '12/13'
                },
                {
                  name: 'FGA',
                  value: '23/23'
                },
                {
                  name: 'D6S1043',
                  value: '11/20'
                },
                {
                  name: 'D8S1179',
                  value: '13/16'
                },
                {
                  name: 'D21S11',
                  value: '29/30'
                },
                {
                  name: 'D18S51',
                  value: '16/24'
                }
              ],
              geneImage: 'http://39.100.79.169:9090/img/dna-test1.jpg'
            },
            {
              id: 6,
              geneType: '基因类型代码',
              geneTypeName: 'ystr',
              dnaPanelName: '试剂盒名称2222',
              locusCount: '位点个数',
              isShow: true,
              geneInfoDetail: [
                {
                  name: 'AMEL2222',
                  value: 'X/Y'
                },
                {
                  name: 'D3S1358',
                  value: '16/17'
                },
                {
                  name: 'D13S317',
                  value: '12/12'
                },
                {
                  name: 'D7S820',
                  value: '8/11'
                },
                {
                  name: 'D16S539',
                  value: '10/11'
                },
                {
                  name: 'Penta E',
                  value: '13/16'
                },
                {
                  name: 'TPOX',
                  value: '8/11'
                },
                {
                  name: 'THO1',
                  value: '9/9'
                },
                {
                  name: 'D2S1338',
                  value: '20/20'
                },
                {
                  name: 'CSF1PO',
                  value: '10/10'
                },
                {
                  name: 'D19S433',
                  value: '13/14.2'
                },
                {
                  name: 'vWA',
                  value: '14/15'
                },
                {
                  name: 'D5S818',
                  value: '12/13'
                },
                {
                  name: 'FGA',
                  value: '23/23'
                },
                {
                  name: 'D6S1043',
                  value: '11/20'
                },
                {
                  name: 'D8S1179',
                  value: '13/16'
                },
                {
                  name: 'D21S11',
                  value: '29/30'
                },
                {
                  name: 'D18S51',
                  value: '16/24'
                }
              ],
              geneImage: 'http://39.100.79.169:9090/img/dna-test1.jpg'
            }
          ]
        },
        {
          id: 1,
          instoreDataType: '入库类型代码',
          sampleType: '样本类型代码',
          sampleLabNo: 'TLCD1600061042',
          sampleName: '潘小东',
          samplePackage: '样本包装',
          nationSysNo: '国家库编号',
          collectOrgName: '采集单位名称',
          collectDatetime: '采集时间',
          instoreDataTypeName: '违法犯罪人员',
          genetype: [
            {
              id: 6,
              geneType: '基因类型代码',
              geneTypeName: 'str',
              dnaPanelName: '试剂盒名称11111',
              locusCount: '位点个数',
              isShow: false,
              geneInfoDetail: [
                {
                  name: 'AMEL33333',
                  value: 'X/Y'
                },
                {
                  name: 'D3S1358',
                  value: '16/17'
                },
                {
                  name: 'D13S317',
                  value: '12/12'
                },
                {
                  name: 'D7S820',
                  value: '8/11'
                },
                {
                  name: 'D16S539',
                  value: '10/11'
                },
                {
                  name: 'Penta E',
                  value: '13/16'
                },
                {
                  name: 'TPOX',
                  value: '8/11'
                },
                {
                  name: 'THO1',
                  value: '9/9'
                },
                {
                  name: 'D2S1338',
                  value: '20/20'
                },
                {
                  name: 'CSF1PO',
                  value: '10/10'
                },
                {
                  name: 'D19S433',
                  value: '13/14.2'
                },
                {
                  name: 'vWA',
                  value: '14/15'
                },
                {
                  name: 'D5S818',
                  value: '12/13'
                },
                {
                  name: 'FGA',
                  value: '23/23'
                },
                {
                  name: 'D6S1043',
                  value: '11/20'
                },
                {
                  name: 'D8S1179',
                  value: '13/16'
                },
                {
                  name: 'D21S11',
                  value: '29/30'
                },
                {
                  name: 'D18S51',
                  value: '16/24'
                }
              ],
              geneImage: 'http://39.100.79.169:9090/img/dna-test1.jpg'
            },
            {
              id: 6,
              geneType: '基因类型代码',
              geneTypeName: 'ystr',
              dnaPanelName: '试剂盒名称2222',
              locusCount: '位点个数',
              isShow: true,
              geneInfoDetail: [
                {
                  name: 'AMEL4444',
                  value: 'X/Y'
                },
                {
                  name: 'D3S1358',
                  value: '16/17'
                },
                {
                  name: 'D13S317',
                  value: '12/12'
                },
                {
                  name: 'D7S820',
                  value: '8/11'
                },
                {
                  name: 'D16S539',
                  value: '10/11'
                },
                {
                  name: 'Penta E',
                  value: '13/16'
                },
                {
                  name: 'TPOX',
                  value: '8/11'
                },
                {
                  name: 'THO1',
                  value: '9/9'
                },
                {
                  name: 'D2S1338',
                  value: '20/20'
                },
                {
                  name: 'CSF1PO',
                  value: '10/10'
                },
                {
                  name: 'D19S433',
                  value: '13/14.2'
                },
                {
                  name: 'vWA',
                  value: '14/15'
                },
                {
                  name: 'D5S818',
                  value: '12/13'
                },
                {
                  name: 'FGA',
                  value: '23/23'
                },
                {
                  name: 'D6S1043',
                  value: '11/20'
                },
                {
                  name: 'D8S1179',
                  value: '13/16'
                },
                {
                  name: 'D21S11',
                  value: '29/30'
                },
                {
                  name: 'D18S51',
                  value: '16/24'
                }
              ],
              geneImage: 'http://39.100.79.169:9090/img/dna-test1.jpg'
            }
          ]
        }
      ],
      caseId: '',
      strInfo: {
        panelName: null,
        locusCount: null,
        list: []
      },
      ystrInfo: {
        panelName: null,
        locusCount: null,
        list: []
      },
      name: null,
      num: null
    }
  },
  created() {
    this.caseId = this.$route.query.id
    // console.log(this.caseId)
  },
  mounted() {
    this.handInitData()
  },
  computed: {},
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // str/ystr 切换按钮
    switch_strYstr_btn(index) {
      this.tR_active = index
      if (this.tR_active === 0) {
        if (this.strInfo.list) {
          this.tableData = this.strInfo.list
        } else {
          this.tableData = []
        }
        this.name = this.strInfo.panelName
        this.num = this.strInfo.locusCount
      } else {
        if (this.ystrInfo.list) {
          this.tableData = this.ystrInfo.list
        } else {
          this.tableData = []
        }
        this.name = this.ystrInfo.panelName
        this.num = this.ystrInfo.locusCount
      }
    },
    // 页面初始数据
    handInitData() {
      this.$axios
        .get('/database/criminal/personDetails', {
          params: {
            id: this.caseId
          }
        })
        .then(res => {
          // console.log(res, '12313')
          this.formInfo = res.result.criminal
          this.strInfo = res.result.STR
          this.ystrInfo = res.result.YSTR
          this.name = this.strInfo.panelName
          this.num = this.strInfo.locusCount
          if (res.result.STR.geneInfo) {
            this.strInfo.list = JSON.parse(res.result.STR.geneInfo)
            this.tableData = JSON.parse(res.result.STR.geneInfo)
          }
          if (res.result.YSTR.geneInfo) {
            this.ystrInfo.list = JSON.parse(res.result.YSTR.geneInfo)
          }
        })
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/seecasedetails';
@import '../../assets/styles/viewgenotyping';
</style>
