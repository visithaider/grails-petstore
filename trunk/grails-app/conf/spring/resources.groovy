import org.hibernate.jmx.StatisticsService
import org.springframework.jmx.export.MBeanExporter
import org.springframework.jmx.support.MBeanServerFactoryBean

beans = {
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