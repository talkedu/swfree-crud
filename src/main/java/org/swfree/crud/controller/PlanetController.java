package org.swfree.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swfree.crud.model.Planet;
import org.swfree.crud.repository.PlanetRepository;
import org.swfree.crud.service.PlanetService;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/planets")
public class PlanetController {

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    PlanetService planetService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Planet> findAll() {
        return planetRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> findById(@PathVariable("id") String id) {
        Optional<Planet> optional = planetRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Planet> findByName(@RequestParam("name") String name) {
        return planetRepository.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        planet.setOccurrences(planetService.getPlanetOccurrencesInMoviesByName(planet.getName()));
        planet = planetRepository.save(planet);

        return ResponseEntity
                .created(URI.create(String.format("/api/planets/%s", planet.getId())))
                .body(planet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        Optional<Planet> optional = planetRepository.findById(id);
        if (optional.isPresent()) {
            planetRepository.delete(optional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        planetRepository.deleteAll();
    }

}
