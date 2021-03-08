package com.tts.TechTalentTwitter.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
  /*Represents the User table for the H2 Database*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	private String email;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	/* To do this, we already created a variable
	 * called active, which indicates whether or
	 * not the user is enabled.*/
	private int active;

	@CreationTimestamp 
	private Date createdAt;
	
	/*Add a Many-to-Many relationship with roles*/
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
}