package org.swfree.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.swfree.crud.exception.PlanetNotFoundException;
import org.swfree.crud.model.Planet;
import org.swfree.crud.repository.PlanetRepository;
import org.swfree.crud.service.PlanetService;

import java.util.Optional;

@RestController
@RequestMapping("api/planet")
public class PlanetController {

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    PlanetService planetService;

    @GetMapping("/{id}")
    public Planet findById(@PathVariable String id) throws Exception {
        Optional<Planet> optional = planetRepository.findById(id);
        if(optional.isPresent()) {
            return planetRepository.findById(id).get();
        } else {
            throw new PlanetNotFoundException();
        }
    }

    @GetMapping
    public Iterable<Planet> findAll() {
        return planetRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody Planet planet) {
        planet.setOccurrences(planetService.getPlanetOccurrencesInMoviesByName(planet.getName()));
        planetRepository.save(planet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        planetRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        planetRepository.deleteAll();
    }

}
