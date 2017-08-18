package com.cloudzon.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "es_user")
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_name", nullable = false, length = 100)
	private String username;

	@Column(name = "mobile_number", nullable = false, length = 100)
	private String mobileNumber;

	@Column(name = "first_name", length = 50)
	private String firstName;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "profile_picture", length = 100)
	private String profilePicture;

	@Column(name = "is_verified", columnDefinition = "BIT DEFAULT 0", length = 1, nullable = false)
	private Boolean isVerified;

	@Column(name = "is_active", columnDefinition = "BIT DEFAULT 1", length = 1, nullable = false)
	private Boolean isActive = false;

	@Column(name = "is_suspect", columnDefinition = "BIT DEFAULT 0", length = 1, nullable = true)
	private Boolean isSuspect;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "es_user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	@BatchSize(size = 20)
	private Set<Authority> authorities = new HashSet<>();

	public String getFullName() {
		StringBuilder objSB = new StringBuilder();
		objSB.append(this.firstName).append(" ").append(this.lastName);
		return objSB.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsSuspect() {
		return isSuspect;
	}

	public void setIsSuspect(Boolean isSuspect) {
		this.isSuspect = isSuspect;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", mobileNumber=" + mobileNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", profilePicture=" + profilePicture + ", isVerified="
				+ isVerified + ", isActive=" + isActive + ", isSuspect=" + isSuspect + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;
		return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
	}

}
