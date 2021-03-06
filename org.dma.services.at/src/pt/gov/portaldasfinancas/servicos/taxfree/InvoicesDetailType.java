
package pt.gov.portaldasfinancas.servicos.taxfree;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoicesDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoicesDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumberOfEntries">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="InvoicesGrossTotal" type="{http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService}PositiveMonetaryType"/>
 *         &lt;element name="CalculatedTaxes" type="{http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService}CalculatedTaxesType" minOccurs="0"/>
 *         &lt;element name="InvoicesList" type="{http://servicos.portaldasfinancas.gov.pt/eTaxFree/TaxFreeService}InvoicesListDetailType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoicesDetailType", propOrder = {
    "numberOfEntries",
    "invoicesGrossTotal",
    "calculatedTaxes",
    "invoicesList"
})
public class InvoicesDetailType {

    @XmlElement(name = "NumberOfEntries")
    protected int numberOfEntries;
    @XmlElement(name = "InvoicesGrossTotal", required = true)
    protected BigDecimal invoicesGrossTotal;
    @XmlElement(name = "CalculatedTaxes")
    protected CalculatedTaxesType calculatedTaxes;
    @XmlElement(name = "InvoicesList", required = true)
    protected InvoicesListDetailType invoicesList;

    /**
     * Gets the value of the numberOfEntries property.
     * 
     */
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    /**
     * Sets the value of the numberOfEntries property.
     * 
     */
    public void setNumberOfEntries(int value) {
        this.numberOfEntries = value;
    }

    /**
     * Gets the value of the invoicesGrossTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInvoicesGrossTotal() {
        return invoicesGrossTotal;
    }

    /**
     * Sets the value of the invoicesGrossTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInvoicesGrossTotal(BigDecimal value) {
        this.invoicesGrossTotal = value;
    }

    /**
     * Gets the value of the calculatedTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link CalculatedTaxesType }
     *     
     */
    public CalculatedTaxesType getCalculatedTaxes() {
        return calculatedTaxes;
    }

    /**
     * Sets the value of the calculatedTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalculatedTaxesType }
     *     
     */
    public void setCalculatedTaxes(CalculatedTaxesType value) {
        this.calculatedTaxes = value;
    }

    /**
     * Gets the value of the invoicesList property.
     * 
     * @return
     *     possible object is
     *     {@link InvoicesListDetailType }
     *     
     */
    public InvoicesListDetailType getInvoicesList() {
        return invoicesList;
    }

    /**
     * Sets the value of the invoicesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoicesListDetailType }
     *     
     */
    public void setInvoicesList(InvoicesListDetailType value) {
        this.invoicesList = value;
    }

}
