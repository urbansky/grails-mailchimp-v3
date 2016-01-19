
grails.config.locations = []
if ((new File("${userHome}/${appName}-config.groovy")).exists()) {
  println "Including configuration file found in home directory: ${userHome}/${appName}-config.groovy"
  grails.config.locations << "file:${userHome}/${appName}-config.groovy"
}
if (System.properties["${appName}.config.location"]) {
  println "Including configuration file specified as system property: " + System.properties["${appName}.config.location"]
  grails.config.locations << "file:" + System.properties["${appName}.config.location"]
}
if (System.getenv("${appName}.config.location") && (new File(System.getenv("${appName}.config.location"))).exists()) {
  println "Including configuration file specified in environment: " + System.getenv("${appName}.config.location")
  grails.config.locations << "file:" + System.getenv("${appName}.config.location")
}


// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}
