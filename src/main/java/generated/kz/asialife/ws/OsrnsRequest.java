//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 12:42:09 PM ALMT 
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
 *         &lt;element name="oked" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="yearFond" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="col_sotr" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "oked",
    "yearFond",
    "colSotr"
})
@XmlRootElement(name = "osrnsRequest")
public class OsrnsRequest {

    protected int oked;
    protected double yearFond;
    @XmlElement(name = "col_sotr")
    protected int colSotr;

    /**
     * Gets the value of the oked property.
     * 
     */
    public int getOked() {
        return oked;
    }

    /**
     * Sets the value of the oked property.
     * 
     */
    public void setOked(int value) {
        this.oked = value;
    }

    /**
     * Gets the value of the yearFond property.
     * 
     */
    public double getYearFond() {
        return yearFond;
    }

    /**
     * Sets the value of the yearFond property.
     * 
     */
    public void setYearFond(double value) {
        this.yearFond = value;
    }

    /**
     * Gets the value of the colSotr property.
     * 
     */
    public int getColSotr() {
        return colSotr;
    }

    /**
     * Sets the value of the colSotr property.
     * 
     */
    public void setColSotr(int value) {
        this.colSotr = value;
    }

}
