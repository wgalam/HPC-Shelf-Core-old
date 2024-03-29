//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2016.12.20 às 03:43:14 PM BRT 
//


package br.ufc.storm.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.ufc.storm.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ContextContract_QNAME = new QName("http://storm.lia.ufc.br", "context_contract");
    private final static QName _CandidateList_QNAME = new QName("http://storm.lia.ufc.br", "candidate_list");
    private final static QName _UnitFile_QNAME = new QName("http://storm.lia.ufc.br", "unit_file");
    private final static QName _ConcreteUnit_QNAME = new QName("http://storm.lia.ufc.br", "concrete_unit");
    private final static QName _ContractList_QNAME = new QName("http://storm.lia.ufc.br", "contract_list");
    private final static QName _AbstractComponent_QNAME = new QName("http://storm.lia.ufc.br", "abstract_component");
    private final static QName _ComputationalSystem_QNAME = new QName("http://storm.lia.ufc.br", "computational_system");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.ufc.storm.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ContextContract }
     * 
     */
    public ContextContract createContextContract() {
        return new ContextContract();
    }

    /**
     * Create an instance of {@link CandidateListType }
     * 
     */
    public CandidateListType createCandidateListType() {
        return new CandidateListType();
    }

    /**
     * Create an instance of {@link UnitFileType }
     * 
     */
    public UnitFileType createUnitFileType() {
        return new UnitFileType();
    }

    /**
     * Create an instance of {@link ConcreteUnitType }
     * 
     */
    public ConcreteUnitType createConcreteUnitType() {
        return new ConcreteUnitType();
    }

    /**
     * Create an instance of {@link ContractList }
     * 
     */
    public ContractList createContractList() {
        return new ContractList();
    }

    /**
     * Create an instance of {@link AbstractComponentType }
     * 
     */
    public AbstractComponentType createAbstractComponentType() {
        return new AbstractComponentType();
    }

    /**
     * Create an instance of {@link ComputationalSystemType }
     * 
     */
    public ComputationalSystemType createComputationalSystemType() {
        return new ComputationalSystemType();
    }

    /**
     * Create an instance of {@link AbstractUnitType }
     * 
     */
    public AbstractUnitType createAbstractUnitType() {
        return new AbstractUnitType();
    }

    /**
     * Create an instance of {@link CalculatedFunctionTermType }
     * 
     */
    public CalculatedFunctionTermType createCalculatedFunctionTermType() {
        return new CalculatedFunctionTermType();
    }

    /**
     * Create an instance of {@link PlatformProfileType }
     * 
     */
    public PlatformProfileType createPlatformProfileType() {
        return new PlatformProfileType();
    }

    /**
     * Create an instance of {@link CalculatedArgumentType }
     * 
     */
    public CalculatedArgumentType createCalculatedArgumentType() {
        return new CalculatedArgumentType();
    }

    /**
     * Create an instance of {@link CalculatedFunctionType }
     * 
     */
    public CalculatedFunctionType createCalculatedFunctionType() {
        return new CalculatedFunctionType();
    }

    /**
     * Create an instance of {@link ContextParameterType }
     * 
     */
    public ContextParameterType createContextParameterType() {
        return new ContextParameterType();
    }

    /**
     * Create an instance of {@link ContextArgumentValueType }
     * 
     */
    public ContextArgumentValueType createContextArgumentValueType() {
        return new ContextArgumentValueType();
    }

    /**
     * Create an instance of {@link CalculatedParameterType }
     * 
     */
    public CalculatedParameterType createCalculatedParameterType() {
        return new CalculatedParameterType();
    }

    /**
     * Create an instance of {@link SliceType }
     * 
     */
    public SliceType createSliceType() {
        return new SliceType();
    }

    /**
     * Create an instance of {@link ContextArgumentType }
     * 
     */
    public ContextArgumentType createContextArgumentType() {
        return new ContextArgumentType();
    }

    /**
     * Create an instance of {@link ConcreteComponentType }
     * 
     */
    public ConcreteComponentType createConcreteComponentType() {
        return new ConcreteComponentType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "context_contract")
    public JAXBElement<ContextContract> createContextContract(ContextContract value) {
        return new JAXBElement<ContextContract>(_ContextContract_QNAME, ContextContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CandidateListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "candidate_list")
    public JAXBElement<CandidateListType> createCandidateList(CandidateListType value) {
        return new JAXBElement<CandidateListType>(_CandidateList_QNAME, CandidateListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnitFileType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "unit_file")
    public JAXBElement<UnitFileType> createUnitFile(UnitFileType value) {
        return new JAXBElement<UnitFileType>(_UnitFile_QNAME, UnitFileType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConcreteUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "concrete_unit")
    public JAXBElement<ConcreteUnitType> createConcreteUnit(ConcreteUnitType value) {
        return new JAXBElement<ConcreteUnitType>(_ConcreteUnit_QNAME, ConcreteUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "contract_list")
    public JAXBElement<ContractList> createContractList(ContractList value) {
        return new JAXBElement<ContractList>(_ContractList_QNAME, ContractList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractComponentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "abstract_component")
    public JAXBElement<AbstractComponentType> createAbstractComponent(AbstractComponentType value) {
        return new JAXBElement<AbstractComponentType>(_AbstractComponent_QNAME, AbstractComponentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComputationalSystemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storm.lia.ufc.br", name = "computational_system")
    public JAXBElement<ComputationalSystemType> createComputationalSystem(ComputationalSystemType value) {
        return new JAXBElement<ComputationalSystemType>(_ComputationalSystem_QNAME, ComputationalSystemType.class, null, value);
    }

}
