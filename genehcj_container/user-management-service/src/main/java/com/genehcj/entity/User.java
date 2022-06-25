package com.genehcj.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genehcj.config.Constants;
import com.genehcj.entity.enumeration.AuthProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 3, max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;
	
	@Size(min = 3, max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;
	
	@Email
	@Size(min = 4, max = 254)
	@Column(unique = true, length = 254)
	private String email;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 4, max = 64)
	@Column(length = 64, unique = true, nullable = false)
	private String login;
	
	@NotBlank
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", nullable = false, length = 60)
	@JsonIgnore
	private String password;
	
	@Size(max = 256)
	@Column(name = "image_url", length = 256)
	private String imageUrl;
	
	@Size(min = 2, max = 10)
	@Column(name = "lang_key", length = 10)
	private String langKey;
	
	private LocalDate birthday;

	private Boolean gender;
	
	@Size(max = 256)
	@Column(length = 256)
	private String address;
	
	@Size(min = 3, max = 20)
	@Column(name = "phone_number", length = 20)
	private String phoneNumber;
	
	@NotNull
	@Column(nullable = false)
	private Boolean activated = false;
	
	@Size(max = 20)
	@JsonIgnore
	@Column(name = "activation_key", length = 20)
	private String activationKey;
	
	@Size(max = 20)
	@JsonIgnore
	@Column(name = "reset_key", length = 20)
	private String resetKey = null;
	
	@Column(name = "reset_date")
	private Instant resetDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthProvider authProvider;
	
	@JsonIgnore
	@ManyToMany
	@BatchSize(size = 20)
	@JoinTable(
			name = "t_user_authority",
			joinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "id_authority", referencedColumnName = "name") })
	private Set<Authority> authorities = new HashSet<Authority>();
	
}
