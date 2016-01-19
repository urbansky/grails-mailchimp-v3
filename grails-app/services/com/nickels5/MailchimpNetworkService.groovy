package com.nickels5

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter
import grails.converters.JSON
import groovy.json.JsonBuilder

/**
 * Low level network connection to the Mailchimp REST-API V3
 */
class MailchimpNetworkService {

  def grailsApplication

  public Map get(String endpoint) {
    execute {
      getWebResource(endpoint).accept("application/json").get(ClientResponse.class)
    }
  }

  public Map post(String endpoint, def data) {
    execute {
      getWebResource(endpoint).accept("application/json").post(ClientResponse.class, new JsonBuilder(data).toString())
    }
  }

  public Map patch(String endpoint, def data) {
    execute {
      getWebResource(endpoint).accept("application/json")
              .header("X-HTTP-Method-Override", "PATCH")
              .post(ClientResponse.class, new JsonBuilder(data).toString())
    }
  }

  public Map delete(String endpoint) {
    execute {
      getWebResource(endpoint).accept("application/json").delete(ClientResponse.class)
    }
  }

  private Map execute(Closure getResponse) {
    Map result = [:]
    try {
      parseResponse(getResponse(), result)
    } catch (Exception e) {
      log.error("Error while communicate with mailchimp", e)
      result.exception = e
    }
    return result
  }

  private WebResource getWebResource(String endpoint) {
    Client client = Client.create()
    client.addFilter(new HTTPBasicAuthFilter("anystring", grailsApplication.config.mailchimp.apiKey))
    return client.resource(grailsApplication.config.mailchimp.apiUrl +  endpoint)
  }

  private void parseResponse(ClientResponse response, Map result) {
    result.status = response.getStatus()

    // 204 (NO-CONTENT) with HTTP_DELETE is OK and not an error
    // But 'response.getEntity' will throw an exception with 204
    if (result.status != 204) {
      result.output = JSON.parse(response.getEntity(String.class))
    }
  }

}
