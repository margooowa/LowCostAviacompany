package com.lowcost.entitiesbean.sold;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.lowcost.entitiesbean.AbstractFacade;
import com.lowcost.entity.Soldticket;

/**
 * Session Bean implementation class SoldFacade
 */
@Stateless
@LocalBean
public class SoldFacade extends AbstractFacade<Soldticket> implements
		SoldFacadeLocal {

	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	public SoldFacade() {
		super(Soldticket.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Soldticket> findByClientID(int id) {
		TypedQuery<Soldticket> querySold = em.createNamedQuery(
				"Soldticket.findByClientID", Soldticket.class);
		querySold.setParameter("id", id);
		List<Soldticket> list = querySold.getResultList();
		return list;
	}

}
