package com.training.directory.repository;

import com.training.directory.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {

    List<Album> findAllByOrderByNameAsc();
}
