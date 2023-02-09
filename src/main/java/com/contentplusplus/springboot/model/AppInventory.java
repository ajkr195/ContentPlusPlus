package com.contentplusplus.springboot.model;

import java.io.Serializable;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppInventory")
@Table(name = "app_inventory")
public class AppInventory  extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "active")
	private boolean active;

	public AppInventory(String title, String description, boolean active) {
		super();
		this.title = title;
		this.description = description;
		this.active = active;
	}
	
	
	
}
