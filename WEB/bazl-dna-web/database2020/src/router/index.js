import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Layout from '../components/Layout'
// import Login from '../components/Login'
import Home from '../pages/Home'
// import CaseDataManage from '../pages/data-manage/Case'
import CaseDetail from '../pages/data-manage/CaseDetail'
import CaseDetailGene from '../pages/data-manage/CaseDetailGene'
import BuildDatabaseManage from '../pages/data-manage/BuildDatabase'
import BuildDatabaseManageDetail from '../pages/data-manage/BuildDatabaseDetail'
// import addQtyCtrlSam from '../pages/data-manage/addQtyCtrlSam'
import QualityControl from '../pages/data-manage/QualityControl'
import QualityControlPollutionRecord from '../pages/data-manage/QualityControlPollutionRecord'
import qtyCtrlDetails from '../pages/data-manage/qtyCtrlDetails'
import Homotype from '../pages/quick-manage/Homotype'
import HomotypeResult from '../pages/quick-manage/HomotypeResult'
import HomotypeResultDetail from '../pages/quick-manage/HomotypeResultDetail'
import HomotypeRecord from '../pages/quick-manage/HomotypeRecord.vue'
import HomotypeRecordView from '../pages/quick-manage/HomotypeRecordView.vue'
import Kinship from '../pages/quick-manage/Kinship'
import KinshipResult from '../pages/quick-manage/KinshipResult'
import KinshipResultDetail from '../pages/quick-manage/KinshipResultDetail'
import KinshipRecord from '../pages/quick-manage/KinshipRecord.vue'
import KinshipRecordView from '../pages/quick-manage/KinshipRecordView.vue'
import quickSTR from '../pages/quick-manage/STR'
import quickStrResult from '../pages/quick-manage/quickStrResult'
import quickStrDetail from '../pages/quick-manage/quickStrDetail'
import qucikStrRecord from '../pages/quick-manage/STRRecord.vue'
import qucikStrRecordView from '../pages/quick-manage/STRRecordView.vue'
import quickYSTR from '../pages/quick-manage/Y_STR'
import quickYStrResult from '../pages/quick-manage/quick_YStrResult'
import quickYStrDetail from '../pages/quick-manage/quick_YStrDetail'
import quickYstrRecord from '../pages/quick-manage/Y_STRRecord.vue'
import quickYstrRecordView from '../pages/quick-manage/Y_STRRecordView.vue'
import geneticSTR from '../pages/genetic-manage/STR'
import geneticYSTR from '../pages/genetic-manage/YSTR'
import geneticKit from '../pages/genetic-manage/Kit'
import geneticKitEdit from '../pages/genetic-manage/KitEdit'
import geneticLikelihoodRatio from '../pages/genetic-manage/LikelihoodRatio'
import geneticPaternity from '../pages/genetic-manage/Paternity'
import PopulationGene from '../pages/genetic-manage/PopulationGene'
import PopulationGeneDetail from '../pages/genetic-manage/PopulationGeneDetail'
import SettingGeneLocus from '../pages/system-settings/GeneLocus'
import SettingWarehousingSTR from '../pages/system-settings/STR'
import SettingWarehousingYSTR from '../pages/system-settings/YSTR'
import SettingWarehousingMixSTR from '../pages/system-settings/MixSTR'
import SettingUser from '../pages/system-settings/User'
import SettingAuthority from '../pages/system-settings/Authority'
import SettingLaboratory from '../pages/system-settings/Laboratory' // 实验室管理
import SettingLaboratoryInfo from '../pages/system-settings/LaboratoryInfo'
import SettingConfiguration from '../pages/system-configuration/Index' // 系统配置
import SettingMethod from '../pages/system-configuration/Method'
import ThaninHomotype from '../pages/thanin-manage/Homotype' // 比中
import ThaninHtDetail from '../pages/thanin-manage/HomotypeDetail'
import ThaninaHtSourceDetail from '../pages/thanin-manage/HomotypeSourceDetail'
import ThaninKsDetail from '../pages/thanin-manage/KinshipDetail'
import ThaninKinship from '../pages/thanin-manage/Kinship'
// import ThaninMixYSTR from '../pages/thanin-manage/MixYSTR'
// import ThaninMixYstrDetail from '../pages/thanin-manage/mixYstrDetail'
import MonitorCaseReport from '../pages/data-monitoring-manage/CaseReport' // 数据监控
import MonitorCaseReportWait from '../pages/data-monitoring-manage/CaseReportWait'
import MonitorCaseReportFail from '../pages/data-monitoring-manage/CaseReportFail'
import MonitorCaseReportSuccess from '../pages/data-monitoring-manage/CaseReportSuccess'
import MonitorDataBaseReport from '../pages/data-monitoring-manage/DataBaseReport'
import MonitorDataComparison from '../pages/data-monitoring-manage/DataComparison'
import Logins from '../components/Logins'
// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// }
Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '/login',
    component: Logins
  },
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect: '/login',
    children: [
      {
        path: '/home',
        name: '/home',
        component: Home
      },
      // 数据综合管理 案件数据管理
      {
        path: '/datamanage/case', // 案件数据管理
        name: '/datamanage/case',
        component: () => import('@/pages/data-manage/Case'),
        // component: CaseDataManage,
        meta: {
          keepAlive: true, // 此组件需要被缓存
          isBack: false // 用于判断上一个页面是哪个
        }
      },
      {
        path: '/datamanage/CaseDetail', // 案件数据管理 查看 - 案件详情
        name: '/datamanage/case',
        component: CaseDetail
      },
      {
        path: '/datamanage/CaseDetailGene', // 案件数据管理 - 案件详情- 查看基因分型
        name: '/datamanage/case',
        component: CaseDetailGene
      },
      // 数据综合管理 建库数据管理
      // {
      //   path: '/datamanage/builddatabase', // 建库数据管理
      //   name: 'BuildDatabaseManage',
      //   component: BuildDatabaseManage
      // },
      {
        path: '/datamanage/builddatabase', // 建库数据管理
        name: '/datamanage/builddatabase',
        component: BuildDatabaseManage,
        meta: {
          keepAlive: true, // 此组件需要被缓存
          isBack: false // 用于判断上一个页面是哪个
        }
      },
      {
        path: '/datamanage/BuildDatabaseDetail', // 建库数据管理 - 查看建库人员详情
        name: '/datamanage/builddatabase',
        component: BuildDatabaseManageDetail
      },
      // 数据综合管理 质控数据管理
      {
        path: '/datamanage/QualityControl', // 质控数据管理
        name: '/datamanage/QualityControl',
        component: QualityControl,
        meta: {
          keepAlive: true, // 此组件需要被缓存
          isBack: false // 用于判断上一个页面是哪个
        }
      },
      {
        path: '/datamanage/QualityControlPollutionRecord', // 质控数据管理 - 查看污染记录
        name: '/datamanage/QualityControl',
        component: QualityControlPollutionRecord,
        meta: {
          keepAlive: true, // 此组件需要被缓存
          isBack: false // 用于判断上一个页面是哪个
        }
      },
      // {
      //   path: '/datamanage/addQtyCtrlSam', // 质控数据管理 - 添加质控样本
      //   name: 'addQtyCtrlSam',
      //   component: addQtyCtrlSam
      // },
      {
        path: '/datamanage/addQtyCtrlSam', // 质控数据管理 - 添加质控样本
        name: '/datamanage/QualityControl',
        component: () => import('@/pages/data-manage/addQtyCtrlSam')
      },
      {
        path: '/datamanage/qtyCtrlDetails', // 质控数据管理 - 查看污染记录 - 查看详情
        name: '/datamanage/QualityControl',
        component: qtyCtrlDetails
      },
      {
        path: '/datamanage/miss', // 数据综合管理 - 失踪人员
        name: '/datamanage/miss',
        component: () => import('@/pages/data-manage/miss')
      },
      {
        path: '/datamanage/missDetails', // 数据综合管理 - 失踪人员 - 查看详情
        name: '/datamanage/miss',
        component: () => import('@/pages/data-manage/missDetails')
      },
      {
        path: '/datamanage/unknown', // 数据综合管理 - 身份不明
        name: '/datamanage/unknown',
        component: () => import('@/pages/data-manage/unknown')
      },
      {
        path: '/datamanage/unknownDetails', // 数据综合管理 - 身份不明 - 身份不明人员详情
        name: '/datamanage/unknown',
        component: () => import('@/pages/data-manage/unknownDetails')
      },
      //  快速比对管理
      {
        path: '/quickmanage/Homotype', // 快速比对管理 - 同型比对
        name: '/quickmanage/Homotype',
        component: Homotype
      },
      {
        path: '/quickmanage/HomotypeResult', // 快速比对管理 - 同型比对 - 比对结果列表
        name: '/quickmanage/Homotype',
        component: HomotypeResult
      },
      {
        path: '/quickmanage/HomotypeResultDetail', // 快速比对管理 - 同型比对 - 比对结果列表 -查看详情
        name: '/quickmanage/Homotype',
        component: HomotypeResultDetail
      },
      {
        path: '/quickmanage/HomotypeRecord', // 快速比对-同型比对-比对记录
        name: '/quickmanage/Homotype',
        component: HomotypeRecord
      },
      {
        path: '/quickmanage/HomotypeRecordView', // 快速比对-同型比对-比对记录-查看
        name: '/quickmanage/Homotype',
        component: HomotypeRecordView
      },
      {
        path: '/quickmanage/Kinship', // 快速比对管理 - 亲缘比对
        name: '/quickmanage/Kinship',
        component: Kinship
      },
      {
        path: '/quickmanage/KinshipResult', // 快速比对管理 - 亲缘比对 - 比对结果列表
        name: '/quickmanage/Kinship',
        component: KinshipResult
      },
      {
        path: '/quickmanage/KinshipResultDetail', // 快速比对管理 - 亲缘比对 - 比对结果列表 - 比对结果详情
        name: '/quickmanage/Kinship',
        component: KinshipResultDetail
      },
      {
        path: '/quickmanage/KinshipRecord', // 快速比对管理 - 亲缘比对 - 比对记录
        name: '/quickmanage/Kinship',
        component: KinshipRecord
      },
      {
        path: '/quickmanage/KinshipRecordView', // 快速比对管理 - 亲缘比对 - 比对记录-查看
        name: '/quickmanage/Kinship',
        component: KinshipRecordView
      },
      {
        path: '/quickmanage/STR', // 快速比对管理 - 混合STR比对
        name: '/quickmanage/STR',
        component: quickSTR
      },
      {
        path: '/quickmanage/quickStrResult', // 快速比对管理 - 混合STR比对 - 比对结果列表
        name: '/quickmanage/STR',
        component: quickStrResult
      },
      {
        path: '/quickmanage/quickStrDetail', // 快速比对管理 - 混合STR比对 -比对结果 - 比对详情
        name: '/quickmanage/STR',
        component: quickStrDetail
      },
      {
        path: '/quickmanage/StrRecord', // 快速比对 - 混合str - 比对记录
        name: '/quickmanage/STR',
        component: qucikStrRecord
      },
      {
        path: '/quickmanage/StrRecordView', // 快速比对 - 混合str - 比对记录 - 查看
        name: '/quickmanage/STR',
        component: qucikStrRecordView
      },
      {
        path: '/quickmanage/Y_STR', // 快速比对管理 - Y-STR比对
        name: '/quickmanage/Y_STR',
        component: quickYSTR
      },
      {
        path: '/quickmanage/quick_YStrResult', // 快速比对管理 - Y-STR比对 - 比对结果
        name: '/quickmanage/Y_STR',
        component: quickYStrResult
      },
      {
        path: '/quickmanage/quick_YStrDetail', // 快速比对管理 - Y-STR比对 - 比对结果 - 比对详情
        name: '/quickmanage/Y_STR',
        component: quickYStrDetail
      },
      {
        path: '/quickmanage/quickYstrRecord', // 快速比对管理 - Y-STR比对 - 比对记录
        name: '/quickmanage/Y_STR',
        component: quickYstrRecord
      },
      {
        path: '/quickmanage/quickYstrRecordView', // 快速比对管理 - Y-STR比对 - 比对记录 - 查看
        name: '/quickmanage/Y_STR',
        component: quickYstrRecordView
      },
      // 基础遗传数据管理
      {
        path: '/geneticmanage/STR', // 基础遗传数据管理 - STR基因座管理
        name: '/geneticmanage/STR',
        component: geneticSTR
      },
      {
        path: '/geneticmanage/YSTR', // 基础遗传数据管理 - Y-STR基因座管理
        name: '/geneticmanage/YSTR',
        component: geneticYSTR
      },
      {
        path: '/geneticmanage/Kit', // 基础遗传数据管理 - 试剂盒管理
        name: '/geneticmanage/Kit',
        component: geneticKit
      },
      {
        path: '/geneticmanage/KitEdit', // 基础遗传数据管理 - 试剂盒管理 - 编辑页面
        name: '/geneticmanage/Kit',
        component: geneticKitEdit
      },
      {
        path: '/geneticmanage/PopulationGene', // 基础遗传数据管理 - 种群基因频率管理
        name: '/geneticmanage/PopulationGene',
        component: PopulationGene
      },
      {
        path: '/geneticmanage/PopulationGeneDetail', // 基础遗传数据管理 - 种群基因频率管理 - 编辑页面
        name: '/geneticmanage/PopulationGene',
        component: PopulationGeneDetail
      },
      {
        path: '/geneticmanage/LikelihoodRatio', // 基础遗传数据管理 - 似然率管理
        name: '/geneticmanage/LikelihoodRatio',
        component: geneticLikelihoodRatio
      },
      {
        path: '/geneticmanage/Paternity', // 基础遗传数据管理 - 亲权指数管理
        name: '/geneticmanage/Paternity',
        component: geneticPaternity
      },
      {
        path: '/setting/genelocus',
        name: '/setting/genelocus',
        component: SettingGeneLocus
      },
      {
        path: '/setting/warehousing/str',
        name: '/setting/warehousing/str',
        component: SettingWarehousingSTR
      },
      {
        path: '/setting/warehousing/ystr',
        name: '/setting/warehousing/ystr',
        component: SettingWarehousingYSTR
      },
      {
        path: '/setting/warehousing/mixstr',
        name: '/setting/warehousing/mixstr',
        component: SettingWarehousingMixSTR
      },
      {
        path: '/setting/user',
        name: '/setting/user',
        component: SettingUser
      },
      {
        path: '/setting/authority',
        name: '/setting/authority',
        component: SettingAuthority
      },
      {
        path: '/setting/laboratory',
        name: '/setting/laboratory',
        component: SettingLaboratory
      },
      {
        path: '/setting/laboratoryinfo',
        name: '/setting/laboratory',
        component: SettingLaboratoryInfo
      },
      {
        path: '/setting/configuration', // 系统配置 - 数据转换配置
        name: '/setting/configuration',
        component: SettingConfiguration
      },
      {
        path: '/setting/method', // 系统配置 - 数据转换配置 - 编辑/添加
        name: '/setting/configuration',
        component: SettingMethod
      },
      // 比中信息管理
      {
        path: '/thanin/homotype', // 比中信息管理 - 同型比中
        name: '/thanin/homotype',
        component: ThaninHomotype
      },
      {
        path: '/thanin/homotypeDetail', // 比中信息管理 - 同型比中 - 查看详情
        name: '/thanin/homotype',
        component: ThaninHtDetail
      },
      {
        path: '/thanin/homotypeSourceDetail', // 比中信息管理 - 同型比中 - 查看详情 - 查看源样本信息
        name: '/thanin/homotype',
        component: ThaninaHtSourceDetail
      },
      {
        path: '/thanin/kinship', // 比中信息管理 - 亲缘比中
        name: '/thanin/kinship',
        component: ThaninKinship
      },
      {
        path: '/thanin/KinshipDetail', // 比中信息管理 - 亲缘比中 - 查看详情
        name: '/thanin/kinship',
        component: ThaninKsDetail
      },
      // {
      //   path: '/thanin/ystr', // 比中信息管理 - Y-STR比中
      //   name: 'ThaninMixYSTR',
      //   component: ThaninMixYSTR
      // },
      {
        path: '/thanin/ystr', // 比中信息管理 - Y-STR比中
        name: '/thanin/ystr',
        component: () => import('@/pages/thanin-manage/MixYSTR')
      },
      {
        path: '/thanin/mixYstrDetail', // 比中信息管理 - Y-STR比中 - 查看详情
        name: '/thanin/ystr',
        // meta: {
        //   title: ''
        // },
        component: () => import('@/pages/thanin-manage/mixYstrDetail')
      },
      {
        path: '/monitoring/case',
        name: '/monitoring/case',
        component: MonitorCaseReport
      },
      {
        path: '/monitoring/case/wait',
        name: '/monitoring/case',
        component: MonitorCaseReportWait
      },
      {
        path: '/monitoring/case/fail',
        name: '/monitoring/case',
        component: MonitorCaseReportFail
      },
      {
        path: '/monitoring/case/success',
        name: '/monitoring/case',
        component: MonitorCaseReportSuccess
      },
      {
        path: '/monitoring/database',
        name: '/monitoring/database',
        component: MonitorDataBaseReport
      },
      {
        path: '/monitoring/compare',
        name: '/monitoring/compare',
        component: MonitorDataComparison
      },
      {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "about" */ '../views/About.vue')
      },
      {
        path: '/settings/authorityEdit', // 系统管理 - 权限管理 - 权限管理
        name: '/settings/authorityEdit',
        component: () => import('@/pages/system-settings/authorityEdit')
      },
      {
        path: '/settings/roleManag', // 系统管理 - 权限管理 - 添加/编辑角色
        name: '/settings/authorityEdit',
        component: () => import('@/pages/system-settings/roleManag')
      }
      // {
      //   path: '/entrusted/miss', // 委托管理 - 失踪人员
      //   name: '/entrusted/miss',
      //   component: () => import('@/pages/entrusted-manage/miss')
      // },
      // {
      //   path: '/entrusted/missEdit', // 委托管理 - 失踪人员 - 编辑
      //   name: '/entrusted/miss',
      //   component: () => import('@/pages/entrusted-manage/missEdit')
      // },
      // {
      //   path: '/entrusted/unknown', // 委托管理 - 身份不明
      //   name: '/entrusted/unknown',
      //   component: () => import('@/pages/entrusted-manage/unknown')
      // }
    ]
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
