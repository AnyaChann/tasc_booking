package com.tasc.api.dto;

public class PlaceDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double rating;
    private String location;
    private String category;
    private Boolean isPopular;
    private Double price;

    public PlaceDTO() {}

    public PlaceDTO(Long id, String name, String description, String imageUrl, Double rating,
                    String location, String category, Boolean isPopular, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.location = location;
        this.category = category;
        this.isPopular = isPopular;
        this.price = price;
    }

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Boolean getIsPopular() { return isPopular; }
    public void setIsPopular(Boolean isPopular) { this.isPopular = isPopular; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}