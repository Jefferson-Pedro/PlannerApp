package br.com.planner.dto;

import java.util.List;

public record TripResquestPayLoad(String destination, 
								  String startAt, 
								  String endAt, 
								  List<String> emailsToInvite,
								  String ownerEmail,
								  String ownerName) {
	
}
