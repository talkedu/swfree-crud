package org.swfree.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.swfree.crud.exception.PlanetNotFoundException;
import org.swfree.crud.model.Planet;
import org.swfree.crud.repository.PlanetRepository;
import org.swfree.crud.service.PlanetService;

import java.util.Optional;

@RestController
@RequestMapping("api/planets")
public class PlanetController {

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    PlanetService planetService;

    @GetMapping
    public Iterable<Planet> findAll() {
        return planetRepository.findAll();
    }

    @GetMapping("/{id}")
    public Planet findById(@PathVariable String id) throws Exception {
        Optional<Planet> optional = planetRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new PlanetNotFoundException();
        }
    }

    @GetMapping(params = {"name"})
    public Iterable<Planet> findByName(String name) throws Exception {
        return planetRepository.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        planet.setOccurrences(planetService.getPlanetOccurrencesInMoviesByName(planet.getName()));
        planet = planetRepository.save(planet);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("Location", String.format("/api/planets/%s", planet.getId()));

        return new ResponseEntity<>(planet, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Planet> optional = planetRepository.findById(id);
        if(optional.isPresent()) {
            planetRepository.delete(optional.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public void deleteAll() {
        planetRepository.deleteAll();
    }

}
