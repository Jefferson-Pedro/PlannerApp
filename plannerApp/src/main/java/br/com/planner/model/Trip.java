package br.com.planner.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import br.com.planner.dto.TripResquestPayLoad;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false)
	private String destination;
	
	@Column(name= "starts_at", nullable = false)
	private LocalDateTime startAt;
	
	@Column(name= "ends_at", nullable = false)
	private LocalDateTime endAt;
	
	@Column(name= "is_confirmed", nullable = false)
	private Boolean isConfirmed;
	
	@Column(name= "owner_name", nullable = false)
	private String ownerName;
	
	@Column(name= "owner_email", nullable = false)
	private String ownerEmail;

	public Trip(TripResquestPayLoad data) {
		this.destination = data.destination();
		this.startAt =  LocalDateTime.parse(data.startAt(), DateTimeFormatter.ISO_DATE_TIME);
		this.endAt = LocalDateTime.parse(data.endAt(), DateTimeFormatter.ISO_DATE_TIME);
		this.isConfirmed = false;
		this.ownerName = data.ownerName();
		this.ownerEmail = data.ownerEmail();
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", destination=" + destination + ", startAt=" + startAt + ", endAt=" + endAt
				+ ", isConfirmed=" + isConfirmed + ", ownerName=" + ownerName + ", ownerEmail=" + ownerEmail + "]";
	}
	
	
}
