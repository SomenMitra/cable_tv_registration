package tech.csm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import tech.csm.model.Registration;
import tech.csm.repository.RegistrationRepo;
import tech.csm.util.FileUploadUtil;

@Service
public class RegistrationServiceImp implements RegistrationService {

	@Autowired
	private RegistrationRepo registrationRepo;

	@Override
	public Registration saveRegistration(Registration reg) {
		reg.setImagePath(FileUploadUtil.uploadFile(reg.getImageFile()));
		reg.setIsDelete("NO");
		return registrationRepo.save(reg);
	}

	@Override
	public List<Registration> getAllRegistrations() {
		String isDelete = "NO";
		return registrationRepo.findAllByIsDelete(isDelete);
	}

	@Override
	public List<Registration> getAllRegistrationsByProviderId(Integer pID) {
		List<Registration> registrations = registrationRepo.findAllByProviderProviderId(pID);
		return registrations;
	}

	@Override
	public Registration softDeleteRegistration(Integer redgId) {
		Registration reg = registrationRepo.findById(redgId).get();
		reg.setIsDelete("YES");
		return registrationRepo.save(reg);
	}

	@Override
	public Registration updateRegistrationById(Integer redgId) {

		return registrationRepo.findById(redgId).get();
	}

	@Override
	public Registration updateRegistration(Registration reg) {
		reg.setIsDelete("NO");
		reg.setImagePath(FileUploadUtil.uploadFile(reg.getImageFile()));
		return registrationRepo.save(reg);
	}

	@Override
	public ByteArrayInputStream generatePdf(List<Registration> registrations) throws DocumentException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();
		int i = 1;
		for (Registration r : registrations) {
			document.add(new Paragraph("SlNo: " + i++));
			document.add(new Paragraph("Name: " + r.getApplicantName()));
			document.add(new Paragraph("Email: " + r.getEmailId()));
			document.add(new Paragraph("Mobile: " + r.getMobileNo()));
			document.add(new Paragraph("Gen: " + r.getGender()));
			document.add(new Paragraph("DOB: " + r.getDob()));
			document.add(new Paragraph("Provider: " + r.getProvider().getProviderName()));
			document.add(new Paragraph("Subscription Type: " + r.getSubscription().getSubscriptionType()));
			document.add(new Paragraph(" "));
		}
		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream generateExcel(List<Registration> registrations) throws IOException {
		 XSSFWorkbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Sheet sheet = workbook.createSheet("Registrations");

	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("SlNo");
	        headerRow.createCell(1).setCellValue("Name");
	        headerRow.createCell(2).setCellValue("Email");
	        headerRow.createCell(3).setCellValue("Mobile");
	        headerRow.createCell(4).setCellValue("Gender");
	        headerRow.createCell(5).setCellValue("DOB");
	        headerRow.createCell(6).setCellValue("Provider");
	        headerRow.createCell(7).setCellValue("Subscription Type");

	        int rowIdx = 1;
	        int slNo = 1;
	        for (Registration r : registrations) {
	            Row row = sheet.createRow(rowIdx++);
	            row.createCell(0).setCellValue(slNo++);
	            row.createCell(1).setCellValue(r.getApplicantName());
	            row.createCell(2).setCellValue(r.getEmailId());
	            row.createCell(3).setCellValue(r.getMobileNo());
	            row.createCell(4).setCellValue(r.getGender());
	            row.createCell(5).setCellValue(r.getDob());
	            row.createCell(6).setCellValue(r.getProvider().getProviderName());
	            row.createCell(7).setCellValue(r.getSubscription().getSubscriptionType());
	        }

	        workbook.write(out);
	        workbook.close();
	        return new ByteArrayInputStream(out.toByteArray());
	}
}
