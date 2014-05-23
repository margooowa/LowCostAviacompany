package com.lowcost.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import com.google.common.base.Objects;

/**
 * The persistent class for the bookingtable database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Bookingtable.findAll", query = "SELECT b FROM Bookingtable b"),
		@NamedQuery(name = "Bookingtable.findByClientID", query = "SELECT b FROM Bookingtable b WHERE b.user.id=:clientID"),
		@NamedQuery(name = "Bookingtable.deleteByID", query = "DELETE FROM Bookingtable b WHERE b.id=:id") })
public class Bookingtable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int amount;
	private Timestamp bookingDate;
	private String luggage;
	private boolean priority;
	private String status;
	private String bookingNumber;
	private BigDecimal fullPrice;
	// bi-directional many-to-one association to Timetable
	@ManyToOne
	@JoinColumn(name = "flightID")
	private Timetable timetable;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "clientID")
	private User user;

	public Bookingtable() {
	}

	public Bookingtable(int id, int amount, Timestamp bookingDate,
			String luggage, boolean priority, String status,
			BigDecimal fullPrice, User user, Timetable timetable) {
		this.id = id;		
		this.amount = amount;
		this.user = user;
		this.timetable = timetable;
		this.bookingDate = bookingDate;
		this.luggage = luggage;
		this.priority = priority;
		this.status = status;
		this.fullPrice = fullPrice;
	}

	public Bookingtable(int amount, String bookingNumber,Timestamp bookingDate, String luggage,
			boolean priority, String status, BigDecimal fullPrice, User user,
			Timetable timetable) {
		this.bookingNumber=bookingNumber;
		this.amount = amount;
		this.user = user;
		this.timetable = timetable;
		this.bookingDate = bookingDate;
		this.luggage = luggage;
		this.priority = priority;
		this.status = status;
		this.fullPrice = fullPrice;
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

	public Timestamp getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), id, amount, bookingDate, luggage, priority, status, bookingNumber, fullPrice, timetable, user);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof Bookingtable) {
			if (!super.equals(object)) 
				return false;
			Bookingtable that = (Bookingtable) object;
			return Objects.equal(this.id, that.id)
				&& Objects.equal(this.amount, that.amount)
				&& Objects.equal(this.bookingDate, that.bookingDate)
				&& Objects.equal(this.luggage, that.luggage)
				&& Objects.equal(this.priority, that.priority)
				&& Objects.equal(this.status, that.status)
				&& Objects.equal(this.bookingNumber, that.bookingNumber)
				&& Objects.equal(this.fullPrice, that.fullPrice)
				&& Objects.equal(this.timetable, that.timetable)
				&& Objects.equal(this.user, that.user);
		}
		return false;
	}

	
	
	

	

}