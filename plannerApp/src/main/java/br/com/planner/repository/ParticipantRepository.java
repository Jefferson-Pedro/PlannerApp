package br.com.planner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.planner.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
	List<Participant> findByTripId(UUID tripId);
}
