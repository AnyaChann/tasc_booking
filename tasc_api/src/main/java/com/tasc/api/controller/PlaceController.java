package com.tasc.api.controller;

import com.tasc.api.dto.ApiResponse;
import com.tasc.api.dto.PlaceDTO;
import com.tasc.api.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {
    
    private final PlaceService placeService;
    
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<ApiResponse<List<PlaceDTO>>> getAllPlaces() {
        try {
            List<PlaceDTO> places = placeService.getAllPlaces();
            return ResponseEntity.ok(ApiResponse.success("Places retrieved successfully", places));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve places: " + e.getMessage()));
        }
    }
    
    @GetMapping(value = "/popular", produces = "application/json")
    public ResponseEntity<ApiResponse<List<PlaceDTO>>> getPopularPlaces() {
        try {
            List<PlaceDTO> places = placeService.getPopularPlaces();
            return ResponseEntity.ok(ApiResponse.success("Popular places retrieved successfully", places));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve popular places: " + e.getMessage()));
        }
    }
    
    @GetMapping(value = "/category/{category}", produces = "application/json")
    public ResponseEntity<ApiResponse<List<PlaceDTO>>> getPlacesByCategory(@PathVariable String category) {
        try {
            List<PlaceDTO> places = placeService.getPlacesByCategory(category);
            return ResponseEntity.ok(ApiResponse.success("Places by category retrieved successfully", places));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve places by category: " + e.getMessage()));
        }
    }
}