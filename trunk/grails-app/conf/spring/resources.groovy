import grails.util.GrailsUtil as GU

import org.apache.activemq.command.ActiveMQQueue
import org.hibernate.jmx.StatisticsService
import org.springframework.jms.core.JmsTemplate
import org.springframework.jmx.export.MBeanExporter
import org.springframework.jmx.support.MBeanServerFactoryBean
import org.springframework.jndi.JndiObjectFactoryBean
import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.apache.activemq.pool.PooledConnectionFactory
import org.springframework.core.io.ClassPathResource

def isProduction = GU.environment == "production"

beans = {

    shoppingCart(ShoppingCart) {bean ->
        bean.scope = "session"
    }

    exportFileResource(ClassPathResource, "java_pet_store_export.xml") {
    }

    if (isProduction) {
        // JMS bound to JNDI
        connectionFactory(JndiObjectFactoryBean) {
            jndiName = "java:/JmsXA"
        }
        coordinatesLookupQueue(JndiObjectFactoryBean) {
            jndiName = "queue/CoordinatesLookupQueue"
        }
    } else {
        // Standalone in-VM JMS 
        connectionFactory(PooledConnectionFactory, "vm://localhost") {
            maxConnections = 10
        }
        coordinatesLookupQueue(ActiveMQQueue, "coordinatesLookupQueue") {
        }
    }

    jmsTemplate(JmsTemplate) {
        connectionFactory = ref("connectionFactory")
        defaultDestination = ref("coordinatesLookupQueue")
    }

    jmsContainer(DefaultMessageListenerContainer) {
        connectionFactory = ref("connectionFactory")
        destination = ref("coordinatesLookupQueue")
        messageListener = ref("coordinatesLookupService")
        if (isProduction) {
            // JTA JMS transactions
            transactionManager = ref("transactionManager")
        } else {
            // Local JMS transactions
            sessionTransacted = true
        }
    }

    if (isProduction) {
        // Expose Hibernate statistics to JMX

        hibernateStats(StatisticsService) {
            statisticsEnabled = true
            sessionFactory = ref("sessionFactory")
        }

        mbeanServerFactory(MBeanServerFactoryBean) {
            locateExistingServerIfPossible = true
        }

        mbeanExporter(MBeanExporter) {
            server = mbeanServerFactory
            beans = ["org.hibernate:name=statistics": hibernateStats]
        }
    }

}