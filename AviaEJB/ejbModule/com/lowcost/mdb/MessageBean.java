package com.lowcost.mdb;


import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.lowcost.entitiesbean.user.UserFacadeLocal;
import com.lowcost.entity.User;


	@MessageDriven(mappedName = "jms/GlassFishCurrencyDollar", activationConfig = {
	    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
	})
	public class MessageBean implements MessageListener {
		
	    @Resource
	    private MessageDrivenContext mdc;
	    @PersistenceContext(unitName = "LowCostPersistance")
	    private EntityManager em;

	    @EJB
	    private UserFacadeLocal query;
	    private Logger logger = Logger.getLogger(getClass().getName());	
	    public MessageBean() {
	    }
	    
	    @Override
	    public void onMessage(Message message) {
	        ObjectMessage msg = null;
	        try {
	            if (message instanceof ObjectMessage) {
	                msg = (ObjectMessage) message;
	                User client = (User) msg.getObject();
	                query.create(client);
	                BasicConfigurator.configure();	
	                logger.info("Client created");
	            }
	            if (message instanceof javax.jms.TextMessage) {
	            	TextMessage mess = (TextMessage)message;
	        	  }
	        } catch (JMSException c) {
	            logger.error(c);
	            mdc.setRollbackOnly();
	        }
	    }

	   	    
	}
