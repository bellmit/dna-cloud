<template>
  <div>
    <Row>
      <Col span="20" class="part">
        <Row class="nav">
          <Col span="24">
            当前位置:
            <span>数据综合管理</span>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/case">案件数据管理</router-link>
            <Icon type="md-arrow-forward" />
            <router-link to="/datamanage/caseDetail">查看案件详情</router-link>
          </Col>
        </Row>
        <div class="part-detail">
          <div class="title">
            查看案件详情
          </div>
          <div class="item-title" style="border-top: none;">
            案件信息
          </div>
          <Row class="item-row tl" style="text-align: left;">
            <Col span="8">
              <span class="item-label">案件名称</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.caseName"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">案件受理编号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.caseAcceptNo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">案件性质</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.casePropertyName"
              ></Input>
              <!-- <Input
                type="text"
                readonly
                style="width: 125px; margin-left: 10px;"
                v-model="caseInfo.caseSubPropertyName"
              ></Input> -->
            </Col>
            <Col span="8">
              <span class="item-label">案发地点</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.scenePlace"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">案发时间</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.casrDatetime"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">现场勘查号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.sysXkNo"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">国家库案件号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.nationSysNo"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">案件A号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.sysCaseAno"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">简要案情</span>
              <Input
                type="textarea"
                style="display: inline-block; width: 89%;"
                v-model="caseInfo.caseSummary"
                readonly
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">现场照片</span>
              <div
                style="display: inline-block; width: 89%;border:1px solid #dcdee2;border-radius:4px;padding:4px 7px;vertical-align:middle;min-height:70px;"
              >
                <div v-if="caseImageList" style="display:flex;">
                  暂无图片
                </div>
                <div v-else>
                  <img
                    :src="item.caseImage"
                    alt=""
                    v-for="item in caseImageList"
                    :key="item.id"
                    style="display:inline-block;margin:0 6px;width:80px;"
                  />
                </div>
              </div>
            </Col>
          </Row>
          <div class="item-title">
            委托信息
          </div>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">委托单位</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignOrgName"
              ></Input>
              <!-- <Input
                type="text"
                readonly
                style="width: 125px; margin-left: 10px;"
                v-model="consignmentInfo.consignOrgName"
              ></Input> -->
            </Col>
            <Col span="8">
              <span class="item-label">委托书编号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignmentFileNo"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">鉴定要求</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.identifyRequirement"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">现勘委托编号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.sysXkWtno"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">委托单位电话</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignOrgPhone"
              ></Input>
            </Col>
            <Col class="item-col" span="8">
              <span class="item-label">委托单位地址</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignOrgAddress"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col class="item-col" span="8">
              <span class="item-label">委托时间</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignmentDatetime"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">送检人一 姓名</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson1Name"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">身份证号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson1CertificateNo"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">联系电话</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson1Phone"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">送检人二 姓名</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson2Name"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">身份证号</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson2CertificateNo"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">联系电话</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.consignPerson2Phone"
              ></Input>
            </Col>
          </Row>
          <!-- <Row class="item-row">
            <Col span="24">
              <span class="item-label">原鉴定情况</span>
              <Input
                type="textarea"
                style="display: inline-block; width: 89%;" v-model="consignmentInfo"
              ></Input>
            </Col>
          </Row>
          <Row class="item-row">
            <Col span="24">
              <span class="item-label">重新鉴定理由</span>
              <Input
                type="textarea"
                style="display: inline-block; width: 89%;" v-model="consignmentInfo"
              ></Input>
            </Col>
          </Row> -->
          <div class="item-title">
            人员信息
          </div>
          <Row class="padding-left-15" style="padding:4px 15px;">
            人员数量
            <span class="blue-font">{{ personDataLength }}</span>
            人
          </Row>
          <Table
            border
            :columns="personCol"
            :data="personData"
            class="bazl-table"
            size="small"
          ></Table>
          <div class="item-title">
            检材信息
          </div>
          <Row class="padding-left-15" style="padding:4px 15px;">
            人员检材数量
            <span class="blue-font">{{ samplePersonDataLength }}</span>
            个
          </Row>
          <Table
            border
            :columns="samplePersonCol"
            :data="samplePersonData"
            class="bazl-table"
            size="small"
          ></Table>
          <Row class="padding-left-15" style="padding:4px 15px;">
            物证检材数量
            <span class="blue-font">{{ sampleDataLength }}</span>
            个
          </Row>
          <Table
            border
            :columns="sampleCol"
            :data="sampleData"
            class="bazl-table"
            size="small"
          ></Table>
          <div class="item-title">
            相关信息
          </div>
          <Row class="item-row">
            <Col span="8">
              <span class="item-label">受理人</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.acceptPersonName"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">受理时间</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="consignmentInfo.acceptDate"
              ></Input>
            </Col>
            <Col span="8">
              <span class="item-label">受理单位</span>
              <Input
                type="text"
                readonly
                style="width: 260px;"
                v-model="caseInfo.labServerName"
              ></Input>
            </Col>
          </Row>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click.prevent="goBack">
              返回
            </button>
          </Row>
        </div>
      </Col>
    </Row>
    <Modal
      v-model="seePic"
      width="500"
      class="bazl-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看照片
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="seePic = false"
        />
      </div>
      <div
        v-show="this.previewPictures.length === 0"
        style="text-align:center;heigth:400px;line-height:400px;font-size:16px;"
      >
        暂无照片
      </div>
      <div v-show="this.previewPictures.length !== 0">
        <Carousel style="width:500px;height:400px;">
          <CarouselItem
            v-for="item in previewPictures"
            :key="item.index"
            style="width:500px;height:400px;"
          >
            <img
              :src="item[sampleImage]"
              alt=""
              style="width:inherit;height:inherit;"
            />
          </CarouselItem>
        </Carousel>
      </div>
    </Modal>
    <!-- 查看人员信息 -->
    <Modal
      v-model="seePerson"
      width="1000"
      class="bazl-modal case-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看人员信息
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="seePerson = false"
        />
      </div>
      <div class="model-content">
        <div class="model-part">
          <div class="model-title">
            人员基本信息
            <span style="position:absolute;right:20px;">
              <Icon
                type="ios-arrow-dropdown-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonActive = !isPersonActive"
                v-show="isPersonActive == false"
              />
              <Icon
                type="ios-arrow-dropup-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonActive = !isPersonActive"
                v-show="isPersonActive == true"
              />
            </span>
          </div>
          <Collapse>
            <div class="input-content" v-show="isPersonActive">
              <Form
                :model="seePersonBase"
                label-position="right"
                :label-width="100"
                class="bazl-form"
                ref="seePersonBase"
              >
                <Row class="item-row">
                  <FormItem label="人员类型" prop="personTypeName">
                    <Input
                      v-model="seePersonBase.personTypeName"
                      style="width:215px;"
                    ></Input>
                  </FormItem>
                </Row>
                <Row>
                  <Col span="16">
                    <Row :gutter="16" class="item-row">
                      <Col span="12">
                        <FormItem label="姓名">
                          <Input v-model="seePersonBase.personName"></Input>
                        </FormItem>
                      </Col>
                      <Col span="12">
                        <FormItem label="曾用名(别名)">
                          <Input v-model="seePersonBase.personAlias"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="12">
                        <FormItem label="性别">
                          <Input
                            v-model="seePersonBase.personGenderName"
                          ></Input>
                        </FormItem>
                      </Col>
                      <Col span="12">
                        <FormItem label="民族">
                          <Input v-model="seePersonBase.personRaceName"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="12">
                        <FormItem label="证件号码">
                          <Input v-model="seePersonBase.personIdcardNo"></Input>
                        </FormItem>
                      </Col>
                      <Col span="12">
                        <FormItem label="电话号码">
                          <Input v-model="seePersonBase.phoneNumber"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="12">
                        <FormItem label="籍贯">
                          <Input v-model="seePersonBase.personAddr"></Input>
                        </FormItem>
                      </Col>
                      <Col span="12">
                        <FormItem label="现在住址">
                          <Input v-model="seePersonBase.input1"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="12">
                        <FormItem label="身高">
                          <Input v-model="seePersonBase.personHeight"></Input>
                        </FormItem>
                      </Col>
                      <Col span="12">
                        <FormItem label="体重">
                          <Input v-model="seePersonBase.personWeight"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                  </Col>
                  <Col span="8">
                    <div class="modal-img-box">
                      <div v-if="personImgPath">
                        <img src="https://i.ibb.co/K0fFZb7/image.jpg" alt="" />
                      </div>
                      <div v-else>
                        <img :src="img_err" alt="" />
                      </div>
                    </div>
                  </Col>
                </Row>
              </Form>
            </div>
          </Collapse>
        </div>
        <hr class="gray-line" />
        <div class="model-part">
          <div class="model-title">
            人员检材信息
            <span style="position:absolute;right:20px;">
              <Icon
                type="ios-arrow-dropdown-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonActive1 = !isPersonActive1"
                v-show="isPersonActive1 == false"
              />
              <Icon
                type="ios-arrow-dropup-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonActive1 = !isPersonActive1"
                v-show="isPersonActive1 == true"
              />
            </span>
          </div>
          <Collapse>
            <div class="input-content" v-show="isPersonActive1">
              <Form
                :model="seePersonSample"
                label-position="right"
                :label-width="100"
                class="bazl-form"
              >
                <Row class="item-row">
                  <Col span="8">
                    <FormItem label="DNA检材编号">
                      <Input v-model="seePersonSample.sampleLabNo"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材名称">
                      <Input v-model="seePersonSample.sampleName"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材类型">
                      <Input v-model="seePersonSample.sampleTypeName"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="8">
                    <FormItem label="检材包装">
                      <Input v-model="seePersonSample.samplePackage"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材描述">
                      <Input v-model="seePersonSample.sampleDesc"></Input>
                    </FormItem>
                  </Col>
                </Row>
              </Form>
            </div>
          </Collapse>
        </div>
        <hr class="gray-line" />
        <div class="model-part">
          <div class="model-title">
            检材基因信息
          </div>
          <div class="input-content">
            <Row class="item-row">
              <Col span="8" class="item-col">
                <span class="item-label">试剂盒</span>
                <Input
                  class="item-input"
                  v-model="seePersonPanel"
                  readonly
                ></Input>
              </Col>
              <Col span="8" class="item-col">
                <span class="item-label">检出位点个数</span>
                <Input
                  class="item-input"
                  v-model="seePersonLocus"
                  readonly
                ></Input>
              </Col>
              <Col span="8" class="toggle-gene">
                <span
                  :class="{ active: this.activePersonGene === 1 }"
                  @click="handleSeePersonGene(1)"
                  >STR</span
                >
                <span
                  :class="{ active: this.activePersonGene === 2 }"
                  @click="handleSeePersonGene(2)"
                  >YSTR</span
                >
              </Col>
            </Row>
          </div>
          <Row class="tc" :gutter="16">
            <div>
              <Col span="8">
                <div class="gene-title">查看基因分型</div>
                <!-- v-show="seePersonGeneData.length != 0"  tabscllor-->
                <Table
                  border
                  :columns="seePersonGeneCol"
                  :data="seePersonGeneData"
                  class="bazl-table light-thead gene-table-hover"
                  v-show="seePersonGeneData.length != 0"
                ></Table>
              </Col>
              <Col span="16">
                <div class="gene-title">查看图谱</div>
                <!-- v-show="seePersonGeneImage != ''" -->
                <div v-if="seePersonGeneImage" class="">
                  <img :src="seePersonGeneImage" alt="" />
                </div>
                <div v-else v-show="seePersonGeneData.length !== 0" class="">
                  <img :src="img_err" alt="" />
                </div>
              </Col>
            </div>
            <Col span="24" v-show="seePersonGeneData.length == 0">
              <img
                src="../../assets/img/earth.png"
                alt=""
                style="width:240px;margin-top:20px;"
              />
              <p>暂无数据....</p>
            </Col>
          </Row>
          <Row class="btn-row">
            <button class="btn btn-blue-bg" @click="seePerson = false">
              关闭
            </button>
          </Row>
        </div>
      </div>
    </Modal>
    <!-- 人员检材信息 -->
    <Modal
      v-model="seePersonSampleModal"
      width="1000"
      class="bazl-modal case-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看人员检材信息
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="seePersonSampleModal = false"
        />
      </div>
      <div class="model-content">
        <div class="model-part">
          <div class="model-title">
            人员检材信息
            <span style="position:absolute;right:20px;">
              <Icon
                type="ios-arrow-dropdown-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonSampleActive = !isPersonSampleActive"
                v-show="isPersonSampleActive == false"
              />
              <Icon
                type="ios-arrow-dropup-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isPersonSampleActive = !isPersonSampleActive"
                v-show="isPersonSampleActive == true"
              />
            </span>
          </div>
          <collapse>
            <div class="input-content" v-show="isPersonSampleActive">
              <Form
                :model="seePersonSampleForm"
                label-position="right"
                :label-width="100"
                class="bazl-form"
              >
                <Row class="item-row">
                  <Col span="8">
                    <FormItem label="检材编号">
                      <Input v-model="seePersonSampleForm.sampleLabNo"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材名称">
                      <Input v-model="seePersonSampleForm.sampleName"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材类型">
                      <Input
                        v-model="seePersonSampleForm.sampleTypeName"
                      ></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row class="item-row">
                  <Col span="8">
                    <FormItem label="检材包装">
                      <Input
                        v-model="seePersonSampleForm.samplePackage"
                      ></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="检材描述">
                      <Input v-model="seePersonSampleForm.sampleDesc"></Input>
                    </FormItem>
                  </Col>
                </Row>
              </Form>
            </div>
          </collapse>
        </div>
        <hr class="gray-line" />
        <div class="model-part">
          <div class="model-title">
            检材基因信息
          </div>
          <div class="input-content">
            <Row class="item-row">
              <Col span="8" class="item-col">
                <span class="item-label">试剂盒</span>
                <Input
                  class="item-input"
                  v-model="seePersonSamplePanel"
                  readonly
                ></Input>
              </Col>
              <Col span="8" class="item-col">
                <span class="item-label">检出位点个数</span>
                <Input
                  class="item-input"
                  v-model="seePersonSampleLocus"
                ></Input>
              </Col>
              <Col span="8" class="toggle-gene">
                <span
                  :class="{ active: this.activePersonSampleGene === 1 }"
                  @click="handleSeePersonSampleGene(1)"
                  >STR</span
                >
                <span
                  :class="{ active: this.activePersonSampleGene === 2 }"
                  @click="handleSeePersonSampleGene(2)"
                  >YSTR</span
                >
              </Col>
            </Row>
          </div>
          <Row class="tc" :gutter="16">
            <Col span="8">
              <div class="gene-title">查看基因分型</div>
              <Table
                border
                :columns="seePersonGeneCol"
                :data="seePersonSampleGeneData"
                class="bazl-table light-thead gene-table-hover"
                v-show="seePersonSampleGeneData.length != 0"
              ></Table>
            </Col>
            <Col span="16">
              <div class="gene-title">查看图谱</div>
              <!-- <img
                :src="seePersonSampleGeneImage"
                alt=""
                v-show="seePersonSampleGeneImage != ''"
              /> -->
              <div class="" v-if="seePersonSampleGeneImage">
                <img :src="seePersonSampleGeneImage" alt="" />
              </div>
              <div
                class=""
                v-else
                v-show="seePersonSampleGeneData.length !== 0"
              >
                <img :src="img_err" alt="" />
              </div>
            </Col>
            <Col span="24" v-show="seePersonSampleGeneData.length == 0">
              <img
                src="../../assets/img/earth.png"
                alt=""
                style="width:240px;margin-top:20px;"
              />
              <p>暂无数据....</p>
            </Col>
          </Row>
          <Row class="btn-row">
            <button
              class="btn btn-blue-bg"
              @click="seePersonSampleModal = false"
            >
              关闭
            </button>
          </Row>
        </div>
      </div>
    </Modal>
    <!-- 物证检材信息开始 -->
    <Modal
      v-model="seeSampleModal"
      width="1000"
      class="bazl-modal case-modal"
      :closable="false"
      :mask-closable="false"
    >
      <div class="header">
        查看物证信息
        <Icon
          type="md-close-circle"
          class="modal-close"
          @click="seeSampleModal = false"
        />
      </div>
      <div class="model-content">
        <div class="model-part">
          <div class="model-title">
            物证检材信息
            <span style="position:absolute;right:20px;">
              <Icon
                type="ios-arrow-dropdown-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isSampleActive = !isSampleActive"
                v-show="isSampleActive == false"
              />
              <Icon
                type="ios-arrow-dropup-circle"
                style="font-size:20px;color:#00439E;cursor:pointer;"
                @click="isSampleActive = !isSampleActive"
                v-show="isSampleActive == true"
              />
            </span>
          </div>
          <collapse>
            <div class="input-content" v-show="isSampleActive">
              <Form
                :model="seeSampleBase"
                label-position="right"
                :label-width="100"
                class="bazl-form"
              >
                <Row>
                  <Col span="8">
                    <Row :gutter="16" class="item-row">
                      <Col span="24">
                        <FormItem label="物证编号">
                          <Input
                            v-model="seeSampleBase.sampleEvidenceNo"
                          ></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="24">
                        <FormItem label="检材DNA编号">
                          <Input v-model="seeSampleBase.sampleLabNo"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="24">
                        <FormItem label="检材名称">
                          <Input v-model="seeSampleBase.sampleName"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="24">
                        <FormItem label="检材类型">
                          <Input v-model="seeSampleBase.sampleTypeName"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="24">
                        <FormItem label="检材包装">
                          <Input v-model="seeSampleBase.samplePackage"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row class="item-row">
                      <Col span="24">
                        <FormItem label="检材描述">
                          <Input v-model="seeSampleBase.sampleDesc"></Input>
                        </FormItem>
                      </Col>
                    </Row>
                  </Col>
                  <Col span="8" offset="2">
                    <div class="modal-img-box mg-img">
                      <div v-if="personImgPath">
                        <img src="https://i.ibb.co/K0fFZb7/image.jpg" alt="" />
                      </div>
                      <div v-else>
                        <img :src="img_err" alt="" />
                      </div>
                    </div>
                  </Col>
                </Row>
              </Form>
            </div>
          </collapse>
        </div>
        <hr class="gray-line" />
        <div class="model-part">
          <div class="model-title">
            检材基因信息
          </div>
          <div class="input-content">
            <Row class="item-row">
              <Col span="8" class="item-col">
                <span class="item-label">试剂盒</span>
                <Input
                  class="item-input"
                  v-model="seeSamplePanel"
                  readonly
                ></Input>
              </Col>
              <Col span="8" class="item-col">
                <span class="item-label">检出位点个数</span>
                <Input
                  class="item-input"
                  v-model="seeSampleLocus"
                  readonly
                ></Input>
              </Col>
              <Col span="8" class="toggle-gene">
                <span
                  :class="{ active: this.activeSampleGene === 1 }"
                  @click="handleSeeSampleGene(1)"
                  >STR</span
                >
                <span
                  :class="{ active: this.activeSampleGene === 2 }"
                  @click="handleSeeSampleGene(2)"
                  >YSTR</span
                >
              </Col>
            </Row>
          </div>
          <Row class="tc" :gutter="16">
            <Col span="8">
              <div class="gene-title">查看基因分型</div>
              <Table
                border
                :columns="seePersonGeneCol"
                :data="seeSampleGeneData"
                class="bazl-table light-thead gene-table-hover"
                v-show="seeSampleGeneData.length != 0"
              ></Table>
            </Col>
            <Col span="16">
              <div class="gene-title">查看图谱</div>
              <!-- <img
                :src="seeSampleGeneImage"
                alt=""
                v-show="seeSampleGeneImage != ''"
              /> -->
              <div class="" v-if="seeSampleGeneImage">
                <img :src="seeSampleGeneImage" alt="" />
              </div>
              <div class="" v-else v-show="seeSampleGeneData.length !== 0">
                <img :src="img_err" alt="" />
              </div>
            </Col>
            <Col span="24" v-show="seeSampleGeneData.length == 0">
              <img
                src="../../assets/img/earth.png"
                alt=""
                style="width:240px;margin-top:20px;"
              />
              <p>暂无数据....</p>
            </Col>
          </Row>
          <Row class="btn-row">
            <button
              class="btn btn-blue-bg"
              @click.prevent="seeSampleModal = false"
            >
              关闭
            </button>
          </Row>
        </div>
      </div>
    </Modal>
  </div>
</template>
<script>
import Collapse from '../../components/collapse'
export default {
  components: { Collapse },
  data() {
    return {
      isPersonActive: true,
      isPersonActive1: true,
      isPersonSampleActive: true,
      isSampleActive: true,
      img_err: require('../../assets/img/earth.png'),
      personImgPath: null,
      caseId: '',
      consignmentId: '',
      personCol: [
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
        },
        {
          title: '人员类型',
          key: 'personTypeName',
          align: 'center'
        },
        {
          title: '姓名',
          key: 'personName',
          align: 'center'
        },
        {
          title: '性别',
          key: 'personGenderName',
          align: 'center'
        },
        {
          title: '身份证号',
          key: 'personIdcardNo',
          align: 'center'
        },
        {
          title: '民族',
          key: 'personRaceName',
          align: 'center'
        },
        {
          title: '籍贯',
          key: 'personAddr',
          align: 'center'
        },
        {
          title: '亲缘身份',
          key: 'relationTypeName',
          align: 'center'
        },
        {
          title: '详情',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'blue-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: event => {
                    console.log(params.row, '--------')
                    Object.keys(this.seePersonBase).forEach(key => {
                      this.seePersonBase[key] = ''
                    })
                    Object.keys(this.seePersonSample).forEach(key => {
                      this.seePersonSample[key] = ''
                    })
                    Object.keys(this.seePersonGene.str).forEach(key => {
                      this.seePersonGene.str[key] = ''
                    })
                    Object.keys(this.seePersonGene.ystr).forEach(key => {
                      this.seePersonGene.ystr[key] = ''
                    })
                    this.$axios
                      .get('/database/caseinfo/findPersonInfo', {
                        params: {
                          personId: params.row.id
                        }
                      })
                      .then(res => {
                        console.log(res, '查看详情----')
                        this.activePersonGene = 1
                        this.seePersonBase = res.result.casePersonInfo
                        this.seePersonSample = res.result.sampleInfo
                        this.seePersonGene.str = res.result.STR || {}
                        this.seePersonGene.ystr = res.result.YSTR || {}
                        this.seePersonPanel =
                          this.seePersonGene.str.panelName || ''
                        this.seePersonLocus =
                          this.seePersonGene.str.locusCount || '0'
                        if (this.seePersonGene.str.geneInfo) {
                          this.seePersonGeneData = JSON.parse(
                            this.seePersonGene.str.geneInfo
                          )
                        } else {
                          this.seePersonGeneData = []
                        }
                        this.seePersonGeneImage =
                          this.seePersonGene.str.geneImage || ''
                      })
                    this.seePerson = true
                  }
                }
              },
              '查看'
            )
          }
        }
      ],
      personData: [{}, {}],
      sampleCol: [
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
        },
        {
          title: '物证编号',
          key: 'sampleEvidenceNo',
          align: 'center'
        },
        {
          title: '检材类型',
          key: 'sampleTypeName',
          align: 'center'
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center'
        },
        {
          title: '检材编号',
          key: 'sampleLabNo',
          align: 'center'
        },
        {
          title: '检材包装',
          key: 'samplePackage',
          align: 'center'
        },
        {
          title: '检材描述',
          key: 'sampleDesc',
          align: 'center'
        },
        {
          title: '详情',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'blue-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: event => {
                    console.log('进入事件---查看-1-2312312', this.seeSampleBase)
                    Object.keys(this.seeSampleBase).forEach(key => {
                      this.seeSampleBase[key] = ''
                    })
                    Object.keys(this.seeSampleGene.str).forEach(key => {
                      this.seeSampleGene.str[key] = ''
                    })
                    Object.keys(this.seeSampleGene.ystr).forEach(key => {
                      this.seeSampleGene.ystr[key] = ''
                    })
                    this.seeSampleModal = true
                    this.$axios
                      .get('/database/caseinfo/findSampleInfo', {
                        params: {
                          sampleId: params.row.id
                        }
                      })
                      .then(res => {
                        this.activeSampleGene = 1
                        this.seeSampleBase = res.result.sampleInfo
                        this.seeSampleGene.str = res.result.STR || {}
                        this.seeSampleGene.ystr = res.result.YSTR || {}
                        this.seeSamplePanel =
                          this.seeSampleGene.str.panelName || ''
                        this.seeSampleLocus =
                          this.seeSampleGene.str.locusCount || '0'
                        if (this.seeSampleGene.str.geneInfo) {
                          this.seeSampleGeneData = JSON.parse(
                            this.seeSampleGene.str.geneInfo
                          )
                        } else {
                          this.seeSampleGeneData = []
                        }
                        this.seeSampleGeneImage =
                          this.seeSampleGene.str.geneImage || ''
                      })
                  }
                }
              },
              '查看'
            )
          }
        }
      ],
      sampleData: [{}, {}],
      samplePersonCol: [
        {
          title: '序号',
          type: 'index',
          width: 40,
          align: 'center'
        },
        {
          title: '检材类型',
          key: 'sampleTypeName',
          align: 'center'
        },
        {
          title: '检材名称',
          key: 'sampleName',
          align: 'center'
        },
        {
          title: '检材编号',
          key: 'sampleLabNo',
          align: 'center'
        },
        {
          title: '关联人员姓名',
          key: 'personName',
          align: 'center'
        },
        {
          title: '检材描述',
          key: 'sampleDesc',
          align: 'center'
        },
        {
          title: '详情',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h(
              'span',
              {
                class: 'blue-font',
                style: {
                  cursor: 'pointer'
                },
                on: {
                  click: event => {
                    Object.keys(this.seePersonSampleForm).forEach(key => {
                      this.seePersonSampleForm[key] = ''
                    })
                    Object.keys(this.seePersonSampleGene.str).forEach(key => {
                      this.seePersonSampleGene.str[key] = ''
                    })
                    Object.keys(this.seePersonSampleGene.ystr).forEach(key => {
                      this.seePersonSampleGene.ystr[key] = ''
                    })
                    this.seePersonSampleModal = true
                    console.log(params.row)
                    this.$axios
                      .get('/database/caseinfo/findSampleInfo', {
                        params: {
                          sampleId: params.row.id
                        }
                      })
                      .then(res => {
                        this.activePersonSampleGene = 1
                        this.seePersonSampleForm = res.result.sampleInfo
                        this.seePersonSampleGene.str = res.result.STR || {}
                        this.seePersonSampleGene.ystr = res.result.YSTR || {}
                        this.seePersonSamplePanel =
                          this.seePersonSampleGene.str.panelName || ''
                        this.seePersonSampleLocus =
                          this.seePersonSampleGene.str.locusCount || '0'
                        if (this.seePersonSampleGene.str.geneInfo) {
                          this.seePersonSampleGeneData = JSON.parse(
                            this.seePersonSampleGene.str.geneInfo
                          )
                        } else {
                          this.seePersonSampleGeneData = []
                        }
                        this.seePersonSampleGeneImage =
                          this.seePersonSampleGene.str.geneImage || ''
                      })
                  }
                }
              },
              '查看'
            )
          }
        }
        // {
        //   title: '照片',
        //   key: 'action',
        //   className: 'blue-font',
        //   align: 'center',
        //   render: (h, params) => {
        //     return h(
        //       'span',
        //       {
        //         style: {
        //           cursor: 'pointer'
        //         },
        //         on: {
        //           click: () => {
        //             this.seePic = true
        //             this.previewPictures = params.row.dnaSampleImageList
        //           }
        //         }
        //       },
        //       '查看检材照片'
        //     )
        //   }
        // },
        // {
        //   title: '基因分型',
        //   key: 'action',
        //   className: 'blue-font',
        //   align: 'center',
        //   render: (h, params) => {
        //     return h(
        //       'span',
        //       {
        //         style: {
        //           cursor: 'pointer'
        //         },
        //         on: {
        //           click: () => {
        //             console.log(params.row)
        //             this.$router.push({
        //               name: 'ViewCaseGenotyping',
        //               params: {
        //                 sampleId: params.row.id
        //               }
        //             })
        //           }
        //         }
        //       },
        //       '查看基因分型'
        //     )
        //   }
        // }
      ],
      samplePersonData: [],
      seePic: false,
      seePerson: false,
      seePersonSampleModal: false,
      seePersonBase: {
        // personTypeName: '',
        // personName: '',
        // personAlias: '',
        // personGenderName: '',
        // personRaceName: '',
        // personIdcardNo: '',
        // phoneNumber: '',
        // personAddr: '',
        // personHeight: '',
        // personWeight: ''
      },
      seePersonSample: {},
      seePersonGene: {
        str: {},
        ystr: {}
      },
      seePersonPanel: '',
      seePersonLocus: '',
      seePersonGeneCol: [
        {
          title: '序号',
          type: 'index',
          align: 'center'
        },
        {
          title: '基因座',
          align: 'center',
          key: 'name'
        },
        {
          title: '等位基因',
          align: 'center',
          key: 'value'
        }
      ],
      seePersonGeneData: [],
      seePersonSampleForm: {},
      activePersonGene: 1,
      seePersonGeneImage: '',
      seePersonSampleGene: {
        str: {},
        ystr: {}
      },
      activePersonSampleGene: 1,
      seePersonSamplePanel: '',
      seePersonSampleLocus: '',
      seePersonSampleGeneData: [],
      seePersonSampleGeneImage: '',
      seeSampleModal: false,
      seeSampleBase: {},
      seeSamplePanel: '',
      seeSampleLocus: '',
      activeSampleGene: 1,
      seeSampleGeneData: [],
      seeSampleGeneImage: '',
      seeSampleGene: {
        str: {},
        ystr: {}
      },
      caseInfo: {
        sysXkNo: '',
        sysCaseAno: '',
        caseAcceptNo: '',
        caseName: '',
        scenePlace: '',
        occurrenceDatetime: '',
        caseSummary: '',
        nationSysNo: '',
        casePropertyName: '',
        caseSubPropertyName: '',
        labServerName: ''
      },
      consignmentInfo: {
        consignmentFileNo: '',
        sysXkWtno: '',
        consignOrgName: '',
        consignOrgPhone: '',
        consignOrgAddress: '',
        consignPerson1Name: '',
        consignPerson1Phone: '',
        consignPerson1CertificateNo: '',
        consignPerson2Name: '',
        consignPerson2Phone: '',
        consignPerson2CertificateNo: '',
        consignmentRegDatetime: '',
        identifyRequirement: '',
        acceptPersonName: '',
        acceptDatetime: ''
      },
      personDataLength: '0', // 案件人员数量
      samplePersonDataLength: '0', // 物证中人员数量
      sampleDataLength: '0', // 物证中物证数量
      caseImageList: [],
      previewPictures: []
    }
  },
  created() {
    this.caseId = this.$route.query.caseId
    this.consignmentId = this.$route.query.consignmentId
    // console.log(this.caseId, this.consignmentId, '------------')
  },
  mounted() {
    this.getCaseDetails()
  },
  beforeRouteLeave(to, from, next) {
    if (to.path === '/datamanage/case') {
      to.meta.keepAlive = true
      to.meta.isBack = true
    }
    next()
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    getCaseDetails() {
      this.$axios
        .get('/database/caseinfo/caseDetails', {
          params: { caseId: this.caseId, consignmentId: this.consignmentId }
        })
        .then(res => {
          // console.log(res, '详情数据2----')
          this.caseInfo = res.result.caseInfo
          this.consignmentInfo = res.result.consignmentInfoVo
          this.personData = res.result.casePersonInfoList
          // console.log(this.personData, '人员信息-----')
          this.personDataLength = res.result.casePersonInfoList.length
          this.samplePersonData = res.result.personDnaSampleInfoList
          this.samplePersonDataLength =
            res.result.personDnaSampleInfoList.length
          this.sampleData = res.result.evidenceDnaSampleInfoList
          this.sampleDataLength = res.result.evidenceDnaSampleInfoList.length
          this.caseImageList = res.result.caseImageList
        })
    },
    handleSeePersonGene(index) {
      this.activePersonGene = index
      if (index === 1) {
        this.seePersonPanel = this.seePersonGene.str.panelName || ''
        this.seePersonLocus = this.seePersonGene.str.locusCount || '0'
        this.seePersonGeneImage = this.seePersonGene.str.geneImage || ''
        if (this.seePersonGene.str.geneInfo) {
          this.seePersonGeneData = JSON.parse(this.seePersonGene.str.geneInfo)
        } else {
          this.seePersonGeneData = []
        }
      } else if (index === 2) {
        this.seePersonPanel = this.seePersonGene.ystr.panelName || ''
        this.seePersonLocus = this.seePersonGene.ystr.locusCount || '0'
        this.seePersonGeneImage = this.seePersonGene.ystr.geneImage || ''
        if (this.seePersonGene.ystr.geneInfo) {
          this.seePersonGeneData = JSON.parse(this.seePersonGene.ystr.geneInfo)
        } else {
          this.seePersonGeneData = []
        }
      }
    },
    handleSeePersonSampleGene(index) {
      // console.log(index)
      this.activePersonSampleGene = index
      if (index === 1) {
        this.seePersonSamplePanel = this.seePersonSampleGene.str.panelName || ''
        this.seePersonSampleLocus =
          this.seePersonSampleGene.str.locusCount || '0'
        if (this.seePersonSampleGene.str.geneInfo) {
          this.seePersonSampleGeneData = JSON.parse(
            this.seePersonSampleGene.str.geneInfo
          )
        } else {
          this.seePersonSampleGeneData = []
        }
        this.seePersonSampleGeneImage =
          this.seePersonSampleGene.str.geneImage || ''
      } else if (index === 2) {
        this.seePersonSamplePanel =
          this.seePersonSampleGene.ystr.panelName || ''
        this.seePersonSampleLocus =
          this.seePersonSampleGene.ystr.locusCount || '0'
        if (this.seePersonSampleGene.ystr.geneInfo) {
          this.seePersonSampleGeneData = JSON.parse(
            this.seePersonSampleGene.ystr.geneInfo
          )
        } else {
          this.seePersonSampleGeneData = []
        }
        this.seePersonSampleGeneImage =
          this.seePersonSampleGene.ystr.geneImage || ''
      }
    },
    handleSeeSampleGene(index) {
      this.activeSampleGene = index
      if (index === 1) {
        this.seeSamplePanel = this.seeSampleGene.str.panelName || ''
        this.seeSampleLocus = this.seeSampleGene.str.locusCount || '0'
        if (this.seeSampleGene.str.geneInfo) {
          this.seeSampleGeneData = JSON.parse(this.seeSampleGene.str.geneInfo)
        } else {
          this.seeSampleGeneData = []
        }
        this.seeSampleGeneImage = this.seeSampleGene.str.geneImage || ''
      } else if (index === 2) {
        this.seeSamplePanel = this.seeSampleGene.ystr.panelName || ''
        this.seeSampleLocus = this.seeSampleGene.ystr.locusCount || '0'
        if (this.seeSampleGene.ystr.geneInfo) {
          this.seeSampleGeneData = JSON.parse(this.seeSampleGene.ystr.geneInfo)
        } else {
          this.seeSampleGeneData = []
        }
        this.seeSampleGeneImage = this.seeSampleGene.ystr.geneImage || ''
      }
    }
  }
}
</script>
<style lang="less" scoped>
@import '../../assets/styles/seecasedetails';
</style>
