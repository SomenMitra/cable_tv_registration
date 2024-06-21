package tech.csm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.model.Provider;

@Repository
public interface ProviderRepo extends JpaRepository<Provider, Integer> {

}
