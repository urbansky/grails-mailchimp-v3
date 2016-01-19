Grails Mailchimp V3 Plugin
=======================
This is a simple wrapper for the Mailchimp REST API Version 3
 
 
Configuration
------

In your Config.groovy you need the following lines:

```groovy
mailchimp.apiUrl = 'YOUR API endpoint' // e.g. 'https://us8.api.mailchimp.com/3.0/' but this depends on which datacentre your API key is valid for
mailchimp.apiKey = 'YOUR API KEY'
```

Examples
-------
    
```groovy
log.info "lists"
def result = mailchimpNetworkService.get("lists")
result.output.lists.each {
  log.info it.name
}

log.info "automations"
result = mailchimpNetworkService.get("automations")
result.output.automations.each {
  log.info it.settings.title
}

Map data = [
  "name":"Freddie'\''s Favorite Hats",
  "contact": ["company":"MailChimp","address1":"675 Ponce De Leon Ave NE","address2":"Suite 5000","city":"Atlanta","state":"GA","zip":"30308","country":"US","phone":""],
  "permission_reminder":"You'\''re receiving this email because you signed up for updates about Freddie'\''s newest hats.",
  "campaign_defaults": ["from_name":"Freddie","from_email":"freddie@freddiehats.com","subject":"","language":"en"],
  "email_type_option":true
]
log.info "create list"
result = mailchimpNetworkService.post("lists", data)
log.info JsonOutput.prettyPrint(result.output.toString())
```

References
------

 * [MailChimp API 3.0 Reference](http://developer.mailchimp.com/documentation/mailchimp/reference/overview/)
 * [Grails Mailchimp Plugin for API 1.3](https://github.com/happyinc/grails-mailchimp)
