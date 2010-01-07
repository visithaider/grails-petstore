grails.mime.file.extensions = true
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text-plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true


// log4j configuration

log4j = {
  root {
      error()
      additivity = true
  }
  debug 'grails.app'
}


// WAR dependency config
grails.war.dependencies = [
    "ant.jar",
    "ant-launcher.jar",
    "hibernate3.jar",
    "jdbc2_0-stdext.jar",
    "jta.jar",
    "groovy-all-*.jar",
    "springmodules-sandbox.jar",
    "standard-${servletVersion}.jar",
    "jstl-${servletVersion}.jar",
    "antlr-*.jar",
    "cglib-*.jar",
    "dom4j-*.jar",
    "ehcache-*.jar",
    "backport-util-concurrent-3.0.jar",
    "commons-logging-*.jar",
    "sitemesh-*.jar",
    "spring-*.jar",
    "log4j-*.jar",
    "ognl-*.jar",
    "hsqldb-*.jar",
    "commons-lang-*.jar",
    "commons-collections-*.jar",
    "commons-beanutils-*.jar",
    "commons-pool-*.jar",
    "commons-dbcp-*.jar",
    "commons-cli-*.jar",
    "commons-validator-*.jar",
    "commons-fileupload-*.jar",
    "commons-io-*.jar",
    "*oro-*.jar",
    "jaxen-*.jar",
    "xstream-1.2.1.jar",
    "xpp3_min-1.1.3.4.O.jar"
]

grails.war.java5.dependencies = [
    "hibernate-annotations.jar",
    "ejb3-persistence.jar",
]

environments {
    development {
    }
    production {
    }
}

grails.views.gsp.encoding="UTF-8"
