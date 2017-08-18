package com.cloudzon.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cloudzon.domain.User;
import com.cloudzon.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

	private final UserRepository userRepository;

	public DomainUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		Collection<GrantedAuthority> objAuthorities = null;
		SimpleGrantedAuthority objAuthority = null;
		UserDetails userDetails = null;
		User objUser = null;
		try {
			// get user data from database
			objUser = this.userRepository.findByUsername(login);
			if (null != objUser) {
				objAuthorities = new ArrayList<GrantedAuthority>();
				objAuthority = new SimpleGrantedAuthority("ROLE_USER");
				objAuthorities.add(objAuthority);
				userDetails = new org.springframework.security.core.userdetails.User(login, "NoPassword", true, true,
						true, true, objAuthorities);
			} else {
				throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found.");
			}
		} finally {
			objUser = null;
			objAuthorities = null;
			objAuthority = null;
		}
		return userDetails;
	}
}
