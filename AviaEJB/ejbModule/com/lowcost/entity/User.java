package com.lowcost.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import com.google.common.base.Objects;

/**
 * The persistent class for the users database table.
 * 
 */
//
@Entity
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "User.findAllClients", query = "SELECT u FROM User u where u.usertype.usertype = 'Client'"),
		@NamedQuery(name = "User.findAllManagers", query = "SELECT u FROM User u where u.usertype.usertype = 'Manager'"),
		@NamedQuery(name = "User.findByPassLogin", query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password"),
		@NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String email;
	private String login;
	private String name;
	private String password;
	private String phone;
	private String surname;
	// //bi-directional many-to-one association to Bookingtable
	@OneToMany(mappedBy = "user")
	private List<Bookingtable> bookingtables;

	// bi-directional many-to-one association to Soldticket
	@OneToMany(mappedBy = "user")
	private List<Soldticket> soldtickets;

	// bi-directional many-to-one association to Usertype
	@ManyToOne
	@JoinColumn(name = "userType")
	private Usertype usertype;

	public User(int id, String email, String login, String name,
			String password, String phone, String surname, Usertype userType) {
		this.id = id;
		this.email = email;
		this.login = login;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.surname = surname;
		this.usertype = userType;
	}

	public User(String email, String login, String name, String password,
			String phone, String surname, Usertype userType) {
		this.email = email;
		this.login = login;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.surname = surname;
		this.usertype = userType;
	}

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Bookingtable> getBookingtables() {
		return this.bookingtables;
	}

	public void setBookingtables(List<Bookingtable> bookingtables) {
		this.bookingtables = bookingtables;
	}

	public Bookingtable addBookingtable(Bookingtable bookingtable) {
		getBookingtables().add(bookingtable);
		bookingtable.setUser(this);
		return bookingtable;
	}

	public Bookingtable removeBookingtable(Bookingtable bookingtable) {
		getBookingtables().remove(bookingtable);
		bookingtable.setUser(null);

		return bookingtable;
	}

	public List<Soldticket> getSoldtickets() {
		return this.soldtickets;
	}

	public void setSoldtickets(List<Soldticket> soldtickets) {
		this.soldtickets = soldtickets;
	}

	public Soldticket addSoldticket(Soldticket soldticket) {
		getSoldtickets().add(soldticket);
		soldticket.setUser(this);

		return soldticket;
	}

	public Soldticket removeSoldticket(Soldticket soldticket) {
		getSoldtickets().remove(soldticket);
		soldticket.setUser(null);

		return soldticket;
	}

	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}

	public Usertype getUsertype() {
		return usertype;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), id, email, login, name, password, phone, surname, bookingtables, soldtickets, usertype);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof User) {
			if (!super.equals(object)) 
				return false;
			User that = (User) object;
			return Objects.equal(this.id, that.id)
				&& Objects.equal(this.email, that.email)
				&& Objects.equal(this.login, that.login)
				&& Objects.equal(this.name, that.name)
				&& Objects.equal(this.password, that.password)
				&& Objects.equal(this.phone, that.phone)
				&& Objects.equal(this.surname, that.surname)
				&& Objects.equal(this.bookingtables, that.bookingtables)
				&& Objects.equal(this.soldtickets, that.soldtickets)
				&& Objects.equal(this.usertype, that.usertype);
		}
		return false;
	}
	

}