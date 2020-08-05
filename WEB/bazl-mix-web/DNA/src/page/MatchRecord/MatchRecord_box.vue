<template>
  <div class="Warning-box">
    <div class="home_title">
      <router-link tag="b" to="/">首页 》</router-link>
      <span>比对记录</span>
    </div>
    <div class="header-title">
      <div class="search">
        <span class="margin-left"></span>
        <span>查询条件</span>
      </div>
    </div>
    <div class="search">
      <ul class="Match_data_search">
        <li class="ivue-input">
          <div><p></p></div>
          <span class="span-style-date">检材编号</span>
          <Input
            v-model="input_value_yb"
            type="text"
            placeholder="请输入检材编号"
          />
        </li>
        <li class="ivue-input">
          <div><p></p></div>
          <span class="span-style-date">提交时间</span>
          <Date-picker
            @on-change="DateChange"
            format="yyyy-MM-dd"
            type="daterange"
            placement="bottom-end"
            placeholder="请选择时间范围"
          ></Date-picker>
        </li>
        <li>
          <Button class="data_search_button" @click="handelSearch">查询</Button>
        </li>
      </ul>
    </div>
    <div class="header-title" style="border:1px solid #ccc">
      <div>
        <span class="margin-left"></span>
        <span>比对结果</span>
      </div>
    </div>
    <div class="schedule-table-body">
      <div
        v-if="
          WarningData != null && WarningData != [] && WarningData.length != 0
        "
      >
        <div>
          <Table
            border
            stripe
            class="schedule-tbale-style light-blue-table warningTab"
            :columns="WarningCol"
            :data="WarningData"
          ></Table>
        </div>
        <div class="page">
          <span>
            当前第{{ currentPage }}页共{{ allPageCount }}页共{{
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
            @on-change="WarningChange"
            :page-size="15"
          />
        </div>
      </div>
      <div class="error-con" v-else>
        <div>
          <Table border class="display-none" :columns="WarningCol"></Table>
          <div class="borderStyle">
            <div>
              <p><img src="../../assets/img/break_err.png" alt="" /></p>
              <p>您好，目前暂无数据显示</p>
              <p>您可尝试其他操作</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Modal v-model="onOkModel" width="400" class="ruku" :closable="false">
      <div class="ruku-con">
        <h2>是否进行入库比对？</h2>
      </div>
      <div class="footer">
        <button class="" @click="InboundComparison">是</button>
        <button class="" @click="onOkModel = false">否</button>
      </div>
    </Modal>
    <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
  </div>
</template>

<script>
import MatchRecordServer from "@/servers/MatchRecordServer";
import ModalDetail from "../../components/ModalDetail";
export default {
  name: "MatchRecord",
  components: { ModalDetail },
  data() {
    return {
      onOkModel: false,
      // 查看详情弹出窗
      modalDetail: {
        modalType: 1,
        modalInfo: [],
        modalTitle: [],
        modalData: [],
        modalTypeHeader: ""
      },
      // 样本编号
      input_value_yb: null,
      // 总条数
      allRecordCount: 0,
      // 总页数
      allPageCount: 1,
      // 页码
      currentPage: 1,
      WarningCol: [
        {
          title: "序号",
          type: "index",
          width: "80"
        },
        {
          title: "检材编号",
          key: "sampleNo"
          // width: "220"
        },
        {
          title: "提交时间",
          key: "datetime",
          width: "120"
        },
        {
          title: "比对进度",
          key: "targetCount",
          // width: "80"
          render: (h, params) => {
            var jd;
            var num_width;
            if (params.row.targetCount == "100%") {
              jd = "div-jd-success";
              num_width = params.row.targetCount;
            } else if (
              params.row.targetCount != "100%" &&
              params.row.targetCount != null
            ) {
              jd = "div-jd-error";
              num_width = params.row.targetCount;
            } else {
              jd = "div-jd-error";
              num_width = "0%";
            }
            return h("div", [
              h("div", {class: 'jd_div'},
              [
                h("div", {
                  class: jd,
                  style: {
                    width: num_width
                    // cursor: "pointer",
                  }
                })
              ]),
              h(
                "div",
                {
                  class: "js-num",
                  style: {}
                },
                num_width
              )
            ]);
          }
        },
        {
          title: "比中地区",
          key: "districtName",
          width: "120",
          render: (h, params) => {
            return h('p', [
              h(
                'Tooltip',
                {
                  props: { placement: 'right-end',transfer: true }
                },
                [
                  h(
                    'p',
                    {
                      style: {
                        display: 'inline-block',
                        width: params.column._width * 0.6 + 'px',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap'
                      }
                    },
                    params.row.districtName
                  ),
                  h(
                    'p',
                    {
                      slot: 'content',
                      style: { whiteSpace: 'normal', wordBreak: 'break-all' }
                    },
                    params.row.districtName
                  )
                ]
              )
            ])
          }
   
          // render: (h, params) => {
          //   return h("div", [
          //     h(
          //       "p",
          //       {
          //         class: "edit_table",
          //         domProps: {
          //           title: params.row.districtName
          //         }
          //       },
          //       params.row.districtName
          //     )
          //   ]);
          // }
        },
        {
          title: "比中数量",
          key: "sameCount",
          width: "120"
        },
        {
          title: "已拆分个数",
          key: "splitCount",
          width: "120"
        },
        {
          title: "比对状态",
          width: "120",
          render: (h, params) => {
            let statusFlag = "";
            let statusFlagCode = params.row.status;
            switch (statusFlagCode) {
              case "1":
                statusFlag = "比对完成";
                break;
              case "2":
                statusFlag = "未开始";
                break;
              case "4":
                statusFlag = "比对中";
                break;
              default:
                statusFlag = "";
            }
            return h("span", {}, statusFlag);
          }
        },
        {
          title: "操作",
          // width: 250,
          render: (h, params) => {
            if (params.row.sameCount != 0) {
              return h("div", [
                h("span", [
                  h("img", {
                    attrs: {
                      src: require("../../assets/img/zdr.png")
                    },
                    style: {
                      cursor: "pointer"
                    },
                    on: {
                      click: event => {
                        this.QueryLocusListInfoForId(params);
                      }
                    }
                  })
                ]),
                h(
                  "span",
                  {
                    props: {},
                    style: {
                      cursor: "pointer"
                    },
                    on: {
                      click: event => {
                        this.QueryLocusListInfoForId(params);
                      }
                    }
                  },
                  "重新比对"
                ),
                h("Icon", {
                  props: {
                    type: "md-eye orage"
                  },
                  domProps: {
                    title: "查看比中详情"
                  },
                  style: {
                    marginLeft: "20px"
                  },
                  on: {
                    click: event => {
                      this.$router.push({
                        path: "/EarlyWarning",
                        name: "EarlyWarning",
                        params: {
                          id: params.row.id,
                          queueType: params.row.queueType
                        }
                      });
                    }
                  }
                }),
                h(
                  "span",
                  {
                    props: {
                      class: "span-style-ck"
                    },
                    style: {
                      cursor: "pointer",
                      marginLeft: "5px"
                      // fontSize:"14px"
                    },
                    on: {
                      click: event => {
                        this.$router.push({
                          path: "/EarlyWarning",
                          name: "EarlyWarning",
                          params: {
                            id: params.row.id,
                            queueType: params.row.queueType
                          }
                        });
                      }
                    }
                  },
                  "查看比中详情"
                )
              ]);
            } else {
              return h("div", [
                h("span", [
                  h("img", {
                    attrs: {
                      src: require("../../assets/img/zdr.png")
                    },
                    style: {
                      cursor: "pointer"
                    },
                    on: {
                      click: event => {
                        this.QueryLocusListInfoForId(params);
                      }
                    }
                  })
                ]),
                h(
                  "span",
                  {
                    props: {},
                    style: {
                      cursor: "pointer"
                    },
                    on: {
                      click: event => {
                        this.QueryLocusListInfoForId(params);
                      }
                    }
                  },
                  "重新比对"
                ),
                h("img", {
                  attrs: {
                    src: require("../../assets/img/cx.png")
                  },
                  style: {
                    width: "22px",
                    marginLeft: "20px",
                    marginRight: "3px",
                    cursor: "pointer"
                  },
                  on: {
                    click: event => {
                      this._ids = params.row.id;
                      this.onOkModel = true;
                    }
                  }
                }),
                h(
                  "span",
                  {
                    style: {
                      cursor: "pointer"
                    },
                    on: {
                      click: event => {
                        this._ids = params.row.id;
                        this.onOkModel = true;
                      }
                    }
                  },
                  "入库比对"
                )
              ]);
            }
          }
        }
      ],
      Date: [],
      WarningData: [
      ],
      // WarningData:[],
      starDate: "",
      endDate: "",
      _ids: "",
      timeId: ""
    };
  },
  mounted() {
    // this.WarningChange_params();
    this.MatchRecord();
  },

  beforeDestroy() {},

  created() {},
  mounted() {
    clearInterval(this.timeId);
    this.setIntervalOp();
    this.MatchRecord();
  },
  beforeDestroy() {
    // 清除定时
    clearInterval(this.timeId);
  },
  methods: {
    // 入库比对
    InboundComparison() {
      let ID_info = {
        id: this._ids
      };
      MatchRecordServer.InboundComparison(ID_info).then(res => {
        if (res.code == 1) {
          this.onOkModel = false;
          this.$Message.success("成功入库比对！");
        } else {
          this.onOkModel = false;
          this.$Message.error("操作失败！");
        }
      });
    },
    // 重新比对
    QueryLocusListInfoForId(params) {
      let queryId = {
        id: params.row.mixedSampleId
      };
      var bigList = [];
      var obj = {};
      MatchRecordServer.QueryLocusListInfoForId(queryId).then(res => {
        if (res.code == 1) {
          if (params.row.source == "1") {
            for (let item of res.result.blendList.geneInfos.resultList) {
              if (item.allele.length > 4) {
                obj.contributorCount = "3";
              } else {
                obj.contributorCount = "2";
              }
            }
            obj.sampleNo = res.result.blendList.sampleNo;
            obj.mixedSampleId = res.result.blendList.id;
            obj.geneInfoList = res.result.blendList.geneInfos.resultList;
            obj.genePicture = res.result.blendList.genePicture;
            obj.tableData = res.result.splitList;
            obj.contributorData = res.result.contriList;
            bigList.push(obj);
            this.$router.push({
              path: "/quickMixedSplit",
              name: "QuickMixedSplit",
              params: {
                geneInfoData: bigList
              }
            });
          } else if (params.row.source == "2") {
            this.$router.push({
              path: "/quickMatch",
              name: "quickMatch"
            });
          }
        } else {
          this.$Message.error("操作失败！");
        }
      });
    },
    // 定时调用比对记录接口
    setIntervalOp() {
      this.timeId = setInterval(() => {
        this.MatchRecord();
      }, 30000);
    },
    // 比对记录 表格参数
    MatchRecord() {
      var inputValue;
      if (this.input_value_yb == null) {
        inputValue = null;
      } else if (
        this.input_value_yb != "" ||
        this.input_value_yb != undefined ||
        this.input_value_yb != null
      ) {
        inputValue = $.trim(this.input_value_yb);
      }
      let pageInfo = {
        sampleNo: inputValue,
        startDatetime: this.starDate,
        endDatetime: this.endDate,
        page: this.currentPage
      };
      let info = JSON.stringify(pageInfo);
      MatchRecordServer.Matchrecord(info).then(res => {
        if (res.code == 1) {
          this.WarningData = res.result.infoList;
          this.allRecordCount = res.result.pageInfo.allRecordCount;
          this.allPageCount = res.result.pageInfo.pageCount;
        } else {
          this.$Message.error("列表获取失败！");
        }
      });
    },
    // 分页监听事件
    WarningChange(val) {
      this.currentPage = val;
      this.MatchRecord();
    },
    // 查询
    handelSearch() {
      this.currentPage = 1;
      this.MatchRecord();
    },
    //  选择时间监听事件
    DateChange(val) {
      this.starDate = val[0];
      this.endDate = val[1];
    }
  }
};
</script>
<style lang="less">
@import "../../assets/styles/title";
@import "../../assets/styles/MatchRecord";
@import "../../assets/styles/scrollbar";
.schedule-tbale-style {
  .edit_table {
    width: 65%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
    vertical-align: middle;
  }
}
</style>
