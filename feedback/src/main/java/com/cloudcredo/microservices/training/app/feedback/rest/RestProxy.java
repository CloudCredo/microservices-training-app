package com.cloudcredo.microservices.training.app.feedback.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RestProxy {

    private final RestTemplate restTemplate;

    private final URI serviceUrl;

    public RestProxy(URI serviceUrl, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    public ResponseEntity<String> proxy(RequestEntity<String> inboundRequest) {
        URI outboundRequestUrl = serviceUrl.resolve(inboundRequest.getUrl().getPath());


        RequestEntity<String> outboundRequest = RequestEntity.method(inboundRequest.getMethod(), outboundRequestUrl)
                .contentType(contentType(inboundRequest.getHeaders()))
                .body(inboundRequest.getBody());

        ResponseEntity<String> response = restTemplate.exchange(outboundRequest, String.class);

        return ResponseEntity.status(response.getStatusCode())
                .contentType(contentType(response.getHeaders()))
                .body(response.getBody());
    }

    private static MediaType contentType(HttpHeaders headers) {
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType : MediaType.APPLICATION_JSON;
    }
}
