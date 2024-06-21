package tech.csm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.model.Provider;
import tech.csm.repository.ProviderRepo;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ProviderRepo providerRepo;

	@Override
	public List<Provider> getAllProviders() {
		return providerRepo.findAll();
	}

	@Override
	public Provider getProviderByProviderId(Integer pId) {
		
		return providerRepo.findById(pId).get();
	}
}
