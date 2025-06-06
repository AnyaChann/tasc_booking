package com.tasc.api.repository;

import com.tasc.api.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    List<Place> findByIsPopularTrue();
    
    List<Place> findByCategory(String category);
    
    @Query("SELECT p FROM Place p WHERE p.isPopular = true ORDER BY p.rating DESC")
    List<Place> findPopularPlacesByRating();
}