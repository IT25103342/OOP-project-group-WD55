package com.projectGroupWD55.bikeRentalAndRideSharing.controller;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/BikeInventory")
public class BikeController {
    private final BikeService bikeService;
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PostMapping("/available")
    public BikeResponseDTO addBikes(@RequestBody BikeRequestDTO bikeRequestDTO) {
        return bikeService.addBike(bikeRequestDTO);
    }
    @GetMapping("/all")
    public List<BikeResponseDTO> GetAllBike() {
        return bikeService.getAllBike();
    }
    @GetMapping("/available")
    public List<BikeResponseDTO> GetAllAvailableBikes() {
        return bikeService.getAllBikesAvailable();
    }
    @GetMapping("/{id}")
    public BikeResponseDTO GetBikeById(@PathVariable Long id) {
        return bikeService.getBikeById(id);
    }
    @PutMapping("/{id}")
    public BikeResponseDTO updateBike(@PathVariable Long id, @RequestBody BikeRequestDTO bikeRequestDTO) {
        return bikeService.updateBike(id, bikeRequestDTO);
    }
    @DeleteMapping("/{id}")
    public BikeResponseDTO archiveBike(@PathVariable Long id) {
        return bikeService.archiveBike(id);
    }


}
