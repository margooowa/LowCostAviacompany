package com.lowcost.entitiesbean.booking;

import java.util.List;

import javax.ejb.Local;

import com.lowcost.entity.Bookingtable;

@Local
public interface BookingFacadeLocal {

	void create(Bookingtable book);

	void edit(Bookingtable book);

	void remove(Bookingtable book);

	void removeById(int id);

	Bookingtable find(int id);

	List<Bookingtable> findAll();

	List<Bookingtable> findByClientId(int id);
}
