
package com.hailian.ylwmall.wsdl.supplier;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="RETCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RETMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OUT_ROW_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OUT_VENDOR_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OUT_TAX_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "retcode",
    "retmsg",
    "outrowid",
    "outvendorcode",
    "outtaxcode"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElementRef(name = "RETCODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retcode;
    @XmlElementRef(name = "RETMSG", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retmsg;
    @XmlElementRef(name = "OUT_ROW_ID", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outrowid;
    @XmlElementRef(name = "OUT_VENDOR_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outvendorcode;
    @XmlElementRef(name = "OUT_TAX_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outtaxcode;

    /**
     * 获取retcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRETCODE() {
        return retcode;
    }

    /**
     * 设置retcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRETCODE(JAXBElement<String> value) {
        this.retcode = value;
    }

    /**
     * 获取retmsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRETMSG() {
        return retmsg;
    }

    /**
     * 设置retmsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRETMSG(JAXBElement<String> value) {
        this.retmsg = value;
    }

    /**
     * 获取outrowid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTROWID() {
        return outrowid;
    }

    /**
     * 设置outrowid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTROWID(JAXBElement<String> value) {
        this.outrowid = value;
    }

    /**
     * 获取outvendorcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTVENDORCODE() {
        return outvendorcode;
    }

    /**
     * 设置outvendorcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTVENDORCODE(JAXBElement<String> value) {
        this.outvendorcode = value;
    }

    /**
     * 获取outtaxcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTTAXCODE() {
        return outtaxcode;
    }

    /**
     * 设置outtaxcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTTAXCODE(JAXBElement<String> value) {
        this.outtaxcode = value;
    }

}
