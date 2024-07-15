package br.com.planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> createTrip(@RequestBody TripResquestPayLoad payload){
		
		System.out.println("payload: " + payload);
		
		Trip newTrip = new Trip(payload);
		
		System.out.println("new Trip: " +newTrip.toString());
		
		this.repository.save(newTrip);
		
		
		
		this.participantService.registerParticipantsToEvent(payload.emailsToInvite(), newTrip.getId());
		
		return ResponseEntity.ok("Sucesso!");
		
	}
}
