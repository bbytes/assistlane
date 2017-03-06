package com.auklabs.assistlane.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.auklabs.assistlane.domain.FaqArticle;
import com.auklabs.assistlane.domain.FaqCategory;
import com.auklabs.assistlane.dto.models.FaqArticleDTO;
import com.auklabs.assistlane.repository.FaqArticleRepository;
import com.auklabs.assistlane.repository.FaqCategoryRepository;

@Service
public class FaqArticleService extends AbstractService<FaqArticle, Long> {

	@Autowired
	private FaqArticleRepository faqArticleRepository;
	
	@Autowired
	private FaqCategoryRepository faqCategoryRepository;

	@Autowired
	private DTOToDomainConverstionService dtoToDomainConverstionService;

	public FaqArticleService(FaqArticleRepository faqArticleRepository) {
		super(faqArticleRepository);
		this.faqArticleRepository = faqArticleRepository;
	}

	public FaqArticle getById(Long id) {
		return faqArticleRepository.findOne(id);
	}

	@Transactional
	public FaqArticle createFaqArticle(FaqArticleDTO faqArticleDTO) {
		FaqArticle faqArticle = dtoToDomainConverstionService.convertFaqArticle(faqArticleDTO);
		return faqArticleRepository.save(faqArticle);
	}
	
	@Transactional
	public FaqArticle updateFaqArticle(Long id, FaqArticleDTO faqArticleDTO) {
		FaqArticle updateFaqArticle = faqArticleRepository.findOne(id);

		if (faqArticleDTO.getBody() != null)
			updateFaqArticle.setBody(faqArticleDTO.getBody());
		else
			updateFaqArticle.setBody(updateFaqArticle.getBody());

		if (faqArticleDTO.getTitle() != null)
			updateFaqArticle.setTitle(faqArticleDTO.getTitle());
		else
			updateFaqArticle.setTitle(updateFaqArticle.getTitle());

		if (faqArticleDTO.getFaqRelatedArticles() != null && !faqArticleDTO.getFaqRelatedArticles().isEmpty()) {

			Set<FaqArticle> faqArticles = new HashSet<FaqArticle>();
			for (FaqArticleDTO faqArticleDTO2 : faqArticleDTO.getFaqRelatedArticles()) {

				FaqArticle subfaqArticle = new FaqArticle();
				subfaqArticle.setTitle(faqArticleDTO2.getTitle());
				subfaqArticle.setBody(faqArticleDTO2.getBody());
				subfaqArticle.setPublish(faqArticleDTO2.getPublish());
				subfaqArticle.setKeywords(faqArticleDTO2.getKeywords());
				faqArticles.add(subfaqArticle);
			}
			updateFaqArticle.setFaqRelatedArticles(faqArticles);
		} else
			updateFaqArticle.setFaqRelatedArticles(updateFaqArticle.getFaqRelatedArticles());

		if (faqArticleDTO.getKeywords() != null && !faqArticleDTO.getKeywords().isEmpty())
			updateFaqArticle.setKeywords(faqArticleDTO.getKeywords());
		else
			updateFaqArticle.setKeywords(updateFaqArticle.getKeywords());

		updateFaqArticle.setPublish(faqArticleDTO.getPublish());
		updateFaqArticle.setCreationDate(updateFaqArticle.getCreationDate());

		return faqArticleRepository.save(updateFaqArticle);

	}
	
	@Transactional
	public FaqArticle addRelatedArticle(Long id1, Long id2) {
		FaqArticle updateFaqArticle = faqArticleRepository.findOne(id1);
		FaqArticle toBeAddedArticle = faqArticleRepository.findOne(id2);

		Set<FaqArticle> faqArticles = new HashSet<FaqArticle>();
		faqArticles.add(toBeAddedArticle);

		updateFaqArticle.setBody(updateFaqArticle.getBody());
		updateFaqArticle.setTitle(updateFaqArticle.getTitle());
		updateFaqArticle.setPublish(updateFaqArticle.getPublish());
		updateFaqArticle.setKeywords(updateFaqArticle.getKeywords());
		updateFaqArticle.setCreationDate(updateFaqArticle.getCreationDate());
		updateFaqArticle.setFaqRelatedArticles(faqArticles);

		return faqArticleRepository.save(updateFaqArticle);

	}
	
	@Transactional
	public FaqArticle addArticleInCategory(Long artilceId , Long categoryId){
		FaqArticle updateFaqArticle = faqArticleRepository.findOne(artilceId);
		
		FaqCategory category = faqCategoryRepository.findOne(categoryId);
		updateFaqArticle.setBody(updateFaqArticle.getBody());
		updateFaqArticle.setTitle(updateFaqArticle.getTitle());
		updateFaqArticle.setPublish(updateFaqArticle.getPublish());
		updateFaqArticle.setKeywords(updateFaqArticle.getKeywords());
		updateFaqArticle.setCreationDate(updateFaqArticle.getCreationDate());
		updateFaqArticle.setFaqRelatedArticles(updateFaqArticle.getFaqRelatedArticles());
		updateFaqArticle.setFaqCategory(category);
		
		return faqArticleRepository.save(updateFaqArticle);
		
	}

	public Page<FaqArticle> getAllFaqArticle(Pageable pageable) {
		return faqArticleRepository.findAll(pageable);
	}
	
	public List<FaqArticle> getAllFaqArticle() {
		return faqArticleRepository.findAll();
	}
	
	public Page<FaqArticle> getAllArticleInCategory(Long id,Pageable pageable) {
		Page<FaqArticle> faqArticles = faqArticleRepository.findByFaqCategoryId(id, pageable);
		return faqArticles;
	}

	@Transactional
	public void deleteFaqArticle(Long id) {
		FaqArticle faqArticle = getById(id);
		faqArticleRepository.delete(faqArticle);
	}

	@Transactional
	public void deleteAllFaqArticle() {
		faqArticleRepository.deleteAll();
	}

}
