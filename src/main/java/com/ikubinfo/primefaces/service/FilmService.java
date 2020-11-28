package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Film;
import com.ikubinfo.primefaces.repository.FilmRepository;

public class FilmService {
	private FilmRepository filmRepository = new FilmRepository();
	
	public List<Film> getFilmsByCategoryId(int category_id) {
		List<Film> films = filmRepository.getAllFilmsByCategoryId(category_id);
		return films;
	}
}
