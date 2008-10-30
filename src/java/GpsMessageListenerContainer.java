import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Session;

public class GpsMessageListenerContainer extends DefaultMessageListenerContainer {

    protected boolean isSessionLocallyTransacted(Session session) {
        boolean isit = super.isSessionLocallyTransacted(session);
        System.err.println("*** Locally transacted: " + isit);
        return isit;
    }
}
