package com.contentplusplus.springboot.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;

@Service
public class AppUserServiceImpl implements AppUserService {

	private AppUserRepository userRepository;
	private AppRoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public AppUserServiceImpl(AppUserRepository userRepository, AppRoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(AppUser user) {
		user.setUserfirstname(user.getUserfirstname());
		user.setUserlastname(user.getUserlastname());
		user.setUseremail(user.getUseremail());
		user.setUserpassword(passwordEncoder.encode(user.getUserpassword()));
		user.setUseruuid(UUID.randomUUID().toString());
		user.setRoles(user.getRoles());
		userRepository.save(user);
	}

	@Override
	public AppUser findByUseremail(String email) {
		return userRepository.findByUseremail(email);
	}

	@Override
	public List<AppUser> findAllUsers() {
		List<AppUser> users = userRepository.findAll();
		return users.stream().map((user) -> user).collect(Collectors.toList());
	}

	@Override
	public AppUser findByUseremailIgnoreCase(String email) {
		return userRepository.findByUseremailIgnoreCase(email);
	}
}
