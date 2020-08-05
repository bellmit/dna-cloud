<template>
    <div>
        <div class="home_title"><router-link tag="b" to="/">首页</router-link> 》 <span>{{this.moreList.moreName}}</span></div>
        <div class="data_box">
            <div class="data_title">
                <p></p><span>查询条件</span>
            </div>
            <ul class="data_search">
                <li>
                    <div><p></p></div><span>案件编号</span>
                    <Input class="data_search_input" type="text" placeholder="请输入案件编号" v-model="data_search.caseNo"/>
                </li>
                <li><div><p style="background:rgba(176,214,252,1);"></p></div>
                    <span>案件名称</span>
                    <Input class="data_search_input" type="text" placeholder="请输入案件名称" v-model="data_search.caseName"/>
                </li>
                <li>
                    <div><p style="background: #FF5A55FF"></p></div>
                    <span>检材编号</span>
                    <Input class="data_search_input" type="text" placeholder="请输入检材编号" v-model="data_search.sampleNo"/>
                </li>
                <li>
                    <div><p></p></div>
                    <span>检材名称</span>
                    <Input class="data_search_input" type="text" placeholder="请输入检材名称" v-model="data_search.sampleName"/>
                </li>
                <li>
                    <Button class="data_search_button" @click="handelSearch">查询</Button>
                </li>
            </ul>
        </div>

        <div class="data_bac">
            <div class="data_table">
                <div class="data_table_left"><p></p><span>{{this.moreList.moreName}}</span></div>
            </div>
            <div class="data_table_bottom" style="margin-bottom:50px;">
                <Table
                        border
                        class="data_table_list"
                        :columns="table_colums"
                        :data="data_list"
                >
                </Table>
                    <!-- <Page
                        :total="this.data_list.allRecordCount"
                        show-elevator
                        :class-name="'morePage'"
                        show-total
                        :page-size="this.data_list.evePageRecordCnt"
                        @on-change="handelPageChange"
                    > -->

<!--                        allRecordCount-->
                    <!-- </Page> -->
                    <div class="page">
                        <span>
                            当前第{{currentPage}}页/共{{this.pageInfo.pageCount}}页/共{{this.pageInfo.allRecordCount}}条信息
                        </span>
                        <Page :total="this.pageInfo.allRecordCount" show-elevator prev-text="上一页" next-text="下一页" class-name="bazl_page" :current="currentPage" :page-size='15' @on-change="handelPageChange"/>
                    </div>
            </div>
        </div>
        <ModalDetail :modalDetail="modalDetail" ref="modal"></ModalDetail>
    </div>
</template>
<script>
    import dataServers from '../../servers/dataServers'
    import ModalDetail from "../../components/ModalDetail";
    import moreServers from '../../servers/moreServers'
    import homeServers from '../../servers/homeServer'

    export default {
        name:"dataBase_box",
        components: {ModalDetail},
        data(){
            return{
                modalDetail:{
                    modalInfo:[],
                    modalData:[],
                    modalType:0,
                    modalTitle:"",
                    modalHeader1:'',
                    modalHeader2:'',
                    modalSplitMixBtn:'',
                    modalBigTitle:''
                },
                table_colums:[
                    {
                        title:"序号",
                        render:(h,params)=>{
                            return h('span',{

                            },params.index+1)
                        }
                    },
                    {
                        title:"案件编号",
                        key:"caseNo"
                    },
                    {
                        title:"案件名称",
                        key:"caseName"
                    },
                    {
                        title:"检材编号",
                        key:"sampleNo"
                    },
                    {
                        title:"检材名称",
                        key:"sampleName"
                    },
                    {
                        title:"比中样本编号",
                        key:"matchedSampleNo"
                    },
                    {
                        title:"比中人员姓名",
                        key:"personName"
                    },
                    {
                        title:"比中位点",
                        key:"ratio"
                    },
                    {
                        title:"比中详情",
                        align:"center",
                        render:(h,params)=>{
                            return h('div',[
                                h('Icon', {
                                    props: {
                                        type: "md-eye orage"
                                    },
                                    on:{
                                        click:(event)=>{
                                            this.$refs.modal.modalShow = true;
                                            this.modalDetail.modalTitle = params.row.sampleNo;
                                            this.modalDetail.modalHeader1 = '混合样本等位基因'
                                            this.modalDetail.modalHeader2 = '比中样本等位基因'
                                            this.modalDetail.modalSplitMixBtn = '混合'
                                            this.modalDetail.modalBigTitle = '查看比中详情'
                                            let detailInfo ={
                                                sampleGeneId:params.row.sampleGeneId,
                                                ratiogeneId:params.row.ratiogeneId
                                            };
                                            homeServers.dataDetail(detailInfo).then(res=>{
                                                this.modalDetail.modalInfo = res.data.viewRatioGeneDetails.resultList;
                                                this.modalDetail.modalData = res.data.viewRatioGeneDetails
                                            })
                                        }
                                    }
                                }),
                            ])
                        }
                    },
                ],
                data_list:[],
                pageInfo:{},
                morePage:1,
                data_search:{
                    caseNo:"",
                    caseName:"",
                    sampleNo:"",
                    sampleName:""
                },
                moreList:[],
                currentPage:1,
                pageCount:1
            }
        },
        created(){
            this.moreList = JSON.parse(localStorage.getItem('moreData'));
            this.handelSearch();
        },
        mounted() {

        },
        methods:{
            handelPageChange(page){
                this.morePage=page;
                this.currentPage=page;
                this.handelSearch()
            },
            handelMoreList(){
                let dataInfo = {
                    curPage:this.morePage
                };
                moreServers.dataMoreList(this.moreList.moreUrl,dataInfo).then(res=>{
                    this.data_list = res.data.caseInfoList;
                    this.pageInfo = res.data.pageInfo
                })
            },
            // handelDataList(){
            //     dataServers.dataList().then(res=>{
            //         this.data_list = res.data.caseMixedSampleGeneVoList;
            //     })
            // },
            handelSearch(){
                let searchInfo ={
                    curPage     :this.morePage,
                    caseNo      :this.data_search.caseNo,
                    caseName    :this.data_search.caseName,
                    sampleNo    :this.data_search.sampleNo,
                    sampleName  :this.data_search.sampleName,
                };    
                moreServers.dataMoreList(this.moreList.moreUrl,searchInfo).then(res=>{
                    this.data_list = res.data.caseInfoList;
                    this.pageInfo = res.data.pageInfo
                })
                // moreServers.dataMoreList()
                // dataServers.dataSearch(searchInfo)
                //     .then(res=>{
                //         this.data_list = res.data.caseMixedSampleGeneVoList;
                //     })
            }
        }
    }
</script>
<style lang="less">
    @import "../../assets/styles/data";
</style>