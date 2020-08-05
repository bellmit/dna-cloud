import Vue from "vue";
import Router from "vue-router";
import Home from "@/page/home/index";
import dataBase from "@/page/dataBase/index";
import Login from "@/page/login/login";
import Case from "@/page/case/index";
import Hybrid from "@/page/hybrid/index";
import More from "@/page/more/index";
import thanDetails from "@/page/thanDetails/index";
import Break from "@/page/break/index";
import ComSchedule from "@/page/ComSchedule/index";
import QuickMatch from "@/page/quickMatch/index";
import QuickMixedSplit from "@/page/quickMixedSplit/index";
import MatchRecord from "@/page/MatchRecord/index";
import EarlyWarning from "@/page/EarlyWarning/index";
import reportGpm from "@/page/reportGpm/index";
import refresh from "@/components/refresh";
import User from "@/page/user/index"
Vue.use(Router);

export default new Router({
  mode: "history",
  // base:'/bazlDnaMix',
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home,
      meta: {
        index: 0
      }
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
      meta: {
        keepAlive: true
      }
    },
    // {
    //   path: "/",
    //   redirect: "/login"
    // },
    {
      path: "/data",
      name: "dataBase",
      component: dataBase,
      meta: {
        index: 4
      }
    },
    {
      path: "/case",
      name: "Case",
      component: Case,
      meta: {
        index: 2
      }
    },
    {
      path: "/hybrid",
      name: "Hybrid",
      component: Hybrid,
      meta: {
        index: 2
      }
    },
    {
      path: "/more",
      name: "More",
      component: More
    },
    {
      path: "/thanDetails",
      name: "thanDetails",
      component: thanDetails,
      meta: {
        index: 4
      }
    },
    {
      path: "/break",
      name: "Break",
      component: Break,
      meta: {
        index: 2
      }
    },
    {
      path: "/ComSchedule",
      name: "ComSchedule",
      component: ComSchedule
    },
    {
      path: "/quickMatch",
      name: "quickMatch",
      component: QuickMatch,
      meta: {
        index: 1
      }
    },
    {
      path: "/quickMixedSplit",
      name: "QuickMixedSplit",
      component: QuickMixedSplit,
      meta: {
        index: 1
      }
    },
    {
      path: "/MatchRecord",
      name: "MatchRecord",
      component: MatchRecord,
      meta: {
        index: 3
      }
      // children:[
      //   {
      //     path: 'EarlyWarning',
      //     name: 'EarlyWarning',
      //     component: EarlyWarning
      //   },
      // ]
    },
    {
      path: "/EarlyWarning",
      name: "EarlyWarning",
      component: EarlyWarning,
      meta: {
        index: 3
      }
    },
    {
      path: "/reportGpm",
      name: "reportGpm",
      component: reportGpm,
      meta: {
        index: 2
      }
    },
    {
      path: "/refresh",
      name: "refresh",
      component: refresh,
      meta: {
        index: 1
      }
    },
    {
      path: '/user',
      name: 'User',
      component: User
    }
  ]
});
