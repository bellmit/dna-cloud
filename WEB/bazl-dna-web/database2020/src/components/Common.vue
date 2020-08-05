<script>
function SectionToChinese(section) {
  var chnNumChar = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
  var chnUnitChar = ['', '十', '百', '千', '万', '亿', '万亿', '亿亿']
  var strIns = ''
  var chnStr = ''
  var unitPos = 0
  var zero = true
  while (section > 0) {
    var v = section % 10
    if (v === 0) {
      if (!zero) {
        zero = true
        chnStr = chnNumChar[v] + chnStr
      }
    } else {
      zero = false
      strIns = chnNumChar[v]
      strIns += chnUnitChar[unitPos]
      chnStr = strIns + chnStr
    }
    unitPos++
    section = Math.floor(section / 10)
  }
  return chnStr
}
function certificateType() {
  // 查询证件类型 6 -24
  this.$axios
    .get('/database/comm/dict/selectByCertificateType', {})
    .then(res => {
      console.log(res, '证件类型')
      sessionStorage.setItem('certificateType', JSON.stringify(res.result))
    })
}
function caseNatureQuery() {
  // 查询案件性质 5-27
  this.$axios.get('/database/comm/dict/selectByCaseType', {}).then(res => {
    // console.log(res, '案件性质')
    sessionStorage.setItem('caseNatureQuery', JSON.stringify(res.result))
  })
}
function sampleTypeQuery() {
  // 查询检材类型 5-27
  this.$axios.get('/database/comm/dict/selectBySampleType', {}).then(res => {
    sessionStorage.setItem('sampleTypeQuery', JSON.stringify(res.result))
    // console.log(res, '检材类型')
  })
}
function personTypeQuery() {
  // 查询案件人员类型
  this.$axios
    .get('/database/comm/dict/selectByCasePersonType', {})
    .then(res => {
      sessionStorage.setItem('personTypeQuery', JSON.stringify(res.result))
    })
}
// function consignOrgName() {
//   // 查询委托单位
//   this.$axios.post('/repidComparison/consignOrgName', {}).then(res => {
//     // console.log(res, '委托单位123123')
//     if (res.code === 1) {
//       sessionStorage.setItem('consignOrgName', JSON.stringify(res.result))
//     } else {
//     }
//   })
// }
function selectRegion() {
  // 查询所属辖区
  this.$axios.get('/database/comm/dict/selectRegion', {}).then(res => {
    if (res.code === 1) {
      sessionStorage.setItem('selectRegion', JSON.stringify(res.result))
    } else {
    }
  })
}
function instoreDataTypeList() {
  // 查询入库类型/database/comm/dict/selectSubordinate
  this.$axios
    .post('/database/comm/instore/instoreDataTypeList', {})
    .then(res => {
      if (res.code === 1) {
        sessionStorage.setItem(
          'instoreDataTypeList',
          JSON.stringify(res.result)
        )
      } else {
      }
    })
}
function populationQuery() {
  // 查询种群类型
  this.$axios
    .get('/database/comm/dict/selectByPopulationInfo', {
      params: { geneType: 1 }
    })
    .then(res => {
      // console.log(res, '种群下拉1231312')
      if (res.code === 1) {
        sessionStorage.setItem('populationQuery', JSON.stringify(res.result))
      } else {
      }
    })
}
function panelNameQuery() {
  // 查询试剂盒名称 panelType=1 为STR panelType=2 为YSTR
  this.$axios
    .get('/database/panel/listByType', {
      params: {
        panelType: '1'
      }
    })
    .then(res => {
      sessionStorage.setItem('panelNameSTR', JSON.stringify(res.result))
      // console.log(res, '试剂盒1111')
    })
  this.$axios
    .get('/database/panel/listByType', {
      params: {
        panelType: '2'
      }
    })
    .then(res => {
      // console.log(res, '试剂盒222')
      sessionStorage.setItem('panelNameYSTR', JSON.stringify(res.result))
    })
}
function personRaceList() {
  // 查询民族列表 5-27
  this.$axios.get('/database/comm/dict/listByRaceTypeCode', {}).then(res => {
    sessionStorage.setItem('personRaceList', JSON.stringify(res.result))
  })
}
function personNationList() {
  // 查询国籍列表
  this.$axios
    .get('/database/comm/dict/listByNationalityTypeCode', {})
    .then(res => {
      sessionStorage.setItem('personNationList', JSON.stringify(res.result))
    })
}
function labServerList() {
  // 查询实验室列表 5-27
  // this.$axios.get('/database/comm/dict/selectLabInfoList', {}).then(res => {
  this.$axios.get('/system/lab/selectLabName', {}).then(res => {
    sessionStorage.setItem('labServerList', JSON.stringify(res.result))
  })
}
// function casePropertyList() { // 查询案件性质列表
//   this.$axios.get('/comm/dict/selectByCaseType', {
//     params: {}
//   }).then(res => {
//     if (res.code === 1) {
//       // console.log(res.data)
//       sessionStorage.setItem('casePropertyList', JSON.stringify(res.result))
//     } else {

//     }
//   })
// }
function qcSampleTypeList() {
  // 查询质控样本类别 5-27更新
  this.$axios.get('/database/comm/dict/selectByQcSampleType', {}).then(res => {
    sessionStorage.setItem('qcSampleTypeList', JSON.stringify(res.result))
  })
}
function qcPersonTypeList() {
  // 查询质控人员类别 5-27更新
  this.$axios.get('/database/comm/dict/selectByQcPersonType', {}).then(res => {
    sessionStorage.setItem('qcPersonTypeList', JSON.stringify(res.result))
  })
}
function selectByCriminalPersonType() {
  // 建库人员类型
  this.$axios
    .get('/database/comm/dict/selectByCriminalPersonType', {})
    .then(res => {
      // console.log(res, '建库人员1231312')
      sessionStorage.setItem(
        'selectByCriminalPersonType',
        JSON.stringify(res.result)
      )
    })
}
function selectByPersonRelationType() {
  // 人员亲缘类别
  this.$axios
    .get('/database/comm/dict/selectByPersonRelationType', {})
    .then(res => {
      sessionStorage.setItem(
        'selectByPersonRelationType',
        JSON.stringify(res.result)
      )
    })
}
function qcSexPersonType() {
  // 查询性别
  this.$axios.get('/database/comm/dict/selectByQcPersonType', {}).then(res => {
    sessionStorage.setItem('qcSexPersonType', JSON.stringify(res.result))
  })
}
function selectLabName() {
  // 比对实验室范围 /system/lab/selectLabName
  this.$axios.get('/system/lab/selectLabName', {}).then(res => {
    // console.log('实验室范围最新数据', res)
    sessionStorage.setItem('selectLabName', JSON.stringify(res.result))
  })
}
function selectByOrgInfo() {
  // 委托单位 { params: { parentId: '1' } }
  const id = sessionStorage.getItem('parentId')
  console.log(id, '查看父级id')
  this.$axios
    .get('/database/comm/dict/selectOrgName', { params: { parentId: id } })
    .then(res => {
      sessionStorage.setItem('selectByOrgInfo', JSON.stringify(res.result))
      console.log(res, '委托单位一级-13123123')
    })
}
// function selectByOrg() {
//   // 委托单位 二级
//   this.$axios
//     .get('/database/comm/dict/selectByOrg', { params: { parentId: '2' } })
//     .then(res => {
//       sessionStorage.setItem('selectByOrg', JSON.stringify(res.result))
//       console.log(res, '委托单位二级-123123')
//     })
// }
function selectByTargetReationType() {
  // 目标亲缘类型
  this.$axios
    .get('/database/comm/dict/selectByTargetReationType', {})
    .then(res => {
      sessionStorage.setItem(
        'selectByTargetReationType',
        JSON.stringify(res.result)
      )
    })
}
export default {
  selectRegion, // 所属辖区
  certificateType, // 证件类型
  selectByOrgInfo, // 委托单位
  selectByTargetReationType, // 目标亲缘类型
  SectionToChinese,
  caseNatureQuery, // 案件性质
  sampleTypeQuery, // 检材类型
  personTypeQuery, // 案件人员类型
  instoreDataTypeList, // 入库数据数据类型
  populationQuery,
  panelNameQuery, // 试剂盒查询 STR 与 YSTR
  personRaceList, // 民族
  personNationList, // 国籍
  labServerList, // 实验室列表
  // casePropertyList,
  qcSampleTypeList, // 查询质控样本类型
  qcPersonTypeList, // 质控人员类别
  selectByCriminalPersonType, // 建库人员类型
  selectByPersonRelationType, // 人员亲缘类别
  selectLabName, // 比对实验室范围
  qcSexPersonType // 性别
  // selectByOrg // 二级单位
}
</script>
