//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2016.12.20 às 03:43:14 PM BRT 
//


package br.ufc.storm.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de calculated_function_type complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="calculated_function_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="function_parameters" type="{http://storm.lia.ufc.br}calculated_function_term_type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="function_arguments" type="{http://storm.lia.ufc.br}context_argument_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="function_id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="function_value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cc_id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="cp_id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculated_function_type", propOrder = {
    "functionParameters",
    "functionArguments"
})
public class CalculatedFunctionType {

    @XmlElement(name = "function_parameters")
    protected List<CalculatedFunctionTermType> functionParameters;
    @XmlElement(name = "function_arguments")
    protected List<ContextArgumentType> functionArguments;
    @XmlAttribute(name = "function_id")
    protected Integer functionId;
    @XmlAttribute(name = "function_value")
    protected String functionValue;
    @XmlAttribute(name = "cc_id")
    protected Integer ccId;
    @XmlAttribute(name = "cp_id")
    protected Integer cpId;

    /**
     * Gets the value of the functionParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CalculatedFunctionTermType }
     * 
     * 
     */
    public List<CalculatedFunctionTermType> getFunctionParameters() {
        if (functionParameters == null) {
            functionParameters = new ArrayList<CalculatedFunctionTermType>();
        }
        return this.functionParameters;
    }

    /**
     * Gets the value of the functionArguments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionArguments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionArguments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContextArgumentType }
     * 
     * 
     */
    public List<ContextArgumentType> getFunctionArguments() {
        if (functionArguments == null) {
            functionArguments = new ArrayList<ContextArgumentType>();
        }
        return this.functionArguments;
    }

    /**
     * Obtém o valor da propriedade functionId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFunctionId() {
        return functionId;
    }

    /**
     * Define o valor da propriedade functionId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFunctionId(Integer value) {
        this.functionId = value;
    }

    /**
     * Obtém o valor da propriedade functionValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionValue() {
        return functionValue;
    }

    /**
     * Define o valor da propriedade functionValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionValue(String value) {
        this.functionValue = value;
    }

    /**
     * Obtém o valor da propriedade ccId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCcId() {
        return ccId;
    }

    /**
     * Define o valor da propriedade ccId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCcId(Integer value) {
        this.ccId = value;
    }

    /**
     * Obtém o valor da propriedade cpId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCpId() {
        return cpId;
    }

    /**
     * Define o valor da propriedade cpId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCpId(Integer value) {
        this.cpId = value;
    }

}
