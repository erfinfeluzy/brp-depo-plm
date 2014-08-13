package com.depobrp.web.zk.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.depobrp.model.user.Module;
import com.depobrp.model.user.Page;
import com.depobrp.model.user.User;


public class CustomUserDetail implements UserDetails, Serializable {

	private static final long serialVersionUID = -4618347316688917384L;

	public CustomUserDetail(User user) throws Exception{
		super();
		this.user = user;
	}

    private User user;

	public Collection<GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for (Module module : this.user.getRole().getModules()) {
        	
        	for (Page page : module.getPages()) {
        		list.add(new GrantedAuthorityImpl(page.getName()));
			}
              
        }
        return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.user.getUsername() == null) ? 
						0 : this.user.getUsername().hashCode());
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
		
		CustomUserDetail other = (CustomUserDetail) obj;
		
		if (this.user.getUsername() == null) {
			if (other.getUser().getUsername() != null)
				return false;
		} else if (!this.user.getUsername().equals(other.user.getUsername()))
			return false;
		return true;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}
}

