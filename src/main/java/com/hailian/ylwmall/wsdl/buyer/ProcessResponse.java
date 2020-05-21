
package com.hailian.ylwmall.wsdl.buyer;

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
 *         &lt;element name="OUT_CUSTOMERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RETCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RETMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "outcustomercode",
    "retcode",
    "retmsg"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElementRef(name = "OUT_CUSTOMERCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outcustomercode;
    @XmlElementRef(name = "RETCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retcode;
    @XmlElementRef(name = "RETMSG", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retmsg;

    /**
     * 获取outcustomercode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTCUSTOMERCODE() {
        return outcustomercode;
    }

    /**
     * 设置outcustomercode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTCUSTOMERCODE(JAXBElement<String> value) {
        this.outcustomercode = value;
    }

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

}
