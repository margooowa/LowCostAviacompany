package com.lowcost.entitiesbean.usertype;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lowcost.entitiesbean.AbstractFacade;
import com.lowcost.entity.Usertype;

/**
 * Session Bean implementation class UsertypeFacade
 */
@Stateless
public class UsertypeFacade extends AbstractFacade<Usertype> implements
		UsertypeFacadeLocal {

	@PersistenceContext(unitName = "LowCostPersistance")
	private EntityManager em;

	public UsertypeFacade() {
		super(Usertype.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
