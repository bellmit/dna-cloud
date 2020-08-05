
package com.bazl.dna.lims.core.webservice.client.libMatch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="out" type="{http://submitMatchDefaultSetting.webservice.web.dna.todaysoft.com}ArrayOfWS_LibMatchGroupResult"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "out"
})
@XmlRootElement(name = "getLibMathcResultGroupResponse")
public class GetLibMathcResultGroupResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfWSLibMatchGroupResult out;

    /**
     * 获取out属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWSLibMatchGroupResult }
     *     
     */
    public ArrayOfWSLibMatchGroupResult getOut() {
        return out;
    }

    /**
     * 设置out属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWSLibMatchGroupResult }
     *     
     */
    public void setOut(ArrayOfWSLibMatchGroupResult value) {
        this.out = value;
    }

}
