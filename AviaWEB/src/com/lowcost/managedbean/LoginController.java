package com.lowcost.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.lowcost.entitiesbean.user.UserFacadeLocal;
import com.lowcost.entitiesbean.usertype.UsertypeFacadeLocal;
import com.lowcost.entity.User;
import com.lowcost.entity.Usertype;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private Logger logger = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;
	@EJB
	private UserFacadeLocal userEJB;
	@EJB
	private UsertypeFacadeLocal usertypeEJB;
	@ManagedProperty(value = "#{cabinetController}")
	private CabinetController cabinetController;
	private User user;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Usertype usertype;
	private List<User> listUser;
	private String exist;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);

	public LoginController() {
	}

	// /Invalidated
	public String invalid() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/home.privet?faces-redirect=true";
	}

	// /Create user
	public String doCreateUser() {
		usertype = usertypeEJB.find(3);
		User client = new User();
		client.setName(name);
		client.setSurname(surname);
		client.setLogin(login);
		client.setPassword(password);
		client.setPhone(phone);
		client.setEmail(email);
		client.setUsertype(usertype);

		try {
			Context jndiContext;
			jndiContext = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext
					.lookup("jms/CurrencyDollar");
			Queue queue = (Queue) jndiContext
					.lookup("jms/GlassFishCurrencyDollar");
			Connection connection = connectionFactory.createConnection();
			Session sessionJMS = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = sessionJMS.createProducer(queue);
			ObjectMessage message = sessionJMS.createObjectMessage();
			message.setObject(client);
			messageProducer.send(message);
			TextMessage msg2 = sessionJMS.createTextMessage();
			msg2.setText("Client was created");
			messageProducer.send(msg2);
			messageProducer.close();
			connection.close();
			makeNull();
		} catch (NamingException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/login.privet?faces-redirect=true";
	}

	// / null fields
	public void makeNull() {
		setLogin(null);
		setName(null);
		setSurname(null);
		setEmail(null);
		setPhone(null);
	}

	// /Doing login
	public void doFindUser() throws IOException {

		listUser = userEJB.findAll();
		for (User us : listUser) {
			if (us.getPassword().equals(password)
					&& us.getLogin().equals(login)) {
				user = userEJB.findByLoginPass(login, password);
				BasicConfigurator.configure();
				logger.info("Client " + user.getLogin() + " login");
				if (user.getUsertype().getIdUsertype() == 3) {
					session.setAttribute("user", user);
					cabinetController.afterLogin();
					String urlBegin = (String) session.getAttribute("urlPre");
					if (urlBegin == null) {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("home.privet");
					}
					if (urlBegin != null) {
						String url = urlBegin.substring(1,
								urlBegin.length() - 6);
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect(url + ".privet");

					}
				}
			}
		}

	}

	// message if user not exist
	public void notExist(AjaxBehaviorEvent event) {
		// exist="Such login or password not exist";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Such login or password not exist", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Validation if such user already exist when create
	public void validateLogin(FacesContext context, UIComponent validate,
			Object login) {
		String log = (String) login;
		listUser = userEJB.findAll();
		for (User us : listUser) {
			if (log.equals(us.getLogin())) {
				FacesMessage message = new FacesMessage(
						"Username already exist");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setCabinetController(CabinetController cabinetController) {
		this.cabinetController = cabinetController;
	}

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}

}
