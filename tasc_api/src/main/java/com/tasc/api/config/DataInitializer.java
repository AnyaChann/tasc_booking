package com.tasc.api.config;

import com.tasc.api.entity.Place;
import com.tasc.api.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final PlaceRepository placeRepository;
    
    @Autowired
    public DataInitializer(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (placeRepository.count() == 0) {
            initializeData();
        }
    }
    
    private void initializeData() {
        // Sample data for testing - using custom constructor (without ID)
        Place[] places = {
            new Place("Hoi An", "Ancient town with beautiful architecture", 
                     "https://images.unsplash.com/photo-1559592413-7cec4d0d5d8e?w=400", 4.0, "Vietnam", "All", true, 150.0),
            new Place("Sai Gon", "Vibrant city with modern skyline", 
                     "https://images.unsplash.com/photo-1583417319070-4a69db38a482?w=400", 4.5, "Vietnam", "All", true, 200.0),
            new Place("Da Nang Beach Resort", "Luxury beachfront hotel", 
                     "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=400", 4.2, "Da Nang", "Hotels", true, 300.0),
            new Place("Hanoi Heritage Hotel", "Traditional Vietnamese hospitality", 
                     "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=400", 4.3, "Hanoi", "Hotels", false, 180.0),
            new Place("Nha Trang Beach", "Beautiful coastal destination", 
                     "https://images.unsplash.com/photo-1590736969955-71cc94901144?w=400", 4.1, "Nha Trang", "All", true, 120.0),
            new Place("Phu Quoc Island", "Tropical paradise with pristine beaches", 
                     "https://images.unsplash.com/photo-1602002418082-a4443e081dd1?w=400", 4.6, "Phu Quoc", "All", true, 250.0),
            new Place("Luxury Sky Hotel", "Modern hotel with city views", 
                     "https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=400", 4.4, "Ho Chi Minh City", "Hotels", false, 220.0),
            new Place("Vietnam Airlines Flight", "Domestic flight services", 
                     "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=400", 4.0, "Nationwide", "Flights", false, 80.0)
        };
        
        for (Place place : places) {
            placeRepository.save(place);
        }
        
        System.out.println("Sample data initialized successfully!");
    }
}