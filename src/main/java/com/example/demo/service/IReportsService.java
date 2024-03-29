package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.Request.SearchReportRequest;
import com.example.demo.response.SearchReportResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface IReportsService {

	public List<String> uniquePlans();

	public List<String> planStatus();

	public List<SearchReportResponse> searchReport(SearchReportRequest request);

	public void downloadExcel(HttpServletResponse response) throws IOException ;

	public void downlodPdf(HttpServletResponse response) throws IOException ;

}
