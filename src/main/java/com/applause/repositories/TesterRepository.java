package com.applause.repositories;

import com.applause.model.Tester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TesterRepository extends CrudRepository<Tester, Long> {

    Optional<Tester> findByCountry(String country);

    Optional<List<Tester>> findAllByCountry(String country);
    Optional<List<Tester>> findAllByCountryIn(List<String> countries);
    Optional<List<Tester>> findAllByCountryIsIn(List<String> countries);


}
