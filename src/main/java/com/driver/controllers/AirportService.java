package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    @Autowired
    AirportRepository airportRepository;

    public static void addAirport(Airport airport) {
        AirportRepository.addAirport(airport);
    }

    public static String getLargestAirportName() {
        List<String> airports = AirportRepository.getAllAirports();
        int maxTerminal = 0;
        String airportWithMaxTerminal = "";
        for(String name : airports){
            Airport airport = AirportRepository.getAirportByName(name);
            if(airport.getNoOfTerminals()>maxTerminal){
                maxTerminal = airport.getNoOfTerminals();
                airportWithMaxTerminal = airport.getAirportName();
            }
        }
        return airportWithMaxTerminal;
    }

    public static void addFlight(Flight flight) {
        AirportRepository.addFlight(flight);
    }

    public static String getAirportNameFromFlightId(Integer flightId) {
        Optional<String> starting = AirportRepository.getAirportByFlightId(flightId);
        if(starting.isPresent()){
            return starting.get();
        }
        throw new RuntimeException("There is no flight for the flightId: " + flightId);
    }

    public static void addPassenger(Passenger passenger) {
        AirportRepository.addPassenger(passenger);
    }

    public static boolean bookATicket(Integer flightId, Integer passengerId) {
        return AirportRepository.bookATicket(flightId,passengerId);
    }

    public static String cancelTicket(Integer flightId, Integer passengerId) {
        Optional<Passenger> passengerOptional = AirportRepository.getPassengerById(passengerId);
        Optional<Flight> flightOptional = AirportRepository.getFlightById(flightId);
        if(passengerOptional.isEmpty()){
            throw new RuntimeException("Passenger id is Invalid");
        }
        if(flightOptional.isEmpty()){
            throw new RuntimeException("Flight id is Invalid");
        }
        String confirmation = AirportRepository.cancelTicket(flightId,passengerId);
        return confirmation;
    }

    public static int calculateFare(Integer flightId) {
        ArrayList<Integer> passengers = AirportRepository.getAllPassengersForFlight(flightId);
        return 3000 + (passengers.size()*50);
    }
}
