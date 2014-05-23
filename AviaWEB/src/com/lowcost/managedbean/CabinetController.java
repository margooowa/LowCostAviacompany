package com.lowcost.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import com.lowcost.entitiesbean.booking.BookingFacadeLocal;
import com.lowcost.entitiesbean.sold.SoldFacadeLocal;
import com.lowcost.entity.Bookingtable;
import com.lowcost.entity.Soldticket;
import com.lowcost.entity.User;

@ManagedBean
@SessionScoped
public class CabinetController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	BookingFacadeLocal bookEJB;
	@EJB
	SoldFacadeLocal soldEJB;
	private List<Bookingtable> book;
	private List<Soldticket> sold;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);

	public CabinetController() {
	}

	public void bookSold() throws IOException {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			String urlPre = FacesContext.getCurrentInstance().getViewRoot()
					.getViewId();
			session.setAttribute("urlPre", urlPre);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("login.privet");
		}
		book = bookEJB.findByClientId(user.getId());
		sold = soldEJB.findByClientID(user.getId());
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cabinet.privet");
	}

	// /after login insert all inforamtion
	public void afterLogin() {
		User user = (User) session.getAttribute("user");
		book = bookEJB.findByClientId(user.getId());
		sold = soldEJB.findByClientID(user.getId());
	}

	public List<Bookingtable> getBook() {
		return book;
	}

	public void setBook(List<Bookingtable> book) {
		this.book = book;
	}

	public List<Soldticket> getSold() {
		return sold;
	}

	public void setSold(List<Soldticket> sold) {
		this.sold = sold;
	}

}
