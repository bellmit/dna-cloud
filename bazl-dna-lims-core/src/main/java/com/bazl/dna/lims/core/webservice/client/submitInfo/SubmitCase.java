
package com.bazl.dna.lims.core.webservice.client.submitInfo;

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
 *         &lt;element name="in0" type="{http://submitMatchDefaultSetting.webservice.web.dna.todaysoft.com}WS_Case"/&gt;
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
    "in0"
})
@XmlRootElement(name = "submitCase")
public class SubmitCase {

    @XmlElement(required = true, nillable = true)
    protected WSCase in0;

    /**
     * 获取in0属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WSCase }
     *     
     */
    public WSCase getIn0() {
        return in0;
    }

    /**
     * 设置in0属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WSCase }
     *     
     */
    public void setIn0(WSCase value) {
        this.in0 = value;
    }

}
