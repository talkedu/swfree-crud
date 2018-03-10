package org.swfree.crud.dto;

import java.util.List;

public class PlanetResult {

    private int count;
    private List<PlanetDTO> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PlanetDTO> getResults() {
        return results;
    }

    public void setResults(List<PlanetDTO> results) {
        this.results = results;
    }
}
