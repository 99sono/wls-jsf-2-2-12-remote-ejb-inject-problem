package web;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Inject;

import ejb.DummyBaseFacade;
import ejb.DummyBaseFacadeLocal;
import service.BasicService;

/**
 * The controller for the AutoStore Picking screen.
 */
public class AbstractWebBean {
    private static final Logger LOGGER = Logger.getLogger(AbstractWebBean.class.getCanonicalName());

    // REMOTE EJB Injection
    /**
     * This field will only be injected if the child class does not redeclare it.
     */
    @EJB
    DummyBaseFacade dummyBaseFacade;

    @EJB
    DummyBaseFacade dummyBaseFacadeNameThatWillNeverExistInChildClass;

    @Inject
    DummyBaseFacadeLocal dummyBaseFacadeLocal;

    @Inject
    DummyBaseFacadeLocal dummyBaseFacadeLocalNameThatWillNeverExistInChildClass;

    @Inject
    BasicService basicService;

    @Inject
    BasicService basicServiceNameThatWillNeverExistInChildClass;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("AbstractWebBean -  bean constructed. ");
        // logStateOfInjectionOfCollaborators();
    }

    public void logStateOfInjectionOfCollaborators() {
        LOGGER.info(
                // (a) Localise that the print is coming out of the abstract class
                AbstractWebBean.class.getCanonicalName() + " ---- "
                // (b) display the injection point state
                        + "INJECTION STATE IS:\n" + this.getCollaboratorsMap());
    }

    private Map<String, String> getCollaboratorsMap() {
        Map<String, String> collanboratorsToStr = new TreeMap<>();

        // Injection behavior in weblogic for Remote EJBs
        collanboratorsToStr.put("\n" + "dummyBaseFacade", safeToTring(dummyBaseFacade));
        collanboratorsToStr.put("\n" + "dummyBaseFacadeNameThatWillNeverExistInChildClass",
                safeToTring(dummyBaseFacadeNameThatWillNeverExistInChildClass));

        // Injection behavior in weblogic for Local EJBs
        collanboratorsToStr.put("\n" + "dummyBaseFacadeLocal", safeToTring(dummyBaseFacadeLocal));
        collanboratorsToStr.put("\n" + "dummyBaseFacadeLocalNameThatWillNeverExistInChildClass",
                safeToTring(dummyBaseFacadeLocalNameThatWillNeverExistInChildClass));

        // Injection behavior forNormalWebBeans
        collanboratorsToStr.put("\n" + "basicService", safeToTring(basicService));
        collanboratorsToStr.put("\n" + "basicServiceNameThatWillNeverExistInChildClass",
                safeToTring(basicServiceNameThatWillNeverExistInChildClass));
        return collanboratorsToStr;
    }

    /**
     * Get the toString of an arbitrary object
     */
    protected String safeToTring(Object object) {
        return object == null ? "null" : object.toString();
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("AbstractWebBean - Request scoped bean destroyed. ");
    }

}