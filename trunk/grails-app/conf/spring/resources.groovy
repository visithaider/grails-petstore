import grails.util.GrailsUtil as GU

import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.command.ActiveMQQueue
import org.hibernate.jmx.StatisticsService
import org.springframework.jms.core.JmsTemplate
import org.springframework.jmx.export.MBeanExporter
import org.springframework.jmx.support.MBeanServerFactoryBean
import org.springframework.jndi.JndiObjectFactoryBean
import org.springframework.jms.listener.DefaultMessageListenerContainer

beans = {

    switch(GU.environment) {
		case "production":
            connectionFactory(JndiObjectFactoryBean) {
                jndiUrl = "java:/JmsXA"
            }
            coordinatesLookupQueue() {
                jndiUrl = "queue/CoordinatesLookupQueue"
            }
            break
        case "development":
        case "test":
            connectionFactory(ActiveMQConnectionFactory) {
                brokerURL = 'vm://localhost'
            }
            coordinatesLookupQueue(ActiveMQQueue, "coordinatesLookupQueue") {
            }
            break
    }

    shoppingCart(ShoppingCart) { bean ->
        bean.scope = "session"
    }

    jmsTemplate(JmsTemplate) {
        connectionFactory = ref("connectionFactory")
        defaultDestination = ref("coordinatesLookupQueue")
    }

	jmsContainer(DefaultMessageListenerContainer) {
		connectionFactory = ref("connectionFactory")
		destination = ref("coordinatesLookupQueue")
		messageListener = ref("coordinatesLookupService")
        //sessionTransacted = true
        //transactionManager = ref("transactionManager")
    }

    // Expose Hibernate statistics to JMX

    hibernateStats(StatisticsService) {
		statisticsEnabled=true
		sessionFactory=ref("sessionFactory")
	}

    mbeanServerFactory(MBeanServerFactoryBean) {
        locateExistingServerIfPossible=true
    }

    mbeanExporter(MBeanExporter) {
        server = mbeanServerFactory
        beans = ["org.hibernate:name=statistics":hibernateStats]
    }

}