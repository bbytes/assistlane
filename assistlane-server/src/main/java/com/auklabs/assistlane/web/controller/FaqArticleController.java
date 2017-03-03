package com.auklabs.assistlane.web.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.auklabs.assistlane.domain.FaqArticle;
import com.auklabs.assistlane.domain.FaqCategory;
import com.auklabs.assistlane.dto.models.FaqArticleDTO;
import com.auklabs.assistlane.resource.FaqArticleResource;
import com.auklabs.assistlane.resource.assembler.FaqArticleResourceAssembler;
import com.auklabs.assistlane.service.FaqArticleService;

@RestController
@RequestMapping(value = "/faqArticles")
public class FaqArticleController {

	@Autowired
	private FaqArticleService faqArticleService;
	
	@Autowired
	private FaqArticleResourceAssembler faqArticleResourseAssembler;

	@Autowired
	private PagedResourcesAssembler<FaqArticle> pagedResourcesAssembler;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PagedResources> getAllFaqArticle(Pageable pageable) {
		Page<FaqArticle> faqArticlePage = faqArticleService.getAllFaqArticle(pageable);
		PagedResources pagedResources = pagedResourcesAssembler.toResource(faqArticlePage, faqArticleResourseAssembler);

		if (faqArticlePage.getContent() == null || faqArticlePage.getContent().isEmpty()) {
			EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
			EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(FaqCategory.class);
			List<EmbeddedWrapper> embedded = Collections.singletonList(wrapper);
			pagedResources = new PagedResources(embedded, pagedResources.getMetadata(),
					pagedResources.getLinks());
		}
		return new ResponseEntity<PagedResources>(pagedResources, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FaqArticleResource> getFaqArticle(@PathVariable Long id){
		FaqArticle faqArticle = faqArticleService.getById(id);
		FaqArticleResource rsource = faqArticleResourseAssembler.toResource(faqArticle);
		return  new ResponseEntity<FaqArticleResource>(rsource, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<FaqArticleResource> saveFaqArticle(@RequestBody FaqArticleDTO faqArticleDTO){
		FaqArticle faqArticle = faqArticleService.createFaqArticle(faqArticleDTO);
		FaqArticleResource rsource = faqArticleResourseAssembler.toResource(faqArticle);
		return  new ResponseEntity<FaqArticleResource>(rsource, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAllFaqArticle(){
		faqArticleService.deleteAllFaqArticle();
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteFaqArticle(@PathVariable Long id){
		faqArticleService.deleteFaqArticle(id);
		return ResponseEntity.noContent().build();
	}
}