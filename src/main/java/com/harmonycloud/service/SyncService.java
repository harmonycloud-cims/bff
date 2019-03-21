package com.harmonycloud.service;

import com.harmonycloud.result.CimsResponseWrapper;
import com.harmonycloud.util.TraceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Service
public class SyncService {


    @Autowired
    private RestProxyTemplate restProxyTemplate;

    @Autowired
    private HttpServletRequest requests;

    public CimsResponseWrapper save(URI uri, String token, Object body) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (requests.getHeader("user") != null) {
                headers.set("user", requests.getHeader("user"));
            }
            if (requests.getHeader("clinic") != null) {
                headers.set("clinic", requests.getHeader("clinic"));
            }
            headers.set("Authorization", "Bearer " + token);
            TraceUtil.addTraceForHttp(requests, headers);
            HttpEntity<Object> request = new HttpEntity<>(body, headers);
            ResponseEntity<CimsResponseWrapper> response = restProxyTemplate.getRestTemplate().postForEntity(uri, request, CimsResponseWrapper.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw e;
        }
    }
}
