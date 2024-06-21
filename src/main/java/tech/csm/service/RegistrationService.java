package tech.csm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;

import tech.csm.model.Registration;

public interface RegistrationService {

	Registration saveRegistration(Registration reg);

	List<Registration> getAllRegistrations();

	List<Registration> getAllRegistrationsByProviderId(Integer pID);

	Registration softDeleteRegistration(Integer redgId);

	Registration updateRegistrationById(Integer redgId);

	Registration updateRegistration(Registration reg);

	ByteArrayInputStream generatePdf(List<Registration> registrations) throws DocumentException;

	ByteArrayInputStream generateExcel(List<Registration> registrations) throws IOException;

}
