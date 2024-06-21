package tech.csm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.csm.model.Registration;

public interface RegistrationRepo extends JpaRepository<Registration, Integer> {

	List<Registration> findAllByIsDelete(String isDelete);

	List<Registration> findAllByProviderProviderId(Integer pID);

}
