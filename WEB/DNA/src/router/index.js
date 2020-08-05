import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/page/home/index'
import dataBase from '@/page/dataBase/index'
import Login from '@/page/login/login'
import Case from '@/page/case/index'
import Hybrid from '@/page/hybrid/index'
import More from '@/page/more/index'
import thanDetails from '@/page/thanDetails/index'
import Break from '@/page/break/index'
import Contrast from '@/page/contrast/index'
import HybridSplit from '@/page/HybridSplit/index'
import QuickComparison from '@/page/QuickComparison/index'
import ComSchedule from '@/page/ComSchedule/index'
import QuickMatch from '@/page/quickMatch/index'
import QuickMixedSplit from '@/page/quickMixedSplit/index'
import MatchRecord from '@/page/MatchRecord/index'
import EarlyWarning from '@/page/EarlyWarning/index'
Vue.use(Router);

export default new Router({
  mode:'history',
  base:'/iLabSTRmix',
  // base:'limsiLabSTRmix',
  // base:'/',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path:'/login',
      name: 'Login',
      component: Login
    },
    { 
      path: '/',
      redirect: '/login'
    },
    {
      path: '/data',
      name: 'dataBase',
      component: dataBase
    },
    {
      path: '/case',
      name: 'Case',
      component: Case
    },
    {
      path: '/hybrid',
      name: 'Hybrid',
      component: Hybrid
    },
    {
      path: '/more',
      name: 'More',
      component: More
    },
    {
      path: '/thanDetails',
      name: 'thanDetails',
      component: thanDetails
    },
    {
      path: '/break',
      name: 'Break',
      component: Break
    },
    {
      path: '/contrast',
      name: 'Contrast',
      component: Contrast
    },
    {
      path: '/HybridSplit',
      name: 'HybridSplit',
      component: HybridSplit
    },
    {
      path: '/QuickComparison',
      name: 'QuickComparison',
      component: QuickComparison
    },
    {
      path: '/ComSchedule',
      name: 'ComSchedule',
      component: ComSchedule
    },
    {
      path: '/quickMatch',
      name: 'quickMatch',
      component: QuickMatch
    },
    {
      path: '/quickMixedSplit',
      name: 'QuickMixedSplit',
      component: QuickMixedSplit
    },
    {
      path: '/MatchRecord',
      name: 'MatchRecord',
      component: MatchRecord
    },
    {
      path: '/EarlyWarning',
      name: 'EarlyWarning',
      component: EarlyWarning
    },
  ]
});
