package tech.csm.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="provider_master")
@Entity
public class Provider implements Serializable {
	
	@Id
	@Column(name = "provider_id")
	private Integer providerId;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@ManyToMany
	@JoinTable(name = "provider_subscription", joinColumns = @JoinColumn(name="privider_id"), inverseJoinColumns = @JoinColumn(name="subscription_id"))
	private List<Subscription> subscriptions;

}
