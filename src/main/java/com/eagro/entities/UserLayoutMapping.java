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

/**
 * UserLayoutMapping entity.
 */
@Entity
@Table(name = "user_layout_mapping")
public class UserLayoutMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Column(name = "id")
	private Long id;

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
	@JoinColumn(name = "layout_id", nullable = false)
	private Layout layout;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public UserLayoutMapping createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public UserLayoutMapping createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public UserLayoutMapping updatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
		return this;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public UserLayoutMapping updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserLayoutMapping userLayoutMapping = (UserLayoutMapping) o;
		if (userLayoutMapping.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), userLayoutMapping.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "UserLayoutMapping{" + "id=" + getId() + ", activeFlag='" + isActiveFlag() + "'" + ", createdDate='"
				+ getCreatedDate() + "'" + ", createdBy='" + getCreatedBy() + "'" + ", updatedDate='" + getUpdatedDate()
				+ "'" + ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
