package com.auklabs.assistlane.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.MediaType;
import com.auklabs.assistlane.AssistlaneAppApplicationTests;
import com.auklabs.assistlane.domain.FaqCategory;
import com.auklabs.assistlane.dto.models.FaqArticleDTO;
import com.auklabs.assistlane.dto.models.FaqCategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class FaqCategoryControllerTest extends AssistlaneAppApplicationTests {

	@After
	public void cleanup(){
		faqCategoryRepository.deleteAll();
	}
	
	@Test
	public void saveFaqCategoryTest() throws Exception{
		
		FaqArticleDTO faqArticleDTO2 = new FaqArticleDTO();
		faqArticleDTO2.setBody("Article2");
		faqArticleDTO2.setPublish(false);
		Set<String> keywords2 = new HashSet<String>();
		keywords2.add("Java2");
		keywords2.add("login2");
		faqArticleDTO2.setKeywords(keywords2);
		Set<FaqArticleDTO> faqArticleDTOs2 = new HashSet<FaqArticleDTO>();
		faqArticleDTO2.setFaqRelatedArticles(faqArticleDTOs2);
		
		
		FaqArticleDTO faqArticleDTO3 = new FaqArticleDTO();
		faqArticleDTO3.setBody("Article3");
		faqArticleDTO3.setPublish(false);
		Set<String> keywords3 = new HashSet<String>();
		keywords3.add("Java3");
		keywords3.add("login3");
		faqArticleDTO3.setKeywords(keywords3);
		Set<FaqArticleDTO> faqArticleDTOs3 = new HashSet<FaqArticleDTO>();
		faqArticleDTO3.setFaqRelatedArticles(faqArticleDTOs3);
		
		
		FaqArticleDTO faqArticleDTO1 = new FaqArticleDTO();
		faqArticleDTO1.setBody("Article1");
		faqArticleDTO1.setPublish(false);
		Set<String> keywords1 = new HashSet<String>();
		keywords1.add("Java1");
		keywords1.add("login1");
		faqArticleDTO1.setKeywords(keywords1);
		
		Set<FaqArticleDTO> faqArticleDTOs1 = new HashSet<FaqArticleDTO>();
		faqArticleDTOs1.add(faqArticleDTO2);
		faqArticleDTOs1.add(faqArticleDTO3);
		faqArticleDTO1.setFaqRelatedArticles(faqArticleDTOs1);
		
		Set<FaqArticleDTO> faqArticleDTOs = new HashSet<FaqArticleDTO>();
		faqArticleDTOs.add(faqArticleDTO1);
		
		FaqCategoryDTO faqCategoryDTO = new FaqCategoryDTO();
		faqCategoryDTO.setDisplayName("Sales");
		faqCategoryDTO.setSummary("It belongs To Sales Department");
		faqCategoryDTO.setFaqArticleDTO(faqArticleDTOs);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(faqCategoryDTO);
		
		mockMvc.perform(post("/api/v1/category/save").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().is2xxSuccessful()).andDo(print());
		 
	}
	
	@Test
	public void getAllFaqCategory() throws Exception{
		
		FaqArticleDTO faqArticleDTO2 = new FaqArticleDTO();
		faqArticleDTO2.setBody("Article2");
		faqArticleDTO2.setPublish(false);
		Set<String> keywords2 = new HashSet<String>();
		keywords2.add("Java2");
		keywords2.add("login2");
		faqArticleDTO2.setKeywords(keywords2);
		Set<FaqArticleDTO> faqArticleDTOs2 = new HashSet<FaqArticleDTO>();
		faqArticleDTO2.setFaqRelatedArticles(faqArticleDTOs2);
		
		
		FaqArticleDTO faqArticleDTO3 = new FaqArticleDTO();
		faqArticleDTO3.setBody("Article3");
		faqArticleDTO3.setPublish(false);
		Set<String> keywords3 = new HashSet<String>();
		keywords3.add("Java3");
		keywords3.add("login3");
		faqArticleDTO3.setKeywords(keywords3);
		Set<FaqArticleDTO> faqArticleDTOs3 = new HashSet<FaqArticleDTO>();
		faqArticleDTO3.setFaqRelatedArticles(faqArticleDTOs3);
		
		
		FaqArticleDTO faqArticleDTO1 = new FaqArticleDTO();
		faqArticleDTO1.setBody("Article1");
		faqArticleDTO1.setPublish(false);
		Set<String> keywords1 = new HashSet<String>();
		keywords1.add("Java1");
		keywords1.add("login1");
		faqArticleDTO1.setKeywords(keywords1);
		
		Set<FaqArticleDTO> faqArticleDTOs1 = new HashSet<FaqArticleDTO>();
		faqArticleDTOs1.add(faqArticleDTO2);
		faqArticleDTOs1.add(faqArticleDTO3);
		faqArticleDTO1.setFaqRelatedArticles(faqArticleDTOs1);
		
		Set<FaqArticleDTO> faqArticleDTOs = new HashSet<FaqArticleDTO>();
		faqArticleDTOs.add(faqArticleDTO1);
		
		FaqCategoryDTO faqCategoryDTO = new FaqCategoryDTO();
		faqCategoryDTO.setDisplayName("Sales");
		faqCategoryDTO.setSummary("It belongs To Sales Department");
		faqCategoryDTO.setFaqArticleDTO(faqArticleDTOs);
		
		faqCategoryService.createFaqCategory(faqCategoryDTO);
	
		mockMvc.perform(get("/api/v1/category/getAllCategory")).andExpect(status().isOk()).andDo(print());
	}
	
	
	public void deleteFaqCategory() throws Exception{
		
		FaqArticleDTO faqArticleDTO2 = new FaqArticleDTO();
		faqArticleDTO2.setBody("Article2");
		faqArticleDTO2.setPublish(false);
		Set<String> keywords2 = new HashSet<String>();
		keywords2.add("Java2");
		keywords2.add("login2");
		faqArticleDTO2.setKeywords(keywords2);
		Set<FaqArticleDTO> faqArticleDTOs2 = new HashSet<FaqArticleDTO>();
		faqArticleDTO2.setFaqRelatedArticles(faqArticleDTOs2);
		
		
		FaqArticleDTO faqArticleDTO3 = new FaqArticleDTO();
		faqArticleDTO3.setBody("Article3");
		faqArticleDTO3.setPublish(false);
		Set<String> keywords3 = new HashSet<String>();
		keywords3.add("Java3");
		keywords3.add("login3");
		faqArticleDTO3.setKeywords(keywords3);
		Set<FaqArticleDTO> faqArticleDTOs3 = new HashSet<FaqArticleDTO>();
		faqArticleDTO3.setFaqRelatedArticles(faqArticleDTOs3);
		
		
		FaqArticleDTO faqArticleDTO1 = new FaqArticleDTO();
		faqArticleDTO1.setBody("Article1");
		faqArticleDTO1.setPublish(false);
		Set<String> keywords1 = new HashSet<String>();
		keywords1.add("Java1");
		keywords1.add("login1");
		faqArticleDTO1.setKeywords(keywords1);
		
		Set<FaqArticleDTO> faqArticleDTOs1 = new HashSet<FaqArticleDTO>();
		faqArticleDTOs1.add(faqArticleDTO2);
		faqArticleDTOs1.add(faqArticleDTO3);
		faqArticleDTO1.setFaqRelatedArticles(faqArticleDTOs1);
		
		Set<FaqArticleDTO> faqArticleDTOs = new HashSet<FaqArticleDTO>();
		faqArticleDTOs.add(faqArticleDTO1);
		
		FaqCategoryDTO faqCategoryDTO = new FaqCategoryDTO();
		faqCategoryDTO.setDisplayName("Sales");
		faqCategoryDTO.setSummary("It belongs To Sales Department");
		faqCategoryDTO.setFaqArticleDTO(faqArticleDTOs);
		
		FaqCategory faqCategory = faqCategoryService.createFaqCategory(faqCategoryDTO);
		Long id = faqCategory.getId();
		
		mockMvc.perform(delete("/api/v1/category/delete/{id}", id))
		.andExpect(status().isNoContent()).andDo(print());
	}
	
	
}