/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.util;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author MSI-JO
 */
public class RequestInterceptor implements  ClientHttpRequestInterceptor{

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
         
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (!request.getURI().getPath().equals("/login")) {
            request.getHeaders().add("Authorization", "Basic " + 
                    BasicHeader.createBasicToken(authentication.getPrincipal().toString(),
                            authentication.getCredentials().toString()));
        }
        
        ClientHttpResponse response = execution.execute(request, body);
        
        return response;
        
    }
    
}
