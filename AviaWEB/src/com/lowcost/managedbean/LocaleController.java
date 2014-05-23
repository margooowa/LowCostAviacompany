package com.lowcost.managedbean;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.BasicConfigurator;

@ManagedBean
@SessionScoped
public class LocaleController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Locale locale = FacesContext.getCurrentInstance().getViewRoot()
			.getLocale();

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		changeLanguage(language);
	}

	public void changeLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot()
				.setLocale(new Locale("uk_UA"));
	}

}
