
package com.hailian.ylwmall.wsdl.supplier;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HAIERMDM.RSP_VENDOR_BANK_TYPE complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.RSP_VENDOR_BANK_TYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ACCOUNT_HOLDER_NAME" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="60"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BANK_COUNTRY" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BANK_ACCOUNT_NUM" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BANK_BRANCH_CODE" minOccurs="0"&gt;
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
@XmlType(name = "HAIERMDM.RSP_VENDOR_BANK_TYPE", propOrder = {
    "accountholdername",
    "bankcountry",
    "bankaccountnum",
    "bankbranchcode"
})
public class HAIERMDMRSPVENDORBANKTYPE {

    @XmlElementRef(name = "ACCOUNT_HOLDER_NAME", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountholdername;
    @XmlElementRef(name = "BANK_COUNTRY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bankcountry;
    @XmlElementRef(name = "BANK_ACCOUNT_NUM", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bankaccountnum;
    @XmlElementRef(name = "BANK_BRANCH_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bankbranchcode;

    /**
     * 获取accountholdername属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACCOUNTHOLDERNAME() {
        return accountholdername;
    }

    /**
     * 设置accountholdername属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACCOUNTHOLDERNAME(JAXBElement<String> value) {
        this.accountholdername = value;
    }

    /**
     * 获取bankcountry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBANKCOUNTRY() {
        return bankcountry;
    }

    /**
     * 设置bankcountry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBANKCOUNTRY(JAXBElement<String> value) {
        this.bankcountry = value;
    }

    /**
     * 获取bankaccountnum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBANKACCOUNTNUM() {
        return bankaccountnum;
    }

    /**
     * 设置bankaccountnum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBANKACCOUNTNUM(JAXBElement<String> value) {
        this.bankaccountnum = value;
    }

    /**
     * 获取bankbranchcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBANKBRANCHCODE() {
        return bankbranchcode;
    }

    /**
     * 设置bankbranchcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBANKBRANCHCODE(JAXBElement<String> value) {
        this.bankbranchcode = value;
    }

}
