<template>
  <div class="home-box">
    <div class="home-top-box">
      <div class="widthDiv">
        <!-- 版本迭代 -->
        <router-link tag="span" to="/case">
          <img src="../../assets/img/baobiao.png" />案件智能快速分析
        </router-link>
        <!-- 待开发功能 -->
        <router-link tag="span" to="/quickMatch">
          <img src="../../assets/img/hl.png" />DNA分析快速比对
        </router-link>
        <!-- 待开发功能 -->
        <router-link tag="span" to="/MatchRecord">
          <img src="../../assets/img/xs.png" />比对记录
        </router-link>
        <!-- 版本迭代 -->
        <router-link tag="span" to="/data">
          <img src="../../assets/img/baobiao.png" />混合数据库管理
        </router-link>
      </div>
    </div>
    <div class="home-center-box clearfix">
      <div class="widthDiv">
        <div class="home-center-leftbox">
          <div class="home-type-body">
            <div ref="MyTypeCharts" class="MyTypeCharts-con"></div>
          </div>
        </div>
        <div class="home-center-rightbox">
          <div class="rightbox-title">
            混合分型/拆分分型
          </div>
          <div class="rightbox-body">
            <div class="rabius-con class-1">
                <div class="rabius-first">
                  <div>
                    <p>{{mixcountNum}}</p>
                  </div>
                </div>
                <p class="rabius-p-style">混合分型数量</p>
            </div>
            <div class="rabius-con">
                <div class="rabius-first class">
                  <div>
                    <p>{{splitcountNum}}</p>
                  </div>
                </div>
                <p class="rabius-p-style">拆分分型数量</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="home-bottom-box">
      <div class="widthDiv">
        <div class="home-DNA-body">
          <div ref="MyDNACharts" class="MyDNACharts-con"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { formatDate } from "../../components/data";
import homeServers from "../../servers/homeServer";
// import ModalDetail from "../../components/ModalDetail";
import caseServer from "../../servers/caseServer";
import dataServers from "../../servers/dataServers";
import echarts from "echarts/lib/echarts";
import markPoint from "echarts/lib/component/markPoint";
export default {
  name: "homeBox",
  // components: { ModalDetail },
  data() {
    return {
      mixcountNum:0,
      splitcountNum:0,
      newName:[],
      newCount:[],
      MixcountData:[],
      SingcountData:[],
      SplitcountData:[],
      // 折线图配置项 DNA数据库样本
      DnaOption:{
        title:[
          {
            text:'DNA数据库样本',
            x:40,
            y:"3%",
        　　textStyle:{
              color:"#000",
              fontSize:18,
            }
          },
          {
            text:'2020  月份/数量',
            right:40,
            y:"3%",
        　　textStyle:{
              color:"#aaa",
              fontSize:14,
            }
          }
        ],
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["单一样本", "混合样本"],
          y: "3%",
          icon: "circle", 
          itemWidth:15,
          itemHeight:15,
        },
        grid: {
          // top:"1%",
          left: "3%",
          right: "4%",
          // bottom: "3%",
          containLabel: true
        },
        // toolbox: {
        //   feature: {
        //     saveAsImage: {}
        //   }
        // },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月","八月","九月","十月","十一月","十二月"],
          splitLine: {
            // 网格线
            lineStyle: {
              type: "dashed" //设置网格线类型 dotted：虚线   solid:实线
            },
            show: true //隐藏或显示
          },
          axisLabel: {
            show: true,
            textStyle: {
              //textStyle y轴下的字体的样式
              color: "#000",
              fontSize: 14
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              //lineStyle Y 轴线样式
              color: "#ccc",
              width: 1,
              type: "dashed"
            }
          }
        },
        yAxis: {
          type: "value",
          splitLine: {
            //网格线
            lineStyle: {
              type: "dashed" //设置网格线类型 dotted：虚线   solid:实线
            },
            show: true //隐藏或显示
          },
          axisLabel: {
            show: true,
            textStyle: {
              //textStyle y轴下的字体的样式
              color: "#aaa",
              fontSize: 14
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              //lineStyle Y 轴线样式
              color: "#ccc",
              width: 1,
              type: "dashed"
            }
          }
        },
        series: [
          {
            name: "单一样本",
            type: "line", // 类型
            // stack: "总量",
            symbolSize:12, // 拐点尺寸
            symbol:'circle', // 拐点类型 实心
            color:"#006CFF", // 拐点颜色  
            //[120, 132, 150, 200, 220, 250, 270,300,450,500,550,600],
            // data:this.SingcountData,
            data:[],
            itemStyle:{
              normal:{
                // 拐点上显示数值
                label : {
                // show: true
                },
                borderColor:'#006CFF',  // 拐点边框颜色
                // backgroundColor:'#006CFF', 
                lineStyle:{                 
                  width:2,  // 设置线宽
                  type:'solid',  //'dotted'虚线 'solid'实线
                  color:"#006CFF",
                }
              }
            }
          },
          {
            name: "混合样本",
            type: "line",
            // stack: "总量",
            symbolSize:12,
            symbol:'circle',
            color:"#FFA400",   
            // [30, 63, 87, 168, 365, 400, 420,430,480,510,570,670],
            data:[],
            itemStyle:{
              normal:{
                // 拐点上显示数值
                label : {
                // show: true
                },
                borderColor:'#FFA400',  // 拐点边框颜色
                lineStyle:{                 
                  width:2,  // 设置线宽
                  type:'solid',  //'dotted'虚线 'solid'实线
                  color:"#FFA400",
                }
              }
            }
          },
          // {
          //   name: "拆分样本",
          //   type: "line",
          //   // stack: "总量",
          //   symbolSize:12, // 折线原点大小
          //   symbol:'circle',
          //   color:"#00B89B",// [20, 23, 30, 67, 89, 108, 123,156,280,300,324,0],
          //   data: [],
          //   itemStyle:{
          //     normal:{
          //       // 拐点上显示数值
          //       label : {
          //       // show: true
          //       },
          //       borderColor:'#00B89B',  // 拐点边框颜色
          //       // borderWidth: 1,
          //       lineStyle:{                 
          //         width:2,  // 设置线宽
          //         type:'solid',  //'dotted'虚线 'solid'实线
          //         color:"#00B89B",
          //       }
          //     }
          //   }
          // },
        ]
      },
      // 柱形图配置项 比中类型
      TypeOptin:{
        title:[
          {
            text:'比中类型',
            x:40,
            y:"3%",
        　　textStyle:{
              color:"#000",
              fontSize:18,
            }
          },
          {
            text:'类型/数量',
            // left:"right",
            right:40,
            y:"3%",
        　　textStyle:{
              color:"#aaa",
              fontSize:14,
            }
          }
        ],
        dataZoom: [
            {
                show: true,
                start: 0,
                end: 100
            },
            {
                type: 'inside',
                start: 0,
                end: 100
            },
        ],
        tooltip: {},
        xAxis: {
          boundaryGap: true,
          data: [],
          axisTick: {
            // show: true,  //是否显示网状线 默认为true
            // alignWithLabel: true
          },
          axisLine: {
            show: true, // 这里的show用于设置是否显示x轴那一条线 默认为true
            lineStyle: {
              // lineStyle里面写x轴那一条线的样式
              color: "#ccc",
              width: 1, // 轴线的粗细 最小为0，值为0的时候线隐藏
              type: "dashed" // 轴线类型 虚线
            }
          },
          axisLabel: {
            show: true, //这里的show用于设置是否显示x轴下的字体 默认为true
            interval: 0, // X轴字体显示 0全部显示 1隔行显示
            //  rotate:30,
            textStyle: {
              //textStyle里面写x轴下的字体的样式
              color: "#000",
              fontSize: 14
            }
          }
        },
        yAxis: {
          splitLine: {
            //网格线
            lineStyle: {
              type: "dashed" //设置网格线类型 dotted：虚线   solid:实线
            },
            show: true //隐藏或显示
          },
          axisLabel: {
            show: true,
            textStyle: {
              //textStyle y轴下的字体的样式
              color: "#aaa",
              fontSize: 14
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              //lineStyle Y 轴线样式
              color: "#ccc",
              width: 1,
              type: "dashed"
            }
          }
        },
        series: [
          // 柱形图中嵌套曲线图 实现背景色渐变
          {
            name: "",
            type: "line",
            smooth: true,
            symbol: "none",
            data: [],
            itemStyle: {
              normal: {
                // opacity:0,
                lineStyle: {
                  color: "rgba(255,0,0,0)"
                }
              }
            },
            // 线图 背景填充
            areaStyle: {
              normal: {
                color: {
                  type: "linear", //设置线性渐变
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: "#C3CCFD" // 0% 处的颜色
                    },
                    {
                      offset: 0.2,
                      color: "#CBD5FD"
                    },
                    {
                      offset: 0.5,
                      color: "#CAD3FE"
                    },
                    {
                      offset: 0.8,
                      color: "#D9E1FE"
                    },
                    {
                      offset: 1,
                      color: "white" // 100% 处的颜色
                    }
                  ],
                  globalCoord: false // 缺省为 false
                }
              }
            }
          },
          {
            name: "比中类型",
            type: "bar",
            data: [],
            barWidth: 30,
            itemStyle: {
              normal: {
                label: {
                  // 顶部标识
                  show: true,
                  position: "top",
                  color: "#000",
                  // itemWidth:50,
                  backgroundColor: "#ccc",
                  // barBorderRadius: [18, 18, 0 ,0],
                  formatter: function(params, ticket, callback) {
                    //格式化展现（标签+值）
                    return params.value;
                  }
                },
                // borderColor: '#000',
                shadowBlur: 8,
                shadowColor: "rgba(0, 0, 0, 0.5)",
                opacity: 1,
                barBorderRadius: [18, 18, 0, 0],
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: "#4660FE"
                  },
                  {
                    offset: 1,
                    color: "#629EFF"
                  }
                ])
              },
              // 鼠标移入
              emphasis: {
                barWidth: 40,
                shadowBlur: 12,
                shadowColor: "rgba(0, 0, 0, 0.5)",
                // fontSize:14
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: "#2378f7" },
                  { offset: 0.7, color: "#2378f7" },
                  { offset: 1, color: "#83bff6" }
                ]),
                label: {
                  // 顶部标识
                  // show: true,
                  // position: 'top',
                  color: "#485FFA",
                  borderColor: "solid",
                  backgroundColor: "#fff",
                  fontSize: 14,
                  formatter: function(params, ticket, callback) {
                    //格式化展现（标签+值）
                    return params.value;
                  }
                }
              }
            }
          }
        ]
      },
    };
  },
  created() {

  },
  destroyed(){
    // 清除图表自适应 避免在其他页面找不到图表容器
    window.onresize = null; 
  },
  mounted() {
    this.handelHomeData(); // 案件数据
    //this.handelserial();串并数据
    //this.handelLatest(); 质控数据
    this.handelSelectCount();
    this.DrawBar(); // 人员类型图表初始化 
    this.DnaCharts() // dna 样本数据图表初始化
    this.BarData(); // 柱形图数据
    this.DNAData();  // 折线图数据
    this.MixedSplitData(); // 混合分型/拆分分型
    // 图表自适应
    // window.addEventListener('resize', this.DrawLineResize)
    var that = this;
    window.onresize = () => {
      that.$echarts.init(this.$refs.MyTypeCharts).resize();
      that.$echarts.init(this.$refs.MyDNACharts).resize();
    };
  },
//数据自动刷新，监听机制告诉Echarts重新设置数据
  watch: {
      // DNA 数据库样本 监听 折线图
      DnaOption: { 
        handler(newVal, oldVal) {
          if (this.DnaChart) {
            if (newVal) {
              this.DnaChart.setOption(newVal);
            } else {
              this.DnaChart.setOption(oldVal);
            }
          } else {
            this.DnaCharts();
          }
        },
        deep: true //对象内部属性深度监听 。
      },
      // 比中类型 监听 柱形图
      TypeOptin:{
        handler(newVal, oldVal){
         if (this.myChart) {
            if (newVal) {
              this.myChart.setOption(newVal);
            } else {
              this.myChart.setOption(oldVal);
            }
          } else {
            this.DrawBar()
          }
        },
        deep: true //对象内部属性深度监听 。
      }
},
  methods: {
    // echarts  人员类型图表
    DrawBar() {
      // 图标容器
      let myChart = this.$echarts.init(this.$refs.MyTypeCharts);
      myChart.clear();
      myChart.setOption(this.TypeOptin,true);
    },
    // DNA样本数据 图表初始化
    DnaCharts() {
      // 容器
      let DnaChart = this.$echarts.init(this.$refs.MyDNACharts);
      DnaChart.clear();
      DnaChart.setOption(this.DnaOption,true);
    },
    // 柱形图数据
    BarData(){     
        homeServers.BarTypeData().then(res => {
          for(let item of res.data){
            this.newName.push(item.name);
            this.newCount.push(item.count);
          }
          this.TypeOptin.xAxis.data = this.newName;
          this.TypeOptin.series[0].data = this.newCount;
          this.TypeOptin.series[1].data = this.newCount;
      });
    },
    // DNA数据库样本 折线图数据
    DNAData(){     
        homeServers.LineDNAData().then(res => {
           this.DnaOption.series[1].data = res.data.mixcount;
          //  this.DnaOption.series[2].data = res.data.splitcount;
           this.DnaOption.series[0].data = res.data.singcount;
      });
    },
    // 混合样本/拆分样本数量  数据
    MixedSplitData(){     
        homeServers.RadiusData().then(res => {
           this.mixcountNum =  res.data.mixcount;
           this.splitcountNum = res.data.splitcount;
      });
    },
    //删除操作
    allCompare_Delete() {
      let allId = {
        id: this.DeleteId
      };
      homeServers.allCompareDelete(allId).then(res => {
        if (res.code == 1) {
          this.$Message.success("删除成功！");
          this.all_Compare();
        } else {
          this.$Message.error("删除失败！");
        }
      });
    },
    // 分页监听事件
    allCompareChange(pageValue) {
      this.allCompareTotalCurrent = pageValue;
      // console.log(this.allCompareTotalCurrent);
      this.all_Compare();
    },
    // 全库任务接口
    all_Compare() {
      let allInfo = {
        page: this.allCompareTotalCurrent
      };
      homeServers.allCompare(allInfo).then(res => {
        // console.log(res);
        // this.allCompareTotal = res.data.
        this.allPageList = res.data.pageInfo.pageCount;
        this.allCompareTotal = res.data.pageInfo.allRecordCount;
        this.allCompareData = res.data.infoList;
      });
    },
    //全库详情返回按钮
    allReturn() {
      this.all_return = false;
      this.allCompareDetailsShow = false;
      this.allCompareShow = true;
    },
    BtnTableShow(index) {
      // console.log(index);
      this.active = index;
      if (index == 0) {
        this.handelHomeData();
        this.SuspectShow = true;
        this.SerialShow = false;
        this.Serial_topShow = false;
        this.allCompareShow = false;
        this.QuickSplitShow = false;
        this.allCompareDetailsShow = false;
      } else if (index == 1) {
        this.handelserial();
        this.SuspectShow = false;
        this.SerialShow = true;
        this.Serial_topShow = false;
        this.allCompareShow = false;
        this.QuickSplitShow = false;
        this.allCompareDetailsShow = false;
      } else if (index == 2) {
        this.handelLatest();
        this.SuspectShow = false;
        this.SerialShow = false;
        this.QuickSplitShow = false;
        this.Serial_topShow = true;
        this.allCompareShow = false;
        this.allCompareDetailsShow = false;
      } else if (index == 3) {
        this.all_Compare();
        this.all_return = true;
        this.SuspectShow = false;
        this.SerialShow = false;
        this.Serial_topShow = false;
        this.QuickSplitShow = false;
        this.allCompareShow = true;
        this.allCompareDetailsShow = false;
      } else if (index == 4) {
        this.QuickSplitList();
        this.QuickSplitShow = true;
        this.all_return = false;
        this.SuspectShow = false;
        this.SerialShow = false;
        this.Serial_topShow = false;
        this.allCompareShow = false;
        this.allCompareDetailsShow = false;
      }
    },
    // 快速拆分结果表格 分页监听事件
    QuickSplitChange(PageVal) {
      // console.log(PageVal);
      this.QuickSplitCurrent = PageVal;
      this.QuickSplitList();
    },
    // 快速拆分比对结果表格数据
    QuickSplitList() {
      let pageInfo = {
        curPage: this.QuickSplitCurrent
      };
      dataServers.dataList(pageInfo).then(res => {
        this.QuickSplitData = res.data.caseMixedSampleGeneVoList;
        this.QuickSplitPageList = res.data.pageInfo.pageCount;
        this.QuickSplitTotal = res.data.pageInfo.allRecordCount;
      });
    },
    //查看更多
    handelMore(status) {
      let Data = {
        moreUrl: "",
        moreName: ""
      };
      if (status == 1) {
        Data = {
          moreUrl: "/main/moreMatchedSuspectList",
          moreName: "案件检材嫌疑人比中"
        };
      } else if (status == 2) {
        Data = {
          moreUrl: "/main/moreSerialCaseMixedSampleList",
          moreName: "串并案检材比中"
        };
      } else {
        Data = {
          moreUrl: "/main/moreQualityMixedSampleList",
          moreName: "质控人员比中列表"
        };
      }
      let moreData = JSON.stringify(Data);
      localStorage.setItem("moreData", moreData);
      this.$router.push("/more");
    },
    handelHomeData() {
      //嫌疑人接口
      // console.log("1111");
      homeServers.homeList().then(res => {
        // console.log(res);
        this.suspect_list = res.data;
      });
    },
    handelPersonCount() {
      //嫌疑人总数
      homeServers.homePerson().then(res => {
        this.suspect_count = res.data;
      });
    },
    handelserial() {
      //检材比中接口
      // console.log(2222);
      homeServers.dataSerial().then(res => {
        this.serial = res.data;
      });
    },
    handelserialCount() {
      //检材比中总数
      homeServers.dataSerialCount().then(res => {
        this.suspect_count1 = res.data;
      });
    },
    handelSelectCount() {
      homeServers.dataSelectCount().then(res => {
        this.selectCount = res.data;
      });
    },
    handelLatest() {
      // console.log(333);
      homeServers.dataLatest().then(res => {
        this.serial_top = res.data;
      });
    },
    handelLatestCount() {
      homeServers.dataLatestCount().then(res => {
        // console.log(res);
        this.lasterCount = res.data;
      });
    },
    ToHybridSplit() {
      this.$router.push({ path: "/HybridSplit" });
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/home";
</style>
