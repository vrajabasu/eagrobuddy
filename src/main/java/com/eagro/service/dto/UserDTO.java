package com.eagro.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.eagro.entities.Authority;
import com.eagro.entities.User;

/**
 * A DTO for the EagroUser entity.
 */
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDTO() {
		// Empty constructor needed for Jackson.
	}

	private Long userId;

	private String loginKey;

	private String password;

	private String firstName;

	private String middleName;

	private String lastName;

	private String emailAddress;

	private boolean activeFlag;

	private LocalDateTime createdDate;

	private String createdBy;

	private LocalDateTime updatedDate;

	private String updatedBy;

	private Set<String> authorities;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.loginKey = user.getLoginKey();
		this.firstName = user.getFirstName();
		this.middleName = user.getMiddleName();
		this.lastName = user.getLastName();
		this.emailAddress = user.getEmailAddress();
		this.activeFlag = user.isActiveFlag();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.updatedBy = user.getUpdatedBy();
		this.updatedDate = user.getUpdatedDate();
		this.password = user.getPassword();
		this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UserDTO eagroUserDTO = (UserDTO) o;
		if (eagroUserDTO.getUserId() == null || getUserId() == null) {
			return false;
		}
		return Objects.equals(getUserId(), eagroUserDTO.getUserId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUserId());
	}

	@Override
	public String toString() {
		return "EagroUserDTO{" + ", userId=" + getUserId() + ", loginKey='" + getLoginKey() + "'" + ", password='"
				+ getPassword() + "'" + ", firstName='" + getFirstName() + "'" + ", middleName='" + getMiddleName()
				+ "'" + ", lastName='" + getLastName() + "'" + ", emailAddress='" + getEmailAddress() + "'"
				+ ", activeFlag='" + getActiveFlag() + "'" + ", createdDate='" + getCreatedDate() + "'"
				+ ", createdBy='" + getCreatedBy() + "'" + ", updatedDate='" + getUpdatedDate() + "'" + ", updatedBy='"
				+ getUpdatedBy() + "'" + "}";
	}

}
