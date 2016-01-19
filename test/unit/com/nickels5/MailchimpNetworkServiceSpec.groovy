package com.nickels5

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MailchimpNetworkService)
@TestMixin(GrailsUnitTestMixin)
class MailchimpNetworkServiceSpec extends Specification {

  MailchimpNetworkService mailchimpNetworkService

  def setup() {
    mailchimpNetworkService = new MailchimpNetworkService()
    mailchimpNetworkService.grailsApplication = new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
  }

  def cleanup() {
  }

  void "Check configuration"() {
    expect:
    mailchimpNetworkService.grailsApplication.config.mailchimp.apiUrl
    mailchimpNetworkService.grailsApplication.config.mailchimp.apiKey
  }

  void "List newsletter"() {
    when:
    def result = mailchimpNetworkService.get("lists")

    then:
    result.status == 200
  }

  void "Create newsletter"() {
  }

  void "Update newsletter"() {
  }

  void "Add member to newsletter"() {
  }

  void "Remove member from newsletter"() {
  }

  void "Remove newsletter"() {
  }

}
