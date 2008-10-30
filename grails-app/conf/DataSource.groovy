dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache {
        use_second_level_cache=true
        use_query_cache=true
        provider_class="org.hibernate.cache.EhCacheProvider"
    }
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
            generate_statistics=true

            // JTA transactions
            transaction {
                manager_lookup_class="org.hibernate.transaction.JBossTransactionManagerLookup"
                factory_class="org.hibernate.transaction.JTATransactionFactory"
            }
            jta.UserTransaction="UserTransaction"

            // JMX-bound TreeCache
            cache {
                use_second_level_cache=true
                use_query_cache=true
                provider_class="org.jboss.hibernate.jbc.cacheprovider.JmxBoundTreeCacheProvider"
            }
            treecache.mbean.object_name="jboss.cache:service=EJB3EntityTreeCache"

            dialect="org.hibernate.dialect.MySQLInnoDBDialect"
        }
    }
}
