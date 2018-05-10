package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A user.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user")
	@SequenceGenerator(name = "sequence_user", sequenceName = "sequence_user", allocationSize = 1)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "login_key")
	private String loginKey;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "active_flag")
	private boolean activeFlag;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private LocalDate updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserLayoutMapping userLayoutMapping;

	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private UserRole userRole;

	public Long getUserId() {
		return userId;
	}

	public User userId(Long userId) {
		this.userId = userId;
		return this;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public User loginKey(String loginKey) {
		this.loginKey = loginKey;
		return this;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getPassword() {
		return password;
	}

	public User password(String password) {
		this.password = password;
		return this;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public User firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public User middleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public User lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public User emailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public User createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public User createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public UserLayoutMapping getUserLayoutMapping() {
		return userLayoutMapping;
	}

	public void setUserLayoutMapping(UserLayoutMapping userLayoutMapping) {
		this.userLayoutMapping = userLayoutMapping;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public User updatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
		return this;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public User updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public UserRole getUserRole() {
		return userRole;
	}

	public User userRole(UserRole userRole) {
		this.userRole = userRole;
		return this;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User eagroUser = (User) o;
		if (eagroUser.getUserId() == null || getUserId() == null) {
			return false;
		}
		return Objects.equals(getUserId(), eagroUser.getUserId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUserId());
	}

	@Override
	public String toString() {
		return "EagroUser{" + ", userId=" + getUserId() + ", loginKey='" + getLoginKey() + "'" + ", password='"
				+ getPassword() + "'" + ", firstName='" + getFirstName() + "'" + ", middleName='" + getMiddleName()
				+ "'" + ", lastName='" + getLastName() + "'" + ", emailAddress='" + getEmailAddress() + "'"
				+ ", activeFlag='" + isActiveFlag() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", createdBy='"
				+ getCreatedBy() + "'" + ", updatedDate='" + getUpdatedDate() + "'" + ", updatedBy='" + getUpdatedBy()
				+ "'" + "}";
	}
}
