package com.codezmr.services;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	EligibilityDetailsRepo eligRepo;
	
	@Autowired
	SearchRequest filterRequest;
	
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


		 filterRequest = request;
		
		List<SearchResponse> response = new ArrayList<>();

		List<EligibilityDetails> entities = eligRepo.findAll(filterSearch()); // if example == null, then findAll will retrieve
																						// all the data.

		for (EligibilityDetails entity : entities) {

			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		}

		return response;
	}

	private Example<EligibilityDetails> filterSearch(){
		EligibilityDetails queryBuilder = new EligibilityDetails();
		

		String planName = filterRequest.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}
			

		String planStatus = filterRequest.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}
			

		LocalDate planStartDate = filterRequest.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = filterRequest.getPlanEndDate();
		if (planEndDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}

		Example<EligibilityDetails> example = Example.of(queryBuilder);

		
		return example;
		
	}
	
	
	@Override
	public void genrateExcel(HttpServletResponse response) throws Exception {

		List<EligibilityDetails> entities = eligRepo.findAll(filterSearch());

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");

		int col = 1;
		for (EligibilityDetails entity : entities) {
			
			HSSFRow dataRow = sheet.createRow(col);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(String.valueOf(entity.getMobile()));
			dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(4).setCellValue(String.valueOf(entity.getSsn()));

			col++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	@Override
	public void genratePdf(HttpServletResponse response) throws Exception {
		List<EligibilityDetails> entities = eligRepo.findAll(filterSearch());

		Document documnet = new Document(PageSize.A4);

		PdfWriter.getInstance(documnet, response.getOutputStream());

		documnet.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph("Search Reports", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		documnet.add(p);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10f);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5f);

		font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mobile", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		
		for(EligibilityDetails entity: entities) {
			
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
			
		}

		documnet.add(table);
		
		documnet.close();

	}

}
