package com.tasc.api.service;

import com.tasc.api.dto.PlaceDTO;
import com.tasc.api.entity.Place;
import com.tasc.api.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    
    private final PlaceRepository placeRepository;
    
    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }
    
    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PlaceDTO> getPopularPlaces() {
        List<Place> places = placeRepository.findPopularPlacesByRating();
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PlaceDTO> getPlacesByCategory(String category) {
        List<Place> places = placeRepository.findByCategory(category);
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private PlaceDTO convertToDTO(Place place) {
        return new PlaceDTO(
                place.getId(),
                place.getName(),
                place.getDescription(),
                place.getImageUrl(),
                place.getRating(),
                place.getLocation(),
                place.getCategory(),
                place.getIsPopular(),
                place.getPrice()
        );
    }
}