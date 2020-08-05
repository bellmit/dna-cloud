<template>
  <div class="home-box">
    <div class="home-center-box clearfix">
      <div class="widthDiv">
        <div class="home-center-leftbox">
          <div class="zx-title">
            <div>最新比中情况一览表</div>
            <router-link tag="div" to="/MatchRecord">更多</router-link>
          </div>
          <div class="swiper-posi-rel">
            <div class="swiper-container">
              <div class="swiper-wrapper">
                <div
                  v-for="(item,index) in detailsData"
                  :key="index"
                  class="swiper-slide details-container"
                >
                  <div class="details-title padding">
                    <div class="over_li" :title="item.sampleNo">
                      <span>混合样本编号</span>
                      <span>{{item.sampleNo}}</span>
                    </div>
                    <div class="over_li" :title="item.sampleName">
                      <span>混合样本名称</span>
                      <span>{{item.sampleName}}</span>
                    </div>
                  </div>
                  <ul class="details-item">
                    <li>
                      <span class="blue-dot">比中时间</span>
                      <span>{{item.comparisonTime}}</span>
                    </li>
                    <li>
                      <span class="blue-dot">比中类型</span>
                      <span>{{item.sampleFlag}}</span>
                    </li>
                    <li class="over_li" :title="item.proportionSampleNo">
                      <span class="blue-dot">比中样本编号</span>
                      <span>{{item.proportionSampleNo}}</span>
                    </li>
                    <li class="over_li" :title="item.proportionSampleName">
                      <span class="blue-dot">比中样本名称</span>
                      <span>{{item.proportionSampleName}}</span>
                    </li>
                    <li>
                      <span class="blue-dot">匹配位点个数</span>
                      <span>{{item.ratio}}</span>
                    </li>
                    <li>
                      <span class="blue-dot">差异位点个数</span>
                      <span>{{item.splitDigit}}</span>
                    </li>
                  </ul>
                  <div class="details-footer">
                    <div @click="detailModelShow(item)">
                      <span>
                        <img src="../../assets/img/cxx.png" alt />
                      </span>
                      <span>查看比中详情</span>
                    </div>
                  </div>
                  <div class="details-posiAbs"></div>
                </div>
              </div>
            </div>
            <div class="left">
              <div class="swiper-button-next"></div>
            </div>
            <div class="right">
              <div class="swiper-button-prev"></div>
            </div>
          </div>
        </div>
        <div class="home-center-rightbox">
          <div class="rightbox-title">混合分型/有效比数量</div>
          <div class="rightbox-body">
            <div class="radius-charts-1">
              <div ref="radiusCharts" class="radiusCharts-con"></div>
              <p class="item-p">混合样本数</p>
            </div>
            <div class="radius-charts-2">
              <div ref="radiusCharts_2" class="radiusCharts-con"></div>
              <p class="item-p">有效比中数</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="home-bottom-box">
      <div class="widthDiv">
        <div class="home-type-body">
          <div ref="MyTypeCharts" class="MyTypeCharts-con"></div>
        </div>
        <div class="home-DNA-body">
          <div ref="MyDNACharts" class="MyDNACharts-con"></div>
        </div>
      </div>
    </div>
    <!-- @on-ok="sureRuku()" -->
    <Modal class="detailModel-style" v-model="detailModel" width="650">
      <p slot="header" style="text-align:center">
        <span>查看比中详情</span>
      </p>
      <div>
        <Table border stripe :columns="detailCol" :data="detailData"></Table>
      </div>
      <div class="detailModel-footer">
        <div>
          <span>合计</span>
          <span>比中基因座数：30</span>
          <span>LR：1231241123</span>
        </div>
      </div>
    </Modal>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
  </div>
</template>
<script>
import { formatDate } from "../../components/data";
import homeServers from "../../servers/homeServer";
import ModalDetail from "../../components/ModalDetail";
import caseServer from "../../servers/caseServer";
import dataServers from "../../servers/dataServers";
import echarts from "echarts/lib/echarts";
import markPoint from "echarts/lib/component/markPoint";
import Swiper from "swiper";
import "swiper/css/swiper.min.css";
export default {
  name: "homeBox",
  components: { ModalDetail },
  // components: { ModalDetail },
  data() {
    return {
      // 查看详情弹出窗
      modalDetail: {
        modalType: 1,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: ""
      },
      detailCol: [
        {
          title: "序号",
          width: "80",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "基因座",
          key: "name"
        },
        {
          title: "混合样本位点",
          key: "name"
        },
        {
          title: "拆分样本位点",
          key: "name"
        },
        {
          title: "LR",
          key: "name"
        },
        {
          title: "基因频率",
          key: "name"
        }
      ],
      detailData: [
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        },
        {
          name: 123
        }
      ],
      detailModel: false,
      detailsData: [
        {
          hh: "HY12312313=41",
          name1: "槟榔(现场物证)",
          time: "2020-04-11",
          type: "类型一",
          num1: "hy1-23-12-31-23-1-1231",
          name2: "槟榔(现场1号物证)",
          num2: "12",
          num: "13"
        },
        {
          hh: "HY12312313=41",
          name1: "槟榔(现场物证)",
          time: "2020-04-11",
          type: "类型一",
          num1: "hy1-23-12-31-23-1-1231",
          name2: "槟榔(现场1号物证)",
          num2: "12",
          num: "13"
        },
        {
          hh: "HY12312313=41",
          name1: "槟榔(现场物证)",
          time: "2020-04-11",
          type: "类型一",
          num1: "hy1-23-12-31-23-1-1231",
          name2: "槟榔(现场1号物证)",
          num2: "12",
          num: "13"
        },
        {
          hh: "HY12312313=41",
          name1: "槟榔(现场物证)",
          time: "2020-04-11",
          type: "类型一",
          num1: "hy1-23-12-31-23-1-1231",
          name2: "槟榔(现场1号物证)",
          num2: "12",
          num: "13"
        }
      ],
      mixcountNum: 0,
      splitcountNum: 0,
      newName: [],
      newCount: [],
      MixcountData: [],
      SingcountData: [],
      SplitcountData: [],
      radius_num: 0,
      radius_num_2:0,
      // 环形进度图
      option: {
        title: {
          show: true,
          text: "",
          x: "center",
          y: "center",
          textStyle: {
            fontSize: "32",
            color: "#000",
            fontWeight: "normal"
          }
        },
        tooltip: {
          trigger: "item",
          formatter: "{d}%",
          show: false
        },
        legend: {
          orient: "vertical",
          x: "left",
          show: false
        },
        series: {
          name: "",
          color: ["#1F5DEA", "#CAE4FF"],
          type: "pie",
          radius: ["65%", "50%"],
          avoidLabelOverlap: true,
          hoverAnimation: false,
          label: {
            normal: {
              show: false,
              position: "center"
            },
            emphasis: {
              show: false
            }
          },
          labelLine: {
            normal: {
              show: false
            }
          },
          data: [
            { name: "", value: "" },
            { name: "", value: "" }
            // {value:this.radius_num, name:''},
            // {value:100-this.radius_num, name:''}
          ]
        }
      },
      option_2: {
        title: {
          show: true,
          text: "",
          x: "center",
          y: "center",
          textStyle: {
            fontSize: "32",
            color: "#000",
            fontWeight: "normal"
          }
        },
        tooltip: {
          trigger: "item",
          formatter: "{d}%",
          show: false
        },
        legend: {
          orient: "vertical",
          x: "left",
          show: false
        },
        series: {
          name: "",
          color: ["#FFA800", "#FFF1D5"],
          type: "pie",
          radius: ["65%", "50%"],
          avoidLabelOverlap: true,
          hoverAnimation: false,
          label: {
            normal: {
              show: false,
              position: "center"
            },
            emphasis: {
              show: false
            }
          },
          labelLine: {
            normal: {
              show: false
            }
          },
          data: [
            { name: "", value: "" },
            { name: "", value: "" }
            // {value:this.radius_num, name:''},
            // {value:100-this.radius_num, name:''}
          ]
        }
      },
      DnaOption: {
        title: [
          {
            text: "DNA数据库样本",
            x: 10,
            y: "3%",
            textStyle: {
              color: "#000",
              fontSize: 18
            }
          },
          {
            text: "2020  月份/数量",
            right: 10,
            y: "3%",
            textStyle: {
              color: "#aaa",
              fontSize: 14
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
          itemWidth: 15,
          itemHeight: 15
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
          data: [
            "一月",
            "二月",
            "三月",
            "四月",
            "五月",
            "六月",
            "七月",
            "八月",
            "九月",
            "十月",
            "十一月",
            "十二月"
          ],
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
            symbolSize: 12, // 拐点尺寸
            symbol: "circle", // 拐点类型 实心
            color: "#006CFF", // 拐点颜色
            //[120, 132, 150, 200, 220, 250, 270,300,450,500,550,600],
            // data:this.SingcountData,
            data: [],
            itemStyle: {
              normal: {
                // 拐点上显示数值
                label: {
                  // show: true
                },
                borderColor: "#006CFF", // 拐点边框颜色
                // backgroundColor:'#006CFF',
                lineStyle: {
                  width: 2, // 设置线宽
                  type: "solid", //'dotted'虚线 'solid'实线
                  color: "#006CFF"
                }
              }
            }
          },
          {
            name: "混合样本",
            type: "line",
            // stack: "总量",
            symbolSize: 12,
            symbol: "circle",
            color: "#FFA400",
            // [30, 63, 87, 168, 365, 400, 420,430,480,510,570,670],
            data: [],
            itemStyle: {
              normal: {
                // 拐点上显示数值
                label: {
                  // show: true
                },
                borderColor: "#FFA400", // 拐点边框颜色
                lineStyle: {
                  width: 2, // 设置线宽
                  type: "solid", //'dotted'虚线 'solid'实线
                  color: "#FFA400"
                }
              }
            }
          }
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
      TypeOptin: {
        title: [
          {
            text: "比中类型",
            x: 40,
            y: "3%",
            textStyle: {
              color: "#000",
              fontSize: 18
            }
          },
          {
            text: "类型/数量",
            // left:"right",
            right: 40,
            y: "3%",
            textStyle: {
              color: "#aaa",
              fontSize: 14
            }
          }
        ],
        dataZoom: [
          {
            show: false,
            start: 0,
            end: 100
          },
          {
            type: "inside",
            start: 0,
            end: 100
          }
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
      // 表格 1 标题
      tableOneCol: [
        {
          title: "序号",
          width: "80",
          render: (h, params) => {
            return h("span", {}, params.index + 1);
          }
        },
        {
          title: "混合样本编号",
          key: "a"
          // width:"220"
        },
        {
          title: "混合样本名称",
          key: "a"
        },
        {
          title: "比中样本类型",
          key: "a"
        },
        {
          title: "比中样本编号",
          key: "a"
        },
        {
          title: "比中样本名称",
          key: "a"
        },
        {
          title: "匹配位点数",
          key: "a"
        },
        {
          title: "差异位点数",
          key: "a"
        },
        {
          title: "比中时间",
          key: "a",
          width: "100"
        },
        {
          title: "比中详情",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "md-eye orage"
                },
                domProps: {
                  title: "查看比中详情"
                },
                on: {
                  click: event => {}
                }
              })
            ]);
          }
        }
      ],
      tableOneData: [
        {
          a: "123"
        },

        {
          a: "123"
        },
        {
          a: "123"
        },
        {
          a: "123"
        },
        {
          a: "123"
        },
        {
          a: "123"
        },
        {
          a: "123"
        }
      ]
    };
  },
  created() {},
  destroyed() {
    // 清除图表自适应 避免在其他页面找不到图表容器
    window.onresize = null;
  },
  mounted() {
    this.findNewestList(); //最新比中情况
    this.Swiper();
    // this.handelHomeData(); // 案件数据
    //this.handelserial();串并数据
    //this.handelLatest(); 质控数据
    // this.handelSelectCount();
    this.DrawBar(); // 人员类型图表初始化
    this.DnaCharts(); // dna 样本数据图表初始化
    this.BarData(); // 柱形图数据
    this.DNAData(); // 折线图数据
    this.radiusCharts(); // 环形图
    // this.MixedSplitData(); // 混合分型/拆分分型
    this.radius();
    this.radiusCharts_2();
    // 图表自适应
    // window.addEventListener('resize', this.DrawLineResize)
    var that = this;
    window.onresize = () => {
      that.$echarts.init(this.$refs.MyTypeCharts).resize();
      that.$echarts.init(this.$refs.MyDNACharts).resize();
      that.$echarts.init(this.$refs.radiusCharts).resize();
      that.$echarts.init(this.$refs.radiusCharts_2).resize();
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
    TypeOptin: {
      handler(newVal, oldVal) {
        if (this.myChart) {
          if (newVal) {
            this.myChart.setOption(newVal);
          } else {
            this.myChart.setOption(oldVal);
          }
        } else {
          this.DrawBar();
        }
      },
      deep: true //对象内部属性深度监听 。
    },
    option: {
      handler(newVal, oldVal) {
        if (this.radiusChart) {
          if (newVal) {
            this.radiusChart.setOption(newVal);
          } else {
            this.radiusChart.setOption(oldVal);
          }
        } else {
          this.radiusCharts();
        }
      },
      deep: true //对象内部属性深度监听 。
    },
    option_2: {
      handler(newVal, oldVal) {
        if (this.radiusChart_2) {
          if (newVal) {
            this.radiusChart_2.setOption(newVal);
          } else {
            this.radiusChart_2.setOption(oldVal);
          }
        } else {
          this.radiusCharts_2();
        }
      },
      deep: true //对象内部属性深度监听 。
    }
  },
  methods: {
    // 最新比中详情表
    findNewestList() {
      homeServers.findNewestList().then(res => {
        if (res.code == 1) {
          this.detailsData = res.result;
        }
      });
    },
    // 最新比中详情表 查看详情按钮
    detailModelShow(item) {
      let info = {
        id: item.id,
        compareId: item.compareId
      };
      this.$refs.modal.modalShow = true;
      this.modalDetail.modalType = 0;
      this.modalDetail.modalTitle = item.sampleNo;
      this.modalDetail.modalHeader1 = "混合样本等位基因";
      this.modalDetail.modalHeader2 = "比中样本等位基因";
      this.modalDetail.modalSplitMixBtn = "混合";
      homeServers.findNewestGene(info).then(res => {
        if (res.code == 1) {
            this.modalDetail.modalInfo = res.result.stringObjectMap.resultList;
            this.modalDetail.modalData = res.result;
            this.$forceUpdate();
        }
      });
    },
    Swiper() {
      new Swiper(".swiper-container", {
        // loop: true, // 循环模式选项
        // 如果需要分页器
        // pagination: {
        //   el: '.swiper-pagination',
        // },
        slidesPerView: 3,
        spaceBetween: 20,
        observeParents: true,
        observer:true,
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev"
        }
      });
    },
    // echarts  人员类型图表
    DrawBar() {
      // 图标容器
      let myChart = this.$echarts.init(this.$refs.MyTypeCharts);
      myChart.clear();
      myChart.setOption(this.TypeOptin, true);
    },
    // DNA样本数据 图表初始化
    DnaCharts() {
      // 容器
      let DnaChart = this.$echarts.init(this.$refs.MyDNACharts);
      DnaChart.clear();
      DnaChart.setOption(this.DnaOption, true);
    },
    //环形图
    radiusCharts() {
      // 容器
      let radiusChart = this.$echarts.init(this.$refs.radiusCharts);
      radiusChart.clear();
      radiusChart.setOption(this.option, true);
    },
    radiusCharts_2() {
      // 容器
      let radiusChart_2 = this.$echarts.init(this.$refs.radiusCharts_2);
      radiusChart_2.clear();
      radiusChart_2.setOption(this.option_2, true);
    },
    // 环形图数据
    radius() {
      homeServers.radiusData().then(res => {
        if(res.code == 1){

        }else{
          
        }
        this.radius_num = Math.round((res.result.mixCount / res.result.totalMixCount) * 10000) / 100.0;
        this.radius_num_2 = Math.round((res.result.effectCount / res.result.totalEffectCount) * 10000) / 100.0;
        this.option.title.text = res.result.mixCount;
        this.option_2.title.text = res.result.effectCount;
        this.option.series.data[0].value = this.radius_num;
        this.option.series.data[1].value = 100 - this.radius_num;
        this.option_2.series.data[0].value = this.radius_num_2;
        this.option_2.series.data[1].value = 100 - this.radius_num_2;
      });
    },
    // 柱形图数据
    BarData() {
      homeServers.BarTypeData().then(res => {
        for (let item of res.result) {
          this.newName.push(item.name);
          this.newCount.push(item.count);
        }
        this.TypeOptin.xAxis.data = this.newName;
        this.TypeOptin.series[0].data = this.newCount;
        this.TypeOptin.series[1].data = this.newCount;
      });
    },
    // DNA数据库样本 折线图数据
    DNAData() {
      homeServers.LineDNAData().then(res => {
        this.DnaOption.series[1].data = res.result.mixcount;
        //  this.DnaOption.series[2].data = res.data.splitcount;
        this.DnaOption.series[0].data = res.result.singcount;
      });
    },
    // 混合样本/拆分样本数量  数据
    MixedSplitData() {
      homeServers.RadiusData().then(res => {
        this.mixcountNum = res.data.mixcount;
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
      this.all_Compare();
    },
    // 全库任务接口
    all_Compare() {
      let allInfo = {
        page: this.allCompareTotalCurrent
      };
      homeServers.allCompare(allInfo).then(res => {
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
      homeServers.homeList().then(res => {
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
      homeServers.dataLatest().then(res => {
        this.serial_top = res.data;
      });
    },
    handelLatestCount() {
      homeServers.dataLatestCount().then(res => {
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
