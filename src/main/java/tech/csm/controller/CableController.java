package tech.csm.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.csm.model.Provider;
import tech.csm.model.Registration;
import tech.csm.model.Subscription;
import tech.csm.service.ProviderService;
import tech.csm.service.RegistrationService;
import tech.csm.util.FileDownloadUtil;

@Controller
public class CableController {

	@Autowired
	private ProviderService providerService;

	@Autowired
	private RegistrationService registrationService;

	@GetMapping("/getForm")
	public String getForm(Model model) {
		List<Provider> providerList = providerService.getAllProviders();
		List<Registration> registrations = registrationService.getAllRegistrations();
		model.addAttribute("providers", providerList);
		model.addAttribute("regList", registrations);
		return "cable";
	}

	@GetMapping("/getSubsByProvid")
	public void getSubscriptionByProviderId(@RequestParam("provId") Integer pId, HttpServletResponse resp) {
		try {

			Provider p = providerService.getProviderByProviderId(pId);
			List<Subscription> subscriptionList = p.getSubscriptions();

			String res = "<option value='0'>--select--</option>";
			for (Subscription s : subscriptionList) {
				res += "<option value='" + s.getSubscriptionId() + "'>" + s.getSubscriptionType() + "</option>";
			}
			resp.getWriter().println(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@PostMapping("/saveReg")
	public String saveRegistration(@ModelAttribute Registration reg, RedirectAttributes rd) {
		if (reg.getRegistrationId() == null) {
			Registration r = registrationService.saveRegistration(reg);
			rd.addFlashAttribute("msg", "one registration done with id : " + reg.getRegistrationId());
			return "redirect:./getForm";
		}
		Registration r = registrationService.updateRegistration(reg);
		rd.addFlashAttribute("umsg", "one registration updated with id : " + reg.getRegistrationId());
		return "redirect:./getForm";
	}

	@GetMapping("/filterProvider")
	public String filterByProvider(Model model, @RequestParam("fPvId") Integer pID) {
		List<Provider> providerList = providerService.getAllProviders();
		List<Registration> registrations = registrationService.getAllRegistrationsByProviderId(pID);
		model.addAttribute("providers", providerList);
		model.addAttribute("regList", registrations);
		return "cable";
	}

	@GetMapping("/delReg")
	public String delReristration(@RequestParam("rId") Integer redgId, RedirectAttributes rd) {
		Registration r = registrationService.softDeleteRegistration(redgId);
		rd.addFlashAttribute("dmsg", "one registration deleted hiving id : " + redgId);
		return "redirect:./getForm";
	}

	@GetMapping("/upReg")
	public String updateReristration(Model model, @RequestParam("rId") Integer redgId) {
		Registration r = registrationService.updateRegistrationById(redgId);
		List<Provider> providerList = providerService.getAllProviders();
		List<Registration> registrations = registrationService.getAllRegistrations();
		List<Subscription> subcriptionList = providerService.getProviderByProviderId(r.getProvider().getProviderId())
				.getSubscriptions();

		model.addAttribute("providers", providerList);
		model.addAttribute("regList", registrations);
		model.addAttribute("reg", r);
		model.addAttribute("subsList", subcriptionList);
		return "cable";
	}

	@GetMapping("/getImage")
	public void name(HttpServletRequest req, HttpServletResponse resp) {
		try {
			FileDownloadUtil.downloadFile(req, resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/download/pdf")
	public ResponseEntity<byte[]> downloadPdf() throws DocumentException {
		List<Registration> registrations = registrationService.getAllRegistrations();
		ByteArrayInputStream pdf = registrationService.generatePdf(registrations);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=registration.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdf.readAllBytes());
	}

	@GetMapping("/download/excel")
	public ResponseEntity<byte[]> downloadExcel() throws IOException {
		List<Registration> registrations = registrationService.getAllRegistrations();
		ByteArrayInputStream excel = registrationService.generateExcel(registrations);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=students.xlsx");

		return ResponseEntity.ok().headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(excel.readAllBytes());
	}

}
