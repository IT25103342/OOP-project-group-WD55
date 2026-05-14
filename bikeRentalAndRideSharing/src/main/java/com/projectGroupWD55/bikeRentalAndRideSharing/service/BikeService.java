package com.projectGroupWD55.bikeRentalAndRideSharing.service;


import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.BikeResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Bike;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BikeStatus;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.BikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//addBike(BikeRequestDTO) → save new bike
//getAllBikes() → return full fleet
//getAvailableBikes() → filter by AVAILABLE status
//getBikeById(Long id) → single bike details
//updateBike(Long id, BikeRequestDTO) → edit bike info
//archiveBike(Long id) → set status to ARCHIVED
@Service
public class BikeService {

    private final BikeRepository bikeRepository;
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }
    public BikeResponseDTO addBike(BikeRequestDTO bikeRequestDTO) {
        Bike boik= new Bike();

        boik.setBrand(bikeRequestDTO.getBrand());
        boik.setModel(bikeRequestDTO.getModel());
        boik.setLocation(bikeRequestDTO.getLocation());
        boik.setCreatedDate(bikeRequestDTO.getCreatedDate());
        boik.setPricePerDay(bikeRequestDTO.getPricePerDay());
        boik.setPricePerHour(bikeRequestDTO.getPricePerHour());
        boik.setCreatedDate(LocalDateTime.now());
        boik.setStatus(BikeStatus.AVAILABLE);

        Bike savedBike= bikeRepository.save(boik); //i made it so that it saves here and the rest is done to protect the dto(saving private DTO, HAHHA get it?)

        BikeResponseDTO response = new BikeResponseDTO();
        response.setBikeId(savedBike.getBikeId());
        response.setBrand(savedBike.getBrand());
        response.setModel(savedBike.getModel());
        response.setLocation(savedBike.getLocation());
        response.setPricePerDay(savedBike.getPricePerDay());
        response.setPricePerHour(savedBike.getPricePerHour());
        response.setImageURL(savedBike.getImageURL());
        response.setStatus(savedBike.getStatus());

        return response;

    }
    public List<BikeResponseDTO> getAllBike() {
        return bikeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BikeResponseDTO> getAllBikesAvailable() {
        return bikeRepository.findByStatus(BikeStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public BikeResponseDTO getBikeById(Long id) {
        Bike boik =bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike does not exist"));
         //i made it so that it saves here and the rest is done to protect the dto(saving private DTO, HAHHA get it?)

        BikeResponseDTO response = new BikeResponseDTO();
        response.setBikeId(boik.getBikeId());
        response.setBrand(boik.getBrand());
        response.setModel(boik.getModel());
        response.setLocation(boik.getLocation());
        response.setPricePerDay(boik.getPricePerDay());
        response.setPricePerHour(boik.getPricePerHour());
        response.setImageURL(boik.getImageURL());
        response.setStatus(boik.getStatus());

        return response;
    }
    public BikeResponseDTO updateBike(Long id, BikeRequestDTO bikeRequestDTO) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike does not exist"));

        if(bikeRequestDTO.getBrand()!=null){
            bike.setBrand(bikeRequestDTO.getBrand());
        }
        if(bikeRequestDTO.getModel()!=null){
            bike.setModel(bikeRequestDTO.getModel());
        }
        if(bikeRequestDTO.getLocation()!=null){
            bike.setLocation(bikeRequestDTO.getLocation());
        }
        if(bikeRequestDTO.getPricePerDay()!=null){
            bike.setPricePerDay(bikeRequestDTO.getPricePerDay());
        }
        if(bikeRequestDTO.getPricePerHour()!=null){
            bike.setPricePerHour(bikeRequestDTO.getPricePerHour());
        }
        if(bikeRequestDTO.getImageURL()!=null){
            bike.setImageURL(bikeRequestDTO.getImageURL());
        }
        Bike updatedBike = bikeRepository.save(bike);
        BikeResponseDTO bikeResponseDTO = new BikeResponseDTO();

        bikeResponseDTO.setBikeId(updatedBike.getBikeId());
        bikeResponseDTO.setImageURL(updatedBike.getImageURL());
        bikeResponseDTO.setBrand(updatedBike.getBrand());
        bikeResponseDTO.setModel(updatedBike.getModel());
        bikeResponseDTO.setLocation(updatedBike.getLocation());
        bikeResponseDTO.setPricePerDay(updatedBike.getPricePerDay());
        bikeResponseDTO.setPricePerHour(updatedBike.getPricePerHour());
        return bikeResponseDTO;
    }
    public BikeResponseDTO archiveBike(Long id) {
        Bike bike=bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike does not exist"));

        bike.setStatus(BikeStatus.ARCHIVED);

        Bike savedBike = bikeRepository.save(bike);

        BikeResponseDTO bikeResponseDTO = new BikeResponseDTO();
        bikeResponseDTO.setBikeId(savedBike.getBikeId());
        bikeResponseDTO.setImageURL(savedBike.getImageURL());
        bikeResponseDTO.setBrand(savedBike.getBrand());
        bikeResponseDTO.setModel(savedBike.getModel());
        bikeResponseDTO.setLocation(savedBike.getLocation());
        bikeResponseDTO.setPricePerDay(savedBike.getPricePerDay());
        bikeResponseDTO.setPricePerHour(savedBike.getPricePerHour());
        bikeResponseDTO.setStatus(savedBike.getStatus());

        return bikeResponseDTO;
    }



    private BikeResponseDTO mapToResponse(Bike bike) {
        BikeResponseDTO response = new BikeResponseDTO();
        response.setBikeId(bike.getBikeId());
        response.setBrand(bike.getBrand());
        response.setModel(bike.getModel());
        response.setLocation(bike.getLocation());
        response.setPricePerDay(bike.getPricePerDay());
        response.setPricePerHour(bike.getPricePerHour());
        response.setImageURL(bike.getImageURL());
        response.setStatus(bike.getStatus());
        return response;
    }
}
