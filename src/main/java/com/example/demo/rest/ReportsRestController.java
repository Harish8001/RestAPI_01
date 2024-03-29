package com.example.demo.rest;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Request.SearchReportRequest;
import com.example.demo.response.SearchReportResponse;
import com.example.demo.service.IReportsService;
import com.example.demo.service.ReportsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportsRestController {

	@Autowired
	public ReportsService service;

	@GetMapping("/Plans")
	public ResponseEntity<List<String>> getPlans() {
		List<String> uniquePlans = service.uniquePlans();
		return new ResponseEntity<>(uniquePlans, HttpStatus.OK);
	}

	@GetMapping("/Statuses")
	public ResponseEntity<List<String>> getStatus() {
		List<String> planStatus = service.planStatus();
		return new ResponseEntity<>(planStatus, HttpStatus.OK);

	}

	@PostMapping("/Search")
	public ResponseEntity<List<SearchReportResponse>> search(@RequestBody SearchReportRequest entity) {
		List<SearchReportResponse> searchReport = service.searchReport(entity);
		return new ResponseEntity<>(searchReport, HttpStatus.OK);
	}

	@GetMapping("/excel")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.xls";
		response.setHeader(headerKey, headerValue);
		service.downloadExcel(response);
	}

	@GetMapping("/pdf")
	public void downloadPDF(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.pdf";
		response.setHeader(headerKey, headerValue);
		service.downlodPdf(response);

	}

}
