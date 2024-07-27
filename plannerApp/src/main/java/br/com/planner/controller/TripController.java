package br.com.planner.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planner.dto.ParticipantCreateResponse;
import br.com.planner.dto.ParticipantData;
import br.com.planner.dto.ParticipantRequestPayLoad;
import br.com.planner.dto.TripCreateResponse;
import br.com.planner.dto.TripResquestPayLoad;
import br.com.planner.model.Participant;
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
		
		System.out.println("payload Trip: " + payload);
		
		Trip newTrip = new Trip(payload);
		
		this.repository.save(newTrip);
		
		this.participantService.registerParticipantsToEvent(payload.emailsToInvite(), newTrip);
		
		return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
		Optional<Trip> trip = this.repository.findById(id);
		
		return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripResquestPayLoad payload){
		Optional<Trip> trip = this.repository.findById(id);
		
		if(trip.isPresent()) {
			Trip rawTrip = trip.get();
			rawTrip.setEndAt(LocalDateTime.parse(payload.endAt(), DateTimeFormatter.ISO_DATE_TIME));
			rawTrip.setStartAt(LocalDateTime.parse(payload.startAt(), DateTimeFormatter.ISO_DATE_TIME));
			rawTrip.setDestination(payload.destination());
			
			this.repository.save(rawTrip);
			
			return ResponseEntity.ok(rawTrip);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/confirm")
	public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){
		Optional<Trip> trip = this.repository.findById(id);
		
		if(trip.isPresent()) {
			Trip rawTrip = trip.get();
			rawTrip.setIsConfirmed(true);
			
			this.repository.save(rawTrip);
			this.participantService.triggerConfirmationEmailToParticipants(id);
			
			return ResponseEntity.ok(rawTrip);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{id}/invite")
	public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayLoad payload){
		Optional<Trip> trip = this.repository.findById(id);
		
		if(trip.isPresent()) {
			Trip rawTrip = trip.get();
	
			this.participantService.registerParticipantsToEvent(payload.email(), rawTrip);
			
			ParticipantCreateResponse partResponse = this.participantService.registerParticipantsToEvent(payload.email(), rawTrip);
			
			System.out.println("Id do Evento: "+ partResponse.id());
			 
			if(rawTrip.getIsConfirmed()) {
				this.participantService.triggerConfirmationEmailToParticipants(payload.email());
			}
			return ResponseEntity.ok(partResponse);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/participants")
	public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
		
		List<ParticipantData> partList = this.participantService.getAllParticipantsFromEvent(id);
		
		return ResponseEntity.ok(partList);
	}
	
}
