package com.lowcost.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Objects;

/**
 * The persistent class for the usertype database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Usertype.findAll", query = "SELECT u FROM Usertype u"),
		@NamedQuery(name = "Usertype.findUsertype", query = "SELECT u.usertype FROM Usertype u where u.usertype='Client'") })
public class Usertype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usertype")
	private int idUsertype;
	private String usertype;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "usertype")
	private List<User> users;

	public Usertype() {
	}

	public Usertype(String usertype) {
		this.usertype = usertype;
	}

	public int getIdUsertype() {
		return this.idUsertype;
	}

	public void setIdUsertype(int idUsertype) {
		this.idUsertype = idUsertype;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUsertype(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUsertype(null);

		return user;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), idUsertype, usertype, users);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof Usertype) {
			if (!super.equals(object)) 
				return false;
			Usertype that = (Usertype) object;
			return Objects.equal(this.idUsertype, that.idUsertype)
				&& Objects.equal(this.usertype, that.usertype)
				&& Objects.equal(this.users, that.users);
		}
		return false;
	}

	

}