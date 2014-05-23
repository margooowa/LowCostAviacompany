package com.lowcost.entitiesbean.timetable;

import java.sql.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.lowcost.entitiesbean.AbstractFacade;
import com.lowcost.entity.Timetable;

/**
 * Session Bean implementation class TimetableFacade
 */
@Stateless
@LocalBean
public class TimetableFacade extends AbstractFacade<Timetable> implements
		TimetableFacadeLocal {

	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	public TimetableFacade() {
		super(Timetable.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Timetable> findNeedFlights(int amoutTickets,
			Date dateDeparture, String from, String to) {
		TypedQuery<Timetable> queryTimetable = em.createNamedQuery(
				"Timetable.findNeedFlight", Timetable.class);
		queryTimetable.setParameter("amoutTickets", amoutTickets);
		queryTimetable.setParameter("dateDeparture", dateDeparture);
		queryTimetable.setParameter("from", from);
		queryTimetable.setParameter("to", to);
		List<Timetable> list = queryTimetable.getResultList();
		return list;
	}

	@Override
	public Timetable findByFlightName(String flightName) {
		TypedQuery<Timetable> queryTimetable = em.createNamedQuery(
				"Timetable.findByFlightName", Timetable.class);
		queryTimetable.setParameter("flightName", flightName);
		Timetable list = queryTimetable.getSingleResult();
		return list;
	}

	@Override
	public List<Timetable> findToCity(String fromCity) {
		TypedQuery<Timetable> queryTimetable = em.createNamedQuery(
				"Timetable.findToCity", Timetable.class);
		queryTimetable.setParameter("fromCity", fromCity);
		List<Timetable> list = queryTimetable.getResultList();
		return list;
	}

}
