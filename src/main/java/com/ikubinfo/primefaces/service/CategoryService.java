package com.ikubinfo.primefaces.service;

import java.util.Date;
import java.util.List;

import com.ikubinfo.datasource.DatasourceManager;
import com.ikubinfo.primefaces.model.Category;
import com.ikubinfo.primefaces.repository.CategoryRepository;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService() {
		categoryRepository = new CategoryRepository(DatasourceManager.getDataSource());
	}

	public List<Category> getAll(String name) {
		return categoryRepository.getAll(name);
	}

	public boolean save(Category category) {
		category.setLastUpdated(new Date());
		return categoryRepository.save(category);

	}

	public boolean create(Category category) {
		category.setLastUpdated(new Date());
		return categoryRepository.create(category);

	}

	public void delete(Category category) throws CategoryInUseException {
		if (categoryRepository.isCategoryInUse(category)) {
			throw new CategoryInUseException("Cannot delete this category because it is already in use.");
		} else {
			categoryRepository.delete(category);
		}

	}

}
