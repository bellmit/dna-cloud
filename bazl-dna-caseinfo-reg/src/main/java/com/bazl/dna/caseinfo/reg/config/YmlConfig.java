package com.bazl.dna.caseinfo.reg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huawei
 * @date 2020/6/28.
 * 加载yaml配置文件的方法
 * Created by sun on 2017-1-15.
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource注解只可以加载proprties文件,无法加载yaml文件
 * 故现在把数据放到application.yml文件中,spring-boot启动时会加载
 */
@Component
@ConfigurationProperties(prefix = "my-yml")
public class YmlConfig {

    int stringFillCount;
    private String[] arrayProps;
    /** 接收prop1里面的属性值 */
    private List<Map<String, String>> listProp1 = new ArrayList<>();
    /** 接收prop2里面的属性值 */
    private List<String> listProp2 = new ArrayList<>();
    /** 接收prop1里面的属性值 */
    private Map<String, String> mapProps = new HashMap<>();
    /** 接收fastdfs里面的属性值 */
    private Map<String, String> fastdfsProps = new HashMap<>();

    public int getStringFillCount() {
        return stringFillCount;
    }

    public void setStringFillCount(int stringFillCount) {
        this.stringFillCount = stringFillCount;
    }

    public String[] getArrayProps() {
        return arrayProps;
    }

    public void setArrayProps(String[] arrayProps) {
        this.arrayProps = arrayProps;
    }

    public List<Map<String, String>> getListProp1() {
        return listProp1;
    }

    public void setListProp1(List<Map<String, String>> listProp1) {
        this.listProp1 = listProp1;
    }

    public List<String> getListProp2() {
        return listProp2;
    }

    public void setListProp2(List<String> listProp2) {
        this.listProp2 = listProp2;
    }

    public Map<String, String> getMapProps() {
        return mapProps;
    }

    public void setMapProps(Map<String, String> mapProps) {
        this.mapProps = mapProps;
    }

    public Map<String, String> getFastdfsProps() {
        return fastdfsProps;
    }

    public void setFastdfsProps(Map<String, String> fastdfsProps) {
        this.fastdfsProps = fastdfsProps;
    }
}
