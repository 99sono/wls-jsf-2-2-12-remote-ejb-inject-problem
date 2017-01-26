package web;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ejb.DummyBaseFacade;
import ejb.DummyBaseFacadeLocal;
import service.BasicService;

/**
 * The controller for the AutoStore Picking screen.
 */
@RequestScoped
@Named("dummyWebBean")
public class DummyWebBean extends AbstractWebBean {
    private static final Logger LOGGER = Logger.getLogger(DummyWebBean.class.getCanonicalName());

    final String controllerName = "Timer Controller";

    // REMOTE EJB Injection
    /**
     * This field will only be injected if the child class does not redeclare it.
     */
    @EJB
    DummyBaseFacade dummyBaseFacade;
    @Inject
    DummyBaseFacadeLocal dummyBaseFacadeLocal;
    @Inject
    BasicService basicService;

    @Override
    @PostConstruct
    public void postConstruct() {
        LOGGER.info("DummyWebBean -  bean constructed. ");
        // logStateOfInjectionOfCollaborators();
    }

    @Override
    public void logStateOfInjectionOfCollaborators() {
        LOGGER.info(
                // (a) Localise that the print is coming out of the abstract class
                DummyWebBean.class.getCanonicalName() + " ---- "
                // (b) display the injection point state
                        + "INJECTION STATE IS:\n" + getCollaboratorsMap());
    }

    private Map<String, String> getCollaboratorsMap() {
        Map<String, String> collanboratorsToStr = new TreeMap<>();

        // Injection behavior in weblogic for Remote EJBs
        collanboratorsToStr.put("\n" + "dummyBaseFacade", safeToTring(dummyBaseFacade));

        // Injection behavior in weblogic for Local EJBs
        collanboratorsToStr.put("\n" + "dummyBaseFacadeLocal", safeToTring(dummyBaseFacadeLocal));

        // Injection behavior forNormalWebBeans
        collanboratorsToStr.put("\n" + "basicService", safeToTring(basicService));

        return collanboratorsToStr;
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        LOGGER.info("AbstractWebBean - Request scoped bean destroyed. ");
    }

    /**
     * Log to the app server server log the state of injection of the web bean. We expect to see that weblogic has not
     * done proper injection of the remote EJB injection points in the parent class. While all other fields are properly
     * injected.
     *
     * @return null - the action command is not expected to make the super travel to any new view
     *
     */
    public String locCollaboratorsFromImplAndAbstractClass() {
        // (a) log the collaborators in this concrete class
        LOGGER.info("\n----------------------------------------------------------------");
        LOGGER.info("FIRST - We log the injection state of collaborators in the implementation class:");
        this.logStateOfInjectionOfCollaborators();
        LOGGER.info("\n\n\n\n\n\n");

        // (b) log the collaborators in the parent class
        LOGGER.info("SECOND - We log the injection state of collaborators in the parent class:");
        super.logStateOfInjectionOfCollaborators();

        // (c) this is a JSF action command that we do not want to pront any view navigation
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Please check the server log. We should have logged the state of injection"));
        return null;
    }

}