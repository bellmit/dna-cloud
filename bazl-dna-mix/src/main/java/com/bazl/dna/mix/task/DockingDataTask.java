package com.bazl.dna.mix.task;

/**
 * Created by Administrator on 2019/10/28.
 */
//@Component
public class DockingDataTask {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private CaseInfoService caseInfoService;
//    @Autowired
//    private SampleInfoService sampleInfoService;
//    @Autowired
//    private MixedSampleGeneService mixedSampleGeneService;
//    @Autowired
//    private PersonInfoService personInfoService;
//    @Autowired
//    SingleSampleGeneService singleSampleGeneService;
//    @Autowired
//    RedisUtils redisUtils;
//    @Autowired
//    QualtyPersonnelService qualtyPersonnelService;
//
//    @Autowired
//    GeneformatUtils geneformatUtils;
//    //基因信息转换条件
//    @Value("${Gen}")
//    private int gen;
//    @Value("${locusNameUrl}")
//    private String locusNameUrl;
//
//
//    /*
//    *    同步lims获取案件信息
//    * */
//    @Scheduled(fixedDelay = 604800000)  //间隔一周
//    public void firstCaseInfoList() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("调用lims案件开始");
//
//        try {
//            int numQueue = 1;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                String result = "";
//                //获取地址
//                String url = FileUtils.getProperties("/application.yml", "caseListUrl");
//                URL realUrl = new URL(url);
//                // 打开与URL之间的链接
//                URLConnection conn = realUrl.openConnection();
//
//                // 设置通用的请求属性
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("conn", "Keep-Alive");
//                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                //发送POST请求必须设置如下两行
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                // 获取URLConnection对象对应的输出流
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(numQueue);
//                // flush输出流缓冲
//                out.flush();
//                logger.info("调用lims-------------------开始");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                logger.info("调用lims-------------------结束");
//                numQueue++;//页数计算
//                if (reader == null ){
//                    isFinish = false;
//                    break look;
//                }else {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result += line;
//                    }
//                    //解析json
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    //获取数据
//                    String data = jsonObject.getString("data");
//                    JSONObject str = JSON.parseObject(data);
//                    List<CaseInfo> caseInfoList = new ArrayList<>();
//                    if (str.containsKey("caseInfoList")) {
//                        try{
//                            JSONObject str2 = JSONObject.parseObject(str.toString());
//                            List<CaseInfo> list2 = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("caseInfoList"), CaseInfo.class);
//                            if(list2.size() >0){
//                                for(int j=0;j< list2.size();j++){
//                                    CaseInfo caseInfo = list2.get(j);
//                                    caseInfoList.add(caseInfo);
//                                }
//                            }
//                            if (ListUtils.isNotNullAndEmptyList(caseInfoList)){
//                                for (CaseInfo info : caseInfoList){
//                                    CaseInfo caseInfo = caseInfoService.selectByCaseId(info.getCaseId());
//                                    if (caseInfo == null){
//                                        CaseInfo infoList = new CaseInfo();
//                                        infoList.setId(info.getCaseId());
//                                        infoList.setCaseName(info.getCaseName());
//                                        infoList.setCaseNo(info.getCaseNo());
//                                        infoList.setCaseBrief(info.getCaseBrief());//简要案情
//                                        infoList.setCaseType(info.getCaseType());//案件类型
//                                        infoList.setCreateDatetime(new Date());
//                                        caseInfoService.insertCaseInfo(infoList);
//                                    }
//                                }
//                            }else {
//                                isFinish = false;
//                                break;
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                            logger.error("添加案件信息失败！" + e.getMessage());
//                        }
//                    }
//                }
//            }
//            logger.info("获取案件信息定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("获取案件信息定时任务失败！" + e.getMessage());
//        }
//    }
//
//    /*
//    *     获取检材信息
//    * */
//    @Scheduled(fixedDelay = 604800000)   //间隔一周
//    public void firstSampleInfo() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("调用lims案件开始");
//
//        try {
//            int numQueue = 1;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                String result = "";
//                //获取地址
//                String url = FileUtils.getProperties("/application.yml", "sampleListUrl");
//                URL realUrl = new URL(url);
//                // 打开与URL之间的链接
//                URLConnection conn = realUrl.openConnection();
//
//                // 设置通用的请求属性
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("conn", "Keep-Alive");
//                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                //发送POST请求必须设置如下两行
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                // 获取URLConnection对象对应的输出流
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(numQueue);
//                // flush输出流缓冲
//                out.flush();
//                logger.info("调用lims-------------------开始");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                logger.info("调用lims-------------------结束");
//                numQueue++;//页数计算
//                if (reader == null ) {
//                    isFinish = false;
//                    break look;
//                }else {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result += line;
//                    }
//                    //解析json
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    //获取数据
//                    String data = jsonObject.getString("data");
//                    JSONObject str = JSON.parseObject(data);
//                    List<SampleInfo> sampleInfoList = new ArrayList<>();
//                    if (str.containsKey("limsSampleInfoList")) {
//                        try{
//                            JSONObject str2 = JSONObject.parseObject(str.toString());
//                            List<SampleInfo> sampleList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("limsSampleInfoList"), SampleInfo.class);
//                            if(sampleList.size() >0){
//                                for(int j=0;j< sampleList.size();j++){
//                                    SampleInfo sampleInfo = sampleList.get(j);
//                                    sampleInfoList.add(sampleInfo);
//                                }
//                            }
//                            if (ListUtils.isNotNullAndEmptyList(sampleInfoList)){
//                                for (SampleInfo info : sampleInfoList){
//                                    SampleInfo sampleInfo = sampleInfoService.selectBySampleId(info.getSampleId());
//                                    if (sampleInfo == null){
//                                        SampleInfo infoList = new SampleInfo();
//                                        info.setId(info.getSampleId());
//                                        /*infoList.setCaseId(info.getCaseId());
//                                        infoList.setSampleNo(info.getSampleNo());
//                                        infoList.setSampleName(info.getSampleName());
//                                        infoList.setSampleType(info.getSampleType());
//                                        infoList.setSampleFlag(info.getSampleFlag());
//                                        if (null != sampleInfo.getLinkId()){
//                                            infoList.setRefPersonId(sampleInfo.getLinkId());
//                                        }
//                                        infoList.setCreatePerson(sampleInfo.getCreatePerson());*/
//                                        info.setRemark(info.getSampleRemark());
//                                        info.setInstoredFlag(Constants.INSTORED_FLAG_STATUS_0);
//                                        info.setCreateDatetime(new Date());
//                                        sampleInfoService.insertSampleInfo(info);
//                                    }
//                                }
//                            }else {
//                                isFinish = false;
//                                break;
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                            logger.error("添加检材信息失败！" + e.getMessage());
//                        }
//                    }
//                }
//            }
//            logger.info("获取检材信息定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("获取检材信息定时任务失败！" + e.getMessage());
//        }
//    }
//
//
//    /*
//    *     获取混合样本信息
//    * */
//    @Scheduled(fixedDelay = 604800000)  //间隔一周
//    public void firstMixedSampleGene() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("调用lims案件开始");
//
//        try {
//            int numQueue = 1;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                String result = "";
//                //获取地址
//                String url = FileUtils.getProperties("/application.yml", "mixedSampleGeneList");
//                URL realUrl = new URL(url);
//                // 打开与URL之间的链接
//                URLConnection conn = realUrl.openConnection();
//
//                // 设置通用的请求属性
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("conn", "Keep-Alive");
//                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                //发送POST请求必须设置如下两行
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                // 获取URLConnection对象对应的输出流
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(numQueue);
//                // flush输出流缓冲
//                out.flush();
//                logger.info("调用lims-------------------开始");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                logger.info("调用lims-------------------结束");
//                numQueue++;//页数计算
//                if (reader == null ) {
//                    isFinish = false;
//                    break look;
//                }else {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result += line;
//                    }
//                    //解析json
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    //获取数据
//                    String data = jsonObject.getString("data");
//                    JSONObject str = JSON.parseObject(data);
//                    List<MixedSampleGene> mixedSampleGeneList = new ArrayList<>();
//                    if (str.containsKey("mixedSampleGeneList")) {
//                        try{
//                            JSONObject str2 = JSONObject.parseObject(str.toString());
//                            List<MixedSampleGene> mixedSampleList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("mixedSampleGeneList"), MixedSampleGene.class);
//                            if(mixedSampleList.size() >0){
//                                for(int j=0;j< mixedSampleList.size();j++){
//                                    MixedSampleGene mixedSampleGene = mixedSampleList.get(j);
//                                    mixedSampleGeneList.add(mixedSampleGene);
//                                }
//                            }
//                            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList)){
//                                List<Map<String, String>> listLocusName = new ArrayList<>();
//                                if (gen == 1){
//                                    logger.info("查询基因座信息开始----------------------");
//                                    String locusInfo = HttpClientUtil.doGet(locusNameUrl,null);
//                                    JSONObject resultObject = JSON.parseObject(locusInfo);
//                                    String dataResultStr = resultObject.getString("data");
//                                    List<HashMap> llocus = JSON.parseArray(dataResultStr, HashMap.class);
//                                    for (Object object : llocus ) {
//                                        listLocusName.add((Map<String, String>) object);
//                                    }
//                                    logger.info("查询基因座信息结束----------------------");
//                                }
//
//                                for (MixedSampleGene info : mixedSampleGeneList){
//                                    MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectBySampleId(info.getSampleId());
//                                    if (mixedSampleGene == null){
//                                        info.setId(UUID.randomUUID().toString());
//                                        info.setCreateDatetime(new Date());
//                                        info.setReagentName(info.getPanelId());
//                                        info.setGenePicture(info.getImagePath());
//                                        info.setBoardBarcode(info.getBoardNo());
//                                        if (gen == 1) {
//                                            logger.info("基因信息转换开始----------------------");
//                                            String gene = geneformatUtils.geneFormatConversionNew(info.getGeneInfo(),listLocusName);
//                                            logger.info("基因信息转换结束----------------------");
//                                            info.setGeneInfo(gene);
//                                        }
//                                        mixedSampleGeneService.insert(info);
//                                    }
//                                }
//                            }else {
//                                isFinish = false;
//                                break;
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                            logger.error("添加检材信息失败！" + e.getMessage());
//                        }
//                    }
//                }
//            }
//            logger.info("获取检材信息定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("获取检材信息定时任务失败！" + e.getMessage());
//        }
//    }
//
//    /*
//    *     获取单一样本信息
//    * */
//    @Scheduled(fixedDelay = 604800000)  //间隔一周
//    public void firstSingleSampleGene() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("调用lims案件开始");
//
//        try {
//            int numQueue = 1;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                String result = "";
//                //获取地址
//                String url = FileUtils.getProperties("/application.yml", "singleSampleGeneList");
//                URL realUrl = new URL(url);
//                // 打开与URL之间的链接
//                URLConnection conn = realUrl.openConnection();
//
//                // 设置通用的请求属性
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("conn", "Keep-Alive");
//                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                //发送POST请求必须设置如下两行
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                // 获取URLConnection对象对应的输出流
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(numQueue);
//                // flush输出流缓冲
//                out.flush();
//                logger.info("调用lims-------------------开始");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                logger.info("调用lims-------------------结束");
//                numQueue++;//页数计算
//                if (reader == null ) {
//                    isFinish = false;
//                    break look;
//                }else {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result += line;
//                    }
//                    //解析json
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    //获取数据
//                    String data = jsonObject.getString("data");
//                    JSONObject str = JSON.parseObject(data);
//                    List<SingleSampleGene> singleSampleGeneList = new ArrayList<>();
//                    if (str.containsKey("singleSampleGeneList")) {
//                        try{
//                            JSONObject str2 = JSONObject.parseObject(str.toString());
//                            List<SingleSampleGene> singleGeneList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("singleSampleGeneList"), SingleSampleGene.class);
//                            if(singleGeneList.size() >0){
//                                for(int j=0;j< singleGeneList.size();j++){
//                                    SingleSampleGene singleSampleGene = singleGeneList.get(j);
//                                    singleSampleGeneList.add(singleSampleGene);
//                                }
//                            }
//                            if (ListUtils.isNotNullAndEmptyList(singleSampleGeneList)){
//                                for (SingleSampleGene info : singleSampleGeneList){
//                                    List< Map<String,String>> listLocusName = new ArrayList< >();
//                                    if (gen == 1){
//                                        logger.info("查询基因座信息开始----------------------");
//                                        String locusInfo = HttpClientUtil.doGet(locusNameUrl,null);
//                                        JSONObject resultObject = JSON.parseObject(locusInfo);
//                                        String dataResultStr = resultObject.getString("data");
//                                        List<HashMap> llocus = JSON.parseArray(dataResultStr, HashMap.class);
//                                        for (Object object : llocus ) {
//                                            listLocusName.add((Map<String, String>) object);
//                                        }
//                                        logger.info("查询基因座信息结束----------------------");
//                                    }
//                                    SingleSampleGene singleSample = singleSampleGeneService.selectBySampleId(info.getSampleId());
//                                    if (singleSample == null){
//                                        info.setId(UUID.randomUUID().toString());
//                                        info.setCreateDatetime(new Date());
//                                        info.setReagentName(info.getPanelId());
//                                        info.setGenePicture(info.getImagePath());
//                                        info.setBoardBarcode(info.getBoardNo());
//                                        if (gen == 1) {
//                                            logger.info("基因信息转换开始----------------------");
//                                            String gene = geneformatUtils.geneFormatConversionNew(info.getGeneInfo(),listLocusName);
//                                            logger.info("基因信息转换结束----------------------");
//                                            info.setGeneInfo(gene);
//                                        }
//                                        singleSampleGeneService.insert(info);
//                                    }
//                                }
//                            }else {
//                                isFinish = false;
//                                break;
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                            logger.error("添加检材信息失败！" + e.getMessage());
//                        }
//                    }
//                }
//            }
//            logger.info("获取检材信息定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("获取检材信息定时任务失败！" + e.getMessage());
//        }
//    }
//
//    /*
//   *     放入缓存
//   * */
//    @Scheduled(fixedDelay = 604800000)  //间隔一周
//    public void firstredis() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("全库比对导入缓存任务开始 : " + LocalDateTime.now().toLocalTime());
//        int num = 0;
//        boolean flag = true;
//        while (flag) {
//            //提交全库 0：物证信息list
//            List<SingleSampleGeneVo> singleSampleGeneList = singleSampleGeneService.selectSampleTypeSampleflag("0", num, 10000);
//            if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
//                flag = false;
//                num = 0;
//                break;//直接跳出while语句
//            }
//            num++;
//            redisUtils.set("allFastComparison_0_"+num, singleSampleGeneList);
//            redisUtils.set("0_sumPage", num);
//        }
//        List<QualtyPersonnel> qualtyPersonnels = qualtyPersonnelService.selectAllList();
//        if (ListUtils.isNotNullAndEmptyList(qualtyPersonnels)){
//            redisUtils.set("allFastComparison_3", qualtyPersonnels);
//        }
//        String [] arr = new String []{"01","03","04","02","99"};
//        for (String str : arr){
//            int sum = 0;
//            boolean flag11 = true;
//            look:
//            while (flag11) {
//                //提交全库 0：物证信息list
//                List<SingleSampleGeneVo> singleSampleGeneList = singleSampleGeneService.selectSampleTypeSampleflag(str, num, 10000);
//                if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
//                    flag11 = false;
//                    break look;//直接跳出while语句
//                }
//                num++;
//                redisUtils.set("allFastComparison_"+str+"_"+num, singleSampleGeneList);
//                redisUtils.set(str+"_sumPage", num);
//            }
//        }
//
//        logger.info("全库比对导入缓存任务结束 : " + LocalDateTime.now().toLocalTime());
//    }
//
//
//
//
//    /*
//   *     获取案件人员信息
//   * */
//
//   @Scheduled(fixedDelay = 604800000)  //间隔一周
//    public void firstPersonInfo() throws InterruptedException {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        logger.info("调用lims案件开始");
//
//        try {
//            int numQueue = 1;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                String result = "";
//                //获取地址
//                String url = FileUtils.getProperties("/application.yml", "personInfoList");
//                URL realUrl = new URL(url);
//                // 打开与URL之间的链接
//                URLConnection conn = realUrl.openConnection();
//
//                // 设置通用的请求属性
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("conn", "Keep-Alive");
//                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
//                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                //发送POST请求必须设置如下两行
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                // 获取URLConnection对象对应的输出流
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(numQueue);
//                // flush输出流缓冲
//                out.flush();
//                logger.info("调用lims-------------------开始");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                logger.info("调用lims-------------------结束");
//                numQueue++;//页数计算
//                if (reader == null ) {
//                    isFinish = false;
//                    break look;
//                }else {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result += line;
//                    }
//                    //解析json
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    //获取数据
//                    String data = jsonObject.getString("data");
//                    JSONObject str = JSON.parseObject(data);
//                    List<PersonInfo> personInfoList = new ArrayList<>();
//                    if (str.containsKey("personInfoList")) {
//                        try{
//                            JSONObject str2 = JSONObject.parseObject(str.toString());
//                            List<PersonInfo> PersonInfoList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("personInfoList"), PersonInfo.class);
//                            if(PersonInfoList.size() >0){
//                                for(int j=0;j< PersonInfoList.size();j++){
//                                    PersonInfo personInfo = PersonInfoList.get(j);
//                                    personInfoList.add(personInfo);
//                                }
//                            }
//                            if (ListUtils.isNotNullAndEmptyList(personInfoList)){
//                                for (PersonInfo info : personInfoList){
//                                    PersonInfo personInfo  = personInfoService.selectByPersonId(info.getPersonId());
//                                    if (personInfo == null){
//                                        PersonInfo personInfo1 = new PersonInfo();
//                                        personInfo1.setId(info.getPersonId());
//                                        personInfo1.setCaseId(info.getCaseId());
//                                        personInfo1.setPersonName(info.getPersonName());
//                                        personInfo1.setSex(info.getPersonGender());
//                                        personInfo1.setIdCard(info.getPersonIdCard());
//                                        personInfo1.setPersonType(info.getPersonType());
//                                        personInfo1.setCreatePerson(info.getCreatePerson());
//                                        personInfo1.setCreateDatetime(info.getCreateDatetime());
//                                        personInfo1.setUpdatePereson(info.getUpdatePereson());
//                                        personInfo1.setUpdateDatetime(info.getUpdateDatetime());
//                                        personInfoService.insert(personInfo1);
//                                    }
//                                }
//                            }else {
//                                isFinish = false;
//                                break;
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                            logger.error("添加检材信息失败！" + e.getMessage());
//                        }
//                    }
//                }
//            }
//            logger.info("获取检材信息定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("获取检材信息定时任务失败！" + e.getMessage());
//        }
//    }
}
