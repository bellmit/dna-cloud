package com.bazl.dna.database.core.init;

import com.bazl.dna.database.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Properties;

/**
 * Created by Administrator on 2017-11-29.
 */
@Component
public class SysInitBean implements ServletContextAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

/*
    @Autowired
    DictService dictService;
*/

    private ServletContext sc;

    @Override
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
        //加载系统配置项
        initSysConfig(sc);
        //初始化数据字典到application中
        //this.initDict(sc);
    }

    private void initSysConfig(ServletContext sc){
        // 把项目名称放到application中
        String ctxPath = sc.getContextPath();
        sc.setAttribute("ctxPath", ctxPath);

        Properties sysProperties = SystemUtil.getSystemConfig();
        for (Object key : sysProperties.keySet()) {
            String value = (String) sysProperties.get(key);
            sc.setAttribute((String) key, value);
        }
    }

    public String getSysConfigValue(String key){
        Object obj = sc.getAttribute(key);
        if(obj != null)
            return obj.toString();
        return null;
    }

/*

    private void initDict(ServletContext sc){
        List<String> allDictTypeList = null;
        List<DictItem> allDictItemList = null;
        try {
            allDictTypeList = dictService.selectAllDictType();
            allDictItemList = dictService.selectAll();
        } catch(Exception ex) {
            logger.error("预加载字典数据错误！", ex);
            return;
        }

        List<DictItem> dictItemList = null;
        for(String dictType : allDictTypeList){
            dictItemList = allDictItemList.stream().filter(dictItem -> dictItem.getDictType().equals(dictType)).collect(Collectors.toList());
            sc.setAttribute(dictType, dictItemList);
        }
    }
*/
/*

    public List<DictItem> getDictItemListByDictType(String dictType){
        Object obj = sc.getAttribute(dictType);
        if(obj != null){
            return (List<DictItem>) obj;
        }
        return null;
    }

    public String getDictName(String dictType, String dictKey){
        List<DictItem> dictItemList = getDictItemListByDictType(dictType);
        if(ListUtils.isNotNullAndEmptyList(dictItemList)){
            List<DictItem> list = dictItemList.stream().filter(dictItem -> dictItem.getDictKey().equals(dictKey)).collect(Collectors.toList());
            if(ListUtils.isNotNullAndEmptyList(dictItemList))
                return list.get(0).getDictName();
        }
        return null;
    }
*/

}
