package tech.csm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Table(name = "subscription_master")
@Entity
public class Subscription {
	
	@Id
	@Column(name = "subscription_id")
	private Integer subscriptionId;

	@Column(name = "subscription_type")
	private String subscriptionType;
	
	@Column(name = "fees")
	private Double fees;
}
