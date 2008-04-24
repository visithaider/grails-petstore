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
        dataSource {
			jndiName = "java:comp/env/jdbc/GrailsPetStoreDB"
            pooled = false
            dbCreate = "create-drop"
            //url = "jdbc:hsqldb:file:GrailsPetStoreDB"
        }
        hibernate {
            // TODO: Second level cache has problems, product names turn up as null 
            //cache.use_second_level_cache=true
            //cache.use_query_cache=true
            //cache.provider_class="org.hibernate.cache.EhCacheProvider"
            show_sql=false
            dialect="org.hibernate.dialect.MySQLInnoDBDialect"
        }
    }
}
