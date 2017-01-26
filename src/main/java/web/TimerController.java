package web;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

/**
 * The controller for the AutoStore Picking screen.
 */
@RequestScoped
@Named("timerController")
public class TimerController {
    private static final Logger LOGGER = Logger.getLogger(TimerController.class.getCanonicalName());

    final String controllerName = "Timer Controller";

    String inputA;
    String inputB;
    String inputC;
    String selectOneButtonValue;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("timerController - Request scoped bean constructed. ");

    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("timerController - Request scoped bean destroyed. ");
    }

    public String getInputA() {
        return inputA;
    }

    public void setInputA(String inputA) {
        this.inputA = inputA;
    }

    public String getInputB() {
        return inputB;
    }

    public void setInputB(String inputB) {
        this.inputB = inputB;
    }

    public String getInputC() {
        return inputC;
    }

    public void setInputC(String inputC) {
        this.inputC = inputC;
    }

    public void nop() {
        LOGGER.info("NOP invoked");
    }

    // open close dialog
    public void openDialog() {
        String javascriptCommand = buildJavascriptOpenDialog("dummyDialogPopup");
        RequestContext.getCurrentInstance().execute(javascriptCommand);
    }

    public void closeDialog() {
        String javascriptCommand = buildJavascriptHideDialog("dummyDialogPopup");
        RequestContext.getCurrentInstance().execute(javascriptCommand);
    }

    public String getSelectOneButtonValue() {
        return selectOneButtonValue;
    }

    public void setSelectOneButtonValue(String selectOneButtonValue) {
        this.selectOneButtonValue = selectOneButtonValue;
    }

    public String buildJavascriptOpenDialog(String widgetDialogName) {
        return String.format("PF('%1$s').show();", widgetDialogName);
    }

    public String buildJavascriptHideDialog(String widgetDialogName) {
        return String.format("PF('%1$s').hide();", widgetDialogName);
    }

}