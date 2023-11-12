package com.training.directory.repository;

import com.training.directory.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    @Query(value = "SELECT image.id FROM image WHERE user_id = :userId", nativeQuery = true)
    String findByUserId(String userId);

    @Modifying
    @Query(value = "DELETE FROM image WHERE image.id = :imageId", nativeQuery = true)
    void deleteById(String imageId);
}
