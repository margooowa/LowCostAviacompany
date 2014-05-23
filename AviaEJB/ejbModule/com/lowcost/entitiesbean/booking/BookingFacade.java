package com.lowcost.entitiesbean.booking;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.lowcost.entitiesbean.AbstractFacade;
import com.lowcost.entity.Bookingtable;

/**
 * Session Bean implementation class BookingFacade
 */
@Stateless
@LocalBean
public class BookingFacade extends AbstractFacade<Bookingtable> implements
		BookingFacadeLocal {

	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public BookingFacade() {
		super(Bookingtable.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Bookingtable> findByClientId(int id) {
		TypedQuery<Bookingtable> queryBooking = em.createNamedQuery(
				"Bookingtable.findByClientID", Bookingtable.class);
		queryBooking.setParameter("clientID", id);
		List<Bookingtable> list = queryBooking.getResultList();
		return list;
	}

}
