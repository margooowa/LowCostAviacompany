package com.lowcost.entitiesbean.usertype;

import java.util.List;

import javax.ejb.Local;

import com.lowcost.entity.Usertype;

@Local
public interface UsertypeFacadeLocal {

	void create(Usertype users);

	void edit(Usertype users);

	void remove(Usertype users);

	Usertype find(int id);

	List<Usertype> findAll();
}
