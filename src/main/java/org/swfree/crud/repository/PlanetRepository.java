package org.swfree.crud.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.swfree.crud.model.Planet;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

    List<Planet> findByName(String name);
}
