package com.ikubinfo.primefaces.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ikubinfo.primefaces.model.Film;
import com.ikubinfo.primefaces.service.FilmService;

@ManagedBean
@ViewScoped
public class FilmManagedBean implements Serializable {

	private static final long serialVersionUID = 3800933422824282320L;

	private List<Film> films;

	private FilmService filmService;

	@PostConstruct
	public void init() {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId");
		filmService = new FilmService();
		films = filmService.getFilmsByCategoryId(Integer.parseInt(value));
		
	}

	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
}
