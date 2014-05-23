package com.lowcost.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lowcost.entitiesbean.timetable.TimetableFacadeLocal;
import com.lowcost.entity.Timetable;
import com.lowcost.entity.User;

@ManagedBean
@SessionScoped
public class HomeController implements Serializable {
	private Logger logger = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;
	@EJB
	private TimetableFacadeLocal flightEJB;
	private Timetable selectedFlightTo;
	private Timetable selectedFlightFrom;
	private List<Timetable> flight;
	private List<Timetable> flightTo;
	private List<Timetable> flightFrom;
	private static List<Timetable> allFlight;
	private List<Timetable> findToCityList;
	private Map<String, String> fromValue;
	private Map<String, String> toValue;
	private java.util.Date today;
	private Date dateDeparture;
	private Date dateReturn;
	private String way;
	private String from;
	private String to;
	private String amoutTickets;
	private String currencyDollar;
	private int amount;
	private BigDecimal total;
	private BigDecimal totalUAH;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);

	public HomeController() {
		way = "One";
		amoutTickets = "1";
		today = new java.util.Date();
		dateDeparture = today;
	}
/// date picker
	public void handleDateSelect(SelectEvent event) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
	}

	// /Туда и обратно
	public String findNeedFlightTwo() throws ParseException, IOException {
		amount = Integer.parseInt(amoutTickets);
		java.sql.Date dateDepartur = new java.sql.Date(dateDeparture.getTime());
		flightTo = flightEJB.findNeedFlights(amount, dateDepartur, from, to);
		session.setAttribute("flight", flightTo);
		BasicConfigurator.configure();
		if (dateReturn != null) {
			java.sql.Date dateRetur = new java.sql.Date(dateReturn.getTime());
			flightFrom = flightEJB.findNeedFlights(amount, dateRetur, to, from);
		}
		logger.info("Flight was found");
		xmlRead();
		return "/client/needFlightTwo.privet?faces-redirect=true";
	}

	// /get currency from http://bank-ua.com/export/currrate.xml
	public void xmlRead() {
		try {
			URL xmlUrl = new URL("http://bank-ua.com/export/currrate.xml");
			InputStream in = xmlUrl.openStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("char3").item(0)
							.getTextContent().equals("USD")) {
						String curr = eElement.getElementsByTagName("rate")
								.item(0).getTextContent();
						currencyDollar = curr.substring(0, 2) + '.'
								+ curr.substring(2, 4);

					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	// reset data
	public void makeNull() {
		setSelectedFlightTo(null);
		setSelectedFlightFrom(null);
		setDateReturn(null);
		setFrom(null);
		setTo(null);
	}

	// /Show flight on needFlightTwo.xhtml
	public String showFlight() throws IOException {
		String page = null;
		try {
			User u = (User) session.getAttribute("user");
			if (u != null) {
				page = "/client/luggage.privet?faces-redirect=true";
			} else {
				String urlPre = FacesContext.getCurrentInstance().getViewRoot()
						.getViewId();
				session.setAttribute("urlPre", urlPre);
				page = "/login.privet?faces-redirect=true";
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return page;
	}

	// Зависимые выпадающие списки
	public Map<String, String> fromList() {
		fromValue = new LinkedHashMap<String, String>();
		allFlight = flightEJB.findAll();
		for (Timetable dd : allFlight) {
			fromValue.put(dd.getFromCity(), dd.getFromCity());
		} // label, value
		return fromValue;
	}

	public Map<String, String> toList() {
		toValue = new LinkedHashMap<String, String>();
		findToCityList = flightEJB.findToCity(from);
		for (Timetable dd : findToCityList) {
			toValue.put(dd.getToCity(), dd.getToCity());
		} // label, value
		return toValue;
	}

	// /Listener for table
	public void onRowSelect(SelectEvent event) {
		logger.info("row " + ((Timetable) event.getObject()).getFlightName());
	}

	// /Count price to
	public BigDecimal priceTo() {
		BigDecimal priceTo = selectedFlightTo.getPrice().multiply(
				new BigDecimal(String.valueOf(amount)));
		return priceTo;
	}

	// /Count price from
	public BigDecimal priceFrom() {
		BigDecimal priceFrom = selectedFlightFrom.getPrice().multiply(
				new BigDecimal(String.valueOf(amount)));
		return priceFrom;
	}

	// /Tolal
	public BigDecimal total() {
		if (selectedFlightFrom != null) {
			total = priceFrom().add(priceTo());
		} else
			total = priceTo();
		totalUAH = total
				.multiply(new BigDecimal(String.valueOf(currencyDollar)));
		session.setAttribute("total", total);
		return total;
	}

	// ////////////
	public Map<String, String> getFromValue() {
		return fromValue;
	}

	public Map<String, String> getToValue() {
		return toValue;
	}

	public List<Timetable> getFlightTo() {
		return flightTo;
	}

	public void setFlightTo(List<Timetable> flightTo) {
		this.flightTo = flightTo;
	}

	public List<Timetable> getFlightFrom() {
		return flightFrom;
	}

	public void setFlightFrom(List<Timetable> flightFrom) {
		this.flightFrom = flightFrom;
	}

	public List<Timetable> getFlight() {
		return flight;
	}

	public void setFlight(List<Timetable> flight) {
		this.flight = flight;
	}

	public Date getDateDeparture() {
		return dateDeparture;
	}

	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getAmoutTickets() {
		return amoutTickets;
	}

	public void setAmoutTickets(String amoutTickets) {
		this.amoutTickets = amoutTickets;
	}

	public Timetable getSelectedFlightTo() {
		return selectedFlightTo;
	}

	public void setSelectedFlightTo(Timetable selectedFlightTo) {
		this.selectedFlightTo = selectedFlightTo;
	}

	public Timetable getSelectedFlightFrom() {
		return selectedFlightFrom;
	}

	public void setSelectedFlightFrom(Timetable selectedFlightFrom) {
		this.selectedFlightFrom = selectedFlightFrom;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCurrencyDollar() {
		return currencyDollar;
	}

	public void setCurrencyDollar(String currencyDollar) {
		this.currencyDollar = currencyDollar;
	}

	public java.util.Date getToday() {
		return today;
	}

	public void setToday(java.util.Date today) {
		this.today = today;
	}

	public BigDecimal getTotalUAH() {
		return totalUAH;
	}

	public void setTotalUAH(BigDecimal totalUAH) {
		this.totalUAH = totalUAH;
	}

}
