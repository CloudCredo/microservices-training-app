package com.cloudcredo.microservices.training.app.feedback.api

import com.cloudcredo.microservices.training.app.feedback.requestrecorder.RequestRecorderService
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.FilterChain

class RequestRecordingFilterTest extends Specification {

  def requestRecorderService = Mock(RequestRecorderService)
  def filter = new RequestRecordingFilter(requestRecorderService)

  def "DoFilterInternal"() {
    given:
    def request = new MockHttpServletRequest('POST', 'http://localhost')
    def response = new MockHttpServletResponse()
    def filterChain = Mock(FilterChain)
    request.setServletPath('/a/path')

    when:
    filter.doFilterInternal(request, response, filterChain)

    then:
    1 * requestRecorderService.recordAPICall('/a/path', 'POST')
    1 * filterChain.doFilter(request, response)
  }
}
