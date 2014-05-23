package com.lowcost.entitiesbean.user;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.TypedQuery;

import com.lowcost.entity.User;

@Local
public interface UserFacadeLocal {

	void create(User users);

	void edit(User users);

	void remove(User users);

	User find(int id);

	List<User> findAll();

	User findByLoginPass(String login, String pass);

	User findByLogin(String login);

	List<User> findAllClients();

	List<User> findAllManagers();
}
