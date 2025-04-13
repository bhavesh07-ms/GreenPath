package com.codeflowdb.repository;

import com.codeflowdb.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);
}
