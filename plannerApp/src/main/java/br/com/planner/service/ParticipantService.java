package br.com.planner.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
	
	public void registerParticipantsToEvent(List<String> participantsToInvate, UUID id) {}
	
	public void triggerConfirmationEmailToParticipants(UUID tripId) {}
}
