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

  public static String TEST_LIST = "Grail plugin test list"
  public static Map TEST_LIST_DATA = [
          "name": TEST_LIST,
          "contact": ["company":"MailChimp","address1":"675 Ponce De Leon Ave NE","address2":"Suite 5000","city":"Atlanta","state":"GA","zip":"30308","country":"US","phone":""],
          "permission_reminder":"You'\''re receiving this email because you signed up for updates about Freddie'\''s newest hats.",
          "campaign_defaults": ["from_name":"Freddie","from_email":"freddie@freddiehats.com","subject":"","language":"en"],
          "email_type_option":true
  ]
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
    when:
    def result = mailchimpNetworkService.post("lists", TEST_LIST_DATA)

    then:
    result.status == 200
  }

  void "Update newsletter"() {
    when: "change the newsletter name"
    Map updateData = TEST_LIST_DATA.clone()
    updateData.name = TEST_LIST + "changed"
    def listId = getListId(TEST_LIST)
    def result = mailchimpNetworkService.patch("lists/${listId}", updateData)

    then:
    result.status == 200
    getListId(TEST_LIST + "changed") == listId

    cleanup: "reset newsletter name"
    mailchimpNetworkService.patch("lists/${listId}", TEST_LIST_DATA)
  }

  void "Add member to newsletter"() {
  }

  void "Remove member from newsletter"() {
  }

  void "Remove newsletter"() {
    when:
    def listId = getListId(TEST_LIST)
    def result = mailchimpNetworkService.delete("lists/${listId}")

    then:
    result.status == 204
  }

  private String getListId(String listName) {
    def result = mailchimpNetworkService.get("lists")
    result.output.lists.findResult { it.name == listName ? it.id : null }
  }
}
