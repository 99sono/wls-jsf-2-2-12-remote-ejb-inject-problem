package ejb;

import javax.ejb.Stateless;

/**
 * Stalless EJB expose a remote EJB business interface. This EJB, in weblogic will only be injtected into the child
 * class of a class hierarchy. Weblogic will leave the parent classes with the field - not injected leading to NULL
 * pointer exceptions.
 */
@Stateless
public class DummyBaseFacadeImpl implements DummyBaseFacade, DummyBaseFacadeLocal {

    @Override
    public String doWork() {
        return null;
    }

}