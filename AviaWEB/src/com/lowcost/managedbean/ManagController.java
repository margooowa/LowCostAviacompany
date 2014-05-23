package com.lowcost.managedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;

import com.lowcost.entitiesbean.booking.BookingFacadeLocal;
import com.lowcost.entitiesbean.sold.SoldFacadeLocal;
import com.lowcost.entitiesbean.timetable.TimetableFacadeLocal;
import com.lowcost.entitiesbean.user.UserFacadeLocal;
import com.lowcost.entity.Bookingtable;
import com.lowcost.entity.Soldticket;
import com.lowcost.entity.Timetable;
import com.lowcost.entity.User;

@ManagedBean
@SessionScoped
public class ManagController implements Serializable {

	private Logger logger = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;
	@EJB
	BookingFacadeLocal bookEJB;
	@EJB
	SoldFacadeLocal soldEJB;
	@EJB
	UserFacadeLocal userEJB;
	@EJB
	TimetableFacadeLocal flightEJB;
	@EJB
	TimetableFacadeLocal timetableEJB;
	private List<User> users;
	private List<Bookingtable> bookings;
	private List<Soldticket> solds;
	private List<Timetable> flights;
	private Map<String, String> statuses;
	private List<Bookingtable> selectedBook;
	private Map<String, String> fromList;
	private Map<String, String> toList;
	private Timetable changeFlight;
	private int newSoldAmount;
	private int newBookAmount;
	private Timestamp soldDate;
	private String amoutTickets;
	private Date dateArrival;
	private Date dateDeparture;
	private String flightName;
	private String fromCity;
	private Date timeArrival;
	private Date timeDeparture;
	private String toCity;
	private String price;
	List<Bookingtable> listOnlySold;

	public ManagController() {
	}

	@PostConstruct
	public void init() {
		users = userEJB.findAllClients();
		bookings = bookEJB.findAll();
		solds = soldEJB.findAll();
		flights = flightEJB.findAll();
		fromList = new LinkedHashMap<>();
		fromList.put("Kiev", "Kiev");
		fromList.put("Rome", "Rome");
		fromList.put("Paris", "Paris");
		fromList.put("Berlin", "Berlin");
		toList = new LinkedHashMap<>();
		toList.put("Kiev", "Kiev");
		toList.put("Rome", "Rome");
		toList.put("Paris", "Paris");
		toList.put("Berlin", "Berlin");
	}

	public Map<String, String> getFromList() {
		return fromList;
	}

	public void setFromList(Map<String, String> fromList) {
		this.fromList = fromList;
	}

	public Map<String, String> getToList() {
		return toList;
	}

	public void setToList(Map<String, String> toList) {
		this.toList = toList;
	}

	// Status changes
	public Map<String, String> changeStatus() {
		statuses = new LinkedHashMap<String, String>();
		statuses.put("booking", "booking");
		statuses.put("sold", "sold");
		return statuses;
	}

	// /Listener to edit cells
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	// /add to sold and delete
	public void addToSold() {
		soldDate = new Timestamp(new Date().getTime());
		for (Bookingtable boo : selectedBook) {
			if (boo.getStatus().equals("sold")) {
				Soldticket sold = new Soldticket(boo.getBookingNumber(),
						boo.getAmount(), boo.getLuggage(), boo.getPriority(),
						soldDate, boo.getFullPrice(), boo.getUser(),
						boo.getTimetable());
				soldEJB.create(sold);
				changeFlight = boo.getTimetable();
				newBookAmount = changeFlight.getAmountBookedTickets()
						- boo.getAmount();
				changeFlight.setAmountBookedTickets(newBookAmount);
				newSoldAmount = changeFlight.getAmountSoldTickets()
						+ boo.getAmount();
				changeFlight.setAmountSoldTickets(newSoldAmount);
				timetableEJB.edit(changeFlight);
				bookEJB.remove(boo);
				bookings = bookEJB.findAll();
				solds = soldEJB.findAll();

				logger.info("Book is deleted and new sold");
			} else
				continue;

		}
	}

	// /New flight
	public void addFlight() {
		int amount = Integer.parseInt(amoutTickets);
		int amountBookedTickets = 0;
		int amountSoldTickets = 0;
		java.sql.Date dateDepartur = new java.sql.Date(dateDeparture.getTime());
		java.sql.Date dateArr = new java.sql.Date(dateArrival.getTime());
		BigDecimal prc = new BigDecimal(price);
		java.sql.Date timeAA = new java.sql.Date(timeArrival.getTime());
		Time arriv = new Time(timeAA.getTime());
		java.sql.Date timeDD = new java.sql.Date(timeDeparture.getTime());
		Time depart = new Time(timeDD.getTime());
		Timetable newFlight = new Timetable(amount, amountBookedTickets,
				amountSoldTickets, dateArr, dateDepartur, flightName, fromCity,
				arriv, depart, toCity, prc);
		flightEJB.create(newFlight);
		logger.info("New flight was created");
		makeNull();
	}

	// /null
	public void makeNull() {
		setDateArrival(null);
		setAmoutTickets(null);
		setDateDeparture(null);
		setFlightName(null);
		setFromCity(null);
		setTimeArrival(null);
		setTimeDeparture(null);
		setToCity(null);
		setPrice(null);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Bookingtable> getBookings() {
		return bookings;
	}

	public void setBookings(List<Bookingtable> bookings) {
		this.bookings = bookings;
	}

	public List<Soldticket> getSolds() {
		return solds;
	}

	public void setSolds(List<Soldticket> solds) {
		this.solds = solds;
	}

	public List<Timetable> getFlights() {
		return flights;
	}

	public void setFlights(List<Timetable> flights) {
		this.flights = flights;
	}

	public List<Bookingtable> getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(List<Bookingtable> selectedBook) {
		this.selectedBook = selectedBook;
	}

	public String getAmoutTickets() {
		return amoutTickets;
	}

	public void setAmoutTickets(String amoutTickets) {
		this.amoutTickets = amoutTickets;
	}

	public Date getDateArrival() {
		return dateArrival;
	}

	public void setDateArrival(Date dateArrival) {
		this.dateArrival = dateArrival;
	}

	public Date getDateDeparture() {
		return dateDeparture;
	}

	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public Date getTimeArrival() {
		return timeArrival;
	}

	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Date getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(Date timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
