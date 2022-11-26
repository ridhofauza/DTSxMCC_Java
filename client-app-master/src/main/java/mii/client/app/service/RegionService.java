/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.service;

import java.util.List;
import mii.client.app.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author MSI-JO
 */
@Service
public class RegionService {

    private RestTemplate restTemplate;

    @Value("${server.baseUrl}/region")
    private String url;

    @Autowired
    public RegionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Region> getAll() {
        return restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Region>>() {
                        }).getBody();
    }

    public Region getById(Long id) {
        return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.GET, null,
                        new ParameterizedTypeReference<Region>() {
                        }).getBody();
    }

    public Region create(Region region) {
        return restTemplate
                .exchange(url, HttpMethod.POST, new HttpEntity(region),
                        new ParameterizedTypeReference<Region>() {
                        }).getBody();
    }

    public Region update(Long id, Region region) {
        return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.PUT, new HttpEntity(region),
                        new ParameterizedTypeReference<Region>() {
                        }).getBody();
    }

    public Region delete(Long id) {
        ResponseEntity<Region> respon = restTemplate
                .exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Region>() {
                        });
        if (respon.getStatusCodeValue() == 200) {
            return respon.getBody();
        }
        return respon.getBody();
    }
}
