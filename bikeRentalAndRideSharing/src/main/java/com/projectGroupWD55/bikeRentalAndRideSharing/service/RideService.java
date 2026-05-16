package com.projectGroupWD55.bikeRentalAndRideSharing.service;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideRequestDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.RideResponseDTO;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.Ride;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RidePassenger;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.RideStatus;
import com.projectGroupWD55.bikeRentalAndRideSharing.entity.User1;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.RidePassengerRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.RideRepository;
import com.projectGroupWD55.bikeRentalAndRideSharing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final RidePassengerRepository ridePassengerRepository;
    private final UserRepository userRepository;
    public RideService(RideRepository rideRepository, RidePassengerRepository ridePassengerRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.ridePassengerRepository = ridePassengerRepository;
        this.rideRepository = rideRepository;
    }


    public RideResponseDTO postRide(RideRequestDTO rideRequestDTO) {
        Ride ride = new Ride();
        ride.setPickupLocation(rideRequestDTO.getPickupLocation());
        User1 poster = userRepository.findById(rideRequestDTO.getPosterId()).orElseThrow(() -> new RuntimeException("User not found"));
        ride.setPoster(poster);
        ride.setDestination(rideRequestDTO.getDestinationLocation());
        ride.setPickupLocation(rideRequestDTO.getPickupLocation());
        ride.setRideTime(rideRequestDTO.getRideTime());
        ride.setStatus(RideStatus.OPEN);
        ride.setSeatsAvailable(rideRequestDTO.getSeatsAvailable());
        ride.setCreatedAt(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        return maptoresponseDTO(savedRide);
    }


    public List<RideResponseDTO> getActiveRides() {
        return rideRepository.findByStatus(RideStatus.OPEN).stream().map(this::maptoresponseDTO).collect(Collectors.toList());
    }
    public List<RideResponseDTO> searchRides(String pickup, String destination) {
        return rideRepository.findByPickupLocationAndDestination(pickup, destination).stream().map(this::maptoresponseDTO).collect(Collectors.toList());
    }

    public RideResponseDTO joinRide(Long rideId,Long ridePassengerId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride does not exist"));
        if(ride.getStatus() != RideStatus.OPEN){
            throw new RuntimeException("Ride is not open");
        }
        if(ridePassengerRepository.existsByRideIdAndPassengerId(rideId ,ridePassengerId)) {
            throw new RuntimeException("already in ride");
        }
        if(ride.getSeatsAvailable()<=0){
            throw new RuntimeException("no seats not available");
        }
        User1 passenger = userRepository.findById(ridePassengerId).orElseThrow(() -> new RuntimeException("User not found"));

        RidePassenger ridePassenger = new RidePassenger();
        ridePassenger.setPassenger(passenger);
        ridePassenger.setRide(ride);
        ridePassenger.setJoinedAt(LocalDateTime.now());
        ridePassengerRepository.save(ridePassenger);

        ride.setSeatsAvailable(ride.getSeatsAvailable() - 1);
        if (ride.getSeatsAvailable() == 0) {
            ride.setStatus(RideStatus.FULL);
        }

        Ride savedRide = rideRepository.save(ride);
        return maptoresponseDTO(savedRide);
    }

    public RideResponseDTO cancelRide(Long rideId,Long ridePassengerId) {
        User1 requester = userRepository.findById(ridePassengerId).orElseThrow(() -> new RuntimeException("User not found"));
        Ride ride=rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride does not exist"));

        if(requester.getId().equals(ride.getPoster().getId())){
            ride.setStatus(RideStatus.CANCELLED);
            rideRepository.save(ride);
        }else{
            RidePassenger toRemove = ridePassengerRepository.findByRideId(rideId).stream().filter(p -> p.getPassenger().getId().equals(ridePassengerId)).findFirst().orElseThrow(() -> new RuntimeException("You are not in this ride"));

            ridePassengerRepository.delete(toRemove);
            ride.setSeatsAvailable(ride.getSeatsAvailable() + 1);
            if (ride.getStatus() == RideStatus.FULL) {
                ride.setStatus(RideStatus.OPEN);
            }
            rideRepository.save(ride);
        }

        return maptoresponseDTO(ride);
    }



    private RideResponseDTO maptoresponseDTO(Ride ride) {
        RideResponseDTO rideResponseDTO = new RideResponseDTO();
        rideResponseDTO.setId(ride.getId());
        rideResponseDTO.setPickupLocation(ride.getPickupLocation());
        rideResponseDTO.setRideTime(ride.getRideTime());
        rideResponseDTO.setDestinationLocation(ride.getDestination());

        rideResponseDTO.setPosterName(ride.getPoster().getUsername());
        rideResponseDTO.setEmail(ride.getPoster().getEmail());

        rideResponseDTO.setStatus(ride.getStatus());
        rideResponseDTO.setSeatsAvailable(ride.getSeatsAvailable());
        rideResponseDTO.setPassengerCount(ride.getPassengerCount());

        List<String> passengerNames = ridePassengerRepository.findByRideId(ride.getId()).stream().map(ridePassenger -> ridePassenger.getPassenger().getUsername()).collect(Collectors.toList());
        rideResponseDTO.setPassengerNames(passengerNames);


        return rideResponseDTO;
    }
}
