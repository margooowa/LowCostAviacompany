package com.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

import com.google.common.base.Objects;

/**
 * The persistent class for the soldticket database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Soldticket.findAll", query = "SELECT s FROM Soldticket s"),
		@NamedQuery(name = "Soldticket.findByClientID", query = "SELECT s FROM Soldticket s WHERE s.user.id=:id") })
public class Soldticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private int amount;
	private String luggage;
	private boolean priority;
	private Timestamp saledate;
	private BigDecimal fullPrice;
	private String bookingNumber;
	// bi-directional many-to-one association to Timetable
	@ManyToOne
	@JoinColumn(name = "FlightID")
	private Timetable timetable;
	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "clientID")
	private User user;

	public Soldticket() {
	}

	public Soldticket(int id, int amount, String luggage, boolean priority,
			Timestamp saledate, BigDecimal fullPrice, User user,
			Timetable timetable) {
		super();
		this.id = id;
		this.amount = amount;
		this.luggage = luggage;
		this.priority = priority;
		this.saledate = saledate;
		this.fullPrice = fullPrice;
		this.user = user;
		this.timetable = timetable;
	}

	public Soldticket(String bookingNumber,int amount, String luggage, boolean priority,
			Timestamp saledate, BigDecimal fullPrice, User user,
			Timetable timetable) {
		this.bookingNumber=bookingNumber;
		this.amount = amount;
		this.luggage = luggage;
		this.priority = priority;
		this.saledate = saledate;
		this.fullPrice = fullPrice;
		this.user = user;
		this.timetable = timetable;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getLuggage() {
		return this.luggage;
	}

	public void setLuggage(String luggage) {
		this.luggage = luggage;
	}

	public boolean getPriority() {
		return this.priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public Date getSaledate() {
		return this.saledate;
	}

	public void setSaledate(Timestamp saledate) {
		this.saledate = saledate;
	}

	public Timetable getTimetable() {
		return this.timetable;
	}

	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(BigDecimal fullPrice) {
		this.fullPrice = fullPrice;
	}


	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

//	@Override
//	public int hashCode(){
//		return Objects.hashCode(super.hashCode(), id, amount, luggage, priority, saledate, fullPrice, bookingNumber, timetable, user);
//	}
//	
//	@Override
//	public boolean equals(Object object){
//		if (object instanceof Soldticket) {
//			if (!super.equals(object)) 
//				return false;
//			Soldticket that = (Soldticket) object;
//			return Objects.equal(this.id, that.id)
//				&& Objects.equal(this.amount, that.amount)
//				&& Objects.equal(this.luggage, that.luggage)
//				&& Objects.equal(this.priority, that.priority)
//				&& Objects.equal(this.saledate, that.saledate)
//				&& Objects.equal(this.fullPrice, that.fullPrice)
//				&& Objects.equal(this.bookingNumber, that.bookingNumber)
//				&& Objects.equal(this.timetable, that.timetable)
//				&& Objects.equal(this.user, that.user);
//		}
//		return false;
//	}

	

	
	
	

	

}