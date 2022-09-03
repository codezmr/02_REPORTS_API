package com.codezmr.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.codezmr.request.SearchRequest;
import com.codezmr.response.SearchResponse;

public interface ReportsService {
	
	public List<String> getUniqePlanNames();
	
	public List<String> getUniqePlanStatuses();
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void genrateExcel(HttpServletResponse response) throws Exception;
	public void genratePdf(HttpServletResponse response) throws Exception;
}
