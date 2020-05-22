
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
 *         &lt;element name="IN_VENDOR_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_VENDOR_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_ACCOUNT_GRP_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TAX_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_STREET_ROOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_POSTAL_CODE_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_REGION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PHONE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SUB_COMPANY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="T_VENDOR_COMPANY" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_COMPANY_TABLE" minOccurs="0"/&gt;
 *         &lt;element name="T_VENDOR_BANK" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_BANK_TABLE" minOccurs="0"/&gt;
 *         &lt;element name="T_VENDOR_PUR" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_PUR_TABLE" minOccurs="0"/&gt;
 *         &lt;element name="T_VENDOR_FINA" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_FINANCE_TABLE" minOccurs="0"/&gt;
 *         &lt;element name="T_VENDOR_BUNS" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_BUSINESS_TABLE" minOccurs="0"/&gt;
 *         &lt;element name="SYSTEM_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_BANK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_BASE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_COMP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_PUR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_FINANCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VIEW_BUSINESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OPERATE_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LEGAL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BILL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "invendorcode",
    "invendorname",
    "inaccountgrpcode",
    "intaxcode",
    "instreetroom",
    "inpostalcodecity",
    "incountry",
    "inregion",
    "inphonenumber",
    "insubcompany",
    "tvendorcompany",
    "tvendorbank",
    "tvendorpur",
    "tvendorfina",
    "tvendorbuns",
    "systemname",
    "viewbank",
    "viewbase",
    "viewcomp",
    "viewpur",
    "viewfinance",
    "viewbusiness",
    "operatetype",
    "createdby",
    "legaltype",
    "billtype"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElementRef(name = "IN_VENDOR_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> invendorcode;
    @XmlElementRef(name = "IN_VENDOR_NAME", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> invendorname;
    @XmlElementRef(name = "IN_ACCOUNT_GRP_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaccountgrpcode;
    @XmlElementRef(name = "IN_TAX_CODE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intaxcode;
    @XmlElementRef(name = "IN_STREET_ROOM", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> instreetroom;
    @XmlElementRef(name = "IN_POSTAL_CODE_CITY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpostalcodecity;
    @XmlElementRef(name = "IN_COUNTRY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incountry;
    @XmlElementRef(name = "IN_REGION", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inregion;
    @XmlElementRef(name = "IN_PHONE_NUMBER", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inphonenumber;
    @XmlElementRef(name = "IN_SUB_COMPANY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insubcompany;
    @XmlElementRef(name = "T_VENDOR_COMPANY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMRSPVENDORCOMPANYTABLE> tvendorcompany;
    @XmlElementRef(name = "T_VENDOR_BANK", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMRSPVENDORBANKTABLE> tvendorbank;
    @XmlElementRef(name = "T_VENDOR_PUR", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMRSPVENDORPURTABLE> tvendorpur;
    @XmlElementRef(name = "T_VENDOR_FINA", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMRSPVENDORFINANCETABLE> tvendorfina;
    @XmlElementRef(name = "T_VENDOR_BUNS", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMRSPVENDORBUSINESSTABLE> tvendorbuns;
    @XmlElementRef(name = "SYSTEM_NAME", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> systemname;
    @XmlElementRef(name = "VIEW_BANK", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewbank;
    @XmlElementRef(name = "VIEW_BASE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewbase;
    @XmlElementRef(name = "VIEW_COMP", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewcomp;
    @XmlElementRef(name = "VIEW_PUR", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewpur;
    @XmlElementRef(name = "VIEW_FINANCE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewfinance;
    @XmlElementRef(name = "VIEW_BUSINESS", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> viewbusiness;
    @XmlElementRef(name = "OPERATE_TYPE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> operatetype;
    @XmlElementRef(name = "CREATED_BY", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> createdby;
    @XmlElementRef(name = "LEGAL_TYPE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> legaltype;
    @XmlElementRef(name = "BILL_TYPE", namespace = "http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW", type = JAXBElement.class, required = false)
    protected JAXBElement<String> billtype;

    /**
     * 获取invendorcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINVENDORCODE() {
        return invendorcode;
    }

    /**
     * 设置invendorcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINVENDORCODE(JAXBElement<String> value) {
        this.invendorcode = value;
    }

    /**
     * 获取invendorname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINVENDORNAME() {
        return invendorname;
    }

    /**
     * 设置invendorname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINVENDORNAME(JAXBElement<String> value) {
        this.invendorname = value;
    }

    /**
     * 获取inaccountgrpcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINACCOUNTGRPCODE() {
        return inaccountgrpcode;
    }

    /**
     * 设置inaccountgrpcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINACCOUNTGRPCODE(JAXBElement<String> value) {
        this.inaccountgrpcode = value;
    }

    /**
     * 获取intaxcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTAXCODE() {
        return intaxcode;
    }

    /**
     * 设置intaxcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTAXCODE(JAXBElement<String> value) {
        this.intaxcode = value;
    }

    /**
     * 获取instreetroom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSTREETROOM() {
        return instreetroom;
    }

    /**
     * 设置instreetroom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSTREETROOM(JAXBElement<String> value) {
        this.instreetroom = value;
    }

    /**
     * 获取inpostalcodecity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPOSTALCODECITY() {
        return inpostalcodecity;
    }

    /**
     * 设置inpostalcodecity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPOSTALCODECITY(JAXBElement<String> value) {
        this.inpostalcodecity = value;
    }

    /**
     * 获取incountry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCOUNTRY() {
        return incountry;
    }

    /**
     * 设置incountry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCOUNTRY(JAXBElement<String> value) {
        this.incountry = value;
    }

    /**
     * 获取inregion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINREGION() {
        return inregion;
    }

    /**
     * 设置inregion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINREGION(JAXBElement<String> value) {
        this.inregion = value;
    }

    /**
     * 获取inphonenumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPHONENUMBER() {
        return inphonenumber;
    }

    /**
     * 设置inphonenumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPHONENUMBER(JAXBElement<String> value) {
        this.inphonenumber = value;
    }

    /**
     * 获取insubcompany属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSUBCOMPANY() {
        return insubcompany;
    }

    /**
     * 设置insubcompany属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSUBCOMPANY(JAXBElement<String> value) {
        this.insubcompany = value;
    }

    /**
     * 获取tvendorcompany属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORCOMPANYTABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMRSPVENDORCOMPANYTABLE> getTVENDORCOMPANY() {
        return tvendorcompany;
    }

    /**
     * 设置tvendorcompany属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORCOMPANYTABLE }{@code >}
     *     
     */
    public void setTVENDORCOMPANY(JAXBElement<HAIERMDMRSPVENDORCOMPANYTABLE> value) {
        this.tvendorcompany = value;
    }

    /**
     * 获取tvendorbank属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORBANKTABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMRSPVENDORBANKTABLE> getTVENDORBANK() {
        return tvendorbank;
    }

    /**
     * 设置tvendorbank属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORBANKTABLE }{@code >}
     *     
     */
    public void setTVENDORBANK(JAXBElement<HAIERMDMRSPVENDORBANKTABLE> value) {
        this.tvendorbank = value;
    }

    /**
     * 获取tvendorpur属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORPURTABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMRSPVENDORPURTABLE> getTVENDORPUR() {
        return tvendorpur;
    }

    /**
     * 设置tvendorpur属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORPURTABLE }{@code >}
     *     
     */
    public void setTVENDORPUR(JAXBElement<HAIERMDMRSPVENDORPURTABLE> value) {
        this.tvendorpur = value;
    }

    /**
     * 获取tvendorfina属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORFINANCETABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMRSPVENDORFINANCETABLE> getTVENDORFINA() {
        return tvendorfina;
    }

    /**
     * 设置tvendorfina属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORFINANCETABLE }{@code >}
     *     
     */
    public void setTVENDORFINA(JAXBElement<HAIERMDMRSPVENDORFINANCETABLE> value) {
        this.tvendorfina = value;
    }

    /**
     * 获取tvendorbuns属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORBUSINESSTABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMRSPVENDORBUSINESSTABLE> getTVENDORBUNS() {
        return tvendorbuns;
    }

    /**
     * 设置tvendorbuns属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMRSPVENDORBUSINESSTABLE }{@code >}
     *     
     */
    public void setTVENDORBUNS(JAXBElement<HAIERMDMRSPVENDORBUSINESSTABLE> value) {
        this.tvendorbuns = value;
    }

    /**
     * 获取systemname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSYSTEMNAME() {
        return systemname;
    }

    /**
     * 设置systemname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSYSTEMNAME(JAXBElement<String> value) {
        this.systemname = value;
    }

    /**
     * 获取viewbank属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWBANK() {
        return viewbank;
    }

    /**
     * 设置viewbank属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWBANK(JAXBElement<String> value) {
        this.viewbank = value;
    }

    /**
     * 获取viewbase属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWBASE() {
        return viewbase;
    }

    /**
     * 设置viewbase属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWBASE(JAXBElement<String> value) {
        this.viewbase = value;
    }

    /**
     * 获取viewcomp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWCOMP() {
        return viewcomp;
    }

    /**
     * 设置viewcomp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWCOMP(JAXBElement<String> value) {
        this.viewcomp = value;
    }

    /**
     * 获取viewpur属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWPUR() {
        return viewpur;
    }

    /**
     * 设置viewpur属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWPUR(JAXBElement<String> value) {
        this.viewpur = value;
    }

    /**
     * 获取viewfinance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWFINANCE() {
        return viewfinance;
    }

    /**
     * 设置viewfinance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWFINANCE(JAXBElement<String> value) {
        this.viewfinance = value;
    }

    /**
     * 获取viewbusiness属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIEWBUSINESS() {
        return viewbusiness;
    }

    /**
     * 设置viewbusiness属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIEWBUSINESS(JAXBElement<String> value) {
        this.viewbusiness = value;
    }

    /**
     * 获取operatetype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOPERATETYPE() {
        return operatetype;
    }

    /**
     * 设置operatetype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPERATETYPE(JAXBElement<String> value) {
        this.operatetype = value;
    }

    /**
     * 获取createdby属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCREATEDBY() {
        return createdby;
    }

    /**
     * 设置createdby属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCREATEDBY(JAXBElement<String> value) {
        this.createdby = value;
    }

    /**
     * 获取legaltype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLEGALTYPE() {
        return legaltype;
    }

    /**
     * 设置legaltype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLEGALTYPE(JAXBElement<String> value) {
        this.legaltype = value;
    }

    /**
     * 获取billtype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBILLTYPE() {
        return billtype;
    }

    /**
     * 设置billtype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBILLTYPE(JAXBElement<String> value) {
        this.billtype = value;
    }

}
