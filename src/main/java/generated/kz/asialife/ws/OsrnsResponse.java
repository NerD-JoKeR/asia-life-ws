//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.14 at 06:44:36 PM ALMT 
//


package kz.asialife.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="premKz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kurs" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sumStrahKz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="premEur" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="err" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "premKz",
    "kurs",
    "sumStrahKz",
    "premEur",
    "err",
    "result"
})
@XmlRootElement(name = "osrnsResponse")
public class OsrnsResponse {

    @XmlElement(required = true)
    protected String premKz;
    @XmlElement(required = true)
    protected String kurs;
    @XmlElement(required = true)
    protected String sumStrahKz;
    @XmlElement(required = true)
    protected String premEur;
    @XmlElement(required = true)
    protected String err;
    @XmlElement(required = true)
    protected String result;

    /**
     * Gets the value of the premKz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremKz() {
        return premKz;
    }

    /**
     * Sets the value of the premKz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremKz(String value) {
        this.premKz = value;
    }

    /**
     * Gets the value of the kurs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKurs() {
        return kurs;
    }

    /**
     * Sets the value of the kurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKurs(String value) {
        this.kurs = value;
    }

    /**
     * Gets the value of the sumStrahKz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSumStrahKz() {
        return sumStrahKz;
    }

    /**
     * Sets the value of the sumStrahKz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSumStrahKz(String value) {
        this.sumStrahKz = value;
    }

    /**
     * Gets the value of the premEur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremEur() {
        return premEur;
    }

    /**
     * Sets the value of the premEur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremEur(String value) {
        this.premEur = value;
    }

    /**
     * Gets the value of the err property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErr() {
        return err;
    }

    /**
     * Sets the value of the err property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErr(String value) {
        this.err = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

}
