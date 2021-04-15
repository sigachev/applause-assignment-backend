package com.applause.repositories;

import com.applause.model.Bug;
import com.applause.model.search.SearchResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugRepository extends CrudRepository<Bug, Long> {

    Optional<List<Bug>> findAllByDevice_Id(Long deviceId);

    Optional<List<Bug>> findAllByTester_Id(Long testerId);

    Optional<List<Bug>> findAllByTester_IdAndDevice_Id(Long deviceId, Long testerId);

}
