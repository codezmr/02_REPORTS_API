package com.codezmr.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.codezmr.entity.EligibilityDetails;
import com.codezmr.repository.EligibilityDetailsRepo;
import com.codezmr.request.SearchRequest;
import com.codezmr.response.SearchResponse;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	EligibilityDetailsRepo eligRepo;

	@Override
	public List<String> getUniqePlanNames() {
		return eligRepo.findPlanNames();

	}

	@Override
	public List<String> getUniqePlanStatuses() {
		return eligRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {

		// Create query dynamically

		EligibilityDetails queryBuilder = new EligibilityDetails();

		String planName = request.getPlanName();
		if (planName != null && planName.equals(""))
			queryBuilder.setPlanName(planName);

		String planStatus = request.getPlanStatus();
		if (planName != null && planName.equals(""))
			queryBuilder.setPlanStatus(planStatus);

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = request.getPlanEndDate();
		if (planStartDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}

		Example<EligibilityDetails> example = Example.of(queryBuilder);

		List<SearchResponse> response = new ArrayList<>();

		List<EligibilityDetails> entities = eligRepo.findAll(example); // if example == null, then findAll will retrieve
																		// all the data.

		for (EligibilityDetails entity : entities) {

			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		}

		return response;
	}

	@Override
	public void genrateExcel(HttpServletResponse response) {

		List<EligibilityDetails> entities = eligRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(0).setCellValue("Email");
		headerRow.createCell(0).setCellValue("Mobile");
		headerRow.createCell(0).setCellValue("Gender");
		headerRow.createCell(0).setCellValue("SSN");
		
		int col = 1;
		for(EligibilityDetails entity : entities) {
			
			HSSFRow dataRow = sheet.createRow(col);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(2).setCellValue(entity.getEmail());
			dataRow.createCell(3).setCellValue(entity.getMobile());
			dataRow.createCell(4).setCellValue(entity.getGender().toString());
			dataRow.createCell(5).setCellValue(entity.getSsn());
			
			col++;
		}
	}

	@Override
	public void genratePdf(HttpServletResponse response) {

	}

}
