
package com.hailian.ylwmall.wsdl.supplier;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HAIERMDM.RSP_VENDOR_PUR_TABLE complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.RSP_VENDOR_PUR_TABLE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="T_VENDOR_PUR_ITEM" type="{http://xmlns.oracle.com/Interface/OuterSysVendorToMDM_NEW/OuterSysVendorToMDM_NEW}HAIERMDM.RSP_VENDOR_PUR_TYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HAIERMDM.RSP_VENDOR_PUR_TABLE", propOrder = {
    "tvendorpuritem"
})
public class HAIERMDMRSPVENDORPURTABLE {

    @XmlElement(name = "T_VENDOR_PUR_ITEM", nillable = true)
    protected List<HAIERMDMRSPVENDORPURTYPE> tvendorpuritem;

    /**
     * Gets the value of the tvendorpuritem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tvendorpuritem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTVENDORPURITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HAIERMDMRSPVENDORPURTYPE }
     * 
     * 
     */
    public List<HAIERMDMRSPVENDORPURTYPE> getTVENDORPURITEM() {
        if (tvendorpuritem == null) {
            tvendorpuritem = new ArrayList<HAIERMDMRSPVENDORPURTYPE>();
        }
        return this.tvendorpuritem;
    }

}
