package com.cloudcredo.microservices.training.app.feedback.api;

import com.cloudcredo.microservices.training.app.feedback.requestrecorder.RequestRecorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestRecordingFilter extends OncePerRequestFilter {

  private RequestRecorderService requestRecorderService;

  @Autowired
  public RequestRecordingFilter(RequestRecorderService requestRecorderService) {
    this.requestRecorderService = requestRecorderService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    requestRecorderService.recordAPICall(request.getServletPath(), request.getMethod());
    filterChain.doFilter(request, response);
  }
}
