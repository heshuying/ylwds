
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
 *         &lt;element name="IN_CUSTOMERNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PARTNERFLAGSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PARTNERFLAGSH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PARTNERFLAGBP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PARTNERFLAGPY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CUSTOMERTITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_ACCOUNTGROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CUSTOMERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SEARCHTERMS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CUSTOMERCOUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_REGION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CONTACTPERSON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_PHONENUMBER1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_VATREGNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_INDUSTRYCLASS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TAXDOCTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_LEGAL_PERSON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_LEGAL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TRAINSTATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TELEPHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_BANKCOUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_BANKKEY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_ACCOUNTHOLDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_COMPANYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_RECONACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SORTKEY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CUSTOMER_SUMMARY_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CUSTOMER_PRICE_PROCEDURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_ACCT_ASSGMT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TAX_CLASSIFICATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SHIP_CONDITION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_POD_RELATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SALES_REGION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANACOUNTNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANORGN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANORGNSHORT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANACOUNTCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANCURRENCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FINANSTATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_SYSNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OPERATETYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_BASE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_BANK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_EXTEND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_SALE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_COMP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UPDATE_FINANCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IF_AFFIX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_AFFIX_IP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_AFFIX_USERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_AFFIX_PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_AFFIX_URL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_AFFIX_FILENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_PROVINCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_AREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_TOWN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_VILLAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_ROAD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_HOUSE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_LONGITUDE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_ADDRESS_LATITUDE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in_SPECIFIC_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_INNER_COMPANYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "incustomernumber",
    "inpartnerflagsp",
    "inpartnerflagsh",
    "inpartnerflagbp",
    "inpartnerflagpy",
    "incustomertitle",
    "inaccountgroup",
    "incustomername",
    "insearchterms",
    "incustomercountry",
    "inlanguage",
    "inregion",
    "incity",
    "instreet",
    "incontactperson",
    "inphonenumber1",
    "invatregno",
    "inindustryclass",
    "intaxdoctype",
    "inlegalperson",
    "inlegaltype",
    "intrainstation",
    "intelephone",
    "inbankcountry",
    "inbankkey",
    "inaccountholder",
    "incompanycode",
    "inreconaccount",
    "insortkey",
    "incustomersummarygroup",
    "incurrency",
    "incustomerpriceprocedure",
    "inacctassgmtgroup",
    "intaxclassification",
    "inshipcondition",
    "inpodrelation",
    "insalesregion",
    "infinanacountname",
    "infinanorgn",
    "infinanorgnshort",
    "infinanacountcode",
    "infinancurrency",
    "infinantype",
    "infinanstate",
    "insysname",
    "increatedby",
    "operatetype",
    "updatebase",
    "updatebank",
    "updateextend",
    "updatesale",
    "updatecomp",
    "updatefinance",
    "ifaffix",
    "inaffixip",
    "inaffixusername",
    "inaffixpassword",
    "inaffixurl",
    "inaffixfilename",
    "inADDRESSPROVINCE",
    "inADDRESSCITY",
    "inADDRESSAREA",
    "inADDRESSTOWN",
    "inADDRESSVILLAGE",
    "inADDRESSROAD",
    "inADDRESSHOUSENUMBER",
    "inADDRESSLONGITUDE",
    "inADDRESSLATITUDE",
    "inSPECIFICADDRESS",
    "ininnercompanycode"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElementRef(name = "IN_CUSTOMERNUMBER", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomernumber;
    @XmlElementRef(name = "IN_PARTNERFLAGSP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpartnerflagsp;
    @XmlElementRef(name = "IN_PARTNERFLAGSH", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpartnerflagsh;
    @XmlElementRef(name = "IN_PARTNERFLAGBP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpartnerflagbp;
    @XmlElementRef(name = "IN_PARTNERFLAGPY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpartnerflagpy;
    @XmlElementRef(name = "IN_CUSTOMERTITLE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomertitle;
    @XmlElementRef(name = "IN_ACCOUNTGROUP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaccountgroup;
    @XmlElementRef(name = "IN_CUSTOMERNAME", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomername;
    @XmlElementRef(name = "IN_SEARCHTERMS", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insearchterms;
    @XmlElementRef(name = "IN_CUSTOMERCOUNTRY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomercountry;
    @XmlElementRef(name = "IN_LANGUAGE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inlanguage;
    @XmlElementRef(name = "IN_REGION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inregion;
    @XmlElementRef(name = "IN_CITY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incity;
    @XmlElementRef(name = "IN_STREET", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> instreet;
    @XmlElementRef(name = "IN_CONTACTPERSON", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incontactperson;
    @XmlElementRef(name = "IN_PHONENUMBER1", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inphonenumber1;
    @XmlElementRef(name = "IN_VATREGNO", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> invatregno;
    @XmlElementRef(name = "IN_INDUSTRYCLASS", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inindustryclass;
    @XmlElementRef(name = "IN_TAXDOCTYPE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intaxdoctype;
    @XmlElementRef(name = "IN_LEGAL_PERSON", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inlegalperson;
    @XmlElementRef(name = "IN_LEGAL_TYPE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inlegaltype;
    @XmlElementRef(name = "IN_TRAINSTATION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intrainstation;
    @XmlElementRef(name = "IN_TELEPHONE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intelephone;
    @XmlElementRef(name = "IN_BANKCOUNTRY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inbankcountry;
    @XmlElementRef(name = "IN_BANKKEY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inbankkey;
    @XmlElementRef(name = "IN_ACCOUNTHOLDER", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaccountholder;
    @XmlElementRef(name = "IN_COMPANYCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incompanycode;
    @XmlElementRef(name = "IN_RECONACCOUNT", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inreconaccount;
    @XmlElementRef(name = "IN_SORTKEY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insortkey;
    @XmlElementRef(name = "IN_CUSTOMER_SUMMARY_GROUP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomersummarygroup;
    @XmlElementRef(name = "IN_CURRENCY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incurrency;
    @XmlElementRef(name = "IN_CUSTOMER_PRICE_PROCEDURE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> incustomerpriceprocedure;
    @XmlElementRef(name = "IN_ACCT_ASSGMT_GROUP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inacctassgmtgroup;
    @XmlElementRef(name = "IN_TAX_CLASSIFICATION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intaxclassification;
    @XmlElementRef(name = "IN_SHIP_CONDITION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inshipcondition;
    @XmlElementRef(name = "IN_POD_RELATION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inpodrelation;
    @XmlElementRef(name = "IN_SALES_REGION", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insalesregion;
    @XmlElementRef(name = "IN_FINANACOUNTNAME", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinanacountname;
    @XmlElementRef(name = "IN_FINANORGN", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinanorgn;
    @XmlElementRef(name = "IN_FINANORGNSHORT", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinanorgnshort;
    @XmlElementRef(name = "IN_FINANACOUNTCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinanacountcode;
    @XmlElementRef(name = "IN_FINANCURRENCY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinancurrency;
    @XmlElementRef(name = "IN_FINANTYPE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinantype;
    @XmlElementRef(name = "IN_FINANSTATE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> infinanstate;
    @XmlElementRef(name = "IN_SYSNAME", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insysname;
    @XmlElementRef(name = "IN_CREATED_BY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> increatedby;
    @XmlElementRef(name = "OPERATETYPE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> operatetype;
    @XmlElementRef(name = "UPDATE_BASE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatebase;
    @XmlElementRef(name = "UPDATE_BANK", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatebank;
    @XmlElementRef(name = "UPDATE_EXTEND", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updateextend;
    @XmlElementRef(name = "UPDATE_SALE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatesale;
    @XmlElementRef(name = "UPDATE_COMP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatecomp;
    @XmlElementRef(name = "UPDATE_FINANCE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatefinance;
    @XmlElementRef(name = "IF_AFFIX", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ifaffix;
    @XmlElementRef(name = "IN_AFFIX_IP", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaffixip;
    @XmlElementRef(name = "IN_AFFIX_USERNAME", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaffixusername;
    @XmlElementRef(name = "IN_AFFIX_PASSWORD", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaffixpassword;
    @XmlElementRef(name = "IN_AFFIX_URL", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaffixurl;
    @XmlElementRef(name = "IN_AFFIX_FILENAME", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inaffixfilename;
    @XmlElementRef(name = "in_ADDRESS_PROVINCE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSPROVINCE;
    @XmlElementRef(name = "in_ADDRESS_CITY", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSCITY;
    @XmlElementRef(name = "in_ADDRESS_AREA", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSAREA;
    @XmlElementRef(name = "in_ADDRESS_TOWN", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSTOWN;
    @XmlElementRef(name = "in_ADDRESS_VILLAGE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSVILLAGE;
    @XmlElementRef(name = "in_ADDRESS_ROAD", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSROAD;
    @XmlElementRef(name = "in_ADDRESS_HOUSE_NUMBER", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSHOUSENUMBER;
    @XmlElementRef(name = "in_ADDRESS_LONGITUDE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSLONGITUDE;
    @XmlElementRef(name = "in_ADDRESS_LATITUDE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inADDRESSLATITUDE;
    @XmlElementRef(name = "in_SPECIFIC_ADDRESS", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inSPECIFICADDRESS;
    @XmlElementRef(name = "IN_INNER_COMPANYCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ininnercompanycode;

    /**
     * 获取incustomernumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERNUMBER() {
        return incustomernumber;
    }

    /**
     * 设置incustomernumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERNUMBER(JAXBElement<String> value) {
        this.incustomernumber = value;
    }

    /**
     * 获取inpartnerflagsp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPARTNERFLAGSP() {
        return inpartnerflagsp;
    }

    /**
     * 设置inpartnerflagsp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPARTNERFLAGSP(JAXBElement<String> value) {
        this.inpartnerflagsp = value;
    }

    /**
     * 获取inpartnerflagsh属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPARTNERFLAGSH() {
        return inpartnerflagsh;
    }

    /**
     * 设置inpartnerflagsh属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPARTNERFLAGSH(JAXBElement<String> value) {
        this.inpartnerflagsh = value;
    }

    /**
     * 获取inpartnerflagbp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPARTNERFLAGBP() {
        return inpartnerflagbp;
    }

    /**
     * 设置inpartnerflagbp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPARTNERFLAGBP(JAXBElement<String> value) {
        this.inpartnerflagbp = value;
    }

    /**
     * 获取inpartnerflagpy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPARTNERFLAGPY() {
        return inpartnerflagpy;
    }

    /**
     * 设置inpartnerflagpy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPARTNERFLAGPY(JAXBElement<String> value) {
        this.inpartnerflagpy = value;
    }

    /**
     * 获取incustomertitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERTITLE() {
        return incustomertitle;
    }

    /**
     * 设置incustomertitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERTITLE(JAXBElement<String> value) {
        this.incustomertitle = value;
    }

    /**
     * 获取inaccountgroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINACCOUNTGROUP() {
        return inaccountgroup;
    }

    /**
     * 设置inaccountgroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINACCOUNTGROUP(JAXBElement<String> value) {
        this.inaccountgroup = value;
    }

    /**
     * 获取incustomername属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERNAME() {
        return incustomername;
    }

    /**
     * 设置incustomername属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERNAME(JAXBElement<String> value) {
        this.incustomername = value;
    }

    /**
     * 获取insearchterms属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSEARCHTERMS() {
        return insearchterms;
    }

    /**
     * 设置insearchterms属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSEARCHTERMS(JAXBElement<String> value) {
        this.insearchterms = value;
    }

    /**
     * 获取incustomercountry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERCOUNTRY() {
        return incustomercountry;
    }

    /**
     * 设置incustomercountry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERCOUNTRY(JAXBElement<String> value) {
        this.incustomercountry = value;
    }

    /**
     * 获取inlanguage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINLANGUAGE() {
        return inlanguage;
    }

    /**
     * 设置inlanguage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINLANGUAGE(JAXBElement<String> value) {
        this.inlanguage = value;
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
     * 获取incity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCITY() {
        return incity;
    }

    /**
     * 设置incity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCITY(JAXBElement<String> value) {
        this.incity = value;
    }

    /**
     * 获取instreet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSTREET() {
        return instreet;
    }

    /**
     * 设置instreet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSTREET(JAXBElement<String> value) {
        this.instreet = value;
    }

    /**
     * 获取incontactperson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCONTACTPERSON() {
        return incontactperson;
    }

    /**
     * 设置incontactperson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCONTACTPERSON(JAXBElement<String> value) {
        this.incontactperson = value;
    }

    /**
     * 获取inphonenumber1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPHONENUMBER1() {
        return inphonenumber1;
    }

    /**
     * 设置inphonenumber1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPHONENUMBER1(JAXBElement<String> value) {
        this.inphonenumber1 = value;
    }

    /**
     * 获取invatregno属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINVATREGNO() {
        return invatregno;
    }

    /**
     * 设置invatregno属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINVATREGNO(JAXBElement<String> value) {
        this.invatregno = value;
    }

    /**
     * 获取inindustryclass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getININDUSTRYCLASS() {
        return inindustryclass;
    }

    /**
     * 设置inindustryclass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setININDUSTRYCLASS(JAXBElement<String> value) {
        this.inindustryclass = value;
    }

    /**
     * 获取intaxdoctype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTAXDOCTYPE() {
        return intaxdoctype;
    }

    /**
     * 设置intaxdoctype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTAXDOCTYPE(JAXBElement<String> value) {
        this.intaxdoctype = value;
    }

    /**
     * 获取inlegalperson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINLEGALPERSON() {
        return inlegalperson;
    }

    /**
     * 设置inlegalperson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINLEGALPERSON(JAXBElement<String> value) {
        this.inlegalperson = value;
    }

    /**
     * 获取inlegaltype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINLEGALTYPE() {
        return inlegaltype;
    }

    /**
     * 设置inlegaltype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINLEGALTYPE(JAXBElement<String> value) {
        this.inlegaltype = value;
    }

    /**
     * 获取intrainstation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTRAINSTATION() {
        return intrainstation;
    }

    /**
     * 设置intrainstation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTRAINSTATION(JAXBElement<String> value) {
        this.intrainstation = value;
    }

    /**
     * 获取intelephone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTELEPHONE() {
        return intelephone;
    }

    /**
     * 设置intelephone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTELEPHONE(JAXBElement<String> value) {
        this.intelephone = value;
    }

    /**
     * 获取inbankcountry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINBANKCOUNTRY() {
        return inbankcountry;
    }

    /**
     * 设置inbankcountry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINBANKCOUNTRY(JAXBElement<String> value) {
        this.inbankcountry = value;
    }

    /**
     * 获取inbankkey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINBANKKEY() {
        return inbankkey;
    }

    /**
     * 设置inbankkey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINBANKKEY(JAXBElement<String> value) {
        this.inbankkey = value;
    }

    /**
     * 获取inaccountholder属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINACCOUNTHOLDER() {
        return inaccountholder;
    }

    /**
     * 设置inaccountholder属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINACCOUNTHOLDER(JAXBElement<String> value) {
        this.inaccountholder = value;
    }

    /**
     * 获取incompanycode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCOMPANYCODE() {
        return incompanycode;
    }

    /**
     * 设置incompanycode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCOMPANYCODE(JAXBElement<String> value) {
        this.incompanycode = value;
    }

    /**
     * 获取inreconaccount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINRECONACCOUNT() {
        return inreconaccount;
    }

    /**
     * 设置inreconaccount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINRECONACCOUNT(JAXBElement<String> value) {
        this.inreconaccount = value;
    }

    /**
     * 获取insortkey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSORTKEY() {
        return insortkey;
    }

    /**
     * 设置insortkey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSORTKEY(JAXBElement<String> value) {
        this.insortkey = value;
    }

    /**
     * 获取incustomersummarygroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERSUMMARYGROUP() {
        return incustomersummarygroup;
    }

    /**
     * 设置incustomersummarygroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERSUMMARYGROUP(JAXBElement<String> value) {
        this.incustomersummarygroup = value;
    }

    /**
     * 获取incurrency属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCURRENCY() {
        return incurrency;
    }

    /**
     * 设置incurrency属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCURRENCY(JAXBElement<String> value) {
        this.incurrency = value;
    }

    /**
     * 获取incustomerpriceprocedure属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCUSTOMERPRICEPROCEDURE() {
        return incustomerpriceprocedure;
    }

    /**
     * 设置incustomerpriceprocedure属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCUSTOMERPRICEPROCEDURE(JAXBElement<String> value) {
        this.incustomerpriceprocedure = value;
    }

    /**
     * 获取inacctassgmtgroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINACCTASSGMTGROUP() {
        return inacctassgmtgroup;
    }

    /**
     * 设置inacctassgmtgroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINACCTASSGMTGROUP(JAXBElement<String> value) {
        this.inacctassgmtgroup = value;
    }

    /**
     * 获取intaxclassification属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTAXCLASSIFICATION() {
        return intaxclassification;
    }

    /**
     * 设置intaxclassification属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTAXCLASSIFICATION(JAXBElement<String> value) {
        this.intaxclassification = value;
    }

    /**
     * 获取inshipcondition属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSHIPCONDITION() {
        return inshipcondition;
    }

    /**
     * 设置inshipcondition属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSHIPCONDITION(JAXBElement<String> value) {
        this.inshipcondition = value;
    }

    /**
     * 获取inpodrelation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPODRELATION() {
        return inpodrelation;
    }

    /**
     * 设置inpodrelation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPODRELATION(JAXBElement<String> value) {
        this.inpodrelation = value;
    }

    /**
     * 获取insalesregion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSALESREGION() {
        return insalesregion;
    }

    /**
     * 设置insalesregion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSALESREGION(JAXBElement<String> value) {
        this.insalesregion = value;
    }

    /**
     * 获取infinanacountname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANACOUNTNAME() {
        return infinanacountname;
    }

    /**
     * 设置infinanacountname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANACOUNTNAME(JAXBElement<String> value) {
        this.infinanacountname = value;
    }

    /**
     * 获取infinanorgn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANORGN() {
        return infinanorgn;
    }

    /**
     * 设置infinanorgn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANORGN(JAXBElement<String> value) {
        this.infinanorgn = value;
    }

    /**
     * 获取infinanorgnshort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANORGNSHORT() {
        return infinanorgnshort;
    }

    /**
     * 设置infinanorgnshort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANORGNSHORT(JAXBElement<String> value) {
        this.infinanorgnshort = value;
    }

    /**
     * 获取infinanacountcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANACOUNTCODE() {
        return infinanacountcode;
    }

    /**
     * 设置infinanacountcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANACOUNTCODE(JAXBElement<String> value) {
        this.infinanacountcode = value;
    }

    /**
     * 获取infinancurrency属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANCURRENCY() {
        return infinancurrency;
    }

    /**
     * 设置infinancurrency属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANCURRENCY(JAXBElement<String> value) {
        this.infinancurrency = value;
    }

    /**
     * 获取infinantype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANTYPE() {
        return infinantype;
    }

    /**
     * 设置infinantype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANTYPE(JAXBElement<String> value) {
        this.infinantype = value;
    }

    /**
     * 获取infinanstate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINFINANSTATE() {
        return infinanstate;
    }

    /**
     * 设置infinanstate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINFINANSTATE(JAXBElement<String> value) {
        this.infinanstate = value;
    }

    /**
     * 获取insysname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSYSNAME() {
        return insysname;
    }

    /**
     * 设置insysname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSYSNAME(JAXBElement<String> value) {
        this.insysname = value;
    }

    /**
     * 获取increatedby属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCREATEDBY() {
        return increatedby;
    }

    /**
     * 设置increatedby属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCREATEDBY(JAXBElement<String> value) {
        this.increatedby = value;
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
     * 获取updatebase属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATEBASE() {
        return updatebase;
    }

    /**
     * 设置updatebase属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATEBASE(JAXBElement<String> value) {
        this.updatebase = value;
    }

    /**
     * 获取updatebank属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATEBANK() {
        return updatebank;
    }

    /**
     * 设置updatebank属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATEBANK(JAXBElement<String> value) {
        this.updatebank = value;
    }

    /**
     * 获取updateextend属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATEEXTEND() {
        return updateextend;
    }

    /**
     * 设置updateextend属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATEEXTEND(JAXBElement<String> value) {
        this.updateextend = value;
    }

    /**
     * 获取updatesale属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATESALE() {
        return updatesale;
    }

    /**
     * 设置updatesale属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATESALE(JAXBElement<String> value) {
        this.updatesale = value;
    }

    /**
     * 获取updatecomp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATECOMP() {
        return updatecomp;
    }

    /**
     * 设置updatecomp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATECOMP(JAXBElement<String> value) {
        this.updatecomp = value;
    }

    /**
     * 获取updatefinance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATEFINANCE() {
        return updatefinance;
    }

    /**
     * 设置updatefinance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATEFINANCE(JAXBElement<String> value) {
        this.updatefinance = value;
    }

    /**
     * 获取ifaffix属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIFAFFIX() {
        return ifaffix;
    }

    /**
     * 设置ifaffix属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIFAFFIX(JAXBElement<String> value) {
        this.ifaffix = value;
    }

    /**
     * 获取inaffixip属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINAFFIXIP() {
        return inaffixip;
    }

    /**
     * 设置inaffixip属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINAFFIXIP(JAXBElement<String> value) {
        this.inaffixip = value;
    }

    /**
     * 获取inaffixusername属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINAFFIXUSERNAME() {
        return inaffixusername;
    }

    /**
     * 设置inaffixusername属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINAFFIXUSERNAME(JAXBElement<String> value) {
        this.inaffixusername = value;
    }

    /**
     * 获取inaffixpassword属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINAFFIXPASSWORD() {
        return inaffixpassword;
    }

    /**
     * 设置inaffixpassword属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINAFFIXPASSWORD(JAXBElement<String> value) {
        this.inaffixpassword = value;
    }

    /**
     * 获取inaffixurl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINAFFIXURL() {
        return inaffixurl;
    }

    /**
     * 设置inaffixurl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINAFFIXURL(JAXBElement<String> value) {
        this.inaffixurl = value;
    }

    /**
     * 获取inaffixfilename属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINAFFIXFILENAME() {
        return inaffixfilename;
    }

    /**
     * 设置inaffixfilename属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINAFFIXFILENAME(JAXBElement<String> value) {
        this.inaffixfilename = value;
    }

    /**
     * 获取inADDRESSPROVINCE属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSPROVINCE() {
        return inADDRESSPROVINCE;
    }

    /**
     * 设置inADDRESSPROVINCE属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSPROVINCE(JAXBElement<String> value) {
        this.inADDRESSPROVINCE = value;
    }

    /**
     * 获取inADDRESSCITY属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSCITY() {
        return inADDRESSCITY;
    }

    /**
     * 设置inADDRESSCITY属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSCITY(JAXBElement<String> value) {
        this.inADDRESSCITY = value;
    }

    /**
     * 获取inADDRESSAREA属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSAREA() {
        return inADDRESSAREA;
    }

    /**
     * 设置inADDRESSAREA属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSAREA(JAXBElement<String> value) {
        this.inADDRESSAREA = value;
    }

    /**
     * 获取inADDRESSTOWN属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSTOWN() {
        return inADDRESSTOWN;
    }

    /**
     * 设置inADDRESSTOWN属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSTOWN(JAXBElement<String> value) {
        this.inADDRESSTOWN = value;
    }

    /**
     * 获取inADDRESSVILLAGE属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSVILLAGE() {
        return inADDRESSVILLAGE;
    }

    /**
     * 设置inADDRESSVILLAGE属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSVILLAGE(JAXBElement<String> value) {
        this.inADDRESSVILLAGE = value;
    }

    /**
     * 获取inADDRESSROAD属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSROAD() {
        return inADDRESSROAD;
    }

    /**
     * 设置inADDRESSROAD属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSROAD(JAXBElement<String> value) {
        this.inADDRESSROAD = value;
    }

    /**
     * 获取inADDRESSHOUSENUMBER属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSHOUSENUMBER() {
        return inADDRESSHOUSENUMBER;
    }

    /**
     * 设置inADDRESSHOUSENUMBER属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSHOUSENUMBER(JAXBElement<String> value) {
        this.inADDRESSHOUSENUMBER = value;
    }

    /**
     * 获取inADDRESSLONGITUDE属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSLONGITUDE() {
        return inADDRESSLONGITUDE;
    }

    /**
     * 设置inADDRESSLONGITUDE属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSLONGITUDE(JAXBElement<String> value) {
        this.inADDRESSLONGITUDE = value;
    }

    /**
     * 获取inADDRESSLATITUDE属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInADDRESSLATITUDE() {
        return inADDRESSLATITUDE;
    }

    /**
     * 设置inADDRESSLATITUDE属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInADDRESSLATITUDE(JAXBElement<String> value) {
        this.inADDRESSLATITUDE = value;
    }

    /**
     * 获取inSPECIFICADDRESS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInSPECIFICADDRESS() {
        return inSPECIFICADDRESS;
    }

    /**
     * 设置inSPECIFICADDRESS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInSPECIFICADDRESS(JAXBElement<String> value) {
        this.inSPECIFICADDRESS = value;
    }

    /**
     * 获取ininnercompanycode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getININNERCOMPANYCODE() {
        return ininnercompanycode;
    }

    /**
     * 设置ininnercompanycode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setININNERCOMPANYCODE(JAXBElement<String> value) {
        this.ininnercompanycode = value;
    }

}
