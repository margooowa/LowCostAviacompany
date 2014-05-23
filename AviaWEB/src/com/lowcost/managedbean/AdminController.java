package com.lowcost.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.lowcost.entitiesbean.user.UserFacadeLocal;
import com.lowcost.entitiesbean.usertype.UsertypeFacadeLocal;
import com.lowcost.entity.User;
import com.lowcost.entity.Usertype;

@ManagedBean
@SessionScoped
public class AdminController implements Serializable {
	private Logger logger = Logger.getLogger(getClass().getName());
	private static final long serialVersionUID = 1L;
	@EJB
	private UserFacadeLocal userEJB;
	@EJB
	private UsertypeFacadeLocal usertypeEJB;
	private User user;
	private String login;
	private String password;
	private List<User> managers;
	private User userCreate;
	private String loginCreate;
	private String passwordCreate;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Usertype usertype;
	private List<User> listUser;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);

	public AdminController() {
	}

	@PostConstruct
	public void init() {
		setManagers(userEJB.findAllManagers());
	}

	// /Doing login
	public String doFindUser() throws IOException {
		String page = "/loginAdmin.xhtml";
		listUser = userEJB.findAll();
		for (User us : listUser) {
			if (us.getPassword().equals(password)
					&& us.getLogin().equals(login)) {
				user = userEJB.findByLoginPass(login, password);
				BasicConfigurator.configure();
				switch (user.getUsertype().getIdUsertype()) {
				case (1): {
					session.setAttribute("admin", user);
					logger.info("Admin " + user.getLogin() + " login");
					page = "/admin/admin.privet?faces-redirect=true";
					break;
				}
				case (2): {
					session.setAttribute("manag", user);
					logger.info("Manager " + user.getLogin() + " login");
					page = "/manager/manager.privet?faces-redirect=true";
				}
				}
			}
		}
		return page;
	}

	// message if user not exist
	public void notExist(AjaxBehaviorEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Such login or password not exist", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// /Invalidated
	public String invalid() throws IOException {
		logger.info("session invalidate");
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/loginAdmin.privet?faces-redirect=true";
	}

	// /Create manager
	public void doCreateManager() {
		usertype = usertypeEJB.find(2);
		userCreate = new User(email, loginCreate, name, passwordCreate, phone,
				surname, usertype);
		logger.info("new manager created");
		userEJB.create(userCreate);
	}

	// Validation if such login already exist when create
	public void validateLoginManager(FacesContext context,
			UIComponent validate, Object login) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}

	public User getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(User userCreate) {
		this.userCreate = userCreate;
	}

	public String getLoginCreate() {
		return loginCreate;
	}

	public void setLoginCreate(String loginCreate) {
		this.loginCreate = loginCreate;
	}

	public String getPasswordCreate() {
		return passwordCreate;
	}

	public void setPasswordCreate(String passwordCreate) {
		this.passwordCreate = passwordCreate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public Usertype getUsertype() {
		return usertype;
	}

	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

}
