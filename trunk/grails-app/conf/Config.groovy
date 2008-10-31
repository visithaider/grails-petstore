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
log4j {
    appender.stdout = "org.apache.log4j.ConsoleAppender"
    appender."stdout.layout"="org.apache.log4j.PatternLayout"
    appender."stdout.layout.ConversionPattern"="[%r] %c{2} %m%n"

    appender.errors = "org.apache.log4j.FileAppender"
    appender."errors.layout"="org.apache.log4j.PatternLayout"
    appender."errors.layout.ConversionPattern"="[%r] %c{2} %m%n"
    appender."errors.File"="${java.io.tmpdir}/stacktrace.log"

    rootLogger="error,stdout"
    logger {
        StackTrace="error,errors"
        grails="info"
        grails {
            app="debug"
        }
        org {
            springframework="info"
            hibernate="info"
            hibernate {
                //hbm2ddl="debug"
                //cache="debug"
                //SQL="debug"
            }
        }
    }
    additivity.StackTrace=false
}

// WAR dependency config
grails.war.dependencies = [
    "ant.jar",
    "ant-launcher.jar",
    "hibernate3.jar",
    "jdbc2_0-stdext.jar",
    //"jta.jar",
    "groovy-all-*.jar",
    "springmodules-sandbox.jar",
    "standard-${servletVersion}.jar",
    "jstl-${servletVersion}.jar",
    "antlr-*.jar",
    "cglib-*.jar",
    "dom4j-*.jar",
    //"ehcache-*.jar",
    //"junit-*.jar",
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
        jpsImport.maxItems = 10
    }
    production {
        
    }
}

/*
def deps = [
    "hibernate3.jar",
    "groovy-all-*.jar",
    "standard-${servletVersion}.jar",
    "jstl-${servletVersion}.jar",
    "oscache-*.jar",
    "commons-logging-*.jar",
    "sitemesh-*.jar",
    "spring-*.jar",
    "log4j-*.jar",
    "ognl-*.jar",
    "commons-*.jar",
    "xstream-1.2.1.jar",
    "xpp3_min-1.1.3.4.O.jar" ]

grails.war.dependencies = { fileset(dir: "libs") { deps.each { pattern -> include(name: pattern) } } }
*/

/*
commons-beanutils-1.7.0.jar
grails-crud-1.0.3.jar
log4j-1.2.15.jar
spring-2.5.4.jar
commons-collections-3.2.jar
grails-gorm-1.0.3.jar
lucene-analyzers.jar
spring-binding-2.0-m1.jar
commons-fileupload-1.1.1.jar
grails-spring-1.0.3.jar
lucene-core.jar
spring-webflow-2.0-m1.jar
commons-io-1.4.jar
grails-web-1.0.3.jar
lucene-highlighter.jar
spring-webmvc.jar
commons-lang-2.1.jar
grails-webflow-1.0.3.jar
lucene-queries.jar
standard-2.4.jar
commons-logging-1.1.jar
groovy-all-1.5.6.jar
lucene-snowball.jar
xpp3_min-1.1.3.4.O.jar
commons-pool-1.2.jar
jaxen-1.1-beta-11.jar
ognl-2.6.9.jar
xstream-1.2.1.jar
commons-validator-1.3.0.jar
jdbc2_0-stdext.jar
oro-2.0.8.jar
compass.jar
jdom.jar
rome-0.9.jar
grails-core-1.0.3.jar
jstl-2.4.jar
sitemesh-2.3.jar

 */

grails.views.gsp.encoding="UTF-8"
