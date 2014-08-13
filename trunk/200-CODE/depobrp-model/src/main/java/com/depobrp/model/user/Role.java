package com.depobrp.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.depobrp.model.common.Auditable;

@Entity
@Table(name = "TBL_MS_ROLE")
public class Role extends Auditable{

	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Role() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4890272817534605335L;

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToMany(mappedBy="role")
	private Set<User> users = new HashSet<User>();
	
	@ManyToMany
	@JoinTable(
			name = "TB_REL_ROLE_MODULE",
			joinColumns = {@JoinColumn (name="ROLE_ID")},
			inverseJoinColumns = {@JoinColumn(name="MODULE_ID")}
	)
	private Set<Module> modules = new HashSet<Module>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
