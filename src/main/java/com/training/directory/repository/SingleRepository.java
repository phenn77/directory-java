package com.training.directory.repository;

import com.training.directory.model.Single;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleRepository extends JpaRepository<Single, String> {
}
