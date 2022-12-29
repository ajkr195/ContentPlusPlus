package com.contentplusplus.springboot.security;

import com.contentplusplus.springboot.model.AppRole;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppUserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsService implements UserDetailsService {

	private AppUserRepository userRepository;

	public AppUserDetailsService(AppUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUseremailIgnoreCase(email);

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUseremail(), user.getUserpassword(),
					mapRolesToAuthorities(user.getRoles()));
		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<AppRole> roles) {
		Collection<? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return mapRoles;
	}
}
