
package com.hailian.ylwmall.wsdl.supplier;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HAIERMDM.RSP_VENDOR_BUSINESS_TYPE complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.RSP_VENDOR_BUSINESS_TYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIRST_CLASS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SECOND_CLASS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HAIERMDM.RSP_VENDOR_BUSINESS_TYPE", propOrder = {
    "firstclass",
    "secondclass"
})
public class HAIERMDMRSPVENDORBUSINESSTYPE {

    @XmlElementRef(name = "FIRST_CLASS", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstclass;
    @XmlElementRef(name = "SECOND_CLASS", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> secondclass;

    /**
     * 获取firstclass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFIRSTCLASS() {
        return firstclass;
    }

    /**
     * 设置firstclass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFIRSTCLASS(JAXBElement<String> value) {
        this.firstclass = value;
    }

    /**
     * 获取secondclass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSECONDCLASS() {
        return secondclass;
    }

    /**
     * 设置secondclass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSECONDCLASS(JAXBElement<String> value) {
        this.secondclass = value;
    }

}
