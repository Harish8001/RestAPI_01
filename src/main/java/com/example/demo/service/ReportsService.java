package com.example.demo.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.Request.SearchReportRequest;
import com.example.demo.entity.Reports;
import com.example.demo.repo.EligibilityRepo;
import com.example.demo.response.SearchReportResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportsService implements IReportsService {

	@Autowired
	private EligibilityRepo repo;

	@Override
	public List<String> uniquePlans() {
		// TODO Auto-generated method stub
		return repo.findDistinctColumnName();

	}

	@Override
	public List<String> planStatus() {

		return repo.findDistinctStatus();
	}

	@Override
	public List<SearchReportResponse> searchReport(SearchReportRequest request) {
		List<SearchReportResponse> response = new ArrayList();
		Reports report = new Reports();
		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			report.setPlanName(planName);
		}
		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			report.setPlanStatus(planStatus);
		}
		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			report.setPlanStartDate(planStartDate);
		}
		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			report.setPlanEndDate(planEndDate);
		}
		Example<Reports> example = Example.of(report);
		List<Reports> all = repo.findAll(example);
		for (Reports reports : all) {
			SearchReportResponse sr = new SearchReportResponse();
			BeanUtils.copyProperties(reports, sr);
			response.add(sr);
		}
		return response;
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		List<Reports> all = repo.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("SLNO");
		row.createCell(1).setCellValue("NAME");
		row.createCell(2).setCellValue("PHONE");
		row.createCell(3).setCellValue("SSN");
		int i = 1;
		for (Reports report : all) {

			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(report.getElgId());
			dataRow.createCell(1).setCellValue(report.getName());
			dataRow.createCell(2).setCellValue(report.getMobile());
			dataRow.createCell(3).setCellValue(report.getPlanName());
			i++;
		}
		;

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

	@Override
	public void downlodPdf(HttpServletResponse response) throws IOException {
		 Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph p = new Paragraph("Report", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	        table.setSpacingBefore(10);
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	         font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("SLNO", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("NAME", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("EMAIL", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("PLAN", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("PLAN STATUS", font));
	        table.addCell(cell); 
			List<Reports> all = repo.findAll();

	        for (Reports user : all) {
	            table.addCell(String.valueOf(user.getElgId()));
	            table.addCell(user.getName());
	            table.addCell(user.getEmail());
	            table.addCell(user.getPlanName());
	            table.addCell(user.getPlanStatus());
	        }
	         
	       
	        document.add(table);
	         
	        document.close();
	}
}
