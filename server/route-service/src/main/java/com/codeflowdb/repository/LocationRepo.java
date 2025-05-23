package com.codeflowdb.repository;

import com.codeflowdb.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);
    List<Location> findByNameContainingIgnoreCase(String name);
}
