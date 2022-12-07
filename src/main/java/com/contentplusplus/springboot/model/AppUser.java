package com.contentplusplus.springboot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppUser")
@Table(name = "app_user")

public class AppUser  extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userfirstname", nullable = false)
	private String userfirstname;
	
	@Column(name = "userlastname", nullable = false)
	private String userlastname;

	@Column(name = "useremail", nullable = false, unique = true)
	private String useremail;
	
	@Column(name = "userpassword", nullable = false)
	private String userpassword;
	
	@Column(name = "useruuid", unique = true)
	@GenericGenerator(name = "uuid", strategy = "uuid4")
	private String useruuid;
	
	@Transient
	private String passwordConfirm;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "app_user_role", joinColumns = {
			@JoinColumn(name = "userid", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "roleid", referencedColumnName = "ID") })
	private List<AppRole> roles = new ArrayList<>();

}
