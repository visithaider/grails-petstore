package gps

import org.apache.activemq.command.ActiveMQTextMessage

class CoordinatesLookupServiceTests extends GroovyTestCase {

    CoordinatesLookupService service

    void testOnMessage() {
        service = new CoordinatesLookupService()
        def message = new ActiveMQTextMessage()
        message.setText "1"

        //service.onMessage message
    }
    
}
