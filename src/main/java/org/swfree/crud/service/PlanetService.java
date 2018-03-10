
package org.swfree.crud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.swfree.crud.dto.PlanetResult;

@Service
public class PlanetService {

    private RestTemplate restTemplate;

    @Autowired
    public PlanetService() {
        this.restTemplate = new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "getDefaultPlanetOccurrencesInMoviesByName",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
        }
    )
    public Integer getPlanetOccurrencesInMoviesByName(String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "swfree");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        PlanetResult result = restTemplate.exchange(UriComponentsBuilder.newInstance()
                        .scheme("https")
                        .host("swapi.co")
                        .path("api/planets/")
                        .queryParam("search", name)
                        .queryParam("format ", "json")
                        .build().toUri(),
                HttpMethod.GET,
                entity,
                PlanetResult.class
        ).getBody();

        if (result.getCount() == 1) {
            return result.getResults().get(0).getFilms().size();
        } else {
            return 0;
        }
    }

    public Integer getDefaultPlanetOccurrencesInMoviesByName(String name) {
        return 0;
    }
}