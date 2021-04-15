package com.applause.repositories;

import com.applause.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {

   Optional<List<Device>> findAllByIdIn(List<Long> idList);
   Optional<List<Device>> findAllByIdIsIn(List<Long> idList);
}
