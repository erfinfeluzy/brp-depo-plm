package com.depobrp.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

import com.depobrp.model.common.Auditable;

@Entity
@Table(name = "TBL_MS_USER")
public class User extends Auditable{

	public User(String username) {
		super();
		this.username = username;
	}
	
	public User() {
		super();
	}

	private static final long serialVersionUID = 6840665209508438103L;
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "LAST_LOGIN")
	private Date lastLogin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="ROLE_ID", nullable=false)
	private Role role;
	
	@OneToMany 
	@JoinColumn(name = "SUPERVISOR_ID") 
	@OrderBy("username")
	private List<User> subOrdinates = new LinkedList<User>();
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "SUPERVISOR_ID") 
	private User supervisor;
	
	@Transient
	private Set<User> allSubOrdinates = new HashSet<User>();
	
	@Transient
	private boolean login = false;

	public void fetchChildInSession(User user, User root){
		try{
//			System.out.println("TRAVERSE: " + user.getUsername());
			if(user.getSubOrdinates().size() < 1)
				return;
			
			for (User subOrdinate : user.getSubOrdinates()) {
				Hibernate.initialize(subOrdinate.getSubOrdinates());
				subOrdinate.fetchChildInSession(subOrdinate, root);
				root.getAllSubOrdinates().add(subOrdinate);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	public User getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(User supervisor) {
		this.supervisor = supervisor;
	}

	public Set<User> getAllSubOrdinates() {
		return allSubOrdinates;
	}

	public void setAllSubOrdinates(Set<User> allSubOrdinates) {
		this.allSubOrdinates = allSubOrdinates;
	}

	public List<User> getSubOrdinates() {
		return subOrdinates;
	}

	public void setSubOrdinates(List<User> subOrdinates) {
		this.subOrdinates = subOrdinates;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}
	
}
