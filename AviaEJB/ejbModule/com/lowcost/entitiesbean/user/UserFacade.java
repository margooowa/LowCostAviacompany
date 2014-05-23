package com.lowcost.entitiesbean.user;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.lowcost.entitiesbean.AbstractFacade;
import com.lowcost.entity.User;

/**
 * Session Bean implementation class UserFacade
 */
@Stateless
@LocalBean
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	public UserFacade() {
		super(User.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public User findByLoginPass(String login, String pass) {
		TypedQuery<User> queryUsers = em.createNamedQuery(
				"User.findByPassLogin", User.class);
		queryUsers.setParameter("login", login);
		queryUsers.setParameter("password", pass);
		User user = queryUsers.getSingleResult();
		return user;
	}

	@Override
	public User findByLogin(String login) {
		TypedQuery<User> queryUsers = em.createNamedQuery("User.findByLogin",
				User.class);
		queryUsers.setParameter("login", login);
		User user = queryUsers.getSingleResult();
		return user;
	}

	@Override
	public List<User> findAllClients() {
		TypedQuery<User> queryUsers = em.createNamedQuery(
				"User.findAllClients", User.class);
		List<User> user = queryUsers.getResultList();
		return user;
	}

	@Override
	public List<User> findAllManagers() {
		TypedQuery<User> queryUsers = em.createNamedQuery(
				"User.findAllManagers", User.class);
		List<User> user = queryUsers.getResultList();
		return user;
	}

}
