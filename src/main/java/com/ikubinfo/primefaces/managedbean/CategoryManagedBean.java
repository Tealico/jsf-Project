package com.ikubinfo.primefaces.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.model.Category;
import com.ikubinfo.primefaces.service.CategoryService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class CategoryManagedBean implements Serializable {
	private static final long serialVersionUID = 3800933422824282320L;
	private Category category;

	private List<Category> categories;
	private String name;

	private CategoryService categoryService;

	@PostConstruct
	public void init() {
		categoryService = new CategoryService();
		categories = categoryService.getAll(null);
		category = new Category();

	}

	public void save() {
		if (categoryService.save(category)) {
			getAll();
			Messages.showInfoMessage("Category updated successfully");

		}
		category = new Category();

	}

	public void insert() {
		Category toAdd = new Category();
		toAdd.setName(name);

		if (categoryService.create(toAdd)) {
			Messages.showInfoMessage("Category was added successfully");
			getAll();
		}

	}

	public void filter() {
		categories = categoryService.getAll(name);
	}

	public void delete() {
		try {
			categoryService.delete(category);
			categories = categoryService.getAll(null);
			Messages.showInfoMessage("Deleted");

		} catch (CategoryInUseException e) {
			Messages.showWarningMessage(e.getMessage());
		}

	}

	public void getAll() {
		categories = categoryService.getAll(null);
	}

	public void reset() {
		name = null;
		filter();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
