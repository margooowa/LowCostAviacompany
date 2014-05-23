package com.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import com.google.common.base.Objects;

/**
 * The persistent class for the timetable database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Timetable.findAll", query = "SELECT t FROM Timetable t"),
		@NamedQuery(name = "Timetable.findToCity", query = "SELECT t FROM Timetable t WHERE t.fromCity=:fromCity"),
		@NamedQuery(name = "Timetable.findByFlightName", query = "SELECT t FROM Timetable t WHERE t.flightName=:flightName"),
		@NamedQuery(name = "Timetable.updateByID", query = "UPDATE Timetable t SET t.amoutTickets=:amount , t.amountBookedTickets=:amountBook WHERE t.id=:id"),
		@NamedQuery(name = "Timetable.findNeedFlight", query = "SELECT t FROM Timetable t WHERE t.amoutTickets>=:amoutTickets AND t.dateDeparture=:dateDeparture AND t.fromCity=:from AND t.toCity=:to") })
public class Timetable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private int amoutTickets;
	private int amountBookedTickets;
	private int amountSoldTickets;
	private Date dateArrival;
	private Date dateDeparture;
	private String flightName;
	private String fromCity;
	private Time timeArrival;
	private Time timeDeparture;
	private String toCity;
	private BigDecimal price;

	// bi-directional many-to-one association to Bookingtable
	@OneToMany(mappedBy = "timetable")
	private List<Bookingtable> bookingtables;

	// bi-directional many-to-one association to Soldticket
	@OneToMany(mappedBy = "timetable")
	private List<Soldticket> soldtickets;

	public Timetable() {
	}

	public Timetable(int amoutTickets, int amountBookedTickets,
			int amountSoldTickets, Date dateArrival, Date dateDeparture,
			String flightName, String fromCity, Time timeArrival,
			Time timeDeparture, String toCity, BigDecimal price) {

		this.amoutTickets = amoutTickets;
		this.amountBookedTickets = amountBookedTickets;
		this.amountSoldTickets = amountSoldTickets;
		this.dateArrival = dateArrival;
		this.dateDeparture = dateDeparture;
		this.flightName = flightName;
		this.fromCity = fromCity;
		this.timeArrival = timeArrival;
		this.timeDeparture = timeDeparture;
		this.toCity = toCity;
		this.price = price;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmoutTickets() {
		return this.amoutTickets;
	}

	public void setAmoutTickets(int amoutTickets) {
		this.amoutTickets = amoutTickets;
	}

	public Date getDateArrival() {
		return this.dateArrival;
	}

	public void setDateArrival(Date dateArrival) {
		this.dateArrival = dateArrival;
	}

	public Date getDateDeparture() {
		return this.dateDeparture;
	}

	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}

	public String getFlightName() {
		return this.flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public Time getTimeArrival() {
		return this.timeArrival;
	}

	public void setTimeArrival(Time timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Time getTimeDeparture() {
		return this.timeDeparture;
	}

	public void setTimeDeparture(Time timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

	public int getAmountBookedTickets() {
		return amountBookedTickets;
	}

	public void setAmountBookedTickets(int amountBookedTickets) {
		this.amountBookedTickets = amountBookedTickets;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Bookingtable> getBookingtables() {
		return this.bookingtables;
	}

	public void setBookingtables(List<Bookingtable> bookingtables) {
		this.bookingtables = bookingtables;
	}

	public Bookingtable addBookingtable(Bookingtable bookingtable) {
		getBookingtables().add(bookingtable);
		bookingtable.setTimetable(this);

		return bookingtable;
	}

	public Bookingtable removeBookingtable(Bookingtable bookingtable) {
		getBookingtables().remove(bookingtable);
		bookingtable.setTimetable(null);

		return bookingtable;
	}

	public List<Soldticket> getSoldtickets() {
		return this.soldtickets;
	}

	public void setSoldtickets(List<Soldticket> soldtickets) {
		this.soldtickets = soldtickets;
	}

	public Soldticket addSoldticket(Soldticket soldticket) {
		getSoldtickets().add(soldticket);
		soldticket.setTimetable(this);

		return soldticket;
	}

	public Soldticket removeSoldticket(Soldticket soldticket) {
		getSoldtickets().remove(soldticket);
		soldticket.setTimetable(null);

		return soldticket;
	}

	public int getAmountSoldTickets() {
		return amountSoldTickets;
	}

	public void setAmountSoldTickets(int amountSoldTickets) {
		this.amountSoldTickets = amountSoldTickets;
	}

//	@Override
//	public int hashCode(){
//		return Objects.hashCode(super.hashCode(), id, amoutTickets, amountBookedTickets, amountSoldTickets,
//				dateArrival, dateDeparture, flightName, fromCity, timeArrival, timeDeparture, toCity, price, bookingtables, soldtickets);
//	}
//	
//	@Override
//	public boolean equals(Object object){
//		if (object instanceof Timetable) {
//			if (!super.equals(object)) 
//				return false;
//			Timetable that = (Timetable) object;
//			return Objects.equal(this.id, that.id)
//				&& Objects.equal(this.amoutTickets, that.amoutTickets)
//				&& Objects.equal(this.amountBookedTickets, that.amountBookedTickets)
//				&& Objects.equal(this.amountSoldTickets, that.amountSoldTickets)
//				&& Objects.equal(this.dateArrival, that.dateArrival)
//				&& Objects.equal(this.dateDeparture, that.dateDeparture)
//				&& Objects.equal(this.flightName, that.flightName)
//				&& Objects.equal(this.fromCity, that.fromCity)
//				&& Objects.equal(this.timeArrival, that.timeArrival)
//				&& Objects.equal(this.timeDeparture, that.timeDeparture)
//				&& Objects.equal(this.toCity, that.toCity)
//				&& Objects.equal(this.price, that.price)
//				&& Objects.equal(this.bookingtables, that.bookingtables)
//				&& Objects.equal(this.soldtickets, that.soldtickets);
//		}
//		return false;
//	}

	



	

	

}