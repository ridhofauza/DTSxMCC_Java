/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.util;

import java.nio.charset.Charset;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author MSI-JO
 */
public class BasicHeader {

    public static String createBasicToken(String username, String password) {
        String auth = username + ":" + password; // roni:roni
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }
    
    public static HttpHeaders createHeader(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = createBasicToken(auth.getPrincipal().toString(), auth.getCredentials().toString());
        return new HttpHeaders(){{
            set("Authorization", "Basic " + token);
        }};
    } 
}
