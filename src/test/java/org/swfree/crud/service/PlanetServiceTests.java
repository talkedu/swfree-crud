package org.swfree.crud.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTests {

	@Autowired
	private PlanetService planetService;

	@Test
	public void testPlanetOccurences() {

		long alderaanOccurrences = planetService.getPlanetOccurrencesInMoviesByName("Alderaan");
		assertEquals(2l, alderaanOccurrences);

		long coruscantOccurrences = planetService.getPlanetOccurrencesInMoviesByName("Coruscant");
		assertEquals(4l, coruscantOccurrences);

		long dagobahOccurrences = planetService.getPlanetOccurrencesInMoviesByName("Dagobah");
		assertEquals(3l, dagobahOccurrences);

		long earthOccurrences = planetService.getPlanetOccurrencesInMoviesByName("Earth");
		assertEquals(0l, earthOccurrences);

	}

}
