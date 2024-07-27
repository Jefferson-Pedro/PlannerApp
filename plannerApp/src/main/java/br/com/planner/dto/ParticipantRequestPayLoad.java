package br.com.planner.dto;

public record ParticipantRequestPayLoad(Boolean isConfirmed,
										String name,
										String email) {}
