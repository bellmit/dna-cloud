export const tableCol = [
  {
    title: '序号',
    type: 'index',
    align: 'center',
    width: 80
  },
  {
    title: '案件性质',
    key: 'casePropertyName',
    align: 'center'
  },
  {
    title: '案件受理号',
    key: 'caseAcceptNo',
    align: 'center'
  },
  {
    title: '案件名称',
    key: 'caseName',
    align: 'center'
  },
  {
    title: '受理时间',
    key: 'acceptDatetime',
    align: 'center'
  },
  {
    title: '受理检材总数',
    key: 'acceptSampleCount',
    align: 'center'
  },
  {
    title: '提交入库检材数',
    key: 'sumbitSampleCount',
    align: 'center'
  },
  {
    title: '提交入库时间',
    key: 'sumbitDatetime',
    align: 'center'
  },
  {
    title: '操作',
    slot: 'action',
    align: 'center'
    // key: 'action',
    // render: (h, params) => {
    //   return h(
    //     'span',
    //     {
    //       class: 'blue-font',
    //       style: {
    //         cursor: 'pointer'
    //       },
    //       on: {
    //         click: () => {
    //           this.$router.push({
    //             path: '/monitoring/case/wait'
    //           })
    //         }
    //       }
    //     },
    //     '入库操作'
    //   )
    // }
  }
]

export const tableFailCol = [
  {
    title: '序号',
    type: 'index',
    align: 'center',
    width: 80
  },
  {
    title: '案件性质',
    key: 'casePropertyName',
    align: 'center'
  },
  {
    title: '案件受理号',
    key: 'caseAcceptNo',
    align: 'center'
  },
  {
    title: '案件名称',
    key: 'caseName',
    align: 'center'
  },
  {
    title: '受理时间',
    key: 'acceptDatetime',
    align: 'center'
  },
  {
    title: '受理检材总数',
    key: 'acceptSampleCount',
    align: 'center'
  },
  {
    title: '提交入库时间',
    key: 'sumbitDatetime',
    align: 'center'
  },
  {
    title: '提交入库数',
    key: 'sumbitSampleCount',
    align: 'center'
  },
  {
    title: '入库成功数',
    key: 'successTransferSampleCount',
    align: 'center'
  },
  {
    title: '入库失败数',
    key: 'failTransferSampleCount',
    align: 'center'
  },
  {
    title: '操作',
    slot: 'action',
    align: 'center'
    //   render: (h, params) => {
    //     return h(
    //       'span',
    //       {
    //         class: 'blue-font',
    //         style: {
    //           cursor: 'pointer'
    //         },
    //         on: {
    //           click: () => {
    //             this.$router.push({
    //               path: '/monitoring/case/fail'
    //             })
    //           }
    //         }
    //       },
    //       '查看详情'
    //     )
    //   }
  }
]

export const tableSuccessCol = [
  {
    title: '序号',
    type: 'index',
    align: 'center',
    width: 80
  },
  {
    title: '案件性质',
    key: 'casePropertyName',
    align: 'center'
  },
  {
    title: '案件受理号',
    key: 'caseAcceptNo',
    align: 'center'
  },
  {
    title: '案件名称',
    key: 'caseName',
    align: 'center'
  },
  {
    title: '受理时间',
    key: 'acceptDatetime',
    align: 'center'
  },
  {
    title: '受理检材总数',
    key: 'acceptSampleCount',
    align: 'center'
  },
  {
    title: '提交入库时间',
    key: 'sumbitDatetime',
    align: 'center'
  },
  {
    title: '提交入库检材数',
    key: 'sumbitSampleCount',
    align: 'center'
  },
  {
    title: '入库成功数',
    key: 'successTransferSampleCount',
    align: 'center'
  },
  {
    title: '操作',
    slot: 'action',
    align: 'center',
    width: 180
    //   render: (h, params) => {
    //     return h('div', [
    //       h(
    //         'span',
    //         {
    //           class: 'blue-font',
    //           style: {
    //             cursor: 'pointer',
    //             margin: '0 8px'
    //           },
    //           on: {
    //             click: () => {
    //               this.$router.push({
    //                 path: '/monitoring/case/success'
    //               })
    //             }
    //           }
    //         },
    //         '查看详情'
    //       ),
    //       h(
    //         'span',
    //         {
    //           class: 'blue-font',
    //           style: {
    //             cursor: 'pointer',
    //             margin: '0 8px'
    //           }
    //         },
    //         '打印入库单'
    //       )
    //     ])
    //   }
  }
]
