package com.auklabs.assistlane.web.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.auklabs.assistlane.domain.FaqCategory;
import com.auklabs.assistlane.dto.models.FaqCategoryDTO;
import com.auklabs.assistlane.resource.FaqCategoryResource;
import com.auklabs.assistlane.resource.assembler.FaqCategoryResourceAssembler;
import com.auklabs.assistlane.service.FaqCategoryService;

@RestController
@RequestMapping(value = "faqCategories")
public class FaqCategoryController {

	@Autowired
	private FaqCategoryService faqCategoryService;

	@Autowired
	private FaqCategoryResourceAssembler faqResourseAssembler;

	@Autowired
	private PagedResourcesAssembler<FaqCategory> pagedResourcesAssembler;

	/**
	 * with this Get All Category Data as well as Article Data
	 * 
	 * @param pageable
	 * @return All FaqCategory
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PagedResources> getAllFaqCategory(@PageableDefault Pageable pageable) {
		Page<FaqCategory> faqCategoryPage = faqCategoryService.getAllFaqCategory(pageable);
		PagedResources adminPagedResources = pagedResourcesAssembler.toResource(faqCategoryPage, faqResourseAssembler);

		if (faqCategoryPage.getContent() == null || faqCategoryPage.getContent().isEmpty()) {
			EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
			EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(FaqCategory.class);
			List<EmbeddedWrapper> embedded = Collections.singletonList(wrapper);
			adminPagedResources = new PagedResources(embedded, adminPagedResources.getMetadata(),
					adminPagedResources.getLinks());
		}
		return new ResponseEntity<PagedResources>(adminPagedResources, HttpStatus.OK);
	}

	/**
	 * With this Api get Both the Category data and Article Data
	 * 
	 * @param id
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FaqCategoryResource> getFaqCategory(@PathVariable Long id) {
		FaqCategory faqCategory = faqCategoryService.getOnlyFaqCategory(id, true);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * With this Api get Both the Category data and Article Data
	 * 
	 * @param categoryId
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<FaqCategoryResource> getFaqCategory(@PathVariable String categoryId) {
		FaqCategory faqCategory = faqCategoryService.getOnlyFaqCategory(categoryId, true);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * With this Api get Only the Category data not Article Data
	 * 
	 * @param id
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<FaqCategoryResource> getOnlyFaqCategory(@PathVariable Long id) {
		FaqCategory faqCategory = faqCategoryService.getOnlyFaqCategory(id, false);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * With this Api get Only the Category data not Article Data
	 * 
	 * @param categoryId
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/category/get/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<FaqCategoryResource> getOnlyFaqCategory(@PathVariable String categoryId) {
		FaqCategory faqCategory = faqCategoryService.getOnlyFaqCategory(categoryId, false);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * with this Get All Category Data without Article Data
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<PagedResources> getAllOnlyFaqCategory() {
		Page<FaqCategory> faqCategoryPage = faqCategoryService.getAllOnlyFaqCategory();
		PagedResources adminPagedResources = pagedResourcesAssembler.toResource(faqCategoryPage, faqResourseAssembler);
		if (faqCategoryPage.getContent() == null || faqCategoryPage.getContent().isEmpty()) {
			EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
			EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(FaqCategory.class);
			List<EmbeddedWrapper> embedded = Collections.singletonList(wrapper);
			adminPagedResources = new PagedResources(embedded, adminPagedResources.getMetadata(),
					adminPagedResources.getLinks());
		}
		return new ResponseEntity<PagedResources>(adminPagedResources, HttpStatus.OK);
	}

	/**
	 * @param faqCategoryDTO
	 * @return FaqCategoryResource
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<FaqCategoryResource> saveFaqCategory(@RequestBody FaqCategoryDTO faqCategoryDTO) {
		FaqCategory faqCategory = faqCategoryService.createFaqCategory(faqCategoryDTO);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.CREATED);
	}

	/**
	 * @param id
	 * @param faqCategoryDTO
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<FaqCategoryResource> updateFaqCategory(@PathVariable Long id,
			@RequestBody FaqCategoryDTO faqCategoryDTO) {
		FaqCategory faqCategory = faqCategoryService.updateFaqCategory(id, faqCategoryDTO);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * @param categoryId
	 * @param faqCategoryDTO
	 * @return FaqCategoryResource
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<FaqCategoryResource> updateFaqCategory(@PathVariable String categoryId,
			@RequestBody FaqCategoryDTO faqCategoryDTO) {
		FaqCategory faqCategory = faqCategoryService.updateFaqCategory(categoryId, faqCategoryDTO);
		FaqCategoryResource rsource = faqResourseAssembler.toResource(faqCategory);
		return new ResponseEntity<FaqCategoryResource>(rsource, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteFaqCategory(@PathVariable Long id) {
		faqCategoryService.deleteFaqCategory(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteFaqCategory(@PathVariable String categoryId) {
		faqCategoryService.deleteFaqCategory(categoryId);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAllFaqCategory() {
		faqCategoryService.deleteAllFaqCategory();
		return ResponseEntity.noContent().build();
	}
}
