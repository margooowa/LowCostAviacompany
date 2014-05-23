package com.lowcost.entitiesbean.sold;

import java.util.List;

import javax.ejb.Local;

import com.lowcost.entity.Soldticket;

@Local
public interface SoldFacadeLocal {

	void create(Soldticket sold);

	void edit(Soldticket sold);

	void remove(Soldticket sold);

	Soldticket find(int id);

	List<Soldticket> findAll();

	List<Soldticket> findByClientID(int id);

}
