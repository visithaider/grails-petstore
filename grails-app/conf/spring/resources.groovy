beans = {

    shoppingCart(ShoppingCart) { bean ->
        bean.scope="session"
    }

    /*
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
    */
}