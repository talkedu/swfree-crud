package org.swfree.crud.dto;

import org.swfree.crud.model.Planet;

import java.util.List;

public class PlanetDTO extends Planet {

    private List<String> films;

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }
}
