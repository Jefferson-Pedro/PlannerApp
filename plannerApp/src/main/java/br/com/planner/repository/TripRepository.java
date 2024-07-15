package br.com.planner.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.planner.model.Trip;

public interface TripRepository extends JpaRepository<Trip, UUID> {
	
}
