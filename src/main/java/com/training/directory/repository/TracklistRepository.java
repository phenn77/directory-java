package com.training.directory.repository;

import com.training.directory.model.Tracklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracklistRepository extends JpaRepository<Tracklist, String> {
}
