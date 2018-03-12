package org.swfree.crud.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.swfree.crud.controller.PlanetController;
import org.swfree.crud.model.Planet;
import org.swfree.crud.repository.PlanetRepository;
import org.swfree.crud.service.PlanetService;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class APITests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanetRepository planetRepository;

    @MockBean
    private PlanetService planetService;

    @Test
    public void testFindAll() throws Exception{
        Planet planet = new Planet();
        planet.setId("1");
        planet.setName("Alderaan");
        planet.setClimate("cold");
        planet.setTerrain("water");
        planet.setOccurrences(2);

        List<Planet> planets = singletonList(planet);

        given(planetRepository.findAll()).willReturn(planets);

        mvc.perform(get("/api/planets").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(planet.getId())))
                .andExpect(jsonPath("$[0].name", is(planet.getName())))
                .andExpect(jsonPath("$[0].climate", is(planet.getClimate())))
                .andExpect(jsonPath("$[0].terrain", is(planet.getTerrain())))
                .andExpect(jsonPath("$[0].occurrences", is(planet.getOccurrences())));
    }


    @Test
    public void testCreate() throws Exception {
        Planet planet = new Planet();
        planet.setName("Alderaan");
        planet.setClimate("cold");
        planet.setTerrain("water");

        Planet result = new Planet();
        BeanUtils.copyProperties(planet, result);

        result.setId("1");
        result.setOccurrences(2);

        given(planetService.getPlanetOccurrencesInMoviesByName(anyString())).willReturn(2);

        given(planetRepository.save(any())).willReturn(result);

        mvc.perform(post("/api/planets").content(asJsonString(planet)).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(result.getId())))
                .andExpect(jsonPath("$.climate", is(result.getClimate())))
                .andExpect(jsonPath("$.terrain", is(result.getTerrain())))
                .andExpect(jsonPath("$.occurrences", is(result.getOccurrences())))
                .andExpect(jsonPath("$.name", is(result.getName())));
    }

    @Test
    public void testSearchByName() throws Exception {
        Planet planet = new Planet();
        planet.setName("Alderaan");
        planet.setClimate("cold");
        planet.setTerrain("water");
        planet.setOccurrences(2);

        List<Planet> planets = singletonList(planet);

        given(planetRepository.findByName(anyString())).willReturn(planets);

        mvc.perform(get("/api/planets").param("name", planet.getName()).contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].climate", is(planet.getClimate())))
                .andExpect(jsonPath("$[0].terrain", is(planet.getTerrain())))
                .andExpect(jsonPath("$[0].occurrences", is(planet.getOccurrences())))
                .andExpect(jsonPath("$[0].name", is(planet.getName())));
    }

    @Test
    public void testFindById() throws Exception {
        Planet planet = new Planet();
        planet.setId("1");
        planet.setName("Alderaan");
        planet.setClimate("cold");
        planet.setTerrain("water");
        planet.setOccurrences(2);

        given(planetRepository.findById(any())).willReturn(Optional.of(planet));

        mvc.perform(get(String.format("/api/planets/%s", planet.getId())).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(planet.getId())))
                .andExpect(jsonPath("$.climate", is(planet.getClimate())))
                .andExpect(jsonPath("$.terrain", is(planet.getTerrain())))
                .andExpect(jsonPath("$.occurrences", is(planet.getOccurrences())))
                .andExpect(jsonPath("$.name", is(planet.getName())));

        given(planetRepository.findById(any())).willReturn(Optional.empty());

        mvc.perform(get(String.format("/api/planets/%s", planet.getId())).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteById() throws Exception {

        Planet planet = new Planet();
        planet.setId("1");
        planet.setName("Alderaan");
        planet.setClimate("cold");
        planet.setTerrain("water");
        planet.setOccurrences(2);

        given(planetRepository.findById(anyString())).willReturn(Optional.of(planet));

        mvc.perform(delete(String.format("/api/planets/%s", planet.getId())))
                .andDo(print())
                .andExpect(status().isNoContent());


        given(planetRepository.findById(anyString())).willReturn(Optional.empty());

        mvc.perform(delete(String.format("/api/planets/%s", planet.getId())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAll() throws Exception {

        mvc.perform(delete("/api/planets"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}