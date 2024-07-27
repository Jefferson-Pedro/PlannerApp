package br.com.planner.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.planner.dto.ParticipantCreateResponse;
import br.com.planner.dto.ParticipantData;
import br.com.planner.model.Participant;
import br.com.planner.model.Trip;
import br.com.planner.repository.ParticipantRepository;

@Service
public class ParticipantService {
	
	@Autowired
	private ParticipantRepository repository;
	
	public void registerParticipantsToEvent(List<String> participantsToInvate, Trip trip) {
		
		List<Participant> participants = participantsToInvate.stream().map(email -> new Participant(email, trip)).toList();
		
		this.repository.saveAll(participants);
		
		System.out.println("Id Participante: " + participants.get(0).getId());
	}
	
	public ParticipantCreateResponse registerParticipantsToEvent(String email, Trip trip) {
		Participant newParticipant = new Participant(email, trip);
		this.repository.save(newParticipant);
		
		return new ParticipantCreateResponse(newParticipant.getId());
	}
	
	public void triggerConfirmationEmailToParticipants(UUID tripId) {}
	
	public void triggerConfirmationEmailToParticipants(String email) {}

	public List<ParticipantData> getAllParticipantsFromEvent(UUID id) {
	
		return this.repository
				.findByTripId(id)
				.stream()
				.map(participant -> 
				new ParticipantData(
						participant.getId(), 
						participant.getName(), 
						participant.getEmail(),
						participant.getIsConfirmed())).toList();
	}
}
