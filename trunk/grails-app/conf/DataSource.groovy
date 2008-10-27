dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=false
    cache.use_query_cache=false
    cache.provider_class="org.hibernate.cache.NoCacheProvider"
    show_sql=false
    format_sql=true
}

environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:hsqldb:mem:devDB"
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
    production {
        // JBossAS - JNDI data source, JTA transactions, MySQL database
        dataSource {
			jndiName = "java:/MySqlDS"
            dbCreate = "create-drop"
            pooled = false
        }
        hibernate {
            hibernate.transaction.manager_lookup_class="org.hibernate.transaction.JBossTransactionManagerLookup"
            hibernate.transaction.factory_class="org.hibernate.transaction.JTATransactionFactory"

            jta.UserTransaction="UserTransaction"

            cache.use_second_level_cache=true
            cache.provider_class="org.jboss.hibernate.cache.DeployedTreeCacheProvider"

            dialect="org.hibernate.dialect.MySQLInnoDBDialect"
            show_sql=false
        }
    }
}
