/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.service;

import java.util.List;
import mii.client.app.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author MSI-JO
 */
@Service
public class CountryService {

    private RestTemplate restTemplate;

    @Value("${server.baseUrl}/country")
    private String url;

    @Autowired
    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Country> getAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cm9uaTpyb25p"); //roni BASE64
        return restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity(headers),
                        new ParameterizedTypeReference<List<Country>>() {
                        }).getBody();
    }

    public Country getById(Long id) {
        return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.GET, null,
                        new ParameterizedTypeReference<Country>() {
                        }).getBody();
    }

    public Country create(Country country) {
        return restTemplate
                .exchange(url, HttpMethod.POST, new HttpEntity(country),
                        new ParameterizedTypeReference<Country>() {
                        }).getBody();
    }

    public Country update(Long id, Country country) {
        return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.PUT, new HttpEntity(country),
                        new ParameterizedTypeReference<Country>() {
                        }).getBody();
    }

    public Country delete(Long id) {
        return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Country>() {
                        }).getBody();
    }
}
