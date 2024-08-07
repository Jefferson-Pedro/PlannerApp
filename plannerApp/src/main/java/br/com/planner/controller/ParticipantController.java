package br.com.planner.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planner.dto.ParticipantRequestPayLoad;
import br.com.planner.model.Participant;
import br.com.planner.model.Trip;
import br.com.planner.repository.ParticipantRepository;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
	
	@Autowired
	private ParticipantRepository repository;
	
	@PostMapping("{id}/confirm")
	public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayLoad payload){
		Optional<Participant> participant = this.repository.findById(id);
		
		if(participant.isPresent()) {
			Participant rawParticipant = participant.get();
			rawParticipant.setIsConfirmed(true);
			rawParticipant.setName(payload.name());
			
			this.repository.save(rawParticipant);
			
			return ResponseEntity.ok(rawParticipant);
		}
		
		return ResponseEntity.notFound().build();
	}

}
