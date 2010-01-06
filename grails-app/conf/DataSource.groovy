dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
    dbCreate = "create-drop"
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
			url = "jdbc:hsqldb:mem:devDB"
		}
	}
	test {
		dataSource {
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
    production {
        // Glassfish v3 default settings
        dataSource {
            jndiName = "jdbc/__default"
        }
        hibernate {
            dialect="org.hibernate.dialect.DerbyDialect"
        }
    }
}
