package com.lowcost.managedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lowcost.entitiesbean.booking.BookingFacadeLocal;
import com.lowcost.entitiesbean.timetable.TimetableFacadeLocal;
import com.lowcost.entity.Bookingtable;
import com.lowcost.entity.Timetable;
import com.lowcost.entity.User;
import com.lowcost.letters.CreateTextForLetter;
import com.lowcost.letters.CreateTextForLetterTwo;
import com.lowcost.post.PostSender;

import org.apache.commons.lang3.RandomStringUtils;

@ManagedBean
@SessionScoped
public class BookingController implements Serializable {
	private Logger logger = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;
	@EJB
	private PostSender postEJB;
	@EJB
	private BookingFacadeLocal bookEJB;
	@EJB
	private TimetableFacadeLocal timetableEJB;
	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;
	@ManagedProperty(value = "#{homeController}")
	private HomeController homeController;
	@ManagedProperty(value = "#{cabinetController}")
	private CabinetController cabinetController;
	private Timestamp bookingDate;
	private String luggageTo;
	private String luggTo;
	private String luggageFrom;
	private String luggFrom;
	private String priorityTo;
	private Boolean priorTo;
	private String priorityFrom;
	private Boolean priorFrom;
	private BigDecimal fullPriceTo;
	private BigDecimal fullPriceFrom;
	private String status;
	private Timetable selectedFlightTo;
	private Timetable selectedFlightFrom;
	private int amount;
	private User user;
	private Bookingtable bookTo;
	private Bookingtable bookFrom;
	private BigDecimal fullPrice;
	private int newAmountTickets;
	private int newBookedAmountTickets;
	private String way;
	private String bookingNumber;
	private BigDecimal currentUAH;

	public BookingController() {
	}

	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);

	// /Creating booking to
	public String writeBookingTo() {
		way = homeController.getWay();
		selectedFlightTo = homeController.getSelectedFlightTo();
		amount = homeController.getAmount();
		user = loginController.getUser();
		status = "booking";
		bookingNumber = RandomStringUtils.random(6, 0, 30, true, true,
				"AS1D2F3G45J67890QWERVBXMZOPUZT".toCharArray());
		fullPriceTo = selectedFlightTo.getPrice().multiply(
				new BigDecimal(String.valueOf(amount)));
		fullPriceTo = fullPriceTo.add(
				new BigDecimal(String.valueOf(priorityTo))).add(
				new BigDecimal(String.valueOf(luggageTo)));
		bookingDate = new Timestamp(new Date().getTime());
		if (priorityTo.equals("25")) {
			priorTo = true;
		} else
			priorTo = false;
		if (luggageTo.equals("0")) {
			luggTo = "none";
		}
		if (luggageTo.equals("25")) {
			luggTo = "25kg";
		} else
			luggTo = "35kg";
		bookTo = new Bookingtable(amount, bookingNumber, bookingDate, luggTo,
				priorTo, status, fullPriceTo, user, selectedFlightTo);

		bookEJB.create(bookTo);
		newAmountTickets = selectedFlightTo.getAmoutTickets() - amount;
		selectedFlightTo.setAmoutTickets(newAmountTickets);
		newBookedAmountTickets = selectedFlightTo.getAmountBookedTickets()
				+ amount;
		selectedFlightTo.setAmountBookedTickets(newBookedAmountTickets);
		timetableEJB.edit(selectedFlightTo);			
		logger.info("Booking created");
		if (way.equals("One")) {
			createLetter();
		}
		if (way.equals("Two")) {
			writeBookingFrom();
			createLetterTwo();
		}
		nullNull();	
		cabinetController.afterLogin();
		return "/client/cabinet.privet?faces-redirect=true";
	}
	
	///null instance
	public void nullNull(){
	session.removeAttribute("flight");
	luggageTo=null;	
	luggageFrom=null;
	priorityTo=null;
	priorityFrom=null;
	homeController.makeNull();
	}

	// /show message
	public void showMessage(AjaxBehaviorEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Thank you",
				"Have a nice flight. Your booking is valid up to 3 days.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Creating booking from if exist
	public void writeBookingFrom() {
		selectedFlightFrom = homeController.getSelectedFlightFrom();
		fullPriceFrom = selectedFlightFrom.getPrice().multiply(
				new BigDecimal(String.valueOf(amount)));
		fullPriceFrom = fullPriceFrom.add(
				new BigDecimal(String.valueOf(priorityFrom))).add(
				new BigDecimal(String.valueOf(luggageFrom)));
		if (priorityFrom.equals("25")) {
			priorFrom = true;
		} else
			priorFrom = false;
		if (luggageFrom.equals("0")) {
			luggFrom = "none";
		}
		if (luggageFrom.equals("25")) {
			luggFrom = "25kg";
		} else
			luggFrom = "35kg";
		bookFrom = new Bookingtable(amount, bookingNumber, bookingDate,
				luggFrom, priorFrom, status, fullPriceFrom, user,
				selectedFlightFrom);
		newAmountTickets = selectedFlightFrom.getAmoutTickets() - amount;
		selectedFlightFrom.setAmoutTickets(newAmountTickets);
		newBookedAmountTickets = selectedFlightFrom.getAmountBookedTickets()
				+ amount;
		selectedFlightFrom.setAmountBookedTickets(newBookedAmountTickets);
		timetableEJB.edit(selectedFlightFrom);
		bookEJB.create(bookFrom);
	}

	// Create letter return ticket
	public void createLetterTwo() {
		BigDecimal full = fullPriceTo.add(fullPriceFrom);
		String context = CreateTextForLetterTwo.createLetterTwo(full,
				user.getName(), user.getSurname(), user.getPhone(),
				user.getEmail(), amount, selectedFlightTo.getFromCity(),
				selectedFlightTo.getToCity(),
				selectedFlightTo.getDateDeparture(),
				selectedFlightTo.getTimeDeparture(),
				selectedFlightTo.getDateArrival(),
				selectedFlightTo.getTimeArrival(),
				selectedFlightFrom.getFromCity(),
				selectedFlightFrom.getToCity(),
				selectedFlightFrom.getDateDeparture(),
				selectedFlightFrom.getTimeDeparture(),
				selectedFlightFrom.getDateArrival(),
				selectedFlightFrom.getTimeArrival(), bookingNumber);
		postEJB.sender(user.getEmail(), context);
	}

	// Create letter one way
	public void createLetter() {
		String context = CreateTextForLetter.createLetter(fullPriceTo,
				user.getName(), user.getSurname(), user.getPhone(),
				user.getEmail(), amount, selectedFlightTo.getFromCity(),
				selectedFlightTo.getToCity(),
				selectedFlightTo.getDateDeparture(),
				selectedFlightTo.getTimeDeparture(),
				selectedFlightTo.getDateArrival(),
				selectedFlightTo.getTimeArrival(), bookingNumber);
		postEJB.sender(user.getEmail(), context);
	}

	// /Total summ plus service
	public BigDecimal totalService() {
		BigDecimal totalPlus = (BigDecimal) session.getAttribute("total");
		BigDecimal currentDollar = new BigDecimal(String.valueOf(homeController
				.getCurrencyDollar()));
		if (priorityTo != null) {
			totalPlus = totalPlus
					.add(new BigDecimal(String.valueOf(priorityTo)));
			currentUAH = totalPlus.multiply(currentDollar);
		}
		if (luggageTo != null) {
			totalPlus = totalPlus
					.add(new BigDecimal(String.valueOf(luggageTo)));
			currentUAH = totalPlus.multiply(currentDollar);
		}
		if (priorityFrom != null) {
			totalPlus = totalPlus.add(new BigDecimal(String
					.valueOf(priorityFrom)));
			currentUAH = totalPlus.multiply(currentDollar);
		}
		if (priorityFrom != null) {
			totalPlus = totalPlus.add(new BigDecimal(String
					.valueOf(luggageFrom)));
			currentUAH = totalPlus.multiply(currentDollar);
		}
		return totalPlus;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public HomeController getHomeController() {
		return homeController;
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public String getLuggageTo() {
		return luggageTo;
	}

	public void setLuggageTo(String luggageTo) {
		this.luggageTo = luggageTo;
	}

	public String getLuggageFrom() {
		return luggageFrom;
	}

	public void setLuggageFrom(String luggageFrom) {
		this.luggageFrom = luggageFrom;
	}

	public String getPriorityTo() {
		return priorityTo;
	}

	public void setPriorityTo(String priorityTo) {
		this.priorityTo = priorityTo;
	}

	public String getPriorityFrom() {
		return priorityFrom;
	}

	public void setPriorityFrom(String priorityFrom) {
		this.priorityFrom = priorityFrom;
	}

	public BigDecimal getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(BigDecimal fullPrice) {
		this.fullPrice = fullPrice;
	}

	public void setFirstPriceTo(BigDecimal firstPriceTo) {
		this.fullPriceTo = firstPriceTo;
	}

	public void setFirstPriceFrom(BigDecimal firstPriceFrom) {
		this.fullPriceFrom = firstPriceFrom;
	}

	public BigDecimal getFirstPriceTo() {
		return fullPriceTo;
	}

	public BigDecimal getFirstPriceFrom() {
		return fullPriceFrom;
	}

	public CabinetController getCabinetController() {
		return cabinetController;
	}

	public void setCabinetController(CabinetController cabinetController) {
		this.cabinetController = cabinetController;
	}

	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public BigDecimal getCurrentUAH() {
		return currentUAH;
	}

	public void setCurrentUAH(BigDecimal currentUAH) {
		this.currentUAH = currentUAH;
	}

}
