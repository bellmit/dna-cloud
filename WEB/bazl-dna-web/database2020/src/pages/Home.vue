<template>
  <div style="width:100%">
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <router-link to="/home">首页</router-link>
          </Col>
        </Row>
        <Row>
          <Col span="20">
            <Row>
              <Col span="24" class="bg-part">
                <Row class="title">
                  <Col span="12" class="tl">最新比中—物证比中人员</Col>
                  <Col span="12" class="tr look-more"
                    ><span @click="goComparePerson">查看更多</span></Col
                  >
                </Row>
                <Table
                  border
                  :columns="matterComparePersonListCol"
                  :data="matterComparePersonListData"
                  class="bazl-table"
                  size="small"
                ></Table>
              </Col>
            </Row>
            <Row>
              <Col span="24" class="bg-part">
                <Row class="title">
                  <Col span="12" class="tl">最新比中—物证比中物证</Col>
                  <Col span="12" class="tr look-more"
                    ><span @click="goCompareMatter">查看更多</span></Col
                  >
                </Row>
                <Table
                  border
                  :columns="matterCompareMatterCol"
                  :data="matterCompareMatterData"
                  class="bazl-table"
                  size="small"
                ></Table>
              </Col>
            </Row>
          </Col>
          <Col span="4" class="data-sum">
            <div class="sum-box">
              <div class="title">数据汇总</div>
              <ul class="sum-part">
                <li>待复合比中</li>
                <li>
                  <span>物证比中人员</span>
                  <span>({{ toReviewCompare.matterComparePersonCount }})</span>
                </li>
                <li>
                  <span>本区比中</span>
                  <span>({{ toReviewCompare.regionCompare }})</span>
                </li>
                <li class="red-border">
                  <span>跨区比中</span>
                  <span>({{ toReviewCompare.spanningArea }})</span>
                </li>
                <li>
                  <span>物证比中物证</span>
                  <span>({{ toReviewCompare.matterComparematterCount }})</span>
                </li>
                <li>
                  <span>本区比中</span>
                  <span>({{ toReviewCompare.matterSameRegionCount }})</span>
                </li>
                <li>
                  <span>跨区比中</span>
                  <span>({{ toReviewCompare.matterSpanningAreaCount }})</span>
                </li>
              </ul>
              <ul class="sum-part">
                <li>待上报数据</li>
                <li>
                  <span>待上报案件数</span>
                  <span>({{ toReportInfo.toReportCaseCount }})</span>
                </li>
                <li>
                  <span>待上报物证数</span>
                  <span>({{ toReportInfo.toReportMatterCount }})</span>
                </li>
                <li>
                  <span>待上报人员数</span>
                  <span>({{ toReportInfo.toReportPersionCount }})</span>
                </li>
              </ul>
              <ul class="sum-part">
                <li>本地库数据概况</li>
                <li>
                  <span>案件数</span>
                  <span>({{ comprehensiveInfo.caseCount }})</span>
                </li>
                <li>
                  <span>物证数</span>
                  <span>({{ comprehensiveInfo.matterCount }})</span>
                </li>
                <li>
                  <span>人员数</span>
                  <span>({{ comprehensiveInfo.personCount }})</span>
                </li>
                <li>
                  <span>STR信息数</span>
                  <span>({{ comprehensiveInfo.strCount }})</span>
                </li>
                <li>
                  <span>Y-STR信息数</span>
                  <span>({{ comprehensiveInfo.ystrCount }})</span>
                </li>
                <li>
                  <span>混合STR信息数</span>
                  <span>({{ comprehensiveInfo.blendCount }})</span>
                </li>
              </ul>
            </div>
          </Col>
        </Row>
      </Col>
    </Row>
  </div>
</template>

<script>
import Common from '../components/Common'
export default {
  data() {
    return {
      // SidebarParams: {
      //   open: ['1'],
      //   active: '1-1'
      // },
      matterComparePersonListCol: [
        // 物证比中人员表格列表
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
          // render: (h, params) => { // 添加0
          //   let indexNo
          //   const indexNum = Number(params.index) + 1
          //   if (indexNum < 10) {
          //     indexNo = '0' + indexNum
          //   } else {
          //     indexNo = Number(params.index) + 1
          //   }
          //   return h('span', indexNo)
          // }
        },
        {
          title: '受理编号',
          align: 'center',
          render: (h, params) => {
            return h('span', {
              class: 'blue-font'
            }, params.row.caseAcceptNo)
          }
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '破获状态',
          key: 'caseStatus',
          className: 'red-font',
          align: 'center',
          width: 60,
          render: (h, params) => {
            const text = params.row.caseStatus === '0' ? '未破获' : '已破获'
            return h('span', text)
          }
        },
        {
          title: '送检单位',
          key: 'consignmentName',
          align: 'center'
        },
        {
          title: '检材编号',
          key: 'sampleNo',
          align: 'center',
          render: (h, params) => {
            return h('span', {
              style: {
                cursor: 'pointer'
              },
              on: {
                click: event => {
                  // console.log(params.row)
                  sessionStorage.setItem('selectName', '/thanin/homotype')
                  sessionStorage.setItem('openNames', '2')
                  this.$router.push({
                    path: '/thanin/homotypeSourceDetail',
                    query: {
                      id: params.row.id
                    }
                  })
                }
              }
            }, params.row.sampleNo)
          }
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center',
          width: 60
        },
        {
          title: '检材类型',
          key: 'sampleTypeName',
          align: 'center',
          width: 60
        },
        {
          title: '比中地区',
          key: 'matchRegion',
          align: 'center',
          width: 60
        },
        {
          title: '人员类型',
          key: 'personTypeName',
          align: 'center',
          width: 60
        },
        {
          title: '人员姓名',
          key: 'personname',
          align: 'center',
          width: 60
        },
        {
          title: '身份证号',
          // key: 'personIdcardNo',
          align: 'center',
          render: (h, params) => {
            return h('span', {
              class: 'blue-font'
            }, params.row.personIdcardNo
            )
          }
        },
        {
          title: '比中数',
          key: 'matchLocusCount',
          align: 'center',
          width: 50
        }
      ],
      matterComparePersonListData: [],
      matterCompareMatterCol: [
        // 物证比中物证表格列表
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
        },
        {
          title: '受理编号',
          align: 'center',
          render: (h, params) => {
            return h('span', {
              class: 'blue-font'
            }, params.row.caseAcceptNo)
          }
        },
        {
          title: '案件名称',
          key: 'caseName',
          align: 'center'
        },
        {
          title: '破获状态',
          className: 'red-font',
          align: 'center',
          width: 60,
          render: (h, params) => {
            const text = params.row.caseStatus === '0' ? '未破获' : '已破获'
            return h('span', text)
          }
        },
        {
          title: '送检单位',
          key: 'consignmentName',
          align: 'center'
        },
        {
          title: '检材编号',
          key: 'sampleNo',
          align: 'center',
          render: (h, params) => {
            return h('span', {
              style: {
                cursor: 'pointer'
              },
              on: {
                click: event => {
                  sessionStorage.setItem('selectName', '/thanin/homotype')
                  sessionStorage.setItem('openNames', '2')
                  this.$router.push({
                    path: '/thanin/homotypeSourceDetail',
                    query: {
                      id: params.row.id
                    }
                  })
                }
              }
            }, params.row.sampleNo)
          }
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center',
          width: 60
        },
        {
          title: '比中地区',
          key: 'matchRegion',
          align: 'center',
          width: 60
        },
        {
          title: '比中检材编号',
          key: 'targetSampleNo',
          align: 'center',
          width: 110
        },
        {
          title: '比中检材名称',
          key: 'targetSampleName',
          align: 'center',
          width: 90
        },
        {
          title: '比中案件受理编号',
          key: 'targetCaseAcceptNo',
          align: 'center',
          width: 110
        },
        {
          title: '比中数',
          key: 'matchLocusCount',
          align: 'center',
          width: 50
        }
      ],
      matterCompareMatterData: [],
      dataSum: {},
      toReviewCompare: {
        matterComparePersonCount: '0',
        regionCompare: '0',
        spanningArea: '0',
        matterComparematterCount: '0',
        matterSameRegionCount: '0',
        matterSpanningAreaCount: '0'
      },
      toReportInfo: {
        toReportCaseCount: '0',
        toReportMatterCount: '0',
        toReportPersionCount: '0'
      },
      comprehensiveInfo: {
        caseCount: '0',
        matterCount: '0',
        personCount: '0',
        strCount: '0',
        ystrCount: '0',
        blendCount: '0'
      }
    }
  },
  methods: {
    gethomePageInfo() {
      this.$axios
        .post('/database/home/gethomePageInfo', {
          userId: sessionStorage.getItem('userId')
        })
        .then(res => {
          // console.log(res, 'chshid111---')
          this.matterComparePersonListData = res.result.matterComparePersonList
          this.matterCompareMatterData = res.result.matterCompareMatterList
          this.toReviewCompare = res.result.toReviewCompare
          this.toReportInfo = res.result.toReportInfo
          this.comprehensiveInfo = res.result.comprehensiveInfo
        })
    },
    goComparePerson() {
      sessionStorage.setItem('linkFlag', '1')
      this.$router.push({
        path: '/thanin/homotype'
      })
    },
    goCompareMatter() {
      sessionStorage.setItem('linkFlag', '1')
      this.$router.push({
        path: '/thanin/homotype'
      })
    }
  },
  created() {
    this.gethomePageInfo()
    // 开始调用公共方法存入缓存
    // Common.casePropertyList.bind(this)()
    // Common.consignOrgName.bind(this)()
    Common.populationQuery.bind(this)()
    Common.instoreDataTypeList.bind(this)() // 入库数据类型
    Common.sampleTypeQuery.bind(this)() // 检材类型
    Common.personTypeQuery.bind(this)() // 案件人员类型
    Common.panelNameQuery.bind(this)() // 调用试剂盒STR/YSTR
    Common.personRaceList.bind(this)() // 民族
    Common.personNationList.bind(this)() // 国籍
    Common.caseNatureQuery.bind(this)() // 案件性质
    Common.labServerList.bind(this)() // 实验室列表
    Common.qcSampleTypeList.bind(this)() // 质控样本类型
    Common.qcPersonTypeList.bind(this)() // 质控人员类别
    Common.selectByCriminalPersonType.bind(this)() // 建库人员类别
    Common.selectByPersonRelationType.bind(this)() // 人员亲缘类别
    Common.qcSexPersonType.bind(this)() // 质控人员性别
    Common.selectLabName.bind(this)() // 比对实验室范围
    Common.selectByOrgInfo.bind(this)() // 委托单位
    Common.selectRegion.bind(this)() // 所属辖区
    // Common.selectByOrg.bind(this)() // 委托单位二级
    Common.selectByTargetReationType.bind(this)() // 目标亲缘类型
    Common.certificateType.bind(this)() // 证件类型
  },
  mounted() {
    // sessionStorage.setItem('selectName', '/home')
    // sessionStorage.setItem('openNames', '1')
  }
}
</script>
<style lang="less" scoped>
@import '../assets/styles/home.less';
</style>
