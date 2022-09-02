package com.codezmr.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.codezmr.request.SearchRequest;
import com.codezmr.response.SearchResponse;

@Service
public class ReportsServiceImpl implements ReportsService{

	@Override
	public List<String> getUniqePlanNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUniqePlanStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void genrateExcel(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genratePdf(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	
}
