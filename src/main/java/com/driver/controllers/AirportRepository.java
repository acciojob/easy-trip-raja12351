package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class AirportRepository {
    private static HashMap<String , Airport> airportList = new HashMap<>();
    private static HashMap<Integer, Flight> flightList = new HashMap<>();
    private static HashMap<Integer, Passenger> passengersList = new HashMap<>();
    private static HashMap<Integer,Integer> passengerFlightHashmap = new HashMap<>();
    private static HashMap<Integer,ArrayList<Integer>> flightPassengerHashmap = new HashMap<>();

    public static void addAirport(Airport airport) {
        airportList.put(airport.getAirportName(),airport);
    }

    public static List<String> getAllAirports() {
        return new ArrayList<>(airportList.keySet());
    }

    public static Airport getAirportByName(String name) {
        if(airportList.containsKey(name)){
            return airportList.get(name);
        }
        return null;
    }

    public static void addFlight(Flight flight) {
        flightList.put(flight.getFlightId(), flight);
    }

    public static Optional<String> getAirportByFlightId(Integer flightId) {
        if(flightList.containsKey(flightId)){
            Flight flight = flightList.get(flightId);
            return Optional.of(String.valueOf(flight.getFromCity()));
        }
        return Optional.empty();
    }

    public static void addPassenger(Passenger passenger) {
        passengersList.put(passenger.getPassengerId(), passenger);
    }

    public static boolean bookATicket(Integer flightId, Integer passengerId) {
        if(passengersList.containsKey(passengerId) && flightList.containsKey(flightId)){
            passengerFlightHashmap.put(passengerId,flightId);
            ArrayList<Integer> passengers = flightPassengerHashmap.getOrDefault(flightId , new ArrayList<>());
            passengers.add(passengerId);
            flightPassengerHashmap.put(flightId,passengers);
            return true;
        }
        return false;
    }

    public static Optional<Flight> getFlightById(Integer flightId) {
        if(flightList.containsKey(flightId)){
            return Optional.of(flightList.get(flightId));
        }
        return Optional.empty();
    }

    public static Optional<Passenger> getPassengerById(Integer passengerId) {
        if(passengersList.containsKey(passengerId)){
            return Optional.of(passengersList.get(passengerId));
        }
        return Optional.empty();
    }

    public static String cancelTicket(Integer flightId, Integer passengerId) {
        if(passengerFlightHashmap.containsKey(passengerId) && (flightPassengerHashmap.containsKey(flightId) && flightPassengerHashmap.get(flightId).contains(passengerId))){
            passengerFlightHashmap.remove(passengerId);
            flightPassengerHashmap.get(flightId).remove(passengerId);
            return "Cancelled";
        }
        throw new RuntimeException("No such Bookings");
    }

    public static ArrayList<Integer> getAllPassengersForFlight(Integer flightId) {
        return flightPassengerHashmap.get(flightId);
    }
}
