package ejb;

import javax.ejb.Remote;

/**
 * Some dummy remote facade business api.
 */
@Remote
public interface DummyBaseFacade {

    String doWork();
}