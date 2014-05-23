package com.lowcost.entitiesbean.timetable;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import com.lowcost.entity.Timetable;

@Local
public interface TimetableFacadeLocal {

	void create(Timetable flight);

	void edit(Timetable flight);

	void remove(Timetable flight);

	Timetable find(int id);

	List<Timetable> findAll();

	List<Timetable> findNeedFlights(int amoutTickets, Date dateDeparture,
			String from, String to);

	Timetable findByFlightName(String flightName);

	List<Timetable> findToCity(String fromCity);
}
