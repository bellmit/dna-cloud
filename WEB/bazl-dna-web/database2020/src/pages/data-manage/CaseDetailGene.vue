<template>
  <div>
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/Case">案件数据管理</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/CaseDetail">查看案件详情</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/CaseDetailGene">查看基因分型</router-link>
          </Col>
        </Row>
        <div class="part-detail viewgene">
          <Row class="title">
            <Col span="12">
              查看基因分型
            </Col>
            <Col span="8">
              <div v-show="this.active === 0">
                <span>试剂盒:</span><span>{{dnaPanelNameSTR}}</span>
                <span style="margin-left:15px;">检出位点数:</span><span>{{locusCountSTR}}</span>
              </div>
              <div v-show="this.active === 1">
                <span>试剂盒:</span><span>{{dnaPanelNameYSTR}}</span>
                <span style="margin-left:15px;">检出位点数:</span><span>{{locusCountYSTR}}</span>
              </div>
            </Col>
            <Col span="3" class="tr view-tab">
              <span :class="{'active': index == active}"  @click="handleType(index)" v-for="(item,index) in types" :key="index">
                {{item.name}}
              </span>
            </Col>
          </Row>
          <Row :gutter="16" v-show="this.active === 0">
            <Col span="24" v-show="geneDataSTR.length === 0">
              <Row class="no-anything">
                <Col span="24" class="no-anything-img">
                  <img src="../../assets/img/earth.png" alt="">
                </Col>
                <Col span="24" class="no-anything-text">
                  暂无数据
                </Col>
              </Row>
            </Col>
            <Col span="18" v-show="geneDataSTR.length !== 0">
              <div class="bg-title">查看基因分型</div>
              <Table
                border
                :columns="geneColSTR"
                :data="geneDataSTR"
                class="bazl-table light-thead"
              ></Table>
            </Col>
            <Col span="6" v-show="geneDataSTR.length !== 0">
              <div class="bg-title">查看图谱</div>
              <div class="atlas">
                <img :src="atlasImgSTR" alt="">
              </div>
            </Col>
          </Row>
          <Row :gutter="16" v-show="this.active === 1">
            <Col span="24" v-show="geneDataYSTR.length === 0">
              <Row class="no-anything">
                <Col span="24" class="no-anything-img">
                  <img src="../../assets/img/earth.png" alt="">
                </Col>
                <Col span="24" class="no-anything-text">
                  暂无数据
                </Col>
              </Row>
            </Col>
            <Col span="18" v-show="geneDataYSTR.length !== 0">
              <div class="bg-title">查看基因分型</div>
              <Table
                border
                :columns="geneColYSTR"
                :data="geneDataYSTR"
                class="bazl-table light-thead"
              ></Table>
            </Col>
            <Col span="6" v-show="geneDataYSTR.length !== 0">
              <div class="bg-title">查看图谱</div>
              <div class="atlas">
                <img :src="atlasImgYSTR" alt="">
              </div>
            </Col>
          </Row>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">返回</button>
          </Row>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      SidebarParams: {
        open: ['2'],
        active: '2-1'
      },
      geneColSTR: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'name',
          align: 'center'
        },
        {
          title: '等位基因',
          key: 'value',
          align: 'center'
        }
      ],
      geneDataSTR: [],
      geneColYSTR: [
        {
          title: '序号',
          type: 'index',
          align: 'center',
          width: 80
        },
        {
          title: '基因座',
          key: 'name',
          align: 'center'
        },
        {
          title: '等位基因',
          key: 'value',
          align: 'center'
        }
      ],
      geneDataYSTR: [],
      atlasImgSTR: '',
      atlasImgYSTR: '',
      sampleId: '',
      types: [
        {
          name: 'STR'
        },
        {
          name: 'YSTR'
        }
      ],
      active: 1,
      dnaPanelNameSTR: '',
      dnaPanelNameYSTR: '',
      locusCountSTR: '',
      locusCountySTR: ''
    }
  },
  methods: {
    getSeeCaseDetails() {
      this.$axios.get('/caseinfo/geneInfo', {
        params: {
          sampleId: this.sampleId
        }
      }).then(res => {
        const arr = res.data
        arr.forEach((item, index) => {
          if (item.geneType === 1) {
            this.geneDataSTR = item.geneInfoDetail
            this.atlasImgSTR = item.geneImage
            this.dnaPanelNameSTR = item.dnaPanelName
            this.locusCountSTR = item.locusCount
          } else if (item.geneType === 2) {
            this.geneDataYSTR = item.geneInfoDetail
            this.atlasImgYSTR = item.geneImage
            this.dnaPanelNameYSTR = item.dnaPanelName
            this.locusCountYSTR = item.locusCount
          }
        })
        console.log(this.geneDataSTR, this.geneDataYSTR)
      })
    },
    handleType(index) {
      this.active = index
    },
    goBack() {
      this.$router.go(-1)
      // this.$router.push({
      //   name: 'SeeCaseDetails'
      // })
    }
  },
  beforeRouteLeave (to, from, next) {
    if (to.path === '/seecasedetails') {
      to.meta.keepAlive = true
    }
    next()
  },
  mounted() {
    this.sampleId = this.$route.params.sampleId
    // console.log(this.sampleId)
    this.getSeeCaseDetails()
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/viewgenotyping';
</style>
