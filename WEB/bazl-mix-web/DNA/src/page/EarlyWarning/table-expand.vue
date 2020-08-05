<template>
  <Row class="expand-row">
    <div class="geneBox height">
      <span class="title">基因座</span>
      <span v-for="item in row.geneInfo">{{ item.name }}</span>
    </div>
    <div class="personBox height">
      <span class="title">{{ row.proportionPersonName }}</span>
      <span v-for="item in row.geneInfo">{{ item.value }}</span>
    </div>
  </Row>
</template>
<script>
export default {
  props: {
    row: Object
  },
  data() {
    return {
      geneList: []
    };
  },
  mounted() {
    this.geneList = this.row;
  }
};
</script>
<style lang="less" scoped>
.expand-row {
  width: 100%;
  height: 85px;
  overflow-x: auto;
  overflow-y: hidden;
  white-space: nowrap !important;
  background: #fff;
  .height {
    width: 100%;
    height: 35px;
    font-size: 0;
  }
  .geneBox {
    span {
      border-top: 1.5px solid #ccc;
    }
  }
  .personBox {
    span {
      border-bottom: 1.5px solid #ccc;
    }
  }
  .title {
    width: 10% !important;
  }
  span {
    display: inline-block !important;
    width: 7%;
    height: 35px;
    line-height: 35px;
    padding: 0 4px;
    text-align: center;
    border: 1px solid #ccc;
    vertical-align: top;
    font-size: 14px;
    background: #77ddff;
    color: #000;
    // border-bottom: none;
    // border-left: none;
    // float: left;
  }
}
.expand-row::-webkit-scrollbar-track-piece {
  background-color: #f8f8f8;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条的宽度*/
.expand-row::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
/*滚动条的设置*/
.expand-row::-webkit-scrollbar-thumb {
  background-color: #dddddd;
  background-clip: padding-box;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条鼠标移上去*/
.expand-row::-webkit-scrollbar-thumb:hover {
  background-color: #bbb;
}
</style>
