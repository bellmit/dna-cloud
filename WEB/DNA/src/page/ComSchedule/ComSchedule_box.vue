<template>
  <div id="schedule">
      <div class="home_title">
        <router-link tag="b" to="/">首页 》</router-link>
         <span>比对进度</span>
      </div>
      <div class="header-title">
          <div>
            <span class="margin-left"></span>
            <span>比对进度</span>
          </div>
      </div>
      <div class="schedule-title">
          <div>
              <p style="font-size: 14px;">目前数据量300万，预计比中时间30分钟</p>
              <div>
                <p>
                  <span></span>
                </p>
                <p>0%</p>
              </div>
          </div> 
      </div>
      <div class="header-title"> 
          <div>
              <span class="margin-left"></span>
              <span>比中列表</span>
          </div>
      </div>
      <div class="schedule-table-body">
        <div v-if="ScheduleData != null && ScheduleData != [] && ScheduleData.length != 0">
          <div>
             <Table border class="schedule-tbale-style"  :columns="ScheduleCol" :data="ScheduleData"></Table>
          </div>
          <div class="page">
          <span>
            当前第{{currentPage}}页/共1页/共{{allRecordCount}}条信息
          </span>
          <Page :total="allRecordCount" show-elevator prev-text="上一页" next-text="下一页" class-name="bazl_page" :current="currentPage" @on-change="ScheduleChange" :page-size="15"/>
          </div>
        </div>
        <div class="error-con" v-else>
          <div>
             <Table border class="display-none"  :columns="ScheduleCol"></Table>
            <div class="borderStyle">
              <div>
                <p><img src="../../assets/img/break_err.png" alt=""></p>
                <p>您好，目前暂无数据显示</p>
                <p>您可尝试其他操作</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="schedule-fix-footer" v-show="back">
        <p @click="GOTOBACK" >返回</p>
      </div>
  </div>
</template>

<script>
import { type } from 'os';
import ComScheduleServer from "@/servers/ComScheduleServer";
export default {
  name: "ComScheduleBox",
  data() {
    return {
      // 比对进度表格 页码
      currentPage:1,
      // 比对进度表格总条数
      allRecordCount:0,
      // 比对进度表格标题
      ScheduleCol:[
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "样本编号",
          key: "yb", 
          width: "150"
        },
        {
          title: "样本名称",
          key: "ym",
          // width: "120"
        },
        {
          title: "Y-STR",
          key: "yr",
          // width: "80"
          render: (h, params) => {
            // console.log(params.row.yr);
            var Class = "";
            if(params.row.yr == "男"){
                Class = "span-style-mr";
            }else{
                Class = "span-style-men";
            }
            return h(
                "span",
                  {
                    class:Class,
                    style: {
                      display: "inline-block",
                      backgroung:"rgba(0,118,255,1)",
                    },
                  },
                 params.row.yr
              )
          }
        },
        {
          title: "类型",
          key: "lx",
          // width: "80",
        },
        {
          title: "案件编号",
          key: "aj",
          // width: "120"
        },
        {
          title: "案件名称",
          key: "mc",
          // width: "120"
        },
        {
          title: "匹配位点个数",
          key: "pp",
          // width: "120"
        },
        {
          title: "容差个数",
          key: "rc",
          // width: "120"
        },
        {
          title: "操作",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: event => {
    
                    }
                  }
                },
                "查看"
              ),
            ]);
          }
        }  
      ],
      ScheduleData:[],
      // 比对进度表格数据
      // ScheduleData:[
      //   {
      //     "yb":"CY-21932911-09009",
      //     "ym":"XXXX",
      //     "yr":"男",
      //     "lx":"人员类型",
      //     "aj":"Fy-220i4-kklow2",
      //     'mc':"其他类别案件",
      //     "pp":"2",
      //     "rc":"1",
      //   },
      //   {
      //     "yb":"CY-2459469-56666",
      //     "ym":"XXXX",
      //     "yr":"女",
      //     "lx":"XXX类型",
      //     "aj":"Fy-2www-567-3-",
      //     'mc':"XXX案件类型",
      //     "pp":"8",
      //     "rc":"5",
      //   }
      // ],
       
       back:true,
    };
  },
  mounted() {
    // console.log(this.ScheduleData);
  },

  beforeDestroy() {

  },
  created() { 
    if(this.$route.params.backType == 1){
      this.back = !this.back
    }
  },
  methods: {
    ScheduleChange(val){

    },
    GOTOBACK(){
      this.$router.go(-1)
    }
  }
}
</script>
<style lang="less">
// @import "../../assets/styles/title";
// @import "../../assets/styles/ComSchedule";
</style>
