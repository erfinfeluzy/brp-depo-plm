package com.depobrp.web.zk.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.depobrp.commons.security.util.SecurityUtil;

@Component("customPasswordEncoder")
public class CustomPasswordEncoder implements PasswordEncoder{

	@Override
	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException {
		
		return SecurityUtil.encrypt(rawPass);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		
		return encPass.equals(SecurityUtil.encrypt(rawPass));
	}

}
