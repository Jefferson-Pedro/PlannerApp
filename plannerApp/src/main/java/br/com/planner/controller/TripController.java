package br.com.planner.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planner.dto.TripCreateResponse;
import br.com.planner.dto.TripResquestPayLoad;
import br.com.planner.model.Trip;
import br.com.planner.repository.TripRepository;
import br.com.planner.service.ParticipantService;

@RestController
@RequestMapping("/trips")
public class TripController {
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private TripRepository repository;
	
	@PostMapping
	public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripResquestPayLoad payload){
		
		System.out.println("payload: " + payload);
		
		Trip newTrip = new Trip(payload);
		
		
		this.repository.save(newTrip);
		
		
		this.participantService.registerParticipantsToEvent(payload.emailsToInvite(), newTrip.getId());
		
		return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
		Optional<Trip> trip = this.repository.findById(id);
		
		return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
