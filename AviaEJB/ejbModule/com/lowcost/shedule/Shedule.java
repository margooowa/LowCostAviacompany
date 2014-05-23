package com.lowcost.shedule;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.lowcost.entity.Bookingtable;
import com.lowcost.entity.Timetable;

@Stateless
public class Shedule {
	private Logger logger = Logger.getLogger(getClass().getName());	
	private static java.util.Date today = new java.util.Date();
	private static Timestamp timestampToday = new Timestamp(today.getTime());
	private Timetable flight;
	private int newAmountBook;
	private int newAmount;
	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	public Shedule() {
	}

	@Schedule(second = "*", minute = "*", hour = "*/6", persistent = false)
	private void findWhatToDelete() throws InterruptedException {
		TypedQuery<Bookingtable> queryBooking = em.createNamedQuery(
				"Bookingtable.findAll", Bookingtable.class);
		List<Bookingtable> list = queryBooking.getResultList();
		for (Bookingtable bb : list) {
			Timestamp timestamp2 = bb.getBookingDate();
			long difference = compareTwoTimeStamps(timestampToday, timestamp2);
			if (difference >= 3) {
				flight=bb.getTimetable();
				newAmount = flight.getAmoutTickets()+bb.getAmount();
				newAmountBook = flight.getAmountBookedTickets()-bb.getAmount();				
				updateByID(newAmount,newAmountBook,flight.getId());
				deleteByID(bb.getId());
				BasicConfigurator.configure();	
				logger.info("Booking was deleted because of 3 days");
			}
				}
	}

	
	public static long compareTwoTimeStamps(java.sql.Timestamp currentTime,
			java.sql.Timestamp oldTime) {
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	private void deleteByID(int id) {
		TypedQuery<Bookingtable> queryTimetable = em.createNamedQuery("Bookingtable.deleteByID", Bookingtable.class);
		queryTimetable.setParameter("id", id);
		queryTimetable.executeUpdate();
	}
	
	private void updateByID(int amount, int amountBook, int id) {
		TypedQuery<Timetable> queryTimetable = em.createNamedQuery("Timetable.updateByID", Timetable.class);
		queryTimetable.setParameter("amount", amount);
		queryTimetable.setParameter("amountBook", amountBook);
		queryTimetable.setParameter("id", id);
		queryTimetable.executeUpdate();
	}
	
}
